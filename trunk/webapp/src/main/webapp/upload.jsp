<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
String ctxPath = request.getContextPath();
%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>upload </title>
</head>
<body>
         <form action="<%=ctxPath %>/htgl/zxstranscriptUpload/upload.action" method="post" enctype="multipart/form-data"  >
                <input type="file"   name="upload" id="upload"/>
                <input type="submit"  value="上传"  />
              </form>
  

</body>
</html>