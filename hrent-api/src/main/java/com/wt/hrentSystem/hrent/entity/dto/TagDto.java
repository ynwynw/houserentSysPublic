package com.wt.hrentSystem.hrent.entity.dto;

import com.wt.hrentSystem.common.entity.BaseEntity;
import com.wt.hrentSystem.hrent.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto  extends BaseEntity<Tag> {
    //标签名字
    private String name;
    //标签颜色rgb(0,0,0)
    private String rgb;
    //标签状态(0:待审核，1:已通过，2，未通过)
    private String state;

    //房源id
    private String houseId;
    //标签id
    private String tagId;
}
