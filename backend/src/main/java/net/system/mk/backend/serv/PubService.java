package net.system.mk.backend.serv;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import net.system.mk.backend.ctrl.vo.CurrentTaskCnt;
import net.system.mk.backend.ctrl.vo.PermMenuTree;
import net.system.mk.backend.ctrl.vo.PermUserLoginRequest;
import net.system.mk.commons.conf.AppConfig;
import net.system.mk.commons.conf.OptionsTableNameHandler;
import net.system.mk.commons.constant.RedisCodeKey;
import net.system.mk.commons.ctx.IBaseContext;
import net.system.mk.commons.ctx.ICtxHelper;
import net.system.mk.commons.dao.*;
import net.system.mk.commons.enums.*;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.ip2region.IpSearcher;
import net.system.mk.commons.meta.*;
import net.system.mk.commons.pojo.MerchantConfig;
import net.system.mk.commons.pojo.PermMenu;
import net.system.mk.commons.pojo.PermUser;
import net.system.mk.commons.pojo.PermUserLoginLog;
import net.system.mk.commons.redis.RedisHelper;
import net.system.mk.commons.utils.OtherUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static net.system.mk.commons.expr.GlobalErrorCode.BUSINESS_ERROR;
import static net.system.mk.commons.expr.GlobalErrorCode.LOGIN_INVALID;
import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2025-11-2025/11/27/0027 9:48
 */
@Service
public class PubService {

    @Resource
    private ICtxHelper ctxHelper;
    @Resource
    private PermUserMapper permUserMapper;
    @Resource
    private PermUserLoginLogMapper permUserLoginLogMapper;
    @Resource
    private IpSearcher ipSearcher;
    @Resource
    private PermMenuMapper permMenuMapper;
    @Value("${mybatis-plus.type-enums-package}")
    private String pkg;
    @Resource
    private DynamicOptionsMapper dynamicOptionsMapper;
    @Resource
    private MerchantConfigMapper merchantConfigMapper;
    @Resource
    private CustomerChatMapper customerChatMapper;
    @Resource
    private MbrWithdrawRecordMapper mbrWithdrawRecordMapper;
    @Resource
    private MbrPdRequestMapper mbrPdRequestMapper;
    @Resource
    private RedisHelper redisHelper;
    @Resource
    private AppConfig appConfig;


    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<PermUser> login(PermUserLoginRequest request) {
        RequestBaseData rbd = RequestBaseData.getInstance();
        PermUser usr = permUserMapper.getByAccountAndPassword(request.getAccount(), request.getPassword());
        if (usr == null) {
            throw new GlobalException(BUSINESS_ERROR, "用户名或密码错误");
        }
        if (usr.isBanned()) {
            throw new GlobalException(BUSINESS_ERROR, "用户被禁用");
        }
        MerchantConfig mch = merchantConfigMapper.selectById(usr.getMerchantId());
        if (mch != null && !OtherUtils.isPermitted(rbd.getIp(), mch.getApiWhiteList())) {
            throw new GlobalException(BUSINESS_ERROR, "IP被限制");
        }
        //判断是否有旧的TOKEN
        if (StrUtil.isNotBlank(usr.getToken())) {
            ctxHelper.kickBackendCtx(usr.getToken());
            redisHelper.deleteTouch(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN_PRIVILEGE, usr.getToken());
        }
        String token = "b#" + IdUtil.fastSimpleUUID();
        usr.setToken(token);
        permUserMapper.updateById(usr);
        PermUserLoginLog log = new PermUserLoginLog();
        log.setPermUserId(usr.id()).setFromIp(rbd.getIp()).setFromRegion(ipSearcher.search(rbd.getIp()));
        //拿500字符长度UA
        if (StrUtil.isNotBlank(rbd.getUserAgent())) {
            log.setFromUa(StrUtil.sub(rbd.getUserAgent(), 0, 500));
        } else {
            log.setFromUa("undefined");
        }
        permUserLoginLogMapper.insert(log);
        ctxHelper.putBackendCtx(usr);
        redisHelper.set(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN_PRIVILEGE, token, permMenuMapper.getUriByRoleType(usr.role()));
        return ResultBody.okData(usr);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public ResultBody<Void> logout() {
        IBaseContext ctx = ctxHelper.getBackendCtx();
        PermUser usr = permUserMapper.selectById(ctx.id());
        usr.setToken(null);
        permUserMapper.updateById(usr);
        ctxHelper.kickBackendCtx(ctx.token());
        redisHelper.deleteTouch(RedisCodeKey.Backend.BACKEND_USER_LOGIN_TOKEN_PRIVILEGE, ctx.token());
        return ResultBody.success();
    }

    public ResultBody<PermUser> info() {
        IBaseContext ctx = ctxHelper.getBackendCtx();
        if (ctx == null) {
            throw new GlobalException(LOGIN_INVALID);
        }
        return ResultBody.okData((PermUser) ctx);
    }

    public List<PermMenuTree> menus() {
        IBaseContext ctx = ctxHelper.getBackendCtx();
        List<PermMenu> menus = permMenuMapper.getMenuByRoleType(ctx.role());
        if (ctx.role() == RoleType.SuperAdmin) {
            menus = permMenuMapper.getMenusBySuperAdmin();
        }
        if (ctx.role() == RoleType.WebMaster) {
            menus = permMenuMapper.getMenusByWebMaster();
        }
        Map<PermMenuGroup, List<PermMenu>> menuMap = menus.stream().collect(Collectors.groupingBy(PermMenu::getPermMenuGroup));
        //根据权限范围划分
        List<PermMenuTree> rs = Lists.newArrayList();
        MenuScope currentScope = ctx.merchantId() == 0 ? MenuScope.platform : MenuScope.merchant;
        for (Map.Entry<PermMenuGroup, List<PermMenu>> entry : menuMap.entrySet()) {
            //菜单组
            PermMenuTree group = new PermMenuTree();
            group.setMenuName(entry.getKey().getChName())
                    .setMenuPath(entry.getKey().getPath())
                    .setIcon(entry.getKey().getIcon())
                    .setSortNo(entry.getKey().getSortNo())
                    .setSelected(Boolean.TRUE);
            for (PermMenu menu : entry.getValue()) {
                if (menu.getMenuScope() == MenuScope.both || menu.getMenuScope() == currentScope) {
                    PermMenuTree item = new PermMenuTree();
                    item.setMenuName(menu.getMenuName())
                            .setMenuPath(menu.getMenuPath())
                            .setSortNo(menu.getSortNo())
                            .setSortNo(menu.getSortNo())
                            .setSelected(Boolean.TRUE);
                    group.getChildren().add(item);
                }
            }
            if (group.getChildren().size() > 0) {
                group.getChildren().sort(Comparator.comparingInt(PermMenuTree::getSortNo));
                rs.add(group);
            }
        }
        rs.sort(Comparator.comparingInt(PermMenuTree::getSortNo));
        return rs;
    }


    public List<DictResponse> dict() {
        Reflections ref = new Reflections(pkg);
        Set<Class<? extends IDbEnums>> enums = ref.getSubTypesOf(IDbEnums.class);
        Map<String, DictResponse> map = new ConcurrentHashMap<>();
        for (Class<? extends IDbEnums> clazz : enums) {
            String name = clazz.getSimpleName();
            DictResponse dr = map.get(name);
            if (dr == null) {
                dr = new DictResponse();
                dr.setDictName(name);
            }
            IDbEnums[] es = clazz.getEnumConstants();
            if (es != null && es.length > 0) {
                for (IDbEnums e : es) {
                    DictItem di = new DictItem();
                    di.setLabel(e.getChName());
                    di.setValue(e.ordinal());
                    dr.addItem(di);
                }
            }
            map.put(name, dr);
        }
        return new ArrayList<>(map.values());
    }


    public List<DictItem> options(OptionsTypeRequest request) {
        OptionsType ot = request.getType();
        try {
            OptionsTableNameHandler.setTableName(ot);
            List<JSONObject> data;
            if (ot.isMerchantInject()) {
                IBaseContext ctx = ctxHelper.getBackendCtx();
                if (ot == OptionsType.merchant_config) {
                    data = dynamicOptionsMapper.selectOptions();
                    if (ctx.merchantId() != 0) {
                        data = data.stream().filter(x -> x.getIntValue("id") == ctx.merchantId()).collect(Collectors.toList());
                    }
                } else {
                    data = dynamicOptionsMapper.selectMerchantOptions(ctx.merchantId());
                }
            } else {
                data = dynamicOptionsMapper.selectOptions();
            }
            if (data != null && data.size() > 0) {
                List<DictItem> rs = data.stream().map(x -> Convert.convert(ot.getDictItemClass(), x).toDictItem()).collect(Collectors.toList());
                return rs.stream().filter(Objects::nonNull).collect(Collectors.toList());
            }
            return null;
        } finally {
            OptionsTableNameHandler.remove();
        }
    }

    public ResultBody<CurrentTaskCnt> currentTaskCnt() {
        IBaseContext ctx = ctxHelper.getBackendCtx();
        CurrentTaskCnt rs = new CurrentTaskCnt();
        rs.setChatCnt(customerChatMapper.unReplyCntByCustomerId(ctx.id()));
        rs.setWithdrawCnt(mbrWithdrawRecordMapper.unProcessedCnt());
        rs.setPdReqCnt(mbrPdRequestMapper.getRunningCnt());
        return ResultBody.okData(rs);
    }

    public ResultBody<String> upload(MultipartFile file) {
        File root = new File(appConfig.getUploadRoot());
        String originalFilename = file.getOriginalFilename();
        String filename = IdUtil.fastSimpleUUID();
        if (originalFilename != null) {
            int lastDotIndex = originalFilename.lastIndexOf(".");
            if (lastDotIndex > 0) {
                filename += originalFilename.substring(lastDotIndex);
            }
        }
        File target = new File(root, filename);
        try {
            file.transferTo(target);
            return ResultBody.okData("/" + filename);
        } catch (IOException e) {
            throw new GlobalException(BUSINESS_ERROR, "文件上传失败");
        }
    }
}
