$(function(){


	var keyword = {
		init: function(){
			//$("#appendKeyword").on("click", keyword.append);
			$("#userTable tbody").on("click", ".audit_yes", keyword.auditPass);
			$("#userTable tbody").on("click", ".audit_no", keyword.auditRefuse);
		},
		auditPass: function(){
			//审核通过
			var id = $(this).data("id");
			$.alert({
				title: "温馨提示",
				txt: "确定要审核通过？",
				btnY: "确定",
				btnN: "取消",
				callbackY: function(){
					//审核通过
					auditPass(id);
				}
			});
			
		},
		auditRefuse : function(){
			var id = $(this).data("id");
			//审核拒绝
			$.alert({
				title: "温馨提示",
				txt: "<div style='margin:auto; width:50%'><p style='text-align: left;'>拒绝原因</p><select id='refusereason' class='input' style='display:block;height: 35px; '>"+
				"<option value='1'>含有法律禁止信息</option>"+
				"<option value='2'>主题不明或描述不清</option>"+
				"<option value='3'>重复提交</option>"+
				"<option value='4'>图片使用不当</option>"+
				"<option value='5'>信息不准确</option>"+
				"<option value=''>其他原因</option>"+
				"</select>"+
				"<p style='text-align: left;'>其他原因</p><textarea class='input' id='otherreason'></textarea></div>",
				btnY: "确定",
				btnN: "取消",
				css: "pop-alert-appendWechatKeyword",
				init: function(){
					$("#pop-alert .mode input").inputInit();
				},
				callbackY: function(){
					var refusereason = $("#pop-alert .pop .bd #refusereason");
					var otherreason = $("#pop-alert .pop .bd #otherreason");
					if(hhutil.isEmpty(refusereason) || hhutil.isEmpty(otherreason) ){
						refusereason.inputError("拒绝原因不能为空！");
						return false;
					}
					auditRefuse(id,refusereason.val(),otherreason.val());
				}
			});
			
		},
		
		del: function(){
			var tr = $(this).parents("tr");
			$.alert({
				title: "温馨提示",
				txt: "确定要删除此奖项吗？",
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




