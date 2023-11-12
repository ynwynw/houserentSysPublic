package com.wt.hrentSystem.common.entity;

import lombok.Data;

import java.util.List;

@Data
public abstract class TreeEntity<T> extends BaseEntity<T> {
    //父级
    protected T parent;
    //父级的id
    protected String parentIds;
    //名字
    protected String name;
    //排序
    protected Integer sort;
    //父类id
    protected String parentId;
    //子类集合
    protected List<T> children;
    //是否有子节点
    protected boolean existChildren;

    public TreeEntity() {
        this.sort = 30;
    }

}
