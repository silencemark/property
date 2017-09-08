<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>我的消息</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/black/js/hhutil.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/page.js"></script>

</head>
<body name="information">
<!--头部-->
<c:if test="${isWeiXinFrom == false }">
<div class="headbox">
  <div class="title f-20">个人消息</div>
    <!-- <a href="javascript:history.go(-1)" class="ico_back f-16">返回</a> -->
    <!-- <a href="#" class="ico_list">更多</a> -->
</div>
</c:if>
<script type="text/javascript">
 
</script>
<div class="msg_list">
	<ul id="notice_ul">
	  <c:forEach items="${dataList }" var ="row" varStatus="t">
        <li onclick="changeStatus('${row.id}','${row.isread}','${row.types }',this);">
        	<div class="xx_01">
        	  <div class="box"  onclick="showContent(this);">
	        	  <span><i class="${row.isread ==0?'yellow':'' }">[${row.isread ==0?'未读':'已读' }]</i>${row.title }</span>
	        	  <em style="width:auto;"><fmt:formatDate value="${row.createtime }" pattern="MM-dd HH:mm"/></em>
	          </div>
	          <a href="javascript:void(0);" onclick="delMessage('${row.id}','${row.types}')" class="del">删除</a>
	        </div>
	          
            <div class="nr_box" <c:if test="${row.url != null and row.url != '' }">onclick="javascript:window.location.href='${row.url }'"</c:if> style="display: none"><p>${row.content }</p></div>
        </li>
       </c:forEach> 
    </ul>
</div>
<script type="text/javascript">

 function showContent(obj){
	 if($(obj).parent().parent().find(".nr_box").attr("style").match("none")){
		 $(obj).parent().parent().find(".nr_box").css("display","block");
		 $(obj).parent().parent().siblings().find(".nr_box").css("display","none");
		 $(obj).css("margin-left","-56px");
		 $(obj).parent().parent().siblings().find(".box").css("margin-left","0px");
	 }else{
		 $(obj).parent().parent().find(".nr_box").css("display","none");
		 $(obj).css("margin-left","0px");
		 
	 }
	 
 }
  
function  delMessage(id,type){
	swal({
		title : "",
		text : "您确定要删除该消息!",
		type : "info",
		showCancelButton : true,
		confirmButtonColor : "#ff7922",
		confirmButtonText : "确认",
		cancelButtonText:"取消",
		closeOnConfirm : true
	}, function() {
		   
		$.ajax({
			type:"post",
			url:"<%=request.getContextPath()%>/notice/delMessage",
			data:{id:id,type:type,delflag:'1'},
			success:function(data){
				if(data){
					
					window.location.reload(true);
				}
				
			},error:function(txt){
				swal({
					title : "",
					text : "操作失败,请稍后再试!",
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
 
 function changeStatus(id,isread,type,obj){
	 if(isread == '0'){
		 $.ajax({
				type:"post",
				url:"<%=request.getContextPath()%>/notice/delMessage",
				data:{id:id,type:type,isread:'1'},
				success:function(data){
					if(data){
					   $(obj).find("i").html("已读").removeClass("yellow");
					}
					
				}
			});
	 }
 }

 PageHelper({
		url:"<%=request.getContextPath() %>/notice/inintMessageAjax",
		data:{userid:"${userid}"},
		success:function(data){
			var html = $("#tmpl_li").html();
			var datalist = data.dataList;
			for(var i=0;datalist.length>i;i++){
				var d = datalist[i]; 
				 if(d.createtime !=null){
						d.createtime = hhutil.parseDate(d.createtime, "MM-DD hh:mm");
					}
				    if(d.content == null){
				    	d.content="";
				    }
				    d.cls="";
				    d.info="已读";
				    if(d.isread+"" == "0"){
				    	d.cls="yellow";
				    	d.info="未读";
				    } 
				    d.clk="";
				    if(d.url != null && d.url != ''){
				    	d.clk="javascript:window.location.href=\'"+d.url+"\'";	
				    }
				    
				  $("#notice_ul").append(hhutil.replace(html,datalist[i])).trigger("create");
			} 
		}
	});  				

</script>
<script type="text/temple" id="tmpl_li">
   <li onclick="changeStatus('{id}','{isread}','{types}',this);">
        	<div class="xx_01">
        	  <div class="box"  onclick="showContent(this);">
	        	  <span><i class="{cls}">[{info}]</i>{title }</span>
	        	  <em style="width:auto;">{createtime }</em>
	          </div>
	          <a href="javascript:void(0);" onclick="delMessage('{id}','{types}')" class="del">删除</a>
	        </div>
            <div class="nr_box" onclick="{clk}" style="display: none"><p>{content }</p></div>
        </li>
</script>


<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
</body>
</html>
