package com.wt.hrentSystem.hrent.entity;

import com.wt.hrentSystem.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//收藏信息实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collection extends BaseEntity<Collection> {
    //房源id
    private String houseId;
    //收藏人id
    private String userId;
}
