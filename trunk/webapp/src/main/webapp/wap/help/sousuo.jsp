<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
import="com.chsi.knowledge.pojo.KnowledgeData,com.chsi.knowledge.util.ManageCacheUtil,java.util.List,com.chsi.search.client.vo.KnowledgeVO"%>
<%List<KnowledgeData> knows = ManageCacheUtil.getTopSearchKnow(); %>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta charset="utf-8">
    <title>搜索_学信网帮助中心</title>
    <meta name="Keywords" content="学信网帮助中心，学信网常见问题">
    <meta name="Description" content="学信网帮助中心，学信网账号、学信档案、应征报名、四六级查分、研招统考、研招推免、学历与成绩认证、面向港澳台招生、高考统考招生、高考特殊类型招生、高考特殊类型报名">
    <link href="http://t1.chei.com.cn/common/wap/help/css/reset.css" rel="stylesheet"/>
    <link href="http://t2.chei.com.cn/common/wap/help/css/style.css?20160615" rel="stylesheet"/>
    <!--[if lt IE 9]><script src="'http://t3.chei.com.cn/common/wap/help/js/respond.js"></script><![endif]-->
    <script type="text/javascript" src="http://t4.chei.com.cn/common/jquery/2.1.1/jquery.min.js"></script>
  
    <script type="text/javascript" src='http://t1.chei.com.cn/common/wap/help/js/template.js'></script>
<script type="text/javascript">
var InputText = '';
$(function(){
	searchInputFn();
	clearFn();
})
function inputSearch(json){
	if(!json.flag){ alert(json.errorMessages); return;}
	$("#ask_list").html(template('input_list_detail',json));
}
function searchInputFn(){
    $('#search_input').on('input paste',function(event){
        event.stopPropagation();          
      	setTimeout(autoSearchFn,500); 
    }).css('visibility','visible').focus();
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
	ajaxJSONP('quickall',text,inputSearch,true);
}
//取消和清除事件
function clearFn(){
	$("#cancel").on("click", function(){
		window.history.back();
	})
	$('#search_clear').on('click',function(){
		$("#search_input").val('').focus();	
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
	ajaxJSONP('allsearch',text,knSearch,false); //自动搜索“报名”
}
// 配置项
var  kn_local_data = {
	url:"http://kl.chsi.com.cn",
	quickall:"/search/quickAll.action",
	allsearch:"/search/allSearch.action"
}
//通用ajax函数
function ajaxJSONP(url,text,callback,flag){
	var _url = kn_local_data['url']+kn_local_data[url];
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
		//jsonpCallback: callback, //回调函数的名称 
		success: callback,
		error: function(XMLHttpRequest, textStatus, errorThrown){
			console.log(textStatus+":"+data);
		}
	});
	return false;
}
// 搜索
function knSearch(json){
	if(!json.flag){ alert(json.errorMessages); return;}  
	$("#ask_list").html(template('ask_list_detail',json));
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
    <img src='http://kl.chsi.com.cn/images/wap/wx_share.jpg' width='0' height='0' />
    <div id="wrap">
      <div class="section section_search">
        <div class="search_text">
          <div class="cancel"><a href="javascript:void(0)" id="cancel">取消</a></div>
          <div class="search_form">
            <input type="text" autofocus id='search_input' style="visibility:hidden;" placeholder="请输入搜索内容"/>
            <span class="clearr" id='search_clear'>×</span>
            <button class='search_tbn' onClick="searchAll()">搜索</button>
          </div>
        </div>
        <div class='keyupTest'></div>
        <ul class="hot_search_list" id='hot_lists'>
        <%if(knows!=null){ 
           for(KnowledgeData vo:knows){%>
           <li><a href="/wap/help/ckjjfa.jsp?id=<%=vo.getId() %>">[<span title="<%out.print(vo.getSystemNames());%>"><% out.print(vo.getSystemName());%></span>]<%=vo.getArticle().getTitle() %></a></li>
           <%} %>
        <%} %>
        </ul>
        <div id='ask_list'></div>
<!--自动完成内容-->
<script id="input_list_detail" type="text/html">
<ul class="hot_search_list">
{{if o.knows.length>0 }}
 {{each o.knows as value i}} 
 <li>
 	<a class="ui-corner-all"  href="/wap/help/ckjjfa.jsp?id={{value.knowId}}&keywords={{value.keywords}}">[<span title="{{value.systems}}">{{value.system}}</span>]{{#hightWord(value.keywords,value.title)}}
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
    <h1><span><img src="http://t2.chei.com.cn/common/wap/help/images/ask.png"/></span><a  href="/wap/help/ckjjfa.jsp?id={{value.knowId}}">[<span title="{{value.systems}}">{{value.system}}</span>]{{#hightWord(value.keywords,value.title)}}</a></h1>	
 	<p onClick="window.location.href='/wap/help/ckjjfa.jsp?id={{value.knowId}}'"><span><img src="http://t3.chei.com.cn/common/wap/help/images/answer.png"/></span>{{#hightWord(value.keywords,value.summary)}}</p>
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
