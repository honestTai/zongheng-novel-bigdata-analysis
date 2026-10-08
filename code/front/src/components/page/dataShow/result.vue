<!--开始还是搜索，无人机的分类-->
<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i>数据集展示</el-breadcrumb-item>
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i>结果展示
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>

    </div>

</template>

<script>
import {
    resultget
} from '../../../utils';
import { Notification } from 'element-ui';

export default {
    name: 'basetable',
    data() {
        return {
            seats: [],
            resultShow: false,
            resultUrl: null
        };
    },
    created() {

    },
    methods: {

    }
};
</script>

<style scoped>

.seat-picker {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 20px;
    flex-wrap: wrap;
    max-width: calc(200px * 10 + 20px * 9);
}

.seat-row {
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
    width: 100%;
    flex-wrap: wrap;
}

.seat {
    width: 200px;
    height: 30px;
    margin-right: 20px;
    background-color: #32CD32;
    color: #fff;
    font-size: 12px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 5px;
    cursor: pointer;
    transition: all 0.2s ease-in-out;
    position: relative;
    margin-bottom: 20px; /* 添加底部外边距 */
}

.seat.selected {
    background-color: #808080;
    cursor: default;
}

.title {
    font-size: 14px;
    margin-bottom: 10px;
}

.info {
    position: absolute;
    bottom: -25px;
    left: 50%;
    transform: translateX(-50%);
    font-size: 12px;
    background-color: rgba(0, 0, 0, 0.7);
    color: #fff;
    padding: 5px;
    border-radius: 5px;
    display: none;
}

.seat:hover .info {
    display: block;
}

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
