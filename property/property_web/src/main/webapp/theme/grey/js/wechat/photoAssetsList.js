$(function(){


	// 图片列表
	var list = {
		init: function(){
			list.dom = $("#wechatPhotoAssetsList");
			list.dom.on("click", ".itemList .item .tools .del", list.onItemDel);
			list.dom.on("click", ".itemList .item .tools .rename", list.onItemRename);
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
		onItemRename: function(){
			var dom = $(this).parents(".item").find(".imgname");
			var id = $(this).data("id");
			var name = dom.html();
			if (name){
				name = $.trim(name);
				$.alert({
					title: "修改名称",
					txt: "<p>名称不多于25个汉字或50个字母</p><input type='hidden' id='imgid' value='" + id + "' class='input' /><input id='name' type='text' value='" + name + "' class='input' />",
					btnY: "修改",
					btnN: "取消",
					css: "pop-alert-audioRename",
					callbackY: function(){
						var input = $("#pop-alert .pop .bd #name");
						var id = $("#pop-alert .pop .bd #imgid").val();
						if (input.inputEmpty()){
							input.inputError("名称不能为空");
							return false;
						} else if (input.inputLengthOverflow(50)){
							input.inputError("名称不多于25个汉字或50个字母");
							return false;
						} else {
							var data = new Object();
							data.id=id;
							data.name=input.val();
							updateDTImgName(data);
						}
					}
				});
			}
		},
		upload: function(){
			$.imgUploadPop({
				width: 500,
				height: -1,
				complete: list.uploadComplete
			});
		},
		uploadComplete: function(d){
			if(!hhutil.isEmpty(d) && "sizeTooBig"==d){
				$.tips.error("图片文件尺寸过大，请小于2M");
			}else{
				/*var html = "<div class='item'><div class='img'><img src='" + hhutil.getRootPath() + d + "' /></div>";
				html+="<div class='tools'><a href='javascript:void(0)' class='del'></a></div>";
				html += "<div class='tools'><a href='javascript:void(0)' class='del'></a></div></div>";
				list.dom.find(".itemList").prepend(html);*/
				//window.saveImg(d);
				var url = hhutil.getRootPath()+"/ws/backer/wxset/saveDTImg?img="+d;
				location.href = url ;
			}
		}
	};
	list.init();




});




