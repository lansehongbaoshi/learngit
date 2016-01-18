<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import="com.chsi.knowledge.pojo.KnowledgeData,com.chsi.knowledge.util.ManageCacheUtil,java.util.List,com.chsi.search.client.vo.KnowledgeVO"%>
<%List<KnowledgeVO> knows = ManageCacheUtil.getTopSearchKnow(); %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    
    <meta charset="utf-8">
    <title>搜索</title>
    <link href="../../css/wap/help/reset.css" rel="stylesheet"/>
    <link href="../../css/wap/help/style.css" rel="stylesheet"/>
    <!--[if lt IE 9]><script src="../../js/wap/respond.js"></script><![endif]-->
    <script type="text/javascript" src="../../js/jquery-1.11.1.min.js"></script>    
    <script type="text/javascript" src='../../js/wap/template.js'></script>
<script type="text/javascript">
var InputText = '';
$(function(){
	searchInputFn();
	clearFn();
})
function searchInputFn(){
	$('#search_input').focus();
	$('.search_form').on('opened', function () {
		alert(1);
		$('.search_form #search_input')[0].focus();
		 uexWindow.showSoftKeyboard();
	});
	var bind_name = 'input paste',userAgent = navigator.userAgent;
	if (userAgent.indexOf("MSIE") != -1) {  
		bind_name = 'propertychange';  
	}else if(userAgent.match(/android/i) == "android")  {  
		bind_name = "keyup";  
	}
	if(userAgent.indexOf('iPhone') > -1){
		var timer, windowInnerHeight;
		function eventCheck(e) {
			if (e) { //blur,focus事件触发的
				//$('.keyupTest').html('android键盘' + (e.type == 'focus' ? '弹出' : '隐藏') + '--通过' + e.type + '事件,keyCode'+e.keyCode||event.keyCode);
				if (e.type == 'click') {//如果是点击事件启动计时器监控是否点击了键盘上的隐藏键盘按钮，没有点击这个按钮的事件可用，keydown中也获取不到keyCode值
					setTimeout(function () {//由于键盘弹出是有动画效果的，要获取完全弹出的窗口高度，使用了计时器
						windowInnerHeight = window.innerHeight;//获取弹出android软键盘后的窗口高度
						timer = setInterval(function () { eventCheck() }, 100);
					}, 500);
				}else {
					clearInterval(timer);
					autoSearchFn();
				}
			}else { //计时器执行的，需要判断窗口可视高度，如果改变说明键盘隐藏了
				if (window.innerHeight > windowInnerHeight) {
					clearInterval(timer);
					//$('.keyupTest').html('android键盘隐藏--通过点击键盘隐藏按钮');
					autoSearchFn();
				}
			}
		}
		$('#search_input').click(eventCheck).blur(eventCheck);
	}else{
		$('#search_input').on(bind_name,function(event){
			event.stopPropagation();  
			autoSearchFn();
		})		
	}	
}
function autoSearchFn(){
	
	var text = $.trim($('#search_input').val());
	if(text ==""){ 
		$('#hot_lists').show();
		$('#ask_list').html('');
		return false;
	}else if (text == InputText){
		return false;	
	}
	$('#hot_lists').hide();
	ajaxJSONP('allsearch',text,'inputSearch');
}
//取消和清除事件
function clearFn(){
	$("#cancel").on("click", function(){
		window.history.back();
	})
	$('#search_clear').on('click',function(){
		$("#search_input").val('');	
		$('#hot_lists').show();
		$('#ask_list').html('');
	});
}
/********  搜索  begin *******/
function searchAll(){
	var $searchText = $("#search_input");
	var text = $.trim($searchText.val());
	if(text ==""){ return false;}
	$('#hot_lists').hide();
	ajaxJSONP('allsearch',text,'knSearch'); //自动搜索“报名”
}
// 配置项
var  kn_local_data = {
	url:"http://kl.chsi.com.cn",
	allsearch:"/search/all.action"
}
//通用ajax函数
function ajaxJSONP(url,text,callback){
	var _url = kn_local_data['url']+kn_local_data[url];//http://kl.chsi.com.cn/search/all.action?keywords=test
	var data = "keywords="+text; 
	InputText = text;
	$.ajax({ 
		global:true, 
		type: "post",
		cache: false,
		async: true,
		crossDomain:true,
		url: _url,  
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
// 搜索
function knSearch(json){
	if(!json.flag){ alert(json.errorMessages); return;}  
	$("#ask_list").html(template('ask_list_detail',json));
}    
function inputSearch(json){
	if(!json.flag){ alert(json.errorMessages); return;}
	$("#ask_list").html(template('input_list_detail',json));
}
//artTemplate辅助方法-高亮关键字
template.helper('hightWord', function (k,o) {
    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]",'g');
    var _k = k.replace(pattern,'')
     var reg = new RegExp("("+$.trim(_k)+")","g");
    return  o.replace(reg, "<strong style='color:#c30'>$1</strong>");
});
/********  搜索  end *******/

</script>

  </head>
  <body>
    <div id="wrap">
      <div class="section section_search">
        <div class="search_text">
          <div class="cancel"><a href="javascript:void(0)" id="cancel">取消</a></div>
          <div class="search_form">
            <input type="text" autofocus id='search_input' placeholder="请输入搜索内容"/>
            <span class="clearr" id='search_clear'>×</span>
            <button class='search_tbn' onClick="searchAll()">搜索</button>
          </div>
        </div>
        <div class='keyupTest'></div>
        <ul class="hot_search_questions" id='hot_lists'>
        <%if(knows!=null){ 
           for(KnowledgeVO vo:knows){%>
           <li><a href="/wap/help/ckjjfa.jsp?id=<%=vo.getKnowledgeId() %>"><%=vo.getTitle() %></a></li>
           <%} %>
        <%} %>
        </ul>
        <div id='ask_list'></div>
<!--自动完成内容-->
<script id="input_list_detail" type="text/html">
<ul class="hot_search_questions">
{{if o.knows.length>0 }}
 {{each o.knows as value i}} 
 <li>
 	<a class="ui-corner-all"  href="/wap/help/ckjjfa.jsp?id={{value.knowId}}">{{#hightWord(value.keywords,value.title)}}
	</a>
 </li> 
 {{/each}}	
{{/if}}
</ul>

</script>
<!--搜索列表内容-->
<script id="ask_list_detail" type="text/html">
<ul class="ask_answer">
{{if o.knows.length>0 }}
 {{each o.knows as value i}} 
 <li>
 	<a class="ui-corner-all"  href="/wap/help/ckjjfa.jsp?id={{value.knowId}}">
 		<h1><span><img src="../../images/wap/help/ask.png"/></span> {{#hightWord(value.keywords,value.title)}}</h1>
	</a>
 	<p><span><img src="../../images/wap/help/answer.png"/></span>{{#hightWord(value.keywords,value.summary)}}</p>
 </li> 
  {{/each}}	
  {{else}}
  <li class="last"> 没有搜索结果，您可以更换关键词重新搜索。</li>
  {{/if}}
</ul>
</script>
      </div>
    </div>
  </body>
</html>
