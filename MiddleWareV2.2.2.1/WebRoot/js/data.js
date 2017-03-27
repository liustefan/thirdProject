// Highcharts 全局设置 语言
Highcharts.setOptions({
    global: {
        useUTC: false
    },
    lang: {
        contextButtonTitle: '图表导出菜单',
        downloadJPEG: '导出JPG格式图片',
        downloadPDF: '导出PDF格式文档',
        downloadPNG: '导出PNG格式图片',
        downloadSVG: '导出SVG矢量图',
        printChart: '打印图表',
        rangeSelectorZoom: ' ',
        resetZoom: '重置缩放',
        loading: '加载中',
        rangeSelectorFrom: '从',
        rangeSelectorTo: '至',
        months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月','十月', '十一月', '十二月'],
        weekdays: ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期天']
    }
});
function setChart(id, objDt) {
    //设置网页大小
    var chartbox = $("#" + id);
    chartbox.css("width", objDt.width);
    chartbox.css("height", objDt.height);

//    Array.prototype.max = function() { // 最大值
//        return Math.max.apply({}, this)
//    };
//    Array.prototype.min = function() { // 最小值
//        return Math.min.apply({}, this)
//    };
//    var rmax = objDt.data.max();
//    var rmin = objDt.data.min();

    var Hz = 1000 / objDt.fs; // objDt.fs
    var startdt = objDt.measureTime;
    var startTime = "";
    var endTime = "";
    var pageDt = 6 * (objDt.page - 1) * 1000;
    var maxValue = 2000;
    var minValue = -2000;

    startdt += pageDt;
    
    switch(objDt.type)
    {
    case "ab_ecg":
    	Hz  = 6000/objDt.data.length ; //频率
    	minValue = -1000;
        maxValue = 1000;
        break;
    case "ecg":
        minValue = -1000;
        maxValue = 1000;
        
        break;
    case "mi_ecg":
        minValue = -127;
        maxValue = 128;
        
        break;
    case "ab_ecg1":
        minValue = 0;
        maxValue = 255;
        
        break;
    case "ab_ecg2":
        minValue = -127;
        maxValue = 128;
        
        break;
    case "ppg":
        minValue = 0;
        maxValue = 128;
        
        break;
    case "hr_ecg":
        startTime = objDt.startTime;
        endTime = objDt.endTime;
        minValue = 0;
        maxValue = 150;
        
        break;
    case "hr_ppg":
        minValue = 30;
        maxValue = 200;
        startTime = objDt.startTime;
        endTime = objDt.endTime;
        break;
    }

    //  console.log(maxValue);
    //  console.log(minValue);
    var cellHeight = (maxValue - minValue) / objDt.height * 10;
    //  console.log(objDt);
    //  maxValue = (rmax < maxValue) ? rmax : maxValue;
    //  minValue = (rmin > minValue) ? rmin : minValue;
    //  console.log(rmax);
    //  console.log(rmin);
    //心电 ecgChart
    if (chartbox.length > 0 && id == "ecgChart") {
        // 创建highcharts
        window.chart = new Highcharts.StockChart({
//            loading: {
//                hideDuration: 1000,
//                showDuration: 1000
//            },
            chart: {
                //          reflow: false,//图会根据当窗口或者框架改变大小时而改变
                height: objDt.height,
                width: objDt.width,
                // animation: true,//是否开启动画
                renderTo: id, // 对应的div id
                // backgroundColor: '#EEEEEE', //设置背景颜色 （可以设置渐变）
                // borderColor: '#ddd', //图表外边框的颜色
                // borderRadius: 30, //图表外边框圆角
                //          borderWidth : '1', // 图表外边框的宽度
                panning: false, //禁用放大
                pinchType: '', //禁用手势操作
                zoomType: "",
                panKey: 'shift',
                // 一些事件 比如addSeries, click, load,redraw, selection
//                events: {
//                    load: function() {
//                        this.showLoading();
//                    }
//                }

            },
            // 选中缩放的地方
            rangeSelector: {
                inputEnabled: true,
                buttons: [
                    //           {
                    //           type : 'second',
                    //           count : 3,
                    //           text : '3秒'
                    //           }
                    //          , {
                    //              type : 'all',
                    //              text : '全部'
                    //          } 
                ],
                buttonTheme: {
                    width: 50
                },
                inputDateFormat: '%Y-%m-%d %H:%M:%S:%L',
                inputBoxWidth: 160,
//                inputEditDateFormat: '%Y-%m-%d %H:%M:%S:%L',
                selected: 0,
                // 格式化form 和TO
                // inputDateParser : function(value) {
                // value = value.split(/[:\.]/);
                // console.log(value);
                // return Date.UTC(2015, 05, 28, 11 + parseInt(value[0], 10),
                // 23 + parseInt(value[1], 10),
                // 05 + parseInt(value[2], 10), parseInt(value[3], 10));
                // }
            },
            // 图表缩放导航
            navigator: {
                enabled: false,
                margin: 10
            },
            exporting: {
                enabled: false
            },
            title: {
                text: ' '
            },
            yAxis: {
                min: minValue,
                max: maxValue,
                tickInterval: cellHeight * 5, // 每大格0.5 毫伏 500
                gridLineWidth: 1,
                gridLineColor: '#ff6a6a', // #ed7b10
                minorGridLineWidth: 0.5, // 次级网格线的宽度 0.5
                minorGridLineColor: '#eda8b7', // 次级网格线的颜色 b0a091
                minorTickInterval: cellHeight, // 次级网格的间隔 0.1毫伏 100
                labels: {
                    enabled: false
                        // 是否显示y轴
                }
            },
            tooltip: {
                enabled: false,
                crosshairs: false //跟随光标的精准线
                    // valueDecimals: 2
            },
            xAxis: {
                type: 'datetime',
                //          minRange : 3000, // 最小放大比例 1S
//                min: startdt,
                tickPixelInterval: 100, // 网格间隔宽度默认100
                tickLength: 0, // 刻度线的长度
                tickInterval: 200, // 每大格0.2S
                gridLineWidth: 1, // 网格线的宽度
                gridLineColor: '#ff6a6a', //网格线的颜色 #ed7b10
                minorGridLineColor: '#eda8b7', //次级网格线的颜色 b0a091
                minorGridLineWidth: 0.5, //次级网格线的宽度
                minorTickInterval: 40, //次级网格的间距 0.04S
                labels: {
                    enabled: false
                        //是否显示x轴
                }

            },
            series: [{
                type: 'line',
                states: {
                    hover: {
                        enabled: false
                    }
                },
                pointStart: startdt + Hz, // 第一个点的时间
                pointInterval: Hz, // 频率
                pointIntervalUnit: 'milliseconds',
                data: objDt.data,
                lineWidth: 1,
                zones: [{
                    color: '#000000', //设置折线的颜色
                }],
                enabled: true
            }],
            credits: {
                enabled: false,
                // text: 'hk-bithealth.com',
                // href: 'http://www.hk-bithealth.com',
                // position: {
                //     align: 'right'
                // }
            },
            scrollbar: {
                enabled: false
                    //     barBackgroundColor: 'gray',
                    //     barBorderRadius: 7,
                    //     barBorderWidth: 0,
                    //     buttonBackgroundColor: 'gray',
                    //     buttonBorderWidth: 0,
                    //     buttonArrowColor: 'yellow',
                    //     buttonBorderRadius: 7,
                    //     rifleColor: 'yellow',
                    //     trackBackgroundColor: 'white',
                    //     trackBorderWidth: 1,
                    //     trackBorderColor: 'silver',
                    //     trackBorderRadius: 7
            }
        });
//        chartbox.highcharts().hideLoading();
        var dtBox =  $(".highcharts-range-selector");
        dtBox.attr("readonly", "readonly");
    }
   
    //瞬时心率图 instantChart hr_ecg
    if (chartbox.length > 0 && id == "instantChart") {
        chartbox.highcharts({
            chart: {
                type: 'line',
                plotBackgroundColor: 'rgba(238, 254, 238, 1)',
                plotBorderColor: '#000',
                plotBorderWidth: 1,
            },
            title: {
                text: ' ',
            },
            xAxis: {
                type: 'datetime',
                labels: {
                     // step: 2,
                	enabled: false,
                    format: '{value:%H:%M:%S}'
                },
                tickWidth: 1,
                tickLength: 0,
                tickColor: '#000',
                lineColor: '#000',
                lineWidth: 1,
            },
            yAxis: {
                // offset: 5,
                min: minValue,
                max: maxValue,
                tickInterval: 25, //每大格25
                gridLineWidth: 0,
                tickWidth: 1,
                tickLength: 5,
                tickColor: '#000',
                // lineColor: '#000',
                // lineWidth: 1,
                title: {
                    text: ' '
                },
                plotLines: [{
                    value: 50,
                    width: 1,
                    color: 'red',
                    zIndex: 1,
                    label: {
                        text: '正常值范围',
                        y: -20,
                        align: 'center',
                        style: {
                            color: 'red',
                            fontSize: 18
                        },

                    }
                }, {
                    value: 100,
                    width: 1,
                    color: 'red'
                }, ]
            },

            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: false,
                    },
                    enableMouseTracking: false,
                    marker: {
                        enabled: false,
                    }
                }
            },
            exporting: {
                enabled: false
            },
            series: [{
                data: objDt.data,
                pointStart: startdt,
                pointInterval: 10 * 1000,
                lineWidth: 1,
                zones: [{
                    color: '#000' //设置折线的颜色
                }],
            }],
            credits: {
                enabled: false,
            },
            legend: {
                enabled: false,
            },
        });
        var chart = chartbox.highcharts();
        chart.setTitle({
            text: startTime + " 至 " + endTime
        });
    }
    //脉搏波图 sphygmusChart ppg
    if (chartbox.length > 0 && id == "sphygmusChart") {
        chartbox.highcharts({
            chart: {
                type: 'line',
                plotBackgroundColor: 'rgba(238, 254, 238, 1)',
                plotBorderColor: '#000',
                plotBorderWidth: 1,
                height: objDt.height,
                width: objDt.width,
            },
            title: {
                text: ' ',
            },
            xAxis: {
                labels: {
                     step: 5,
                     align: 'left',
                     format: '{value:%Y-%m-%d %H:%M:%S}'
                },
                type: 'datetime',
                tickWidth: 1,
                tickLength: 5,
                tickColor: '#000',
                lineColor: '#000',
                lineWidth: 1,
            },
            yAxis: {
            	labels: {
            		enabled: false,
            	},
                min: minValue,
                max: maxValue,
                tickInterval: 22, //每大格22
                gridLineWidth: 0,
                tickWidth: 0,
                tickLength: 5,
                tickColor: '#000',
                title: {
                    text: ' '
                },                
            },

            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: false,
                    },
                    enableMouseTracking: false,
                    marker: {
                        enabled: false,
                    }
                }
            },
            exporting: {
                enabled: false
            },
            series: [{
                data: objDt.data,
                pointStart: startdt,
                pointInterval: Hz,
                lineWidth: 1,
                zones: [{
                    color: '#000' //设置折线的颜色
                }],
            }],
            credits: {
                enabled: false,
            },
            legend: {
                enabled: false,
            },
        });
//        var chart = chartbox.highcharts();
//        chart.setTitle({
//            text: Highcharts.dateFormat('%Y-%m-%d %H:%M:%S:%L', chart.series[0].xAxis.min) + " 至 " + Highcharts.dateFormat('%Y-%m-%d %H:%M:%S:%L', chart.series[0].xAxis.max)
//        });
    }
    // 瞬时脉率图  instantSphygmusChart hr_ppg
    if (chartbox.length > 0 && id == "instantSphygmusChart") {
        chartbox.highcharts({
            chart: {
                type: 'line',
                plotBackgroundColor: 'rgba(238, 254, 238, 1)',
                plotBorderColor: '#000',
                plotBorderWidth: 1,
            },
            title: {
                text: ' ',
            },
            xAxis: {
                labels: {
                    // step: 2,
                    enabled: false,
                    format: '{value:%H:%M:%S}'
                },
                type: 'datetime',
//                tickWidth: 0,
                tickLength: 0,
//                tickColor: '#000',
                lineColor: '#000',
                lineWidth: 1,
            },
            yAxis: {
                // offset: 5,
                min: minValue,
                max: maxValue,
                tickInterval: 25, //每大格25
                gridLineWidth: 0,
                tickWidth: 1,
                tickLength: 5,
                tickColor: '#000',
                title: {
                    text: ' '
                },
                plotLines: [{
                    value: 50,
                    width: 1,
                    color: 'red',
                    zIndex: 1,
                    label: {
                        text: '正常值范围',
                        y: -20,
                        align: 'center',
                        style: {
                            color: 'red',
                            fontSize: 18
                        },

                    }
                }, {
                    value: 100,
                    width: 1,
                    color: 'red'
                }, ]
            },

            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: false,
                    },
                    enableMouseTracking: false,
                    marker: {
                        enabled: false,
                    }
                }
            },
            exporting: {
                enabled: false
            },
            series: [{
                data: objDt.data,
                pointStart: startdt,
                pointInterval: 1,
                lineWidth: 1,
                zones: [{
                    color: '#000' //设置折线的颜色
                }],
            }],
            credits: {
                enabled: false,
            },
            legend: {
                enabled: false,
            },
        });
        var chart = chartbox.highcharts();
        chart.setTitle({
            text: startTime + " 至 " + endTime
        });
    }
}
