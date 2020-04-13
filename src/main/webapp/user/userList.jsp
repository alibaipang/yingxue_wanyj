<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        $("#myTable").jqGrid({
            url: "${path}/user/findAllByLimit",
            editurl: "${path}/user/edit",
            datatype : "json",
            rowNum : 10,
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            rowList : [ 5, 10, 15, 20 ],
            pager : '#page',
            viewrecords : true,
            multiselect:true,
            colNames: ["ID", "姓名","性别","电话", "头像", "签名", "微信", "状态", "注册时间","地址"],
            styleUI: "Bootstrap",
            colModel: [
                {name: "id", hidden: true},
                {name: "username", editable: true, align: "center"},
                {name: "sex", editable: true, align: "center"},
                {name: "phone", editable: true, align: "center"},
                {
                    name: "headImg", editable: true, align: "center", edittype: "file",
                    //参数;列的值,操作,行对象
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='"+rowObject.headImg+"' width='100px' height='50px' />";
                    }
                },
                {name: "sign", editable: true, align: "center"},
                {name: "wechat", editable: true, align: "center"},
                {
                    name: "status", align: "center",
                    formatter: function (cellvalue, options, rowObject) {
                        console.log(rowObject)
                        if (cellvalue == 1) {
                            return "<button class='btn btn-success' onclick=" + "change(" + cellvalue + ",\"" + rowObject.id + "\"" + ")" + ">冻结</button>";
                        } else {
                            return "<button class='btn btn-danger' onclick=" + "change(" + cellvalue + ",\"" + rowObject.id + "\"" + ")" + ">解冻</button>";
                        }



                    }
                },
                {name: "createDate", align: "center"},
                {name: "address", editable: true, align: "center"},
            ]
        })
        //表格工具栏
        $("#myTable").jqGrid('navGrid', '#page',
            {edit: true, add: true, del: true, addtext: "添加", edittext: "修改", deltext: "删除"},
            /**
             * 修改、添加、删除 之后的额外操作----关闭对话框*/
            {
                closeAfterEdit: true,
                /**文件上传
                 * fileElementId、需要上传文件域的id、也就是input框的id、也就是对应的属性
                 * 后台将添加的id一json的形式相应在页面存在responseText里面*/
                afterSubmit: function (response) {
                    console.log(response)
                    $.ajaxFileUpload({
                        url: "${path}/user/fileUpLoadAliYun",
                        fileElementId: "headImg",
                        type: "post",
                        dataType: "json",
                        data: {id: response.responseText},
                        success: function () {
                            //刷新表单
                            $("#myTable").trigger("reloadGrid");
                        }
                    })
                    //返回值、防止对话框不发关闭
                    return "ali";
                }
            },
            {
                closeAfterAdd: true,
                /**文件上传
                 * fileElementId、需要上传文件域的id、也就是input框的id、也就是对应的属性
                 * 后台将添加的id一json的形式相应在页面存在responseText里面*/
                afterSubmit: function (response) {
                    console.log(response)
                    $.ajaxFileUpload({
                        url: "${path}/user/fileUpLoadAliYun",
                        fileElementId: "headImg",
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
    /*改变用户的状态*/
    function change(status,id) {
        $.ajax({
            url:"${path}/user/update",
            type:"post",
            datatype:"json",
            data:{id:id,status:status},
            success:function(){
                //刷新表单
                $("#myTable").trigger("reloadGrid");
            }
        })
    }
    /*发送手机验证码*/
    $("#basic-addon2").click(function(){
        var phone = $("#ipt").val()
        if(phone==""){
            $("#showMessage").html("手机号不能为空")
            //展示警告框
            $("#delMessage").show();
            //自动关闭警告框
            setTimeout(function () {
                $("#delMessage").hide();
            }, 2000);
        }
        else{
            var re = /^1\d{10}$/;
            if (re.test(phone)) {
                $.ajax({
                    url:"${path}/user/sendPhone",
                    type:"post",
                    datatype:"json",
                    data:{phoneNumber:phone},
                    success:function(){}
                })
            } else {
                $("#showMessage").html("手机号格式错误")
                //展示警告框
                $("#delMessage").show();
                //自动关闭警告框
                setTimeout(function () {
                    $("#delMessage").hide();
                }, 2000);
            }
        }
    })
    /*导出用户的数据信息*/
    $("#option1").click(function(){
        $.ajax({
            url:"${path}/poi/poi",
            type:"post",
            datatype:"json",
            data:{},
            success:function(){
                $("#showMessage").html("导出成功")
                //展示警告框
                $("#delMessage").show();
                //自动关闭警告框
                setTimeout(function () {
                    $("#delMessage").hide();
                }, 2000);
            }
        })
    })
</script>
<div class="panel panel-primary">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>
    <%--标签页--%>
    <ul class="nav nav-tabs">
        <li class="active"><a href="">用户管理</a></li>
        <li style="float: right;width: 1027px">
            <%--警告框--%>
            <div id="delMessage" class="alert alert-danger" style="display: none">
                <span class="text-center" style="color: red" id="showMessage"></span>
            </div>
        </li>

    </ul>
    <%--按钮组--%>
    <div class="btn-group text-left" data-toggle="buttons" align="left">
            <button class="btn btn-primary" id="option1">导出用户的信息</button>
            <button class="btn btn-success">导入用户</button>
            <button class="btn btn-info">测试按钮</button>
        <div class="input-group text-right" >
            <input  type="text"  id = "ipt" class="form-control" placeholder="请输入手机号" aria-describedby="basic-addon2">
            <span  class="input-group-addon btn btn-primary " id="basic-addon2">发送验证码</span>
        </div>
    </div>
    <%--表格--%>
    <table id="myTable"></table>
    <%--分页--%>
    <div id="page"></div>
</div>