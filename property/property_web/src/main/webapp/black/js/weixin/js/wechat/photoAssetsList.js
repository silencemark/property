$(function(){


	// 图片列表
	var list = {
		init: function(){
			list.dom = $("#wechatPhotoAssetsList");
			list.dom.on("click", ".itemList .item .tools .del", list.onItemDel);
			$("#flashupload").on("click", list.upload);
		},
		onItemDel: function(e){
			var dom = $(this).parents(".item");
			var imgid = $(this).data("id");
			$.alert({
				title: "温馨提示",
				txt: "确定要删除此图片吗？",
				btnY: "删除",
				btnYcss: "btnC",
				btnN: "取消",
				callbackY: function(){
					$.loading();
					try{   
						if(delImg && typeof(delImg) == "function"){    
							delImg(eval(imgid),function(){
								$.loading.remove();
							});   
						}  
					 }catch(e){
						 //alert("方法不存在");  
					 }
					//dom.fadeOut(200, function(){
						
						//dom.remove();
					//});
				}
			});
		},
		upload: function(){
			$.imgUploadPop({
				width: 500,
				height: -1,
				complete: list.uploadComplete
			});
		},
		uploadComplete: function(d){
			var html = "<div class='item'><div class='img'><img src='" + hhutil.getRootPath() + d + "' /></div>";
			html += "<div class='tools'><a href='javascript:void(0)' class='del'></a></div></div>";
			list.dom.find(".itemList").prepend(html);
		}
	};
	list.init();




});




