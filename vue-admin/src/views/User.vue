<template>
  <div>
    <!--        搜索工具-->
    <div class="p-10">
      <el-input style="width: 200px" placeholder="请输入名称" v-model="username" suffix-icon="el-icon-search"
                clearable></el-input>
      <el-input style="width: 200px" placeholder="请输入邮箱" v-model="email" suffix-icon="el-icon-message"
                clearable
                class="ml-5"></el-input>
      <el-input style="width: 200px" placeholder="请输入地址" v-model="address" suffix-icon="el-icon-position"
                clearable
                class="ml-5"></el-input>
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
      <el-upload
          class="upload-demo ml-5"
          action="http://localhost:8080/user/import"
          :headers="headers"
          :show-file-list="false"
          style="display: inline-block;" accept="xlsx" :on-success="importExcelSuccess" :on-error="importExcelError">
        <el-button type="primary" icon="el-icon-upload2">导入</el-button>
      </el-upload>
        <el-button type="warning" class="ml-5" icon="el-icon-download" @click="exportExcel">导出</el-button>
    </div>
    <!--    数据-->
    <el-table :data="tableData" style="width: 100%" stripe @selection-change="handleSelectionChange">
      <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="用户 ID">
            <span>{{ props.row.id }}</span>
          </el-form-item>
          <el-form-item label="用户名">
            <span>{{ props.row.username }}</span>
          </el-form-item>
          <el-form-item label="昵称">
            <span>{{ props.row.nickname }}</span>
          </el-form-item>
          <el-form-item label="邮箱">
            <span>{{ props.row.email }}</span>
          </el-form-item>
          <el-form-item label="电话">
            <span>{{ props.row.phone }}</span>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <span>{{ props.row.status }}</span>
            <template slot-scope="scope">
              <el-tag
                  :type="props.row.status === 0 ? 'success' :(props.row.status === 1) ? 'warning':'danger'"
                  disable-transitions>
                {{ props.row.status === 0 ? '正常' : (props.row.status === 1) ? '异常' : '已注销' }}
              </el-tag>
            </template>
          </el-form-item>
          <el-form-item label="注册时间">
            <span>{{ props.row.createTime }}</span>
          </el-form-item>
        </el-form>
      </template>
      </el-table-column>
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="username" label="用户名" sortable></el-table-column>
      <el-table-column prop="nickname" label="昵称" sortable></el-table-column>
      <el-table-column prop="avatarUrl" label="头像" sortable >
        <template slot-scope="scope">
          <el-avatar size="large" :src="scope.row.avatarUrl"
                     class="mr-5">user
          </el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="role" label="角色" sortable ></el-table-column>
      <el-table-column prop="sex" label="性别" sortable >
        <template slot-scope="scope">
          <span>{{ scope.row.sex === 0 ? '女' : '男' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="age" label="年龄" sortable></el-table-column>
      <el-table-column prop="email" label="邮箱" sortable></el-table-column>
      <el-table-column prop="phone" label="电话" sortable></el-table-column>
      <el-table-column prop="address" sortable label="地址"></el-table-column>
      <el-table-column prop="onlineStatus" label="在线状态"
                       :filters="[{ text: '离线', value: false }, { text: '在线', value: true }]"
                       :filter-method="filterSwitch"
                       filter-placement="bottom-end" width="100">
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.onlineStatus"
              @change="handleChange(scope.row)"
              active-color="#13ce66"
              inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态"
                       :filters="[{ text: '正常', value: 0 }, { text: '异常', value: 1 },{text: '已注销', value: 2}]"
                       :filter-method="filterTag"
                       filter-placement="bottom-end" width="100">
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.status === 0 ? 'success' :(scope.row.status === 1) ? 'warning':'danger'"
              disable-transitions>
            {{ scope.row.status === 0 ? '正常' : (scope.row.status === 1) ? '异常' : '已注销' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="注册日期" sortable width="180">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>
          <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
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
    <el-dialog :title="title" :visible.sync="dialogFormVisible" width="25%">
      <el-form :model="form" label-width="70px" size="small">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="头像">
          <el-input v-model="form.avatarUrl" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.role" placeholder="请选择角色" clearable>
            <el-option
                v-for="item in roles"
                :key="item.flag"
                :label="item.name"
                :value="item.flag">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="form.sex" placeholder="请选择性别" clearable>
            <el-option
                v-for="item in sexes"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <el-date-picker
              v-model="form.age"
              align="right"
              type="date"
              placeholder="选择日期"
              :picker-options="pickerOptions"
              clearable>
          </el-date-picker>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" autocomplete="off" clearable></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="用户状态" clearable>
            <el-option
                v-for="item in statuses"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
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
  name: "User",
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '昨天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },
      total: 0,
      isOnline: false,
      status: '',
      username: '',
      email: '',
      address: '',
      ok: '',
      success: '',
      error: '',
      tableData: [],
      multipleSelection: [],
      title: '',
      sexes: [{
        value: 0,
        label: '女'
      }, {
        value: 1,
        label: '男'
      }],
      statuses: [{
        value: 0,
        label: '正常'
      }, {
        value: 1,
        label: '异常'
      }, {
        value: 2,
        label: '已注销'
      }],
      form: {},
      dialogFormVisible: false,
      noticeDialog: false,
      notice: '',
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      asideWidth: 200,
      logoTextShow: true,
      pageNum: 1,
      pageSize: 5,
      headers: {
        token: localStorage.getItem("user").token
      },
      roles: []
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.request.get("/user/page", {
        params: {
          "pageNum": this.pageNum,
          "pageSize": this.pageSize,
          "username": this.username,
          "email": this.email,
          "address": this.address
        }
      }).then(resp => {
        // console.log(resp)
        this.tableData = resp.data.records;
        this.total = resp.data.total;
      })
      this.request.get("/role").then(res => {
        this.roles = res.data;
      })
    },
    handleChange(row) {
        // console.log(row)
      if (row.status === 2) {
        this.$message.warning("该用户已经注销，无法进行操作");
        this.load();
        return;
      }
      if (row.onlineStatus) {
        this.success = "用户" + row.username + "上线了";
        this.error = "用户" + row.username + "上线失败";
        this.notice = '确认要上线' + row.username + '用户吗？';
      } else {
        this.success = "用户" + row.username + "成功下线";
        this.error = "用户" + row.username + "下线失败";
        this.notice = '确认要下线' + row.username + '用户吗？';
      }
      this.form = row
      this.$confirm(this.notice, '提示', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.handleSave();
      }).catch(() => {
        this.load();
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    handleSave() {
      this.request.post("/user", this.form).then(res => {
        if (res.data) {
          this.$message.success(this.success);
          this.dialogFormVisible = false;
          this.load();
        } else {
          this.$message.error(this.error);
        }
      }).catch(reason => {
          console.log(reason.messages)
      })
    },
    handleAdd() {
      this.dialogFormVisible = true;
      this.title = '用户信息';
      this.ok = '保 存';
      this.success = '添加成功';
      this.error = '添加失败';
      this.form = {}
    },
    handleSelectionChange(val) {
      // console.log(val);
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
      this.request.delete("/user/" + id + "/2").then(res => {
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
      for (let i = 0; i < this.multipleSelection.length; i++) {
        if (this.multipleSelection[i].status !== 2) {
          this.multipleSelection[i].status = 2
        } else {
          this.$message.warning("用户" + this.multipleSelection[i].username + "已注销！！请勿重新注销！！");
        }
      }
      let data = this.multipleSelection.map(item => {
        return {id: item.id, status: item.status}
      });
      // console.log(data)
      this.request.post("/user/batch", data).then(res => {
        if (res.data) {
          this.$message.success("删除成功");
          this.load();
        } else {
          this.$message.error("删除失败");
        }
      })
      // console.log(this.multipleSelection)
    },
    exportExcel() {
      window.open("http://localhost:8080/user/export");
    },
    importExcelSuccess() {
        this.$message.success("导入成功");
        this.load();
    },
    importExcelError(){
      this.$message.error("导入失败");
    },
    reset() {
      this.username = "";
      this.email = "";
      this.address = "";
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
    },
    filterTag(value, row) {
      // console.log(value, row)
      return row.status === value;
    },
    filterSwitch(value, row) {
      // console.log(value, row)
      return row.onlineStatus === value;
    },
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