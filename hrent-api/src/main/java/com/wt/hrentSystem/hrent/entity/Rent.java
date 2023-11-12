package com.wt.hrentSystem.hrent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wt.hrentSystem.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//在租的租金信息实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rent extends BaseEntity<Rent> {
    //在租信息id
    private String rentingId;
    //租金应缴日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payableTime;
    //租金实缴日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;
    //租客id
    private String tenantId;
    //租客名字
    private String tenantName;
    //租金信息状态(0:未到时间，1:已到时间，2:已交租)
    private Integer state;

    //前端需要的字段
    //查询开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTimeSearch;
    //查询结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTimeSearch;
    //模糊搜索条件
    private String condition;

}
