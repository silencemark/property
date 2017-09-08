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
<link href="${basePath}appcssjs/style/page.css" type="text/css"rel="stylesheet">
<link href="${basePath}appcssjs/style/public.css" type="text/css"rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/page.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>  
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
			    <c:if test="${empty user.headimage}">
					<img src="${basePath}appcssjs/images/page/pic_bg.png" width="86"
						height="86">
				</c:if>

				<c:if test="${!empty user.headimage}">
					<img src="${user.headimage_show}" width="86"
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
	<c:if test="${param.isself == null  or param.isself == ''}">
	    <a href="${basePath }member/initEditMemberInfo" class="btn_follow" style="top:35px;"><i class="edit">编辑</i></a> 
	</c:if>
	<div class="pagelist_nav">
	       <c:choose>
	          <c:when test="${param.applyorderid !=null and param.applyorderid != '' }">
	             <a href="${basePath}my/goWorkmandetail?userid=${user.memberid}&applyorderid=${param.applyorderid}&orderid=${map.orderid}&jobid=${map.jobid}&projectid=${map.projectid}" class="line">基本资料</a>
	             <a href="${basePath}my/goEvaluate?memberid=${user.memberid}&isattention=${map.isattention}&isself=${map.isself}&applyorderid=${param.applyorderid}&status=${param.status}&orderid=${map.orderid}&jobid=${map.jobid}&projectid=${map.projectid}" class="active line">评价列表</a>  
	          </c:when>
	          <c:otherwise>
	            <c:if test="${map.isself == null or map.isself =='' }">
	              <a href="${basePath}my/detailinfo" class="line">基本资料</a>
	            </c:if>
	            <c:if test="${map.isself != null  and map.isself != ''}">
	             <a href="${basePath}my/detailinfo?userid=${user.memberid}" class="line">基本资料</a>
	            </c:if>
			    <a id="evluate_a" href="${basePath}my/goEvaluate?memberid=${user.memberid}&isattention=${map.isattention}&isself=${map.isself}&attentionid=${map.attentionid}" class="active line">评价列表</a>  
	          </c:otherwise>
	       </c:choose>
	        
	</div>
 	<div class="evaluate">
		<div class="t_name">
			<span>全部评价<em>(${evaluations.size()}次)</em></span>
			<!-- <i>4.0分</i> -->
			<c:if test="${evaluations.size()>0 }">
			<b> <c:forEach var="1" begin="1" end="${user.avg}">
					<img src="${basePath}appcssjs/images/public/star.png" />
				</c:forEach>
			</b>
			</c:if>
			<div class="clear"></div>
		</div>
		<ul>
			<c:forEach items="${evaluations}" var="item">
			   <li>
	        	<div class="user_img"><b> 
	        	  <c:choose>
					<c:when test="${empty item.headimage}">
						<img src="${basePath}appcssjs/images/page/pic_bg.png"
							width="62" height="62">
					</c:when>
					<c:otherwise>
						<img src="${item.headimage_show}" width="62" height="62">
					</c:otherwise>
				</c:choose>  
	        	</b></div>
	            <div class="xx_box">
	            	<div class="xx_01"><span>${item.nickname}</span><b>
	            	  <c:forEach var="1"
									begin="1" end="${item.score}">
									<img src="${basePath}appcssjs/images/public/star.png" />
					  </c:forEach>
	            	</b></div>
	                <div class="xx_02">${item.description}</div>
	                <div class="xx_03"><fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd hh:mm" /></div>
	            </div>
            </li>
		 
			</c:forEach>
		</ul>
	</div> 
	
	<c:if test="${map.isself !=null and map.isself !='' }"> 
	<c:choose>
		<c:when test="${map.isattention  == 1}">
			<a class="btn_follow" onclick="cancelAttention('${map.attentionid}',this)">
			<i id="attention" class="state_ed"  >已关注</i></a>
		</c:when>
		<c:when test="${map.isattention == 0}">
			<a class="btn_follow"    onclick="attention('${user.memberid}',this)">
			<i id="attention" class="state">关注</i></a>
		</c:when>
	</c:choose>
    </c:if>
	<!--底部按钮-->
<%-- 	<c:if test="${param.status==1 and param.applyorderid != null and param.applyorderid != ''}">
		<div class="bottom_btn" id="bottom">
			<span><a class="bg_green"
				onclick="updatestatus('4','${param.applyorderid}')">暂不合适</a></span> <span><a
				class="bg_yellow"
				onclick="updatestatus('3','${param.applyorderid}')">成交</a></span>
		</div>
	</c:if>
    <c:if test="${param.status==5 and param.applyorderid != null and param.applyorderid != ''}">
		<div class="bottom_btn" id="bottom">
			<span><a class="bg_green" onclick="sureCancel('6','${param.applyorderid}')">同意取消</a></span>
			<span><a class="bg_yellow" onclick="sureCancel('7','${param.applyorderid}')">不同意取消</a></span>
		</div>
	</c:if> --%>
<script type="text/javascript">
function sureCancel(status,applyorderid){
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
				data:{status:status,applyorderid:applyorderid},
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
					$("#evluate_a").attr("href","${basePath}my/goEvaluate?memberid=${user.memberid}&isattention=1&isself=${map.isself}&attentionid="+data.attentionid);
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
				$("#evluate_a").attr("href","${basePath}my/goEvaluate?memberid=${user.memberid}&isattention=0&isself=${map.isself}&attentionid="+data.attentionid);
			});
			
		}
	}
});
}

	function updatestatus(status,applyorderid){
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
						if('${map.projectid}' != ''){
							window.location.href="<%=request.getContextPath() %>/employer/releasemapinfo?projectid=${map.projectid }&orderid=${map.orderid }";
						}else{
							window.location.href="<%=request.getContextPath() %>/employer/workermapinfo?jobid=${map.jobid }&orderid=${map.orderid }";
						}
					}
				}
		});
		});
		
	}
	
	
	PageHelper({
		url:"<%=request.getContextPath() %>/my/goEvaluateAjax",
		data:{userid:"${map.userid}"},
		success:function(data){
			var html = $("#tmpl_li").html();
			var datalist = data.dataList;
			for(var i=0;datalist.length>i;i++){
				var d = datalist[i]; 
			     if(d.createtime !=null){
					d.createtime = hhutil.parseDate(d.createtime, "YYYY-MM-DD hh:mm:ss");
				}
			     if(d.description == null){
			    	 d.description="";
			     }
			     
			     d.img_star="";
			     if(d.score !=null){
			    	 for(var j=0;j<parseInt(d.score);j++){
			    		 d.img_star+="<img src='${basePath}appcssjs/images/public/star.png' />";
			    	 }
			     }
			     
				  $("#trade_ul").append(hhutil.replace(html,datalist[i])).trigger("create");
			} 
		}
	});  
</script>	

 <script type="text/temple" id="tmpl_li">
  <li>
	           <div class="user_img"><b> 
					<img src="{headimage_show}" width="62" height="62">
	        	</b></div>
	            <div class="xx_box">
	            	<div class="xx_01"><span>{nickname}</span><b>
	            	  {img_star}
	            	</b></div>
	                <div class="xx_02">{description}</div>
	                <div class="xx_03">{createtime}</div>
	            </div>
            </li>
</script>
	<!--底部导航-->
	<c:if test="${param.applyorderid == null or param.applyorderid == '' }"><jsp:include page="/public/app_bottom.jsp" flush="true" /></c:if>
	<!--底部导航-->
</body>
</html>


