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
                    $(function(){
                    	showSearchResult($("#kId").val(),0);
                    	$("a[data-action='update-time']").on("click",function(){
                    		if(confirm("确定要修改该知识的更新时间为当前时间？")) {
                    			document.location.href="/htgl/knowledge/updateKnowledgeTime.action?id=<s:property value=" knowledgeData.id " />";
                    		}
                    	});
                    	$(document).on("click","a[data-action='delete']",function() {
                        	if(confirm("确定要删除该知识点？")) {
            	            	var knowId = '<s:property value=" knowledgeData.id " />';
            	            	$.getJSON(
                        			"<%=ctxPath%>/htgl/knowledge/delKnowledge.action",
                        			{id:knowId},
                        			function(data){
                        				if(data.flag=='true') {
                        					alert("删除成功");
                        					document.location.href="/htgl/knowledge/searchindex.action";
                        				} else {
                        					var errMsg = data.errorMessages.pop();
                        					alert(errMsg);
                        				}
                        			}
            	            	)
                        	}
                        });
                	}); 
                
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {}
                    
                    
                    function showSearchResult(KId,curPage) {
                        $.getJSON("/htgl/knowledge/showDiscussContent.action", {
                               id:KId,
                                curPage: curPage
                            },
                            function showSearchResult(json) {
                                if (json.flag == 'true') {
                                    $("#search_result").html("");
                                    var knows = json.o.knows;
                                    var pagination = json.o.pagination;
                                    for (var i = 0; i < knows.length; i++) {
                                        var k = knows[i];
                                        var str = " <tr><td>"+k.userName+"</td><td>"+k.content+"</td><td>"+k.time+"</td></tr>";
                                        $("#search_result").append(str);
                                    }
                                    $("#dynamic-table_info").html("总计：共 "+ pagination.totalCount +" 条。");
                                    $("#dynamic-table_paginate").html(formatP(KId, pagination, curPage));
                                    $("#table_footer_info").show();
                                }
                            }
                        )
                    }

                    function formatP(KId, pagination, curPage) {
                        var curPage = pagination.curPage;
                        var totalCount = pagination.totalCount;
                        var pageCount = Math.ceil(totalCount / pagination.pageCount);
                        var prePage = "";
                        var nextPage = "";
                        if (curPage - 1 >= 0) {
                            prePage = " <li class=\"paginate_button previous\" aria-controls=\"dynamic-table\" tabindex=\"0\" id=\"dynamic-table_previous\"><a href=\"javascript:void(0)\" onclick=\"showSearchResult('"+KId+"','" + (curPage - 1) + "')\">上一页</a>";
                        }
                  
                        if (curPage + 1 < pageCount) {
                            nextPage = " <li class=\"paginate_button next\" aria-controls=\"dynamic-table\" tabindex=\"0\" id=\"dynamic-table_next\"><a href=\"javascript:void(0)\" onclick=\"showSearchResult('" +KId+"','" + (curPage + 1) + "')\">下一页</a></li>";
                        }
                        var htmlStr = "<ul class=\"pagination\">" + prePage + nextPage + "</ul>";
                        return htmlStr;
                    }

                </script>
                <ul class="breadcrumb">
                    <li> <i class="ace-icon fa fa-home home-icon"></i> <a href="/htgl/index.action">首页</a> </li>
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
              
                <div class="row">
                   
                   <div class="col-sm-12 widget-container-col ui-sortable">
                                        <div class="widget-box transparent ui-sortable-handle">
                                            <div class="widget-header">
                                                <h2 class="widget-title"><s:property value=" knowledgeData.article.title " /></h2>

                                                <div class="widget-toolbar no-border">
                                                    <a href="#" data-action="update-time">
                                                        <i class="ace-icon fa fa-clock-o"></i>
                                                    </a>

                                                    <a href="/htgl/knowledge/modifyindex.action?id=<s:property value=" knowledgeData.id " />&systemId=<s:property value=" knowledgeData.systemData.id " />" >
                                                        <i class="ace-icon fa fa-pencil"></i>
                                                    </a>
                                                    
                                                    <a href="#" data-action="delete">
                                                        <i class="ace-icon fa fa-trash"></i>
                                                    </a>

                                                    <a href="#" data-action="collapse">
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
                                            <dd> <s:property value="tagData.name" /></dd>
                                           
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

                      
                         <div class="widget-box widget-color-grey" style="border:none;">
                            <div class="widget-header">
                                <h4 class="widget-title lighter smaller">用户评价</h4>
                            </div>

                            <div class="widget-body">
                                <div class="widget-main padding-8">
                                
                                    共参与<s:property value="discussCountVO.sum"/>人<br/>
                                    认为有帮助：<div class="Bar"><div style="width:  <s:property value='discussCountVO.usefulPersent'/>%;"></div></div> <s:property value="discussCountVO.usefulPersent"/>% 
                                    （ <s:property value="discussCountVO.useful"/>人）<br/>
                                    认为无帮助：  <div class="Bar"><div style="width:  <s:property value='discussCountVO.unusefulPersent'/>%;"></div></div><s:property value="discussCountVO.unusefulPersent"/>% 
                                    （ <s:property value="discussCountVO.unuseful"/>人）<br/>
                                 
                                 
    <div id="discuss-content">评论
            <table>
                <tr>
                <th>姓名</th> 
                <th>内容</th> 
                <th>时间</th>
                </tr>
                
              <tbody id="search_result">
              <s:iterator value="contentList.knows" id="o">
                    <tr>
                        <td><s:property value="#o.userName"/></td>
                        <td><s:property value="#o.content"/></td>
                        <td><s:property value="#o.time"/></td>
                    </tr>
              
              </s:iterator>
            
              </tbody>
                
            </table>
       <div class="row" id="table_footer_info" >
           <div class="col-xs-6">
               <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite"></div>
           </div>
           <div class="col-xs-6">
               <div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
               </div>
           </div>
       </div>
            
        </div>
                                                            
                                </div>
                            </div>
                        </div>

                        <div class="clear"></div>
                    </div>
                </div>


            </div>