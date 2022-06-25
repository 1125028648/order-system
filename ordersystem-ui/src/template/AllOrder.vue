<template>
  <div>
    <div style="margin-bottom: 20px;text-align: left">
      <DatePicker
        type="datetime"
        placeholder="Select date and time"
        style="width: 200px"
        size="large"
        @on-change="timeChange"
        transfer
      ></DatePicker>
      <Button @click="timeSubmit" type="primary" size="large">确认</Button>
    </div>
    <Table border :columns="columns" :data="allOrder" width="1200" max-height="600"></Table>
  </div>
</template>

<script>
/* eslint-disable */
export default {
  name: "OrderHistory",
  data() {
    return {
      columns: [
        {
          title: "订单号",
          key: "orderNumber"
        },
        {
          title: "菜名",
          key: "menuName"
        },
        {
          title: "数量",
          key: "number"
        },
        {
          title: "下单时间",
          key: "createTime"
        },
        {
          title: "用户名",
          key: "username"
        },
        {
          title: "预定就餐时间",
          key: "mealTime"
        },
        {
          title: "订单总价(￥)",
          key: "orderTotal"
        },
        {
          title: "支付状态",
          key: "payState"
        }
      ],

      allOrder: [],
      selectData: ""
    };
  },

  created() {
    this.$axios({
      method: "get",
      url: "/ordermenu/checkAllOrder"
    }).then(res => {
      for (let i = 0; i < res.data.length; i++) {
        if (res.data[i].payState === 0) {
          res.data[i].payState = "未支付";
        } else {
          res.data[i].payState = "已支付";
        }
      }

      this.allOrder = res.data;
    });
  },

  methods: {
    timeChange(data) {
      this.selectData = data;
    },

    timeSubmit() {
      this.$axios({
        method: "get",
        url: "/ordermenu/checkAllOrderAndTime" + "?" + "data=" + this.selectData
      }).then(res => {
        for (let i = 0; i < res.data.length; i++) {
          if (res.data[i].payState === 0) {
            res.data[i].payState = "未支付";
          } else {
            res.data[i].payState = "已支付";
          }

          this.allOrder = res.data;
        }
      });
    }
  }
};
</script>