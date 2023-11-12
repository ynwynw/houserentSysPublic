const path={
  //用户
  wechatLogin:'wechat/login',
  getLoginData:'user/getLoginData',
  getUserNumber:'wechat/getUserNumber',
  userSave:'user/wechatSave',
  login:'user/loginJson',
  getBaseInfoCount:'user/getBaseInfoCount',

  //房源
  findHousePage:'wechat/findHousePage',
  findHouseByCondition:'wechat/findHouseByCondition',
  getHouseById:'wechat/getHouseById',
  //我看过的房源
  findViewedHousePage:'houseResource/findViewedHousePage',
  //我收藏的房源
  findCollectionHousePage:'houseResource/findCollectionHousePage',
  //我正在租的房源
  findRentingHousePage:'houseResource/findRentingHousePage',


  //标签
  findAllTags:'wechat/findAllTags',

  //用户
  getUserById:'wechat/getUserById',
  uploadHead:'user/uploadHead',

  //申请
  addViewApply:'applyView/save',
  addRentApply:'applyRent/save',
  getRentApplyByEntity:'applyRent/getByEntity',
  getViewApplyByEntity:'applyView/getByEntity',
  findPersonalViewApply:'applyView/findPersonalViewApply',
  findPersonalRentApply:'applyRent/findPersonalRentApply',
  getViewApply:'applyView/wechatGet',
  saveViewApply:'applyView/save',
  getRentApply:'applyRent/wechatGet',
  saveRentApply:'applyRent/save',
  //支付押金成功
  paySuccess:'applyRent/paySuccess',

  //消息
  getNewMessageCount:'message/getNewCount',
  findMessage:'message/findPersonalMessage',
  changeMessage:'message/save',

  //字典
  findDict:'dict/findAllJson',

  //公共
  getSystemResource:'sysCommon/getSystemResource',

  //收藏
  findCollectionList:'collection/findAll',
  saveCollection:'collection/save',
  deleteCollection:'collection/delete',

  //图片
  findAllPic:'pic/findAllJson'
}

module.exports={
  path
}