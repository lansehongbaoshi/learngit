<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.chsi.knowledge.dic.*"%>
<%@ page import="com.chsi.knowledge.service.*"%>
<%@ page import="com.chsi.knowledge.pojo.*"%>
<%@ page import="com.chsi.knowledge.util.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
SystemService systemService = ServiceFactory.getSystemService();
List<SystemData> systems = systemService.getSystems(false);
%>
<!--breadcrumbs-->
<div class="breadcrumbs" id="breadcrumbs">
  <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {}
                </script>
  <ul class="breadcrumb">
    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="/cti/index.action">首页</a></li>
    <li class="active">添加新知识</li>
  </ul>
  <!-- /.breadcrumb -->
</div>
<div class="page-content">
  <div class="page-header">
    <h1>添加新知识</h1>
  </div>
  <div class="row">
    <div class="col-xs-12">
      <form id="myform" action="<%=ctxPath%>/cti/knowledge/addKnowledge.action" method="post" enctype="multipart/form-data" class="form-horizontal">
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top">标题：</label>
          <div class="col-sm-9">
            <input id="title" type="text" name="title" style="width: 400px;" value="">
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top">关键字：</label>
          <div class="col-sm-9">
            <input id="keywords" type="text" name="keywords" style="width: 200px;" value=""> <span>（说明：2~3个,英文逗号,隔开）</span>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top">热点度：</label>
          <div class="col-sm-9">
            <input id="sort" type="text" name="sort" style="width: 100px;" value="20" readonly="readonly"> <span>（说明：1~99之间的数字,数值越大,排序越靠前）</span>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top"> 标签： </label>
          <div class="col-sm-9" id="tag"><span id="selectedtag" style="margin:0 2px;"></span><a href="javascript:void(0)" data-toggle="modal" data-target="#myModal">添加标签</a></div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top"> 类型： </label>
          <div class="col-sm-9" id="">
          <select name="type">
<%--           <option value="<%=KnowledgeType.PUBLIC.toString()%>">公开</option> --%>
          <option value="<%=KnowledgeType.PRIVATE.toString()%>">内部</option>
          </select>
          </div>
        </div>
        <input id="content" type="hidden" name="content">
      </form>
      <div class="form-horizontal">
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top"> 内容： </label>
          <div class="col-sm-9">
            <script type="text/plain" id="container" style="width: 990px; height: 440px;">
                        </script>
          </div>
        </div>
      </div>
      <div class="clear"></div>
      <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
          <button class="btn btn-info" type="button" id="modifyBtn">
            <i class="ace-icon fa fa-check bigger-110"></i> 新增
          </button>
          &nbsp; &nbsp; &nbsp;
          <button class="btn" type="reset" onclick="history.go(-1)">
            <i class="ace-icon fa fa-undo bigger-110"></i> 取消
          </button>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
               添加标签
            </h4>
         </div>
         <div class="modal-body">
         <%for(SystemData system:systems){ %>
         <p><%=system.getName() %>：</p>
            <p>
            <%List<TagData> tags = ManageCacheUtil.getTagList(system.getId());
            for(TagData tag:tags){
            %>
            <span style="margin:0 2px;"><input type="checkbox" name="tagIds" value="<%=tag.getId()%>" title="<%=tag.getName()%>"><%=tag.getName()%></span>
            <%} %>
            </p>
            <%} %>
         </div>
         <div class="modal-footer">

            <button id="savetag" type="button" class="btn btn-primary save" data-dismiss="modal">
               确定
            </button>
                        <button type="button" class="btn btn-default" 
               data-dismiss="modal">取消
            </button>
         </div>
      </div><!-- /.modal-content -->
</div>
</div>
<!-- 配置文件 -->
<script type="text/javascript" src="/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="/ueditor/ueditor.all.min.js"></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
UE.Editor.prototype.getActionUrl = function(action) {
    if (action == 'uploadimage' || action == 'uploadfile') {
        return 'http://kl.chsi.com.cn/cti/file/up.action';
    } else if (action == 'uploadvideo') {
        return 'http://a.b.com/video.php';
    } else {
        return this._bkGetActionUrl.call(this, action);
    }
}
    var editor = UE.getEditor('container');
</script>
<script>
                $(function () {
                    $("#modifyBtn").click(function () {
                        var html = editor.getContent();
                        $("#content").val(html);
                        $("#myform").submit();
                    });
$("#savetag").click(function () {
	var checked = $("#myModal .modal-body input:checked");
	var selectedtagSpan = $("#selectedtag");
	selectedtagSpan.html("");
    checked.each(function(){
    	selectedtagSpan.append($(this).clone());
    	selectedtagSpan.append($(this).attr("title"));
    });
});
                })
            </script>