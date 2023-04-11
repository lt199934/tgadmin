<template>
  <div>
    <!--        搜索工具-->
    <div class="p-10">
      <el-input style="width: 200px" placeholder="请输入标题" v-model="name" suffix-icon="el-icon-search"
                clearable></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset">重置</el-button>
    </div>
    <!--        工具栏-->
    <div class="p-10">
      <el-button type="success" icon="el-icon-plus" @click="handleAdd">写文章</el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='好的'
          cancel-button-text='不用了'
          icon="el-icon-info"
          icon-color="red"
          title="您确定删除吗？"
          @confirm="handleDeleteBatch"
      >
        <el-button type="danger" icon="el-icon-remove-outline" slot="reference">批量删除</el-button>
      </el-popconfirm>
    </div>
    <!--    数据-->
    <el-table :data="tableData" style="width: 100%" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="id" label="ID" sortable></el-table-column>
      <el-table-column prop="title" label="文章标题" sortable></el-table-column>
      <el-table-column prop="content" label="文章内容" sortable>
        <template slot-scope="scope">
          <el-button @click="view(scope.row.content)" type="primary"> 查看内容</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="author" label="作者" sortable></el-table-column>
      <el-table-column prop="createTime" label="注册日期" sortable width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              icon="el-icon-edit"
              type="success"
              @click="handleEdit(scope.row)">编辑
          </el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='好的'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="handleDelete(scope.row.id)"
          >
            <el-button
                size="mini"
                icon="el-icon-delete"
                type="danger"
                slot="reference"
            >删除
            </el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--        分页-->
    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <!--     dialog弹出框   -->
    <el-dialog :title="title" :visible.sync="dialogFormVisible">
      <el-form :model="form" label-width="70px" size="small">
        <el-form-item label="文章标题">
          <el-input v-model="form.title" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="文章分类">
          <el-select v-model="form.category" placeholder="请选择分类" clearable style="width: 100%" size="small">
            <el-option
                v-for="item in categories"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              <span type="info">{{ item.name }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-show="title === '修改信息'" label="文章作者">
          <el-input v-model="form.author" readonly autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="文章内容">
          <mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"></mavon-editor>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSave">{{ ok }}</el-button>
      </div>
    </el-dialog>

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
  </div>
</template>

<script>
export default {
  name: "Article",
  data() {
    return {
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: '',
      ok: '',
      success: '',
      error: '',
      title: '',
      tableData: [],
      multipleSelection: [],
      form: {},
      categories: [],
      dialogFormVisible: false,
      viewDialogVisible: false,
      treeData: [],
      props: {
        label: 'name'
      },
      expands: [],
      checks: [],
      roleId: 0,
      flag: '',
      content: ''
    }
  },
  created() {
    this.load();
  },
  methods: {
    view(content) {
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
      this.request.get("/category").then(resp => {
        // console.log(resp)
        this.categories = resp.data;
      })
    },
    imgAdd(pos, $file) {
      let $vm = this.$refs.md;
      const formatData = new FormData();
      formatData.append('file', $file);
      axios({
        url: '/file/upload',
        methods: 'post',
        data: formatData,
        headers: {'Content-Type': 'multipart/form-data'},
      }).then(res => {
        $vm.$img2Url(pos, res.data);
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

<style lang="scss">
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