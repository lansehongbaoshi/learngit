<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国高等教育学历认证网上申请平台--首页</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/main.css"/>
</head>

<body>

<div id="container">
  <div id="header">
<h1>中国高等教育学历认证网上申请平台</h1>
<img src="<%=request.getContextPath()%>/images/big_logo.jpg" alt="中国高等教育学历认证网上申请平台" />
  <!-- end #header --></div>
  <div id="sidebar1">
  <div class="login_box">
  <div class="login_box_top">
  <h2>系统登录</h2>
  <p class="sign_in"><input type="button" class="bnt_login" value="学历认证网上申请" onclick="gotoIndex()" /> <br />
  <span>(请使用学信网账号登录)</span>
   <a href="https://account.chsi.com.cn/account/account!newAccount" target="_blank">注册学信网账号</a> | <a href="https://account.chsi.com.cn/account/password!retrive" target="_blank">找回密码</a> 

  </p>
  </div>
  <div class="kf_box">
   <p><strong style="font-size:14px">服务热线：</strong><br />
    010-82332257</p>
   </div>
   
  </div>
     <div class="left_sm">
        <strong style="color:#F00">声明：</strong>2008年6月1日，《中国高等教育学历查询报告》正式更名为《中国高等教育学历认证报告》，两者效力相同，样式无明显变化。
    </div>
  <!-- end #sidebar1 --></div>
  
  
  <div id="mainContent">
  <div class="main_box1">
    <h2 class="boder_line" style="color:#ca3f3f;"><span><a href="#"><img src="<%=request.getContextPath()%>/images/more_icon.jpg" alt="more" border="0" /></a></span>认证项目</h2>
    <ul class="fltlft" style="width:260px">
     <li><a href="#">中文学历认证报告</a></li>
     <li><a href="#">英文学历认证报告</a></li>
     <li><a href="#">中文成绩单</a></li>
     <li><a href="#">英文成绩单</a></li>
     <li><a href="#">中英文翻译认证报告</a></li>
    </ul>
    <p class="fltrt" style="padding-top:10px; "><a href="http://www.chsi.com.cn/xlrz/xlbgys.shtml" target="_blank"><img src="<%=request.getContextPath()%>/images/example.jpg" border="0" /></a><br />
  <a href="http://www.chsi.com.cn/xlrz/xlbgys.shtml" target="_blank">(报告样本)</a></p>
  <br class="clearfloat" />
     <h2 class="boder_line" style=" margin-top:6px;">常见问题</h2>
    <ul>
     <li><a href="http://www.chsi.com.cn/xlrz/ct05.shtml" target="_blank">学历认证申请方式</a> <img src="<%=request.getContextPath()%>/images/new_icon.gif" /></li>
     <li><a href="http://www.chsi.com.cn/xlrz/ct05.shtml#1" target="_blank">网上申请认证注意事项</a> <img src="<%=request.getContextPath()%>/images/new_icon.gif" /></li>
     <li><a href="http://www.chsi.com.cn/xlrz/ct05.shtml#1" target="_blank">中心现场申请认证办法</a></li>
     <li><a href="http://www.chsi.com.cn/xlrz/ct05.shtml#2" target="_blank">邮寄方式申请认证办法</a></li>
     <li><a href="http://www.chsi.com.cn/xlrz/xlrzsqb.doc" target="_blank">下载学历认证申请表</a></li>
     <li><a href="http://www.chsi.com.cn/xlrz/ct02.shtml" target="_blank">高等教育学历认证代理机构</a></li>
     <li><a href="http://www.chsi.com.cn/xlrz/ct01.shtml" target="_blank">教育部学历认证中心介绍</a></li>  
    </ul>
    
    </div>
    <div class="main_box2">
     <h2 class="boder_line">公告栏</h2>
    <ul style="height:126px; overflow:hidden;">
     <li>中国高等教育学历认证网上申请平台现已开通 <img src="<%=request.getContextPath()%>/images/new_icon.gif" /></li>
    <li><a href="http://www.chsi.com.cn/xlrz/201111/20111122/261713528.html" target="_blank" style="color:#F00;"><strong>关于代理办理学历认证的严正声明</strong></a>&nbsp;&nbsp;</li>
    <li><a href="http://www.chsi.com.cn/xlrz/201010/20101022/134905777.html" target="_blank">关于开通学历认证语音咨询电话的公告</a></li>
    <li><a href="http://www.chsi.com.cn/xlrz/200907/20090708/27655314.html" target="_blank">关于暂停受理军队院校研究生学历认证申请的通告</a></li>
    <li><a href="http://www.chsi.com.cn/xlcx/200804/20080411/4755845.html" target="_blank">关于打击学历认证报告造假行为的声明</a></li>
    </ul>
    <br class="clearfloat" />
     <h2 class="boder_line">联系我们</h2>
    <ul class="contact_us">
      <li><strong>工作时间：</strong>周一至周五8:30-17:30</li>
<li><strong>通讯地址：</strong>北京市海淀区北四环中路238号柏彦大厦506室
（北航北门向东200米）</li>
	 <li><strong>邮政编码：</strong>100191</li>
     <li><strong>咨询电话：</strong>010-82332257
     <!--<br>
      由于进行电话咨询的用户较多，因此建议您通过电子邮件方式进行咨询，我们会及时给您回复。--></li>
      <li><strong>传真电话：</strong>010-82338426</li>
      <li><strong>客服邮箱：</strong> xlrz@moe.edu.cn
     <%-- <br><a _fcksavedurl="mailto:xlrz@moe.edu.cn" href="mailto:xlrz@moe.edu.cn"><img width="95" height="19" src="http://www.chsi.com.cn/images/xlcx/xlrzEmail.gif"></a>--%>
     </li>
     </ul>
    </div>
    <!-- end #mainContent --></div>
    <br class="clearfloat" />
   <%-- <div style="clear:both; padding:10px; margin:10px 15px 0px 15px; font-size:12px; border:solid #e1e2e2 1px; text-align:center;">
        <strong>声明：</strong>2008年6月1日，《中国高等教育学历查询报告》正式更名为《中国高等教育学历认证报告》，两者效力相同，样式无明显变化。
    </div>--%>
   <br class="clearfloat" />
  <div id="footer">
<a href="<%=request.getContextPath()%>/about/about_center.shtml" target="_blank">中心简介</a> － <a href="<%=request.getContextPath()%>/about/about_site.shtml" target="_blank">网站简介</a> － <a href="<%=request.getContextPath()%>/about/contact.shtml" target="_blank">联系我们</a> － <a href="<%=request.getContextPath()%>/about/zhaopin.shtml" target="_blank">招聘信息</a> － <a href="<%=request.getContextPath()%>/about/copyright.shtml" target="_blank">版权声明</a> － <a href="<%=request.getContextPath()%>/ad/index.shtml" target="_blank">网站广告</a> － <a href="<%=request.getContextPath()%>/help/" target="_blank">帮助中心</a> － <a href="<%=request.getContextPath()%>/help/wzdh.jsp" target="_blank">网站导航</a>

 <br />
    主办单位：中国高等教育学生信息网　　　服务热线：010-82332257　　　客服邮箱：xlrz@moe.edu.cn
  <!-- end #footer --></div>
<!-- end #container --></div>
<script type="text/javascript">
function gotoIndex(){
	window.location.href="<%=request.getContextPath()%>/htgl/application/list.action";
}
</script>
</body>
</html>
