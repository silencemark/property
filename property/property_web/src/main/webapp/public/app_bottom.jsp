<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %><%-- web-app version="2.5" 时解决低版本不解析el表达式 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page isELIgnored="false" %><%-- web-app version="2.5" 时解决低版本不解析el表达式 --%>
<script type="text/javascript">
$(function(){   
	//加载选中样式
	$('#'+$('body').attr('name')).attr('class','dq');
});
</script>
<!--

//-->	
<!--底部导航-->
<div class="bottom_none"></div>
<c:if test="${isWeiXinFrom==true || isweixin==true}">
<div class="bottom_menu">
	<ul>
    	<li id="homepage"><a href="<%=Constants.PROJECT_PATH %>index/employerindex?"+new Date().getTime()><b class="bg_home"></b><span>首页</span></a></li>
        <li id="supplier"><a href="<%=Constants.PROJECT_PATH %>weixin/supplier/getsupplierlist?"+new Date().getTime()><b class="bg_supplier"></b><span>供应商</span></a></li>
        <li id="information"><a href="<%=Constants.PROJECT_PATH %>notice/inintMessage?"+new Date().getTime()><b class="bg_msg"></b><span>消息</span></a></li>
        <li id="myinfo"><a href="<%=Constants.PROJECT_PATH %>my/mycenter?"+new Date().getTime()><b class="bg_user"><!-- <em>8</em> --></b><span>我的</span></a></li>
    </ul> 
</div> 
</c:if>  

<!--底部导航-->
