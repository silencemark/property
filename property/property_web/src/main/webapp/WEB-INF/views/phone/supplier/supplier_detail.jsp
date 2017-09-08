<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.qiniu.util.AuthConstant"%>  
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>供应商详情</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/employer.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/js/mobiscrol/css/mobiscroll.custom-2.6.2.min.css" type="text/css" rel="stylesheet">  
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/index.js"></script>
</head>

<body name="supplier">
<script type="text/javascript">
   function turnBack(){
	   var userAgentInfo = navigator.userAgent;
	   if(userAgentInfo.indexOf("iPhone") > 0 ){
		   returnWebBack(); 
	   }else{
		   window.history.go(-1);
	   }
   }
</script>
<!--头部-->
<c:if test="${isWeiXinFrom==false}">
	 <div class="headbox">
		<div class="title f-20">供应商详情</div>
	    <a href="javascript:void(0)" class="ico_back f-16" onclick="turnBack()">返回</a>
	    <!-- <a onclick="showShare()" class="ico_list">更多</a> -->
	</div>
</c:if>
<div class="xx_detail">
	<div class="name"><span>基本资料</span></div>
    <div class="xx_box xx_pad">
        <p><b>供应商名称：</b><span>${suppliermap.suppliername}</span></p>
        <p><b>营业地址：</b><span>${suppliermap.address}</span></p>
        <p><b>营业时间：</b><span>${suppliermap.businesshours}</span></p> 
        <p><b>联系人：</b><span>${suppliermap.contacter}</span></p>
        <p><b>联系电话：</b><span onclick="tel('${suppliermap.telephone}')">${suppliermap.telephone}</span></p>
    </div>
    <div class="name"><span>主营产品</span></div>
    <div class="p_box">${suppliermap.products}</div>
    <div class="name"><span>店面图片</span></div>
    <div class="pic_box">
    	<c:forEach items="${phonelist}" var="phone1">
    		<c:if test="${phone1.type==2}">
    			<span><img onclick="imgutil.FDIMG(this)" style="height:100%" src="${phone1.url}"></span>
    		</c:if>
    	</c:forEach>
        <div class="clear"></div>
    </div>
    <div class="name"><span>产品图片</span></div>
    <div class="pic_box">
    <c:forEach items="${phonelist}" var="phone1">
    	<c:if test="${phone1.type==1}">
    		<span><img onclick="imgutil.FDIMG(this)" style="height:100%" src="${phone1.url}"></span>
    	</c:if>
    </c:forEach>
        <div class="clear"></div>
    </div>
    <div class="name"><span>其他资料</span></div>
    <div class="xx_box xx_pad">
        <p><b>分享次数：</b><span id="sharecount">${suppliermap.sharetimes==null?0:suppliermap.sharetimes}</span></p>
        <p><b>阅读次数：</b><span id="readcount">${suppliermap.readtimes==null?0:suppliermap.readtimes}</span></p>
        <p><b>外链地址：</b><span>${suppliermap.outsideurl}</span></p>
    </div>
</div>
<c:if test="${isWeiXinFrom==true}">
	<a href="javascript:void(0);" class="share_btn02" onclick="$('#div_mask').show();$('#shareimg').show();"><i>分享</i></a>
</c:if>
<c:if test="${isWeiXinFrom==false}">
	<a href="javascript:void(0);" class="share_btn" style="width:80px" onclick="showShare();"><i>分享</i></a>
</c:if>
<a href="<%=request.getContextPath() %>/public/developer.jsp?longitude=${suppliermap.lon}&latitude=${suppliermap.lat}&suppliername=${suppliermap.suppliername}" class="look_map"><i>查看地图</i></a>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->

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
<div class="share_box" style="display: none;" id="suppliersharebox">
   	<ul>
    	<li><a href="" onclick="suppliershareFriend()"><img src="<%=request.getContextPath() %>/appcssjs/images/public/share_ico01.png"></a><span>发送给朋友</span></li>
        <li><a href="" onclick="suppliershareFriendCircle()"><img src="<%=request.getContextPath() %>/appcssjs/images/public/share_ico02.png"></a><span>分享到朋友圈</span></li>
    </ul>
</div>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
function tel(phone){
	   var userAgentInfo = navigator.userAgent;   
	   if (userAgentInfo.indexOf("Android") > 0 && '${isWeiXinFrom}' == 'false' ){
		   dialTel(phone);
	   }
	   
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
	var context = "我正在使用嘀嗒叫人的供应商：${suppliermap.suppliername}，快来和我一起吧!";
	//分享给朋友
	//title:分享的标题；link分享的链接；imgUrl分享的图标
 	wx.onMenuShareAppMessage({
 	    title: '嘀嗒叫人',
 	    desc: context,
 	    link:  "<%=Constants.PROJECT_PATH%>weixin/supplier/getsupplierdetail?supplierid=${suppliermap.supplierid}",
 	    imgUrl:  "<%=Constants.PROJECT_PATH%>/upload/didalogo.png",
 	    success: function () {
 	    	$.ajax({
 	    		type:'post',
 	    		dataType:'json',
 	    		url:'<%=request.getContextPath()%>/employer/givesharecoin?pagename=供应商详情&sharesourceid=${suppliermap.supplierid}&supplierid=${suppliermap.supplierid}',
 	    		success:function(){
 	    			$('#sharecount').text(parseInt($('#sharecount').text())+1);
 	    		}
 	    	});
 	    }
 	});
	//分享给朋友圈
	//title:分享的标题；link分享的链接；imgUrl分享的图标
 	wx.onMenuShareTimeline({
 		title: context ,
 		link:  "<%=Constants.PROJECT_PATH%>weixin/supplier/getsupplierdetail?supplierid=${suppliermap.supplierid}",
 	    imgUrl:  "<%=Constants.PROJECT_PATH%>/upload/didalogo.png",
 	    success: function () {
 	    	$.ajax({
 	    		type:'post',
 	    		dataType:'json',
 	    		url:'<%=request.getContextPath()%>/employer/givesharecoin?pagename=供应商详情&sharesourceid=${suppliermap.supplierid}&supplierid=${suppliermap.supplierid}',
 	    		success:function(){
 	    			$('#sharecount').text(parseInt($('#sharecount').text())+1);
 	    		}
 	    	});
 	    }
    });
 	//============
});
function showShare(){
	$('#suppliersharebox').css('display','block');
}
var zhaiyao = "我正在使用嘀嗒叫人的供应商：${suppliermap.suppliername}，快来和我一起吧!";
var shareTitle = "嘀嗒叫人";

var sharelink = "<%=Constants.PROJECT_PATH%>weixin/supplier/getsupplierdetail?supplierid=${suppliermap.supplierid}";
var shareimgUrl = "<%=Constants.PROJECT_PATH%>/upload/didalogo.png";
function suppliershareFriend(){
 
	$.ajax({
 		type:'post',
 		dataType:'json',
 		url:'<%=request.getContextPath()%>/employer/givesharecoin?pagename=供应商详情&sharesourceid=${suppliermap.supplierid}&supplierid=${suppliermap.supplierid}',
 		success:function(data){
 			$('#sharecount').text(parseInt($('#sharecount').text())+1);
 			//goWXShareFriend(shareTitle,zhaiyao,sharelink,shareimgUrl);		
 		}
 	});
	 
	$('#suppliersharebox').hide();
	goWXShareFriend(shareTitle,zhaiyao,sharelink,shareimgUrl);
}
function suppliershareFriendCircle(){
	
	$.ajax({
 		type:'post',
 		dataType:'json',
 		url:'<%=request.getContextPath()%>/employer/givesharecoin?pagename=供应商详情&sharesourceid=${suppliermap.supplierid}&supplierid=${suppliermap.supplierid}',
 		success:function(data){
 			$('#sharecount').text(parseInt($('#sharecount').text())+1);
 			//goWXShareFriendCircle(shareTitle,zhaiyao,sharelink,shareimgUrl);		
 		}
 	});
 	
	$('#suppliersharebox').hide();
	goWXShareFriendCircle(shareTitle,zhaiyao,sharelink,shareimgUrl);
}
</script>
</body>
</html>
