package com.wt.hrentSystem.hrent.service;

import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.service.BaseService;
import com.wt.hrentSystem.hrent.dao.RentDao;
import com.wt.hrentSystem.hrent.entity.Pic;
import com.wt.hrentSystem.hrent.entity.Rent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RentService")
public class RentService extends BaseService<RentDao, Rent> {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Rent get(String id) {
        return super.get(id);
    }

    /**
     * 查询多条数据
     * @param rent
     * @return
     */
    public List<Rent> findList(Rent rent){
        return super.findList(rent);
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param rent
     * @return 消息集合
     */
    public Page<Rent> findPage(Page page, Rent rent) {
        return super.findPage(page,rent);
    }


    /**
     * 删除数据
     * @param rent
     */

    public void delete(Rent rent) {
        super.delete(rent);
    }

    /**
     * 更新或插入数据
     */
    public void save(Rent rent){
        super.save(rent);
    }
}
