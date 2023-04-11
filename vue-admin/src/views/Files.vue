<template>
  <div>
    <!--        搜索工具-->
    <div class="p-10">
      <el-input style="width: 200px" placeholder="请输入文件名称" v-model="name" suffix-icon="el-icon-search"
                clearable></el-input>
      <el-select v-model="type" class="ml-5" placeholder="请选择文件类型" clearable>
        <el-option
            v-for="item in this.types"
            :key="item.id"
            :label="item"
            :value="item">
        </el-option>
      </el-select>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="info" @click="reset">重置</el-button>
    </div>
    <!--        工具栏-->
    <div class="p-10">
      <el-upload
          class="upload-demo ml-5"
          :headers="headers"
          action="http://localhost:8080/file/upload"
          :show-file-list="false"
          style="display: inline-block;" :on-success="handleUpload" :on-error="importExcelError">
        <el-button type="primary" icon="el-icon-upload2">上传</el-button>
      </el-upload>
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
      <el-table-column prop="name" label="文件名称" sortable width="180"></el-table-column>
      <el-table-column prop="type" label="文件类型" sortable width="120"></el-table-column>
      <el-table-column prop="size" label="文件大小(KB)" sortable width="120"></el-table-column>
      <el-table-column prop="path" label="文件位置"></el-table-column>
      <el-table-column prop="url" label="下载地址" width="120">
        <template slot-scope="scope">
          <el-button
              size="mini"
              icon="el-icon-download"
              type="warning"
              @click="handleDownLoad(scope.row)">下载
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="enable"
             :filters="[{ text: '开启', value: true }, { text: '关闭', value: false }]"
             :filter-method="filterSwitch"
             filter-placement="bottom-end" label="状态" width="120">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable"
             @change="handleChange(scope.row)"
             active-color="#13ce66"
             inactive-color="#ff4949"
             >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">

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
  </div>
</template>

<script>
export default {
  name: "Files",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      name: '',
      type: '',
      notice: '',
      types: '',
      headers: {
        token: JSON.parse(localStorage.getItem("user")).token
      },
      multipleSelection: [],
    }
  }
  ,
  created() {
    this.load();
  },
  methods: {
    load() {
      this.request.get("/file/page", {
        params: {
          "pageNum": this.pageNum,
          "pageSize": this.pageSize,
          "name": this.name,
          "type": this.type
        }
      }).then(resp => {
        // console.log(resp)
        this.tableData = resp.data.records;
        this.total = resp.data.total;
        this.types = Array.from(new Set(resp.data.records.map(v => v.type)));
      })
    },
    handleChange(row) {
      if (row.enable) {
        this.notice = '开启';
      } else {
        this.notice = '关闭';
      }
      this.$confirm("确定" + this.notice + "吗？", '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.request.post("/file/update", row).then(res => {
          if (res.code === '200') {
            this.$message.success("已" + this.notice);
            this.load();
          }
        });
      }).catch(() => {
        this.load();
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val;
    },
    handleDownLoad(row) {
      console.log(row);
      this.$confirm('确定下载' + row.name + "吗？", '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        window.open(row.url);
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });

    },
    handleDelete(id) {
      console.log(id);
      this.request.delete("/file/" + id).then(res => {
        if (res.code === '200') {
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
      console.log(ids)
      this.request.post("/file/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功");
          this.load();
        } else {
          this.$message.error("删除失败");
        }
      })
      console.log(this.multipleSelection)
    },
    handleUpload(res) {
      if (res.code === "200") {
        this.$message.success("上传成功");
      }
      if (res.code === "550") {
        this.$message.warning("文件重复");
      }
      this.load();
    },
    importExcelError() {
      this.$message.error("上传失败");
    },
    reset() {
      this.name = "";
      this.type = "";
      this.load();
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.load();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.load();
    },
    filterSwitch(value, row) {
      console.log(value, row)
      return row.enable === value;
    },
  },
}
</script>

<style scoped>

</style>