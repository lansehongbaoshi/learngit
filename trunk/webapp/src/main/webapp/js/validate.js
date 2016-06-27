// 文件编码使用 utf-8
function checkForm(oForm){
    var els = oForm.elements;
    for(var i=0;i < els.length;i++){
    	if(els[i].disabled){
				continue;
    	}else{
    		var sVal = getValue(els[i]);
    		if (els[i].type == "text") {// 验证输入类型的长度
	      	if (els[i].getAttribute("maxlength") != null) {
	      		var num = els[i].getAttribute("maxlength");
		      	if (lengthWithCN(sVal) > num) {
		      		  alert("输入超长！");
	              goBack(els[i]);
	              return false;
		      	}
	      	}
	    		var specialReg = new RegExp("%|'");// 输入的特殊字符
					if (specialReg.test(sVal)) {// TODO真实需要输入特殊字符时某处理
						alert("输入中含有非法字符！");
						goBack(els[i]);
						return false;
				  }
	      }
	      if(els[i].getAttribute("check")){// 下拉框和输入都验证
			    var sReg = els[i].getAttribute("check");
			    var reg = new RegExp(sReg,"i");
			    if(!reg.test(sVal)){
			        alert(els[i].getAttribute("warn"));
			        goBack(els[i]);
			        return false;
			    }
		    }
      }
   }
   return true;
}

function getValue(el){
    var sType = el.type;
    switch(sType){
        case "text": return el.value;
        case "hidden":
        case "password":
        case "file":
        case "textarea": return el.value;
        case "checkbox":
        case "radio": return getValueChoose(el);
        case "select-one":
        case "select-multiple": return getValueSel(el);
    }
    function getValueChoose(el){
        var sValue = "";
        var tmpels = document.getElementsByName(el.name);
        for(var i=0;i < tmpels.length;i++){
            if(tmpels[i].checked){
                sValue += "0";
            }
        }
        return sValue;
    }
    function getValueSel(el){
        var sValue = "";
        for(var i=0;i < el.options.length;i++){
            if(el.options[i].selected && el.options[i].value != ""){
                sValue += "0";
            }
        }
        return sValue;
    }
}

function goBack(el){
    var sType = el.type;
    switch(sType){
        case "text":
        case "password":
        case "file":
        case "textarea": el.focus();break;
        case "checkbox":
        case "radio": var els = document.getElementsByName(el.name);els[0].focus();break;
        case "select-one":
        case "select-multiple":el.focus();break;
    }
}

function isSfzh(str){
  var reg = /^\d{15}$|^\d{18}$|^\d{17}X$/;
  return reg.test(str);
}

function lengthWithCN(Str) {
    var escStr = escape(Str);
    var numI = 0;
    var escStrlen = escStr.length;
    for (var i = 0;  i < escStrlen;  i++) {
        if(escStr.charAt(i) == '%') // 编码改变
            if(escStr.charAt(++i) == 'u') // 双字节编码
                numI ++;
    }
    return Str.length + numI;
}
