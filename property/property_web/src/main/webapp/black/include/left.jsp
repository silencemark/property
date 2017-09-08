<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.lr.backer.util.CookieUtil"%>
<%@page import="com.lr.backer.util.UserUtil"%>
<%@page import="com.alibaba.fastjson.JSON"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<div class="sidebar affix">
	<ul class="main-nav open-active">
	
		<% 
		try{
			Map<String, Object> map = UserUtil.getMenu(request);
			String a=map.get("data").toString();
			List<Map<String, Object>> list=CookieUtil.getList(a);
			request.setAttribute("menuList", list);
			request.setAttribute("menuJson", JSON.toJSONString(list));
		}catch(Exception e){
			response.sendRedirect("/system/login");
		}
		%>
		<li class="nav-dashboard">
			<a href="<%=request.getContextPath() %>/system/index"><i
				class="ico-dashboard "></i>首页</a> 
		</li>
	 			<c:forEach items="${menuList}" var="item">
	 				<c:choose>
		 				<c:when test="${item.parentid=='0'}">
			 				<li class="${item.remark} dropdown">
				 				<a href="javascript:void(0);"> <i class="ico-gear"></i>${item.name}<span class="caret"></span> </a>
					 				<ul class="sub-nav">
					 					<c:forEach items="${menuList}" var="it">
							 				<c:choose>
							 					<c:when test="${item.id==it.parentid}">
													 <li class="${it.remark}">
														<a href="<%=request.getContextPath()%>${it.linkurl}">${it.name}</a>
													</li> 
							 				</c:when>
							 				</c:choose>
							 		</c:forEach>
					 			</ul>
			 				</li>
		 				</c:when>
		 				</c:choose>
	 			</c:forEach>
<%-- 	 			
	 			<li class="nav-weixin dropdown">
	 				<a href="javascript:void(0);"> <i class="ico-gear"></i>微信管理<span class="caret"></span> </a>
	 				<ul class="sub-nav">
	 					<li class="nav-weixin-menu">
							<a href="<%=request.getContextPath() %>/ws/backer/system/findMenuList?funid=11"> 自定义菜单 </a>
						</li>
	
						<li class="nav-weixin-reply">
							<a href="<%=request.getContextPath() %>/ws/backer/wxset/findReplyAttention?funid=11"> 自定义回复 </a>
						</li>
	
						<li class="nav-weixin-qunfa">
							<a href="<%=request.getContextPath() %>/ws/backer/wxset/findNoticeSend?funid=13"> 高级群发功能 </a>
						</li>
	
						<li class="nav-weixin-audit">
							<a href="<%=request.getContextPath() %>/ws/backer/wxset/findMessageAudit?funid=36"> 群发审核 </a>
						</li>
	
						<li class="nav-weixin-sucai">
							<a href="<%=request.getContextPath() %>/ws/backer/wxset/findDTImgTextList?funid=18"> 素材库 </a>
						</li>
						
		 			</ul>
	 			</li> --%>
	</ul>
</div>