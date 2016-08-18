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
  <div class="page-header">
    <h1>上传字典</h1>
  </div>
  <div class="row">
    <form name="" action="/htgl/robot/set/updateCommit.action" method="post" onsubmit="" enctype="multipart/form-data">
      <div class="col-xs-12">
      	<input type="file" name="file">
      	<input type="submit" value="提交">
      </div>
    </form>
  </div>
</div>