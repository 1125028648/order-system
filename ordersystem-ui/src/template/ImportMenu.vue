<template>
  <div>
    <Upload
      action="http://localhost:8088/menu/upload"
      :on-success="handleSuccess"
      :format="['jpg']"
      :on-format-error="handleFormatError"
    >
      <Alert type="error" style="width:400px">注：图片名必须和菜名一致,且仅支持jpg格式</Alert>
      <Button icon="ios-cloud-upload-outline">上传菜单图片</Button>
    </Upload>
    <div class="insertMenu">
      <Divider orientation="left" style="font-size:20px;">添加新菜</Divider>
      <div>
        <span style="font-size:16px">请输入菜名、单价和种类：</span>
        <Input
          v-model="insertMenu.menuName"
          placeholder="请输入新添的菜名"
          size="large"
          clearable
          style="width:200px"
        />
        <InputNumber v-model="insertMenu.price" size="large" :min="0" :step="0.1"/>
        <Select
          v-model="insertMenu.category"
          size="large"
          style="width:200px"
          placeholder="请选择菜品种类"
          transfer
        >
          <Option
            v-for="item in categoryList"
            :value="item.value"
            :key="item.value"
          >{{ item.label }}</Option>
        </Select>
        <Button @click="insert" type="primary" size="large">提交</Button>
      </div>
      <Divider orientation="left" style="font-size:20px;">删除菜单</Divider>
      <div>
        <span style="font-size:16px">删除单个菜品：</span>
        <Input
          v-model="deleteMenu.menuName"
          placeholder="请输入要删除的菜名"
          size="large"
          clearable
          style="width:200px"
        />
        <Button @click="deleteone" type="primary" size="large">提交</Button>
        <br>
      </div>
      <div style="margin-top: 20px;">
        <span style="font-size:16px">删除全部菜单：</span>
        <Button @click="modelState = true" type="error" size="large">删除全部菜单</Button>
        <Modal v-model="modelState" title="删除全部菜单" @on-ok="deleteAll" @on-cancel="cancel">
          <p style="font-size: 30px; color: red;">是否确认删除全部菜单?</p>
        </Modal>
      </div>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
export default {
  name: "ImportMenu",
  data() {
    return {
      insertMenu: {
        menuName: "",
        price: 0,
        category: ""
      },

      deleteMenu: {
        menuName: ""
      },

      categoryList: [
        {
          value: "entree",
          label: "主菜"
        },
        {
          value: "sidedish",
          label: "副菜"
        },
        {
          value: "drink",
          label: "饮品"
        }
      ],

      modelState: false
    };
  },

  methods: {
    handleSuccess(res) {
      if (res === true) {
        this.$Message.success("上传图片成功");
      } else {
        this.$Message.error("上传图片失败");
      }
    },

    handleFormatError(file) {
      this.$Message.warning("请上传jpg格式图片");
    },

    insert() {
      if (this.checkInsertMenu() === true) {
        this.$axios({
          method: "post",
          url: "/menu/insertMenu",
          data: this.insertMenu
        }).then(res => {
          if (res.data === true) {
            this.$Message.success("添加成功");
          } else {
            this.$Message.warning("请先添加图片或菜名已存在");
          }
        });
      }
    },

    deleteone() {
      if (this.checkDelete() === true) {
        this.$axios({
          method: "post",
          url: "/menu/deleteMenu",
          data: this.deleteMenu
        }).then(res => {
          if (res.data === true) {
            this.$Message.success("删除成功");
          } else {
            this.$Message.error("不存在此菜名！");
          }
        });
      }
    },

    deleteAll() {
      this.$axios({
        method: "post",
        url: "/menu/deleteAll"
      }).then(res => {
        if (res.data === true) {
          this.$Message.success("删除全部成功");
        } else {
          this.$Message.error("删除全部失败");
        }
      });
    },

    cancel() {
      this.$Message.info("已取消删除全部菜单");
    },

    //添加菜品校验
    checkInsertMenu() {
      if (this.insertMenu.menuName === "" || this.insertMenu.category === "") {
        this.$Message.warning("请输入菜品信息！");
        return false;
      } else {
        return true;
      }
    },

    //删除菜品校验
    checkDelete() {
      if (this.deleteMenu.menuName === "") {
        this.$Message.warning("请输入要删除的菜名！");
        return false;
      } else {
        return true;
      }
    }
  }
};
</script>

<style scoped>
.font {
  font-size: 20px;
  margin-top: 20px;
}

.insertMenu {
  text-align: left;
}
</style>