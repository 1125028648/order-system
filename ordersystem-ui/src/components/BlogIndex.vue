<template>
  <div class="layout">
    <Sider :style="{position: 'fixed', height: '100vh', left: 0, overflow: 'auto'}">
      <Menu active-name="1-2" theme="dark" width="auto" :open-names="['1']" @on-select="clickTo">
        <Submenu name="1">
          <template slot="title">
            <Icon type="ios-navigate"></Icon>菜单
          </template>
          <MenuItem name="checkMenu">查看总菜单</MenuItem>
          <MenuItem name="checkMenuByType">分类菜单</MenuItem>
        </Submenu>
        <Submenu name="2">
          <template slot="title">
            <Icon type="ios-keypad"></Icon>订单
          </template>
          <MenuItem name="checkOrder">查看预提交订单</MenuItem>
          <MenuItem name="checkHistoryOrder">查看历史订单</MenuItem>
        </Submenu>
        <Submenu name="3" v-if="this.role === 0">
          <template slot="title">
            <Icon type="ios-analytics"></Icon>管理员功能
          </template>
          <MenuItem name="importMenu">菜单管理</MenuItem>
          <MenuItem name="vipManage">会员管理</MenuItem>
          <MenuItem name="allOrder">查看所有订单</MenuItem>
        </Submenu>
      </Menu>
    </Sider>
    <Layout :style="{marginLeft: '200px'}">
      <Header
        :style="{background: '#fff',boxShadow: '0 2px 3px 2px rgba(0,0,0,.1)', fontSize: '20px', textAlign: 'right'}"
      >
        欢迎，{{ username }}
        <Avatar :src="'./static/VIP.jpg'" size="large" v-if="role != 1"/>
        <Button @click="updateUser" type="primary">个人信息修改</Button>
        <Modal v-model="updateUserStatus" title="修改信息">
          <div style="text-align: center">
            <Input
              type="text"
              size="large"
              v-model="updateUserText.phone"
              clearable
              placeholder="请输入电话"
              class="myinput"
            />
            <br>
            <Input
              type="text"
              size="large"
              v-model="updateUserText.email"
              clearable
              placeholder="请输入邮箱"
              class="myinput"
            />
          </div>
          <div slot="footer">
            <Button type="text" size="large" @click="cancel">取消</Button>
            <Button type="primary" size="large" @click="ok">确定</Button>
          </div>
        </Modal>
        <Button @click="updatePassword" type="success">密码修改</Button>
        <Modal v-model="updatePasswordStatus" title="修改密码">
          <div style="text-align: center">
            <Input
              type="password"
              size="large"
              v-model="updatePasswordText.oldPassword"
              clearable
              placeholder="请输入旧密码"
              class="myinput"
            />
            <br>
            <Input
              type="password"
              size="large"
              v-model="updatePasswordText.newPassword"
              clearable
              placeholder="请输入新密码"
              class="myinput"
            />
            <br>
            <Input
              type="password"
              size="large"
              v-model="updatePasswordText.newPassword_confirm"
              clearable
              placeholder="请确认新密码"
              class="myinput"
            />
          </div>
          <div slot="footer">
            <Button type="text" size="large" @click="cancelUpdatePassword">取消</Button>
            <Button type="primary" size="large" @click="okUpdatePassword">确定</Button>
          </div>
        </Modal>
        <Button @click="exit" type="error">退出</Button>
      </Header>
      <Content :style="{padding: '0 16px 16px'}">
        <div>
          <ButtonGroup shape="circle" :style="{margin: '16px 0'}" size="large">
            <Button type="text" @click="toMenu">
              <Icon type="ios-arrow-back"></Icon>Menu
            </Button>
            <Button type="text" @click="toOrder">
              Order
              <Icon type="ios-arrow-forward"></Icon>
            </Button>
          </ButtonGroup>
        </div>
        <Card class="indexCard">
          <div>
            <orderMenu v-if="this.page === 'checkMenu'" @orderMenu="order"></orderMenu>
            <orderMenuByType v-else-if="this.page === 'checkMenuByType'" @orderMenu="order"></orderMenuByType>
            <orderList v-else-if="this.page === 'checkOrder'" :message="order_menu"></orderList>
            <orderHistory v-else-if="this.page === 'checkHistoryOrder'"></orderHistory>
            <importMenu v-else-if="this.page === 'importMenu'"></importMenu>
            <vipManage v-else-if="this.page === 'vipManage'"></vipManage>
            <allOrder v-else-if="this.page === 'allOrder'"></allOrder>
          </div>
        </Card>
      </Content>
    </Layout>
  </div>
</template>

<style scoped>
.layout {
  border: 1px solid #d7dde4;
  background: #f5f7f9;
  position: relative;
  border-radius: 4px;
  overflow: hidden;
}
.layout-header-bar {
  background: #fff;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
}

.indexCard {
  display: inline-block;
  height: auto;
  min-width: 1200px;
  min-height: 200px;
}

.myinput {
  width: 50% !important;
  margin-bottom: 5%;
}
</style>

<script>
/* eslint-disable */
import orderMenu from "@/template/OrderMenu.vue";
import orderList from "@/template/OrderList.vue";
import orderHistory from "@/template/OrderHistory.vue";
import importMenu from "@/template/ImportMenu.vue";
import vipManage from "@/template/VipManage.vue";
import orderMenuByType from "@/template/OrderMenuByType.vue";
import allOrder from "@/template/AllOrder.vue";

export default {
  name: "BlogIndex",
  components: {
    orderMenu,
    orderList,
    orderHistory,
    importMenu,
    vipManage,
    orderMenuByType,
    allOrder
  },
  data() {
    return {
      username: "",
      role: "",
      page: "checkMenu",
      order_menu: [],
      updateUserStatus: false,
      updateUserText: {
        username: "",
        phone: "",
        email: ""
      },
      updatePasswordStatus: false,
      updatePasswordText: {
        oldPassword: "",
        newPassword: "",
        newPassword_confirm: ""
      }
    };
  },

  created() {
    //   this.username = sessionStorage.getItem('username');
    //   this.role = sessionStorage.getItem('role');
    //   if(this.username === null) {
    //        this.$Message.warning("未登录");
    //        this.$router.replace({ path: "/login" });
    //   }

    this.$axios({
      method: "get",
      url: "/user/checkLogin"
    }).then(res => {
      if (res.data !== "") {
        this.username = res.data.username;
        this.role = res.data.role;
        sessionStorage.setItem("username", this.username);
        sessionStorage.setItem("role", this.role);

        //订单验证
        let number = this.getUrlParam("out_trade_no");
        if (number !== "") {
          this.$axios({
            method: "get",
            url: "/ordermenu/selectAli" + "?" + "number=" + number
          });
        }
      } else {
        this.$Message.warning("未登录");
        this.$router.replace({ path: "/login" });
      }
    });
  },

  methods: {
    //获取参数
    getUrlParam(paraName) {
      var url = document.location.toString();
      var arrObj = url.split("?");

      if (arrObj.length > 1) {
        var arrPara = arrObj[1].split("&");
        var arr;

        for (var i = 0; i < arrPara.length; i++) {
          arr = arrPara[i].split("=");

          if (arr != null && arr[0] == paraName) {
            return arr[1];
          }
        }
        return "";
      } else {
        return "";
      }
    },

    clickTo(name) {
      this.page = name;
    },

    exit() {
      this.$axios({
        method: "get",
        url: "/user/exit"
      }).then(res => {
        sessionStorage.removeItem("username");
        sessionStorage.removeItem("role");
        this.$router.replace({ path: "/login" });
      });
    },

    order(menu) {
      this.order_menu = menu;
    },

    toMenu() {
      this.page = "checkMenu";
    },

    toOrder() {
      this.page = "checkOrder";
    },

    //获取用户信息
    updateUser() {
      this.$axios({
        method: "get",
        url: "/user/information" + "?" + "username=" + this.username
      }).then(res => {
        this.updateUserText = res.data;
        this.updateUserStatus = true;
      });
    },

    //修改用户信息
    ok() {
      if (this.checkPhone() === true) {
        if (this.checkMail() === true) {
          this.$axios({
            method: "post",
            url: "/user/updateInformation",
            data: this.updateUserText
          }).then(res => {
            this.updateUserStatus = false;
            this.$Message.success("信息修改成功！");
          });
        }
      }
    },

    cancel() {
      this.updateUserStatus = false;
      this.$Message.info("取消修改");
    },

    //修改密码
    updatePassword() {
      this.updatePasswordStatus = true;
    },

    okUpdatePassword() {
      if (this.checkOldPassword() === true) {
        let text = {
          username: this.username,
          password: this.updatePasswordText.oldPassword
        };
        this.$axios({
          method: "post",
          url: "/user/checkPassword",
          data: text
        }).then(res => {
          console.log("haha"+res.data);
          if (res.data === true) {
            if (this.checkPassword() === true) {
              text.password = this.updatePasswordText.newPassword;
              this.$axios({
                method: "post",
                url: "/user/updateInformation",
                data: text
              }).then(res => {
                this.updatePasswordStatus = false;
                this.$Message.success("密码修改成功");
              });
            }
          } else {
            this.$Message.error("输入原密码错误！");
          }
        });
      }
    },

    cancelUpdatePassword() {
      this.updatePasswordStatus = false;
      this.updatePasswordText.oldPassword = "";
      this.updatePasswordText.newPassword = "";
      this.updatePasswordText.newPassword_confirm = "";
      this.$Message.info("已取消密码修改");
    },

    //邮箱判断
    checkMail() {
      var reg = new RegExp(
        "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"
      );
      if (this.updateUserText.email === "") {
        this.$Message.warning("请输入邮箱！");
        return false;
      } else if (!reg.test(this.updateUserText.email)) {
        this.$Message.error("请输入正确的邮箱！");
        return false;
      } else {
        return true;
      }
    },

    //电话判断
    checkPhone() {
      if (this.updateUserText.phone === "") {
        this.$Message.warning("请输入电话！");
        return false;
      } else {
        return true;
      }
    },

    //原密码校验
    checkOldPassword() {
      if (this.updatePasswordText.oldPassword === "") {
        this.$Message.warning("请输入原密码！");
        return false;
      } else {
        return true;
      }
    },

    //新密码校验
    checkPassword() {
      if (this.updatePasswordText.newPassword.length < 6) {
        this.$Message.warning("密码必须大于等于6位!");
        return false;
      } else if (
        this.updatePasswordText.newPassword !==
        this.updatePasswordText.newPassword_confirm
      ) {
        this.$Message.warning("请再次确认新密码!");
        return false;
      } else {
        return true;
      }
    }
  }
};
</script>
