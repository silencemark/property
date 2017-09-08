<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.lr.backer.util.UserUtil"%>
<div class="header">
	<h2 class="site-logo_labor">
		<span><font color="white">嘀嗒叫人后台</font></span>
	</h2>
	<ul class="nav navbar-nav navbar-right">
		<li>
			<a href="javascript:void(0);"><i class="ico-user"></i><% Map<String,Object> userMap = UserUtil.getUser(request); %><%=userMap.get("realname") %></a>
		</li>
		<li>
			<a href="<%=request.getContextPath() %>/system/goUpdatePwd?userid=<%=userMap.get("userid") %>"><i
				class="ico-psd"></i>修改密码</a>
		</li>
		<!-- <li>
			<a href="#about" data-toggle="modal" data-backdrop="static"><i
				class="ico-about"></i>关于</a>
		</li> -->
		<li>
			<a href='<%=request.getContextPath()%>/system/login'
				target=_top><i class="ico-exit"></i>退出</a>
		</li>
	</ul>
</div>
