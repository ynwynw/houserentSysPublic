package com.wt.hrentSystem.common.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Menu;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.entity.User;
import com.wt.hrentSystem.common.service.MenuService;
import com.wt.hrentSystem.common.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @Autowired
    private UserUtils userUtils;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<Menu> get(String id){
        Menu menu = menuService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,menu,"成功");
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(Menu menu, HttpServletRequest request, HttpServletResponse response){
        Page<Menu> page = new Page<>(request, response);
        Page<Menu> page1 = menuService.findPage(page, menu);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<Menu>> findAll(Menu menu){
        List<Menu> list = menuService.findList(menu);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(Menu menu){
        menuService.save(menu);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(Menu menu){
        menuService.delete(menu);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 通过角色查询已有的菜单，返回格式为基本菜单的id数组
     */
    @RequestMapping("findRoleMenu")
    public ResponseResult findRoleMenu(Menu menu){
        List<String> haveMenu = menuService.findRoleMenu(menu.getRoleId());
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,haveMenu,"成功");
    }

    /**
     * 保存某个用户的菜单
     */
    @RequestMapping("keepRoleMenu")
    public ResponseResult keepRoleMenu(@RequestBody Menu menu, HttpServletRequest request){
        try {
            menuService.keepRoleMenu(menu,request);
            return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseResult(ResponseCodeCanstants.FAILED,"失败");
    }

    /**
     * 获取登录用户的菜单
     */
    @RequestMapping("getLoginMenu")
    public ResponseResult getLoginMenu(HttpServletRequest request){
        User user = userUtils.getUser(request);
        Menu menu = new Menu();
        menu.setRoleId(user.getRole().toString());
        menu.setSystem(1);
        List<Menu> list = menuService.findList(menu);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,list,"成功");
    }
}
