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
			共参与
			<s:property value="discussCountVO.sum" />
			人<br /> 认为有帮助：
			<div class="Bar">
				<div
					style="width:  <s:property value='discussCountVO.usefulPersent'/>%;"></div>
			</div>
			<s:property value="discussCountVO.usefulPersent" />
			% （
			<s:property value="discussCountVO.useful" />
			人）<br /> 认为无帮助：
			<div class="Bar">
				<div
					style="width:  <s:property value='discussCountVO.unusefulPersent'/>%;"></div>
			</div>
			<s:property value="discussCountVO.unusefulPersent" />
			% （
			<s:property value="discussCountVO.unuseful" />
			人）<br />
			<div id="discuss-content">
				评论
				<table>
					<tr>
						<th>姓名</th>
						<th>内容</th>
						<th>时间</th>
					</tr>
					<tbody id="search_result">
						<s:iterator value="contentList.knows" id="o">
							<tr>
								<td><s:property value="#o.userName" /></td>
								<td><s:property value="#o.content" /></td>
								<td><s:property value="#o.time" /></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<div class="row" id="table_footer_info">
					<div class="col-xs-6">
						<div class="dataTables_info" id="dynamic-table_info" role="status"
							aria-live="polite"></div>
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
                    var str = " <tr><td>"+k.userName+"</td><td>"+k.content+"</td><td>"+k.time+"</td></tr>";
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
