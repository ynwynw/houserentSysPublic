package com.wt.hrentSystem.hrent.service;

import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.service.BaseService;
import com.wt.hrentSystem.hrent.dao.PicDao;
import com.wt.hrentSystem.hrent.entity.Pic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PicService")
public class PicService extends BaseService<PicDao, Pic> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Pic get(String id) {
        return super.get(id);
    }

    /**
     * 查询多条数据
     * @param pic
     * @return
     */
    public List<Pic> findList(Pic pic){
        return super.findList(pic);
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param pic
     * @return 消息集合
     */
    public Page<Pic> findPage(Page page, Pic pic) {
        return super.findPage(page,pic);
    }


    /**
     * 删除数据
     * @param pic
     */

    public void delete(Pic pic) {
        super.delete(pic);
    }

    /**
     * 更新或插入数据
     */
    public void save(Pic pic){
        super.save(pic);
    }

    /**
     * 删除数据（物理删除）
     */
    public void realDelete(String id){
        this.dao.realDelete(id);
    }
}
