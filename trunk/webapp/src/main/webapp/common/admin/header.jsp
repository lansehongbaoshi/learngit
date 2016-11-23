<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
    <div id="navbar" class="navbar navbar-default">
        <script type="text/javascript">
            try {
                ace.settings.check('navbar', 'fixed')
            } catch (e) {}
        </script>

        <div class="navbar-container" id="navbar-container">
            <button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
                <span class="sr-only">Toggle sidebar</span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>
            </button>

            <div class="navbar-header pull-left">
                <a href="/admin/index.action" class="navbar-brand">
                    <small>
							<i class="fa fa-leaf"></i>  <img src="/assets/images/logo_title.png" alt="学信网知识库管理系统"/>
						</small>
                </a>
            </div>

            <div class="navbar-buttons navbar-header pull-right" role="navigation">
                <ul class="nav ace-nav">
                   

                    <li class="light-blue">
                            <span style="color:white;">
									<small>欢迎，</small>
									<%=com.chsi.knowledge.util.RemoteCallUtil.getXm() %>
								</span>
                       <span>
                       <a href="<%=com.chsi.knowledge.util.RemoteCallUtil.getAccountUrl() %>/passport/logout"><small style="color:white;">退出</small></a>
                       </span>
                    </li>
                </ul>
            </div>
        </div>
        <!-- /.navbar-container -->
    </div>