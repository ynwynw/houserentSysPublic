package com.wt.hrentSystem.common.service;

import com.wt.hrentSystem.common.dao.MenuDao;
import com.wt.hrentSystem.common.entity.Menu;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.util.TreeUtils;
import com.wt.hrentSystem.common.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("MenuService")
public class MenuService extends BaseService<MenuDao, Menu>{

    @Autowired
    private UserUtils userUtils;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Menu get(String id) {
        return super.get(id);
    }

    /**
     * 查询多条数据
     * @param menu
     * @return
     */
    public List<Menu> findList(Menu menu){
        List<Menu> treeData = TreeUtils.getTreeData(super.findList(menu));
        return treeData;
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param menu
     * @return 消息集合
     */
    public Page<Menu> findPage(Page page, Menu menu) {
        return super.findPage(page,menu);
    }


    /**
     * 删除数据
     * @param menu
     */

    public void delete(Menu menu) {
        super.delete(menu);
    }

    /**
     * 更新或插入数据
     */
    public void save(Menu menu){
        super.save(menu);
    }

    //查询某个角色已经有的id
    public List<String> findRoleMenu(String roleId) {
        return this.dao.findRoleMenu(roleId);
    }

    //保存某个用户的菜单
    @Transactional
    public void keepRoleMenu(Menu menu, HttpServletRequest request) {
        //删除以前的菜单
        String roleId = menu.getMenuList().get(0).getRoleId();
        this.dao.realDelete(roleId);
        //批量插入现在的菜单
        this.dao.insertBatch(menu);
    }
}
