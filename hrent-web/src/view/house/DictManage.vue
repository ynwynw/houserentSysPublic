<template>
  <div class="dictManage">
    <el-tabs type="card" @edit="handleTabsEdit" v-model="tableTabsValue">
      <el-tab-pane label="字典列表" name="dictList">
        <el-form :inline="true" :model="searchDict" class="searchDict" size="small">
          <el-form-item label="字典类型">
            <el-select v-model="searchDict.type" filterable  placeholder="字典类型" clearable>
              <el-option v-for="item in dictTypeList" :label="item.type" :value="item.type" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="检索条件">
            <el-input v-model="searchDict.condition" placeholder="请输入字典意思或描述"></el-input>
          </el-form-item>
          <el-button type="primary" size="small" @click="findDictPage">搜索</el-button>
          <el-button type="primary" size="small" @click="addDict">添加字典</el-button>
        </el-form>
        <el-table :data="dictList" :header-cell-style="{
                'background-color': '#E8E8E8',
                'height': '3.56vw',
                'padding':'0',
                'border': 'none',
                'font-size':'0.7vw',
                'color':'#666666'
            }" height="600"
            :row-class-name="tableRowClassName"
            style="width: 100%;overflow: scroll;overflow-x: hidden !important;">
          <el-table-column type="index"></el-table-column>
          <el-table-column prop="value" label="字典值" sortable></el-table-column>
          <el-table-column prop="label" label="字典意思"></el-table-column>
          <el-table-column prop="type" label="字典类型"></el-table-column>
          <el-table-column prop="description" label="描述"></el-table-column>
          <el-table-column prop="sort" label="排序" sortable></el-table-column>
          <el-table-column prop="createBy" label="创建人">
            <template v-slot="scope">
              {{scope.row.createBy==null?"":scope.row.createBy.name}}
            </template>
          </el-table-column>

          <el-table-column prop="createDate" label="创建时间" sortable></el-table-column>
          <el-table-column label="操作" width="130px">
            <template v-slot="scope">
              <el-button size="mini" type="primary" icon="el-icon-edit" @click="edit(scope.row)"></el-button>
              <el-button size="mini" type="warning" icon="el-icon-delete" @click="deleteUser(scope.row.id)"></el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-dialog :visible.sync="dictDialogVisible" title="修改字典" width="30%">
          <el-form :model="dictEdit" :rules="rules" ref="dictEdit">
            <el-form-item label="字典值：" class="is-required" prop="value">
              <el-input v-model="dictEdit.value" placeholder="请输入字典的值"></el-input>
            </el-form-item>
            <el-form-item label="字典意思：" class="is-required" prop="label">
              <el-input v-model="dictEdit.label" placeholder="请输入字典意思"></el-input>
            </el-form-item>
            <el-form-item label="字典类型：" class="is-required" prop="type">
              <el-input v-model="dictEdit.type" placeholder="请输入字典类型"></el-input>
            </el-form-item>
            <el-form-item label="字典描述：" class="is-required" prop="description">
              <el-input v-model="dictEdit.description" placeholder="请输入字典描述"></el-input>
            </el-form-item>
            <el-form-item label="字典排序：" prop="sort">
              <el-input v-model="dictEdit.sort" placeholder="请输入字典排序"></el-input>
            </el-form-item>
            <el-form-item class="editBtn">
              <el-button type="primary" size="small" @click="editDictCols('dictEdit')">修改</el-button>
              <el-button size="small" @click="cancelEdit">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>

        <el-pagination
          @size-change="sizeChange"
          @current-change="currentChange"
          @prev-click="prevClick"
          @next-click="nextClick"
          background
          layout="total,sizes,prev, pager, next"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="10"
          :total="dictPage.count">
        </el-pagination>
      </el-tab-pane>
      <el-tab-pane v-for="item in tabList" :label="item.title" :name="item.name" :key="item.name" closable>
        <el-form :model="addDictCols" :rules="rules" ref="addDictCols" class="addDictCols" size="small">
          <el-form-item label="字典值：" prop="value" class="is-required">
            <el-input v-model="addDictCols.value" placeholder="请输入字典的值"></el-input>
          </el-form-item>
          <el-form-item label="字典意思：" prop="label" class="is-required">
            <el-input v-model="addDictCols.label" placeholder="请输入字典意思"></el-input>
          </el-form-item>
          <el-form-item label="字典类型：" prop="type" class="is-required">
            <el-input v-model="addDictCols.type" placeholder="请输入字典类型"></el-input>
          </el-form-item>
          <el-form-item label="字典描述：" prop="description" class="is-required">
            <el-input v-model="addDictCols.description" placeholder="请输入字典描述"></el-input>
          </el-form-item>
          <el-form-item label="字典排序：" prop="sort">
            <el-input v-model="addDictCols.sort" placeholder="请输入字典排序"></el-input>
          </el-form-item>
          <el-form-item class="addBtn">
            <el-button type="primary" size="small" @click="addCols('addDictCols')">添加</el-button>
            <el-button size="small" @click="cancel">取消</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: "DictManage",
  data(){
    return{
      ruleForm:{
        value:'',
        label:'',
        type:'',
        description:'',
        sort:'',
      },
      rules:{
        value:[
          { required: true, message: '请输入字典值', trigger: 'blur' }
        ],
        label:[
          { required: true, message: '请输入字典意思', trigger: 'blur' }
        ],
        type:[
          { required: true, message: '请输入字典类型', trigger: 'blur' }
        ],
        description:[
          { required: true, message: '请输入字典描述', trigger: 'blur' }
        ],
      },
      dictEdit:{
        id:'',
        value:'',
        label:'',
        type:'',
        description:'',
        sort:'',
      },
      dictEditInit:{
        id:'',
        value:'',
        label:'',
        type:'',
        description:'',
        sort:'',
      },
      dictDialogVisible:false,
      dictPage:{
        count:10,
        pageSize:10,
        pageNo: 1
      },
      addDictCols:{
        value:'',
        label:'',
        type:'',
        description:'',
        sort:'',
      },
      dictTypeList:[
        {
          id:'1',
          type:'hrent_role'
        },
        {
          id:'2',
          type: 'hrent_state'
        }
      ],
      searchDict:{
        type:'',
        condition:''
      },
      tableTabsValue:'dictList',
      tabList:[

      ],
      dictList:[

      ]
    }
  },
  methods:{
    cancelEdit(){
      this.dictDialogVisible = false;
      this.dictEdit = this.dictEditInit;
    },
    editDictCols(formName){
      this.$refs[formName].validate((valid) => {
        console.log(this.dictEdit)
        if (valid) {
          let params = this.dictEdit;
          this.$network.editDict(params).then(res=>{
            if(res.data.code=='200'){
              this.$message.success(res.data.message);
            }
            this.findDictPage();
            this.dictEdit = this.dictEditInit;
            this.dictDialogVisible = false;
          })
        } else {
          return false;
        }
      });
    },
    edit(row){
      this.dictDialogVisible = true;
      this.dictEdit.id = row.id;
      this.dictEdit.value  = row.value;
      this.dictEdit.description = row.description;
      this.dictEdit.type = row.type;
      this.dictEdit.sort = row.sort;
      this.dictEdit.label = row.label;
    },
    deleteUser(id){
      //根据id删除字典值
      this.$confirm('是否删除该字典?','警告',{
        confirmButtonText:'确定',
        cancelButtonText:'取消',
        type:'warning'
      }).then(()=>{
        let params = {
          id:id
        }
        //向后端发送请求，删除该字典
        this.$network.deleteDict(params).then(res=>{
          if(res.data.code=='200'){
            this.$message.success(res.data.message);
            this.findDictPage();
          }else{
            this.$message.error(res.data.message);
          }

        })

      })
    },
    handleTabsEdit(targetName, action){
      if(action==='remove'){
        this.tabList=[];
        this.tableTabsValue='dictList';
        this.addDictCols = {
          value:'',
          label:'',
          type:'',
          description:'',
          sort:'',
        }
      }
    },
    tableRowClassName({row, rowIndex}) {
      let index = rowIndex;
      if (index % 2 == 0) {
        return 'warning-row'
      } else {
        return 'warning-col'
      }
    },
    addDict(){
      this.tabList=[{
        title:'添加字典',
        name:'addDict'
      }]
      this.tableTabsValue='addDict';
    },
    addCols(formName){
      this.$refs[formName][0].validate((valid) => {
        if (valid) {
          let params = this.addDictCols;
          // let temp = {
          //   id:1,
          //   text:2
          // }
          // params.assign(temp)
          this.$network.addDict(params).then(res=>{
            if(res.data.code=='200'){
              this.$message.success(res.data.message);
            }
            this.tabList=[];
            this.tableTabsValue='dictList';
            this.findDictPage();
            this.addDictCols = {
              value:'',
              label:'',
              type:'',
              description:'',
              sort:'',
            }
          })
        } else {
          return false;
        }
      });
    },
    cancel(){
      this.tabList=[];
      this.tableTabsValue='dictList';
      this.addDictCols = {
        value:'',
        label:'',
        type:'',
        description:'',
        sort:'',
      }
    },
    findDict(){
      //调用后端接口，返回查询的数据

    },
    sizeChange(val){
      this.dictPage.pageSize = val;
      this.findDictPage();
    },
    currentChange(val){
      this.dictPage.pageNo = val;
      this.findDictPage();
    },
    prevClick(val){
      this.dictPage.pageNo = val;
      this.findDictPage();
    },
    nextClick(val){
      this.dictPage.pageNo = val;
      this.findDictPage();
    },
    findDictPage(){
      let params = {
        pageNo:this.dictPage.pageNo,
        pageSize:this.dictPage.pageSize,
        type:this.searchDict.type,
        condition:this.searchDict.condition,
      }
      this.$network.findDictPage(params).then(res=>{
        if(res.data.code=="200"){
          this.dictList = res.data.data.list;
          this.dictPage.count = res.data.data.count;
        }
      })

      this.$network.findDictAllType().then(res=>{
        if(res.data.code=="200"){
          this.dictTypeList = res.data.data;
        }
      })
    },
  },
  beforeMount() {
  },
  mounted() {
    //发送请求，查询所有字典
    let params = {
      pageNo:1,
      pageSize:10,
    }
    this.$network.findDictPage(params).then(res=>{
      if(res.data.code=="200"){
        this.dictList = res.data.data.list;
        this.dictPage.count = res.data.data.count;
      }
    })

    this.$network.findDictAllType().then(res=>{
      if(res.data.code=="200"){
        this.dictTypeList = res.data.data;
      }
    })
  }
}
</script>

<style scoped>
.searchDict{
  float: left;
}
.addDictCols{
  width: 300px;
  margin-left: 100px;
}
.addBtn>*{
  margin: 0 30px;
}

.editBtn>*{
  margin: 0 50px;
}
</style>
