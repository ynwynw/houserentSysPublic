export const GET="get";
export const POST="post";
export const path={
  //公共资源
  getBaseInfo:"sysCommon/getBaseInfo",
  getDataCount:"sysCommon/getDataCount",

  findUserPage:"/user/findPage",
  addUser:"user/save",
  login:"user/login",
  getLoginData:'user/getLoginData',
  deleteUser:'user/delete',
  updateUser:'user/save',
  getUser:'user/get',
  findLoginName:'sysCommon/findLoginName',
  registerUser:'sysCommon/register',

  deleteMenu:'menu/delete',
  findMenu:'menu/findAll',
  delArticleImg:'article/delImg',
  findAllRole:'role/findAll',
  findRoleMenu:'menu/findRoleMenu',
  keepRoleMenu:'menu/keepRoleMenu',
  getLoginMenu:'menu/getLoginMenu',

  //字典
  findDictPage:'dict/findPage',
  findDictAllType:'dict/findAllType',
  addDict:'dict/save',
  editDict:'dict/save',
  deleteDict:'dict/delete',
  findAllJson:'dict/findAllJson',

  //房源
  housePicUpload:'houseResource/uploadPic',
  addHouse:'houseResource/save',
  findHousePage:'houseResource/findPage',
  findHouseByRole:'houseResource/findByRole',
  saveHouse:'houseResource/save',
  deleteHouse:'houseResource/delete',

  //标签
  findAllTags:'tag/findAll',

  //图片
  findPicPage:'pic/findPage',
  //修改图片
  updatePic:'pic/save',
  deletePic:'pic/delete',

  //申请
  findViewApplyPage:'applyView/findPage',
  deleteViewApply:'applyView/delete',
  saveViewApply:'applyView/save',

  findRentApplyPage:'applyRent/findPage',
  deleteRentApply:'applyRent/delete',
  saveRentApply:'applyRent/save',

  //在租列表
  findRentingListPage:'houseRenting/findPage',
  deleteRenting:'houseRenting/delete',

  //租金记录
  findRentPage:'rent/findPage',
  deleteRent:'rent/delete',

  //消息
  addMessage:'message/save',
  getMessageCount:'message/getNewCount',
  findMessage:'message/findPersonalMessage',
  changeMessage:'message/save',

}
