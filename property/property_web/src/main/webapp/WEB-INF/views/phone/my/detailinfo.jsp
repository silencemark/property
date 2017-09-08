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
<link href="${basePath}appcssjs/style/public.css" type="text/css"rel="stylesheet">
<link href="${basePath}appcssjs/style/page.css" type="text/css"rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/index.js"></script>
</head>

<body name="myinfo">
<script type="text/javascript">
function turnBack(){
	   var userAgentInfo = navigator.userAgent;
	   if(userAgentInfo.indexOf("iPhone") > 0 && '${param.resource}' != '' ){
		   returnHomeBack();
	   }else{
		   window.history.go(-1);
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
					<img  onclick="imgutil.FDIMG(this)" src="${user.headimage_show}" width="86"
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
				<img src="${basePath}appcssjs/images/public/star.png"/>
			</c:forEach>
			</div>
		</div>
	</div>
	<div class="pagelist_nav">
	    
	    <c:if test="${isself == null or isself =='' }">
	           <a href="${basePath}my/detailinfo" class="active line">基本资料</a>
	    </c:if>
	    <c:if test="${isself != null  and isself != ''}">
	          <a href="${basePath}my/detailinfo?userid=${user.memberid}" class="active line">基本资料</a>
	    </c:if>
		<a id="url_evluate" href="${basePath}my/goEvaluate?memberid=${user.memberid}&isattention=${ifattention == true?'1':0}&isself=${isself}&attentionid=${attentionid}">评价列表</a>
	</div>
	<div class="num_xxbox">
	    <c:if test="${isself == null or isself =='' }">
			<i><a href="<%=request.getContextPath() %>/order/getEmployeeOrderList?type=1" class="bg_01"><span>成功抢单&nbsp;${applynum.count}</span></a></i> 
			<i><a href="<%=request.getContextPath() %>/order/getEmployerOrderList" class="bg_02"><span>发布订单&nbsp; ${ordernum.count}</span></a></i>
		</c:if>
		 
		<div class="clear"></div>
	</div>
	<div class="detail_xx">
		<div class="name">基本信息</div>
		<div class="box">
			<div class="span_box"  style="text-align: left;">
				<span><b>姓名：</b>${user.realname}</span> 
				<span class="sec"><b>性别：</b>
					<c:choose>
						<c:when test="${user.sex==0}">女</c:when>
						<c:when test="${user.sex==1}">男</c:when>
						<c:otherwise>
            				保密
            			</c:otherwise>
					</c:choose> 
				</span>
			 <span><b>年龄：</b>${fn:substringBefore(user.age,'.')}</span>
			</div>
			<div class="p_box">
			  <c:if test="${isself != null and isself !='' }">
				<p>
					<b>成功抢单：</b>${applynum.count}单
				</p>
				<p>
					<b>发布订单：</b>${ordernum.count}单
				</p>
			  </c:if>	
				
				
				<p>
					<b>所属区域：</b>${user.area}
				</p>
				
				<p>
					<b>公司名称：</b>${user.companyname}
				</p>
				<p>
					<b>工种/行业：</b>${user.cname}
				</p>
				<p onclick="void(0)">
					<b>联系电话：</b> 
					  <c:choose>
					     <c:when test="${isself == null or isself =='' }">${user.phone}</c:when>
					     <c:when test="${isself != null and isself !=''  }">
					          <c:if test="${param.ispay eq 0 or param.ispay == null or param.ispay == ''}">${fn:replace(user.phone, fn:substring(user.phone,3,7), '****')}</c:if>
					          <c:if test="${param.ispay eq 1}">${user.phone }</c:if>
					     </c:when>
					  </c:choose>
				</p>
				<p>
					<b>最后登陆区域：</b>${user.dprovincename}&nbsp;${user.dcityname}
				</p>
			</div>
		</div> 
	</div>
	<div class="detail_xx">
		<div class="name">个人简介</div>
		<div class="box_02">
			<p>${user.personalintroduction}</p>
		</div>
	</div>
	<c:if test="${isself == null  or isself == ''}">
	<div class="detail_xx">
		<div class="name">身份证照片</div>
		<div class="box_02">
			<div class="pic_box">
              <c:forEach items="${idCardImgList}" var="item">
    		   <span><img onclick="imgutil.FDIMG(this)" width="49%" style="height:120px;padding: 4 4" src="${item.idCard_show}"></span>
             </c:forEach>
        <div class="clear"></div>
    </div>
		</div>
	</div>
	</c:if>
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
	 <c:if test="${isself == null  or isself == ''}">
	    <a href="${basePath }member/initEditMemberInfo" class="btn_follow" style="top:35px;"><i class="edit">编辑</i></a> 
	</c:if>
	<c:if test="${isself != null }">
	<c:choose>
		<c:when test="${ifattention==true}">
			<a href="javascript:void(0);" onclick="cancelAttention('${attentionid}',this)" class="btn_follow"><i class="state_ed" id="attention"
				 >已关注</i></a>
			<!--已关注状态i标签class为state_ed-->
		</c:when>
		<c:when test="${ifattention==false}">
	    <a href="javascript:void(0);" onclick="attention('${user.memberid}',this)" class="btn_follow"><i class="state" id="attention" >关注</i></a>
			<!--已关注状态i标签class为state_ed-->
		</c:when>
	</c:choose>
    </c:if>
    
 <script type="text/javascript">
 function tel(phone){
	   var userAgentInfo = navigator.userAgent;   
	   if (userAgentInfo.indexOf("Android") > 0 && '${isWeiXinFrom}' == 'false' ){
		   dialTel(phone);
	   }
	   
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
						$("#url_evluate").attr("href","${basePath}my/goEvaluate?memberid=${user.memberid}&isattention=1&isself=${isself}&attentionid="+data.attentionid);
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
					$("#url_evluate").attr("href","${basePath}my/goEvaluate?memberid=${user.memberid}&isattention=0&isself=${isself}&attentionid="+data.attentionid);
				});
				
			}
		}
	});
}
	
</script>
	<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
	<!--底部导航-->

</body>
</html>
