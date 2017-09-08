<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.qiniu.util.AuthConstant"%>  
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>发布项目</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/employer.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/js/mobiscrol/css/mobiscroll.custom-2.6.2.min.css" type="text/css" rel="stylesheet">  
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
<script src="<%=request.getContextPath() %>/appcssjs/scripts/mobiscrol/js/mobiscroll.custom-2.6.2.min.js"></script> 
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.iframe-transport.js"></script>
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.fileupload.js"></script> 
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.fileupload-ui.js"></script> 
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.fileupload-process.js"></script>   
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.fileupload-validate.js"></script>  
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/index.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>
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
		<div class="title f-20">发布项目</div>
	    <a href="javascript:void(0)" class="ico_back f-16" onclick="turnBack()">返回</a>
	    <!-- <a href="#" class="ico_list">更多</a> -->
	</div>
</c:if>
<div class="menu_list" style="display: none" id="worktype">
	<ul>
		<c:forEach items="${industrylist}" var="item">
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
<form method="post" action="<%=request.getContextPath() %>/employer/releaserelease" id="myForm"> 
<input type="hidden" name="ownedindustry" value="${membermap.dataid}" id="ownedindustry"/>
<input type="hidden" name="projectarea" value="${membermap.projectarea}" id="projectarea"/> <!-- 城市 -->
<div class="from_publish">
	<ul>
    	<li>
        	<span>项目标题</span>
            <input type="text" placeholder="请填写标题"  class="text_01" value="${membermap.projecttitle}" name="projecttitle" maxlength="20" oninput="on_input(this,'projecttitle');"  onblur="changetitle(this)">
            <div class="clear"></div>
       	</li>
        <li>
        	<span>行业类别</span>
        	<i class="sel" onclick="getworktype()" id="jobwork"> ${membermap.cname==null?'':membermap.cname}</i>
            <div class="clear"></div>
       	</li>
        <li>
        	<span>项目周期<i>(开始)</i></span>
            <input type="text" class="time" readonly="readonly" name="starttime" onchange="changetimes()">
            <div class="clear"></div>
       	</li>
        <li>
        	<span>项目周期<i>(结束)</i></span>
            <input type="text" class="time" readonly="readonly" name="endtime" onchange="changetimes()">
            <div class="clear"></div>
       	</li>
        <li>
        	<span>工作地区</span>
            <i class="sel" onclick="getworkarea()" id="areatxt">${membermap.provincename == null?'':membermap.provincename }</i>
            <div class="clear"></div>
       	</li>
        <li>
        	<span>详细地址</span>
            <input type="text" placeholder="请填写详细地址" class="text_01" value="${membermap.address }" name="address" maxlength="100" oninput="on_input(this,'address');" onblur="changeaddress(this)"> 
            <div class="clear"></div>
       	</li>
        <li>
        	<span>项目描述</span>
            <div class="clear"></div>
        </li>
        <li>
        	<div class="text_area"><textarea placeholder="请填写项目需求..." name="projectdescription" class="text_02" oninput="on_input(this,'projectdescription');" onblur="changedescription(this)">${membermap.projectdescription}</textarea></div>
        </li>
        <li>
        	<span>项目图片</span>
        	<c:choose>
        		<c:when test="${isWeiXinFrom==true}">
        			<a href="javascript:void(0)"  onclick="uploadProjectImgs();" class="add_btn" >上传图片</a>
        		</c:when>
        		<c:otherwise>
        			<c:choose>
        				<c:when test="${isAndroidFrom==true}">
		        			<a href="javascript:void(0)"  onclick="upload_project_img('projectimgs')" id="updateimgid" class="add_btn" >上传图片</a>
		        		</c:when>
		        		<c:otherwise><a   onclick="upload_project_img('projectimgs')" id="updateimgid" class="add_btn" >上传图片</a></c:otherwise>
        			</c:choose>
        		</c:otherwise>
        	</c:choose>
        	<i class="xx">请上传图片，最多9张</i><div class="clear"></div>
            <input style="display: none" type="file" name="file" id="many_upload"/>     
            <div id="praject_img" class="pic_box" >
                 <c:forEach items="${membermap.projectImgList }" var="row">
                    <b><input type="hidden" name="projectimg" value="${row.url}"/><img onclick="imgutil.FDIMG(this)" src="${row.projectimg_show }" width="52" height="52"><em onclick="deleteimg(this);">删除</em></b>                     
                 </c:forEach>
                <div class="clear" id="projectimgs"></div>
            </div>            
        </li>
         
        <li>
        	<span>联系人</span>
            <input type="text" placeholder="请输入您的称呼" class="text_01" name="contacter" maxlength="10" value="${membermap.realname}" oninput="on_input(this,'realname');"  >
            <div class="clear"></div>
       	</li>
        <li>
        	<span>联系方式</span>
            <input type="tel" placeholder="请输入您的联系方式" class="text_01" name="telephone" data-type="num" oninput="on_input(this,'phone');"  maxlength="11"    value="${membermap.phone}">
            <div class="clear"></div>
       	</li>
    </ul> 
</div>
<div class="main_bigbtn"><input type="button" onclick="public_recruit()" value="发布项目" id="submitbtn"></div>

<input type="hidden" id="latitude" name="latitude" value="${cityInfo.latitude}"/>
<input type="hidden" id="longitude" name="longitude" value="${cityInfo.longitude}"/>
<input type="hidden" name="projectprovince" id="projectprovince" value="${membermap.projectprovince}"/><!-- 省份 -->
<input type="hidden" name="projectprovincename" id="projectprovincename"/>
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
	<%-- if("${cityInfo.cname}" == ""){
	var data=getBaiduLngLat();
		data=$.parseJSON(data);
		latitude=data.lat;
		longitude=data.lng;
		$('#latitude').val(latitude);
		$('#longitude').val(longitude);
 		$.ajax({
	        type: "POST",
	        url:"<%=request.getContextPath() %>/employer/getcity?lat="+latitude+"&lng="+longitude,
			dataType:"json",
	        success: function(data){
	        }
		})
	} --%>
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
			/*  var data=getJingWeiAndChannelId();
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

 

function changetitle(obj){
	if(!app_textArea($(obj).val())){
		swal("","标题含有违规字符，请重新输入!");
		$(obj).focus();
	} 
}
function changeaddress(obj){
	if(!app_textArea($(obj).val())){
		swal("","地址含有违规字符，请重新输入!");
		$(obj).focus();
	} 
}
function changedescription(obj){
	if(!app_textArea($(obj).text())){
		swal("","描述含有违规字符，请重新输入!");
		$(obj).focus();
	} 
}
function getnextdict(dataid,cname){
	$("#twotype").html("");
	$('#worktype').hide();
	$('#worktype2').show();
	$.ajax({
		type:"post",
		dataType:"json",
		url:"<%=request.getContextPath()%>/employer/getnextdict?dataid="+dataid,
		success:function(data){
			if(data.length> 0){
				var temp="";
				for(var i=0;i<data.length;i++){
					temp+="<li><a href=\"javascript:void(0)\" onclick=\"chooseworktype(\'"+data[i].dataid+"\',\'"+data[i].cname+"\')\"><span>"+data[i].cname+"</span><i>更多</i></a></li>";
				}
				$("#twotype").html(temp);
			}else{
				$('#jobwork').text(cname);
				$('#ownedindustry').val(dataid);
				$('#myForm').show();
				$('#worktype').hide();
			}
		}
	})
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
function getworktype(){
	$('#myForm').hide();
	$('#worktype').show();
}
function chooseworktype(dataid,cname){
	$('#jobwork').text(cname);
	$('#ownedindustry').val(dataid);
	$('#myForm').show();
	$('#worktype2').hide();
	var opt={
			cname:cname,
			dataid:dataid
		}
		intoRedis(opt);
}
function getworkarea(){
	$('#myForm').hide();
	$('#workarea').show();
}
function changetimes(){
	var nowdate =new Date();
	var start = new Date(($('input[name="starttime"]').val()).replace(/-/g,'/'));
	var end = new Date(($('input[name="endtime"]').val()).replace(/-/g,'/'));
	if(parseInt((start.getTime() - nowdate.getTime())) <= 0){
		swal({
			title : "",
			text : "开始时间必须大于当前时间,请重新设置结束时间!",
			type : "error",
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
			type : "error",
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
	
	if($('input[name="projecttitle"]').val() == ''){ 
		swal({
			title : "",
			text : "请输入项目标题!",
			type : "error",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="projecttitle"]').focus(); 
		});
		return;
	}
	
	if(!app_textArea($('input[name="projecttitle"]').val())){ 
		swal({
			title : "",
			text : "标题含有违规字符，请重新输入!",
			type : "error",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="projecttitle"]').val("");
			$('input[name="projecttitle"]').focus(); 
		});
		return;
	}
	if($('input[name="ownedindustry"]').val() == ''){
		swal({
			title : "",
			text : "请选择行业类别!",
			type : "error",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('#jobwork').click();
		});
		return;
	}
	if($('input[name="starttime"]').val() == ''){
		swal({
			title : "",
			text : "请输入开始时间!",
			type : "error",
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
			type : "error",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="endtime"]').focus();
		});
		return;
	}
	if($('input[name="projectarea"]').val() == "" && $('input[name="projectprovince"]').val() == ""){
		swal({
			title : "",
			text : "请选择工作地区!",
			type : "error",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('#areatxt').click();
		});
		return;
	}
	if($('input[name="address"]').val() == ''){
		swal({
			title : "",
			text : "请输入详细地址!",
			type : "error",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="address"]').focus(); 
		});
		return;
	}
	if(!app_textArea($('input[name="address"]').val())){ 
		swal({
			title : "",
			text : "地址含有违规字符，请重新输入!",
			type : "error",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="address"]').val("");
			$('input[name="address"]').focus(); 
		});
		return;
	}
	if($('input[name="projectdescription"]').val() == ''){
		swal({
			title : "",
			text : "请输入项目描述!",
			type : "error",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="projectdescription"]').focus(); 
		});
		return;
	}
	if(!app_textArea($('input[name="projectdescription"]').text())){ 
		swal({
			title : "",
			text : "项目描述含有违规字符，请重新输入!",
			type : "error",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
			$('input[name="projectdescription"]').text("");
			$('input[name="projectdescription"]').focus(); 
		});
		return;
	}
	if($('input[name=projectimg]').length==0){
		swal({
			title : "",
			text : "请添加至少一张项目图片!",
			type : "error",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function() {
		});
		return;
	}
	if($('input[name="contacter"]').val() == ''){
		swal({
			title : "",
			text : "请输入联系人!",
			type : "error",
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
			type : "error",
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
	
	console.log(hhutil.getFormBean("myForm"));
	 
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
})	

 function uploadcall(data){
	 
	var id=data.id;
	var keylist=(data.keys).split(",");
	 
	  for(var i=0;i<keylist.length;i++){
					$('#'+data.id).before("<b><input type='hidden' name='projectimg' value='"+keylist[i]+"'/><img onclick=\"imgutil.FDIMG(this)\" src='http://7xqoy0.com1.z0.glb.clouddn.com/"+keylist[i]+"' width='52' height='52'><em onclick=\'deleteimg(this);\'>删除</em></b>");
					var imgcount=$('input[name=projectimg]').length;
					$("#updateimgid").attr("onclick","upload_project_img('projectimgs')");
	}
	  
	  var array = new Array();
		$("input[name='projectimg']").each(function(index,dom){
			   t = {
					projectimg_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
		            url: $(this).val(),
		        };
			   array.push(t); 
          
  	 });
	          var dt = {
	        	projectImgList:JSON.stringify(array)
	          }; 
	          intoRedis(dt);
	}

    
   function  upLoadIosImageKey(data){
	   var keylist=(data.keys).split(",");
	   for(var i=0;i<keylist.length;i++){
		   $('#'+data.id).before("<b><input type='hidden' name='projectimg' value='"+keylist[i]+"'/><img onclick=\"imgutil.FDIMG(this)\" src='http://7xqoy0.com1.z0.glb.clouddn.com/"+keylist[i]+"' width='52' height='52'><em onclick=\'deleteimg(this);\'>删除</em></b>");
		   var imgcount=$('input[name=projectimg]').length;
		   $("#updateimgid").attr("onclick","upload_project_img('projectimgs')"); 
     }
	   var array = new Array();
		$("input[name='projectimg']").each(function(index,dom){
			   t = {
					projectimg_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
		            url: $(this).val(),
		        };
			   array.push(t); 
   	 });
	          var dt = {
	        	projectImgList:JSON.stringify(array)
	          }; 
	          intoRedis(dt);
	}
	   
   
 	$('#many_upload').fileupload({
		url:'http://up.qiniu.com',   
		formData:{
			'token'	: '<%=AuthConstant.getToken() %>'  
		},
		type:'POST',
		maxNumberOfFiles:1,
		autoUpload:true,
	    dataType: 'json',
	    acceptFileTypes:  /(\.|\/)(gif|jpe?g|png)$/i, 
	    maxFileSize: 5000000, 
	    done: function (e, data) {
	    		$.ajax({
					url:'<%=request.getContextPath() %>/upload/downimg/wx/'+data.result.key,
					success:function(data1){
						$('#projectimgs').before("<b><input type='hidden' name='projectimg' value='"+data.result.key+"'/><img  onclick=\"imgutil.FDIMG(this)\" src='"+data1+"' width='52' height='52'><em onclick=\'deleteimg(this);\'>删除</em></b>");
						
						var array = new Array();
						$("input[name='projectimg']").each(function(index,dom){
							   t = {
									projectimg_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
						            url: $(this).val(),
						        };
							   array.push(t); 
				            
				    	 });
					          var dt = {
					        	projectImgList:JSON.stringify(array)
					          }; 
					          intoRedis(dt);
					}  
				});
	    },
	    progressall: function (e, data) {
	    	/* 
	    	$('.div_mask').css('display','block');
	    	$('#shejitishi').css('display','block');  
	    	
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        $('#progress1 .bar').css(
	            'width',
	            progress + '%'
	        );
	        $('#tishi_pro').html('正在上传...'+progress+'%/100%');
	       if(progress == 100){   
	    	   $('.div_mask').css('display','none'); 
	       		$('#shejitishi').css('display','none');  
	       } */
	    }
	});
	function deleteimg(obj){
		$(obj).parent().remove();
		var array = new Array();
		$("input[name='projectimg']").each(function(index,dom){
			   t = {
					projectimg_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
		            url: $(this).val(),
		        };
			   array.push(t); 
            
    	 });
	          var dt = {
	        	projectImgList:JSON.stringify(array)
	          }; 
	          intoRedis(dt);
	  }
	
	
	function uploadProjectImgs (){
		var num = $('#praject_img img').length;
		if(num<9){
			$('#many_upload').click();
		}else{
    		swal({
    			title : "",
    			text : "最多上传9张照片！",
    			type : "info",
    			showCancelButton : false,
    			confirmButtonColor : "#ff7922",
    			confirmButtonText : "确认",
    			closeOnConfirm : true
    		}, function(){
    			filenum=10;
    		});
    		return;
    	}
		
	}
	
	function upload_project_img(id){
		var imgcount = $('#praject_img img').length;
		if("${isAndroidFrom}" == "true"){
			upload(id,parseInt(9-imgcount));
		}else{
			uploadImg(id,parseInt(9-imgcount));
		}
	}	
	
</script>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->

</body>
</html>
