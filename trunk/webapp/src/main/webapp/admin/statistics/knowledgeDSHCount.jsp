<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%>
<style>
h1.jumbo {
    font-size:6em;
    margin:0;
    color:#4d63bc;
}
</style>
<script src="/js/countUp.js"></script>

<div style="width: 100%;height:500px;float:left">
    <p>待审核知识的数量：</p>
    <h1 class="jumbo" id="myTargetElement"></h1>
</div>

<script type="text/javascript">

var options = {
		  useEasing : true, 
		  useGrouping : true, 
		  separator : ',', 
		  decimal : '.', 
		  prefix : '', 
		  suffix : '' 
};
$.getJSON("/admin/search/searchDSHKnowCount.action?t="+new Date(),function callback(json){
	if(json.flag=="true"){
		var totaltxt = json.o.total;
		var tatal = Number(totaltxt);
		var demo = new CountUp("myTargetElement", 0, tatal, 0, 2.5, options);
		demo.start();
	}
});

</script>
