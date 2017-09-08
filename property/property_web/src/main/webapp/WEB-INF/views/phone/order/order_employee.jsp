<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>我的订单</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
</head>
<body name="homepage">
<!--头部-->
<c:if test="${isweixin == false }">
<div class="headbox">
  <div class="title f-20">我的订单</div>
    <a href="javascript:turnBack()" class="ico_back f-16">返回</a>
    <!-- <a href="#" class="ico_list">更多</a> -->
</div>
</c:if>
<script type="text/javascript">
 $(function(){
	 if('${param.type}' == "2"){
		 $("#ing").removeClass("line active");
		 $("#end").addClass("line active");
	 }else{
		 $("#end").removeClass("line active");
	 }
	 
 });
 function turnBack(){
	   var userAgentInfo = navigator.userAgent;
	   if(userAgentInfo.indexOf("iPhone") > 0 && '${param.resource}' != '' ){ 
		   returnHomeBack();
	   }else{
		   window.history.go(-1);
	   }
 }
 
</script>
<div class="pagelist_nav">
   <a href="<%=request.getContextPath() %>/order/getEmployeeOrderList?type=1" id="ing" class="line active">处理中</a>
   <a href="<%=request.getContextPath() %>/order/getEmployeeOrderList?type=2" id="end">已完成</a></div>
 <div class="order_list01">
	<ul id="order_list">
    	<c:forEach items="${dataList}" var ="row" varStatus="t">
        <li>
        	<div class="user_img" onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/detailinfo?userid=${row.publisherid }&ispay=${row.ifpay}'">
            	<b><img src="${row.headimage_show }" width="62" height="62"></b>
                <span class="${row.instatus ==1?'name_v':'name' }">${row.nickname }</span>
            </div>
            <div class="user_box" 
                 <c:choose>
            	    <c:when test="${row.isjob == null }">
            	       onclick="javascript:window.location.href='<%=request.getContextPath() %>/employer/applyProjectDetail?applyorderid=${row.applyorderid }'"
            	    </c:when>
            	    <c:when test="${row.isjob != null }">
            	       onclick="javascript:window.location.href='<%=request.getContextPath() %>/employer/applyJobDetail?applyorderid=${row.applyorderid }'"
            	    </c:when>
            	  </c:choose>
            >
                <div class="xx_01 ${row.isjob !=null?'bg_gong':'bg_xiang' } word_hidden">${row.title }<c:if test="${row.isjob != null }"><%-- <span>￥${row.salary }</span> --%></c:if></div>
                <div class="xx_02 word_hidden"><fmt:formatDate value="${row.starttime }" pattern="MM-dd HH:mm"/> 至 <fmt:formatDate value="${row.endtime }" pattern="MM-dd HH:mm"/></div>
                  <div class="xx_03 word_hidden">${row.companyname}</div>
                <div class="xx_03 word_hidden">${row.contacter } ${row.telephone }</div>
            </div>
            <div class="tool_xx" style="width: 94px">
            	<c:if test="${row.isjob != null }"><div class="num">${row.recruitmentnum }人</div></c:if>
            	<c:if test="${row.isjob == null }"><div class="num">1人</div></c:if>
            	<c:choose>
	            	<c:when test="${row.status < 5 }">
	            	   <c:choose>
	            	   <c:when test="${row.status != 4 and row.orderstatus == 1 and row.status != 2}">
	            	     <div class="state bg_yellow" onclick="cancleOrder('${row.orderid }','${row.applyorderid}','${row.status }')" ${row.status ==3?'STYLE="MARGIN-TOP:5PX"':'' } >取消订单</div>
	            	   </c:when>
	            	   <c:otherwise>
	            	      <div style="height: auto" class="state"></div> 
	            	   </c:otherwise>
	            	   </c:choose>
	            	   <c:if test="${row.orderstatus == 2 and row.status == 3 }">
	            	        <c:choose>
			                      <c:when test="${row.score == null  }">
			                            <div class="state bg_green" onclick="javascript:window.location.href='<%=request.getContextPath() %>/evaluate/initAddEvalue?orderid=${row.applyorderid }&evaluationerid=${row.applicantid }&userid=${row.publisherid }&type=employee'" STYLE="MARGIN-TOP:5PX" >去评价</div> 
			                      </c:when>
			                      <c:when test="${row.score != null }">
			                        <div   style="text-align: center;margin-top: 5px">
			                           <c:forEach var = "i" begin="1" end="${row.score }">
			                               <img width="14px" height="13px" src="<%=request.getContextPath() %>/appcssjs/images/public/star.png">
			                           </c:forEach>
			                      </div>
			                      </c:when>
                           </c:choose>
	            	   </c:if>
	            	</c:when>
	            	<c:otherwise><div class="state"></div></c:otherwise>
            	</c:choose>
                <div class="zx_xx01" style="${row.score != null?'margin-top:5px':''}">
                 <c:choose>
            	    <c:when test="${row.status == 1 }">待处理</c:when>
            	    <c:when test="${row.status == 2 }">已取消</c:when>
            	    <c:when test="${row.status == 3 }">已成交</c:when>
            	    <c:when test="${row.status == 4 }">暂不合适</c:when>
            	    <c:when test="${row.status == 5 }">工人取消</c:when>
            	    <c:when test="${row.status == 6 }">雇主同意</c:when>
            	    <c:when test="${row.status == 7 }">雇主拒绝</c:when>
            	    <c:when test="${row.status == 8 }">雇主取消</c:when>
            	    <c:when test="${row.status == 9 }">工人同意</c:when>
            	    <c:when test="${row.status == 10 }">工人拒绝</c:when>
            	    <c:when test="${row.status == 11 }">已过期</c:when>
            	    <c:when test="${row.status == 12 }">已被抢走</c:when>
            	  </c:choose>
                </div>
            </div>
        </li>
        </c:forEach>
    </ul>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/page.js"></script>
<script type="text/javascript">
 function cancleOrder(orderid,applyorderid,st){
	 var status ="";
	 if(st=="1"){
		 status="2";
	 }else{
		 status ="5";
	 }
	 swal({
			title : "",
			text : "取消行为，将会影响您在平台的信誉！您确定还要取消吗?",
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
				data:{status:status,applyorderid:applyorderid,iscancel:"1",orderid:orderid},
				success:function(data){
					if(data){
						<%-- window.location.href="<%=request.getContextPath() %>/order/getEmployeeOrderList?type=2"; --%>
						window.location.reload(true);
					}
				},error:function(sts){
					  swal({
							title : "",
							text : "取消订单失败，请稍后再试",
							type : "info",
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
 
 PageHelper({
		url:"<%=request.getContextPath() %>/order/getEmployeeOrderListAjax",
		data:{type:"${param.type}"},
		success:function(data){
			var html = $("#tmpl_li").html();
			var datalist = data.dataList;
			for(var i=0;datalist.length>i;i++){
				var d = datalist[i]; 
			    if(d.starttime !=null){
					d.starttime = hhutil.parseDate(d.starttime, "MM-DD hh:mm");
				}
	            if(d.endtime !=null){
					d.endtime = hhutil.parseDate(d.endtime, "MM-DD hh:mm");
				}
	            if(d.instatus+"" == "1"){
	            	d.name_v = "name_v";
	            }else{
	            	d.name_v="name";
	            }
	            
	            if(d.isjob  == null){
	            	d.detClick = "javascript:window.location.href='<%=request.getContextPath() %>/employer/applyProjectDetail?applyorderid="+d.applyorderid+"'";
	            	d.class_img="bg_xiang";
	            	d.span_text="";
	            	d.peonum = 1;
	            }else{
	            	d.detClick = "javascript:window.location.href='<%=request.getContextPath() %>/employer/applyJobDetail?applyorderid="+d.applyorderid+"'";
	                d.class_img="bg_gong";
	                d.span_text="<span>￥"+d.salary+"</span>";
	                d.peonum = d.recruitmentnum;
	            }
	            
	            if(d.companyname == null){
	            	d.companyname = "";
	            } 
	             
	            var cancle="<div style='height:auto' class='state'></div>";
	            var pj="";
	            d.ht="<div class='state'></div>";
	            if(parseInt(d.status) < 5){
	            	var sl = "";
            		if(d.status+"" == "3" ){
            			sl = "STYLE='MARGIN-TOP:5PX'";
            			if(d.score == null && d.orderstatus+"" == "2"){
            				var t ="<%=request.getContextPath() %>/evaluate/initAddEvalue?orderid="+d.applyorderid+"&evaluationerid="+d.applicantid+"&userid="+d.publisherid+"&type=employee";
            				var onck = "javascript:window.location.href="+"'"+t+"'";
            				pj ='<div class="state bg_green" onclick="'+onck+'" STYLE="MARGIN-TOP:5PX" >去评价</div>';
            			}else{
            				var img = "";
            				var dd= parseInt(d.score);
            				for(var j =0; j<dd;j++){
            				    img+="<img width='14px' height='13px' src='<%=request.getContextPath() %>/appcssjs/images/public/star.png'>";	
            				}
            				pj = "<div style='text-align: center;margin-top: 5px'>"+img+"</div>";
            			}
            			
            		} 
	            	if(d.status+"" != "4" && d.orderstatus+"" == "1"){
	            		cancle ="<div class='state bg_yellow' "+sl+" onclick='cancleOrder(\""+d.applyorderid+"\",\""+d.status+"\")' >取消订单</div>";
	            	}
	            	
	            	d.ht=""+cancle+pj;
	            	
	            } 
	            
	            d.st_style="";
	            if(d.score != null){
	            	d.st_style = "style='margin-top:5px'";
	            }
	            
	            if(d.status+"" ==1){
	            	d.or_status="待处理";	
	            }else if(d.status+"" ==2){
	            	d.or_status="已取消";
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
	            
		 
			  $("#order_list").append(hhutil.replace(html,datalist[i])).trigger("create");
			} 
		}
	});  
 
</script>
<script type="text/temple" id="tmpl_li">
<li>
        	<div class="user_img" onclick="javascript:window.location.href='<%=request.getContextPath()%>/my/goWorkmandetail?orderid={orderid }&applicantid={applicantid }&publisherid={publisherid }'">
            	<b><img src="{headimage_show }" width="62" height="62"></b>
                <span class="{name_v}">{nickname }</span>
            </div>
            <div class="user_box" onclick="{detClick}">
                <div class="xx_01 {class_img} word_hidden">{title } {span_text}</div>
                <div class="xx_02 word_hidden">{starttime } 至 {endtime }</div>
                <div class="xx_03 word_hidden">{companyname }</div>
                <div class="xx_03 word_hidden">{contacter } {telephone }</div>
            </div>
            <div class="tool_xx" style="width: 94px">
            	   <div class="num">{peonum}人</div>
                   {ht}
                <div class="zx_xx01" {st_style}>
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
