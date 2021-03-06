<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{layout}">
<body layout:fragment="content">
<div class="page-header">
    <h1>
        Tables
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            Static &amp; Dynamic Tables
        </small>
    </h1>
</div>
    <div class="row">
        <div class="col-xs-12">
            <div class="row">
                <div class="col-xs-12 widget-container-col" id="widget-container-col-1">
                    <div class="widget-box" id="widget-box-1">
                        <div class="widget-header">
                            <h5 class="widget-title">筛选</h5>

                            <div class="widget-toolbar">
                                <a href="#" data-action="collapse">
                                    <i class="ace-icon fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main">
                                <form class="form-horizontal" id="search-form">
<#list cols?keys as key>
    <#if (key?index_of('primaryKey'))!=-1>
        <#assign index=key?index_of('primaryKey')>
        <div class="form-group">
            <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="${key?substring(0,index-1)}">${key?substring(0,index-1)}:</label>
            <div class="col-xs-12 col-sm-9">
                <div class="clearfix">
                    <input type="text" name="${key?substring(0,index-1)}" id="${key?substring(0,index-1)}" class="col-xs-12 col-sm-6" />
                </div>
            </div>
        </div>
    <#else>
        <div class="form-group">
            <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="${key}">${key}:</label>
            <div class="col-xs-12 col-sm-9">
                <div class="clearfix">
                    <input type="text" name="${key}" id="${key}" class="col-xs-12 col-sm-6" />
                </div>
            </div>
        </div>
    </#if>

</#list>

                                    <div class="clearfix form-actions">
                                        <div class="col-md-offset-3 col-md-9">
                                            <button class="btn btn-info" type="button" id="search">
                                                <i class="ace-icon fa fa-check bigger-110"></i>
                                                搜索
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="space-6"></div>
            <div class="row">
                <div class="col-xs-12">

                    <div class="clearfix">
                        <div class="pull-left tableTools-container-left">
                            <div class="btn-group">
                                <a type="button" class="btn" th:href="@{/${modelName?lower_case}/add}" sec:authorize="hasAuthority('${modelName?lower_case}:add')">创建</a>
                                <button type="button" class="btn btn-danger" id="batch-delete" sec:authorize="hasAuthority('${modelName?lower_case}:delete')">批量删除</button>
                            </div>
                        </div>
                        <div class="pull-right tableTools-container"></div>
                    </div>

                    <!-- div.table-responsive -->

                    <!-- div.dataTables_borderWrap -->
                    <div>
                        <table id="dynamic-table" class="table table-striped table-bordered table-hover">
                            <thead>
                            <tr>
                                <th class="center">
                                    <label class="pos-rel">
                                        <input type="checkbox" class="ace"/>
                                        <span class="lbl"></span>
                                    </label>
                                </th>
<#list cols?keys as key>
    <#if (key?index_of('primaryKey'))!=-1>
        <#assign index=key?index_of('primaryKey')>
        <th>${key?substring(0,index-1)}</th>
    <#else>
        <th>${key}</th>
    </#if>

</#list>
                                <th></th>
                            </tr>
                            </thead>

                            <tbody>
                                <td class=" center"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"></span> </label></td>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" th:inline="javascript">
        jQuery(function ($) {
            //initiate dataTables plugin
            var myTable =$('#dynamic-table')
                        //.wrap("<div class='dataTables_borderWrap' />")   //if you are applying horizontal scrolling (sScrollX)
                            .DataTable({
                                bAutoWidth: false,
                                "language": {
                                    "url": contextPath+"ace/assets/js/zh_CN.json"
                                },
                                'order': [],
                                "ordering": true,
                                "processing": true,
                                'paging': true,
                                "serverSide": true,
                                'searching': false,
                                "ajax": function (data, callback, settings) {
                                    var formData = $("#search-form").serialize();
                                    console.log(formData);
                                    var pageNum=data.start/data.length+1;
                                    var pageSize=data.length;
                                    var draw=data.draw;

                                    var orderByClause="";

                                    for(var i=0;i<data.order.length;i++){
                                        var obj=data.order[i];
                                        var col=obj.column;
                                        var dir=obj.dir;
                                        var pro = data.columns[col].data;
                                        orderByClause+=pro+" "+dir+",";
                                    }
                                    orderByClause = orderByClause.substr(0,orderByClause.lastIndexOf(","));

                                    var param="?pageNum="+pageNum+"&pageSize="+pageSize+"&"+formData+"&draw="+draw+"&od="+orderByClause;
                                    $.ajax({
                                        "url": contextPath+"api/${modelName?lower_case}/page"+param,
                                        "type": "get",
                                        dataType:"json",
                                        contentType:'application/json',
                                        "success": function (json) {
                                            if (json.status == 200) {
                                                var data = {};
                                                data.recordsTotal = json.content.recordsTotal;
                                                data.recordsFiltered = json.content.recordsFiltered;
                                                data.data = json.content.data;
                                                callback(data);
                                            }
                                        },
                                        "error": function (XMLHttpRequest, textStatus, errorThrown) {
                                            console.log(errorThrown);
                                        }
                                    });
                                },
                                "columns": [
                                    {"data": null},
<#list cols?keys as key>
    <#if (key?index_of('primaryKey'))!=-1>
        <#assign index=key?index_of('primaryKey')>
                                    {"data": "${key?substring(0,index-1)}"},
    <#else >
        {"data": "${key}"},
    </#if>
</#list>
                                    {"data": null}
                                ],
                                "columnDefs": [
                                    {
                                        "targets": 0,
                                        'searchable': false,
                                        'orderable': false,
                                        "data": null,
                                        "render": function (data, type, row) {
                                            return '<label class="pos-rel"><input type="checkbox" class="ace" /><span class="lbl"></span> </label>';
                                        },
                                        "className": "center"

                                    },
<#list cols?keys as key>
                                    {
                                        "targets": ${key_index+1},
                                        "orderable": true,
                                        "searchable": true,
                                        "render": function (data, type, row) {
                                            return '<td>' + data + '</td>';
                                        }

                                    },
</#list>
                                    {
                                        "targets": -1,
                                        "orderable": false,
                                        data: null,
                                        "render": function (data, type, row) {
                                            return '<div class="hidden-sm hidden-xs action-buttons">'
                                                    [# sec:authorize="hasAuthority('${modelName?lower_case}:view')"]
                                            + '<a class="blue" href="detail/?id='+data.${primaryKey}+'"><i class="ace-icon fa fa-search-plus bigger-130"></i></a>'
                                                    [/]
                                            [# sec:authorize="hasAuthority('${modelName?lower_case}:edit')"]
                                            + '<a class="green" href="edit/?id='+data.${primaryKey}+'"><i class="ace-icon fa fa-pencil bigger-130"></i></a>'
                                                    [/]
                                            [# sec:authorize="hasAuthority('${modelName?lower_case}:delete')"]
                                            + '<a class="red delete" data-id="'+data.${primaryKey}+'"><i class="ace-icon fa fa-trash-o bigger-130"></i></a>'
                                                    [/]
                                            + '</div><div class="hidden-md hidden-lg"><div class="inline pos-rel"><button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto"><i class="ace-icon fa fa-caret-down icon-only bigger-120"></i></button>'
                                            + '<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">'
                                                    [# sec:authorize="hasAuthority('${modelName?lower_case}:view')"]
                                            + '<li><a href="detail/?id='+data.${primaryKey}+'" class="tooltip-info" data-rel="tooltip" title="查看"><span class="blue"><i class="ace-icon fa fa-search-plus bigger-120"></i></span></a></li>'
                                                    [/]
                                            [# sec:authorize="hasAuthority('${modelName?lower_case}:edit')"]
                                            + '<li><a href="edit/?id='+data.${primaryKey}+'" class="tooltip-success" data-rel="tooltip" title="编辑"><span class="green"><i class="ace-icon fa fa-pencil-square-o bigger-120"></i></span></a></li>'
                                                    [/]
                                            [# sec:authorize="hasAuthority('${modelName?lower_case}:delete')"]
                                            + '<li><a class="tooltip-error delete" data-rel="tooltip" title="删除" data-id="'+data.${primaryKey}+'"><span class="red"><i class="ace-icon fa fa-trash-o bigger-120"></i></span></a></li>'
                                                    [/]
                                            +'</ul></div></div>';
                                        }

                                    }
                                ],
                                select: {
                                    style: 'multi'
                                }
                            });


            $.fn.dataTable.Buttons.defaults.dom.container.className = 'dt-buttons btn-overlap btn-group btn-overlap';

            new $.fn.dataTable.Buttons(myTable, {
                buttons: [
                    {
                        "extend": "colvis",
                        "text": "<i class='fa fa-search bigger-110 blue'></i> <span class='hidden'>显示/隐藏 列</span>",
                        "className": "btn btn-white btn-primary btn-bold",
                        columns: ':not(:first):not(:last)'
                    },
                    {
                        "extend": "copy",
                        "text": "<i class='fa fa-copy bigger-110 pink'></i> <span class='hidden'>拷贝到剪切板</span>",
                        "className": "btn btn-white btn-primary btn-bold"
                    },
                    {
                        "extend": "csv",
                        "text": "<i class='fa fa-database bigger-110 orange'></i> <span class='hidden'>导出CVS</span>",
                        "className": "btn btn-white btn-primary btn-bold"
                    },
                    {
                        "extend": "excel",
                        "text": "<i class='fa fa-file-excel-o bigger-110 green'></i> <span class='hidden'>导出Excel</span>",
                        "className": "btn btn-white btn-primary btn-bold"
                    },
                    {
                        "extend": "pdf",
                        "text": "<i class='fa fa-file-pdf-o bigger-110 red'></i> <span class='hidden'>导出PDF</span>",
                        "className": "btn btn-white btn-primary btn-bold"
                    },
                    {
                        "extend": "print",
                        "text": "<i class='fa fa-print bigger-110 grey'></i> <span class='hidden'>打印</span>",
                        "className": "btn btn-white btn-primary btn-bold",
                        autoPrint: false,
                        message: 'This print was produced using the Print button for DataTables'
                    }
                ]
            });
            myTable.buttons().container().appendTo($('.tableTools-container'));



            //style the message box
            var defaultCopyAction = myTable.button(1).action();
            myTable.button(1).action(function (e, dt, button, config) {
                defaultCopyAction(e, dt, button, config);
                $('.dt-button-info').addClass('gritter-item-wrapper gritter-info gritter-center white');
            });


            var defaultColvisAction = myTable.button(0).action();
            myTable.button(0).action(function (e, dt, button, config) {

                defaultColvisAction(e, dt, button, config);


                if ($('.dt-button-collection > .dropdown-menu').length == 0) {
                    $('.dt-button-collection')
                            .wrapInner('<ul class="dropdown-menu dropdown-light dropdown-caret dropdown-caret" />')
                            .find('a').attr('href', '#').wrap("<li />")
                }
                $('.dt-button-collection').appendTo('.tableTools-container .dt-buttons')
            });

            ////

            setTimeout(function () {
                $($('.tableTools-container')).find('a.dt-button').each(function () {
                    var div = $(this).find(' > div').first();
                    if (div.length == 1) div.tooltip({container: 'body', title: div.parent().text()});
                    else $(this).tooltip({container: 'body', title: $(this).text()});
                });
            }, 500);


            myTable.on('select', function (e, dt, type, index) {
                if (type === 'row') {
                    $(myTable.row(index).node()).find('input:checkbox').prop('checked', true);
                }
            });
            myTable.on('deselect', function (e, dt, type, index) {
                if (type === 'row') {
                    $(myTable.row(index).node()).find('input:checkbox').prop('checked', false);
                }
            });


            /////////////////////////////////
            //table checkboxes
            $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

            //select/deselect all rows according to table header checkbox
            $('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function () {
                var th_checked = this.checked;//checkbox inside "TH" table header

                $('#dynamic-table').find('tbody > tr').each(function () {
                    var row = this;
                    if (th_checked) myTable.row(row).select();
                    else  myTable.row(row).deselect();
                });
            });

            //select/deselect a row when the checkbox is checked/unchecked
            $('#dynamic-table').on('click', 'td input[type=checkbox]', function () {
                var row = $(this).closest('tr').get(0);
                if (this.checked) myTable.row(row).deselect();
                else myTable.row(row).select();
            });


            $(document).on('click', '#dynamic-table .dropdown-toggle', function (e) {
                e.stopImmediatePropagation();
                e.stopPropagation();
                e.preventDefault();
            });




            $('[data-rel="tooltip"]').tooltip({placement: tooltip_placement});

            function tooltip_placement(context, source) {
                var $source = $(source);
                var $parent = $source.closest('table')
                var off1 = $parent.offset();
                var w1 = $parent.width();

                var off2 = $source.offset();

                if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2)) return 'right';
                return 'left';
            }

            [# sec:authorize="hasAuthority('${modelName?lower_case}:delete')"]
            //单个删除
            $('#dynamic-table tbody').on( 'click', 'a.delete', function () {
                var id = $(this).attr('data-id');
                bootbox.confirm( {
                    message: "您真的要删除？",
                    buttons: {
                        confirm: {
                            label: '确定',
                            className: 'btn-success'
                        },
                        cancel: {
                            label: '取消',
                            className: 'btn-danger'
                        }
                    },
                    callback: function (result) {

                       if(result){
                           $.ajax({
                               url: contextPath+"api/${modelName?lower_case}/?id="+id,
                               method: "delete",
                               success: function (data) {
                                   if(data.status==200){
                                       myTable.draw();
                                   }
                               },
                               error: function (data) {
                                   //console.log(data);
                               }
                           })
                       }
                    }
                });

            } );
            [/]
            [# sec:authorize="hasAuthority('${modelName?lower_case}:delete')"]
            $("#batch-delete").on("click", function () {

                var rows = myTable.rows('.selected').data();

                if(rows.length<=0){
                    bootbox.alert("请选择你要删除的对象!");
                    return;
                }
                bootbox.confirm( {
                    message: "您真的要删除？",
                    buttons: {
                        confirm: {
                            label: '确定',
                            className: 'btn-success'
                        },
                        cancel: {
                            label: '取消',
                            className: 'btn-danger'
                        }
                    },
                    callback: function (result) {
                        if(result){

                            var rows = myTable.rows('.selected').data();

                            var ids=[];

                            for(var i=0;i<rows.length;i++){
                                ids.push(rows[i].${primaryKey});
                            }

                            console.log(ids);

                            $.ajax({
                                url: contextPath+"api/${modelName?lower_case}/batch",
                                method : "delete" ,
                                data : JSON.stringify(ids),
                                contentType: "application/json",
                                dataType : "json",
                                success: function (data) {
                                    if(data.status==200){
                                        myTable.draw();
                                    }
                                },
                                error: function (data) {
                                    console.log(data);
                                }
                            })
                        }
                    }
                });
            })
                    [/]


            $("#search").on("click",function(e){
                myTable.draw();
            })
        })
    </script>
</body>
</html>
