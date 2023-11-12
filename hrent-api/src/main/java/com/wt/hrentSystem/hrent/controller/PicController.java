package com.wt.hrentSystem.hrent.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.util.FileUpLoadUtils;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.hrent.entity.Pic;
import com.wt.hrentSystem.hrent.service.PicService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("pic")
public class PicController {
    @Resource
    private PicService picService;

    @Resource
    UserUtils userUtils;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<Pic> get(String id){
        Pic pic = picService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,pic,"成功");
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(Pic pic, HttpServletRequest request, HttpServletResponse response){
        Page<Pic> page = new Page<>(request, response);
        Page<Pic> page1 = picService.findPage(page, pic);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<Pic>> findAll(Pic pic){
        List<Pic> list = picService.findList(pic);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(HttpServletRequest request,@RequestBody Pic pic){
        pic.setCurrentUser(userUtils.getUser(request));
        picService.save(pic);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(HttpServletRequest request,Pic pic){
        pic.setCurrentUser(userUtils.getUser(request));
        picService.delete(pic);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 上传图片
     */
    @RequestMapping("uploadPic")
    public ResponseResult uploadPic(HttpServletRequest request, MultipartFile file){
        String picUrl = "/default/system/switch/";
        FileUpLoadUtils fileUpLoadUtils = new FileUpLoadUtils();
        String visitPath = fileUpLoadUtils.uploadFile(file, picUrl);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,visitPath,"上传成功");
    }

    /**
     * 查询所有，参数为json格式
     */
    @RequestMapping("findAllJson")
    public ResponseResult findAllJson(@RequestBody Pic pic){
        return findAll(pic);
    }
}
