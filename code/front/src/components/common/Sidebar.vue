<!--菜单-->
<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                >{{ threeItem.title }}
                                </el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            >{{ subItem.title }}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';

export default {
    data() {
        
        return {
            // type:JSON.parse(localStorage.getItem('user')).type,
            collapse: false,
            items:[{}]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
        // if(this.type === 0){
            this.items=[
                {
                    icon: 'el-icon-lx-home',
                    index: 'home',
                    title: '书籍分析'
                },
                {
                    icon: 'el-icon-s-platform',
                    index: 'pythonList',
                    title: '脚本管理',
                },{
                    icon: 'el-icon-s-platform',
                    index: '1',
                    title: '数据集',
                    subs:[{
                        icon: 'el-icon-s-platform',
                        index: 'clickData',
                        title: '点击数据',
                    },{
                        icon: 'el-icon-s-platform',
                        index: 'commond',
                        title: '推荐数据',
                    },{
                        icon: 'el-icon-s-platform',
                        index: 'month',
                        title: '月票数据',
                    },{
                        icon: 'el-icon-s-platform',
                        index: 'books',
                        title: '书籍信息',
                    },{
                        icon: 'el-icon-s-platform',
                        index: 'comments',
                        title: '评论信息',
                    }]
                },{
                    icon: 'el-icon-s-platform',
                    // index: 'result',
                    title: '分析结果展示',
                    subs:[{
                        icon: 'el-icon-s-platform',
                        index: 'emotion',
                        title: '情感分析',
                    },{
                        icon: 'el-icon-s-platform',
                        index: 'bookShow',
                        title: '书籍分析',
                    },{
                        icon: 'el-icon-s-platform',
                        index: 'chart',
                        title: '榜单分析',
                    },
                    //     {
                    //     icon: 'el-icon-s-platform',
                    //     index: 'types',
                    //     title: '类型分析',
                    // }
                    ]
                },{
                    icon: 'el-icon-document',
                    index: 'taskList',
                    title: '爬取任务管理'
                }
            ]
        // }
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
    width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}

.sidebar > ul {
    height: 100%;
}
</style>
