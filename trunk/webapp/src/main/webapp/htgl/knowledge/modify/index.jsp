<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%>
<link href="<%=ctxPath%>/umeditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
<script type="text/javascript" charset="utf-8" src="<%=ctxPath%>/umeditor/umeditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=ctxPath%>/umeditor/umeditor.min.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/umeditor/lang/zh-cn/zh-cn.js"></script>
<script>
$(function(){
	var tagName = "";
	<s:iterator value="knowTagRelationList" id="knowTagRelation">
	tagName+='<s:property value="tagData.name"/>';
	</s:iterator>
    $("#modifyBtn").click(function(){
    	var html = editor.getContent();
	    $("#content").val(html);
	    $("#myform").submit();
    });
    $("#delBtn").click(function(){
	   if(confirm("确定删除该知识点？")) {
	       $("#mydelform").submit();
	   }
    });
    $.getJSON("/view/getKnowledgeList.action?systemId=<s:property value='systemId'/>&tagId=normal",
        function showTags(json){
            if(json.flag=="true"){
              var options = "";
              var viewTagVOs = json.o.viewTagVOs;
               for(var i=0;i<viewTagVOs.length;i++){
                var option = viewTagVOs[i];
                var selected = tagName.indexOf(option.name)>-1?"checked='checked'":"";
                options+="<input type='checkbox' name='tagName' value='"+option.name+"' "+selected+">"+option.description+"&nbsp;&nbsp;";
               }
               $("#tag").html(options);
            }
        }
    );
})
</script>
<form id="myform" action="<%=ctxPath%>/htgl/updateKnowledge.action" method="post" enctype="multipart/form-data">
<input type="hidden" name="id" value="<s:property value="id" />">
<input type="hidden" name="systemId" value="<s:property value="systemId" />">
<input type="hidden" id="content" name="content" value="">
<p>标题：<input id="title" type="text" name="title" style="width: 400px;" value="<s:property value="knowledgeData.article.title" />"></p>
<p>标签：<span id="tag"></span></p>
<p>关键字：<input id="keywords" type="text" name="keywords" style="width: 200px;" value="<s:property value="knowledgeData.keywords" />"></p>
<p>热点度：<input id="" type="text" name="sort" style="width: 200px;" value="<s:property value="knowledgeData.sort" />">（说明：数值越大，排序越靠前）</p>
</form>
<p>内容：</p>
<script type="text/plain" id="myEditor" style="width:1000px;height:240px;">
<s:property value="knowledgeData.article.content" escape="false" />
</script>
<div class="clear"></div>
<input id="modifyBtn" type="button" value="修改">
<form id="mydelform" action="<%=ctxPath%>/htgl/delKnowledge.action" method="post" enctype="multipart/form-data">
<input type="hidden" name="id" value="<s:property value="id" />">
<input id="delBtn" type="button" value="删除">
</form>
<script type="text/javascript">
   var editor = UM.getEditor('myEditor',{});
</script>