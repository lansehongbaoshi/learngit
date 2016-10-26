<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.chsi.knowledge.dic.*"%>
<%@ page import="com.chsi.knowledge.service.*"%>
<%@ page import="com.chsi.knowledge.pojo.*"%>
<%@ page import="com.chsi.knowledge.util.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String ctxPath = request.getContextPath();
SystemService systemService = ServiceFactory.getSystemService();
List<SystemData> systems = systemService.getSystems(false);
%>
<script type="text/javascript" src='http://t1.chei.com.cn/common/wap/help/js/template.js'></script>
<script type="text/javascript" src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<style>
  .ui-autocomplete {
    max-height: 100px;
    overflow-y: auto;
    /* 防止水平滚动条 */
    overflow-x: hidden;
  }
  /* IE 6 不支持 max-height
   * 我们使用 height 代替，但是这会强制菜单总是显示为那个高度
   */
  html .ui-autocomplete {
    height: 100px;
  }
  
  .ui-autocomplete-loading {
    background: white url('images/ui-anim_basic_16x16.gif') right center no-repeat;
  }
  
</style>

<!--breadcrumbs-->
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {}
                </script>
	<ul class="breadcrumb">
		<li><i class="ace-icon fa fa-home home-icon"></i> <a
			href="/cti/index.action">首页</a></li>
		<li class="active">添加新知识</li>
	</ul>
	<!-- /.breadcrumb -->
</div>
<div class="page-content">
	<div class="page-header">
		<h1>添加新知识</h1>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<form id="myform"
				action="<%=ctxPath%>/cti/knowledge/searchindex/addindex/addDSHKnowledge.action"
				method="post" enctype="multipart/form-data" class="form-horizontal">
				<div class="form-group">
					<label for="" class="col-sm-1 control-label no-padding-top">标题：</label>
					<div class="col-sm-9">
						<input id="title" type="text" name="title" style="width: 400px;float: left"
							value="">
						<span id="titleCheck" type="text" name="titleCheck" style="float: left"></span>
						<div id='search_list'></div>

					</div>
				</div>
				<div class="form-group">
					<label for="" class="col-sm-1 control-label no-padding-top">关键字：</label>
					<div class="col-sm-9">
						<input id="keywords" type="text" name="keywords"
							style="width: 200px;" value=""> <span>（说明：2~3个,英文逗号,隔开）</span>
					</div>
				</div>
				<div class="form-group">
					<label for="" class="col-sm-1 control-label no-padding-top">热点度：</label>
					<div class="col-sm-9">
						<input id="sort" type="text" name="sort" style="width: 100px;"
							value="20" readonly="readonly"> <span>（说明：1~99之间的数字,数值越大,排序越靠前）</span>
					</div>
				</div>
				<div class="form-group">
					<label for="" class="col-sm-1 control-label no-padding-top">
						标签： </label>
					<div class="col-sm-9" id="tag">
						<span id="selectedtag" style="margin: 0 2px;"></span><a
							href="javascript:void(0)" data-toggle="modal"
							data-target="#myModal">添加标签</a>
					</div>
				</div>
				<div class="form-group">
					<label for="" class="col-sm-1 control-label no-padding-top">
						类型： </label>
					<div class="col-sm-9" id="">
						<select name="type">
							<%--           <option value="<%=KnowledgeType.PUBLIC.toString()%>">公开</option> --%>
							<option value="<%=KnowledgeType.PRIVATE.toString()%>">内部</option>
						</select>
					</div>
				</div>
				<input id="content" type="hidden" name="content">
			</form>
			<div class="form-horizontal">
				<div class="form-group">
					<label for="" class="col-sm-1 control-label no-padding-top">
						内容： </label>
					<div class="col-sm-9">
						<script type="text/plain" id="container"
							style="width: 990px; height: 440px;">
                        </script>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="clearfix form-actions">
				<div class="col-md-offset-3 col-md-9">
					<button class="btn btn-info" type="button" id="modifyBtn">
						<i class="ace-icon fa fa-check bigger-110"></i> 新增
					</button>
					&nbsp; &nbsp; &nbsp;
					<button class="btn" type="reset" onclick="history.go(-1)">
						<i class="ace-icon fa fa-undo bigger-110"></i> 取消
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">添加标签</h4>
			</div>
			<div class="modal-body">
				<%for(SystemData system:systems){ %>
				<p><%=system.getName() %>：
				</p>
				<p>
					<%List<TagData> tags = ManageCacheUtil.getTagList(system.getId());
            for(TagData tag:tags){
            %>
					<span style="margin: 0 2px;"><input type="checkbox"
						name="tagIds" value="<%=tag.getId()%>" title="<%=tag.getName()%>"><%=tag.getName()%></span>
					<%} %>
				</p>
				<%} %>
			</div>
			<div class="modal-footer">

				<button id="savetag" type="button" class="btn btn-primary save"
					data-dismiss="modal">确定</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">取消
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="contentModal" tabindex="-1" role="dialog" aria-labelledby="contentModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" 
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="contentModalLabel">
                    提交内容包含敏感词汇
                </h4>
            </div>
            <div class="modal-body">
                <div id="contentModalText">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" 
                        data-dismiss="modal">返回修改
                </button>
                <button id="confirm" type="button" class="btn btn-primary">
                    继续提交
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 配置文件 -->
<script type="text/javascript" src="/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="/ueditor/ueditor.all.min.js"></script>
<!-- 实例化编辑器 -->
<script type="text/javascript">
UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
UE.Editor.prototype.getActionUrl = function(action) {
    if (action == 'uploadimage' || action == 'uploadfile') {
        return 'http://kl.chsi.com.cn/cti/file/up.action';
    } else if (action == 'uploadvideo') {
        return 'http://a.b.com/video.php';
    } else {
        return this._bkGetActionUrl.call(this, action);
    }
}
    var editor = UE.getEditor('container');
</script>
<script>
$(function () {
    $("#modifyBtn").click(function () {
        var html = editor.getContent();
        $.getJSON("/cti/knowledge/searchadd/addindex/checkBadWord.action", {
        	keywords: html,
            t: new Date().getTime()
        },function showBadWordResult(json) {
        	
        	if (json.flag == 'true') {
        		console.log(json.o.content);
        		$("#contentModalText").html(json.o.content); 
        		$("#contentModal").modal("show");
        		
        	}else{
        		console.log(html);
        		$("#content").val(html);
                $("#myform").submit();
        	}
        });
        
    });
    $("#confirm").click(function () {
    	var html = editor.getContent();
    	$("#content").val(html);
        $("#myform").submit();
    });
    $("#savetag").click(function () {
		var checked = $("#myModal .modal-body input:checked");
		var selectedtagSpan = $("#selectedtag");
		selectedtagSpan.html("");
	    checked.each(function(){
    	selectedtagSpan.append($(this).clone());
    	selectedtagSpan.append($(this).attr("title"));
        });
    });
    //自动完成
//    searchInputIng();
    $("#title").autocomplete({
    	source: function( request, response ) {
            $.ajax({
              url: "http://kl.chsi.com.cn/cti/knowledge/searchadd/addindex/quickAll.action",
              dataType: "jsonp",
              data: {
                keywords:$.trim($("#title").val()),
                t: new Date().getTime()
              },
              success: function( data ) {
            	console.log(data.o.knows);
                response( $.map( data.o.knows, function( item ) {
                  return {
                    label: "["+item.system+"]"+item.title,
                    value: item.title
                  }
                }));
              }
            });
          },
          minLength: 2
    });
    $(".ui-helper-hidden-accessible").css("display","none");
    $("#title").blur(function () { 
        
        var title = $(this).val();
        $.getJSON("/cti/knowledge/searchadd/addindex/checkRepeat.action", {
        	keywords: title,
            t: new Date().getTime()
        },
        function showSearchResult(json) {
        	if (json.flag == 'true') {
        		if(json.o.flag== true){
        			
        			var text = "<font>检查不通过,有类似重复标题：<br>";
        			for(var i=0;i<json.o.datas.length;i++){
        				text += json.o.datas[i].title+"<br>"
        			}
        			text += "</font>"
        			$("#titleCheck").html(text); 
                    $("#titleCheck").css("color","red");
//                     $(".ui-helper-hidden-accessible").html(text);
//                     $(".ui-helper-hidden-accessible").css("color","red");
//                     $(".ui-helper-hidden-accessible").css("display","");
        			
        		}else{

        			$("#titleCheck").html("<font>检查通过</font>"); 
                    $("#titleCheck").css("color","green");

        		}
        		
        	}
        });
    }); 

    
});






function searchInputIng(){
    $('#title').on('input paste',function(event){
        event.stopPropagation();          
        setTimeout(autoSearchFn,500); 
    }).css('visibility','visible').focus();
}
function autoSearchFn(){    
    var text = $.trim($('#title').val());
    console.log(text);
    
    if(text ==""){ 
    	return false;
    }
    ajaxJSONP('http://kl.chsi.com.cn/cti/knowledge/searchadd/addindex/quickAll.action',text,inputSearchShow,true);
//     if(text ==""){ 
//         $('#hot_lists').show();
//         $('#ask_list').html('');
//         return false;
//     }else if (text == InputText){
//         return false;   
//     }
//     $('#hot_lists').hide();
//     ajaxJSONP('quickall',text,inputSearch,true);
}
//通用ajax函数
function ajaxJSONP(url,text,callback,flag){
    var _url = url;
    var data = "keywords="+text; 
    InputText = text;
    $.ajax({ 
        global:true, 
        type: "post",
        cache: false,
        async: true,
        crossDomain:true,
        url: _url,  
        data:data,
        dataType: "jsonp",  
        jsonp: "callback", //回调函数的参数  
        //jsonpCallback: callback, //回调函数的名称 
        success: callback,
        error: function(XMLHttpRequest, textStatus, errorThrown){
            console.log(textStatus+":"+data);
        }
    });
    return false;
}
function inputSearchShow(json){
    if(!json.flag){ alert(json.errorMessages); return;}
    console.log("展示搜索结果！");
    console.log(json);
    $("#search_list").html(template('input_list_detail',json));
}
//artTemplate辅助方法-高亮关键字
template.helper('hightWord', function (k,o) {
    var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]",'g');
    var _k = k.replace(pattern,'')
     var reg = new RegExp("("+$.trim(_k)+")","g");
    return  o.replace(reg, "<strong style='color:#c30'>$1</strong>");
});
</script>
<!--自动完成内容-->
<script id="input_list_detail" type="text/html">
<ul class="hot_search_list">
{{if o.knows.length>0 }}
 {{each o.knows as value i}} 
 <li>
    <a class="ui-corner-all"  href="javascript:void(0)">[<span title="{{value.systems}}">{{value.system}}</span>]{{#hightWord(value.keywords,value.title)}}
    </a>
 </li> 
 {{/each}}  
{{/if}}
</ul>

</script>