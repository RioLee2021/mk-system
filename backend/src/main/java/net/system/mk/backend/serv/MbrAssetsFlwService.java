package net.system.mk.backend.serv;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.system.mk.backend.ctrl.biz.vo.MbrAssetsFlwPagerRequest;
import net.system.mk.commons.dao.MbrAssetsFlwMapper;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.MbrAssetsFlw;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 15:37
 */
@Service
public class MbrAssetsFlwService {

    @Resource
    private MbrAssetsFlwMapper mbrAssetsFlwMapper;



    public PagerResult<MbrAssetsFlw> list(MbrAssetsFlwPagerRequest request) {
        QueryWrapper<Object> q = OtherUtils.createIdDescWrapper(request,"maf");
        Object m = request.getMember();
        if (m!=null){
            q.and(oc->oc.eq("mb.account",m).or().eq("mb.phone",m).or().eq("mb.id",m));
        }
        return PagerResult.of(mbrAssetsFlwMapper.getPageByEw(request.toPage(),q));
    }
}
