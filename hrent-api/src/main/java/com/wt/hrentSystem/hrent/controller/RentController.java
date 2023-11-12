package com.wt.hrentSystem.hrent.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.hrent.entity.Rent;
import com.wt.hrentSystem.hrent.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("rent")
public class RentController {
    @Resource
    private RentService rentService;

    @Autowired
    private UserUtils userUtils;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<Rent> get(String id){
        Rent rent = rentService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,rent,"成功");
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(Rent rent, HttpServletRequest request, HttpServletResponse response){
        rent.setCurrentUser(userUtils.getUser(request));
        Page<Rent> page = new Page<>(request, response);
        Page<Rent> page1 = rentService.findPage(page, rent);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<Rent>> findAll(Rent rent){
        List<Rent> list = rentService.findList(rent);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(@RequestBody Rent rent,HttpServletRequest request){
        rent.setCurrentUser(userUtils.getUser(request));
        rentService.save(rent);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(Rent rent,HttpServletRequest request){
        rent.setCurrentUser(userUtils.getUser(request));
        rentService.delete(rent);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }
}
