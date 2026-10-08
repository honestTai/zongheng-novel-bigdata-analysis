<template>
    <div class="container" style="padding: 6px">
        <el-row style="">
            <br />
            <el-col>
                <el-select v-model="bookId" filterable @change="handleBlur1" placeholder="请选择">
                    <el-option v-for="item in bookList" :key="item.bookId" :label="item.bookName" :value="item.bookId">
                    </el-option>
                </el-select>
            </el-col>
        </el-row>
        <el-row :span="25" style="padding-top: 25px">
            <div>
                <el-col :span="4">
                    <el-link type="primary" style="font-size: 35px">评论总量{{ total_count }}条
                    </el-link>
                </el-col>
                <el-col :span="4">
                    <el-link type="primary" style="font-size: 35px">正面评论{{ po_count }}条
                    </el-link>
                </el-col>
                <el-col :span="4">
                    <el-link type="primary" style="font-size: 35px">负面评论{{ pa_count }}条
                    </el-link>
                </el-col>
            </div>
        </el-row>
        <el-row :span="25" style="padding-top: 25px">
            <el-col :span="8">

                <div id="echarts1" style="height: 300px" />
            </el-col>
            <el-col :span="5">
                <el-link type="" style="font-size: 18px; font-weight: bold"></el-link>
                <div id="circle1" style="height: 400px" />
            </el-col>
            <el-col :span="8">

                <div id="echarts2" style="height: 300px" />
            </el-col>
        </el-row>
        <el-row :span="25" style="padding-top: 25px">
            <el-col :span="10">
                <el-link type="" style="font-size: 18px; font-weight: bold">正面评论
                </el-link>
                <el-table :data="po_data" style="font-size: 16px" class="customer-table"
                    :header-cell-style="{ textAlign: 'center' }" :cell-style="{ textAlign: 'center' }">
                    <el-table-column prop="content" label="内容" align="center" width="200px">
                    </el-table-column>
                    <el-table-column prop="createTime" label="发布时间">
                        <template slot-scope="scope">
                            {{ formatDate(scope.row.createTime) }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="ipRegion" label="来源地"></el-table-column>
                    <el-table-column prop="nickName" label="用户"></el-table-column>
                </el-table>
            </el-col>
            <el-col :span="10" :offset="2">
                <el-link type="" style="font-size: 18px; font-weight: bold">负面评论
                </el-link>
                <el-table :data="pa_data" style="font-size: 16px" class="customer-table"
                    :header-cell-style="{ textAlign: 'center' }" :cell-style="{ textAlign: 'center' }">
                    <el-table-column prop="content" label="内容" align="center" width="200px">
                    </el-table-column>
                    <el-table-column prop="createTime" label="发布时间">
                        <template slot-scope="scope">
                            {{ formatDate(scope.row.createTime) }}
                        </template>
                    </el-table-column>
                    <el-table-column prop="ipRegion" label="来源地"></el-table-column>
                    <el-table-column prop="nickName" label="用户"></el-table-column>
                </el-table>
            </el-col>
        </el-row>

    </div>
</template>

<script>
import 'echarts-wordcloud/dist/echarts-wordcloud';
import 'echarts-wordcloud/dist/echarts-wordcloud.min';
import { getBookList, getAllCommentsByBookId } from '../../../utils';
import { formatDate } from '../../../utils/date';

export default {
    props: {
        className: {
            type: String,
            default: 'chart'
        },
        id: {
            type: String,
            default: 'chart'
        },
        width: {
            type: String,
            default: '100%'
        },
        height: {
            type: String,
            default: '789px'
        },
        title: {
            type: String,
            default: ''
        }
    },
    data() {
        return {
            chart: null,
            total_count: 0,
            po_count: 0,
            ne_count: 0,
            pa_count: 0,
            topic: '',
            wordData: [],
            wordData1: [],
            wordData2: [],
            summary: '',
            top_pa: [],
            top_po: [],
            pa_data: [],
            po_data: [],
            bookList: [],
            bookId: 38134,
            emotionText: ''
        };
    },
    created() {
        this.getbookList();
    },
    mounted() {
        this.getWords();
        this.initChart();
    },
    beforeDestroy() {
        if (!this.chart) {
            return;
        }
        this.chart.dispose();
        this.chart = null;
    },
    methods: {
        formatDate(value) {
            if (value) {
                const date = new Date(value);
                const year = date.getFullYear();
                const month = ('0' + (date.getMonth() + 1)).slice(-2);
                const day = ('0' + date.getDate()).slice(-2);
                return `${year}-${month}-${day}`;
            }
            return '';
        },
        handleBlur1() {
            // console.log(this.bookId)
            this.getWords();
        },
        getbookList() {
            getBookList().then((response) => {

                this.bookList = response.data.data;

            });
        },
        getWords() {
            var data = { bookId: this.bookId };
            getAllCommentsByBookId(data)
                .then((res) => {
                    console.log(res.data.data)
                    let list = res.data.data
                    if (list.length > 0) {
                        this.pa_data = list.filter(item => item.sentiment === '负面');
                        this.po_data = list.filter(item => item.sentiment === '正面');
                        // this.summary = res.data.summary;
                        this.total_count = list.length;
                        this.po_count = this.po_data.length;
                        this.pa_count = this.pa_data.length;
                        this.wordData1 = this.po_data.map(item => ({
                            name: item.content,
                            value: Math.floor(Math.random() * 1000) // 生成一个0到1000之间的随机数
                        }));

                        this.wordData2 = this.pa_data.map(item => ({
                            name: item.content,
                            value: Math.floor(Math.random() * 1000) // 同样的方式生成随机数
                        }));
                        this.initChart();
                    } else {
                        this.$message.show({
                            message: '暂无评论',
                            type: 'warning',
                            duration: 2000
                        });
                    }

                });
        },
        initChart() {
            var option2 = {
                title: {
                    text: '负面评论词云图',
                    x: 'center'
                },
                backgroundColor: '#fff',
                // tooltip: {
                //   pointFormat: "{series.name}: <b>{point.percentage:.1f}%</b>"
                // },
                series: [
                    {
                        type: 'wordCloud',
                        //用来调整词之间的距离
                        gridSize: 10,
                        //用来调整字的大小范围
                        // Text size range which the value in data will be mapped to.
                        // Default to have minimum 12px and maximum 60px size.
                        sizeRange: [14, 60],
                        // Text rotation range and step in degree. Text will be rotated randomly in range [-90,                                                                             90] by rotationStep 45
                        //用来调整词的旋转方向，，[0,0]--代表着没有角度，也就是词为水平方向，需要设置角度参考注释内容
                        // rotationRange: [-45, 0, 45, 90],
                        // rotationRange: [ 0,90],
                        rotationRange: [0, 0],
                        //随机生成字体颜色
                        // maskImage: maskImage,
                        textStyle: {
                            normal: {
                                color: function () {
                                    return (
                                        'rgb(' +
                                        Math.round(Math.random() * 255) +
                                        ', ' +
                                        Math.round(Math.random() * 255) +
                                        ', ' +
                                        Math.round(Math.random() * 255) +
                                        ')'
                                    );
                                }
                            }
                        },
                        //位置相关设置
                        // Folllowing left/top/width/height/right/bottom are used for positioning the word cloud
                        // Default to be put in the center and has 75% x 80% size.
                        left: 'center',
                        top: 'center',
                        right: null,
                        bottom: null,
                        width: '200%',
                        height: '200%',
                        //数据
                        data: this.wordData2
                    }
                ]
            };
            var option1 = {
                title: {
                    text: '正面评论词云图',
                    x: 'center'
                },
                backgroundColor: '#fff',
                // tooltip: {
                //   pointFormat: "{series.name}: <b>{point.percentage:.1f}%</b>"
                // },
                series: [
                    {
                        type: 'wordCloud',
                        //用来调整词之间的距离
                        gridSize: 10,
                        //用来调整字的大小范围
                        // Text size range which the value in data will be mapped to.
                        // Default to have minimum 12px and maximum 60px size.
                        sizeRange: [14, 60],
                        // Text rotation range and step in degree. Text will be rotated randomly in range [-90,                                                                             90] by rotationStep 45
                        //用来调整词的旋转方向，，[0,0]--代表着没有角度，也就是词为水平方向，需要设置角度参考注释内容
                        // rotationRange: [-45, 0, 45, 90],
                        // rotationRange: [ 0,90],
                        rotationRange: [0, 0],
                        //随机生成字体颜色
                        // maskImage: maskImage,
                        textStyle: {
                            normal: {
                                color: function () {
                                    return (
                                        'rgb(' +
                                        Math.round(Math.random() * 255) +
                                        ', ' +
                                        Math.round(Math.random() * 255) +
                                        ', ' +
                                        Math.round(Math.random() * 255) +
                                        ')'
                                    );
                                }
                            }
                        },
                        //位置相关设置
                        // Folllowing left/top/width/height/right/bottom are used for positioning the word cloud
                        // Default to be put in the center and has 75% x 80% size.
                        left: 'center',
                        top: 'center',
                        right: null,
                        bottom: null,
                        width: '200%',
                        height: '200%',
                        //数据
                        data: this.wordData1
                    }
                ]
            };

            var circleOptioin = {
                title: {
                    text: '评论情感属性',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '40',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: [
                            { value: this.po_count, name: '正面评论' },
                            { value: this.pa_count, name: '负面评论' }
                        ]
                    }
                ]
            };
            this.$echarts
                .init(document.getElementById('circle1'))
                .setOption(circleOptioin);
            this.$echarts
                .init(document.getElementById('echarts1'))
                .setOption(option1);
            this.$echarts
                .init(document.getElementById('echarts2'))
                .setOption(option2);
        }
    }
};
</script>

<style>
.container {
    padding: 10px;
}

.el-row {
    margin-bottom: 20px;
}

.el-col {
    border-radius: 4px;
}

.bg-purple-dark {
    background: #99a9bf;
}

.bg-purple {
    background: #d3dce6;
}

.bg-purple-light {
    background: #e5e9f2;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}

.row-bg {
    padding: 10px 0;
    background-color: #f9fafc;
}
</style>
