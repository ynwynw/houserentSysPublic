package com.wt.hrentSystem.common.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Message;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.service.MessageService;
import com.wt.hrentSystem.common.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    @Resource
    private MessageService messageService;

    @Autowired
    private UserUtils userUtils;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<Message> get(String id){
        Message message = messageService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,message,"成功");
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(Message message, HttpServletRequest request, HttpServletResponse response){
        Page<Message> page = new Page<>(request, response);
        Page<Message> page1 = messageService.findPage(page, message);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<Message>> findAll(Message message){
        List<Message> list = messageService.findList(message);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(@RequestBody Message message, HttpServletRequest request){
        message.setCurrentUser(userUtils.getUser(request));
        messageService.save(message);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(Message message){
        messageService.delete(message);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 获取未读消息（新消息的数量）
     */
    @RequestMapping("getNewCount")
    public ResponseResult getNewCount(HttpServletRequest request){
        //创建一个实体类
        Message message = new Message();
        message.setCurrentUser(userUtils.getUser(request));
        Integer newMessageCount = messageService.getNewCount(message);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,newMessageCount,"成功");
    }

    /**
     * 查询个人的信息
     */
    @RequestMapping("findPersonalMessage")
    public ResponseResult findPersonalMessage(HttpServletRequest request,@RequestBody Message message,HttpServletResponse response){
        message.setCurrentUser(userUtils.getUser(request));
        Page<Message> page = new Page<>(message.getPageNo(), message.getPageSize());
        Page<Message> page1 = messageService.findPersonalMessagePage(page, message);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }
}
