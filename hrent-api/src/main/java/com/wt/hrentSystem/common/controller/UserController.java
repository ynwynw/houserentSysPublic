package com.wt.hrentSystem.common.controller;


import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.entity.User;
import com.wt.hrentSystem.common.service.MessageService;
import com.wt.hrentSystem.common.service.UserService;
import com.wt.hrentSystem.common.util.FileUpLoadUtils;
import com.wt.hrentSystem.common.util.MD5Utils;
import com.wt.hrentSystem.common.util.TokenUtils;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.hrent.entity.ApplyView;
import com.wt.hrentSystem.hrent.service.ApplyViewService;
import com.wt.hrentSystem.hrent.service.CollectionService;
import com.wt.hrentSystem.hrent.service.HouseRentingService;
import com.wt.hrentSystem.hrent.service.HouseResourceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2021-08-19 10:52:38
 */
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private HouseRentingService houseRentingService;

    @Autowired
    private HouseResourceService houseResourceService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ApplyViewService applyViewService;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("register")
    public ResponseResult Register(User user){
        try {
            userService.save(user);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult(ResponseCodeCanstants.FAILED,"失败");
        }
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 根据id查询某个用户
     */
    @RequestMapping("get")
    public ResponseResult<User> get(String id){
        User user = userService.get(id);
        if(user!=null){
            return new ResponseResult(ResponseCodeCanstants.SUCCESS,user,"成功");
        }else{
            return new ResponseResult(ResponseCodeCanstants.FAILED,"没有该用户");
        }
    }

    /**
     * 分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(User user, HttpServletRequest request, HttpServletResponse response){
        Page<User> page = new Page<>(request, response);
        Page<User> page1 = userService.findPage(page, user);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 获取登录用户信息
     */
    @RequestMapping("getLoginData")
    public ResponseResult<User> getLoginData(HttpServletRequest request){
        String token = request.getHeader("token");
        String userId = TokenUtils.getUserId(token);
        User user = new User();
        if(StringUtils.isNotBlank(userId)){
            user = userService.get(userId);
        }
        if (user!=null){
            return new ResponseResult(ResponseCodeCanstants.SUCCESS,user,"成功");
        }else{
            return new ResponseResult(ResponseCodeCanstants.FAILED,"失败");
        }
    }

    /**
     *  查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<User>> findAll(User user, HttpServletRequest request, HttpServletResponse response){
        List<User> list = userService.findList(user);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改用户
     */
    @RequestMapping("save")
    public ResponseResult save(HttpServletRequest request, User user){
        user.setCurrentUser(userUtils.getUser(request));
        userService.save(user);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 微信小程序修改用户信息(json)
     */
    @RequestMapping("wechatSave")
    public ResponseResult wechatSave(HttpServletRequest request, @RequestBody User user){
        user.setCurrentUser(userUtils.getUser(request));
        userService.save(user);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除用户
     */
    @RequestMapping("delete")
    public ResponseResult delete(User user, HttpServletRequest request){
        user.setCurrentUser(userUtils.getUser(request));
        userService.delete(user);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "uploadHead",method = RequestMethod.POST)
    public ResponseResult uploadHead(HttpServletRequest request,MultipartFile file){
        String id = userUtils.getUser(request).getId();
        String headUrl = "/upload/img/"+id+"/head/";
        FileUpLoadUtils fileUpLoadUtils = new FileUpLoadUtils();
        String visitPath = fileUpLoadUtils.uploadFile(file, headUrl);
//        System.out.println(visitPath);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,visitPath,"上传成功");
    }

    /**
     * 用戶登录
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ResponseResult login(User user,HttpServletRequest request){
        //根据前端输入的登录名和密码查询是否由该用户
//        String remoteAddr = request.getRemoteAddr();
        User queryUser = new User();
        queryUser.setLoginName(user.getLoginName());
        User queryUser1 = userService.getByEntity(queryUser);
        if(queryUser1==null){
            return new ResponseResult(ResponseCodeCanstants.FAILED,"没有该用户");
        }else{
            try {
                queryUser.setPassword(MD5Utils.EncoderByMd5(user.getPassword()));
                User queryUser2 = userService.getByEntity(queryUser);
                if(queryUser2==null){
                    return new ResponseResult(ResponseCodeCanstants.FAILED,"用户名或者密码失败");
                }
                String token = TokenUtils.sign(queryUser2);
                if(token!=null){
                    //登录成功修改用户信息，修改登录时间
                    User t1 = userService.get("1");
                    String id = queryUser2.getId();
                    User updateUser = new User();
                    updateUser.setId(id);
                    updateUser.setUpdateBy(t1);
                    userService.update(updateUser);
                    return new ResponseResult(ResponseCodeCanstants.SUCCESS,token,"登录成功");
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return new ResponseResult(ResponseCodeCanstants.FAILED,"登录出现错误");
        }
    }

    /**
     * 用户登录，参数为json
     *
     */
    @RequestMapping(value = "loginJson",method = RequestMethod.POST)
    public ResponseResult loginJson(@RequestBody User user,HttpServletRequest request){
        return login(user,request);
    }

    /**
     * 查询个人基本信息的数量
     */
    @RequestMapping("getBaseInfoCount")
    public ResponseResult getBaseInfoCount(HttpServletRequest request){
        User user = userUtils.getUser(request);
        //返回的结果集
        Map<String,Integer> resultMap = new HashMap<>();
        //我看过的数量
        Integer visited = applyViewService.findCountByUserId(user.getId());
        resultMap.put("visited",visited);
        //我收藏的数量
        Integer collection = collectionService.findCountByUserId(user.getId());
        resultMap.put("collection",collection);
        //我正在租的数量
        Integer myRent = houseRentingService.getCountByUserId(user.getId());
        resultMap.put("myRent",myRent);
        //我的房源数量
        Integer myHouse = houseResourceService.getCountByUserId(user.getId());
        resultMap.put("myHouse",myHouse);
        //我的消息数量
        Integer messageCount = messageService.getCountByUserId(user.getId());
        resultMap.put("messageCount",messageCount);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,resultMap,"查询成功");
    }

}
