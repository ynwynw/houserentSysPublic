package com.wt.hrentSystem.common.service;


import com.wt.hrentSystem.common.dao.BaseDao;
import com.wt.hrentSystem.common.entity.BaseEntity;
import com.wt.hrentSystem.common.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public abstract class BaseService<D extends BaseDao<T>,T extends BaseEntity<T>> {

    @Autowired
    protected D dao;


    public BaseService() {
    }

    //根据id获取对象
    public T get(String id){
        dao.get(id);
        return id!=null && !id.equals("")?this.dao.get(id):null;
    }
    //根据实体类获取实体类对象
    public T getByEntity(T entity){
        return this.dao.getByEntity(entity);
    }
    //通过实体类对象条件查询所有信息
    public List<T> findList(T entity){
        return this.dao.findList(entity);
    }
    //分页查询数据
    public Page<T> findPage(Page<T> page, T entity){
        if(entity.getPage()!=null && entity.getPage().getPageSize()>0){
            page.setPageSize(entity.getPage().getPageSize());
        }
        entity.setPage(page);
        page.setList(this.dao.findList(entity));
        return page;
    }
    //新增或修改数据
    @Transactional(readOnly = false)
    public void save(T entity){
        if(entity.getId()!=null && !entity.getId().equals("")){
            entity.preUpdate();
            this.dao.update(entity);
        }else{
            entity.preInsert();
            this.dao.insert(entity);
        }
    }
    //修改数据
    @Transactional(readOnly = false)
    public void update(T entity){
        this.dao.update(entity);
    }
    //新增数据
    @Transactional(readOnly = false)
    public void insert(T entity){
        entity.preInsert();
        this.dao.insert(entity);
    }
    //删除数据
    @Transactional(readOnly = false)
    public void delete(T entity){
        entity.preUpdate();
        this.dao.delete(entity);
    }
}
