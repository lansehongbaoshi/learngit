<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.chsi.framework.util.TimeUtil" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
Calendar cal = Calendar.getInstance();
cal.add(Calendar.DAY_OF_MONTH, -1);
String startDate = TimeUtil.getTime(cal, "yyyyMMdd");
cal.add(Calendar.DAY_OF_MONTH, -9);
String endDate = TimeUtil.getTime(cal, "yyyyMMdd");
%>
<script src="/js/echarts.min.js"></script>
<div class="breadcrumbs" id="breadcrumbs">
  <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {}
            </script>
  <ul class="breadcrumb">
    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="/htgl/index.action">首页</a></li>
    <li class="active">统计分析</li>
  </ul>
  <!-- /.breadcrumb -->
  <div class="nav-search" id="nav-search">
    <form class="form-search">
      <span class="input-icon"> <input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off"> <i class="ace-icon fa fa-search nav-search-icon"></i>
      </span>
    </form>
  </div>
  <!-- /.nav-search -->
</div>
<div class="page-content">
  <div class="rows">
    <div class="col-xs-12">
      <div class="row">
        <div class="col-xs-12">
          <h3 class="row header smaller lighter blue">
            <span class="col-xs-2">统计分析</span> 
            <span class="col-xs-8">
            <a href="#" target="_self" class="btn-new-mail pull-right charttype" data-type="session"> 
            <span class="btn btn-primary no-border"><span class="bigger-110">机器人会话统计</span></span>
            </a>
            <a href="#" target="_self" class="btn-new-mail pull-right charttype" data-type="q"> 
            <span class="btn btn-primary no-border"><span class="bigger-110">机器人问题统计</span></span>
            </a>
            <a href="#" target="_self" class="btn-new-mail pull-right charttype" data-type="visitlog"> 
            <span class="btn btn-primary no-border"><span class="bigger-110">访问量统计</span></span>
            </a>
            </span>
          </h3>
          <div>
            <div id="knowl_condition" style="display:none;"><span>系统：<select id="systemIds" class="" name="systemId"></select>排名靠前个数：<input id="topCnt" type="text" name="topCnt">开始日期：<input id="startDate" type="text" name="startDate">截止日期：<input id="endDate" type="text" name="endDate"><button id="knowl_btn">统计</button></span></div>
            <div id="main" style="width: 1000px;height:500px;float:left;"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
function countAll(values) {
	var total = 0;
	for(var i in values) {
		total+=values[i].value;
	}
	return total;
}
function getNames(values){
	var arr = new Array();
	for(var i in values) {
		arr.push(values[i].name);
	}
	return arr;
}
    $(function () {
    	$(".charttype").on('click',function(){
    		var type = $(this).data('type');
    		if(type=='') return;
    		var data = {};
    		data.type = type;
    		if(type=='visitlog') {
    			$("#knowl_condition").show();
    			var myChart = echarts.init(document.getElementById('main'));
      			myChart.setOption({});
    			return;
    		} else {
    			$("#knowl_condition").hide();
    		}
    		$.post("/htgl/total/option.jsp",data,function(result){
        		var option = eval(result);
        		var myChart = echarts.init(document.getElementById('main'));
      			myChart.setOption(option);
      			if(type=='q'){
        			myChart.on('click', function (params) {
        				if(params.name=='无答案')
        			    window.open('/htgl/total/listQ.action?type=' + encodeURIComponent(params.name));
        			});
    			}
        	});
    	});
    	$("#knowl_btn").on('click',function(){
    		var data = {};
    		data.type = 'visitlog';
    		data.systemId=$("#systemIds").val();
			data.topCnt=$("#topCnt").val();
			data.startTime=$("#startDate").val();
			data.endTime=$("#endDate").val();
    		$.post("/htgl/total/option.jsp",data,function(result){
        		var option = eval(result);
        		var myChart = echarts.init(document.getElementById('main'));
      			myChart.setOption(option);
        	});
    	});
    	$.getJSON("/htgl/listSystem.action",
            function showSystems(json) {
                if (json.flag == "true") {
                    var options = "";
                    for (var i = 0; i < json.o.length; i++) {
                        var option = json.o[i];
                        options += "<option value='" + option.id + "'>" + option.name + "</option>";
                    }
                    $("#systemIds").html(options);
                }
            }
        );
    })
</script>