<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!--breadcrumbs-->

    <div class="breadcrumbs" id="breadcrumbs">
        <script type="text/javascript">
            try {
                ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) {}
        </script>
        <ul class="breadcrumb">
            <li> <i class="ace-icon fa fa-home home-icon"></i> <a href="/admin/index.action">首页</a> </li>
            <li class="active">添加新标签</li>
        </ul>
        <!-- /.breadcrumb -->
    </div>
    <div class="page-content">
        <div class="page-header">
            <h1> 添加新标签  </h1>
        </div>
        <div class="row">
            <form name="" action="/admin/tag/add.action" method="get">
                <div class="col-xs-12">
                    <p>
                        所属系统：
                        <select id="systemIds" name="systemId">
                        </select>
                    </p>
                    <p>标签名称：
                        <input type="text" name="name" style="width: 400px;" value="">
                    </p>
                    <p>
                        标签描述：
                        <input type="text" name="description" style="width: 400px;">
                    </p>

                    <p>标签排序：
                        <input id="sort" type="text" name="sort" style="width: 100px;" value=""> <span>（说明：1~999之间的数字,数值越大,排序越靠前）</span>
                            </p>
                    <p>默认标签：
                        <input id="" type="checkbox" name="property" style="width: 100px;" value="1" > <span>（说明：勾上则为默认标签，一个系统只能勾一个默认标签）</span>
                            </p>   
                    
                        <div class="clear"></div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info" type="submit" id="modifyBtn">
                                    <i class="ace-icon fa fa-check bigger-110"></i> 新增
                                </button>

                                &nbsp; &nbsp; &nbsp;
                                <button class="btn" type="reset" onclick="history.go(-1)">
                                    <i class="ace-icon fa fa-undo bigger-110"></i> 取消
                                </button>
                            </div>
                        </div>
                    </div>
                     </form>
                </div>




            </div>
            
<script>
$(function(){
	$.getJSON("/admin/listSystem.action",
        function showSystems(json){
            if(json.flag=="true"){
              var options = "<option value=''>请选择...</option>";
               for(var i=0;i<json.o.length;i++){
                var option = json.o[i];
                options+="<option value='"+option.id+"'>"+option.name+"</option>";
               }
               $("#systemIds").html(options);
            }
        }
    );
})
</script>