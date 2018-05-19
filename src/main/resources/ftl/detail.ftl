<html xmlns:th="http://www.thymeleaf.org"
      xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{layout}">
<head>
    <title>首页</title>
    <style>
        .aa{
            background-color: #1a1a1a;
            width: 1000px;
            height: 1000px;
        }
    </style>
</head>
<body layout:fragment="content">
<div class="row">
<div class="aa"></div>
</div>
<script type="text/javascript" th:inline="javascript">

    jQuery(function($) {
        var id=[[${r"${id}"}]];
        $.ajax({
            url: contextPath+"api/${modelName?lower_case}?id="+id,
            method: "get",
            success: function (data) {
                console.log(data);
            },
            error: function (data) {
                alert("查询数据出错！");
            }
        })

    })
</script>
</body>
</html>