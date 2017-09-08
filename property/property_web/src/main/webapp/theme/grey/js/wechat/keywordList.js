$(function(){


	var page = {
		init: function(){
			$("#keywordTable").on("click", ".delete", page.onDel);
		},
		onDel: function(){
			var dom = $(this).parents("tr");
			$.alert({
				title: "温馨提示",
				txt: "确定要删除此关键字吗？",
				btnY: "删除",
				btnYcss: "btnC",
				btnN: "取消",
				callbackY: function(){
					$.loading();
					setTimeout(function(){
						$.loading.remove();
						dom.remove();
					},1000);
				}
			});
		}
	};
	page.init();






});




