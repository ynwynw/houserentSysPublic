package com.wt.hrentSystem.common.service;

import com.wt.hrentSystem.common.dao.DictDao;
import com.wt.hrentSystem.common.entity.Dict;
import com.wt.hrentSystem.common.entity.Message;
import com.wt.hrentSystem.common.entity.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DictService")
public class DictService extends BaseService<DictDao, Dict>{


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Dict get(String id) {
        return super.get(id);
    }

    /**
     * 查询多条数据
     * @param dict
     * @return
     */
    public List<Dict> findList(Dict dict){
        return super.findList(dict);
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param dict
     * @return 消息集合
     */
    public Page<Dict> findPage(Page page, Dict dict) {
        return super.findPage(page,dict);
    }


    /**
     * 删除数据
     * @param dict
     */

    public void delete(Dict dict) {
        super.delete(dict);
    }

    /**
     * 更新或插入数据
     */
    public void save(Dict dict){
        super.save(dict);
    }

    /**
     * 查询字典所有类型
     * @return
     */
    public List<Dict> findAllType() {
        return this.dao.findAllType();
    }
}
