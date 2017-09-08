var isimgda = true;
/*$(function(e){
	
	var winHeight = document.documentElement.clientHeight;
	var winWidth = document.documentElement.clientWidth; 
	 
	$("img").click(function(){
		if(!isimgda){
			return;
		}
		 var src = $(this).attr("src");
		
		 var img=new Image();
		 img.src=src;
		 var width=img.width,height=img.height;//图片大小
		 if(width == 0 && height == 0){
			 return;
		 }
		 if(winHeight>height && winWidth>width){
			 
		 }else{
			 height = height*3/4;
			 width = width*3/4;
		 } 
		var imghtml = 
			'<div id="win_PMG">'+
			'<div onclick="$(\'#win_PMG\').remove();" style="width: 100%; height: 100%; opacity:0.5; position: fixed; top:0px; background-color: #333; z-index: 9000">'+
			'</div>'+
			'<img ondblclick="$(\'#win_PMG\').remove();" src="'+src+'"    align="center" style="position: fixed; top:'+((winHeight-height)/2)+'px; left:'+((winWidth-width)/2)+'px; z-index: 9001; width: '+width+'px;height: '+height+'px">'+
			'</div>';
		$("body").append(imghtml);
	});
	
})*/
function ImgFangDa(dom,tagName){ 
	var src = "";
	 if(tagName){
		 src = $(dom).val();
	 } else{
		 src = $(dom).attr("src");
	 }
	 var img=new Image();
	 img.src=src;
	 var width=img.width,height=img.height;//图片大小
	 if(width == 0 && height == 0){
		 return;
	 }
	 var winHeight = document.documentElement.clientHeight;
	 var winWidth = document.documentElement.clientWidth; 
	 if(winHeight>height && winWidth>width){
		 
	 }else{
		 height = height*3/4;
		 width = width*3/4;
	 } 
	var imghtml = 
		'<div id="win_PMG">'+
		'<div onclick="$(\'#win_PMG\').remove();" style="width: 100%; height: 100%; opacity:0.5; position: fixed; top:0px; background-color: #333; z-index: 9000">'+
		'</div>'+
		'<img ondblclick="$(\'#win_PMG\').remove();" src="'+src+'"    align="center" style="position: fixed; top:'+((winHeight-height)/2)+'px; left:'+((winWidth-width)/2)+'px; z-index: 9001; width: '+width+'px;height: '+height+'px">'+
		'</div>';
	$("body").append(imghtml);
}
printObject = function(tmpl, bean){
 			for(var i in bean){//用javascript的for/in循环遍历对象的属性 
 				if(bean.hasOwnProperty(i)){
 					while(true){
 						tmpl=tmpl.replace("{"+i+"}",bean[i]);
 						if(tmpl.indexOf("{"+i+"}")==-1){
 							break;
 						}
 					}
 				    
 				} 
 			} 
 			return tmpl;
} 
getURL = function(url,page){
	if(!(null === url || "" === url || undefined === url)){
		if(url.indexOf('?')<0){//不存在url
			url+=('?a=1');
		}
		for(var i in page){//用javascript的for/in循环遍历对象的属性 
				if(page.hasOwnProperty(i)){
				url=url+"&"+i+"="+page[i];
				} 
		}  
	}
	return url ;
}
/**
 * 提示
 * @param content
 * @param index
 * @param width
 */
function winpop(content,index,width){ 
	if(!index){
		index=3000;
	}
	if(!width){
		width=200;
	}
	var html =  
	  '<div class="modal-dialog"  id="winpopTip"  style="width:'+(width+50)+'px; margin-left:55%">'+
	  '  <div class="modal-content" style="width:'+width+'px; float: right;"> '+
	    '  <div class="modal-header">'+
	     '   <button type="button" class="close" onclick="$(\'#winpopTip\').remove();" data-dismiss="modal" aria-hidden="true">&times;</button>'+
	    '    <h3 class="modal-title">提示</h3>'+
	   '   </div>'+
	    '  <div class="modal-body">'+
	         content+
	    '  </div>   '+
	   ' </div>'+ 
	'</div>';
	var body = '<div style="padding: inherit;position:fixed;  top:10px; left:300px;  z-index: '+index+'" id="winpopBody"></div>';
	if($("#winpopBody").length==0){
		$("body").append(body);
	} 
	$("#winpopBody").append(html);
	setTimeout("closewinpop()",3000); 
}
function closewinpop(){
	$('#winpopTip').remove();
} 