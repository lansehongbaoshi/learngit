<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.chsi.framework.util.ValidatorUtil,com.chsi.knowledge.pojo.*,com.chsi.knowledge.service.*,java.util.*,com.chsi.knowledge.vo.*,net.sf.json.*" %>
<%
RobotService robotService = ServiceFactory.getRobotService();
CommonService commonService = ServiceFactory.getCommonService();
List<PieVO> totalList = null;
String type = request.getParameter("type");
String startTime = request.getParameter("startTime");
String endTime = request.getParameter("endTime");
if(!ValidatorUtil.isNull(type)) {
    if(type.equals("session")) {
        totalList = robotService.totalSession(startTime, endTime);
        %>
({title:{text:'机器人会话统计',subtext:<%=String.format("'共%d个会话'",PieVO.totalCnt(totalList))%>,x:'center'},tooltip:{trigger:'item',formatter:"{a}<br/>{b}:{c}({d}%)"},legend:{orient:'vertical',left:'left',data:<%=JSONArray.fromObject(PieVO.getNames(totalList)).toString()%>},series:[{name:'会话类型',type:'pie',radius:'55%',center:['50%','60%'],data:<%=JSONArray.fromObject(totalList).toString()%>,itemStyle:{emphasis:{shadowBlur:10,shadowOffsetX:0,shadowColor:'rgba(0,0,0,0.5)'}}}]})
<%} else if(type.equals("q")) {
        totalList = robotService.totalQ(startTime, endTime);
        %>
({title:{text:'机器人问题统计',subtext:<%=String.format("'共%d个问题,点【无答案】可看详情'",PieVO.totalCnt(totalList))%>,x:'center'},tooltip:{trigger:'item',formatter:"{a}<br/>{b}:{c}({d}%)"},legend:{orient:'vertical',left:'left',data:<%=JSONArray.fromObject(PieVO.getNames(totalList)).toString()%>},series:[{name:'问题类型',type:'pie',radius:'55%',center:['50%','60%'],data:<%=JSONArray.fromObject(totalList).toString()%>,itemStyle:{emphasis:{shadowBlur:10,shadowOffsetX:0,shadowColor:'rgba(0,0,0,0.5)'}}}]})
<% } else if(type.equals("visitlog")) {
    String systemId = request.getParameter("systemId");
    String topCnt = request.getParameter("topCnt");
    startTime = startTime.replaceAll("-", "");
    endTime = endTime.replaceAll("-", "");
    LineChartVO vo = commonService.getTopKnowlVisitLog(systemId, Integer.parseInt(topCnt), startTime, endTime);
    %>
({title:{text:'',x:'center'},tooltip:{trigger:'axis'},legend:{data:<%=JSONArray.fromObject(vo.getLegend()).toString() %>},toolbox:{show:false},grid:{left:'3%',right:'4%',bottom:'3%',containLabel:true},xAxis:[{type:'category',boundaryGap:false,data:<%=JSONArray.fromObject(vo.getxAxis()).toString() %>}],yAxis:[{type:'value'}],series:<%=JSONArray.fromObject(vo.getSeries()).toString() %>})
<%}
} 
%>