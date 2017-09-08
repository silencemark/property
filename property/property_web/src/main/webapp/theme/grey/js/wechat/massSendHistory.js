$(function(){

	var page = {
		init: function(){
			$("#table").on("click", ".delete", page.delClick);
		},
		delClick: function(){
			var tr = $(this).parents("tr");
			$.alert({
				title: "温馨提示",
				txt: "确定要删除该历史数据吗？",
				btnY: "删除",
				btnYcss: "btnC",
				btnN: "取消",
				callbackY: function(){
					$.loading();
					setTimeout(function(){
						tr.remove();
						$.loading.remove();
					},500);
				}
			});
		}
	};
	page.init();

});




