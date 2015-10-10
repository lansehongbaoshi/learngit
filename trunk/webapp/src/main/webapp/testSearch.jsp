<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.chsi.search.client.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<%
SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
List<String> orgTypes = new ArrayList<String>();
orgTypes.add("40000");
orgTypes.add("49000");
orgTypes.add("41000");
orgTypes.add("45000");
orgTypes.add("47000");
try{
    out.println("=====searchClient.getOrgInfos(\"*:*\", orgTypes, \"\", 0, 20).getPageCount()"+searchClient.getOrgInfos("*:*", orgTypes, "", 0, 20).getPageCount());
    out.println("=====searchClient.getOrgInfos(\"邢台\", orgTypes, \"dwdm\", 0, 20).getList().size()"+searchClient.getOrgInfos("邢台", orgTypes, "dwdm", 0, 20).getList().size());
}catch(Exception ex){
	ex.printStackTrace();
}
try{
	out.println("=====searchClient.getOrgUserInfos(\"王\", orgTypes, \"dwdm\", 0, 100).getSize():"+searchClient.getOrgUserInfos("王", orgTypes, "dwdm", 0, 100).getSize());
}catch(Exception ex){
	ex.printStackTrace();
}
%>
</body>
</html>