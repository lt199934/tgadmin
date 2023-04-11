<template>
    <div class="wrapper">
        <div class="form">
            <div style="margin: 20px auto;text-align: center;font-size: 24px"><b>登 录</b></div>
            <el-form :model="user" :rules="rules" ref="user">
                <el-form-item prop="username">
                    <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" clearable
                              v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" clearable show-password
                              v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-input
                            v-model="user.code"
                            auto-complete="off"
                            placeholder="验证码"
                            maxlength="4"
                            clearable
                            style="width: 45%"
                    >
                    </el-input>
                    <div class="login-code">
                        <img id="code" src="http://localhost:8082/captcha/code" alt="加载失败" @click="getCode"
                             class="login-code-img"/>
                    </div>
                </el-form-item>
                <el-form-item style="margin: 10px 0;text-align: right">
                    <el-checkbox style="float: left;color: #fff">记住我</el-checkbox>
                    <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
                    <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">注册
                    </el-button>
                </el-form-item>
            </el-form>

        </div>
    </div>
</template>

<script>
import {setRoutes} from "@/router";

export default {
    name: `Login`,
    data() {
        return {
            btn:'获取验证码',
            time : 60,
            buttonDisabled : false,
            user: {
                username: 'admin',
                password: 'admin',
                code: '',
            },
            rules: {
                username: [
                    {required: true, message: '请输入用户名', trigger: 'blur'},
                    {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 3, max: 6, message: '长度在 3 到 5 个字符', trigger: 'blur'}
                ],
                code: [
                    {required: true, message: '验证码为空', trigger: 'blur'},
                ]
            }
        }
    },
    created() {
       this.getCode();
    },
    methods: {
        getCode() {
            document.getElementById("code").src = "http://localhost:8082/captcha/code?time=" + new Date().getTime();
        },
        login() {
            this.$refs['user'].validate((valid) => {
                if (valid) {
                    this.request.post("/user/login", this.user).then(res => {
                        if (res.code === '200') {
                            localStorage.setItem("user", JSON.stringify(res.data));
                            localStorage.setItem("menus", JSON.stringify(res.data.menus));
                            // 当前用户的动态路由
                            setRoutes();
                            this.$router.push('/');
                            this.$message.success("登录成功");
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }
            });

        }
    }
}
</script>

<style lang="scss">
.wrapper {
    width: 100%;
    height: 100vh;
    background-image: linear-gradient(15deg, #13547a 0%, #80d0c7 100%);
    overflow: hidden;
}
.form {
    margin: 200px auto;
    /*background-color: #fff;*/
    width: 350px;
    height: 43%;
    padding: 20px;
    border-radius: 10px;
    /*opacity: 0.5;*/
    /* 为其整体设置接近透明的效果*/
    background-color: rgba(255, 255, 255, 0.1);
    /* 设置box-shadow使其有立体感 */
    box-shadow: 5px 5px 0 0 rgba(0, 0, 0, 0.2);
}

.login-code {
    width: 33%;
    height: 38px;
    float: right;

    img {
        cursor: pointer;
        vertical-align: middle;
    }
}

.login-code img {
    width: 100%;
    height: 38px;
}

.el-textarea__inner, .el-input__inner {
    background: transparent !important;
}
.el-checkbox__input.is-checked {
    color: red !important;
}
.el-input input {
    color: #fff !important;
}

</style>