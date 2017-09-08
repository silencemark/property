<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/head/base.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<title>意见反馈</title>
<link href="${basePath }appcssjs/style/public.css" type="text/css"
	rel="stylesheet">
<link href="${basePath }appcssjs/style/page.css" type="text/css"
	rel="stylesheet">
</head>

<body name="myinfo">
	<!--头部-->
	<c:if test="${isWeiXinFrom==false}">
		<div class="headbox">
			<div class="title f-20">意见反馈</div>
			<a href="javascript:history.go(-1)" class="ico_back f-16">返回</a>
			<!-- <a href="#" class="ico_list">更多</a> -->
		</div>
	</c:if>
	<form action="#" id="feedback">
		<div class="control">
			<div class="text_area m_12">
				<textarea placeholder="请输入您宝贵的意见，10-200字" class="text_02"
					name="content" id="content" maxlength="200"></textarea>
			</div>
			<input type="text" class="text" placeholder="请输入您的手机号码"
				name="contact" id="contact"  >
		</div>
		<div class="main_bigbtn">
			<input type="button" id="save" value="提交" onclick="saveFeedBack()">
		</div>
	 
	</form>
<script type="text/javascript">
	function saveFeedBack() {
		//校验意见反馈内容
		var content = $("#content").val();
		if(content==null||content.length<10||content.length>200){
			swal({
				title : "",
				text : "请输入长度在10-200之间意见反馈内容!",
				type : "",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function() {
				$("#content").focus();
			});
			return;
		}
		
		var phone = $("#contact").val();
		//校验手机号码
		if(!/^1[3|4|5|7|8]\d{9}$/.test(phone)){
			swal({
				title : "",
				text : "您输入的手机号码格式不正确，请重新输入！",
				type : "",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function() {
				$("#contact").focus();
			});
            return;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url : "${basePath }/complaint/savecomplaint",
			data : $('#feedback').serialize(),
			success : function(data) {
				if (data.success == true) {
					swal({
						title : "",
						text : "你已成功提交反馈，点击确认返回个人中心!",
						type : "success",
						showCancelButton : false,
						confirmButtonColor : "#ff7922",
						confirmButtonText : "确认",
						closeOnConfirm : true
					}, function() {
						window.location.href = "${basePath }my/mycenter";
					});
				}
			}
		});
	}
	 
</script>
	<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
	<!--底部导航-->

</body>
</html>


