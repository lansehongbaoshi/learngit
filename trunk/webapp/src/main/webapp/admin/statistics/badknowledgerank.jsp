<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%>
<script src="/js/echarts.min.js"></script>

<div id="main" style="width: 100%;height:400px;float:left;max-width: 550px;">
</div>
<script type="text/javascript">


$(document).ready(function(){
    //do something
//    getBadRank();
    getGoodRank();
});

function getGoodRank(){
	
	//基于准备好的dom，初始化echarts实例
	var myBadChart = echarts.init(document.getElementById('main'));
	//指定图表的配置项和数据
	var xAxisBadData = [];
	var baddate = [];
	
	$.getJSON("/admin/statistics/goodKnowledgeRank.action?t="+new Date(), function showSearchResult(json) {
        if(json.flag=="success"){
            var objs = json.o;
            for(var i=0;i<objs.length;i++){
                var item = objs[i];
                var one = [];
                xAxisBadData.push(item.title);
                baddate.push(item.usefulPersent);
            }
        }
        
        var option = {
                backgroundColor: '#eee',
                title: {
                    text: '好评排名',
                    left :'center'
                },
                tooltip: {},
                legend: {
                    data: [{
                        name:'好评'
                    }],
//                  backgroundColor:'#ccc',
                    align: 'left',
                    left: 10
                },
                color:['#c23531'],
                xAxis: {
                    data: xAxisBadData,
//                  name: '知识标题',
                    silent: false,
                    axisLabel: {
                        interval: 0,
                        margin:3,
                        rotate: -30
                    },
                    axisLine: {onZero: true},
                    splitLine: {show: false},
                    splitArea: {show: false}
                },
                yAxis: {
                    name: '好评比率(%)',
//                  nameRotate:50,
                    splitArea: {show: false}
                },
                graphic: [
                          {
                              type: 'image',
                              id: 'goBad',
                              right: 50,
                              top: 5,
                              z: 10,
                              style: {
                                  image: '/images/wap/help/qiehuan.png',
                                  width: 25,
                                  height: 25,
                                  opacity: 0.9
                              },
                              onclick: function () {
                            	  getBadRank();
                              }
                          }
                      ],
                grid: {
                    left: 50,
                    bottom:100
                },
                series: [
                         {
                             name: '好评',
                             type: 'bar',
                             data: baddate
                         }
                     ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myBadChart.setOption(option);
            myBadChart.on('click', function (params) {
                // 控制台打印数据的名称
                //console.log(params);
            });
        
    });
}

function getBadRank(){
	
	//基于准备好的dom，初始化echarts实例
	var myBadChart = echarts.init(document.getElementById('main'));
	//指定图表的配置项和数据
	var xAxisBadData = [];
	var baddate = [];
	
	$.getJSON("/admin/statistics/badKnowledgeRank.action?t="+new Date(), function showSearchResult(json) {
        if(json.flag=="success"){
            var objs = json.o;
            for(var i=0;i<objs.length;i++){
                var item = objs[i];
                var one = [];
                xAxisBadData.push(item.title);
                baddate.push(item.unusefulPersent);
            }
        }
        
        var option = {
                backgroundColor: '#eee',
                title: {
                    text: '差评排名',
                    left :'center'
                },
                tooltip: {},
                legend: {
                    data: [{
                        name:'差评'
                    }],
//                  backgroundColor:'#ccc',
                    align: 'left',
                    left: 10
                },
                color:['#2f4554'],
                xAxis: {
                    data: xAxisBadData,
//                  name: '知识标题',
                    silent: false,
                    axisLabel: {
                        interval: 0,
                        margin:3,
                        rotate: -30
                    },
                    axisLine: {onZero: true},
                    splitLine: {show: false},
                    splitArea: {show: false}
                },
                yAxis: {
                    name: '差评比率(%)',
//                  nameRotate:50,
                    splitArea: {show: false}
                },
                graphic: [
                          {
                              type: 'image',
                              id: 'goBad',
                              right: 50,
                              top: 5,
                              z: 10,
                              style: {
                                  image: '/images/wap/help/qiehuan.png',
                                  width: 25,
                                  height: 25,
                                  opacity: 0.9
                              },
                              onclick: function () {
                            	  getGoodRank();
                              }
                          }
                      ],
                grid: {
                    left: 50,
                    bottom:100
                },
                series: [
                         {
                             name: '差评',
                             type: 'bar',
                             data: baddate
                         }
                     ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myBadChart.setOption(option);
            myBadChart.on('click', function (params) {
                // 控制台打印数据的名称
                //console.log(params);
            });
        
    });
}
</script>