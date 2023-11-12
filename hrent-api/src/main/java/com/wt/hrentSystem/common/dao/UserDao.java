package com.wt.hrentSystem.common.dao;



import com.wt.hrentSystem.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * (SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-19 10:52:33
 */
@Mapper
public interface UserDao extends BaseDao<User>{

    //通过openid查询用户
    User findUserByOpenid(String openId);
}

