<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s"%>
        <%
String ctxPath = request.getContextPath();
%>
            <!--breadcrumbs-->

            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {}
                </script>
                <ul class="breadcrumb">
                    <li> <i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a> </li>
                    <li class="active">修改知识</li>
                </ul>
                <!-- /.breadcrumb -->

                <div class="nav-search" id="nav-search">
                    <form class="form-search">
                        <span class="input-icon">
      <input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off">
      <i class="ace-icon fa fa-search nav-search-icon"></i> </span>
                    </form>
                </div>
                <!-- /.nav-search -->
            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1> 修改知识  </h1>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <form id="myform" action="<%=ctxPath%>/htgl/updateKnowledge.action" method="post" enctype="multipart/form-data" class="form-horizontal">
                            <input type="hidden" name="id" value="<s:property value=" id " />">
                            <input type="hidden" name="systemId" value="<s:property value=" systemId " />">
                            <input type="hidden" id="content" name="content" value="">
                             <div class="form-group">
                                 <label for="" class="col-sm-3 control-label no-padding-top">标题：</label>
                                 <div class="col-sm-9">
                                <input id="title" type="text" name="title" style="width: 400px;" value="<s:property value=" knowledgeData.article.title " />">
                                 </div>
                            </div>
                           
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label no-padding-top">关键字：</label>
                                <div class="col-sm-9">
                                    <input id="keywords" type="text" name="keywords" style="width: 200px;" value="<s:property value=" knowledgeData.keywords " />"> <span>（说明：2~3个,英文逗号,隔开）</span>
                                    </div>
                            </div>
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label no-padding-top">热点度：</label>
                                <div class="col-sm-9">
                                <input id="sort" type="text" name="sort" style="width: 100px;" value="<s:property value=" knowledgeData.sort " />"> <span>（说明：1~99之间的数字,数值越大,序越靠前）</span>
                                </div>
                            </div>
                            <div class="form-group">
                             <label for="" class="col-sm-3 control-label no-padding-top">
                            标签：
                                </label>
                                <div class="col-sm-9" id="tag">
                                </div>
                
                          
                            </div>

</form>

             
                 <div class="form-horizontal ">
                       <div class="form-group">
                                <label for="" class="col-sm-3 control-label no-padding-top">
                            内容：
                                </label>
                                <div class="col-sm-9">
                                <script type="text/plain" id="container" style="width:1000px;height:240px;">
<s:property value="knowledgeData.article.content" escape="false" />
</script>
                                </div>
                            </div> 
                            </div>
<div class="clear"></div>
                      <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info" type="button" id="modifyBtn">
                                    <i class="ace-icon fa fa-check bigger-110"></i> 修改
                                </button>
                                 &nbsp; &nbsp; &nbsp;
                                <button class="btn" type="reset">
                                    <i class="ace-icon fa fa-undo bigger-110"></i> 取消
                                </button>
                                 
                            </div>
                        </div>
                     </div>
      </div>
    </div>



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
<!-- 配置文件 -->
<script type="text/javascript" src="/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="/ueditor/ueditor.all.min.js"></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
UE.Editor.prototype.getActionUrl = function(action) {
    if (action == 'uploadimage' || action == 'uploadfile') {
        return 'http://kl.chsi.com.cn/htgl/file/up.action';
    } else if (action == 'uploadvideo') {
        return 'http://a.b.com/video.php';
    } else {
        return this._bkGetActionUrl.call(this, action);
    }
}
    var editor = UE.getEditor('container');
</script>
