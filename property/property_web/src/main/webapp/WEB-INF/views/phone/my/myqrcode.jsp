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
<title>我的二维码</title>
<link href="${basePath }appcssjs/style/public.css" type="text/css"
	rel="stylesheet">
<link href="${basePath }appcssjs/style/page.css" type="text/css"
	rel="stylesheet">
</head>

<body name="myinfo">
	<!--头部-->
	<c:if test="${isWeiXinFrom==false}">
		<div class="headbox">
			<div class="title f-20">我的二维码</div>
			<a href="javascript:history.go(-1)" class="ico_back f-16">返回</a>
			<!-- <a href="#" class="ico_list">更多</a> -->
		</div>
	</c:if>
	<div class="ewm_page">
	<div class="user_box">
    	<div class="user_img">
    		<c:if test="${empty user.headimage}">
					<img src="${basePath}appcssjs/images/page/pic_bg.png" width="86"
						height="86">
				</c:if>
				<c:if
					test="${!empty user.headimage}">
					<img src="${user.headimage}" width="86" height="86">
				</c:if>
    	</div>
        <div class="xx">
        	<div class="xx_01">
        	<c:choose>
				<c:when test="${user.individualstatus==1||user.enterprisestatus==1}">
					<div class="name_v">${user.nickname}</div>
				</c:when>
				<c:otherwise>
					<div class="name">${user.nickname}</div>
				</c:otherwise>
			</c:choose>
			</div>
            <div class="xx_02">${user.area}</div>
        </div>
    </div>
    <div class="ewm_img">
    	<img src="${basePath }${user.qrcodeurl}">
    </div>
</div>

	<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
	<!--底部导航-->

</body>
</html>
<script type="text/javascript">
	function saveFeedBack() {
		//校验意见反馈内容
		var content = $("#content").val();
		if(content==null||content.length<10||content.length>200){
			swal("","请输入长度10-200意见反馈内容");
			return;
		}
		//校验手机号码
		if(!valiPhone()){
			return;
		}
		$("#save").attr("disabled", "disabled");
		$.ajax({
			type : "post",
			dataType : "json",
			url : "${basePath }/complaint/savecomplaint",
			data : $('#feedback').serialize(),
			success : function(data) {
				if (data.success == true) {
					swal({
						title : "提交反馈",
						text : "你已成功提交反馈，点击确认返回个人中心!",
						type : "success",
						showCancelButton : false,
						confirmButtonColor : "#ff7922",
						confirmButtonText : "确认!",
						closeOnConfirm : false
					}, function() {
						window.location.href = "${basePath }my/mycenter";
					});
				}
			}
		})
	}
	function valiPhone() {
		var phone = $("#contact").val();
		var ismobile = mobileVali(phone);
		var istel = telVali(phone);
		if(!ismobile&&!istel){
			swal("","联系方式格式不正确");
			$("#contact").focus();
			return false;
		}
		return true;
	}
	function mobileVali(str) {
		return (/^(?:13\d|15[09]|17[09]|18[09])-?\d{5}(\d{3}|\*{3})$/.test(str));
	}
	function telVali(str) {
		//"兼容格式: 国家代码(2到3位)-区号(2到3位)-电话号码(7到8位)-分机号(3位)"  
		return (/^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/.test(str));
	}
</script>

