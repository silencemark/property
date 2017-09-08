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
<title>首页</title>
<meta name="Keywords" content="">
<meta name="Description" content="">
<jsp:include page="../../../../black/include/header.jsp" flush="true"></jsp:include>
<link href="<%=request.getContextPath() %>/theme/grey/style/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/theme/grey/style/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/theme/grey/style/page.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/index.js"></script>
</head>
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
	<body class="p-weixin p-weixin-qunfa">
	<div class="wrapper">

	<jsp:include page="../../../../black/include/top.jsp"></jsp:include>
	<div class="content">
		<div class="crumb">
            <h5>微信管理 &#187; 群发记录</h5>
        </div>
        
	<!--container-->
	<div class="webContainer clearFix containerMassSendHistory" id="webContainer">

		<jsp:include page="../../../../black/include/left.jsp"></jsp:include>

		<!--main-->
		<div class="webMain" style="width:100%">
			<div class="mainHeader">
				<h1>高级群发功能</h1>
				<ul class="menu clearFix">
					<li><a href="<%=request.getContextPath() %>/ws/backer/wxset/findNoticeSend">新建群发消息</a></li>
					<li class="current"><a href="<%=request.getContextPath() %>/ws/backer/wxset/findNoticeSendHistory">已发送</a></li>
				</ul>
			</div>
			<div class="mainBody">

				
				<!--表格-->
				<table class="table" id="table">
					<thead>
						<tr>
							<td style="width:50px;">编号</td>
							<td style="width:100px;">消息类型</td>
							<td style="width:40px;">发送人数</td>
							<td class="t4" style="width:35px;">状态</td>
							<td class="t4" style="width:50px;">发送日期</td>
							<td class="t4" style="width:50px;">消息内容</td>
							<td class="th1" style="width:50px">拒绝原因</td>
							<td class="th1" style="width:100px">其他原因</td>
							<td class="t5">操作</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dataList }" var="item" varStatus="st">
							<tr>
								<td >${st.index+1}</td>
								<td>${item.msgtypename}</td>
								<td ><c:choose><c:when test="${item.sendcount==null or item.sendcount=='' }">0</c:when>
										<c:otherwise>${item.sendcount }</c:otherwise></c:choose>
								</td>
								<td class="t3"><c:choose><c:when test="${item.sendstatus==0 }">未审核</c:when>
										<c:when test="${item.sendstatus==1 }">已通过</c:when>
										<c:when test="${item.sendstatus==2 }">已拒绝</c:when>
										<c:when test="${item.sendstatus==3 }">发送成功</c:when>
										<c:when test="${item.sendstatus==4 }">发送失败</c:when>
										</c:choose>
								</td>
								
								<td class="t4"><fmt:formatDate value="${item.sendtime}" pattern="yyyy-MM-dd HH:mm"/></td>
								<td class="t4">
								<c:choose>
											<c:when test="${item.msgtype==5}">
												<img src="${item.imgurl}" onclick="imgutil.FDIMG(this)" width="50" height="50"/>
											</c:when>
											<c:otherwise>
											${item.content}
											</c:otherwise>
								</c:choose>
								</td>
								<td align="t3">
									<c:choose><c:when test="${item.refusereason == 1 }">含有法律禁止信息</c:when>
									<c:when test="${item.refusereason == 2 }">主题不明或描述不清</c:when>
									<c:when test="${item.refusereason == 3 }">重复提交</c:when>
									<c:when test="${item.refusereason == 4 }">图片使用不当</c:when>
									<c:when test="${item.refusereason == 5 }">信息不准确 </c:when>
									</c:choose>
								</td>
								<td align="t3" class ="wordBreak" style ="width:25%">
								
								<center><p title="${item.otherreason }" style="width:100px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${item.otherreasonshort }</p></center>
								
								</td>
								<td class="t5">
									<p class="console" style="display:block;">
										<c:choose>
											<c:when test="${item.msgtype==3}">
												<a href="${item.imgtexturl}" >详情</a>&nbsp;&nbsp;
											</c:when>
											
										</c:choose>
										
										<a href="javascript:void(0)" data-id="${item.id }" class="delete">删除</a>
									</p>
								</td>
							</tr>						
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="8">
								${pager}
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<!--main end-->

	</div>
	<!--container end-->
	</div></div>

	<script src="<%=request.getContextPath()%>/theme/black/js/gtyutil.js"></script>
	<script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/wechat/massSendHistory.js"></script>
	<script type="text/javascript">
	$(function(){
		//删除消息记录
		$(document).delegate('.delete', 'click', function() {
			var id = $(this).data("id");
			$.alert({
				title: "温馨提示",
				txt: "确定要删除该发送记录吗？",
				btnY: "删除",
				btnYcss: "btnC",
				btnN: "取消",
				callbackY: function(){
					var url = "<%=request.getContextPath() %>/ws/backer/wxset/deleteNoticeSend?a=1";
					if(!hhutil.isEmpty(id)){
						url += "&id="+id;
					}
					hhutil.ajax(url,null,function(data){
						location.href = "<%=request.getContextPath() %>/ws/backer/wxset/findNoticeSendHistory";
					});
				}
			});
		});
	});
	</script>	
</body>
</html>