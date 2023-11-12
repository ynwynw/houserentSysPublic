package com.wt.hrentSystem.hrent.dao;

import com.wt.hrentSystem.common.dao.BaseDao;
import com.wt.hrentSystem.hrent.entity.ApplyRent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ApplyRentDao extends BaseDao<ApplyRent> {

    //查询个人的租房申请记录
    List<ApplyRent> findPersonalViewApplyPage(ApplyRent applyRent);

    int countApply(@Param("startTime")Date startTime,@Param("endTime")Date endTime);
}
