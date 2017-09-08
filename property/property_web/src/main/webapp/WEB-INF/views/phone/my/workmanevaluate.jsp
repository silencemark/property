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
<title>详细信息</title>
<link href="${basePath}appcssjs/style/public.css" type="text/css"
	rel="stylesheet">
<link href="${basePath}appcssjs/style/page.css" type="text/css"
	rel="stylesheet">
</head>

<body name="myinfo">
	<!--头部-->
	<c:if test="${isWeiXinFrom==false}">
		<div class="headbox">
			<div class="title f-20">详细信息</div>
			<a href="javascript:history.go(-1)" class="ico_back f-16">返回</a>
			<!-- <a href="#" class="ico_list">更多</a> -->
		</div>
	</c:if>
	<div class="banner_box">
		<div class="user_box">
			<div class="img">
				<img src="${basePath}appcssjs/images/index/user_pic01.png"
					width="86" height="86">
			</div>
			<!--<div class="name f-16">罗珍</div>-->
			<!--未认证设计师
        <div class="name_v f-16">罗珍</div><!--已认证设计师-->
			<c:choose>
				<c:when test="${user.individualstatus==1||user.enterprisestatus==1}">
					<div class="name_v f-16">${user.nickname}</div>
				</c:when>
				<c:otherwise>
					<div class="name f-16">${user.nickname}</div>
				</c:otherwise>
			</c:choose>
			<!--已认证设计师-->
			<div class="star_box">
				<c:forEach var="1" begin="1" end="${statistics.avg}">
					<img src="${basePath}appcssjs/images/public/star.png" />
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="pagelist_nav">
		<a
			href="${basePath}my/detailinfo?userid=${user.memberid}&applyorderid=${applyorder.applyorderid}"
			class="line">基本资料</a><a class="active line"
			href="${basePath}my/goEvaluate?userid=${user.memberid}&applyorderid=${applyorder.applyorderid}">评价列表</a>
	</div>
	<div class="evaluate">
		<div class="t_name">
			<span>全部评价<em>(${fn:substringBefore(statistics.avg,'.')}次)</em></span>
			<!-- <i>4.0分</i> -->
			<b> <c:forEach var="1" begin="1" end="${statistics.avg}">
					<img src="${basePath}appcssjs/images/public/star.png" />
				</c:forEach>
			</b>
			<div class="clear"></div>
		</div>
		<ul>
			<c:forEach items="${evaluations}" var="item">
				<li>
					<div class="user_img">
						<b> <c:choose>
								<c:when test="${empty item.headimage}">
									<img src="${basePath}appcssjs/images/page/pic_bg.png"
										width="62" height="62">
								</c:when>
								<c:otherwise>
									<img src="${item.headimage}" width="62" height="62">
								</c:otherwise>
							</c:choose>
						</b>
					</div>
					<div class="xx_box">
						<div class="xx_01">
							<span>${item.nickname}</span> <b> <c:forEach var="1"
									begin="1" end="${item.score}">
									<img src="${basePath}appcssjs/images/public/star.png" />
								</c:forEach>
							</b>
						</div>
						<div class="xx_01">
							<span>${item.description}</span>
						</div>
						<div class="xx_01">
							<span><fmt:formatDate value="${item.createtime}"
									pattern="yyyy-MM-dd hh:mm" /></span>
						</div>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<c:choose>
		<c:when test="${ifattention==true}">
			<a class="btn_follow">
			<i id="attention" class="state_ed" onclick="attention()">关注</i></a>
		</c:when>
		<c:when test="${ifattention==false}">
			<a class="btn_follow">
			<i id="attention" class="state" onclick="attention('${user.memberid}')">关注</i></a>
		</c:when>
	</c:choose>
	<!--底部按钮-->
	<c:if test="${applyorder.status==1}">
		<div class="bottom_btn" id="bottom">
			<span>
			   <a class="bg_green" href="javascript:void(0);" onclick="updatestatus('4','${applyorder.applyorderid}','${applyorder.orderid}','${amount }')">暂不合适</a>
		   </span> 
		   
		   <c:choose>
					<c:when test="${applyorder.ifpay==0}">
					   <span><a class="bg_yellow" href="javascript:void(0);" onclick="sureClick();">成交</a></span>
					</c:when>
					<c:otherwise>
					<span><a class="bg_yellow" href="javascript:void(0);" onclick="updatestatus('3','${applyorder.applyorderid}','${applyorder.orderid}','${amount }')">成交</a></span>
					</c:otherwise>
			</c:choose>
		</div>
	</c:if>
	<c:if test="${applyorder==null}">
		<!--底部导航-->
		<jsp:include page="/public/app_bottom.jsp" flush="true" />
		<!--底部导航-->
	</c:if>


</body>
</html>
<script type="text/javascript">
function sureClick(){
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath() %>/workers/judgeorder",
		data:{orderid:'${applyorder.orderid}'},
		success:function(data){
			if(data == "5"){
				  swal({
		 				title : "",
		 				text : "您已经招满工人了！不能再招了！",
						type : "",
		 				showCancelButton : false,
		 				confirmButtonColor : "#ff7922",
		 				confirmButtonText : "确认",
						closeOnConfirm : true
					}, function(){
						 
					});	
				
			}else{
				var amount = '${amount}';
				textStr = "查看工人联系方式需要支付"+amount+"元佣金,点击继续查看，进入支付佣金!";
				swal({ 
					title : "",
					text : textStr, 
					showCancelButton : true,
					confirmButtonColor : "#ff7922",
					confirmButtonText : "确认",
					cancelButtonText : "取消",
					closeOnConfirm : true
				}, function() {
					window.location.href='<%=request.getContextPath() %>/my/payclinch?orderid=${applyorder.orderid}&amount=${amount}&applyorderid=${applyorder.applyorderid}&applicantid=${user.memberid}';	
				});	
				
			}
		 
		}
	});
	
}
	function attention(userid){
		if(userid==null||userid==''){
			swal("","您已关注过了，无需再关注。");
			return 
		}
		$.ajax({
			type : "post",
			dataType : "json",
			url : "${basePath }/my/attention",
			data : {
				"userid" : userid
			},
			success : function(data) {
				if (data.success == true) {
					swal({
						title : "",
						text : "关注成功!",
						type : "success",
						showCancelButton : false,
						confirmButtonColor : "#ff7922",
						confirmButtonText : "确认!",
						closeOnConfirm : false
					}, function() {
						$('#attention').removeClass("state").addClass("state_ed");
						$('#attention').attr("onclick","attention()");
					});
				}else{
					swal("",data.errorMsg);
				}
		}
	})
}
	function updatestatus(status,applyorderid,orderid,amount){
		var textStr ="";
		if(status=='4'){
			textStr="您要忍心拒绝可爱的工人小伙伴吗？";
		}else if(status=='3'){
			if('${applyorder.ifpay}'=='1'){
				textStr = "确认要和工人小伙伴合作！"
			}else{
				textStr = "查看工人联系方式需要支付"+amount+"元佣金,点击继续查看，进入支付佣金!"
			}
		}else{
			textStr="操作确认！";
		}
		swal({
			title : "",
			text : textStr,
			showCancelButton : true,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			cancelButtonText : "取消",
			closeOnConfirm : true
		}, function() {
			$.ajax({
				type : "post",
				dataType : "json",
				url : "${basePath }/my/updateApplyOrderStatus",
				data : {
					"status" : status,
					"applyorderid" : applyorderid,
					"orderid" : orderid
				},
				success : function(data) {
					if (data.success == true) {
						$('#bottom').hide();
						if('${map.projectid}' != ''){
							window.location.href="<%=request.getContextPath() %>/employer/releasemapinfo?projectid=${map.projectid }&orderid=${map.orderid }";
						}else{
							window.location.href="<%=request.getContextPath() %>/employer/workermapinfo?jobid=${map.jobid }&orderid=${map.orderid }";
						}
						
					}else{
						swal("",data.msg);
					}
				}
		});
		});
		
	}
	<%-- function updatestatus(status,applyorderid){
		var textStr ="";
		if(status=='4'){
			textStr="您要忍心拒绝可爱的工人小伙伴吗？";
		}else if(status=='3'){
			textStr = "确认要和工人小伙伴合作!";
		}else{
			textStr="操作确认！"
		}
		swal({
			title : "",
			text : textStr,
			showCancelButton : true,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			cancelButtonText : "取消",
			closeOnConfirm : true
		}, function() {
			$.ajax({
				type : "post",
				dataType : "json",
				url : "${basePath }/my/updateApplyOrderStatus",
				data : {
					"status" : status,
					"applyorderid" : applyorderid
				},
				success : function(data) {
					if (data.success == true) {
						$('#bottom').hide();
					}
				}
		});
		});
		
	} --%>
</script>

