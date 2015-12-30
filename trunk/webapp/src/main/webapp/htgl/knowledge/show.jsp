<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s"%>
        <%
String ctxPath = request.getContextPath();
String from = request.getParameter("from");
String title = "";
if("update".equals(from)) {
    title="更新知识成功";
} else if("add".equals(from)) {
    title="新增知识成功";
} else {
    title="知识详情";
}
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
                    <li class="active">知识详情</li>
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
                    <h1><%=title %></h1>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                             <div class="form-group">
                                 <label for="" class="col-sm-3 control-label no-padding-top">标题：</label>
                                <s:property value=" knowledgeData.article.title " />
                            </div>
                           
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label no-padding-top">关键字：</label>
                                <s:property value=" knowledgeData.keywords " />
                            </div>
                            <div class="form-group">
                                <label for="" class="col-sm-3 control-label no-padding-top">热点度：</label>
                                <s:property value=" knowledgeData.sort " />
                            </div>
                            <div class="form-group">
                             <label for="" class="col-sm-3 control-label no-padding-top">
                            标签：
                                </label>
                                <s:iterator value="knowTagRelationList" id="knowTagRelation"><s:property value="tagData.name"/>,</s:iterator>
                                </div>
                              
                        <div class="form-group">
                                <label for="" class="col-sm-3 control-label no-padding-top">
                            内容：
                                </label>
                                <s:property value="knowledgeData.article.content" escape="false" />
                            </div> 
                        <div class="clear"></div>
                    </div>
                </div>


            </div>
