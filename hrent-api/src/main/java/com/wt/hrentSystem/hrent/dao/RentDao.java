package com.wt.hrentSystem.hrent.dao;

import com.wt.hrentSystem.common.dao.BaseDao;
import com.wt.hrentSystem.hrent.entity.Rent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RentDao extends BaseDao<Rent> {
}
