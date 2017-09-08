<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>雇主我的订单</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
</head>
<body name="homepage">
<!--头部-->
<c:if test="${isweixin == false }">
<div class="headbox">
  <div class="title f-20">雇主我的订单</div>
    <a href="javascript:void(0);" onclick="turnBack()" class="ico_back f-16">返回</a>
    <!-- <a href="#" class="ico_list">更多</a> -->
</div>
</c:if>
<script type="text/javascript">
 $(function(){
	 if('${param.status}' == "2"){
		 $("#ing").removeClass("line active");
		 $("#end").addClass("line active");
	 }else{
		 $("#end").removeClass("line active");
	 }
	 
 });
 function turnBack(){
	   var userAgentInfo = navigator.userAgent;
	   if(userAgentInfo.indexOf("iPhone") > 0 && '${param.resource}' != ''){
		   returnHomeBack();
	   }else{
		   window.history.go(-1);
	   }
 }
</script>
<div class="pagelist_nav">
   <a href="<%=request.getContextPath() %>/order/getEmployerOrderList" id="ing" class="line active">处理中</a>
   <a href="<%=request.getContextPath() %>/order/getEmployerOrderList?status=2" id="end">已完成</a></div>
<div class="order_list01">
	<ul id="order_list">
    	 <c:forEach items="${orderList }" var ="row" varStatus="t">
        <li>
        	<div class="user_img" onclick="javascript:window.location.href='<%=request.getContextPath() %>/my/detailinfo'">
            	<b><img src="${row.headimage }" width="62" height="62"></b>
                <span class="${row.instatus ==1?'name_v':name }">${row.nickname }</span>
            </div>
             <div 
<%--                 <c:choose>
                 <c:when test="${param.status == 2  }">
                    <c:choose>
                       <c:when test="${row.projectid ==null }">
		                 onclick="javascript:window.location.href='<%=request.getContextPath() %>/order/getEmployerOrderJobctDetail?jobid=${row.jobid }&orderid=${row.orderid }'"
		               </c:when>
		               <c:when test="${row.projectid !=null }">
		                 onclick="javascript:window.location.href='<%=request.getContextPath() %>/order/getEmployerOrderProjectDetail?projectid=${row.projectid }&orderid=${row.orderid }'"
		               </c:when>
                     </c:choose>`
                 </c:when>
                 <c:when test="${param.status == 1  }"> --%>
		            <c:choose>
		              <c:when test="${row.projectid ==null }">
		                 onclick="javascript:window.location.href='<%=request.getContextPath() %>/employer/workermapinfo?jobid=${row.jobid }&orderid=${row.orderid }'"
		              </c:when>
		              <c:when test="${row.projectid !=null }">
		                 onclick="javascript:window.location.href='<%=request.getContextPath() %>/employer/releasemapinfo?projectid=${row.projectid }&orderid=${row.orderid }'"
		              </c:when>
		            </c:choose>
		           <%--  </c:when>
		        </c:choose> --%>
             >
            <div class="user_box">
                <div class="xx_01 ${row.projectid ==null?'bg_gong':'bg_xiang' } word_hidden">${row.title}</div>
                <div class="xx_02 word_hidden"><fmt:formatDate value="${row.starttime }" pattern="MM-dd HH:mm"/> 至 <fmt:formatDate value="${row.endtime }" pattern="MM-dd HH:mm"/></div>
                <div class="xx_03 word_hidden">${row.address }</div>
            </div>
            <div class="tool_xx" >
            	<div class="num">
            	  <c:choose>
            	     <c:when test="${row.projectid == null}">${row.peoplenum}/${row.recruitmentnum}</c:when>
            	     <c:when test="${row.projectid != null}">${row.peoplenum}/1</c:when>
            	  </c:choose>
            	人</div>
               
                   <c:choose>
            	     <c:when test="${row.status == 1}">
            	         <c:choose>
            	           <c:when test="${param.status == 2 }"> <div class="state bg_yellow">已完成</div></c:when>
            	           <c:otherwise><div class="state bg_green">处理中</div></c:otherwise>
            	         </c:choose>  
            	      </c:when>
            	     <c:when test="${row.status == 2}"><div class="state bg_yellow">已完成</div></c:when>
            	  </c:choose>
                 </div>
           </div>
        </li>
        </c:forEach>
    </ul>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/page.js"></script>
<script type="text/javascript">
 PageHelper({
		url:"<%=request.getContextPath() %>/order/getEmployerOrderListAjax",
		data:{status:"${param.status}"},
		success:function(data){
			var html = $("#tmpl_li").html();
			var datalist = data.dataList;
			for(var i=0;datalist.length>i;i++){
				var d =datalist[i];
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
				  
				d.on_click="";
				if(d.status+"" == "2"  ){
					if(d.projectid == null){
						d.on_click = "javascript:window.location.href='<%=request.getContextPath() %>/order/getEmployerOrderJobctDetail?jobid="+d.jobid+"+&orderid="+d.orderid+"'";
					}else{
						
						d.on_click = "javascript:window.location.href='<%=request.getContextPath() %>/order/getEmployerOrderProjectDetail?projectid="+d.projectid+"&orderid="+d.orderid+"'";
					} 
					
				}else{
                    if(d.projectid == null){
                    	d.on_click = "javascript:window.location.href='<%=request.getContextPath() %>/employer/workermapinfo?jobid="+d.jobid+"&orderid="+d.orderid+"'";
						
					}else{
						d.on_click = "javascript:window.location.href='<%=request.getContextPath() %>/employer/releasemapinfo?projectid="+d.projectid +"&orderid="+d.orderid+"'";
					} 
					
				}
				
				if(d.projectid == null){
					d.img_class = "bg_gong";
					d.peoplenums = d.peoplenum+"/"+d.recruitmentnum;
				}else{
					d.img_class = "bg_xiang";
					d.peoplenums = d.peoplenum+"/1";
				}
				
				d.btn_color="state bg_yellow";
				if(d.status+"" == "1"){
					d.btn_color="state bg_green";
					d.s_st = "处理中";
					
				}else{
					d.s_st = "已完成";
				}
					 
					 
			  $("#order_list").append(hhutil.replace(html,datalist[i])).trigger("create");
			} 
		}
	}); 
 </script>	 
<script type="text/temple" id="tmpl_li">
  <li>
        <div class="user_img" onclick="javascript:window.location.href='<%=request.getContextPath() %>/my/detailinfo'">
            	<b><img src="{headimage }" width="62" height="62"></b>
                <span class="{name_v}">{nickname }</span>
            </div>
            <div onclick="{on_click}">
            <div class="user_box">
                <div class="xx_01 {img_class} word_hidden">{title}</div>
                <div class="xx_02 word_hidden">{starttime } 至{endtime }</div>
                <div class="xx_03 word_hidden">{address }</div>
            </div>
            <div class="tool_xx" >
            	<div class="num">
            	   {peoplenums}
            	人</div>
                <div class="{btn_color}">
                    {s_st}
                 </div>
            </div>
           </div>
        </li>

</script>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
</body>
</html>
