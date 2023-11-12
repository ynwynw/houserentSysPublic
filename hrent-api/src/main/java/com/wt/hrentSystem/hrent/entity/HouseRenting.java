package com.wt.hrentSystem.hrent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wt.hrentSystem.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//在租信息实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseRenting extends BaseEntity<HouseRenting> {
    //房源id
    private String houseId;
    //合同地址
    private String contract;
    //租客id
    private String tenantId;
    //租客名字
    private String tenantName;
    //租金
    private Double money;
    //租房开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    //租房结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    //前端需要的参数
    //开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String startTimeSearch;
    //结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String endTimeSearch;
    //模糊搜索条件
    private String condition;

}
