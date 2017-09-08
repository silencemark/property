<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/head/base.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>招工详情</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
</head>

<body name="homepage">
<script type="text/javascript">
  function turnBack(){
	  var curWwwPath=window.document.location.href;
	   if(curWwwPath.indexOf("resource")>0){
		   returnHomeBack();
	   }else{
	       window.history.go(-1);
	   }
}
  
</script>
<!--头部-->
<c:if test="${isWeiXinFrom==false}">
	 <div class="headbox">
		<div class="title f-20">招工详情</div>
	     <a href="javascript:void(0)" onclick="turnBack();" class="ico_back f-16">返回</a>
    <!-- <a href="#" class="ico_list">更多</a> -->
	</div>
</c:if>
<div class="order_detail">
	<ul>
    	<li><span>订单标题：</span><i>${jobmap.jobtitle}</i><div class="clear"></div></li>
    	<li><span>订单单号：</span><i>${jobmap.orderno}</i><div class="clear"></div></li>
    	<li><span>工种：</span><i>${jobmap.jobtypename}</i><div class="clear"></div></li>
        <li><span>薪资(元/人/天)：</span><i style="width:50%">${jobmap.salary}元</i><div class="clear"></div></li>
        <li><span>结算方式：</span><i>${jobmap.settlementmethodname }</i><div class="clear"></div></li>
        <li><span>项目周期(开始)：</span><i style="width:50%"><fmt:formatDate value="${jobmap.starttime}" pattern="yyyy-MM-dd HH:mm"/></i><div class="clear"></div></li>
        <li><span>项目周期(结束)：</span><i style="width:50%"><fmt:formatDate value="${jobmap.endtime}" pattern="yyyy-MM-dd HH:mm"/></i><div class="clear"></div></li>
        <li><span>工作地区：</span><i>${jobmap.provincename } ${jobmap.cityname }</i><div class="clear"></div></li>
        <li><span>工作地址：</span><i>${jobmap.workplace }</i><div class="clear"></div></li>
        <li><span>联系人：</span><i>${jobmap.contacter }</i><div class="clear"></div></li>
        <li><span>联系方式：</span><i>${jobmap.telephone }</i><div class="clear"></div></li>
        <li><span>公司名称：</span><i>${jobmap.companyname }</i><div class="clear"></div></li>
        <li class="last"><span>工作要求：</span><i>${jobmap.jobrequirements }</i><div class="clear"></div></li>
    </ul>
</div>


<div class="main_bigbtn"><input type="button" value="立即抢单" onclick="nowsinple()"></div> 
<input type="hidden" id="longitude" value="${cityInfo.longitude}"/>
<input type="hidden" id="latitude" value="${cityInfo.latitude}"/>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
<script src="http://api.map.baidu.com/api?v=2.0&ak=wBYHoaC0rzxp8zqGCdx9WXxa" type="text/javascript"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<script type="text/javascript">
var latitude; // 纬度，浮点数，范围为90 ~ -90
var longitude; // 经度，浮点数，范围为180 ~ -180。

if('${isWeiXinFrom}'=='true'){
	var geolocation = new BMap.Geolocation();
	if("${cityInfo.cname}" == ""){
		geolocation.getCurrentPosition(function(r){
			if(this.getStatus() == BMAP_STATUS_SUCCESS){
				longitude = r.point.lng;
				latitude = r.point.lat; 
			}else{
				longitude = 26.629907;
				latitude = 106.709177;
			}
			$('#latitude').val(latitude);
			$('#longitude').val(longitude);
			$.ajax({
		        type: "POST",
		        url:"<%=request.getContextPath() %>/employer/getcity?lat="+latitude+"&lng="+longitude,
				dataType:"json",
		        success: function(data){
		        }
			});
		},{enableHighAccuracy: true});
	}
}else{
	 
	if("${cityInfo.cname}" == ""){
		var userAgentInfo = navigator.userAgent;	
		if(userAgentInfo.indexOf("Android") > 0 ){
			var data=getBaiduLngLat();
			if(data !=""){
				
				  data=$.parseJSON(data);
				  latitude=data.lat;
				  longitude=data.lng;
				  if(data.edition == '5.0'){
				  translateBaiDuJW(longitude,latitude,function(point){
					  if(point.lat+"" == "0" || point.lng+"" == "0" ){
						  point.lat="";
						  point.lng="";
					  }
					  latitude = point.lat; 
					  longitude = point.lng;
					  $('#latitude').val(latitude);
					  $('#longitude').val(longitude);
						$.ajax({
					        type: "POST",
					        url:"<%=request.getContextPath() %>/employer/getcity?lat="+latitude+"&lng="+longitude,
							dataType:"json",
					        success: function(data){
					        }
						});
					  
				    });
				  }else{
					  $('#latitude').val(latitude);
					  $('#longitude').val(longitude);
						$.ajax({
					        type: "POST",
					        url:"<%=request.getContextPath() %>/employer/getcity?lat="+latitude+"&lng="+longitude,
							dataType:"json",
					        success: function(data){
					        }
						});
		 
			}	
		}
		
		if(userAgentInfo.indexOf("iPhone") > 0 ){
			 var data=getJingWeiAndChannelId();
				data=$.parseJSON(data);
				latitude=data.lat;
				longitude=data.lng;
				//百度地图API功能
				//GPS坐标
				  translateBaiDuJW(longitude,latitude,function(point){
					  if(point.lat+"" == "0" || point.lng+"" == "0" ){
						  point.lat="";
						  point.lng="";
					  }
					   $('#latitude').val(point.lat);
						$('#longitude').val(point.lng);
						$.ajax({
					        type: "POST",
					        url:"<%=request.getContextPath() %>/employer/getcity?lat="+point.lat+"&lng="+point.lng,
							dataType:"json",
					        success: function(data){
					        }
						});
				
				  });
				
		}
		
	 
	}
  }
}

function nowsinple(){
	if('${user.status}' != '1'){
		 swal({
				title : "",
				text : "您好，你的帐户存在异常请联系管理员!",
				type : "",
				showCancelButton : true,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				cancelButtonText : "取消",
				closeOnConfirm : true
			}, function(){
			});	
		 return;
	 }
	 if('${user.iscompletion}' == '0'){
		 swal({
				title : "",
				text : "请完善个人资料，点击确认后进入完善资料页面!",
				type : "",
				showCancelButton : true,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "去完善",
				cancelButtonText : "取消",
				closeOnConfirm : true
			}, function(){
				 window.location.href="<%=request.getContextPath()%>/member/initEditMemberInfo";
			});	
		 return;
	 }
	 if('${user.individualstatus}' != '1'){
		 swal({
				title : "",
				text : "您认证未通过，请重新修改个人资料!",
				type : "",
				showCancelButton : true,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "修改",
				cancelButtonText : "取消",
				closeOnConfirm : true
			}, function(){
				 window.location.href="<%=request.getContextPath()%>/member/initEditMemberInfo";
			});	
		 return;
	 }
	 

	$.ajax({
		type:'post',
		dataType:'json',
		url:'${basePath}workers/grabsingle?publisherid=${jobmap.createrid}&jobid=${jobmap.jobid}&orderid=${jobmap.orderid}&longitude='+$('#longitude').val()+'&latitude='+$('#latitude').val()+'&starttime=${jobmap.starttime}&endtime=${jobmap.endtime}',
		success:function(data){
			if(data=="1"){
				   swal({
		 				title : "",
		 				text : "您已经抢过此单!",
						type : "",
		 				showCancelButton : false,
		 				confirmButtonColor : "#ff7922",
		 				confirmButtonText : "确认",
						closeOnConfirm : true
					}, function(){
						 
					});	
			}else if(data=="2"){
				   swal({
		 				title : "",
		 				text : "不能抢同一时间段内的招工！",
						type : "",
		 				showCancelButton : false,
		 				confirmButtonColor : "#ff7922",
		 				confirmButtonText : "确认",
						closeOnConfirm : true
					}, function(){
						 
					});	
			}else if(data=="4"){
				   swal({
		 				title : "",
		 				text : "当前订单已过期，去看看其它订单吧！",
						type : "",
		 				showCancelButton : false,
		 				confirmButtonColor : "#ff7922",
		 				confirmButtonText : "确认",
						closeOnConfirm : true
					}, function(){
						 
					});	
			}else if(data=="5"){
				   swal({
		 				title : "",
		 				text : "当前订单已经招满了！去看看其它订单吧！",
						type : "",
		 				showCancelButton : false,
		 				confirmButtonColor : "#ff7922",
		 				confirmButtonText : "确认",
						closeOnConfirm : true
					}, function(){
						 
					});	
			}else{
				swal({
					title : "",
					text : "恭喜您!抢单成功，点击确认返回抢单列表!",
					type : "success",
					showCancelButton : false,
					confirmButtonColor : "#ff7922",
					confirmButtonText : "确认!",
					closeOnConfirm : false
				}, function() {
					 var userAgentInfo = navigator.userAgent;
					   
					   if(userAgentInfo.indexOf("iPhone") > 0 && '${isWeiXinFrom}'=='false'){
						   returnHomeBack();
						   returnHome(2);
					   }else{
						   window.location.href="${basePath}index/workmanindex";
					   }
					
				});
			}
		}
	});
}

 

</script> 
 
</body>
</html>
