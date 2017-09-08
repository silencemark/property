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
	<div class="webContainer clearFix containerWcAudioAssetsList" id="webContainer">

		<jsp:include page="../../../../black/include/left.jsp"></jsp:include>

		<!--main-->
		<div class="webMain" style = "width: 100%">
			<div class="mainHeader">
				<h1>素材管理</h1>
				<ul class="menu clearFix">
					<li><a href="<%=request.getContextPath() %>/ws/backer/wxset/findDTImgTextList">图文消息</a></li>
					<li><a href="<%=request.getContextPath() %>/ws/backer/wxset/findDTImgList">图片</a></li>
					<li class="current"><a href="<%=request.getContextPath() %>/ws/backer/wxset/findDTAudioList">语音</a></li>
					 
				</ul>
				<div class="toolbtns">
					<div class="flashupload"><div id="flashupload"></div></div>
					<p>大小: 不超过2M，格式: mp3、wma、wav、amr</p>
				</div>
			</div>
			<div class="mainBody" id="wechatAudioAssetsList">

				<div class="itemList clearFix" style="width: 100%">
					
					<%--遍历生成语音 --%>
					<c:forEach items="${dataList }" var="item" varStatus="st" >
						<div style="width: 211px" class="item" data-audio="<%=request.getContextPath() %>${item.path}" data-id="${item.id}">
							<a href="javascript:void(0)" class="play"><sub></sub><span>${item.time}s</span></a>
							<div class="info">
								<h1>${item.name}</h1>
								<span>${item.size}K</span>
							</div>
							<div class="tools">
								<a href="<%=request.getContextPath() %>/ws/backer/wxset/downloadAudio?id=${item.id}" class="download"></a>
								<a href="javascript:void(0)" class="rename"></a>
								<a href="javascript:void(0)" class="del"></a>  
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
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/swfobject.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/wechat/audioAssetsList.js"></script>
	
	<form id="updateDTAudioForm" action="<%=request.getContextPath() %>/ws/backer/wxset/saveDTAudio" method="post">
		<input type="hidden" id="update_name" name="name">
		<input type="hidden" id="update_id" name="id">
	</form>
	
	<script type="text/javascript">
	function deleteAudio(id){
		//alert(data);
		var url = "<%=request.getContextPath() %>/ws/backer/wxset/deleteDTAudio?id="+id;
		hhutil.ajax(url,null,function(data){
			loadAudioList();
		});
	}
	function saveAudio(data){
		$('#update_name',$('#updateDTAudioForm')).val(data.name);
		$('#update_id',$('#updateDTAudioForm')).val(data.id);
		$('#updateDTAudioForm').submit();
		
		//alert(data);
		var url = "<%=request.getContextPath() %>/ws/backer/wxset/saveDTAudio?a=1";
		if(!hhutil.isEmpty(data)){
			url += "&id="+data.id;
			url += "&name="+data.name;
		}
		hhutil.ajax(url,null,function(data){
			loadAudioList();
		});
	}
	function loadAudioList(){
		location.href = '<%=request.getContextPath()%>/ws/backer/wxset/findDTAudioList' ;
	}
	</script>

</body>
</html>