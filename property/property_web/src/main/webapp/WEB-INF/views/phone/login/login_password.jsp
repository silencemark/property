<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>密码登陆</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>
<script src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>

  
</head>
<body>
<script src="http://api.map.baidu.com/api?v=2.0&ak=wBYHoaC0rzxp8zqGCdx9WXxa" type="text/javascript"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<!--头部-->
<div class="headbox">
  <div class="title f-20">密码登陆</div>
    <!-- <a href="#" class="ico_back f-16">返回</a> -->
    <!-- <a href="#" class="ico_list">更多</a> -->
</div>
<div class="body_minheight">
<div class="login">
	<ul>
    	<li class="line"><span>手机号</span><input type="text" name="username" maxlength="11" class="text" placeholder="请输入"></li>
        <li class="mm_box"><span>密   码</span>
          <input type="password" name="password" onKeyUp="value=value.replace(/[\W]/g,'')" class="text" placeholder="6-16位数字、大小写字母"   maxlength="16"/>
          <a href="javascript:void(0)" style="display: none" class="ico_eye">可见</a>
       </li>
    </ul>
</div>
<div class="main_bigbtn"><input onclick="loginForm()" type="button" value="登 陆"></div>
<div class="login_link"><a href="<%=request.getContextPath()%>/login/startPhoneLogin">手机快捷登陆</a></div>
<div class="wxts_word">温馨提示：未注册嘀嗒叫人账号的手机号，登录时将自动注册嘀嗒叫人，且代表您已同意<a href="<%=request.getContextPath() %>/login/startAgreement">《服务协议》</a><!-- 和<a href="#">《嘀嗒叫人合作协议》</a> --></div>
</div>
<script type="text/javascript">
$(function(){
	
	var flg =false;
	$(".ico_eye").click(function(){
		if(flg == false){
			$("input[name='password']").attr("type","text");
			flg=true;
		}else{
			$("input[name='password']").attr("type","password");
			flg=false;
		}
	});
	
	$("input[name='password']").keyup(function() {
		if($("input[name='password']").val() != ""){
			$(".ico_eye").show();
		}else{
			$(".ico_eye").hide();
		}
	});
});

function loginForm(){
	if($("input[name='username']").val() == "" || !/^1[3|4|5|7|8]\d{9}$/.test($("input[name='username']").val())){
		 swal({
				title : "",
				text : "手机号码格式错误,请重新输入!",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				$("input[name='username']").focus();
			});	
	
		return;
	}
    if($("input[name='password']").val() == "" ){
    	 swal({
				title : "",
				text : "密码不能为空!",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				$("input[name='password']").focus();
			});	
    	return;
    }
    
    if($("input[name='password']").val().length<6){
    	 swal({
				title : "",
				text : "密码不能小于6位",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				$("input[name='password']").focus();
			});	
    	
    	return;
    }
    
    var device = ""; 
    var channelid = "";
    var latitude="";
    var longitude="";
 
    var userAgentInfo = navigator.userAgent;
    /* string[] keywords = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };   */
    if(userAgentInfo.indexOf("Android") > 0 && '${isWeiXinFrom}' == 'false'){
    var obj =  getDeviceChanneiID();
    var dt={};
    if(obj == null || obj ==""){
    	dt=null;
    }else{
    	dt=JSON.parse(obj);
    	device=dt.device;
    	channelid=dt.response_params.channel_id;
    }
  
	    var data=getBaiduLngLat();
	    if(data != null && data != ''){
		data=$.parseJSON(data);
	  if(data.edition == '5.0'){
	  translateBaiDuJW(data.lng,data.lat,function(point){
			  if(point.lat+"" == "0" || point.lng+"" == "0" ){
				  point.lat="";
				  point.lng="";
			  }
			  latitude = point.lat; 
			  longitude = point.lng;
			  
		    });
	  
		  }else{
				latitude=data.lat;
				longitude=data.lng;
		  } 
		  
		}
	 
    }
 
    
  if(userAgentInfo.indexOf("iPhone") > 0 && '${isWeiXinFrom}' == 'false'){
	   var data=getJingWeiAndChannelId();
		data=$.parseJSON(data);
		latitude=data.lat;
		longitude=data.lng;
		device = data.device;
		channelid = data.channelid;
		//百度地图API功能
		//GPS坐标
		  translateBaiDuJW(longitude,latitude,function(point){
				  if(point.lat+"" == "0" || point.lng+"" == "0" ){
					  point.lat="";
					  point.lng="";
				  }
				  latitude = point.lat;
			      longitude = point.lng;
			  
			});
      }

/*   if(flg ==  true){ */
  var opt={
	        username:$("input[name='username']").val(),
	        password:$("input[name='password']").val(),
	        latitude:latitude,
	        longitude:longitude,
	        channelid:channelid,
	        device:device
	    };
	    
		$.ajax({
		  type:"post",
		  url:"<%=request.getContextPath() %>/login/passwordLogin",
		  data:opt,
		  success:function(data){
			  if(data.flg){
				  if (userAgentInfo.indexOf("iPhone") > 0 && data.iswx == false){
						  returnUserId(data.userId);   
			      }else{
					  window.location.href="<%=request.getContextPath() %>/index/employerindex?type=1";  
				  }			  
				  
			  }else{
				  swal({
						title : "",
						text :data.msg,
						showCancelButton : false,
						confirmButtonColor : "#ff7922",
						confirmButtonText : "确认",
						closeOnConfirm : true
					}, function(){
					});	
			  }
		  },error:function(text){
			  swal({
					title : "",
					text : "登陆失败，请稍后再试！",
					showCancelButton : false,
					confirmButtonColor : "#ff7922",
					confirmButtonText : "确认",
					closeOnConfirm : true
				}, function(){
				});	
		  }
			
		});
  
  /* } */
}

</script>
</body>
</html>
