<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.chsi.knowledge.pojo.*,com.chsi.knowledge.util.ManageCacheUtil,java.util.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
List<KnowledgeData> list = ManageCacheUtil.getIndexTopKnowl(5);
%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <meta charset="utf-8">
        <title>学信网帮助中心</title>
        <meta name="Keywords" content="学信网帮助中心，学信网常见问题">
        <meta name="Description" content="学信网帮助中心，学信网账号、学信档案、征兵报名、四六级查分、研招统考、研招推免、学历与成绩认证、面向港澳台招生、高考统考招生、高考特殊类型招生、高考特殊类型报名">
    </head>
    <body>
        <img src='http://t1.chei.com.cn/common/wap/images/share_help.jpg' width='0' height='0' style='position: absolute;top:-100px;left: 0' />
        <div id="wrap">
            <div class="section">
                <div class="question">
                    <s:include value="searchbox.jsp"></s:include>
                    <div class="hot">
                        <h2 class='hot_title'>热门问题</h2>
                        <div class='hot_more' onClick="window.location.href='/wap/help/hotmore.jsp'">更多>></div>
                        <ul class="hot_list"> 
                        <%for(int i=0;i<list.size();i++) {
                            KnowledgeData one = list.get(i);%>
                            <li<%if(i==(list.size()-1)) out.print(" class=\"last\"");%>>
                                <a class='hl_a' href="/wap/help/ckjjfa.jsp?id=<%=one.getId()%>">[<span title="<%=one.getSystemNames() %>"><%=one.getSystemName() %></span>]<%=one.getArticle().getTitle() %>
                                    <img class='hl_next_img' src="http://t1.chei.com.cn/common/wap/help/images/more.png" />
                                </a>
                            </li>
                        <%} %>
                        </ul>
                    </div>
                </div>
                <div class="part">
                    <span class="fg_line"></span>
                    <div>
                        <dl><%SystemData systemData = ManageCacheUtil.getSystem("account"); %>
                            <a href="/wap/help/catalog.jsp?id=account">
                                <dt><img src="http://t2.chei.com.cn/common/wap/help/images/icon_zh.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl>
                        <dl class="none"><%systemData = ManageCacheUtil.getSystem("my"); %>
                            <a href="/wap/help/catalog.jsp?id=my">
                                <dt><img src="http://t3.chei.com.cn/common/wap/help/images/icon_xxda.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl>             
                    </div>  
                    <div>
                        <dl><%systemData = ManageCacheUtil.getSystem("zb"); %>
                            <a href="/wap/help/catalog.jsp?id=zb">
                                <dt><img src="http://t1.chei.com.cn/common/wap/help/images/icon_zb.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl>
                        <dl class="none"><%systemData = ManageCacheUtil.getSystem("cet"); %>
                            <a href="/wap/help/catalog.jsp?id=cet">
                                <dt><img src="http://t3.chei.com.cn/common/wap/help/images/icon_cet.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl>                           
                    </div>                      
                    <div>
                        <dl><%systemData = ManageCacheUtil.getSystem("yz_wb"); %>
                            <a href="/wap/help/catalog.jsp?id=yz_wb">
                                <dt><img src="http://t4.chei.com.cn/common/wap/help/images/icon_yz.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl>       
                        <dl class="none"><%systemData = ManageCacheUtil.getSystem("yz_tm"); %>
                            <a href="/wap/help/catalog.jsp?id=yz_tm">
                                <dt><img src="http://t4.chei.com.cn/common/wap/help/images/icon_yz.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl>  
                                              
                    </div> 
                    <div>
                        <dl><%systemData = ManageCacheUtil.getSystem("xlrz"); %>
                            <a href="/wap/help/catalog.jsp?id=xlrz">
                                <dt><img src="http://t4.chei.com.cn/common/wap/help/images/icon_xlrz.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl> 
                        <dl class="none"><%systemData = ManageCacheUtil.getSystem("yz_gat"); %>
                            <a href="/wap/help/catalog.jsp?id=yz_gat">
                                <dt><img src="http://t4.chei.com.cn/common/wap/help/images/icon_gatzs.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl>              
                    </div>
                    <div>
                        <dl><%systemData = ManageCacheUtil.getSystem("gk_tk"); %>
                            <a href="/wap/help/catalog.jsp?id=gk_tk">
                                <dt><img src="http://t1.chei.com.cn/common/wap/help/images/icon_gk.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl>     
                        <dl class="none"><%systemData = ManageCacheUtil.getSystem("gk_ts"); %>
                            <a href="/wap/help/catalog.jsp?id=gk_ts">
                                <dt><img src="http://t1.chei.com.cn/common/wap/help/images/icon_gk.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl>      
                    </div> 
                    <div class="last">
                        <dl><%systemData = ManageCacheUtil.getSystem("gk_wb"); %>
                            <a href="/wap/help/catalog.jsp?id=gk_wb">
                                <dt><img src="http://t1.chei.com.cn/common/wap/help/images/icon_gk.png" title="<%=systemData.getName() %>" alt="<%=systemData.getName() %>"/></dt>
                                <dd>
                                    <h3><%=systemData.getName() %></h3>
                                    <p><%=systemData.getDescription() %></p>
                                </dd>
                            </a>
                        </dl>    
                    </div> 
                </div>
            </div>
        </div>      
    </body>
</html>
