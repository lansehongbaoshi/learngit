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
    <li class="active">机器人配置</li>
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
            <span class="col-xs-7">已有对话配置</span> <span class="col-xs-5"> 
            
            <a href="/htgl/robot/set/addIndex.action" target="_self" class="btn-new-mail pull-right"> 
            <span class="btn btn-primary no-border"> <i class="ace-icon fa glyphicon-plus bigger-130"></i> <span class="bigger-110">新增</span></span>
            </a>
            </span>
          </h3>
          <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
              <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable" role="grid" aria-describedby="dynamic-table_info">
                <thead>
                  <tr role="row">
                    <th width="340" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">用户</th>
                    <th width="340" class="hidden-200" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">机器人</th>
                    <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                  </tr>
                </thead>
                <tbody id="systemIds">
                  <s:iterator value="qaSet" id="entry">
                    <tr role="row" class="odd">
                      <td class="hidden-200">
	                       <s:property value="key.q"/>
                      </td>
                      <td class="hidden-200">
	                      <s:iterator value="value" var="a" status="index">
	                          <s:property value="#index.index+1"/>.<s:property value="#a.a"/><br>
	                      </s:iterator>
                      </td>
                      <td class="hidden-260">
                        <div class="hidden-sm hidden-xs action-buttons" data-id="<s:property value="key.id"/>">
                          <a class="green" title="修改" target='_self' href="/htgl/robot/set/updateIndex.action?id=<s:property value='key.id'/>"><i class="ace-icon fa fa-pencil bigger-130"></i> </a> <a title="删除" class="red delBtn" href="javascript:void(0)"> <i class="ace-icon fa fa-trash-o bigger-130"></i>
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
</div><!-- /.modal -->

<script type="text/javascript">
function openWindow(obj){
	$(".modal-body").load("/htgl/system/list.action",{id:obj});
}
$(".save").click(function(){
   var str ="";
   var saveId = $(".saveId").val();
    $("input:checked").each(function(){
        str += $(this).val() + ",";
    });
    str = str.substring(0,str.length-1)
     $.getJSON(
     "/htgl/system/updateTag.action",
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
                	if(confirm("删除后将不可恢复，确定删除该对话？")) {
                		var $div = $(this).closest("div");
    	            	var id = $div.data("id");
    	            	$.getJSON(
                			"/htgl/robot/set/delete.action",
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