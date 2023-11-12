package com.wt.hrentSystem.common.dao;


import java.util.List;

public interface BaseDao<T> {
    T get(String var1);

    T getByEntity(T var1);

    List<T> findList(T var1);

    List<T> findAllList(T var1);

    /** @deprecated */
    @Deprecated
    List<T> findAllList();

    int insert(T var1);

    int update(T var1);


    int deleteById(String var1);

    int delete(T var1);

}
