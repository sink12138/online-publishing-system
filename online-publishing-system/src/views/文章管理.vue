<template>
    <div class="editor/articles">
        <h1>文章管理</h1>
        <router-link to="/editor/check/articles">
            <el-row>
                <el-button type="primary">查看所有负责的文章</el-button>
            </el-row>
        </router-link>
        <router-link to="/editor/assign">
            <el-row>
                <el-button type="primary">分配审稿人</el-button>
            </el-row>
        </router-link>
        <router-link to="/editor/manage/articles">
            <el-row>
                <el-button type="primary">管理收到的文章</el-button>
            </el-row>
        </router-link>
       <router-link to="/editor/upload">
            <el-row>
                <el-button type="primary">上传编辑排版后的文章</el-button>
            </el-row>
        </router-link>
        <router-link to="/editor/publish">
            <el-row>
                <el-button type="primary">出版文章</el-button>
            </el-row>
        </router-link>
        <router-link to="/editor/confirm/claim">
            <el-row>
                <el-button type="primary">确认文章认领申请</el-button>
            </el-row>
        </router-link>
        <router-link to="/editor/confirm/withdraw">
            <el-row>
                <el-button type="primary">确认撤稿申请</el-button>
            </el-row>
        </router-link>
        <router-link to="/editor/reviews">
            <el-row>
                <el-button type="primary">查看文章评论</el-button>
            </el-row>
        </router-link>
        <router-link to="/editor/claims">
            <el-row>
                <el-button type="primary">查看文章认领申请</el-button>
            </el-row>
        </router-link>
    </div>
</template>
