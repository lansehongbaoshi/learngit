<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.chsi.knowledge.pojo.KnowledgeData,com.chsi.knowledge.util.ManageCacheUtil,java.util.List" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
List<KnowledgeData> list = ManageCacheUtil.getTopKnowl();
%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta charset="utf-8">
    <title>帮助</title>
    <link href="../../css/wap/help/reset.css" rel="stylesheet"/>
    <link href="../../css/wap/help/style.css" rel="stylesheet"/>
    <!--[if lt IE 9]>
        <script src="../../js/wap/respond.js"></script>
      <![endif]-->
  </head>
  <body>
    <div id="wrap">
      <div class="section">
        <div class="question">
                  <div class="search">
                      <div>
                          <div class="sou"><img src="../../images/wap/help/search.png"/>遇到问题搜一搜</div>
                          <div class="text"></div>
                      </div>
                  </div>
                  <div class="hot">
                      <h2>热门问题</h2>
                      <ul class="hot_list">
                      <%for(int i=0;i<list.size();i++) {
                          KnowledgeData one = list.get(i);%>
                          <li<%if(i==(list.size()-1)) out.print(" class=\"last\"");%>><a href="/wap/help/ckjjfa.jsp?id=<%=one.getId()%>"><i><%=one.getArticle().getTitle() %></i><span><img src="../../images/wap/help/more.png"/></span></a></li>
                       <%} %>
                      </ul>
                  </div>
              </div>
              <div class="part">
                <span class="fg_line"></span>
                  <div class="first">
                      <dl>
                          <dt><img src="../../images/wap/help/user.png"/></dt>
                          <dd>
                              <h3>账户问题</h3>
                              <p>用户名密码、身份证重复等</p>
                          </dd>
                      </dl>
                      <dl class="none">
                          <dt><a href="/wap/help/catalog.jsp?id=yz_wb"><img src="../../images/wap/help/yanzhao.png"/></a></dt>
                          <dd>
                              <h3>研招网报</h3>
                              <p>打印准考证、报名资格等</p>
                          </dd>
                      </dl>
                  </div>
                  <div class="second">
                      <dl>
                          <dt><a href="/wap/help/catalog.jsp?id=zb"><img src="../../images/wap/help/zhengbing.png"/></a></dt>
                          <dd>
                              <h3>征兵网</h3>
                              <p>常见问题、报名条件</p>
                          </dd>
                      </dl>
                      <dl class="none">
                          <dt><img src="../../images/wap/help/dangan.png"/></dt>
                          <dd>
                              <h3>学信档案</h3>
                              <p>图片校对、高等教育等</p>
                          </dd>
                      </dl>
                  </div>
              </div>
      </div>
      <!--<div class="footer">
        <ul>
          <li>
            <dl>
              <dt><img src="../../images/wap/help/footer01.png"/></dt>
              <dd>学信网</dd>
            </dl>
          </li>
          <li>
            <dl>
              <dt><img src="../../images/wap/help/footer02.png"/></dt>
              <dd>新闻</dd>
            </dl>
          </li>
          <li>
            <dl>
              <dt><img src="../../images/wap/help/footer03_1.png"/></dt>
              <dd>消息中心</dd>
            </dl>
          </li>
          <li>
            <dl>
              <dt><img src="../../images/wap/help/footer04.png"/></dt>
              <dd>个人中心</dd>
            </dl>
          </li>
        </ul>
      </div>-->
    </div>
  </body>
</html>
