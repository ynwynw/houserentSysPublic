package com.wt.hrentSystem.hrent.entity;

import com.wt.hrentSystem.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pic extends BaseEntity<Pic> {
    //图片名字（前端传递的）
    private String name;
    //图片类型(1:小程序轮播图,2:按钮,3:公用,4:系统，5:房源，6:用户)
    private Integer type;
    //图片状态
    private Integer state;
    //图片地址
    private String url;
    //房源id(没有时为null)
    private String houseId;
}
