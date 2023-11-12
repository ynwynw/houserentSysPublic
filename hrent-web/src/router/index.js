import Vue from 'vue'
import Router from 'vue-router'
import Init from "../view/Init";
import HouseManage from "../view/house/HouseManage";
import HouseRenting from "../view/house/HouseRenting";
import RentRecord from "../view/house/RentRecord";
import LookHouseApply from "../view/apply/LookHouseApply";
import showData from "../view/ShowData";
import ShowData from "../view/ShowData";
import PicManage from "../view/PicManage";
import UserManage from "../view/UserManage";
import DictManage from "../view/house/DictManage";
import RentHouseApply from "../view/apply/RentHouseApply";
import Login from "../view/Login";
import MenuManage from "../view/MenuManage";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect:'/login'
    },
    {
      path:'/login',
      name:'login',
      component:Login
    },
    {
      path: '/init',
      name: 'init',
      component: Init,
      children:[
        {
          path: '/houseManage',
          name: 'houseManage',
          component: HouseManage
        },
        {
          path: '/houseRenting',
          name: 'houseRenting',
          component: HouseRenting
        },
        {
          path: '/rentRecord',
          name: 'rentRecord',
          component: RentRecord
        },
        {
          path: '/lookHouseApply',
          name: 'lookHouseApply',
          component:LookHouseApply
        },
        {
          path: '/rentHouseApply',
          name: 'rentHouseApply',
          component:RentHouseApply
        },
        {
          path: '/showData',
          name: 'showData',
          component:ShowData
        },
        {
          path: '/picManage',
          name: 'picManage',
          component:PicManage
        },
        {
          path: '/userManage',
          name: 'userManage',
          component: UserManage
        },
        {
          path: '/dictManage',
          name: 'dictManage',
          component: DictManage
        },
        {
          path : '/menuManage',
          name: 'menuManage',
          component:MenuManage
        }

      ]
    }

  ]
})
