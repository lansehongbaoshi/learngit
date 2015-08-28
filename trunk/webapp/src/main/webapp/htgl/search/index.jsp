<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

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
       $.getJSON("/search/searchAllKnow.action",{systemId:$("#systemIds").val(),keywords:$("#keywords").val()},
	       function showSearchResult(json){
	           if(json.flag=='true') {
	               $("#search_result").html("");
                var knows = json.o.knows;
                for(var i=0;i<knows.length;i++){
                    var k = knows[i];
                    var str = "<li><h3><a target='_blank' href='/htgl/modifyindex.action?id="+k.knowId+"'>"+k.title+"</a></h3><p>"+k.summary+"</p></li>";
                    $("#search_result").append(str);
                }
	           }
           }
       )
    })
})
</script>

<select id="systemIds" name="systemId">
</select>
<input id="keywords" type="text" name="keywords">
<input id="searchBtn" type="button" value="搜索">
<ul id="search_result">
</ul>