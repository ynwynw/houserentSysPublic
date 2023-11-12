package com.wt.hrentSystem.hrent.dao;

import com.wt.hrentSystem.common.dao.BaseDao;
import com.wt.hrentSystem.hrent.entity.ApplyView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplyViewDao extends BaseDao<ApplyView> {
    //查询个人申请信息
    List<ApplyView> findPersonalViewApplyPage(ApplyView applyView);

    //查询个人看房申请记录条数
    Integer findCountByUserId(String userId);

}
