<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> Python脚本管理
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">

            <div class="handle-box">

                <el-input v-model="query.likeString" placeholder="请输入查询内容" class="handle-input mr10"
                          @change="handleSearchRealName"></el-input>
                <el-button
                    type="primary"
                    icon="el-icon-lx-add"
                    class="handle-del mr10"
                    @click="addDataSetShow = true"
                >新增脚本
                </el-button>
            </div>
            <el-table
                :data="dataSetList"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >

                <el-table-column prop="scriptId" label="序号" width="160" align="center"></el-table-column>
                <el-table-column prop="scriptName" label="脚本名称" align="center" width="160"></el-table-column>
                <el-table-column prop="scriptDescription" label="脚本描述" align="center"></el-table-column>
                <el-table-column prop="scriptFilePath" label="脚本文件地址" align="center"></el-table-column>
                <el-table-column label="操作"  width="120" align="center" fixed="right">
                    <template slot-scope="scope">
                        <el-button
                            class="green"
                            @click="delData(scope.row.scriptId)"
                        >删除
                        </el-button>
                    </template>
                </el-table-column>
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
        <el-dialog title="脚本添加" :visible.sync="addDataSetShow" width="80%">
            <div class="block">
                <el-form ref="form" :model="script" label-width="80px">
                    <el-form-item label="名称">
                        <el-input v-model="script.scriptName"></el-input>
                    </el-form-item>
                    <el-form-item label="描述">
                        <el-input v-model="script.scriptDescription"></el-input>
                    </el-form-item>
                    <el-form-item label="选取文件" prop="fileList">
                        <el-upload
                            class="upload-demo"
                            drag
                            ref="upload"
                            :http-request="fileUpload"
                            action=""
                            accept=".py"
                            :limit="1"
                            :file-list="fileList"
                            :auto-upload="true"
                        >
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                        </el-upload>
                    </el-form-item>
                </el-form>
            </div>
            <el-button @click="addDataSetShow = false">关闭</el-button>
            <el-button @click="addDataSet()">提交</el-button>
        </el-dialog>
    </div>
</template>

<script>
import { scriptList, saveScript, delscript, uploadFile } from '../../../utils';

export default {
    name: 'basetable',
    data() {
        return {
            query: {
                pageIndex: 1,
                pageSize: 10,
                likeString: '',
                id: null,
            },
            dataSetList: [],
            pageTotal: 0,
            addDataSetShow:false,
            script:{},
            fileList:[]
        };
    },
    created() {
        this.getList();
    },
    methods: {

        fileUpload(file) {
            console.log(file)
            var that = this;
            let formData = new FormData();
            formData.append('file', file.file);
            uploadFile(formData).then(res => {
                console.log(res);
                this.script.scriptFilePath=res.data.data
            });


        },
        //数据集添加
        addDataSet(){
            saveScript(this.script).then(res => {
                this.$message.success(res.data.message);
                this.addDataSetShow=false
                this.getList()
                this.script={

                }
                this.fileList =[]
            });
        },
        getList() {
            scriptList(this.query).then(res => {
                let data = res.data;
                console.log(data)
                this.pageTotal = data.data.total;
                this.dataSetList = data.data.records;
            });
        },
        //删除操作
        delData(e) {
            delscript(e).then(res => {
                this.$message.success(res.data.message);
                this.getList()
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
            this.$set(this.query, 'title', val);
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
