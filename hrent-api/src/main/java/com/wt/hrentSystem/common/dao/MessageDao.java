package com.wt.hrentSystem.common.dao;

import com.wt.hrentSystem.common.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageDao extends BaseDao<Message>{

    //获取当前登录人的新消息
    Integer getNewCount(Message message);

    //查询某个人的消息数量（未读）
    Integer getCountByUserId(String userId);

    //查询某个人的消息列表
    List<Message> findPersonalMessagePage(Message message);
}
