package com.wt.hrentSystem.hrent.service;

import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.service.BaseService;
import com.wt.hrentSystem.hrent.dao.HouseRentingDao;
import com.wt.hrentSystem.hrent.entity.HouseRenting;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Service("HouseRentingService")
public class HouseRentingService extends BaseService<HouseRentingDao, HouseRenting> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public HouseRenting get(String id) {
        return super.get(id);
    }

    /**
     * 查询多条数据
     * @param houseRenting
     * @return
     */
    public List<HouseRenting> findList(HouseRenting houseRenting){
        return super.findList(houseRenting);
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param houseRenting
     * @return 消息集合
     */
    public Page<HouseRenting> findPage(Page page, HouseRenting houseRenting) {
        return super.findPage(page,houseRenting);
    }


    /**
     * 删除数据
     * @param houseRenting
     */

    public void delete(HouseRenting houseRenting) {
        super.delete(houseRenting);
    }

    /**
     * 更新或插入数据
     */
    public void save(HouseRenting houseRenting){
        super.save(houseRenting);
    }

    public int getNumByHouseId(String houseId) {
        return this.dao.getNumByHouseId(houseId);
    }

    //查询某个用户正在租的房源数量
    public Integer getCountByUserId(String userId) {
        Date date = new Date();
    return this.dao.getCountByUserId(userId,date);
    }

}
