package com.wt.hrentSystem.hrent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wt.hrentSystem.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//看房申请记录实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyView extends BaseEntity<ApplyView> {
    //申请人id
    private String applyUserId;
    //房源id
    private String houseId;
    //看房时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date viewTime;
    //看房重新选择的时间（房东拒绝后重新选择的时间）
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date viewTimeRe;
    //申请状态(0:申请中，1已通过，2已结束)
    private Integer state;

    //前端需要使用的属性字段(0：未处理，1：已处理)
    private Integer isHandle;
    //房源数据
    private HouseResource houseResource;
    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
}
