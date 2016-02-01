<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".search").on("click", function(){
		document.location.href = "/wap/help/sousuo.jsp";
	})
})
</script>
<div class="search">
  <div>
    <div class="sou">
      <img src="../../images/wap/help/search.png" />遇到问题搜一搜
    </div>
    <div class="text"><a href='/wap/help/index.jsp'><img src='../../images/wap/help/wap_index.png'/></a></div>
  </div>
</div>