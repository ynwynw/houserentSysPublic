package com.wt.hrentSystem.hrent.service;


import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.service.BaseService;
import com.wt.hrentSystem.hrent.dao.ApplyViewDao;
import com.wt.hrentSystem.hrent.entity.ApplyView;
import com.wt.hrentSystem.hrent.entity.HouseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ApplyViewService")
public class ApplyViewService extends BaseService<ApplyViewDao, ApplyView> {

    @Autowired
    HouseResourceService houseResourceService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ApplyView get(String id) {
        ApplyView applyView = super.get(id);
        applyView.setHouseResource(houseResourceService.get(applyView.getHouseId()));
        return applyView;
    }

    /**
     * 查询多条数据
     * @param applyView
     * @return
     */
    public List<ApplyView> findList(ApplyView applyView){
        return super.findList(applyView);
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param applyView
     * @return 消息集合
     */
    public Page<ApplyView> findPage(Page page, ApplyView applyView) {
        Page page1 = super.findPage(page, applyView);
        List<ApplyView> list = page1.getList();
        for (ApplyView view : list) {
            view.setHouseResource(houseResourceService.get(view.getHouseId()));
        }
        page1.setList(list);
        return page1;
    }


    /**
     * 删除数据
     * @param applyView
     */

    public void delete(ApplyView applyView) {
        super.delete(applyView);
    }

    /**
     * 更新或插入数据
     */
    public void save(ApplyView applyView){
        super.save(applyView);
    }

    //查询个人的看房申请信息
    public Page<ApplyView> findPersonalViewApply(Page<ApplyView> page, ApplyView applyView) {
        if(applyView.getPage()!=null && applyView.getPage().getPageSize()>0){
            page.setPageSize(applyView.getPage().getPageSize());
        }
        applyView.setPage(page);
        List<ApplyView> viewList = this.dao.findPersonalViewApplyPage(applyView);
        for (ApplyView view : viewList) {
            view.setHouseResource(houseResourceService.get(view.getHouseId()));
        }
        page.setList(viewList);
        return page;
    }

    //查询个人的看房记录条数
    public Integer findCountByUserId(String userId) {
        return this.dao.findCountByUserId(userId);
    }
}
