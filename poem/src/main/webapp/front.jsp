<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
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
        var categoryIdF = "";
        var authorF = "";

        $(function () {
            $.ajax({
                url: "${pageContext.request.contextPath}/category/getAllCategory",
                datatype: "json",
                type: "post",
                success: function (data) {
                    $.each(data, function (index, category) {
                        var cate = $("<li><a href='#' id='" + category.id + "category_id' onclick=\"filterCategory('" + category.id + "')\">" + category.name + "</a></li>");
                        $("#category_ol").append(cate);
                    })
                }
            });


            $.ajax({
                url: "${pageContext.request.contextPath}/poem/getAll",
                datatype: "json",
                type: "post",
                success: function (data) {
                    $.each(data, function (index, author) {
                        var authorname = $("<li><a href='#' id='" + author + "author_id' onclick=\"filterAuthor('" + author + "')\">" + author + "</a></li>");
                        $("#author_ol").append(authorname);
                    })
                }
            });

            $.ajax({
                url: "${pageContext.request.contextPath}/poem/selectAll",
                datatype: "json",
                type: "post",
                success: function (data) {
                    $.each(data, function (index, poem) {
                        //onmouseover='f1()'移入  onmouseout='f2()'移出
                        var poemList = $("<div id='poemList_id' onmouseover=\"f1('" + poem.content + "','" + poem.authordes + "')\" onmouseout=\"f2()\" class=\"col-md-3\">\n" +
                            "<a href='" + poem.href + "' class=\"thumbnail\">\n" +
                            "<img src='" + poem.imagepath + "'>\n" +
                            "<ul>\n" +
                            "<li>" + poem.name + "</li>\n" +
                            "<li>" + poem.author + "." + poem.type + "</li>\n" +
                            "</ul>\n" +
                            "</a>\n" +
                            "</div>");


                        $("#poem_div").append(poemList);

                    });
                }
            })


        })

        //鼠标移入事件
        function f1(content, authordes) {
            $("#content_body").text(content);
            $("#authordes_body").text(authordes);
            $("#show_poem").attr("style", "display:inline;height:450px;width:400px");

        }

        //鼠标移出事件
        function f2() {
            $("#show_poem").attr("style", "display:none");
        }


        /*$("#poemList_id").hover(function(){
            //移入事件
            /!*$("#imgFdj")[0].src=$(this)[0].src;
            $("#imgFdj")[0].style.display="inline";*!/
        },function(){
            //移出事件
            /!*$("#imgFdj")[0].src=$(this)[0].src;
            $("#imgFdj")[0].style.display="none";*!/
        });*/

        $("body").on("mousemove",function(event){
            $("#aaaa")[0].style.left = event.pageX+10+"px";
            $("#aaaa")[0].style.top = event.pageY+10+"px";
        });

        function searchEs() {
            $("#poem_div").empty();
            var value = $("#search_val").val();
            $.ajax({
                url: "${pageContext.request.contextPath}/poem/searchEs",
                data: {"value": value, "categoryId": categoryIdF, "author": authorF},
                datatype: "json",
                type: "post",
                success: function (data) {
                    $.each(data, function (index, poem) {
                        var poemList = $("<div class=\"col-md-3\">\n" +
                            "<a href='" + poem.href + "' class=\"thumbnail\">\n" +
                            "<img src='" + poem.imagepath + "'>\n" +
                            "<ul>\n" +
                            "<li>" + poem.name + "</li>\n" +
                            "<li>" + poem.author + "." + poem.type + "</li>\n" +
                            "</ul>\n" +
                            "</a>\n" +
                            "</div>");

                        $("#poem_div").append(poemList);
                    })
                }
            });
            $("#search_val").val("");
            authorF = null;
            categoryIdF = null;
        }


        function filterCategory(id) {
            categoryIdF = id;
        }

        function filterAuthor(author) {
            authorF = author;
        }

    </script>
</head>
<body>

<div class="panel panel-default">
    <div class="panel-body">
        <div class="row">
            <div class="col-md-3 col-md-offset-5">
                <h3>唐诗-宋词检索系统</h3>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-11">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon3">检索唐诗宋词</span>
                <input type="text" class="form-control" id="search_val" aria-describedby="basic-addon3"
                       placeholder="输入检索条件">
            </div>
        </div>
        <div class="col-lg-1">
            <button type="button" class="btn btn-default" onclick="searchEs()">检索</button>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-10 col-md-offset-1">
            <%--类别--%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <ol class="breadcrumb" id="category_ol">
                        <li><a href="#">所有</a></li>
                    </ol>
                </div>
            </div>
            <%--名字展示--%>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <ol class="breadcrumb" id="author_ol">
                        <li><a href="#">所有</a></li>
                    </ol>
                </div>
            </div>


            <%--图片--%>
            <div id="poem_div">

            </div>

        </div>
    </div>

</div>

<%--古诗详情--%>
<div id="aaaa" style="height: 400px;width: 400px">
    <div id="show_poem" class="panel panel-default" style="display: none">
        <div class="panel-heading">内容</div>
        <div id="content_body" class="panel-body">
            Panel content
        </div>
        <div class="panel-heading">作者详情</div>
        <div id="authordes_body" class="panel-body">
            Panel content
        </div>
    </div>
</div>


</body>
</html>