<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if ("/".equals(path)) path = "";
int serverPort = request.getServerPort();
String sPort = (serverPort == 80 || serverPort == 443)?"":(":" + request.getServerPort());
String serverName = request.getServerName();
String base = request.getScheme() + "://" + serverName+sPort; 
String basePath = base + path + "/";
request.setAttribute("basePath", basePath);
%>
<%@ page isELIgnored="false" %><%-- web-app version="2.5" 时解决低版本不解析el表达式 --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page isELIgnored="false" %><%-- web-app version="2.5" 时解决低版本不解析el表达式 --%>
<base href="<%=basePath%>">
<!--[if lt IE 9]>
	   <script src="<%=basePath%>libs/js/ie8.js" type="text/javascript"></script>
	     <script src="<%=basePath%>libs/js/json2.js" type="text/javascript"></script>
	<![endif]-->
<link rel="stylesheet" href="${basePath }sweetalert/dist/sweetalert.css">
<script src="${basePath }sweetalert/dist/sweetalert-dev.js"></script>
<script src="<%=basePath%>js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
var path = "<%=basePath%>"; 

</script>
