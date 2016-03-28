<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.chsi.knowledge.pojo.KnowledgeData,com.chsi.knowledge.util.ManageCacheUtil,java.util.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<style>
.robot{background-color: #EDF3F1;width:400px;position:relative;}
.person{text-align:right;position:relative;}
</style>
<script>
var sessionId = "<s:property value='sessionId'/>";
function input() {
	var q = $("#inputbox").val();
	if($.trim(q)=='') return;
	var height = $("#showbox").prop('scrollHeight');//原来的高度
	$("#showbox").append("<div class='person'>"+q+"</div>");
	$.post("/robot/qa.action",{sessionId:sessionId,q:q},function(result){
		if(result.flag=='true') {
			var data = result.o;
			//console.log(result);
			var a="<div class='robot'>";
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
			a+="</div>";
			$("#showbox").append(a);
			if(height>$("#showbox").height()){
				$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示
			}
		}
	},'json');
	$("#inputbox").val('');
}
$(function(){
	$("#showbox").html("<div class='robot'>你好，我是学信网机器人，有什么可以帮助您的？</div>");
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
		var height = $("#showbox").prop('scrollHeight');
		$("#showbox").append("<div class='person'>"+$(this).text()+"</div>");
		//scrollToBottom();
		var knowId = $(this).data("id");
		$.post("/robot/qa.action",{sessionId:sessionId,knowId:knowId},function(result){
			if(result.flag=='true') {
    			var data = result.o;
    			//console.log(result);
    			var a="<div class='robot'>";
  				a+=data.result[0].summary+"<br>";
    			a+="</div>";
    			$("#showbox").append(a);
    			//scrollToBottom();
    			if(height>$("#showbox").height()){
    				$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示
    			}
			}
		},'json');
	});
})
</script>
<div id="showbox" style="border:1px solid #ccc;height:400px;width:600px;overflow-y: auto;overflow-x: hidden;">

</div>
<div style="">
<textarea id="inputbox" style="border:1px solid #ccc;height:100px;width:600px;" placeholder="请用一句话简要描述您的问题，比如'账户忘记密码？'">
</textarea>
<input id="sendBtn" type="button" value="发送">
</div>