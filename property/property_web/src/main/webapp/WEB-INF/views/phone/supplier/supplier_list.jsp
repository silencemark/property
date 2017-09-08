<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>供应商列表</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/page.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=wBYHoaC0rzxp8zqGCdx9WXxa" type="text/javascript"></script>  
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<script type="text/javascript">
	var latitude; // 纬度，浮点数，范围为90 ~ -90
	var longitude; // 经度，浮点数，范围为180 ~ -180。
	if('${isWeiXinFrom}'=='false'){
//		if("${map.cityname}" == "" && "${cityInfo.cname}" == "" ){
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
					 
					  $.ajax({
					        type: "POST",
					        url:"<%=request.getContextPath() %>/employer/getcity?lat="+latitude+"&lng="+longitude,
							dataType:"json",
					        success: function(data){
					        	$('input[name=areaid]',$('#screenForm')).val(data.areaid);
				        		$('input[name=cityname]',$('#screenForm')).val(data.cname);
				        		$('#cityname').text(data.cname);
					        }
						});
					  
				    });
				  }else{
					  $.ajax({
					        type: "POST",
					        url:"<%=request.getContextPath() %>/employer/getcity?lat="+latitude+"&lng="+longitude,
							dataType:"json",
					        success: function(data){
					        	$('input[name=areaid]',$('#screenForm')).val(data.areaid);
				        		$('input[name=cityname]',$('#screenForm')).val(data.cname);
				        		$('#cityname').text(data.cname);
					        }
						});
					  
				  }
			  
			}else{
				latitude="";
				longitude="";
			}
		}
		
		if(userAgentInfo.indexOf("iPhone") > 0 ){
//		    var data=getJingWeiAndChannelId();
		      /*  if(data != null && data != '' && data !='underfined'){ */
		    	   /*  data=$.parseJSON(data);
		    	    latitude=data.lat;
					longitude=data.lng; */
					  latitude = '${param.lat}';
					  longitude = '${param.lng}';
					//百度地图API功能  
						//GPS坐标
						  translateBaiDuJW(longitude,latitude,function(point){
							  if(point.lat+"" == "0" || point.lng+"" == "0" ){
								  point.lat="";
								  point.lng="";
							  }
							  latitude=point.lat;
							  longitude=point.lng;
							  $.ajax({
							        type: "POST",
							        url:"<%=request.getContextPath() %>/employer/getcity?lat="+latitude+"&lng="+longitude,
									dataType:"json",
							        success: function(data){
							        	$('input[name=areaid]',$('#screenForm')).val(data.areaid);
						        		$('input[name=cityname]',$('#screenForm')).val(data.cname);
						        		$('#cityname').text(data.cname);
							        }
								});
							  
						  });
					
		       /* } */
				
		
		}
		  
			
		   
//		}
	}else{
		var geolocation = new BMap.Geolocation();
		if("${map.cityname}" == "" && "${cityInfo.cname}" == "" ){
			geolocation.getCurrentPosition(function(r){
				if(this.getStatus() == BMAP_STATUS_SUCCESS){
					longitude = r.point.lng;
					latitude = r.point.lat; 
				}else {
					longitude = 26.629907;
					latitude = 106.709177;
				}
				$.ajax({
			        type: "POST",
			        url:"<%=request.getContextPath() %>/employer/getcity?lat="+latitude+"&lng="+longitude,
					dataType:"json",
			        success: function(data){
		        		$('input[name=areaid]',$('#screenForm')).val(data.areaid);
		        		$('input[name=cityname]',$('#screenForm')).val(data.cname);
		        		$('#cityname').text(data.cname);
			        }
				})
			},{enableHighAccuracy: true});
		}
	}
</script>
</head>
<body name="supplier">

<div class="find_box">
	<div class="text_box">
		<form action="<%=request.getContextPath() %>/weixin/supplier/getsupplierlist" method="post">
    	<input type="text" class="text" name="searchtext" placeholder="请输入关键字，如公司名称、营业范围" value="${map.searchtext}">
        <input type="submit" value="" class="btn">
        </form>
    </div>
</div>
<div class="order_list01 border_top">
	<ul id="supp_ul">
<%-- 		<c:forEach items="${supplierList}" var="supp">
	    	<li onclick="location.href='<%=request.getContextPath() %>/weixin/supplier/getsupplierdetail?supplierid=${supp.supplierid}'">
	        	<div class="user_img">
	            	<b><img src="${supp.supplierlog}" width="62" height="62"></b>
	            </div>
	            <div class="user_box p_right">
	                <div class="xx_04"><span class="name_v">${supp.suppliername}</span><i>${supp.spacing}</i></div>
	                <div class="xx_02 word_hidden">${supp.products}</div>
	                <div class="xx_03 word_hidden">营业时间：${supp.businesshours}</div>
	            </div>
	        </li>
        </c:forEach> --%>
     </ul>
</div>
<script type="text/javascript">
$.ajax({
	url:"<%=request.getContextPath() %>/weixin/supplier/getsupplierlistAjax",
	data:{searchtext:"${map.searchtext}",pageNo:1},
	success:function(data){
		var html = $("#tmpl_li").html();
		var datalist = data.dataList;
		for(var i=0;datalist.length>i;i++){
			var d = datalist[i];
			
		   /*  if(d.createtime !=null){
				d.createtime = hhutil.parseDate(d.createtime, "MM-DD hh:mm");
			}
		     */
			  $("#supp_ul").append(hhutil.replace(html,datalist[i])).trigger("create");
		}
	}
});  

PageHelper({
	url:"<%=request.getContextPath() %>/weixin/supplier/getsupplierlistAjax",
	data:{searchtext:"${map.searchtext}",pageNo:1},
	success:function(data){
		var html = $("#tmpl_li").html();
		var datalist = data.dataList;
		for(var i=0;datalist.length>i;i++){
			var d = datalist[i]; 
		   /*  if(d.createtime !=null){
				d.createtime = hhutil.parseDate(d.createtime, "MM-DD hh:mm");
			}
		     */
			  $("#supp_ul").append(hhutil.replace(html,datalist[i])).trigger("create");
		} 
	}
});  

</script>

<script type="text/temple" id="tmpl_li">
 	<li onclick="location.href='<%=request.getContextPath() %>/weixin/supplier/getsupplierdetail?supplierid={supplierid}'">
	        	<div class="user_img">
	            	<b><img src="{supplierlog}" width="62" height="62"></b>
	            </div>
	            <div class="user_box p_right">
	                <div class="xx_04"><span class="name_v"  style="width:80%;overflow:hidden;text-overflow:ellipsis;background:url('');"><nobr>{suppliername}</nobr></span><i>{spacing}</i></div>
	                <div class="xx_02 word_hidden">{products}</div>
	                <div class="xx_03 word_hidden">营业时间：{businesshours}</div>
	            </div>
	 </li>
</script>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->

</body>
</html>
