<template>
  <el-card style="width: 500px;border: 1px solid #ccc">
    <el-form :model="form" label-width="80px" size="small">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:8080/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          >
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off" clearable></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.nickname" autocomplete="off" clearable></el-input>
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
        <el-input  type="textarea" v-model="form.address" autocomplete="off" clearable></el-input>
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
      <el-form-item>
        <el-button type="primary" @click="handleSave">更 新</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "Person",
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
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
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      this.request.get("/user/person").then(res => {
        if (res.code === '200') {
          this.form = res.data;
          res.token = JSON.parse(localStorage.getItem("user")).token;
          localStorage.setItem("user",JSON.stringify(res));
        }
      })
    },
    handleSave() {
      this.request.post("/user", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("更新成功");
          // 触发父级获取最新数据
          this.$emit("refreshUser");
          this.load();
        } else if(res.code === '550'){
          this.$message.warning(res.msg);
        }else {
          this.$message.error("更新失败");
        }
      })
    },
    handleAvatarSuccess (res) {
      this.form.avatarUrl = res.data;
    }
  }
}
</script>

<style scoped>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 10px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 128px;
  height: 128px;
  line-height: 128px;
  text-align: center;
}

.avatar {
  width: 128px;
  height: 128px;
  display: block;
}
</style>