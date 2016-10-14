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
            <style type="text/css">
                .dl-horizontal dd,
                .dl-horizontal dt {
                    line-height: 2em;
                }
            </style>
            <link href="/css/progressbar.css" rel="stylesheet" type="text/css">
            <!--breadcrumbs-->

            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {}

                </script>
                <ul class="breadcrumb">
                    <li> <i class="ace-icon fa fa-home home-icon"></i> <a href="/htgl/index.action">首页</a> </li>
                    <li class="active">知识详情</li>
                </ul>
                <!-- /.breadcrumb -->
            </div>

            <div class="page-content">
              
                <div class="row">
                   
                   <div class="col-sm-12 widget-container-col ui-sortable">
                                        <div class="widget-box transparent ui-sortable-handle">
                                            <div class="widget-header">
                                                <h2 class="widget-title"><s:property value="knowledgeData.article.title" escape="false" /></h2>

                                                <div class="widget-toolbar no-border" data-id="<s:property value=" knowledgeData.id " />">

                                                    <a href="#" title="收起/展示" data-action="collapse">
                                                        <i class="ace-icon fa fa-chevron-up"></i>
                                                    </a>
                                                    
                                                </div>
                                            </div>

                                            <div class="widget-body">
                                            
                                                <div class="widget-main padding-6 no-padding-left no-padding-right">
                                                 <div style="color:#999; padding:5px;">
                                  关键字： <s:property value=" knowledgeData.keywords " />  
                                </div>
                                <div style="padding:10px 0;">
                            <s:property value="knowledgeData.article.content" escape="false" />
                        </div>
                                                    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                    <div class="col-xs-12">


                        <div class="widget-box widget-color-pink" style="border:none">
                            <div class="widget-header">
                                <h4 class="widget-title lighter smaller">状态</h4>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main padding-8">
                                    <dl class="dl-horizontal">
                                        <dt>知识点编号：</dt>
                                        <dd>#<s:property value=" knowledgeData.id " />
                                            <input id="kId" type="hidden" value="<s:property value='knowledgeData.id' />"/>
                                        </dd>
                                        <dt>热点度：</dt>
                                        <dd><s:property value=" knowledgeData.sort " /></dd>
                                        <dt>当前状态：</dt>
                                        <dd> <span class="label label-sm label-success">已发布</span> </dd>
                                        <dt>知识类型：</dt>
                                        <dd> <span class="label label-sm label-success"><s:property value='knowledgeData.type' /></span> </dd>
                                    </dl>
                                </div>
                            </div>
                        </div>

                        <div class="widget-box widget-color-blue2" style="border:none">
                            <div class="widget-header">
                                <h4 class="widget-title lighter smaller">标签</h4>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main padding-8">
                            
                                    <dl class="dl-horizontal  bigger-120 blue">
                                        <s:iterator value="knowTagRelationList" id="knowTagRelation">
                                            <dt><span class="glyphicon glyphicon-tag " aria-hidden="true"></span></dt>
                                            <dd><s:property value="tagData.systemData.name" />&gt;<s:property value="tagData.name" /></dd>
                                        </s:iterator>
                                    </dl>
                                </div>
                            </div>
                        </div>

                        <div class="widget-box widget-color-green2" style="border:none">
                            <div class="widget-header">
                                <h4 class="widget-title lighter smaller">贡献者</h4>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main padding-8">
                                    <dl class="dl-horizontal">
                                        <dt>创建者：</dt>
                                        <dd><s:property value="knowledgeData.createrName " /></dd>
                                        <dt>创建时间：</dt>
                                        <dd><s:date format="yyyy-MM-dd HH:mm:ss" name="knowledgeData.createTime"/></dd>
                                        <dt>最后更新者：</dt>
                                        <dd><s:property value=" knowledgeData.updaterName " /></dd>
                                        <dt>最后更新时间：</dt>
                                        <dd><s:date format="yyyy-MM-dd HH:mm:ss" name="knowledgeData.updateTime"/> </dd>
                                    </dl>
                                </div>
                            </div>
                        </div>

			            <jsp:include page="/common/userDiscuss.jsp">
			                <jsp:param name="kId" value="${id}" />
			            </jsp:include>

                        <div class="clear"></div>
                    </div>
                </div>
<form id="modifyForm" action="/htgl/knowledge/modifyindex.action" method="post" target="">
    <input type="hidden" id="theId" name="id">
    </form>

            </div>