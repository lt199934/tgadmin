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
      <el-button type="success" icon="el-icon-plus" @click="handleAdd">增加</el-button>
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
      <el-table-column prop="name" label="角色名称" sortable></el-table-column>
      <el-table-column prop="description" label="描述" sortable></el-table-column>
      <el-table-column prop="flag" label="唯一标识" sortable></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              size="mini"
              icon="el-icon-edit"
              type="success"
              @click="handleEdit(scope.row)">编辑
          </el-button>
          <el-button type="info" @click="handleAddMenu(scope.row)" icon="el-icon-menu">权限菜单</el-button>
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
    <el-dialog :title="title" :visible.sync="dialogFormVisible" width="25%">
      <el-form :model="form" label-width="70px" size="small">
        <el-form-item label="角色名称">
          <el-input v-model="form.name" autocomplete="off" clearable></el-input>
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

    <el-dialog :title="title" :visible.sync="menuFormVisible" width="25%">
      <el-form label-width="70px" size="small">
        <el-tree
            :props="props"
            :data="treeData"
            show-checkbox
            node-key="id"
            ref="tree"
            :default-expanded-keys="expands"
            :default-checked-keys="checks">
            <span class="custom-tree-node" slot-scope="{node,data}">
              <i :class="data.icon"></i> {{ data.name }}
            </span>
        </el-tree>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSaveRoleMenu">{{ ok }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "User",
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
      dialogFormVisible: false,
      menuFormVisible: false,
      treeData: [],
      props: {
        label: 'name'
      },
      expands: [],
      checks: [],
      roleId: 0,
      flag: ''
    }
  },
  created() {
    this.load();
  },
  computed: {
    userInfo() {
      return this.$store.getters.getUserInfo
    }
  },
  methods: {
    load() {
      this.request.get("/role/page", {
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
    handleSave() {
      this.request.post("/role", this.form).then(res => {
        if (res.data) {
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
      this.title = '角色信息';
      this.ok = '保 存';
      this.success = '添加成功';
      this.error = '添加失败';
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
      this.request.delete("/role/" + id).then(res => {
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
      this.request.post("/role/del/batch", ids).then(res => {
        if (res.data) {
          this.$message.success("批量删除成功");
          this.load();
        } else {
          this.$message.error("批量删除失败");
        }
      })
      // console.log(this.multipleSelection)
    },
    handleAddMenu(row) {
      this.roleId = row.id;
      this.flag = row.flag;
      this.title = '权限菜单';
      this.ok = '保 存';
      this.menuFormVisible = true;
      this.request.get("/menu", {
        params: {
          "name": this.name
        }
      }).then(resp => {
        console.log(resp)
        this.treeData = resp.data;
        this.expands = resp.data.map(v => v.id);
      })
      this.request.get("/role/roleMenu/" + row.id).then(resp => {
        // console.log(resp)
        this.checks = resp.data.map(v => v.menuId);
        this.request.get("/menu/ids").then(res => {
          const ids = res.data;
          ids.forEach(id => {
            if (!this.checks.includes(id)) {
              this.$refs.tree.setChecked(id, false);
            }
          })
        })

      })
    },
    handleSaveRoleMenu() {
      this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
        if (res.code === '200') {
          this.$message.success("权限分配成功");
          this.menuFormVisible = false;
          // this.$store.commit("logout");
          if(this.flag === "ROLE_ADMIN") {
            this.refreshMenu(this.flag);
          }
        } else {
          this.$message.success("权限分配失败");
        }
      })
    },
    refreshMenu(role) {
      let data = {"role":role}
      this.request.post("/menu/refresh", data).then(res => {
        localStorage.setItem("menus", JSON.stringify(res.data));
      });
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