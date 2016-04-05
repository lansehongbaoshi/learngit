<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.chsi.knowledge.pojo.KnowledgeData,com.chsi.knowledge.util.ManageCacheUtil,java.util.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<style>
.content { padding-top: 25px;}
.logo { position: relative; width:1000px; height: 90px; margin:0 auto; background: #afafaf; text-align:center; color: #fff; font-size: 22px;}
.logo .switch { position: absolute; right: 0; top: 0; background: #f30;}	
.main { width:998px; margin:0 auto; background: #f1f1f1; border:1px solid #b8b8b8;  border-top:none;}
.main .left { float: left; width: 630px; }
.main .left #showbox { height: 380px; padding-top: 25px; border-bottom: 1px solid #b8b8b8; overflow-y: auto;overflow-x: hidden; }
.main .left #sendbox { position: relative; height: 140px;   background: #fff; }
.main .left #sendbox #inputbox { width: 610px; height: 80px; padding: 10px; border: none;  }
.main .left #sendbox #sendBtn { position: absolute; right: 25px; bottom: 10px; display: inline-block; width: 100px; height: 30px; line-height: 28px; border: 1px solid #333; text-align: center; }
.main .right{ float: right; width: 367px; border-left: 1px solid #b8b8b8;}
.main .right .normal-question { height: 350px; border-bottom: 1px solid #b8b8b8;}
.main .right .advert{ height: 190px; }
.robot{ position:relative; float: left; max-width: 450px; margin-left: 70px; margin-bottom: 25px;  padding: 10px; border: 1px solid #b8b8b8; border-radius: 5px; background-color: #fff; word-break: break-all; word-wrap: break-word;}
.robot .icon1 { position: absolute; left:-66px; top: -6px; width: 62px; height: 62px; background: url(../images/wap/help/icon_yz.png) no-repeat ;}
.person{position:relative; float: right; max-width: 450px;  margin-right: 70px; margin-bottom: 25px;  padding: 10px; border: 1px solid #b8b8b8; border-radius: 5px; background-color: #fff; word-break: break-all; word-wrap: break-word;}
.person .icon2 { position: absolute; right: -66px; top: -6px; width: 62px; height: 62px; background: url(../images/wap/help/icon_zh.png) no-repeat ;}
</style>
<script>
var sessionId = "<s:property value='sessionId'/>";
function input() {
	var q = $("#inputbox").val();
	if($.trim(q)=="") return;	
	$("#showbox").append("<div class='clearfix'><div class='person'><div class='icon2'></div>"+q+"</div></div>");
	var height = $("#showbox").prop('scrollHeight');//原来的高度	
	$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示								
	$.post("/robot/qa.action",{sessionId:sessionId,q:q},function(result){
		if(result.flag=='true') {
			var data = result.o;
			//console.log(result);
			var a="<div class='clearfix'><div class='robot'><div class='icon1'></div>";
			if(data.AType=='INDEFINITE') {
				a+="您的意思是?<br>"
				for(i in data.result) {
					var knowl = data.result[i];
					a+="<a class='indefinite' data-id='"+knowl.knowId+"' href='javascript:void(0)'>"+"["+knowl.system+"]"+knowl.title+"</a><br>";
				}
			}else if(data.AType=='NONE'){
				a+=data.content+"<br>";
			}else{
				a+=data.content+"<br>";
			}
			a+="</div></div>";
			$("#showbox").append(a);	
			var height = $("#showbox").prop('scrollHeight');//原来的高度	
			$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示										
		}
	},'json');
	$("#inputbox").val('');	
}
$(function(){
	$("#showbox").html("<div class='clearfix'><div class='robot'><div class='icon1'></div>你好，我是学信网机器人，有什么可以帮助您的？</div></div>");
	$("#sendBtn").on("click",function(){
		input();		
	});
	$("#inputbox").keydown(function(event) {
		if(event.keyCode==13) {
			input();
			event.preventDefault();
		}
	});
	$(document).on("click","a.indefinite",function(){
		$("#showbox").append("<div class='clearfix'><div class='person'><div class='icon2'></div>"+$(this).text()+"</div></div");
		var knowId = $(this).data("id");
		$.post("/robot/qa.action",{sessionId:sessionId,knowId:knowId},function(result){
			if(result.flag=='true') {
    			var data = result.o;
    			//console.log(result);
    			var a="<div class='clearfix'><div class='robot'><div class='icon1'></div>";
  				a+=data.result[0].summary+"<br>";
    			a+="</div></div>";
    			$("#showbox").append(a);
    			var height = $("#showbox").prop('scrollHeight');//原来的高度	
				$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示					
			}
		},'json');
	});
	var _wh = $(window).height();
	var _ch = $(".main").height();
	var _lh =$("#showbox").height();
	var _rh =$(".main .right .advert").height();
	function footerHeight(){
		if(_wh - _ch > 146){			
			$(".main").css("height",(_wh-146)+"px");		
		}
		if(_wh - _lh > 312){
			$("#showbox").css("height",(_wh-312)+"px");
		}
		if(_wh - _rh > 497){
			$(".main .right .advert").css("height",(_wh-497)+"px");
		}		
	}
	footerHeight();
	$(window).resize(function() {
		var _wh = $(this).height();
		if(_wh - _ch > 146){			
			$(".main").css("height",(_wh-146)+"px");		
		}
		if(_wh - _lh > 312){
			$("#showbox").css("height",(_wh-31)+"px");
		}	
		if(_wh - _rh > 497){
			$(".main .right .advert").css("height",(_wh-497)+"px");
		}		
	});
})
</script>
<div class="logo">
	学信网机器人
	<div class="switch">开关</div>
</div>
<div class="main clearfix">
	<div class="left">
		<div id="showbox"></div>
		<div id="sendbox">
			<textarea id="inputbox" placeholder="请用一句话简要描述您的问题，比如'账户忘记密码？'"></textarea>
			<input id="sendBtn" type="button" value="发送">
		</div>
	</div>
	<div class="right">
		<div class="normal-question"></div>
		<div class="advert"></div>
	</div>
</div>