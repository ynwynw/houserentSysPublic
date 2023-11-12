package com.wt.hrentSystem.hrent.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.hrent.entity.Tag;
import com.wt.hrentSystem.hrent.service.TagService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("tag")
public class TagController{
    @Resource
    private TagService tagService;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<Tag> get(String id){
        Tag tag = tagService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,tag,"成功");
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(Tag tag, HttpServletRequest request, HttpServletResponse response){
        Page<Tag> page = new Page<>(request, response);
        Page<Tag> page1 = tagService.findPage(page, tag);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<Tag>> findAll(Tag tag){
        List<Tag> list = tagService.findList(tag);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(Tag tag){
        tagService.save(tag);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(Tag tag){
        tagService.delete(tag);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }
}
