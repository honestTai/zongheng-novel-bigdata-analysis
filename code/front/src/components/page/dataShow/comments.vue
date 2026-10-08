<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 评论数据
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

            <div class="handle-box">

                <el-input v-model="query.likeString" placeholder="请输入查询内容" class="handle-input mr10"
                          @change="handleSearchRealName"></el-input>
            </div>
            <el-table
                :data="monthDataList"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >
                <el-table-column prop="userImgUrl" label="用户头像" align="center">
                    <template slot-scope="scope">
                        <!--                        <img :src="scope.row.bookCover">-->
                        <el-image
                            style="width: 100px; height: 100px"
                            :src="'https://static.zongheng.com/userimage/' + scope.row.userImgUrl"
                        >
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="nickName" label="评论用户昵称" align="center"></el-table-column>
                <el-table-column prop="bookName" label="被评论的书籍" align="center"></el-table-column>
                <el-table-column prop="content" label="评论内容" align="center">
                    <template slot-scope="scope">
                        <div v-html="scope.row.content"></div>
                    </template>
                </el-table-column>
                <el-table-column prop="upvoteNum" label="评论点赞数" align="center"></el-table-column>
                <el-table-column prop="createTime" label="评论时间" align="center">
                    <template slot-scope="scope">
                        {{ formatDate(scope.row.createTime) }}
                    </template>
                </el-table-column>

                <el-table-column prop="ipRegion" label="评论用户来自地区" align="center"></el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    background
                    layout="total,sizes, prev, pager, next, jumper"
                    @size-change="handlePageSizeChange"
                    @current-change="handlePageChange"
                    :current-page="query.pageIndex"
                    :page-sizes="[10, 50, 100, 200,500,1000]"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                >
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
import { commentsList, monthList } from '../../../utils';

export default {
    name: 'basetable',
    data() {
        return {
            query: {
                pageIndex: 1,
                pageSize: 10,
                likeString: '',
                orderBy:'-1',
                rankNo:''
            },
            monthDataList: [],
            pageTotal: 0,
            options: [{
                value: '-1',
                label: '正序'
            }, {
                value: '1',
                label: '倒叙'
            }],
        };
    },
    created() {
        this.getList();
    },
    methods: {

        getList() {
            commentsList(this.query).then(res => {
                let data = res.data;
                this.pageTotal = data.data.total;
                this.monthDataList = data.data.records;
            });
        },
        //分页跳转
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getList();
        },
        //每页大小改变
        handlePageSizeChange(val) {
            this.$set(this.query, 'pageSize', val);
            this.getList();
        },

        handleSearchRealName(val) {
            this.$set(this.query, 'likeString', val);
            this.getList();
        },
        handleSearchRankNo(val) {
            this.$set(this.query, 'rankNo', val);
            this.getList();
        },
        handleSearchOrderBy(val) {
            this.$set(this.query, 'orderBy', val);
            this.getList();
        },
        formatDate(value) {
            if (value) {
                const date = new Date(value);
                const year = date.getFullYear();
                const month = ('0' + (date.getMonth() + 1)).slice(-2);
                const day = ('0' + date.getDate()).slice(-2);
                return `${year}-${month}-${day}`;
            }
            return '';
        }

    }
};
</script>

<style scoped>
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}

.table {
    width: 100%;
    font-size: 14px;
}

.red {
    color: #ff0000;
}

.mr10 {
    margin-right: 10px;
}

</style>
