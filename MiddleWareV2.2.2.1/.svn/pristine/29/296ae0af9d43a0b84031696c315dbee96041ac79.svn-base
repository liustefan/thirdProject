<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>瞬时心率图</title>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
}

.loading-indicator {
	font-size: 8pt;
	background-image: url(<%=request.getContextPath()%>/images/loading.gif);
	background-repeat: no-repeat;
	background-position: top left;
	padding-left: 20px;
	height: 18px;
	text-align: left;
}

#loading{
	display: none;
	position: absolute;
	left: 45%;
	top: 40%;
	border: 3px solid #B2D0F7;
	background: white url(<%=request.getContextPath()%>/images/block-bg.gif) repeat-x;
	padding: 10px;
	font: bold 14px verdana, tahoma, helvetica;
	color: #003366;
	width: 180px;
	text-align: center;
}

#msg {
	display:none;
	position: absolute;
	left: 45%;
	top: 40%;
	text-align: center;
	color: red;
}
</style>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-1.9.1.min.js"></script>
<script src="<%=request.getContextPath()%>/js/highstock.js"></script>
<script src="<%=request.getContextPath()%>/js/exporting.js"></script>
<script src="<%=request.getContextPath()%>/js/data.js"></script>
</head>
<body>
	<div id="msg">没有数据</div>
	<div id="loading">
		<div class="loading-indicator">页面正在加载中...</div>
	</div>
	<div id="instantChart"
		style="height: 300px; width: 1000px; margin: 0 auto;"></div>
</body>
<script type="text/javascript">
	//判断页面是否加载完毕，如果加载完毕，就删除加载信息的DIV
	document.onreadystatechange = function() {
		try {
			if (document.readyState == "complete") {
				delNode("loading");
			}
		} catch (e) {
			console.log("页面加载失败");
		}
	}

	//删除指定的DIV
	function delNode(nodeId) {
		try {
			var node = $("#" + nodeId);
			if (node !== null) {
				node.remove();
			}
		} catch (e) {
			console.log("删除ID为" + nodeId + "的节点出现异常");
		}
	}
	$(function() {
		var dt = ${empty highChareData}? null :eval(${highChareData}) ;
		if (dt && dt.msg !== false) {
			setChart("instantChart", dt);
		}else {
			$('#msg').show();
		}
	});
</script>
</html>
