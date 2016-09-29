<%--
  Created by IntelliJ IDEA.
  User: Li_Jian
  Date: 2015/10/27
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>公共权限管理</title>
</head>
<body>
    <form action="initRole.jsp" method="post">
    <select name="role">
    <option value="ROLE_KNOWLEDGE">管理权限</option>
    <option value="ROLE_KNOWLEDGE_READONLY">只读权限</option>
    <option value="ROLE_KNOWLEDGE_ADMIN">系统权限</option>
    </select>
        单点登录账号:<input type="text" name="loginName"/>
        <input type="radio" name="type" value="yes" checked>授权&nbsp;&nbsp;
        <input type="radio" name="type" value="no">取消授权&nbsp;&nbsp;
        <input type="submit"  name="" value="提交">
    </form>
</body>
</html>
