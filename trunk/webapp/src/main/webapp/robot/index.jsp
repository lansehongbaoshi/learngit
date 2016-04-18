<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.chsi.knowledge.pojo.KnowledgeData,com.chsi.knowledge.util.ManageCacheUtil,java.util.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<link rel="stylesheet" href="http://t2.chei.com.cn/common/zbbm/js/autocomplete/jqueryui.autocomplete.css" />
<script src="http://t3.chei.com.cn/common/zbbm/js/autocomplete/jqueryui.autocomplete.js"></script>
<script src='http://t1.chei.com.cn/common/wap/help/js/template.js'></script>
<script src="http://t1.chei.com.cn/common/kn/js/kn_page.js"></script>
<style>
.content { padding: 25px 0 30px 0; background: #ccc;}
.logo { position: relative; width:1000px; height: 70px; margin:0 auto;  background-color: #28bca4; color: #fff; }
.logo .logo_img { position: absolute; left: 0px; top: -25px; width: 78px; height: 93px; background: url(../images/wap/help/logo.png) no-repeat;}	
.logo .logo_title { padding: 10px 0 0 85px; font-size: 22px;}
.logo .logo_sub { padding: 0px 0 0 85px; font-size: 16px; opacity: 0.5; filter:alpha(opacity=50);}
.logo .switch { position: absolute; right: 15px; top: 20px; width: 30px; height: 30px; background: url(../images/wap/help/close.png) no-repeat; cursor: pointer;}	
.logo .switch:hover{ opacity: 0.8; filter:alpha(opacity=80);}
.main { width:999px; margin:0 auto; background: #f1f1f1; border: 1px solid #d3d3d3; border-right: none;  border-top:none;}
.main .left { float: left; width: 671px; position: relative; }
.main .left #showbox { height: 529px; padding-top: 25px; border-bottom: 1px solid #d3d3d3; overflow-y: auto;overflow-x: hidden; }
.main .left #sendbox { position: relative; height: 165px;   background: #fff; }
.main .left #sendbox .send_top { height: 100px;}
.main .left #sendbox .send_bottom { height: 65px;  background: #f1f1f1; } 
.main .left #sendbox #inputbox { width: 651px; height: 80px; padding: 10px; line-height: 25px; border: none;  }
.main .left #sendbox #sendBtn { position: absolute; right: 10px; bottom: 8px; display: inline-block; width: 92px; height: 45px; line-height: 40px; border: none; background: url(../images/wap/help/send.png) no-repeat; color: #fff; font-size: 16px; text-align: center; cursor: pointer; }
.main .left #sendbox #contentwordage { position: absolute; left: 10px; bottom: 20px;  color: #999;}
.main .left #sendbox #contentwordage .red { color: #f30;}
.main .left .all_tips { position: absolute; bottom: 165px; left: 0; }
.main .right{ float: right; width: 326px;  border-left: 1px solid #d3d3d3; border-right: 1px solid #d3d3d3; background: #fff;}
.main .right .normal-question { height: 655px;}
.main .right #kn_lists {}
#ui-id-1 {max-width: 669px;}
#kn_labels {  background: #f1f1f1; }
#kn_labels ul li { float: left;  width: 108px; height: 50px; line-height: 50px; border-right: 1px solid #d3d3d3; border-bottom: 1px solid #d3d3d3; text-align: center; color: #666;  background: #f1f1f1;}
#kn_labels ul li.cn {cursor: pointer;}
#kn_labels ul li.selected { background: #fff; color: #333;}
#kn_lists {}
#kn_lists .top_title { padding: 12px; border-bottom: 1px solid #d3d3d3; }
#kn_lists ul li { padding:2px 12px; height: 35px; line-height: 35px; border-bottom: 1px solid #d3d3d3; color: #666; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;}
#kn_lists ul li a { color: #666; }
#kn_lists .pagenation { padding:30px 12px;}
#kn_lists .pagenation .kn-page_up_no { display:inline-block ; width: 70px; height: 30px; margin-right: 25px; line-height: 30px; border: 1px solid #d3d3d3; border-radius: 5px;  text-align: center;  background: #f2f2f2; cursor: default; }
#kn_lists .pagenation .kn-pagination_down {  display:inline-block ; width: 70px; height: 30px; margin-right: 25px; line-height: 30px; border: 1px solid #28bca4;  border-radius: 5px;text-align: center;   color: #fff; background: #28bca4; cursor: pointer; }
#kn_lists .pagenation .kn-pagination_down:hover{text-decoration: none; opacity: 0.8; filter:alpha(opacity=80);}
.main .right .advert{ height: 65px;  background: #f1f1f1; }
.system {color: #999; margin-left: 10px;}
.system_1 {color: #999;}
.robot{ position:relative; float: left; max-width: 450px; margin-left: 75px; margin-bottom: 25px;  padding: 10px; border: 1px solid #d3d3d3; border-radius: 5px; background-color: #fff; word-break: break-all; word-wrap: break-word;}
.robot .icon1 { position: absolute; left:-66px; top: -6px; width: 62px; height: 62px; background: url(../images/wap/help/icon_yz.png) no-repeat ;}
.robot a { color:#0e6c9c;}
.person{position:relative; float: right; max-width: 450px;  margin-right: 75px; margin-bottom: 25px;  padding: 10px; border: 1px solid #28bca4; border-radius: 5px; background-color: #28bca4; color: #fff; word-break: break-all; word-wrap: break-word;}
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
				a+="您的意思是?"
				for(i in data.result) {
					var knowl = data.result[i];
					a+="<br /><a class='indefinite' data-id='"+knowl.knowId+"' href='javascript:void(0)'>"+"["+knowl.system+"]"+knowl.title+"</a>";
				}
			}else if(data.AType=='NONE'||data.AType=='ROBOT'){
				a+=data.content;
				//a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";
			}else{
				a+=data.content;
				a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";
			}
			a+="</div></div>";
			$("#showbox").append(a);	
			var height = $("#showbox").prop('scrollHeight');//原来的高度	
			$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示										
		}
	},'json');
	$("#inputbox").val('');	
}
	//点击常见问题
$(function(){
	$("#showbox").html("<div class='clearfix'><div class='robot'><div class='icon1'></div><s:property value='hello'/></div></div>");
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
			if($("#judge").val()==""){
				if($("#inputbox").val().length>100){
					alert("字数超过限制！");
				}else{	
					input();
					event.preventDefault();
			        var pattern = "还可以输入<span class='red'>100</span>个字";
		            $('#contentwordage').html(pattern);
		            $('#ui-id-1').hide();
				}
			}
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
  				a+=data.result[0].summary;
  				a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";
    			a+="</div></div>";
    			$("#showbox").append(a);
    			var height = $("#showbox").prop('scrollHeight');//原来的高度	
				$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示					
			}
		},'json');
	});
	//控制页面整体高度
	var _wh = $(window).height();
	var _lh =$("#showbox").height();
	function footerHeight(){
		if(_wh - _lh > 317){			
			$("#showbox").css("height",(_wh-317)+"px");
			$(".main .normal-question").css("height",(_wh-191)+"px");		
		}
	}
	footerHeight();
	$(window).resize(function() {
		var _wh = $(this).height();
		if(_wh - _lh > 317){			
			$("#showbox").css("height",(_wh-317)+"px");
			$(".main .normal-question").css("height",(_wh-191)+"px");		
		}
	});
	//控制可以输入字数
	var limitNum = 100;
    var pattern = "还可以输入<span class='red'>"+ limitNum + "</span>个字";
    $('#contentwordage').html(pattern);   
    $('#inputbox').bind("input propertychange",function(){
	        var remain = $(this).val().length;
	        if(remain > 100){
	                pattern = "<span class='red'>字数超过限制！</span>";
	            }else{
	                var result = limitNum - remain;
	                pattern = "还可以输入<span class='red'>" + result + "</span>个字";
	            }
	        $('#contentwordage').html(pattern);
	        $("#judge").val("");
       });
    //控制选择标签变色
 	$("#kn_labels ul .cn").bind("click",function(){
		$(this).addClass("selected").siblings().removeClass("selected");		
	}); 
	//关闭机器人
})
</script>
<div class="logo">
	<div class="logo_img"></div>
	<div class="logo_title">学信网机器人 | chsi.com.cn</div>
	<div class="logo_sub">24小时竭诚为您服务</div>
	<div class="switch"></div>
</div>
<div class="main clearfix">
	<div class="left">
		<div id="showbox"></div>
		<div id="sendbox">
			<div class="send_top">
				<textarea id="inputbox" maxlength="100" autocomplete="off" placeholder="请用一句话简要描述您的问题，比如'如何找回用户名和密码？'"></textarea>
				<input id="judge" type="hidden" value="" />
			</div>
			<div class="send_bottom">
				<input id="sendBtn" type="button" value="发送">
				<span id="contentwordage"></span>
			</div>
		</div>
		<div class="all_tips"></div>
	</div>
	<div class="right">
		<div class="normal-question">
			<div id="kn_labels" >
				<ul class="clearfix">
					<li class="selected cn" onclick="ajaxJSONP('systemId=account','knList')" >学信网账号</li>
					<li class="cn" onclick="ajaxJSONP('systemId=my','knList')">学信档案</li>
					<li class="cn" style="margin-right: 0; border-right: none;" onclick="ajaxJSONP('systemId=yz_wb','knList')" >研招统考</li>
					<li class="cn" onclick="ajaxJSONP('systemId=yz_tm','knList')">研招推免</li>
                    <li class="cn" onclick="ajaxJSONP('systemId=zb','knList')">应征报名</li>
                    <li style="border-right: none;"></li>
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
				<div class="pagenation" id="pagenation_list">
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
		<div class="advert"></div>
	</div>
</div>
<script type="text/javascript">

//artTemplate辅助方法
template.helper('paramStr', function (o) {
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
    $("#kn_list").html(template('snippet_list',json)); //直接使用json数据
//  drawPage("#pagenation_list",json["o"]);
	$("#kn_list a").on("click",function(){
		var q=$(this).text();
		$("#showbox").append("<div class='clearfix'><div class='person'><div class='icon2'></div>"+q+"</div></div");
		var height = $("#showbox").prop('scrollHeight');//原来的高度	
		$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示								
		var knowId = $(this).data("id");
		$.post("/robot/qa.action",{sessionId:sessionId,knowId:knowId},function(result){
			if(result.flag=='true') {
    			var data = result.o;
    			//console.log(result);
    			var a="<div class='clearfix'><div class='robot'><div class='icon1'></div>";
  				a+=data.result[0].summary;
  				a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";				
    			a+="</div></div>";
    			$("#showbox").append(a);
    			var height = $("#showbox").prop('scrollHeight');//原来的高度	
				$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示					
			}
		},'json');
	});      
}    
// function knListPage(json){
//   if(!json.flag){ alert(json.errorMessages); return;}
//  $("#kn_list").html(template('snippet_list',json["o"]));   
//	$("#kn_list a").on("click",function(){
//		var q=$(this).text();
//		$("#showbox").append("<div class='clearfix'><div class='person'><div class='icon2'></div>"+q+"</div></div");
//		var height = $("#showbox").prop('scrollHeight');//原来的高度	
//		$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示								
//		var knowId = $(this).data("id");
//		$.post("/robot/qa.action",{sessionId:sessionId,knowId:knowId},function(result){
//			if(result.flag=='true') {
//  			var data = result.o;
//  			//console.log(result);
//  			var a="<div class='clearfix'><div class='robot'><div class='icon1'></div>";
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
$(function() {
		$('#inputbox').autocomplete({
			minLength: 0,
            max: 0,
            delay: 100,
            autoFill:false,
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
				 $("#judge").val(ui.item.value+"<span class='system'>["+ui.item.system+"]</span>");					
			},
			change: function(event, ui) {
				 $("#judge").val("");					
			},
            select: function(event, ui){
            	var knowId = ui.item.knowId;
            	var q=ui.item.value;           	
            	$("#showbox").append("<div class='clearfix'><div class='person'><div class='icon2'></div>"+q+"</div></div");
            	$.post("/robot/qa.action",{sessionId:sessionId,knowId:knowId},function(result){
					if(result.flag=='true') {
		    			var data = result.o;
		    			//console.log(result);
		    			var a="<div class='clearfix'><div class='robot'><div class='icon1'></div>";
		  				a+=data.result[0].summary;
  						a+="<span class='system_1' data-id='"+data.result[0].systemId+"'>["+data.result[0].system+"]</span>";
		    			a+="</div></div>";
		    			$("#showbox").append(a);
		    			var height = $("#showbox").prop('scrollHeight');//原来的高度	
						$("#showbox").scrollTop(height);//滚动到原来的高度，正好从最新用户输入开始显示					
					}
				},'json');         
		        var pattern = "还可以输入<span class='red'>100</span>个字";
	            $('#contentwordage').html(pattern);	 
	            $("#judge").val("");
	            $("#inputbox").val("");	
				return false;				
           },
           position: { my : "left bottom", at: "left bottom",of: ".all_tips" } 
		}).data("ui-autocomplete")._renderItem = function (ul, item) {   
        	var reg = new RegExp("("+item.keywords+")","g");
        	item.desc =  item.desc.replace(reg, "<strong style='color:#c30'>$1</strong>");
        	item.label =  item.label.replace(reg, "<strong style='color:#c30'>$1</strong>");          
            return $("<li>").append("<a>"+item.label+"<span class='system'>["+item.system+"]</span></a>").appendTo(ul);
		};
		$( window ).on('beforeunload',function() {
		  $.post("/robot/close.action?sessionId="+sessionId);
		});
		//关闭页面
		$(".switch").click(function(e) {			
//			CloseWebPage();
			window.location.href = 'about:blank ';
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
   ajaxJSONP('systemId=account','knList');   
});
</script>