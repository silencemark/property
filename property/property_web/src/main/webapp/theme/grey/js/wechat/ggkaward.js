$(function(){


	var keyword = {
		init: function(){
			$("#appendKeyword").on("click", keyword.append);
			$("#awardTable tbody").on("click", ".revise", keyword.revise);
			$("#awardTable tbody").on("click", ".del", keyword.del);
		},
		showPop : function(o) {
			if (!o) {
				o = {id:"",awardname:"",wintip:"",awardpercent:"",awardtotalpcount:"",
						validtype:"",validdaynum:"",validendtime:"",desc:"",td:undefined,btnY:"添加"
				};
			}
			var html = "<div class='i awardname'><h6>奖品名称</h6><input type='text' class='input' value='"+ o.awardname +"' /></div>";
			html += "<div class='i wintip'><h6>中奖提示</h6><input type='text' class='input' value='"+ o.wintip +"' /></div>";
			html += "<div class='i awardpercent'><h6>中奖几率</h6><input type='text' class='input' value='"+ o.awardpercent +"' /></div>";
			html += "<div class='i awardtotalpcount'><h6>中奖名额</h6><input type='text' class='input' value='"+ o.awardtotalpcount +"' /></div>";
			html += "<div class='i'><h6>有效期</h6>";
			html += "<div class='validtypediv'>";
			if(o.validtype == 1){
				//1指定天数
				html += "<input type='radio' class='r' name='validtype' value='1' data-val='指定天数'  checked='checked'/>";
				html += "<input type='radio' class='r' name='validtype' value='2' data-val='指定日期' />";
			}else{
				//2指定日期
				html += "<input type='radio' class='r' name='validtype' value='1' data-val='指定天数'/>";
				html += "<input type='radio' class='r' name='validtype' value='2' data-val='指定日期'  checked='checked' />";
			}
			html += "</div></div>";
			if (o.validtype == 1){
				html += "<div class='i validendtime' style='display:none;'><h6>指定日期</h6><div class='data_input'><input type='text' class='js_date' value='" + o.validendtime + "' /></div></div>";
				html += "<div class='i validdaynum'><h6>指定天数</h6><input type='text' class='input w220' value='" + o.validdaynum + "' /></div>";
			} else {
				html += "<div class='i validendtime'><h6>指定日期</h6><div class='data_input'><input type='text' class='js_date' value='" + o.validendtime + "' /></div></div>";
				html += "<div class='i validdaynum' style='display:none;'><h6>指定天数</h6><input type='text' class='input w220' value='" + o.validdaynum + "' /></div>";
			}
			html += "<div class='i desc'><h6>使用说明</h6><textarea class='input'>" + o.desc + "</textarea></div>";
			html += "<div class='i id' style='display:none;'><h6>id</h6><input type='hidden' class='r input' value='" + o.id + "' /></div>";
			
			$.alert({
				title: "添加选项",
				txt: html,
				btnY: o.btnY,
				btnN: "取消",
				css: "pop-alert-appendWechatKeyword",
				init: function(){
					$("#pop-alert .validtypediv .r").inputInit();
					$("#pop-alert .js_date").dateInputInit();
					$("#pop-alert .validtypediv .js_radio").on("click", function(){
						var id = $(this).parent().find("input:checked").val();
						if (id == 1){
							$("#pop-alert .pop .validendtime").hide();
							$("#pop-alert .pop .validdaynum").show();
						} else {
							$("#pop-alert .pop .validendtime").show();
							$("#pop-alert .pop .validdaynum").hide();
						}
					});
				},
				callbackY: function(){
					var awardname = $("#pop-alert .pop .bd .awardname input");
					var wintip = $("#pop-alert .pop .bd .wintip input");
					var awardpercent = $("#pop-alert .pop .bd .awardpercent input");
					var awardtotalpcount = $("#pop-alert .pop .bd .awardtotalpcount input");
					var validtype = $("#pop-alert .pop .validtypediv input:checked");
					var validdaynum = $("#pop-alert .pop .bd .validdaynum input");
					var validendtime = $("#pop-alert .pop .bd .validendtime input");
					var desc = $("#pop-alert .pop .bd .desc textarea");
					var id = $("#pop-alert .pop .bd .id input");
					//判断奖项名称
					if(awardname.inputEmpty()){
						awardname.inputError("奖项名称不能为空");
						return false;
					}else if (awardname.inputLengthOverflow(30)){
						awardname.inputError("奖项名称不多于15个汉字或30个字母");
						return false;
					} 
					//判断中奖提示
					if(wintip.inputEmpty()){
						wintip.inputError("中奖提示不能为空");
						return false;
					}else if (wintip.inputLengthOverflow(30)){
						wintip.inputError("中奖提示不多于15个汉字或30个字母");
						return false;
					} 
					//验证中奖概率
					if(awardpercent.inputEmpty()){
						awardpercent.inputError("中奖概率不能为空");
						return false;
					}else if (!hhutil.checkInt(awardpercent.val())){
						awardpercent.inputError("中奖提示只能为数字");
						return false;
					}

					//将值写入到table中
					var html = ("<td class='t1'>" + awardname.val()  + "</td>");
						html += '<td class="t2">'+wintip.val()+'</td>';
						html += '<td class="t3">'+awardpercent.val()+'</td>';
						html += '<td class="t4">'+awardtotalpcount.val()+'</td>';
						html += '<td class="t5">';
					if(validtype.attr("value")==1){
						html += ($.trim(validdaynum.val())+'天');
					}else if(validtype.attr("value")==2){
						html += ($.trim(validendtime.val()));
					}
					html += '</td>';

					var alinputs = "<input type='hidden' name='ggkAwardListV2.awardname' class='awardname' value='" + awardname.val() + "' />"
						+ "<input type='hidden' name='ggkAwardListV2.wintip' class='wintip' value='" + wintip.val() + "' />"
						+ "<input type='hidden' name='ggkAwardListV2.awardpercent' class='awardpercent' value='" + awardpercent.val() + "' />"
						+ "<input type='hidden' name='ggkAwardListV2.awardtotalpcount' class='awardtotalpcount' value='" + awardtotalpcount.val() + "' />"
						+ "<input type='hidden' name='ggkAwardListV2.validtype' class='validtype' value='" + validtype.attr("value") + "' />"
						+ "<input type='hidden' name='ggkAwardListV2.validdaynum' class='validdaynum' value='" + validdaynum.val() + "' />"
						+ "<input type='hidden' name='ggkAwardListV2.validendtime' class='validendtime' value='" + validendtime.val() + "' />"
						+ "<input type='hidden' name='ggkAwardListV2.desc' class='desc' value='" + desc.val() + "' />"
						+ "<input type='hidden' name='ggkAwardListV2.id' class='id' value='" + id.val() + "' />";
					html += ("<td class='t6'><p class='console'>" +
							alinputs + 
							"<a href='javascript:void(0)' class='revise'>修改</a><a href='javascript:void(0)' class='del'>删除</a>" +
							"</p></td>");
					if (o.td) {
						o.td.parent().empty().append(html);
					} else {
						html = "<tr>" + html + "</tr>";
						$("#awardTable tbody").append(html);
					}
				}
			});
		},
		append: function(){
			keyword.showPop();
		},
		revise: function(){
			var td = $(this).parent().parent(), o = {};
			o.awardname = td.find("input.awardname").val();
			o.wintip = td.find("input.wintip").val();
			o.awardpercent = td.find("input.awardpercent").val();
			o.awardtotalpcount = td.find("input.awardtotalpcount").val();
			o.validtype = td.find("input.validtype").val();
			o.validdaynum = td.find("input.validdaynum").val();
			o.validendtime = td.find("input.validendtime").val();
			o.desc = td.find("input.desc").val();
			o.id = td.find("input.id").val();
			o.td = td;
			o.btnY = "修改";
			keyword.showPop(o);
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
						var id = $("#id",$("#myform")).val();
						if(!hhutil.isEmpty(id)){
							//添加删除的状态
							tr.find(".console").append("<input type='hidden' name='ggkAwardListV2.state' value='1'/>");
							tr.hide();
						}else{
							tr.remove();
						}
						//tr.remove();
					},500);
				}
			});
		}
	};
	keyword.init();

});

