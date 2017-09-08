<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>雇主招工明细</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/page.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
</head>
<body name="homepage">
<!--头部-->
<c:if test="${isweixin == false }">
<div class="headbox">
  <div class="title f-20">雇主招工明细</div>
    <a href="javascript:history.go(-1)" class="ico_back f-16">返回</a>
   <!--  <a href="#" class="ico_list">更多</a> -->
</div>
</c:if>
<div class="pagelist_nav">
   <a href="<%=request.getContextPath() %>/employer/workermapinfo?jobid=${jobMap.jobid}&orderid=${jobMap.orderid}" id="ing">招工申请列表</a>
   <a href="<%=request.getContextPath() %>/order/getEmployerOrderJobctDetail?jobid=${jobMap.jobid }&orderid=${jobMap.orderid }" id="end"  class="line active">招工基本信息</a></div>
<div class="xx_detail">
	<div class="name"><span>${jobMap.jobtitle}</span></div>
    <div class="xx_box">
    	<p><b>订单标题：</b><span>${jobMap.jobtitle}</span></p>
    	<p><b>订单单号：</b><span>${jobMap.orderno }</span></p>
    	<p><b>工种：</b><span>${jobMap.cname}</span></p>
    	<p><b>薪资（元/人/天）：</b><span>${jobMap.salary}元</span></p>
    	<p><b>结算方式：</b><span>${jobMap.settlementmethodname}</span></p>
    	<p><b>用工时间（开始）：</b><span><fmt:formatDate value="${jobMap.starttime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
    	<p><b>用工时间（结束）：</b><span><fmt:formatDate value="${jobMap.endtime}" pattern="yyyy-MM-dd HH:mm"/></span></p>
    	<p><b>工作地区：</b><span>${jobMap.provincename } ${jobMap.cityname }</span></p>
    	<p><b>工作地址：</b><span>${jobMap.workplace }</span></p>
    	<p><b>招聘人数：</b><span>${jobMap.recruitmentnum}人</span></p>
        <p><b>联系人：</b><i>${jobMap.contacter }</i>
        <b>联系方式：</b><span onclick="tel('${jobMap.telephone }')">${jobMap.telephone }</span></p>
        <p><b>工作要求：</b><span>${jobMap.jobrequirements }</span></p>
    </div>
</div>
<%-- <div class="order_list01">
	<div class="t_name">
    	<span>申请记录</span>
        <i>${peopleList.size() }人</i>
        <div class="clear"></div>
    </div>
	<ul id="pople_ul">
       <c:forEach items="${peopleList }" var ="row" >
        <li>
        	<div class="user_img" style="width: auto" onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/goWorkmandetail?orderid=${row.orderid }&userid=${row.applicantid }&applyorderid=${row.applyorderid }'">
            	<b><img src="${row.headimage}" width="62" height="62"></b>
            </div>
            <div class="user_box">
                <div class="xx_04"><span class="${row.instatus == 1?'}name_v':'name' }name_v">${row.nickname }</span><b> 
                 <c:forEach var = "i" begin="1" end="${row.evalueavg }">
                               <img src="<%=request.getContextPath() %>/appcssjs/images/public/star.png">
                </c:forEach>
                </b></div>
                <div class="xx_02 word_hidden">申请时间：<fmt:formatDate value="${row.createtime }" pattern="MM-dd HH:mm"/></div>
                <div class="xx_03 word_hidden">联系方式 ： ${fn:substring(row.phone, 0, 3)}*****${fn:substring(row.phone,8,fn:length(row.phone))}${row.phone }</div>
               
            </div>
            <div class="tool_xx" style ="width: 94px">
                   <c:if test="${row.status == 3 }">
                     <c:choose>
                      <c:when test="${row.score == null }">
                        <div onclick="javascript:window.location.href='<%=request.getContextPath() %>/evaluate/initAddEvalue?orderid=${row.applyorderid }&evaluationerid=${row.publisherid }&userid=${row.applicantid }&type=employer&jobid=${jobMap.jobid }&oid=${row.orderid }'"  class="state bg_yellow">评价</div> 
                      </c:when>
                      <c:when test="${row.score != null }">
                        <div class="state " style="margin-left: 0px;margin-top: 0px">
                           <c:forEach var = "i" begin="1" end="${row.score }">
                               <img width="14px" height="13px" src="<%=request.getContextPath() %>/appcssjs/images/public/star.png">
                           </c:forEach>
                      </div>
                      </c:when>
                    </c:choose>
                   </c:if>
                   <c:choose>
            	    <c:when test="${row.status == 5 }"><div onclick="cancleOrder('${row.applyorderid}');" class="state bg_green">同意取消</div></c:when>
            	    <c:otherwise><div class="state"></div></c:otherwise>
            	 </c:choose>
            	 <div class="zx_xx01" style="${row.score != null?'margin-top:30px':''}">
                  <c:choose>
            	    <c:when test="${row.status == 1 }">待处理</c:when>
            	    <c:when test="${row.status == 2 }">待确认</c:when>
            	    <c:when test="${row.status == 3 }">已成交</c:when>
            	    <c:when test="${row.status == 4 }">暂不合适</c:when>
            	    <c:when test="${row.status == 5 }">工人取消</c:when>
            	    <c:when test="${row.status == 6 }">雇主同意</c:when>
            	    <c:when test="${row.status == 7 }">雇主拒绝</c:when>
            	    <c:when test="${row.status == 8 }">雇主取消</c:when>
            	    <c:when test="${row.status == 9 }">工人同意</c:when>
            	    <c:when test="${row.status == 10 }">工人拒绝</c:when>
            	    <c:when test="${row.status == 11 }">已过期</c:when>
            	  </c:choose>
                </div>
            </div>
        </li>
      </c:forEach>
      
    </ul>
</div> --%>
<script type="text/javascript">
function tel(phone){
	   var userAgentInfo = navigator.userAgent;   
	   if (userAgentInfo.indexOf("Android") > 0 && '${isWeiXinFrom}' == 'false' ){
		   dialTel(phone);
	   }
	   
}
 <%-- function cancleOrder(applyorderid){
	 swal({
			title : "",
			text : "您确定同意该用户的取消申请?",
			type : "info",
			showCancelButton : true,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			cancelButtonText:"取消",
			closeOnConfirm : true
		}, function() {
			 var status="6";
			 $.ajax({
				type:"post",
				url:"<%=request.getContextPath() %>/order/updateApplyOrderStatus",
				data:{status:status,applyorderid:applyorderid},
				success:function(data){
					if(data){
						  swal({
								title : "",
								text : "操作成功！",
								type : "success",
								showCancelButton : false,
								confirmButtonColor : "#ff7922",
								confirmButtonText : "确认",
								closeOnConfirm : true
							}, function() {
								window.location.reload(true);
							});	
					}
				},error:function(sts){
					  swal({
							title : "",
							text : "操作失败,请稍后再试",
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
 } --%>
 PageHelper({
		url:"<%=request.getContextPath() %>/order/getEmployerOrderJobctDetailAjax",
		data:{orderid:"${map.orderid}"},
		success:function(data){
			var html = $("#tmpl_li").html();
			var datalist = data.dataList;
			for(var i=0;datalist.length>i;i++){
				var d = datalist[i]; 
			    if(d.createtime !=null){
					d.createtime = hhutil.parseDate(d.createtime, "MM-DD hh:mm");
				}
			    if(d.instatus+"" == "1"){
	            	d.name_v = "name_v";
	            }else{
	            	d.name_v="name";
	            }
			    
			    if(d.nickname == null){
			    	d.nickname ="匿名";
			    }
			    
			    if(d.phone ==null){
			    	d.phone="暂无";
			    } 
			    
			    d.imgs_ = "";
			    if(d.evalueavg !=null ){
			    	for(var j =0 ;j < parseInt(d.evalueavg);j++){
			    		 d.imgs_ += "<img src='<%=request.getContextPath() %>/appcssjs/images/public/star.png'>";
			    	}
			    	
			    }
			    d.pj="";
			    if(d.status+"" == "3"){
			    	if(d.score == null ){ 
			    		var pjurl="<%=request.getContextPath() %>/evaluate/initAddEvalue?orderid="+d.applyorderid+"&evaluationerid="+d.publisherid+"&userid="+d.applicantid+"&type=employer&jobid=${jobMap.jobid }&oid="+d.orderid;
			    		var pjclick = "javascript:window.location.href="+"'"+pjurl+"'";
			    		d.pj = '<div onclick="'+pjclick+'" class="state bg_yellow">评价</div>';
			    	}else{
			    		for(var j =0 ;j < parseInt(d.score);j++){
			    			d.pj+="<img width='14px' height='13px' src='<%=request.getContextPath() %>/appcssjs/images/public/star.png'>";
				    	}
			    	}
			    }
			    
			    d.btn_cal="";
			    if(d.status+"" == "5"){
			    	//d.btn_cal= "<div onclick='cancleOrder(\""+d.applyorderid+"\");' class='state bg_green'>同意取消</div>";
			    }
			    
			    d.t_style="";
			    if(d.score != null){
			       d.t_style="style='margin-top:30px'";
			    }
			    
			    if(d.status+"" ==1){
	            	d.or_status="待处理";	
	            }else if(d.status+"" ==3){
	            	d.or_status="已成交";
	            }else if(d.status+"" ==4){
	            	d.or_status="暂不合适";
	            }else if(d.status+"" ==5){
	            	d.or_status="工人取消";
	            }else if(d.status+"" ==6){
	            	d.or_status="雇主同意";
	            }else if(d.status+"" ==7){
	            	d.or_status="雇主拒绝";
	            }else if(d.status+"" ==8){
	            	d.or_status="雇主取消";
	            }else if(d.status+"" ==9){
	            	d.or_status="工人同意";
	            }else if(d.status+"" ==10){
	            	d.or_status="工人拒绝";
	            }else if(d.status+"" ==11){
	            	d.or_status="已过期";
	            }else if(d.status+"" ==12){
	            	d.or_status="已被抢走";
	            }
			    
			    
				  $("#pople_ul").append(hhutil.replace(html,datalist[i])).trigger("create");
			} 
		}
	});  
 
</script>
<script type="text/temple" id="tmpl_li">
    <li>
        	<div class="user_img" style="width: auto" onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/goWorkmandetail?orderid={orderid }&userid={applicantid }&applyorderid={applyorderid }'">
            	<b><img src="{headimage}" width="62" height="62"></b>
            </div>
            <div class="user_box">
                <div class="xx_04"><span class="{name_v}">{nickname }</span><b> 
                   {imgs_}   
                </b></div>
                <div class="xx_02 word_hidden">申请时间：{createtime }</div>
                <div class="xx_03 word_hidden">联系方式 ： {phone}</div>
               
            </div>
            <div class="tool_xx" style ="width: 94px">
                   {pj}
            	   {btn_cal}  
            	 <div class="zx_xx01" {t_style}>
                   {or_status}
                </div>
            </div>
        </li>

</script>

<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
</body>
</html>
