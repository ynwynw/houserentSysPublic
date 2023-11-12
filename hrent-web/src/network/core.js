import axios from "axios";
import {GET,POST,path} from './config';
import {Loading,Message} from "element-ui";
import router from '../router/index';
import {base} from "../config";

//创建一个axios实例 返回一个axios对象
const instance = axios.create({
  baseURL:base.baseUrl,
  //timeout:5000
  //设置axios为form-data
  headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8;multipart/form-data'},
  transformRequest:[function (data){
    // let ret = [];
    // for (let it in data){
    //   ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
    // }
    // return ret
    // 普通对象,通过URLSearchParams传参
    let params = new URLSearchParams();
    for (let i in data) {
      if (Object.prototype.hasOwnProperty.call(data, i)) { //过滤
        params.append(i, data[i]);
      }
    }
    return params;
  }]
});

//创建一个axios实例 返回一个axios对象
const instanceJson = axios.create({
  baseURL:base.baseUrl,
  //timeout:5000
  //设置axios为form-data
  headers: {'Content-Type': 'application/json;charset=UTF-8;multipart/form-data'},
  // transformRequest:[function (data){
  //   // 普通对象,通过URLSearchParams传参
  //   let params = new URLSearchParams();
  //   for (let i in data) {
  //     if (Object.prototype.hasOwnProperty.call(data, i)) { //过滤
  //       params.append(i, data[i]);
  //     }
  //   }
  //   return params;
  // }]
});

// 创建loading实例
var loading = null;
// 添加请求拦截器
instance.interceptors.request.use(function (config) {
  loading = Loading.service({
    lock: true,
    text: 'Loading',
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.5)'
  });
  config.headers = {DeviceType: 'H5'};
  if (localStorage.getItem('token')) {
    config.headers.token = localStorage.getItem('token');
  }
  return config;
}, function (error) {
  // 对请求错误做些什么
  Message(error);
  console.log("========>", error);
  return Promise.reject(error);
});

// 添加响应拦截器
instance.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  // setTimeout(() => {
    loading.close();
  // }, 500);
  if(response.data=='50000'){
    router.push({
      path:'login',
    })
  }
  return response;
}, function (error) {
  // 对响应错误做点什么
  Message(error);
  console.log("========>", error);
  return Promise.reject(error);
});

//抛出 request 方法 接收三个参数 1.请求方式 2.请求路径 3.传递的参数
export function request(methods, url, params) {
  switch (methods) {
    case GET:
      return get(url, params);  //把promise对象 返回
    case POST:
      return post(url, params)
  }
}

//get请求
function get(url, params) {
  //axios请求  返回的是promise对象 有返回值 后续可以直接调用then catch 等方法
  return instance.get(url, params)
}

//post请求
function post(url, params) {
  return instance.post(url, params)
}

//抛出 request 方法 接收三个参数 1.请求方式 2.请求路径 3.传递的参数
export function requestJson(methods, url, params) {
  switch (methods) {
    case GET:
      return getJson(url, params);  //把promise对象 返回
    case POST:
      return postJson(url, params)
  }
}

//JSON数据传送

//get请求
function getJson(url, params) {
  //axios请求  返回的是promise对象 有返回值 后续可以直接调用then catch 等方法
  return instanceJson.get(url, params)
}

//post请求
function postJson(url, params) {
  return instanceJson.post(url, params)
}

// 创建loading实例
var loading = null;
// 添加请求拦截器
instanceJson.interceptors.request.use(function (config) {
  loading = Loading.service({
    lock: true,
    text: 'Loading',
    spinner: 'el-icon-loading',
    background: 'rgba(0, 0, 0, 0.5)'
  });
  if (localStorage.getItem('token')) {
    config.headers.token = localStorage.getItem('token');
  }
  return config;
}, function (error) {
  // 对请求错误做些什么
  Message(error);
  console.log("========>", error);
  return Promise.reject(error);
});

// 添加响应拦截器
instanceJson.interceptors.response.use(function (response) {
  // 对响应数据做点什么
  // setTimeout(() => {
    loading.close();
  // }, 50);
  return response;
}, function (error) {
  // 对响应错误做点什么
  Message(error);
  console.log("========>", error);
  return Promise.reject(error);
});




