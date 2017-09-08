<%@ page language="java"  pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<!DOCTYPE HTML>
<!--[if lt IE 7 ]><html class="ie6 ieOld"><![endif]-->
<!--[if IE 7 ]><html class="ie7 ieOld"><![endif]-->
<!--[if IE 8 ]><html class="ie8 ieOld"><![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html><!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>粉丝数据</title>
<meta name="Keywords" content="">
<meta name="Description" content="">
<jsp:include page="../../../../black/include/header.jsp" flush="true"></jsp:include>
<link href="<%=request.getContextPath() %>/theme/grey/style/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/theme/grey/style/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/theme/grey/style/page.css" rel="stylesheet" type="text/css" />
</head>

<body>

	<jsp:include page="../../../../black/include/top.jsp"></jsp:include>

	<!--container-->
	<div class="webContainer clearFix containerWechatUsers" id="webContainer">

		<jsp:include page="../../../../black/include/left.jsp"></jsp:include>

		<!--main-->
		<div class="webMain">
			<div class="mainHeader">
				<h1>粉丝数据</h1>
			</div>
			
			<div class="mainBody">
				
				<!--表单-->
				<div class="form">
					<div class="formHeader">
						<h1>${pvMap.nickname}</h1>
					</div>
					<form>
						<fieldset>
							<div class="l">
								<h6>编号</h6>
								<div class="i"><p>${pvMap.memberid}</p></div>
							</div>
							<div class="l head">
								<h6>头像</h6>
								<div class="i"><img src="${pvMap.headimg}" width="100" height="50"/></div>
							</div>
							<div class="l">
								<h6>昵称</h6>
								<div class="i"><p>${pvMap.nickname}</p></div>
							</div>
							<div class="l">
								<h6>性别</h6>
								<div class="i"><p><c:choose><c:when test="${pvMap.sex==1}">男</c:when>
									<c:when test="${pvMap.sex==2}">女</c:when></c:choose></p></div>
							</div>
							<div class="l">
								<h6>地区</h6>
								<div class="i"><p>${pvMap.province}-${pvMap.city}</p></div>
							</div>
							<div class="l">
								<h6>手机号</h6>
								<div class="i"><p>${pvMap.phone}</p></div>
							</div>
							<div class="l">
								<h6>所属帮派</h6>
								<div class="i"><p>${pvMap.agencyname}</p></div>
							</div>
							<%--
							<div class="l">
								<h6>车型</h6>
								<div class="i"><p>${pvMap.cartypename}</p></div>
							</div>
							<div class="l">
								<h6>轮胎品种</h6>
								<div class="i"><p>${pvMap.tiretypename}</p></div>
							</div>
							 --%>
							<div class="l">
								<h6>参与活动</h6>
								<div class="i"><p>${pvMap.activitycount}次</p></div>
							</div>
						</fieldset>
					</form>
				</div>

				<!--主提交按钮-->
				<div class="btnCon">
					<a href="javascript:void(0);" onclick="window.history.go(-1);" class="btnD">返回</a>
				</div>
				
				

			</div>
		</div>
		<!--main end-->

	</div>
	<!--container end-->

	<script src="<%=request.getContextPath()%>/theme/black/js/gtyutil.js"></script>
	<script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/swfobject.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/wechat/users.js"></script>
	<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/theme/grey/js/wechat/agency.js"></script>
	
	<script type="text/javascript">
	$(function(){
		
	});
	</script>
</body>
</html>