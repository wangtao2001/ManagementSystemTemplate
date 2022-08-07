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
      <el-input v-model="keyword" placeholder="书名关键字" style="width: 30%;" clearable />
      <el-button type="primary" style="margin-left: 10px;" @click="load" size="medium" >查询</el-button>
      <el-button type="primary" style="margin-left: 10px;" size="medium"  @click="clear">重置</el-button>
    </div>
    <el-table :data="tableData" border style="width: 70%">
      <el-table-column prop="id" label="ID" width="80" sortable />
      <el-table-column prop="bookname" label="书名" />
      <el-table-column prop="price" label="价格" width="180" />
      <el-table-column prop="author" label="作者" width="180" />
      <el-table-column prop="date" label="出版日期" width="180" />
      <el-table-column label="操作" width="100" fixed="right">
        <template #default="scope">
          <el-button type="text" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认删除该书籍吗?" @confirm="handleDelete(scope.row)">
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
        width="30%">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="书名">
          <el-input v-model="form.bookname"></el-input>
        </el-form-item>
        <el-form-item label="价格">
          <el-input type="number" v-model="form.price"></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="form.author"></el-input>
        </el-form-item>
        <el-form-item label="出版时间">
          <el-date-picker
              v-model="form.date"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
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
  name: "Book",
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
    // 查询表格数据
    load() {
      axios({
        method: "get",
        url: "/api/book/get",
        params: {
          keyword: this.keyword
        }
      }).then(res => {
        res = res.data;
        if (res.code === "200") {
          this.total = res.data.length;
          this.tableData = res.data.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
        }
      })
    },
    // 新增
    add() {
      this.dialogVisible = true;
    },
    // 提交新增 或 修改
    save() {
      if (this.form.id) {
        axios({
          method: "post",
          url: "/api/book/update",
          data: this.form
        }).then(res => {
          res = res.data;
          if (res.code === "200" ) {
            this.$message.success("更新成功")
            this.load()
            this.dialogVisible = false
            this.form = {}// 清空表单域
          } else {
            this.$message.error(res.msg);
          }
        })
      } else {
        axios({
          method: "post",
          url: "/api/book/insert",
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
    // 编辑
    handleEdit(row) {
      this.form = row
      this.dialogVisible = true
    },
    // 删除
    handleDelete(row) {
      axios({
        method: "post",
        url: "/api/book/delete",
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