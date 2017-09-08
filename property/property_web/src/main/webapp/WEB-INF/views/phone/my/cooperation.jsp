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
<title>商务合作</title>
<link href="${basePath}appcssjs/style/public.css" type="text/css"
	rel="stylesheet">
<link href="${basePath}appcssjs/style/page.css" type="text/css"
	rel="stylesheet">
</head>

<body name="myinfo">
	<!--头部-->
	<c:if test="${isWeiXinFrom==false}">
		<div class="headbox">
			<div class="title f-20">商务合作</div>
			<a href="javascript:history.go(-1)" class="ico_back f-16">返回</a>
			<!-- <a href="#" class="ico_list">更多</a> -->
		</div>
	</c:if>
	<div class="cooperation">
	<div class="logo_img"><img src="${basePath}appcssjs/images/membercenter/logo.png"></div>
    <ul>
    	<!-- <li><span>公司名称</span><i>上海宏恒软件科技公司</i></li> -->
        <li><span>联系人</span><i>王先生</i></li>
        <li><span>联系电话</span><i>18621689595</i></li>
        <li><span>电子邮箱</span><i>shiyaotech@ddjiaoren.com</i></li>
        <!-- <li class="last"><span>QQ</span><i>38902266</i></li> -->
    </ul>
</div>
	<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
	<!--底部导航-->

</body>
</html>

