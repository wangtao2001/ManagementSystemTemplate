<template>
  <div class="table">
    <!--功能区-->
    <div class="op">
      <el-button type="primary" size="medium" @click="add">新增</el-button>
      <el-button type="primary" size="medium" >导入</el-button>
      <el-button type="primary" size="medium" >导出</el-button>
    </div>
    <!--搜索区-->
    <div class="search" style="display: flex; align-items: center">
      <el-input v-model="keyword" placeholder="昵称关键字" style="width: 30%;" clearable />
      <el-button type="primary" style="margin-left: 10px;" @click="load" size="medium" >查询</el-button>
      <el-button type="primary" style="margin-left: 10px;" size="medium"  @click="clear">重置</el-button>
    </div>
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" sortable />
      <el-table-column prop="username" label="用户名" width="180" />
      <el-table-column prop="nickname" label="昵称" width="180" />
      <el-table-column prop="age" label="年龄" width="80" />
      <el-table-column prop="phoneNumber" label="电话号码" width="180" />
      <el-table-column prop="sex" label="性别" width="80" />
      <el-table-column prop="admin" width="80" label="管理员" />
      <el-table-column prop="address" label="地址" />
      <el-table-column label="操作" width="100" fixed="right">
        <template #default="scope">
          <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除该用户吗?" @confirm="handleDelete(scope.row)">
            <template #reference>
              <el-button type="text" style="margin-left: 4px">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <div style="margin: 10px 0;">
      <el-pagination
          @size-change="load"
          @current-change="load"
          :current-page.sync="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size.sync="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <!--新增与修改用户-->
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
        top="3vh">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input :disabled="usernameDisable" v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-slider v-model="form.age"></el-slider>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="form.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.sex" label="0">女</el-radio>
          <el-radio v-model="form.sex" label="1">男</el-radio>
          <el-radio v-model="form.sex" label="2">未知</el-radio>
        </el-form-item>
        <el-form-item label="管理员">
          <el-switch
              v-model="form.admin"
              active-text="是"
              inactive-text="否">
          </el-switch>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
     </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";
import {
  parseAdmin2BoolCopy,
  parseAdmin2IntCopy,
  parseArrayInt2Admin,
  parseArrayInt2Sex,
  parseSex2IntCopy
} from "@/utils/parse";

export default {
  name: "Home",
  created() {
    this.load();
  },
  methods: {
    // 取消表单
    cancel(){
      this.form = {}
      this.dialogVisible = false
    },
    // 重置
    clear(){
      this.keyword = "";
      this.load();
    },
    // 编辑
    handleEdit(row) {
      this.usernameDisable = true; // 不允许更改用户名
      console.log(this.usernameDisable)
      this.form = parseAdmin2BoolCopy( parseSex2IntCopy(row));
      this.dialogVisible = true
    },
    // 删除
    handleDelete(row) {
      axios({
        method: "post",
        url: "/api/user/delete",
        data: {
          id: row.id
        }
      }).then(res => {
        res = res.data;
        if (res.code === "200" ) {
          this.$message.success("删除成功")
          this.load();
        } else {
          this.$message.error(res.msg);
        }
      })
    },
    // 新增用户
    add() {
      this.dialogVisible = true;
      this.usernameDisable = false;
    },
    // 提交新增用户请求 或 修改用户
    save() {
      if (this.form.id) {
        this.usernameDisable = true;
        this.form.admin === true?this.form.admin = 0: this.form.admin = 1
        axios({
          method: "post",
          url: "/api/user/update",
          data: this.form
        }).then(res => {
          res = res.data;
          if (res.code === "200" ) {
            this.$message.success("更新成功")
            this.load()
            this.form = {}// 清空表单域
            this.dialogVisible = false
          } else {
            this.$message.error(res.msg);
          }
        })
      } else {
        this.form.admin === "是"?this.form.admin = 0: this.form.admin = 1
        axios({
          method: "post",
          url: "/api/user/insert",
          data: this.form
        }).then(res => {
          res = res.data;
          if (res.code === "200" ) {
            this.$message.success("新增成功")
            this.load()
            this.form = {}// 清空表单域
            this.dialogVisible = false
          } else {
            this.$message.error(res.msg);
          }
        })
      }
    },
    // 查询表格数据
    load() {
      // 分页查询
      // 经过思考还是做前端分页吧，因为后端是手动分页，导致total等一系列很难获取
      axios({
        method: "get",
        url: "/api/user/get",
        params: { // post中是data，get中是params
          keyword: this.keyword
        }
      }).then(res => {
          res = res.data;
          if (res.code === "200") {
            parseArrayInt2Sex(res.data);
            parseArrayInt2Admin(res.data);
            this.total = res.data.length;
            this.tableData = res.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
          }
      })
    }
  },
  data() {
    return {
      form: {}, // 新增与修改用户时的双向绑定
      pageSize: 5, // 每页有几个
      dialogVisible: false,
      total: 0, // 一共多少个
      currentPage: 1, // 当前页
      keyword: '',
      tableData: [],
      usernameDisable: false
    }
  }
}
</script>

<style scoped>
.op, .search {
  margin: 10px 0;
}
.table {
  padding: 20px;
}
</style>