<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css" href="http://t1.chei.com.cn/common/zbbm/js/autocomplete/jqueryui.autocomplete.css" />
<script src="http://t1.chei.com.cn/common/zbbm/js/autocomplete/jqueryui.autocomplete.js"></script>
<!--breadcrumbs-->
<div class="breadcrumbs" id="breadcrumbs">
  <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {}
                </script>
  <ul class="breadcrumb">
    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="/cti/index.action">首页</a></li>
    <li class="active">修改知识</li>
  </ul>
  <!-- /.breadcrumb -->
</div>
<div class="page-content">
  <div class="page-header">
    <h1>修改知识</h1>
  </div>
  <div class="row">
    <div class="col-xs-12">
      <form id="myform" action="<%=ctxPath%>/cti/knowledge/searchadd/updateSelfKnowledge.action" method="post" enctype="multipart/form-data" class="form-horizontal">
        <input type="hidden" name="id" value="<s:property value=" id " />"> <input type="hidden" id="content" name="content" value="">
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top">标题：</label>
          <div class="col-sm-9">
            <input id="title" class="ui-input ui-autocomplete-input" type="text" name="title" style="width: 400px;float: left"
                            value="<s:property value="knowledgeData.article.title" escape="false" />">
            <span id="titleCheck" type="text" name="titleCheck" style="float: left"></span>
            <div id='search_list'></div>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top">关键字：</label>
          <div class="col-sm-9">
            <input id="keywords" type="text" name="keywords" style="width: 200px;" value="<s:property value=" knowledgeData.keywords " />"> <span>（说明：2~3个,英文逗号,隔开）</span>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top">热点度：</label>
          <div class="col-sm-9">
            <input id="sort" type="text" name="sort" style="width: 100px;" value="<s:property value=" knowledgeData.sort " />" readonly="readonly"> <span>（说明：1~99之间的数字,数值越大,排序越靠前）</span>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top"> 标签： </label>
          <div class="col-sm-9" id="tag">
          <span id="selectedtag" style="margin:0 2px;">
          <s:iterator value="knowTagRelationList" id="knowTagRelation">
          <input type="checkbox" name="tagIds" value="<s:property value="tagData.id"/>" checked="checked"><s:property value="tagData.name"/>
    </s:iterator>
          </span>
          <a href="javascript:void(0)" data-toggle="modal" data-target="#myModal">修改标签</a>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top"> 类型： </label>
          <div class="col-sm-9" id="">
          <select name="type">
<%--           <option value="<%=KnowledgeType.PUBLIC.toString()%>">公开</option> --%>
          <option value="<%=KnowledgeType.PRIVATE.toString()%>">内部</option>
          </select>
          </div>
        </div>
      </form>
      <div class="form-horizontal ">
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top"> 内容： </label>
          <div class="col-sm-9">
            <script type="text/plain" id="container" style="width: 990px; height: 440px;">
<s:property value="knowledgeData.article.content" escape="false" />
</script>
          </div>
        </div>
      </div>
      <div class="clear"></div>
      <div class="clearfix form-actions">
        <div class="col-md-offset-3 col-md-9">
          <button class="btn btn-info" type="button" id="modifyBtn">
            <i class="ace-icon fa fa-check bigger-110"></i> 修改
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
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"  aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
               添加标签
            </h4>
         </div>
         <div class="modal-body">
         <%for(SystemData system:systems){ %>
         <p><%=system.getName() %>：</p>
            <p>
            <%List<TagData> tags = ManageCacheUtil.getTagList(system.getId());
            for(TagData tag:tags){
            %>
            <span style="margin:0 2px;"><input type="checkbox" name="tagIds" value="<%=tag.getId()%>" title="<%=tag.getName()%>"><%=tag.getName()%></span>
            <%} %>
            </p>
            <%} %>
         </div>
         <div class="modal-footer">

            <button id="savetag" type="button" class="btn btn-primary save" data-dismiss="modal">
               确定
            </button>
                        <button type="button" class="btn btn-default" 
               data-dismiss="modal">取消
            </button>
         </div>
      </div><!-- /.modal-content -->
</div>
</div>
<script>
$(function(){
	var tagIds = "";
	<s:iterator value="knowTagRelationList" id="knowTagRelation">
	tagIds+='<s:property value="tagData.id"/>';
	</s:iterator>
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
//  searchInputIng();
  
	$('#title').autocomplete({
		minLength : 0,
		max : 5,
		delay : 500,
		source : function(request, response) {
			var term = request.term;
			//if (term in cache) { response(cache[term]); return;}
			var postdata = {
				"keywords" : request.term
			}
			var _url = "http://kl.chsi.com.cn/cti/knowledge/searchadd/addindex/quickAll.action";
			$.ajax({
				type : "get",
				cache : false,
				async : true,
				crossDomain : true,
				url : _url,
				data : postdata,
				dataType : "jsonp",
				jsonp : "callback", //回调函数的参数  
				jsonpCallback : "parseAutoSearch", //回调函数的名称  
				success : function(data) {
					if (!data['flag']) {
						return;
					}
					//if(data['o'].length<1){response(); return;}
					//cache[term] = data["o"];
                    console.log(data);
					response($.map(data.o.knows,function(item) {
						return {
							value : item.title,
							tagId : item.tagIds[0],
							keywords : item.keywords,
							label : item.title,
							desc : item.summary
						}
					}));

				},
				error : function(XMLHttpRequest,
						textStatus, errorThrown) {
					alert(textStatus)
					alert('请求时发生了错误，请稍后再试');
				}
			});

		},
		focus : function(event, ui) {
			$('#search_n').val(ui.item.value);
		},

		select : function(event, ui) {
			$("#help_search_form").submit();
		}

	}).data("ui-autocomplete")._renderItem = function(ul,item) {
		var reg = new RegExp("(" + item.keywords + ")", "g");
		item.desc = item.desc.replace(reg,
				"<strong style='color:#c30'>$1</strong>");
		item.label = item.label.replace(reg,
				"<strong style='color:#c30'>$1</strong>");

		return $("<li>").append(
				"<a>" + item.label + "<br/><span class='summer_stuff'>"
						+ item.desc + "</span></a>").appendTo(ul);
	};

	checkTitle();
	$("#title").blur(function() {
		checkTitle();
	});

	$("#myModal .modal-body input").each(function() {
		if (tagIds.indexOf($(this).val()) > -1) {
			$(this).attr("checked", "checked");
		}
	});
});

	function showRepeatTitle(){
	    $("#repeatTitleModal").modal("show");
	}
	function checkTitle() {
		var title = $("#title").val();
		console.log("知识的id:${id}");
		$.getJSON("/cti/knowledge/searchadd/addindex/checkRepeat.action", {
			keywords : title,
			knowId:"${id}",
			t : new Date().getTime()
		}, function showSearchResult(json) {
			if (json.flag == 'true') {
				if (json.o.flag == true) {

					var text = "<font>";
	                for(var i=0;i<json.o.datas.length;i++){
	                    text += json.o.datas[i].title+"<br>"
	                }
	                text += "</font>"
	                $("#titleModalText").html(text); 
	                $("#titleModalText").css("color","red");
	                $("#titleCheck").html("<font style='color:red;'>存在疑似重复标题，点击<a href='javascript:void(0)' onclick='showRepeatTitle()' >查看</a></font>"); 
				}
			}
		});
	}
</script>

<!-- 模态框（Modal） -->
<div class="modal fade" id="contentModal" tabindex="-1" role="dialog" aria-labelledby="contentModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" 
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="contentModalLabel">
                    内容包含敏感词汇
                </h4>
            </div>
            <div class="modal-body">
                <div id="contentModalText">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" 
                        data-dismiss="modal">返回审核
                </button>
                <button id="confirm" type="button" class="btn btn-primary">
                    继续通过
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- 模态框（Modal） -->
<div class="modal fade" id="repeatTitleModal" tabindex="-1" role="dialog" aria-labelledby="titleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" 
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="titleModalLabel">
                    疑似重复的标题
                </h4>
            </div>
            <div class="modal-body">
                <div id="titleModalText">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" 
                        data-dismiss="modal">确定
                </button>
            </div>
                
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

<script type="text/javascript">

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