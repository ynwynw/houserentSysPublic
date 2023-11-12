<template>
  <div class="userManage">
    <el-form :inline="true" :model="formInline" size="mini">
      <el-form-item label="角色:">
        <el-select v-model="formInline.role" placeholder="角色" clearable>
          <el-option
            v-for="(item, index) in roleList"
            :value="item.value"
            :label="item.label"
            :key="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="注册时间">
        <el-date-picker
          v-model="formInline.registerTime"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="查询条件" size="mini">
        <el-input
          v-model="formInline.condition"
          placeholder="用户名或邮箱电话"
          clearable
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="findUserByCondition" size="small "
          >查询</el-button
        >
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="openAddUserForm" size="small "
          >增加用户</el-button
        >
      </el-form-item>
    </el-form>
    <el-table
      :data="userList"
      :header-cell-style="{
        'background-color': '#E8E8E8',
        height: '3.56vw',
        padding: '0',
        border: 'none',
        'font-size': '0.7vw',
        color: '#666666'
      }"
      height="600"
      style="width: 100%;"
      stripe
    >
      <el-table-column type="index" fixed></el-table-column>
      <el-table-column
        prop="name"
        label="用户名"
        sortable
        width="120"
        fixed
      ></el-table-column>
      <el-table-column
        prop="loginName"
        label="登录名"
        width="100"
        fixed
      ></el-table-column>
      <el-table-column
        prop="roleStr"
        label="角色"
        width="120"
      ></el-table-column>
      <el-table-column prop="email" label="邮箱" width="170"></el-table-column>
      <el-table-column
        prop="mobile"
        label="手机号"
        width="150"
      ></el-table-column>
      <el-table-column label="头像" width="150">
        <template slot-scope="scope">
          <el-image
            :src="
              scope.row.headUrl == null
                ? baseUrl + defaultHeadUrl
                : scope.row.openId == null
                ? baseUrl + scope.row.headUrl
                : baseUrl + scope.row.headUrl
            "
            class="headUrl"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="50">
        <template slot-scope="scope">
          <span>{{ scope.row.sex == 0 ? "男" : "女" }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="birthday"
        label="出生日期"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="loginTime"
        label="上一次登录时间"
        width="150"
      ></el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
        width="150"
      ></el-table-column>
      <el-table-column label="操作" width="130px" fixed="right">
        <template v-slot="scope">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="edit(scope.row)"
          ></el-button>
          <el-button
            size="mini"
            type="warning"
            icon="el-icon-delete"
            @click="deleteUser(scope.row.id)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      @prev-click="prevClick"
      @next-click="nextClick"
      background
      layout="total,sizes,prev, pager, next"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="10"
      :total="userPage.count"
    ></el-pagination>
    <el-dialog title="添加用户" :visible.sync="addUserFormVisible" width="30%">
      <el-form
        :model="addUserForm"
        class="addUserForm"
        :rules="addUserRules"
        ref="addUserForm"
      >
        <el-form-item
          label="用户名："
          :label-width="formLabelWidth"
          prop="name"
        >
          <el-input
            v-model="addUserForm.name"
            autocomplete="off"
            maxLength="20"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="登录名："
          :label-width="formLabelWidth"
          prop="alias"
        >
          <el-input
            v-model="addUserForm.loginName"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="密码："
          :label-width="formLabelWidth"
          prop="password1"
        >
          <el-input
            type="password"
            v-model="addUserForm.password1"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="请再次输入密码："
          :label-width="formLabelWidth"
          prop="password2"
        >
          <el-input
            type="password"
            v-model="addUserForm.password2"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="角色：" :label-width="formLabelWidth" prop="role">
          <el-select v-model="addUserForm.role" placeholder="请选择角色">
            <el-option
              v-for="item in roleList"
              :value="item.value"
              :label="item.label"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别：" :label-width="formLabelWidth" prop="sex">
          <el-select v-model="addUserForm.sex" placeholder="请选择性别">
            <el-option
              v-for="item in sexList"
              :value="item.id"
              :label="item.name"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱：" :label-width="formLabelWidth" prop="email">
          <el-input v-model="addUserForm.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item
          label="手机号："
          :label-width="formLabelWidth"
          prop="mobile"
        >
          <el-input
            maxlength="11"
            v-model.number="addUserForm.mobile"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="出生日期"
          :label-width="formLabelWidth"
          prop="birthday"
          class="is-required"
        >
          <el-date-picker
            v-model="addUserForm.birthday"
            type="date"
            placeholder="选择出生日期"
          >
          </el-date-picker>
        </el-form-item>
        <div class="preUserImg">
          <div>
            <el-avatar
              :src="baseUrl + addUserForm.headUrl"
              size="large"
            ></el-avatar>
          </div>
        </div>
        <el-form-item label="头像：" label-width="100px">
          <el-upload
            class="upload-demo"
            drag
            :headers="uploadHeaders"
            :action="uploadHead"
            :on-success="uploadSuccess"
            :before-upload="beforeUpload"
            multiple
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addUserFormVisible = false" size="mini"
          >取 消</el-button
        >
        <el-button type="primary" @click="addUser('addUserForm')" size="mini"
          >确 定</el-button
        >
      </div>
    </el-dialog>
    <el-dialog
      title="修改用户"
      :visible.sync="updateUserFormVisible"
      width="30%"
    >
      <el-form
        :model="updateUserForm"
        class="addUserForm"
        :rules="updateUserRules"
        ref="updateUserForm"
      >
        <el-form-item
          label="用户名："
          :label-width="formLabelWidth"
          prop="name"
        >
          <el-input
            v-model="updateUserForm.name"
            autocomplete="off"
            maxLength="20"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="登录名："
          :label-width="formLabelWidth"
          prop="alias"
        >
          <el-input
            v-model="updateUserForm.loginName"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="角色：" :label-width="formLabelWidth" prop="role">
          <el-select v-model="updateUserForm.role" placeholder="请选择角色">
            <el-option
              v-for="item in roleList"
              :value="item.value"
              :label="item.label"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别：" :label-width="formLabelWidth" prop="sex">
          <el-select v-model="updateUserForm.sex" placeholder="请选择性别">
            <el-option
              v-for="item in sexList"
              :value="item.id"
              :label="item.name"
              :key="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱：" :label-width="formLabelWidth" prop="email">
          <el-input
            v-model="updateUserForm.email"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="手机号："
          :label-width="formLabelWidth"
          prop="mobile"
        >
          <el-input
            maxlength="11"
            v-model.number="updateUserForm.mobile"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="出生日期"
          :label-width="formLabelWidth"
          prop="birthday"
          class="is-required"
        >
          <el-date-picker
            v-model="updateUserForm.birthday"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出生日期"
          >
          </el-date-picker>
        </el-form-item>
        <div class="preUserImg">
          <div>
            <el-avatar
              :src="baseUrl + updateUserForm.headUrl"
              size="large"
            ></el-avatar>
          </div>
        </div>
        <el-form-item label="头像：" label-width="100px">
          <el-upload
            class="upload-demo"
            drag
            :headers="uploadHeaders"
            :action="uploadHead"
            :on-success="uploadSuccess"
            :before-upload="beforeUpload"
            multiple
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updateUserFormVisible = false" size="mini"
          >取 消</el-button
        >
        <el-button
          type="primary"
          @click="updateUser('updateUserForm')"
          size="mini"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { base } from "../config";

export default {
  name: "UserManage",
  data() {
    return {
      baseUrl: base.picLocal,
      uploadHeaders: {},
      addUserFormVisible: false,
      updateUserFormVisible: false,
      formLabelWidth: "200px",
      uploadHead: base.baseUrl + "/user/uploadHead",
      addUserRules: {
        name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        loginName: [
          { required: true, message: "请输入登录名", trigger: "blur" }
        ],
        password1: [{ required: true, message: "请输入密码", trigger: "blur" }],
        password2: [
          { required: true, message: "请再次输入密码", trigger: "blur" }
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            required: true,
            pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
            message: "请输入正确格式的邮箱",
            trigger: "blur"
          }
        ],
        roleId: [{ required: true, message: "请选择角色", trigger: "change" }],
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        mobile: [
          { required: true, message: "请输入电话号码", trigger: "blur" },
          {
            required: true,
            pattern: /^1\d{10}$/,
            message: "电话号码为11位",
            trigger: "blur"
          }
        ],
        birthday: [
          {
            type: "date",
            require: true,
            message: "请选择出生日期",
            trigger: "change"
          }
        ]
      },
      updateUserRules: {
        name: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        loginName: [
          { required: true, message: "请输入登录名", trigger: "blur" }
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            required: true,
            pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
            message: "请输入正确格式的邮箱",
            trigger: "blur"
          }
        ],
        roleId: [{ required: true, message: "请选择角色", trigger: "change" }],
        sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        mobile: [
          { required: true, message: "请输入电话号码", trigger: "blur" },
          {
            required: true,
            pattern: /^1\d{10}$/,
            message: "电话号码为11位",
            trigger: "blur"
          }
        ],
        birthday: [
          { require: true, message: "请选择出生日期", trigger: "change" }
        ]
      },
      addUserForm: {
        name: "",
        loginName: "",
        password1: "",
        password2: "",
        role: "",
        sex: "",
        email: "",
        mobile: "",
        birthday: "",
        headUrl: "/default/head/head.png"
      },
      updateUserForm: {
        name: "",
        loginName: "",
        role: "",
        sex: "",
        email: "",
        mobile: "",
        birthday: "",
        headUrl: "/default/head/head.png"
      },
      sexList: [
        {
          id: "0",
          name: "男"
        },
        {
          id: "1",
          name: "女"
        }
      ],
      defaultHeadUrl: "/default/head/head.png",
      roleList: [
        {
          id: "1",
          name: "租户"
        },
        {
          id: "2",
          name: "房东"
        },
        {
          id: "3",
          name: "管理员"
        },
        {
          id: "4",
          name: "超级管理员"
        }
      ],
      formInline: {
        condition: "",
        role: "",
        date1: null,
        date2: null,
        registerTime: ""
      },
      userPage: {
        pageNo: 1,
        pageSize: 10,
        count: 0
      },
      userList: []
    };
  },
  methods: {
    addUser(formName) {
      //验证表单合法性
      console.log(this.$refs[formName]);
      this.$refs[formName].validate(valid => {
        if (valid) {
          let roleStr = "";
          //向后端发送请求，增加用户
          this.roleList.forEach(i => {
            if (i.value == this.addUserForm.role) {
              roleStr = i.label;
            }
          });
          let params = {
            name: this.addUserForm.name,
            loginName: this.addUserForm.loginName,
            password: this.addUserForm.password2,
            role: this.addUserForm.role,
            roleStr: roleStr,
            sex: this.addUserForm.sex,
            email: this.addUserForm.email,
            mobile: this.addUserForm.mobile,
            birthday: this.addUserForm.birthday,
            headUrl:
              this.addUserForm.headUrl == this.defaultHeadUrl
                ? ""
                : this.addUserForm.headUrl
          };
          this.$network.addUser(params).then(res => {
            if (res.data.code == "200") {
              this.$message.success("添加用户成功！");
              this.findUserByPage();
              this.addUserFormVisible = false;
            }
          });
        }
      });
    },
    uploadSuccess(response, file, fileList) {
      if (response.code == "200") {
        if (this.addUserFormVisible) {
          this.addUserForm.headUrl = response.data;
        } else {
          this.updateUserForm.headUrl = response.data;
        }
      } else {
        this.$message.error("上传失败！");
      }
    },
    deleteUser(id) {
      this.$confirm("确定删除用户吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          //向后端发送请求，删除用户
          let params = {
            id: id
          };
          this.$network.deleteUser(params).then(res => {
            if (res.data.code == "200") {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
            }
            this.onSubmit();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    edit(row) {
      if (row.headUrl == null) {
        row.headUrl = this.defaultHeadUrl;
      }
      console.log(row);
      row.role = row.role + "";
      this.updateUserForm = row;
      this.updateUserFormVisible = true;
    },
    updateUser(formName) {
      //验证表单合法性
      this.$refs[formName].validate(valid => {
        if (valid) {
          let roleStr = "";
          //向后端发送请求，增加用户
          this.roleList.forEach(i => {
            if (i.value == this.updateUserForm.role) {
              roleStr = i.label;
            }
          });
          let params = {
            id: this.updateUserForm.id,
            name: this.updateUserForm.name,
            loginName: this.updateUserForm.loginName,
            // password:this.updateUserForm.password2,
            role: this.updateUserForm.role,
            roleStr: roleStr,
            sex: this.updateUserForm.sex,
            email: this.updateUserForm.email,
            mobile: this.updateUserForm.mobile,
            birthday: new Date(this.updateUserForm.birthday),
            headUrl:
              this.updateUserForm.headUrl == this.defaultHeadUrl
                ? ""
                : this.updateUserForm.headUrl
          };
          console.log(params);
          this.$network.updateUser(params).then(res => {
            if (res.data.code == "200") {
              this.$message.success("修改用户成功！");
              this.findUserByPage();
              this.updateUserFormVisible = false;
            }
          });
        }
      });
    },
    sizeChange(val) {
      // let _this = this;
      // let params = {
      //   pageNo: 1,
      //   pageSize: val
      // }
      // this.$network.findUserPage(params).then(res=>{
      //   _this.userList = res.data.data.list
      //   _this.userPage.count = res.data.data.count
      // }).catch(error=>{
      //   console.log(error);
      // })
    },
    currentChange(val) {
      console.log(`${val}`);
    },
    prevClick(val) {
      console.log(`${val}`);
    },
    nextClick(val) {
      console.log(`${val}`);
    },
    openAddUserForm() {
      this.addUserFormVisible = true;
    },
    beforeUpload() {
      if (localStorage.getItem("token")) {
        this.uploadHeaders.token = localStorage.getItem("token");
      }
      console.log(localStorage.getItem("token"));
    },
    findUserByPage() {
      this.findUserByCondition();
    },
    findUserByCondition() {
      //将查询参数封装为对象
      let params = {};
      if (
        this.formInline.registerTime != null &&
        this.formInline.registerTime != ""
      ) {
        this.formInline.date1 = this.formInline.registerTime[0];
        this.formInline.date2 = this.formInline.registerTime[1];
        params = {
          pageNo: this.userPage.pageNo,
          pageSize: this.userPage.pageSize,
          condition: this.formInline.condition,
          role: this.formInline.role,
          startTime: this.formInline.date1,
          endTime: this.formInline.date2
        };
      } else {
        params = {
          pageNo: this.userPage.pageNo,
          pageSize: this.userPage.pageSize,
          condition: this.formInline.condition,
          role: this.formInline.role
        };
      }
      //向后端发送请求
      this.$network.findUserPage(params).then(res => {
        this.userList = res.data.data.list;
        this.userPage.count = res.data.data.count;
      });
    }
  },
  beforeMount() {
    this.findUserByCondition();
    //通过字典将用户全部查找出来
    //从字典中取出角色信息
    let params = {
      type: "hrent_role_type"
    };
    this.$network
      .findAllJson(params)
      .then(res => {
        this.roleList = res.data.data;
      })
      .catch(err => {
        console.log(err);
      });
  }
};
</script>

<style scoped>
.preUserImg {
  float: left;
  margin: 10px 20px 10px 10px;
  line-height: 200px;
}
.headUrl {
  width: 50px;
  height: 50px;
}
.upload-demo {
  height: 100px;
  width: 200px;
}
.el-upload-dragger {
  height: 150px;
}
</style>
