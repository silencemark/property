
//引入js
//document.write("<script language=javascript src='/js/Base64.js'></script>");

function ButtonappExit(){
	try{
		if(Labor!=null){
	    	Labor.appExit();
	  }else{
	    	appExit();
	    } 
	}catch(e){
		appExit();
	} 
}
function ButtonOrientate(url){
	try {
		if(Labor!=null){ 
			Labor.onOrientate(url);
	    }else{
	    	onOrientate(url);
	    } 
	} catch (e) {
		try{ 
			onOrientate(url);
		}catch(e){  
			 location.href = url;
		}
		
		
	}
	
}
/**
 * 扫描二维码
 */
function ButtonScanQRCode(){
	
	try{
		if(Labor!=null){
	    	Labor.ScanQRCode();
	    }else{
	    	ScanQRCode();
	    } 
	}catch(e){
		ScanQRCode();
	} 
}
/**
 * 通讯录
 * @param value
 * @param url
 */
function ButtonAddressBook(value,url){
	try{
		if(Labor!=null){
			Labor.AddressBook(value,url);
	    }else{
	    	AddressBook(value,url);
	    } 
	}catch(e){
		try{
			AddressBook(value,url);	
		}catch(e1){
			/*DJ_FUN.phone.showAlert("请在金童APP上打开",function(){
				location.href = url;
			})*/
			location.href = url;
		}
	} 
}
/**
 * 微信
 * @param type
 */
function ButtonaddWeixinPlatform(type){
	try{
		if(Labor!=null){
			Labor.addWeixinPlatform(type);
	    }else{
	    	addWeixinPlatform(type);;
	    } 
	}catch(e){
		try{
			addWeixinPlatform(type);
		}catch(w){
			location.href="https://open.weixin.qq.com/connect/qrconnect?appid=wx8ca7ffc0ee32a589&redirect_uri=http://www.ddjiaoren.com/api/wx/login.asp?type=app_bangding&response_type=code&scope=snsapi_login&state=state#wechat_redirect";	
		}
	} 
}
/**
 * 微博绑定
 * @param type
 */
function ButtonaddWeboPlatform(type){
	try{
		if(Labor!=null){
			Labor.addWeboPlatform(type);
	    }else{
	    	addWeboPlatform(type);;
	    } 
	}catch(e){
		try{
			addWeboPlatform(type);
		}catch(q){
			location.href="http://www.ddjiaoren.com/api/wb/App_SinaBindingLogin";
		}
	} 
}
/**
 * qq绑定
 * @param type
 */
function ButtonaddQQPlatform(type){
	try{
		if(Labor!=null){
			Labor.addQQPlatform(type);
	    }else{
	    	addQQPlatform(type);;
	    } 
	}catch(e){
		try{
			addQQPlatform(type);
		}catch(q){
			location.href="http://www.ddjiaoren.com/api/qq/login?type=app_bangding";
		}
	} 
}

/**
 * qq分享
 * @param title    标题
 * @param zhaiyao  摘要
 * @param url      分享路径
 * @param imgurl   图片路径
 */
function ButtonaddQQshore(title,zhaiyao,url,imgurl){
	if(url.indexOf("http://")==-1){
		url = "http://www.ddjiaoren.com/"+url;
	}
	if(imgurl.indexOf("http://")==-1){
		imgurl = "http://www.ddjiaoren.com/"+imgurl;
	}
	try{
		if(Labor!=null){
			Labor.addQQshore(title,zhaiyao,url,imgurl);
	    }else{
	    	addQQshore(title,zhaiyao,url,imgurl)
	    } 
	}catch(e){
		addQQshore(title,zhaiyao,url,imgurl)
	} 
}

/**
 * 微信分享好友
 * @param title    标题
 * @param zhaiyao  摘要
 * @param url      分享路径
 * @param imgurl   图片路径
 */
function goWXShareFriend(title,zhaiyao,url,imgurl){
	if(url.indexOf("http://")==-1){
		url = "http://www.ddjiaoren.com/"+url;
	}
	if(imgurl.indexOf("http://")==-1){
		imgurl = "http://www.ddjiaoren.com/"+imgurl;
	}
	try{
		if(Labor!=null){
			Labor.WXshareFriend(title,zhaiyao,url,imgurl);
	    }else{
	    	WXshareFriend(title,zhaiyao,url,imgurl)
	    } 
	}catch(e){
		WXshareFriend(title,zhaiyao,url,imgurl)
	} 
}


/**
 * 微信分享朋友圈
 * @param title    标题
 * @param zhaiyao  摘要
 * @param url      分享路径
 * @param imgurl   图片路径
 */
function goWXShareFriendCircle(title,zhaiyao,url,imgurl){
	if(url.indexOf("http://")==-1){
		url = "http://www.ddjiaoren.com/"+url;
	}
	if(imgurl.indexOf("http://")==-1){
		imgurl = "http://www.ddjiaoren.com/"+imgurl;
	}
	try{
		if(Labor!=null){
			Labor.WXshareFriendCircle(title,zhaiyao,url,imgurl);
	    }else{
	    	WXshareFriendCircle(title,zhaiyao,url,imgurl)
	    } 
	}catch(e){
		WXshareFriendCircle(title,zhaiyao,url,imgurl)
	} 
}
/**
 * 新浪微博分享
 * @param title    标题
 * @param zhaiyao  摘要
 * @param url      分享路径
 * @param imgurl   图片路径
 */
function ButtonaddSINAshore(title,zhaiyao,url,imgurl){
	alert(title+':'+zhaiyao+':'+url+':'+imgurl)
	if(url.indexOf("http://")==-1){
		url = "http://www.ddjiaoren.com/"+url;
	}
	if(imgurl.indexOf("http://")==-1){
		imgurl = "http://www.ddjiaoren.com/"+imgurl;
	}
	try{
		if(Labor!=null){
			Labor.addSINAshore(title,zhaiyao,url,imgurl);
	    }else{
	    	addSINAshore(title,zhaiyao,url,imgurl)
	    } 
	}catch(e){
		addSINAshore(title,zhaiyao,url,imgurl)
	} 
}
/**
 * 分享到圈子
 * @param title
 * @param zhaiyao
 * @param url
 * @param imgurl
 * @param identity
 */
function ButtonaddQZshore(title,zhaiyao,url,imgurl,identity){ 
	if(url.indexOf("http://")==-1){
		url = "http://www.ddjiaoren.com/"+url;
	} 
	var data = new Object();
	if(title==null || title==""){
		return ;
	}
	data.title = title;
	data.zhaiyao = zhaiyao;
	data.url = url;
	data.imgurl = imgurl;  
    $.ajax({
    	url:"/upload/encode",
		data:data,
		type:"post",
		success:function(data){
			if(identity=="hz"){//患者
				location.href = "/phone_dynamic/go_release?identity=hz&data="+data;
			}else{//其他
				location.href = "/phone_dynamic/go_release?identity=ys&data="+data;
			}
		},error:function(){
		
		}
	})  
}

//底部选中
function ButtonaddOnSelect(value,call){
	
	try{
		if("object" == typeof Labor && "function" == typeof (Labor.onSelect)){
			Labor.onSelect(value);
		}else if("function" == typeof onSelect){
			onSelect(value);
		} 
		if("function"==typeof call){
			call();
		}
	}catch(e){
		if("function"==typeof call){
			call();
		}
	} 
}

function showModel(){
	this.showAlert = function(s){
		var strings = {
				content:"提示内容",
				ico:"/phone/images/public/ico_correct.png",
				callback:function(){},
				falseCallBack:function(){}
		};
		strings = _this.extend(strings,s);
		var time = new Date().getTime(); 
		var tmpl = "";
		tmpl+='<div class="div_mask showAlert-'+time+'" onclick="$(\'.showAlert-'+time+'\').remove()"></div><!--半透明遮罩层-->';
		tmpl+='<div class="tc_correct showAlert-'+time+'" >';
		tmpl+='<div class="pic"><img src="'+strings.ico+'"></div>';
		tmpl+='<div class="txt">'+strings.content+'</div>';
		tmpl+='<div class="btn_box"><input type="button" id="showAlert_btn_'+time+'" value="确定" class="btn"></div>';
		tmpl+='</div>'; 
		$("body").append(tmpl);
		$("#showAlert_btn_"+time).click(function(){
			$(".showAlert-"+time).remove();
			if("function" == typeof strings.callback){
				strings.callback();
			}
		}); 
	}
	this.showConfirm = function(s){
		var strings = {
				content:"提示内容",
				ico:"/phone/images/public/ico_correct.png",
				callback:function(){},
				falseCallBack:function(){}
		};
		strings = _this.extend(strings,s);
		var time = new Date().getTime(); 
		var tmpl = "";
		tmpl+='<div class="div_mask showAlert-'+time+'" onclick="$(\'.showAlert-'+time+'\').remove()"></div><!--半透明遮罩层-->';
		tmpl+='<div class="tc_correct showAlert-'+time+'" >';
		tmpl+='<div class="pic"><img src="'+strings.ico+'"></div>';
		tmpl+='<div class="txt">'+strings.content+'</div>'; 
		tmpl+='<div class="btn_box">';
		
		tmpl+='<input type="button" id="showAlert_btn_ok_'+time+'" value="确定" class="btn" style="width: 49%; float: left;">';
		tmpl+='<input type="button" id="showAlert_btn_no_'+time+'" value="取消" class="btn" style="width: 49%; float: right; background: #D3D8D8; border: #D3D8D8; ">';
		tmpl+='<div class="clear"></div>';
		
		tmpl+='</div>'; 
		$("body").append(tmpl);
		$("#showAlert_btn_ok_"+time).click(function(){
			$(".showAlert-"+time).remove();
			if("function" == typeof strings.callback){
				strings.callback();
			}
		});
		$("#showAlert_btn_no_"+time).click(function(){
			$(".showAlert-"+time).remove();
			if("function" == typeof strings.falseCallBack){
				strings.falseCallBack();
			}
		}); 
		
	}
	this.showPrompt = function(s){
		
		var strings = {
				content:"请输入",
				ico:"/phone/images/public/ico_correct.png",
				callback:function(){},
				falseCallBack:function(){}
		};
		strings = _this.extend(strings,s);
		var time = new Date().getTime(); 
		var tmpl = "";
		tmpl+='<div class="div_mask showAlert-'+time+'" onclick="$(\'.showAlert-'+time+'\').remove()"></div><!--半透明遮罩层-->';
		tmpl+='<div class="tc_correct showAlert-'+time+'" >'; 
		tmpl+='<div class="txt" style="padding: 25px;"><textarea id="showPrompt_'+time+'" style="border: 1px solid #DAD4D4; width: 100%; height:80px" placeholder="'+strings.content+'"></textarea></div>'; 
		tmpl+='<div class="btn_box">';
		
		tmpl+='<input type="button" id="showAlert_btn_ok_'+time+'" value="确定" class="btn" style="width: 49%; float: left;">';
		tmpl+='<input type="button" id="showAlert_btn_no_'+time+'" value="取消" class="btn" style="width: 49%; float: right; background: #D3D8D8; border: #D3D8D8; ">';
		tmpl+='<div class="clear"></div>';
		
		tmpl+='</div>'; 
		$("body").append(tmpl);
		$("#showAlert_btn_ok_"+time).click(function(){
			var value =  $("#showPrompt_"+time).val();
			$(".showAlert-"+time).remove();
			if("function" == typeof strings.callback){
				strings.callback(value);
			}
		});
		$("#showAlert_btn_no_"+time).click(function(){
			var value =  $("#showPrompt_"+time).val();
			$(".showAlert-"+time).remove();
			if("function" == typeof strings.falseCallBack){
				strings.falseCallBack(value);
			}
		});
	}
	this.extend = function(oldbean,newbean){
		if("object" != typeof oldbean){
			oldbean = {};
		}
		if("object" != typeof newbean){
			newbean = {};
		}
		for(var i in newbean){//用javascript的for/in循环遍历对象的属性 
			if(newbean.hasOwnProperty(i)){
				oldbean[i] = newbean[i];
			} 
	    } 
		return oldbean;
	} 
	var _this = this; 
}

var show = new showModel();

/**
 * android 拨打电话
 * @param phone
 */
function dialTel(phone){
	try{
		Labor.DialTelephone(phone);
	}catch(e){
		 
	} 
}

/**
 * android 雇主工人首页切换
 * @param url
 */
function changeIndexView(url){
	
	try{
		Labor.EmployersAndWorkers(url);
	}catch(e){
		 
	} 
	
}



/**
* 获取用户设备id
* 
*/
function getDeviceChanneiID(){
	var obj;
	try{
		if(Labor!=null){
			obj=Labor.GetChannelId();
	    }else{
	    	obj=GetChannelId();
	    } 
	}catch(e){
		try{
		  obj=GetChannelId();
		}catch(m){
		   obj ="";
		}
	} 
	return obj;
}

/**
 * app 文本域判断不能输入表情
 * 如果返回false 提示错误信息
 * @param str
 */
function app_textArea(str){
	
	return /^[u4e00-\u9fa5\w,!:;""\/''，：；！‘’。.“”\s@#$%^&*()_+=！#￥……（）——+-~？?《》]*$/.test(str);
}

/**
 * android 上传
 * @param id
 * @param num
 */
function upload(id,num){
	try{
	if(Labor!=null){
		Labor.uploadFile(id,num);
	}
	}catch(e){
		
	}
}
function getBaiduLngLat(){
	try{
	if(Labor!=null){
		return Labor.getBaiduLngLat();
	}
	}catch(e){
		return "";
	}
}
function getapppay(prepayid,sign,partnerid){
	if(Labor!=null){
		Labor.todopay(prepayid,sign,partnerid);
	}
}

function wxpay(prepayId,amt,nonceStr,timeStamp,sign){
	if(Labor!=null){
		Labor.wxpay(prepayId,amt,nonceStr,timeStamp,sign)
	}
}
function goback(){
	location.href=successurl;
}
/**
 * 调用百度api 将gps坐标转换成百度坐标
 * @param xx 经度
 * @param yy 纬度
 * @param call 回掉函数
 */
function translateBaiDuJW(xx,yy,call){
	var gpsPoint = new BMap.Point(xx,yy);
	//坐标转换，地理坐标转换成平面坐标
    BMap.Convertor.translate(gpsPoint,0,translateCallback);
	//坐标转换完之后的回调函数
	function translateCallback(point){
	   console.log(point.lng + "," + point.lat);
	   if("function" == typeof call){
		   call(point);
	   }
	}
	 
}



/**
 * 登陆成功返回给ios用户id
 * ios
 * @param userId
 */
function returnUserId(userId){
	try{
	  getUserId(userId);
	}catch(e){
	}
}

/**
 * 注销登陆
 * ios 
 */
function exitIosApp(){
	try{
	   exitLogin();
	}catch(e){
  }
}

/**
 * 获取经纬度和用户设备信息
 * ios
 */
function getJingWeiAndChannelId(){
	try{
	    return jsGetDeviceInfo();
	}catch(e){
	  
	}
}


/**
 * 首页一级页面的返回
 * ios
 */
function returnHomeBack(){
	try{
		homeBackReturn();
	}catch(e){
	  
	}
}

/**
 * 其它按钮的一级页面的返回
 * ios
 */
function returnWebBack(){
	try{
		webBack();
	}catch(e){
	  
	}
}

/**
 * ios 上传图片
 * id:需要返回追加的div
 * num:最大上传数目
 */
function uploadImg(id,num){
 
	try{
    	uploadOnClick(id,num);
	}catch(e){
		
	}
}
 
/**
 * ios 图片放大
 * @param imgArray
 */
function imgLook (num,imgArray){
	try{
    	 imageLook(num,imgArray);
	}catch(e){
		
	}
}

/**
 * type=1 返回雇主首页
 * type=2 返回工人首页
 * @param type
 */
function returnHome(type){
	try{
	goGackSelect(type);
	}catch(e){
		
	}
}





