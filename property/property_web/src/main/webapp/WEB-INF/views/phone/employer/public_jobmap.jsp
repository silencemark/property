<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>招工地图明细</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/page.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script> 

 
</head>
 <script src="http://api.map.baidu.com/api?v=2.0&ak=wBYHoaC0rzxp8zqGCdx9WXxa" type="text/javascript"></script>  
  <script type="text/javascript">
  	var latitude; // 纬度，浮点数，范围为90 ~ -90
	var longitude; // 经度，浮点数，范围为180 ~ -180。
	function getbaidumap(){
		<c:if test="${jobmap.latitude == null}">
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
			if(this.getStatus() == BMAP_STATUS_SUCCESS){
				longitude = r.point.lng;
				latitude = r.point.lat; 
			}else {
				longitude = 26.629907;
				latitude = 106.709177;
			}
			initialize();
		},{enableHighAccuracy: true});
		</c:if>
		<c:if test="${jobmap.latitude != null}">
			longitude = '${jobmap.longitude}';
			latitude = '${jobmap.latitude}';
		initialize();
		</c:if>
		loadingbody();
	}
        var map;
      //创建图标对象 
        var myIcon=new BMap.Icon("<%=request.getContextPath()%>/upload/women.png",new BMap.Size(16,20),{});
        var myIcon1=new BMap.Icon("<%=request.getContextPath()%>/upload/man.png",new BMap.Size(16,20),{});
        //创建标注对象并添加到地图,{icon:myIcon}  
        function initialize(){  
            map=new BMap.Map("container");
            var myIconimg=null;
            var point=new BMap.Point(longitude,latitude);
            map.centerAndZoom(point,15);
            map.addControl(new BMap.NavigationControl());  
            map.addControl(new BMap.ScaleControl());  
            map.addControl(new BMap.OverviewMapControl());  
            map.addControl(new BMap.MapTypeControl());  
            <c:if test="${membermap.sex==0}">
            myIconimg=myIcon;
        		var marker=new BMap.Marker(point,{icon:myIcon});
        		map.addOverlay(marker);  
                var bounds=map.getBounds();  
                var lngSpan=bounds.maxX-bounds.minX;
                var latSpan=bounds.maxY-bounds.minY;
            </c:if>
            <c:if test="${membermap.sex==1 || membermap.sex==2}">
            myIconimg=myIcon1;
        		var marker=new BMap.Marker(point,{icon:myIcon1});
        		map.addOverlay(marker);
                var bounds=map.getBounds();
                var lngSpan=bounds.maxX-bounds.minX;
                var latSpan=bounds.maxY-bounds.minY;
            </c:if>
            var black="<a style='float:right' href=\"<%=request.getContextPath()%>/member/initEditMemberInfo\"><img src=\"../upload/black.jpg\" width=\"14px\" height=\"22px\"/></a>";
            var nickname="我(${membermap.nickname})";
            var opts={
                    width:200,//信息窗口宽度height:100,//信息窗口高度  
                    height:80,
                    title:"<div class='map_box' style='border-bottom:0px;'><div class='user_xx' style='position:static;background:white;'><div class='box'><b style='float:left'><img src='${membermap.headimage}' width=\"58\" height=\"58\"></b><div class=\"xx\"><div class=\"name\" style=\"line-height:18px;font-size:14px;\">"+nickname+"</div><div class=\"clear\"></div><font sytle=\"padding-top:1px;line-height:12px;max-height:15px;overflow:hidden;color:#999;\">${order.jobtypename}</font>"+black+"<br/><font sytle=\"padding-top:1px;line-height:12px;max-height:15px;overflow:hidden;\">资料完善${membermap.perfectdegree}%</font></div></div></div></div>" //信息窗口标题  
                }
            addMarker(point,myIconimg,opts);
            //创建标注 
            <c:forEach items="${peopleList}" var="order">
        	var point=new BMap.Point(parseFloat('${order.longitude}'),parseFloat('${order.latitude}'));  
            map.addControl(new BMap.NavigationControl());  
            map.addControl(new BMap.ScaleControl());  
            map.addControl(new BMap.OverviewMapControl());  
            map.addControl(new BMap.MapTypeControl());  
	            <c:if test="${order.sex==0}">
	            myIconimg=myIcon;
	        		var marker=new BMap.Marker(point,{icon:myIcon});
	        		map.addOverlay(marker);  
	                var bounds=map.getBounds();  
	                var lngSpan=bounds.maxX-bounds.minX;
	                var latSpan=bounds.maxY-bounds.minY;
	            </c:if>
	            <c:if test="${order.sex==1 || order.sex==2}">
	            myIconimg=myIcon1;
	        		var marker=new BMap.Marker(point,{icon:myIcon1});
	        		map.addOverlay(marker);  
	                var bounds=map.getBounds();  
	                var lngSpan=bounds.maxX-bounds.minX;
	                var latSpan=bounds.maxY-bounds.minY;
	            </c:if>
	            var black="";
	            var nickname='${order.nickname}';
                var opts={
                        width:200,//信息窗口宽度height:100,//信息窗口高度  
                        height:80,
                        title:"<div class='map_box' style='border-bottom:0px;'><div class='user_xx' style='position:static;background:white;'><div class='box'><b style='float:left'><img src='${order.headimage}' width=\"58\" height=\"58\"></b><div class=\"xx\"><div class=\"name\" style=\"line-height:18px;font-size:14px;\">"+nickname+"</div><div class=\"clear\"></div><font sytle=\"padding-top:1px;line-height:12px;max-height:15px;overflow:hidden;color:#999;\">${order.jobtypename}</font>"+black+"<br/><font sytle=\"padding-top:1px;line-height:12px;max-height:15px;overflow:hidden;\">资料完善${order.perfectdegree}%</font></div></div></div></div>" //信息窗口标题  
                    }
                addMarker(point,myIconimg,opts);
            </c:forEach>
        }
        //编写自定义函数，创建标注  
        function addMarker(point,Icon,opts){
            var infoWindow=new BMap.InfoWindow("",opts);//创建信息窗口对象  
            var marker=new BMap.Marker(point,{icon:Icon});  
                map.addOverlay(marker);
                marker.addEventListener("click",function(){  
                   map.openInfoWindow(infoWindow,point);//打开信息窗口  
            });
        }
        function loadingbody(){
        	$.ajax({
        		type:'post',
        		dataType:'json',
        		url:'<%=request.getContextPath()%>/employer/getnoticenum?orderid=${jobmap.orderid}',
        		success:function(data){
        			if(data != null && data != ''){
        				infonum=data;
        			}
        			getnoticeandtime();
        		}
        	})
        }
       function getnoticeandtime(){
        	var interval = 1000; 
        	function ShowCountDown() 
        	{ 
        	var now = new Date(); 
//        	alert("-1-------"+endDate);
        	var leftTime=parseInt('${jobmap.starttimesecond}')-now.getTime();
        	if(leftTime <= 0){
        		$('#timestr').text("已过期");
        	}else{
	        	var leftsecond = parseInt(leftTime/1000); 
	        	var day1=Math.floor(leftsecond/(60*60*24)); 
	        	var hour=Math.floor((leftsecond-day1*24*60*60)/3600); 
	        	var minute=Math.floor((leftsecond-day1*24*60*60-hour*3600)/60); 
	        	var second=Math.floor(leftsecond-day1*24*60*60-hour*3600-minute*60);
	        	var timestr="";
	        	if(parseInt(day1)>0){
	        		timestr+=day1+"天";
	        	}
	        	if(parseInt(hour)>0){
	        		timestr+=hour+"小时";
	        	}
	        	if(parseInt(minute)>0){
	        		timestr+=minute+"分";
	        	}
	        	if(parseInt(second)>0){
	        		timestr+=second+"秒";
	        	}
	        	$('#timestr').text(timestr);
        	}
        	}
        	window.setInterval(function(){ShowCountDown();}, interval);
        	
        	var infonumreal=0;
        	var timer=null;
        	if('${map.isfrist}'=='1'){
        		timer=window.setInterval(function(){setinfonum();}, interval);
        	}else{
        		$('#infonum').text(infonum+"人");
        	}
        	function setinfonum(){
        		if(infonumreal==parseInt(infonum)){
        			clearInterval(timer);
        		}else{
        			infonumreal=infonumreal+1;
        			$('#infonum').text(infonumreal+"人");
        		}
        		
        	}
        }
       
       function turnBack(){
    	   var userAgentInfo = navigator.userAgent;
    	   if(userAgentInfo.indexOf("iPhone") > 0  && '${param.resource}' != ''){
    		   returnHomeBack();
    	   }else{
    		   window.history.go(-1);
    	   }
       }
</script>

<body  onload="getbaidumap()" name="homepage">
 
<!--头部-->
<c:if test="${isWeiXinFrom==false}">
	 <div class="headbox">
		<div class="title f-20">招工地图明细</div>
	     <a href="javascript:void(0);"  onclick="turnBack();" class="ico_back f-16">返回</a>
    <!-- <a href="#" class="ico_list">更多</a> -->
	</div>
</c:if>
<div class="pagelist_nav">
   <a href="<%=request.getContextPath() %>/employer/workermapinfo?jobid=${jobmap.jobid}&orderid=${jobmap.orderid}" id="ing" class="line active">招工申请列表</a>
   <a href="<%=request.getContextPath() %>/order/getEmployerOrderJobctDetail?jobid=${jobmap.jobid }&orderid=${jobmap.orderid }" id="end"  >招工基本信息</a></div>
<div class="map_box">
	<div class="img_box">
	<div id="container" style="width:100%;height:300px;"></div>  
	</div>
    <div class="num_box"><p>已通知&nbsp;&nbsp;&nbsp;<i id="infonum">0人</i>，剩余时间&nbsp;&nbsp;<i id="timestr"></i></p><span>${publishtext}</span></div>
</div>
<div class="order_list01">
	<div class="t_name">
    	<span>申请记录</span>
        <i>${ordernum}人</i>
        <div class="clear"></div>
    </div>
	<ul>
		 <c:forEach items="${peopleList }" var ="row" varStatus="t">
        <li>
        	<div class="user_img" style="width: auto"  onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/goWorkmandetail?orderid=${row.orderid }&userid=${row.applicantid }&applyorderid=${row.applyorderid }&jobid=${jobmap.jobid}'">
            	<b><img src="${row.headimage}" width="62" height="62"></b>
            </div>
            <div class="user_box"  onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/goWorkmandetail?orderid=${row.orderid }&userid=${row.applicantid }&applyorderid=${row.applyorderid }&jobid=${jobmap.jobid}'">
                <div class="xx_04"><span class="${row.instatus == 1?'}name_v':'name' }name_v">${row.nickname }</span><b> 
                 <c:forEach var = "i" begin="1" end="${row.evalueavg }">
                               <img src="<%=request.getContextPath() %>/appcssjs/images/public/star.png">
                </c:forEach>
                </b></div>
                <div class="xx_02 word_hidden">申请时间：<fmt:formatDate value="${row.createtime }" pattern="MM-dd HH:mm"/></div>
                <div class="xx_03 word_hidden">联系方式 ： 
                  <c:choose>
                	<c:when test="${row.ifpay==1}">
                		${row.phone}
                	</c:when>
                	<c:otherwise>
                		${fn:replace(row.phone, fn:substring(row.phone,3,7), '****')}
                	</c:otherwise>
                </c:choose>
                </div>
            </div>
            <div class="tool_xx" style ="width: 94px">
                   <c:if test="${row.status == 3 }">
                     <c:choose>
                      <c:when test="${row.score == null and row.status_order == 2 }"> 
                        <div style="margin-top:4px" onclick="javascript:window.location.href='<%=request.getContextPath() %>/evaluate/initAddEvalue?orderid=${row.applyorderid }&evaluationerid=${row.publisherid }&userid=${row.applicantid }&jobid=${jobmap.jobid }&oid=${row.orderid }'"  class="state bg_yellow">评价</div>
                      </c:when>
                      <c:when test="${row.score != null }">
                        <div class="state " style="margin-left: 0px;margin-top: 0px">
                           <c:forEach var = "i" begin="1" end="${row.score }">
                               <img width="14px" height="13px" src="<%=request.getContextPath() %>/appcssjs/images/public/star.png">
                           </c:forEach>
                      </div>
                      </c:when>
                    </c:choose>
                   </c:if>
                 <c:if test="${row.status == 3 and  row.status_order != 2 }"><div style="margin-top: 5px" class="state bg_yellow" onclick="cancelOrder('${row.orderid }','${row.applyorderid}');">取消</div></c:if>
            	 <div class="zx_xx01"  style="margin-top: 5px">
	                  <c:choose>
	            	    <c:when test="${row.status == 1 }">待处理</c:when>
	            	    <c:when test="${row.status == 2 }">已取消</c:when>
	            	    <c:when test="${row.status == 3 }">已成交</c:when>
	            	    <c:when test="${row.status == 4 }">暂不合适</c:when>
	            	    <c:when test="${row.status == 5 }">工人取消</c:when>
	            	    <c:when test="${row.status == 6 }">雇主同意</c:when>
	            	    <c:when test="${row.status == 7 }">雇主拒绝</c:when>
	            	    <c:when test="${row.status == 8 }">雇主取消</c:when>
	            	    <c:when test="${row.status == 9 }">工人同意</c:when>
	            	    <c:when test="${row.status == 10 }">工人拒绝</c:when>
	            	    <c:when test="${row.status == 11 }">已过期</c:when>
	            	    <c:when test="${row.status == 12 }">已被抢走</c:when>
            	  </c:choose>
                </div>
            </div>
        </li>
      </c:forEach>
    </ul>
</div>
<script type="text/javascript">
function cancelOrder(orderid,applyorderid){
	 var status="8";
		swal({
			title : "",
			text : "取消行为，将会影响您在平台的信誉！您确定还要取消吗?",
			type : "info",
			showCancelButton : true,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			cancelButtonText:"取消",
			closeOnConfirm : true
		}, function() {
			 $.ajax({
					type:"post",
					url:"<%=request.getContextPath() %>/order/updateApplyOrderStatus",
					data:{status:status,applyorderid:applyorderid,iscancel:"1",orderid:orderid},
					success:function(data){
						if(data){
							window.location.reload(true);
						}
					},error:function(sts){
						swal({
							title : "",
							text : "操作失败!",
							type : "error",
							showCancelButton : false,
							confirmButtonColor : "#ff7922",
							confirmButtonText : "确认",
							closeOnConfirm : true
						}, function() {
							 
						});
					 
					}
					 
				  });
		});
	 }
 PageHelper({
		url:"<%=request.getContextPath() %>/order/getEmployerOrderJobctDetailAjax",
		data:{orderid:"${map.orderid}"},
		success:function(data){
			var html = $("#tmpl_li").html();
			var datalist = data.dataList;
			for(var i=0;datalist.length>i;i++){
				var d = datalist[i]; 
			    if(d.createtime !=null){
					d.createtime = hhutil.parseDate(d.createtime, "MM-DD hh:mm");
				}
			    if(d.instatus+"" == "1"){
	            	d.name_v = "name_v";
	            }else{
	            	d.name_v="name";
	            }
			    
			    if(d.nickname == null){
			    	d.nickname ="匿名";
			    }
			    
			    d.atten="";
			    if(d.isattention+"" == "1"){
			    	d.atten="<span class='gz'>已关注</span>";
			    }
			    
			    if(d.phone ==null){
			    	d.phone="暂无";
			    }else if(d.phone !=null && d.ifpay+"" == "0"){
			    	d.phone=d.phone.replace(d.phone.substring(3,7),"****");
			    } 
			    
			    d.imgs_ = "";
			    if(d.evalueavg !=null ){
			    	for(var j =0 ;j < parseInt(d.evalueavg);j++){
			    		 d.imgs_ += "<img src='<%=request.getContextPath() %>/appcssjs/images/public/star.png'>";
			    	}
			    	
			    }
			    d.pj="";
			    if(d.status+"" == "3"){
			    	if(d.score == null && d.status_order+"" == "2" ){ 
			    		var pjurl="<%=request.getContextPath() %>/evaluate/initAddEvalue?orderid="+d.applyorderid+"&evaluationerid="+d.publisherid+"&userid="+d.applicantid+"&type=employer&jobid=${jobmap.jobid }&oid="+d.orderid;
			    		var pjclick = "javascript:window.location.href="+"'"+pjurl+"'";
			    		d.pj = '<div style="margin-top:4px" onclick="'+pjclick+'" class="state bg_yellow">评价</div>';
			    	}else{
			    		 d.pj+='<div class="state " style="margin-left: 0px;margin-top: 0px">';
			    		for(var j =0 ;j < parseInt(d.score);j++){
			    			d.pj+="<img width='14px' height='13px' src='<%=request.getContextPath() %>/appcssjs/images/public/star.png'>";
				    	}
			    		d.pj+="</div>";
			    	}
			    }
		        
			    if(d.status == 3 && row.status_order != 2){
			       d.cel='<div style="margin-top: 5px" class="state bg_yellow" onclick="cancelOrder('+d.orderid+','+d.applyorderid+');">取消</div>';
			    }
			    
			    if(d.status+"" ==1){
	            	d.or_status="待处理";	
	            }else if(d.status+"" ==2){
	            	d.or_status="已取消";
	            }else if(d.status+"" ==3){
	            	d.or_status="已成交";
	            }else if(d.status+"" ==4){
	            	d.or_status="暂不合适";
	            }else if(d.status+"" ==5){
	            	d.or_status="工人取消";
	            }else if(d.status+"" ==6){
	            	d.or_status="雇主同意";
	            }else if(d.status+"" ==7){
	            	d.or_status="雇主拒绝";
	            }else if(d.status+"" ==8){
	            	d.or_status="雇主取消";
	            }else if(d.status+"" ==9){
	            	d.or_status="工人同意";
	            }else if(d.status+"" ==10){
	            	d.or_status="工人拒绝";
	            }else if(d.status+"" ==10){
	            	d.or_status="已过期";
	            }
				  $("#pople_ul").append(hhutil.replace(html,datalist[i])).trigger("create");
			} 
		}
	});  
 
</script>
<script type="text/temple" id="tmpl_li">
    <li>
        	<div class="user_img" style="width: auto" onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/goWorkmandetail?orderid={orderid }&userid={applicantid }&applyorderid={applyorderid }'">
            	<b><img src="{headimage}" width="62" height="62"></b>
                 {atten}
            </div>
            <div class="user_box" onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/goWorkmandetail?orderid={orderid }&userid={applicantid }&applyorderid={applyorderid }'">
                <div class="xx_04"><span class="{name_v}">{nickname }</span><b> 
                   {imgs_}   
                </b></div>
                <div class="xx_02 word_hidden">申请时间：{createtime }</div>
                <div class="xx_03 word_hidden">联系方式 ： {phone}</div>
            </div>
            <div class="tool_xx" style ="width: 94px"  >
                  {cancel}
                  {cel}    
            	 <div class="zx_xx01" style="margin-top: 5px">
                   {or_status}
                </div>
            </div>
        </li>

</script>

<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->

</body>
</html>