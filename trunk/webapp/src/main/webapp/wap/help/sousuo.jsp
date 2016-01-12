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
    <script type="text/javascript" src="http://t4.chsi.com.cn/common/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src='../../js/wap/template.js'></script>
<script type="text/javascript">
$(function(){
	$("#cancel").on("click", function(){
		window.history.back();
	})
})
//搜索
function searchAll(){
	var $searchText = $("#search_text");
	var text = $.trim($searchText.val());
	var _par = "keywords="+text;
	if(text ==""){ alert("请输入您要咨询的问题"); return false;}
	$("#cache_v").val(_par);
	$('.hot_search_questions').hide();
	ajaxJSONP('allsearch',_par,'knSearch'); //自动搜索“报名”
}
// 配置项
var  kn_local_data = {
	url:"http://kl.chsi.com.cn",
	list:"/view/getKnowledgeList.action",
	detail:"/view/getKnowledge.action",
	discuss:"/view/discuss.action",
	allsearch:"/search/all.action"
}
//通用ajax函数
function ajaxJSONP(url,data,callback){
	var _url = kn_local_data['url']+kn_local_data[url];//http://kl.chsi.com.cn/search/all.action?keywords=test
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
	//$(".ask_answer").show();  
	$("#ask_list").html(template('ask_list_template',json));
}    
//artTemplate辅助方法-高亮关键字
template.helper('hightWord', function (k,o) {
    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]",'g');
    var _k = k.replace(pattern,'')
     var reg = new RegExp("("+$.trim(_k)+")","g");
    return  o.replace(reg, "<strong style='color:#c30'>$1</strong>");
});
</script>

  </head>
  <body>
    <div id="wrap">
      <div class="section section_search">
        <div class="search_text">
          <div class="cancel"><a href="javascript:void(0)" id="cancel">取消</a></div>
          <div class="search_form">
            <input type="text" class='search_text' id='search_text'/>
            <span class="clearr">×</span>
            <button class='search_tbn' onClick="searchAll()">搜索</button>
            <input type="hidden" id="cache_v" name="cache_v" />  
          </div>
        </div>
        <ul class="hot_search_questions">
        <%if(knows!=null){ 
           for(KnowledgeVO vo:knows){%>
           <li><a href="/wap/help/ckjjfa.jsp?id=<%=vo.getKnowledgeId() %>"><%=vo.getTitle() %></a></li>
           <%} %>
        <%} %>
        </ul>
        <div id='ask_list'></div>
        <!--搜索列表内容-->
<script id="kn_res_list_template" type="text/html">
 <div class="ui-box-container">
 
<div class="faq-list">
<div id="res_loading" class="kn-loading"><img alt="正在加载中..." src="http://t1.chei.com.cn/common/zbbm/images/htgl/loading.gif"></div>
<div id="res_list">{{include 'ask_list_detail'}}</div>
</div>
<div class="pagenation clearfix" id="res_pagenation_list"></div>
</div> 
</script>
<!--搜索列表内容-->
<script id="ask_list_template" type="text/html">
{{include 'ask_list_detail'}} 
</script>
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
  <li style="color:#c30;"> 抱歉，未找到相关问题。</li>
  {{/if}}
</ul>
</script>
      </div>
    </div>
  </body>
</html>
