package com.wt.hrentSystem.hrent.service;

import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.service.BaseService;
import com.wt.hrentSystem.hrent.dao.TagDao;
import com.wt.hrentSystem.hrent.entity.Tag;
import com.wt.hrentSystem.hrent.entity.dto.TagDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TagService")
public class TagService extends BaseService<TagDao, Tag> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Tag get(String id) {
        return super.get(id);
    }

    /**
     * 查询多条数据
     * @param tag
     * @return
     */
    public List<Tag> findList(Tag tag){
        return super.findList(tag);
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param tag
     * @return 消息集合
     */
    public Page<Tag> findPage(Page page, Tag tag) {
        return super.findPage(page,tag);
    }


    /**
     * 删除数据
     * @param tag
     */

    public void delete(Tag tag) {
        super.delete(tag);
    }

    /**
     * 更新或插入数据
     */
    public void save(Tag tag){
        super.save(tag);
    }

    //通过房源id查找所有标签
    public List<TagDto> findByHouseId(String houseId) {
        return this.dao.findByHouseId(houseId);
    }

    //添加房源与标签的关系
    public void addHouseTags(TagDto tagDto) {
        this.dao.addHouseTags(tagDto);
    }

    //删除某个房源的所有标签
    public void deleteByHouseId(String houseId){
        this.dao.deleteByHouseId(houseId);
    }
}
