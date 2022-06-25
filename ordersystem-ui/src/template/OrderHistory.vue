<template>
    <div>
        <div style="margin-bottom: 20px;text-align: left">
            <DatePicker type="datetime" placeholder="Select date and time" style="width: 200px" size="large" @on-change="timeChange" transfer></DatePicker>
            <Button @click="timeSubmit" type="primary" size="large">确认</Button>
        </div>
        <Table border :columns="columns" :data="orderHistoryList" width="1200" max-height="600"></Table>
    </div>
</template>

<script>
/* eslint-disable */
export default {
    name: 'OrderHistory',
    data() {
        return {
            columns: [
                {
                    title: '菜名',
                    key: 'menuName'
                },
                {
                    title: '数量',
                    key: 'number'
                },
                {
                    title: '下单时间',
                    key: 'createTime'
                },
                {
                    title: '预定就餐时间',
                    key: 'mealTime'
                },
                {
                    title: '订单号',
                    key: 'orderNumber'
                },
                {
                    title: '订单总价(￥)',
                    key: 'orderTotal'
                }
            ],

            orderHistoryList:[],
            username:"",
            selectData: ""
        }
    },

    created() {
        this.username = sessionStorage.getItem('username');
        
        this.$axios({
            method: 'get',
            url: '/ordermenu/checkHistoryOrder'+'?'+'username='+this.username
        }).then(res=>{
            this.orderHistoryList = res.data;
        })
    },

    methods: {
        timeChange(data) {
            this.selectData = data;
        },

        timeSubmit() {
            this.$axios({
                method: 'get',
                url: '/ordermenu/checkHistoryOrderAndTime'+'?'+'username='+this.username+'&data='+this.selectData
            }).then(res=>{
                this.orderHistoryList = res.data;
            })
        }
    }
}
</script>