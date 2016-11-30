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
try{
    String keyword = request.getParameter("keyword");
    if(keyword!=null) {
        List<OrgInfoVO> orgs = searchClient.getOrgInfos(keyword, orgTypes, "", 0, 100).getList();
        for(OrgInfoVO vo:orgs) {
            out.println(String.format("<p>dwdm:%s,dwmc:%s,orgType:%s,orgTagIds:%s</p>",vo.getDwdm(),vo.getDwmc(),vo.getOrgType(),Arrays.toString(vo.getTagIds().toArray())));
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