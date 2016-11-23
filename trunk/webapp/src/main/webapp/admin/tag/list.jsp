<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%String systemId = request.getParameter("systemId");
%>
<div class="breadcrumbs" id="breadcrumbs">
  <script type="text/javascript">
            try {
                ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) {}
        </script>
  <ul class="breadcrumb">
    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="/admin/index.action">首页</a>
    </li>
    <li class="active">标签管理</li>
  </ul>
  <!-- /.breadcrumb -->

</div>


<div class="page-content">
  <div class="page-header">
    <h1>
      标签管理 <small> <i class="ace-icon fa fa-angle-double-right"></i>
        根据条件查询
      </small>
    </h1>
  </div>
  <div class="rows">

    <div class="col-xs-12 col-md-3">
      <select id="systemIds" class="form-control" name="systemId">
      </select>
    </div>
    <div class="col-xs-12 col-md-9">
      <button type="button" id="listTags" class="btn btn-purple btn-sm">
        <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
        查询
      </button>
      <span>（请选择系统后，点击【查询】显示“标签列表”）</span>
    </div>

  </div>
  <div class="rows">

    <div class="col-xs-12">
      <div class="row">
        <div class="col-xs-12">

          <h3 class="row header smaller lighter blue">
            <span class="col-xs-7">标签列表</span> <span class="col-xs-5">
              <a href="/admin/tag/addIndex.action" target="_self"
              class="btn-new-mail pull-right"> <span
                class="btn btn-primary no-border"> <i
                  class="ace-icon fa glyphicon-plus bigger-130"></i> <span
                  class="bigger-110">新增</span>
              </span>
            </a>
            </span>

          </h3>
          <div class="table-header" id="search_table_header"
            style="display: none">搜索 "Latest Registered Domains"
            的结果</div>

          <div>
            <div id="dynamic-table_wrapper"
              class="dataTables_wrapper form-inline no-footer">

              <table id="dynamic-table"
                class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable"
                role="grid" aria-describedby="dynamic-table_info">
                <thead>
                  <tr role="row">
                    <th width="210" class="hidden-180" tabindex="0"
                      aria-controls="dynamic-table" rowspan="1"
                      colspan="1"
                      aria-label="Domain: activate to sort column ascending">标签名称</th>
                    <th width="310" class="hidden-260" tabindex="0"
                      aria-controls="dynamic-table" rowspan="1"
                      colspan="1">标签描述</th>
                    <th width="100" class="hidden-100" tabindex="0"
                      aria-controls="dynamic-table" rowspan="1"
                      colspan="1">标签排序</th>
                    <th width="100" class="hidden-100" tabindex="0"
                      aria-controls="dynamic-table" rowspan="1"
                      colspan="1">知识数目</th>
                    <th class="sorting_disabled" rowspan="1" colspan="1"
                      aria-label=""></th>
                  </tr>
                </thead>
                <tbody id="tags_result">

                </tbody>
              </table>

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
                        var options = "";
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
            <%if(systemId!=null){%>
            	showTags('<%=systemId%>');
            <%}%>
            $("#listTags").click(function () {
                var systemId = $("#systemIds").val();
                showTags(systemId);
            });
            function showTags(systemId) {
            	$.getJSON("/admin/tag/list.action", {systemId: systemId,t:new Date().getTime()},
                  function showSystems(json) {
                      if (json.flag == "true") {
                          var options = "";
                          var str = "";
                          for (var i = 0; i < json.o.length; i++) {
                              var tag = json.o[i];
                              var odd_even = (i%2==0)?"even":"odd";
                           str = str + (" <tr role=\"row\" class=\""+odd_even+"\"><td>"+ tag.name +"</td><td class=\"hidden-260\">" + tag.description + "</td><td class=\"hidden-100\">" + tag.sort + "</td><td class=\"hidden-100\">" + tag.knowCnt + "</td><td><div class=\"hidden-sm hidden-xs action-buttons\" data-id=\"" + tag.id +"\"> <a class=\"green\" title=\"修改\" href='/admin/tag/updateIndex.action?id=" + tag.id +"'><i class=\"ace-icon fa fa-pencil bigger-130\"></i> </a> <a title=\"删除\" class=\"red delBtn\" href=\"javascript:void(0)\"> <i class=\"ace-icon fa fa-trash-o bigger-130\"></i> </a></div>" + "</td></tr>");
                           
                          }
                          $("#tags_result").html(str);
                      }
                  }
                );
            }
            $(document).on("click",".delBtn",function() {
            	if(confirm("删除后将不可恢复，确定删除该标签？")) {
            		var $div = $(this).closest("div");
	            	var tagId = $div.data("id");
	            	$.getJSON(
            			"/admin/tag/delete.action",
            			{id:tagId},
            			function(data){
            				if(data.flag=='true') {
            					alert("删除成功");
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