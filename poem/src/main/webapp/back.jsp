<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <%--引入bootstrap的核心css--%>
    <link rel="stylesheet" href="boot/css/bootstrap.min.css">
    <%--引入jqgrid的核心css--%>
    <%--jqgird的主题css--%>
    <link rel="stylesheet" href="jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入jquery的js--%>
    <script src="boot/js/jquery-3.3.1.min.js"></script>
    <%--引入bootstrap的js--%>
    <script src="boot/js/bootstrap.min.js"></script>
    <%--jqgird的国际化js--%>
    <script src="jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%--jqgird的js--%>
    <script src="jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="boot/js/ajaxfileupload.js"></script>
    <script src="boot/js/china.js"></script>
    <script>
        $(function () {
            $("#tb").jqGrid({
                styleUI: "Bootstrap",
                url: "${pageContext.request.contextPath}/poem/getAllPoem",
                datatype: "json",
                mtype: "post",
                //宽度自动匹配
                autowidth: true,
                //分页 rowNum:每页展示几条 rowList:每页展示几条的集合
                pager: "#page",
                rowNum: 10,
                rowList: [10, 5, 15],
                //显示总条数
                viewrecords: true,
                //全选(在表的第一列添加复选框)
                multiselect: true,
                //增删改的时候发送请求的位置
                editurl: "${pageContext.request.contextPath}/poem/edit",
                colNames: ["ID", "诗词名", "作者", "类型", "来源","类别","内容", "作者简介"],
                colModel: [
                    {
                        name: "id"
                    },
                    {
                        name: "name",
                        editable: true
                    },
                    {
                        name: "author",
                        editable: true
                    },
                    {
                        name: "type",
                        editable: true
                    },
                    {
                        name: "origin",
                        editable: true
                    },
                    {
                        name: "categoryId",
                        hidden:true,
                        editrules: { edithidden: true },
                        editable: true,
                        /*edittype:可以编辑的类型 editoptions:编辑的一系列选项*/
                        edittype:"select",
                        editoptions:{
                            dataUrl:"${pageContext.request.contextPath}/category/getAll"
                        }
                    },
                    {
                        name: "content",
                        editable: true
                    },
                    {
                        name: "authordes",
                        editable: true
                    }
                ],
            }).jqGrid("navGrid", "#page",
                {//指定前台页面按钮组展示不展示
                    refresh: false, search: false
                },
                {//修改按钮之前或者之后执行的 (beforeShowForm/afterSubmit)
                    closeAfterEdit: true
                },
                {//添加按钮之前或者之后执行的
                    closeAfterAdd: true
                },
                {//删除按钮之前或者之后执行的

                });



            $.ajax({
                url:"${pageContext.request.contextPath}/poem/hot",
                datatype:"json",
                type:"post",
                success:function (data) {
                    $.each(data,function (key,value) {
                       var hotbtn = $("<button class=\"btn btn-primary\" type=\"button\">"+key+"" +
                           "<span class=\"badge\">"+value+"</span>\n" +
                           "</button>") ;
                       $("#hot_body").append(hotbtn);
                    });
                }
            })
        });
        
        function addEs() {
            $.ajax({
                url:"${pageContext.request.contextPath}/poem/addEs",
                datatype:"json",
                type:"post",
                success:function (data) {
                    alert("添加成功")
                }
            })
        }

        function deleteEs() {
            $.ajax({
                url:"${pageContext.request.contextPath}/poem/deleteEs",
                datatype:"json",
                type:"post",
                success:function (data) {
                    alert("清空成功")
                }
            })
        }
    </script>
</head>
<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">唐诗宋词后台管理系统</a>
        </div>
        <div style="padding-top: 5px">
            <button type="button" class="btn btn-danger" onclick="deleteEs()">清空ES所有文档</button>
            <button type="button" class="btn btn-primary" onclick="addEs()">重建ES索引</button>
        </div>
    </div><!-- /.container-fluid -->
</nav>

    <div class="container-fluid">
        <table id="tb" style="height: 400px"></table>
        <div id="page" style="height: 35px"></div>
    </div>

<br>

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-6">
            <div class="panel panel-default">
                <div class="panel-heading">全网热搜榜</div>
                <div id="hot_body" class="panel-body">

                </div>
            </div>
        </div>

        <div class="col-xs-6">
            <div class="input-group">
                <input type="text" class="form-control" placeholder="输入热词" aria-describedby="basic-addon2">
                <span class="input-group-addon" id="basic-addon2">添加远程词典</span>
            </div>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>Warning!</strong> Better check yourself, you're not looking too good.
            </div>
        </div>



    </div>
</div>


</body>
</html>