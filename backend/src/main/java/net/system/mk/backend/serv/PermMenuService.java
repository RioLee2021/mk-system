package net.system.mk.backend.serv;

import net.system.mk.backend.ctrl.auth.vo.PermMenuPagerRequest;
import net.system.mk.backend.ctrl.auth.vo.PermMenuUpdateRequest;
import net.system.mk.commons.dao.PermMenuMapper;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.PermMenu;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2025-11-2025/11/28/0028 21:01
 */
@Service
public class PermMenuService {

    @Resource
    private PermMenuMapper permMenuMapper;

    public PagerResult<PermMenu> list(PermMenuPagerRequest request) {
        return PagerResult.of(permMenuMapper.selectPage(request.toPage(), OtherUtils.createIdDescWrapper(request)));
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void update(PermMenuUpdateRequest request) {
        PermMenu menu = permMenuMapper.selectById(request.getId());
        if (menu==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "菜单不存在");
        }
        menu.setIcon(request.getIcon()).setSortNo(request.getSortNo());
        permMenuMapper.updateById(menu);
    }
}
