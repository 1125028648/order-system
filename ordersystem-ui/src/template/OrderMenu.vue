<template>
    <div>
        <div  style="text-align:right">
            <Button @click="submit" type="primary">确定</Button>
        </div>
        <Card class="menuCard" v-for="(one_menu,index) in menu" :key="index">
            <img :src="one_menu.picture" style="width:200px;height:150px;">
            <br>
            <span class="font">
                菜名：{{ one_menu.menuName }}
            </span>
            <br>
            <span class="font">
                价格： {{ one_menu.price }}￥
            </span>
            <br>
            <span class="font">
                数量：<InputNumber v-model="menu[index].number" size="large" :max="10" :min="0" value="0" style="width: 60px"></InputNumber>
            </span>
        </Card>
    </div>
</template>

<script>
/* eslint-disable */
export default {
    name: 'OrderMenu',
    data() {
        return {
            menu: [],
            submitMenu:[],
            host: "http://localhost:8088",
        }
    },

    methods: {
        submit() {
            for(let i=0;i<this.menu.length;i++){
                if(this.menu[i].number !== 0){
                    this.submitMenu.push(this.menu[i]);
                }
            }

            if(this.submitMenu.length === 0){
                this.$Message.warning("请点菜！");
            }else{
                this.$emit('orderMenu',this.submitMenu);
            this.$Message.success("已生成订单");
            }
        }
    },

    created() {
        this.$axios({
            method:'post',
            url:'/menu/check_all'
        }).then(res => {
            this.menu = res.data;
            for(let i=0;i<this.menu.length;i++){
                this.menu[i].number = 0;
                this.menu[i].picture = this.host+"/"+this.menu[i].picture;
            }
        })
    }
}
</script>

<style scoped>
    .font {
        font-size: 20px;
        margin-top: 20px;
    }

    .menuCard {
        height:300px; 
        width:280px;
        float:left;
        background-color: #eeeeee;
        margin: 15px 15px;
        text-align: center;
    }
</style>