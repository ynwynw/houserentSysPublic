package com.wt.hrentSystem.wechat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WechatUser{
	//名字
	private String name;
	//微信code
	private String code;
	//性别
	private String gender;
	//头像
	private String headUrl;

}