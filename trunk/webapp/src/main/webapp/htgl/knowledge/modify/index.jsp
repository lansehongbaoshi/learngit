<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%>
<script type="text/javascript" src="<%=ctxPath%>/ckeditor/ckeditor.js"></script>
<script>
$(function(){
	var tagName = "<s:property value='tagName'/>";
  //编辑器
    CKEDITOR.replace( 'contentArea', {
        //extraPlugins: 'autogrow',
        //autoGrow_maxHeight: 260,
        height: '300px',
        toolbar:  [
        { name: 'basicstyles', groups: [ 'basicstyles' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike'] },
        { name: 'cleanup', groups: [ 'cleanup' ], items: [ 'RemoveFormat' ]},
        { name: 'bidi', groups: [ 'bidi' ], items: [ 'BidiLtr', 'BidiRtl' ]},
        { name: 'list', groups: [ 'list', 'indent', 'blocks'], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote']},
        { name: 'align', groups: [ 'align' ], items: [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock' ]},
        { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
        '/',
        { name: 'insert', items: [ 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar'] },
        { name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
        { name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
        { name: 'tools', items: [ 'Maximize' ] }
        ],
        // Remove the Resize plugin as it does not make sense to use it in conjunction with the AutoGrow plugin.
        removePlugins: 'elementspath'
        //resize_enabled: false
    });
    $("#modifyBtn").click(function(){
	    var oEditor = CKEDITOR.instances.contentArea;
	    $("#content").val(oEditor.getData());
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
                var selected = tagName.indexOf(option.name)>-1?"selected='true'":"";
                options+="<option value='"+option.name+"' "+selected+">"+option.description+"</option>";
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
<p>标签：<select id="tag" name="tagName"></select></p>
<p>关键字：<input id="keywords" type="text" name="keywords" style="width: 200px;" value="<s:property value="knowledgeData.keywords" />"></p>
<p>热点度：<input id="" type="text" name="sort" style="width: 200px;" value="<s:property value="knowledgeData.sort" />">（说明：数值越大，排序越靠前）</p>
</form>
<p>内容：</p>
<p>
<textarea cols="80"  id="contentArea" name="contentArea" style="display:none;" rows="20" class="">
<s:property value="knowledgeData.article.content" />
</textarea>
</p>
<input id="modifyBtn" type="button" value="修改">
<form id="mydelform" action="<%=ctxPath%>/htgl/delKnowledge.action" method="post" enctype="multipart/form-data">
<input type="hidden" name="id" value="<s:property value="id" />">
<input id="delBtn" type="button" value="删除">
</form>