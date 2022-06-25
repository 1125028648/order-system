<template>
  <div class="login">
    <Card class="card">
      <span class="title">订 餐 系 统</span>
      <br>
      <Input
        type="text"
        size="large"
        v-model="login.username"
        clearable
        placeholder="请输入用户名"
        class="myinput"
      />
      <br>
      <Input
        type="password"
        size="large"
        v-model="login.password"
        placeholder="请输入密码"
        clearable
        class="myinput"
        @keyup.enter.native="loginConfirm"
      />
      <br>
      <Button @click="loginConfirm" style="margin-top:5%" size="large">登录</Button>
      <Button @click="register" style="margin-top:5%; margin-left:5%" size="large">注册</Button>
    </Card>
    <!-- <hr>
    <pageFoot></pageFoot> -->
  </div>
</template>

<script>
/* eslint-disable */
// import pageFooter from "@/template/PageFooter.vue";
import { log } from "util";

export default {
  name: "BlogLogin",
  // components: { pageFooter },
  data() {
    return {
      login: {
        username: "",
        password: ""
      },
      responseResult: []
    };
  },
  methods: {
    loginConfirm() {
      if (this.checkUsername() === true) {
        if (this.checkPassword() === true) {
          this.$axios({
            method: "post",
            url: "/user/login",
            data: this.login
          }).then(res => {
            if (res.data !== "") {
              this.$Message.success("登录成功");
              this.$router.replace({ path: "/index" });
            } else {
              // Alert("用户名或密码错误");
              this.login.password = "";
              this.$Message.error("用户名或密码错误");
            }
          });
        }
      }
    },

    register() {
      this.$router.replace({ path: "/register" });
    },

    //用户名校验
    checkUsername() {
      if (this.login.username === "") {
        this.$Message.warning("请输入用户名！");
        return false;
      } else {
        return true;
      }
    },

    //密码校验
    checkPassword() {
      if (this.login.password === "") {
        this.$Message.warning("请输入密码！");
        return false;
      } else {
        return true;
      }
    }
  }
};
</script>

<style scoped>
.title {
  font-size: 20px;
  /* letter-spacing: 20px; */
  font-weight: 800;
  word-spacing: 20px;
}

.login {
  /* background: #609bd7; */
  background: url('../../static/03.jpg');
  background-size: 100% 100%;
  height: 100%;
  width: 100%;
  position: fixed;
}

.card {
  width: 30%;
  margin: 10% auto;
}

.myinput {
  width: 50% !important;
  margin-top: 10%;
}
</style>