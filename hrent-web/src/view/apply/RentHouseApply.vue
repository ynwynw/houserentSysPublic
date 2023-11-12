<template>
  <div class="rentApply">
    <el-tabs type="card" @edit="handleTabsEdit" v-model="tableTabsValue">
      <el-tab-pane label="租房申请列表" name="rentApplyList">
        <!--查询表单-->
        <el-form
          :inline="true"
          :model="findRentApply"
          class="findLookApply"
          size="mini"
        >
          <el-form-item label="申请时间">
            <el-date-picker
              v-model="findRentApply.applyDate"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="检索输入" label-width="100px">
            <el-input
              v-model="findRentApply.condition"
              clearable
              placeholder="请输入房源Id或者地址"
            ></el-input>
          </el-form-item>
          <el-button
            type="primary"
            size="mini"
            @click="findRentApplyByCondition"
            >查询</el-button
          >
        </el-form>
        <el-table
          :data="rentApplyList"
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
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            prop="houseId"
            label="房源id"
            show-overflow-tooltip
          ></el-table-column>
          <el-table-column
            prop="startTime"
            label="租房开始时间"
          ></el-table-column>
          <el-table-column prop="state" label="申请状态" sortable>
            <template v-slot="scope">
              <el-tag v-if="scope.row.state == 0">申请中</el-tag>
              <el-tag v-if="scope.row.state == 1" type="success"
                >申请通过</el-tag
              >
              <el-tag v-if="scope.row.state == 2" type="danger"
                >申请拒绝</el-tag
              >
              <el-tag v-if="scope.row.state == 3" type="success"
                >押金已支付</el-tag
              >
              <el-tag v-if="scope.row.state == 4" type="danger"
                >押金未支付</el-tag
              >
              <el-tag v-if="scope.row.state == 5" type="info">已结束</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            prop="deposit"
            label="押金"
            sortable
          ></el-table-column>
          <el-table-column prop="createBy" label="申请人">
            <template v-slot="scope">
              {{ scope.row.createBy == null ? "" : scope.row.createBy.name }}
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
          :total="rentApplyPage.count"
        ></el-pagination>
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
                        :src="picLocal + applyUser.headUrl"
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
                      <el-tag
                        v-if="handleApply.state == 2"
                        type="danger"
                        class="tag"
                        >申请拒绝</el-tag
                      >
                      <el-tag
                        v-if="handleApply.state == 3"
                        type="success"
                        class="tag"
                        >押金已支付</el-tag
                      >
                      <el-tag
                        v-if="handleApply.state == 4"
                        type="danger"
                        class="tag"
                        >押金未支付</el-tag
                      >
                      <el-tag
                        v-if="handleApply.state == 5"
                        type="info"
                        class="tag"
                        >已结束</el-tag
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
                        @click="refuseApply"
                        >拒绝申请</el-button
                      >
                    </div>
                  </el-card>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { base } from "../../config";
import { baseMessage } from "../../network/message";

export default {
  name: "RentHouseApply",
  data() {
    return {
      picLocal: base.picLocal,
      tableTabsValue: "rentApplyList",
      rentApplyList: [],
      rentApplyPage: {
        pageNo: 1,
        pageSize: 10,
        count: 0
      },
      findRentApply: {},
      tabList: [],
      handleApply: {},
      applyUser: {}
    };
  },
  methods: {
    sizeChange(val) {
      this.rentApplyPage.pageSize = val;
      this.findLookApplyByCondition();
    },
    currentChange(val) {
      this.rentApplyPage.pageNo = val;
      this.findRentApplyByCondition();
    },
    prevClick(val) {},
    nextClick(val) {},
    handleTabsEdit(targetName, action) {
      if (action === "remove") {
        this.tabList = [];
        this.tableTabsValue = "rentApplyList";
        this.findRentApplyByCondition();
      }
    },
    tableRowClassName({ row, rowIndex }) {
      let index = rowIndex;
      if (index % 2 == 0) {
        return "warning-row";
      } else {
        return "warning-col";
      }
    },
    findRentApplyByCondition() {
      let params = {
        pageNo: this.rentApplyPage.pageNo,
        pageSize: this.rentApplyPage.pageSize,
        condition: this.findRentApply.condition
      };
      if (
        this.findRentApply.applyDate != null &&
        this.findRentApply.applyDate != ""
      ) {
        params["startTime"] = this.findRentApply.applyDate[0];
        params["endTime"] = this.findRentApply.applyDate[1];
      }
      //向后端发送请求，查询数据
      this.$network
        .findRentApplyPage(params)
        .then(res => {
          this.rentApplyList = res.data.data.list;
          this.rentApplyPage.count = res.data.data.count;
        })
        .catch(err => {
          console.log(err);
        });
    },
    edit(row) {
      //修改数据
      this.tabList = [
        {
          title: "申请详情",
          name: "applyDetail"
        }
      ];
      this.tableTabsValue = "applyDetail";
      this.handleApply = row;
      //想后端发送请求，把申请人的信息查询出来
      let params = {
        id: row.applyUserId
      };
      this.$network
        .getUser(params)
        .then(res => {
          this.applyUser = res.data.data;
          //如果是状态为3或4，那么在房东点击过后将这条申请的状态变为已结束
          if (this.handleApply.state == 3 || this.handleApply.state == 4) {
            //如果是房东点击的，才将状态变为已结束，否则就不改变状态
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
                .saveRentApply(params)
                .then(res => {})
                .catch(err => {
                  console.log(err);
                });
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    deleteApply(id) {
      //删除数据
      this.$confirm("确定删除这条申请记录吗", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(res => {
          //向后端发送请求，删除这条申请记录
          let params = {
            id: id
          };
          this.$network.deleteRentApply(params).then(res => {
            if (res.data.code == "200") {
              this.$message({
                message: "删除成功",
                type: "success"
              });
              this.findRentApplyByCondition();
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
      this.$prompt("请输入押金（必填）", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        inputErrorMessage: "请输入正确地押金！",
        inputValidator: value => {
          if (!/(^[1-9]\d*$)/.test(value)) {
            return false;
          } else {
            return true;
          }
        }
      })
        .then(value => {
          if (value.value == "") {
            this.$message({
              message: "请输入押金",
              type: "error"
            });
          } else {
            let params = {
              id: this.handleApply.id,
              state: 1,
              deposit: value.value
            };
            this.$network
              .saveRentApply(params)
              .then(res => {
                if (res.data.code == "200") {
                  this.$message({
                    message: "操作成功",
                    type: "success"
                  });
                  //向租户发送租房申请通过的消息
                  let params = {
                    receiverType: "0",
                    receiverId: this.applyUser.id,
                    title: "租房申请通过",
                    text: baseMessage.rentApplyAdopt(),
                    state: 0
                  };
                  //向后端发送请求，增加消息
                  this.$network
                    .addMessage(params)
                    .then(res => {})
                    .catch(err => {
                      console.log(err);
                    });
                }
                //退出标签页
                this.tableTabsValue = "rentApplyList";
                this.tabList = [];
                //重新查询列表数据
                this.findRentApplyByCondition();
              })
              .catch(err => {
                console.log(err);
              });
          }
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
      this.$confirm("确定不通过这条申请吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        //向后端发送请求，修改这条信息的状态
        let params = {
          id: this.handleApply.id,
          state: 2
        };
        this.$network
          .saveRentApply(params)
          .then(res => {
            if (res.data.code == "200") {
              this.$message({
                message: "操作成功",
                type: "success"
              });
            }
            //向租户发送租房申请通过的消息
            let params = {
              receiverType: "0",
              receiverId: this.applyUser.id,
              title: "租房申请未通过",
              text: baseMessage.rentApplyNotAdopt(),
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
            this.tableTabsValue = "rentApplyList";
            this.tabList = [];
            //重新查询列表数据
            this.findRentApplyByCondition();
          })
          .catch(err => {
            console.log(err);
          });
      });
    }
  },
  beforeMount() {
    //加载初始数据
    this.findRentApplyByCondition();
  }
};
</script>

<style scoped>
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
  margin-left: 115px;
}
.tag {
  text-align: center;
  display: block;
  margin: 0px 100px;
  margin-top: 10px;
}
</style>
