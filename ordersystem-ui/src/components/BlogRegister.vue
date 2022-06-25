<template>
  <div class="register">
    <Card class="card">
      <span class="title">注 册</span>
      <br>
      <Input
        type="text"
        size="large"
        v-model="register.username"
        clearable
        placeholder="请输入用户名"
        class="myinput"
      />
      <br>
      <Input
        type="text"
        size="large"
        v-model="register.phone"
        clearable
        placeholder="请输入电话"
        class="myinput"
      />
      <br>
      <Input
        type="text"
        size="large"
        v-model="register.email"
        clearable
        placeholder="请输入邮箱"
        class="myinput"
      />
      <br>
      <Input
        type="password"
        size="large"
        v-model="register.password"
        clearable
        placeholder="请输入大于等于6位的密码"
        class="myinput"
      />
      <br>
      <Input
        type="password"
        size="large"
        v-model="register.password_confirm"
        clearable
        placeholder="请确认密码"
        class="myinput"
        @keyup.enter.native="registerConfirm"
      />
      <br>
      <Button @click="registerConfirm" style="margin-top:5%" size="large">确认</Button>
      <Button @click="cancel" style="margin-top:5%; margin-left:5%" size="large">取消</Button>
    </Card>
    <!-- <hr>
    <pageFoot></pageFoot> -->
  </div>
</template>

<script>
/* eslint-disable */
// import pageFooter from "@/template/PageFooter.vue";
// import { log } from 'util';

export default {
  name: "BlogRegister",
  // components: { pageFooter },
  data() {
    return {
      register: {
        username: "",
        phone: "",
        email: "",
        password: "",
        password_confirm: ""
      },
      responseResult: []
    };
  },
  methods: {
    registerConfirm() {
      if (this.checkUsername() === true) {
        if (this.checkPhone() === true) {
          if (this.checkMail() === true) {
            if (this.checkPassword() === true) {
              this.$axios({
                method: "post",
                url: "/user/register",
                data: this.register
              }).then(res => {
                if (res.data === true) {
                  this.$Message.success("注册成功");
                  this.$router.replace({ path: "/login" });
                } else {
                  this.register.username = "";
                  this.$Message.warning("用户名已存在！");
                }
              });
            }
          }
        }
      }
    },

    cancel() {
      this.$router.replace({ path: "/login" });
    },

    //邮箱判断
    checkMail() {
      var reg = new RegExp(
        "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"
      );
      if (this.register.email === "") {
        this.$Message.warning("请输入邮箱！");
        return false;
      } else if (!reg.test(this.register.email)) {
        this.$Message.error("请输入正确的邮箱！");
        return false;
      } else {
        return true;
      }
    },

    //电话判断
    checkPhone() {
      if (this.register.phone === "") {
        this.$Message.warning("请输入电话！");
        return false;
      } else {
        return true;
      }
    },

    //密码判断
    checkPassword() {
      if (this.register.password.length < 6) {
        this.$Message.warning("密码必须大于等于6位!");
        return false;
      } else if (this.register.password !== this.register.password_confirm) {
        this.$Message.warning("请再次确认密码!");
        return false;
      } else {
        return true;
      }
    },

    //用户名判断
    checkUsername() {
      if (this.register.username === "") {
        this.$Message.warning("请输入用户名！");
        return false;
      } else {
        return true;
      }
    }
  }
};
</script>

<style scoped>
.register {
  /* background-color: #74d8dc; */
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
  margin-top: 5%;
}

.title {
  font-size: 20px;
  /* letter-spacing: 20px; */
  font-weight: 800;
  word-spacing: 20px;
}
</style>