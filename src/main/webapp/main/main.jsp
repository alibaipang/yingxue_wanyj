<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学APP的后台管理系统</title>
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
</head>
<body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <button class="navbar-toggle collapsed" data-target="#d1" data-toggle="collapse" type="button">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="">应学视频APP后台管理系统</a>
            </div>

            <div class="collapse navbar-collapse" id="d1">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="">欢迎：<strong class="text-primary">${sessionScope.admin.username}</strong></a></li>
                    <li><a href="${path}/Admin/loginOut"><span class="glyphicon glyphicon-log-out"></span>&nbsp;退出登录</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <%--主体部分--%>
    <div class="container-fluid">
        <div class="row">
            <%--左侧手风琴--%>
            <div class="col-md-2">
                <div class="panel-group text-center " id="accordion" role="tablist" aria-multiselectable="true" >
                    <div class="panel panel-info">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    用户管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="JavaScript:$('#main').load('${path}/user/userList.jsp')">用户展示</a></li>
                                    <li><a href="JavaScript:$('#main').load('${path}/goeasy/goeasy.jsp')">用户统计</a></li>
                                    <li><a href="JavaScript:$('#main').load('${path}/echarts/china.jsp')">用户分布</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-success">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                    分类管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                            <div class="panel-body">
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="javaScript:$('#main').load('${path}/category/category.jsp')">类别展示</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-warning">
                        <div class="panel-heading" role="tab" id="headingThree">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                    视频管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="panel-body">
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="javaScript:$('#main').load('${path}/video/videoList.jsp')">视频展示</a></li>
                                    <li><a href="javaScript:$('#main').load('${path}/video/searchVideo.jsp')">视频搜索</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-primary">
                        <div class="panel-heading" role="tab" id="headingFore">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFore" aria-expanded="false" aria-controls="collapseThree">
                                    日志管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFore" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="panel-body">
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="javaScript:$('#main').load('${path}/log/logList.jsp')">日志展示</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div class="panel panel-danger">
                        <div class="panel-heading" role="tab" id="headingFive">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="false" aria-controls="collapseThree">
                                    反馈管理
                                </a>
                            </h4>
                        </div>
                        <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                            <div class="panel-body">
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="javaScript:$('#main').load('${path}/feedback/feedbackList.jsp')">反馈展示</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <%--右侧主体--%>
            <div class="col-md-10" id="main">
                <%--巨幕--%>
                <div class="jumbotron text-center">
                    <h2>欢迎来到应学视频APP后台管理系统</h2>
                </div>
                <%--轮番图--%>
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" align="center">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                    </ol>

                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox">
                        <div class="item active">
                            <img src="${path}/bootstrap/img/pic4.jpg">
                            <div class="carousel-caption">
                                广告一
                            </div>
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/pic3.jpg">
                            <div class="carousel-caption">
                                广告二
                            </div>
                        </div>
                        <div class="item">
                            <img src="${path}/bootstrap/img/pic1.jpg">
                            <div class="carousel-caption">
                                广告三
                            </div>
                        </div>
                    </div>
                    <!-- Controls -->
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>

            </div
        </div>
    </div>
    <%--页脚--%>
    <div class="panel-footer">
        <p class="text-center">@百知教育 wanyj@zparkhr.com</p>
    </div>
</body>
</html>
