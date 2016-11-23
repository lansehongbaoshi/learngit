<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%> 

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>${kData.article.title}</title>
<style type="text/css">
/* CSS index */
body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, code, form, fieldset, legend, input, button, textarea, p, blockquote, th, td {
	margin: 0;
	padding: 0;
}

body,button,input,select,textarea {
    font:12px/1.5 '\5FAE\8F6F\96C5\9ED1',tahoma,arial,"Hiragino Sans GB",\5b8b\4f53;
}

body {
	background: #f3f7f8;
	color:#666;
}

a{ text-decoration:none;}
a:hover{ text-decoration:underline;}

img{ border:none;}

.paddTop10{ padding-top:10px;}
.paddTop15{ padding-top:15px;}
.paddTop20{ padding-top:20px;}

.marginTop10{ margin-top:10px;}
.marginTop15{ margin-top:15px;}
.marginTop20{ margin-top:20px;}

.font14{ font-size:14px;}
.artical_summer{ margin:5px 0px; text-indent:2em;}
.news-date{ color:#999; float:right;}


/*二级列表*/
.sub-head{ height:34px; line-height:34px; padding:0; }
.sub-head .ui-box-head-title{ font-size:16px; border-bottom:solid 2px #497D3B; border-left:none; padding:2px 10px;  height:30px; line-height:30px; }
.artical-s{ padding-left:10px;}
.ui-img-txt h3{ font-size:14px;}
/*faq详细*/
.kn-article-container{ margin-top: 15px; font-size: 12px;}
.kn-article-title{  border-bottom: solid 1px #ccc; }
.kn-article-title h2{ margin: 5px 10px; font-size: 14px;}

.kn-article-comments-tags{ padding: 5px 10px;}
.article-tags{ color: #848484;  font-size: 12px;}
.article-tags a{ color: #666;}

.kn-article-content{ padding: 5px 10px; color:#666666;}

.kn-article-content p {
    line-height: 24px;
    margin-bottom: 10px;
}
.kn-article-content h5 {
    font-size:16px;
    margin-bottom: 10px;
}
</style>
</head>

<body>

<div class="ui-box-head sub-head">
    <h2 class="ui-box-head-title">${kData.article.title}</h2>
  </div>
  
  <div class="ui-box-container">
 <div class="kn-article-container clearfix">
  <div class="kn-article-comments-tags"> <b>关键字：</b>${kData.keywords} &nbsp;&nbsp;<b>标签：</b>
   <s:iterator value="ktrDatas" id="ktr">
   <s:property value="#ktr.tagData.name"/>&nbsp;
   </s:iterator>
  </div>
  <div class="kn-article-content"> 
${kData.article.content}
 </div>
  
  
  
</div>
 </div>


</body>
</html>

 