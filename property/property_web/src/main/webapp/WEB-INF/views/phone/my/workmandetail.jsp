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
	<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/index.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
</head>

<body name="myinfo">
<script type="text/javascript">
function turnBack(){
	if('${map.projectid}' != ''){
		location.href="<%=request.getContextPath()%>/employer/releasemapinfo?projectid=${map.projectid}&orderid=${map.orderid}";
	}else{
		location.href="<%=request.getContextPath()%>/employer/workermapinfo?jobid=${map.jobid}&orderid=${map.orderid}";
		
	}
}
</script>
	<!--头部-->
	<c:if test="${isWeiXinFrom==false}">
		<div class="headbox">
			<div class="title f-20">详细信息</div>
			<a href="javascript:void(0);" onclick="turnBack();" class="ico_back f-16">返回</a>
			<!-- <a href="#" class="ico_list">更多</a> -->
		</div>
	</c:if>
	<div class="banner_box">
		<div class="user_box">
			<div class="img">
			<c:if test="${empty user.headimage}">
					<img src="${basePath}appcssjs/images/page/pic_bg.png" width="86"
						height="86">
				</c:if>

				<c:if test="${!empty user.headimage}">
					<img src="${user.headimage_show}" onclick="imgutil.FDIMG(this)" width="86"
						height="86">
				</c:if>
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
				<c:forEach var="1" begin="1" end="${user.avg}">
					<img src="${basePath}appcssjs/images/public/star.png" />
				</c:forEach>
			</div>
		</div>
	</div>
	<div class="pagelist_nav">
		<a href="${basePath}my/goWorkmandetail?userid=${user.memberid}&applyorderid=${applyorder.applyorderid}" class="active line">基本资料</a>
		<a id="godetail_a" href="${basePath}my/goEvaluate?memberid=${user.memberid}&applyorderid=${applyorder.applyorderid}&isattention=${ifattention == true?'1':0}&isself=no&status=${applyorder.status}&attentionid=${attentionid}&projectid=${map.projectid}&jobid=${map.jobid}&orderid=${map.orderid}">评价列表</a>
	</div>
	<%-- <div class="num_xxbox">
		<i><a href="javascript:void();" class="bg_01"><span>成功抢单&nbsp;${applynum.count}</span></a></i>
		<i><a href="javascript:void();" class="bg_02"><span>发布订单&nbsp;${ordernum.count}</span></a></i>
		<div class="clear"></div>
	</div> --%>

	<div class="detail_xx">
		<div class="name">基本信息</div>
		<div class="box">
			<div class="span_box" style="text-align: left;">
				<span><b>姓名：</b>${user.realname}</span> <span class="sec"><b>性别：</b>
					<c:choose>
						<c:when test="${user.sex==0}">女</c:when>
						<c:when test="${user.sex==1}">男</c:when>
						<c:otherwise>
            				保密
            			</c:otherwise>
					</c:choose> </span> <span><b>年龄：</b><font id="age"></font></span>
			</div>
			<div class="p_box">
				
				<p>
					<b>成功抢单：</b>${applynum.count}单
				</p>
				
				<p>
					<b>发布订单：</b>${ordernum.count}单
				</p>
				
				
				<p>
					<b>所属区域：</b>${user.area}
				</p>
				
				<p>
					<b>公司名称：</b>${user.companyname}
				</p>
				<p>
					<b>工种/行业：</b>${user.cname}
				</p>
				<p>
					<b>最后登陆区域：</b>${user.dprovincename}&nbsp;${user.dcityname}
				</p>
			</div>
			<div class="tel">
				<c:choose>
					<c:when test="${applyorder.ifpay==0}">
						<span>联系电话 :${fn:replace(user.phone,fn:substring(user.phone,3,7),'****')}</span>
						<a onclick="call('${applyorder.orderid}','${amount}')"><img
							src="${basePath}appcssjs/images/page/btn_tel.png"></a>
					</c:when>
					<c:otherwise>
						<span onclick="tel('${user.phone}')">联系电话： ${user.phone}</span>
					 
					</c:otherwise>
				</c:choose>

			</div>
		</div>
	</div>
	<div class="detail_xx">
		<div class="name">个人简介</div>
		<div class="box_02">
			<p>${user.personalintroduction}</p>
		</div>
	</div>
	<div class="detail_xx">
		<div class="name">个人图片</div>
		<div class="box_02">
			<div class="pic_box">
              <c:forEach items="${personalImgList}" var="item">
    		   <span><img onclick="imgutil.FDIMG(this)" width="49%" style="height:120px;padding: 4 4" src="${item.personal_show}"></span>
             </c:forEach>
        <div class="clear"></div>
    </div>
		</div>
	</div>
	<div class="detail_xx">
		<div class="name">公司简介</div>
		<div class="box">
			<div class="p_box">
				<p>
					<span>${user.companyintroduction}</span>
				</p>
			</div>
		</div>
	</div>
	<div style="height: 45px;"></div>
	<c:choose>
		<c:when test="${ifattention==true}">
			<a  class="btn_follow" href="javascript:void(0);" onclick="cancelAttention('${attentionid}',this)" ><i class="state_ed" id="attention">已关注</i></a>
			<!--已关注状态i标签class为state_ed-->
		</c:when>
		<c:when test="${ifattention==false}">
			<a  class="btn_follow" href="javascript:void(0);" onclick="attention('${user.memberid}',this)"><i class="state" id="attention">关注</i></a>
			<!--已关注状态i标签class为state_ed-->
		</c:when>
	</c:choose>
	<!--底部按钮-->

	<c:if test="${applyorder.status==1}">
		<div class="bottom_btn" id="bottom">
			<span><a class="bg_green" href="javascript:void(0);" onclick="updatestatus('4','${applyorder.applyorderid}','${applyorder.orderid}','${amount }')">暂不合适</a></span>
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
	<c:if test="${applyorder.status==5}">
		<div class="bottom_btn" id="bottom">
			<span><a class="bg_green" href="javascript:void(0);" onclick="sureCancel('6','${applyorder.applyorderid}','${applyorder.orderid}')">同意取消</a></span>
			<span><a class="bg_yellow" href="javascript:void(0);" onclick="sureCancel('7','${applyorder.applyorderid}','${applyorder.orderid}')">不同意取消</a></span>
		</div>
	</c:if>
	<!--底部按钮-->

</body>
</html>
<script type="text/javascript">
function tel(phone){
	   var userAgentInfo = navigator.userAgent;   
	   if (userAgentInfo.indexOf("Android") > 0 && '${isWeiXinFrom}' == 'false' ){
		   dialTel(phone);
	   }
	   
}
$(function(){
	var usercode = "${user.idcardnum}";
	 if(usercode!=''){
	 var date = new Date();
	 var year = date.getFullYear(); 
	 var birthday_year = parseInt(usercode.substr(6,4));
	 var userage= year - birthday_year;
	 $('#age').text(userage); 
	 }
})
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
 


 function sureCancel(status,applyorderid,orderid){
		swal({
			title : "",
			text : "您确定要进行此操作?",
			type : "info",
			showCancelButton : true,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			cancelButtonText:"取消",
			closeOnConfirm : true
		}, function() {
			 $.ajax({
					type:"post",
					url:"<%=request.getContextPath() %>/order/updateApplyOrderStatus",
					data:{status:status,applyorderid:applyorderid,orderid:orderid},
					success:function(data){
						if(data){
							window.location.reload(true);
						}
					},error:function(sts){
						swal({
							title : "",
							text : "操作失败!",
							type : "error",
							showCancelButton : false,
							confirmButtonColor : "#ff7922",
							confirmButtonText : "确认",
							closeOnConfirm : true
						}, function() {
							 
						});
					 
					}
					 
				  });
		});
} 
 function attention(userid,obj){
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
						text : "关注成功,再次点击将取消关注!",
						type : "success",
						showCancelButton : false,
						confirmButtonColor : "#ff7922",
						confirmButtonText : "确认",
						closeOnConfirm : true
					}, function() {
						$(obj).find("i").removeClass("state").addClass("state_ed").html("已关注");
						$(obj).attr("onclick","cancelAttention(\'"+data.attentionid+"\',this)");
						$("#godetail_a").attr("href","${basePath}my/goEvaluate?memberid=${user.memberid}&applyorderid=${applyorder.applyorderid}&isattention=1&isself=no&status=${applyorder.status}&attentionid="+data.attentionid);
					});
				}else{
					swal("",data.errorMsg);
				}
			}
	});
}

function cancelAttention(attentionid,obj){
	$.ajax({
		url:"${basePath }/my/cancelAttention",
		type:"post",
		data:{attentionid:attentionid},
		success:function(data){
			if(data){
				swal({
					title : "",
					text : "取消关注成功!",
					type : "success",
					showCancelButton : false,
					confirmButtonColor : "#ff7922",
					confirmButtonText : "确认",
					closeOnConfirm : true
				}, function() {
					$(obj).find("i").removeClass("state_ed").addClass("state").html("关注");
					$(obj).attr("onclick","attention(\'${user.memberid}\',this)");
					$("#godetail_a").attr("href","${basePath}my/goEvaluate?memberid=${user.memberid}&applyorderid=${applyorder.applyorderid}&isattention=0&isself=no&status=${applyorder.status}&attentionid="+data.attentionid);
				});
				
			}
		}
	});
}
	
  function call(orderid,amount){
		swal({
			title : "",
			text : "查看工人联系方式需要支付"+amount+"元佣金,点击继续查看，进入支付佣金!",
			type : "info",
			showCancelButton : true,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "继续查看",
			cancelButtonText :"取消",
			html:true,
			closeOnConfirm : true
		}, function() {
			window.location.href = "${basePath }my/pay?orderid="+orderid+"&amount="+amount+"&applyorderid=${applyorder.applyorderid}&applicantid=${user.memberid}";
		});
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
</script>

