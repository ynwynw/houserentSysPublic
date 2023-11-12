package com.wt.hrentSystem.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dict extends BaseEntity<Dict>{
    //字典值
    private String value;
    //字典标签
    private String label;
    //字典类型
    private String type;
    //字典描述
    private String description;
    //字典序号
    private String sort;

    //前端查询
    private String condition;
}
