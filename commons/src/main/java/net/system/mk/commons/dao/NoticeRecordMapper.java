package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.system.mk.commons.enums.LangType;
import net.system.mk.commons.pojo.NoticeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author USER
 * @date 2026-01-2026/1/22/0022 16:51
 */
@Mapper
public interface NoticeRecordMapper extends BaseMapper<NoticeRecord> {


    @Select("select * from notice_record where (mbr_id = #{mbrId} or mbr_id = 0) and lang_type = #{langType} order by id")
    List<NoticeRecord> getListByMbrIdAndLangType(@Param("mbrId") Integer mbrId, @Param("langType") LangType langType);
}
