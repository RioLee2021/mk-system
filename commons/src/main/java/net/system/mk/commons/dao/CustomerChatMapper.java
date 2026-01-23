package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.system.mk.commons.pojo.CustomerChat;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * @author USER
 * @date 2026-01-2026/1/23/0023 19:39
 */
@Mapper
public interface CustomerChatMapper extends BaseMapper<CustomerChat> {

    @Select("select count(1) from customer_chat where customer_id=#{customerId} and replay_flg = false")
    Integer unReplyCntByCustomerId(@Param("customerId") Integer customerId);
}
