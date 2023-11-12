const app = getApp()

const request = (url, options) => {
   //  console.log(app.globalData.baseUrl)
   return new Promise((resolve, reject) => {
       wx.request({
           url: `${app.globalData.baseUrl}${url}`,
           method: options.method,
           data: JSON.stringify(options.data),
           header: {
               'Content-Type': 'application/json; charset=UTF-8',
               'token': wx.getStorageSync('token') || ''
           },
           success(request) {
               // console.log(request.data)
               if(request.data==50000){
                  //如果微信小程序未登录，那么就跳转到登录页面，如果登录过，那么就自动进行登录，获取到token
                  // console.log(wx.getStorageSync('loginData'))
                  if(wx.getStorageSync('loginData')!=null&&wx.getStorageSync('loginData')!=''){
                     // console.log(wx.getStorageSync('loginData').openId)
                     let openId = wx.getStorageSync('loginData').openId;
                     // console.log(openId)
                     //向后端发送请求，通过openId登录
                     // console.log(app.globalData.baseUrl)
                     wx.request({
                       url: app.globalData.baseUrl+"wechat/loginByOpenId",
                       method:post,
                       data:{
                          openId:openId
                       },
                       header: {
                        'Content-Type': 'application/json; charset=UTF-8',
                       },
                       success(request){
                          console.log(request.data)
                          //将用户信息和token储存起来
                          wx.setStorageSync('loginData', request.data.data.loginUser);
                          wx.setStorageSync('token', request.data.data.token);
                          //储存起来之后再次调用被拦截的接口
                          wx.request({
                           url: `${app.globalData.baseUrl}${url}`,
                           method: options.method,
                           data: JSON.stringify(options.data),
                           header: {
                               'Content-Type': 'application/json; charset=UTF-8',
                               'token': request.data.data.token || ''
                           },
                           success(res){
                              console.log(res)
                           }
                          })
                       }
                     })
                   }else{
                     //跳转到个人信息页面进行登录操作
                     wx.setStorageSync('isLogin', false)
                     wx.switchTab({
                       url: '/pages/userInfo/myInfo',
                     })
                   }
               }
                resolve(request.data)
           },
           fail(error) {
               console.log(error)
               reject(error.data)
           }
       })
   })
}

const get = (url, options = {}) => {
   return request(url, { method: 'GET', data: options })
}

const post = (url, options) => {
   return request(url, { method: 'POST', data: options })
}

const put = (url, options) => {
   return request(url, { method: 'PUT', data: options })
}

// 不能声明DELETE（关键字）
const remove = (url, options) => {
   return request(url, { method: 'DELETE', data: options })
}

module.exports = {
   get,
   post,
   put,
   remove
}