<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 <!--breadcrumbs-->

    <div class="breadcrumbs" id="breadcrumbs">
        <script type="text/javascript">
            try {
                ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) {}
        </script>
        <ul class="breadcrumb">
            <li> <i class="ace-icon fa fa-home home-icon"></i> <a href="/htgl/index.action">首页</a> </li>
            <li class="active">系统管理</li>
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
            <h1> 添加新系统 </h1>
        </div>
        <div class="row">
          <form name="" action="/htgl/system/addSystem.action" method="get">
            
                <div class="col-xs-12">
                    <p>
		          系统位置： <input type="text" name="sort" value="" style="width: 400px;">
		           </p>
                    <p>
                        系统代码：<input type="text" name="id" value="" style="width: 400px;"><span>（说明：填写系统简称代码，如征兵就写“zb”）<span>
                    </p>
                    <p>系统名称：<input type="text" name="name" value="" style="width: 400px;" >
                    </p>
                    <p>
                       系统描述：<input type="text" name="description" value="" style="width: 400px;">
                    </p>
                    
                    <p>
                    开放时间段：<input type="button" value="+" onclick="addLi()"><br>
                    <ol>
                    </ol>
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
              
<script type="text/javascript">
function addLi(){
	$("ol").append("<li><input type='text' name='startTime' style='width: 400px;' value='2016-03-01 08:00:00'>—<input type='text' name='endTime' style='width: 400px;' value='2016-07-01 08:00:00'>&nbsp;&nbsp;<input type='button' value='x' onclick='removeLi(this)'></li>");
}

function removeLi(obj){
	$(obj).parent().remove();
}

</script>


