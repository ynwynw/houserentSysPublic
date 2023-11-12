package com.wt.hrentSystem.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * (SysUser)实体类
 *
 * @author makejava
 * @since 2021-08-19 10:52:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity<User> {
    private static final long serialVersionUID = 701316766510450438L;
    /**
     * 角色
     */
    private Integer role;
    //角色名字
    private String roleStr;
    /**
     * 用户名
     */
    private String name;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 头像图片id
     */
    private String pId;
    /**
     * 头像图片地址
     */
    private String headUrl;
    /**
     * 性别（0：男，1：女）
     */
    private String sex;
    /**
     * 微信小程序用户唯一标识符openid
     */
    private String openId;
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date birthday;
    /**
     * 登录时间(最近一次)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /**
     * 数据库之外的属性
     */
    /**
     * 该用户对应的菜单
     */

    //注册开始时间（前端查询条件）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    //注册结束时间(前端查询条件)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    //模糊查询条件
    private String condition;

    //用户拥有房源数量
    private Integer houseNum;

}
