<template>
  <div class="myMenu" >
    <el-menu class="sidebar-el-menu"
             :default-active="$route.path"
             :collapse="collapse"
             background-color="rgb(54,52,51)"
             text-color="rgb(242,230,206)"
             active-text-color="rgba(81,196,211)"
             unique-opened router
             :router="true"
             :collapse-transition="false"
             >
      <template v-for="item in menuList">
        <template v-if="item.children!=null&&item.children.length>0">
          <el-submenu :index="item.sort.toString()" :key="item.sort">
            <template slot="title">
              <i :class="item.icon"></i><span slot="title">{{item.name}}</span>
            </template>
            <el-menu-item v-for="child in item.children" :index="child.url" :key="child.url">
                <span slot="title">{{child.name}}</span>
            </el-menu-item>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.url" :key="item.url">
            <i :class="item.icon"></i><span slot="title">{{item.name }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "MyMenu",
  data() {
    return {
      collapse: false,
      menuList:[
        {
          icon:'el-icon-monitor',
          sort:'1',
          url:'/showData',
          name:'数据展示',
          childList:[]
        },
        {
          icon:'el-icon-house',
          sort:'2',
          url:'/house',
          name:'房源管理',
          childList:[
            {
              sort:'1',
              url:'/houseManage',
              name:'房源列表',
              childList:[]
            },
            {
              sort:'2',
              url:'/houseRenting',
              name:'在租列表',
              childList:[]
            },
            {
              sort:'3',
              url:'/rentRecord',
              name:'租金记录',
              childList:[]
            }
          ]
        },
        {
          icon:'el-icon-message',
          sort:'3',
          url:'/apply',
          name:'申请管理',
          childList:[
            {
              sort:'1',
              url:'/lookHouseApply',
              name:'看房申请',
              childList:[]
            },
            {
              sort:'2',
              url:'/rentHouseApply',
              name:'租房申请',
              childList:[]
            }
          ]
        },
        {
          icon:'el-icon-picture-outline',
          sort:'4',
          url:'/picManage',
          name:'图片管理',
          childList:[]
        },
        {
          icon:'el-icon-user',
          sort:'5',
          url:'/userManage',
          name:'用户管理',
          childList:[]
        },
        {
          icon:'el-icon-notebook-1',
          sort:'6',
          url:'/dictManage',
          name:'字典管理',
          childList:[]
        },
        {
          icon: 'el-icon-s-order',
          sort:'7',
          url: '/menuManage',
          name:'菜单管理',
          childList: []
        }

      ]
    }
  },
  methods:{

  },
  mounted() {
    //取出当前登录人的菜单
    this.$network.getLoginMenu().then(res=>{
      // console.log("res.data.data")
      // console.log(res.data.data)
      this.menuList = res.data.data
    }).catch(err=>{
      console.log(err)
    })
  }
}
</script>

<style scoped>

.myMenu{
  /*width: 250px;*/
  height: 100%;
  background-color: #324157;
  /*position: fixed;*/
  float: right;
}

element.style{
  padding-left: 0px;
}

.sidebar-el-menu{
  width: 150px;
}

  ul {
    height: 100%;
  }

  .sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
    float: left;
  }

  .sidebar > ul {
    height: 100%;
  }



.el-submenu .el-menu-item {
  height: 50px;
  line-height: 50px;
  padding: 0;
  min-width: 200px;
  margin-left: 30px;
}
.el-menu{
  border: 0;
}

.el-container{
  height: 100%;
}

</style>
