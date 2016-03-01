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
    <title>学信网帮助中心</title>
  </head>
  <body>
    <div id="wrap">
      <div class="section">
        <div class="question">
                  <s:include value="searchbox.jsp"></s:include>
                  <div class="hot">
                      <h2>热门问题</h2>
                      <ul class="hot_list">
                      <%for(int i=0;i<list.size();i++) {
                          KnowledgeData one = list.get(i);%>
                          <li<%if(i==(list.size()-1)) out.print(" class=\"last\"");%>>
	                          <a href="/wap/help/ckjjfa.jsp?id=<%=one.getId()%>">
	                          	<div>
		                          	<i><%=one.getArticle().getTitle() %></i>
		                          	<span><img src="http://t1.chei.com.cn/common/wap/help/images/more.png"/></span>
	                          	</div>
	                          </a>
                          </li>
                       <%} %>
                      </ul>
                  </div>
              </div>
              <div class="part">
                <span class="fg_line"></span>
                  <div class="first">
                      <dl>
                      	<a href="#">
                          <dt><img src="http://t2.chei.com.cn/common/wap/help/images/user.png"/></dt>
                          <dd>
                              <h3>账户问题</h3>
                              <p>忘记密码、身份证重复等</p>
                          </dd>
                        </a>
                      </dl>
                      <dl class="none">
                      	<a href="/wap/help/catalog.jsp?id=yz_wb">
                          <dt><img src="http://t3.chei.com.cn/common/wap/help/images/yanzhao.png"/></dt>
                          <dd>
                              <h3>研招网报</h3>
                              <p>打印准考证、报名资格等</p>
                          </dd>
                        </a>
                      </dl>
                  </div>
                  <div class="second">
                      <dl>
                      	<a href="/wap/help/catalog.jsp?id=zb">
                          <dt><img src="http://t4.chei.com.cn/common/wap/help/images/zhengbing.png"/></dt>
                          <dd>
                              <h3>征兵网</h3>
                              <p>常见问题、报名条件</p>
                          </dd>
                        </a>
                      </dl>
                      <dl class="none">
                      	<a href="#">
                          <dt><img src="http://t1.chei.com.cn/common/wap/help/images/dangan.png"/></dt>
                          <dd>
                              <h3>学信档案</h3>
                              <p>图片校对、高等教育等</p>
                          </dd>
                        </a>
                      </dl>
                  </div>
              </div>
      </div>
    </div>
  </body>
</html>
