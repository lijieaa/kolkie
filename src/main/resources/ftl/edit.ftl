<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{layout}">
<body layout:fragment="content">
<div class="row">
    <div class="col-xs-12">
        <div class="row">
            <form class="form-horizontal" id="form" method="post">

                <div class="form-group">
                    <label class="control-label col-xs-12 col-sm-3 no-padding-right" for="title">title:</label>
                    <input type="hidden" name="tId" id="tId">
                    <div class="col-xs-12 col-sm-9">
                        <div class="clearfix">
                            <input type="text" name="title" id="title" class="col-xs-12 col-sm-6" />
                        </div>
                    </div>
                </div>

                <div class="space-4"></div>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button class="btn btn-info" type="submit" id="submit">
                            <i class="ace-icon fa fa-check bigger-110"></i>
                            提交
                        </button>

                        &nbsp; &nbsp; &nbsp;
                        <button class="btn" type="reset">
                            <i class="ace-icon fa fa-undo bigger-110"></i>
                            重置
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <!-- PAGE CONTENT ENDS -->
    </div>
    <!-- /.col -->
</div>
<script type="text/javascript" th:inline="javascript">

    jQuery(function($) {
        var id=[[${id}]]
        $.ajax({
            url: contextPath+"api/news?id="+id,
            method: "get",
            success: function (data) {
                $('#form').autofill(data.content);
            },
            error: function (data) {
                alert("查询数据出错！");
            }
        })




        var $form = $('#form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: false,
            ignore: "",
            rules: {
                title: {
                    required: true
                }
            },

            messages: {
                title: {
                    required: "title不能为空！",
                }
            },


            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },

            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error');
                $(e).remove();
            },

            errorPlacement: function (error, element) {
                 error.insertAfter(element.parent());
            },

            submitHandler: function (form) {

                var data = $(form).serializeObject();

                $.ajax({
                    url: contextPath+"api/news/",
                    method: "put",
                    data : JSON.stringify(data),
                    contentType: "application/json",
                    dataType : "json",
                    success: function (data) {
                        location.href=contextPath+"news/list";
                    },
                    error: function (data) {
                        console.log(data);
                    }
                })


            },
            invalidHandler: function (form) {

            }
        });
    })
</script>
</body>
</html>
</body>
</html>