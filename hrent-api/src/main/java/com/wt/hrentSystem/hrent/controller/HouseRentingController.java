package com.wt.hrentSystem.hrent.controller;
import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.hrent.entity.HouseRenting;
import com.wt.hrentSystem.hrent.service.HouseRentingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("houseRenting")
public class HouseRentingController {
    @Resource
    private HouseRentingService houseRentingService;

    @Autowired
    private UserUtils userUtils;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<HouseRenting> get(String id){
        HouseRenting houseRenting = houseRentingService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,houseRenting,"成功");
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(HouseRenting houseRenting, HttpServletRequest request, HttpServletResponse response){
        houseRenting.setCurrentUser(userUtils.getUser(request));
        Page<HouseRenting> page = new Page<>(request, response);
        Page<HouseRenting> page1 = houseRentingService.findPage(page, houseRenting);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<HouseRenting>> findAll(HouseRenting houseRenting){
        List<HouseRenting> list = houseRentingService.findList(houseRenting);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(@RequestBody HouseRenting houseRenting,HttpServletRequest request){
        try {
            houseRenting.setCurrentUser(userUtils.getUser(request));
            houseRentingService.save(houseRenting);
            return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult(ResponseCodeCanstants.FAILED,"失败");
        }
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(HouseRenting houseRenting,HttpServletRequest request){
        houseRenting.setCurrentUser(userUtils.getUser(request));
        houseRentingService.delete(houseRenting);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

}
