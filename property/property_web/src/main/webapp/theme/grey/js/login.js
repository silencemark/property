$(function(){

	var tips = {
		error: function(t){
			clearTimeout(tips.timeout);
			$("#_tips_").remove();
			$("#loginBox").append("<div id='_tips_' class='_tips_error'>" + t + "</div>");
			tips.timeout = setTimeout(tips.remove, 2000);
		},
		remove: function(){
			$("#_tips_").remove();
		}
	}

	var page = {
		init: function(){
			$("#submit").on("click", page.submitClick);
		},
		submitClick: function(){
			var name = $("#username");
			var pw = $("#password");
			var cd = $("#code");
			if (name.inputEmpty()){
				$.tips.error("请输入用户名");
				return false;
			} else if (pw.inputEmpty()){
				$.tips.error("请输入密码");
				return false;
			} else if (cd.inputEmpty()){
				$.tips.error("请输验证码");
				return false;
			} else {
				$.loading();
				$('#loginform').submit();
				/**setTimeout(function(){
					$.loading.remove();
					$.alert("静态展示");
				},2000);**/
			}
		}
	};
	page.init();


});