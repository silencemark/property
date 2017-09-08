$(function(){


	// 图文消息列表
	var list = {
		init: function(){
			list.dom = $("#wechatArticleAssetsList");
			$.waterfall.init(list.dom.find(".itemList"), "item", 3, 289, 15);
			//list.dom.on("click", ".itemList .item .tools .del", list.onItemDel);
		},
		onItemDel: function(e){
			var dom = $(this).parents(".item");
			alert(dom);
			$.alert({
				title: "温馨提示",
				txt: "确定要删除此素材吗？",
				btnY: "删除",
				btnYcss: "btnC",
				btnN: "取消",
				callbackY: function(){
					
					//delGraphic();	
				/**$.loading();
					dom.fadeOut(200, function(){
						$.loading.remove();
						dom.remove();
						$.waterfall.init(list.dom.find(".itemList"), "item", 3, 289, 15);
					});**/
				}
			});
		}
	};
	list.init();




	
	









});




