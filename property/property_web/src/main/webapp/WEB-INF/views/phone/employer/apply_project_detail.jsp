<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/head/base.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>项目报价详情</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
</head>
<body name="homepage">
<script type="text/javascript">
   function turnBack(){
	   var userAgentInfo = navigator.userAgent;
	   if(userAgentInfo.indexOf("iPhone") > 0  && '${param.resource}' != ''){
		   returnHomeBack();
	   }else{
		   window.history.go(-1);
	   }
   }
</script>
<!--头部-->
<c:if test="${isWeiXinFrom==false}">
	 <div class="headbox">
		<div class="title f-20">项目报价详情</div>
	    <a href="javascript:void(0);" onclick="turnBack()" class="ico_back f-16" >返回</a>
	   <!--  <a onclick="showShare()" class="ico_list">更多</a> -->
	</div>
</c:if>
<div class="order_detail" name="homepage">
	<ul>
    	<li><b>项目信息</b><div class="clear"></div></li>
    	<li><span>项目标题：</span><i>${applydetailmap.projecttitle}</i><div class="clear"></div></li>
    	<li><span>订单单号：</span><i>${applydetailmap.orderno}</i><div class="clear"></div></li>
    	<li><span>行业类别：</span><i>${applydetailmap.jobtypename}</i><div class="clear"></div></li>
        <li><span>项目周期(开始)：</span><i style="width:50%"><fmt:formatDate value="${applydetailmap.starttime}" pattern="yyyy-MM-dd HH:mm"/></i><div class="clear"></div></li>
        <li><span>项目周期(结束)：</span><i style="width:50%"><fmt:formatDate value="${applydetailmap.endtime}" pattern="yyyy-MM-dd HH:mm"/></i><div class="clear"></div></li>
        <li><span>工作地区：</span><i>${applydetailmap.provincename} ${applydetailmap.cityname}</i><div class="clear"></div></li>
        <li><span>详细地址：</span><i>${applydetailmap.address}</i><div class="clear"></div></li>
        <li><span>项目描述：</span><i>${applydetailmap.projectdescription }</i><div class="clear"></div></li>
        <li><span>联系人：</span><i onclick="tel('${applydetailmap.contacter}')">${applydetailmap.contacter}</i><div class="clear"></div></li>
        <li><span>联系方式：</span><i onclick="tel('${applydetailmap.telephone}');">${applydetailmap.telephone}</i><div class="clear"></div></li>
        <li class="last"><span>公司名称：</span><i>${applydetailmap.companyname}</i><div class="clear"></div></li>
    </ul>
</div>
<div class="order_detail mar_top">
	<ul>
    	<li><b>报价信息</b><div class="clear"></div></li>
    	<li><span>项目总报价：</span><i>￥${applydetailmap.quotation}</i><div class="clear"></div></li>
        <li><span>报价概述：</span><div class="clear"></div></li>
        <li><div class="text_area"><p>${applydetailmap.quotationdescription}</p></div></li>
        <li class="last"><span>报价状态：</span><i class="yellow">
        	 <c:choose>
            	    <c:when test="${applydetailmap.status == 1 }">待处理</c:when>
            	    <c:when test="${applydetailmap.status == 2 }">已取消</c:when>
            	    <c:when test="${applydetailmap.status == 3 }">已成交</c:when>
            	    <c:when test="${applydetailmap.status == 4 }">暂不合适</c:when>
            	    <c:when test="${applydetailmap.status == 5 }">工人取消</c:when>
            	    <c:when test="${applydetailmap.status == 6 }">雇主同意</c:when>
            	    <c:when test="${applydetailmap.status == 7 }">雇主拒绝</c:when>
            	    <c:when test="${applydetailmap.status == 8 }">雇主取消</c:when>
            	    <c:when test="${applydetailmap.status == 9 }">工人同意</c:when>
            	    <c:when test="${applydetailmap.status == 10 }">工人拒绝</c:when>
            	    <c:when test="${applydetailmap.status == 11 }">已过期</c:when>
            	    <c:when test="${applydetailmap.status == 12 }">已被抢走</c:when>
             </c:choose>
         </i>
         <div class="clear"></div></li>
    </ul>
</div>
<c:if test="${applydetailmap.status == 8 }">
<div class="double_btn">
	<span><a href="javascript:void(0);" onclick="sureCancel('9','${param.applyorderid}','${applydetailmap.orderid}')"  class="bg_green">同意取消</a></span>
    <span><a href="javascript:void(0);" onclick="sureCancel('10','${param.applyorderid}','${applydetailmap.orderid}')" class="bg_yellow">不同意取消</a></span>
</div>
</c:if>
<c:if test="${isWeiXinFrom==true}">
	<a href="javascript:void(0);" class="share_btn" style="width:80px" onclick="$('#div_mask').show();$('#shareimg').show();"><i>分享</i></a>
</c:if>
<c:if test="${isWeiXinFrom==false}">
	<a href="javascript:void(0);" class="share_btn" style="width:80px" onclick="showShare();"><i>分享</i></a>
</c:if>
<style type="text/css">
.tc_sharets { position:fixed; top:0px; left:0px; width:100%; z-index:9999;}
.tc_sharets .close_btn { padding:12px 15px 0px 15px;}
.tc_sharets .img { text-align:right;}
</style>

<div class="div_mask" style="display: none;opacity: 0.8" id="div_mask" onclick="$('#div_mask').hide();$('#shareimg').hide();"></div>
<div class="tc_sharets"  style="display: none" id="shareimg">
	<div class="close_btn" onclick="$('#div_mask').hide();$('#shareimg').hide();"><a href="javascript:void(0)"><img src="<%=request.getContextPath() %>/upload/btn_close01.png"></a></div>
    <div class="img" onclick="$('#div_mask').hide();$('#shareimg').hide();"><img src="<%=request.getContextPath() %>/upload/share_ts.png"></div>
</div>
<div class="share_box" style="display: none;" id="sharebox">
   	<ul>
    	<li><a href="javascript:shareFriend()"><img src="<%=request.getContextPath() %>/appcssjs/images/public/share_ico01.png"></a><span>发送给朋友</span></li>
        <li><a href="javascript:shareFriendCircle();"><img src="<%=request.getContextPath() %>/appcssjs/images/public/share_ico02.png"></a><span>分享到朋友圈</span></li>
    </ul>
</div>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
function tel(phone){
	   var userAgentInfo = navigator.userAgent;   
	   if (userAgentInfo.indexOf("Android") > 0 && '${isWeiXinFrom}' == 'false' ){
		   dialTel(phone);
	   }
	   
}
function sureCancel(status,applyorderid,orderid){
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
				data:{status:status,applyorderid:applyorderid,orderid:orderid},
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


var signature = '${shareMap.signature}';
if(location.href.indexOf("//system")>=0){
	signature = '${shareMap.signature2}';
}
//通过config接口注入权限验证配置
wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: '${shareMap.appId}', // 必填，公众号的唯一标识
    timestamp: '${shareMap.timestamp}', // 必填，生成签名的时间戳
    nonceStr: '${shareMap.nonceStr}', // 必填，生成签名的随机串
    signature: signature,// 必填，签名，见附录1
    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage','hideMenuItems'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
}); 
//ready 接口处理成功
wx.ready(function(){
	var context = "我正在嘀嗒叫人应聘${applydetailmap.jobtype}的${applydetailmap.jobtitle}，快来和我一起抢单吧";
	//分享给朋友
	//title:分享的标题；link分享的链接；imgUrl分享的图标
 	wx.onMenuShareAppMessage({
 	    title: '嘀嗒叫人',
 	    desc: context,
 	    link:  "<%=Constants.PROJECT_PATH%>employer/applyProjectDetail?applyorderid=${applydetailmap.applyorderid}",
 	    imgUrl:  "<%=Constants.PROJECT_PATH%>upload/didalogo.png",
 	    success: function () {
 	    	//添加嘀嗒币
 	    	$.ajax({
 	    		type:'post',
 	    		dataType:'json',
 	    		url:'<%=request.getContextPath()%>/employer/givesharecoin?pagename=项目报价详情&sharesourceid=${applydetailmap.applyorderid}',
 	    		success:function(){
 	    		}
 	    	});
 	    }
 	});

	//分享给朋友圈
	//title:分享的标题；link分享的链接；imgUrl分享的图标
 	wx.onMenuShareTimeline({
 		title: context,
 		link:  "<%=Constants.PROJECT_PATH%>employer/applyProjectDetail?applyorderid=${applydetailmap.applyorderid}",
 	    imgUrl:  "<%=Constants.PROJECT_PATH%>upload/didalogo.png",
 	    success: function () {
 	    	$.ajax({
 	    		type:'post',
 	    		dataType:'json',
 	    		url:'<%=request.getContextPath()%>/employer/givesharecoin?pagename=项目报价详情&sharesourceid=${applydetailmap.applyorderid}',
 	    		success:function(){
 	    		}
 	    	});
 	    }
    });
 	//============
});
function showShare(){
	$('#sharebox').css('display','block');
}
var zhaiyao = "我正在使用嘀嗒叫人，快来和我一起吧!";
var shareTitle = "嘀嗒叫人";

var sharelink = "<%=Constants.PROJECT_PATH%>employer/applyProjectDetail?applyorderid=${param.applyorderid}";
var shareimgUrl = "<%=Constants.PROJECT_PATH%>upload/didalogo.png";
function shareFriend(){
	
	
	$('#sharebox').hide();
	goWXShareFriend(shareTitle,zhaiyao,sharelink,shareimgUrl);
}
function shareFriendCircle(){
	$('#sharebox').hide();
	goWXShareFriendCircle(shareTitle,zhaiyao,sharelink,shareimgUrl);
}
</script>
</body>
</html>