package com.wt.hrentSystem.hrent.dao;

import com.wt.hrentSystem.common.dao.BaseDao;
import com.wt.hrentSystem.hrent.entity.Collection;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CollectionDao extends BaseDao<Collection> {

    //查询某个用户收藏的房源数量
    Integer findCountByUserId(String userId);
}
