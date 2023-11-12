// pages/userInfo/myInfo.js
// 获取应用实例
const app = getApp()
import api from '../../config/api'
import path from '../../config/path'
const { $Message } = require('../../dist/base/index');
const { $Toast } = require('../../dist/base/index');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    picLocal:app.globalData.picLocal,
    baseHeadPic:app.globalData.picLocal+"/default/head/head.png",
    user:{
    },
    isLogin:'',
    count:{
      visited:0,
      collection:0,
      myHouse:0,
      myRent:1,
      messageCount:0,
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //判断有没有登录
    let isLogin = wx.getStorageSync('isLogin');
    //判断本地有没有用户信息
    if(wx.getStorageSync('loginData')!=null){
      this.setData({
        user:wx.getStorageSync('loginData')
      })
    }
    
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    let isLogin = wx.getStorageSync('isLogin')
    if(isLogin==false){
      $Message({
        content:'请先进行登录',
        type:'error' 
      });
    }
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    //每次进入这个页面，就刷新用户的数据
    //获取登录用户信息
    api.post(path.path.getLoginData,{

    }).then(res=>{
      wx.setStorageSync('loginData', res.data);
      this.setData({
        user:res.data
      })
      wx.setStorageSync("isLogin",true)
    }).catch(err=>{
      console.log(err)
    })

    //获取我看过的，我收藏的，我的房源的数量

    //获取我的基本信息的数量
    api.post(path.path.getBaseInfoCount).then(res=>{
      this.setData({
        count:res.data
      })
    })
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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  handleLogin:function(){
    this.getUserProfile();
  },

  getUserProfile(){
    wx.getUserProfile({
      desc: '用于完善用户资料',
      success: (res)=> {
        var userInfo = res.userInfo
        var nickName = userInfo.nickName
        var avatarUrl = userInfo.avatarUrl
        var gender = userInfo.gender //性别 0：未知、1：男、2：女
        var province = userInfo.province
        var city = userInfo.city
        var country = userInfo.country
        //向后端发送请求，进行登录操作
        this.login(userInfo);
      }
    ,
    fail:(err)=>{
      console.log(err)
    }
    })
  },

  login: function(userInfo){
    var that = this;
    wx.login({
      success(res){
        if(res.code){
          // console.log(res)
          api.post(path.path.wechatLogin,{
              code:res.code,
              name:userInfo.nickName,
              gender:userInfo.gender,
              headUrl:userInfo.avatarUrl
          }).then(res => {
            wx.setStorageSync('token',res.data);
            //获取登录用户信息
            api.post(path.path.getLoginData,{

            }).then(res=>{
              wx.setStorageSync('loginData', res.data);
              that.setData({
                user:res.data
              })
              //提示
              $Message({
                content:'登录成功',
                type:'success'
              });
              wx.setStorageSync("isLogin",true)
            })
          }).catch(err => {
            console.log(err)
          })
        }
      }
    })
  },
  toChangeInfo(){
    //获取当前登录的用户信息，将用户的id传给新页面
    wx.navigateTo({
      url: './changeMyInfo?id='+this.data.user.id,
    })
  },
  changeLogin(){
    //切换登录方式，使用管理系统的账号登录
    wx.navigateTo({
      url: './manageLogin/login',
    })
  },
  wechatLogin(){
  },

  //点击已看过的
  toVisited(){
    //判断已看过的数量是否为0
    if(this.data.count.visited==0){
      $Toast({
        content:'您没有看过的房源',
        icon: 'warning_fill'
      })
    }else{
      //跳转到我看过的页面
      wx.navigateTo({
        url: './viewed/viewed',
      })
    }
  },

  //点击我收藏的
  toCollection(){
    //判断我收藏的数量是否为0
    if(this.data.count.collection==0){
      $Toast({
        content:'您没有收藏的房源',
        icon: 'warning_fill'
      })
    }else{
      //跳转到我的收藏的页面
      wx.navigateTo({
        url: './collection/collection',
      })
    }
  },

  //点击我正在租
  toMyRent(){
    //判断我正在租的数量是否为0
    if(this.data.count.myRent==0){
      $Toast({
        content: '您没有正在租的房源',
        icon: 'warning_fill'
      })
    }else{
      wx.navigateTo({
        url: './renting/renting',
      })
    }
  },

  //点击我的房源
  toMyHouse(){
    //跳转到我的房源列表页面
    $Toast({
      content: '该功能暂未开放',
      icon: 'warning_fill'
    });
  },
  rentManage(){
    $Toast({
      content:'该功能暂未开放',
      icon:'warning_fill'
    })
  }
})