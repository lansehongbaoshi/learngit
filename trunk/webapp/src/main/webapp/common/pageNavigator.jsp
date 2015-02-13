<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.chsi.knowledge.web.util.URLUtil" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
  StringBuffer strUrl = URLUtil.getActionURL(request);
  java.util.Enumeration enum1 = request.getParameterNames();
  strUrl.append("?");
  while(enum1.hasMoreElements()){
    String paramName = (String)enum1.nextElement();
    if(!(paramName.equals("start"))){
      strUrl.append("&" + paramName);
      strUrl.append("=" + java.net.URLEncoder.encode(request.getParameter(paramName), "utf-8"));
    }
  }
  com.chsi.framework.page.Page pageRs = (com.chsi.framework.page.Page)request.getAttribute("page");
%>
<font>
<c:if test="${page.curPage == 1}">
首页&nbsp;&nbsp;上页
</c:if>
<c:if test="${page.previousPageAvailable == true}">
<%
  StringBuffer firstURL = new StringBuffer();
  firstURL.append(strUrl);
  firstURL.append("&start=0");
  StringBuffer previousURL = new StringBuffer();
  previousURL.append(strUrl);
  previousURL.append("&start="+pageRs.getStartOfPreviousPage());
%>
    <a href='<%=firstURL%>' style=" text-decoration:underline; color:#0000FF">首页</a>&nbsp;&nbsp;<a href='<%=previousURL%>' style=" text-decoration:underline; color:#0000FF">上页</a>
</c:if>
<c:if test="${page.nextPageAvailable == true}">
<%
  StringBuffer nextURL = new StringBuffer();
  nextURL.append(strUrl);
  nextURL.append("&start="+pageRs.getStartOfNextPage());
  StringBuffer lastURL = new StringBuffer();
  lastURL.append(strUrl);
  lastURL.append("&start="+pageRs.getStartOfLastPage());
%>&nbsp;&nbsp;<a href='<%=nextURL%>' style=" text-decoration:underline; color:#0000FF">下页</a>&nbsp;&nbsp;<a href='<%=lastURL%>' style=" text-decoration:underline; color:#0000FF">末页</a>
</c:if>
<c:if test="${page.curPage == page.totalPage}">
&nbsp;&nbsp;下页&nbsp;&nbsp;末页
</c:if>
&nbsp;&nbsp;
<select onchange="gotoPage(this)">
<script language="javascript">
<!--
    for(var i = 1; i <= <c:out value="${page.totalPage}"/>; i++){
        if (i == <c:out value="${page.curPage}"/>) {
            document.write("<option value='" + i + "' selected>" + i + "</option>");
        } else {
            document.write("<option value='" + i + "'>" + i + "</option>");
        }
    }
//-->
</script>
</select>
<script language="javascript">
function gotoPage(selectObj){
    var page = selectObj.value;
    var start = (page - 1) * <c:out value="${page.pageCount}"/>;
    window.location.href = "<%=strUrl%>&start=" + start;
}
</script>
</font>