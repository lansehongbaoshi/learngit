<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String knowledgeId = request.getParameter("kId");
%>
<div class="widget-box widget-color-grey" style="border: none;">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">用户评价</h4>
    </div>

    <div class="widget-body">
        <div class="widget-main padding-8">
            <table>
                <tbody>
                    <tr><td rowspan="2" style="padding:5px 10px;"> 
                    共参与 <s:property value="discussCountVO.sum" /> 人
                    </td>
                        <td style="padding:5px 10px;">
                        <i class="ace-icon fa fa-thumbs-o-up blue bigger-130"></i> 认为有帮助：</td>
                        <td width="150">
                <div class="progress  progress-mini" style="margin:0;">
                    <div class="progress-bar"  style="width:  <s:property value='discussCountVO.usefulPersent'/>%;"></div>
                </div>
                        </td>
                        <td style="padding:5px 10px;">  
                    <strong class="blue">
                        <s:property value="discussCountVO.usefulPersent" />
                % 
                    </strong>         
                （
                <s:property value="discussCountVO.useful" />
                人）
                        </td>
                      
                    </tr>
                    <tr>
                        <td style="padding:5px 10px;">
                         <i class="ace-icon fa fa-thumbs-o-down red bigger-130"></i> 认为无帮助：
                        </td>
                        <td width="150">
                <div class="progress  progress-mini" style="margin:0;">
                    <div class="progress-bar progress-bar-danger" style="height:15px; width:  <s:property value='discussCountVO.unusefulPersent'/>%;"></div>
                </div>
                        </td>
                        <td style="padding:5px 10px;">
                        <strong class="red">
                                    
                <s:property value="discussCountVO.unusefulPersent" />
                % （
                        </strong>
                <s:property value="discussCountVO.unuseful" />
                人）
                        </td>
                    </tr>
                </tbody>
            </table>

                      


            <div id="discuss-content" style="border-top:solid 2px #478fca; margin: 10px 15px; padding: 10px; font-size: 14px;">
	            <div id="search_result">
		            <s:iterator value="contentList.knows" id="o">
		            
						<div class="itemdiv commentdiv">
						    <div class="user">
						        <img alt="Joe's Avatar" src="/assets/avatars/avatar2.png">
						    </div>
						
						    <div class="body">
						        <div class="name">
						            <s:property value="#o.userName" />
						        </div>
						    
						        <div class="time">
						            <i class="ace-icon fa fa-clock-o"></i>
						            <span class="blue"><s:property value="#o.time" /></span>
						        </div>
						    
						        <div class="text">
						            <i class="ace-icon fa fa-quote-left"></i>
						            <s:property value="#o.content" />
						        </div>
						    </div>
						
						    <div class="tools" style="display:none">
						        <div class="action-buttons bigger-125">
						            <a href="#">
						                <i class="ace-icon fa fa-pencil blue"></i>
						            </a>
						        
						            <a href="#">
						                <i class="ace-icon fa fa-trash-o red"></i>
						            </a>
						        </div>
						    </div>
						</div>
		            </s:iterator>           
	            </div>
                <div class="row" id="table_footer_info">
                    <div class="col-xs-6">
                        <div class="dataTables_info" id="dynamic-table_info"
                            role="status" aria-live="polite"></div>
                    </div>
                    <div class="col-xs-6">
                        <div class="dataTables_paginate paging_simple_numbers"
                            id="dynamic-table_paginate"></div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

$(function(){
	console.log("知识的kId：<%=knowledgeId%>");
	showSearchResult("<%=knowledgeId%>",0);
});

function showSearchResult(KId,curPage) {
    $.getJSON("/common/showDiscussContent.action", {
            id:KId,
            curPage: curPage
        },
        function showSearchResult(json) {
            if (json.flag == 'true') {
                $("#search_result").html("");
                var knows = json.o.knows;
                var pagination = json.o.pagination;
                for (var i = 0; i < knows.length; i++) {
                    var k = knows[i];
                    var str = "<div class=\"itemdiv commentdiv\"><div class=\"user\"><img alt=\"Joe's Avatar\" src=\"/assets/avatars/avatar2.png\"></div><div class=\"body\"><div class=\"name\">"+k.userName+"</div><div class=\"time\"><i class=\"ace-icon fa fa-clock-o\"></i><span class=\"blue\">"+k.time+"</span></div><div class=\"text\"><i class=\"ace-icon fa fa-quote-left\"></i>"+k.content+"</div></div><div class=\"tools\" style=\"display:none\"><div class=\"action-buttons bigger-125\"><a href=\"#\"><i class=\"ace-icon fa fa-pencil blue\"></i></a><a href=\"#\"><i class=\"ace-icon fa fa-trash-o red\"></i></a></div></div></div>";
                    $("#search_result").append(str);
                }
                $("#dynamic-table_info").html("第"+(parseInt(pagination.curPage)+1)+"页，共"+ pagination.totalCount +" 条。");
                $("#dynamic-table_paginate").html(formatP(KId, pagination, curPage));
                $("#table_footer_info").show();
            }
        }
    )
}

function formatP(KId, pagination, curPage) {
    var curPage = pagination.curPage;
    var totalCount = pagination.totalCount;
    var pageCount = Math.ceil(totalCount / pagination.pageCount);
    var prePage = "";
    var nextPage = "";
    if (curPage - 1 >= 0) {
        prePage = " <li class=\"paginate_button previous\" aria-controls=\"dynamic-table\" tabindex=\"0\" id=\"dynamic-table_previous\"><a href=\"javascript:void(0)\" onclick=\"showSearchResult('"+KId+"','" + (curPage - 1) + "')\">上一页</a>";
    }

    if (curPage + 1 < pageCount) {
        nextPage = " <li class=\"paginate_button next\" aria-controls=\"dynamic-table\" tabindex=\"0\" id=\"dynamic-table_next\"><a href=\"javascript:void(0)\" onclick=\"showSearchResult('" +KId+"','" + (curPage + 1) + "')\">下一页</a></li>";
    }
    var htmlStr = "<ul class=\"pagination\">" + prePage + nextPage + "</ul>";
    return htmlStr;
}

</script>
