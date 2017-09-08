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
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
<title>首页</title>
<meta name="Keywords" content="">
<meta name="Description" content="">

<jsp:include page="../../../../black/include/header.jsp" flush="true"></jsp:include>
<link href="<%=request.getContextPath() %>/theme/grey/style/wxreset.css" rel="stylesheet" type="text/css" />
<%--
<link href="<%=request.getContextPath() %>/theme/grey/style/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/theme/grey/style/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/theme/grey/style/page.css" rel="stylesheet" type="text/css" />
 --%>
</head>

<body>
	
	<div class="wrapper">
		<c:choose><c:when test="${null == pvMap and '' == pvMap}">
			
			<div class="wrapper-title">
				<h1>素材不存在</h1>
			</div>
			
		</c:when>
		<c:otherwise>
			
			<div class="wrapper-title">
				<h1>${pvMap.title}</h1>
				<span><fmt:formatDate value="${pvMap.createtime }" pattern="yyyy-MM-dd"/><span>${pvMap.author}</span><span>滴答叫人</span></span>
			</div>
	
			<div class="wrapper-content">
				<!-- 封面图片展示  -->
				<c:if test="${null != pvMap.ifviewcontent and 1 == pvMap.ifviewcontent}">
					<p><img alt="" src="<%=request.getContextPath() %>${pvMap.imgurl}"/></p>
				</c:if>
				
				<P style="TEXT-INDENT: 2em">
					${pvMap.content}
				</P>
				
			</div>
			
			<c:choose><c:when test="${null == pvMap.linkurl or '' == pvMap.linkurl}">
<%--				<a href="javascript:void(0);" class="outlink">阅读原文</a>--%>
			</c:when>
			<c:otherwise>
				<a href="${pvMap.linkurl}" class="outlink">阅读原文</a>
			</c:otherwise></c:choose>
			
		</c:otherwise></c:choose>	
		
	</div>
	
	<!-- ---------over --------------- -->
</div>

<%--
	<script src="<%=request.getContextPath()%>/theme/black/js/gtyutil.js"></script>
	<script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/swfobject.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/common.js"></script>
 --%>

</body>
</html>