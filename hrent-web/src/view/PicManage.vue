<template>
  <div class="userManage">
    <el-form :inline="true" size="small" :model="findPic" class="findPic">
      <el-form-item label="图片类型">
        <el-select v-model="findPic.type" placeholder="请选择图片类型">
          <el-option v-for="item in typeList" :label="item.label" :value="item.value" :key="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" size="mini" @click="findPage">查询</el-button>
      <el-button type="primary" size="mini" @click="addPic" v-if="findPic.type==1">增加图片</el-button>
    </el-form>
    <el-table :data="picList" :header-cell-style="{
                'background-color': '#E8E8E8',
                'height': '3.56vw',
                'padding':'0',
                'border': 'none',
                'font-size':'0.7vw',
                'color':'#666666'
            }"
              :row-class-name="tableRowClassName"
              height="600"
              style="width: 100%;overflow: scroll;overflow-x: hidden !important;"
              >
      <el-table-column type="index" fixed prop="index"></el-table-column>
      <el-table-column prop="name" label="图片名" width="200"></el-table-column>
      <el-table-column prop="type" label="图片类型" width="100"></el-table-column>
      <el-table-column label="状态" width="150">
        <template slot-scope="scope">

          <el-switch style="display: block"
                     v-model="scope.row.state"
                     active-color="#13ce66"
                     inactive-color="#ff4949"
                     :active-value="1"
                     :inactive-value="0"
                     active-text="正常"
                     inactive-text="停用"
                     @change="changeState($event,scope.row.id)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="图片" width="150">
        <template slot-scope="scope">
          <el-image :src="scope.row.type==7?scope.row.url:picLocal+scope.row.url" class="picShow"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="houseId" label="房源Id"></el-table-column>
      <el-table-column prop="updateBy.id" label="更新人"></el-table-column>
      <el-table-column prop="updateDate" label="更新时间"></el-table-column>
      <el-table-column label="操作" width="130px">
        <template v-slot="scope">
          <el-button size="mini" type="warning" icon="el-icon-delete" @click="deletePic(scope.row.id)"></el-button>
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
      :page-size="picPage.pageSize"
      :total="picPage.count"></el-pagination>
    <el-dialog title="添加小程序轮播图" :visible.sync="type1Visible" :close-on-click-modal="false">
      <!--类似用户头像-->
      <div class="infoImg">
        <el-image :src="picLocal+''+picUrl" style="width: 300px;height: 200px;"></el-image>
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="type1Visible = false" size="mini">取 消</el-button>
        <el-button type="primary" @click="addPicType1"  size="mini">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {base} from "../config";

export default {
  name: "PicManage",
  data(){
    return{
      picName:'',
      uploadHead:base.baseUrl+'/pic/uploadPic',
      picUrl:'',
      type1Visible:false,
      findPic:{
        type:1
      },
      uploadHeaders:{},
      picLocal: base.picLocal,
      baseUrl:base.baseUrl,
      picList:[
      ],
      picPage:{
        pageNo:1,
        pageSize:10,
        count:10,
      },
      //图片类型(1:小程序轮播图,2:按钮,3:公用,4:系统，5:房源，6:用户,7:微信小程序头像
      typeList:[
        {
          value:1,
          label:'小程序轮播图'
        },
        {
          value:2,
          label:'按钮'
        },
        {
          value:3,
          label:'公用'
        },
        {
          value:4,
          label:'系统'
        },
        {
          value:5,
          label:'房源'
        },
        {
          value:6,
          label:'用户'
        },
        {
          value: 7,
          label:'微信小程序头像'
        }
      ]
    }
  },
  methods:{
    deletePic(id){
      this.$confirm('确定删除这张图片吗？','提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let params = {
          id:id
        }
        this.$network.deletePic(params).then(res=>{
          if(res.data.code=='200'){
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.findPage();
          }else{
            this.$message.error("删除失败")
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    tableRowClassName({row, rowIndex}) {
      let index = rowIndex;
      if (index % 2 == 0) {
        return 'warning-row'
      } else {
        return 'warning-col'
      }
    },
    sizeChange(val){
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
      this.picPage.pageSize = val;
      this.findPage();
    },
    currentChange(val){
      this.picPage.pageNo = val;
      this.findPage();
    },
    prevClick(val){
      // console.log(`${val}`)
    },
    nextClick(val){
      // console.log(`${val}`)
    },
    findPage(){
      let params = {
        pageNo: this.picPage.pageNo,
        pageSize: this.picPage.pageSize,
        type:this.findPic.type,
      }
      //加载图片数据
      this.$network.findPicPage(params).then(res=>{
        this.picList = res.data.data.list;
        this.picPage.count = res.data.data.count;
      }).catch(err=>{
        console.log(err)
      })
    },
    changeState(val,id){
      let params = {
        state:val,
        id:id
      }
      this.$network.updatePic(params).then(res=>{
        if(res.data.code=='200'){
          this.$message.success("修改成功")
        }else{
          this.$message.error("修改失败")
        }
      }).catch(err=>{
        console.log(err)
      })
    },
    addPic(){
      this.type1Visible = true;
    },
    uploadSuccess(response, file, fileList){
      if(response.code=="200"){
        this.picUrl = response.data;
        this.picName = file.name;
        fileList=[];
        this.$message.success("上传成功")
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
    addPicType1(){
      //将图片实体类整合为一个实体类
      let params = {
        url:this.picUrl,
        name:this.picName,
        type:1,
        state:1,
      }
      this.$network.updatePic(params).then(res=>{
        this.$message.success("新增成功")
        this.findPage();
      }).catch(err=>{
        console.log(err)
      })
      this.type1Visible = false;
    }
  },
  beforeMount() {
    //加载图片数据
    this.findPage();
  }
}
</script>

<style scoped>
.picShow{
  height: 50px;
  width: 50px;
}
</style>
