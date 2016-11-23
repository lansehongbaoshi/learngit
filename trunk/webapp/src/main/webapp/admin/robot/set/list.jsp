<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<style>
.zxx_text_overflow_1 {
    width: 27em;
    white-space: nowrap;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    overflow: hidden;
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
            href="/admin/index.action">首页</a></li>
        <li class="active">机器人配置</li>
    </ul>
    <!-- /.breadcrumb -->
</div>
<div class="page-content">
    <div class="rows">
        <div class="col-xs-12">
            <h3 class="row header smaller lighter orange">
                机器人基本设置 <small> <i class="ace-icon fa fa-angle-double-right"></i>
                    机器人开场白等设置
                </small>
            </h3>
            <div class="dataTables_wrapper form-inline no-footer">
                <table aria-describedby="dynamic-table_info" role="grid"
                    class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable">
                    <thead>
                        <tr role="row">
                            <th width="340" colspan="1" rowspan="1"
                                aria-controls="dynamic-table" tabindex="0" class="hidden-200">设置项</th>
                            <th width="340" colspan="1" rowspan="1"
                                aria-controls="dynamic-table" tabindex="0" class="hidden-200">机器人回答</th>
                            <th aria-label="" colspan="1" rowspan="1"
                                class="sorting_disabled"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="odd" role="row">
                            <td class="hidden-200"><strong>开场白</strong> (#hello) <br>
                                <small>说明：使用机器人的用户首先会立即随机收到此条开场白内容</small></td>
                            <td class="hidden-200" id="hello">
                                <%--               <s:iterator value="qaSet" id="entry"> --%> <%--               <s:if test="key.q=='#hello'"> --%>
                                <%--               <s:set var="id1" value="key.id"/> --%> <%--               <s:iterator value="value" var="a" status="index"> --%>
                                <%--                     <s:property value="#index.index+1"/> --%>
                                <!--                     . --> <%--                     <s:property value="#a.a"/> --%>
                                <!--                     <br> --> <%--                   </s:iterator> --%>
                                <%--               </s:if> --%> <%--               </s:iterator> --%>
                            </td>
                            <%--               <td class="hidden-260"><div data-id="q95cfjzkvuzhuuw6" class="hidden-sm hidden-xs action-buttons"> <a href="/admin/robot/set/updateIndex.action?id=<s:property value="#id1"/>" target="_self" title="修改" class="green"><i class="ace-icon fa fa-pencil bigger-130"></i> </a> <a href="javascript:void(0)" class="red delBtn" title="删除"> <i class="ace-icon fa fa-trash-o bigger-130"></i> </a> </div></td> --%>
                        </tr>
                        <tr class="odd" role="row">
                            <td class="hidden-200"><strong>机器人应急回答</strong>（#noanswer）<br>
                                <small>说明：当机器人回答不上时将按此内容回复。</small></td>
                            <td class="hidden-200" id="noanswer">
                                <%--               <s:iterator value="qaSet" id="entry"> --%> <%--               <s:if test="key.q=='#noanswer'"> --%>
                                <%--               <s:set var="id2" value="key.id"/> --%> <%--               <s:iterator value="value" var="a" status="index"> --%>
                                <%--                     <s:property value="#index.index+1"/> --%>
                                <!--                     . --> <%--                     <s:property value="#a.a"/> --%>
                                <!--                     <br> --> <%--                   </s:iterator> --%>
                                <%--               </s:if> --%> <%--               </s:iterator> --%>
                            </td>
                            <%--               <td class="hidden-260"><div data-id="65rsrxlmzka9izh8" class="hidden-sm hidden-xs action-buttons"> <a href="/admin/robot/set/updateIndex.action?id=<s:property value="#id2"/>" target="_self" title="修改" class="green"><i class="ace-icon fa fa-pencil bigger-130"></i> </a> <a href="javascript:void(0)" class="red delBtn" title="删除"> <i class="ace-icon fa fa-trash-o bigger-130"></i> </a> </div></td> --%>
                        </tr>
                        <tr class="odd" role="row">
                            <td class="hidden-200"><strong>用户输入无有效文字时机器人回答</strong>（#blank）<br>
                                <small>说明：当用户输入的文字被过滤后是空白时机器人的答复。</small></td>
                            <td class="hidden-200" id="blank">
                                <%--               <s:iterator value="qaSet" id="entry"> --%> <%--               <s:if test="key.q=='#blank'"> --%>
                                <%--               <s:set var="id2" value="key.id"/> --%> <%--               <s:iterator value="value" var="a" status="index"> --%>
                                <%--                     <s:property value="#index.index+1"/> --%>
                                <!--                     . --> <%--                     <s:property value="#a.a"/> --%>
                                <!--                     <br> --> <%--                   </s:iterator> --%>
                                <%--               </s:if> --%> <%--               </s:iterator> --%>
                            </td>
                            <%--               <td class="hidden-260"><div data-id="65rsrxlmzka9izh8" class="hidden-sm hidden-xs action-buttons"> <a href="/admin/robot/set/updateIndex.action?id=<s:property value="#id2"/>" target="_self" title="修改" class="green"><i class="ace-icon fa fa-pencil bigger-130"></i> </a> <a href="javascript:void(0)" class="red delBtn" title="删除"> <i class="ace-icon fa fa-trash-o bigger-130"></i> </a> </div></td> --%>
                        </tr>
                </table>
            </div>
            <div class="rows" >
            <h3 class="row header smaller lighter blue">
                <div class="col-xs-3">常用语对话配置</div> <div class="col-xs-9">
                <div class="col-xs-11">
                    <div class="input-group">
                        <input id="keywords" type="text" class="form-control search-query"
                            placeholder="请输入用户提问、机器人回答搜索配置" name="keywords" /> <span
                            class="input-group-btn">
                            <button type="button" id="searchBtn"
                                class="btn btn-purple btn-sm">
                                <span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
                                搜索
                            </button>
                        </span>
                    </div>
                </div>
                <div class="col-xs-1"><a
                        href="/admin/robot/set/addIndex.action" target="_self"
                        class="btn-new-mail pull-right"> <span
                            class="btn btn-primary btn-xs no-border"> <i
                                class="ace-icon fa glyphicon-plus bigger-130"></i> <span
                                class="bigger-110">新增</span></span>
                    </a> </div></div>
            </h3>
            </div>
            <div class="table-header" id="search_table_header" style="display:">
                搜索 "*:*" 的结果</div>
            <div id="dynamic-table_wrapper"
                class="dataTables_wrapper form-inline no-footer">
                <table id="dynamic-table"
                    class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable"
                    role="grid" aria-describedby="dynamic-table_info">
                    <thead>
                        <tr role="row">
                            <th width="340" class="hidden-200" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1">用户提问</th>
                            <th width="340" class="hidden-200" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1">机器人回答</th>
                            <th width="100" class="hidden-200" tabindex="0"
                                aria-controls="dynamic-table" rowspan="1" colspan="1">分词阈值</th>
                            <th class="sorting_disabled" rowspan="1" colspan="1"
                                aria-label=""></th>
                        </tr>
                    </thead>
                    <tbody id="search_result">
                        <tr role="row" class="odd">

                            <td colspan="5">无数据</td>

                        </tr>

                    </tbody>
                </table>
                <div class="row" id="table_footer_info" style="display:">
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
            <%--col-xs-12--%>
        </div>
    </div>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
    aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                    aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">添加标签</h4>
            </div>
            <div class="modal-body">在这里添加一些文本</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary save">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!-- /.modal -->

<script type="text/javascript">
function synchronizationIndex(){
    $.getJSON("/admin/robot/set/synchronizationIndex.action");
}

function showSearchResult(text,type,curPage){
    $.getJSON("/admin/robot/set/listDate.action", {
        text: text,
        type: "question",
        curPage: curPage,
        callback:"",
        t: new Date().getTime()
    },function showSearchResult(json) {
        if (json.flag == 'true') {
            $("#search_result").html("");
            $("#search_table_header").html("");
            $("#dynamic-table_paginate").html("");
            $("#table_footer_info").hide();
            var robotBeans = json.o.robotBean;
            if(robotBeans.length<1){
               $("#search_result").html(" <tr role=\"row\" class=\"odd\"\><td  colspan=\"5\"> 无数据 </td><\/tr>");
                return;
            }
            var pagination = json.o.pagination;
            for (var i = 0; i < robotBeans.length; i++) {
                var k = robotBeans[i];
                var odd_even = (i%2==0)?"even":"odd";
                var str = "";
                str += "<tr role='row' class='"+odd_even+"'>";
                str += "<td class='hidden-200'>"+k.q+"</td>";
                str += "<td class='hidden-200'><ol>";
                   for(var j = 0;j<k.a.length;j++){
                       str += "<li><p class='zxx_text_overflow_1' title='"+k.a[j]+"'>"+k.a[j]+"</p></li>";
                   }
                   str += "</ol></td>";
                   str += "<td class='hidden-100'>"+k.num+"</td>";
                   str += "<td class='hidden-260'><div class='hidden-sm hidden-xs action-buttons' data-id="+k.id+"> <a class='green' title='修改' target='_self' href='/admin/robot/set/updateIndex.action?id="+k.id+"'><i class='ace-icon fa fa-pencil bigger-130'></i> </a> <a title='删除' class='red delBtn' href='javascript:void(0)'> <i class='ace-icon fa fa-trash-o bigger-130'></i> </a> </div></td>";
                str += "</tr>";
                   $("#search_result").append(str);
            }
            $("#dynamic-table_paginate").html(formatP(pagination, text, "question", curPage));
            var query = $.trim(text);
            if(query==""||query=="null"||query==null){
                query = "*:*";
            }
            $("#search_table_header").html("搜索 \“"+ query +"\” 的结果").show();
            $("#dynamic-table_info").html("第"+curPage+"页，总计：共 "+ pagination.totalCount +" 条。");
            $("#table_footer_info").show();
            //$("#rows_content").show();
        }
    })
}

function formatP(pagination, text, type, curPage) {
    var curPage = pagination.curPage;
    var totalCount = pagination.totalCount;
    var pageCount = Math.ceil(totalCount / pagination.pageCount);
    var prePage = "";
    var nextPage = "";
    if (curPage - 1 > 0) {
        prePage = " <li class=\"paginate_button previous\" aria-controls=\"dynamic-table\" tabindex=\"0\" id=\"dynamic-table_previous\"><a href=\"javascript:void(0)\" onclick=\"showSearchResult('" + text + "','" + type + "'," + (curPage - 1) + ")\">上一页</a>";
    }

    if (curPage + 1 <= pageCount) {
        nextPage = " <li class=\"paginate_button next\" aria-controls=\"dynamic-table\" tabindex=\"0\" id=\"dynamic-table_next\"><a href=\"javascript:void(0)\" onclick=\"showSearchResult('" + text + "','" + type + "'," + (curPage + 1) + ")\">下一页</a></li>";
    }
    var htmlStr = "<ul class=\"pagination\">" + prePage + nextPage + "</ul>";
    return htmlStr;
}

function showBasicConf(){

    $.getJSON("/admin/robot/set/basicDate.action", {
        qs1:"#hello", 
        qs2:"#noanswer",
        qs3:"#blank",
        callback:"",
        t: new Date().getTime()
    },function showbasicResult(date) {
        var listBean = date.o;
        var helloBean = listBean[0];
        var helloA ="<ol>";
        for(var i=0;i<helloBean.a.length;i++){
            helloA += ("<li>"+helloBean.a[i]+"</li>");
        }
        helloA +="</ol>";
        $("#hello").html(helloA);
        $("#hello").after("<td class='hidden-260'><div data-id='"+helloBean.id+"' class='hidden-sm hidden-xs action-buttons'> <a href='/admin/robot/set/updateIndex.action?id="+helloBean.id+"' target='_self' title='修改' class='green'><i class='ace-icon fa fa-pencil bigger-130'></i> </a> <a href='javascript:void(0)' class='red delBtn' title='删除'> <i class='ace-icon fa fa-trash-o bigger-130'></i> </a> </div></td>");

        var noanswerBean = listBean[1];
        var noanswerA ="<ol>";
        for(var i=0;i<noanswerBean.a.length;i++){
            noanswerA += "<li>"+noanswerBean.a[i]+"</li>";
        }
        noanswerA +="</ol>";
        $("#noanswer").html(noanswerA);
        $("#noanswer").after("<td class='hidden-260'><div data-id='"+noanswerBean.id+"' class='hidden-sm hidden-xs action-buttons'> <a href='/admin/robot/set/updateIndex.action?id="+noanswerBean.id+"' target='_self' title='修改' class='green'><i class='ace-icon fa fa-pencil bigger-130'></i> </a> <a href='javascript:void(0)' class='red delBtn' title='删除'> <i class='ace-icon fa fa-trash-o bigger-130'></i> </a> </div></td>");

        var blankBean = listBean[2];
        var blankA ="<ol>";
        for(var i=0;i<blankBean.a.length;i++){
            blankA +=  "<li>"+blankBean.a[i]+"</li>";
        }
        blankA +="</ol>";
        $("#blank").html(blankA);
        $("#blank").after("<td class='hidden-260'><div data-id='"+blankBean.id+"' class='hidden-sm hidden-xs action-buttons'> <a href='/admin/robot/set/updateIndex.action?id="+blankBean.id+"' target='_self' title='修改' class='green'><i class='ace-icon fa fa-pencil bigger-130'></i> </a> <a href='javascript:void(0)' class='red delBtn' title='删除'> <i class='ace-icon fa fa-trash-o bigger-130'></i> </a> </div></td>");
        
    });
    
}
$(function () {
    showBasicConf();
    showSearchResult("","question",1);
    $("#searchBtn").click(function(){
        var condition = $("#keywords").val();
        showSearchResult(condition,"question",1);
    
    });
});


// function openWindow(obj){
//     $(".modal-body").load("/admin/system/list.action",{id:obj});
// }

// $(".save").click(function(){
//    var str ="";
//    var saveId = $(".saveId").val();
//     $("input:checked").each(function(){
//         str += $(this).val() + ",";
//     });
//     str = str.substring(0,str.length-1)
//      $.getJSON(
//      "/admin/system/updateTag.action",
//      {id:saveId,name:str},
//      function(data){
//          if(data.flag=='true') {
//              alert("保存成功");
//              $(".btn-default").trigger("click");
//          } 
//      }
//      )
// })

$(function () {
    $(document).on("click",".delBtn",function() {
        if(confirm("删除后将不可恢复，确定删除该对话？")) {
            var $div = $(this).closest("div");
         var id = $div.data("id");
         $.getJSON(
                "/admin/robot/set/delete.action",
                {id:id},
                function(data){
                    if(data.flag=='true') {
                        alert("删除成功");
                        $div.closest("tr").remove();
                        var text = $("#keywords").val();
                        showSearchResult(text,"question",1);
                    } else {
                        var errMsg = data.errorMessages.pop();
                        alert(errMsg);
                    }
                }
         )
        }
    });
})
</script>