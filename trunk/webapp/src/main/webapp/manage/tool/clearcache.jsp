<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.List"%>
<%@ page import="com.chsi.knowledge.util.*" %>
<%@ page import="com.chsi.knowledge.service.*" %>
<%@ page import="com.chsi.knowledge.pojo.*" %>
<%
String ctxPath = request.getContextPath();
String clearResult = "";
String key = request.getParameter("key");
boolean isResult = false;
if(key!=null && !"".equals(key)) {
    if(MemCachedUtil.get(key)!=null) {
        MemCachedUtil.removeByKey(key);
	    if(MemCachedUtil.get(key)==null) {
	        clearResult = "缓存清除成功："+key;
	    } else {
	        clearResult = "缓存清除失败："+key;
	    }
    } else {
        clearResult = "缓存里无此key："+key;
    }
    isResult = true;
}
String method = request.getParameter("method");
StringBuffer sb = new StringBuffer();

if(method!=null&& !"".equals(method)) {
    if(method.equals("clear all knowledge cache")) {
        KnowledgeService KnowledgeService = ServiceFactory.getKnowledgeService();
        SystemService SystemService = ServiceFactory.getSystemService();
        List<SystemData> list = SystemService.getSystems(false);
        
        for(SystemData data:list) {
            List<KnowledgeData> list2 = KnowledgeService.get(data.getId());
            for(KnowledgeData data2:list2) {
                ManageCacheUtil.removeKnowledgeDataById(data2.getId());
                sb.append(String.format("<br>清除知识缓存:id=%s", data2.getId()));
            }
        }
        isResult = true;
    } else if(method.equals("clear index top")) {
        key = "com.chsi.knowledge.util.ManageCacheUtil.getIndexTopKnowl";
        MemCachedUtil.removeByKey(key);
    } else if(method.equals("clear more top")) {
        key = "com.chsi.knowledge.util.ManageCacheUtil.getCatalogTopKnowl";
        MemCachedUtil.removeByKey(key);
    } else if(method.equals("clear top search")) {
        key = "com.chsi.knowledge.util.ManageCacheUtil.getTopSearchKnow";
        MemCachedUtil.removeByKey(key);
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
<form action="<%=ctxPath%>/manage/tool/clearcache.jsp" method="post" style="margin:0; display:block;" onsubmit="return checkForm(this)">
	  缓存key：<input type="text" style="width:640px;" title="全匹配" id="key" name="key" value="com.chsi.knowledge.util.ManageCacheUtil." /> <font color="blue">(全匹配)</font>
      <input type="submit" value="提交" class="xlbutton"/>
</form>
<hr>
<form action="<%=ctxPath%>/manage/tool/clearcache.jsp" method="post" style="margin:0;display:block;" onsubmit="">
      <input type="submit" name="method" value="clear all knowledge cache" class="xlbutton"/>
      <input type="submit" name="method" value="clear index top" class="xlbutton" title="首页热门问题列表"/>
      <input type="submit" name="method" value="clear more top" class="xlbutton" title="查看更多热门问题列表"/>
      <input type="submit" name="method" value="clear top search" class="xlbutton" title="搜索频率高的问题"/>
</form>
<%if(isResult) {%>
<hr>
<p>
<h2>处理结果</h2>
<%=clearResult %>
<%=sb.toString() %>
</p>
<%} %>
</body></html>
