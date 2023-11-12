package com.wt.hrentSystem.hrent.service;

import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.User;
import com.wt.hrentSystem.common.service.BaseService;
import com.wt.hrentSystem.hrent.config.PicType;
import com.wt.hrentSystem.hrent.dao.HouseResourceDao;
import com.wt.hrentSystem.hrent.entity.HouseRenting;
import com.wt.hrentSystem.hrent.entity.HouseResource;
import com.wt.hrentSystem.hrent.entity.Pic;
import com.wt.hrentSystem.hrent.entity.Tag;
import com.wt.hrentSystem.hrent.entity.dto.TagDto;
import javafx.scene.control.Tab;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("HouseResourceService")
public class HouseResourceService extends BaseService<HouseResourceDao, HouseResource> {

    @Resource
    PicService picService;

    @Resource
    TagService tagService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public HouseResource get(String id) {
        HouseResource houseResource = super.get(id);
        Pic pic = new Pic();
        pic.setHouseId(houseResource.getId());
        houseResource.setPicList(picService.findList(pic));
        houseResource.setTagList(tagService.findByHouseId(houseResource.getId()));
        return houseResource;
    }

    /**
     * 查询多条数据
     * @param houseResource
     * @return
     */
    public List<HouseResource> findList(HouseResource houseResource){
        return super.findList(houseResource);
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param houseResource
     * @return 消息集合
     */
    public Page<HouseResource> findPage(Page page, HouseResource houseResource) {
        Page page1 = super.findPage(page, houseResource);
        List<HouseResource> list = page1.getList();
        for (HouseResource resource : list) {

            //找出房源的所有图片
            Pic pic = new Pic();
            pic.setHouseId(resource.getId());
            List<Pic> picList = picService.findList(pic);
            resource.setPicList(picList);
            //查找所有的标签

            List<TagDto> tagList = tagService.findByHouseId(resource.getId());
            resource.setTagList(tagList);

        }
        return page1;
    }

    /**
     * 微信小程序通过地址查询房源
     */
    public Page<HouseResource> findHouseByConditionPage(Page page,HouseResource houseResource){
        if(houseResource.getPage()!=null && houseResource.getPage().getPageSize()>0){
            page.setPageSize(houseResource.getPage().getPageSize());
        }
        houseResource.setPage(page);
        List<HouseResource> list = this.dao.findHouseByConditionPage(houseResource);
        for (HouseResource resource : list) {

            //找出房源的所有图片
            Pic pic = new Pic();
            pic.setHouseId(resource.getId());
            List<Pic> picList = picService.findList(pic);
            resource.setPicList(picList);
            //查找所有的标签

            List<TagDto> tagList = tagService.findByHouseId(resource.getId());
            resource.setTagList(tagList);

        }
        page.setList(list);
        return page;
    }


    /**
     * 删除数据
     * @param houseResource
     */

    public void delete(HouseResource houseResource) {
        super.delete(houseResource);
    }

    /**
     * 更新或插入数据
     */
    @Transactional
    public void save(HouseResource houseResource){
        //判断房源id是否为空，如果为空则为新增，如果不为空则为保存
        if(StringUtils.isBlank(houseResource.getId())){
            //生成流水号
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date();
            String format = simpleDateFormat.format(date);
            String uid = "HR"+format+System.currentTimeMillis();
//            houseResource.preInsert();
            houseResource.setId(uid);
            houseResource.setCreateBy(houseResource.getCurrentUser());
            houseResource.setUpdateBy(houseResource.getCurrentUser());
            houseResource.setCreateDate(new Date());
            houseResource.setUpdateDate(new Date());
            //新增的房源将其状态设置为0表示审核中
            houseResource.setState(0);
            //将删除标志设置为0
            houseResource.setDelFlag(0);
            //先将房源图片全部插入
            List<Pic> picList = houseResource.getPicList();
            for (Pic pic : picList) {
                pic.setCurrentUser(houseResource.getCurrentUser());
                pic.preInsert();
                //设置房源信息
                pic.setHouseId(houseResource.getId());
                pic.setState(1);
                pic.setType(PicType.HouseResource);
                picService.insert(pic);
            }
            //将标签信息全部插入
            List<TagDto> tagList = houseResource.getTagList();
            for (TagDto tagDto : tagList) {
                tagDto.setCurrentUser(houseResource.getCurrentUser());
                tagDto.preInsert();
                //设置标签信息
                tagDto.setHouseId(houseResource.getId());
                tagService.addHouseTags(tagDto);
            }
            //保存房源信息
            this.dao.insert(houseResource);
        }else{
            //如果房源id不为空
            houseResource.preUpdate();
            //如果更改了图片将原本的房源图片查询出来，删除后在添加新的图片
            if(houseResource.getPicList()!=null&&houseResource.getPicList().size()>0){
                Pic picFind = new Pic();
                picFind.setHouseId(houseResource.getId());
                List<Pic> oldList = picService.findList(picFind);
                //将原本的图片列表删除
                for (Pic pic : oldList) {
                    picService.realDelete(pic.getId());
                }
                //再将房源图片全部插入
                List<Pic> picList = houseResource.getPicList();
                for (Pic pic : picList) {
                    pic.setCurrentUser(houseResource.getCurrentUser());
                    pic.preInsert();
                    //设置房源信息
                    pic.setHouseId(houseResource.getId());
                    pic.setState(1);
                    pic.setType(PicType.HouseResource);
                    picService.insert(pic);
                }
            }
            //如果更改了标签，将原来的所有标签全部删除，再添加新的标签
            if(houseResource.getTagList()!=null&&houseResource.getTagList().size()>0){
                tagService.deleteByHouseId(houseResource.getId());
                List<TagDto> tagList = houseResource.getTagList();
                for (TagDto tagDto : tagList) {
                    tagDto.setCurrentUser(houseResource.getCurrentUser());
                    tagDto.preInsert();
                    //设置标签信息
                    tagDto.setHouseId(houseResource.getId());
                    tagService.addHouseTags(tagDto);
                }
            }
            //保存房源信息
            this.dao.update(houseResource);
        }
    }

    //根据角色查询房源信息
    public Page<HouseResource> findByRole(Page<HouseResource> page, HouseResource houseResource) {
        if(houseResource.getPage()!=null && houseResource.getPage().getPageSize()>0){
            page.setPageSize(houseResource.getPage().getPageSize());
        }
        houseResource.setPage(page);
        List<HouseResource> list = this.dao.findByRolePage(houseResource);
        for (HouseResource resource : list) {

            //找出房源的所有图片
            Pic pic = new Pic();
            pic.setHouseId(resource.getId());
            List<Pic> picList = picService.findList(pic);
            resource.setPicList(picList);
            //查找所有的标签

            List<TagDto> tagList = tagService.findByHouseId(resource.getId());
            resource.setTagList(tagList);

        }
        page.setList(list);
        return page;
    }

    //查询某个用户的房源数量
    public Integer getCountByUserId(String userId) {
        return this.dao.getCountByUserId(userId);
    }

    //查询我看过的房源
    public Page<HouseResource> findViewedHousePage(Page<HouseResource> page, HouseResource houseResource) {
        if(houseResource.getPage()!=null && houseResource.getPage().getPageSize()>0){
            page.setPageSize(houseResource.getPage().getPageSize());
        }
        houseResource.setPage(page);
        List<HouseResource> list = this.dao.findViewedHousePage(houseResource);
        for (HouseResource resource : list) {

            //找出房源的所有图片
            Pic pic = new Pic();
            pic.setHouseId(resource.getId());
            List<Pic> picList = picService.findList(pic);
            resource.setPicList(picList);
            //查找所有的标签

            List<TagDto> tagList = tagService.findByHouseId(resource.getId());
            resource.setTagList(tagList);

        }
        page.setList(list);
        return page;
    }

    //查询我收藏的房源
    public Page<HouseResource> findCollectionHousePage(Page<HouseResource> page, HouseResource houseResource) {
        if(houseResource.getPage()!=null && houseResource.getPage().getPageSize()>0){
            page.setPageSize(houseResource.getPage().getPageSize());
        }
        houseResource.setPage(page);
        List<HouseResource> list = this.dao.findCollectionHousePage(houseResource);
        for (HouseResource resource : list) {

            //找出房源的所有图片
            Pic pic = new Pic();
            pic.setHouseId(resource.getId());
            List<Pic> picList = picService.findList(pic);
            resource.setPicList(picList);
            //查找所有的标签

            List<TagDto> tagList = tagService.findByHouseId(resource.getId());
            resource.setTagList(tagList);

        }
        page.setList(list);
        return page;
    }

    //查询我正在租的房源
    public Page<HouseResource> findRentingHousePage(Page<HouseResource> page, HouseResource houseResource) {
        if(houseResource.getPage()!=null && houseResource.getPage().getPageSize()>0){
            page.setPageSize(houseResource.getPage().getPageSize());
        }
        houseResource.setPage(page);
        List<HouseResource> list = this.dao.findRentingHousePage(houseResource);
        for (HouseResource resource : list) {

            //找出房源的所有图片
            Pic pic = new Pic();
            pic.setHouseId(resource.getId());
            List<Pic> picList = picService.findList(pic);
            resource.setPicList(picList);
            //查找所有的标签

            List<TagDto> tagList = tagService.findByHouseId(resource.getId());
            resource.setTagList(tagList);

        }
        page.setList(list);
        return page;
    }

    public Integer findScaleCount(HouseResource houseResource){
        return this.dao.findScaleCount(houseResource);
    }
}
