$(function(){


	var keyword = {
		init: function(){
			$("#appendKeyword").on("click", keyword.append);
			$("#keywordTable tbody").on("click", ".revise", keyword.revise);
			$("#keywordTable tbody").on("click", ".del", keyword.del);
		},
		showPop : function(o) {
			if (!o) {
				o = {title:"",answerone:"",answertwo:"",answerthree:"",
						answerfour:"",td:undefined,btnY:"添加"
				};
			}
			var txt = "<div class='i title'><h6>问题</h6><input type='text' class='input' value='"+ o.title +"' /></div>";
			txt += "<p>选项名称不多于15个汉字或30个字母</p>";
			txt += "<div class='i answerone'><h6>选项1</h6><input type='text' class='input' value='"+ o.answerone +"' /></div>";
			txt += "<div class='i answertwo'><h6>选项2</h6><input type='text' class='input' value='"+ o.answertwo +"' /></div>";
			txt += "<div class='i answerthree'><h6>选项3</h6><input type='text' class='input' value='"+ o.answerthree +"' /></div>";
			txt += "<div class='i answerfour'><h6>选项4</h6><input type='text' class='input' value='"+ o.answerfour +"' /></div>";
			$.alert({
				title: "添加选项",
				txt: txt,
				btnY: "添加",
				btnN: "取消",
				css: "pop-alert-appendWechatKeyword pop-alert-appendQuestion",
				init: function(){
					$("#pop-alert .mode input").inputInit();
				},
				callbackY: function(){
					var title = $("#pop-alert .pop .bd .title input");
					var answerone = $("#pop-alert .pop .bd .answerone input");
					var answertwo = $("#pop-alert .pop .bd .answertwo input");
					var answerthree = $("#pop-alert .pop .bd .answerthree input");
					var answerfour = $("#pop-alert .pop .bd .answerfour input");
					//判断奖项名称
					if(title.inputEmpty()){
						title.inputError("投票问题不能为空");
						return false;
					} else if (answerone.inputLengthOverflow(30)){
						answerone.inputError("选项名称不多于15个汉字或30个字母");
						return false;
					}  else if (answertwo.inputLengthOverflow(30)){
						answertwo.inputError("选项名称不多于15个汉字或30个字母");
						return false;
					}  else if (answerthree.inputLengthOverflow(30)){
						answerthree.inputError("选项名称不多于15个汉字或30个字母");
						return false;
					}  else if (answerfour.inputLengthOverflow(30)){
						answerfour.inputError("选项名称不多于15个汉字或30个字母");
						return false;
					} 
					//将值写入到table中
					var alinputs = "<input type='hidden' name='answerone' class='answerone' value='" + answerone.val() + "' />"
						+ "<input type='hidden' name='answertwo' class='answertwo' value='" + answertwo.val() + "' />"
						+ "<input type='hidden' name='answerthree' class='answerthree' value='" + answerthree.val() + "' />"
						+ "<input type='hidden' name='answerfour' class='answerfour' value='" + answerfour.val() + "' />"
						+ "<input type='hidden' name='title' class='title' value='" + title.val() + "' />"
					
					var html = ("<td class=\"t1\">" + title.val() + alinputs + "</td>");
					html += ("<td class=\"t3\"><p class='console'><a href='javascript:void(0)' class='revise'>修改</a><a href='javascript:void(0)' class='del'>删除</a></p></td>");
					if (o.td) {
						o.td.parent().empty().append(html);
					} else {
						html = "<tr>" + html + "</tr>";
						$("#keywordTable tbody").append(html);
					}
				}
			});
		},
		append: function(){
			if ($(this).parents("#keywordTable").find("tbody").children().length == 1) {
				return false;
			}
			keyword.showPop();
		},
		revise: function(){
			var td = $(this).parent().parent().siblings(".t1"), o = {};
			o.title = td.find("input.title").val();
			o.answerone = td.find("input.answerone").val();
			o.answertwo = td.find("input.answertwo").val();
			o.answerthree = td.find("input.answerthree").val();
			o.answerfour = td.find("input.answerfour").val();
			o.td = td;
			o.btnY = "修改";
			keyword.showPop(o);
		},
		del: function(){
			var tr = $(this).parents("tr");
			$.alert({
				title: "温馨提示",
				txt: "确定要删除此选项吗？",
				btnY: "删除",
				btnYcss: "btnC",
				btnN: "取消",
				callbackY: function(){
				$.loading.remove();
					tr.remove();
				}
			});
		}
	};
	keyword.init();

	var awardObject = {
			init: function(){
				$("#appendAward").on("click", awardObject.append);
				$("#awardTable").on("click", ".console .del", awardObject.onDel);
				$("#awardTable").on("click", ".console .revise", awardObject.onRevise);
			},
			append: function(){
				awardObject.showPop();
			},
			onRevise: function(){
				var td = $(this).parent().parent(), o = {};
				o.awardname = td.find("input.awardname").val();
				o.awardtotalnum = td.find("input.awardtotalnum").val();
				o.validtype = td.find("input.validtype").val();
				o.validdaynum = td.find("input.validdaynum").val();
				o.validdate = td.find("input.validdate").val();
				o.desc = td.find("input.desc").val();
				o.wintip = td.find("input.wintip").val();
				o.id = td.find("input.id").val();
				o.td = td;
				o.btnY = "修改";
				awardObject.showPop(o);
			},
			showPop: function(o){
				if (!o){
					o = {id:"",awardname:"",wintip:"",awardtotalnum:"",validtype:"",validdaynum:"",validdate:"",desc:"",btnY:"添加",td:undefined};
				}
				var html = "<div class='i awardname'><h6>奖品名称</h6><input type='text' class='input' value='" + o.awardname + "' /></div>";
				html += "<div class='i wintip'><h6>中奖提示</h6><input type='text' class='input' value='" + o.wintip + "' /></div>";
				html += "<div class='i awardtotalnum'><h6>中奖名额</h6><input type='text' class='input' value='" + o.awardtotalnum + "' /></div>";
				html += "<div class='i'><h6>有效期</h6>";
				html += "<div class='validtypediv'>";
				/*html += '<input type="radio" class="r" name="r" value="1" data-val="指定天数" checked="checked" style="display: none;">' +
					'<a href="javascript:void(0)" class="js_radio js_radioChecked" data-n="r"><span></span><p>指定天数</p></a>' +
					'<input type="radio" class="r" name="r" value="2" data-val="指定日期" style="display: none;">' +
					'<a href="javascript:void(0)" class="js_radio" data-n="r"><span></span><p>指定日期</p></a>';*/
				
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
					html += "<div class='i validdate' style='display:none;'><h6>指定日期</h6><div class='data_input'><input type='text' class='js_date' value='" + o.validdate + "' /></div></div>";
					html += "<div class='i validdaynum'><h6>指定天数</h6><input type='text' class='input w220' value='" + o.validdaynum + "' /></div>";
				} else {
					html += "<div class='i validdate'><h6>指定日期</h6><div class='data_input'><input type='text' class='js_date' value='" + o.validdate + "' /></div></div>";
					html += "<div class='i validdaynum' style='display:none;'><h6>指定天数</h6><input type='text' class='input w220' value='" + o.validdaynum + "' /></div>";
				}
				
				
				html += "<div class='i desc'><h6>使用说明</h6><textarea class='input'>" + o.desc + "</textarea></div>";
				html += "<div class='i id' style='display:none;'><h6>id</h6><input type='hidden' class='r input' value='" + o.id + "' /></div>";
				$.alert({
					title: "添加奖品",
					txt: html,
					btnY: o.btnY,
					btnN: "取消",
					css: "pop-alert-appendQuestion",
					init: function(){
						$("#pop-alert .validtypediv .r").inputInit();
						$("#pop-alert .js_date").dateInputInit();
						$("#pop-alert .validtypediv .js_radio").on("click", function(){
							var id = $(this).parent().find("input:checked").val();
							if (id == 1){
								$("#pop-alert .pop .validdate").hide();
								$("#pop-alert .pop .validdaynum").show();
							} else {
								$("#pop-alert .pop .validdate").show();
								$("#pop-alert .pop .validdaynum").hide();
							}
						});
					},
					callbackY: function(){
						var awardname = $("#pop-alert .pop .bd .awardname .input");
						var wintip = $("#pop-alert .pop .bd .wintip .input");
						var awardtotalnum = $("#pop-alert .pop .bd .awardtotalnum .input");
						var validtype = $("#pop-alert .pop .validtypediv input:checked");
						var validdaynum = $("#pop-alert .pop .bd .validdaynum .input");
						var validdate = $("#pop-alert .pop .bd .validdate input");
						var desc = $("#pop-alert .pop .bd .desc .input");
						var id = $("#pop-alert .pop .bd .id input");
						
						if (awardname.inputEmpty()){
							awardname.inputError("奖品名称不能为空");
							return false;
						} else if (awardname.inputLengthOverflow(60)){
							awardname.inputError("奖品名称不多于30个汉字或60个字母");
							return false;
						}

						//将值写入到table中
						var html = ("<td class='t1'>" + awardname.val()  + "</td>");
							html += '<td class="t2">'+wintip.val()+'</td>';
							html += '<td class="t3">'+awardtotalnum.val()+'</td>';
							html += '<td class="t4">';
						if(validtype.attr("value")==1){
							html += ($.trim(validdaynum.val())+'天');
						}else if(validtype.attr("value")==2){
							html += ($.trim(validdate.val()));
						}
						html += '</td>';

						var alinputs = "<input type='hidden' name='voteAwardList.awardname' class='awardname' value='" + awardname.val() + "' />"
							+ "<input type='hidden' name='voteAwardList.wintip' class='wintip' value='" + wintip.val() + "' />"
							+ "<input type='hidden' name='voteAwardList.awardtotalnum' class='awardtotalnum' value='" + awardtotalnum.val() + "' />"
							+ "<input type='hidden' name='voteAwardList.validtype' class='validtype' value='" + validtype.attr("value") + "' />"
							+ "<input type='hidden' name='voteAwardList.validdaynum' class='validdaynum' value='" + validdaynum.val() + "' />"
							+ "<input type='hidden' name='voteAwardList.validdate' class='validdate' value='" + validdate.val() + "' />"
							+ "<input type='hidden' name='voteAwardList.desc' class='desc' value='" + desc.val() + "' />"
							+ "<input type='hidden' name='voteAwardList.id' class='id' value='" + id.val() + "' />";
						html += ("<td class='t5'><p class='console'>" +
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
			onDel: function(){
				var tr = $(this).parents("tr");
				$.alert({
					title: "温馨提示",
					txt: "确定要删除吗？",
					btnY: "删除",
					btnN: "取消",
					btnYcss: "btnC",
					callbackY: function(){
						$.loading();
						setTimeout(function(){
							$.loading.remove();
							var id = $("#id",$("#myform")).val();
							if(!hhutil.isEmpty(id)){
								//添加删除的状态
								tr.find(".console").append("<input type='hidden' name='voteAwardList.state' value='1'/>");
								tr.hide();
							}else{
								tr.remove();
							}
						},500);
						
					}
				});
			}
		};
		awardObject.init();

	
	









});




