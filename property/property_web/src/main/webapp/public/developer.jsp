<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% 
	Object longitude = request.getParameter("longitude");
	Object latitude = request.getParameter("latitude");
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
 <script src="http://api.map.baidu.com/api?v=2.0&ak=wBYHoaC0rzxp8zqGCdx9WXxa" type="text/javascript"></script>  
	<title>设置点的新图标</title>
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	
	var point = new BMap.Point(<%=longitude%>,<%=latitude%>);
	map.centerAndZoom(point, 15);
	map.addControl(new BMap.NavigationControl());  
    map.addControl(new BMap.ScaleControl());  
    map.addControl(new BMap.OverviewMapControl());  
    map.addControl(new BMap.MapTypeControl());  
	//创建小狐狸
	var pt = new BMap.Point(<%=longitude%>,<%=latitude%>);
	var marker2 = new BMap.Marker(pt);  // 创建标注
	map.addOverlay(marker2);              // 将标注添加到地图中
	var opts={
            width:100,//信息窗口宽度height:100,//信息窗口高度  
            height:50,
            title:"<%=request.getParameter("suppliername")%>"
        }
	 var infoWindow=new BMap.InfoWindow("",opts);
	 map.openInfoWindow(infoWindow,point);
	marker2.addEventListener("click",function(){
        map.openInfoWindow(infoWindow,point);//打开信息窗口 
 });
</script>
