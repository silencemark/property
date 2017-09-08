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

<body class="p-weixin p-weixin-sucai">
	<div class="wrapper">

	<jsp:include page="../../../../black/include/top.jsp"></jsp:include>
	<div class="content">
		<div class="crumb">
            <h5>微信管理 &#187; 素材管理</h5>
        </div>	

	<!--container-->
	<div class="webContainer clearFix containerWcArticleAssetsList" id="webContainer">

		<jsp:include page="../../../../black/include/left.jsp"></jsp:include>

		<!--main-->
		<div class="webMain">
			<div class="mainHeader">
				<h1>素材管理</h1>
				<ul class="menu clearFix">
					<li class="current"><a href="<%=request.getContextPath() %>/ws/backer/wxset/findDTImgTextList">图文消息</a></li>
					<li><a href="<%=request.getContextPath() %>/ws/backer/wxset/findDTImgList">图片</a></li>
					<li><a href="<%=request.getContextPath() %>/ws/backer/wxset/findDTAudioList">语音</a></li>
					 
				</ul>
				<div class="toolbtns">
					<a href="<%=request.getContextPath() %>/ws/backer/wxset/dtImgTextMulti" class="btnA btnSmall w150">添加多图文消息</a>
					<a href="<%=request.getContextPath() %>/ws/backer/wxset/dtImgTextSingle" class="btnA btnSmall w150">添加单图文消息</a>
				</div>
			</div>
			
			<div class="mainBody" id="wechatArticleAssetsList">
				<form id="searchform" action="<%=request.getContextPath() %>/ws/backer/wxsetfindDTImgTextList" method="post">
					<div class="tableTools clearFix mb30">
						<div class="i mr20"><input type="text" id="title" name="title" value="${pvMap.title }"  class="input w180" style="height: 35px;"/></div>
						<div class="i"><input type="submit"  id="submit" class="btnB btnSmall" value="查找"/></div>
						 
						
					</div>
				</form>
				<div class="itemList clearFix" >
					<%--遍历生成图文 --%>
					<c:forEach items="${dataList }" var="item" varStatus="st">
						<c:choose><c:when test="${item.imgtexttype==1 }">
							 <%-- 单图文 --%>
							 <c:forEach items="${item.childList }" var="item2" varStatus="st2">  
								<div class="item itemSingle">
									<h2><a target="_blank" href="<%=request.getContextPath() %>/ws/backer/wxset/showDTImgText?imgtextlistid=${item2.imgtextlistid }">${item2.title }</a></h2>
									<span class="date"><fmt:formatDate value="${item.createtime }" pattern="MM月dd日"/></span>
									<div class="img"><img src="<%=request.getContextPath()%>${ item2.imgurl}" /></div>
									<p>${item2.summary}</p>
									<div class="tools"> 
										<a href="<%=request.getContextPath() %>/ws/backer/wxset/dtImgTextSingle?imgtextid=${item.imgtextid}" class="edit"></a>
										<a href="javascript:void(0);" onclick='delGraphic(${item.imgtextid});'  class="del"></a>
									</div>
								</div>
							</c:forEach>
							<%-- over  --%>
						</c:when>
						<c:when test="${item.imgtexttype==2 }"> 
							<%--多图文 --%>
							<div class="item itemMulit">
								<span class="date" style="height: 35px;"><fmt:formatDate value="${item.createtime }" pattern="MM月dd日"/></span>
								<c:forEach items="${item.childList }" var="item2" varStatus="st2">
									<c:choose><c:when test="${st2.index==0 }">
										<div class="cover" style="margin-top: 5px;"><div class="img"><img src="<%=request.getContextPath()%>${item2.imgurl }"/><p><a target="_blank"  href="<%=request.getContextPath() %>/ws/backer/wxset/showDTImgText?imgtextlistid=${item2.imgtextlistid}">${item2.title}</a></p></div></div>
									</c:when> 
									<c:otherwise>
										<div class="sub clearFix"><p><a target="_blank"  href="<%=request.getContextPath() %>/ws/backer/wxset/showDTImgText?imgtextlistid=${item2.imgtextlistid }">${item2.title }</a></p><div class="img"><img src="<%=request.getContextPath()%>${item2.imgurl}"/></div></div>
									</c:otherwise></c:choose>
								</c:forEach>
								<div class="tools">
									<a href="<%=request.getContextPath() %>/ws/backer/wxset/dtImgTextMulti?imgtextid=${item.imgtextid }" class="edit"></a>
									<a href="javascript:void(0);" onclick='delGraphic(${item.imgtextid });' class="del"></a>
								</div>
							</div>								
						</c:when>
						<c:otherwise></c:otherwise>
						</c:choose>
					</c:forEach>
					
				</div>
				${pager}
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
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/wechat/articleAssetsList.js"></script>
	<script type="text/javascript"><!--
	function delGraphic(id){
		$.alert({
			title: "温馨提示",
			txt: "确定要删除此图文吗？",
			btnY: "删除",
			btnYcss: "btnC",
			btnN: "取消",
			callbackY: function(){
				$.loading();
				location.href = "<%=request.getContextPath() %>/ws/backer/wxset/delDTImgText?imgtextid="+id;
			}
		});	
	}
	$(function() {
		$('#btn_search').click(function(){
			$('#searchform').submit();
		});
		//监听刷新事件
		$(document).keydown(function(event){
			onkeydown(event);
		});
	});
	//当用户在此界面刷新时，强制导航到此界面正确的action。防止在添加完一条数据后刷新页面导致重复提交。
	function onkeydown(){
		if(event.keyCode==116){
			event.keyCode=0;
			event.cancelBubble=true;
			event.preventDefault();
			location.href = "<%=request.getContextPath() %>/ws/backer/wxset/findDTImgTextList";
			return false;
		}
	}
	</script>
</body>
</html>