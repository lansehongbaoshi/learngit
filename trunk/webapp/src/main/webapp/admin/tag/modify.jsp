<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 <!--breadcrumbs-->

    <div class="breadcrumbs" id="breadcrumbs">
        <script type="text/javascript">
            try {
                ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) {}
        </script>
        <ul class="breadcrumb">
            <li> <i class="ace-icon fa fa-home home-icon"></i> <a href="/admin/index.action">首页</a> </li>
            <li class="active">标签管理</li>
        </ul>
        <!-- /.breadcrumb -->
    </div>
   
    <div class="page-content">
        <div class="page-header">
            <h1> 标签修改  </h1>
        </div>
        <div class="row">
            <form action="/admin/tag/update.action" method="get">
               <input type="hidden" name="id" value="<s:property value="tagData.id" />">
                <div class="col-xs-12">
                    <p>
                        所属系统：
                       <s:property value="tagData.systemData.name" />
                    </p>
                    <p>标签名称：
                        <input type="text" name="name" style="width: 400px;" value="<s:property value='tagData.name' />">
                    </p>
                    <p>
                        标签描述：
                        <input type="text" name="description" style="width: 400px;" value="<s:property value='tagData.description' />">
                    </p>

                    <p>标签排序：
                        <input id="sort" type="text" name="sort" style="width: 100px;" value="<s:property value='tagData.sort' />"> <span>（说明：1~999之间的数字,数值越大,排序越靠前）</span>
                            </p>
                    <p>默认标签：
                        <input id="" type="checkbox" name="property" style="width: 100px;" value="1" <s:if test="tagData.property==1">checked="checked"</s:if>> <span>（说明：勾上则为默认标签，一个系统只能勾一个默认标签）</span>
                            </p>   
                    
                        <div class="clear"></div>
                        <div class="clearfix form-actions">
                            <div class="col-md-offset-3 col-md-9">
                                <button class="btn btn-info" type="submit" id="modifyBtn">
                                    <i class="ace-icon fa fa-check bigger-110"></i> 修改
                                </button>

                                &nbsp; &nbsp; &nbsp;
                                <button class="btn" type="reset" onclick="history.go(-1)">
                                    <i class="ace-icon fa fa-undo bigger-110"></i> 取消
                                </button>
                            </div>
                        </div>
                    </div>
                     </form>
                </div>




            </div>
             
<script>
$(function(){
})
</script>
