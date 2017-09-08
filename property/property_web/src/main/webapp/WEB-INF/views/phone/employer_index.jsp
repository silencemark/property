<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>雇主首页</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/index.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>  
<script src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
</head>

<body name="homepage">
<!--头部-->
<c:if test="${isWeiXinFrom==false}">
	 <div class="headbox">
		<div class="title f-20">雇主首页</div>
	     <!-- <a href="javascript:history.go(-1)" class="ico_back f-16">返回</a> -->
    <!-- <a href="#" class="ico_list">更多</a> -->
	</div>
</c:if>
<!--banner开始 注明：banner图尺寸大小是640px*250px-->
<div class="contentWrap">
	<div class="user_title">
    	<div class="a_box">
    	    <a href="javascript:changeView('<%=request.getContextPath() %>/index/employerindex?type=1');" class="active">雇主</a>
    	    <a href="javascript:changeView('<%=request.getContextPath() %>/index/workmanindex?type=1');">工人</a></div>
    </div>
    <ul id="slider">
    	<c:forEach items="${banerlist }" var="item">
        	<li style="display:block" onclick="window.location.href='${item.linkurl}'"><img src="${item.imgurl }"  alt="" width="100%" height="146"></li> 
        </c:forEach> 
    </ul>
    <div id="pagenavi">
    	<c:forEach items="${banerlist }" var="item" varStatus="count"> 
        	<a href="javascript:void(0);">${count.index+1 }</a>
        </c:forEach>
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/touchScroll.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/touchslider.dev.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/run.js"></script>
<!--banner结束-->
<script type="text/javascript">
function  changeView(url){
	  var userAgentInfo = navigator.userAgent;   
	   if(userAgentInfo.indexOf("Android") > 0 && '${isWeiXinFrom}' == 'false' ){
		   changeIndexView('<%=Constants.PROJECT_PATH%>'+url);
		   window.location.href=url; 
	   }else{
		 window.location.href=url; 
	   }
}

 function isComplete(type){
	 
	 
	 if('${isWeiXinFrom}' == 'true'){
	   
	 
	 if('${user.iscompletion}' == '1' || '${user.status_rz}' == '1'){
		 if(type == '1'){
			 window.location.href="<%=request.getContextPath()%>/employer/intojob";
		 }else{
			 window.location.href="<%=request.getContextPath()%>/employer/intoproject";
		 }
	 }else{
		   swal({
				title : "",
				text : "发布内容时请完善个人身份证信息或者企业信息！",
				type : "info",
				showCancelButton : true,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "去完善",
				cancelButtonText : "取消",
				closeOnConfirm : true
			}, function(){
				 window.location.href="<%=request.getContextPath()%>/member/initEditMemberInfo";
			});	
		 
	 }
   }else{
	 if('${id}' == 'xxx'){
	<%--    swal({
			title : "",
			text : "请登录后 操作！",
			type : "",
			showCancelButton : true,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "去登陆",
			cancelButtonText : "取消",
			closeOnConfirm : true
		}, function(){
			 window.location.href="<%=request.getContextPath()%>/login/inintLoginPassword";  
		});	 --%>
		window.location.href="<%=request.getContextPath()%>/login/inintLoginPassword";
	 
	   }else{
		   
			 if('${user.iscompletion}' == '1' && '${user.refusetype}' == '0'){
				 if(type == '1'){
					 window.location.href="<%=request.getContextPath()%>/employer/intojob";
				 }else{
					 window.location.href="<%=request.getContextPath()%>/employer/intoproject";
				 }
			 }else{
				   
				  if('${user.refusetype}' == '1'){
					swal({
					  title : "",
						text : "您的个人信息或者企业信息认证失败,请修改!",
						type : "",
						showCancelButton : true,
						confirmButtonColor : "#ff7922",
						confirmButtonText : "去修改",
						cancelButtonText : "取消",
						closeOnConfirm : true
					}, function(){
						 window.location.href="<%=request.getContextPath()%>/member/initEditMemberInfo";
					});	
					  
					  
				  }else{
				   swal({
						title : "",
						text : "请先完善您的个人资料后再操作！",
						type : "",
						showCancelButton : true,
						confirmButtonColor : "#ff7922",
						confirmButtonText : "去完善",
						cancelButtonText : "取消",
						closeOnConfirm : true
					}, function(){
						 window.location.href="<%=request.getContextPath()%>/member/initEditMemberInfo";
					});	
				  }
			 }
		   
	      } 
    } 
	 
} 
</script>
<div class="index_menu">
	<span><a href="javascript:void(0);" onclick="isComplete('1')"><b><img src="<%=request.getContextPath() %>/appcssjs/images/index/menu_01.png" alt="发布招工"></b><i>发布招工</i></a></span>
    <span><a href="javascript:void(0);" onclick="isComplete('2')"><b><img src="<%=request.getContextPath() %>/appcssjs/images/index/menu_02.png" alt="发布项目"></b><i>发布项目</i></a></span>
    <div class="clear"></div>
</div>

<div class="index_list">
	<div class="t_name"><b>line</b><span>我的订单</span><a href="<%=request.getContextPath() %>/order/getEmployerOrderList">更多</a></div>
    <ul>
    	<c:forEach items="${orderlist }" var="item">
	    	<li>
	        	<div class="user_img" onclick="javascript:window.location.href='<%=request.getContextPath() %>/my/detailinfo'">
	            	<b><img src="${item.headimage }" width="62" height="62"></b>
	                <span class="name_v">${item.contacter }</span>
	            </div>
	            <div
	             onclick="
	    	    <c:choose>
	    		<c:when test="${item.jobid != null}">
		           location.href='/employer/workermapinfo?jobid=${item.jobid}&orderid=${item.orderid}'
	    		</c:when>
	    		<c:when test="${item.projectid != null}">
		           location.href='/employer/releasemapinfo?projectid=${item.projectid}&orderid=${item.orderid}'
	    		</c:when>
	    	   </c:choose>
	    	   ">
	            <div class="user_box">
	                <c:if test="${item.jobid != null}">
	                	<div class="xx_01 bg_gong word_hidden">
	                </c:if>
	                <c:if test="${item.projectid != null}">
	                	<div class="xx_01 bg_xiang word_hidden">
	                </c:if>
	                ${item.title}</div>
	                <div class="xx_02 word_hidden"><fmt:formatDate value="${item.starttime }" pattern="MM-dd HH:mm"/>  至 <fmt:formatDate value="${item.endtime }" pattern="MM-dd HH:mm"/></div>
	                <div class="xx_03 word_hidden">${item.address }</div>
	            </div>
	            <div class="tool_xx">
	            	<div class="num">
	            	<c:if test="${item.sumcount != 0}">
	            		${item.numcount }/${item.sumcount  }人
	            	</c:if> 
	            	<c:if test="${item.sumcount == 0 || item.sumcount==null}">
	            		${item.numcount }/1人
	            	</c:if>
	            	</div>
	            	<c:choose>
	            		<c:when test="${item.status == 1 }">
	            			<div class="state bg_green">处理中</div>
	            		</c:when>
	            		<c:when test="${item.status == 2 }">
	            			<div class="state bg_yellow">已完成</div>
	            		</c:when>
	            	</c:choose>
	            </div>
	            </div>
	        </li>
        </c:forEach>
    </ul>
</div>

<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
</body>
</html>
