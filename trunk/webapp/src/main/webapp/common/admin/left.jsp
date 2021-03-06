<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
com.chsi.knowledge.vo.LoginUserVO user = com.chsi.knowledge.web.util.WebAppUtil.getLoginUserVO(request);
%>
<div id="sidebar" class="sidebar responsive">
  <script type="text/javascript">
try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
</script>
  <ul id="menu" class="nav nav-list">
    <%if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_CTI_USER) && !user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE)) {%>
    <li id="homepage_menu" class=""><a href="/admin/cti/index.action"> <i class="menu-icon fa fa-tachometer"></i> <span class="menu-text"> 首页 </span>
    </a> <b class="arrow"></b></li>
    <%} else if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE)){ %>
    <li id="homepage_menu" class=""><a href="/admin/index.action"> <i class="menu-icon fa fa-tachometer"></i> <span class="menu-text"> 首页 </span>
    </a> <b class="arrow"></b></li>
    <%} %>
    <%if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE_READONLY)) {%>
    <li id="search_menu" class=""><a href="/admin/query/searchindex.action"> <i class="menu-icon fa fa-search"></i> <span class="menu-text"> 知识查询 </span>
    </a> <b class="arrow"></b></li>
    <li id="total_menu" class=""><a href="/admin/total/list"> <i class="menu-icon fa fa-area-chart"></i> <span class="menu-text"> 统计分析 </span>
    </a> <b class="arrow"></b></li>
    <%} %>
    <%if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_CTI_USER) && !user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE)) {%>
    <li id="search_menu" class=""><a href="/admin/cti/knowledge/searchindex.action"> <i class="menu-icon fa fa-pencil-square-o"></i> <span class="menu-text"> 知识管理 </span>
    </a> <b class="arrow"></b></li>
    <li id="add_menu" class=""><a href="/admin/cti/knowledge/searchadd.action"> <i class="menu-icon fa fa-file-text"></i> <span class="menu-text"> 待审核知识 </span>
    </a> <b class="arrow"></b></li>
    <%} %>
    <%if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE)) {%>
    <li id="knowledge_menu" class=""><a href="/admin/knowledge/searchindex.action"> <i class="menu-icon fa fa-pencil-square-o"></i> <span class="menu-text"> 知识管理 </span>
    </a> <b class="arrow"></b></li>
    <li id="knowledgechexk_menu" class=""><a href="/admin/knowledge/checkindex.action"> <i class="menu-icon fa fa-gavel"></i> <span class="menu-text"> 知识审核 </span>
    </a> <b class="arrow"></b></li>
    <li id="tag_menu" class=""><a href="/admin/tag/index.action"> <i class="menu-icon fa fa-tag"></i> <span class="menu-text"> 标签管理 </span>
    </a> <b class="arrow"></b></li>
    <%} %>
    <%if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE_ADMIN)) {%>
    <li id="system_menu" class=""><a href="/admin/system/listSystems"> <i class="menu-icon fa fa-desktop"></i> <span class="menu-text"> 系统管理 </span>
    </a> <b class="arrow"></b></li>
    <%} %>
    <%if(user.getAuths().contains(com.chsi.knowledge.Constants.ROLE_KNOWLEDGE)) {%>
    <li id="recycle_menu" class=""><a href="/admin/recycle/index"> <i class="menu-icon fa fa-trash"></i> <span class="menu-text"> 回收站 </span>
    </a> <b class="arrow"></b></li>
    <li id="robot_menu" class=""><a href="/admin/robot/set/list"> <i class="menu-icon fa fa-gear"></i> <span class="menu-text"> 机器人配置 </span>
    </a> <b class="arrow"></b></li>
    <li id="total_menu" class=""><a href="/admin/total/list"> <i class="menu-icon fa fa-area-chart"></i> <span class="menu-text"> 统计分析 </span>
    </a> <b class="arrow"></b></li>
    <li id="log_menu" class=""><a href="/admin/log/index"> <i class="menu-icon fa fa-history"></i> <span class="menu-text"> 日志查询 </span>
    </a> <b class="arrow"></b></li>
    <%} %>
  </ul>
  <!-- /.nav-list -->
  <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
    <i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
  </div>
  <script type="text/javascript">
	try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
 	var url = document.location.href;
// 	if(url.indexOf("/tag/")>-1) $("#tag_menu").addClass("active");
// 	else if(url.indexOf("/knowledge/")>-1) $("#knowledge_menu").addClass("active");
// 	else if(url.indexOf("/system/")>-1) $("#system_menu").addClass("active");
// 	else if(url.indexOf("/search/")>-1) $("#search_menu").addClass("active");
// 	else if(url.indexOf("/recycle/")>-1) $("#recycle_menu").addClass("active");
// 	else if(url.indexOf("/admin/index")>-1) $("#homepage_menu").addClass("active");
	
	$(function () {
		var flag = false;
		$("#menu li").children("a").each(function (index, domEle) {
//			//console.log(this.href);
			if(this.href == url){
				flag = true;
				$(this).parent().addClass("active");
			}
		});
		if(!flag){
			var nemuUrl = url;
            var end = nemuUrl.lastIndexOf("/");
            if(end>-1){
                nemuUrl = nemuUrl.substring(0,end);
            }

			$("#menu li").children("a").each(function (index, domEle) {
	            if(this.href.indexOf(nemuUrl)>-1){
	                flag = true;
	                $(this).parent().addClass("active");
	            }
	        });
		}
		
		
	})
					
					
  </script>
</div>
