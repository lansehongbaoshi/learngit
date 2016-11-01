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
</div>
<div class="page-content">
  <div class="page-header">
    <h1>修改对话</h1>
  </div>
  <div class="row">
    <form name="" action="/htgl/robot/set/update.action" method="post" onsubmit="return checkForm(this)" enctype="multipart/form-data">
      <div class="col-xs-12">
      <s:iterator value="qaSet" id="entry">
      <input type="hidden" name="id" value="<s:property value='key.id'/>">
        <p>
          用户提问：<input type="text" name="q" value="<s:property value="key.q"/>" check="^[\S]{1,}$" warn="用户提问不能为空" style="width: 600px;"  maxlength="50" <s:if test="key.systemDefined">readonly="true"</s:if>><span>（说明：多种相似的词汇或句子用","隔开，如：“姓名,名字”）</span>
        </p>
        <p>
          匹配分词阈值：<input type="text" name="num" value="<s:property value="key.num"/>" check="^[1-9]\d*$" warn="匹配分词阈值为正整数" style="width: 400px;"  maxlength="50" <s:if test="key.systemDefined">readonly="true"</s:if>><span>（说明：用户输入匹配用户提问的分词数目大于等于此值时才认为回答合理）</span>
        </p>
        <p>
          机器人回答：<input type="button" value="+" onclick="addLi()"></p>
        <ol>
          <s:iterator value="value" var="a" status="index">
            <li>
							<input type="text" name="a" value="<s:property value="#a.a"/>" style="width: 600px;" maxlength="1000" check="^[\S]{1,}$" warn="请补充完整机器人回答" >&nbsp;&nbsp;<input type="button" value="x" onclick="removeLi(this)">
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
    $("ol").append("<li><input type=\"text\" name=\"a\" value=\"\" style=\"width: 600px;\" maxlength=\"1000\" check='^[\\S]{1,}$' warn='请补充完整机器人回答'>&nbsp;&nbsp;<input type='button' value='x' onclick='removeLi(this)'></li>");
}

function removeLi(obj){
	if($("ol li").size()<=1) {
        alert("机器人回答请至少保留一个！");
        return;
    }
    $(obj).parent().remove();
}
</script>
