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
                        系统代码：<input type="text" name="id" value="" style="width: 400px;"><span>（说明：填写系统简称代码，如征兵就写“zb”）<span>
                    </p>
                    <p>系统名称：<input type="text" name="name" value="" style="width: 400px;" >
                    </p>
                    <p>
                       系统描述：<input type="text" name="description" value="" style="width: 400px;">
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
})
</script>


