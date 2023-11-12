package com.wt.hrentSystem.common.service;

import com.wt.hrentSystem.common.dao.MessageDao;
import com.wt.hrentSystem.common.entity.Message;
import com.wt.hrentSystem.common.entity.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("MessageService")
public class MessageService extends BaseService<MessageDao, Message>{

    @Resource
    private MessageDao messageDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Message get(String id) {
        return super.get(id);
    }

    /**
     * 查询多条数据
     * @param message
     * @return
     */
    public List<Message> findList(Message message){
        return super.findList(message);
    }

    /**
     * 分页查询多条数据
     * @param page
     * @param message
     * @return 消息集合
     */
    public Page<Message> findPage(Page page, Message message) {
        return super.findPage(page,message);
    }


    /**
     * 删除数据
     * @param message
     */

    public void delete(Message message) {
        super.delete(message);
    }

    /**
     * 更新或插入数据
     */
    public void save(Message message){
        super.save(message);
    }

    //查询当前登录人新消息的数量
    public Integer getNewCount(Message message) {
        return this.dao.getNewCount(message);
    }

    //查询某个人的消息数量（未读）
    public Integer getCountByUserId(String userId) {
        return this.dao.getCountByUserId(userId);
    }

    //查询某个人的消息列表
    public Page<Message> findPersonalMessagePage(Page<Message> page, Message message) {
        if(message.getPage()!=null && message.getPage().getPageSize()>0){
            page.setPageSize(message.getPage().getPageSize());
        }
        message.setPage(page);
        page.setList(this.dao.findPersonalMessagePage(message));
        return page;
    }
}
