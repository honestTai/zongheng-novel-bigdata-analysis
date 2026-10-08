<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 任务列表
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
                >新增任务
                </el-button>
            </div>
            <el-table
                :data="dataSetList"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
            >

                <el-table-column prop="taskId" label="序号" width="160" align="center"></el-table-column>
                <el-table-column prop="taskName" label="任务名称" align="center" width="160"></el-table-column>
                <el-table-column prop="script.scriptName" label="任务类型（需要运行的爬虫脚本）" align="center"></el-table-column>
                <el-table-column prop="taskStartTime" label="开始时间" align="center">
                </el-table-column>
                <el-table-column prop="taskEndTime" label="结束时间" align="center">
                </el-table-column>
                <el-table-column prop="taskStatus" label="任务状态" align="center">
                </el-table-column>
                <el-table-column prop="info" label="任务信息" align="center">
                </el-table-column>
                <el-table-column label="操作" align="center" fixed="right">
                    <template slot-scope="scope">
                        <el-button
                            class="green"
                            @click="delData(scope.row.taskId)"
                        >删除
                        </el-button>
                        <el-button
                            class="green"
                            @click="start(scope.row.taskId)"
                        >开始
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
        <el-dialog title="数据集添加" :visible.sync="addDataSetShow" width="80%">
            <div class="block">
                <el-form ref="form" :model="task" label-width="80px">
                    <el-form-item label="名称">
                        <el-input v-model="task.taskName"></el-input>
                    </el-form-item>
                    <el-form-item label="运行脚本">
                        <el-select v-model="task.taskScriptId" class="handle-select mr10"
                                   clearable
                                   filterable>
                            <el-option v-for="item in scripts" :value="item.scriptId"
                                       :key="item.scriptId" :label="item.scriptName">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
            <el-button @click="addDataSetShow = false">关闭</el-button>
            <el-button @click="addDataSet()">提交</el-button>
        </el-dialog>

        <el-dialog title="任务结果展示" :visible.sync="resultShow" width="55%">
            <iframe :src="resultUrl" width="100%" height="600px">
            </iframe>
        </el-dialog>
    </div>
</template>

<script>
import { taskLists, saveTask, deltask, infos, start } from '../../../utils';


export default {
    name: 'basetable',
    mounted() {
        this.loadHtmlContent();
    },
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
            task:{},
            projectList:[],
            scripts:[],
            datasets:[],
            resultShow:false,
            resultUrl:'',
            htmlContent:""
        };
    },
    created() {
        this.getList();
        this.getInfo();
    },
    methods: {
        async loadHtmlContent() {
            const response = await fetch(this.resultUrl);
            const html = await response.text();
            this.htmlContent = html;
        },
        watch(e){
            this.resultShow=true
            this.resultUrl="http://127.0.0.1:8776/api/result/"+e.replace("G:\\system\\python\\","")
            this.loadHtmlContent();
        },
        start(e){
            start(e).then(res => {
                this.$message.success(res.data.message);
                this.getList()
            });
        },
        getInfo(){
            infos().then(res=>{
                this.scripts=res.data.data.scripts
            })
        },
        //数据集添加
        addDataSet(){
            saveTask(this.task).then(res => {
                this.$message.success(res.data.message);
                this.addDataSetShow=false
                this.getList()
            });
        },
        getList() {
            taskLists(this.query).then(res => {
                let data = res.data;
                console.log(data)
                this.pageTotal = data.data.total;
                this.dataSetList = data.data.records;
            });
        },
        //删除操作
        delData(e) {
            deltask(e).then(res => {
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
