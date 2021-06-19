<template>
  <div class="reviewer">
    <div>
      <h1>您待审阅的文章如下</h1>
    </div>
    <div class="articles">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="文章标题">
                <span>{{ props.row.title }}</span>
              </el-form-item>
              <el-form-item label="关键字">
                <span>{{ props.row.keywords }}</span>
              </el-form-item>
              <el-form-item label="处理状态">
                <span>{{ props.row.status }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="文章 ID" prop="articleId"> </el-table-column>
        <el-table-column label="文章标题" prop="title"> </el-table-column>
        <el-table-column label="文章状态" prop="status"> </el-table-column>
        <el-table-column fixed="right" label="操作" width="200">
          <template slot-scope="scope">
            <!-- <el-select v-model="scope.row.value" placeholder="是否通过">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select> -->
            <el-button
              type="text"
              size="small"
              @click="downloadarticle(scope.row)"
              >下载<i class="el-icon-download el-icon--right"></i
            ></el-button>
            <!-- <el-button @click="open(scope.row)" type="text" size="small"
              >评价</el-button
            > -->
            <el-button
              @click="
                dialogFormVisible = true;
                handleEdit(scope.$index, scope.row);
              "
              type="text"
              size="small"
              >评价</el-button
            >
            <el-button @click="submit(scope.row)" type="text" size="small"
              >提交</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog title="审核意见" :visible.sync="dialogFormVisible" :modal-append-to-body='false'>
      <el-form :model="form">
        <el-form-item label="评论" :label-width="formLabelWidth">
          <el-input v-model="form.comments" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否通过" :label-width="formLabelWidth">
          <el-select v-model="form.value" placeholder="是否通过">
            <el-option label="通过" value=true></el-option>
            <el-option label="不通过" value=false></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="
            dialogFormVisible = false;
            update(form.value, form.comments);
          "
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<style>
.reviewer {
  opacity: 0.7;
}
.articles {
  width: 1200px;
  margin: 0 auto;
}
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}
</style>

<script>
export default {
  data() {
    return {
      dialogFormVisible: false,
      form: {},
      formLabelWidth: "120px",
      tableData: [],
      options: [
        {
          value: true,
          label: "通过",
        },
        {
          value: false,
          label: "不通过",
        },
      ],
      value: "",
      index: 0,
    };
  },
  created() {
    this.convert();
  },
  methods: {
    update(value, comments) {
      if(value=="true")
        this.tableData[this.index].value = true;
      else
        this.tableData[this.index].value = false;
      this.tableData[this.index].comments = comments;
    },
    handleEdit(index, row) {
      console.log(index, row);
      this.form = row;
      this.index = index;
    },
    downloadarticle(row) {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/download",
        params: { articleId: row.articleId },
        responseTpe: "blob",
      }).then(
        (response) => {
          console.log(response);
          const filename = decodeURIComponent(
            response.headers["content-disposition"].split(";")[1].split("=")[1]
          );
          console.log(filename);
          this.load(response.data, filename);
        },
        (err) => {
          alert(err);
        }
      );
    },
    load(data, filename) {
      if (!data) {
        return;
      }
      let url = window.URL.createObjectURL(
        new Blob([data], { type: "application/force-download;charset=utf-8" })
      );
      let link = document.createElement("a");
      link.style.display = "none";
      link.href = url;
      link.download = filename;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      window.URL.revokeObjectURL(url);
    },
    submit(row) {
      if (row.comments !== undefined && row.comments !== "") {
        console.log(row.articleId);
        console.log(row.value);
        console.log(row.comments);
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/reviewer/review/submit",
          data: JSON.stringify({
            articleId: Number(row.articleId),
            pass: row.value,
            comments: row.comments,
          }),
        }).then(
          (response) => {
            console.log(response);
            if (response.data.success == true) {
              location.reload();
            }
          },
          (err) => {
            alert(err);
          }
        );
      } else {
        this.$message({
          message: "请输入评论",
        });
      }
    },
    convert: function () {
      this.$axios
        .get("http://82.156.190.251:80/apis/reviewer/articles")
        .then((res) => {
          this.tableData = res.data.slice(1);
          console.log(this.tableData);
        });
    },
    open(row) {
      this.$prompt("请输入评论", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: "",
        showclose: true,
      })
        .then(({ value }) => {
          this.$message({
            type: "success",
            message: "通过意见:" + row.value + ",你的评论是: " + value,
          });
          row.comments = value;
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
      console.log(row);
    },
  },
};
</script>
