<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.chsi.search.client.*"%>
<%@ page import="com.chsi.search.client.vo.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<%
SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
String orgType = request.getParameter("orgType");
List<String> orgTypes = new ArrayList<String>();
if(orgType!=null) {
    orgTypes = Arrays.asList(orgType.split(","));
}
/* orgTypes.add("40000");
orgTypes.add("49000");
orgTypes.add("41000");
orgTypes.add("45000");
orgTypes.add("47000"); */
String orderBy = request.getParameter("orderBy");
try{
    String keyword = request.getParameter("keyword");
    if(keyword!=null) {
        List<OrgUserInfoVO> orgUsers = searchClient.getOrgUserInfos(keyword, orgTypes, orderBy!=null?orderBy:"", 0, 100).getList();
        for(OrgUserInfoVO vo:orgUsers) {
            out.println(String.format("<p>xm:%s,dwdm:%s,dwmc:%s,userType:%s,zwlx:%s</p>",vo.getXm(),vo.getDwdm(),vo.getDwmc(),vo.getUserType(),vo.getZwlx()));
        }
    	//out.println("=====searchClient.getOrgInfos(\"邢台\", orgTypes, \"dwdm\", 0, 20).getList().size()"+searchClient.getOrgInfos("邢台", orgTypes, "dwdm", 0, 20).getList().size());
    }else{%>
        <form action="/testSearch.jsp">
        关键字：<input type="text" name="keyword">
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