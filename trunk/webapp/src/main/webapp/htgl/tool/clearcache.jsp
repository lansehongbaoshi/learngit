<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.List"%>
<%@ page import="com.chsi.knowledge.util.MemCachedUtil" %>
<%
String ctxPath = request.getContextPath();
String clearResult = "请输入缓存key";
String key = request.getParameter("key");
if(key!=null && !"".equals(key)) {
    if(MemCachedUtil.get(key)!=null) {
        MemCachedUtil.removeByKey(key);
	    if(MemCachedUtil.get(key)==null) {
	        clearResult = "缓存清除成功";
	    } else {
	        clearResult = "缓存清除失败";
	    }
    } else {
        clearResult = "缓存里无此key";
    }
}
%>
<html>
<head>
<title>缓存清理</title>
<script language="javascript">
function checkForm(abc){
	var a = document.getElementById("key");
	if(a.value=='') {
		alert("不能为空");
		return false
	}
	return true;
}
</script>
<style type="text/css">
 form{ display:inline;}
</style>
</head>
<body>
<br />
<form action="<%=ctxPath%>/htgl/tool/clearcache.jsp" method="post" style="margin:0; display:inline;" onsubmit="return checkForm(this)">
	  缓存key：<input type="text" style="width:340px;" title="全匹配" id="key" name="key" value="" /> <font color="blue">(全匹配)</font>
      <input type="submit" value="提交" class="xlbutton"/>
</form>
<%=clearResult %>
</body></html>
