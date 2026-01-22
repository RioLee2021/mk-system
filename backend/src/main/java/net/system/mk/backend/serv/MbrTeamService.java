package net.system.mk.backend.serv;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.system.mk.backend.ctrl.biz.vo.MbrTeamPagerRequest;
import net.system.mk.backend.ctrl.biz.vo.TeamStatusUpdRequest;
import net.system.mk.commons.dao.MbrInfoMapper;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.meta.ResultBody;
import net.system.mk.commons.pojo.MbrInfo;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 15:51
 */
@Service
public class MbrTeamService {

    @Resource
    private MbrInfoMapper mbrInfoMapper;

    public PagerResult<MbrInfo> list(MbrTeamPagerRequest request) {
        //先拿当前团队的会员信息
        MbrInfo mb = mbrInfoMapper.getByMerchantIdAndId(request.getMerchantId(), request.getCurrentId());
        if (mb==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "当前团队不存在");
        }
        String route = mb.getRelationshipRoute();
        QueryWrapper<Object> q = OtherUtils.createIdDescWrapper(request,"mb");
        if (request.getCond() != null) {
            q.and(oc ->
                    oc.eq("mb.phone", request.getCond())
                            .or().eq("mb.invite_code", request.getCond())
                            .or().eq("mb.id", request.getCond()).or().eq("mb.account", request.getCond())
            );
        }
        if (StrUtil.isNotBlank(request.getIp())) {
            q.and(orCond -> orCond.eq("mb.register_ip", request.getIp()).or().eq("mb.login_ip", request.getIp()));
        }
        if (request.getSameIpOnly()) {
            q.and(oc -> oc.gt("login_stat.cnt", 1).or().gt("reg_stat.cnt", 1));
        }
        q.likeRight("mb.relationship_route", route+",");
        if (request.getLevel()>0){
            q.apply("LENGTH(mb.relationship_route) - LENGTH(REPLACE(mb.relationship_route, ',', '')) = {0}",route.split(",").length+ request.getLevel());
        }
        return PagerResult.of(mbrInfoMapper.getPageByEw(request.toPage(), q));
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public ResultBody<Void> updTeamStatus(TeamStatusUpdRequest request) {
        MbrInfo mb = mbrInfoMapper.getByMerchantIdAndId(request.getMerchantId(), request.getId());
        if (mb==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "当前团队不存在");
        }
        mbrInfoMapper.updMbrStatusByRelationshipAndMerchantId(mb.getRelationshipRoute(), request.getStatus(), request.getMerchantId());
        return ResultBody.success();
    }
}
