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
                    <li> <i class="ace-icon fa fa-home home-icon"></i> <a href="/htgl/index.action">首页</a> </li>
                    <li class="active">添加新知识</li>
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
                    <h1> 添加新知识  </h1>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <form id="myform" action="<%=ctxPath%>/htgl/knowledge/addKnowledge.action" method="post" enctype="multipart/form-data" class="form-horizontal">
                            <div class="form-group">
                            <label for="" class="col-sm-3 control-label no-padding-top">系统：</label>
                            <div class="col-sm-9">
                            <select id="systemIds" name="systemId">
                            </select>
                            </div>
                            <input type="hidden" id="content" name="content" value="">
                            </div>
                             <div class="form-group">
                                 <label for="" class="col-sm-3 control-label no-padding-top">标题：</label>
                                 <div class="col-sm-9">
                                <input id="title" type="text" name="title" style="width: 400px;" value="">
                                 </div>
                            </div>
                           
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label no-padding-top">关键字：</label>
                                <div class="col-sm-9">
                                    <input id="keywords" type="text" name="keywords" style="width: 200px;" value=""> <span>（说明：2~3个,英文逗号,隔开）</span>
                                    </div>
                            </div>
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label no-padding-top">热点度：</label>
                                <div class="col-sm-9">
                                <input id="sort" type="text" name="sort" style="width: 100px;" value=""> <span>（说明：1~99之间的数字,数值越大,序越靠前）</span>
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
                        <div class="form-horizontal">
                        <div class="form-group">
                                <label for="" class="col-sm-3 control-label no-padding-top">
                            内容：
                                </label>
                                <div class="col-sm-9">
                                 <script type="text/plain" id="container" style="width:650px;height:240px;">
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

            <script>
                $(function () {
                    $("#systemIds").change(function () {
                        $.getJSON("/htgl/tag/list.action?systemId=" + $(this).val(),
                            function showTags(json) {
                                if (json.flag == "true") {
                                    var options = "";
                                    var tags = json.o;
                                    for (var i = 0; i < tags.length; i++) {
                                        var option = tags[i];
                                        options += "<input type='checkbox' name='tagName' id='tag_"+ i +"' value='" + option.name + "'><label for='tag_"+ i +"'>" + option.description + "</label>&nbsp;&nbsp;";
                                    }
                                    $("#tag").html(options);
                                }
                            }
                        );
                    });
                    $.getJSON("/htgl/listSystem.action",
                        function showSystems(json) {
                            if (json.flag == "true") {
                                var options = "<option value=''>请选择...</option>";
                                for (var i = 0; i < json.o.length; i++) {
                                    var option = json.o[i];
                                    var selected = '';
                                    options += "<option value='" + option.id + "'" + selected + ">" + option.name + "</option>";
                                }
                                $("#systemIds").html(options);
                            }
                            $("#systemIds").trigger('change');
                        }
                    );

                    $("#modifyBtn").click(function () {
                        var html = editor.getContent();
                        $("#content").val(html);
                        $("#myform").submit();
                    });

                })
            </script>