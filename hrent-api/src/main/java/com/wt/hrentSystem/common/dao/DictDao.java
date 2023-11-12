package com.wt.hrentSystem.common.dao;

import com.wt.hrentSystem.common.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictDao extends BaseDao<Dict>{
    //查询字典所有类型
    List<Dict> findAllType();
}
