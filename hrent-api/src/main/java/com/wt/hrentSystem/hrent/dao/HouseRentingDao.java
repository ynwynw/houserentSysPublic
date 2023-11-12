package com.wt.hrentSystem.hrent.dao;

import com.wt.hrentSystem.common.dao.BaseDao;
import com.wt.hrentSystem.hrent.entity.HouseRenting;
import com.wt.hrentSystem.hrent.entity.StaticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface HouseRentingDao extends BaseDao<HouseRenting> {
    //通过houseId查询在租列表的数据
    int getNumByHouseId(String houseId);

    //查询某个用户正在租的房源数量
    Integer getCountByUserId(@Param("userId") String userId, @Param("date") Date date);

    //房源地址统计
    List<StaticsVO> getAddressCount();
}
