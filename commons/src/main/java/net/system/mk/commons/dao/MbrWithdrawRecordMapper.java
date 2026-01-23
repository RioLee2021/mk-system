package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.system.mk.commons.pojo.MbrWithdrawRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 14:58
 */
@Mapper
public interface MbrWithdrawRecordMapper extends BaseMapper<MbrWithdrawRecord> {

    @Select("select count(1) from mbr_withdraw_record where status = 0")
    Integer unProcessedCnt();
}
