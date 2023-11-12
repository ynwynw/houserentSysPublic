package com.wt.hrentSystem.hrent.entity;

import com.wt.hrentSystem.common.entity.BaseEntity;
import com.wt.hrentSystem.hrent.entity.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

//房源信息实体类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseResource extends BaseEntity<HouseResource> {
    //房源所在省
    private String province;
    //房源所在市
    private String city;
    //房源所在区(县)
    private String county;
    //房源所在具体地址
    private String address;
    //租金
    private Double money;
    //面积
    private Integer area;
    //房源描述
    private String describe;
    //房源类型
    private String houseType;
    //房源所在楼层
    private Integer floor;
    //房源修建时间
    private String buildTime;
    //出租类型(0:整租，1:合租)
    private Integer rentType;
    //合租人数(整租时为1)
    private Integer peopleNum;
    //房源状态
    private Integer state;
    //中心纬度
    private BigDecimal latitude;
    //中心经度
    private BigDecimal longitude;


    //与前端相关参数
    //房源图片集合
    private List<Pic> picList;
    //房源标签集合
    private List<TagDto> tagList;
    //查询开始面积
    private Integer minArea;
    //查询结束面积
    private Integer maxArea;
    //查询开始房租
    private Integer minMoney;
    //查询结束房租
    private Integer maxMoney;
    //模糊查询条件
    private String condition;
    //房源类型字典值
    private String houseTypeStr;
    //标签数量
    private Integer tagNum;
}
