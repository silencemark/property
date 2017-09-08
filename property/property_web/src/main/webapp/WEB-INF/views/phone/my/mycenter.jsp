<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/head/base.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<title>我的</title>
<link href="${basePath }appcssjs/style/public.css" type="text/css"
	rel="stylesheet">
<link href="${basePath }appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
</head>
<style type="text/css">
.bg_tel a{
	color: white;
}
</style>
<body name="myinfo">
	<!--头部-->
	<c:if test="${isWeiXinFrom==false}">
		<div class="headbox">
			<div class="title f-20">个人中心</div>
			<!-- <a href="#" class="ico_back f-16" onclick="history.go(-1)">返回</a> <a
				href="#" class="ico_list">更多</a> -->
		</div>
	</c:if>
	<div class="banner_box02">
		<div class="tool">
    		<a href="${basePath }member/initEditMemberInfo" class="btn_follow" style="top:65px;"><i class="edit">编辑</i></a>
			<%-- <a href="${basePath }member/initEditMemberInfo" class="ico_edit"><img
				src="${basePath}appcssjs/images/membercenter/ico_edit.png"></a> --%>
		</div>
		<div class="user_box">
			<div class="img">
			<a href="${basePath}my/detailinfo">
				<c:if test="${empty user.headimage}">
					<img src="${basePath}appcssjs/images/page/pic_bg.png" width="86"
						height="86">
				</c:if>
				<c:if
					test="${!empty user.headimage}">
					<img src="${user.headimage_show}" width="86" height="86">
				</c:if>
				</a>
			</div>
			<c:if test="${user.individualstatus==1||user.enterprisestatus==1}">
				<div class="ico_v"></div>
			</c:if>
			<div class="name">
				<c:if test="${user.companyname!=null&&user.companyname!=''}">${user.companyname}</c:if>
				<c:if test="${user.companyname==null||user.companyname==''}">${user.nickname}</c:if>
			</div>
			<div class="name">
				 <a style="color: white" href="${basePath}my/detailinfo">个人详细信息</a>
			</div>
		</div>
		<div class="banner_xx">
			<i class="line"><span class="bg_name">${user.nickname}</span></i> 
			<i class="line"><span onclick="tel('${user.phone}');"
				class="bg_tel">${user.phone}</span></i>
		</div>
	</div>
	<div class="my_index">
		<ul>
			<li><a href="${basePath}my/mycoin"><b><img
						src="${basePath}appcssjs/images/membercenter/menu_01.png"></b><span>我的账户</span><i><img
						src="${basePath}appcssjs/images/public/right_link.png"></i></a>
				<div class="clear"></div></li>
			<%-- <li><a href="${basePath}my/myqrcode?memberid=${user.memberid}"><b><img
						src="${basePath}appcssjs/images/membercenter/menu_02.png"></b><span>我的二维码</span><i><img
						src="${basePath}appcssjs/images/public/right_link.png"></i></a>
				<div class="clear"></div></li> --%>
			<li><a href="${basePath}my/myattention"><b><img
						src="${basePath}appcssjs/images/membercenter/menu_03.png"></b><span>我的关注</span><i><img
						src="${basePath}appcssjs/images/public/right_link.png"></i></a>
				<div class="clear"></div></li>
			<c:if test="${isWeiXinFrom==false}">
			<li><a href="${basePath}my/goupdatepwd"><b><img
						src="${basePath}appcssjs/images/membercenter/menu_04.png"></b><span>修改密码</span><i><img
						src="${basePath}appcssjs/images/public/right_link.png"></i></a>
				<div class="clear"></div></li>
			</c:if>
			<li><a href="${basePath}complaint/gofeedback"><b><img
						src="${basePath}appcssjs/images/membercenter/menu_05.png"></b><span>意见反馈</span><i><img
						src="${basePath}appcssjs/images/public/right_link.png"></i></a>
				<div class="clear"></div></li>
			<li><a href="${basePath}my/cooperation"><b><img
						src="${basePath}appcssjs/images/membercenter/menu_06.png"></b><span>商务合作</span><i><img
						src="${basePath}appcssjs/images/public/right_link.png"></i></a>
				<div class="clear"></div></li>
			<li><a href="${basePath}/login/startAgreement"><b><img
						src="${basePath}appcssjs/images/membercenter/menu_07.png"></b><span>服务协议</span><i><img
						src="${basePath}appcssjs/images/public/right_link.png"></i></a>
				<div class="clear"></div></li>
		</ul>
	</div>
	<c:if test="${isWeiXinFrom==false}">
	<div class="out_btn">
		<div class="main_bigbtn">
			<input type="button" onclick="exit();" value="注销">
		</div>
	</div>
	</c:if>
<script type="text/javascript">
   function exit(){
	   var userAgentInfo = navigator.userAgent;   
	   if (userAgentInfo.indexOf("iPhone") > 0 && '${isWeiXinFrom}' == 'false' ){
		   exitIosApp();   
	   }
	   
	   window.location.href='<%=request.getContextPath() %>/login/inintLoginPassword';
	   
   }
   
   function tel(phone){
	   var userAgentInfo = navigator.userAgent;   
	   if (userAgentInfo.indexOf("Android") > 0 && '${isWeiXinFrom}' == 'false' ){
		   dialTel(phone);
	   }
	   
   }
   
</script>
	<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
	<!--底部导航-->

</body>
</html>
