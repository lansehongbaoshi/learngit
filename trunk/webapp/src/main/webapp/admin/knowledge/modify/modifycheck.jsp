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
List<SystemData> systems = systemService.getSystems(false, null);
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
    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="/admin/index.action">首页</a></li>
    <li class="active">知识审核</li>
  </ul>
  <!-- /.breadcrumb -->
</div>
<div class="page-content">
  <div class="page-header">
    <h1>审核知识</h1>
  </div>
  <div class="row">
    <div class="col-xs-12">
      <form id="myform" action="<%=ctxPath%>/admin/knowledge/checkUpdate.action" method="post" enctype="multipart/form-data" class="form-horizontal" onsubmit="return checkTheForm(this)">
        <input type="hidden" name="id" value="<s:property value=" id " />"> <input type="hidden" id="content" name="content" value="">
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top">标题：</label>
          <div class="col-sm-9">
            <input id="title" class="ui-input ui-autocomplete-input" type="text" name="title" style="width: 400px;float: left" maxlength="100"
                            value="<s:property value="knowledgeData.article.title" escape="false" />">
            <span id="titleCheck" type="text" name="titleCheck" style="float: left"></span>
            <div id='search_list'></div>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top">关键字：</label>
          <div class="col-sm-9">
            <input id="keywords" type="text" name="keywords" style="width: 200px;" value="<s:property value=" knowledgeData.keywords " />"> <span>（建议：2~3个,英文逗号,隔开）</span>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-1 control-label no-padding-top">热点度：</label>
          <div class="col-sm-9">
            <input id="sort" type="text" name="sort" style="width: 100px;" value="<s:property value=" knowledgeData.sort " />"> <span>（说明：1~999之间的整数,数值越大,排序越靠前）</span>
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
          <s:if test="knowledgeData.type==\"PUBLIC\"">
            <option value="<%=KnowledgeType.PUBLIC.toString()%>">公开</option>
            <option value="<%=KnowledgeType.PRIVATE.toString()%>">内部</option>
          </s:if>
          <s:else>
            <option value="<%=KnowledgeType.PRIVATE.toString()%>">内部</option>
            <option value="<%=KnowledgeType.PUBLIC.toString()%>">公开</option>
          </s:else>
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
            <i class="ace-icon fa fa-check bigger-110"></i> 审核通过
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
		var content = editor.getContent();
        var title = $("#title").val();
        var keywords = $("#keywords").val();
        $.post("/admin/knowledge/searchindex/addindex/checkBadWord.action", {
        	content : content,
            title : title,
            keywords: keywords,
            t: new Date().getTime()
        },function showBadWordResult(json) {
            
            if (json.flag == 'true') {
                console.log(json.o.content);
                $("#contentModalText").html(json.o.content); 
                $("#contentModal").modal("show");
                
            }else{
                console.log(content);
                $("#content").val(content);
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
    
	$("#title").autocomplete({
        minLength: 0,
        max: 5,
        delay: 200,
        source: function (request, response) {
            var term = request.term;
            var postdata = {"keywords":request.term,"systemId":""};
            var _url = "/admin/knowledge/searchindex/addindex/autoAllTitle.action";
            $.ajax({ 
                type: "get",
                cache: false,
                async: true,
                crossDomain:true,
                url: _url,  
                data:postdata, 
                dataType: "jsonp",
                jsonp: "callback", //回调函数的参数  
                jsonpCallback: "parseAutoSearch", //回调函数的名称  
                success: function(data) {
                	console.log(data);
                    response($.map(data["o"].knows, function(item){
                        return {
                            value: item.title,
                            keywords: item.keywords,
                            label: item.title,
                            desc: item.summary,
                            system: item.system,
                            knowId: item.knowId
                        }                       
                    }));
                },
                error: function(XMLHttpRequest, textStatus, errorThrown){
                    alert(textStatus+'请求时发生了错误，请稍后再试');  
                }  
            }); 
        },
        focus: function(event, ui) {
            $("#judge").val("0");
            return false;
        },
        change:function(event, ui) {
             $("#judge").val("");
        },
        select: function(event, ui){
            $("#judge").val("");
            return false;
       }
    }).data("ui-autocomplete")._renderItem = function (ul, item) {   
        return $("<li>").append("<a>"+item.label+"<span class='system'>["+item.system+"]</span></a>").appendTo(ul);
    };
    
    checkTitle();
    $("#title").blur(function () { 
        checkTitle()
    }); 
    
    $("#myModal .modal-body input").each(function(){
    	if(tagIds.indexOf($(this).val())>-1) {
    		$(this).attr("checked","checked");
    	}
    });
});
function showRepeatTitle(){
    $("#repeatTitleModal").modal("show");
}
function checkTitle() {
    var title = $("#title").val();
    $.getJSON("/admin/knowledge/searchindex/addindex/checkRepeat.action", {
        keywords: title,
        knowId:"${id}",
        t: new Date().getTime()
    },
    function showSearchResult(json) {
        if (json.flag == 'true') {
            if(json.o.flag== true){
                
                var text = "<font>";
                for(var i=0;i<json.o.datas.length;i++){
                    text += json.o.datas[i].title+"<br>"
                }
                text += "</font>"
                $("#titleModalText").html(text); 
//                $("#titleModalText").css("color","red");
                $("#titleCheck").html("<font style='color:red;'>存在疑似重复标题，点击<a href='javascript:void(0)' onclick='showRepeatTitle()' >查看</a></font>"); 
            }
        }
    });
}

function checkTheForm(from){

    var keywords = $("#keywords").val();
    if(keywords.length<1){
        alert("请输入关键字");
        return false;
    }
    var title = $("#title").val();
    if(title.length<1||title.length>100){
        alert("请输入标题并且长度在100以内");
        return false;
    }
    var content = editor.getContent();
    if(content.length<1){
        alert("请输入内容");
        return false;
    }
    var tagIds = $("#selectedtag input[name='tagIds']");
    if(tagIds.length<1){
        alert("请先到便签管理中增加标签");
        return false;
    }
    var sort = $("#sort").val();
    var r = /^[1-9][0-9]{0,2}$/;　　//正整数
    var flag=r.test(sort);
    if(!flag||sort.length<1||Number(sort)<1||Number(sort)>999){
        alert("请输入热点度:1~999之间的整数,数值越大,排序越靠前");
        return false;
    }
    var type = $($("select[name='type']")[0]).val();
    if(type.length<1){
        alert("请选择类型");
        return false;
    }
    return true;
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
                <h4 class="modal-title" id="contentModalLabel" style="font-weight:700">
                    提交信息包含敏感词如下
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
                    继续审核通过
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
        return 'http://kl.chsi.com.cn/admin/file/up.action';
    } else if (action == 'uploadvideo') {
        return 'http://a.b.com/video.php';
    } else {
        return this._bkGetActionUrl.call(this, action);
    }
}
    var editor = UE.getEditor('container');
</script>
