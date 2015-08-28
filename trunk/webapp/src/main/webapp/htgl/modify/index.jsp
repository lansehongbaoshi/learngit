<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%>
<script type="text/javascript" src="<%=ctxPath%>/ckeditor/ckeditor.js"></script>
<script>
$(function(){
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
    })
})
</script>
<form id="myform" action="<%=ctxPath%>/htgl/updateKnowledge.action" method="post" enctype="multipart/form-data">
<input type="hidden" name="id" value="<s:property value="id" />">
<input type="hidden" id="content" name="content" value="">
<p>标题：<input id="title" type="text" name="title" style="width: 200px;" value="<s:property value="knowledgeData.article.title" />"></p>
</form>
<p>内容：</p>
<p>
<textarea cols="80"  id="contentArea" name="contentArea" style="display:none;" rows="20" class="">
<s:property value="knowledgeData.article.content" />
</textarea>
</p>
<input id="modifyBtn" type="button" value="修改">
