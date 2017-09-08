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
<title>我的账户</title>
<link href="${basePath }appcssjs/style/public.css" type="text/css"rel="stylesheet">
<link href="${basePath }appcssjs/style/page.css" type="text/css"rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/page.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>  
</head>

<body name="myinfo">
 
	<!--头部-->
	<c:if test="${isWeiXinFrom==false}">
		<div class="headbox">
			<div class="title f-20">我的账户</div>
			 <a href="javascript:history.go(-1)" class="ico_back f-16">返回</a>
    <!-- <a href="#" class="ico_list">更多</a> -->
		</div>
	</c:if>
	<div class="pagelist_nav">
		<a href="${basePath }my/mycoin" class="line active">我的嘀嗒币</a><a
			href="${basePath }my/mypayrecord">交易记录</a>
	</div>
	<div class="my_account">
		<div class="box">
			<span>账户余额</span> <b>${user.tickcoin==null?'0.0':user.tickcoin}</b>
		</div>
	</div>
	<div class="account_list">
		<div class="t_name">
			<span>嘀嗒币明细</span>
		</div>
		<ul id="trade_ul">
			<c:forEach items="${coinList}" var="item"  >
				<li style="text-align: left">
					<div class="xx_01">${item.title}</div>
					<div class="xx_02">
						<span><fmt:formatDate value="${item.paytime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
						<c:if test="${empty item.pay_userid }">
							<i class="yellow">+${item.amount}</i>
						</c:if>
						<c:if test="${!empty item.pay_userid }">
							<i class="yellow">-${item.amount}</i>
						</c:if>
					</div>
				</li>
			</c:forEach>
		</ul>
	</div>
	<!--底部导航-->
<script type="text/javascript">
PageHelper({
	url:"<%=request.getContextPath() %>/my/mycoinAjax",
	data:{memberid:"${memberid}"},
	success:function(data){
		var html = $("#tmpl_li").html();
		var datalist = data.dataList;
		for(var i=0;datalist.length>i;i++){
			var d = datalist[i]; 
		     if(d.paytime !=null){
				d.paytime = hhutil.parseDate(d.paytime, "YYYY-MM-DD hh:mm:ss");
			}
		     if(d.title == null){
		    	 d.title="";
		     }
		     if(d.pay_userid == null){
		    	 d.trade="+"+d.amount;
		     }else{
		    	 d.trade="-"+d.amount;
		     }
			  $("#trade_ul").append(hhutil.replace(html,datalist[i])).trigger("create");
		} 
	}
});  

</script>

<script type="text/temple" id="tmpl_li">
 	<li style="text-align: left">
		<div class="xx_01">{title}</div>
		  <div class="xx_02">
		    <span>{paytime}</span>
			<i class="yellow">{trade}</i>
		</div>
   </li>
</script>
	
<jsp:include page="/public/app_bottom.jsp" flush="true" />
	<!--底部导航-->

</body>
</html>
