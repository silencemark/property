<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>

<body class="p-dashboard">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 

		<div class="content">
			<div class="crumb">
				<h5>
					首页
				</h5>
			</div>
			<div class="content-container">
				<div class="row">
					<div class="col-md-12" role="main" id="main">
						<h3 class="current-tit">
							<span>首页</span>
						</h3>
						<script src="<%=request.getContextPath() %>/js/esl.js"></script>
					<div class="dash-cover">
                        
                        <div class="dash-cover-item">
                           <Strong style="margin-left:20px;font-size: 16px;">欢迎使用嘀嗒叫人信息管理系统！</Strong>
                        </div>
                        
                    </div>
                    </div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
$(function(){
    function echartWidth () {
        var tabsWidth = $(".nav-pills").width();
        $(".ui-echarts").css ("width",function () {
            return (tabsWidth - 30);
        });
    }
    echartWidth();
    $(window).on('resize', echartWidth);
});
</script>
</body>
</html>