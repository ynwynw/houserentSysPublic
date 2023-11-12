import api from '../../../config/api'
import path from '../../../config/path'
const { $Toast } = require('../../../dist/base/index');
const app = getApp()
// pages/index/search/search.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    picLocal:app.globalData.picLocal,
    showRight:false,
    condition:'',
    rentType:null,
    rentTypeStr:'请选择',
    bottomLoading:false,
    rentTypeVisible:false,
    allRentType:[
      {
        id:'0',
        name:'整租',
      },
      {
        id:'1',
        name:'合租',
      }
    ],
    minArea:null,
    maxArea:null,
    allTags:[
      {
        id:1,
        name:'近地铁',
      },
      {
        id:2,
        name:'朝南',
      },
      {
        id:3,
        name:'朝北',
      }
    ],
    minRentMoney:null,
    maxRentMoney:null,
    allTagsStr:['近地铁','朝南','朝北'],
    chooseTags:[],
    region: ['四川省', '成都市', '全部'],
    customItem: '全部',
    houseList:[],
    pageData:{
      pageNo:1,
      pageSize:5,
      count:0,
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      condition:options.condition
    })
    //通过condition查询相应的房源
    api.post(path.path.findHouseByCondition,{
      pageNo:this.data.pageData.pageNo,
      pageSize:this.data.pageData.pageSize,
      condition:this.data.condition,
    }).then(res => {
      this.data.pageData.count = res.data.count;
      this.setData({
        houseList:res.data.list,
        pageData:this.data.pageData,
      })
    }).catch(err => {
      console.log(err)
    });
    //向后端发送请求，查询所有的标签
    api.post(path.path.findAllTags,{

    }).then(res=>{
      this.setData({
        allTags:res.data
      });
      let arr = [];
      //将标签的名字赋值给allTagsStr
      this.data.allTags.forEach(e=>{
        arr.push(e.name)
      })
      this.setData({
        allTagsStr:arr
      })
    }).catch(err => {
      console.log(err)
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.bottomStart()
    //如果没有数据后
    if(this.data.pageData.count<(this.data.pageData.pageNo*this.data.pageData.pageSize)){
      $Toast({
        content:'没有更多数据了'
      })
      this.bottomEnd();
      return;
    }
    //页面触底之后加载第二页的数据加到第一页上去
    this.data.pageData.pageNo++;
    this.setData({
      pageData:this.data.pageData
    })
    let params = {
      pageNo:this.data.pageData.pageNo,
      pageSize:this.data.pageData.pageSize,
      condition:this.data.condition,
      rentType:this.data.rentType,
      minArea:this.data.minArea,
      maxArea:this.data.maxArea,
      minMoney:this.data.minRentMoney,
      maxMoney:this.data.maxRentMoney,
      tagList:this.data.chooseTags,
      province:this.data.region[0]=="全部"?'':this.data.region[0],
      city:this.data.region[1]=="全部"?'':this.data.region[1],
      county:this.data.region[2]=="全部"?'':this.data.region[2],
    }
    //向后端发送请求，获取数据
    api.post(path.path.findHouseByCondition,params).then(res=>{
      // console.log(res)
      this.data.pageData.count = res.data.count;
      let getList = res.data.list;
      let oraginList = this.data.houseList;
      getList.forEach((item)=>{
        oraginList.push(item)
      })
      this.setData({
        houseList:oraginList,
        pageData:this.data.pageData,
      })
      //将加载中去掉
      this.bottomEnd();
    }).catch(err => {
      console.log(err)
    });
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
   toggleRight(){
     this.setData({
       showRight:!this.data.showRight
     });
   },
   chooseRentType(){
     this.setData({
       rentTypeVisible:true
     });
   },
   rentTypeCancel(){
     this.setData({
       rentTypeVisible:false
     });
   },
   rentTypeClick({detail}){
     this.setData({
      rentTypeStr:this.data.allRentType[detail.index].name,
      rentType:this.data.allRentType[detail.index].id,
      rentTypeVisible:false,
     })
   },
   tagChoose(e){
     //判断是否选择重复
     if(this.data.chooseTags.indexOf(this.data.allTags[e.detail.value])==-1){
      this.data.chooseTags.push(this.data.allTags[e.detail.value])
      this.setData({
        chooseTags:this.data.chooseTags
      })
     }
     
   },
   remove:function(e){
     //获取点击标签的id
     //从已选择的标签中找出该标签，去除改标签
     for(var i=0;i<this.data.chooseTags.length;i++){
       if(e.currentTarget.dataset.id==this.data.chooseTags[i].id){
          this.data.chooseTags.splice(i,1);
          this.setData({
          chooseTags:this.data.chooseTags
        })
       }
     }
   },
   bindRegionChange: function (e) {
    // console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      region: e.detail.value
    })
  },
  //清空选择的信息
  clearInfo(){
    this.setData({
      rentTypeStr:'请选择',
      minArea:null,
      maxArea:null,
      minRentMoney:null,
      maxRentMoney:null,
      region: ['四川省', '成都市', '全部'],
      chooseTags:[],
    })
  },
  //根据条件筛选符合条件的房源
  searchInfo(){
    //向后端发送请求，查询符合条件的房源
  },
  //查询以前的搜索历史
  history(){
    
  },
  searchHouse(){
    //处理参数
    this.data.pageData.pageNo = 1;
    this.data.pageData.pageSize = 5;
    this.setData({
      pageData:this.data.pageData
    })
    let params = {
      pageNo:this.data.pageData.pageNo,
      pageSize:this.data.pageData.pageSize,
      condition:this.data.condition,
      rentType:this.data.rentType,
      minArea:this.data.minArea,
      maxArea:this.data.maxArea,
      minMoney:this.data.minRentMoney,
      maxMoney:this.data.maxRentMoney,
      tagList:this.data.chooseTags,
      province:this.data.region[0]=="全部"?'':this.data.region[0],
      city:this.data.region[1]=="全部"?'':this.data.region[1],
      county:this.data.region[2]=="全部"?'':this.data.region[2],
    }
    //向后端发送请求，获取数据
    api.post(path.path.findHouseByCondition,params).then(res=>{
      // console.log(res)
      this.data.pageData.count = res.data.count;
      this.setData({
        houseList:res.data.list,
        pageData:this.data.pageData,
        showRight:false,
      })
      //关闭筛选条件框

    }).catch(err => {
      console.log(err)
    });
    // console.log(params)
  },
  minAreaChange(e){
    this.setData({
      minArea:e.detail.detail.value
    })
  },
  maxAreaChange(e){
    this.setData({
      maxArea:e.detail.detail.value
    })
  },
  minRentMoneyChange(e){
    this.setData({
      minRentMoney:e.detail.detail.value
    })
  },
  maxRentMoneyChange(e){
    this.setData({
      maxRentMoney:e.detail.detail.value
    })
  },
  conditionChange(e){
    this.setData({
      condition:e.detail.detail.value
    })
  },
  //底部加载
  bottomStart(){
    this.setData({
      bottomLoading:true
    })
  },
  //底部不加载
  bottomEnd(){
    this.setData({
      bottomLoading:false
    })
  },
  toHouseInfo(e){
    // console.log(e)
    wx.navigateTo({
      url: '../houseInfo/houseInfo?id='+e.currentTarget.dataset.id,
    })
  }
})