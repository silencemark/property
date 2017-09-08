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
	<div class="webContainer clearFix containerWcPhotoAssetsList" id="webContainer">

		<jsp:include page="../../../../black/include/left.jsp"></jsp:include>

		<!--main-->
		<div class="webMain" style="width: 100%">
			<div class="mainHeader">
				<h1>素材管理</h1>
				<ul class="menu clearFix">
					<li><a href="<%=request.getContextPath() %>/ws/backer/wxset/findDTImgTextList">图文消息</a></li>
					<li class="current"><a href="<%=request.getContextPath() %>/ws/backer/wxset/findDTImgList">图片</a></li>
					<li><a href="<%=request.getContextPath() %>/ws/backer/wxset/findDTAudioList">语音</a></li>
					 
				</ul>
				<div class="toolbtns">
					<a href="javascript:void(0)" id="flashupload" class="btnA btnSmall w150">添加图片素材</a>
					<p>大小: 不超过2M，格式: bmp、png、jpeg、jpg、gif</p>
				</div>
			</div>
			<div class="mainBody" id="wechatPhotoAssetsList">

				<div class="itemList clearFix" style="width: 100%">
					<c:forEach items="${dataList }" var="item" varStatus="st" >
						<div class="item">
							<div class="img">
								 
									<img onclick="imgutil.FDIMG(this);" src="<%=request.getContextPath()%>${item.img}"/>
							 
							</div>
							<div class="imgname">
							<c:choose>
								<c:when test="${null!=item.name and ''!=item.name}">${item.name}</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
							</c:choose>
							</div>
							<div class="tools">
								<a href="javascript:void(0)" data-id="${item.imgid}" class="rename"></a>
								<a href="javascript:void(0)" data-id="${item.imgid}" class="del"></a>
							</div>
						</div>
				    </c:forEach>
				</div>

				${pager}

			</div>
		</div>
		<!--main end-->

	</div>
	<!--container end-->
	
	<script src="<%=request.getContextPath()%>/theme/black/js/gtyutil.js"></script>
	<script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/swfobject.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/wechat/photoAssetsList.js"></script>
	<%-- <script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/imageZoom/FancyZoom.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/imageZoom/FancyZoomHTML.js"></script> --%>
	
	<form id="updateDTImgForm" action="<%=request.getContextPath() %>/ws/backer/wxset/updateDTImgName" method="post">
		<input type="hidden" id="update_name" name="name">
		<input type="hidden" id="update_imgid" name="imgid">
	</form>
	<script type="text/javascript">
	function loadImgList(){
		location.href = "<%=request.getContextPath() %>/ws/backer/wxset/findDTImgList";
	}
	//保存图片
	function saveImg(img){
		var url = "<%=request.getContextPath() %>/ws/backer/wxset/saveDTImg?img="+img;
		location.href = url ;
	}
	function updateDTImgName(data){
		$('#update_name',$('#updateDTImgForm')).val(data.name);
		$('#update_imgid',$('#updateDTImgForm')).val(data.id);
		$('#updateDTImgForm').submit();
	}
	
	//删除图片
	function delImg(imgid,callback){
		var url = "<%=request.getContextPath() %>/ws/backer/wxset/delDTImg?a=1";
		if(!hhutil.isEmpty(imgid)){
			url += "&imgid="+imgid;
		}
		hhutil.ajax(url,null,function(data){
			callback();
			loadImgList();
		});
	}
	//监听刷新事件
	$(document).keydown(function(event){
		onkeydown(event);
	});
	//当用户在此界面刷新时，强制导航到此界面正确的action。防止在添加完一条数据后刷新页面导致重复提交。
	function onkeydown(){
		if(event.keyCode==116){
			event.keyCode=0;
			event.cancelBubble=true;
			event.preventDefault();
			location.href = "<%=request.getContextPath() %>/ws/backer/wxset/findDTImgList";
			return false;
		}
	}
	</script>

</body>
</html>