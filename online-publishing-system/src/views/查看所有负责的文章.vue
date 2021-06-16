<template>
  <div class="reviewer">
    <div>
      <h1>您负责的文章如下</h1>
      <router-link to="/editor" v-show="this.$route.path == '/editor/articles'">
        <el-button class="back" type="info" icon="el-icon-back"></el-button>
      </router-link>
    </div>
    <div class="table">
      <el-table
        :data="
          tableData.slice((currentPage - 1) * pagesize, currentPage * pagesize)
        "
        :header-cell-style="{ height: '60px' }"
        style="height: 100%;width: 100%;padding-top:10px;scoped"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="文章ID">
                <span>{{ props.row.articleId }}</span>
              </el-form-item>
              <el-form-item label="文章标题">
                <span>{{ props.row.title }}</span>
              </el-form-item>
              <el-form-item label="关键字">
                <span>{{ props.row.keywords }}</span>
              </el-form-item>
              <el-form-item label="第一作者">
                <span>{{ props.row.firstAuthor }}</span>
              </el-form-item>
              <el-form-item label="其他作者">
                <span>{{ props.row.otherAuthors }}</span>
              </el-form-item>
              <el-form-item label="文章状态">
                <span>{{ props.row.status }}</span>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="文章编号" prop="articleId"> </el-table-column>
        <el-table-column label="文章标题" prop="title"> </el-table-column>
        <el-table-column label="文章状态" prop="status"> </el-table-column>
        <el-table-column label="文章操作">
          <template slot-scope="scope">
          <el-dropdown>
            <span class="el-dropdown-link">
              可执行操作<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                ><el-button
                    type="text"
                    size="small"
                    v-if="scope.row.status != '待撤稿'"
                    @click="downloadArticle(scope.row)"
                    >下载</el-button
                  ></el-dropdown-item
              >
              <el-dropdown-item
                ><template slot-scope="scope"
                  ><el-button
                    type="text"
                    size="small"
                    v-if="scope.row.status == '待审核'"
                    @click="assignReviewers(scope.row)"
                    >分配审稿人</el-button
                  >
                </template></el-dropdown-item
              >
              <el-dropdown-item
                ><el-button
                    type="text"
                    size="small"
                    v-if="
                      scope.row.status == '待接收' ||
                      scope.row.status == '修改稿待接收'
                    "
                    @click="acceptArticle(scope.row)"
                    >接收</el-button
                  ></el-dropdown-item
              >
              <el-dropdown-item
                ><el-button
                    type="text"
                    size="small"
                    v-if="
                      scope.row.status == '待接收' ||
                      scope.row.status == '修改稿待接收'
                    "
                    @click="rejectArticle(scope.row)"
                    >拒绝</el-button
                  ></el-dropdown-item
              >
              <el-dropdown-item
                  <router-link to="/editor/upload"
                    ><el-button
                      type="text"
                      size="small"
                      v-if="scope.row.status == '编辑中'"
                      >上传编辑稿</el-button
                    ></router-link
                  ></el-dropdown-item
              >
              <el-dropdown-item><el-button
                    type="text"
                    size="small"
                    v-if="scope.row.status == '待撤稿'"
                    @click="acceptWithdraw(scope.row)"
                    >接受撤稿</el-button
                  ></el-dropdown-item
              >
              <el-dropdown-item><el-button
                    type="text"
                    size="small"
                    v-if="scope.row.status == '待撤稿'"
                    @click="rejectWithdraw(scope.row)"
                    >拒绝撤稿</el-button
                  ></el-dropdown-item
              >
              <el-dropdown-item><el-button
                    type="text"
                    size="small"
                    v-if="scope.row.status == '编辑中'"
                    @click="publishArticle(scope.row)"
                    >出版文章</el-button
                  ></el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination">
        <div class="reviewer-button">
          <router-link to="/editor/claims">
            <el-button type="ops">查看文章认领申请</el-button>
          </router-link>
          <router-link to="/editor/reviews">
            <el-button type="ops">查看评论</el-button>
          </router-link>
        </div>
        <el-pagination
          background
          layout="prev, pager, next, jumper"
          :total="total"
          :page-size="10"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
        >
        </el-pagination>
      </div>
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped>
.back {
  position: fixed;
  left: 60px;
  top: 80px;
}
.breadcrumb {
  position: relative;
  text-align: center;
}
.reviewer-button .el-button {
  margin-right: 20px;
}
.table {
  text-align: center;
}
.pagination {
  display: inline;
  position: fixed;
  justify-content: center;
  left: 700px;
  bottom: 60px;
}
.el-button--ops.is-active,
.el-button--ops:active {
  background: #000000;
  border-color: #000000;
  color: #fff;
}
.el-button--ops:focus,
.el-button--ops:hover {
  background: #ababab;
  border-color: #ababab;
  color: #000000;
}
.el-button--ops {
  color: #fff;
  background: #000000;
  border-color: #000000;
  position: relative;
  left: -550px;
  top: 30px;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>

<script>
export default {
  mounted: function () {
    this.$axios({
      method: "get",
      url: "http://82.156.190.251:80/apis/editor/articles",
    })
      .then((response) => {
        console.log(response);
        var arraylist = new Array();
        arraylist = response.data;
        this.success = arraylist[0].success;
        if (this.success == true) {
          this.results = arraylist[0].results;
          this.tableData = arraylist.slice(1);
          console.log(this.tableData);
        }
      })
      .catch((err) => console.log(err));
  },
  methods: {
    downloadArticle(row) {
      this.$axios({
        method: "get",
        url: "http://82.156.190.251:80/apis/download",
        params: { articleId: Number(row.articleId) },
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
    publishArticle(row) {
      this.$prompt("请输入该文章的文献标识符", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(({ value }) => {
          let JsonPublishArticle = JSON.stringify({
            articleId: Number(row.articleId),
            identifier: value,
          });
          console.log(JsonPublishArticle);
          this.$store.commit("Assign");
          this.$axios({
            method: "post",
            url: "http://82.156.190.251:80/apis/editor/publish",
            data: JsonPublishArticle,
          }).then((res) => {
            console.log(res);
            if (res.data.success == true) {
              this.$message({
                showClose: true,
                message: "出版成功！",
                type: "success",
              });
              window.location.href = "../editor/articles";
            } else {
              this.$message({
                showClose: true,
                message: res.data.message,
                type: "error",
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
    },
    assignReviewers(row) {
      this.$prompt(
        "请输入分配的审稿人序列",
        "两个审稿人编号请用英文逗号','进行分割",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
        }
      )
        .then(({ value }) => {
          var reviewersId = value.split(",");
          for (let i = 0; i < reviewersId.length; i++) {
            reviewersId[i] = parseInt(reviewersId[i]);
          }
          let JsonAssignArticle = JSON.stringify({
            articleId: Number(row.articleId),
            reviewerId: reviewersId,
          });
          console.log(JsonAssignArticle);
          this.$store.commit("Assign");
          this.$axios({
            method: "post",
            url: "http://82.156.190.251:80/apis/editor/assign",
            data: JsonAssignArticle,
          }).then((res) => {
            console.log(res);
            if (res.data.success == true) {
              this.$message({
                showClose: true,
                message: "分配审稿人成功",
                type: "success",
              });
            } else {
              this.$message({
                showClose: true,
                message: res.data.message,
                type: "error",
              });
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
    },
    acceptArticle(row) {
      this.$confirm(
        "您正要接受编号为",
        row.articleId,
        "的文章，是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        let JsonConfirmArticleId = JSON.stringify({
          articleBufferId: Math.abs(Number(row.articleId)),
        });
        console.log(JsonConfirmArticleId);
        this.$store.commit("confirm");
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/editor/accept",
          data: JsonConfirmArticleId,
        }).then((res) => {
          console.log(res);
          if (res.data.success == true) {
            this.$message({
              showClose: true,
              message: "接受文章成功",
              type: "success",
            });
          } else {
            this.$message({
              showClose: true,
              message: res.data.message,
              type: "error",
            });
          }
        });
        location.reload();
      });
    },
    rejectArticle(row) {
      this.$confirm(
        "您正要拒绝编号为",
        row.articleId,
        "的文章，是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        let JsonConfirmArticleId = JSON.stringify({
          articleBufferId: Math.abs(Number(row.articleId)),
        });
        console.log(JsonConfirmArticleId);
        this.$store.commit("confirm");
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/editor/reject",
          data: JsonConfirmArticleId,
        }).then((res) => {
          console.log(res);
          if (res.data.success == true) {
            this.$message({
              showClose: true,
              message: "拒绝文章成功",
              type: "success",
            });
          } else {
            this.$message({
              showClose: true,
              message: res.data.message,
              type: "error",
            });
          }
        });
        location.reload();
      });
    },
    acceptWithdraw(row) {
      this.$confirm(
        "您正要接受编号为",
        row.articleId,
        "的文章，是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        let JsonConfirmArticleId = JSON.stringify({
          articleId: Number(row.articleId),
          confirm: true,
        });
        console.log(JsonConfirmArticleId);
        this.$store.commit("confirm");
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/editor/confirm/withdraw",
          data: JsonConfirmArticleId,
        }).then((res) => {
          console.log(res);
          if (res.data.success == true) {
            this.$message({
              showClose: true,
              message: "接受文章成功",
              type: "success",
            });
          } else {
            this.$message({
              showClose: true,
              message: res.data.message,
              type: "error",
            });
          }
        });
        location.reload();
      });
    },
    rejectWithdraw(row) {
      this.$confirm(
        "您正要拒绝编号为",
        row.articleId,
        "的文章，是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        let JsonRefuseArticleId = JSON.stringify({
          articleId: Number(row.articleId),
          confirm: false,
        });
        console.log(JsonRefuseArticleId);
        this.$store.commit("refuse");
        this.$axios({
          method: "post",
          url: "http://82.156.190.251:80/apis/editor/confirm/withdraw",
          data: JsonRefuseArticleId,
        }).then((res) => {
          console.log(res);
          if (res.data.success == true) {
            this.$message({
              showClose: true,
              message: "拒绝文章成功",
              type: "success",
            });
          } else {
            this.$message({
              showClose: true,
              message: res.data.message,
              type: "error",
            });
          }
        });
        location.reload();
      });
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
  },
  data() {
    return {
      tableData: [],
      pagesize: 8,
      currentPage: 1,
      articleData: "",
      total: 0,
    };
  },
};
</script>
