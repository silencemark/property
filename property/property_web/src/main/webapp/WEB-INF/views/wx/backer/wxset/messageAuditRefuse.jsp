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

		<body class="p-weixin p-weixin-audit">
	<div class="wrapper">
		<jsp:include page="../../../../../black/include/top.jsp"></jsp:include>
<div class="content">
		<div class="crumb">
            <h5>微信管理 &#187; 群发审核</h5>
        </div>	
		<!--container-->
		<div class="webContainer clearFix" id="webContainer">

			<jsp:include page="../../../../../black/include/left.jsp"></jsp:include>

			
		<!--main-->
		<div class="webMain"   style="width: 100%">
			<div class="mainHeader">
				<h1>群发审核</h1>
				<ul class="menu clearFix">
					<li><a href="<%=request.getContextPath() %>/ws/backer/wxset/findMessageAudit">未审核(${auditNoCount })</a></li>
					<li><a href="<%=request.getContextPath() %>/ws/backer/wxset/findMessageAuditPass">已通过(${auditSuccessCount })</a></li>
					<li class="current"><a href="<%=request.getContextPath() %>/ws/backer/wxset/findMessageAuditRefuse">已拒绝(${auditRefuseCount })</a></li>
				</ul>
			</div>
			<div class="mainBody">

 				<!-- 查询条件 -->
 				<form id="memberform" action="<%=request.getContextPath() %>/ws/backer/wxset/findMessageAuditRefuse" method="post">
				<div class="tableTools clearFix mb30">
					<div class="i mr20"><input type="text" id="content" name="content" value="${pvMap.content}"   placeholder="请输入查询条件"  class="input w180" /></div>
					<div class="i"><input type="submit" class="btnB btnSmall" value="查找"></div>
				</div>
				</form>
				

				<!--表格-->
				<div class="fl">

					<table class="table" id="userTable">
						<thead>
							<tr>
							<td class="th1" style="width:100px">编号</td>
								<td class="th1" >发布类型</td>
								<td class="th1">发布内容</td>
								<td class="th1" >发布作者</td>
								<td class="th1" >发布时间</td>
							
								<td class="th1" >拒绝原因</td>
								<td class="th1" >其他原因</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${dataList }" var="item" varStatus="st" >
							<tr> 
								<td align="t3">${st.index+1}</td>
								<td align="t3">${item.msgtypename}</td>
								<td align="t3"><c:choose><c:when test="${item.msgtypekey=='txt'}"><center><p title="${item.content}" style="width:100px; white-space: nowrap;text-overflow: ellipsis;">${item.contentshort}</p></center></c:when>
										<c:when test="${item.msgtypekey=='img'}"><img src="${item.imgpath}" width="100" height="50"/></c:when>
										<c:when test="${item.msgtypekey=='audio'}">
											<a href="javascript:void(0);" onclick="$.audioPlayer('<%=request.getContextPath()  %>${item.audiopath}');">播放</a>&nbsp;&nbsp;
											<a href="javascript:void(0);" onclick="$.audioPlayer.remove();">暂停</a>&nbsp;&nbsp;
										</c:when>
										<c:when test="${item.msgtypekey=='article'}">
											 <c:forEach items="${item.articleList}" var="article" varStatus="st2">
												<a target="_blank" href="<%=request.getContextPath() %>/ws/backer/wxset/showDTImgTextRefuse?imgtextlistid=${article}">
													[图文消息${st2.index+1}]
												</a><br/>
											</c:forEach>
										</c:when>
										</c:choose>
								</td>
								<td align="t3"><c:choose><c:when test="${item.roletype==1}">固特异官方</c:when>
										<c:otherwise>${item.agencyname}</c:otherwise></c:choose></td>
								<td align="t3"><fmt:formatDate value="${item.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td align="t3">
									<c:choose><c:when test="${item.refusereason == 1}">含有法律禁止信息</c:when>
									<c:when test="${item.refusereason == 2}">主题不明或描述不清</c:when>
									<c:when test="${item.refusereason == 3}">重复提交</c:when>
									<c:when test="${item.refusereason == 4}">图片使用不当</c:when>
									<c:when test="${item.refusereason == 5}">信息不准确 </c:when>
									</c:choose>
								</td>
								<td align="t3" class ="wordBreak" style ="width:25%">
								
								<center><p title="${item.otherreason}" style="width:100px; overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">${item.otherreasonshort}</p></center>
								
								</td>
							</tr>
							</c:forEach>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="7">
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/theme/grey/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/theme/grey/js/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/theme/grey/js/wechat/messageaudit.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/swfobject.js"></script>
	<script type="text/javascript">
	//审核通过
	function auditPass(id){
		var url = "<%=request.getContextPath() %>/ws/backer/wxset/noticeSendAudit?a=1";
		url+="&sendstatus=1&id="+id ;
		location.href = url ;
	}
	//审核拒绝
	function auditRefuse(id,refusereason,otherreason){
		var url = "<%=request.getContextPath() %>/ws/backer/wxset/noticeSendAudit?a=1";
		url+="&sendstatus=2&id="+id ;
		url+="&refusereason="+refusereason ;
		url+="&otherreason="+otherreason ;
		location.href = url ;
	}	
	
	</script>

</body>
</html>