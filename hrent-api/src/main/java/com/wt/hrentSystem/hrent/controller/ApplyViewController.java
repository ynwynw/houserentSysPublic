package com.wt.hrentSystem.hrent.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.hrent.entity.ApplyView;
import com.wt.hrentSystem.hrent.service.ApplyViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("applyView")
public class ApplyViewController {
    @Resource
    private ApplyViewService applyViewService;

    @Autowired
    private UserUtils userUtils;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<ApplyView> get(String id){
        ApplyView apply = applyViewService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,apply,"成功");
    }

    /**
     * 根据id查询单条数据（json参数）
     */
    @RequestMapping("wechatGet")
    public ResponseResult<ApplyView> wechatGet(@RequestBody ApplyView applyView){
        ApplyView applyView1 = applyViewService.get(applyView.getId());
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,applyView1,"查询成功");
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(ApplyView applyView, HttpServletRequest request, HttpServletResponse response){
        applyView.setCurrentUser(userUtils.getUser(request));
        Page<ApplyView> page = new Page<>(request, response);
        Page<ApplyView> page1 = applyViewService.findPage(page, applyView);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<ApplyView>> findAll(ApplyView applyView){
        List<ApplyView> list = applyViewService.findList(applyView);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(@RequestBody ApplyView applyView, HttpServletRequest request){
        applyView.setCurrentUser(userUtils.getUser(request));
        applyViewService.save(applyView);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(ApplyView applyView,HttpServletRequest request){
        applyView.setCurrentUser(userUtils.getUser(request));
        applyViewService.delete(applyView);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 查询个人的申请信息
     */
    @RequestMapping("findPersonalViewApply")
    public ResponseResult findPersonalViewApply(@RequestBody ApplyView applyView,HttpServletRequest request,HttpServletResponse response){
        applyView.setCurrentUser(userUtils.getUser(request));
        Page<ApplyView> page = new Page<>(applyView.getPageNo(), applyView.getPageSize());
        Page<ApplyView> page1 = applyViewService.findPersonalViewApply(page, applyView);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"查询成功");
    }

    /**
     * 根据实体类查询申请信息
     */
    @RequestMapping("getByEntity")
    public ResponseResult getByEntity(@RequestBody ApplyView applyView){
        try {
            return new ResponseResult(ResponseCodeCanstants.SUCCESS,applyViewService.getByEntity(applyView),"查询成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseResult(ResponseCodeCanstants.FAILED,"查询失败");
    }
}
