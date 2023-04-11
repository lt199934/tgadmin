<template>
  <div>
    <!--        搜索工具-->
    <div class="p-10">
      <el-input style="width: 200px" placeholder="请输入名称" v-model="name" suffix-icon="el-icon-search"
                clearable></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button class="ml-5" type="info" @click="reset">重置</el-button>
    </div>
    <!--        工具栏-->
    <div class="p-10">
      <el-button type="success" icon="el-icon-plus" @click="handleAdd()">增加</el-button>
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
    <el-table :data="tableData" style="width: 100%" stripe
              row-key="id" default-expand-all
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="id" label="ID" sortable></el-table-column>
      <el-table-column prop="name" label="菜单名称" sortable></el-table-column>
      <el-table-column prop="path" label="菜单路径"></el-table-column>
      <el-table-column prop="component" label="菜单组件"></el-table-column>
      <el-table-column prop="icon" label="菜单图标" class-name="fs-24" align="center" label-class-name="fs-12">
        <template slot-scope="scope">
          <i :class="scope.row.icon" style="font-size:18px "></i>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="备注" sortable></el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button
              size="mini"
              icon="el-icon-edit"
              type="success"
              @click="handleEdit(scope.row)">编辑
          </el-button>
          <el-button
              size="mini"
              icon="el-icon-plus"
              type="warning"
              v-show="!scope.row.path||scope.row.pid"
              @click="handleAdd(scope.row.id)">增加
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
    <!--     dialog弹出框   -->
    <el-dialog :title="title" :visible.sync="dialogFormVisible" width="25%">
      <el-form :model="form" label-width="70px" size="small">
        <el-form-item label="父级菜单">
          <el-input v-model="form.pid" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="显示排序">
          <el-input-number v-model="form.id" :min="1" autocomplete="off" clearable></el-input-number>
        </el-form-item>
        <el-form-item label="菜单名称">
          <el-input v-model="form.name" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="菜单路径">
          <el-input v-model="form.path" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="菜单组件">
          <el-input v-model="form.component" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="菜单图标">
          <el-select v-model="form.icon" placeholder="请选择文件类型" clearable style="width: 100%" size="small">
            <el-option
                v-for="item in icons"
                :key="item.value"
                :label="item.name"
                :value="item.value">
              <span type="info"><i :class="item.value"></i> {{ item.value }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="textarea" v-model="form.description" autocomplete="off" clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSave">{{ ok }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Menu",
  data() {
    return {
      total: 0,
      pageNum: 1,
      pageSize: 5,
      name: '',
      ok: '',
      title: '',
      success: '',
      error: '',
      tableData: [],
      multipleSelection: [],
      form: {},
      dialogFormVisible: false,
      icons: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.request.get("/menu", {
        params: {
          "name": this.name
        }
      }).then(resp => {
        // console.log(resp)
        this.tableData = resp.data;
      })

      this.request.get("/dict/icons").then(resp => {
        // console.log(resp)
        this.icons = resp.data;
      })
    },
    handleSave() {
      this.request.post("/menu", this.form).then(res => {
        if (res.data) {
          this.$message.success(this.success);
          this.dialogFormVisible = false;
          this.refreshMenu("ROLE_ADMIN");
          this.load();
        } else {
          this.$message.error(this.error);
        }
      })
    },
    refreshMenu(role) {
      let data = {"role": role}
      this.request.post("/menu/refresh", data).then(res => {
        localStorage.setItem("menus", JSON.stringify(res.data));
      });
    },
    handleAdd(pid) {
      this.dialogFormVisible = true;
      this.title = '菜单信息';
      this.ok = '保 存';
      this.success = '添加成功';
      this.error = '添加失败';
      this.form = {}
      if (pid) {
        this.form.pid = pid;
      }
    },
    handleSelectionChange(val) {
      // console.log(val)
      this.multipleSelection = val;
    },
    handleEdit(row) {
      console.log(row);
      this.form = row;
      this.title = '修改信息';
      this.ok = '更 新';
      this.success = '更新成功';
      this.error = '更新失败';
      this.dialogFormVisible = true;
    },
    handleDelete(id) {
      // console.log(id);
      this.request.delete("/menu/" + id).then(res => {
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

      let data = this.multipleSelection.map(item => {
        return {id: item.id, status: item.status}
      });
      console.log(data)
      this.request.post("/menu/del/batch", data).then(res => {
        if (res.data) {
          this.$message.success("删除成功");
          this.load();
        } else {
          this.$message.error("删除失败");
        }
      })
      console.log(this.multipleSelection)
    },
    reset() {
      this.name = "";
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

.fs-24 {
  font-size: 24px;
}

.fs-12 {
  font-size: 12px;
}
</style>