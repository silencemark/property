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
<title>我的账户交易记录</title>
<link href="${basePath }appcssjs/style/public.css" type="text/css"
	rel="stylesheet">
<link href="${basePath }appcssjs/style/page.css" type="text/css"
	rel="stylesheet">
</head>

<body name="myinfo">
	<!--头部-->
	<c:if test="${isWeiXinFrom==false}">
		<div class="headbox">
			<div class="title f-20">修改密码</div>
			<a href="javascript:history.go(-1)" class="ico_back f-16">返回</a>
			<!-- <a href="#" class="ico_list">更多</a> -->
		</div>
	</c:if>
	<div class="password">
		<ul>
			<li><span>手 机 号</span><input type="text" class="text"
				placeholder="请输入手机号" value="${user.phone}">
			<%-- <i>&nbsp;${user.phone}</i> --%>
			</li>
			<li><span>验 证 码</span><input type="text" class="text"
				placeholder="请输入" name="code" id="code"><input type="button"
				onclick="getvalidcode(this)" value="获取验证码" class="yzm_btn"></li>
			<li><span>新 密 码</span><input type="password" class="text"
				placeholder="6-16个字母、数字、下划线 " id="pwd" name="password"><a
				 class="ico_eye" id="eye" onclick="changeeye()">可见</a></li>
			<li class="last"><span>确认密码</span><input type="password"
				class="text" placeholder="请输入" id="confirmpwd"
				name="confirmpassword"></li>
		</ul>
	</div>
	<input type="hidden" id="nowcode" name="nowcode" />
	<div class="main_bigbtn">
		<input type="button" value="保存" onclick="updatepwd()" id="updatepwd">
	</div>
	<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
	<!--底部导航-->

</body>
<script type="text/javascript">
	var countdown = 60;
	function getvalidcode(val) {
		var t = setTimeout(function() {
			getvalidcode(val);
		}, 1000);
		if (countdown == 60) {
			var phone = ${user.phone};
			var code = parseInt(10 * Math.random()) + ""
					+ parseInt(10 * Math.random()) + ""
					+ parseInt(10 * Math.random()) + ""
					+ parseInt(10 * Math.random());
			$('#nowcode').val(code);
			var url = "${basePath }/my/getvalidcode?phone="+phone+"&code="+code;
			$.ajax({
				type : "POST",
				url : url,
				dataType : "json",
				success : function(data) {
				}
			})
		}
		
		if (countdown == 0) {
			val.removeAttribute("disabled");
			val.value = "获取验证码";
			countdown = 60;
			clearTimeout(t);
		} else {
			val.setAttribute("disabled", true);
			val.value = "获取(" + countdown + ")";
			countdown--;
		}
	}
	function updatepwd() {
		$("#updatepwd").attr("disabled", "disabled");
		var code = $("#code").val();
		var nowcode =$('#nowcode').val();
		if (code != nowcode) {
			swal("","短信验证码不正确,请重新输入");
			$("#updatepwd").removeAttr('disabled');
			return;
		}
		var pwd = $("#pwd").val();
		var confirmpwd = $("#confirmpwd").val();
		if (!isPasswd(pwd)) {
			swal("","请输入正确的密码格式");
			$("#pwd").focus();
			$("#updatepwd").removeAttr('disabled');
			return;
		}
		if (pwd != confirmpwd) {
			swal("","二次密码输入不一至");
			$("#confirmpwd").focus();
			$("#updatepwd").removeAttr('disabled');
			return;
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url : "${basePath }/my/updatepwd",
			data : {
				"password" : pwd,
				"code" : code
			},
			success : function(data) {
				if (data.success == true) {
					swal({
						title : "",
						text : "你已成功修改密码，点击确认返回个人中心!",
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
	var iseye = false;//设置是否可见
    
	function changeeye(){
		if(iseye){
			iseye = false;
			$("#pwd").attr("type","password");
			$("#confirmpwd").attr("type","password");
		}else{
			iseye = true;
			$("#pwd").attr("type","input");
			$("#confirmpwd").attr("type","input");
		}
	}
	
	function isPasswd(s) {
		var patrn = /^(\w){6,16}$/;
		if (!patrn.exec(s))
			return false;
		return true;
	}
</script>
</html>
