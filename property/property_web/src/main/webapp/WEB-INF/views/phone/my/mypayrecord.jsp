<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/head/base.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>我的账户交易记录</title>
<link href="${basePath }appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="${basePath }appcssjs/style/page.css" type="text/css" rel="stylesheet">
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
<div class="pagelist_nav"><a href="${basePath }my/mycoin" class="line">我的嘀嗒币</a><a href="${basePath }my/mypayrecord" class="active">交易记录</a></div>
<div class="my_account">
	<div class="box">
        <span>总交易额（元）</span>
        <b>${sumMap.amount==null?'0.0':sumMap.amount}</b>
    </div>
</div>
<div class="account_list">
	<div class="t_name"><span>交易记录</span></div>
    <ul id="trade_ul">
    <c:forEach items="${payList}" var="item"  >
    	<li style="text-align: left">
        	<div class="xx_01"> 
        		<c:choose>
				   <c:when test="${item.paypurposetype==1}"> 
				    	&nbsp;消费 
				   </c:when>
				    <c:when test="${item.paypurposetype==2}"> 
				    	&nbsp;查看工人信息 
				   </c:when>
				    <c:when test="${item.paypurposetype==99}"> 
				    	&nbsp;资料完善奖励红包
				   </c:when>
				   <c:otherwise>
				   		&nbsp;其它
				   </c:otherwise>
				</c:choose>
        	</div>
            <div class="xx_02"><span><fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
            	<i class="yellow">
        		<c:choose>
				   <c:when test="${item.incomepay==1}"> 
				    	+${item.amount}
				   </c:when>
				   <c:otherwise>
				   		-${item.amount}
				   </c:otherwise>
				</c:choose>
				</i></div>
        </li>
        </c:forEach>
    </ul>
</div>

<script type="text/javascript">
PageHelper({
	url:"<%=request.getContextPath() %>/my/mypayrecordAjax",
	data:{memberid:"${memberid}"},
	success:function(data){
		var html = $("#tmpl_li").html();
		var datalist = data.dataList;
		for(var i=0;datalist.length>i;i++){
			var d = datalist[i]; 
		     if(d.createtime !=null){
				d.createtime = hhutil.parseDate(d.createtime, "YYYY-MM-DD hh:mm:ss");
			}
		     if(d.paypurposetype+"" == "1"){
		    	 d.msg ="消费";
		     }else if(d.paypurposetype+"" == "2"){
		    	 d.msg ="查看工人信息";
		     }else if(d.paypurposetype+"" == "99"){
		    	 d.msg ="资料完善奖励红包";
		     }else{
		    	 d.msg ="其它";
		     }
		     if(d.incomepay+"" == "1"){
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
     <div class="xx_01">
        {msg}
      </div>
      <div class="xx_02"><span>{createtime}</span>
         <i class="yellow">
        	{trade}
		</i>
      </div>
  </li>
</script>


	<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
	<!--底部导航-->

</body>
</html>

