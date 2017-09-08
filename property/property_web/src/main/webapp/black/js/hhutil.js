/**
 * 宏恒Js工具类
 * 
 * **/
var hhutil = {
		/**
		 * 替换
		 */
		replace:function(tmpl, bean){
 			for(var i in bean){//用javascript的for/in循环遍历对象的属性 
 				if(bean.hasOwnProperty(i)){
	 				var regExp=new RegExp("{[ ]*"+i+"[ ]*}","gmi");
	 				while(true){
	 					tmpl = tmpl.replace(regExp,bean[i]);
	 					if(!regExp.test(tmpl)){
	 						break;
	 					}
	 				}
	 				
 				} 
 			} 
 			return tmpl;
 		},
		/**
		 * 获取get参数
		 * key为参数名
		 * 如果key为null返回所有值 
		 */
		GetRequest:function(key) { 
			var url = location.search; //获取url中"?"符后的字串 
			var theRequest = new Object(); 
			if (url.indexOf("?") != -1) { 
			var str = url.substr(1); 
			strs = str.split("&"); 
			for(var i = 0; i < strs.length; i ++) { 
				theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
				} 
			}
			if(key==null){
				return theRequest;
			}else{
				return theRequest[key];
			}
		},
		getLocalhostPath:function(){
		    /*//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
		    var curWwwPath=window.document.location.href;
		    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		    var pathName=window.document.location.pathname;
		    var pos=curWwwPath.indexOf(pathName);
		    //获取主机地址，如： http://localhost:8080
		    var localhostPath=curWwwPath.substring(0,pos);
		    //获取带"/"的项目名，如：/nnjssd
		    localhostPath+=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		    return localhostPath;*/
			var projectName=$('#projectName').val();
			return projectName;
//		    return(localhostPath+projectName);
		},
		
		/**
		 * ajaxFileUpload 异步上传文件
		 * @url ajax请求的地址
		 * @fileElementId file 的ID
		 * @callback 回调函数，用于处理请求后的业务逻辑
		 */
		 ajaxFileUpload:function(url,fileElementId,callback) {  
            $.ajaxFileUpload ( {  
                url: url,  
                secureuri: false,  
                fileElementId: fileElementId,  
                dataType: 'html',  
                beforeSend: function() {  
                    $("#loading").show();  
                },  
                complete: function() {  
                    $("#loading").hide();  
                },  
                success: function(data) {  
                	if ("function" == typeof callback){
                		if("object" == typeof data){
							callback(data);
						}else{
							callback(JSON.parse(data));
						}
                	}   
                },  
                error: function(data,e) {  
                	if ("function" == typeof callback){
                		if("object" == typeof data){
                			
                			var responseText = data.responseText;
                			var url = data.responseText.substring(responseText.indexOf('<pre style="word-wrap: break-word; white-space: pre-wrap;">')+'<pre style="word-wrap: break-word; white-space: pre-wrap;">'.length,responseText.lastIndexOf("</pre>"));
                			data = JSON.parse(url);
                           
							callback(data,e);
						}else{
							callback(JSON.parse(data),e);
						}
                	}
                }  
            })
            
        },
		
		/**
		 * Ajax 异步请求json数据
		 * @url ajax请求的地址
		 * @data ajax 请求的参数
		 * @callback 回调函数，用于处理请求后的业务逻辑
		 */
		ajax : function (url,data,callback){
			$.ajax({
					url: url,
					type: "post",
					dataType: 'json',
					data:this.getDataJson(data),
					timeout: 100000,
					contentType:'application/json;charset=UTF-8',
					beforeSend : function(XMLHttpRequest){
						//发送请求前
//						alert('before');
					},
					success: function(data) {
						if ("function" == typeof callback)
							if("object" == typeof data){
								callback(data);
							}else{
								callback(JSON.parse(data));
							}
							
					},
					error: function(data,e){
						if ("function" == typeof callback){
                		if("object" == typeof data){
                			callback(data,e);
						}else{
							callback(JSON.parse(data),e);
						}
                	}
			        }
				});
			
		},
		
		getDataJson : function(formBean){
			return JSON.stringify(formBean);
		},
		
		//自动封装form
		getFormBean : function(formID){
			var tempData = $('#'+formID).serializeArray();
			var item = new Object() ;
			if(null!=tempData&&''!=tempData&&typeof(tempData)!='undefined'){
				$.each(tempData, function(i, field){
					item[field.name]=field.value;
				});
			}
			return item;
		},
		getFormNameBean : function(formID){
			var tempData = $('form[name="'+formID+'"]').eq(0).serializeArray();
			var item = new Object() ;
			if(null!=tempData&&''!=tempData&&typeof(tempData)!='undefined'){
				$.each(tempData, function(i, field){
					item[field.name]=field.value;
				});
			}
			return item;
		},
		
		//判断是否为空
		isEmpty : function(value){
			return (null === value || "" === value || undefined === value);
		},
		
		getRootPath : function(){
		    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
		    var curWwwPath=window.document.location.href;
		    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		    var pathName=window.document.location.pathname;
		    var pos=curWwwPath.indexOf(pathName);
		    //获取主机地址，如： http://localhost:8080
		    var localhostPath=curWwwPath.substring(0,pos);
		    //获取带"/"的项目名，如：/nnjssd
		    return localhostPath;
		},
		
		parseDate : function(datetime,format){
			if(!this.isEmpty(datetime)){
				var date = new Date(datetime);
				if(format){
					return date.format(format);
				}else{
					return date.format("YYYY-MM-DD");
				}
			}
			return null ;
		},
		parseDateMinute : function(datetime){
			if(!this.isEmpty(datetime)){
				var date = new Date(datetime);
				return date.format("YYYY-MM-DD hh:mm");
			}
			return null ;
		},
		checkMobile : function(value){
			//验证手机号码
			var reg = /^(((13[0-9]{1})|(12[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1})|)+\d{8})$/;
            return value.length == 11 && reg.test(value);
		},
		checkEmail : function(value){
		    var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
		    return pattern.test(value);
		}
};

Date.prototype.format = function(fmt) { //author: meizz 
	var o = {
		"M+": this.getMonth() + 1, //月份 
		"D+": this.getDate(), //日 
		"h+": this.getHours(), //小时 
		"m+": this.getMinutes(), //分 
		"s+": this.getSeconds(), //秒 
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		"S": this.getMilliseconds() //毫秒 
	};
	if (/(Y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}