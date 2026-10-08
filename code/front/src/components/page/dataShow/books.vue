<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 书籍数据
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
                <el-table-column prop="bookCover" label="图书照片" align="center">
                    <template slot-scope="scope">
                        <!--                        <img :src="scope.row.bookCover">-->
                        <el-image
                            style="width: 100px; height: 100px"
                            :src="scope.row.pic"
                        >
                        </el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="descinfo" label="作品介绍" align="center"></el-table-column>
                <el-table-column prop="arthur" label="作者" align="center"></el-table-column>
                <el-table-column prop="bookName" label="作品名称" align="center">
                    <template slot-scope="scope">
                        <!--                        <img :src="scope.row.bookCover">-->
                        <a :href="scope.row.link">{{scope.row.bookName}}</a>
                    </template>
                </el-table-column>
                <el-table-column prop="bookType" label="作品类型" align="center"></el-table-column>
                <el-table-column prop="totalclick" label="总点击数" align="center" ></el-table-column>
                <el-table-column prop="totalrecommend" label="总推荐数" align="center" ></el-table-column>
                <el-table-column prop="weekrecommend" label="周推荐数" align="center" ></el-table-column>
                <el-table-column prop="words" label="总字数" align="center" ></el-table-column>

                <el-table-column prop="fans" label="粉丝数" align="center" ></el-table-column>
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
import { books, commentsList, monthList } from '../../../utils';

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
            books(this.query).then(res => {
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
