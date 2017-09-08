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
		<style type="text/css">
			.pop-alert-appendWechatKeyword .pop .bd {
				padding-bottom: 0px !important
			}
		</style>
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
	</head>

	<body class="p-weixin p-weixin-reply">
		<div class="wrapper">	
		<jsp:include page="../../../../../black/include/top.jsp"></jsp:include>
	<div class="content">
		<div class="crumb">
            <h5>微信管理 &#187; 关键字回复</h5>
        </div>
		<!--container-->
		<!--container-->
		<div class="webContainer clearFix containerWcMenu" id="webContainer">

			<jsp:include page="../../../../../black/include/left.jsp"></jsp:include>


			<div class="webMain">
			<div class="mainHeader">
				<h1>${pvMap.type}关键字回复</h1>
			</div>
			<div class="mainBody">

				<!--表单-->
				<div class="form" >
					<div class="formHeader">
						<h1>${pvMap.type}关键字回复</h1>
					</div>
					<form id="myform" action="<%=request.getContextPath() %>/ws/backer/wxset/saveReplyKeyword" method="post">
						<input type="hidden" id="ruleid" name="ruleid" value="${pvMap.ruleid}">
						<input type="hidden" id="replytype" name="replytype">
						<input type="hidden" id="replycontent" name="replycontent">
						<div id="mykeydiv" style="display:none;"></div>
						
						<fieldset>
							<div class="l">
								<h6>规则名</h6>
								<div class="i"><input type="text" class="input w300"  id="rulename" name="rulename" value="${pvMap.rulename}"/></div>
							</div>
							<div class="l">
								<h6>关键字</h6>
								<div class="i">
									<table class="table" id="keywordTable">
										<thead>
											<tr>
												<td class="t1">关键字</td>
												<td class="t2">完全匹配</td>
												<td class="t3"><a href="javascript:void(0)" class="btnB" id="appendKeyword">添加</a></td>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${pvMap.textKeyList }" var="item" varStatus="st" >
												<tr>
													<td class="t1">${item.name}</td>
													<td class="t2" data-b="${item.matetype}"><c:choose><c:when test="${item.matetype==0}">完全匹配</c:when>
														<c:when test="${item.matetype==1}">模糊匹配</c:when></c:choose></td>
													<td class="t3">
														<p class="console">
															<a href="javascript:void(0)" class="revise">修改</a><a href="javascript:void(0)" class="del">删除</a>
														</p>
													</td>
												</tr>	
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
							<div class="l">
								<h6>回复内容</h6>
								<div class="i">
									<div class="wcInputs" id="wcInputs" data-msgtype="${pvMap.msgtypekey}"
											 data-content="${pvMap.replycontent}">
										<div class="hd clearFix">
											<c:choose><c:when test="${null == pvMap.msgtypekey or '' == pvMap.msgtypekey}" >
												<a href="javascript:void(0)" data-type="bdtxt" class="txt c"><span></span>文本回复</a>
												<a href="javascript:void(0)" data-type="bdarticle"
													class="article"><span></span>图文消息</a>
												<a href="javascript:void(0)" data-type="bdimg" class="img"><span></span>图片</a>
												<a href="javascript:void(0)" data-type="bdaudio"
													class="audio"><span></span>语音</a>
											</c:when>
											<c:otherwise>
												<a href="javascript:void(0)" data-type="bdtxt" class="txt<c:if test="${'txt'==pvMap.msgtypekey}"> c</c:if>"><span></span>文本回复</a>
												<a href="javascript:void(0)" data-type="bdarticle"
													class="article<c:if test="${'article'==pvMap.msgtypekey}"> c</c:if>"><span></span>图文消息</a>
												<a href="javascript:void(0)" data-type="bdimg" class="img<c:if test="${'img'==pvMap.msgtypekey}"> c</c:if>"><span></span>图片</a>
												<a href="javascript:void(0)" data-type="bdaudio"
								 					class="audio <c:if test="${'audio'==pvMap.msgtypekey}"> c</c:if>"><span></span>语音</a>
											</c:otherwise></c:choose>
										</div>
										<div class="bd">
											<div class="bditem bdtxt">
												<textarea></textarea>
												<div class="tools"><p>还可以输入600字</p></div>
											</div>
											<div class="bditem bdarticle">
												<a href="javascript:void(0)" class="appendAsset"
												data-roletype="${pvMap.roletype}">添加素材</a>
												<div class="asset"></div>
											</div>
											<div class="bditem bdimg">
												<a href="javascript:void(0)" class="appendPhoto"
												data-roletype="${pvMap.roletype}">添加图片</a>
												<div class="photo"></div>
											</div>
											<div class="bditem bdaudio">
												<a href="javascript:void(0)" class="appendAudio"
												data-roletype="${pvMap.roletype}">添加语音</a>
												<div class="audio"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
				
				<!--主提交按钮-->
				<div class="btnCon btnCon2">
					<a href="javascript:void(0);" onclick="window.history.go(-1);" class="btnD">取消</a>
					<a href="javascript:void(0);" id="submit" class="btnA">提交</a>
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/theme/grey/js/wechat/keyword.js"></script>
	
	<script type="text/javascript">
	function saveContent(msgtype,content){
		$.alert({
			title: "温馨提示",
			txt: "确定要保存？",
			btnY: "确定",
			btnN: "取消",
			callbackY: function(){
				// 1. 将消息放置到隐藏域中
				$('#replytype').val(msgtype);
				$('#replycontent').val(content);
				// 2. 封装关键字
				createKeyForm();
				$('#myform').submit();
				//=======
			}
		});
		
	}
	//生成关键字form
	function createKeyForm(){
		$('#mykeydiv').empty();
		$('#keywordTable tbody tr').each(function(index){
			console.log("index="+index);
			//mykeydiv : name | matetype
			var name = $(this).find(".t1").text();
			var matetype = $(this).find(".t2").data("b");
			var html = "<input type='hidden' name='keyWordList["+index+"].name' value="+name+" />";
			html+= ("<input type='hidden' name='keyWordList["+index+"].matetype' value="+matetype+" />");
			$('#mykeydiv').append(html);
		});
	}
	$(function(){
		$('#submit').click(function(){
			$('.bd .bditem').each(function(){
				var display = $(this).css("display");
				if(!hhutil.isEmpty(display) && display=='block'){
					var classid = $(this).attr("class");
					var msgtype = "";
					var content = "";
					if(!hhutil.isEmpty(classid) && classid.indexOf("bdtxt")>=0){
						msgtype = "txt";
						content = $(this).find("textarea").val();
					}else if(classid.indexOf("bdarticle")>=0){
						msgtype = "article";
						content = $(this).find(".asset .item").data("id");
					}else if(classid.indexOf("bdimg")>=0){
						msgtype = "img";
						content = $(this).find(".photo .item").data("id");
					}else if(classid.indexOf("bdaudio")>=0){
						msgtype = "audio";
						content = $(this).find(".audio .item").data("id");
					}
					saveContent(msgtype,content);
				}
			});
		});
	});
	</script>
</body>
</html>