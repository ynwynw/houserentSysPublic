package com.wt.hrentSystem.hrent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wt.hrentSystem.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 租房申请实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRent extends BaseEntity<ApplyRent> {
    //申请人id
    private String applyUserId;
    //房源id
    private String houseId;
    //租房开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    //租房结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    //状态
    private Integer state;
    //押金
    private Double deposit;
    //租房合同地址
    private String contractUrl;

    //前端需要使用的属性字段(0：未处理，1：已处理)
    private Integer isHandle;

    //房源实体类
    private HouseResource houseResource;
    //查询条件开始时间个结束时间
    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTimeApply;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTimeApply;
}
