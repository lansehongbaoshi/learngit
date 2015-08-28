<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="com.chsi.knowledge.Constants" %>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
String message = (String)request.getAttribute(Constants.REQUEST_ERROR);
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
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>系统提示</title>
<style type="text/css">
html, body, p,ul,ol,li,img,h1,h2,h3,h4,h5,h6,form,fieldset,legend,select,input{margin:0; padding:0;}
body{background:#f9f9f9;color:#333333;text-align:center;font:14px/28px Tahoma, Helvetica, Arial, 'Microsoft YaHei', \5FAE\8F6F\96C5\9ED1, SimSun, sans-serif;}
ul,ol,li{list-style:none;}
select,input{vertical-align:middle; font-size:12px; font-family: Tahoma, Helvetica, Arial, 'Microsoft YaHei', \5FAE\8F6F\96C5\9ED1, SimSun, sans-serif;}
img{border:0;}
p{text-indent:0;}
a,a:link,a:visited{color:#333333; text-decoration:none;}
a:hover{color:#DC5925; text-decoration:underline;}
.clearfix:after{content:'\0020'; display:block; clear:both; height:0;}
.clearfix{zoom:1;}
.clearFloat{ clear:both;height:0;line-height:0;font-size:0; overflow:hidden;}


.btm{ height:60px; line-height:60px; border-top:1px solid #DDD; text-align:center;}
a.indexlogin{
    border:none;
	color: white;
	background-color: #1188B4;
    margin:15px auto;
    padding:8px 20px;
	font-size: 14px;
	cursor: pointer;
    text-align:center;
	-webkit-border-radius: 2px;
	border-radius: 2px;
	text-indent: 0;
    text-decoration:none;
}
a.indexlogin:hover{
    color: white;
	background-color: #2EADCF;
	text-decoration: none;
}
</style>

<style type="text/css">
/* CSS Document */
.box {
    background: url("https://t1.chsi.com.cn/passport/images/login2015/picsrv_bg.jpg") no-repeat scroll center top rgba(0, 0, 0, 0);
    width: 500px;
    height: 350px;
    margin: 0px auto;
    padding-top:1px;
   font:14px/25px "Microsoft YaHei", "微软雅黑", "SimSun", "宋体", "Arial Narrow", HELVETICA;
   
}
.picsrv-tips{ background-color:#fafafa; border:solid 1px #ccc; padding:10px 20px; margin:75px 15px; text-align:left;  color:#848484;}
.picsrv-tips h3{ font-size:14px; margin:15px auto;}


</style>
</head>
<body>

<div class="box">
<table cellpadding="0" border="0" width="100%">
<tbody>
<tr>
<td height="100%">
<div class="picsrv-tips">
    <h3><strong style="color:#c30;">提示信息：</strong><%=message%></h3>
    <p style="padding:5px 0;"><span style="color:#999;">（<%=time%>）</span> </p>
    <div class="btm"><a href="javascript:history.back(-1)" class="indexlogin">返回</a></div>
    </div>
 
</td>
</tr>
</tbody>
</table>

  </div>
</body>
</html>