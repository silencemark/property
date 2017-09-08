$(function(){

	var users = {
		init: function(){
			users.table = $("#userTable");
			users.table.find("thead .th1 a.all").on("click", users.checkAll);
			users.table.find("tbody .t1 a.js_checkbox").on("click", users.checkOne);
			$("#appendTeam").on("click", users.appendTeam);
			$("#renameTeam").on("click", users.renameTeam);
			$("#deleteTeam").on("click", users.deleteTeam);
			
		},
		checkOne: function(){
			users.checkInputChecked();
		},
		checkAll: function(){
			if (users.table.find("thead .th1 input.all")[0].checked){
				users.table.find("tbody .t1 input").attr("checked", "true").next(".js_checkbox").addClass("js_checkboxChecked");
			} else {
				users.table.find("tbody .t1 input").removeAttr("checked").next(".js_checkbox").removeClass("js_checkboxChecked");
			}
			users.checkInputChecked();
		},
		checkInputChecked: function(){
			users.checkedArr = [];
			users.table.find("tbody .t1 input").each(function(){
				if ($(this)[0].checked){
					users.checkedArr.push($(this).attr("name"));
				}
			});
			if (users.checkedArr.length != 0){
				users.table.find("thead .th2 select.team").removeAttr("disabled").next("div.team").removeClass("js_selectDisabled");
			} else {
				users.table.find("thead .th2 select.team").attr("disabled", "disabled").next("div.team").addClass("js_selectDisabled");
			}
			//console.log(users.checkedArr)
		},
		appendTeam: function(){
			$.alert({
				title: "添加分组",
				txt: "<p>分组名称不多于6个汉字或12个字母</p><input type='text' class='input' />",
				btnY: "添加",
				btnN: "取消",
				css: "pop-alert-appendWechatUserTeam",
				callbackY: function(){
					var input = $("#pop-alert .pop .bd .input");
					if (input.inputEmpty()){
						input.inputError("分组名称不能为空");
						return false;
					} else if (input.inputLengthOverflow(12)){
						input.inputError("分组名称不多于6个汉字或12个字母");
						return false;
					} else {
						//添加分组
						$.loading();
						saveGroup(null,input.val(),function(){
							$.loading.remove();
						});
						//$("#userTeam ul").append("<li><a href='javascript:void(0)'>" + input.val() + "<span>(0)</span></a></li>");
					}
				}
			});
		},
		renameTeam: function(){
			var groupid = $(this).data("id");
			$.alert({
				title: "重命名分组",
				txt: "<p>分组名称不多于6个汉字或12个字母</p><input type='text' class='input' />",
				btnY: "确定",
				btnN: "取消",
				css: "pop-alert-appendWechatUserTeam",
				callbackY: function(){
					var input = $("#pop-alert .pop .bd .input");
					if (input.inputEmpty()){
						input.inputError("分组名称不能为空");
						return false;
					} else if (input.inputLengthOverflow(12)){
						input.inputError("分组名称不多于6个汉字或12个字母");
						return false;
					} else {
						$.loading();
						saveGroup(groupid,input.val(),function(){
							$.loading.remove();
						});
					}
				}
			});
		},
		deleteTeam: function(){
			var groupid = $(this).data("id");
			if(groupid<100){
				$.alert({
					title: "温馨提示",
					txt: "系统默认分组不能删除！",
					btnN: "取消"
				});
			}else{
				$.alert({
					title: "温馨提示",
					txt: "删除后该组下的成员将会移动至未分组，确定要删除该组吗？",
					btnY: "删除",
					btnYcss: "btnC",
					btnN: "取消",
					callbackY: function(){
						$.loading();
						deleteGroup(groupid,function(){
							$.loading.remove();
						});
						/**
						 
						setTimeout(function(){
							$.loading.remove();
						},500);
						**/
					}
				});
				//==============
			}
		}
	};
	users.init();









});




