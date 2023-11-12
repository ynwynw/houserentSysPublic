package com.wt.hrentSystem.hrent.dao;

import com.wt.hrentSystem.common.dao.BaseDao;
import com.wt.hrentSystem.hrent.entity.Pic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PicDao extends BaseDao<Pic> {

    //物理删除数据
    void realDelete(String id);
}
