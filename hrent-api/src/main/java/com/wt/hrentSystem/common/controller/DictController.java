package com.wt.hrentSystem.common.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Dict;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.service.DictService;
import com.wt.hrentSystem.common.util.UserUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("dict")
public class DictController {
    @Resource
    private DictService dictService;

    @Resource
    private UserUtils userUtils;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<Dict> get(String id){
        Dict dict = dictService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,dict,"成功");
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(Dict dict, HttpServletRequest request, HttpServletResponse response){
        Page<Dict> page = new Page<>(request, response);
        Page<Dict> page1 = dictService.findPage(page, dict);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<Dict>> findAll(Dict dict){
        List<Dict> list = dictService.findList(dict);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 查询所有，参数为json格式
     */
    @RequestMapping("findAllJson")
    public ResponseResult<List<Dict>> findAllJson(@RequestBody Dict dict){
        List<Dict> list = dictService.findList(dict);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"查询成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(HttpServletRequest request, Dict dict){
        dict.setCurrentUser(userUtils.getUser(request));
        dictService.save(dict);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(HttpServletRequest request, Dict dict){
        dict.setCurrentUser(userUtils.getUser(request));
        dictService.delete(dict);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 查询字典所有类型
     */
    @RequestMapping("findAllType")
    public ResponseResult findAllType(){
        List<Dict> dictList = dictService.findAllType();
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,dictList,"成功");
    }
}
