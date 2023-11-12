<template>
  <el-container>
    <el-header>
      <Header v-on:showPersonInfo="showPersonInfo" ref="header" v-on:showMessage="showMessage"></Header>
    </el-header>
    <el-container>
      <el-aside width="250px" :style="{height:gd}">
        <MyMenu></MyMenu>
      </el-aside>
      <el-main>
        <router-view/>
        <el-dialog title="个人信息" :visible.sync="personInfoVisible" width="50%" :close-on-click-modal="false" :close-on-press-escape="false">
          <div class="personInfo">
            <div class="infoImg">
              <el-image :src="picLocal+''+person.headUrl" style="width: 200px;height: 200px;"></el-image>
              <el-upload
                :headers="uploadHeaders"
                :action="uploadHead"
                :on-success="uploadSuccess"
                :before-upload="beforeUpload"
                :show-file-list="false"
                list-type="picture-card"
                style="margin-top: 20px">
                <i class="el-icon-plus"></i>
              </el-upload>
            </div>
            <div class="otherInfo">
              <el-form :inline="true" :model="person" size="mini" class="person" ref="person" :rules="personRules">
                <el-form-item label="用户id：" prop="id" class="is-required">
                  <el-input v-model="person.id" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="用户名：" prop="name" class="is-required">
                  <el-input v-model="person.name" autocomplete="off" maxLength="20"></el-input>
                </el-form-item>
                <el-form-item label="登录名：" prop="alias" class="is-required">
                  <el-input v-model="person.loginName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="角色：" prop="role" class="is-required">
                  <el-select v-model="person.role" placeholder="请选择角色" >
                    <el-option v-for="item in roleList" :value="item.id" :label="item.label" :key="item.id" disabled></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="性别：" prop="sex" class="is-required">
                  <el-select v-model="person.sex" placeholder="请选择性别">
                    <el-option v-for="item in sexList" :value="item.id" :label="item.name" :key="item.id"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="邮箱：" prop="email" class="is-required">
                  <el-input v-model="person.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="手机号：" :label-width="formLabelWidth" prop="mobile" class="is-required">
                  <el-input maxlength="11" v-model.number="person.mobile" style="width: 150px"></el-input>
                </el-form-item>
                <el-form-item label="出生日期" prop="birthday" class="is-required">
                  <el-date-picker v-model="person.birthday"
                                  style="width: 150px"
                                  value-format="yyyy-MM-dd"

                                  placeholder="选择出生日期">

                  </el-date-picker>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="personInfoVisible = false" size="mini">取 消</el-button>
                <el-button type="primary" @click="updateUser('person')"  size="mini">确 定</el-button>
              </div>
            </div>
          </div>
        </el-dialog>
        <el-dialog title="消息列表" :visible.sync="messageListVisible" width="50%" :close-on-click-modal="false" @close="dialogClose">
          <el-dialog
            width="30%"
            title="消息详情"
            :visible.sync="innerVisible"
            append-to-body
            :close-on-click-modal="false">
            <div class="title">{{viewMessage.title}}</div>
            <div class="text">{{viewMessage.text}}</div>
            <div class="time">{{viewMessage.createDate}}</div>
          </el-dialog>
          <div class="messageList">
            <el-tabs type="border-card"  tab-position="left" @tab-click="changeTab">
              <el-tab-pane label="未读">
                <div class="allMessage">
                  <div class="everyMessage" v-for="item in messageList" :key="item.id" @click="messageDetail(item)">
                    <div class="out">
                      <el-row>
                        <el-col :span="12"><div class="messageTitle">{{item.title}}</div></el-col>
                        <el-col :span="12"><div class="messageTime">{{item.createDate}}</div></el-col>
                      </el-row>
                    </div>
                    <div class="messageText">{{item.text}}</div>
                    <el-divider></el-divider>
                  </div>
                </div>
              </el-tab-pane>
              <el-tab-pane label="已读">
                <div class="allMessage">
                  <div class="everyMessage" v-for="item in messageList" :key="item.id" @click="messageDetail(item)">
                    <div class="out">
                      <el-row>
                        <el-col :span="12"><div class="messageTitle">{{item.title}}</div></el-col>
                        <el-col :span="12"><div class="messageTime">{{item.createDate}}</div></el-col>
                      </el-row>
                    </div>
                    <div class="messageText">{{item.text}}</div>
                    <el-divider></el-divider>
                  </div>
                </div>
              </el-tab-pane>
            </el-tabs>
            <el-pagination
              class="messagePage"
              @size-change="sizeChange"
              @current-change="currentChange"
              @prev-click="prevClick"
              @next-click="nextClick"
              background
              layout="total,sizes,prev, pager, next"
              :page-sizes="[10, 20, 30, 40]"
              :page-size="10"
              :total="messagePage.count">
            </el-pagination>
          </div>
        </el-dialog>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import Header from "../components/common/Header";
import MyMenu from "../components/common/MyMenu";
import {base} from "../config";

export default {
  name: "Home",
  components: {Header, MyMenu},
  data(){
    return{
      viewMessage:{},
      innerVisible:false,
      messageList:[],
      messagePage:{
        pageNo:1,
        pageSize:10,
        count:0
      },
      currentTab:'未读',
      messageListVisible:false,
      picLocal:base.picLocal,
      formLabelWidth:"120",
      uploadHead:base.baseUrl+'/user/uploadHead',
      uploadHeaders:{},
      gd:"",
      personInfoVisible:false,
      person:{
        headUrl: '/default/head/head.png'
      },
      //查询出来的头像地址，用于做比较，如果头像没有变化，那么就不修改头像信息
      orginPicUrl:'',
      personRules:{
        name:[
          {required:true,message:"请输入用户名",trigger:'blur'}
        ],
        loginName:[
          {required:true,message:"请输入登录名",trigger:'blur'}
        ],
        email:[
          {required:true,message:"请输入邮箱",trigger:'blur'},
          {required:true, pattern:/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/, message: "请输入正确格式的邮箱",trigger: 'blur' }
        ],
        roleId:[
          {required:true,message:"请选择角色",trigger:'change'}
        ],
        sex:[
          {required:true,message:"请选择性别",trigger:'change'}
        ],
        mobile:[
          {required: true,message: "请输入电话号码",trigger:'blur'},
          {required: true,pattern:/^1\d{10}$/, message: "电话号码为11位",trigger: 'blur'}
        ],
        birthday:[
          {require: true,message:"请选择出生日期",trigger:'change'},
        ]
      },
      roleList:[

      ],
      sexList:[
        {
          id:'0',
          name:'男'
        },
        {
          id:'1',
          name:'女'
        },
      ],
      defaultHeadUrl:base.picLocal+'/default/head/head.png'
    }
  },
  methods:{
    messageDetail(item){
      this.innerVisible = true;
      this.viewMessage = item;
      if(this.currentTab=='未读'){
        //将这条消息的状态变为已读
        let params = {
          id:item.id,
          state:1
        }
        this.$network.changeMessage(params).then(res=>{

        }).catch(err=>{
          console.log(err)
        })
      }
    },
    sizeChange(val){
      this.messagePage.pageSize = val;
      this.showMessage();
    },
    currentChange(val){
      this.messagePage.pageNo = val;
      this.showMessage();
    },
    dialogClose(){
      this.$refs.header.getMessageCount()
    },
    prevClick(){

    },
    nextClick(){

    },
    changeTab(tab){
      this.currentTab = tab.label;
      this.messagePage.pageNo=1;
      this.messagePage.pageSize=10;
      this.showMessage()
    },
    showMessage(){
      //向后端发送接口，查询信息
      this.messageListVisible = true;
      let params = {}
      if(this.currentTab=='未读'){
        params = {
          state:0,
          pageNo:this.messagePage.pageNo,
          pageSize:this.messagePage.pageSize
        }
      }else{
        params = {
          state:1,
          pageNo:this.messagePage.pageNo,
          pageSize:this.messagePage.pageSize
        }
      }
      //想后端发送请求查询消息
      this.$network.findMessage(params).then(res=>{
        this.messageList = res.data.data.list;
        this.messagePage.count = res.data.data.count;
      }).catch(err=>{
        console.log(err)
      })

    },
    showPersonInfo(id){
      this.personInfoVisible=true;
      //向后端发送请求，获取用户信息
      let params = {
        id:id
      }
      this.$network.getUser(params).then(res=>{
        this.person=res.data.data;
        this.person.role = this.person.role+"";
        this.person.password = "";
        this.person.headUrl = this.person.headUrl+"";
        if(this.person.headUrl==null){
          this.person.headUrl = '/default/head/head.png';
          this.orginPicUrl = this.person.headUrl;
        }else{
          this.orginPicUrl = this.person.headUrl;
        }
        // console.log(this.person)
        console.log(this.roleList)
      })
    },
    updateUser(formName){
      //验证表单合法性
      this.$refs[formName].validate((valid) => {
        if(valid){
          let roleStr = "";
          //向后端发送请求，修改用户
          this.roleList.forEach(i=>{
            if(i.id==this.person.role){
              roleStr = i.name;
            }
          })
          //判断用户的头像是否改变
          let headUrl = '';
          if(this.orginPicUrl != this.person.headUrl){
            headUrl = this.person.headUrl;
          }
          let params = {
            id:this.person.id,
            name:this.person.name,
            loginName:this.person.loginName,
            role:this.person.role,
            roleStr:roleStr,
            sex:this.person.sex,
            email:this.person.email,
            mobile:this.person.mobile,
            birthday:new Date(this.person.birthday),
            headUrl:headUrl
          }
          this.$network.updateUser(params).then(res=>{
            if(res.data.code=='200'){
              this.$message.success("修改成功！");
              this.personInfoVisible=false;
              //修改标题的信息
              this.$refs.header.getLoginData();
            }
          })
        }
      });
    },
    uploadSuccess(response, file, fileList){
      if(response.code=="200"){
        this.person.headUrl = response.data;
        fileList=[];
      }else{
        this.$message.error("上传失败！");
      }
    },
    beforeUpload(){
      if (localStorage.getItem('token')) {
        this.uploadHeaders.token = localStorage.getItem('token');
      }
      // console.log(localStorage.getItem('token'));

    },
  },
  beforeMount() {
    //从字典中取出角色信息
    let params = {
      type:'hrent_role_type'
    }
    this.$network.findAllJson(params).then(res=>{
      this.roleList = res.data.data
    }).catch(err=>{
      console.log(err)
    })
  },
  comments:{
    "Header":Header
  },
  mounted() {
    this.gd=window.innerHeight-60+"px";
  }
}
</script>

<style scoped>
.el-header{
  padding: 0;
}

.el-aside{
  background-color: rgb(54,52,51);
  margin: 0;
  padding: 0;
  border-right: solid 1px #e6e6e6;
  /*height: 100% !important;*/
}

.el-footer{
  position: absolute;
  bottom: 0;
  margin-bottom: 0;
}
.el-container{
  height: 100% !important;
}
.el-image__inner{
  width: 200px;
  height: 200px;
}
.infoImg{
  width: 400px;
  height: 200px;
  display: flex;
  float: left;
}

.personInfo{
  display: flex;
}

.dialog-footer{
  margin: 20px 30px;
}

.messageList{
  height: 550px;
}

.messageTitle{
  font-size: 18px;
  font-family: 华文楷体;
  font-weight: bold;
}

.messageTime{
  font-size: 12px;
  text-align: right;
}

.allMessage{
  height: 500px;
  overflow: scroll;
  overflow-x: hidden !important;
}

.title{
  font-size: 18px;
  font-family: 华文楷体;
  font-weight: bold;
}

.time{
  font-size: 12px;
  text-align: right;
}

</style>
