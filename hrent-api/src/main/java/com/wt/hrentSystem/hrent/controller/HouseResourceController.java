package com.wt.hrentSystem.hrent.controller;

import com.wt.hrentSystem.common.config.ResponseCodeCanstants;
import com.wt.hrentSystem.common.entity.Page;
import com.wt.hrentSystem.common.entity.ResponseResult;
import com.wt.hrentSystem.common.util.FileUpLoadUtils;
import com.wt.hrentSystem.common.util.UserUtils;
import com.wt.hrentSystem.hrent.entity.HouseRenting;
import com.wt.hrentSystem.hrent.entity.HouseResource;
import com.wt.hrentSystem.hrent.service.HouseRentingService;
import com.wt.hrentSystem.hrent.service.HouseResourceService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("houseResource")
public class HouseResourceController {
    @Resource
    private HouseResourceService houseResourceService;

    @Resource
    private UserUtils userUtils;

    /**
     * 根据id查询单条数据
     */
    @RequestMapping("get")
    public ResponseResult<HouseResource> get(String id){
        HouseResource houseResource = houseResourceService.get(id);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,houseResource,"成功");
    }

    /**
     *分页查询
     */
    @RequestMapping("findPage")
    public ResponseResult<Page> findPage(@RequestBody HouseResource houseResource, HttpServletRequest request, HttpServletResponse response){
        houseResource.setCurrentUser(userUtils.getUser(request));
        Page<HouseResource> page = new Page<>(houseResource.getPageNo(), houseResource.getPageSize());
        if(houseResource.getTagList()!=null && houseResource.getTagList().size()>0){
            houseResource.setTagNum(houseResource.getTagList().size());
        }
        Page<HouseResource> page1 = houseResourceService.findPage(page, houseResource);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询所有
     */
    @RequestMapping("findAll")
    public ResponseResult<List<HouseResource>> findAll(HouseResource houseResource){
        List<HouseResource> list = houseResourceService.findList(houseResource);
        return new ResponseResult<>(ResponseCodeCanstants.SUCCESS,list,"成功");
    }

    /**
     * 增加或修改
     */
    @RequestMapping("save")
    public ResponseResult save(HttpServletRequest request,@RequestBody HouseResource houseResource){
        houseResource.setCurrentUser(userUtils.getUser(request));
        houseResourceService.save(houseResource);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 删除
     */
    @RequestMapping("delete")
    public ResponseResult delete(HouseResource houseResource,HttpServletRequest request){
        houseResource.setCurrentUser(userUtils.getUser(request));
        houseResourceService.delete(houseResource);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,"成功");
    }

    /**
     * 上传图片
     */
    @RequestMapping("uploadPic")
    public ResponseResult uploadPic(HttpServletRequest request, MultipartFile file){
        String id = userUtils.getUser(request).getId();
        //图片地址
        //String headUrl = "/img/"+id+"/house/";
        String headUrl = "/";
        FileUpLoadUtils fileUpLoadUtils = new FileUpLoadUtils();
        String visitPath = fileUpLoadUtils.uploadFile(file, headUrl);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,visitPath,"成功");
    }

    /**
     * 根据角色查询房源信息
     */
    @RequestMapping("findByRole")
    public ResponseResult findByRole(HttpServletRequest request,@RequestBody HouseResource houseResource,HttpServletResponse response){
        houseResource.setCurrentUser(userUtils.getUser(request));
        Page<HouseResource> page = new Page<>(houseResource.getPageNo(), houseResource.getPageSize());
        if(houseResource.getTagList()!=null && houseResource.getTagList().size()>0){
            houseResource.setTagNum(houseResource.getTagList().size());
        }
        Page<HouseResource> page1 = houseResourceService.findByRole(page, houseResource);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询我看过的房源
     */
    @RequestMapping("findViewedHousePage")
    public ResponseResult findViewedHousePage(HttpServletRequest request,@RequestBody HouseResource houseResource,HttpServletResponse response){
        houseResource.setCurrentUser(userUtils.getUser(request));
        Page<HouseResource> page = new Page<>(houseResource.getPageNo(), houseResource.getPageSize());
        Page<HouseResource> page1 = houseResourceService.findViewedHousePage(page,houseResource);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询我收藏的房源
     */
    @RequestMapping("findCollectionHousePage")
    public ResponseResult findCollectionHousePage(HttpServletRequest request,@RequestBody HouseResource houseResource,HttpServletResponse response){
        houseResource.setCurrentUser(userUtils.getUser(request));
        Page<HouseResource> page = new Page<>(houseResource.getPageNo(), houseResource.getPageSize());
        Page<HouseResource> page1 = houseResourceService.findCollectionHousePage(page,houseResource);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }

    /**
     * 查询我正在租的房源
     */
    @RequestMapping("findRentingHousePage")
    public ResponseResult findRentingHousePage(HttpServletRequest request,@RequestBody HouseResource houseResource,HttpServletResponse response){
        houseResource.setCurrentUser(userUtils.getUser(request));
        Page<HouseResource> page = new Page<>(houseResource.getPageNo(), houseResource.getPageSize());
        Page<HouseResource> page1 = houseResourceService.findRentingHousePage(page,houseResource);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,page1,"成功");
    }
}
