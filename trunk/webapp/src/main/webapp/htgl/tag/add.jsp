<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!--breadcrumbs-->

    <div class="breadcrumbs" id="breadcrumbs">
        <script type="text/javascript">
            try {
                ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) {}
        </script>
        <ul class="breadcrumb">
            <li> <i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a> </li>
            <li class="active">添加新标签</li>
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
            <h1> 添加新标签  </h1>
        </div>
        <div class="row">
            <form name="" action="/htgl/tag/add.action" method="get">
                <div class="col-xs-12">
                    <p>
                        系统：
                        <select id="systemIds" name="systemId">
                        </select>
                    </p>
                    <p>名称：
                        <input type="text" name="name" style="width: 400px;" value="">
                    </p>
                    <p>
                        描述：
                        <input type="text" name="description" style="width: 400px;">
                    </p>

                    <p>排序：
                        <input id="sort" type="text" name="sort" style="width: 100px;" value=""> <span>（说明：1~99之间的数字,数值越大,序越靠前）<span>
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
	$.getJSON("/htgl/system/listSystem.action",
        function showSystems(json){
            if(json.flag=="true"){
              var options = "";
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