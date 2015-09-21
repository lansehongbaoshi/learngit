<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%>
<script type="text/javascript" src="<%=ctxPath%>/ckeditor/ckeditor.js"></script>
<script>
$(function(){
    $.getJSON("/htgl/system/listSystem.action",
        function showSystems(json){
            if(json.flag=="true"){
              var options = "";
               for(var i=0;i<json.o.length;i++){
                var option = json.o[i];
                options+="<option value='"+option.id+"'>"+option.name+"</option>";
               }
               $("#systemIds").html(options);
            }
        }
    );
    $("#systemIds").change(function(){
    	$.getJSON("/view/getKnowledgeList.action?systemId="+$(this).val()+"&tagId=normal",
  	        function showTags(json){
  	            if(json.flag=="true"){
  	              var options = "";
  	              var viewTagVOs = json.o.viewTagVOs;
  	               for(var i=0;i<viewTagVOs.length;i++){
  	                var option = viewTagVOs[i];
  	                options+="<option value='"+option.name+"'>"+option.description+"</option>";
  	               }
  	               $("#tagName").html(options);
  	            }
  	        }
  	    );
    });
    
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
    })
})
</script>
<form id="myform" action="<%=ctxPath%>/htgl/addKnowledge.action" method="post" enctype="multipart/form-data">
系统：<select id="systemIds" name="systemId">
</select>
<input type="hidden" id="content" name="content" value="">
<p>标题：<input id="title" type="text" name="title" style="width: 400px;" value=""></p>
<p>标签：<select id="tagName" name="tagName"></select></p>
<p>关键字：<input id="keywords" type="text" name="keywords" style="width: 200px;" value=""></p>
<p>热点度：<input id="sort" type="text" name="sort" style="width: 100px;" value=""></p>
</form>
<p>内容：</p>
<p>
<textarea cols="80"  id="contentArea" name="contentArea" style="display:none;" rows="20" class="">

</textarea>
</p>
<input id="modifyBtn" type="button" value="新增">
