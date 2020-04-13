<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    /*延迟加载*/
    $(function(){
        //初始化表格的方法
        $("#cateTable").jqGrid({
            url : "${path}/category/queryCategoryByLimit",
            editurl: "${path}/category/edit",
            datatype : "json",
            rowNum : 10,
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            rowList : [ 5, 10, 15, 20 ],
            pager : '#catePage',
            viewrecords : true,
            multiselect:true,
            subGrid : true,  //开启子表格
            colNames : [ 'Id', '类别名', '级别'],
            colModel : [
                {name : 'id',index : 'id',  width : 55,hidden:true},
                {name : 'cateName',editable: true,index : 'invdate',width : 90},
                {name : 'levels',index : 'name',width : 100}
            ],
            /**
             *subgridId、在点击一行数据时会在父表格中创建div、用来容纳子表格、subgridId就是div的id
             * rowId、就是点击一行数据的id
             * */
            subGridRowExpanded : function(subgridId, rowId) {  //设置子表格相关的属性
                //复制子表格内容
                //addSubGrid(subgridId, rowId);
                var subgridTableId= subgridId+"Table";  //定义子表格 Table的id
                var pagerId= subgridId+"Page";   //定义子表格工具栏id

                //在子表格容器中创建子表格和子表格分页工具栏
                $("#" + subgridId).html("<table id='"+subgridTableId+"' /> <div id='"+pagerId+"'>");
                //子表格
                $("#" + subgridTableId).jqGrid({
                    url:"${path}/category/queryCategoryByLimit?pId="+rowId,
                    editurl: "${path}/category/edit?pId="+rowId,
                    datatype : "json",
                    rowNum : 10,
                    styleUI:"Bootstrap",
                    height:"auto",
                    autowidth:true,
                    rowList : [ 5, 10, 15, 20 ],
                    pager : "#"+pagerId,
                    viewrecords : true,
                    multiselect:true,
                    colNames : [ 'Id', '类别名', '级别','父ID'],
                    colModel : [
                        {name : 'id',index : 'id',  width : 55,hidden:true},
                        {name : 'cateName',editable: true,index : 'invdate',width : 90},
                        {name : 'levels',index : 'name',width : 100},
                        {name : 'parentId',index : 'name',width : 100,hidden:true}
                    ]
                });
                //子表格分页工具栏
                $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId,
                    {
                        add : true, edit : true, del : true, search : false,addtext:"添加",edittext:"修改",deltext:"删除"
                    },
                    {
                        closeAfterEdit: true,
                    },
                    {
                        closeAfterAdd: true,
                    },
                    {
                        closeAfterDel:true,
                        afterSubmit:function (response) {
                            if (response.responseJSON.status=="400") {
                                //在警告框中追加内容
                                $("#showMessage").html(response.responseJSON.message+"!!!")
                                //展示警告框
                                $("#delMessage").show();
                                //自动关闭警告框
                                setTimeout(function () {
                                    $("#delMessage").hide();
                                }, 2000);
                            }
                            return "ali";
                            //刷新表单
                            $("#" + subgridTableId).trigger("reloadGrid");
                        }
                    }
                );
            }
        });
        //父表格分页工具栏
        $("#cateTable").jqGrid('navGrid', '#catePage',
            {
                add : true, edit : true, del : true, search : false,addtext:"添加",edittext:"修改",deltext:"删除"
            },
            {
                closeAfterEdit: true,
            },
            {
                closeAfterAdd: true,
            },
            {   closeAfterDel:true,
                afterSubmit:function (response) {
                    if (response.responseJSON.status==="400") {
                        //在警告框中追加内容
                        $("#showMessage").html(response.responseJSON.message+"!!!")
                        //展示警告框
                        $("#delMessage").show();
                        //自动关闭警告框
                        setTimeout(function () {
                            $("#delMessage").hide();
                        }, 2000);

                    }
                    return "ali";
                    //刷新表单
                    $("#cateTable").trigger("reloadGrid");
                }
            }
        );
    });
</script>
<div class="panel panel-primary">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>类别信息</h2>
    </div>
    <%--标签页--%>
    <ul class="nav nav-tabs">
        <li class="active"><a href="">类别管理</a></li>
    </ul>
        <%--警告框--%>
        <div id="delMessage" class="alert alert-danger" style="display: none">
            <span class="text-right" id="showMessage"></span>
        </div>
    <%--表格--%>
    <table id="cateTable"></table>
    <%--分页--%>
    <div id="catePage"></div>
</div>



