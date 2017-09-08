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
<title>工人首页</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/index.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
</head>

<body name="homepage">
<!--头部-->
<c:if test="${isWeiXinFrom==false}">
	 <div class="headbox">
		<div class="title f-20">工人首页</div>
	      <!-- <a href="javascript:history.go(-1)" class="ico_back f-16">返回</a> -->
    <!-- <a href="#" class="ico_list">更多</a> -->
	</div>
</c:if>
<!--banner开始 注明：banner图尺寸大小是640px*250px-->
<div class="contentWrap">
	<div class="user_title">
    	<div class="a_box">
    	    <a href="javascript:changeView('<%=request.getContextPath() %>/index/employerindex?type=1');" >雇主</a>
    	    <a href="javascript:changeView('<%=request.getContextPath() %>/index/workmanindex?type=1');" class="active">工人</a>
       </div>
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

<div class="index_menu">
	<span><a href="<%=request.getContextPath()%>/workers/hireworkerlist"><b><img src="<%=request.getContextPath() %>/appcssjs/images/index/menu_03.png" alt="快速抢单"></b><i>快速抢单</i></a></span>
    <span><a href="<%=request.getContextPath()%>/workers/projectlist"><b><img src="<%=request.getContextPath() %>/appcssjs/images/index/menu_04.png" alt="承接项目"></b><i>承接项目</i></a></span>
    <div class="clear"></div>
</div>

<div class="index_list">
	<div class="t_name"><b>line</b><span>我的订单</span><a href="<%=request.getContextPath()%>/order/getEmployeeOrderList?type=1">更多</a></div>
    <ul>
    	<c:forEach items="${orderlist}" var="item">
	    	<li>
	        	<div class="user_img" onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/detailinfo?userid=${item.publisherid }'">
	            	<b><img src="${item.headimage}" width="62" height="62"></b>
	                <span class="name_v" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">${item.nickname }</span>
	            </div>
	            <div onclick="
	             <c:choose>
	    		<c:when test="${item.jobid != null}">
	    		  	location.href='<%=request.getContextPath() %>/employer/applyJobDetail?applyorderid=${item.applyorderid}'
	    		</c:when>
	    		<c:when test="${item.projectid != null}">
	    			location.href='<%=request.getContextPath() %>/employer/applyProjectDetail?applyorderid=${item.applyorderid}'
	    		</c:when>
	    	</c:choose>"
	            >
	            <div class="user_box">
	               <div class="xx_01 ${item.jobid != null?'bg_gong':'bg_xiang'}  word_hidden">
	               <span>${item.title }</span>
	                <c:if test="${item.sumcount != 0 }">
	                	<i>|</i><em>${item.sumcount }人</em> 
	                </c:if>
	                </div>
	                <div class="xx_02 word_hidden">${item.companyname }</div>
	                <div class="xx_03 word_hidden">剩余${item.daytext }</div>  
	            </div>
	            <div class="tool_xx" style="width: 88px">
	            	<div class="star">
	            		<c:forEach  var="i" begin="1" end="${item.evaluationavg }"><img width="14px" height="13px" src="<%=request.getContextPath() %>/appcssjs/images/public/star.png"></c:forEach>
	            	</div>
	            	<div class="state bg_green">
	            	 <c:choose>
	            	    <c:when test="${item.status == 1 }">待处理</c:when>
	            	    <c:when test="${item.status == 2 }">已取消</c:when>
	            	    <c:when test="${item.status == 3 }">已成交</c:when>
	            	    <c:when test="${item.status == 4 }">暂不合适</c:when>
	            	    <c:when test="${item.status == 5 }">工人取消</c:when>
	            	    <c:when test="${item.status == 6 }">雇主同意</c:when>
	            	    <c:when test="${item.status == 7 }">雇主拒绝</c:when>
	            	    <c:when test="${item.status == 8 }">雇主取消</c:when>
	            	    <c:when test="${item.status == 9 }">工人同意</c:when>
	            	    <c:when test="${item.status == 10 }">工人拒绝</c:when>
	            	    <c:when test="${item.status == 11 }">已过期</c:when>
	            	    <c:when test="${item.status == 12 }">已被抢走</c:when>
                       </c:choose>
	            	</div>
	            </div>
	            </div>
	        </li>
        </c:forEach>

    </ul>
</div>
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

</script>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
</body>
</html>
