<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../base.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>数据 - AdminLTE2定制版</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <!-- 页面meta /-->

</head>
<body>
<div id="frameContent" class="content-wrapper" style="margin-left:0px;">
    <section class="content-header">
        <h1>
            统计分析
            <small>在线人数统计</small>
        </h1>
    </section>
    <section class="content">
        <div class="box box-primary">
            <div id="main" style="width: 600px;height:400px;"></div>
        </div>
    </section>
</div>
</body>

<script src="${ctx}/plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="${ctx}/plugins/echarts/echarts.min.js"></script>
<script type="text/javascript">
    var myChart = echarts.init(document.getElementById('main'));
    //发get请求，获取数据
    var url = "${pageContext.request.contextPath}/stat/getOnlineData.do";
    $.get(url, function (data) {
        var times=[];
        var counts=[];
        for(var i=0;i<data.length;i++){
            times[i]=data[i].hourTime;
            counts[i]=data[i].peopleCount;
        }
        var option = {
            xAxis: {
                type: 'category',
                data: times
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: counts,
                type: 'line'
            }]
        };
        myChart.setOption(option);
    })
</script>

</html>