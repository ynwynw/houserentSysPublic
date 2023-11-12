export const baseMessage = {
  //审核成功的消息
  examineSuccess:function(houseId){
    return "您有一条房源信息已审核成功，房源编号【"+houseId+"】";
  },
  //审核失败消息
  examineFail:function(houseId){
    return "您有一条房源信息审核失败！请前往查看，房源编号【"+houseId+"】";
  },

  //看房申请
  //新增看房申请
  addViewApply:function(){
    return "您有一条看房申请，请尽快处理";
  },
  //看房申请通过
  viewApplyAdopt(){
    return "您有一条看房申请已通过";
  },
  //看房申请未通过
  viewApplyNotAdopt(){
    return "您有一条看房申请未通过";
  },
  //看房申请修改时间
  viewApplyChangeTime(){
    return "您的一条看房申请预约时间被修改，请前往处理"
  },

  //租房申请
  //新增租房申请
  addRentApply(){
    return "您有一条新的租房申请，请前往处理"
  },
  //租房申请通过
  rentApplyAdopt(){
    return "您有一条租房申请已通过，请前往查看"
  },
  //租房申请未通过
  rentApplyNotAdopt(){
    return "您有一条租房申请未通过，请前往查看"
  },

  //缴纳租金
  rentMoney(houseId){
    return "您编号为【"+houseId+"】的房源的租金应该缴纳了，请前往缴费"
  }
}
