<template>
  <div class="showData">
    <el-row :span="24">
      <div class="houseBaseTitle" style="background: #e5e9f2;height: 40px">
        房源基本信息
      </div>
    </el-row>
    <el-row class="houseBase">
      <el-col :span="8">
        <div id="houseAddress" style="width: 480px;height: 360px"></div>
      </el-col>
      <el-col :span="8">
        <div id="housePrice" style="width: 480px;height: 360px"></div>
      </el-col>
      <el-col :span="8">
        <div id="houseSize" style="width: 480px;height: 360px"></div>
      </el-col>
    </el-row>
    <el-row :span="24">
      <div class="houseBaseTitle" style="background: #e5e9f2;height: 40px">
        交易基本信息
      </div>
    </el-row>
    <el-row class="tradeInfo">
      <el-col :span="8">
        <div id="tradeTimeColumn" style="width: 480px;height: 360px"></div>
      </el-col>
      <el-col :span="8">
        <div id="tradeTimeLine" style="width: 480px;height: 360px"></div>
      </el-col>
      <el-col :span="8">
        <div id="tradeTime"></div>
      </el-col>
    </el-row>
    <!-- <el-row :span="24">
      <div class="houseBaseTitle" style="background: #e5e9f2;height: 40px">
        交易量与房源数据的关系
      </div>
    </el-row> -->
    <!-- <el-row class="tradeAndHouse"></el-row> -->
  </div>
</template>

<script>
import * as echarts from "echarts";
export default {
  name: "ShowData",
  data() {
    return {
      dataCount: {
        areaResultList: [],
        priceResultList: [],
        addResultList: []
      }
    };
  },
  methods: {
    //获取各种数据量
    getDataCount() {
      this.$network.getDataCount().then(res => {
        // this.dataCount.addResultList = JSON.stringify(res.data.addResultList);
        // this.dataCount.priceResultList = res.data.priceResultList;
        // this.dataCount.areaResultList = res.data.areaResultList;
        this.dataCount = res.data.data;
        this.houseAddress();
        this.housePrice();
        this.houseSize();
        this.tradeTimeColumn();
        this.tradeTimeLine();
      });
    },
    //房源地址数据
    houseAddress() {
      var houseAddress = echarts.init(document.getElementById("houseAddress"));
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "房源地址占比"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
          {
            name: "房源地址占比",
            type: "pie",
            radius: "50%",
            data: [
              { value: 10, name: "武侯区" },
              { value: 10, name: "高新区" },
              { value: 2, name: "锦江区" },
              { value: 2, name: "金牛区" },
              { value: 1, name: "成华区" },
              { value: 3, name: "龙泉驿区" },
              { value: 7, name: "新都区" },
              { value: 6, name: "郫都区" },
              { value: 3, name: "温江区" },
              { value: 5, name: "双流区" },
              { value: 6, name: "青白江区" },
              { value: 1, name: "天府新区" }
            ],
            data: this.dataCount.addResultList,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.2)"
              },
              normal: {
                label: {
                  show: true,
                  formatter: "{b}"
                },
                labelLine: {
                  show: true,
                  length: 30
                }
              }
            }
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      houseAddress.setOption(option);
    },
    //房源价格占比
    housePrice() {
      var housePrice = echarts.init(document.getElementById("housePrice"));
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "房源价格占比(元/月/人)"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
          {
            name: "房源价格占比",
            type: "pie",
            radius: ["30%", "60%"],
            // data: [
            //   {value:10,name:'500以下'},
            //   {value:10,name:'501-1500'},
            //   {value:2,name:'1500-2000'},
            //   {value:2,name:'2000-2500'},
            // ],
            data: this.dataCount.priceResultList,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.2)"
              },
              normal: {
                label: {
                  show: true,
                  formatter: "{b}",
                  normal: {
                    position: "inner"
                  }
                }
              }
            }
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      housePrice.setOption(option);
    },
    //房源大小占比
    houseSize() {
      var houseSize = echarts.init(document.getElementById("houseSize"));
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "房源面积占比"
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        series: [
          {
            name: "房源面积占比",
            type: "pie",
            radius: "50%",
            // data: [
            //   {value:10,name:'30以下'},
            //   {value:10,name:'30-50'},
            //   {value:2,name:'50-70'},
            //   {value:3,name:'70-80'},
            //   {value:7,name:'80-90'},
            //   {value:6,name:'90-100'},
            //   {value:3,name:'100以上'},
            // ],
            data: this.dataCount.areaResultList,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.2)"
              },
              normal: {
                label: {
                  show: true,
                  formatter: "{b}"
                },
                labelLine: {
                  show: true,
                  length: 30
                }
              }
            }
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      houseSize.setOption(option);
    },
    //交易时间柱状图
    tradeTimeColumn() {
      var tradeTimeColumn = echarts.init(
        document.getElementById("tradeTimeColumn")
      );
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "交易量柱状图(最近一年)"
        },
        tooltip: {},
        xAxis: {
          data: this.dataCount.houseRentMonthList
        },
        yAxis: {},
        series: [
          {
            name: "交易量",
            type: "bar",
            data: this.dataCount.houseRentMonthCountList
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      tradeTimeColumn.setOption(option);
    },
    //交易时间折线图
    tradeTimeLine() {
      var tradeTimeLine = echarts.init(
        document.getElementById("tradeTimeLine")
      );
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "交易量折线图(最近一年)"
        },
        xAxis: {
          data: this.dataCount.houseRentMonthList
        },
        yAxis: {},
        series: [
          {
            name: "交易量",
            type: "line",
            data: this.dataCount.houseRentMonthCountList
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      tradeTimeLine.setOption(option);
    }
    //交易数量与房源的数据关系
    //交易数量与价格的占比
    //交易数量与房源大小的占比
  },
  mounted() {
    this.getDataCount();
  }
};
</script>

<style scoped>
.showData {
  font-family: 华文楷体;
  font-size: 26px;
  font-weight: bold;
  height: 800px;
  overflow: scroll;
  overflow-x: hidden !important;
}
.houseBase {
  height: 400px;
}
.tradeInfo {
  height: 400px;
}
.tradeAndHouse {
  height: 400px;
}
</style>
