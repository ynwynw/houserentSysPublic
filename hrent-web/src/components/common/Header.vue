<template>
  <div class="Header">
    <el-image
      style="width: 50px; height: 50px;float: left"
      :src="picLocal + sysLogoUrl"
      class="sysLogo"
    ></el-image>
    <div class="sysName">{{ sysName }}</div>

    <el-dropdown class="user-name" trigger="click" @command="handleCommand">
      <span class="el-dropdown-link">
        {{ userName }} <i class="el-icon-caret-bottom"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <a target="_blank">
          <el-dropdown-item @click.native="showPersonInfo"
            >个人信息</el-dropdown-item
          >
        </a>
        <!-- <a href="" target="_blank">
          <el-dropdown-item></el-dropdown-item>
        </a> -->
        <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

    <div class="userImg">
      <div>
        <el-avatar :src="userImg"></el-avatar>
      </div>
    </div>
    <!--消息中心-->
    <div class="btn-bell">
      <el-badge
        :value="message"
        :max="99"
        class="item"
        @click.native="showMessage"
      >
        <el-tooltip
          effect="dark"
          :content="message ? `有${message}条未读消息` : `消息中心`"
          placement="bottom"
        >
          <i class="el-icon-bell"></i>
        </el-tooltip>
      </el-badge>
      <!--      <span class="btn-bell-badge" v-if="message"></span>-->
    </div>
  </div>
</template>

<script>
import { base } from "../../config";

export default {
  // 组件的名称
  title: "",
  name: "Header",
  data() {
    return {
      picLocal: base.picLocal,
      sysName: "房屋租赁管理系统",
      sysLogoUrl: "/agency-1.png",
      message: 0,
      userName: "小城",
      userId: "",
      personInfoVisible: false,
      messageListVisible: true,
      userImg:
        "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
    };
  },
  methods: {
    handleCommand(command) {
      if (command == "loginout") {
        localStorage.removeItem("loginData");
        localStorage.removeItem("token");
        this.$router.push("/login");
      }
    },
    showPersonInfo() {
      this.personInfoVisible = !this.personInfoVisible;
      this.$emit("showPersonInfo", this.userId);
    },
    showMessage() {
      this.messageListVisible = !this.messageListVisible;
      this.$emit("showMessage");
    },
    //重新获取数据
    getLoginData() {
      //向后端发送请求，获取登录数据
      this.$network
        .getLoginData()
        .then(res => {
          if (res.data.code == 200) {
            localStorage.setItem("loginData", JSON.stringify(res.data.data));
          } else {
            this.$message.error("获取登录用户信息失败");
          }
          let loginUser = JSON.parse(localStorage.getItem("loginData"));
          this.userName = loginUser.name;
          this.userImg =
            loginUser.headUrl == null
              ? "http://localhost:8888/hrent/default/head/head.png"
              : this.picLocal + loginUser.headUrl;
          this.userId = loginUser.id;
        })
        .catch(error => {
          console.log(error);
        });
    },
    getMessageCount() {
      this.$network
        .getNewCount()
        .then(res => {
          this.message = res.data.data;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    //登录之后从localStorage中获取数据
  },
  mounted() {
    //向后端发送请求，获取登录用户的数据
    let params = {};
    this.$network
      .getLoginData(params)
      .then(res => {
        if (res.data.code == 200) {
          localStorage.setItem("loginData", JSON.stringify(res.data.data));
        } else {
          this.$message.error("获取登录用户信息失败");
        }
        let loginUser = JSON.parse(localStorage.getItem("loginData"));
        this.userName = loginUser.name;
        this.userImg =
          loginUser.headUrl == null
            ? "http://localhost:8888/hrent/default/head/head.png"
            : this.picLocal + loginUser.headUrl;
        this.userId = loginUser.id;
      })
      .catch(error => {
        console.log(error);
      });
    //每隔一分钟调用一次getMessageCount()方法，获取新消息数量
    this.getMessageCount();
    window.setInterval(() => {
      setTimeout(() => {
        this.getMessageCount();
      });
    }, 1000 * 60);
  }
};
</script>

<style scoped>
.Header {
  width: 100%;
  height: 60px;
  background-color: rgb(54, 52, 51);
}

.sysName {
  float: left;
  color: white;
  line-height: 60px;
  font-size: 25px;
}

.sysLogo {
  line-height: 70px;
  margin: 5px;
}

.userImg {
  float: right;
  margin: 10px 20px 10px 10px;
}

.user-name {
  height: 50px;
  font-size: 15px;
  color: white;
  line-height: 70px;
  float: right;
  margin-right: 30px;
}

.btn-bell {
  position: relative;
  width: 50px;
  height: 50px;
  text-align: center;
  border-radius: 15px;
  cursor: pointer;
  float: right;
  margin: 10px;
}

.btn-bell-badge {
  position: absolute;
  right: 0;
  top: -2px;
  width: 8px;
  height: 8px;
  border-radius: 4px;
  background: #f56c6c;
  color: #fff;
}

.btn-bell .el-icon-bell {
  color: #fff;
  font-size: 25px;
  margin: 10px;
}
</style>
