package com.wt.hrentSystem.common.dao;

import com.wt.hrentSystem.common.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDao extends BaseDao<Menu>{
    //查询某个角色已经拥有的角色
    List<String> findRoleMenu(String roleId);

    //批量插入
    void insertBatch(Menu menu);

    //真实删除
    void realDelete(String roleId);
}
