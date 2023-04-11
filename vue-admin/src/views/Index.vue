<template>
    <div>
        <!--    一言-->
        <el-row :gutter="20" style="margin-bottom: 20px;padding-bottom: 20px;">
            <el-col :span="12" class="fs-18">
                <el-card v-loading="loading" style="height:212px;">
                    <div slot="header" class="clearfix">
                        <span><i class="el-icon-position"></i> 一句话</span>
                        <el-button style="float: right; padding: 3px 0" icon="el-icon-refresh" @click="yy()"
                                   type="text">换一句
                        </el-button>
                    </div>
                    {{ yiyan }} <span v-show="author"> ——{{ author }}</span>
                </el-card>
            </el-col>
            <!--      音乐-->
            <el-col :span="12" class="fs-18" style="-ms-overflow-style:none;overflow-y: inherit">
                <el-card>
                    <div slot="header" class="clearfix">
                        <span><i class="el-icon-position"></i> 音乐</span>
                    </div>
                    <meting-js
                            server="netease"
                            type="playlist"
                            id="2624316345"
                            list-folded="true"
                            list-max-height="100px"
                            mutex="true">
                    </meting-js>

                </el-card>
            </el-col>
        </el-row>

        <el-row :gutter="10" class="mb-20">
            <el-col :span="6">
                <el-card style="color: #3f87a6">
                    <div>
                        <el-icon class="el-icon-user-solid"></el-icon>
                        用户总数
                    </div>
                    <div type="info" style="padding: 10px 0;text-align: center;font-weight: bolder">
                        <el-tag>{{ total }}</el-tag>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #f69d3c">
                    <div>
                        <el-icon class="el-icon-user"></el-icon>
                        在线总数
                    </div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bolder">
                        <el-tag type="success">{{ onlineTotal }}</el-tag>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: deeppink ">
                    <div>
                        <el-icon class="el-icon-user-solid"></el-icon>
                        文章总数
                    </div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bolder">
                        <el-tag type="danger">{{ articleTotal }}</el-tag>
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="color: #409EFF">
                    <div>
                        <el-icon class="el-icon-user-solid"></el-icon>
                        用户总数
                    </div>
                    <div style="padding: 10px 0;text-align: center;font-weight: bolder">1000</div>
                </el-card>
            </el-col>
        </el-row>

        <el-row :gutter="20" class="mb-20">
            <el-col :span="6">
                <el-card id="main" style="height: 400px;padding-top: 20px"></el-card>
            </el-col>
            <el-col :span="6">
                <el-card id="pie" style="height: 400px;padding-top: 20px"></el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="height: 400px;padding-top: 20px">
                    <div slot="header" class="clearfix">
                        <span><i class="el-icon-position"></i> 技术选型</span>
                    </div>
                    <el-row :gutter="10">
                        <el-col :span="12">
                            <h4>前端</h4>
                            <ul class="th">
                                <li>Vue2+axios+Vuex</li>
                                <li>Elementui</li>
                                <li>Aplayer</li>
                                <li>Echarts</li>
                                <li>Yarn</li>
                                <li>一言</li>
                                <li>MavonEditor</li>
                                <li>...</li>
                            </ul>
                        </el-col>
                        <el-col :span="12">
                            <h4>前端</h4>
                            <ul>
                                <li>SpringBoot</li>
                                <li>Redis</li>
                                <li>JWT</li>
                                <li>MyBatis</li>
                                <li>MyBatis-Plus</li>
                                <li>Fastjson</li>
                                <li>swagger</li>
                                <li>hutool</li>
                                <li>...</li>
                            </ul>
                        </el-col>
                    </el-row>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card style="height: 400px;padding-top: 20px">这是主页</el-card>
            </el-col>
        </el-row>

        <el-row>
            <el-card>
                <el-col :span="6">
                    <div id="main" style="width: 500px;height: 400px">这是主页</div>
                </el-col>
                <el-col :span="6">
                    <div id="pie" style="width: 500px;height: 400px">这是主页</div>
                </el-col>
            </el-card>
        </el-row>
    </div>
</template>

<script>
import * as echarts from "echarts"

export default {
    name: "Index",
    data() {
        return {
            total: 0,
            articleTotal: 0,
            onlineTotal: 0,
            yiyan: '',
            author: '',
            loading: false
        }
    },
    mounted() {
        var chartDom = document.getElementById('main');
        var myChart = echarts.init(chartDom);
        var option;

        option = {
            title: {
                text: '各季度注册人数统计',
                subtext: '趋势图',
                left: 'left'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['第一季度', '第二季度', '第三季度', '第四季度']
            },
            toolbox: {
                size: 'mini',
                show: true,
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: {
                type: 'category',
                data: ['第一季度', '第二季度', '第三季度', '第四季度']
            },
            yAxis: {
                type: 'value',
            },
            series: [
                {
                    data: [],
                    type: 'line',
                    label: 'name'
                },
                {
                    data: [],
                    type: 'bar',
                }
            ]
        };
        var piecChartDom = document.getElementById('pie');
        var pieMyChart = echarts.init(piecChartDom);
        var pieOption;
        pieOption = {
            title: {
                text: '各季度注册人数统计',
                subtext: '比例图',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    name: '人数',
                    type: 'pie',
                    radius: '50%',
                    data: [],
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        this.request.get("/echarts/mebers").then(res => {
            this.total = res.data.total
            this.articleTotal = res.data.articleTotal
            this.onlineTotal = res.data.onlineTotal
            // option.xAxis.data = res.data.y;
            option.series[0].data = res.data.records;
            option.series[1].data = res.data.records;
            myChart.setOption(option);

            pieOption.series[0].data = [
                {value: res.data.records[0], name: '第一季度'},
                {value: res.data.records[1], name: '第二季度'},
                {value: res.data.records[2], name: '第三季度'},
                {value: res.data.records[3], name: '第四季度'},
            ]
            pieMyChart.setOption(pieOption);
        })
        this.yy();
    },
    methods: {
        yy() {
            this.loading = true
            setTimeout(() => {
                this.request.get("https://v1.hitokoto.cn/?c=f&encode=josn").then(res => {
                    this.yiyan = res.hitokoto;
                    this.author = res.creator;
                    this.loading = false;
                })
            }, 2000);
        }
    },
}
</script>

<style lang="scss">
#app{
    line-height: 2;
}
ul {
  padding: 0;
  margin: 0;
}

ul {
  list-style-type: none;
  margin-top: 10px;
}

h4 {
  margin-top: 0;
}
</style>