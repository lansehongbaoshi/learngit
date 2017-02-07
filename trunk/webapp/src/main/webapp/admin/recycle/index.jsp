<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String systemId = request.getParameter("systemId");
%>
<div class="breadcrumbs" id="breadcrumbs">
  <script type="text/javascript">
            try {
                ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) {}
        </script>
  <ul class="breadcrumb">
    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="/admin/index.action">首页</a></li>
    <li class="active">回收站</li>
  </ul>
  <!-- /.breadcrumb -->
</div>
<div class="page-content">
  <!-- <div class="page-header">
    <h1>
      回收站 <small> <i class="ace-icon fa fa-angle-double-right"></i> 指定系統查询
      </small>
    </h1>
  </div> -->
  <br>
  <div class="rows">
    <div class="col-xs-12 col-md-3">
      <select id="systemIds" class="form-control" name="systemId">
      </select>
    </div>
    <div class="col-xs-12 col-md-9">
      <button type="button" id="list" class="btn btn-purple btn-sm">
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span> 查询
      </button>
      <span>（请选择系统后，点击【查询】显示“已刪除列表”）</span>
    </div>
  </div>
  <div class="rows">
    <div class="col-xs-12" style="">
      <div class="row">
        <div class="col-xs-12">
          <h3 class="row header smaller lighter blue">
            <span class="col-xs-7">已刪除列表</span> <span class="col-xs-5"> </span>
          </h3>
          <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
              <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable" role="grid" aria-describedby="dynamic-table_info">
                <thead>
                  <tr role="row">
                    <th width="100" class="hidden-180" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">系统</th>
                    <th class="hidden-180" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">标题</th>
                    <th width="510" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">回答摘要</th>
                    <th width="100" class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">点击次数</th>
                    <th width="100" class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Status: activate to sort column ascending">状态</th>
                    <th width="180" class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                  </tr>
                </thead>
                <tbody id="search_result">
                </tbody>
              </table>
              <div class="row" id="table_footer_info" style="display: none">
                <div class="col-xs-6">
                  <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite"></div>
                </div>
                <div class="col-xs-6">
                  <div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
        $(function () {
            $.getJSON("/admin/listSystem.action",
                function showSystems(json) {
                    if (json.flag == "true") {
                        var options = "<option value=''>请选择系统...</option>";
                        for (var i = 0; i < json.o.length; i++) {
                            var option = json.o[i];
                            var selected = "";
                            <%if(systemId!=null){%>
                              if(option.id=='<%=systemId%>') {
                            	selected = " selected=\"selected\"";
                              }
                            <%}%>
                            options += "<option value='" + option.id + "'"+selected+">" + option.name + "</option>";
                        }
                        $("#systemIds").html(options);
                    }
                }
            );
            showDeledKnowl('');
            $("#list").click(function () {
                var systemId = $("#systemIds").val();
                showDeledKnowl(systemId);
            });
            function showDeledKnowl(systemId) {
            	$.getJSON("/admin/recycle/list.action", {systemId: systemId},
                  function show(json) {
                      if (json.flag == "true") {
                          var options = "";
                          var str = "";
                          var knows = json.o;
                          
                          if(knows.length<1){
                              $("#search_result").html(" <tr role=\"row\" class=\"odd\"\><td  colspan=\"6\"> 无数据 </td><\/tr>");
                               return;
                           } else {
                        	   $("#search_result").html("");
                              for (var i = 0; i < knows.length; i++) {
                                  var k = knows[i];
                                  var odd_even = (i%2==0)?"even":"odd";
                                  var str = " <tr role=\"row\" data-id="+k.id+" class=\""+odd_even+"\"><td>" + k.systemName + "</td><td>" + k.article.title + "</td><td class=\"hidden-260\"><a target='_blank' href='/admin/view/viewKnowledge.action?id=" + k.id + "'>" + k.summary + "</a></td><td class=\"hidden-80\">"+k.visitCnt+"</td><td class=\hidden-80\><span class=\"label label-sm label-success\">已删除</span></td><td><div class=\"hidden-sm hidden-xs action-buttons\"><a class=\"blue undo\" title=\"恢复\" href=\"javascript:void(0)\"> <i class=\"ace-icon fa fa-undo bigger-130\"></i></a><a class=\"red del\" title=\"彻底删除\" href=\"javascript:void(0)\"> <i class=\"ace-icon fa fa-trash bigger-130\"></i> </a></div>" + "</td></tr>";
                                  $("#search_result").append(str);
                              }
                      	   }
                      }
                  }
                );
            }
            $(document).on("click",".undo",function() {
            	if(confirm("确定恢复该知识？")) {
            		var $div = $(this).closest("tr");
	            	var id = $div.data("id");
	            	$.getJSON(
            			"/admin/recycle/rollback.action",
            			{klId:id},
            			function(data){
            				if(data.flag=='true') {
            					alert("恢复成功");
            					$div.closest("tr").remove();
            				} else {
            					var errMsg = data.errorMessages.pop();
            					alert(errMsg);
            				}
            			}
	            	)
            	}
            });
            $(document).on("click",".del",function() {
            	if(confirm("确定彻底删除该知识，删除后将不可恢复？")) {
            		var $div = $(this).closest("tr");
	            	var id = $div.data("id");
	            	$.getJSON(
            			"/admin/recycle/delKnowledgePermanently.action",
            			{klId:id},
            			function(data){
            				if(data.flag=='true') {
            					alert("彻底删除成功");
            					$div.closest("tr").remove();
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
