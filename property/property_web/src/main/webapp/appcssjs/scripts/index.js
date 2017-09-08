 /**\
  * 图片放大
  */
var imgutil = {
		FDIMG:function(dom){
			    var src = $(dom).attr( "src" );
			    var img=new Image();
				 img.src=src;
			     var width=img.width;
			     var height=img.height;//图片大小
				 if(width == 0 && height == 0){
					 return;
				 }
		          imgutil.isPC(function(flg){
		        	  imgutil.alt(function(data){
						  var winHeight =  data.height;
						  var winWidth =   data.width ;
						   if(flg){
							/*   if(width > winWidth){
						        	 width = (winWidth)*0.8; 
						        }
						      if(height>winHeight){
						        	 height = (winHeight)*0.8;
						        }*/
					          imgutil.AutoResizeImage(width, height, winWidth, winHeight*0.98,function(o){
					        	  
					        	  width = o.w;
					        	  height = o.h;
					        	   
					           });
					        	 
						   }else{
						         /*if(width > winWidth){
						        	 width = winWidth; 
						         }
						         if(height>winHeight){
						        	 height = winHeight;	 
						         }*/
							   imgutil.AutoResizeImage(width, height, winWidth, winHeight,function(o){
						        	  
						        	  width = o.w;
						        	  height = o.h;
						        	   
						           });
						  }

						     var top  = ((winHeight-height)/2);
							 var left = ((winWidth-width)/2);
							 //var top  = 0;
							 //var left = 0;
							
							/*var  html='<div onclick="$(\'.carrousel\').remove();" style="width:'+width+';height:'+height+';top:'+top+',left:'+left+';display:block" class="carrousel"> <span class="close entypo-cancel"></span>'; 
							     html+='<div class="wrapper"> <img align="center" src="'+src+'"/> </div>';
							     html+='</div>';*/
							
						     var img_model = new Date().getTime()+"img";
						     var html = 
										'<div id="win_PMG">'+
										'<div onclick="$(\'#win_PMG\').remove();" style="width: 100%; height: 100%; opacity:0.8; position: fixed; top:0px; background-color: #333; z-index: 9000">'+
										'</div>'+
										'<img onclick="$(\'#win_PMG\').remove();" id="'+img_model+'" src="'+src+'"    align="center" style="position: fixed; top:'+top+'px; left:'+left+'px; z-index: 9001; width: '+width+'px;height: '+height+'px">'+
										'</div>';
						     $("body").append(html);
						  
					  });  
		        	  
		          });
				 
		        
		},AutoResizeImage : function (w,h,maxWidth,maxHeight,call){
			var hRatio;
			var wRatio;
			var Ratio = 1;
			wRatio = maxWidth / w;
			hRatio = maxHeight / h;
			if (maxWidth ==0 && maxHeight==0){
			Ratio = 1;
			}else if (maxWidth==0){//
			if (hRatio<1) Ratio = hRatio;
			}else if (maxHeight==0){
			if (wRatio<1) Ratio = wRatio;
			}else if (wRatio<1 || hRatio<1){
			Ratio = (wRatio<=hRatio?wRatio:hRatio);
			}
			if (Ratio<1){
			w = w * Ratio;
			h = h * Ratio;
			}
			var co = {
					w:w,
					h:h
			};
			call(co);
			 
		},alt:function(call){
			 var s = "";
			    s += " 网页可见区域宽："+ document.body.clientWidth+"\n";
			    s += " 网页可见区域高："+ document.body.clientHeight+"\n";
			    s += " 网页可见区域宽："+ document.body.offsetWidth + " (包括边线和滚动条的宽)"+"\n";
			    s += " 网页可见区域高："+ document.body.offsetHeight + " (包括边线的宽)"+"\n";
			    s += " 网页正文全文宽："+ document.body.scrollWidth+"\n";
			    s += " 网页正文全文高："+ document.body.scrollHeight+"\n";
			    s += " 网页被卷去的高(ff)："+ document.body.scrollTop+"\n";
			    s += " 网页被卷去的高(ie)："+ document.documentElement.scrollTop+"\n";    
			    s += " 网页被卷去的左："+ document.body.scrollLeft+"\n";
			    s += " 网页正文部分上："+ window.screenTop+"\n";
			    s += " 网页正文部分左："+ window.screenLeft+"\n";      
			    s += " 屏幕分辨率的高："+ window.screen.height+"\n";
			    s += " 屏幕分辨率的宽："+ window.screen.width+"\n";
			    s += " 屏幕可用工作区高度："+ window.screen.availHeight+"\n";
			    s += " 屏幕可用工作区宽度："+ window.screen.availWidth+"\n";
			    s += " 你的屏幕设置是 "+ window.screen.colorDepth +" 位彩色"+"\n";
			    s += " 你的屏幕设置 "+ window.screen.deviceXDPI +" 像素/英寸"+"\n";
			    s += " window的页面可视部分实际高度(ff) "+window.innerHeight+" ";    
			    /*alert (s);*/
			    var data={
			    	width:window.innerWidth ,
			    	height:window.innerHeight
			    }
			    call(data);
		},isPC:function(call){//true pc
            var userAgentInfo = navigator.userAgent; 
            var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"); 
            var flag = true; 
            for (var v = 0; v < Agents.length; v++) { 
                if (userAgentInfo.indexOf(Agents[v]) > 0) { flag = false; break; } 
            } 
            call(flag);
        }
		
}
 
/* $(function(){
	 var carrousel = $( ".carrousel" );
	   $('img[name="img"]').click( function(e){
	   
	   });

	   carrousel.click( function(e){
	     carrousel.find( "img" ).attr( "src", '' );
	     carrousel.fadeOut( 200 );
	   } );
 
 });*/
	
 