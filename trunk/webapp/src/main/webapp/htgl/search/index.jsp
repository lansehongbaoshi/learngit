<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
function showSearchResult(systemId,keywords,curPage) {
    $.getJSON("/search/searchAllKnow.action",{systemId:systemId,keywords:keywords,curPage:curPage},
       function showSearchResult(json){
           if(json.flag=='true') {
               $("#search_result").html("");
            var knows = json.o.knows;
            var pagination = json.o.pagination;
            for(var i=0;i<knows.length;i++){
                var k = knows[i];
                var str = "<li><h3><a target='_blank' href='/htgl/modifyindex.action?id="+k.knowId+"'>"+k.title+"</a></h3><p>"+k.summary+"</p></li>";
                $("#search_result").append(str);
            }
            $("#search_result").append(formatP(pagination,systemId,keywords,curPage));
           }
       }
   )
}
function del(knowId){
    
}
function formatP(pagination,systemId,keywords,curPage) {
    var curPage = pagination.curPage;
    var totalCount = pagination.totalCount;
    var pageCount = Math.ceil(totalCount/pagination.pageCount);
    var prePage="";
    var nextPage="";
    if(curPage-1>0) {
	   prePage = "<a href=\"javascript:void(0)\" onclick=\"showSearchResult('"+systemId+"','"+keywords+"','"+(curPage-1)+"')\">上一页</a>";
    }
    if(curPage+1<=pageCount) {
	   nextPage = "<a href=\"javascript:void(0)\" onclick=\"showSearchResult('"+systemId+"','"+keywords+"','"+(curPage+1)+"')\">下一页</a>";
    }
    var htmlStr="<hr><p>"+prePage+"&nbsp;&nbsp;&nbsp;&nbsp;"+nextPage+"&nbsp;&nbsp;&nbsp;&nbsp;共"+totalCount+"条</p>";
    return htmlStr;
}
$(function(){
    $.getJSON("/htgl/listSystem.action",
        function showSystems(json){
            if(json.flag=="true"){
              var options = "";
               for(var i=0;i<json.o.length;i++){
                var option = json.o[i];
                options+="<option value='"+option.id+"'>"+option.name+"</option>";
               }
               $("#systemIds").html(options);
            }
        }
    );
    $("#searchBtn").click(function(){
	   showSearchResult($("#systemIds").val(),$("#keywords").val(),0);
    })
})
</script>

<select id="systemIds" name="systemId">
</select>
<input id="keywords" type="text" name="keywords">
<input id="searchBtn" type="button" value="搜索">
<ul id="search_result">
</ul>