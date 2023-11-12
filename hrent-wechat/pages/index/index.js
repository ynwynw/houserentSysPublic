// index.js
// 获取应用实例
const app = getApp()
import api from '../../config/api'
import path from '../../config/path'
const { $Toast } = require('../../dist/base/index');

Page({
  data: {
    current:'home',
    condition:'',
    picLocal:app.globalData.picLocal,
    picList:[],
    showRight:false,
    infoImage:{
      list:[
        
      ],
      indicatorDots:true,
      autoplay:true,
      interval:5000,
      duration:500
    },
    btnMenu:[
      {
        name:'活动',
        url:'activity',
        icon:'../../images/button/btn_activity.png',
      },
      {
        name:'附近',
        url:'near',
        icon:'../../images/button/btn_near.png',
      },
      {
        name:'宝典',
        url:'bible',
        icon:'../../images/button/btn_bible.png',
      },
      {
        name:'都市',
        url:'city',
        icon:'../../images/button/btn_city.png',
      },
      {
        name:'社区',
        url:'community',
        icon:'../../images/button/btn_community.png',
      },
      {
        name:'经济',
        url:'economic',
        icon:'../../images/button/btn_economic.png',
      },
      {
        name:'紧急出售',
        url:'sale',
        icon:'../../images/button/btn_sale.png',
      },
      {
        name:'优选',
        url:'choose',
        icon:'../../images/button/btn_choose.png',
      }
    ],
    houseList:[
      
    ],
    // motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    canIUseGetUserProfile: false,
    canIUseOpenData: wx.canIUse('open-data.type.userAvatarUrl') && wx.canIUse('open-data.type.userNickName'), // 如需尝试获取用户信息可改为false
    bottomLoading:false,
    pageData:{
      pageNo:1,
      pageSize:5,
      count:0,
    }
  },
  toggleRight(){
    this.setData({
      showRight: !this.data.showRight
  });
  },
  handleLogin(){
    this.getUserProfile()
  },
  getPhoneNumber(e){
    console.log(e.detail.code);
  },
  // 事件处理函数
  bindViewTap() {
    wx.navigateTo({
      url: '../logs/logs'
    })
    wx.request({
      url: app.globalData.baseUrl,
    })
  },
  //跳转到搜索页面
  toSearch(){
    console.log(this.data.condition)
    wx.navigateTo({
      url: 'search/search?condition='+this.data.condition,
    })
    
  },
  onLoad() {
    //查询精品推荐房源数据
    let params = {
      pageNo:this.data.pageData.pageNo,
      pageSize:this.data.pageData.pageSize
    }
    api.post(path.path.findHousePage,params).then(res => {
      this.data.pageData.count = res.data.count;
      this.setData({
        houseList:res.data.list,
        pageData:this.data.pageData
      })
    }).catch(err => {
      console.log(err)
    })
    //查询小程序轮播图
    params = {
      state:1,
      type:1,
    }
    api.post(path.path.findAllPic,params).then(res=>{
      this.data.infoImage.list = res.data;
      this.setData({
        infoImage:this.data.infoImage
      })
    }).catch(err=>{
      console.log(err)
    })
  },
  getUserProfile(e) {
    // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认，开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
    wx.getUserProfile({
      desc: '展示用户信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
      success: (res) => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
        wx.setStorageSync('userInfo', res.userInfo)
      },
      fail: (err) => {
        console.log(err,'错误')
      }
    })
  },
  conditionInput:function(e){
    this.setData({
      condition:e.detail.detail.value
    })
  },
  toHouseInfo(e){
    // console.log(e)
    wx.navigateTo({
      url: 'houseInfo/houseInfo?id='+e.currentTarget.dataset.id,
    })
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
    }
    api.post(path.path.findHousePage,params).then(res => {
      let getList = res.data.list;
      let oraginList = this.data.houseList;
      getList.forEach((item)=>{
        oraginList.push(item)
      })
      this.setData({
        houseList:oraginList
      })
      //将加载中去掉
      this.bottomEnd();
    }).catch(err => {
      console.log(err)
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
})
