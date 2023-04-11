<template>
  <el-row :gutter="20">
    <el-col :span="16">
      <el-card>
        <el-form :model="form" label-width="70px" size="small">
          <el-form-item label="文章标题">
            <el-input v-model="form.title" autocomplete="off" clearable></el-input>
          </el-form-item>
          <el-form-item v-show="title === '修改信息'"  label="文章作者">
            <el-input v-model="form.author"  readonly autocomplete="off" clearable></el-input>
          </el-form-item>
          <el-form-item label="文章内容">
            <mavon-editor  ref="md" v-model="form.content" :ishljs="true"  @imgAdd="imgAdd"></mavon-editor>
          </el-form-item>
        </el-form>
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="handleSave">{{ ok }}</el-button>
        <el-dialog title="文章信息" :visible.sync="viewDialogVisible">
          <el-card>
            <div>
              <mavon-editor
                  class="md"
                  :value="content"
                  :subfield="false"
                  default-open="preview"
                  :toolbars-flag="false"
                  :scroll-style="true"
                  :ishljs="true"
              >
              </mavon-editor>
            </div>
          </el-card>
        </el-dialog>
      </el-card>
    </el-col>
    <el-col :span="8">

    </el-col>
  </el-row>

</template>

<script>
export default {
  name: "WriteArticle",
  data() {
    return {
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: '',
      ok: '发布',
      success: '',
      error: '',
      title: '',
      tableData: [],
      multipleSelection: [],
      form: {},
      dialogFormVisible: false,
      viewDialogVisible: false,
      treeData: [],
      props: {
        label: 'name'
      },
      expands: [],
      checks:[],
      roleId: 0,
      flag: '',
      content: ''
    }
  },
  created() {
    this.load();
  },
  methods: {
    view(content){
      this.content = content;
      this.viewDialogVisible = true;
    },
    load() {
      this.request.get("/article/page", {
        params: {
          "pageNum": this.pageNum,
          "pageSize": this.pageSize,
          "name": this.name
        }
      }).then(resp => {
        // console.log(resp)
        this.tableData = resp.data.records;
        this.total = resp.data.total;
      })
    },
    imgAdd(pos,$file) {
      let $vm = this.$refs.md;
      const formatData = new FormData();
      formatData.append('file',$file);
      axios({
        url:'/file/upload',
        methods: 'post',
        data:formatData,
        headers: {'Content-Type':'multipart/form-data'},
      }).then(res => {
        $vm.$img2Url(pos,res.data);
      })
    },
    handleSave() {
      this.request.post("/article", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success(this.success);
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error(this.error);
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true;
      this.title = '写文章';
      this.ok = '发 布';
      this.success = '发布成功';
      this.error = '发布失败';
      this.form = {}
    },
    handleSelectionChange(val) {
      // console.log(val)
      this.multipleSelection = val;
    },
    handleEdit(row) {
      // console.log(row);
      this.form = row;
      this.title = '修改信息';
      this.ok = '更 新';
      this.success = '更新成功';
      this.error = '更新失败';
      this.dialogFormVisible = true;
    },
    handleDelete(id) {
      // console.log(id);
      this.request.delete("/article/" + id).then(res => {
        if (res.data) {
          this.$message.success("删除成功");
          this.load();
        } else {
          this.$message.error("删除失败");
        }
      })
    },
    handleDeleteBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning("请先选择要批量删除的内容");
        return;
      }
      let ids = this.multipleSelection.map(item => item.id);
      this.request.post("/article/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      })
      // console.log(this.multipleSelection)
    },
    reset() {
      this.name = "";
      this.load();
    },
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.load();
    },
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.load();
    }
  },

}
</script>

<style scoped>
.demo-table-expand {
  font-size: 0;
}

.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}

.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 100%;
}
</style>