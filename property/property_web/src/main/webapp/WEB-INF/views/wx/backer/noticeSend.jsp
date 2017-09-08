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
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>高级群发</title>
		<meta name="Keywords" content="">
		<meta name="Description" content="">
		<jsp:include page="../../../../black/include/header.jsp" flush="true"></jsp:include>
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

	<body class="p-weixin p-weixin-qunfa">
	<div class="wrapper">

		<jsp:include page="../../../../black/include/top.jsp"></jsp:include>
	<div class="content">
		<div class="crumb">
            <h5>微信管理 &#187; 高级群发</h5>
        </div>
		<!--container-->
		<div class="webContainer clearFix containerMassSend" id="webContainer">

			<jsp:include page="../../../../black/include/left.jsp"></jsp:include>

			<!--main-->
			<div class="webMain">
				<div class="mainHeader">
					<h1>
						高级群发功能
					</h1>
					<div class="toolbtns">
						<c:choose><c:when test="${sessionScope.roleid==3 }">
						</c:when>
						<c:otherwise>
							<a id="setCanSendNum" href="javascript:void(0)"
								class="btnA btnSmall w150">设置群发数量</a>
						</c:otherwise></c:choose>

					</div>
					<ul class="menu clearFix">
						<li class="current">
							<a
								href="<%=request.getContextPath()%>/ws/backer/wxset/findNoticeSend">新建群发消息</a>
						</li>
						<li>
							<a
								href="<%=request.getContextPath()%>/ws/backer/wxset/findNoticeSendHistory">已发送</a>
						</li>
					</ul>
				</div>
				<div class="mainBody">

					<!--提醒-->
					<div class="summary mb20">
						为保障用户体验，微信公众平台严禁恶意营销以及诱导分享朋友圈，严禁发布色情低俗、暴力血腥、政治谣言等各类违反法律法规及相关政策规定的信息。一旦发现，我们将严厉打击和处理。
					</div>

					<!--筛选-->
					<div class="tableTools clearFix mb30">
						<form id="myform"
							action="<%=request.getContextPath()%>/ws/backer/wxset/noticeSend"
							method="post">
							<%--<%
								if (Constants.ROLE_TYPE_ADMIN.equals(UserSession.getInstance()
										.getCurrentUser().getRoletype())) {
									//官方进行群发可选择分组，经销商不需要分组
							%>
							<div class="w150 i mr10">
								<h5>
									群发对象
								</h5>
								<select name="groupid">
									<option value="">
										全部用户
									</option>
									<c:forEach items="${groupList }" var="item" varStatus="st" >
										<option value="${item.groupid" />">
											${item.groupname" />
										</option>
									</c:forEach>
								</select>
							</div>
							<%
								}
							%>
						
					--%></div>


					<!--表单-->
					<div class="form">
						<div class="formHeader">
							<h1>
								您本月还能群发 ${canSendNum } 条消息
							</h1>
						</div>

						<input id="canSendNum" type="hidden" value="${canSendNum }">
						<input type="hidden" id="type" name="msgtype">
						<input type="hidden" id="content" name="content">

						<fieldset>
							<div class="l">
								<h6>
									群发内容
								</h6>
								<div class="i">
									<div class="wcInputs" id="wcInputs">
										<div class="hd clearFix">
											<a href="javascript:void(0)" data-type="bdtxt" class="txt c"><span></span>文本回复</a>
											<a href="javascript:void(0)" data-type="bdarticle"
												class="article"><span></span>图文消息</a>
											<a href="javascript:void(0)" data-type="bdimg" class="img"><span></span>图片</a>
											<a href="javascript:void(0)" data-type="bdaudio"
												class="audio"><span></span>语音</a>
										</div>
										<div class="bd">
											<div class="bditem bdtxt">
												<textarea></textarea>
												<div class="tools">
													<p>
														还可以输入1000字
													</p>
												</div>
											</div>
											<div class="bditem bdarticle">
												<a href="javascript:void(0)" class="appendAsset"
													data-roletype="${paramCondition.roletype}">添加素材</a>
												<div class="asset"></div>
											</div>
											<div class="bditem bdimg">
												<a href="javascript:void(0)" class="appendPhoto"
													data-roletype="${ paramCondition.roletype}">添加图片</a>
												<div class="photo"></div>
											</div>
											<div class="bditem bdaudio" >
												<a href="javascript:void(0)" class="appendAudio"
													data-roletype="${paramCondition.roletype }">添加语音</a>
												<div class="audio"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</fieldset>

					</div>
					</form>

					<!--主提交按钮-->
					<div class="btnCon">
						<input type="submit" id="submit" class="btnA" value="群发" />
					</div>

				</div>
			</div>
			<!--main end-->

		</div>
		<!--container end-->
		</div></div>

		<script src="<%=request.getContextPath()%>/theme/black/js/gtyutil.js"></script>
		<script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/theme/grey/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/theme/grey/js/common.js"></script>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/theme/grey/js/wechat/massSend.js"></script>

		<script type="text/javascript">
		var agencyCanSendNum = '${paramCondition.agencyCanSendNum}';
	$(function() {

		$('#submit').click(function() {
			var val = $('#canSendNum').val();
			if (hhutil.isEmpty(val) || val <= 0) {
				//没有可发送的条数
				$.alert( {
					title : "温馨提示",
					txt : "您本月可群发次数已用完！",
					btnN : "取消"
				});
			} else {
				//获取选中的数据
				$('.bd').find("[class='bditem' style='display:block']").each(
						function() {
							console.log($(this));
						});
				$('.bd .bditem').each(function() {
					var display = $(this).css("display");
					if (!hhutil.isEmpty(display) && display == 'block') {
						//这里显示选中的tab 
						var tclass = $(this).attr("class");
						var content = "";
						var type = "";
						if (tclass.indexOf("bdtxt") >= 0) {//文本
							type = "txt";
							content = $(this).find("textarea").val();
						} else if (tclass.indexOf("bdarticle") >= 0) {//图文
							type = "article";
							content = $(this).find(".asset .item").data("id");
						} else if (tclass.indexOf("bdimg") >= 0) {//图片
							type = "img";
							content = $(this).find(".photo .item").data("id");
						} else if (tclass.indexOf("bdaudio") >= 0) {//语音
							type = "audio";
							content = $(this).find(".audio .item").data("id");
						}
						if (content) {
							//alert(type+"\n"+content);
							$('#type').val(type);
							$('#content').val(content);
							$('#myform').submit();
						} else {
							$.tips.error("请输入群发内容");
						}
					}
				});
			}
		});
		var page = {
				init: function(){
					$("#setCanSendNum").on("click", page.showPop);
				},
				showPop: function(o){
					var canSendNum = agencyCanSendNum;
					var o = {"setCanSendNum" : setCanSendNum};
					var html = "<p>经销商本月能发送的消息数量</p>";
					html += "<div class='i num'><input type='text' class='input' value='" + canSendNum + "' /></div>";
					$.alert({
						title: "设置群发消息数量",
						txt: html,
						btnY: "设置",
						btnN: "取消",
						css: "pop-alert-appendQuestion",
						init: function(){
							//$("#pop-alert .rad .r").inputInit();
						},
						callbackY: function(){
							var num = $("#pop-alert .pop .bd .num .input");
							if (num.inputNotNum()){
								num.inputError("请输入数字");
								return false;
							} else {
								window.location.href="<%=request.getContextPath()%>/ws/backer/wxset/setCanSendNum?messagecount="+num.val();
							}
						}
					});
				},
			};
			page.init();
	});
</script>
		<style type="text/css">
#pop-alert .pop {
	top: 50%;
}

.pop-alert-appendQuestion .pop {
	width: 600px !important;
	margin: -300px 0 0 -300px !important;
}

.pop-alert-appendQuestion .pop .btn {
	padding-left: 148px !important;
}

.pop-alert-appendQuestion .pop .bd p {
	padding: 10px;
	text-align: center;
}

.pop-alert-appendQuestion .pop .bd .p {
	padding: 10px;
	text-align: center;
}

.pop-alert-appendQuestion .pop .bd .i {
	margin-bottom: 10px;
	height: 36px;
}

.pop-alert-appendQuestion .pop .bd .i h6 {
	float: left;
	width: 120px;
	text-align: right;
	margin-right: 10px;
	line-height: 36px;
}

.pop-alert-appendQuestion .pop .bd .i .input {
	width: 340px;
	margin: 0 auto;
}
</style>
	</body>
</html>