/*
 * @author <a href="mailto:weiqc@chsi.com.cn">weiqc</a>
 * \u4e0b\u62c9\u5c42\u3002\u7528\u4e8e\u5728\u8868\u5355\u8f93\u5165\u65f6\u6267\u884c\u6309\u7167\u7279\u5b9a\u6d41\u7a0b\u83b7\u5f97\u6570\u636e\u540e\u663e\u793a\u7c7b\u4f3c\u4e0b\u62c9\u83dc\u5355\u7684\u9009\u62e9\u5c42
 * \u57fa\u4e8ejQuery
 * data{
 *     bind:bindId,
 *     target:id,//\u5982\u679c\u4e0d\u8bbe\u5b9a,\u5219\u4e3abind
 *     prev:id,
 *     next:id,
 *     paginationDom:dom,
 *     promptDom:dom,
 *     width:width,//width of items
 *     bindHide:id,//\u5982\u679c\u4e0d\u8bbe\u5b9a,\u4e0d\u66f4\u65b0\u9690\u85cf\u8868\u5355
 *     select:handler,//\u81ea\u5b9a\u4e49\u9009\u53d6\u7684\u64cd\u4f5c\uff0chandler\u662f\u51fd\u6570\u540d\uff0c\u51fd\u6570\u5b9e\u73b0function functionname(event)
 *     search:handler, //\u5173\u952e\u5b57\u641c\u7d22\u64cd\u4f5c\u7684\u51fd\u6570\u540d\u3002\u51fd\u6570\u5b9e\u73b0function functionname(event)
 *     formId:formId, //\u9700\u8981\u63a7\u5236\u56de\u8f66\u952e\u7684\u8868\u5355
 *     buttonId:(buttonId|[buttonId1,buttonId2]) //\u8868\u5355\u4e2d\u5145\u5f53submit\u7684\u6309\u94ae\u7684ID\uff0c\u53ef\u4e3aId\u4e32\u6216\u8005\u6570\u7ec4\uff0c\u5982\u679c\u539f\u6709onclick\uff0c\u6539\u4e3aonsubmit\u65f6\u6267\u884c
 * }
 * var selection = new Selection(data);
 * \u5728\u81ea\u5b9a\u4e49\u51fd\u6570\u4e2d\uff0cevent\u5bf9\u5e94\u7684\u90fd\u662f\u5f53\u524d\u64cd\u4f5c\u5bf9\u5e94\u7684dom\u3002
 * var dom = event.currentTarget;\u5982\u679c\u9700\u8981jQuery\u5bf9\u8c61\uff0c\u4f7f\u7528$(event.currentTarget)
 * 
 */
function Selection(data){
	this.data = data;
	this.keyWord = null;//\u5165\u53e3\u8868\u5355\u503c\u4e34\u65f6\u4fdd\u5b58\u57df
	this.entranceInputId = null;//\u5165\u53e3,\u8f93\u5165\u8868\u5355\u7684ID
	this.items = null;//\u4e0b\u62c9\u5217\u8868\u9879(data\u683c\u5f0f)
	this.itemDoms = null;//\u4e0b\u62c9\u5217\u8868\u9879(jquery dom\u683c\u5f0f)
	this.targetInputId = null;//\u76ee\u6807\u663e\u793a\u8868\u5355,\u4e00\u822c\u4e0eentranceInputId\u4e00\u81f4,\u5982\u679c\u4e0d\u7279\u6307,\u5219\u9ed8\u8ba4
	this.targetHiddenId = null;//\u76ee\u6807\u9690\u85cf\u57df\u8868\u5355id
	this.prevPage = null;//\u4e0a\u4e00\u9875dom id
	this.nextPage = null;//\u4e0b\u4e00\u9875 dom id
	this.paginationDom = null;//\u5206\u9875dom.\u5982\u9700\u4fdd\u5b58\u5206\u9875\u8bb0\u5f55\u70b9\u7b49\u5206\u9875\u4fe1\u606f\uff0c\u81ea\u884c\u6dfb\u52a0\u3002
	this.promptDom = null;//\u63d0\u793a\u4fe1\u606fdom
	
	this.formId = null;
	this.buttonId = null;
	
	/*
	 * \u5c42ID\u4fdd\u5b58\u6bb5
	 */
	this.random = (Math.random()*10000000000 + "").substr(0,6);
	this.selectionWrapDivId = "swdi_" + this.random;
	this.itemsWrapDivId = "iwdi_" + this.random;
	this.itemDivIdBase = "idi_" + this.random + "_";
	this.promptDivId = "pdi_" + this.random;
	this.paginationDivId = "padi_" + this.random;
	this.bottomDivId = "bdi_" + this.random;
	
	this.objId = "selection_" + this.random;
	/*
	 * \u9700\u8981\u5b9e\u73b0\u4e00\u4e2afunction(event),set function name.
	 * \u8ba9Selection\u77e5\u9053\u76d1\u542c\u5230keyWord\u66f4\u6539\u540e\u9700\u8981\u6267\u884c\u7684\u64cd\u4f5c,
	 * \u6b64\u51fd\u6570\u8fd8\u8d1f\u8d23\u83b7\u5f97ajax\u6570\u636e\u540e\u66f4\u65b0items\u653e\u5165Selection\u66f4\u65b0\u5217\u8868.
	 */
	this.searchHandler = null;
	/*
	 * \u5982\u679c\u9700\u8981\u81ea\u5b9a\u4e49\u9009\u62e9\u5217\u8868\u9879\u7684\u64cd\u4f5c,\u5219\u66f4\u65b0\u6b64\u53d8\u91cf,\u540c\u6837\u662f\u5b9e\u73b0function(event),\u5e76set\u5165function name
	 */
	this.selectHandler = null;
	
	this.interval = null;
	this.width = null;//items\u5bbd\u5ea6

	if(typeof Selection.prototype._initialized === 'undefined'){
		Selection.prototype.setEntranceInputId = function(id){
			this.entranceInputId = id;
		}
		Selection.prototype.setTargetInputId = function(id){
			this.targetInputId = id;
		}
		Selection.prototype.setTargetHiddenId = function(id){
			this.targetHiddenId = id;
		}
		Selection.prototype.setPrevPage = function(id){
			this.prevPage = id;
		}
		Selection.prototype.setNextPage = function(id){
			this.nextPage = id;
		}
		Selection.prototype.setPaginationDom = function(dom){
			this.paginationDom = dom;
		}
		Selection.prototype.setPromptDom = function(dom){
			this.promptDom = dom;
		}
		
		Selection.prototype.clearInterval = function(){
			if(this.interval)window.clearInterval(this.interval);
			this.interval = null;
		}
		
		Selection.prototype.select = function(target){
			var obj = window[target.attr("objid")];
			if(target.attr("itemindex")){
				target.click();
			}else{
				target = $(".selectionItemTrChosen[id*='" + obj.itemDivIdBase + "']");
				if(target && target.length > 0) $(target[0]).click();
			}
		}
		
		Selection.prototype.defaultSelectHandler = function(event){
			var item = $(event.currentTarget);
			var obj = window[item.attr("objid")];
			$("#" + obj.targetInputId).val(item.text());
			var hide = item.attr("hideValue");
			if(obj.targetHiddenId && hide){
				$("#" + obj.targetHiddenId).val(hide);
			}
			obj.keyWord = item.text();
			obj.destroy();
		}
		
		Selection.prototype.specialSelectHandler = function(event){
			var item = $(event.currentTarget);
			var obj = window[item.attr("objid")];
			obj.selectHandler(event);
		}
		
		Selection.prototype.navigate = function(event){
			var target = $(event.currentTarget);
			var obj = window[target.attr("objid")];
			if(event.which == 27) {
				obj.destroy();//ESC
				obj.clearInterval();
				return;
			}
			var code = event.which;
			if(code == 37){
				var ctrl = $("#" + obj.prevPage);
				if(ctrl) ctrl.click();
			}else if(code == 39){
				var ctrl = $("#" + obj.nextPage);
				if(ctrl) ctrl.click();
			}else if(code == 38 || code == 40){
				var wrap = $("#" + obj.selectionWrapDivId);
				if(wrap.length == 0){
					if(obj.interval == null){
						obj.interval = window.setInterval("window['"+target.attr("objid")+"'].search()",800);
					}
					obj.refresh();
					return true;
				}
				obj.refreshItemClass(event);
			}else if(code == 13){
				//Enter
				var wrap = $("#" + obj.selectionWrapDivId);
				if(wrap.length == 0){
					return;
				}
				event.preventDefault();
				event.stopPropagation();
				obj.select(target);
				return false;
			}
		}
		
		Selection.prototype.refreshItemClass = function(event){
			var target = $(event.currentTarget);
			var obj = window[target.attr("objid")];
			//\u9f20\u6807\u4e8b\u4ef6
			if(target.attr("itemindex")){
				$(".selectionItemTrChosen[id*='" + obj.itemDivIdBase + "']").each(function(){
					$(this).removeClass("selectionItemTrChosen");
				})
				target.addClass("selectionItemTrChosen");
			}else{
				//Input \u952e\u76d8\u4e0a\u4e0b\u952e left up right down 37 38 39 40
				var current = $(".selectionItemTrChosen[id*='" + obj.itemDivIdBase + "']");
				if(!current || current.length == 0) return;
				var index = parseInt(current.attr("itemindex"),10);
				var code = event.which;
				if(code == 38){
					if(index >= 0){
						if(index == 0){
							index = obj.itemDoms.length -1;
						}else{
							index--;
						}
						current.removeClass("selectionItemTrChosen");
						$("#" + obj.itemDivIdBase + index).addClass("selectionItemTrChosen");
					}
				}else if(code == 40){
					if(index <= obj.itemDoms.length -1){
						if(index == obj.itemDoms.length -1){
							index = 0;
						}else{
							index++;
						}
						current.removeClass("selectionItemTrChosen");
						$("#" + obj.itemDivIdBase + index).addClass("selectionItemTrChosen");
					}
				}
			}
		}
		
		/*
		 * items:array
		 * item{
		 *     text:"\u663e\u793a\u7684\u5185\u5bb91",
		 *     attrs:{
		 *         "hideValue":"1",//\u5982\u679c\u9700\u8981\u66f4\u65b0\u9690\u85cf\u57df\uff0c\u5219\u6b64\u5904\u5fc5\u987b\u8981\u6709
		 *         "key":"key1"
		 *     }
		 * }
		 */
		Selection.prototype.loadItems = function(items,flag){
			var cf = $(":focus");
			if(cf.length == 0 || $(cf).attr("id") != this.targetInputId) return;
			this.items = items;
			var _itemDoms = new Array();
			if(!this.items || items.length == 0){
				this.items = null;
				this.itemDoms = null;
			}else{
				for(var i = 0; i < this.items.length ; i++){
					var item = this.items[i];
					var tr = $("<tr />").attr("id",this.itemDivIdBase + i).addClass("selectionItemTr").attr("itemindex",i);
					var td = $("<td />");
					//var dom = $("<div>").attr("id",this.itemDivIdBase + i).addClass("selectionItemDiv");
					var span = $("<span />").text(item.text).css("width",item.text.length + "em");
					td.append(span).appendTo(tr);
					if(i == 0){
						tr.addClass("selectionItemTrChosen");
					}
					if(item.attrs){
						for(var attr in item.attrs){
							tr.attr(attr,item.attrs[attr]);
						}
					}
					tr.attr("objid",this.objId);
					tr.bind("mouseover",this.refreshItemClass);
					if(this.width)td.width(this.width);
					_itemDoms.push(tr);
				}
				this.itemDoms = _itemDoms;
			}
			if(flag){
				this.refresh();
			}
		}
		
		/*
		 * \u8f7d\u5165\u65b0\u7684Doms\uff0cjQuery\u5bf9\u8c61
		 * \u4e00\u822c\u7528\u4e8e\u81ea\u5b9a\u4e49\u5217\u8868\u7ed3\u70b9\uff0c\u6700\u597d\u5728dom\u76f4\u63a5\u8bbe\u5b9a\u6709width\uff0c\u5426\u5219\u5c06\u904d\u5386\u5143\u7d20\u7d2f\u8ba1width
		 * \u4e5f\u53ef\u4ee5\u8bbe\u5b9aselection.width
		 * items\u7528\u4e8e\u7ed9tr\u52a0\u5165\u5c5e\u6027\u3002items\uff1a[]
		 * item{
		 *     attrs:{
		 *         "key1":"value1"
		 *     }
		 * }
		 */
		Selection.prototype.loadDoms = function(doms,items,flag){
			this.items = items;
			var _itemDoms = new Array();
			if(!doms && !this.items){
				for(var i = 0; i < doms.length ; i++){
					var dom = doms[i];
					var item = this.items[i];
					var tr = $("<tr />").attr("id",this.itemDivIdBase + i).addClass("selectionItemTr").attr("itemindex",i);
					var td = $("<td />");
					//var dom = $("<div>").attr("id",this.itemDivIdBase + i).addClass("selectionItemDiv");
					td.append(dom).appendTo(tr);
					if(i == 0){
						tr.addClass("selectionItemTrChosen");
					}
					if(item.attrs){
						for(var attr in item.attrs){
							tr.attr(attr,item.attrs[attr]);
						}
					}
					tr.attr("objid",this.objId);
					tr.bind("mouseover",this.refreshItemClass);
					if(this.width)td.width(this.width);
					_itemDoms.push(tr);
				}
			}
			this.itemDoms = _itemDoms;
			if(flag){
				this.refresh();
			}
		}
		
		Selection.prototype.refresh = function(){
			if(!this.itemDoms || this.itemDoms.length == 0){
				var span1 = $("<span />").text("\u627e\u4e0d\u5230\u4e0e" + " ");
				var spanword = $("<span />").text(this.keyWord).addClass("selectionWord");
				var span2 = $("<span />").text(" " +"\u6709\u5173\u7684\u6570\u636e\u3002");
				var table = $("<table />").attr({
					"cellspacing":"0",
					"cellpadding":"0",
					"border":"0"
				}).css("margin","0 auto");
				var tbody = $("<tbody />");
				var tr = $("<tr />");
				var td = $("<td />").attr({
					"nowrap":"nowrap"
				});
				var p = $("<p />").css("text-align","center");
				p.append(span1).append(spanword).append(span2);
				td.append(p);
				tr.append(td);
				tbody.append(tr);
				table.append(tbody);
				table.hide().appendTo($("body"));
				var width = table.width();
				table.show().remove();
				table.css("width",width);
				this.draw({
					bottom:table
				});
				return;
			}
			this.draw();
		}
		Selection.prototype.bindTrHandler = function(){
			var obj = window[this.objId];
			for(var i = 0;i<this.itemDoms.length;i++){
				var dom = this.itemDoms[i];
				var trid = dom.attr("id");
				try{
					if(this.selectHandler){
						document.getElementById(trid).addEventListener("click",obj.specialSelectHandler,false);
					}else{
						document.getElementById(trid).addEventListener("click",obj.defaultSelectHandler,false);
					}
				}catch(e){
					if(this.selectHandler){
						dom.bind("click",obj.specialSelectHandler);
					}else{
						dom.bind("click",obj.defaultSelectHandler);
					}
				}
			}
		}
		
		/*
		 * \u53d6\u5f97\u5408\u9002\u7684\u4e0b\u62c9\u5c42\u663e\u793a\u4f4d\u7f6e
		 */
		Selection.prototype.position = function(){
			var base = $("#" + this.entranceInputId);
			var body = $("body");
			var basePos = base.offset();
			var baseWidth = base.width();
			var baseHeight = base.height();
			//var bodyWidth = document.body.clientWidth;
			//var bodyHeight = document.body.clientHeight;
			var bodyWidth = body.width();
			var bodyHeight = body.height();
			var needWidth = $("#" + this.selectionWrapDivId).width();
			var needHeight = $("#" + this.selectionWrapDivId).height();
			var top = 0;
			var left = 0;
			if(needWidth <= (bodyWidth - basePos.left)){
				left = basePos.left;
			}else{
				left = bodyWidth - needWidth;
			}
			var scrollTop = document.body.scrollTop;
			if((needHeight + basePos.top) > (scrollTop + bodyHeight)){
				top = basePos.top - needHeight;
			}else{
				top = basePos.top + baseHeight;
			}
			return {left:left,top:top};
		}
		Selection.prototype.draw = function(param){
			var swrap = $("#" + this.selectionWrapDivId);
			if(swrap.length == 0) swrap = $("<div />").attr("id",this.selectionWrapDivId).addClass("selectionWrapDiv");
			else swrap.remove();
			swrap.removeAttr("style").hide().css("z-index",this.random);
			$("body").append(swrap);
			var data = {};
			if(param){
				data = $.extend(data,param);
			}
			if(!param){
				if(this.itemDoms && this.itemDoms.length > 0){
					data.items = this.itemDoms;
				}else data.items = null;
				if(this.promptDom){
					data.prompt = this.promptDom;
				}else data.prompt = null;
				if(this.paginationDom){
					data.pagination = this.paginationDom;
				}else data.pagination = null;
			}
			var widths = {};
			var itemwrap = $("#" + this.itemsWrapDivId);
			if(data.items){
				data.items = this.itemDoms;
				if(itemwrap.length == 0) itemwrap = $("<div />").attr("id",this.itemsWrapDivId).addClass("selectionItemsWrapDiv");
				itemwrap.empty();
				var table = $("<table />").attr({
					"cellspacing":"0",
					"cellpadding":"0",
					"border":"0"
				});
				var tbody = $("<tbody />");
				table.append(tbody);
				itemwrap.append(table);
				for(var i = 0;i<data.items.length;i++){
					tbody.append(data.items[i]);
				}
				swrap.append(itemwrap);
				this.bindTrHandler();
				widths.items = this.getItemsWidth();
			}else{
				if(itemwrap) itemwrap.remove();
				widths.items = 0;
			}
			var prompt = $("#" + this.promptDivId);
			if(data.prompt){
				data.prompt = this.promptDom;
				if(prompt.length == 0) prompt = $("<div />").attr("id",this.promptDivId).addClass("selectionPromptDiv");
				prompt.empty().append(data.prompt);
				itemwrap.append(prompt);
				widths.prompt = prompt.width();
			}else{
				if(prompt) prompt.remove();
				widths.prompt = 0;
			}
			var pagination = $("#" + this.paginationDivId);
			if(data.pagination){
				data.pagination = this.paginationDom;
				if(pagination.length == 0) pagination = $("<div />").attr("id",this.paginationDivId).addClass("selectionPaginationDiv");
				pagination.empty().append(data.pagination);
				swrap.append(pagination);
				widths.pagination = pagination.width();
			}else{
				if(pagination) pagination.remove();
				widths.pagination = 0;
			}
			var bottom = $("#" + this.bottomDivId);
			if(data.bottom){
				if(bottom.length == 0) bottom = $("<div />").attr("id",this.bottomDivId).addClass("selectionBottomDiv");
				bottom.empty().append(data.bottom);
				swrap.append(bottom);
				widths.bottom = this.getBottomWidth();
			}else{
				if(bottom) bottom.remove();
				widths.bottom = 0;
			}
			var width1 = widths.items + widths.prompt;
			var width2 = widths.pagination;
			var width3 = widths.bottom;
			var swrapwidth = Math.max(width1,width2,width3);
			swrap.width(swrapwidth + 10);
			var position = this.position();
			swrap.css("top",position.top);
			swrap.css("left",position.left);
			swrap.show();
		}
		
		Selection.prototype.getItemsWidth = function(){
			if(this.width)return parseInt(this.width,10);
			var trs = $(".selectionItemTr[id*='"+this.itemDivIdBase+"']");
			if(trs.length == 0)return 0;
			var _widthArray = new Array();
			for(var i = 0;i < trs.length ; i++){
				var tr = $(trs[i]);
				var td = $(tr.children()[0]);
				var w = 0;
				if(td.width() != 0){
					w = td.width();
				}else{
					var content = $(td.children()[0]);
					w = content.width();
				}
				_widthArray.push(w);
			}
			var w = eval("Math.max(" + _widthArray.join(",") + ")");
			return w==0?300:w; 
		}
		
		Selection.prototype.getBottomWidth = function(){
			if(this.width)return parseInt(this.width,10);
			var bottom = $("#" + this.bottomDivId);
			if(bottom.length == 0)return 0;
			var first = bottom.children();
			var width = $(first).width();
			return width==0?300:width;
		}
		
		Selection.prototype.destroy = function(){
			var div = $("#" + this.selectionWrapDivId);
			if(div.length != 0) div.remove();
		}
		
		Selection.prototype.controlForm = function(formId){
			formId = formId?formId:this.formId;
			this.formId = formId;
			var buttonId = this.buttonId;
			if(formId){
				var form = $("#" + formId);
				form.bind("keydown",function(event){
					if(event.which == 13){
						var wrap = $(".selectionWrapDiv");
						if(wrap.length != 0) return false;
						else {
							var btn = null;
							if(buttonId){
								btn = $("#"+buttonId +":enabled");
							}
							if(null == btn || btn.length > 0){
								$("#" + formId).submit();
							}
							//\u4fee\u6539\u65f6\u5f02\u6b65\u63d0\u4ea4\u65f6\u89e6\u53d1
							var action = $("#" + formId).attr("action");
							if("#" == action){
								if(btn)btn.click();
							}
							return false;
						}
					}
				});
			}
		}
		
		Selection.prototype.controlButton = function(buttonId){
			buttonId = buttonId?buttonId:this.buttonId;
			this.buttonId = buttonId;
			var formId = this.formId;
			if(typeof buttonId == "string"){
				if(buttonId && formId){
					var btn = $("#" + buttonId);
					btn.bind("click",function(event){
						$("#" + formId).submit();
					});
				}
			}else if(typeof buttonId == "object"){
				if(buttonId.length > 0 && formId){
					$.each(buttonId,function(index,value){
						var btn = $("#" + value);
						btn.bind("click",function(event){
							$("#" + formId).submit();
						});
					});
				}
			}
		}
		
		Selection.prototype.search = function(){
			var input = $("#" + this.entranceInputId);
			if(!input) {
				return;
			}
			if(!input.val()) {
				this.destroy();
				return;
			}
			if(input.val() == this.keyWord) {
				return;
			}
			this.keyWord = input.val();
			input.trigger("search");
		}
		
		Selection.prototype.init = function(){
			var data = this.data;
			if(!data) return false;
			if(data.keyWord){
				this.keyWord = data.keyWord;
			}
			if(data.bind){
				this.entranceInputId = data.bind;
				this.targetInputId = data.bind;
			}
			if(data.target){
				this.targetInputId = data.target;
			}
			if(data.prev){
				this.prevPage = data.prev;
			}
			if(data.next){
				this.nextPage = data.next;
			}
			if(data.paginationDom){
				this.paginationDom = data.paginationDom;
			}
			if(data.promptDom){
				this.promptDom = data.promptDom;
			}
			if(data.width){
				this.width = data.width;
			}
			if(data.bindHide){
				this.targetHiddenId = data.bindHide;
			}
			if(data.select){
				this.selectHandler = data.select;
			}
			if(data.search){
				this.searchHandler = data.search;
			}
			if(data.formId || data.buttonId){
				this.formId = data.formId;
				this.buttonId = data.buttonId;
			}
			if(data.buttonId){
				this.controlButton();
			}
			if(data.formId){
				this.controlForm();
			}
			var entrance = $("#" + this.entranceInputId);
			var _entrance = document.getElementById(this.entranceInputId);
			window[this.objId] = this;
			var objId = this.objId;
			entrance.attr("objid",objId).attr("autocomplete","off").attr("disableautocomplete","disableautocomplete");
			entrance.focus(function(){
				var obj = window[objId];
				if(!obj.interval){
					obj.interval = window.setInterval("window['"+objId+"'].search()",800);
				}
			})
			entrance.bind("search",this.searchHandler);
			/*entrance.keydown(function(event){
				var obj = window[objId];
				event.stopPropagation();
				obj.navigate(event);
			});*/
			/*
			_entrance.addEventListener("keydown",function(event){
				event.cancelBubble = true;
				event.stopPropagation();
				if(event.which == 13){
					event.preventDefault();
				}
				return window[objId].navigate(event);
			},false);*/
			try{
				_entrance.addEventListener("keydown",window[objId].navigate,false);
			}catch(e){
				entrance.bind("keydown",this.navigate);
			}
			//entrance.bind("keydown",this.navigate);
			entrance.blur(function(){
				var obj = window[objId];
				//obj.destroy();
				//window.clearInterval(window[objId].interval);
				obj.clearInterval();
				setTimeout("window['" + objId + "'].destroy()",200);
			});
		}
		Selection.prototype._initialized = true;
	}
	this.init();
}
