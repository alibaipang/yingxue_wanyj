<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        $("#myTable").jqGrid({
            url: "${path}/video/queryAllByLimit",
            editurl: "${path}/video/edit",
            datatype : "json",
            rowNum : 5,
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            rowList : [ 5, 10, 15, 20 ],
            pager : '#page',
            viewrecords : true,
            multiselect:true,
            colNames: ["ID", "名称", "视频", "上传时间", "描述", "所属类别", "类别ID", "用户id"],
            colModel: [
                {name: "id", hidden: true},
                {name: "title", editable: true, align: "center"},
                {
                    name: "path", editable: true, align: "center", edittype: "file",
                    //参数;列的值,操作,行对象
                    formatter: function (cellvalue, options, rowObject) {
                        return "<video src='"+rowObject.path+"' controls  width='150px' height='100px' />";
                    }
                },
                {name: "publishDate", align: "center"},
                {name: "brief", editable: true, align: "center"},
                {name: "category.cateName", align: "center"},
                {name: "categoryId", hidden: true, align: "center",},
                {name: "userId", hidden: true, align: "center"},
            ]
        })
        //表格工具栏
        $("#myTable").jqGrid('navGrid', '#page',
            {
                add : true, edit : true, del : true, search : false,addtext:"添加",edittext:"修改",deltext:"删除"
            },
            /**
             * 修改、添加、删除 之后的额外操作----关闭对话框*/
            {
                closeAfterEdit: true,
                afterSubmit:function(response){
                    $.ajaxFileUpload({
                        url: "${path}/video/fileUpLoadAliYun",
                        fileElementId: "path",
                        type: "post",
                        dataType: "json",
                        data: {id: response.responseText},
                        success: function () {
                            //刷新表单、两种方式解决问题
                            //1、将dataTYpe的值改为text类型（推荐）
                            //2、后台的方法返回值为Int类型、一json的形式返回
                            $("#myTable").trigger("reloadGrid");
                        }
                    })
                    //返回值、防止对话框不发关闭
                    return "ali";
                }
            },
            {
                closeAfterAdd: true,
                afterSubmit:function(response){
                    $.ajaxFileUpload({
                        url: "${path}/video/fileUpLoadAliYun",
                        fileElementId: "path",
                        type: "post",
                        dataType: "json",
                        data: {id: response.responseText},
                        success: function () {
                            //刷新表单、两种方式解决问题
                            //1、将dataTYpe的值改为text类型（推荐）
                            //2、后台的方法返回值为Int类型、一json的形式返回
                            $("#myTable").trigger("reloadGrid");
                        }
                    })
                    //返回值、防止对话框不发关闭
                    return "ali";
                }
            },
            {}
        );

    });

</script>
<div class="panel panel-primary">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>视频信息</h2>
    </div>
    <%--标签页--%>
    <ul class="nav nav-tabs">
        <li class="active"><a href="">视频管理</a></li>
    </ul>
    <%--表格--%>
    <table id="myTable"></table>
    <%--分页--%>
    <div id="page"></div>
</div>