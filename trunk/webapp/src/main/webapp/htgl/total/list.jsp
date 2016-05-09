<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.chsi.framework.util.TimeUtil" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
Calendar cal = Calendar.getInstance();
cal.add(Calendar.DAY_OF_MONTH, -1);
String endDate = TimeUtil.getTime(cal, "yyyy-MM-dd");
cal.add(Calendar.DAY_OF_MONTH, -9);
String startDate = TimeUtil.getTime(cal, "yyyy-MM-dd");
%>
<script src="/js/echarts.min.js"></script>
<link href="/assets/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/assets/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/assets/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<style>
.form_datetime{
    width:140px;
}
.leftpart{
  width:140px;
  float:left;
  margin-right:10px;
}
.line-height-30{
line-height:30px;
}
.margin-top-10{
margin-top: 10px;
}
</style>
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
            <div id="knowl_condition" class="leftpart" style="display:none;">
            <span class="">系统：<select id="systemIds" class="" name="systemId"></select></span><br>
            <span class="">个数：<input id="topCnt" type="text" name="topCnt" value="6" style="width:100px;"></span><br>
            <span class="line-height-30">开始日期：</span>                            
            <div class="input-group date form_datetime a_v_time" style="margin:0;" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
            <input id="startDate" name="startDate" class="form-control for-height32" size="16" type="text" value="<%=startDate%>" readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
            <span class="line-height-30"> 截止日期：</span>
            <div class="input-group date form_datetime a_v_time" style="margin:0;" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
            <input id="endDate" name="endDate" class="form-control for-height32" size="16" type="text" value="<%=endDate%>" readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
            <p class="margin-top-10" style="text-align:right;"><button id="knowl_btn">统计</button></p>
            <p class="margin-top-10"><b>说明：</b><br>访问量大的前n个知识每日访问量情况</p>
            </div>
            <div id="knowl_session" class="leftpart" style="display:none;">
            <span class="line-height-30">开始日期：</span>                            
            <div class="input-group date form_datetime a_v_time" style="margin:0;" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
            <input id="startDate1" name="startDate" class="form-control for-height32" size="16" type="text" value="<%=startDate%>" readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
            <span class="line-height-30"> 截止日期：</span>
            <div class="input-group date form_datetime a_v_time" style="margin:0;" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
            <input id="endDate1" name="endDate" class="form-control for-height32" size="16" type="text" value="<%=endDate%>" readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
            <p class="margin-top-10" style="text-align:right;"><button id="knowl_btn1">统计</button></p>
            <p class="margin-top-10"><b>说明：</b><br>空会话——用户并未发出提问</p>
            </div>
            <div id="knowl_q" class="leftpart" style="display:none;">
            <span class="line-height-30">开始日期：</span>                            
            <div class="input-group date form_datetime a_v_time" style="margin:0;" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
            <input id="startDate2" name="startDate" class="form-control for-height32" size="16" type="text" value="<%=startDate%>" readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
            <span class="line-height-30"> 截止日期：</span>
            <div class="input-group date form_datetime a_v_time" style="margin:0;" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1">
            <input id="endDate2" name="endDate" class="form-control for-height32" size="16" type="text" value="<%=endDate%>" readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>
            <p class="margin-top-10" style="text-align:right;"><button id="knowl_btn2">统计</button></p>
            <p class="margin-top-10"><b>说明：</b><br>机器人回答情况统计，点击“无答案”可查看详情</p>
            </div>
            <div id="main" style="width: 900px;height:500px;float:left;"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
$('.form_datetime').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2
});

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
    			$("#knowl_session").hide();
    			$("#knowl_q").hide();
    			var myChart = echarts.init(document.getElementById('main'));
      			myChart.setOption({});
    			return;
    		} else if(type=='session') {
                $("#knowl_condition").hide();
                $("#knowl_session").show();
                $("#knowl_q").hide();
                var myChart = echarts.init(document.getElementById('main'));
                myChart.setOption({});
                return;
    		} else{
                $("#knowl_condition").hide();
                $("#knowl_session").hide();
                $("#knowl_q").show();
                var myChart = echarts.init(document.getElementById('main'));
                myChart.setOption({});
                return;
    		}
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
    	$("#knowl_btn1").on('click',function(){
    		var data = {};
    		data.type = 'session';
			data.startTime=$("#startDate1").val();
			data.endTime=$("#endDate1").val();
    		$.post("/htgl/total/option.jsp",data,function(result){
        		var option = eval(result);
        		var myChart = echarts.init(document.getElementById('main'));
      			myChart.setOption(option);
        	});
    	});
    	$("#knowl_btn2").on('click',function(){
    		var data = {};
    		data.type = 'q';
			data.startTime=$("#startDate2").val();
			data.endTime=$("#endDate2").val();
    		$.post("/htgl/total/option.jsp",data,function(result){
        		var option = eval(result);
        		var myChart = echarts.init(document.getElementById('main'));
      			myChart.setOption(option);
      			myChart.on('click', function (params) {
    				if(params.name=='无答案')
    			    window.open('/htgl/total/listQ.action?start=0&type=' + encodeURIComponent(params.name)+'&startTime='+$("#startDate2").val()+'&endTime='+$("#endDate2").val());
    			});
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