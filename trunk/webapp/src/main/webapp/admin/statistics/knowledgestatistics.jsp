<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%>
<script src="/js/echarts.min.js"></script>

<div id="knowledgestatistics" style="width: 770px;height:500px;float:left;">
</div>
<script type="text/javascript">
$(document).ready(function(){
    //do something
    getAllSystemRank();
});


function getAllSystemRank(){
	$.getJSON("/admin/statistics/knowledgeStatistics.action?t="+new Date(), function showSearchResult(json) {
        //指定图表的配置项和数据
        console.log(json);
        
        //基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('knowledgestatistics'));
        
        // 指定图表的配置项和数据
        var xAxisData = [];
        var data1 = [];
        var data2 = [];

        var itemStyle = {
                normal: {
                },
                emphasis: {
                    barBorderWidth: 1,
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowOffsetY: 0,
                    shadowColor: 'rgba(0,0,0,0.5)'
                }
            };
        var data = json.o;

        for (var i = 0; i < data.length; i++) {
            xAxisData.push(data[i].name);
            data1.push(Number( data[i].goodPercent).toFixed(2));
            data2.push(-1*Number(data[i].badPercent).toFixed(2));
        }
        
        var option = {
            backgroundColor: '#eee',
            title: {
                text: '系统知识统计表',
                left :'center'
            },
            legend: {
                data: ['好评', '差评'],
                align: 'left',
                left: 10
            },
            brush: {
                type: ['rect', 'polygon', 'lineX', 'lineY', 'keep', 'clear'],
                xAxisIndex: 0
            },
            toolbox: {
                show: true,
                feature: {

                    dataView: {readOnly: true},
                    magicType: {
                        type: ['stack', 'tiled']
                    },
                    restore: {},
                    saveAsImage: {}
                }
            },
            tooltip: {},
            xAxis: {
                data: xAxisData,
                name: 'X Axis',
                silent: false,
                axisLabel: {
                    interval: 0,
                    rotate: -45
                },
                axisLine: {onZero: true},
                splitLine: {show: false},
                splitArea: {show: false}
            },
            yAxis: {
//              inverse: true,
                max:100,
                min:-100,
                splitArea: {show: false}
            },
            grid: {
                left: 100
            },
            visualMap: {
                type: 'continuous',
                dimension: 1,
                text: ['High', 'Low'],
//              inverse: true,
                itemHeight: 340,
                calculable: true,
                min: -100,
                max: 100,
                top: 65,
                left: 10,
                inRange: {
                    colorLightness: [0.4, 0.8]
                }
//               outOfRange: {
//                   color: '#bbb'
//               },
//               controller: {
//                   inRange: {
//                       color: '#2f4554'
//                   }
//               }
            },
            series: [
                     {
                         name: '好评',
                         type: 'bar',
                         stack: 'one',
                         itemStyle: itemStyle,
                         data: data1
                     },
                     {
                         name: '差评',
                         type: 'bar',
                         stack: 'one',
                         itemStyle: itemStyle,
                         data: data2
                     }
                 ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.on('click', function (params) {
            // 控制台打印数据的名称
            var systemName = params.name;
            var seriesName = params.seriesName;
            
            getRankInSystem(systemName,seriesName);
        });
    });
}



function getRankInSystem(systemName,seriesName){
	
	if(seriesName=="好评"){
		console.log(systemName+"--"+seriesName);
		$.getJSON("/admin/statistics/goodKnowledgeInSystem.action?t="+new Date(),{
			systemName:systemName,
			seriesName:seriesName
		},
		function showSearchResult(json) {
			console.log("点击了："+systemName+"的"+seriesName);
			console.log(json);
			var goodKnowledgeInSystemChart = echarts.init(document.getElementById('knowledgestatistics'));
			var goodData = [];
			var xgoodAxisData = [];
			var knows = json.o;
			for(var i=0;i<knows.length;i++ ){
				var data = knows[i];
				goodData.push(Number(data.usefulPersent).toFixed(2));
				xgoodAxisData.push(data.title);
			}
			
			var goodInSystemOption = {
	                backgroundColor: '#eee',
	                title: {
	                    text: systemName+seriesName+'统计表',
	                    left :'center'
	                },
	                tooltip: {},
	                legend: {
	                    data: [seriesName],
	                    align: 'left',
	                    left: 10
	                },
	                xAxis: {
	                    data: xgoodAxisData,
	                    name: '知识标题',
	                    silent: false,
	                    axisLabel: {
	                        interval: 0,
	                        rotate: -45
	                    },
	                    axisLine: {onZero: true},
	                    splitLine: {show: false},
	                    splitArea: {show: false}
	                },
	                yAxis: {
	                    name: seriesName+'比率(%)',
//	                  nameRotate:50,
	                    splitArea: {show: false}
	                },
	                graphic: [
	                    {
		                    type: 'image',
		                    id: 'logo',
		                    right: 50,
		                    top: 50,
		                    z: 10,
	                        style: {
	                            image: '/images/wap/help/wap_back.png',
	                            width: 25,
	                            height: 25,
	                            opacity: 0.9
	                        },
	                        onclick: function () {
	                        	getAllSystemRank();
	                        }
	                    }
	                ],
	                grid: {
	                    left: 100
	                },
	                series: [
	                         {
	                             name: seriesName,
	                             type: 'bar',
	                             data: goodData
	                         }
	                     ]
	            };
			goodKnowledgeInSystemChart.setOption(goodInSystemOption);
			
		});
	}else{
		$.getJSON("/admin/statistics/badKnowledgeInSystem.action?t="+new Date(),{
            systemName:systemName,
            seriesName:seriesName
        },
        function showSearchResult(json) {
        	console.log("点击了："+systemName+"的"+seriesName);
            console.log(json);
            
            var badKnowledgeInSystemChart = echarts.init(document.getElementById('knowledgestatistics'));
            var badData = [];
            var xbadAxisData = [];
            var knows = json.o;
            for(var i=0;i<knows.length;i++ ){
                var data = knows[i];
                badData.push(Number(data.unusefulPersent).toFixed(2));
                xbadAxisData.push(data.title);
            }
            
            var badInSystemOption = {
                    backgroundColor: '#eee',
                    title: {
                        text: systemName+seriesName+'统计表',
                        left :'center'
                    },
                    tooltip: {},
                    legend: {
                        data: [seriesName],
                        align: 'left',
                        left: 10
                    },
                    xAxis: {
                        data: xbadAxisData,
                        name: '知识标题',
                        silent: false,
                        axisLabel: {
                            interval: 0,
                            rotate: -45
                        },
                        axisLine: {onZero: true},
                        splitLine: {show: false},
                        splitArea: {show: false}
                    },
                    yAxis: {
                        name: seriesName+'比率(%)',
//                    nameRotate:50,
                        splitArea: {show: false}
                    },
                    graphic: [
	                        {
	                        type: 'image',
	                        id: 'logo',
	                        right: 50,
	                        top: 50,
	                        z: 10,
                            style: {
                                image: '/images/wap/help/wap_back.png',
                                width: 25,
                                height: 25,
                                opacity: 0.9
                            },
                            onclick: function () {
                                getAllSystemRank();
                            }
                        }
                    ],
                    grid: {
                        left: 100
                    },
                    series: [
                             {
                                 name: seriesName,
                                 type: 'bar',
                                 data: badData
                             }
                         ]
                };
            badKnowledgeInSystemChart.setOption(badInSystemOption);
            
        });
	}
}


</script>