<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script src="${path}/bootstrap/js/jquery.min.js"></script>
<script src="${path}/echarts/js/echarts.js"></script>
<script src="${path}/echarts/js/china.js"></script>
<script type="text/javascript">

    $(function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('div2'));

        $.get("${path}/echarts/queryUserByAddress",function(data){

            var series=[];

            for(var i=0;i<data.length;i++){
                var e =data[i];

                series.push({
                    name: e.title,  //
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data:e.city,
                })
            }

            // 指定图表的配置项和数据
            var option = {
                title : {
                    text: '每月用户注册分布图',
                    subtext: '真实数据',
                    left: 'center'
                },
                tooltip : {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data:["man",'woman']
                },
                visualMap: {
                    min: 0,
                    max: 6,
                    left: 'left',
                    top: 'bottom',
                    text:['高','低'],           // 文本，默认为数值文本
                    calculable : true
                },
                toolbox: {
                    show: true,
                    orient : 'vertical',
                    left: 'right',
                    top: 'center',
                    feature : {
                        mark : {show: true},
                        dataView : {show: true, readOnly: false},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                series : series
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

        },"json");

    });

</script>


<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>china.map</title>
</head>
<body>
<div align="center">

    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="div2" style="width: 600px;height:400px;"></div>

</div>
</body>
</html>
