$(function(){


	var keyword = {
		init: function(){
			$("#appendKeyword").on("click", keyword.append);
			$("#keywordTable tbody").on("click", ".revise", keyword.revise);
			$("#keywordTable tbody").on("click", ".del", keyword.del);
		},
		append: function(){
			$.alert({
				title: "添加关键字",
				txt: "<p>关键字不多于15个汉字或30个字母</p><input type='text' class='input' /><div class='mode'><input type='radio' checked='checked' value='0' data-val='完全匹配' name='mode' /><input type='radio' value='1' data-val='模糊匹配' name='mode' /></div>",
				btnY: "添加",
				btnN: "取消",
				css: "pop-alert-appendWechatKeyword",
				init: function(){
					$("#pop-alert .mode input").inputInit();
				},
				callbackY: function(){
					var input = $("#pop-alert .pop .bd .input");
					var radio = $("#pop-alert .pop .bd .mode input:checked").val();
					if (input.inputEmpty()){
						input.inputError("关键字不能为空");
						return false;
					} else if (input.inputLengthOverflow(30)){
						input.inputError("关键字不多于15个汉字或30个字母");
						return false;
					} else {
						var yes = radio == 0 ? "完全匹配" : "模糊匹配";
						var html = "<tr><td class='t1'>" + input.val() + "</td><td class='t2' data-b='" + radio + "'>" + yes + "</td><td class='t3'>";
						html += "<p class='console'><a href='javascript:void(0)' class='revise'>修改</a><a href='javascript:void(0)' class='del'>删除</a></p></td></tr>";
						$("#keywordTable tbody").append(html);
					}
				}
			});
		},
		revise: function(){
			var tr = $(this).parents("tr");
			var key = tr.find(".t1").html();
			var bool = tr.find(".t2").data("b");
			var radioHtml = "";
			if (bool == 0){
				radioHtml = "<input type='radio' checked='checked' value='0' data-val='完全匹配' name='mode' /><input type='radio' value='1' data-val='模糊匹配' name='mode' />";
			} else {
				radioHtml = "<input type='radio' value='0' data-val='完全匹配' name='mode' /><input type='radio' checked='checked' value='1' data-val='模糊匹配' name='mode' />";
			}
			$.alert({
				title: "修改关键字",
				txt: "<p>关键字不多于15个汉字或30个字母</p><input type='text' class='input' value='" + key + "' /><div class='mode'>" + radioHtml + "</div>",
				btnY: "修改",
				btnN: "取消",
				css: "pop-alert-appendWechatKeyword",
				init: function(){
					$("#pop-alert .mode input").inputInit();
				},
				callbackY: function(){
					var input = $("#pop-alert .pop .bd .input");
					var radio = $("#pop-alert .pop .bd .mode input:checked").val();
					if (input.inputEmpty()){
						input.inputError("关键字不能为空");
						return false;
					} else if (input.inputLengthOverflow(30)){
						input.inputError("关键字不多于15个汉字或30个字母");
						return false;
					} else {
						var yes = radio == 0 ? "完全匹配" : "模糊匹配";
						tr.find(".t1").html(input.val());
						tr.find(".t2").data("b", radio).html(yes);
					}
				}
			});
		},
		del: function(){
			var tr = $(this).parents("tr");
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
						tr.remove();
					},500);
				}
			});
		}
	};
	keyword.init();



	
	









});




