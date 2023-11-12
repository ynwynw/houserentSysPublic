package com.wt.hrentSystem.common.service;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.dao.UserDao;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.entity.User;
import com.wt.hrentSystem.common.util.MD5Utils;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.hrent.config.PicType;
import com.wt.hrentSystem.hrent.dao.ApplyRentDao;
import com.wt.hrentSystem.hrent.dao.HouseRentingDao;
import com.wt.hrentSystem.hrent.entity.HouseResource;
import com.wt.hrentSystem.hrent.entity.Pic;
import com.wt.hrentSystem.hrent.service.HouseResourceService;
import com.wt.hrentSystem.hrent.service.PicService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (SysUser)表服务实现类
 *
 * @author makejava
 * @since 2021-08-19 10:52:38
 */
@Service("UserService")
public class UserService extends BaseService<UserDao, User>{
    @Resource
    private UserDao userDao;

    @Resource
    private PicService picService;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private HouseResourceService houseResourceService;

    @Autowired
    private ApplyRentDao applyRentDao;


    /**
     * 获取登录用户的信息
     *
     * @param id 主键
     * @return 实例对象
     */

    public User get(String id) {
        return super.get(id);
//        //查询该用户的菜单
//        Menu menu = new Menu();
//        menu.setRoleId(user.getRoleId());
//        YmlUtil.loadYml("application.yml");
//        String sysId = String.valueOf(YmlUtil.getValue("sys.id"));
//        menu.setSysId(sysId);
//        List<Menu> list = menuDao.findList(menu);
//        List<Menu> treeData = TreeUtils.getTreeData(list);
//        return new LoginUserData(user,treeData);
    }

    /**
     * 获取登录用户信息
     */
//    public LoginUserData getLoginData(){
//        User user = UserUtils.getUser();
//        //查询该用户的菜单
//        Menu menu = new Menu();
//        menu.setCurrentUser(user);
//        YmlUtil.loadYml("application.yml");
//        String sysId = String.valueOf(YmlUtil.getValue("sys.id"));
//        menu.setSysId(sysId);
//        List<Menu> list = menuDao.findList(menu);
//        List<Menu> treeData = TreeUtils.getTreeData(list);
//        return new LoginUserData(user,treeData);
//    }

    /**
     * 根据实体类查询单条数据
     * @param user
     * @return
     */
    public User getByEntity(User user){
        return super.getByEntity(user);
    }


    /**
     * 查询多条数据
     * @param user 用户实例对象
     * @return 实例对象的集合
     */

    public List<User> findList(User user) {
        return super.findList(user);
    }

    public Page<User> findPage(Page page, User user){
        return super.findPage(page, user);
    }

    /**
     * 删除数据
     * @param user
     */

    public void delete(User user) {
        super.delete(user);
    }

    /**
     * 更新或者增加用户
     * @param user
     */

    @Transactional
    public void save(User user){
        String password = user.getPassword();
        //对用户密码进行加密处理
        String s = null;
        try {
            if(StringUtils.isNotBlank(password)){
                s = MD5Utils.EncoderByMd5(password);
            }
            //判断是否有头像,如果有则新增一条头像数据
            if(StringUtils.isNotBlank(user.getHeadUrl())){
                Pic pic = new Pic();
                pic.setCurrentUser(user.getCurrentUser());
                pic.setType(PicType.User);
                pic.setUrl(user.getHeadUrl());
                pic.preInsert();
                picService.insert(pic);
                user.setPId(pic.getId());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        user.setPassword(s);
        super.save(user);
    }


    /**
     * 通过openid查询用户
     */
    public User findUserByOpenid(String openId){
        return this.dao.findUserByOpenid(openId);
    }

    //注册用户
    @Transactional
    public void register(User user) {
        String password = user.getPassword();
        //对用户密码进行加密处理
        String s = null;
        try {
            if(StringUtils.isNotBlank(password)){
                s = MD5Utils.EncoderByMd5(password);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        user.setPassword(s);
        user.setRole(1);
        user.setRoleStr("房东");
        user.setCurrentUser(this.dao.get("1"));
        super.save(user);
    }

    public ResponseResult getDataCount(User user) {
        //地址数据
        String[] addList = new String[]{"武侯区","高新区","锦江区","金牛区","成华区","龙泉驿区","新都区","郫都区","温江区","双流区","青白江区","天府新区"};
        List addResultList = new ArrayList();
        HouseResource houseResource = new HouseResource();
        houseResource.setCurrentUser(user);
        for (String s : addList) {
            houseResource.setCounty(s);
            Map tempMap = new HashMap();
            Integer scaleCount = houseResourceService.findScaleCount(houseResource);
            if(scaleCount !=0){
                tempMap.put("value",scaleCount);
                tempMap.put("name",s);
                addResultList.add(tempMap);
            }
        }
        //价格占比数据
        houseResource.setCounty(null);
        Integer[] minPrice = new Integer[]{0,1001,2001,3001,4001,5001};
        Integer[] maxPrice = new Integer[]{1000,2000,3000,4000,5000,10000};
        List priceResultList = new ArrayList();

        for (int i = 0;i<minPrice.length;i++){
            houseResource.setMinMoney(minPrice[i]);
            houseResource.setMaxMoney(maxPrice[i]);
            Map priceMap = new HashMap();
            Integer scaleCount = houseResourceService.findScaleCount(houseResource);
            if(scaleCount!=0){
                priceMap.put("value",scaleCount);
                priceMap.put("name",minPrice[i]+"-"+maxPrice[i]);
                priceResultList.add(priceMap);
            }
        }
        //面积占比数据
        houseResource.setMinMoney(null);
        houseResource.setMaxMoney(null);
        Integer[] minArea = new Integer[]{0,31,51,71,91,101};
        Integer[] maxArea = new Integer[]{30,50,70,90,100,150};
        List areaResultList = new ArrayList();
        for (int i = 0;i<minArea.length;i++){
            houseResource.setMinArea(minArea[i]);
            houseResource.setMaxArea(maxArea[i]);
            Map areaMap = new HashMap();

            Integer scaleCount = houseResourceService.findScaleCount(houseResource);
            if(scaleCount!=0){
                areaMap.put("value",scaleCount);
                areaMap.put("name",minArea[i]+"-"+maxArea[i]);
                areaResultList.add(areaMap);
            }
        }
        //过去一年交易量
        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置日期为1号
        // 获取当前年份和月份
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        List<String> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        for (int i = 12; i > 0; i--) {
            if (month < 1) {
                year -= 1;
                month = 12;
            }
            String m = "";
            if (month < 10) {
                m = "0" + month;
            } else {
                m = month + "";
            }
            list.add(year+"-"+m);
            month--;
        }
        Collections.reverse(list);
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        list.stream().forEach(month2 ->{
            DateTime startTime = DateUtil.parse(month2 + "-01" + " 00:00:00");
            DateTime dateTime = DateUtil.endOfMonth(startTime);
            String format = DateUtil.format(dateTime, "yyyy-MM-dd");
            DateTime endTime = DateUtil.parse(format + " 23:59:59");
            int i = applyRentDao.countApply(startTime, endTime);
            list1.add(month2);
            list2.add(i);
        });

        Map resultMap = new HashMap();
        resultMap.put("addResultList",addResultList);
        resultMap.put("priceResultList",priceResultList);
        resultMap.put("areaResultList",areaResultList);
        resultMap.put("houseRentMonthList",list1);
        resultMap.put("houseRentMonthCountList",list2);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,resultMap,"成功");
    }
}
