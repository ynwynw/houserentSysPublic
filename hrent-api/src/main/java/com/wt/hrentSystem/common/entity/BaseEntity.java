package com.wt.hrentSystem.common.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
public abstract class BaseEntity<T> implements Serializable {
    protected String id;
    //创建人
    protected User createBy;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createDate;
    //修改人
    protected User updateBy;
    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date updateDate;
    //提醒
    protected String remake;
    //删除标志
    protected Integer delFlag;
    //当前用户
    protected User currentUser;
    //分页类
    protected Page<T> page;
    //sqlMap，用于过滤sql
    protected Map<String,String> sqlMap;
    //pageNo,pageSize，用于json格式参数时的分页
    protected Integer pageNo;
    protected Integer pageSize;

    //新增数据
    public void preInsert(){
        if(this.getId() == null || "".equals(this.getId())){
            this.setId(UUID.randomUUID().toString().replaceAll("-",""));
        }
//        UserUtils userUtils = new UserUtils();
//        User user = userUtils.getUser();
        //暂时处理没有登录人的情况
        User user = this.getCurrentUser();
        if(StringUtils.isNotBlank(user.getId())){
            this.createBy = user;
            this.updateBy = user;
        }
        this.createDate = new Date();
        this.updateDate = createDate;
        this.delFlag = 0;
    }
    //更新数据
    public void preUpdate(){
//        UserUtils userUtils = new UserUtils();
//        User user = userUtils.getUser();
        User user = this.getCurrentUser();
        if(StringUtils.isNotBlank(user.getId())){
            this.updateBy = user;
        }
        this.updateDate = new Date();
    }

}
