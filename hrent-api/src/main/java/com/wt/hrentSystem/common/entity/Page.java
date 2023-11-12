package com.wt.hrentSystem.common.entity;

import com.wt.hrentSystem.common.util.CookieUtils;
import com.wt.hrentSystem.common.util.YmlUtil;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class Page<T> {
    //当前页
    private int pageNo;
    //页面大小
    private int pageSize;
    //数据量
    private int count;
    //数据
    private List<T> list;
    //排序
    private String orderBy;

    public Page(){
        this.pageNo = 1;
        YmlUtil.loadYml("application.yml");
        this.pageSize = Integer.valueOf(String.valueOf(YmlUtil.getValue("pageSize")));
        this.list  = new ArrayList<>();
        this.orderBy = "";
    }

    public Page(Integer pageNo,Integer pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.list  = new ArrayList<>();
        this.orderBy = "";
    }

    public Page(HttpServletRequest request, HttpServletResponse response){
        this(request,response,-2);
    }

    public Page(HttpServletRequest request, HttpServletResponse response, int defaultPageSize){
        this.pageNo = 1;
        YmlUtil.loadYml("application.yml");
        this.pageSize = Integer.valueOf(String.valueOf(YmlUtil.getValue("pageSize")));
        this.list = new ArrayList<>();
        this.orderBy = "";
        String no = request.getParameter("pageNo");
        if(StringUtils.isNumeric(no)){
            CookieUtils.setCookie(response,"pageNo",no);
            this.setPageNo(Integer.parseInt(no));
        }else if(request.getParameter("repage")!=null){
            no =CookieUtils.getCookie(request,"pageNo");
            if(StringUtils.isNumeric(no)){
                this.setPageNo(Integer.valueOf(no));
            }
        }

        String size = request.getParameter("pageSize");
        if(StringUtils.isNumeric(size)){
            CookieUtils.setCookie(response,"pageSize",size);
            this.setPageSize(Integer.parseInt(size));
        }else if(request.getParameter("repage")!=null){
            size =CookieUtils.getCookie(request,"pageSize");
            if(StringUtils.isNumeric(size)){
                this.setPageSize(Integer.parseInt(size));
            }
        }else if(defaultPageSize!=-2){
            this.pageSize=defaultPageSize;
        }
        String orderBy = request.getParameter("orderBy");
        if(StringUtils.isNotBlank(orderBy)){
            this.setOrderBy(orderBy);
        }
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
