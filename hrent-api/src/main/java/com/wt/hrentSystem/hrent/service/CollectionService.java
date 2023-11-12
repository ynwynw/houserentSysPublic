package com.wt.hrentSystem.hrent.service;

import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.service.BaseService;
import com.wt.hrentSystem.hrent.dao.CollectionDao;
import com.wt.hrentSystem.hrent.entity.Collection;
import com.wt.hrentSystem.hrent.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CollectionService")
public class CollectionService extends BaseService<CollectionDao, Collection> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Collection get(String id) {
        return super.get(id);
    }

    /**
     * 查询多条数据
     * @param collection
     * @return
     */
    public List<Collection> findList(Collection collection){
        return super.findList(collection);
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param collection
     * @return 消息集合
     */
    public Page<Tag> findPage(Page page, Collection collection) {
        return super.findPage(page,collection);
    }


    /**
     * 删除数据
     * @param collection
     */

    public void delete(Collection collection) {
        super.delete(collection);
    }

    /**
     * 更新或插入数据
     */
    public void save(Collection collection){
        super.save(collection);
    }

    /**
     * 根据用户id查询某个用户收藏的房源数量
     */
    public Integer findCountByUserId(String userId){
        return this.dao.findCountByUserId(userId);
    }
}
