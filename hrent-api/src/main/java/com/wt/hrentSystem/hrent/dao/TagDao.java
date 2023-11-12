package com.wt.hrentSystem.hrent.dao;

import com.wt.hrentSystem.common.dao.BaseDao;
import com.wt.hrentSystem.hrent.entity.Tag;
import com.wt.hrentSystem.hrent.entity.dto.TagDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagDao extends BaseDao<Tag> {
    //通过房源id查找所有标签
    List<TagDto> findByHouseId(@Param("houseId")String houseId);

    //新增房源的标签关系
    void addHouseTags(TagDto tagDto);

    void deleteByHouseId(String houseId);
}
