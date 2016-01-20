<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div id="sidebar" class="sidebar responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>
<!--

				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="ace-icon fa fa-signal"></i>
						</button>

						<button class="btn btn-info">
							<i class="ace-icon fa fa-pencil"></i>
						</button>

						<button class="btn btn-warning">
							<i class="ace-icon fa fa-users"></i>
						</button>

						<button class="btn btn-danger">
							<i class="ace-icon fa fa-cogs"></i>
						</button>
					</div>

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div>
-->
				
				<!-- /.sidebar-shortcuts -->

				<ul class="nav nav-list">
					<li class="">
						<a href="/htgl/search/searchindex.action">
							<i class="menu-icon fa fa-tachometer"></i>
							<span class="menu-text"> 首页 </span>
						</a>

						<b class="arrow"></b>
					</li>
					<li id="search_menu" class="">
						<a href="/htgl/search/searchindex.action">
							<i class="menu-icon fa fa-search"></i>

							<span class="menu-text">
								查询
							</span>
						</a>

						<b class="arrow"></b>
					</li>

					<li id="knowledge_menu" class="">
						<a href="/htgl/knowledge/searchindex.action">
							<i class="menu-icon fa fa-pencil-square-o"></i>
							<span class="menu-text"> 知识管理 </span>
						</a>

						<b class="arrow"></b>
					</li>
                     
					<li id="tag_menu" class="">
						<a href="/htgl/tag/index.action">
							<i class="menu-icon fa fa-tag"></i>
							<span class="menu-text"> 标签管理 </span>
						</a>

						<b class="arrow"></b>
					</li>
					<li id="system_menu" class="">
						<a href="/htgl/system/listSystems">
							<i class="menu-icon fa fa-desktop"></i>
							<span class="menu-text"> 系统管理 </span>
						</a>

						<b class="arrow"></b>
					</li>
					 

				
				</ul><!-- /.nav-list -->

				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
					var url = document.location.href;
					if(url.indexOf("/tag/")>-1) $("#tag_menu").addClass("active");
					else if(url.indexOf("/knowledge/")>-1) $("#knowledge_menu").addClass("active");
					else if(url.indexOf("/system/")>-1) $("#system_menu").addClass("active");
					else if(url.indexOf("/search/")>-1) $("#search_menu").addClass("active");
				</script>
			</div>