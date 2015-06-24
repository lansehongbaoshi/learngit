<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.chsi.knowledge.service.ServiceFactory"%>
<%@ page import="com.chsi.knowledge.service.KnowledgeService"%>
<%@ page import="com.chsi.knowledge.pojo.KnowledgeData"%>
<%
KnowledgeService knowledgeService=ServiceFactory.getKnowledgeService();
KnowledgeData know = knowledgeService.getKnowledgeById("iaz6cx50bi6zpnh4"); //siDZtpVmP7nyfjaM 测试平台ID
knowledgeService.update(know, "应征年龄说明", 
  "<p> <strong>年龄的计算：</strong>年龄 = 报名年份 - 出生年份。 </p>  <p> <strong>男兵报名年龄</strong><br> a.研究生：17至24周岁（1991.1.1-1998.12.31 出生的）；<br> b.一本、二本、三本：<br> i. 在校生、毕业班生：17 至 22 周岁（1993.1.1-1998.12.31 出生的）；<br>ii. 应届毕业生、往届毕业生：17 至 24 周岁（1991.1.1-1998.12.31 出生的）；<br>c.专科：<br>i. 在校生、毕业班生：17至22 周岁（1993.1.1-1998.12.31 出生的）；<br>ii. 应届毕业生、往届毕业生：17 至 24 周岁（1991.1.1-1998.12.31 出生的）；<br>d.高校新生：17 至 22 周岁（1993.1.1-1998.12.31 出生的）；<br>e.普通高中、中等职业： <br>i.应届毕业生、往届毕业生：17 至 22 周岁（1993.1.1-1998.12.31 出生的）；<br>ii.其他： 17 至 22 周岁（1993.1.1-1998.12.31 出生的）；<br> f.初中：18 至 20 周岁（1995.1.1-1997.12.31 出生的）。 </p> <p><strong>女兵报名年龄</strong><br> a. 研究生：17至22 周岁（1993.1.1-1998.12.31 出生的）；<br>b. 一本、二本、三本、专科：17至22 周岁（1993.1.1-1998.12.31 出生的）；<br> c. 高校新生、普通高中：17 至 22 周岁 （1993.1.1-1998.12.31 出生的）。<br> </p>", "16a669296a704688b2cbf38fef310811");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
</body>
</html>