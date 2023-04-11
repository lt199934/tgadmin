<template>
  <el-card style="width: 500px;border: 1px solid #ccc">
    <div slot="header" class="clearfix">
      <span>修改密码</span>
    </div>
    <el-form :model="form" :rules="rules" ref="form" label-width="120px" size="small">
      <el-form-item prop="oldPwd" label="旧密码">
        <el-input size="medium"  placeholder="请输入旧密码" prefix-icon="el-icon-lock" aria-required="true" show-password clearable v-model="form.oldPwd"></el-input>
      </el-form-item>
      <el-form-item prop="newPwd" label="新密码" >
        <el-input size="medium" style="margin: 10px 0" placeholder="请输入新密码" prefix-icon="el-icon-lock" aria-required="true" clearable show-password v-model="form.newPwd"></el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword" label="确认新密码">
        <el-input size="medium" placeholder="请确认新密码" prefix-icon="el-icon-lock" aria-required="true" clearable show-password v-model="form.confirmPassword"></el-input>
      </el-form-item>
      <div style="margin: 10px 0;text-align: center">
        <el-button type="primary" size="small" autocomplete="off" @click="save">修改密码</el-button>
        <el-button type="warning" size="small" autocomplete="off" @click="reset">重置</el-button>
      </div>
    </el-form>
  </el-card>

</template>

<script>
import router from "@/router";

export default {
  name: "Upwd",
  data() {
    return{
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      form: {},
      rules: {
        oldPwd: [
          {required: true, message: '请输入旧密码', trigger: 'blur'},
          {min: 3, max: 6, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        newPwd: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
          {min: 3, max: 6, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '请确认新密码', trigger: 'blur'},
          {min: 3, max: 6, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    save (){
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if(this.form.newPwd !== this.form.confirmPassword) {
            this.$message.error("两次密码不一致");
            return false;
          }
          this.form.id = this.user.id;
          console.log(this.form)
          this.request.post("/user/upPwd",this.form).then(res => {
            if (res.code === '200') {
              localStorage.setItem("user",JSON.stringify(res.data));
              this.$message.success("密码修改成功");
              router.push("/login")
            }else {
              this.$message.error(res.msg);
            }
          })
        }
      });

    },
    reset() {
      this.form = {}
    }
  }
}
</script>

<style scoped>
  .wrapper {
    height: 100vh;
    background-image: linear-gradient(0.25turn, #3f87a6, #ebf8e1, #f69d3c);
    overflow: hidden;
  }
</style>