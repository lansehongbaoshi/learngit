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
	$("#listTags").click(function(){
		var systemId = $("#systemIds").val();
		$.getJSON("/htgl/tag/list.action",{systemId:systemId},
	        function showSystems(json){
	            if(json.flag=="true"){
	              var options = "";
	               for(var i=0;i<json.o.length;i++){
	                var tag = json.o[i];
	                options+="<li>"+tag.name+"-"+tag.description+"<a href='/htgl/tag/updateIndex.action?id="+tag.id+"' target='_blank'>修改</a></li>";
	               }
	               $("#tags").html(options);
	            }
	        }
	    );
	});
})
</script>
<select id="systemIds" name="systemId">
</select><input id="listTags" type="button" value="查询标签">
<ul id="tags">
</ul>