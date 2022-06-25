<template>
  <div class="font">
    <div style="text-align: left;margin-bottom: 20px">
      <span>请选择就餐时间：</span>
      <DatePicker
        type="datetime"
        :options="optionsOne"
        placeholder="Select date and time"
        style="width: 200px"
        size="large"
        @on-change="timeChange"
        transfer
      ></DatePicker>
    </div>
    <Table border :columns="columns" :data="message" width="1200"></Table>
    <Alert>总价：{{ total }}￥</Alert>
    <Alert type="success" v-if="role != 1">会员价：{{ vipTotal }}￥</Alert>
    <Button @click="modalStatusChange" type="primary" style="margin-top:20px;">付款</Button>
    <Modal v-model="modalStatus" title="付款" @on-ok="ok" @on-cancel="cancel">
      <div style="text-align:center;">
        <span style="font-size: 20px">是否确认付款？</span>
      </div>
    </Modal>
    <Button @click="clearList" type="primary" style="margin-top:20px;">清空</Button>
  </div>
</template>

<script>
/* eslint-disable */
export default {
  name: "OrderList",
  props: ["message"],
  data() {
    return {
      columns: [
        {
          title: "菜名",
          key: "menuName"
        },
        {
          title: "单价(￥)",
          key: "price"
        },
        {
          title: "数量",
          key: "number"
        },
        {
          title: "操作",
          key: "action",
          width: 150,
          align: "center",
          render: (h, params) => {
            return h(
              "Button",
              {
                props: {
                  type: "error",
                  size: "small"
                },
                on: {
                  click: () => {
                    this.remove(params.index);
                  }
                }
              },
              "Delete"
            );
          }
        }
      ],

      optionsOne: {
        disabledDate(date) {
          return date && date.valueOf() < Date.now();
        }
      },

      total: 0,
      vipTotal: 0,
      role: "",
      discount: 0,
      orderData: [],

      modalStatus: false,
      mealTime: "",

      alipayOrder: {
        widout_trade_no: "",
        widsubject: "手机网站支付测试商品",
        widtotal_amount: "",
        widbody: "购买测试商品"
      }
    };
  },

  created() {
    for (let i = 0; i < this.message.length; i++) {
      this.total = this.total + this.message[i].price * this.message[i].number;
    }

    this.role = sessionStorage.getItem("role");
    if (this.role !== 1) {
      this.$axios({
        method: "post",
        url: "/discount/selectOne"
      }).then(res => {
        this.discount = res.data;
        this.vipTotal = ((this.total * this.discount) / 10).toFixed(2);
      });
    }
  },

  methods: {
    formatTen(num) {
      return num > 9 ? num + "" : "0" + num;
    },

    formatDate(date) {
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var hour = date.getHours();
      var minute = date.getMinutes();
      var second = date.getSeconds();
      return (
        year +
        "-" +
        this.formatTen(month) +
        "-" +
        this.formatTen(day) +
        " " +
        this.formatTen(hour) +
        ":" +
        this.formatTen(minute) +
        ":" +
        this.formatTen(second)
      );
    },

    remove(index) {
      this.message.splice(index, 1);
      this.total = 0;
      for (let i = 0; i < this.message.length; i++) {
        this.total =
          this.total + this.message[i].price * this.message[i].number;
      }

      if (this.role != 1) {
        this.vipTotal = ((this.total * this.discount) / 10).toFixed(2);
      }
    },

    modalStatusChange() {
      this.orderData = this.message;
      var selectTime = this.formatDate(new Date());

      if (this.orderData.length === 0) {
        this.$Message.warning("请先点菜！");
      } else if (this.mealTime === "") {
        this.$Message.warning("请先选择就餐时间！");
      } else if (this.mealTime < selectTime){
        this.$Message.warning("请选择正确的时间！");
      } else {
        this.modalStatus = true;
      }
    },

    ok() {
      //生成订单号
      let username = sessionStorage.getItem("username");

      let date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      var hour = date.getHours();
      var minute = date.getMinutes();
      var second = date.getSeconds();

      let orderNumber =
        "" +
        year +
        this.formatTen(month) +
        this.formatTen(day) +
        this.formatTen(hour) +
        this.formatTen(minute) +
        this.formatTen(second);

      for (let i = 0; i < username.length; i++) {
        orderNumber += username.charAt(i).charCodeAt();
      }

      //保存到数据库
      var time = this.formatDate(new Date());
      if (this.role != 1) {
        this.alipayOrder.widtotal_amount = this.vipTotal;
      } else {
        this.alipayOrder.widtotal_amount = this.total;
      }

      for (let i = 0; i < this.message.length; i++) {
        this.orderData[i].username = sessionStorage.getItem("username");
        this.orderData[i].createTime = time;
        this.orderData[i].payState = 0;
        this.orderData[i].orderNumber = orderNumber;
        this.orderData[i].mealTime = this.mealTime;
        this.orderData[i].orderTotal = this.alipayOrder.widtotal_amount;
      }

      this.$axios({
        method: "post",
        url: "/ordermenu/save",
        data: this.orderData
      }).then(res => {
        if (res.data === false) {
          this.$Message.error("数据异常");
        } else {
          //支付模拟
          this.alipayOrder.widout_trade_no = orderNumber;

          this.$axios({
            method: "post",
            url: "/ordermenu/alipay",
            data: this.alipayOrder
          }).then(res => {
            // var w = window.open('about:blank');
            // w.document.write(res.data);
            // w.document.close();
            // w.print();
            document.write(res.data);
            document.close();
          });
        }
      });
    },

    cancel() {
      this.$Message.info("已取消付款");
    },

    //清空
    clearList() {
      this.message = [];
      this.total = 0;
      this.vipTotal = 0;
    },

    timeChange(data) {
      this.mealTime = data;
    }
  }
};
</script>

<style scoped>
.font {
  font-size: 20px;
  margin-top: 20px;
}
</style>