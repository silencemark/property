<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>快速抢单</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/employer.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=wBYHoaC0rzxp8zqGCdx9WXxa" type="text/javascript"></script>  
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/page.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>  
<script type="text/javascript">


	var latitude; // 纬度，浮点数，范围为90 ~ -90
	var longitude; // 经度，浮点数，范围为180 ~ -180。
	
	
	function getcityInfo(){
		if('${isWeiXinFrom}'=='false'){
			if("${map.cityname}" == "" && "${cityInfo.cname}" == "" ){
				var flg = false;
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
							        	if(data.provincename=="上海" || data.provincename=="重庆" || data.provincename=="北京" || data.provincename=="天津"){
							        		$('input[name=provinceid]',$('#screenForm')).val(data.provinceid);
							        		$('input[name=provincename]',$('#screenForm')).val(data.provincename);
							        		$('#cityname').text(data.provincename);
							        	}else{
							        		$('input[name=areaid]',$('#screenForm')).val(data.areaid);
							        		$('input[name=cityname]',$('#screenForm')).val(data.cname);
							        		$('#cityname').text(data.cname);
							        	}
						        		getFristInfo();
							        }
								});
							  
						    });
						  }else{
							  $.ajax({
							        type: "POST",
							        url:"<%=request.getContextPath() %>/employer/getcity?lat="+latitude+"&lng="+longitude,
									dataType:"json",
							        success: function(data){
							        	if(data.provincename=="上海" || data.provincename=="重庆" || data.provincename=="北京" || data.provincename=="天津"){
							        		$('input[name=provinceid]',$('#screenForm')).val(data.provinceid);
							        		$('input[name=provincename]',$('#screenForm')).val(data.provincename);
							        		$('#cityname').text(data.provincename);
							        	}else{
							        		$('input[name=areaid]',$('#screenForm')).val(data.areaid);
							        		$('input[name=cityname]',$('#screenForm')).val(data.cname);
							        		$('#cityname').text(data.cname);
							        	}
						        		getFristInfo();
							        }
								});
							  
						  }	
						
					 
					
					}
					
				}
				
				if(userAgentInfo.indexOf("iPhone") > 0 ){
				/* 	 var data=getJingWeiAndChannelId();
						data=$.parseJSON(data);
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
							        	if(data.provincename=="上海" || data.provincename=="重庆" || data.provincename=="北京" || data.provincename=="天津"){
							        		$('input[name=provinceid]',$('#screenForm')).val(data.provinceid);
							        		$('input[name=provincename]',$('#screenForm')).val(data.provincename);
							        		$('#cityname').text(data.provincename);
							        	}else{
							        		$('input[name=areaid]',$('#screenForm')).val(data.areaid);
							        		$('input[name=cityname]',$('#screenForm')).val(data.cname);
							        		$('#cityname').text(data.cname);
							        	}
						        		getFristInfo();
							        }
								});
							  
						  });
				}
				
				 
			
				 
			<%-- 	var data=getBaiduLngLat();
				data=$.parseJSON(data);
				latitude=data.lat;
				longitude=data.lng;
				$.ajax({
			        type: "POST",
			        url:"<%=request.getContextPath() %>/employer/getcity?lat="+latitude+"&lng="+longitude,
					dataType:"json",
			        success: function(data){
			        	if(data.provincename=="上海" || data.provincename=="重庆" || data.provincename=="北京" || data.provincename=="天津"){
			        		$('input[name=provinceid]',$('#screenForm')).val(data.provinceid);
			        		$('input[name=provincename]',$('#screenForm')).val(data.provincename);
			        		$('#cityname').text(data.provincename);
			        	}else{
			        		$('input[name=areaid]',$('#screenForm')).val(data.areaid);
			        		$('input[name=cityname]',$('#screenForm')).val(data.cname);
			        		$('#cityname').text(data.cname);
			        	}
		        		getFristInfo();
			        }
				}) --%>
			}else{ 
				getFristInfo();
			}
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
				        	if(data.provincename=="上海" || data.provincename=="重庆" || data.provincename=="北京" || data.provincename=="天津"){
				        		$('input[name=provinceid]',$('#screenForm')).val(data.provinceid);
				        		$('input[name=provincename]',$('#screenForm')).val(data.provincename);
				        		$('#cityname').text(data.provincename);
				        	}else{
				        		$('input[name=areaid]',$('#screenForm')).val(data.areaid);
				        		$('input[name=cityname]',$('#screenForm')).val(data.cname);
				        		$('#cityname').text(data.cname);
				        	}
			        		getFristInfo();
				        }
					})
				},{enableHighAccuracy: true});
			}else{
				getFristInfo();
			}
		}
	}
	
	   function turnBack(){
		   var userAgentInfo = navigator.userAgent;
		   if(userAgentInfo.indexOf("iPhone") > 0 ){
			   returnHomeBack();
		   }else{
			   window.history.go(-1);
		   }
	   }
</script>
</head>

<body name="homepage" onload="getcityInfo();">
<!--头部-->
<c:if test="${isWeiXinFrom==false}">
	 <div class="headbox">
		<div class="title f-20">快速抢单</div>
	    <a href="javascript:void(0)" class="ico_back f-16" onclick="turnBack()">返回</a>
	  <!--   <a href="#" class="ico_list">更多</a> -->
	</div>
</c:if>
<div class="headbox" id="headcity" style="display: none">
    <a href="javascript:void(0)" onclick="getlastarea()" class="ico_back f-16"> </a><a class="ico_back f-16" style="float: right;position: static;margin-top: 11px;background: #424242;margin-right: 10px;display: none" id="confirmbtn" onclick="confirmcity()" >确认</a>
</div>
<div class="menu_list" style="display: none" id="areatype">
	<ul>
		<c:forEach items="${areaList}" var="item">
    		<li><a href="javascript:void(0)" onclick="getnextarea('${item.areaid}','${item.cname}')"><span>${item.cname}</span><i>更多</i></a></li>
    	</c:forEach>
    </ul>
</div>
<div class="menu_list" style="display: none" id="areatype2">
	<ul id="twotype">
    </ul> 
</div>

<div class="headbox" id="headjobtype" style="display: none">
    <a href="javascript:void(0)" onclick="getlastdict()" class="ico_back f-16"> </a><a class="ico_back f-16" style="float: right;position: static;margin-top: 11px;background: #424242;margin-right: 10px;display: none;" id="confirmbtnjob" onclick="confirmjobtype()" >确认</a>
</div>
<div class="menu_list" style="display: none" id="job_type">
	<ul>
		<c:forEach items="${industrylist}" var="item">
    		<li><a href="javascript:void(0)" onclick="getnextdict('${item.dataid}','${item.cname}')"><span>${item.cname}</span><i>更多</i></a></li>
    	</c:forEach>
    </ul>
</div>
<div class="menu_list" style="display: none" id="job_type2">
	<ul id="twojobtype">
    </ul>
</div>


<div id="allcontent">
<div class="page_selbox">
	<span id="time_span">时间</span>
    <div class="selbox_01" style="display:none;" id="timesearch">
    	<div class="box">
        	<div class="jt_top"></div>
            <ul>
            	<li><a  href="javascript:void(0)" name="div_one"  class="active" onclick="javascript:publicSubmit('timeid',0)">全部</a></li>
            	<c:forEach items="${timelist}" var="item">
            		<li><a href="javascript:void(0)" name="div_one" id="div_one_${item.datacode}" onclick="javascript:publicSubmit('timeid','${item.datacode}')">${item.cname}</a></li>
            	</c:forEach>
            </ul>
        </div>
    </div>
    <span id="salary_span">薪资</span>
    <div class="selbox_02" style="display:none" id="salarysearch">
    	<div class="box">
        	<div class="jt_top"></div>
            <ul>
            	<li><a href="javascript:void(0)" name="div_two"  class="active" onclick="javascript:publicSubmit('salaryid',0)">全部</a></li>
            	<c:forEach items="${salarylist}" var="item">
            		<li><a href="javascript:void(0)" name="div_two" id="div_two_${item.datacode}" onclick="javascript:publicSubmit('salaryid','${item.datacode}')">${item.cname}</a></li>
            	</c:forEach>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <span style="background: #F1F1F1;" id="jobtypename" onclick="changejobtype()">
    	<c:choose>
    	<c:when test="${map.jobtype != null && map.jobtype != ''}">
			${map.jobtypename}
		</c:when>
		<c:when test="${map.parentjobtype != null && map.parentjobtype != ''}">
			${map.parentjobtypename}
		</c:when>
		<c:otherwise>工种</c:otherwise>
		</c:choose>
    	</span>
    
    <span class="last" onclick="changearea()"><i class="word_hidden" id="cityname">
    <c:choose>
		<c:when test="${map.areaid != null && map.areaid !=''}">
			${map.cityname}
		</c:when>
		<c:when test="${map.provinceid != null && map.provinceid !=''}">
			${map.provincename}
		</c:when>
		<c:otherwise>
			${cityInfo.cname==null?'定位中...':cityInfo.cname}
		</c:otherwise>
	</c:choose></i></span><!--位置的激活状态样式为active02-->
</div>

<!--全部地区开始-->
<div class="div_mask" style="display:none"></div>
<div class="tc_page_selbox" style="display:none">
	<div class="t_name">
    	<a href="#" class="l_a">取消</a>
        <a href="#" class="r_a">完成</a>
    </div>
    <div class="box">
        <div class="ul_box">
            <ul>
            	<c:forEach items="${areaList}" var="area">
            		<li ><a href="#">${area.cname}</a></li>
            		${area.areaid}
            	</c:forEach>
            </ul>
        </div>
        <div class="ul_box">
            <ul>
            </ul>
        </div>
        <div class="clear"></div>
    </div>
</div>
<!--全部地区结束-->
<style type="text/css">
#space
{ display:block; float:right; line-height:24px; color:#ff7922;}
</style>
<div class="order_list01 m_top">
	<ul id="job_ul">
		<c:forEach items="${hireworkerlist}" var="item" varStatus="t">
	    	<li onclick="location.href='<%=request.getContextPath()%>/workers/initgrabsingle?jobid=${item.jobid}'">
	        	<div class="user_img">
	            	<b><img src="${item.headimage}" width="62" height="62"></b>
	                <span class="name_v">${item.contacter}${t.count }</span>
	            </div>
	            <div class="user_box p_right">
	                <div class="xx_01 word_hidden">${item.jobtitle}<em>(${item.recruitmentnum}人)</em><i id="space">${item.spacing}</i></div>
	                <div class="xx_02 word_hidden">${item.companyname}</div>
	                <div class="xx_03 word_hidden">薪资：${item.salary}元/天</div>
	            </div>
	            <div class="ico_box">
	            	<span class="time">用工时间：<fmt:formatDate value="${item.starttime}" pattern="yyyy-MM-dd"/>  至 <fmt:formatDate value="${item.endtime}" pattern="yyyy-MM-dd"/> </span>
	                <span class="address">${item.workplace}</span>
	            </div>
	        </li>
        </c:forEach>
    </ul>
</div>
<input type="hidden" id="provinceid1" />
<input type="hidden" id="provincename1" />
<input type="hidden" id="areaid1" />
<input type="hidden" id="cityname1" />
<input type="hidden" id="dictid1" />
<input type="hidden" id="dictname1" />
<input type="hidden" id="parentjobtype" />
<input type="hidden" id="parentjobtypename" />

</div>
<form action="<%=request.getContextPath() %>/workers/hireworkerlist" method="post" id="screenForm">
	<input type="hidden" name="timeid" value="${map.timeid}">
	<input type="hidden" name="salaryid" value="${map.salaryid }">
	<input type="hidden" name="areaid" value="${map.areaid}">
	<input type="hidden" name="cityname" value="${map.cityname}">
	<input type="hidden" name="provinceid" value="${map.provinceid}">
	<input type="hidden" name="provincename" value="${map.provincename}">
	<input type="hidden" name="jobtype" value="${map.jobtype}">
	<input type="hidden" name="jobtypename" value="${map.jobtypename}">
	<input type="hidden" name="parentjobtype" value="${map.parentjobtype}">
	<input type="hidden" name="parentjobtypename"  value="${map.parentjobtypename}">
</form>
<script type="text/temple" id="tmpl_li">
 		<li>
	        	<div class="user_img" onclick="location.href='<%=request.getContextPath()%>/my/detailinfo?userid={createrid}'" >
	            	<b><img src="{headimage}" width="62" height="62"></b>
	                <span class="name_v">{contacter}</span>
	            </div>
	            <div class="user_box p_right" onclick="location.href='<%=request.getContextPath()%>/workers/initgrabsingle?jobid={jobid}'">
	                <div class="xx_01 word_hidden">{jobtitle}<em>({recruitmentnum}人)</em><i id="space">{spacing}</i></div>
	                <div class="xx_02 word_hidden">{companyname}</div>
	                <div class="xx_03 word_hidden">薪资：{salary}元/天</div>
	            </div>
	            <div class="ico_box">
	            	<span class="time">项目周期：{starttime}至 {endtime}</span>
	                <span class="address">{workplace}</span>
	            </div>
	 </li>
</script>
<script type="text/javascript">


function getFristInfo(){
 var data1 = hhutil.getFormBean("screenForm");
     data1.applycreateid = '${map.applycreateid}';
     data1.pageNo = 1;
 		$.ajax({
    		url:"<%=request.getContextPath() %>/workers/hireworkerlistAjax",
    		data:data1,
    		success:function(data){
    			var html = $("#tmpl_li").html();
    			var datalist = data.dataList;
    			for(var i=0;datalist.length>i;i++){
    				var d = datalist[i]; 
    			     if(d.starttime !=null){
    					d.starttime = hhutil.parseDate(d.starttime, "YYYY-MM-DD");
    				}
    			     if(d.companyname ==null){
     					d.companyname = "";
     				}
    			     if(d.endtime !=null){
    						d.endtime = hhutil.parseDate(d.endtime, "YYYY-MM-DD");
    				 }
    				  $("#job_ul").append(hhutil.replace(html,datalist[i])).trigger("create");
    			}
    		}
    	});     
}
var data = hhutil.getFormBean("screenForm");
data.applycreateid = '${map.applycreateid}';
PageHelper({
	url:"<%=request.getContextPath() %>/workers/hireworkerlistAjax",
	data:data,
	success:function(data){
		var html = $("#tmpl_li").html();
		var datalist = data.dataList;
		for(var i=0;datalist.length>i;i++){
			var d = datalist[i]; 
		     if(d.starttime !=null){
				d.starttime = hhutil.parseDate(d.starttime, "YYYY-MM-DD");
			}
		     if(d.companyname ==null){
					d.companyname = "";
				}
		     if(d.endtime !=null){
					d.endtime = hhutil.parseDate(d.endtime, "YYYY-MM-DD");
			  }
		    
			  $("#job_ul").append(hhutil.replace(html,datalist[i])).trigger("create");
		} 
	}
});  

$(function(){
	if('${map.timeid}' != ''){
		$('a[name="div_one"]').attr('class','');
		$('#div_one_${map.timeid}').attr('class','active');
	}
	if('${map.salaryid}' != ''){
		$('a[name="div_two"]').attr('class','');
		$('#div_two_${map.salaryid}').attr('class','active');
	}
	
	$('#time_span').click(function(){
		if($(this).attr('class') == ''){
			$('#salary_span').attr("class","");
			$(this).attr("class","active");
		}else{
			$(this).attr("class","");
		}
		$('#salarysearch').css('display','none');
		$('#timesearch').toggle();
	});
	$('#salary_span').click(function(){
		if($(this).attr('class') == ''){
			$('#time_span').attr("class","");
			$(this).attr("class","active");
		}else{
			$(this).attr("class","");
		}
		$('#timesearch').css('display','none');
		$('#salarysearch').toggle();
	});
	
});
function publicSubmit(str,value){
	$('input[name='+str+']',$('#screenForm')).val(value);
	$('#screenForm').submit();
}


function confirmcity(){
	$('input[name=areaid]',$('#screenForm')).val("");
	$('input[name=cityname]',$('#screenForm')).val("");
	$('input[name=provinceid]',$('#screenForm')).val($('#provinceid1').val());
	$('input[name=provincename]',$('#screenForm')).val($('#provincename1').val());
	$('#screenForm').submit();
}
function confirmjobtype(){
	$('input[name=jobtype]',$('#screenForm')).val("");
	$('input[name=jobtypename]',$('#screenForm')).val("");
	$('input[name=parentjobtype]',$('#screenForm')).val($('#parentjobtype').val());
	$('input[name=parentjobtypename]',$('#screenForm')).val($('#parentjobtypename').val());
	$('#screenForm').submit();
}
function changejobtype(){
	$('#job_type').show();
	$('#headjobtype').show();
	$('#allcontent').hide();
}
function changearea(){
	$('#areatype').show();
	$('#headcity').show();
	$('#allcontent').hide();
}

function getnextdict(dataid,cname){
	$("#twojobtype").html("");
	$('#job_type').hide();
	$('#dictid1').val(dataid);
	$('#dictname1').val(cname);
	$('#parentjobtype').val(dataid);
	$('#parentjobtypename').val(cname);
	$.ajax({
		type:"post",
		dataType:"json",
		url:"<%=request.getContextPath()%>/employer/getnextdict?dataid="+dataid,
		success:function(data){
			var temp="<li style='background:#E7EDED;'><a href=\"javascript:void(0)\"><span>"+cname+"</span></a></li>";
			for(var i=0;i<data.length;i++){
				temp+="<li><a href=\"javascript:void(0)\" onclick=\"changejobtypenow(\'"+data[i].dataid+"\',\'"+data[i].cname+"\')\"><span>"+data[i].cname+"</span><i>更多</i></a></li>";
			}
			$("#twojobtype").html(temp);
			$('#job_type2').show();
			$('#confirmbtnjob').show();
		}
	})
}
function changejobtypenow(dataid,cname){
	$('input[name=parentjobtype]',$('#screenForm')).val("");
	$('input[name=parentjobtypename]',$('#screenForm')).val("");
	$('input[name=jobtype]',$('#screenForm')).val(dataid);
	$('input[name=jobtypename]',$('#screenForm')).val(cname);
	$('#screenForm').submit();
}

function getnextarea(dataid,cname){
	if(cname=="上海" || cname=="重庆" || cname=="天津" || cname=="北京"){
		$('input[name=areaid]',$('#screenForm')).val("");
		$('input[name=cityname]',$('#screenForm')).val("");
		$('input[name=provinceid]',$('#screenForm')).val(dataid);
		$('input[name=provincename]',$('#screenForm')).val(cname);
		$('#screenForm').submit();
	}else{
		$("#twotype").html("");
		$('#areatype').hide();
		$('#provinceid1').val(dataid);
		$('#provincename1').val(cname);
		$('#areaid1').val("");
		$('#cityname1').val("");
		$.ajax({
			type:"post",
			dataType:"json",
			url:"<%=request.getContextPath()%>/employer/getnextarea?areaid="+dataid,
			success:function(data){
					var temp="<li style='background:#E7EDED;'><a href=\"javascript:void(0)\"><span>"+cname+"</span></a></li>";
					for(var i=0;i<data.length;i++){
						temp+="<li><a href=\"javascript:void(0)\" onclick=\"changenowarea(\'"+data[i].areaid+"\',\'"+data[i].cname+"\')\"><span>"+data[i].cname+"</span><i>更多</i></a></li>";
					}
					$("#twotype").html(temp);
					$('#areatype2').show();
					$('#confirmbtn').show();
			}
		})
	}
}
function changenowarea(dataid,cname){
	$('input[name=provinceid]',$('#screenForm')).val("");
	$('input[name=provincename]',$('#screenForm')).val("");
	$("#twotype").html("");
	$('#areatype').hide();
	$('#areaid1').val(dataid);
	$('#cityname1').val(cname);
	$('input[name=areaid]',$('#screenForm')).val(dataid);
	$('input[name=cityname]',$('#screenForm')).val(cname);
	$('#screenForm').submit();
}

function getlastdict(){
	var dictid1=$('#dictid1').val();
	$("#twojobtype").html("");
	$('#headjobtype').hide();
	$('#job_type').hide();
	$.ajax({
		type:"post",
		dataType:"json",
		url:"<%=request.getContextPath()%>/employer/getlastdict?dataid="+dictid1,
		success:function(data){
			if(data.length> 0 && data[0].parentid != null && data[0].parentid != '' ){
				var temp="";
				for(var i=0;i<data.length;i++){
					if(i==0){
						$('#areaid1').val(data[i].parentid);
					}
					temp+="<li><a href=\"javascript:void(0)\" onclick=\"getnextdict(\'"+data[i].dataid+"\',\'"+data[i].cname+"\')\"><span>"+data[i].cname+"</span><i>更多</i></a></li>";
				}
				$("#twojobtype").html(temp);
				$('#headjobtype').show();
			}else{
				$('#allcontent').show();
				$('#job_type').hide();
				$('#job_type2').hide();
				$('#headjobtype').hide();
			}
			$('#job_type2').show();
//			$('#confirmbtn').show();
		}
	});
}

function getlastarea(){
	var areaid1=$('#areaid1').val();
	$("#twotype").html("");
	$('#headcity').hide();
	$('#areatype').hide();
	$('#allcontent').show();
	$('#areatype').hide();
	$('#areatype2').hide();
	$('#headcity').hide();
$('#areatype2').show();
}
	 
	</script>
 
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->

</body>
</html>
