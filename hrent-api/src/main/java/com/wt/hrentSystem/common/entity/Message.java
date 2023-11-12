package com.wt.hrentSystem.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseEntity<Message>{
    //接收类型(0:人，1:角色)
    private String receiverType;
    //接收人id
    private String receiverId;
    //消息标题
    private String title;
    //消息名称
    private String messageName;
    //消息内容
    private String text;
    //消息状态(0:未读，1:已读)
    private Integer state;
    //消息所处系统
    private String system;
}
