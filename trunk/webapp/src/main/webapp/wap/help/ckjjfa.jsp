<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.chsi.knowledge.pojo.*,com.chsi.knowledge.service.*,java.util.List" %>
<%@ page import="com.chsi.knowledge.Constants" %>
<%@ page import="com.chsi.knowledge.util.ManageCacheUtil" %>
<%@ page import="com.chsi.cms.client.*" %>
<%@ page import="com.chsi.news.vo.Article" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% 
String id = request.getParameter("id");
String keywords = request.getParameter("keywords");
KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
KnowledgeData knowledgeData = ManageCacheUtil.getKnowledgeDataById(id);

//如果没访问过，向访问知识队列中插入ID
String visitedFlag = Constants.VISIT + id;
if (null == session.getAttribute(visitedFlag)) {
    session.setAttribute(visitedFlag, id);
    ServiceFactory.getQueueService().addVisitKnowledgeId(id);
}
String discussedFlag = Constants.DISCUSS + id;
boolean isDiscussed = null != session.getAttribute(discussedFlag);

KnowTagRelationService knowTagRelationService = ServiceFactory.getKnowTagRelationService();
List<KnowTagRelationData> list = knowTagRelationService.getKnowTagRelationByKnowId(id);
TagData tagData = list.get(0).getTagData();
list = ManageCacheUtil.getKnowTag(tagData.getId());
int otherNum = 5;
%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    
    <meta charset="utf-8">
    <title>查看解决方案_学信网帮助中心</title>
    <link href="http://t1.chei.com.cn/common/wap/help/css/reset.css" rel="stylesheet"/>
    <link href="/css/wap/help/style.css" rel="stylesheet"/>
    <!--[if lt IE 9]><script src="'http://t3.chei.com.cn/common/wap/help/js/respond.js"></script><![endif]-->
  </head>
  <body>
    <div id="wrap">
      <div class="section">
        <s:include value="searchbox.jsp"></s:include>
        <div class="findUser">
                  <h2><%=knowledgeData.getArticle().getTitle() %></h2>
                  <div class='question_source'>问题来源：<a href='#'>学信网账号</a>&nbsp;&nbsp;&nbsp;更新于：2016.03.11</div>
                    <div class="cont">
                      <div class="article">
                          <%=knowledgeData.getArticle().getContent() %>
                      </div>
                  </div>
                  
                  <div class="bot">
                  	
                      <h6>
                      	<span class="before"></span>
                      	<label>此解答是否有用</label>
                        <span class="after"></span>
                      </h6>
                      <div class="huifu">
                      	<%if(!isDiscussed){ %>
                        <ul>
                            <li><button >有用</button></li>
                            <li class="none"><button>无用</button></li>
                        </ul>
                        <%}else{ %>
                             <div class='has_reply'>已收到您的建议。谢谢！</div>
                        <%}%>
                      </div>
                       <div class="huifu_end" style='display:none;'>
                       	<div class='kp'>有用</div>
                        <span>谢谢您的反馈！<span class='huifu_ccpj available' style='display:none;'>补充评价</span></span>
                      </div>
                     
                  </div>
                  
              </div>
              <div class="may_que">
                  <h3>您可能遇到的问题</h3>
                  <ul class="hot_list">
                  <%int realnum = 0;
                  CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
                  for(int i=0;i<list.size()&&realnum<otherNum;i++) {
                      KnowTagRelationData one = list.get(i);
                      KnowledgeData data = one.getKnowledgeData();
                      if(data.getId().equals(id)) continue;
                      Article article = cmsServiceClient.getArticle(data.getCmsId());
                      realnum++;
                  %>
                      <li<%if(i==list.size()-1 || realnum==otherNum) out.print(" class=\"last\"");%>>
	                      <a class='hl_a' href="/wap/help/ckjjfa.jsp?id=<%=data.getId() %>">
	                      	<%=article.getTitle() %>
		                    <img class='hl_next_img' src="http://t1.chei.com.cn/common/wap/help/images/more.png"/>
	                      </a>
                      </li>
                   <%} %>
                  </ul>
              </div>
      </div>
      	<div class="dialog" style="display:none">
                <div class="bot_bar">
                  <div class="title">
                    <div class="cancel available">取消</div>
                    <h1>您觉得无用的原因</h1>
                    <div class="submit">提交</div>
                  </div>
                  <ul class='sel_list'>
                    <li><label for='sel1'><input type="radio" name="sel" id='sel1' value='解决方案看不懂'/>解决方案看不懂</label></li>
                    <li><label for='sel2'><input type="radio" name="sel" id='sel2' value='操作后不成功'/>操作后不成功</label></li>
                    <li><label for='sel3'><input type="radio" name="sel" id='sel3' value='其他'/>其他</label></li>
                  </ul>
                  <textarea class='text_area' placeholder="谢谢您帮我们发现了新大陆，和小编说说呗!"></textarea>
                </div>
            </div>
    </div>
  </body>
<script type="text/javascript">
$(function(){
	$('.huifu').delegate('li','click',function(e){
		var $this = $(this),$huifuEnd=$('.huifu_end');
		var n = 1;
		$this.parents('.huifu').hide();	
		$huifuEnd.show();
		if($this.hasClass("none")){//无用
			$huifuEnd.find('.kp').text('无用');
			$('.dialog').show();
		}else{
			usefulFn();
		}
	});
	$('.huifu_end').delegate('.huifu_ccpj','click',function(e){
		$('.dialog').show();			
	});
	$('.dialog').delegate('.cancel','click',function(e){
		$(this).parents('.dialog').hide();
		$('.huifu_ccpj').show();
	}).find('input[name="sel"]').change(function(e) {
        var $this = $(this);
		var value = $('input[name="sel"]:checked').val()||'';
		if(value!=''){			
			var $dialog = $this.parents(".dialog");
			$dialog.find('.submit').addClass('available')
				   .click(function(){submitFn(value,$('.text_area').val());			
			});			
		}
    }).end().find('textarea').keyup(function(){
		$this = $(this);
		var val = $this.val()||'';
		if(val!=''){
			var $dialog = $this.css('color','#333').parents(".dialog");
			var $textArea = $dialog.find('input[name="sel"]').eq(2).prop('checked',true);
			var textarea = $('input[name="sel"]:checked').val()||'';
			$dialog.find('.submit').addClass('available')
				.click(function(){submitFn(val,textarea)});			
		}	
	});
	<%if(keywords!=null){%>
	$.post("/search/allSearch.action", {keywords:'<%=keywords %>'});
	<%}%>
});

var _url = "/view/discuss.action";
var data = {knowledgeId:'<%=id%>'};
function usefulFn(){
	data.discussStatus = 1;
	$.ajax({ 
		global:true, 
		type: "post",
		cache: false,
		async: true,
		crossDomain:true,
		url: _url,  
		data:data,
		//dataType: "jsonp",  
		//jsonp: "callback", //回调函数的参数  
		//jsonpCallback: callback, //回调函数的名称  
		error: function(){  
			//alert('请求时发生了错误，请稍后再试');  
		}  
	}); 
	//alert('有用');	
}
function submitFn(selectTxt,textarea){
	$('.dialog').hide();
	$('.huifu_ccpj').hide();
	data.discussStatus = 0;
	if(textarea=='谢谢您帮我们发现了新大陆，和小编说说呗!'){
		textarea='';
	}else{
		textarea="："+textarea;
	}
	if(selectTxt=='其他') data.content=selectTxt+textarea;
	else data.content=selectTxt;
	//alert('选中了：'+selectTxt+'，填写内容为：'+textarea+'');
	$.ajax({
		global:true,
		type: "post",
		cache: false,
		async: true,
		crossDomain:true,
		url: _url,
		data:data,
		//dataType: "jsonp",
		//jsonp: "callback", //回调函数的参数
		//jsonpCallback: callback, //回调函数的名称
		error: function(){
			alert('请求时发生了错误，请稍后再试');
		}
	});
};
</script>  
</html>
