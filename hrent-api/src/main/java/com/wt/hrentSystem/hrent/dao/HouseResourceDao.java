package com.wt.hrentSystem.hrent.dao;

import com.wt.hrentSystem.common.dao.BaseDao;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.hrent.entity.HouseResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface HouseResourceDao extends BaseDao<HouseResource> {
    //微信小程序条件查询
    List<HouseResource> findHouseByConditionPage(HouseResource houseResource);

    //根据角色查询出相应的房源信息
    List<HouseResource> findByRolePage(HouseResource houseResource);

    //查询某个用户的房源数量
    Integer getCountByUserId(String userId);

    //查询我看过的房源
    List<HouseResource> findViewedHousePage(HouseResource houseResource);

    //查询我收藏的房源
    List<HouseResource> findCollectionHousePage(HouseResource houseResource);

    //查询我正在租的房源
    List<HouseResource> findRentingHousePage(HouseResource houseResource);

    //查询地址数据数量
    Integer findScaleCount(HouseResource houseResource);
}
