<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
    <script>
        $(function(){
            $("#btnId").click(function(){
                //构建表格、每次构建之前清空表格
                $("#myTable").empty()
                //构建表格头
                $("#myTable").append("<tr align='center'>" +
                    "<td>视频标题</td>" +
                    "<td>视频简介</td>" +
                    "<td>视频封面</td>" +
                    "<td>上传时间</td>" +
                    "</tr>")

                var content = $("#contentId").val()
                $.ajax({
                    url:"${path}/video/higHlightSearch",
                    type:"post",
                    dataType:"JSON",
                    data:{content:content},
                    success:function(data){
                        console.log(data)
                        //遍历数据放入表格中
                        $.each(data,function(index,video){
                            $("#myTable").append("<tr align='center'>" +
                                "<td>"+video.title+"</td>" +
                                "<td>"+video.brief+"</td>" +
                                "<td><video src='"+video.path+"' controls  width='150px' height='100px' /></td>" +
                                "<td>"+video.publishDate+"</td>" +
                                "</tr>")
                        })

                    }
                })
            });
        })
    </script>
</head>
<body>
    <div align="center">
        <%--搜索框--%>
        <div class="input-group" style="width: 300px">
            <input id="contentId" type="text" class="form-control" placeholder="请输入视频标题|简介" aria-describedby="basic-addon2">
            <span class="input-group-btn" id="basic-addon2">
            <button class="btn btn-info" id="btnId">搜索</button>
        </span>
        </div>
    </div>
    <%--表格--%>
    <table id="myTable" class="table table-hover table-bordered table-striped">
    </table>
</body>
</html>