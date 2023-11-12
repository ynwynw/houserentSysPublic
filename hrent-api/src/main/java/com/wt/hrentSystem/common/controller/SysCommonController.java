package com.wt.hrentSystem.common.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.config.ServerConfig;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.entity.User;
import com.wt.hrentSystem.common.service.UserService;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.common.util.YmlUtil;
import com.wt.hrentSystem.hrent.entity.HouseResource;
import com.wt.hrentSystem.hrent.entity.Pic;
import com.wt.hrentSystem.hrent.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//公共接口
@RestController
@RequestMapping("sysCommon")
public class SysCommonController {
    @Autowired
    PicService picService;
    @Autowired
    UserService userService;
    @Autowired
    UserUtils userUtils;

    //获取当前项目的后端基本地址
    @RequestMapping("getBaseUrl")
    public ResponseResult getBaseUrl(){
        ServerConfig serverConfig = new ServerConfig();
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,serverConfig.getBaseUrl(),"获取成功");
    }

    //获取项目的一些基本信息
    @RequestMapping("getBaseInfo")
    public ResponseResult getBaseInfo(){
        YmlUtil.loadYml("application.yml");
        String nginxVisitPath = YmlUtil.getValue("file.visitLocal").toString();
        Map<String,Object> baseInfo = new HashMap<>();
        baseInfo.put("nginxVisitPath",nginxVisitPath);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,baseInfo,"获取成功");
    }

    //获取项目的某个基本资源
    @RequestMapping("getSystemResource")
    public ResponseResult getSystemResource(){
        //获取系统图片
        Pic pic = picService.get("1");
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("logoPic",pic.getUrl());
        //获取系统名字
        YmlUtil.loadYml("application.yml");
        String name = YmlUtil.getValue("sys.name").toString();
        resultMap.put("systemName",name);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,resultMap,"成功");
    }

    //获取某个登录名是否已经存在
    @RequestMapping("findLoginName")
    public ResponseResult findLoginName(User user){
        List<User> list = userService.findList(user);
        if(list.size()==0){
            return new ResponseResult(ResponseCodeCanstants.SUCCESS,false,"登录名不存在");
        }
        return new ResponseResult(ResponseCodeCanstants.FAILED,true,"登录名已存在");
    }

    //注册用户
    @RequestMapping("register")
    public ResponseResult register(User user){
        userService.register(user);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"注册成功");
    }

    //获取echarts图标数据
    @RequestMapping("getDataCount")
    public ResponseResult getDataCount(HttpServletRequest request){
        User user = userUtils.getUser(request);
        return userService.getDataCount(user);
    }
}
