<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
%> 
<table cellpadding="5" cellspacing="1" border="1" width="645">
  <tr>
    <td><s:property value="knowledgeVO.article.title" /> </td>
  </tr>
   <tr>
   <td><s:property value="knowledgeVO.article.content" /></td>
   </tr> 
</table>

访问频度:<s:property value="knowledgeVO.knowledgeData.visitCnt" /> <br>

   <input type="hidden" name="knowledgeId" value="<s:property value="knowledgeVO.knowledgeData.id"/>" id="knowledgeId">
      很满意:<input type="radio" name="discussStatus" value="1"> 
      一般:  <input type="radio" name="discussStatus" value="2" checked="checked" > 
      不满意:<input type="radio" name="discussStatus" value="3"> 
   <input type="button" value="提交评价" id="submit">
<script type="text/javascript">  
$(document).ready(function () {
	$("#submit").bind("click",function(){discuss()});
});
   function discuss(){
	   $.ajax({
		    url:"<%=ctxPath%>/htgl/discuss/discuss.action",
			type : "POST",
			traditional : true,
			data : {
				knowledgeId : $("#knowledgeId").val(),
				discussStatus : $("input[name=discussStatus]:checked").val()
			},
			success : function(data) {
				var m = eval('(' + data + ')');
				if (m.flag == "success") {
					alert("评价成功!");
				} else {
					alertError(m.errorMessages);
				}
			},
			error : function() {
				alert("保存错误！");
			}
		});
	}
   function alertError(list){
	    var str="";
	    for(var text in list){  '<p>'+list[text]+'</p>'
	           str+= list[text]+"\n";
	       }
	   alert(str); 
	}
	
</script>