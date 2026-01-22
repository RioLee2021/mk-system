package net.system.mk.backend.serv;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.system.mk.backend.ctrl.basic.vo.NoticeRecordPagerRequest;
import net.system.mk.backend.ctrl.basic.vo.NoticeRecordSaveRequest;
import net.system.mk.commons.dao.NoticeRecordMapper;
import net.system.mk.commons.expr.GlobalErrorCode;
import net.system.mk.commons.expr.GlobalException;
import net.system.mk.commons.meta.PagerResult;
import net.system.mk.commons.pojo.NoticeRecord;
import net.system.mk.commons.utils.OtherUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;

/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 16:52
 */
@Service
public class NoticeRecordService {

    @Resource
    private NoticeRecordMapper noticeRecordMapper;

    public PagerResult<NoticeRecord> list(NoticeRecordPagerRequest request) {
        QueryWrapper<NoticeRecord> q = OtherUtils.createIdDescWrapper(request);
        if (request.getId()!=null){
            q.and(oc->oc.eq("id",request.getId()).or().eq("mbr_id",request.getId()));
        }
        return PagerResult.of(noticeRecordMapper.selectPage(request.toPage(),q));
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public void add(NoticeRecordSaveRequest request) {
        NoticeRecord data = new NoticeRecord();
        BeanUtil.copyProperties(request,data,"id");
        noticeRecordMapper.insert(data);
    }


    @Transactional(rollbackFor = Exception.class, propagation = REQUIRED)
    public void update(NoticeRecordSaveRequest request) {
        NoticeRecord data = noticeRecordMapper.selectById(request.getId());
        if (data==null){
            throw new GlobalException(GlobalErrorCode.BUSINESS_ERROR, "数据不存在");
        }
        noticeRecordMapper.updateById(data);
    }
}
