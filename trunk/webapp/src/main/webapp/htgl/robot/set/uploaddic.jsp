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
</div>
<div class="page-content">
  <div class="page-header">
    <h1>上传字典</h1>
  </div>
  <div class="row">
  <p style="font-weight:bold;">说明：支持txt格式上传，txt里问答各占一行，问答之间隔一空白行。一个提问多种回答的，可以按相同的提问不同的回答写多个。</p>
    <form name="" action="/htgl/robot/set/updateCommit.action" method="post" onsubmit="" enctype="multipart/form-data">
      <div class="col-xs-12">
      	<input type="file" name="file">
      	<input type="submit" value="提交">
      </div>
    </form>
  </div>
</div>