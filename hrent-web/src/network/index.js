import {GET, POST, path} from './config';
import {request, requestJson} from './core'; //引入core模块中抛出的request方法

const network = {
  getBaseInfo(){
    return request(POST,path.getBaseInfo);
  },
  getDataCount(){
    return request(POST,path.getDataCount);
  },

  findUserPage(params) {
    return request(POST, path.findUserPage, params)
  },
  addUser(params){
    return request(POST,path.addUser,params)
  },
  login(params){
    return request(POST,path.login,params)
  },
  getLoginData(params){
    return request(POST,path.getLoginData,params)
  },
  deleteUser(params){
    return request(POST,path.deleteUser,params)
  },
  updateUser(params){
    return request(POST,path.updateUser,params)
  },
  getUser(params){
    return request(POST,path.getUser,params)
  },
  findLoginName(params){
    return request(POST,path.findLoginName,params)
  },
  register(params){
    return request(POST,path.registerUser,params)
  },

  findAllSystem(){
    return request(GET,path.findAllSystem)
  },

  //菜单
  deleteMenu(params){
    return request(POST,path.deleteMenu,params)
  },
  findMenu(params){
    return request(POST,path.findMenu,params)
  },
  findRoleMenu(params){
    return request(POST,path.findRoleMenu,params)
  },
  keepRoleMenu(params){
    return requestJson(POST,path.keepRoleMenu,params)
  },
  getLoginMenu(){
    return requestJson(POST,path.getLoginMenu)
  },

  delArticleImg(params){
    return request(POST,path.delArticleImg,params)
  },
  findAllRole(){
    return request(POST,path.findAllRole)
  },


  //字典
  findDictPage(params){
    return request(POST,path.findDictPage,params)
  },
  findDictAllType(){
    return request(POST,path.findDictAllType)
  },
  addDict(params){
    return request(POST,path.addDict,params)
  },
  editDict(params){
    return request(POST,path.editDict,params)
  },
  deleteDict(params){
    return request(POST,path.deleteDict,params)
  },
  findAllJson(params){
    return requestJson(POST,path.findAllJson,params)
  },


  housePicUpload(params){
    return request(POST,path.deleteDict,params)
  },
  addHouseResource(params){
    return requestJson(POST,path.addHouse,params)
  },
  findHousePage(params){
    return request(POST,path.findHousePage,params)
  },
  findHousePageJson(params){
    return requestJson(POST,path.findHousePage,params)
  },
  findHouseByRole(params){
    return requestJson(POST,path.findHouseByRole,params)
  },
  saveHouse(params){
    return requestJson(POST,path.saveHouse,params)
  },
  deleteHouse(params){
    return request(POST,path.deleteHouse,params)
  },



  findAllTags(){
    return request(POST,path.findAllTags)
  },

  findPicPage(params){
    return request(POST,path.findPicPage,params)
  },
  updatePic(params){
    return requestJson(POST,path.updatePic,params)
  },
  deletePic(params){
    return request(POST,path.deletePic,params)
  },

  //申请
  findViewApplyPage(params){
    return request(POST,path.findViewApplyPage,params)
  },
  deleteViewApply(params){
    return request(POST,path.deleteViewApply,params)
  },
  //修改看房申请
  saveViewApply(params){
    return requestJson(POST,path.saveViewApply,params)
  },

  findRentApplyPage(params){
    return request(POST,path.findRentApplyPage,params)
  },
  deleteRentApply(params){
    return request(POST,path.deleteRentApply,params)
  },
  saveRentApply(params){
    return requestJson(POST,path.saveRentApply,params)
  },

  //在租列表
  findRentingListPage(params){
    return request(POST,path.findRentingListPage,params)
  },
  deleteRenting(params) {
    return request(POST,path.deleteRenting,params)
  },

  //租金记录
  findRentPage(params){
    return request(POST,path.findRentPage,params)
  },
  deleteRent(params){
    return request(POST,path.deleteRent,params)
  },

  //消息
  addMessage(params){
    return requestJson(POST,path.addMessage,params)
  },
  getNewCount(params){
    return request(POST,path.getMessageCount,params)
  },
  findMessage(params){
    return requestJson(POST,path.findMessage,params)
  },
  changeMessage(params){
    return requestJson(POST,path.changeMessage,params)
  }
};
//抛出network
export default network;

