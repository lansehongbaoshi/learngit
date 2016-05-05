<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  String string = com.chsi.framework.web.UrlUtil.getRequestURL(request);
  StringBuffer strUrl = new StringBuffer(string);
  java.util.Enumeration enum1 = request.getParameterNames();
  strUrl.append("?");
  while(enum1.hasMoreElements()){
    String paramName = (String)enum1.nextElement();
    if(!(paramName.equals("start"))){
      if(strUrl.indexOf("?") != (strUrl.length() - 1))
        strUrl.append("&");
      strUrl.append(paramName);
      strUrl.append("=" + java.net.URLEncoder.encode(request.getParameter(paramName), "UTF-8"));
    }
  }
  strUrl.append("&from=pageNavigator");
  com.chsi.framework.page.Page pageRs = (com.chsi.framework.page.Page)request.getAttribute("page");
%>
<style type="text/css">
  #pageNum {
    width: 35px;
    height: 20px;
    text-align: center;
    padding:0;
    border:0 none;
  }
  .pagination {
    margin: 30px 0px 20px;
}
  .pagination ul {
    box-shadow: none;
}
  .pagination ul li{
    float:left;
    display:block;
    border:1px solid #ddd;
    padding:4px 12px;
  }
  .pagination ul > li > a:hover, .pagination ul > li > a:focus {
    background:#58b8e0;
    color:#fff;
  }
  .pagination ul  li  span{
    color:#999;
  }
  .pagination ul li a.selected{
    background:#58b8e0;
    color:#fff;
  }
</style>
<%
	int curPage = pageRs.getCurPage();
    int totalPage = pageRs.getTotalPage();
    int pagecount = pageRs.getPageCount();

    StringBuffer firstURL = new StringBuffer(strUrl);
    firstURL.append("&start=0");
    StringBuffer lastURL = new StringBuffer(strUrl);
    lastURL.append("&start=" + pageRs.getStartOfLastPage());

    int count = 5;
    int itv = count/2;
    int minPage = 1;
    int maxPage = 1;
    if(curPage - itv < 1){
        minPage = 1;
        maxPage = minPage + count - 1;
        if(maxPage > totalPage){
            maxPage = totalPage;
        }
    }else{
        minPage = curPage - itv;
    }
    if(curPage + itv > totalPage){
        maxPage = totalPage;
        minPage = maxPage - count + 1;
        if(minPage < 1){
            minPage = 1;
        }
    }else{
        if(maxPage == 1){
            maxPage = curPage+itv;
        }
    }
%>
<div class="pagination pagination-centered">
    <ul>
        <c:if test="${page.curPage == 1}">
            <li class="disabled"><span style="color:#999;">上一页</span></li>
        </c:if>
        <c:if test="${page.previousPageAvailable == true}">
        <%
          StringBuffer previousURL = new StringBuffer();
          previousURL.append(strUrl);
          previousURL.append("&start="+pageRs.getStartOfPreviousPage());
        %>
            <li><a href='<%=previousURL%>'>上一页</a></li>
        </c:if>
        <%
        if(curPage>3&&totalPage>5){%>
        <li class="lip"><a href="<%= firstURL%>">1</a></li>
        <%}
        if(curPage>4&&totalPage>6){%>
        <li class="lip disabled"><a href="javascript:void();">...</a></li>
      <% }
    for(int i=minPage;i<=maxPage;i++){
        StringBuffer pageURL = new StringBuffer();
        pageURL.append(strUrl);
        pageURL.append("&start="+(i - 1)* pagecount);
        String ll = "<li class=\"lip %s\"><a href=\"%s\">%s</a></li>";
        String li;
        if(i==curPage){
            li = String.format(ll,"selected","###",i);
        }else{
            li = String.format(ll, "",pageURL, i);
        }
        %>
        <%=li %>
        <%
    }
     if(curPage+3<totalPage&&totalPage>6){
    %>
       <li class="lip disabled"><a href="javascript:void();">...</a></li>
    <%
     }
    if(curPage<totalPage){

        StringBuffer nextURL = new StringBuffer();
        nextURL.append(strUrl);
        nextURL.append("&start="+pageRs.getStartOfNextPage());
        if(curPage+2<totalPage&&totalPage>5){
        %>
        <li class="lip"><a href="<%=lastURL%>"><c:out value="${page.totalPage}" /></a></li>
        <%}}%>
            <%-- <li><span><c:out value="${page.curPage}"/>/<c:out value="${page.totalPage}"/>页</span></li>    --%>
        <c:if test="${page.nextPageAvailable == true}">
        <%
          StringBuffer nextURL = new StringBuffer();
          nextURL.append(strUrl);
          nextURL.append("&start="+pageRs.getStartOfNextPage());
        %>
          <li><a href='<%=nextURL%>'>下一页</a></li>
        </c:if>
        <c:if test="${page.curPage == page.totalPage}">
            <li class="disabled"><span style="color:#999;">下一页</span></li>
        </c:if>
             <li><span style="background-color: transparent;border:none; color: #666; padding:4px 8px;">到第</span></li>
            <li>
                  <span style="border: none; padding: 0px; background-color: transparent; color: #666;">
                    <input type="hidden" id="totalPage" value="<c:out value='${page.totalPage}'/>"/>
                    <input type="text" class="input" id="pageNum" value="<c:out value='${page.curPage}'/>" size="4" maxLength="4" <c:if test="${page.totalPage == 1}">style="color:#999;"</c:if>/>
                  </span>
            </li>
            <li><span style="background-color: transparent;border:none; color: #666; padding:4px 8px;">页</span></li>
            <li style="border:0px;"><input type="button" class="btn btn-purple btn-sm" <c:if test="${page.totalPage <= 1}">disabled="disabled"</c:if> <c:if test="${page.totalPage > 1}">onClick="jumpPage()"</c:if> value="确定" /></li>
        <ul>
</div>
<script language="javascript">
function gotoPage(selectObj){
	var page = selectObj.value;
	var start = (page - 1) * <c:out value="${page.pageCount}"/>;
	window.location.href = "<%=strUrl%>&start=" + start;
}

function jumpPage(){
  var tp = document.getElementById("totalPage");
  if(tp.value <= 1)
    return false;
  var pattern = /^\d{1,4}$/;
  var pn = document.getElementById("pageNum");
  if(pn != null && pn.value != ''){
    var page = pn.value;
    if(!pattern.exec(page)){
      alert("跳转页码有误");
      return false;
    }
    if(parseInt(page,10) < 1 || parseInt(page,10) > tp.value){
      alert("跳转页码范围1-" + tp.value);
      return false;
    }
    var start = (page - 1) * <c:out value="${page.pageCount}"/>;
	  window.location.href = "<%=strUrl%>&start=" + start;
  }
}

$(function(){
  var list = $('.pagination ul li');
  list.each(function(){
    if ($(this).hasClass('selected')) {
      $(this).find('a').addClass('selected');
    }
  });
})
</script>
</div>