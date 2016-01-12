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
var InputText = '';
$(function(){
	$('#search_input').focus();
	$("#cancel").on("click", function(){
		window.history.back();
	})
	$('#search_input').on("keyup",function(){
		var text = $.trim($(this).val());
		if(text ==""){ 
			$('#hot_lists').show();
			$('#ask_list').html('');
			return false;
		}else if (text == InputText){
			return false;	
		}
		InputText = text;
		var _par = "keywords="+text;
		$('#hot_lists').hide();
		ajaxJSONP('allsearch',_par,'inputSearch'); 
	})
	
})
/********  搜索  begin *******/
function searchAll(){
	var $searchText = $("#search_input");
	var text = $.trim($searchText.val());
	var _par = "keywords="+text;
	if(text ==""){ return false;}
	$("#cache_v").val(_par);
	$('#hot_lists').hide();
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
            <input type="text" id='search_input'/>
            <span class="clearr">×</span>
            <button class='search_tbn' onClick="searchAll()">搜索</button>
            <input type="hidden" id="cache_v" name="cache_v" />  
          </div>
        </div>
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
  {{else}}
  <li style="color:#c30;"> 抱歉，未找到相关问题。</li>
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
  <li style="color:#c30;"> 抱歉，未找到相关问题。</li>
  {{/if}}
</ul>
</script>
      </div>
    </div>
  </body>
</html>
