<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <li class="active">搜索</li>
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
            <h1> 查询 <small> <i class="ace-icon fa fa-angle-double-right"></i> 根据条件查询 </small> </h1>
        </div>

       <div class="rows">
       <div class="col-xs-12 col-md-3">
        <select id="systemIds" class="form-control" name="systemId">
        </select>
        </div>
        <div class="col-xs-12 col-md-6">
        <div class="input-group">
        <input id="keywords" type="text" class="form-control search-query" placeholder="知识点标题..." name="keywords" />
        <span class="input-group-btn">
<button type="button" id="searchBtn" class="btn btn-purple btn-sm">
																			<span class="ace-icon fa fa-search icon-on-right bigger-110"></span> 搜索
        </button>
        </span>
        </div>
        </div>
       </div>

        <div class="rows">
           
            <div class="col-xs-12">
                <div class="row">
                    <div class="col-xs-12">
                       
                        <h3 class="row header smaller lighter blue">
                        <span class="col-xs-7">知识点列表</span>
                        <span class="col-xs-5">
                            <a  href="/htgl/addindex.action" target="_blank"  class="btn-new-mail pull-right">
														<span class="btn btn-primary no-border">
															<i class="ace-icon fa glyphicon-plus bigger-130"></i>
															<span class="bigger-110">新增</span>
														</span>
													</a>
                        </span>
                        
                        </h3>
         
                  
                        <div class="table-header" id="search_table_header" style="display:none"> 搜索 "Latest Registered Domains" 的结果</div>

                        <!-- div.table-responsive -->

                        <!-- div.dataTables_borderWrap -->
                        <div>
                            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                      
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                        <tr role="row">
                                            <th  class="hidden-180" tabindex="0"  aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">标题</th>
                                             <th  width="410"  class="hidden-200" tabindex="0"  aria-controls="dynamic-table" rowspan="1" colspan="1">回答摘要</th> 
                                            <th width="100" class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">点击次数</th>
                                            <th width="100" class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">状态</th>
                                            <th  width="180" class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                                        </tr>
                                    </thead>
                                    <tbody id="search_result">
                                        <tr role="row" class="odd">
                                    
                                            <td  colspan="5"> 无数据 </td>
                                
                                        </tr>
                           
                                    </tbody>
                                </table>
                                <div class="row" id="table_footer_info" style="display:none">
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
            </div>
        </div>
    </div>
    <script type="text/javascript">
        function showSearchResult(systemId, keywords, curPage) {
            $.getJSON("/search/searchAllKnow.action", {
                    systemId: systemId,
                    keywords: keywords,
                    curPage: curPage
                },
                function showSearchResult(json) {
                    if (json.flag == 'true') {
                        $("#search_result").html("");
                        var knows = json.o.knows;
                        var pagination = json.o.pagination;
                        for (var i = 0; i < knows.length; i++) {
                            var k = knows[i];
                            var odd_even = (i%2==0)?"even":"odd";
                            var str = " <tr role=\"row\" data-id="+k.knowId+" class=\""+odd_even+"\"><td><a target='_blank' href='/view/viewKnowledge.action?id=" + k.knowId + "'>" + k.title + "</a></td><td class=\"hidden-260\">" + k.summary + "</td><td class=\"hidden-80\">"+k.visitCnt+"</td><td class=\hidden-80\><span class=\"label label-sm label-success\">已发布</span></td><td><div class=\"hidden-sm hidden-xs action-buttons\"><a class=\"blue\" target='_blank' href=\"/htgl/showKnowledge.action?id=" + k.knowId +"\"> <i class=\"ace-icon fa fa-search-plus bigger-130\"></i> </a> <a class=\"green\" title=\"修改\" target='_blank' href='/htgl/modifyindex.action?id=" + k.knowId +"&systemId=" + systemId + "'><i class=\"ace-icon fa fa-pencil bigger-130\"></i> </a> <a title=\"删除\" class=\"red delBtn\" href=\"#\"> <i class=\"ace-icon fa fa-trash-o bigger-130\"></i> </a></div>" + "</td></tr>";
                            $("#search_result").append(str);
                        }
                        $("#search_table_header").html("搜索 \“"+knows[0].keywords +"\” 的结果").show();
                        $("#dynamic-table_info").html("总计：共 "+ pagination.totalCount +" 条。");
                        $("#dynamic-table_paginate").html(formatP(pagination, systemId, keywords, curPage));
                        $("#table_footer_info").show();
                    }
                }
            )
        }

        function del(knowId) {

        }

        function formatP(pagination, systemId, keywords, curPage) {
            var curPage = pagination.curPage;
            var totalCount = pagination.totalCount;
            var pageCount = Math.ceil(totalCount / pagination.pageCount);
            var prePage = "";
            var nextPage = "";
            if (curPage - 1 > 0) {
                prePage = " <li class=\"paginate_button previous\" aria-controls=\"dynamic-table\" tabindex=\"0\" id=\"dynamic-table_previous\"><a href=\"javascript:void(0)\" onclick=\"showSearchResult('" + systemId + "','" + keywords + "','" + (curPage - 1) + "')\">上一页</a>";
            }
      
            if (curPage + 1 <= pageCount) {
                nextPage = " <li class=\"paginate_button next\" aria-controls=\"dynamic-table\" tabindex=\"0\" id=\"dynamic-table_next\"><a href=\"javascript:void(0)\" onclick=\"showSearchResult('" + systemId + "','" + keywords + "','" + (curPage + 1) + "')\">下一页</a></li>";
            }
            var htmlStr = "<ul class=\"pagination\">" + prePage + nextPage + "</ul>";
            return htmlStr;
        }
        $(function () {
            $.getJSON("/htgl/system/listSystem.action",
                function showSystems(json) {
                    if (json.flag == "true") {
                        var options = "";
                        for (var i = 0; i < json.o.length; i++) {
                            var option = json.o[i];
                            options += "<option value='" + option.id + "'>" + option.name + "</option>";
                        }
                        $("#systemIds").html(options);
                    }
                }
            );
            $("#searchBtn").click(function () {
                showSearchResult($("#systemIds").val(), $("#keywords").val(), 0);
            });
            $(document).on("click",".delBtn",function() {
            	if(confirm("删除后将不可恢复，确定删除该知识点？")) {
            		var $tr = $(this).closest("tr");
	            	var knowId = $tr.data("id");
	            	$.getJSON(
            			"<%=ctxPath%>/htgl/delKnowledge.action",
            			{id:knowId},
            			function(data){
            				if(data.flag=='true') {
            					alert("删除成功");
            					$tr.remove();
            				} else {
            					var errMsg = data.errorMessages.pop();
            					alert(errMsg);
            				}
            			}
	            	)
            	}
            });
        })
    </script>