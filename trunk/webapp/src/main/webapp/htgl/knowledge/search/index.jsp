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
            <li> <i class="ace-icon fa fa-home home-icon"></i> <a href="/htgl/index.action">首页</a> </li>
            <li class="active">知识管理</li>
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
       <div class="col-xs-12 col-md-2">
        <select id="type" class="form-control" name="type">
        <option value="">请选择类型...</option>
        <option value="PRIVATE">内部</option>
        <option value="PUBLIC">公开</option>
        </select>
        </div>
       <div class="col-xs-12 col-md-2">
        <select id="systemIds" class="form-control" name="systemId">
        </select>
        </div>
        <div class="col-xs-12 col-md-2">
        <select id="tags">
        <option value="">请选择标签...</option>
        </select>
        </div>
        <div class="col-xs-12 col-md-6">
        <div class="input-group">
        <input id="keywords" type="text" class="form-control search-query" placeholder="知识点标题、回答、标签、关键字..." name="keywords" />
        <span class="input-group-btn">
<button type="button" id="searchBtn" class="btn btn-purple btn-sm">
																			<span class="ace-icon fa fa-search icon-on-right bigger-110"></span> 搜索
        </button>
        </span>
        </div>
        </div>
       </div>

        <div id="rows_content" class="rows" style="">
           
            <div class="col-xs-12">
                <div class="row">
                    <div class="col-xs-12">
                       
                        <h3 class="row header smaller lighter blue">
                        <span class="col-xs-7">知识点列表</span>
                        <span class="col-xs-5">
                            <a  href="/htgl/knowledge/addindex.action" target="_blank"  class="btn-new-mail pull-right">
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
                                            <th width="100" class="hidden-80" tabindex="0"  aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">系统</th>
                                            <th width="100" class="hidden-80" tabindex="1"  aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">标签</th>
                                            <th width="200" class="hidden-180" tabindex="1"  aria-controls="dynamic-table" rowspan="2" colspan="1" aria-label="Domain: activate to sort column ascending">标题</th>
                                             <th width=""  class="hidden-200" tabindex="3"  aria-controls="dynamic-table" rowspan="1" colspan="1">回答摘要</th> 
                                            <th width="70" class="hidden-480" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">热点度</th>
                                            <th width="80" class="hidden-480" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">点击次数</th>
                                            <th width="80" class="hidden-480" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">状态</th>
                                            <th width="100" class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
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
            $.getJSON("/htgl/search/searchAllKnow.action", {
                    systemId: systemId,
                    keywords: keywords,
                    tag:$("#tags").val(),
                    type:$("#type").val(),
                    curPage: curPage,
                    t: new Date().getTime()
                },
                function showSearchResult(json) {
                    if (json.flag == 'true') {
                        $("#search_result").html("");
                        $("#search_table_header").html("");
                        $("#dynamic-table_info").html("");
                        $("#dynamic-table_paginate").html("");
                        $("#table_footer_info").hide();
                        var knows = json.o.knows;
                        if(knows.length<1){
                           $("#search_result").html(" <tr role=\"row\" class=\"odd\"\><td  colspan=\"5\"> 无数据 </td><\/tr>");
                            $("#search_table_header").html("搜索 \“"+$("#keywords").val() +"\” 的结果").show();
                            return;
                        }
                        var pagination = json.o.pagination;
                        for (var i = 0; i < knows.length; i++) {
                            var k = knows[i];
                            var odd_even = (i%2==0)?"even":"odd";
                            var str = " <tr role=\"row\" data-id="+k.knowId+" class=\""+odd_even+"\"><td class=\"hidden-80\">" + k.system + "</td><td class=\"hidden-80\">" + k.tags + "</td><td class=\"hidden-160\">" + k.title + "</td><td class=\"hidden-260\">" + k.summary + "</td><td class=\"hidden-80\">"+k.sort+"</td><td class=\"hidden-80\">"+k.visitCnt+"</td><td class=\hidden-80\><span class=\"label label-sm label-success\">已发布</span><span class=\"label label-sm label-success\">"+k.type+"</span></td><td><div class=\"hidden-sm hidden-xs action-buttons\"><a class=\"blue\" target='_blank' title=\"查看\" href=\"/htgl/knowledge/showKnowledge.action?id=" + k.knowId +"\"> <i class=\"ace-icon fa fa-search-plus bigger-130\"></i> </a> <a class=\"green\" title=\"修改\" target='_blank' href='/htgl/knowledge/modifyindex.action?id=" + k.knowId +"&systemId=" + k.systemId + "'><i class=\"ace-icon fa fa-pencil bigger-130\"></i> </a> <a title=\"删除\" class=\"red delBtn\" href=\"#\"> <i class=\"ace-icon fa fa-trash-o bigger-130\"></i> </a></div>" + "</td></tr>";
                            $("#search_result").append(str);
                        }
                        $("#search_table_header").html("搜索 \“"+knows[0].keywords +"\” 的结果").show();
                        $("#dynamic-table_info").html("总计：共 "+ pagination.totalCount +" 条。");
                        $("#dynamic-table_paginate").html(formatP(pagination, systemId, keywords, curPage));
                        $("#table_footer_info").show();
                        //$("#rows_content").show();
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
            $.getJSON("/htgl/listSystem.action",
                function showSystems(json) {
                    if (json.flag == "true") {
                        var options = "<option value=''>请选择系统...</option>";
                        for (var i = 0; i < json.o.length; i++) {
                            var option = json.o[i];
                            options += "<option value='" + option.id + "'>" + option.name + "</option>";
                        }
                        $("#systemIds").html(options);
                        showSearchResult($("#systemIds").val(), "*:*", 0); //初始化首页数据
                    }
                }
            );
           
            $("#searchBtn").click(function () {
                if($.trim($("#keywords").val())==""){
                     showSearchResult($("#systemIds").val(), "*:*", 0);
                    }
                    else{
                     showSearchResult($("#systemIds").val(), $("#keywords").val(), 0);
                        }
                
            });
            $(document).on("click",".delBtn",function() {
            	if(confirm("确定删除该知识点？")) {
            		var $tr = $(this).closest("tr");
	            	var knowId = $tr.data("id");
	            	$.getJSON(
            			"<%=ctxPath%>/htgl/knowledge/delKnowledge.action",
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
            $(document).on("change","#systemIds",function() {
            	var data = {};
            	data.systemId = $("#systemIds").val();
            	$.getJSON("/htgl/listTag.action",data,
                    function showTags(json){
                        if(json.flag=="true"){
                          var options = "<option value=''>请选择标签...</option>";
                          var tags = json.o;
                           for(var i=0;i<tags.length;i++){
                            var option = tags[i];
                            options+="<option value='"+option.id+"'>"+option.name+"</option>";
                           }
                           $("#tags").html(options);
                        }
                    }
                );
            });
            
        })
    </script>