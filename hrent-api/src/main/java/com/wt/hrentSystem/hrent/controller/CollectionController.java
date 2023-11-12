package com.wt.hrentSystem.hrent.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.hrent.entity.Collection;
import com.wt.hrentSystem.hrent.entity.Tag;
import com.wt.hrentSystem.hrent.service.CollectionService;
import com.wt.hrentSystem.hrent.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("collection")
public class CollectionController {
    @Resource
    private CollectionService collectionService;

    @Autowired
    private UserUtils userUtils;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<Collection> get(String id){
        Collection collection = collectionService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,collection,"成功");
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(Collection collection, HttpServletRequest request, HttpServletResponse response){
        Page<Tag> page = new Page<>(request, response);
        Page<Tag> page1 = collectionService.findPage(page, collection);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<Collection>> findAll(@RequestBody Collection collection){
        List<Collection> list = collectionService.findList(collection);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(@RequestBody Collection collection, HttpServletRequest request){
        collection.setCurrentUser(userUtils.getUser(request));
        collectionService.save(collection);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(@RequestBody Collection collection,HttpServletRequest request){
        collection.setCurrentUser(userUtils.getUser(request));
        collectionService.delete(collection);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }
}
