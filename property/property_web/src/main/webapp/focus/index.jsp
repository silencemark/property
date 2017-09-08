<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=uf-8" />
<meta name="keywords" content="JS浠ｇ爜,5绉峧Query寮瑰嚭澶у浘鏁堟灉 ab钃濆缃�http://www.ablanxue.com,JS骞垮憡浠ｇ爜,JS鐗规晥浠ｇ爜" />
<meta name="description" content="姝や唬鐮佸唴瀹逛负5绉峧Query寮瑰嚭澶у浘鏁堟灉 ab钃濆缃�http://www.ablanxue.com锛屽睘浜庣珯闀垮父鐢ㄤ唬鐮侊紝" />
<title>5绉峧Query寮瑰嚭澶у浘鏁堟灉 ab钃濆缃�http://www.ablanxue.com</title>
<link rel="stylesheet" href="css/ablanxue.css" />
<script src="<%=request.getContextPath()%>/black/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/focus/js/jquery2.min.js"></script>       
<script type="text/javascript" src="<%=request.getContextPath()%>/focus/js/jquery.imgbox.pack.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#example1-1").imgbox();

			$("#example1-2").imgbox({
			    'zoomOpacity'	: true,
				'alignment'		: 'center'
			});   

			$("#example1-3").imgbox({
				'speedIn'		: 0,
				'speedOut'		: 0
			});

			$("#example2-1, #example2-2").imgbox({
				'speedIn'		: 0,
				'speedOut'		: 0,
				'alignment'		: 'center',
				'overlayShow'	: true,
				'allowMultiple'	: false
			});
		});
	</script>
</head>
<body>
<!-- 浠ｇ爜 寮� -->
<div id="content">
		<h1>imgBox</h1>
		<hr />
		<div id="images">
		
			<a id="example1-1" title="" href="images/4006876523_289a8296ee.jpg"><img alt="" src="images/4006876523_289a8296ee_m.jpg" /></a>
			<a id="example1-2" title="Lorem ipsum dolor sit amet" href="images/photo_unavailable.gif"><img alt="" src="images/photo_unavailable_m.gif" />
			</a>
			<a id="example1-3" title="Maecenas eros massa, pulvinar et sagittis adipiscing, &lt;br /&gt; molestie et arcu" href="images/4003912116_389c3891cf.jpg"><img alt="" src="images/4003912116_389c3891cf_m.jpg" /></a>
			
			<a id="example2-1" title="" href="images/3793633187_44790d1f0a_o.jpg"><img alt="" src="images/3793633187_f56bb1bf99_m.jpg" /></a>
			<a id="example2-2" title="Maecenas eros massa, pulvinar et sagittis adipiscing, molestie et arcu" href="images/3793633099_3e1e53e4ac_o.jpg"><img alt="" src="images/3793633099_4f9c3e08b3_m.jpg" /></a>

		</div>
		
		<div id="credit"></div>
	</div>
<!-- 浠ｇ爜 缁撴潫 -->
<div style="text-align:center">
<p>浠ｇ爜鏁寸悊锛氾細<a href="http://www.ablanxue.com/" target="_blank">ab钃濆缃�/a></p>
<p>锛婂皧閲嶄粬浜哄姵鍔ㄦ垚鏋滐紝杞浇璇疯嚜瑙夋敞鏄庡嚭澶勶紒娉細姝や唬鐮佷粎渚涘涔犱氦娴侊紝璇峰嬁鐢ㄤ簬鍟嗕笟鐢ㄩ�銆�/p>

</div>
</body>
</html>