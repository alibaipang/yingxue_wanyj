<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.5.js"></script>
<script>

    /*初始化GoEasy对象*/
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-49c4bb3ed91945448c35358477615835", //替换为您的应用appkey
    });
    //GoEasy-OTP可以对appkey进行有效保护,详情请参考​ ​

    $(function(){

        var textareaMsg;

        //接收消息
        goEasy.subscribe({
            channel: "186-chat", //替换为您自己的channel
            onMessage: function (message) {

                //接收发送的内容
                var message= message.content;

                //判断内容是不是自己发的
                if(textareaMsg==message){
                    //是   不在左侧展示
                }else{
                    //不是自己发的  正常展示

                    //渲染页面

                    //给展示div设置样式
                    var msgDiv=("<div style='width: auto;height: 30px;'><div style='float: left;background-color:#bbccdd;border-radius: 5px'>"+message+"</div></div>");

                    //将发送内容展示再文本框左侧
                    $("#showMsg").append(msgDiv);
                }
            }
        });


        //点击发送按钮触发事件
        $("#sendBtn").click(function(){
            //获取文本输入框内容
            var content= $("#sendMsg").val();

            //将发送的内容交给变量
            textareaMsg=content;

            //通过GoEasy发送内容
            goEasy.publish({
                channel: "186-chat", //替换为您自己的channel
                message: content, //替换为您想要发送的消息内容
                onSuccess:function(){
                    //清空输入框内容
                    $("#sendMsg").val("");

                    //给展示div设置样式
                    var msgDiv=("<div style='width: auto;height: 30px;'><div style='float: right;background-color: #ccaadd;border-radius: 5px'>"+content+"</div></div>");

                    //将发送内容展示再文本框左侧
                    $("#showMsg").append(msgDiv);
                },
                onFailed: function (error) {
                    alert("消息发送失败，错误编码："+error.code+" 错误信息："+error.content);
                }
            });
        });
    });
</script>


<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>聊天室</title>
    </head>
    <body >
        <center>
            <div style="width: 500px;height:600px;border: 3px #acdd4a solid">
                <%--内容展示区域--%>
                <div id="showMsg" style="width: 494px;height: 500px;border:3px #a6e1ec solid"></div>

                <%--输入文本发送区域--%>
                <div style="width: 494px;height: 88px;border: 3px orange solid">
                    <%--输入框--%>
                    <textarea id="sendMsg" style="width: 400px;height: 88px;border: 3px #ccaadd solid" ></textarea>
                    <%--<input style="width: 400px;height: 88px;border: 3px #ccaadd solid" />--%>
                    <%--发送按钮--%>
                    <button id="sendBtn" style="width: 60px;height: 60px;background-color: #acdd4a;"  >发送</button>
                </div>
            </div>
        </center>
    </body>
</html>