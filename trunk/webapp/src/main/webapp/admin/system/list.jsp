<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="breadcrumbs" id="breadcrumbs">
  <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {}
            </script>
  <ul class="breadcrumb">
    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="/admin/index.action">首页</a></li>
    <li class="active">系统管理</li>
  </ul>
  <!-- /.breadcrumb -->
</div>
<div class="page-content">
  <div class="rows">
    <div class="col-xs-12">
      <div class="row">
        <div class="col-xs-12">
          <h3 class="row header smaller lighter blue">
            <span class="col-xs-7">系统列表</span> <span class="col-xs-5"> 
            
            <a href="/admin/system/addSystemIndex.action" target="_self" class="btn-new-mail pull-right"> 
            <span class="btn btn-primary no-border"> <i class="ace-icon fa glyphicon-plus bigger-130"></i> <span class="bigger-110">新增</span></span>
            </a>
            </span>
          </h3>
          <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
              <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable" role="grid" aria-describedby="dynamic-table_info">
                <thead>
                  <tr role="row">
                    <th class="hidden-180" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">排序</th>
                    <th class="hidden-180" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">代码</th>
                    <th width="150" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">名称</th>
                    <th width="200" class="hidden-480 sorting" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Clicks: activate to sort column ascending">描述</th>
                    <th width="150" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">属性</th>
                    <th width="80" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">标签数目</th>
                    <th width="160" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">知识数目(内部/公开)</th>
                    <th width="300" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">开放时间段</th>
                    <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                  </tr>
                </thead>
                <tbody id="systemIds">
                  <s:iterator value="systemDatas" id="data">
                    <tr role="row" class="odd">
                      <td class="hidden-80"><s:property value="#data.sort" /></td>
                      <td class="hidden-80"><s:property value="#data.id" /></td>
                      <td class="hidden-260"><s:property value="#data.name" /></td>
                      <td class="hidden-260"><s:property value="#data.description" /></td>
                      <td class="hidden-260"><s:if test="#data.property==0" >公开</s:if><s:if test="#data.property==1" >内部</s:if></td>
                      <td class="hidden-200"><s:property value="#data.tagCnt" /></td>
                      <td class="hidden-200"><s:property value="#data.knowPrivateCnt" />/<s:property value="#data.knowPublicCnt" /></td>
                      <td class="hidden-200">
	                      <s:iterator value="#data.list" var="time">
	                          <a href="javascript:void(0)" data-toggle="modal" data-target="#myModal" onclick="openWindow('<s:property value="#time.id"/>')"><s:date name="#time.startTime" format="yyyy-MM-dd HH:mm:ss"/>—<s:date name="#time.endTime" format="yyyy-MM-dd HH:mm:ss"/></a>
	                      </s:iterator>
                      </td>
                      <td class="hidden-260">
                        <div class="hidden-sm hidden-xs action-buttons" data-id="<s:property value='#data.id'/>">
                          <a class="green" title="修改" target='_self' href="/admin/system/updateSystemIndex.action?id=<s:property value='#data.id'/>"><i class="ace-icon fa fa-pencil bigger-130"></i></a> 
                          <a data-action="update-time" href="javascript:void(0)" title="将当前时间设置为更新时间"> <i class="ace-icon fa fa-clock-o bigger-130"></i></a>
                          <a title="删除" class="red delBtn" href="javascript:void(0)"> <i class="ace-icon fa fa-trash-o bigger-130"></i>
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
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
               添加标签
            </h4>
         </div>
         <div class="modal-body">
            在这里添加一些文本
         </div>
         <div class="modal-footer">

            <button type="button" class="btn btn-primary save">
               保存
            </button>
                        <button type="button" class="btn btn-default" 
               data-dismiss="modal">关闭
            </button>
         </div>
      </div><!-- /.modal-content -->
</div>
</div><!-- /.modal -->

<script type="text/javascript">
function openWindow(obj){
	$(".modal-body").load("/admin/system/list.action",{id:obj});
}
$(".save").click(function(){
   var str ="";
   var saveId = $(".saveId").val();
    $("input:checked").each(function(){
        str += $(this).val() + ",";
    });
    str = str.substring(0,str.length-1)
     $.getJSON(
     "/admin/system/updateTag.action",
     {id:saveId,name:str},
     function(data){
         if(data.flag=='true') {
             alert("保存成功");
             $(".btn-default").trigger("click");
         } 
     }
     )
})
</script>
<script>
    $(function () {
    	$(document).on("click",".delBtn",function() {
        	if(confirm("删除后将不可恢复，确定删除该系统？")) {
        		var $div = $(this).closest("div");
          	var id = $div.data("id");
          	$.getJSON(
        			"/admin/system/delSystem.action",
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
    	$("a[data-action='update-time']").on("click",function(){
            if(confirm("确定刷新该系统下所有知识的更新时间吗？")) {
            	var $div = $(this).closest("div");
                var id = $div.data("id");
                document.location.href="/admin/system/listSystems/updateKnowledgeTime?id="+id;
            }
        });
    })
</script>