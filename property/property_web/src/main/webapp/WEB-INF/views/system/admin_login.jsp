<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html class="ie ie6"> <![endif]-->
<!--[if IE 7 ]> <html class="ie ie7"> <![endif]-->
<!--[if IE 8 ]> <html class="ie ie8"> <![endif]-->
<!--[if IE 9 ]> <html class="ie ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="zh-cn">
	<!--<![endif]-->
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<META   HTTP-EQUIV="pragma"   CONTENT="no-cache">   
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>用户登录 - 嘀嗒叫人后台</title>
		<%-- <link href="<%=Constants.PROJECT_PATH%>/black/images/favicon.ico" rel="shortcut icon" type="image/x-icon" /> --%>
		<link href="<%=request.getContextPath() %>/black/css/login.css" rel="stylesheet" />
		<script type="text/javascript" src="<%=request.getContextPath() %>/wxcssjs/scripts/jquery-1.10.2.min.js"></script> 
	</head>
	<body class="p_login">

		<div class="login_layout">
			<div class="login_decbg"></div>
			<div class="login_content">
				<div class="login_cont" style="height: 350px;">
					<form action="<%=request.getContextPath() %>/system/login" method="post" id="form"> 
						<h3>
							<span>嘀嗒叫人后台</span>
						</h3>
						<p>  
							<label for="userName">
								用户名
							</label>  
							<input type="text" name="username" onfocus="a()" />   
							  
						</p> 
						<input type="text" style="display: none" />       
						<input type="text" style="display: none" />    
						<p>   
							<label for="userPasswd">  
								密&nbsp;&nbsp;&nbsp;码  
							</label>
							<input type="password" name="password" value=""/>   
							
						</p>
						<p>
							<label for="userCode">
								验证码 
							</label> 
							<input type="text" name="userCode" style="width: 150px;" value=""/>  
							<img id="vcode2" style="margin-left: -50px;" src="<%=request.getContextPath() %>/vcode/getAdminVcode?"+new Date().getTime() onclick='this.src="<%=request.getContextPath() %>/vcode/getAdminVcode?"+new Date().getTime()' width="76" height="32" />
						</p>
						<div class="login_tips login_alert">    
							<p id="shuru">   
									<i class="ico-warning"></i>${errors}	  
							</p>
						</div>
						<p class="login_sub">
							<input name="Submit" type="button" onclick="op()" class="login_ipt_sub"
								id="Submit" value="登 陆">
							<input name="cs" type="button" class="login_ipt_sub" id="cs"
								value="取 消" onClick=showConfirmMsg1();/> 
						</p>
					</form>
				</div>
				<script type="text/javascript">    
					
				/** cookie操作开始 */
				var SetCookie = function(name, value, expire) {
					var exp = new Date();
					exp.setTime(exp.getTime() + expire);
					document.cookie = name + "=" + escape(value) + ";expires="
							+ exp.toGMTString() + ";path=/";
				};

				var getCookie = function(name) {
					var arr = document.cookie.match(new RegExp("(^| )" + name
							+ "=([^;]*)(;|$)"));
					return arr ? unescape(arr[2]) : null;
				};
				
				$(function(){ 
					$('input[name="username"]').val('');        
					$('input[name="password"]').val('');      
				});
				function a(){
					$('input[name="username"]').val($.trim($('input[name="username"]').val()));    
				}
					function op(){    
						
						if(document.getElementsByName('username')[0].value == ''){
							document.getElementById('shuru').innerHTML = '<i class="ico-warning"></i>用户名不能为空';
							return false;
						}else if(document.getElementsByName('password')[0].value == ''){
							document.getElementById('shuru').innerHTML = '<i class="ico-warning"></i>密码不能为空';
							return false;
						}else if(document.getElementsByName('userCode')[0].value == ''){
							document.getElementById('shuru').innerHTML = '<i class="ico-warning"></i>验证码不能为空';
							return false;
						}  
						//SetCookie('admin_username',1,365*24*3600*1000);  
					document.getElementById('form').submit();
					}
				</script>
			</div>
			<div class="login_foot">
				<div>
					<p class="floatl">
						Copyright   2015 xxxxx. All Rights Reserved.   沪ICP备15027227号-1
					</p>
					<p class="floatr">
						
		xxxxx：沪卫（中医）网审[2015]第10186号    xxxx：（沪）-非经营性-2015-0097号
					</p>
				</div>
			</div>
		</div>
	</body>
</html>


