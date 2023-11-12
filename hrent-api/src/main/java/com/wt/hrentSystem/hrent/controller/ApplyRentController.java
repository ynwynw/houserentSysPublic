package com.wt.hrentSystem.hrent.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.hrent.entity.ApplyRent;
import com.wt.hrentSystem.hrent.entity.ApplyView;
import com.wt.hrentSystem.hrent.entity.HouseRenting;
import com.wt.hrentSystem.hrent.service.ApplyRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("applyRent")
public class ApplyRentController{
    @Resource
    private ApplyRentService applyRentService;

    @Autowired
    private UserUtils userUtils;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<ApplyRent> get(String id){
        ApplyRent applyRent = applyRentService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,applyRent,"成功");
    }


    /**
     * 通过ID查询单条数据,JSON
     */
    @RequestMapping("wechatGet")
    public ResponseResult<ApplyRent> wechatGet(@RequestBody ApplyRent applyRent){
        return get(applyRent.getId());
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(ApplyRent applyRent, HttpServletRequest request, HttpServletResponse response){
        applyRent.setCurrentUser(userUtils.getUser(request));
        Page<ApplyRent> page = new Page<>(request, response);
        Page<ApplyRent> page1 = applyRentService.findPage(page, applyRent);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<ApplyRent>> findAll(ApplyRent applyRent){
        List<ApplyRent> list = applyRentService.findList(applyRent);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(@RequestBody ApplyRent applyRent, HttpServletRequest request){
        applyRent.setCurrentUser(userUtils.getUser(request));
        applyRentService.save(applyRent);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(ApplyRent applyRent,HttpServletRequest request){
        applyRent.setCurrentUser(userUtils.getUser(request));
        applyRentService.delete(applyRent);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 查询个人的申请租房信息
     */
    @RequestMapping("findPersonalRentApply")
    public ResponseResult findPersonalRentApply(@RequestBody ApplyRent applyRent,HttpServletRequest request,HttpServletResponse response){
        applyRent.setCurrentUser(userUtils.getUser(request));
        Page<ApplyRent> page = new Page<>(applyRent.getPageNo(), applyRent.getPageSize());
        Page<ApplyRent> page1 = applyRentService.findPersonalViewApply(page, applyRent);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"查询成功");
    }

    /**
     * 成功支付押金之后调用的接口
     */
    @RequestMapping("paySuccess")
    public ResponseResult  paySuccess(@RequestBody ApplyRent applyRent,HttpServletRequest request){
        applyRent.setCurrentUser(userUtils.getUser(request));
        try {
            applyRentService.paySuccess(applyRent);
            return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
        }catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseResult(ResponseCodeCanstants.FAILED,"成功");
    }

    /**
     * 根据房源Id和用户id查询是否有租房申请
     */
    @RequestMapping("getByEntity")
    public ResponseResult getByEntity(@RequestBody ApplyRent applyRent){
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,applyRentService.getByEntity(applyRent),"成功");
    }

}
