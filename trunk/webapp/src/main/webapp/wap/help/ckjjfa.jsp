<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.chsi.knowledge.pojo.*,com.chsi.knowledge.service.*,java.util.List" %>
<%@ page import="com.chsi.knowledge.Constants" %>
<%@ page import="com.chsi.knowledge.util.ManageCacheUtil" %>
<%@ page import="com.chsi.cms.client.*" %>
<%@ page import="com.chsi.news.vo.Article" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% 
String id = request.getParameter("id");
KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
KnowledgeData knowledgeData = knowledgeService.getKnowledgeWithArticleById(id);

//如果没访问过，向访问知识队列中插入ID
String visitedFlag = Constants.VISIT + id;
if (null == session.getAttribute(visitedFlag)) {
    session.setAttribute(visitedFlag, id);
    ServiceFactory.getQueueService().addVisitKnowledgeId(id);
}
String discussedFlag = Constants.DISCUSS + id;
boolean isDiscussed = null != session.getAttribute(discussedFlag);

KnowTagRelationService knowTagRelationService = ServiceFactory.getKnowTagRelationService();
List<KnowTagRelationData> list = knowTagRelationService.getKnowTagRelationByKnowId(id);
TagData tagData = list.get(0).getTagData();
list = ManageCacheUtil.getKnowTag(tagData.getId());
int otherNum = 4;
%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta charset="utf-8">
    <title>查看解决方案</title>
    <link href="../../css/wap/help/reset.css" rel="stylesheet"/>
    <link href="../../css/wap/help/style.css" rel="stylesheet"/>
    <!--[if lt IE 9]>
        <script src="../../js/wap/respond.js"></script>
      <![endif]-->
  </head>
  <body>
    <div id="wrap">
      <div class="section">
        <div class="search" style="border: none;">
                      <div>
                          <div class="sou"><img src="../../images/wap/help/search.png"/>遇到问题搜一搜</div>
                          <div class="text"></div>
                      </div>
              </div>
        <div class="findUser">
                  <h2><%=knowledgeData.getArticle().getTitle() %></h2>
                    <div class="cont">
                      <div class="article">
                          <%=knowledgeData.getArticle().getContent() %>
                      </div>
                  </div>
                  <%if(!isDiscussed){ %>
                  <div class="bot">
                      <h6><span class="before"></span>此解答是否有用<span class="after"></span></h6>
                      <div class="huifu">
                        <ul>
                            <li><button>有用</button></li>
                            <li class="none"><button>无用</button></li>
                        </ul>
                      </div>
                  </div>
                  <%} %>
              </div>
              <div class="may_que">
                  <h3>您可能遇到的问题</h3>
                  <ul class="hot_list">
                  <%int realnum = 0;
                  CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
                  for(int i=0;i<list.size()&&realnum<=otherNum;i++) {
                      KnowTagRelationData one = list.get(i);
                      KnowledgeData data = one.getKnowledgeData();
                      if(data.getId().equals(id)) continue;
                      Article article = cmsServiceClient.getArticle(data.getCmsId());
                      realnum++;
                  %>
                      <li<%if(i==list.size()-1 || realnum==otherNum) out.print(" class=\"last\"");%>><a href="/wap/help/ckjjfa.jsp?id=<%=data.getId() %>"><i><%=article.getTitle() %></i><span><img src="../../images/wap/help/more.png"/></span></a></li>
                   <%} %>
                  </ul>
              </div>
      </div>
      <div class="dialog" style="display:none">
                <div class="bot_bar">
                  <div class="title">
                    <div class="cancel">取消</div>
                    <h1>您觉得无用的原因</h1>
                    <div class="submit">提交</div>
                  </div>
                  <ul>
                    <li><input type="radio" name="sel"/>解决方案看不懂</li>
                    <li><input type="radio" name="sel"/>操作后不成功</li>
                    <li><input type="radio" name="sel"/>其他</li>
                  </ul>
                  <textarea>
                    谢谢您帮我们发现了新大陆，和小编说说呗~
                  </textarea>
                </div>
            </div>
    </div>
  </body>
</html>
