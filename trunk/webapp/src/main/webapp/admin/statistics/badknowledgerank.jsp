<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%>
<script src="/js/echarts.min.js"></script>

<div id="main" style="width: 100%;height:400px;float:left;max-width: 550px;">
</div>
<script type="text/javascript">
//基于准备好的dom，初始化echarts实例
var myBadChart = echarts.init(document.getElementById('main'));
//指定图表的配置项和数据
var xAxisBadData = [];
var baddate = [];

$(document).ready(function(){
    //do something
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
    		        data: ['差评'],
    		        align: 'left',
    		        left: 10
    		    },
    		    xAxis: {
    		        data: xAxisBadData,
//    		        name: '知识标题',
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
//     		        nameRotate:50,
    		        splitArea: {show: false}
    		    },
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
			    console.log(params);
			});
    	
    });
});

</script>