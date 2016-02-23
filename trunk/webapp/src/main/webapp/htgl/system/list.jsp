<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="breadcrumbs" id="breadcrumbs">
  <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {}
            </script>
  <ul class="breadcrumb">
    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="/htgl/index.action">首页</a></li>
    <li class="active">系统管理</li>
  </ul>
  <!-- /.breadcrumb -->
  <div class="nav-search" id="nav-search">
    <form class="form-search">
      <span class="input-icon"> <input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off"> <i class="ace-icon fa fa-search nav-search-icon"></i>
      </span>
    </form>
  </div>
  <!-- /.nav-search -->
</div>
<div class="page-content">
  <div class="rows">
    <div class="col-xs-12">
      <div class="row">
        <div class="col-xs-12">
          <h3 class="row header smaller lighter blue">
            <span class="col-xs-7">所有系统</span> <span class="col-xs-5"> <a href="/htgl/system/addSystemIndex.action" target="_self" class="btn-new-mail pull-right"> <span class="btn btn-primary no-border"> <i class="ace-icon fa glyphicon-plus bigger-130"></i> <span class="bigger-110">新增</span>
              </span>
            </a>
            </span>
          </h3>
          <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
              <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable" role="grid" aria-describedby="dynamic-table_info">
                <thead>
                  <tr role="row">
                    <th class="hidden-180" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">系统代码</th>
                    <th width="200" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">系统名称</th>
                    <th width="310" class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">系统描述</th>
                    <th width="80" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">标签数目</th>
                    <th width="170" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">开始时间</th>
                    <th width="170" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">结束时间</th>
                    <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                  </tr>
                </thead>
                <tbody id="systemIds">
                  <s:iterator value="systemDatas" id="data">
                    <tr role="row" class="odd">
                      <td class="hidden-80"><s:property value="#data.id" /></td>
                      <td class="hidden-260"><s:property value="#data.name" /></td>
                      <td class="hidden-260"><s:property value="#data.description" /></td>
                      <td class="hidden-200"><s:property value="#data.tagCnt" /></td>
                      <td class="hidden-200"><s:date name="#data.startTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                      <td class="hidden-200"><s:date name="#data.endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                      <td class="hidden-260">
                        <div class="hidden-sm hidden-xs action-buttons" data-id="<s:property value='#data.id'/>">
                          <a class="green" title="修改" target='_self' href="/htgl/system/updateSystemIndex.action?id=<s:property value='#data.id'/>"><i class="ace-icon fa fa-pencil bigger-130"></i> </a> <a title="删除" class="red delBtn" href="javascript:void(0)"> <i class="ace-icon fa fa-trash-o bigger-130"></i>
                          </a>
                        </div>
                      </td>
                    </tr>
                  </s:iterator>
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
            	$(document).on("click",".delBtn",function() {
                	if(confirm("删除后将不可恢复，确定删除该系统？")) {
                		var $div = $(this).closest("div");
    	            	var id = $div.data("id");
    	            	$.getJSON(
                			"/htgl/system/delSystem.action",
                			{id:id},
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