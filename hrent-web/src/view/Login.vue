<template>
  <div>
    <el-card class="login-form-layout">
      <el-form
        autocomplete="on"
        :model="loginForm"
        ref="loginForm"
        label-position="left"
        v-if="option=='login'">
        <div style="text-align: center">
        </div>
        <h2 class="login-title color-main">房屋租赁管理系统登录</h2>
        <el-form-item prop="username">
          <el-input
            name="username"
            type="text"
            v-model="loginForm.username"
            autocomplete="on"
            placeholder="请输入用户名">
            <span slot="prefix">
              <i class="el-icon-user"></i>
            </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            name="password"
            :type="pwdType"
            @keyup.enter.native="handleLogin"
            v-model="loginForm.password"
            autocomplete="on"
            placeholder="请输入密码">
            <span slot="prefix">
              <i class="el-icon-lock"></i>
            </span>
            <span slot="suffix" @click="showPwd">
            </span>
          </el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 10px">
          <el-button
            style="width: 100%"
            type="primary"
            :loading="loading"
            @click.native.prevent="handleLogin"
          >登录</el-button>
        </el-form-item>
        <div class="funLink">
          <div class="forget">忘记密码？</div>
          <div class="newUser" @click="toRegister">新用户？</div>
        </div>
      </el-form>
      <el-form v-if="option=='register'" autocomplete="on" :model="registerForm" :rules="registerRuleForm" ref="registerForm" status-icon>
        <div class="registerTitle"><h2>注册用户</h2></div>
        <el-form-item prop="loginName">
          <el-input
            name="loginName"
            type="text"
            v-model="registerForm.loginName"
            autocomplete="on"
            placeholder="请输入登录名">
            <span slot="prefix">
              <i class="el-icon-s-check"></i>
            </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="registerUserName">
          <el-input
            name="registerUserName"
            type="text"
            v-model="registerForm.registerUserName"
            autocomplete="off"
            placeholder="请输入用户名">
            <span slot="prefix">
              <i class="el-icon-user"></i>
            </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="mobile">
          <el-input
            name="mobile"
            type="text"
            v-model.number="registerForm.mobile"
            maxlength="11"
            placeholder="请输入手机号">
            <span slot="prefix">
              <i class="el-icon-phone"></i>
            </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="registerPassword">
          <el-input
            name="registerPassword"
            type="password"
            v-model="registerForm.registerPassword"
            autocomplete="off"
            placeholder="请输入密码">
            <span slot="prefix">
              <i class="el-icon-lock"></i>
            </span>
          </el-input>
        </el-form-item>
        <el-form-item prop="passwordSure">
          <el-input
            name="passwordSure"
            type="password"
            v-model="registerForm.passwordSure"
            autocomplete="off"
            placeholder="请再次输入密码">
            <span slot="prefix">
              <i class="el-icon-lock"></i>
            </span>
          </el-input>
        </el-form-item>
        <div class="funBtn">
          <el-button type="primary" size="mini" @click="registerUser('registerForm')">注册</el-button>
          <el-button type="primary" size="mini" @click="backLogin">返回</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import {base} from "../config";

export default {
  name: "Login",
  data() {
    var validateLoginName = (rule,value,callback)=>{
      if(value===''){
        callback(new Error('请输入登录名'))
      }else{
        //向后端发送请求，查询是否有这个登录名
        let params = {
          loginName:value
        }
        this.$network.findLoginName(params).then(res=>{
          if(res.data.code=='500'){
            callback(new Error(res.data.message))
          }else{
            callback();
          }
        }).catch(err=>{
          console.log(err)
        })
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.registerForm.registerPassword !== '') {
          this.$refs.registerForm.validateField('passwordSure');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerForm.registerPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      option:'login',
      picLocal:base.picLocal,
      logoUrl:"/default/system/loginLogo.png",
      loginForm: {
        username: "admin",
        password: "123456"
      },
      loading: false,
      pwdType: "password",
      registerForm:{
        loginName:'',
        registerUserName:'',
        mobile:'',
        registerPassword:'',
        passwordSure:'',
      },
      registerRuleForm:{
        passwordSure:[
          {validator:validatePass2, trigger: 'blur' }
        ],
        loginName:[
          {validator:validateLoginName, trigger:'blur'}
        ],
        mobile:[
          {required: true, message: "请输入电话号码", trigger:'blur'},
          {required: true, pattern:/^1\d{10}$/, message: "电话号码为11位",trigger: 'blur'}
        ],
        registerUserName:[
          {required:true, message:"请输入用户名", trigger:'blur'}
        ],
        registerPassword:[
          {validator:validatePass, trigger: 'blur' }
        ],

      }
    };
  },
  methods: {
    backLogin(){
      this.option='login';
    },
    //注册用户
    registerUser(formName){
      console.log(this.$refs.registerForm)
      this.$refs.registerForm.validate((valid)=>{
        if(valid){
          //判断输入的两次密码是否一致
          if(this.registerForm.registerPassword!=this.registerForm.passwordSure){
            this.$message.error("两次输入的密码不一致");
          }
          let params = {
            loginName:this.registerForm.loginName,
            password:this.registerForm.registerPassword,
            mobile:this.registerForm.mobile,
            name:this.registerForm.registerUserName
          }
          //调用后台接口，新增用户
          this.$network.register(params).then(res=>{
            if(res.data.code=='200'){
              this.$message.success("注册成功")
              this.option='login';
            }
          })
        }else{
          return false
        }
      })
    },
    //显示注册表单
    toRegister(){
      this.option='register';
    },
    showPwd() {
      if (this.pwdType === "password") {
        this.pwdType = "";
      } else {
        this.pwdType = "password";
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          let params = {
            loginName:this.loginForm.username,
            password:this.loginForm.password
          }
          this.$network.login(params)
            .then(res => {
              this.loading = false;
              let code = res.data.code;
              if(code == "200"){
                this.$router.push({
                  path:'showData',
                })
                localStorage.setItem("token",res.data.data);
                //访问后台，获取登录用户信息
                //向后端发送请求，获取登录数据
                this.$network.getLoginData().then(res=>{
                  if(res.data.code==200){
                    localStorage.setItem("loginData",JSON.stringify(res.data.data));
                  }else{
                    this.$message.error("获取登录用户信息失败")
                  }
                }).catch(error=>{
                  console.log(error);
                })
              }else{
                this.$message(res.data.message)
              }
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          // eslint-disable-next-line no-console
          console.log("参数验证不合法！");
          return false;
        }
      });
    }
  },
  beforeMount() {
    //在加载之前首先访问后台，获取图片访问地址
    this.$network.getBaseInfo().then(res=>{
      if(res.data.code=='200'){

      }
    })
  }
};
</script>

<style scoped>

.funLink{
  display: flex;
  justify-content: space-around;
}

.funLink>div{
  font-size: 10px;
  font-family: "Tw Cen MT Condensed";
  color: #9C9C9C;
}

h2{
  margin-bottom: 50px;
}

.login-form-layout {
  position: absolute;
  left: 0;
  right: 0;
  width: 360px;
  margin: 140px auto;
  border-top: 10px solid #409eff;
}

.login-title {
  text-align: center;
}

.login-center-layout {
  background: #409eff;
  width: auto;
  height: auto;
  max-width: 100%;
  max-height: 100%;
  margin-top: 200px;
}
</style>
