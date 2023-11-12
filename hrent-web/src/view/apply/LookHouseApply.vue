<template>
  <div class="lookHouseApply">
    <el-tabs type="card" @edit="handleTabsEdit" v-model="tableTabsValue">
      <el-tab-pane label="看房申请列表" name="lookHouseApplyList">
        <el-form
          :inline="true"
          :model="findLookApply"
          class="findLookApply"
          size="mini"
        >
          <el-form-item label="申请时间">
            <el-date-picker
              v-model="findLookApply.applyDate"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="检索输入" label-width="100px">
            <el-input
              v-model="findLookApply.condition"
              clearable
              placeholder="请输入房源Id或者地址"
            ></el-input>
          </el-form-item>
          <el-button
            type="primary"
            size="mini"
            @click="findLookApplyByCondition"
            >查询</el-button
          >
        </el-form>
        <!--申请列表-->
        <div class="applyList">
          <el-table
            :data="applyList"
            :header-cell-style="{
              'background-color': '#E8E8E8',
              height: '3.56vw',
              padding: '0',
              border: 'none',
              'font-size': '0.7vw',
              color: '#666666'
            }"
            height="600"
            :row-class-name="tableRowClassName"
            style="width: 100%;overflow: scroll;overflow-x: hidden !important;"
          >
            <el-table-column type="index" label="序号"></el-table-column>
            <el-table-column
              prop="applyUserId"
              label="申请人id"
              sortable
              width="150px"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              prop="houseId"
              label="房源id"
              width="150px"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              prop="viewTime"
              label="看房时间"
              sortable
            ></el-table-column>
            <el-table-column
              prop="viewTimeRe"
              label="重新选择的时间"
              sortable
            ></el-table-column>
            <el-table-column prop="state" label="状态" sortable>
              <template v-slot="scope">
                <el-tag v-if="scope.row.state == 0">申请中</el-tag>
                <el-tag v-if="scope.row.state == 1" type="success"
                  >申请通过</el-tag
                >
                <el-tag v-if="scope.row.state == 2">申请另选时间</el-tag>
                <el-tag v-if="scope.row.state == 3" type="danger"
                  >申请拒绝</el-tag
                >
                <el-tag v-if="scope.row.state == 4" type="danger"
                  >申请另选时间拒绝</el-tag
                >
                <el-tag v-if="scope.row.state == 5" type="info">已结束</el-tag>
                <el-tag v-if="scope.row.state == 6" type="danger"
                  >申请另选时间通过</el-tag
                >
              </template>
            </el-table-column>
            <el-table-column
              prop="createDate"
              label="创建时间"
              sortable
            ></el-table-column>
            <el-table-column label="操作" width="130px">
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
                  @click="deleteApply(scope.row.id)"
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
            :total="lookApplyPage.count"
          ></el-pagination>
        </div>
      </el-tab-pane>
      <el-tab-pane
        v-for="item in tabList"
        :label="item.title"
        :name="item.name"
        :key="item.name"
        closable
      >
        <div class="applyDetail">
          <el-carousel :interval="4000" type="card" height="400px">
            <el-carousel-item
              v-for="item in handleApply.houseResource.picList"
              :key="item.id"
            >
              <img :src="picLocal + item.url" width="400" height="320" />
            </el-carousel-item>
          </el-carousel>
          <div class="house">
            <div class="houseInfoTitle"><span>房屋信息</span></div>
            <div>
              <span class="houseMoney">{{
                handleApply.houseResource.money
              }}</span
              >元/月
            </div>
            <el-divider></el-divider>
            <div class="houseInfoContent">
              <el-row :gutter="30">
                <el-col :span="6"
                  >所在地区：{{
                    handleApply.houseResource.province +
                      handleApply.houseResource.city +
                      handleApply.houseResource.county
                  }}</el-col
                >
                <el-col :span="6"
                  >地址：{{ handleApply.houseResource.address }}</el-col
                >
                <el-col :span="6"
                  >面积：{{ handleApply.houseResource.area }}平方米</el-col
                >
                <el-col :span="6"
                  >户型：{{ handleApply.houseResource.houseType }}</el-col
                >
              </el-row>
              <el-row :gutter="30">
                <el-col :span="6"
                  >层数：{{ handleApply.houseResource.floor }}</el-col
                >
                <el-col :span="6"
                  >修建时间：{{ handleApply.houseResource.buildTime }}</el-col
                >
                <el-col :span="6"></el-col>
                <el-col :span="6"></el-col>
              </el-row>
            </div>
            <el-divider></el-divider>
            <div class="houseInfoDescribe">
              <div class="describeTitle">
                <span>房源描述</span>
              </div>
              <div class="describeContent">
                <span>{{ handleApply.houseResource.describe }}</span>
              </div>
            </div>
          </div>
          <el-divider></el-divider>
          <div class="apply">
            <div class="applyTitle">申请详情</div>
            <el-divider></el-divider>
            <div class="applyFirst">
              <div class="applyUser">
                <div class="applyUserTitle">申请人</div>
                <div class="applyUserContent">
                  <el-card :body-style="{ padding: '0px' }" class="userCard">
                    <div class="infoOut">
                      <!-- <img
                        :src="baseUrl + applyUser.headUrl"
                        class="userHead"
                      /> -->
                      <div class="infoDetail">
                        <el-row :gutter="20" style="margin-top: 10px">
                          <el-col :span="12"
                            ><div class="userName">
                              {{ applyUser.name }}
                            </div></el-col
                          >
                          <el-col :span="12"
                            ><div>
                              {{ applyUser.sex == 0 ? "男" : "女" }}
                            </div></el-col
                          >
                        </el-row>
                        <div class="birthday">{{ applyUser.birthday }}</div>
                        <div class="mobile">手机号：{{ applyUser.mobile }}</div>
                        <div class="email" v-if="applyUser.email">
                          邮箱：{{ applyUser.email }}
                        </div>
                      </div>
                    </div>
                  </el-card>
                </div>
              </div>
              <div class="detail">
                <div class="detailTitle">详情信息</div>
                <div class="detailContent">
                  <el-card
                    :body-style="{ padding: '0px' }"
                    class="applyDetailCard"
                    style="text-align: left"
                  >
                    <div style="margin-left: 20px;margin-top: 10px">
                      创建时间：{{ handleApply.createDate }}
                    </div>
                    <div style="margin-left: 20px;margin-top: 10px">
                      预约看房时间：{{ handleApply.viewTime }}
                    </div>
                    <div
                      style="margin-left: 20px;margin-top: 10px"
                      v-if="handleApply.viewTimeRe"
                    >
                      重新预约时间：{{ handleApply.viewTimeRe }}
                    </div>
                    <div>
                      <el-tag v-if="handleApply.state == 0" class="tag"
                        >申请中</el-tag
                      >
                      <el-tag
                        v-if="handleApply.state == 1"
                        type="success"
                        class="tag"
                        >申请通过</el-tag
                      >
                      <el-tag v-if="handleApply.state == 2" class="tag"
                        >申请另选时间</el-tag
                      >
                      <el-tag
                        v-if="handleApply.state == 3"
                        type="danger"
                        class="tag"
                        >申请拒绝</el-tag
                      >
                      <el-tag
                        v-if="handleApply.state == 4"
                        type="danger"
                        class="tag"
                        >申请另选时间拒绝</el-tag
                      >
                      <el-tag
                        v-if="handleApply.state == 5"
                        type="info"
                        class="tag"
                        >已结束</el-tag
                      >
                      <el-tag
                        v-if="handleApply.state == 6"
                        type="success"
                        class="tag"
                        >申请另选时间通过</el-tag
                      >
                    </div>
                    <div class="funBtn">
                      <el-button
                        type="primary"
                        v-if="handleApply.state == 0"
                        size="mini"
                        @click="adoptApply"
                        >通过申请</el-button
                      >
                      <el-button
                        type="primary"
                        v-if="handleApply.state == 0"
                        size="mini"
                        @click="chooseOtherTime"
                        >另选时间</el-button
                      >
                      <el-button
                        type="primary"
                        v-if="handleApply.state == 0"
                        size="mini"
                        @click="refuseApply"
                        >拒绝申请</el-button
                      >
                    </div>
                  </el-card>
                </div>
              </div>
            </div>
          </div>
          <el-dialog title="选择时间" :visible.sync="chooseTimeVisible">
            <el-form :model="chooseTimeForm">
              <el-form-item label="再次选择的日期">
                <el-date-picker
                  v-model="chooseTimeForm.date"
                  type="date"
                  placeholder="再次选择的日期"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd "
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="再次选择的时间">
                <el-time-select
                  v-model="chooseTimeForm.time"
                  :picker-options="{
                    start: '09:00',
                    step: '00:15',
                    end: '20:30'
                  }"
                  placeholder="再次选择的时间"
                >
                </el-time-select>
              </el-form-item>

              <el-form-item label="原因">
                <el-input
                  v-model="chooseTimeForm.remake"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="chooseTimeVisible = false">取 消</el-button>
              <el-button type="primary" @click="submitChoose">确 定</el-button>
            </div>
          </el-dialog>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { base } from "../../config";
import { baseMessage } from "../../network/message";

export default {
  name: "LookHouseApply",
  data() {
    return {
      baseUrl: base.picLocal,
      dialogVisible: false,
      picLocal: base.picLocal,
      tableTabsValue: "lookHouseApplyList",
      tabList: [],
      findLookApply: {
        applyDate: "",
        condition: ""
      },
      lookApplyPage: {
        pageNo: 1,
        pageSize: 10,
        count: 0
      },
      applyList: [],
      handleApply: {},
      applyUser: {},
      chooseTimeVisible: false,
      chooseTimeForm: {
        remake: "",
        date: "",
        time: "",
        viewTimeRe: ""
      }
    };
  },
  methods: {
    sizeChange(val) {
      this.lookApplyPage.pageSize = val;
      this.findLookApplyByCondition();
    },
    currentChange(val) {
      this.lookApplyPage.pageNo = val;
      this.findLookApplyByCondition();
    },
    prevClick(val) {},
    nextClick(val) {},
    handleTabsEdit(targetName, action) {
      if (action === "remove") {
        this.tabList = [];
        this.tableTabsValue = "lookHouseApplyList";
        this.findLookApplyByCondition();
      }
    },
    findLookApplyByCondition() {
      //参数
      let params = {
        pageNo: this.lookApplyPage.pageNo,
        pageSize: this.lookApplyPage.pageSize,
        condition: this.findLookApply.condition
      };
      if (
        this.findLookApply.applyDate != null &&
        this.findLookApply.applyDate != ""
      ) {
        params["startTime"] = this.findLookApply.applyDate[0];
        params["endTime"] = this.findLookApply.applyDate[1];
      }
      //向后端发送请求
      this.$network
        .findViewApplyPage(params)
        .then(res => {
          this.applyList = res.data.data.list;
          this.lookApplyPage.count = res.data.data.count;
        })
        .catch(err => {
          console.log(err);
        });
    },
    tableRowClassName({ row, rowIndex }) {
      let index = rowIndex;
      if (index % 2 == 0) {
        return "warning-row";
      } else {
        return "warning-col";
      }
    },
    edit(row) {
      this.tabList = [
        {
          title: "申请详情",
          name: "applyDetail"
        }
      ];
      this.tableTabsValue = "applyDetail";
      this.handleApply = row;
      //向后端发送请求，把申请人的信息查询出来
      let params = {
        id: row.applyUserId
      };
      this.$network
        .getUser(params)
        .then(res => {
          this.applyUser = res.data.data;
          //如果点击进行处理的人是房东，那么就将状态变为已结束,状态为3或4的申请
          if (row.state == 3 || row.state == 4) {
            if (
              row.houseResource.createBy.id ==
              JSON.parse(localStorage.getItem("loginData")).id
            ) {
              //如果两个用户相同，向后端发送信息，修改申请状态
              let params = {
                id: row.id,
                state: 5
              };
              this.$network
                .saveViewApply(params)
                .then(res => {})
                .catch(err => {
                  console.log(err);
                });
            }
          }
        })
        .catch(res => {
          console.log(res);
        });
    },
    deleteApply(id) {
      this.$confirm("确定删除这条申请记录吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(res => {
          //想后端发送请求，删除条申请记录
          let params = {
            id: id
          };
          this.$network.deleteViewApply(params).then(res => {
            if (res.data.code == "200") {
              this.$message({
                message: "删除成功",
                type: "success"
              });
              this.findLookApplyByCondition();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    //通过申请
    adoptApply() {
      this.$confirm("确定通过这条申请吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(res => {
          //向后端发送请求，修改这条信息的状态
          let params = {
            id: this.handleApply.id,
            state: 1
          };
          this.$network
            .saveViewApply(params)
            .then(res => {
              //向租户发送看房申请通过的消息
              let params = {
                receiverType: "0",
                receiverId: this.applyUser.id,
                title: "看房申请通过",
                text: baseMessage.viewApplyAdopt(),
                state: 0
              };
              //向后端发送请求，增加消息
              this.$network
                .addMessage(params)
                .then(res => {})
                .catch(err => {
                  console.log(err);
                });
              //退出标签页
              this.tableTabsValue = "lookHouseApplyList";
              this.tabList = [];
              //重新查询列表数据
              this.findLookApplyByCondition();
            })
            .catch(err => {
              console.log(err);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消操作"
          });
        });
    },
    //拒绝申请
    refuseApply() {
      this.$prompt("请输入拒绝理由（可不填）", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(value => {
          // console.log(value)
          //向后端发送请求，修改这条信息的状态
          let params = {
            id: this.handleApply.id,
            state: 3,
            remake: value.value
          };
          this.$network
            .saveViewApply(params)
            .then(res => {
              //向租户发送看房申请未通过的消息
              let params = {
                receiverType: "0",
                receiverId: this.applyUser.id,
                title: "看房申请未通过",
                text: baseMessage.viewApplyNotAdopt(),
                state: 0
              };
              //向后端发送请求，增加消息
              this.$network
                .addMessage(params)
                .then(res => {})
                .catch(err => {
                  console.log(err);
                });
              //退出标签页
              this.tableTabsValue = "lookHouseApplyList";
              this.tabList = [];
              //重新查询列表数据
              this.findLookApplyByCondition();
            })
            .catch(err => {
              console.log(err);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消操作"
          });
        });
    },
    //另选时间
    chooseOtherTime() {
      this.chooseTimeVisible = true;
    },
    submitChoose() {
      let params = {
        id: this.handleApply.id,
        state: 2,
        viewTimeRe: this.chooseTimeForm.date + this.chooseTimeForm.time,
        remake: this.chooseTimeForm.remake
      };
      //向后端发送请求，更改申请信息
      this.$network
        .saveViewApply(params)
        .then(res => {
          //向租户发送看房申请另选时间的消息
          let params = {
            receiverType: "0",
            receiverId: this.applyUser.id,
            title: "看房申请另选时间",
            text: baseMessage.viewApplyChangeTime(),
            state: 0
          };
          //向后端发送请求，增加消息
          this.$network
            .addMessage(params)
            .then(res => {})
            .catch(err => {
              console.log(err);
            });
        })
        .catch(err => {
          console.log(err);
        });
      //关闭dialog和标签页
      this.chooseTimeVisible = false;
      //退出标签页
      this.tableTabsValue = "lookHouseApplyList";
      this.tabList = [];
      //重新查询列表数据
      this.findLookApplyByCondition();
    }
  },
  beforeMount() {
    //在页面加载之前，从数据库取出数据
    this.findLookApplyByCondition();
  }
};
</script>

<style scoped>
.applyList {
  /*height: 700px;*/
  /*height: 65vh;*/
  /*overflow: scroll;*/
  /*overflow-x: hidden !important;*/
}

.applyDetail {
  margin: 0 auto;
  width: 1200px;
  height: 600px;
  overflow: scroll;
  overflow-x: hidden !important;
}

.houseInfoTitle {
  font-size: 22px;
  font-family: 黑体;
}
.houseInfoContent {
  width: 1000px;
}
.houseMoney {
  font-size: 30px;
  font-family: 新細明體-ExtB;
  color: red;
}
.el-col-6 {
  text-align: left;
  margin-top: 30px;
}
.houseInfoDescribe {
  width: 1200px;
}
.describeTitle {
  font-size: 18px;
  left: 0;
}
.describeContent {
  text-align: left;
}

.applyTitle {
  font-size: 18px;
}

.apply {
  width: 1200px;
}

.applyFirst {
  display: flex;
}

.applyUser {
  width: 600px;
}
.detail {
  width: 600px;
}
.applyUserTitle {
  font-size: 15px;
}
.detailTitle {
  font-size: 15px;
}
.infoOut {
  display: flex;
}

.userCard {
  width: 400px;
  margin: 0 auto;
}
.infoDetail {
  display: block;
  width: 100%;
}

.userName {
  font-size: 20px;
  font-family: 华文楷体;
}

.birthday {
  height: 40px;
}

.mobile {
  text-align: left;
  margin-left: 10px;
}
.email {
  text-align: left;
  margin-left: 10px;
}
.applyDetailCard {
  width: 400px;
  margin: 0 auto;
}
.funBtn {
  justify-content: space-between;
  margin-top: 10px;
  margin-left: 65px;
}
.tag {
  text-align: center;
  display: block;
  margin: 0px 100px;
  margin-top: 10px;
}
</style>
