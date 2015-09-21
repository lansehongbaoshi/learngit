<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
$(function(){
	$.getJSON("/htgl/system/listSystem.action",
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
})
</script>
<form name="" action="/htgl/tag/add.action" method="get">
系统：<select id="systemIds" name="systemId">
</select>
名称：<input type="text" name="name">
描述：<input type="text" name="description">
热点度：<input type="text" name="sort" value="">（数字越大排序越往前）
<input type="submit" value="新增">
</form>