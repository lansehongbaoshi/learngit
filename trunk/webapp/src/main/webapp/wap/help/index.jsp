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
                      <h2 class='hot_title'>热门问题</h2>
                      <div class='hot_more'>更多>></div>
                      <ul class="hot_list"> 
                      <%for(int i=0;i<list.size();i++) {
                          KnowledgeData one = list.get(i);%>
                          <li<%if(i==(list.size()-1)) out.print(" class=\"last\"");%>>
	                          <span class='hl_title' onClick="window.location.href='###'">【学信网账号】</span>
                              <a class='hl_a' href="/wap/help/ckjjfa.jsp?id=<%=one.getId()%>">
	                          	<%=one.getArticle().getTitle() %>
		                        <img class='hl_next_img' src="http://t1.chei.com.cn/common/wap/help/images/more.png"/>
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
                      	<a href="/wap/help/catalog.jsp?id=account">
                          <dt><img src="http://t2.chei.com.cn/common/wap/help/images/user.png"/></dt>
                          <dd>
                              <h3>学信网账号</h3>
                              <p>忘记密码、身份证重复等</p>
                          </dd>
                        </a>
                      </dl>
                      <dl class="none">
                      	<a href="/wap/help/catalog.jsp?id=yz_wb">
                          <dt><img src="http://t3.chei.com.cn/common/wap/help/images/yanzhao.png"/></dt>
                          <dd>
                              <h3>研招</h3>
                              <p>网上报名、调剂、推免等</p>
                          </dd>
                        </a>
                      </dl>
                  </div>
                  <div class="second">
                      <dl>
                      	<a href="/wap/help/catalog.jsp?id=zb">
                          <dt><img src="http://t4.chei.com.cn/common/wap/help/images/zhengbing.png"/></dt>
                          <dd>
                              <h3>应征报名</h3>
                              <p>兵役登记、应征报名等</p>
                          </dd>
                        </a>
                      </dl>
                      <dl class="none">
                      	<a href="/wap/help/catalog.jsp?id=my">
                          <dt><img src="http://t1.chei.com.cn/common/wap/help/images/dangan.png"/></dt>
                          <dd>
                              <h3>学信档案</h3>
                              <p>学籍学历、图像校对等</p>
                          </dd>
                        </a>
                      </dl>
                  </div>
              </div>
      </div>
    </div>
  </body>
</html>
