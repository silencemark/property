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
<title>我的关注</title>
<link href="${basePath}appcssjs/style/public.css" type="text/css"rel="stylesheet">
<link href="${basePath}appcssjs/style/page.css" type="text/css"rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/page.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>  
</head>

<body name="myinfo">
	<!--头部-->
	<c:if test="${isWeiXinFrom==false}">
		<div class="headbox">
			<div class="title f-20">我的关注</div>
			<a href="javascript:history.go(-1)" class="ico_back f-16"  >返回</a> 
			<!-- <a href="#" class="ico_list">更多</a> -->
		</div>
	</c:if>
	<div class="follow_list">
		<ul id="attention_ul">
        <c:forEach items="${attentions}" var="item" varStatus="t">    
    	<li onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/detailinfo?userid=${item.memberid}'">
    	    
        	<div class="user_img"><img src="
        	    <c:choose>
    	          <c:when test="${item.headimage != null and item.headimage != '' }">${item.headimage_show }</c:when>
    	           <c:otherwise>${basePath}appcssjs/images/page/pic_bg.png</c:otherwise>
    	        </c:choose>
        	  " width="62" height="62"></div>
            <div class="xx_box">
            	<div class="xx_01"><span class="name">${item.nickname }</span><b style="float:right">
            	   <c:forEach var="1" begin="1" end="${item.avg}">
							<img src="${basePath}appcssjs/images/public/star.png"/>
				   </c:forEach>
            	   </b></div>
                <div class="xx_02"><span>
                   <c:choose>
						<c:when test="${item.sex==0}">女</c:when>
						<c:when test="${item.sex==1}">男</c:when>
						<c:otherwise>
						   	保密
						</c:otherwise>
					</c:choose>
                </span><span>${fn:substringBefore(item.age,'.')}岁</span><i style="float:right">${item.cname }</i></div>
                <div class="xx_03">${fn:replace(item.phone, fn:substring(item.phone,3,7), '****')}</div>
            </div>
        </li>
        </c:forEach>
    </ul>
	</div>
<script type="text/javascript">
PageHelper({
	url:"<%=request.getContextPath() %>/my/myattentionAjax",
	data:{memberid:"${memberid}"},
	success:function(data){
		var html = $("#tmpl_li").html();
		var datalist = data.dataList;
		for(var i=0;datalist.length>i;i++){
			var d = datalist[i]; 
		     if(d.paytime !=null){
				d.paytime = hhutil.parseDate(d.paytime, "YYYY-MM-DD hh:mm:ss");
			}
		    if(d.headimage == null){
		       d.headimage = "${basePath}appcssjs/images/page/pic_bg.png";
		    }
		     
		    d.img_star="";
		    if(d.avg != null ){
		    	for(var j=0;j<parseInt(d.avg);j++){
		    	  d.img_star+= "<img src='${basePath}appcssjs/images/public/star.png'/>";
		    	}
		    }
		    
		    if(d.sex+"" == "1"){
		       d.sex="男";
		    }else if(d.sex+"" == "0"){
		       d.sex="女";
		    }else{
		       d.sex="保密";
		    }
		    if(d.phone ==null){
		    	d.phone="暂无";
		    }else{
		    	d.phone=d.phone.replace(d.phone.substring(3,7),"****");
		    } 
		     
			$("#attention_ul").append(hhutil.replace(html,datalist[i])).trigger("create");
		} 
	}
});  

</script>

<script type="text/temple" id="tmpl_li">
            <li onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/detailinfo?userid={memberid}'">
					<div class="user_img">
						<img src="{headimage}" width="62" height="62">
					</div>
					<div class="xx_box">
						<div class="xx_01">
							<span class="name">{nickname}</span>
							<b style="float:right">
							   {img_star}
							</b>
						</div>
						<div class="xx_02">
							<span>{sex}</span>
                         <span>{age}岁</span><i style="float:right">{cname}</i>
						</div>
						<div class="xx_03">{phone}</div>
					</div>
				</li>
</script>
	<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
	<!--底部导航-->

</body>
</html>

