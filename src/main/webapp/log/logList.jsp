<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        $("#myTable").jqGrid({
            url: "${path}/log/findAllByLimit",
            datatype : "json",
            rowNum : 10,
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            rowList : [ 5, 10, 15, 20 ],
            pager : '#page',
            viewrecords : true,
            colNames: ["ID", "操作者", "操作时间", "操作的结果", "签名操作的事"],
            styleUI: "Bootstrap",
            colModel: [
                {name: "id", hidden: true},
                {name: "name",  align: "center"},
                {name: "date", align: "center"},
                {name: "result",align: "center"},
                {name: "what",align: "center"},
            ]
        })
        //表格工具栏
        $("#myTable").jqGrid('navGrid', '#page',
            {edit: false, add: false, del: false,search:false},
            /**
             * 修改、添加、删除 之后的额外操作----关闭对话框*/
            {},
            {},
            {}
        );

    });
</script>
<div class="panel panel-primary">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>日志信息</h2>
    </div>
    <%--标签页--%>
    <ul class="nav nav-tabs">
        <li class="active"><a href="">日志管理</a></li>
    </ul>
    <%--表格--%>
    <table id="myTable"></table>
    <%--分页--%>
    <div id="page"></div>
</div>