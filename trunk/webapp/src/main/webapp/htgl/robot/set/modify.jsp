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
    <h1>修改对话</h1>
  </div>
  <div class="row">
    <form name="" action="/htgl/robot/set/update.action" method="get">
      <div class="col-xs-12">
      <s:iterator value="qaSet" id="entry">
      <input type="hidden" name="id" value="<s:property value='key.id'/>">
        <p>
          用户：<input type="text" name="q" value="<s:property value="key.q"/>" style="width: 600px;"  maxlength="50" <s:if test="key.systemDefined">readonly="true"</s:if>><span>（说明：多种相似的词汇或句子用","隔开，如：“姓名,名字”）</span>
        </p>
        <p>
          机器人：<input type="button" value="+" onclick="addLi()"></p>
        <ol>
          <s:iterator value="value" var="a" status="index">
            <li>
							<input type="text" name="a" value="<s:property value="#a.a"/>" style="width: 600px;" maxlength="500">&nbsp;&nbsp;<input type="button" value="x" onclick="removeLi(this)">
                        </li>
          </s:iterator>
        </ol>
        
     </s:iterator>
        <div class="clear"></div>
        <div class="clearfix form-actions">
          <div class="col-md-offset-3 col-md-9">
            <button class="btn btn-info" type="submit" id="modifyBtn">
              <i class="ace-icon fa fa-check bigger-110"></i> 修改
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
    $("ol").append("<li><input type=\"text\" name=\"a\" value=\"\" style=\"width: 600px;\" maxlength=\"500\">&nbsp;&nbsp;<input type='button' value='x' onclick='removeLi(this)'></li>");
}

function removeLi(obj){
    $(obj).parent().remove();
}

</script>
