<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <div class="breadcrumbs" id="breadcrumbs">
        <script type="text/javascript">
            try {
                ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) {}
        </script>
        <ul class="breadcrumb">
            <li> <i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a> </li>
            <li class="active">标签管理</li>
        </ul>
        <!-- /.breadcrumb -->

        <div class="nav-search" id="nav-search">
            <form class="form-search">
                <span class="input-icon">
      <input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off">
      <i class="ace-icon fa fa-search nav-search-icon"></i> </span>
            </form>
        </div>
        <!-- /.nav-search -->
    </div>


    <div class="page-content">
        <div class="page-header">
            <h1> 标签管理 <small> <i class="ace-icon fa fa-angle-double-right"></i> 根据条件查询 </small> </h1>
        </div>
        <div class="rows">

            <div class="col-xs-12 col-md-3">
                    <select id="systemIds" class="form-control" name="systemId">
                    </select>
            </div>
            <div class="col-xs-12 col-md-9">
             <button type="button" id="listTags" class="btn btn-purple btn-sm">
				<span class="ace-icon fa fa-search icon-on-right bigger-110"></span> 搜索
                    </button>
            </div>
            
        </div>
        <div class="rows">

            <div class="col-xs-12">
                <div class="row">
                    <div class="col-xs-12">
                       
                        <h3 class="row header smaller lighter blue">
                        <span class="col-xs-7">标签列表</span>
                        <span class="col-xs-5">
                            <a  href="/htgl/tag/addIndex.action" target="_blank"  class="btn-new-mail pull-right">
														<span class="btn btn-primary no-border">
															<i class="ace-icon fa glyphicon-plus bigger-130"></i>
															<span class="bigger-110">新增</span>
														</span>
													</a>
                        </span>
                        
                        </h3>
                         <div class="table-header" id="search_table_header" style="display:none"> 搜索 "Latest Registered Domains" 的结果</div>
                         
                           <div>
                            <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                      
                                <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable" role="grid" aria-describedby="dynamic-table_info">
                                    <thead>
                                        <tr role="row">
                                            <th  class="hidden-180" tabindex="0"  aria-controls="dynamic-table" rowspan="1" colspan="1" aria-label="Domain: activate to sort column ascending">标签</th>
                                             <th  width="410"  class="hidden-200" tabindex="0"  aria-controls="dynamic-table" rowspan="1" colspan="1">描述</th> 
                                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                                        </tr>
                                    </thead>
                                    <tbody id="tags_result">
                                        <tr role="row" class="odd">
                                    
                                            <td  colspan="3"> 无数据 </td>
                                
                                        </tr>
                           
                                    </tbody>
                                </table>
                                <div class="row">
                                    <div class="col-xs-6">
                                        <div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite"></div>
                                    </div>
                                    <div class="col-xs-6">
                                        <div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate">
                                           
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                     
                    </div>
                    
                </div>
            </div>
        </div>
    </div>

    <script>
        $(function () {
            $.getJSON("/htgl/system/listSystem.action",
                function showSystems(json) {
                    if (json.flag == "true") {
                        var options = "";
                        for (var i = 0; i < json.o.length; i++) {
                            var option = json.o[i];
                            options += "<option value='" + option.id + "'>" + option.name + "</option>";
                        }
                        $("#systemIds").html(options);
                    }
                }
            );
            $("#listTags").click(function () {
                var systemId = $("#systemIds").val();
                $.getJSON("/htgl/tag/list.action", {
                        systemId: systemId
                    },
                    function showSystems(json) {
                        if (json.flag == "true") {
                            var options = "";
                            for (var i = 0; i < json.o.length; i++) {
                                var tag = json.o[i];
                                var odd_even = (i%2==0)?"even":"odd";
                            var str = " <tr role=\"row\" class=\""+odd_even+"\"><td>"+ tag.name +"</td><td class=\"hidden-260\">" + tag.description + "</td><td><div class=\"hidden-sm hidden-xs action-buttons\"><a class=\"blue\" href=\"#\"> <i class=\"ace-icon fa fa-search-plus bigger-130\"></i> </a> <a class=\"green\" title=\"修改\" target='_blank' href='/htgl/tag/updateIndex.action?id=" + tag.id +"'><i class=\"ace-icon fa fa-pencil bigger-130\"></i> </a> <a title=\"删除\" class=\"red\" href=\"#\"> <i class=\"ace-icon fa fa-trash-o bigger-130\"></i> </a></div>" + "</td></tr>";
                             
                            }
                            $("#tags_result").html(str);
                        }
                    }
                );
            });
        })
    </script>