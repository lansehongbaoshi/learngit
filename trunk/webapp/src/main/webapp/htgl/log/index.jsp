<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.chsi.framework.util.TimeUtil"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
Calendar cal = Calendar.getInstance();
cal.add(Calendar.DAY_OF_MONTH, +1);
String endDate = TimeUtil.getTime(cal, "yyyy-MM-dd");
cal.add(Calendar.DAY_OF_MONTH, -9);
String startDate = TimeUtil.getTime(cal, "yyyy-MM-dd");
%>
<!--breadcrumbs-->

<script src="/js/echarts.min.js"></script>
<link href="/assets/css/bootstrap-datetimepicker.min.css"
    rel="stylesheet" media="screen">
<script type="text/javascript" src="/assets/js/bootstrap.min.js"></script>
<script type="text/javascript"
    src="/assets/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript"
    src="/assets/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<style>
.zxx_text_overflow_1 {
    width: 27em;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    overflow: hidden;
}

.form_datetime {
    width: 140px;
}

.leftpart {
    width: 140px;
    float: left;
    margin-right: 10px;
}

.line-height-30 {
    line-height: 30px;
}

.margin-top-10 {
    margin-top: 10px;
}
</style>

<div class="breadcrumbs" id="breadcrumbs">
    <script type="text/javascript">
            try {
                ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) {}
        </script>
    <ul class="breadcrumb">
        <li><i class="ace-icon fa fa-home home-icon"></i> <a
            href="/htgl/index.action">首页</a></li>
        <li class="active">日志管理</li>
    </ul>
    <!-- /.breadcrumb -->

    <div class="nav-search" id="nav-search">
        <form class="form-search">
            <span class="input-icon"> <input type="text"
                placeholder="Search ..." class="nav-search-input"
                id="nav-search-input" autocomplete="off"> <i
                class="ace-icon fa fa-search nav-search-icon"></i>
            </span>
        </form>
    </div>
    <!-- /.nav-search -->
</div>


<div class="page-content">
    <div class="page-header">
        <h1>
            查询 <small> <i class="ace-icon fa fa-angle-double-right"></i>
                根据条件查询
            </small>
        </h1>
    </div>

    <div class="rows">
        <div id="date" class="col-xs-12" style="display:;">
            <div class="col-xs-2 leftpart">
                <span class="line-height-30">开始日期：</span>
                <div class="input-group date form_datetime a_v_time"
                    style="margin: 0;" data-date="" data-date-format="yyyy-mm-dd"
                    data-link-field="dtp_input1">
                    <input id="startDate1" name="startDate"
                        class="form-control for-height32" size="16" type="text"
                        value="<%=startDate%>" readonly> <span
                        class="input-group-addon"><span
                        class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
            <div class="col-xs-2 leftpart">
                <span class="line-height-30"> 截止日期：</span>
                <div class="input-group date form_datetime a_v_time"
                    style="margin: 0;" data-date="" data-date-format="yyyy-mm-dd"
                    data-link-field="dtp_input1">
                    <input id="endDate1" name="endDate"
                        class="form-control for-height32" size="16" type="text"
                        value="<%=endDate%>" readonly> <span
                        class="input-group-addon"><span
                        class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
            <div class="col-xs-8"></div>
            <div class="col-xs-1 col-md-1 input-group">
                <span class="line-height-30">&nbsp;&nbsp;</span>
                <div class="input-group">
                    <span class="input-group-btn">
                        <button type="button" id="searchBtn" class="btn btn-purple btn-sm">
                            <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                            搜索
                        </button>
                    </span>
                </div>
            </div>
        </div>


    </div>

    <div id="rows_content" class="rows" style="">

        <div class="col-xs-12">
            <div class="row">
                <div class="col-xs-12">

                    <h3 class="row header smaller lighter blue">
                        <span class="col-xs-7">知识点列表</span> <span class="col-xs-5"></span>
                    </h3>

                    <div class="table-header" id="search_table_header"
                        style="display: none">搜索 "Latest LogOper Domains" 的结果</div>

                    <!-- div.table-responsive -->

                    <!-- div.dataTables_borderWrap -->
                    <div>
                        <div id="dynamic-table_wrapper"
                            class="dataTables_wrapper form-inline no-footer">

                            <table id="dynamic-table"
                                class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable"
                                role="grid" aria-describedby="dynamic-table_info">
                                <thead>
                                    <tr role="row">
                                        <th width="200" class="hidden-80" tabindex="0"
                                            aria-controls="dynamic-table" rowspan="1" colspan="1"
                                            aria-label="Domain: activate to sort column ascending">操作时间</th>
                                        <th width="100" class="hidden-80" tabindex="1"
                                            aria-controls="dynamic-table" rowspan="1" colspan="1"
                                            aria-label="Domain: activate to sort column ascending">操作人</th>
                                        <th width="100" class="hidden-180" tabindex="1"
                                            aria-controls="dynamic-table" rowspan="2" colspan="1"
                                            aria-label="Domain: activate to sort column ascending">操作位置</th>
                                        <th width="100" class="hidden-200" tabindex="3"
                                            aria-controls="dynamic-table" rowspan="1" colspan="1">操作内容</th>
                                        <th width="120" class="sorting_disabled" rowspan="1"
                                            colspan="1" aria-label=""></th>
                                    </tr>
                                </thead>
                                <tbody id="search_result">
                                    <tr role="row" class="odd">

                                        <td colspan="5">无数据</td>

                                    </tr>

                                </tbody>
                            </table>
                            <div class="row" id="table_footer_info" style="display: none">
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
        </div>
    </div>
</div>

<script type="text/javascript">
$(".form_datetime").datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    minView: 2
});
    
function showSearchResult(startDate, endDate, curPage) {
    $.getJSON("/htgl/log/index/searchLogOper.action", {
    	startDate: startDate,
    	endDate: endDate,
        curPage: curPage,
        t: new Date().getTime()
        },
        function showResult(json) {
            if (json.flag == 'true') {
                $("#search_result").html("");
                $("#search_table_header").html("");
                $("#dynamic-table_info").html("");
                $("#dynamic-table_paginate").html("");
                $("#table_footer_info").hide();
                var logOpers = json.o.logOpers;
                if(logOpers.length<1){
                   $("#search_result").html(" <tr role=\"row\" class=\"odd\"\><td  colspan=\"5\"> 无数据 </td><\/tr>");
                    $("#search_table_header").html("搜索 \“"+$("#startDate1").val() +"\”至\“"+$("#endDate1").val()+"\” 的结果").show();
                    return;
                }
                var pagination = json.o.pagination;
                for (var i = 0; i < logOpers.length; i++) {
                    var logOper = logOpers[i];
                    var odd_even = (i%2==0)?"even":"odd";
                    
                    var str = " <tr role=\"row\" data-id="+logOper.id+" class=\""+odd_even+"\"><td class=\"hidden-80\" title=\""+logOper.createDate+"\">" + logOper.createDate + "</td><td class=\"hidden-80\">" + logOper.userId + "</td><td class=\"hidden-160\">" + logOper.m1 + "</td><td class=\"hidden-260\"><div class='zxx_text_overflow_1' title='"+ logOper.oper +"'>" + logOper.oper + "<div></td><td><div class=\"hidden-sm hidden-xs action-buttons\"></div></td></tr>";
                    
                    $("#search_result").append(str);
                }
                $("#search_table_header").html("搜索 \“"+$("#startDate1").val() +"\”至\“"+$("#endDate1").val()+"\” 的结果").show();
                console.log("总计：共 "+ pagination.totalCount +" 条，第"+(parseInt(curPage)+1)+"页。");
                $("#dynamic-table_info").html("总计：共 "+ pagination.totalCount +" 条，第"+(parseInt(curPage)+1)+"页。");
                $("#dynamic-table_paginate").html(formatP(pagination, startDate, endDate, curPage));
                $("#table_footer_info").show();
                //$("#rows_content").show();
            }
        }
    )
}

function formatP(pagination, startDate, endDate, curPage) {
    var curPage = pagination.curPage;
    var totalCount = pagination.totalCount;
    var pageCount = Math.ceil(totalCount / pagination.pageCount);
    console.log(curPage+"---"+pageCount);
    var prePage = "";
    var nextPage = "";
    if (curPage - 1 >= 0) {
        prePage = " <li class=\"paginate_button previous\" aria-controls=\"dynamic-table\" tabindex=\"0\" id=\"dynamic-table_previous\"><a href=\"javascript:void(0)\" onclick=\"showSearchResult('" + startDate + "','" + endDate + "','" + (curPage - 1) + "')\">上一页</a>";
    }

    if (curPage + 1 < pageCount) {
        nextPage = " <li class=\"paginate_button next\" aria-controls=\"dynamic-table\" tabindex=\"0\" id=\"dynamic-table_next\"><a href=\"javascript:void(0)\" onclick=\"showSearchResult('" + startDate + "','" + endDate + "','" + (curPage + 1) + "')\">下一页</a></li>";
    }
    var htmlStr = "<ul class=\"pagination\">" + prePage + nextPage + "</ul>";
    return htmlStr;
}

$(function () {
	
    showSearchResult($("#startDate1").val(), $("#endDate1").val(), 0); //初始化首页数据
    
    $("#searchBtn").click(function () {
        if($.trim($("#startDate1").val())==""){
            showSearchResult("", $("#endDate1").val(), 0);
        }else{
            showSearchResult($("#startDate1").val(), $("#endDate1").val(), 0);
        }
        
    });
    
})
    </script>