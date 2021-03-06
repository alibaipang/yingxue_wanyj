<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script src="${path}/echarts/js/echarts.js"></script>
<script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.5.js"></script>
<script type="text/javascript">

    $(function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('div1'));
        $.get("${path}/echarts/queryUserByMonth",function(data){
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '用户月注册统计',  //标题
                    subtext:"真实数据",
                    link:"${path}/main/main.jsp",
                    target:"self"
                },
                tooltip: {},  //鼠标提示
                legend: {
                    data:['man','woman']  //选项卡
                },
                xAxis: {
                    data: data.month
                },
                yAxis: {},  //自适应
                series: [{
                    name: 'man',
                    type: 'bar',
                    data: data.man
                },{
                    name: 'woman',
                    type: 'bar',
                    data: data.woman
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        },"json");

    });

</script>
<script type="text/javascript">
    //初始化goeasy对象
    var goEasy = new GoEasy({
        host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BC-7f40d4e429584d36a6c6806352b81183", //替换为您的应用appkey
    });
    //GoEasy-OTP可以对appkey进行有效保护,详情请参考​ ​


    $(function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('div1'));
            goEasy.subscribe({
                channel: "yingx_ali", //替换为您自己的channel
                onMessage: function (message) {
                    //获取GoEasy数据
                    var datas = message.content;
                    //将json字符串转为javascript对象
                    var data = JSON.parse(datas);
                    var option = {
                        title: {
                            text: '用户月注册统计',  //标题
                            subtext:"真实数据",
                            link:"${path}/main/main.jsp",
                            target:"self"
                        },
                        tooltip: {},  //鼠标提示
                        legend: {
                            data:['man','woman']  //选项卡
                        },
                        xAxis: {
                            data: data.month
                        },
                        yAxis: {},  //自适应
                        series: [{
                            name: 'man',
                            type: 'bar',
                            data: data.man
                        },{
                            name: 'woman',
                            type: 'bar',
                            data: data.woman
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
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
    <title>Goeasy</title>
</head>
<body>
<div align="center">

    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="div1" style="width: 600px;height:400px;"></div>

</div>
</body>
</html>