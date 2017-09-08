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
	<div class="webContainer clearFix containerWcMulitArticleAsset" id="webContainer">

		<jsp:include page="../../../../black/include/left.jsp"></jsp:include>
		
		<form action="<%=request.getContextPath() %>/ws/backer/wxset/saveDTImgTextMultiForm"  
			method="post"	id="jsonform" style="display: block;">
			<input type="hidden" id="multiGraphic" name="multiGraphic"/>
		</form>
		<!--main-->
		<div class="webMain">
			<div class="mainHeader">
				<h1>添加多图文消息</h1>
			</div>
			<div class="mainBody" id="wechatMulitAsset">

				<div class="item itemMulit" id="itemMulit"></div>

				<div class="form">
					<form>
						<input type="hidden" id="imgtextid">
						<input type="hidden" id="imgtextlistid">
						<fieldset>
							<div class="l">
								<h6>标题</h6>
								<div class="i"><input type="text" class="input" id="title" name="title" style="margin-top:5px;height:35px;"/></div>
							</div>
							<div class="l">
								<h6>作者<span>（选填）</span></h6>
								<div class="i"><input type="text" class="input" id="author" name="author" style="margin-top:5px;height:35px;"/></div>
							</div>
							<div class="l cover">
								<h6>封面<span class="up1">（最大64K）</span><span class="up2" style="display:none">（小图片建议尺寸：200像素 * 200像素）</span></h6>
								<div class="i">
									<div class="up" style="margin-top:5px;"><input type="text" class="js_imgupload" name="imgurl" id="imgurl" maxWidth="600" onchange="$.__mulitArticleAssetImgChange__" /></div>
									<div class="cb clearFix"><input type="checkbox" name="ifviewcontent"  data-val="封面图片显示在正文中"  id="ifviewcontent" /></div>
								</div> 
							</div> 
							<%--
							<div class="l">
								<h6>摘要</h6>
								<div class="i"><textarea class="input" id="summary" name="summary" ></textarea></div>
							</div>
							 --%>
							<div class="l">
								<h6>正文</h6>
								<div class="i" style="margin-top:5px;"><script id="content" type="text/plain" name="content"></script></div>
								<span id="mycontent" style="display:none;"></span>
							</div> 
							<div class="l">
								<h6>原文链接<span>（选填）</span></h6>
								<div class="i"><input type="text" class="input" id="linkurl" name="linkurl" style="margin-top:5px;height:35px;"/></div>
							</div>
						</fieldset> 
					</form>
					<div class="point"></div>
				</div>

				<div class="btnCon btnCon2">
					<a href="#" class="btnD" onclick="window.location.href='<%=request.getContextPath()%>/ws/backer/wxset/findDTImgTextList'">取消</a>
					<a href="javascript:void(0)" id="preview" class="btnD">预览</a>
					<input type="button" id="submit" class="btnA" value="提交" />
				</div>
				
			</div>
		</div>
		<!--main end-->

	</div>
	<!--container end-->

	<script>
	var __mulit = {
	<c:choose><c:when test="${dataList == null or fn:length(dataList)<=0}">
		<%-- 新增 --%>
		cover: {
			imgtextid:"",
			imgtextlistid:"",
			title: "",
			author: "",
			imgurl: "",
			ifviewcontent: "1",
			content: "",
			linkurl: ""
		},
		sub: [{
			imgtextid:"",
			imgtextlistid:"",
			title: "",
			author: "",
			imgurl: "",
			ifviewcontent: "1",
			content: "",
			linkurl: ""
		}]
	</c:when>
	<c:otherwise>
		<%-- 修改 --%>
		<c:forEach items="${dataList }" var="item" varStatus="st" >
			<c:choose><c:when test="${st.index==0}">
				<%-- 第一项 --%>
				cover: {
					imgtextid:"${item.imgtextid}",
					imgtextlistid:"${item.imgtextlistid}",
					title: "${item.title}",
					author: "${item.author}",
					imgurl: "${item.imgurl}",
					ifviewcontent: "${item.ifviewcontent}",
					content: '${item.content}',
					linkurl: "${item.linkurl}"
				},
			</c:when>
			<c:otherwise>
				<c:if test="${st.index==1}">
					sub: [
				</c:if>
				 {
					imgtextid:"${item.imgtextid}",
					imgtextlistid:"${item.imgtextlistid}",
					title: "${item.title}",
					author: "${item.author}",
					imgurl: "${item.imgurl}",
					ifviewcontent: "${item.ifviewcontent}",
					content: '${item.content}',
					linkurl: "${item.linkurl}"
				},
				<c:if test="${st.index == fn:length(dataList)-1}">
					]
				</c:if>
				
			</c:otherwise></c:choose>
		</c:forEach>
	</c:otherwise></c:choose>
	}
	//提交多图文
	function saveDTImgTextMulti(){
		console.log(JSON.stringify(__mulit));
		$('#multiGraphic').val(JSON.stringify(__mulit));
		$('#jsonform').submit();
	}
	</script>
	<script type="text/javascript">
	//发送预览
	function sendPreview(openid){
		//多图文信息
		var multiGraphic = JSON.stringify(__mulit);
		$('#multiGraphic2').val(multiGraphic);
		$('#nickname2').val(openid);
		$('#prevform').submit();   
	}
	function prevFlag(flag){
		if(flag=="false"){
			flag=false;
		}
			if(!flag){
				alert("预览失败！");
			}
			//console.log(data);
	}
	</script>
	
	<form action="<%=request.getContextPath() %>/ws/backer/wxset/previewMulti"  
			method="post"	id="prevform" style="display: block;" target="prevTargetIframe">
			<input type="hidden" id="multiGraphic2" name="multiGraphic"/>
			<input type="hidden" id="nickname2" name="nickname"/>
	</form>
	<iframe id="prevTargetIframe" name="prevTargetIframe" style="width:0px;heihgt:0px;display: none;"></iframe>

	<script src="<%=request.getContextPath()%>/theme/black/js/gtyutil.js"></script>
	<script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/swfobject.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/wechat/mulitArticleAsset.js"></script>

	<!--富文本编辑器-->
	<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/ueditor/lang/zh-cn/zh-cn.js"></script>
    <!--富文本编辑器 end-->
    
</body>
</html>