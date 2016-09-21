<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.chsi.knowledge.pojo.*,com.chsi.knowledge.service.*,java.util.List" %>
<%@ page import="com.chsi.knowledge.Constants" %>
<%@ page import="com.chsi.knowledge.util.ManageCacheUtil" %>
<%@ page import="com.chsi.cms.client.*" %>
<%@ page import="com.chsi.news.vo.Article" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% 
String id = request.getParameter("id");
String keywords = request.getParameter("keywords");
KnowledgeService knowledgeService = ServiceFactory.getKnowledgeService();
KnowledgeData knowledgeData = ManageCacheUtil.getKnowledgeDataById(id);

//如果没访问过，向访问知识队列中插入ID
String visitedFlag = Constants.VISIT + id;
if (null == session.getAttribute(visitedFlag)) {
    session.setAttribute(visitedFlag, id);
    ServiceFactory.getQueueService().addVisitKnowledgeId(id);
}
String discussedFlag = Constants.DISCUSS + id;
boolean isDiscussed = null != session.getAttribute(discussedFlag);

List<KnowTagRelationData> list = ManageCacheUtil.getKnowTagRelationByKnowId(id);
TagData tagData = list.get(0).getTagData();
list = ManageCacheUtil.getKnowTag(tagData.getId());
int otherNum = 5;
%>
<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta charset="utf-8">
    <title>查看解决方案_学信网帮助中心</title>
    <meta name="Keywords" content="学信网帮助中心，学信网常见问题">
    <meta name="Description" content="学信网帮助中心，学信网账号、学信档案、应征报名、四六级查分、研招统考、研招推免、学历与成绩认证、面向港澳台招生、高考统考招生、高考特殊类型招生、高考特殊类型报名">
  </head>
  <body>
    <img src='http://t1.chei.com.cn/common/wap/images/share_help.jpg' width='0' height='0' style='position: absolute;top:-100px;left: 0' />
    <div id="wrap">
      <div class="section">
        <s:include value="searchbox.jsp"></s:include>
        <div class="findUser">
                  <h2><%=knowledgeData.getArticle().getTitle() %></h2>
                  <div class='question_source'>问题来源：<%for(SystemData system:knowledgeData.getSystemDatas()){ %><a href='/wap/help/catalog.jsp?id=<%=system.getId()%>'><%=system.getName() %></a>&nbsp;<%} %>&nbsp;&nbsp;&nbsp;更新于：<%=knowledgeData.getLastOperTime("yyyy.MM.dd") %></div>
                    <div class="cont">
                      <div class="article">
                          <%=knowledgeData.getArticle().getContent() %>
                      </div>
                  </div>
                  
                  <div class="bot">
                  	
                      <h6>
                      	<span class="before"></span>
                      	<label>此解答是否有用</label>
                        <span class="after"></span>
                      </h6>
                      <div class="huifu">
                      	<%if(!isDiscussed){ %>
                        <ul>
                            <li><button >有用</button></li>
                            <li class="none"><button>无用</button></li>
                        </ul>
                        <%}else{ %>
                             <div class='has_reply'>已收到您的建议。谢谢！</div>
                        <%}%>
                      </div>
                       <div class="huifu_end" style='display:none;'>
                       	<div class='kp'>有用</div>
                        <span>谢谢您的反馈！<span class='huifu_ccpj available' style='display:none;'>补充评价</span></span>
                      </div>
                     
                  </div>
                  
              </div>
              <div class="may_que">
                  <h3>您可能遇到的问题</h3>
                  <ul class="hot_list">
                  <%int realnum = 0;
                  CmsServiceClient cmsServiceClient = CmsServiceClientFactory.getCmsServiceClient();
                  for(int i=0;i<list.size()&&realnum<otherNum;i++) {
                      KnowTagRelationData one = list.get(i);
                      KnowledgeData data = one.getKnowledgeData();
                      if(data.getId().equals(id)) continue;
                      Article article = cmsServiceClient.getArticle(data.getCmsId());
                      realnum++;
                  %>
                      <li<%if(i==list.size()-1 || realnum==otherNum) out.print(" class=\"last\"");%>>
	                      <a class='hl_a' href="/wap/help/ckjjfa.jsp?id=<%=data.getId() %>">
	                      	<%=article.getTitle() %>
		                    <img class='hl_next_img' src="http://t1.chei.com.cn/common/wap/help/images/more.png"/>
	                      </a>
                      </li>
                   <%} %>
                  </ul>
              </div>
      </div>
      	<div class="dialog" style="display:none">
                <div class="bot_bar">
                  <div class="title">
                    <div class="cancel available">取消</div>
                    <h1>您觉得无用的原因</h1>
                    <div class="submit">提交</div>
                  </div>
                  <ul class='sel_list'>
                    <li><label for='sel1'><input type="radio" name="sel" id='sel1' value='解决方案看不懂'/>解决方案看不懂</label></li>
                    <li><label for='sel2'><input type="radio" name="sel" id='sel2' value='操作后不成功'/>操作后不成功</label></li>
                    <li><label for='sel3'><input type="radio" name="sel" id='sel3' value='其他'/>其他</label></li>
                  </ul>
                  <textarea class='text_area' placeholder=""></textarea>
                </div>
        </div>
    <!-- 图片查看器 -->
    <div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="pswp__bg"></div>
        <div class="pswp__scroll-wrap">
            <div class="pswp__container">
                <div class="pswp__item"></div>
            </div>
            <div class="pswp__ui pswp__ui--hidden">
                <div class="pswp__top-bar">
                    <div class="pswp__counter"></div>
                    <button class="pswp__button pswp__button--close" title="Close (Esc)"></button>
                   <!--  <button class="pswp__button pswp__button--share" title="Share"></button> -->
                    <button class="pswp__button pswp__button--fs" title="Toggle fullscreen"></button>
                    <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>
                    <div class="pswp__preloader">
                        <div class="pswp__preloader__icn">
                            <div class="pswp__preloader__cut">
                                <div class="pswp__preloader__donut"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
                    <div class="pswp__share-tooltip"></div>
                </div>
                <button class="pswp__button pswp__button--arrow--left" title="Previous (arrow left)">
                </button>
                <button class="pswp__button pswp__button--arrow--right" title="Next (arrow right)">
                </button>
                <div class="pswp__caption">
                    <div class="pswp__caption__center"></div>
                </div>
            </div>
        </div>
    </div>
    </div>
  </body>
<link rel="stylesheet" href="http://t1.chei.com.cn/common/wap/photoswipe/css/photoswipe.css"> 
<link rel="stylesheet" href="http://t1.chei.com.cn/common/wap/photoswipe/css/default-skin/default-skin.css"> 
<script src="http://t1.chei.com.cn/common/wap/photoswipe/js/photoswipe.min.js"></script> 
<script src="http://t1.chei.com.cn/common/wap/photoswipe/js/photoswipe-ui-default.min.js"></script>  
<script>
$(function(){
	$('.huifu').delegate('li','click',function(e){
		var $this = $(this),$huifuEnd=$('.huifu_end');
		var n = 1;
		$this.parents('.huifu').hide();	
		$huifuEnd.show();
		if($this.hasClass("none")){//无用
			$huifuEnd.find('.kp').text('无用');
			$('.dialog').show();
		}else{
			usefulFn();
		}
	});
	$('.huifu_end').delegate('.huifu_ccpj','click',function(e){
		$('.dialog').show();			
	});
  $('.title').delegate('.submit','click',function(){
    var textarea = $('input[name="sel"]:checked').val()||'';
    var val = $('.dialog').find('textarea').val()||'';
    submitFn(val,textarea);
  });
	$('.dialog').delegate('.cancel','click',function(e){
		$(this).parents('.dialog').hide();
		$('.huifu_ccpj').show();
	}).find('input[name="sel"]').change(function(e) {
        var $this = $(this);
		var value = $('input[name="sel"]:checked').val()||'';
		if(value!=''){			
			var $dialog = $this.parents(".dialog");
			$dialog.find('.submit').addClass('available');
				   //.click(function(){submitFn(value,$('.text_area').val());			
			//});			
		}
  }).end().find('textarea').keyup(function(){
		$this = $(this);
		var val = $this.val()||'';
		if(val!=''){
			var $dialog = $this.css('color','#333').parents(".dialog");
			var $textArea = $dialog.find('input[name="sel"]').eq(2).prop('checked',true);
			var textarea = $('input[name="sel"]:checked').val()||'';
			$dialog.find('.submit').addClass('available');
				//.click(function(){submitFn(val,textarea)});			
		}	
	})
	<%if(keywords!=null){%>
	$.post("/search/allSearch.action", {keywords:'<%=keywords %>'});
	<%}%>
});

var _url = "/view/discuss.action";
var data = {knowledgeId:'<%=id%>'};
function usefulFn(){
	data.discussStatus = 1;
	$.ajax({ 
		global:true, 
		type: "post",
		cache: false,
		async: true,
		crossDomain:true,
		url: _url,  
		data:data,
		//dataType: "jsonp",  
		//jsonp: "callback", //回调函数的参数  
		//jsonpCallback: callback, //回调函数的名称  
		error: function(){  
			//alert('请求时发生了错误，请稍后再试');  
		}  
	}); 
	//alert('有用');	
}
function submitFn(selectTxt,textarea){
	$('.dialog').hide();
	$('.huifu_ccpj').hide();
	data.discussStatus = 0;
	if(textarea=='谢谢您帮我们发现了新大陆，和小编说说呗!'){
		textarea='';
	}else{
		textarea="："+textarea;
	}
	if(selectTxt=='其他') data.content=selectTxt+textarea;
	else data.content=selectTxt;
	//alert('选中了：'+selectTxt+'，填写内容为：'+textarea+'');
	$.ajax({
		global:true,
		type: "post",
		cache: false,
		async: true,
		crossDomain:true,
		url: _url,
		data:data,
		//dataType: "jsonp",
		//jsonp: "callback", //回调函数的参数
		//jsonpCallback: callback, //回调函数的名称
		error: function(){
			alert('请求时发生了错误，请稍后再试');
		}
	});
};

//微信中图片可预览效果的实现。
$('.article ').on('click', 'img',function(event) {
    var imgArray = [],imgIndex=0;
    var curImageSrc = $(this).attr('src');
    var oParent = $(this).parent();
    if (curImageSrc && !oParent.attr('href')) {
        $('.article img').each(function(index, el) {
            var itemSrc = $(this).attr('src');
            imgArray.push({src:itemSrc,w:2*this.width,h:2*this.height});
            if(itemSrc == curImageSrc){
                imgIndex = index;
            }
        });
        openPhotoSwipe(imgIndex,imgArray);
    }
});
//调用PhotoSwiper方法，图片放大效果
function openPhotoSwipe(index,imgArr){
    var pswpElement = document.querySelectorAll('.pswp')[0];
    var $content = $(pswpElement).find('.pswp__container');
    var items =  $content.find('.pswp__item').length;
    var imgItems = imgArr.length;
    var n = imgItems - items;
    if(n >0){
        //动态设置item，否则报错，item和图片个数相同
        for(var i =0 ;i<n ;i++){
            $('<div class="pswp__item"></div>').appendTo($content);
        }
    }
    var options = {
        history: false,
        focus: false,
        showAnimationDuration: 0,
        hideAnimationDuration: 0,
        index:index
    };
    var gallery = new PhotoSwipe(pswpElement, PhotoSwipeUI_Default, imgArr, options);
    gallery.init();
}

</script> 

</html>
