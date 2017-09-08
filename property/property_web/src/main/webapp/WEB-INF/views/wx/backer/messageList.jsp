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
<title>消息列表</title>
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
				<h1>消息通知</h1>
			</div>
			
			<div class="mainBody">
				
				
				<!--表格-->
				<div class="fl" style="width:100%;">

					<table class="table" id="userTable" >
						<thead>
							<tr>
								<td class="t1">编号</td>
								<td class="t2">标题</td>
								<td class="t3">内容</td>
								<td class="t7">发送时间</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${dataList }" var="item" varStatus="st" >
								<tr>
									<td class="t1">${st.index+1}</td>
									<td class="t2">${item.title}</td>
									<td class="t5">${item.content}</td>
									<td class="t7"><fmt:formatDate value="${item.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td class="t7"><a href="<%=request.getContextPath() %>/account!showMessageView.action?id=${item.title}">查看</a></td>
									
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="4">
									${pager}
								</td>
							</tr>
						</tfoot>
					</table>

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