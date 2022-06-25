<template>
    <div class="font">
        <div style="margin:20px auto">
            <span>设置会员折扣：</span>
            <InputNumber :max="10" :min="0" :step="0.1" v-model="discount" size="large"></InputNumber>
            <Button @click="submitDiscount" type="primary" size="large">提交</Button>
        </div>
        <Divider />
        <Table border :columns="columns" :data="userList" width="800" style="margin:0 auto"></Table>
    </div>
</template>

<script>
/* eslint-disable */
export default {
    name: 'VipManage',
     data() {
        return {
            columns: [
                {
                    title: '用户名',
                    key: 'username'
                },
                {
                    title: '电话',
                    key: 'phone'
                },
                {
                    title: '邮箱',
                    key: 'email'
                },
                {
                    title: '操作',
                    key: 'action',
                    align: 'center',
                    render: (h,params) => {
                        return h('i-switch',{
                            props: {
                                type: 'primary',
                                value: params.row.role === 2
                            },
                            on:{
                                'on-change': (value) => {
                                    this.switch(params.index)
                                }
                            }
                        },'Delete')
                    }
                }
            ],

            userList: [],
            discount: 0,
        }
    },

    created(){
        this.$axios({
            method: 'post',
            url: '/user/findAll'
        }).then(res =>{
            this.userList = res.data;
        });
    },

    mounted(){
        this.$axios({
            method: 'post',
            url: '/discount/selectOne'
        }).then(res=>{
            this.discount = res.data;
        })
    },

    methods: {
        switch (index) {
           if(this.userList[index].role === 1){
               this.userList[index].role = 2;
               this.updateRole(this.userList[index]);
           }else{
               this.userList[index].role = 1;
               this.updateRole(this.userList[index]);
           }
        },

        updateRole(user){
            this.$axios({
                method: 'post',
                url: '/user/updateRole',
                data: user
            }).then(res =>{
                if(res.data === true){
                    this.$Message.success("用户 "+user.username+" 身份修改成功！");
                }
            })
        },

        submitDiscount(){
            this.$axios({
                method: 'get',
                url: '/discount/insertOne'+'?'+'discount='+this.discount
            }).then(res =>{
                if(res.data === true){
                    this.$Message.success("折扣修改成功！");
                }
            })
        }
    }
}
</script>

<style scoped>
    .font {
        font-size: 20px;
        margin-top: 20px;
    }
</style>