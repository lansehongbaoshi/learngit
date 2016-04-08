<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.chsi.knowledge.pojo.KnowledgeData,com.chsi.knowledge.util.ManageCacheUtil,java.util.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<script src='http://t1.chei.com.cn/common/wap/help/js/template.js'></script>
<script src="http://t1.chei.com.cn/common/kn/js/kn_page.js"></script>
<style>
.content { padding: 25px 0 30px 0;}
.logo { position: relative; width:1000px; height: 90px; margin:0 auto; background: #afafaf; text-align:center; color: #fff; font-size: 22px;}
.logo .switch { position: absolute; right: 0; top: 0; background: #f30;}	
.main { width:998px; margin:0 auto; background: #f1f1f1; border:1px solid #b8b8b8;  border-top:none;}
.main .left { float: left; width: 630px; }
.main .left #showbox { height: 380px; padding-top: 25px; border-bottom: 1px solid #b8b8b8; overflow-y: auto;overflow-x: hidden; }
.main .left #sendbox { position: relative; height: 140px;   background: #fff; }
.main .left #sendbox #inputbox { width: 610px; height: 80px; padding: 10px; line-height: 25px; border: none;  }
.main .left #sendbox #sendBtn { position: absolute; right: 25px; bottom: 10px; display: inline-block; width: 100px; height: 30px; line-height: 28px; border: 1px solid #333; text-align: center; cursor: pointer; }
.main .left #sendbox #contentwordage { position: absolute; left: 10px; bottom: 10px;  color: #999;}
.main .left #sendbox #contentwordage .red { color: #f30;}
.main .right{ float: right; width: 367px; border-left: 1px solid #b8b8b8;}
.main .right .normal-question { height: 480px; border-bottom: 1px solid #b8b8b8;}
#kn_labels { padding: 20px 30px 0 30px; }
#kn_labels ul li { float: left; margin:0 18px 20px 0 ; width: 90px; height: 35px; line-height: 35px; text-align: center;  background: #e2e2e2; cursor: pointer;  }
#kn_labels ul li.selected { color: #fff; background: #999999;}
#kn_lists {padding: 0px 30px 20px 30px;}
#kn_lists .top_title { margin-bottom: 10px; color: #000; font-weight: 800;}
#kn_lists ul li { line-height: 26px; cursor: pointer; }
#kn_lists .pagenation { margin-top: 12px;}
#kn_lists .pagenation .kn-page_up_no { display:inline-block ; width: 50px; height: 25px; margin-right: 10px; line-height: 25px;;  text-align: center;  background: #e2e2e2; cursor: default; }
#kn_lists .pagenation .kn-pagination_down {  display:inline-block ; width: 50px; height: 25px; margin-right: 10px; line-height: 25px;;  text-align: center;   color: #fff; background: #999999; cursor: pointer; }
.main .right .advert{ height: 65px; }
.robot{ position:relative; float: left; max-width: 450px; margin-left: 70px; margin-bottom: 25px;  padding: 10px; border: 1px solid #b8b8b8; border-radius: 5px; background-color: #fff; word-break: break-all; word-wrap: break-word;}
.robot .icon1 { position: absolute; left:-66px; top: -6px; width: 62px; height: 62px; background: url(../images/wap/help/icon_yz.png) no-repeat ;}
.robot a { color:#0e6c9c;}
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
	//点击常见问题
$(function(){
	$("#showbox").html("<div class='clearfix'><div class='robot'><div class='icon1'></div><s:property value='hello'/></div></div>");
	$("#sendBtn").on("click",function(){
		if($("#inputbox").val().length>100){
			alert("字数超过限制！");
		}else{
			input();
		}
	});
	$("#inputbox").keydown(function(event) {
		if(event.keyCode==13) {
			if($("#inputbox").val().length>100){
				alert("字数超过限制！");
			}else{	
				input();
				event.preventDefault();
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
  				a+=data.result[0].summary+"<br>";
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
	var _rh =$(".main .right .advert").height();
	function footerHeight(){
		if(_wh - _lh > 312){			
			$("#showbox").css("height",(_wh-312)+"px");
			$(".main .right .advert").css("height",(_wh-627)+"px");		
		}
	}
	footerHeight();
	$(window).resize(function() {
		var _wh = $(this).height();
		if(_wh - _lh > 312){			
			$("#showbox").css("height",(_wh-312)+"px");
			$(".main .right .advert").css("height",(_wh-627)+"px");		
		}else{
			$("#showbox").css("height",_lh+"px");
			$(".main .right .advert").css("height",_rh+"px");
		}
	});
	//控制可以输入字数
	var limitNum = 100;
    var pattern = "还可以输入<span class='red'>"+ limitNum + "</span>字符";
    $('#contentwordage').html(pattern);
    $('#inputbox').keyup(function(){
	        var remain = $(this).val().length;
	        if(remain > 100){
	                pattern = "<span class='red'>字数超过限制！</span>";
	            }else{
	                var result = limitNum - remain;
	                pattern = "还可以输入<span class='red'>" + result + "</span>字符";
	            }
	            $('#contentwordage').html(pattern);
        });
    //控制选择标签变色
 	$("#kn_labels ul li").bind("click",function(){
		$(this).addClass("selected").siblings().removeClass("selected");		
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
			<textarea id="inputbox" maxlength="100" placeholder="请用一句话简要描述您的问题，比如'如何找回用户名和密码？'"></textarea>
			<input id="sendBtn" type="button" value="发送">
			<span id="contentwordage"></span>
		</div>
	</div>
	<div class="right">
		<div class="normal-question">
			<div id="kn_labels" >
				<ul class="clearfix">
					<li onclick="ajaxJSONP('systemId=account&tagId=uprkf85mr1n6jo57','knList')" class="selected">学信网账号</li>
					<li onclick="ajaxJSONP('systemId=my&tagId=o170014zvshroheu','knList')">学信档案</li>
					<li onclick="ajaxJSONP('systemId=yz_wb&tagId=fn51191g0mnglpqc','knList')" style="margin-right: 0;">研招统考</li>
					<li onclick="ajaxJSONP('systemId=yz_tm&tagId=xpc1h2dwm08bvdq1','knList')">研招推免</li>
                    <li onclick="ajaxJSONP('systemId=zb&tagId=lmaxksjbjh5ft3','knList')">应征报名</li>
				</ul>
			</div>
			<div id="kn_lists">
				<div class="top_title">常见问题</div>
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
				 {{each knowListVO.knows as value i}}  
				       <li><span> {{(knowListVO.pagination.curPage-1)*knowListVO.pagination.pageCount+i+1}}. <a  href="javascript:;">{{value.title}}</a></span></li>
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
            url: "/view/getKnowledgeList.action",  
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
     $("#kn_list").html(template('snippet_list',json["o"]));
     drawPage("#pagenation_list",json["o"]);
	$("#kn_list a").on("click",function(){
		var q=$(this).text();
		$("#showbox").append("<div class='clearfix'><div class='person'><div class='icon2'></div>"+q+"</div></div");
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
	});      
}    
 function knListPage(json){
     if(!json.flag){ alert(json.errorMessages); return;}
     $("#kn_list").html(template('snippet_list',json["o"]));
		$("#kn_list a").on("click",function(){
		var q=$(this).text();
		$("#showbox").append("<div class='clearfix'><div class='person'><div class='icon2'></div>"+q+"</div></div");
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
	});     
     //drawPage("#pagenation_list",json["o"])
}  
 //分页初始化
function drawPage(ele,obj){
     var _last_tag = obj["navigations"].pop();
  	var _page_obj = obj["knowListVO"].pagination;
  	if(!!!_page_obj){ return false;}
   	$(ele).pagefoot({ css:"mj_pagefoot"      //分页脚css样式类
		,pageSize:_page_obj["pageCount"]  //每页显示的记录数
    	,displayNum:0  //显示的固定页数
    	,itemsNum:_page_obj["totalCount"]                //总记录数
    	,currentNum:_page_obj["curPage"]              //当前页码
    	,currentClass:"page_current"     //当前页码时样式
    	,linkClass:"page_normal"             //页码链接样式
    	,previous:"上一页"      			//上一页显示文本
    	,next:"下一页"         					 //下一页显示文本
    	,abledClass:"kn-pagination_down"		//上下页-可用时样式
		,disabledClass:"kn-page_up_no"		//上下页-不可用时样式
    	,paging:function(page){ //分页事件触发时callback函数
            _last_tag["param"]["curPage"] = page;
             ajaxJSONP($.param(_last_tag["param"]),'knListPage')		
        }           
	}); 
}     
 

    
//初始化
    $(function(){ 
       ajaxJSONP('systemId=zb&tagId=7ew6t0bn978knoqp','knList');   
    });
         
    

</script>