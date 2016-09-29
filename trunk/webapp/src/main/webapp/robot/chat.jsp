<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.chsi.knowledge.pojo.KnowledgeData,com.chsi.knowledge.util.ManageCacheUtil,java.util.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" href="http://t2.chei.com.cn/common/zbbm/js/autocomplete/jqueryui.autocomplete.css" />
<link rel="stylesheet"  href='http://t1.chsi.com.cn/common/plugins/dialog/6.0.5/css/ui-dialog.css' />
<script src="http://t3.chei.com.cn/common/zbbm/js/autocomplete/jqueryui.autocomplete.js"></script>
<script src='http://t1.chei.com.cn/common/wap/help/js/template.js'></script>
<script src="http://t1.chei.com.cn/common/kn/js/kn_page.js"></script>
<script src='http://t1.chsi.com.cn/common/plugins/dialog/6.0.5/dialog-min.js'></script>
<script src='http://t1.chsi.com.cn/common/plugins/dialog/6.0.5/dialog-plus-min.js'></script>
<%String systemId = request.getParameter("system"); 
systemId = systemId==null?"account":systemId;%>
<style>
.content { padding: 25px 0 30px 0; background: #ccc;}
.logo { position: relative; width:1000px; height: 70px; margin:0 auto;  background-color: #28bca4; color: #fff; }
.logo .logo_img { position: absolute; left: 0px; top: -25px; width: 78px; height: 93px; background: url(../images/wap/help/logo.png) no-repeat 2px 0;}	
.logo .logo_title { padding: 10px 0 0 85px; font-size: 22px;}
.logo .logo_sub { padding: 0px 0 0 85px; font-size: 16px; opacity: 0.5; filter:alpha(opacity=50);}
.logo .switch { position: absolute; right: 15px; top: 20px; width: 30px; height: 30px; background: url(../images/wap/help/close.png) no-repeat; cursor: pointer;}	
.logo .switch:hover{ opacity: 0.8; filter:alpha(opacity=80);}
.logo .weixin { position: absolute; right: 60px; top: 0px; width: 70px; height: 70px; line-height: 110px; background: url(../images/wap/help/weixin.png) 15px 10px no-repeat; font-size: 12px; text-align: center; cursor: pointer;}
.logo .weixin .weixin_l {position: absolute; top: 69px; right: -10px; display: none; width: 180px; height: 180px; border-radius: 3px; box-shadow: 0px 4px 6px #999; background: url(../images/wap/help/chsi.jpg) no-repeat;}
.logo .weixin .triangle-up {position: absolute; top:62px; right: 30px; display: none; width: 0; height: 0; border-left: 8px solid transparent;border-right: 8px solid transparent; border-bottom: 8px solid #fff;}
.logo .weixin:hover .weixin_l { display: block;}
.logo .weixin:hover .triangle-up { display: block;}
.main { width:1000px; margin:0 auto; background: #f1f1f1; }
.main .left { float: left; width: 645px; position: relative; padding-top: 12px; }
.main .left .tp { width: 590px; margin: 0 auto; padding-left: 35px; border-radius: 5px; line-height: 35px; color: #666; background: url(../images/wap/help/tp.png) no-repeat 5px 8px;  background-color: #dffef9; }
.main .left #showbox { position: relative; height: 478px; padding-top: 30px; border-bottom: 1px solid #d3d3d3; overflow-y: scroll;overflow-x: hidden; }
.main .left #sendbox { position: relative; height: 165px;   background: #fff; }
.main .left #sendbox .send_top { height: 100px;}
.main .left #sendbox .send_bottom { height: 65px;  background: #f1f1f1; } 
.main .left #sendbox #inputbox { width: 625px; height: 80px; padding: 10px; line-height: 25px; border: none;  }
.main .left #sendbox #sendBtn { position: absolute; right: 10px; bottom: 12px; display: inline-block; width: 90px; padding: 6px 12px;  border: 1px solid transparent; border-radius: 5px; line-height: 1.42857;	box-shadow: 0 0 0 #209683  inset, 0 3px 0 0 #209683; background-color: #28bca4; border-color: #28bca4; color: #fff; font-size: 16px; text-align: center; cursor: pointer; }
.main .left #sendbox #contentwordage { position: absolute; left: 10px; bottom: 20px;  color: #999;}
.main .left #sendbox #contentwordage .red { color: #f30;}
.main .left .all_tips { position: absolute; bottom: 165px; left: 0; }
.main .right{ float: right; width: 354px; height: 721px;  border-left: 1px solid #d3d3d3; background: #fff;}
#ui-id-1 {max-width: 665px;}
#kn_labels {  background: #f1f1f1; overflow: hidden;}
#kn_labels ul { width: 120%;}
#kn_labels ul li { float: left;  width: 117px; height: 50px; line-height: 50px; border-right: 1px solid #d3d3d3; border-bottom: 1px solid #d3d3d3; text-align: center; color: #666;  background: #f1f1f1;}
#kn_labels ul li.cn {cursor: pointer;}
#kn_labels ul li.selected { background: #fff; color: #333;}
#kn_labels ul li.no-border-right { border-right: none;}
#kn_lists .top_title { padding: 12px; border-bottom: 1px solid #d3d3d3; }
#kn_lists ul li { padding:2px 12px; height: 35px; line-height: 35px; border-bottom: 1px solid #d3d3d3; color: #666; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;}
#kn_lists ul li a { color: #666; }
#kn_lists .pagenation { padding:30px 12px;}
#kn_lists .pagenation .kn-page_up_no { display:inline-block ; width: 70px; height: 30px; margin-right: 25px; line-height: 30px; border: 1px solid #d3d3d3; border-radius: 5px;  text-align: center;  background: #f2f2f2; cursor: default; }
#kn_lists .pagenation .kn-pagination_down {  display:inline-block ; width: 70px; height: 30px; margin-right: 25px; line-height: 30px; border: 1px solid #28bca4;  border-radius: 5px;text-align: center;   color: #fff; background: #28bca4; cursor: pointer; }
#kn_lists .pagenation .kn-pagination_down:hover{text-decoration: none; opacity: 0.8; filter:alpha(opacity=80);}
.main .right .advert{ height: 65px;  background: #f1f1f1; }
.system {color: #999; margin-left: 10px;}
.hide { display: none;}
.marginb { margin-bottom: 25px;}
.robot{ position:relative; float: left; max-width: 466px; margin-left: 75px; padding: 10px; border: 1px solid #d3d3d3; border-radius: 5px; background-color: #fff; word-break: break-all; word-wrap: break-word;}
.robot .icon1 { position: absolute; left:-66px; top: -6px; width: 62px; height: 62px; background: url(../images/wap/help/icon_robot.png) no-repeat 0 5px;}
.robot a { color:#0e6c9c;}
.robot .feedback{ min-width: 320px; color: #999; }
.robot .feedback .system_1 { float: left; color: #0d9c85;}
.robot .feedback .title { float: right;}
.robot label, .robot  input { cursor: pointer;}
.robot label { margin-left: 5px; }
.robot input  { margin-right: 2px; vertical-align: -1px;}
.robot img {max-width: 466px; cursor: pointer;}
.ui-dialog-footer button.ui-dialog-autofocus { background-color: #28bca4; }
.ui-dialog-footer button.ui-dialog-autofocus:hover, .ui-dialog-footer button.ui-dialog-autofocus:focus, .ui-dialog-footer button.ui-dialog-autofocus:active { background-color: #28bca4; }
#kn_feedback { width: 450px; padding: 6px; border: 1px solid #9c9b9a; border-radius: 5px; box-shadow: 0 0 5px #b2b2b2; color: #292929;  }
.person{position:relative; float: right; max-width: 466px;  margin-right: 65px; padding: 10px; border: 1px solid #28bca4; border-radius: 5px; background-color: #28bca4; color: #fff; word-break: break-all; word-wrap: break-word;}
.person .icon2 { position: absolute; right: -75px; top: -6px; width: 62px; height: 62px; background: url(../images/wap/help/icon_user.png) no-repeat 0 5px;}
.ui-dialog-content img {max-width: 1200px;}
</style>
<div class="logo">
	<div class="logo_img"></div>
	<div class="logo_title">学信网机器人 | chsi.com.cn</div>
	<div class="logo_sub">24小时竭诚为您服务</div>
	<div class="switch"></div>
	<div class="weixin">
		扫码关注
		<span class="triangle-up"></span>
        <span class="weixin_l"></span> 
    </div>
</div>
<div class="main clearfix">
	<div class="left">
		<div class="tp">对话已建立，学信网机器人正在为您服务 ......</div>
		<div id="showbox"></div>
		<div id="sendbox">
			<div class="send_top">
				<textarea id="inputbox" maxlength="100" autocomplete="off" placeholder="请用一句话简要描述您的问题，比如：如何找回用户名和密码"></textarea>
				<input id="judge" type="hidden" value="" />
			</div>
			<div class="send_bottom">
				<input id="sendBtn" type="button" value="发送"/>
				<span id="contentwordage"></span>
			</div>
		</div>
		<div class="all_tips"></div>
	</div>
	<div class="right">
		<div class="normal-question">
			<div id="kn_labels" >
				<ul class="clearfix">
					<li class="cn" onclick="ajaxJSONP('systemId=account','knList')" data-id="account" >学信网账号</li>
					<li class="cn" onclick="ajaxJSONP('systemId=my','knList')" data-id="my">学信档案</li>
					<li class="cn no-border-right" onclick="ajaxJSONP('systemId=yz_wb','knList')" data-id="yz_wb" >研招统考</li>
					<li class="cn" onclick="ajaxJSONP('systemId=yz_tm','knList')" data-id="yz_tm">研招推免</li>
                    <li class="cn" onclick="ajaxJSONP('systemId=zb','knList')" data-id="zb">应征报名</li>
                    <li class="cn no-border-right" onclick="ajaxJSONP('systemId=cet','knList')" data-id="cet">四六级查分</li>
                    <li class="cn" onclick="ajaxJSONP('systemId=yz_gat','knList')" data-id="yz_gat">面向港澳台招生</li>
                    <li class="cn" onclick="ajaxJSONP('systemId=gk_tk','knList')" data-id="gk_tk">高考统考招生</li>
                    <li class="cn no-border-right" onclick="ajaxJSONP('systemId=gk_ts','knList')" data-id="gk_ts">高考特殊类型招生</li>
                    <li class="cn" onclick="ajaxJSONP('systemId=gk_wb','knList')" data-id="gk_wb">高考特殊类型报名</li>
                    <li class="cn" onclick="ajaxJSONP('systemId=xlrz','knList')" data-id="xlrz">学历与成绩认证</li>
                    <li class="no-border-right"></li>
				</ul>
			</div>
			<div id="kn_lists">
				<div class="top_title">热门问题</div>
				<div id="kn_list"></div>
				<!--<ul>
					<li>1、如何判断网报是否成功？</li>
					<li>2、如何进行网上报名？</li>					
					<li>3、学历（学籍）信息校验是怎么回事？</li>				
					<li>4、网上报名的有效期限？</li>					
					<li>5、报考硕士研究生是否必须网上报名？</li>					
				</ul>			-->
				<!--<div class="pagenation" id="pagenation_list">-->
				</div>
			</div>			
			<script id="snippet_list" type="text/html">
				<ul>
				 {{each o as value i}}  
				       <li title="{{value.title}}"><span> {{i+1}}. <a  href="javascript:;" data-id="{{value.knowId}}">{{value.title}}</a></span></li>
				 {{/each}}	
				</ul>
			</script>				
		</div>
		<!--<div class="advert"></div>-->
	</div>
</div>
<script>
var sessionId = "<s:property value='sessionId'/>";
//评论
function htmlspecialchars(str){    
    str = str.replace(/&/g, '&amp;');  
    str = str.replace(/</g, '&lt;');  
    str = str.replace(/>/g, '&gt;');  
    str = str.replace(/"/g, '&quot;');  
    str = str.replace(/'/g, '&#039;');  
    return str;  
}
function ajaxJSONP_s(data,callback){
    $.ajax({ 
            global:true, 
            type: "post",
            cache: false,
            async: true,
            crossDomain:true,
            url: "/view/discuss.action",  
            data:data,
            dataType: "jsonp",  
            jsonp: "callback", //回调函数的参数  
            jsonpCallback: callback, //回调函数的名称
            error: function(){  
                alert('请求时发生了错误，请稍后再试');  
            }  
        }); 
         return false;
}
function feedback(json){
    return false;
}	
//文本框输入调用
function input() {
	var q =htmlspecialchars($("#inputbox").val());
	if($.trim(q)=="") return;	
	$("#showbox").append("<div class='clearfix marginb'><div class='person'><div class='icon2'></div>"+q+"</div></div>");
	var height = $("#showbox").prop("scrollHeight");//原来的高度	
	$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示								
	$.post("/robot/qa.action",{sessionId:sessionId,q:q},function(result){
		if(result.flag=='true') {
			var data = result.o;
			//console.log(result);
			var a="<div class='clearfix marginb hide'><div class='robot'><div class='icon1'></div>";
			if(data.AType=="INDEFINITE") {
				a+="您的意思是?";
				for(i in data.result) {
					var knowl = data.result[i];
					a+="<br /><a class='indefinite' data-id='"+knowl.knowId+"' href='javascript:void(0)'>"+"["+knowl.system+"]  <span>"+knowl.title+"</span></a>";
				}
				a+="<br />以上问题中没有我的答案？请联系客服MM！<br />客服热线：010-82199588 <br />客服传真：010-80115555转475249 或 010-62160938 <br />客服邮箱：kefu@chsi.com.cn";
			}else if(data.AType=="NONE"||data.AType=="ROBOT"){
				a+=data.content;
				//a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";
			}else{
				a+=data.content;
				a+="<div class='feedback clearfix'>";
				a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";
				a+="<span class='title'>这个回答是否有用？";
				a+="<span class='help_judge'><label><input type='radio'class='helpfulYes' value='1' name='discussStatus' data-id='"+data.result[0].knowId+"' />是</label>";
				a+="<label><input type='radio' class='helpfulNo' value='0' name='discussStatus' data-id='"+data.result[0].knowId+"' />否</label></span></span></div>";
			}
			a+="</div></div>";
			$("#showbox").append(a);
			setTimeout(function () {
				$("#showbox .marginb:last").removeClass("hide");
				var height = $("#showbox").prop('scrollHeight');//原来的高度	
				$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示		
			}, 500);									
		}
	},"json");
	$("#inputbox").val("");	
}
//点击常见问题
$(function(){
	$("#showbox").html("<div class='clearfix marginb'><div class='robot'><div class='icon1'></div><s:property value='hello'/></div></div>");
	$("#sendBtn").on("click",function(){
		if($("#inputbox").val().length>100){
			alert("字数超过限制！");
		}else{
			input();
	        var pattern = "还可以输入<span class='red'>100</span>个字";
            $('#contentwordage').html(pattern);	
   	        $('#ui-id-1').hide();           
		}
	});	
	$("#inputbox").keydown(function(event) {
		if(event.keyCode==13) {	
			event.preventDefault();
			if($("#judge").val()==""){
				if($("#inputbox").val().length>100){
					alert("字数超过限制！");
				}else{	
					input();						
			        var pattern = "还可以输入<span class='red'>100</span>个字";
		            $('#contentwordage').html(pattern);
		            $('#ui-id-1').hide();
				}
			}
		}
	});
	$(document).on("click","a.indefinite",function(){
		$("#showbox").append("<div class='clearfix marginb'><div class='person'><div class='icon2'></div>"+$(this).children("span").text()+"</div></div");
		var knowId = $(this).data("id");
		$.post("/robot/qa.action",{sessionId:sessionId,knowId:knowId},function(result){
			if(result.flag=='true') {
    			var data = result.o;
    			//console.log(result);
    			var a="<div class='clearfix marginb hide'><div class='robot'><div class='icon1'></div>";
  				a+=data.result[0].summary;
  				a+="<div class='feedback clearfix'>";
  				a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";
  				a+="<span class='title'>这个回答是否有用？";
				a+="<span class='help_judge' ><label><input type='radio'class='helpfulYes' value='1' name='discussStatus' data-id='"+data.result[0].knowId+"' />是</label>";
				a+="<label><input type='radio' class='helpfulNo' value='0' name='discussStatus' data-id='"+data.result[0].knowId+"' />否</label></span></span></div>";
    			a+="</div></div>";
    			$("#showbox").append(a);
    			setTimeout(function () {
    				$("#showbox .marginb:last").removeClass("hide");
    				var height = $("#showbox").prop('scrollHeight');//原来的高度	
					$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示		
				}, 500);	
			}
		},'json');
	});
	//控制页面整体高度
	var _wh = $(window).height();
	var _lh =$("#showbox").height();
	var _rh =$(".main .right").height();
	function footerHeight(){
		if(_wh - _lh > 368){			
			$("#showbox").css("height",(_wh-368)+"px");
			$(".main .right").css("height",(_wh-125)+"px");
		}else{
			$("#showbox").css("height",_lh+"px");
			$(".main .right").css("height",_rh+"px");
		}
	}
	footerHeight();
	$(window).resize(function() {
		var _wh = $(this).height();
		if(_wh - _lh > 368){			
			$("#showbox").css("height",(_wh-368)+"px");
			$(".main .right").css("height",(_wh-125)+"px");		
		}else{
			$("#showbox").css("height",_lh+"px");
			$(".main .right").css("height",_rh+"px");
		}
		var height = $("#showbox").prop('scrollHeight');//原来的高度	
		$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示		
	});
	//控制可以输入字数
	var limitNum = 100;
    var pattern = "还可以输入<span class='red'>"+ limitNum + "</span>个字";
    $('#contentwordage').html(pattern);   
    $('#inputbox').on("input propertychange",function(){		
        var remain = $(this).val().length;
        if(remain > 100){
                pattern = "<span class='red'>字数超过限制！</span>";
            }else{
                var result = limitNum - remain;
                pattern = "还可以输入<span class='red'>" + result + "</span>个字";
            }
        $("#contentwordage").html(pattern);       
    });
    $('#inputbox').on("keydown",function(){
    	$("#judge").val("");  
    });
    //控制选择标签变色
 	$("#kn_labels ul .cn").on("click",function(){
		$(this).addClass("selected").siblings().removeClass("selected");		
	}); 
	//图片跳转
	$("#showbox").on("click","img",function(){
		var s_src=$(this)[0].src;
//		console.log(s_src);
//		window.open(s_src);
		dialog({
			title:"查看大图",
			content: "<img src='"+s_src+"'/>"
			}).showModal();				
	});		
	//评论
	$("#showbox").on("click",".helpfulYes",function(){
		var v=$(this).val();
		var _d ={"knowledgeId":$(this).data("id"),"discussStatus":v};
		ajaxJSONP_s(_d,"feedback");
		$(this).parents(".title").html("已收到您的建议。谢谢！");
	});	
	$("#showbox").on("click",".helpfulNo",function(){
		var v=$(this).val();
		var id=$(this).data("id");
		var t=$(this).parents(".title");
		var k=$(this);
		dialog({
            	title:"您的建议（200个字符以内，选填）：",
                content: "<div><textarea maxlength='200' tabindex='1' rows='5' cols='80' id='kn_feedback' name='feedback'></textarea></div>",
                backdropOpacity: 0.2,
                padding:10,
                ok:function () {
                	var content=$("#kn_feedback").val();
//              	console.log(id);
                	if($.trim(content).length>=200){ 
                		alert("您输入的评论内容过长，请输200字以内的字符");
                	    return false;
                	}else{	                    	
                    	var _c={"knowledgeId":id,"discussStatus":"0","content":$.trim(content)};
                    	ajaxJSONP_s(_c,"feedback");
                		t.html("已收到您的建议。谢谢！");
            		}
                },
                okValue:"确定",
                cancel:function () {
          			k.attr("checked",false);
                },
                cancelValue: "取消"	                    
        }).showModal();	
	});	
});

//artTemplate辅助方法
template.helper("paramStr", function (o) {
    return $.param(o);
});
 
//通用ajax函数
function ajaxJSONP(data,callback){
    $.ajax({ 
            global:true, 
            type: "post",
            cache: false,
            async: true,
            crossDomain:true,
            url: "/view/getHotKnowledgeList.action",  
            data:data,
            dataType: "jsonp",  
            jsonp: "callback", //回调函数的参数  
            jsonpCallback: callback, //回调函数的名称
            error: function(){  
                alert('请求时发生了错误，请稍后再试');  
            }  
        }); 
    return false;
}    
 //列表翻页
 function knList(json){
	if(!json.flag){ alert(json.errorMessages); return;}
	$("#pagenation_list span").remove();
    $("#kn_list").html(template("snippet_list",json)); //直接使用json数据
//  drawPage("#pagenation_list",json["o"]);
	$("#kn_list a").on("click",function(){
		var q=htmlspecialchars($(this).text());
		$("#showbox").append("<div class='clearfix marginb'><div class='person'><div class='icon2'></div>"+q+"</div></div");
		var height = $("#showbox").prop("scrollHeight");//原来的高度	
		$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示								
		var knowId = $(this).data("id");
		$.post("/robot/qa.action",{sessionId:sessionId,knowId:knowId},function(result){
			if(result.flag=='true') {
    			var data = result.o;
    			//console.log(result);
    			var a="<div class='clearfix marginb hide'><div class='robot'><div class='icon1'></div>";
  				a+=data.result[0].summary;
  				a+="<div class='feedback clearfix'>"
  				a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";
  				a+="<span class='title'>这个回答是否有用？";
				a+="<span class='help_judge'><label><input type='radio'class='helpfulYes' value='1' name='discussStatus' data-id='"+data.result[0].knowId+"' />是</label>";
				a+="<label><input type='radio' class='helpfulNo' value='0' name='discussStatus'  data-id='"+data.result[0].knowId+"' />否</label></span></span></div>";
    			a+="</div></div>";
    			$("#showbox").append(a);    			
    			setTimeout(function () {
    				$("#showbox .marginb:last").removeClass("hide");
    				var height = $("#showbox").prop('scrollHeight');//原来的高度	
					$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示		
				}, 500);
			}
		},"json");
	});      
}    
// function knListPage(json){
//   if(!json.flag){ alert(json.errorMessages); return;}
//  $("#kn_list").html(template('snippet_list',json["o"]));   
//	$("#kn_list a").on("click",function(){
//		var q=$(this).text();
//		$("#showbox").append("<div class='clearfix marginb'><div class='person'><div class='icon2'></div>"+q+"</div></div");
//		var height = $("#showbox").prop('scrollHeight');//原来的高度	
//		$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示								
//		var knowId = $(this).data("id");
//		$.post("/robot/qa.action",{sessionId:sessionId,knowId:knowId},function(result){
//			if(result.flag=='true') {
//  			var data = result.o;
//  			//console.log(result);
//  			var a="<div class='clearfix marginb'><div class='robot'><div class='icon1'></div>";
//				a+=data.result[0].summary;
//				a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";
//  			a+="</div></div>";
//  			$("#showbox").append(a);
//  			var height = $("#showbox").prop('scrollHeight');//原来的高度	
//				$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示					
//			}
//		},'json');
//	});     
     //drawPage("#pagenation_list",json["o"])
//}  
 //分页初始化
//function drawPage(ele,obj){
//   var _last_tag = obj["navigations"].pop();
//	var _page_obj = obj["knowListVO"].pagination;
//	if(!!!_page_obj){ return false;}
// 	$(ele).pagefoot({ css:"mj_pagefoot"      //分页脚css样式类
//		,pageSize:_page_obj["pageCount"]  //每页显示的记录数
//  	,displayNum:0  //显示的固定页数
//  	,itemsNum:_page_obj["totalCount"]                //总记录数
//  	,currentNum:_page_obj["curPage"]              //当前页码
//  	,currentClass:"page_current"     //当前页码时样式
//  	,linkClass:"page_normal"             //页码链接样式
//  	,previous:"上一页"      			//上一页显示文本
//  	,next:"下一页"         					 //下一页显示文本
//  	,abledClass:"kn-pagination_down"		//上下页-可用时样式
//		,disabledClass:"kn-page_up_no"		//上下页-不可用时样式
//  	,paging:function(page){ //分页事件触发时callback函数
//          _last_tag["param"]["curPage"] = page;
//           ajaxJSONP($.param(_last_tag["param"]),'knListPage');          
//      }           
//	}); 
//}   
//自动完成
$(function() {
	$("#inputbox").autocomplete({
		minLength: 0,
        max: 5,
        delay: 200,
        source: function (request, response) {
            var term = request.term;
            var postdata = {"keywords":request.term};
            var _url = "http://kl.chsi.com.cn/search/autoTitle.action";
            $.ajax({ 
	            type: "get",
	            cache: false,
	            async: true,
	            crossDomain:true,
	            url: _url,  
	            data:postdata, 
	            dataType: "jsonp",
	            jsonp: "callback", //回调函数的参数  
	            jsonpCallback: "parseAutoSearch", //回调函数的名称  
	            success: function(data) {
	                response($.map(data["o"].knows, function(item){
	                    return {
			                value: item.title,
			                keywords: item.keywords,
			                label: item.title,
			                desc: item.summary,
			                system: item.system,
			                knowId: item.knowId
			            }		                
	                }));
	            },
	            error: function(XMLHttpRequest, textStatus, errorThrown){
	                alert(textStatus+'请求时发生了错误，请稍后再试');  
	            }  
    		}); 
        },
		focus: function(event, ui) {
			$("#judge").val("0");
		},
		change:function(event, ui) {
			 $("#judge").val("");
		},
        select: function(event, ui){
        	var knowId = ui.item.knowId;
        	var q=htmlspecialchars(ui.item.value);   
        	$("#showbox").append("<div class='clearfix marginb'><div class='person'><div class='icon2'></div>"+q+"</div></div");
        	$.post("/robot/qa.action",{sessionId:sessionId,knowId:knowId},function(result){
				if(result.flag=="true") {
	    			var data = result.o;
	    			//console.log(result);
	    			var a="<div class='clearfix marginb hide'><div class='robot'><div class='icon1'></div>";
	  				a+=data.result[0].summary;
	  				a+="<div class='feedback clearfix'>";
					a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";
					a+="<span class='title'>这个回答是否有用？";
					a+="<span class='help_judge'><label><input type='radio' class='helpfulYes' value='1' name='discussStatus'  data-id='"+data.result[0].knowId+"' />是</label>";
					a+="<label><input type='radio' class='helpfulNo' value='0' name='discussStatus' data-id='"+data.result[0].knowId+"'/>否</label></span></span></div>";  						
	    			a+="</div></div>";
	    			$("#showbox").append(a);
				}	
				setTimeout(function () {
    				$("#showbox .marginb:last").removeClass("hide");
    				var height = $("#showbox").prop('scrollHeight');//原来的高度	
					$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示		
				}, 500);					
				$("#judge").val("");
				$("#inputbox").val("");
			},"json"); 
			var pattern = "还可以输入<span class='red'>100</span>个字";
            $("#contentwordage").html(pattern);	 
            $("#judge").val("");
            $("#inputbox").val("");	
			return false;
       },
       position: { my : "left bottom", at: "left bottom",of: ".all_tips" } 
	}).data("ui-autocomplete")._renderItem = function (ul, item) {   
//  	var reg = new RegExp("("+item.keywords+")","g");
//  	item.desc =  item.desc.replace(reg, "<strong style='color:#c30'>$1</strong>");
//  	item.label =  item.label.replace(reg, "<strong style='color:#c30'>$1</strong>");
        return $("<li>").append("<a>"+item.label+"<span class='system'>["+item.system+"]</span></a>").appendTo(ul);
	};
	$(window).on("beforeunload",function() {
	  $.post("/robot/close.action?sessionId="+sessionId);
	});
	//关闭页面
	$(".switch").click(function(e) {			
//			CloseWebPage();
		if(confirm("确定要离开此页吗？")){
			window.open("about:blank","_self").close();
		}				
	});
//		function CloseWebPage() {
//		    if (navigator.userAgent.indexOf("MSIE") > 0) {
//		        if (navigator.userAgent.indexOf("MSIE 6.0") > 0) {
//		            window.opener = null;
//		            window.close();
//		        } else {
//		            window.open('', '_top');
//		            window.top.close();
//		        }
//		    } else if (navigator.userAgent.indexOf("Firefox") > 0) {
//		        window.location.href = 'about:blank ';
//		    } else {
//		        window.opener = null;
//		        window.open('', '_self', '');
//		        window.close();
//		    }
//		}
});
//初始化
$(function(){ 
    var systemId = "<%=systemId%>";
    $("#kn_labels ul li").each(function(){
        var thisID = $(this).attr("data-id");
        if(thisID==systemId){
            $(this).addClass("selected");
        }
    });
    ajaxJSONP("systemId="+systemId,"knList");   
});
</script>