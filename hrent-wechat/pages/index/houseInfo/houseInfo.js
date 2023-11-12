// pages/index/houseInfo/houseInfo.js
import api from '../../../config/api'
import path from '../../../config/path'
import baseMessage from '../../../config/message'
const app = getApp()
const {
  $Message
} = require('../../../dist/base/index');
const { $Toast } = require('../../../dist/base/index');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    collectionInfo:{},
    collection:false,
    markers: [{
      id: 1,
      iconPath: "../../../images/icon/location.png",
      height: 34,
      latitude: 23.099994,
      longitude: 113.324520,
    }],
    picLocal: app.globalData.picLocal,
    id: '',
    imageSetting: {
      indicatorDots: true,
      autoplay: true,
      interval: 5000,
      duration: 500
    },
    houseReource: {

    },
    owner: {

    },
    baseHeadPic: app.globalData.picLocal + "/default/head/head.png",
    picLocal: app.globalData.picLocal,
    showModal: false,
    viewTime: '',
    viewDate: '',
    startDate: '',
    endDate: '',
    rentVisiable: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // console.log(app.globalData.picLocal)
    this.setData({
      id: options.id
    })
    // console.log(this.data.id)
    //通过id查询出房源的全部信息
    let params = {
      id: this.data.id
    }
    api.post(path.path.getHouseById, params).then(res => {
      this.setData({
        houseReource: res.data
      })
      //console.log(this.data.houseReource)
      //根据房源信息查找房东信息
      // console.log(this.data.houseReource.createBy.id)
      let params = {
        id: this.data.houseReource.createBy.id
      }
      api.post(path.path.getUserById, params).then(res => {
        // console.log(res)
        this.setData({
          owner: res.data
        })
      }).catch(err => {
        console.log(err)
      })
      //设置地图上的指示标
      this.data.markers[0].latitude = this.data.houseReource.latitude;
      this.data.markers[0].longitude = this.data.houseReource.longitude;
      this.setData({
        markers: this.data.markers
      })

      let userId = wx.getStorageSync('loginData').id;
      //查询这个房源有没有被用户收藏
      params = {
        userId:userId,
        houseId:this.data.houseReource.id
      }
      api.post(path.path.findCollectionList,params).then(res=>{
        // console.log(res.data)
        var collection = false;
        var collectionInfo = {};
        if(res.data.length>0){
          collection = true
          collectionInfo = res.data[0]
        }
        this.setData({
          collection:collection,
          collectionInfo:collectionInfo
        })
      }).catch(err=>{
        console.log(err)
      })
    }).catch(err => {
      console.log(err)
    });
    //获取当前日期，将开始日期设置为当前日期的后一天
    var myDate = new Date()
    // console.log(myDate.getMonth())
    this.setData({
      startDate: myDate.getFullYear() + "-" + myDate.getMonth() + "-" + myDate.getDate(),
      // endDate: myDate.getFullYear() + 1 + "-" + myDate.getMonth() + 1 + "-" + myDate.getDate(),
    })

    

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

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  toApplyLook(e) {
    console.log(this.data.houseReource)
    let userId = wx.getStorageSync('loginData').id;
    //查询当前登录用户是否已经有这个房源的申请，并且状态为0或者1的
    let params = {
      applyUserId:userId,
      houseId:this.data.houseReource.id
    }
    api.post(path.path.getViewApplyByEntity,params).then(res=>{
      //如果查询出来的数据还有状态没有处理的，并且在预约时间之内的，就提示用户
      if(res.data!=null){
        if(res.data.state==0||res.data.state==2){
          $Toast({
            content:'请将其余这个房源的其余看房申请结束之后再次申请'
          })
          return;
        }
      }
      this.setData({
        showModal: true
      })
    }).catch(err=>{
      console.log(err)
    })
    
  },
  toApplyRent(e) {
    console.log(this.data.houseReource)
    let userId = wx.getStorageSync('loginData').id;
    //查询当前登录用户是否已经有这个房源的申请，并且状态为0或者1的
    let params = {
      applyUserId:userId,
      houseId:this.data.houseReource.id
    }
    api.post(path.path.getRentApplyByEntity,params).then(res=>{
      //如果查询出来的数据还有状态没有处理的，并且在预约时间之内的，就提示用户
      if(res.data!=null){
        if(res.data.state==0||res.data.state==1){
          $Toast({
            content:'请将其余这个房源的其余租房申请结束之后再次申请'
          })
          return;
        }
      }
      //继续流程
      //判断用户是否已经登录
    if (wx.getStorageSync('isLogin') == true) {
      //判断当前登录用户有没有手机号，如果没有手机号，那么就获取用户的手机号
      if (wx.getStorageSync('loginData').mobile == null) {
        //获取code，向后台发送获取手机号
        // console.log(e.detail.code)
        let params = {
          code: e.detail.code
        }
        api.post(path.path.getUserNumber, params).then(res => {
          // console.log(res.data)
          //储存用户信息，将用户的手机号储存起来
          let userId = wx.getStorageSync('loginData').id;
          let params = {
            id: userId,
            mobile: res.data
          }
          api.post(path.path.userSave, params).then(res => {

          }).catch(err => {
            console.log(err)
          })
        }).catch(err => {
          console.log(err)
        });
        //更新完成用户信息之后重新查询登录用户的信息
        api.post(path.path.getLoginData).then(res => {
          // console.log(res);
          wx.setStorageSync('loginData', res.data);
        }).catch(err => {
          console.log(err)
        })
      }
      this.setData({
        showModal2: true
      })
    } else {
      //跳转到个人信息页面进行登录操作
      wx.setStorageSync('isLogin', false)
      wx.switchTab({
        url: '/pages/userInfo/myInfo',
      })
    }
    }).catch(err=>{
      console.log(err)
    })
  },
  modal_click_Hidden() {
    //弹出自定义弹出框，选择时间
    this.setData({
      showModal: false
    })
  },
  modal_click_Hidden2() {
    //弹出自定义弹出框，选择时间
    this.setData({
      showModal2: false
    })
  },
  bindDateChange2(e) {
    this.setData({
      endDate: e.detail.value
    })
  },
  bindDateChange(e) {
    this.setData({
      viewDate: e.detail.value
    })
  },
  bindTimeChange(e) {
    this.setData({
      viewTime: e.detail.value
    })
  },
  //增加一个看房申请
  Sure2() {
    let houseId = this.data.houseReource.id
    let endDate = this.data.endDate + " 23:59:59"
    let userId = wx.getStorageSync('loginData').id;
          let params = {
            applyUserId: userId,
            houseId: houseId,
            state: 0,
            endTime: endDate
          }
          //生成租房申请
          api.post(path.path.addRentApply, params).then(res => {
            if (res.code == "200") {
              $Message({
                content: '租房申请已提交，请前往申请详情页面查看',
                type: 'success'
              })
              this.setData({
                showModal2: false
              })
              //向房东发送新增租房申请的消息
              let params = {
                receiverType:'0',
                receiverId:this.data.houseReource.createBy.id,
                title:'新增租房申请',
                text:baseMessage.addRentApply(),
                state:0
              }
              //向后端发送请求，增加消息
              this.$network.addMessage(params).then(res=>{

              }).catch(err=>{
                console.log(err)
              })
            }
          }).catch(err => {
            console.log(err)
          })
  },
  //增加一个看房申请
  Sure() {
    let userId = wx.getStorageSync('loginData').id;
    //处理看房时间
    // console.log(this.data.viewDate+" "+this.data.viewTime)
    let params = {
      applyUserId: userId,
      houseId: this.data.houseReource.id,
      viewTime: this.data.viewDate + " " + this.data.viewTime,
      state: 0,
    }
    //向后端发送请求
    api.post(path.path.addViewApply, params).then(res => {
      // console.log(res)
      $Message({
        content: '看房申请已提交，请前往申请页面查看详情',
        type: 'success'
      })
      //关闭弹框
      this.setData({
        showModal: false
      })
      //向房东发送看房申请的消息
      let params = {
        receiverType:'0',
        receiverId:this.data.houseReource.createBy.id,
        title:'新增看房申请',
        text:baseMessage.addViewApply(),
        state:0
      }
      //向后端发送请求，增加消息
      this.$network.addMessage(params).then(res=>{

      }).catch(err=>{
        console.log(err)
      })
    }).catch(err => {
      console.log(err)
    })
  },
  sureRent() {
    console.log(this.data.rentVisiable)
    this.setData({
      rentVisiable: false
    })
  },
  cancelRent() {
    console.log(this.data.rentVisiable)
    this.setData({
      rentVisiable: false
    })
  },
  collectionChange(){
    //向后端发送请求，改变收藏
    let userId = wx.getStorageSync('loginData').id;
    if(this.data.collection){
      let params = {
        id:this.data.collectionInfo.id
      }
      //取消收藏
      api.post(path.path.deleteCollection,params).then(res=>{
        if(res.code==200){
          $Toast({
            content:'已取消收藏'
          })
        }
      }).catch(err=>{
        console.log(err)
      })
    }else{
      let params = {
        houseId:this.data.houseReource.id,
        userId:userId
      }
      //添加收藏
      api.post(path.path.saveCollection,params).then(res=>{
        if(res.code==200){
          $Toast({
            content:'已成功收藏'
          })
        }
      }).catch(err=>{
        console.log(err)
      })
    }
    this.setData({
      collection:!this.data.collection
    })
  }
})