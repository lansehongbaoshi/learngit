<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.chsi.framework.util.TimeUtil" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%

%>
<div class="breadcrumbs" id="breadcrumbs">
  <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {}
            </script>
  <ul class="breadcrumb">
    <li><i class="ace-icon fa fa-home home-icon"></i> <a href="/admin/index.action">首页</a></li>
    <li class="active">统计分析</li>
  </ul>
  <!-- /.breadcrumb -->
</div>
<div class="page-content">
  <div class="rows">
    <div class="col-xs-12">
      <div class="row">
        <div class="col-xs-12">
          <h3 class="row header smaller lighter blue">
            <span class="col-xs-7">所有"<s:property value="type" />"问题</span> <span class="col-xs-5"> 
            </span>
          </h3>
          <div>
            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
              <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable" role="grid" aria-describedby="dynamic-table_info">
                <thead>
                  <tr role="row">
                    <th width="200" class="hidden-80" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">提问时间</th>
                    <th class="hidden-180" tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">提问详情</th>
                  </tr>
                </thead>
                <tbody id="systemIds">
                  <s:iterator value="page.list" id="data">
                    <tr role="row" class="odd">
                      <td class="hidden-80"><s:date format="yyyy-MM-dd HH:mm:ss" name="#data.createTime"/></td>
                      <td class="hidden-80"><s:property value="#data.q" /></td>
                    </tr>
                  </s:iterator>
                </tbody>
              </table>
            </div>
            <s:include value="/common/pageNavigator2.jsp"></s:include>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
