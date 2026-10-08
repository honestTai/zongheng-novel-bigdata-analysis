<template>
    <div class="container" style="padding: 6px">

        <!--词云图-->
        <el-row :span="25" style="padding-top: 25px">
            <el-col :span="12">

                <div id="echarts3" style="height: 500px" />
            </el-col>
            <el-col :span="12">

                <div id="echarts2" style="height: 500px" />
            </el-col>
        </el-row>
        <!--排行榜-->
        <el-row :span="25" style="padding-top: 25px">
            <el-col :span="24">

                <div id="echarts4" style="height: 300px" />
            </el-col>
        </el-row>

        <el-row :span="25" style="padding-top: 25px">

            <el-col :span="24">

                <div id="echarts7" style="height: 300px" />
            </el-col>

        </el-row>


    </div>
</template>

<script>
import 'echarts-wordcloud/dist/echarts-wordcloud';
import 'echarts-wordcloud/dist/echarts-wordcloud.min';
import { getBookList, getAllCommentsByBookId, booksAnalysis } from '../../../utils';

export default {
    props: {

    },
    data() {
        return {
            fans:[],
            words:[],
            line:{},
            lines:{}
        };
    },
    created() {
        // this.getWords()
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
        handleBlur1() {
            // console.log(this.bookId)
            this.getWords();
        },
        getWords() {
            booksAnalysis().then(res=>{
                let data = res.data.data
                this.fans = data.fans;
                this.words = data.words;
                this.line = data.line
                this.lines = data.lines
                this.initChart()
            })
        },
        initChart() {
            var option2 = {
                title: {
                    text: '书籍粉丝Top200',
                    // x: 'center'
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
                        data: this.fans
                    }
                ]
            };
            var option3 = {
                title: {
                    text: '书籍推荐Top200',
                    // x: 'center'
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
                        data: this.words
                    }
                ]
            };
            var lineChartOption = {
                title: {
                    text: '书籍受欢迎排行',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    data: this.line.names
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: this.line.values,
                    type: 'line' // 修改这里为 'line'
                }],
                dataZoom: [
                    {
                        type: 'slider',
                        xAxisIndex: 0,
                        start: 0,
                        end: 100
                    },
                    {
                        type: 'inside',
                        xAxisIndex: 0,
                        start: 0,
                        end: 100
                    }
                ]
            };


            var linesChartOption = {
                title: {
                    text: '书籍点击与字数关系',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross'
                    }
                },
                xAxis: {
                    type: 'category',
                    data: this.lines.names
                },
                yAxis: [
                    {
                        type: 'value',
                        name: '总点击数'
                    },
                    {
                        type: 'value',
                        name: '字数',
                        position: 'right'
                    }
                ],
                series: [
                    {
                        name: '总点击数',
                        type: 'scatter',
                        data: this.lines.clicks
                    },
                    {
                        name: '字数',
                        type: 'bar',
                        yAxisIndex: 1,
                        data: this.lines.values
                    }
                ],
                dataZoom: [
                    {
                        type: 'slider',
                        xAxisIndex: 0,
                        start: 0,
                        end: 100
                    },
                    {
                        type: 'inside',
                        xAxisIndex: 0,
                        start: 0,
                        end: 100
                    }
                ]
            };

            this.$echarts
                .init(document.getElementById('echarts3'))
                .setOption(option3);
            this.$echarts
                .init(document.getElementById('echarts2'))
                .setOption(option2);
            this.$echarts
                .init(document.getElementById('echarts4'))
                .setOption(lineChartOption);
            this.$echarts
                .init(document.getElementById('echarts7'))
                .setOption(linesChartOption);
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
