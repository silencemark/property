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

<title>支付佣金</title>
<link href="${basePath}appcssjs/style/public.css" type="text/css"
	rel="stylesheet">
<link href="${basePath}appcssjs/style/page.css" type="text/css"
	rel="stylesheet">
</head>
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
<script src="<%=request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>

<body>
<!--头部-->
<c:if test="${isWeiXinFrom==false}">
	<div class="headbox">
	  <div class="title f-20">支付佣金</div>
	   <a href="javascript:history.go(-1)" class="ico_back f-16">返回</a>
			<!-- <a href="#" class="ico_list">更多</a> -->
	</div>
</c:if>
<div class="pay_top">
	<div class="box">
    	<b><img src="<%=request.getContextPath() %>/appcssjs/images/page/ico_pay.png"></b>
        <span>
        	<i>需支付佣金</i>
            <em>${map.amount}元</em>
        </span>
        <div class="clear"></div>
    </div>
</div>
<div class="pay_box">
	<ul>
    	<li><b><img src="<%=request.getContextPath() %>/appcssjs/images/page/ico_pay01.png"></b><span>嘀嗒币支付<em>(余额：${membermap.tickcoin==null?0.00:membermap.tickcoin}币)</em></span><a href="javascript:void(0)" class="radio_ed" name="pay_div" onclick="changezhifu('1',this)">选择</a></li>
        <li><b><img src="<%=request.getContextPath() %>/appcssjs/images/page/ico_pay02.png"></b><span>微信支付</span><a href="javascript:void(0)" class="radio" name="pay_div" onclick="changezhifu('2',this)">选择</a></li>
        <!-- <li><b><img src="images/page/ico_pay03.png"></b><span>支付宝客户端</span><a href="javascript:void(0)" class="radio" name="pay_div" onclick="changezhifu('3',this)">选择</a></li> -->
    </ul>
</div>
<input type="hidden" id="type" value="1">
<input type="hidden" id="tickcoin" value="${membermap.tickcoin==null?0:membermap.tickcoin}"/>
<div class="main_bigbtn"><input type="button" onclick="callpay()" value="我要支付"></div>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
<script type="text/javascript">
var successurl="${successurl}";
function callpayback(data){
	if(data.success==1){
		swal({
				title : "",
				text : "支付成功!",
				type : "success",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				window.location.href="<%=request.getContextPath()%>/my/payclinchdeposit?type=2&out_trade_no=${appSignMap.out_trade_no}";
			});
	}else{
		swal("","支付失败！");
	}
}
	    function changezhifu(type,obj){
	    	$('a[name="pay_div"]').attr('class','radio');
	    	$(obj).attr('class','radio_ed');
	    	$('#type').val(type);
	    }
    	function callpay(){
    	if($('#type').val()=="2"){
    		if('${isWeiXinFrom}'=='false'){
    			wxpay("${appSignMap.prepay_id}","${map.amount}","${appSignMap.nonce_str}","${appSignMap.timestamp}","${appSignMap.sign}");
    		}else{
    			
		   		 WeixinJSBridge.invoke('getBrandWCPayRequest',{
		   	  		 "appId" : "${payMap.appid}",
		   	  		 "timeStamp" : "${payMap.timeStamp}",
		   	  		 "nonceStr" : "${payMap.nonceStr}",
		   	  		 "package" : "${payMap.packagevalue}",
		   	  		 "signType" : "MD5",
		   	  		 "paySign" : "${payMap.signvalue}" 
		   	   			},function(res){
		   					WeixinJSBridge.log(res.err_msg);
		//   	 				alert(res.err_code + res.err_desc + res.err_msg);
		   		            if(res.err_msg == "get_brand_wcpay_request:ok"){
		   		            	swal({
		   		    				title : "",
		   		    				text : "支付成功!",
		   		    				type : "success",
		   		    				showCancelButton : false,
		   		    				confirmButtonColor : "#ff7922",
		   		    				confirmButtonText : "确认",
		   		    				closeOnConfirm : true
		   		    			}, function(){
		   		    				window.location.href="<%=request.getContextPath()%>/my/payclinchdeposit?type=2&out_trade_no=${appSignMap.out_trade_no}";
		   		    			});
		   		            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){  
		   		            	swal("","用户支付取消！");
		   		            }else{
		   		            	swal("","支付失败！");
		   		            }  
		   				})
    			}
	    	}else{
	    		if($('#tickcoin').val() > 0){
	    			swal({
	    				title : "",
	    				text : "支付成功!",
	    				type : "success",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				window.location.href="<%=request.getContextPath()%>/my/payclinchdeposit?type=1&out_trade_no=${appSignMap.out_trade_no}";
	    			});
	    		}else{
	    			swal("","您的嘀嗒币不足,请选择其他支付方式!");
	    		}
	    		
	    	}
    	}
    </script>
</body>
</html>
