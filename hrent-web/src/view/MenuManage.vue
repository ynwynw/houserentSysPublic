<template>
  <div class="menuManage">
    <el-tabs type="card" v-model="tableTabsValue">
      <el-tab-pane label="菜单管理" name="menuManage">
        <el-row>
          <el-col :span="8">
            <el-select v-model="chooseRole" placeholder="请选择" @change="selectChange">
              <el-option
                v-for="item in roleList"
                :key="item.id"
                :label="item.label"
                :value="item.value">

              </el-option>
            </el-select>
            <div class="menuTitle">为角色分配菜单</div>
            <div class="TreeMenu">
              <el-tree
                :data="allMenuList"
                :props="props"
                :default-expanded-keys="[2, 6]"
                :default-checked-keys="haveMenu"
                show-checkbox
                node-key="id"
                ref="tree">

              </el-tree>
            </div>
            <el-button type="primary" @click="getCheckedNodes">保存</el-button>
          </el-col>
          <el-col :span="8"></el-col>
          <el-col :span="8"></el-col>
        </el-row>
      </el-tab-pane>

    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "MenuManage",
  data(){
    return{
      allMenuList:[],
      props: {
        label: 'name',
        children:'children',
        value:'id',
      },
      tableTabsValue:'menuManage',
      chooseRole:'3',
      roleList:[],
      haveMenu:[]
    }
  },
  methods:{
    selectChange(data){
      //通过选中的角色到数据库中查询该角色对应的菜单
      let params = {
        roleId:data
      }
      this.chooseRole = data
      this.$network.findRoleMenu(params).then(res=>{
        // console.log(res.data.data)
        // this.haveMenu = res.data.data;
        this.$refs.tree.setCheckedKeys(res.data.data)
      }).catch(err=>{
        console.log(err)
      })
    },
    getCheckedNodes(){
      //获取已选择的菜单
      let list = this.$refs.tree.getCheckedNodes();
      //将id改变，将remake字段设置为id，id重新获取
      for (let i = 0;i<list.length;i++){
        list[i].remake = list[i].id;
        //生成随机id
        let ranId = Number(Math.random().toString().substr(3,36) + Date.now()).toString(36);
        //再次循环数组，设置parentId
        for (let j = i+1;j<list.length;j++){
          if(list[j].parentId==list[i].id){
            list[j].parentId = ranId;
          }
        }
        list[i].id=ranId;
        //将菜单中的roleId设置为下拉框中的值
        list[i].roleId = this.chooseRole;
      }
      console.log(list)
      //想后端发送请求，保存菜单
      let params = {
        menuList:list
      }
      this.$network.keepRoleMenu(params).then(res=>{
        if(res.data.code=='200'){
          this.$message({
            message:'保存成功',
            type:'success'
          })
        }else{
          this.$message({
            message:'保存失败',
            type:'error'
          })
        }
        //重置菜单信息
        let params = {
          system:1,
          roleId:999,
        }
        this.$network.findMenu(params).then(res=>{
          this.allMenuList = res.data.data;
        })
        //查询当前下拉框选中的角色的菜单
        params = {
          roleId:this.chooseRole
        }
        this.$network.findRoleMenu(params).then(res=>{
          this.haveMenu = res.data.data;
        }).catch(err=>{
          console.log(err)
        })
      }).catch(err=>{
        console.log(err)
      })
    }
  },
  beforeMount() {
    let params = {
      system:1,
      roleId:999,
    }
    this.$network.findMenu(params).then(res=>{
      this.allMenuList = res.data.data;
    })
    //从字典中取出角色信息
    params = {
      type:'hrent_role_type'
    }
    this.$network.findAllJson(params).then(res=>{
      this.roleList = res.data.data
    }).catch(err=>{
      console.log(err)
    })
    //通过选中的角色到数据库中查询该角色对应的菜单
    params = {
      roleId:this.chooseRole
    }
    this.$network.findRoleMenu(params).then(res=>{
      this.haveMenu = res.data.data;
    }).catch(err=>{
      console.log(err)
    })

  }
}
</script>

<style scoped>
.menuTitle{
  float: left;
  font-family: 华文楷体;
  font-size: 20px;
  color: #409EFF;
}
</style>
