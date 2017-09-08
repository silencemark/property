<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>发布招工</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/employer.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
<link href="<%=request.getContextPath() %>/js/mobiscrol/css/mobiscroll.custom-2.6.2.min.css" type="text/css" rel="stylesheet">  
<script type="text/javascript" src="<%=request.getContextPath() %>/js/mobiscrol/js/mobiscroll.custom-2.6.2.min.js"></script> 
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>
</head>

<body name="homepage"> 
<script type="text/javascript">
   function turnBack(){
	   var userAgentInfo = navigator.userAgent;
	   if(userAgentInfo.indexOf("iPhone") > 0 ){
		   returnHomeBack();
	   }else{
		   window.history.go(-1);
	   }
   }
</script>
<!--头部-->
<c:if test="${isWeiXinFrom==false}">
	 <div class="headbox">
		<div class="title f-20">发布招工</div>
	    <a href="javascript:void(0)" class="ico_back f-16" onclick="turnBack()">返回</a>
	    <!-- <a href="#" class="ico_list">更多</a> -->
	</div>
</c:if>
<div class="menu_list" style="display: none" id="workkind">
	<ul>
		<c:forEach items="${occupationlist}" var="item">
    		<li><a href="javascript:void(0)" onclick="getnextdict('${item.dataid}','${item.cname}')"><span>${item.cname}</span><i>更多</i></a></li>
    	</c:forEach>
    </ul>
</div>
<div class="menu_list" style="display: none" id="worktype2">
	<ul id="twotype">
    </ul>
</div>
<div class="menu_list" style="display: none" id="workarea">
	<ul>
		<c:forEach items="${areaList}" var="item">
    		<li><a href="javascript:void(0)" onclick="getnextarea('${item.areaid}','${item.cname}')"><span>${item.cname}</span><i>更多</i></a></li>
    	</c:forEach>
    </ul>
</div>
 
<!--主体内容开始-->
<form method="post" action="<%=request.getContextPath() %>/employer/releasejob" id="myForm">
<input type="hidden" name="jobtype" value="${membermap.dataid}" id="jobtype">
<div class="from_publish">
	<ul>
<%--        	<li >
        	<span>项目名称</span><input type="text" class="text_01" placeholder="项目名称" name="projectname" id="projectname" >
            <div class="sel_box" style="display: none;border:0px;" id="projectlist">
            	<c:forEach items="${projectlist}" var="item">
            		<a href="javascript:void(0)" onclick="changeprojectname('${item.projectname}')" >${item.projectname}</a>
            	</c:forEach>
            </div>
        	<div class="clear"></div>
        </li> --%>
      <li>
        	<span>订单标题</span>
            <input type="text" placeholder="请填写标题" class="text_01" name="jobtitle" maxlength="16" value="${membermap.jobtitle}" oninput="on_input(this,'jobtitle');"  onblur="changetitle(this)">
            <div class="clear"></div>
       	</li>
        <li>
        	<span>工种</span>
        	<i class="sel" onclick="getworkkind()" id="jobkind">${membermap.cname==null?'':membermap.cname}</i>
            <div class="clear"></div>
       	</li>
      <li> 
        	<span>薪资<i>(元/人/天)</i></span>
            <input type="number" class="text_01" placeholder="请输入薪资" name="salary" value="${membermap.salary }" maxlength="10" oninput="on_input(this,'salary');"  onKeyUp="amount(this)" onBlur="overFormat(this)">
            <div class="clear"></div>
       	</li>
        <li>
        	<span>结算方式</i></span>
            <select class="sel_02" name="settlementmethod" onchange="chooseMethod(this)">
            	<c:forEach items="${clearingformlist }" var="item">
            		<option value="${item.dataid }">${item.cname }</option> 
            	</c:forEach>
            </select>
            <div class="clear"></div>
       	</li>
      <li>
        	<span>用工时间<i>(开始)</i></span>
            <input type="text" class="time" readonly="readonly"   name="starttime" onchange="changetimes()">
            <div class="clear"></div>
       	</li>
      <li>
        	<span>用工时间<i>(结束)</i></span>
            <input type="text" class="time" readonly="readonly"   name="endtime" onchange="changetimes()">
            <div class="clear"></div>
       </li>
       <li>
        	<span>工作地区</span>
            <i class="sel" onclick="getworkarea()" id="areatxt">${membermap.provincename==null?'':membermap.provincename }</i>
            <div class="clear"></div>
       </li>
      <li>
        	<span>工作地点</span>
            <input type="text" placeholder="请填写详细地址" class="text_01" value="${membermap.workplace }" oninput="on_input(this,'workplace');" name="workplace" maxlength="100" onblur="changeworkplace(this)">
            <div class="clear"></div>
       	</li>
        <li>
        	<span>招聘人数</span>
            <input type="tel"  class="text_01" data-type="num" id ="recruitmentnum" value="${membermap.re== null?'1':membermap.re }" name="recruitmentnum" oninput="on_input(this,'re');">
            <div class="clear"></div>
       	</li>
      <li>
        	<span>工作要求</span>
            <div class="clear"></div>
        </li>
      <li>
        	<div class="text_area"><textarea placeholder="请填写用工需求，10-200字" name="jobrequirements" maxlength="200" class="text_02"  oninput="on_input(this,'jobrequirements');" onchange="changedescription(this)">${membermap.jobrequirements }</textarea></div>
        </li>
      <li>
        	<span>联系人</span>
            <input type="text" placeholder="请输入您的称呼" class="text_01" id="contacter" name="contacter" oninput="on_input(this,'realname');" maxlength="10" value="${membermap.realname}">
            <div class="clear"></div>
       	</li>
        <li>
        	<span>联系方式</span>
            <input type="tel" placeholder="请输入您的联系方式" data-type="num" class="text_01" id="telephone" name="telephone" oninput="on_input(this,'phone');"  maxlength="11" value="${membermap.phone}">
            <div class="clear"></div>
       	</li>
    </ul> 
</div>
<div class="main_bigbtn"><input type="button" onclick="public_recruit()" value="发布招工" id="submitbtn"></div>
<input type="hidden" id="latitude" name="latitude" value="${cityInfo.latitude}"/>
<input type="hidden" id="longitude" name="longitude" value="${cityInfo.longitude}"/>

<input type="hidden" name="projectarea" value="${membermap.projectarea }" id="projectarea"/>
<input type="hidden" name="projectprovince" id="projectprovince" value="${membermap.projectprovince}"/>
<input type="hidden" name="projectprovincename" value="" id="projectprovincename"/>
</form>
<!--主体内容结束-->
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
			}else {
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
			})
		},{enableHighAccuracy: true});
	}
}else{
	var userAgentInfo = navigator.userAgent;
	if("${cityInfo.cname}" == ""){
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
			
			
		}
		
		if(userAgentInfo.indexOf("iPhone") > 0 ){
			    latitude = '${param.lat}';
			    longitude = '${param.lng}';
			/*  var data=getJingWeiAndChannelId();
				data=$.parseJSON(data);
				latitude=data.lat;
				longitude=data.lng; */
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
/* window.onload = function(){
    document.onclick = function(e){
    	document.getElementById("projectlist").style.display = "none";
    }
    document.getElementById('projectname').onclick = function(e){
    	document.getElementById("projectlist").style.display = "block";
        e = e||event; stopFunc(e);
    }
    document.getElementById("projectlist").onclick = function(e){
        e = e||event; stopFunc(e);
    }
} */
//存redis
function intoRedis(data){
	data.key='${key}';
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/member/addMemberToRedis",
		data:data,
		success:function(){
			
		}
		
	});
}

function on_input(obj,name){
	   var dt = new Object; 
	   dt[name]=$(obj).val();
	   intoRedis(dt);
	}


function chooseMethod(obj){
	var dt= new Object();
	   dt.settlementmethod = $(obj).val();
	   intoRedis(dt)
}
function getworkarea(){
	$('#myForm').hide();
	$('#workarea').show();
}
function changetitle(obj){
	if(!app_textArea($(obj).val())){
		swal("","标题含有违规字符，请重新输入!");
		$(obj).focus();
	} 
}
function changeworkplace(obj){
	if(!app_textArea($(obj).val())){
		swal("","工作地区含有违规字符，请重新输入!");
		$(obj).focus();
	} 
}
function changedescription(obj){
	if(!app_textArea($(obj).text())){
		swal("","工作要求含有违规字符，请重新输入!");
		$(obj).focus();
	} 
}

function getnextarea(dataid,cname){
	$('#projectarea').val("");
	$('#projectprovince').val("");
	var dt = new Object();
	if(cname=="上海" || cname=="重庆" || cname=="天津" || cname=="北京"){
		$('#projectprovince').val(dataid);
		$('#areatxt').text(cname);
		$('#myForm').show();
		$('#workarea').hide();
		
		dt.projectarea ="";
		dt.projectprovince = dataid;
		dt.provincename = cname;
	}else{
		$('#projectprovince').val(dataid);
		dt.projectprovince = dataid;
		$('#projectprovincename').val(cname);
		$("#twotype").html("");
		$('#workarea').hide();
		$('#worktype2').show();
		$.ajax({
			type:"post",
			dataType:"json",
			url:"<%=request.getContextPath()%>/employer/getnextarea?areaid="+dataid,
			success:function(data){
					var temp="";
					for(var i=0;i<data.length;i++){
						temp+="<li><a href=\"javascript:void(0)\" onclick=\"choosearea(\'"+data[i].areaid+"\',\'"+data[i].cname+"\')\"><span>"+data[i].cname+"</span><i>更多</i></a></li>";
					}
					$("#twotype").html(temp);
			}
		});
	}
	
	intoRedis(dt);
}
function choosearea(dataid,cname){
	$("#twotype").html("");
	$('#workarea').hide();
	$('#worktype2').show();
	$('#areatxt').text($('#projectprovincename').val()+" "+cname);
	$('#projectarea').val(dataid);
	$('#myForm').show();
	$('#workarea').hide();
	var dt = {
			projectarea:dataid,
			provincename:$('#projectprovincename').val()+" "+cname
		}
		intoRedis(dt);
}
function stopFunc(e){   
    e.stopPropagation?e.stopPropagation():e.cancelBubble = true;       
}
function changeprojectname(projectname){
	$('input[name=projectname]').val(projectname);
	document.getElementById("projectlist").style.display = "none";
}
function getworkkind(){
	$('#myForm').hide();
	$('#workkind').show();
}

function getnextdict(dataid,cname){
	$("#twotype").html("");
	$('#workkind').hide();
	$('#worktype2').show();
	$.ajax({
		type:"post",
		dataType:"json",
		url:"<%=request.getContextPath()%>/employer/getnextdict?dataid="+dataid,
		success:function(data){
			if(data.length> 0){
				var temp="";
				for(var i=0;i<data.length;i++){
					temp+="<li><a href=\"javascript:void(0)\" onclick=\"chooseworkkind(\'"+data[i].dataid+"\',\'"+data[i].cname+"\')\"><span>"+data[i].cname+"</span><i>更多</i></a></li>";
				}
				$("#twotype").html(temp);
			}else{
				$('#jobkind').text(cname);
				$('#jobtype').val(dataid);
				$('#myForm').show();
				$('#workkind').hide();
			}
		}
	})
}
function chooseworkkind(dataid,cname){
	$('#jobkind').text(cname);
	$('#jobtype').val(dataid);
	$('#myForm').show();
	$('#worktype2').hide();
	var opt={
		cname:cname,
		dataid:dataid
	}
	intoRedis(opt);
	
}
function changetimes(){
	var nowdate =new Date();
	var start = new Date(($('input[name="starttime"]').val()).replace(/-/g,'/'));
	var end = new Date(($('input[name="endtime"]').val()).replace(/-/g,'/'));
	if(parseInt((start.getTime() - nowdate.getTime())) <= 0){
		swal({
			title : "",
			text : "开始时间必须大于当前时间,请重新设置结束时间!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			setstartendtime();
			$('input[name="starttime"]').focus(); 
		});
		return; 
	}
	
	if($('input[name="starttime"]').val() > $('input[name="endtime"]').val() || parseInt((end.getTime() - start.getTime())/1000/60/60) <= 2){
		swal({
			title : "",
			text : "结束时间至少大于开始时间2小时以上,请重新设置结束时间!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			setstartendtime()
			$('input[name="endtime"]').focus(); 
		});
		return; 
	}
	
	 
	
}
function public_recruit(){
	if('${membermap.status}' != '1'){
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
	if('${membermap.individualstatus}' != '1'){
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
	//空验证
	if($('#projectname').val() == ''){ 
		swal({
			title : "",
			text : "请输入项目名称!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('#projectname').focus(); 
		});
		return;
	}
	if($('input[name="jobtitle"]').val() == ''){ 
		swal({
			title : "",
			text : "请输入订单标题!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="jobtitle"]').focus(); 
		});
		return;
	}
	if(!app_textArea($('input[name="jobtitle"]').val())){ 
		swal({
			title : "",
			text : "标题含有违规字符，请重新输入!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="jobtitle"]').val("");
			$('input[name="jobtitle"]').focus(); 
		});
		return;
	}
	if($('input[name="jobtype"]').val() == ''){ 
		swal({
			title : "",
			text : "请选择工种!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('#jobkind').click();
		});
		return;
	}
	if($('input[name="salary"]').val() == ''){ 
		swal({
			title : "",
			text : "请输入薪资!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="salary"]').focus(); 
		});
		return;
	}
	if($('input[name="salary"]').val() == '0'){ 
		swal({
			title : "",
			text : "薪资不能为0!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="salary"]').val(""); 
			$('input[name="salary"]').focus(); 
		});
		return;
	}
	
	
	if($('input[name="salary"]').val().length >6){ 
		swal({
			title : "",
			text : "薪资不能超过6位！",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="salary"]').val(""); 
			$('input[name="salary"]').focus(); 
		});
		return;
	}
	
	if($('input[name="salary"]').val() == '0'){ 
		swal({
			title : "",
			text : "薪资不能为0!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="salary"]').val(""); 
			$('input[name="salary"]').focus(); 
		});
		return;
	}
	
	if($('input[name="starttime"]').val() == ''){ 
		swal({
			title : "",
			text : "请输入开始时间!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="starttime"]').focus(); 
		});
		return;
	}
	if($('input[name="endtime"]').val() == ''){ 
		swal({
			title : "",
			text : "请输入结束时间!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="endtime"]').focus();
		});
		return;
	}
	var start = new Date(($('input[name="starttime"]').val()).replace(/-/g,'/'));
	var end = new Date(($('input[name="endtime"]').val()).replace(/-/g,'/'));
	if($('input[name="starttime"]').val() > $('input[name="endtime"]').val() || parseInt((end.getTime() - start.getTime())/1000/60/60) <= 2){
		swal({
			title : "",
			text : "开始时间不能大于结束时间,请重新设置结束时间!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="endtime"]').focus(); 
		});
		return; 
	}
	if($('input[name="workplace"]').val() == ''){ 
		swal({
			title : "",
			text : "请输入工作地点!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="workplace"]').focus();
		});
		return;
	}
	if(!app_textArea($('input[name="workplace"]').val())){ 
		swal({
			title : "",
			text : "工作地点含有违规字符，请重新输入!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="workplace"]').val("");
			$('input[name="workplace"]').focus(); 
		});
		return;
	}
	if($('input[name="recruitmentnum"]').val() == ''){ 
		swal({
			title : "",
			text : "请输入招聘人数!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="recruitmentnum"]').focus(); 
		});
		return;
	}
	if($('input[name="recruitmentnum"]').val() == '0'){ 
		swal({
			title : "",
			text : "请输入大于 0 的招聘人数!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="recruitmentnum"]').focus(); 
		});
		return;
	}
	if($('input[name="jobrequirements"]').val() == ''){ 
		swal({
			title : "",
			text : "请输入工作要求!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="jobrequirements"]').focus(); 
		});
		return;
	}
	if(!app_textArea($('input[name="jobrequirements"]').text())){ 
		swal({
			title : "",
			text : "工作要求含有违规字符，请重新输入!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="jobrequirements"]').text("");
			$('input[name="jobrequirements"]').focus(); 
		});
		return;
	}
	if($('input[name="contacter"]').val() == ''){ 
		swal({
			title : "",
			text : "请输入联系人!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="contacter"]').focus(); 
		});
		return;
	}
	if($('input[name="telephone"]').val() == '' || !(/^1[3|4|5|8][0-9]\d{4,8}$/.test($('input[name="telephone"]').val()))){
		swal({
			title : "",
			text : "请输入有效的联系方式!",
			type : "",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="telephone"]').focus();  
		});
		return;
	}
	
	var startdate=$('input[name="starttime"]').val();
	var enddate=$('input[name="endtime"]').val();
	$('input[name="starttime"]').val(startdate.substring(0,startdate.length-2)+"00");
	$('input[name="endtime"]').val(enddate.substring(0,startdate.length-2)+"00"); 
	$('#submitbtn').attr("disabled","disabled");
	$('#myForm').submit();
} 
Date.prototype.format = function(format) {  
    /* 
     * 使用例子:format="yyyy-MM-dd hh:mm:ss"; 
     */  
    var o = {  
        "M+" : this.getMonth() + 1, // month  
        "d+" : this.getDate(), // day  
        "h+" : this.getHours(), // hour  
        "m+" : this.getMinutes(), // minute  
        "s+" : this.getSeconds(), // second  
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
        "S" : this.getMilliseconds()  
        // millisecond  
    }  
   
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
                        - RegExp.$1.length));  
    }  
   
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1  
                            ? o[k]  
                            : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}
function setstartendtime(){
	var date1=new Date();
	date1.setHours(date1.getHours() + 2);
	var mouth1=(date1.getMonth()+1)+"";
	var day1=(date1.getDate())+"";
	var hour1=(date1.getHours())+"";
	if(mouth1.length==1){
		mouth1="0"+mouth1;
	}
	if(day1.length==1){
		day1="0"+day1;
	}
	if(hour1.length==1){
		hour1="0"+hour1;
	}
	var date4=date1.getFullYear()+"-"+mouth1+"-"+day1+" "+hour1+":00";
	
	var date2=new Date();
	date2.setDate(date2.getDate());
	date2.setHours(date2.getHours() + 10);
	var mouth=(date2.getMonth()+1)+"";
	var day=(date2.getDate())+"";
	var hour=(date2.getHours())+"";
	if(mouth.length==1){
		mouth="0"+mouth;
	}
	if(day.length==1){
		day="0"+day;
	}
	if(hour.length==1){
		hour="0"+hour;
	}
	var date3=date2.getFullYear()+"-"+mouth+"-"+day+" "+hour+":00";
	$('input[name="starttime"]').val(date4);
	$('input[name="endtime"]').val(date3);
}
$(function(){
	setstartendtime();
	var currYear = (new Date()).getFullYear();	
	var opt={};
	opt.date = {preset : 'date', dateFormat: 'yy-mm-dd' };
	//opt.datetime = { preset : 'datetime', minDate: new Date(2012,3,10,9,22), maxDate: new Date(2014,7,30,15,44), stepMinute: 5  };
	opt.datetime = {preset : 'datetime', dateFormat: 'yy-mm-dd' }; 
	opt.time = {preset : 'time'};
	opt.default = { 
		theme: 'android-ics light', //皮肤样式 
        display: 'modal', //显示方式  
        mode: 'scroller', //日期选择模式
        dateOrder : 'yymmdd', 
		lang:'zh',
        startYear:currYear - 10, //开始年份
        endYear:currYear + 10 //结束年份
	};    

	$('input[name="starttime"]').scroller('destroy').scroller($.extend(opt['datetime'], opt['default']));
	$('input[name="endtime"]').scroller('destroy').scroller($.extend(opt['datetime'], opt['default']));
	
	$('#projectname').keydown(function(){
			$('#projectlist').hide();
	});
	
	
	
});
function amount(th){
    var regStrs = [
        ['^0(\\d+)$', '$1'], //禁止录入整数部分两位以上，但首位为0
        ['[^\\d\\.]+$', ''], //禁止录入任何非数字和点
        ['\\.(\\d?)\\.+', '.$1'], //禁止录入两个以上的点
        ['^(\\d+\\.\\d{2}).+', '$1'] //禁止录入小数点后两位以上
    ];
    for(i=0; i<regStrs.length; i++){
        var reg = new RegExp(regStrs[i][0]);
        th.value = th.value.replace(reg, regStrs[i][1]);
    }
}
function overFormat(th){
    var v = th.value;
    if(/^0+\d+\.?\d*.*$/.test(v)){
        v = v.replace(/^0+(\d+\.?\d*).*$/, '$1');
        v = inp.getRightPriceFormat(v).val;
    }else if(/^0\.\d$/.test(v)){
        v = v + '0';
    }else if(!/^\d+\.\d{2}$/.test(v)){
        if(/^\d+\.\d{2}.+/.test(v)){
            v = v.replace(/^(\d+\.\d{2}).*$/, '$1');
        }else if(/^\d+\.$/.test(v)){
            v = v + '00';
        }else if(/^\d+\.\d$/.test(v)){
            v = v + '0';
        }else if(/^[^\d]+\d+\.?\d*$/.test(v)){
            v = v.replace(/^[^\d]+(\d+\.?\d*)$/, '$1');
        }else if(/\d+/.test(v)){
            v = v.replace(/^[^\d]*(\d+\.?\d*).*$/, '$1');
            ty = false;
        }else if(/^0+\d+\.?\d*$/.test(v)){
            v = v.replace(/^0+(\d+\.?\d*)$/, '$1');
            ty = false;
        }
    }
   
    th.value = v;
}
</script>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
</body>
</html>
