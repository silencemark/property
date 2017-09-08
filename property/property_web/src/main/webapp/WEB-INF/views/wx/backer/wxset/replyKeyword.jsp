<%@ page language="java" 
	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<!--[if lt IE 7 ]><html class="ie6 ieOld"><![endif]-->
<!--[if IE 7 ]><html class="ie7 ieOld"><![endif]-->
<!--[if IE 8 ]><html class="ie8 ieOld"><![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<html>
	<!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>首页</title>
		<meta name="Keywords" content="">
		<meta name="Description" content="">
		<jsp:include page="../../../../../black/include/header.jsp" flush="true"></jsp:include>
		<link href="<%=request.getContextPath()%>/theme/grey/style/reset.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/theme/grey/style/base.css"
			rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath()%>/theme/grey/style/page.css"
			rel="stylesheet" type="text/css" />
<style>
.webContainer{
	padding-top:80px;width: auto;
}
.webContainer .webMain .mainHeader .menu{
	padding-top: 0px;
}
.webContainer .webMain{
	float: left;
}
.saved .content{
    margin-left: 0px; 
	min-height:auto;
}
<%-- .webContainer{
	background:url(<%=request.getContextPath()%>/black/js/weixin/images/ui/lineB.png) 15x 0 repeat-y #fff;
} --%>
.form fieldset div.l div.i {
    margin-left: 10px;
}
</style>			
	</head>

	<body class="p-weixin p-weixin-reply">
		<div class="wrapper">
		<jsp:include page="../../../../../black/include/top.jsp"></jsp:include>
	<div class="content">
		<div class="crumb">
            <h5>微信管理 &#187; 关键字回复</h5>
        </div>
		<!--container-->
		<!--container-->
		<div class="webContainer clearFix containerWcMenu" id="webContainer">

			<jsp:include page="../../../../../black/include/left.jsp"></jsp:include>


			<!--main-->
			<div class="webMain"  style="width: 100%">
				<div class="mainHeader">
					<h1>
						关键字回复
					</h1>
					<ul class="menu clearFix">

						<li>
							<a
								href="<%=request.getContextPath()%>/ws/backer/wxset/findReplyAttention">被关注自动回复</a>
						</li>
						<li class="current">
							<a
								href="<%=request.getContextPath()%>/ws/backer/wxset/findReplyKeyword">关键字回复</a>
						</li>
						<%-- <li>
							<a
								href="<%=request.getContextPath()%>/ws/backer/wxset/findReplyNoKeyword">未匹配到关键字回复</a>
						</li> --%>
					</ul>
				</div>
				<div class="mainBody">


					<!--表格-->
					<table class="table"  id="keywordTable">
						<thead>
							<tr>
								<td class="t1">
									编号
								</td>
								<td class="t2">
									规则名
								</td>
								<td class="t3">
									回复类型
								</td>
								<td class="t4">
									回复次数
								</td>
								<td class="t5">
									创建时间
								</td>
								<td class="t5">
									状态
								</td>
								<td class="t6">
									操作
								</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${dataList }" var="item" varStatus="st" >
								<tr>
									<td class="t1">${st.index+1}</td>
									<td class="t2">${item.rulename}</td>
									<td class="t3">${item.msgtypename}</td>
									<td class="t4">${item.replycount}</td>
									<td class="t5"><fmt:formatDate value="${item.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									<td class="t5"><c:choose><c:when test="${item.ifactive==1}">开启</c:when>
										<c:when test="${item.ifactive==0}">关闭</c:when></c:choose></td>
									
									<td class="t6">
										<p class="console" style="display: block;">
											<c:choose><c:when test="${item.ifactive==1}"><a data-id="${item.ruleid}" href="javascript:void(0);" 
												class="ifactiveclose">关闭</a></c:when>
											<c:when test="${item.ifactive==0}"><a data-id="${item.ruleid}" href="javascript:void(0);" 
												class="ifactiveopen">开启</a></c:when></c:choose>
										
											<a href="<%=request.getContextPath()%>/ws/backer/wxset/initReplyKeywordAdd?ruleid=${item.ruleid}" 
												class="revise">修改</a>
											<a href="javascript:void(0)" data-id="${item.ruleid}"  class="delete">删除</a>
										</p>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="10">
									${pager}
								</td>
							</tr>
						</tfoot>
					</table>

					<!--主提交按钮-->
					<div class="btnCon">
						<a href="<%=request.getContextPath()%>/ws/backer/wxset/initReplyKeywordAdd" class="btnA">新增</a>
					</div>


				</div>
			</div>
			<!--main end-->

		</div>
		<!--container end-->


		<script src="<%=request.getContextPath()%>/theme/black/js/gtyutil.js"></script>
		<script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/theme/grey/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/theme/grey/js/common.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/theme/grey/js/wechat/keywordList.js"></script>
		<script type="text/javascript">
		$(function(){
			//删除
			$(document).delegate('.delete', 'click', function() {
				//删除规则
				var ruleid = $(this).data("id");
				var url = "<%=request.getContextPath()%>/ws/backer/wxset/deleteReplyKeyword?ruleid="+ruleid ;
				$.alert({
					title: "温馨提示",
					txt: "确定要删除该关键字回复？删除之后无法恢复",
					btnY: "确定",
					btnN: "取消",
					callbackY: function(){
						location.href = url ;		
					}
				});
				//=========
			});

			//开启
			$(document).delegate('.ifactiveopen', 'click', function() {
				var ruleid = $(this).data("id");
				var url = "<%=request.getContextPath()%>/ws/backer/wxset/changeReplyKeyStatus?ruleid="+ruleid+"&ifactive=1" ;
				$.alert({
					title: "温馨提示",
					txt: "确定要开启该规则？",
					btnY: "确定",
					btnN: "取消",
					callbackY: function(){
						location.href = url ;		
					}
				});
				//=========
			});

			//关闭
			$(document).delegate('.ifactiveclose', 'click', function() {
				var ruleid = $(this).data("id");
				var url = "<%=request.getContextPath()%>/ws/backer/wxset/changeReplyKeyStatus?ruleid="+ruleid+"&ifactive=0" ;
				$.alert({
					title: "温馨提示",
					txt: "确定要关闭该规则？",
					btnY: "确定",
					btnN: "取消",
					callbackY: function(){
						location.href = url ;		
					}
				});
				//=========
			});
		});
		</script>





	</body>
</html>

