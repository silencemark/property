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
<title>评价</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script> 
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>  
</head>
<body name="homepage">
<!--头部-->
<c:if test="${isweixin == false }">
<div class="headbox">
  <div class="title f-20">评价</div>
    <a href="javascript:history.go(-1)" class="ico_back f-16">返回</a>
    <!-- <a href="#" class="ico_list">更多</a> -->
</div>
</c:if>
 <div class="control">
  <form  id="subForm" >
	<div class="eva_xx">
    	<span>评分</span>
        <b id="imgArray">
           <a href="javascript:void(0);"><img src="<%=request.getContextPath() %>/appcssjs/images/public/star2.png"></a>
           <a href="javascript:void(0);"><img src="<%=request.getContextPath() %>/appcssjs/images/public/star2.png"></a>
           <a href="javascript:void(0);"><img src="<%=request.getContextPath() %>/appcssjs/images/public/star2.png"></a>
           <a href="javascript:void(0);"><img src="<%=request.getContextPath() %>/appcssjs/images/public/star2.png"></a>
           <a href="javascript:void(0);"><img src="<%=request.getContextPath() %>/appcssjs/images/public/star2.png"></a>
        </b>
        <em>封杀</em>
        <i><a href="javascript:void(0);" id="isban_btn" class="radio" onclick="changeban(this)">选择</a></i>
        <input type="hidden" id="isban" value="0" name="isban"/>
        <input type="hidden" id="score" value="0" name="score"/>
        <input type="hidden" id="orderid" value="${map.orderid }" name="orderid"/>
        <input type="hidden" id="userid" value="${map.userid }" name="userid"/>
        <input type="hidden" id="evaluationerid" value="${map.evaluationerid }" name="evaluationerid"/>
    </div>
	<div class="text_area"><textarea id="description" placeholder="输入详细评价信息，10-200字" class="text_02"></textarea></div>
 </form>
</div>
<div class="main_bigbtn"><input type="button" onclick="subEvluate();" value="发表评价"></div>


<script type="text/javascript">
  function changeban(obj){
	  var isban=$(obj).val();
	  if(isban=='0'){
		  $(obj).val('1'); 
		  $(obj).attr('class','radio_ed');
	  }else{
		  $(obj).val('0');
		  $(obj).attr('class','radio');
	  }
  }
  $(function(){
	  var chge = "<%=request.getContextPath() %>/appcssjs/images/public/star.png";
	  var deft = "<%=request.getContextPath() %>/appcssjs/images/public/star2.png";
	  $("#imgArray img").each(function(index,item){
		   $(this).on({
			    mouseover:function(){ 
			    	$("#imgArray img:lt("+(index+1)+")").attr("src",chge);
			    	$("#score").attr("value",index+1);
			    },  
			    mouseout:function(){
			    	 $("#imgArray img:gt("+(index)+")").attr("src",deft);
			    	
			    }, 
			    click:function(){ 
			    	 $("#imgArray img:lt("+(index+1)+")").attr("src",chge);
			    	 $("#score").attr("value",index+1);
			    	 $("#imgArray img:gt("+(index)+")").attr("src",deft);
			    	 return;
			    }
			  }); 
	  }); 
	  $("#isban_btn").on({
	     click:function(){
	    	 if($(this).hasClass("radio")){
	    		 $(this).removeClass("radio").addClass("radio_ed");
	    		 $("#isban").val("1");
	    	 }else{
	    		 $(this).removeClass("radio_ed").addClass("radio");
	    		 $("#isban").val("0");
	    	 }
	    	 
	     }  
	  });
 
  });
  
  function subEvluate(){
	  if($("#score").val() == "0"){
		  swal({
				title : "",
				text : "请不要偷懒,要认真打评分哦!",
				type : "",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function() {
				 
			});
		 
		  return;
	  }
	  var description = $("#description").val();
	  if(description.length == 0 || description.length<10 || description.length >200){
		  swal({
				title : "",
				text : "评价信息内容在10-200字之间!",
				type : "",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function() {
				 
			});
		  return ;
	  }
	  var data = hhutil.getFormBean("subForm");
	      data.description=$("#description").val();
	  $.ajax({
		  type:"post",
		  url:"<%=request.getContextPath() %>/evaluate/addEvluate",
		  data:data,
		  success:function(dt){
			  if(dt){
				  swal({
						title : "",
						text : "发表评价成功!",
						type : "success",
						showCancelButton : false,
						confirmButtonColor : "#ff7922",
						confirmButtonText : "确认",
						closeOnConfirm : true
					},function(){
						  if('${map.type}' == 'employee'){
							  window.location.href="<%=request.getContextPath() %>/order/getEmployeeOrderList?type=2";
						  }else{
							  if('${map.jobid}' == ""){
								  window.location.href="<%=request.getContextPath() %>/employer/releasemapinfo?projectid=${map.projectid}&orderid=${map.oid}";
							  }else{
								  window.location.href="<%=request.getContextPath() %>/employer/workermapinfo?jobid=${map.jobid}&orderid=${map.oid}";
							  }
						  }
					});
			  }
			  
		  },error:function(txt){
			  swal({
					title : "",
					text : "发表评论失败，请稍后再试",
					type : "",
					showCancelButton : false,
					confirmButtonColor : "#ff7922",
					confirmButtonText : "确认",
					closeOnConfirm : true
				}, function() {
					 
				});
		  }
		  
	  });
	  
  }
  
</script>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
</body>
</html>
