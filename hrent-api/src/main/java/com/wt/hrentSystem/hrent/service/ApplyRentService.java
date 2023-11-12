package com.wt.hrentSystem.hrent.service;

import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.service.BaseService;
import com.wt.hrentSystem.hrent.dao.ApplyRentDao;
import com.wt.hrentSystem.hrent.entity.ApplyRent;
import com.wt.hrentSystem.hrent.entity.ApplyView;
import com.wt.hrentSystem.hrent.entity.HouseRenting;
import com.wt.hrentSystem.hrent.entity.HouseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("ApplyRentService")
public class ApplyRentService extends BaseService<ApplyRentDao, ApplyRent> {

    @Autowired
    private HouseResourceService houseResourceService;

    @Autowired
    private HouseRentingService houseRentingService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ApplyRent get(String id) {
        ApplyRent applyRent = super.get(id);
        applyRent.setHouseResource(houseResourceService.get(applyRent.getHouseId()));
        return applyRent;
    }



    /**
     * 查询多条数据
     * @param applyRent
     * @return
     */
    public List<ApplyRent> findList(ApplyRent applyRent){
        List<ApplyRent> list = super.findList(applyRent);
        for (ApplyRent rent : list) {
            rent.setHouseResource(houseResourceService.get(rent.getHouseId()));
        }
        return list;
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param applyRent
     * @return 消息集合
     */
    public Page<ApplyRent> findPage(Page page, ApplyRent applyRent) {
        Page page1 = super.findPage(page, applyRent);
        List<ApplyRent> list = page1.getList();
        for (ApplyRent rent : list) {
            rent.setHouseResource(houseResourceService.get(rent.getHouseId()));
        }
        page1.setList(list);
        return page1;
    }


    /**
     * 删除数据
     * @param applyRent
     */

    public void delete(ApplyRent applyRent) {
        super.delete(applyRent);
    }

    /**
     * 更新或插入数据
     */
    public void save(ApplyRent applyRent){
        super.save(applyRent);
    }

    //查询个人的租房申请信息
    public Page<ApplyRent> findPersonalViewApply(Page<ApplyRent> page, ApplyRent applyRent) {
        if(applyRent.getPage()!=null && applyRent.getPage().getPageSize()>0){
            page.setPageSize(applyRent.getPage().getPageSize());
        }
        applyRent.setPage(page);
        List<ApplyRent> rentList = this.dao.findPersonalViewApplyPage(applyRent);
        for (ApplyRent view : rentList) {
            view.setHouseResource(houseResourceService.get(view.getHouseId()));
        }
        page.setList(rentList);
        return page;
    }

    @Transactional
    public void paySuccess(ApplyRent applyRent) {
        //修改租房申请的订单信息
        applyRent.setState(3);
        applyRent.setStartTime(new Date());
        super.save(applyRent);
        //新增正在列表的数据
        ApplyRent applyRent1 = this.dao.get(applyRent.getId());
        HouseResource houseResource = houseResourceService.get(applyRent1.getHouseId());
        HouseRenting houseRenting = new HouseRenting();
        houseRenting.setHouseId(houseResource.getId());
        //计算租金
        houseRenting.setMoney(houseResource.getMoney()/houseResource.getPeopleNum());
        houseRenting.setStartTime(new Date());
        houseRenting.setTenantId(applyRent.getCurrentUser().getId());
        houseRenting.setCurrentUser(applyRent.getCurrentUser());
        houseRenting.setEndTime(applyRent1.getEndTime());
        houseRentingService.save(houseRenting);
        //如果有足够数量的人已经租了这个房源，那么这个房源的状态就变为已租
        int num = houseRentingService.getNumByHouseId(houseResource.getId());
        if(num==houseResource.getPeopleNum()){
            HouseResource houseResource1 = new HouseResource();
            houseResource1.setId(houseResource.getId());
            houseResource1.setState(2);
            houseResourceService.update(houseResource1);
        }
    }
}
