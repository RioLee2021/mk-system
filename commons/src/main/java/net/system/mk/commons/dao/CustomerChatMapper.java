package net.system.mk.commons.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import net.system.mk.commons.ext.CustomerChatResponse;
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

    @Select("select count(1) from customer_chat where customer_id=#{customerId} and reply_flg = false")
    Integer unReplyCntByCustomerId(@Param("customerId") Integer customerId);

    @Select("select cc.id as chat_id, cc.mbr_id,mi.account, mi.login_ip as last_login_ip,cc.reply_flg, " +
            "(select if(cml1.image_flg,concat('img[',cml1.content,']'),cml1.content) from chat_msg_log cml1 where cml1.chat_id = cc.id and cml1.owner_id = cc.mbr_id order by cml1.id desc limit 1) as msg_content, " +
            "(select cml2.create_at from chat_msg_log cml2 where cml2.chat_id = cc.id and cml2.owner_id = cc.mbr_id order by  cml2.id desc limit 1) as send_time," +
            "(select if(cml3.image_flg,concat('img[',cml3.content,']'),cml3.content) from chat_msg_log cml3 where cml3.chat_id = cc.id and cml3.owner_id != cc.mbr_id order by cml3.id desc limit 1) as reply_content, " +
            "(select cml4.create_at from chat_msg_log cml4 where cml4.chat_id = cc.id and cml4.owner_id != cc.mbr_id order by  cml4.id desc limit 1) as reply_time" +
            " from customer_chat cc inner join mbr_info mi on cc.mbr_id = mi.id ${ew.customSqlSegment}")
    IPage<CustomerChatResponse> getPageByEw(IPage<CustomerChatResponse> page, @Param("ew")QueryWrapper<Object> ew);


}
