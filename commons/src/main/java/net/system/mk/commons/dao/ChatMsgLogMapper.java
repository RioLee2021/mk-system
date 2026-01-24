package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.system.mk.commons.pojo.ChatMsgLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author USER
 * @date 2026-01-2026/1/25/0025 0:12
 */
@Mapper
public interface ChatMsgLogMapper extends BaseMapper<ChatMsgLog> {

    @Select("select * from chat_msg_log where chat_id = #{chatId} order by id desc limit 100")
    List<ChatMsgLog> getLatest100MsgByChatId(@Param("chatId")Integer chatId);

    @Select("select * from chat_msg_log where chat_id = #{chatId} order by id desc limit 5")
    List<ChatMsgLog> getLatest5MsgByChatId(@Param("chatId")Integer chatId);
}
