<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.chsi.search.client.*"%>
<%@ page import="com.chsi.search.client.vo.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.chsi.search.client.criteria.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<%
SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
String sysType = request.getParameter("sysType");
List<String> sysTypes = new ArrayList<String>();
sysTypes.add(sysType);
List<String> contactTypes = new ArrayList<String>();
contactTypes.add("0200");
try{
    String keyword = request.getParameter("keyword");
    if(keyword!=null) {
        ContactSearchCriteria c = ContactSearchCriteria.getInstance();
        c.setKeywords(keyword);
        c.setContactTypes(contactTypes);
        c.setSysTypes(sysTypes);
        c.setPageSize(10);
        com.chsi.framework.page.Page<String> pages = searchClient.getContactId(c);
        for(String contactId:pages.getList()) {
            out.println(String.format("<p>contactId:%s</p>", contactId));
        }
    	//out.println("=====searchClient.getOrgInfos(\"邢台\", orgTypes, \"dwdm\", 0, 20).getList().size()"+searchClient.getOrgInfos("邢台", orgTypes, "dwdm", 0, 20).getList().size());
    }else{%>
        <form action="/testSearchContact.jsp">
        关键字：<input type="text" name="keyword">
        系统类型：<input type="text" name="sysType">
        <input type="submit" value="查询">
        </form>
<%}
}catch(Exception ex){
	ex.printStackTrace();
}
/* try{
	out.println("=====searchClient.getOrgUserInfos(\"王\", orgTypes, \"dwdm\", 0, 100).getSize():"+searchClient.getOrgUserInfos("王", orgTypes, "dwdm", 0, 100).getSize());
}catch(Exception ex){
	ex.printStackTrace();
} */
%>
</body>
</html>