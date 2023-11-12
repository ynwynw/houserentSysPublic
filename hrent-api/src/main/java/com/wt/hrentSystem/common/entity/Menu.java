package com.wt.hrentSystem.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 菜单实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends TreeEntity<Menu>{
    //角色类型
    private String roleId;
    //菜单路径
    private String url;
    //菜单图标
    private String icon;
    //菜单所属系统
    private Integer system;

    //前端需要使用的参数
    private List<Menu> menuList;

    //更新时间
    private Date updateTime;
}
