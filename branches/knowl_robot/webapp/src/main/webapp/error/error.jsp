<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%
String ctxPath = request.getContextPath();
String message = (String)session.getAttribute("message");
if(null == message || message.equals("")){
	message = "系统访问出现异常";
}
try{
    if(pageContext.getErrorData().getStatusCode() == HttpServletResponse.SC_BAD_REQUEST){
        message = "非法请求";
    }
}catch(Exception e){}
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String time = sdf.format(new Date());
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统提示</title>
<style type="text/css">
body {margin-top:100px}
td {font-size:12px}
span{
    float: right;
    padding-right: 16px;
    color: #FFFF00;
}
</style>
</head>
<body>
<table width="410" height="163" border="0" align="center" cellpadding="0" cellspacing="0" background="<%=ctxPath%>/images/login/bg_error.jpg">
  <tr>
    <td width="127">&nbsp;</td>
    <td width="267" align="center">
    <br /><br />
    <font color="#FFFF00"><%=message%></font><br>
    <br>
    <font color="#FFFFFF"><br>
    <b>[</b><a href="javascript:history.back()" style="color:#FFFF00">返 回</a><b>]</b></font></td>
    <td width="16">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3" height="32px">
        <span><%=time%></span>
    </td>
  </tr>
</table>
</body>
</html>