$(function(){


	var keyword = {
		init: function(){
			$("#appendKeyword").on("click", keyword.append);
			$("#awardTable tbody").on("click", ".revise", keyword.revise);
			$("#awardTable tbody").on("click", ".del", keyword.del);
		},
		getselectdata:function(dataList,id){
			var html = "";
			if(null!=dataList && dataList.length>0){
				for(var i=0,len=dataList.length;i<len;i++){
					var item = dataList[i];
					console.log(item);
					if(id==item.dataid){
						html+="<option selected='selected' value='"+item.dataid+"'>"+item.cname+"</option>";
					}else{
						html+="<option value='"+item.dataid+"'>"+item.cname+"</option>";
					}
				}
			}
			return html;
		},
		showPop : function(o) {
			if (!o) {
				o = {id:"",activityinfoid:"",awardid:"",discount:"",probability:"",
						winnum:"",validdate:"",td:undefined,btnY:"添加"
				};
			}
			
			var html = "<div class='i awardid'><h6>奖品名称</h6><select id='awardid' style='display:block;' class='js_dateinput' name='awardid'>";
			html+=$("#_awardDictList").html();
			//html+=keyword.getselectdata(awardDictList,o.awardid);
			html+="</select></div>";
			
			//html += "<div class='i discount'><h6>优惠折扣</h6><select id='discount' class='js_dateinput' style='display:block;' name='discount'>";
			//html+=$("#_discountDictList").html();
			//html+="</select></div>";
			html += "<div class='i probability'><h6>中奖概率</h6><select id='probability' class='js_dateinput' style='display:block;' name='probability'>";
			html+=$("#_probabilityDictList").html();
			html+="</select></div>";
			html += "<div class='i winnum'><h6>中奖名额</h6><input type='text' class='input' value='"+ o.winnum +"' /></div>";
			html += "<div class='i validdate'><h6>有效期</h6><select id='validdate' class='js_dateinput' style='display:block;' name='validdate'>";
			html+=$("#_validateDictList").html();
			html+="</select></div>";
			html += "<div class='i id' style='display:none;'><h6>id</h6><input type='hidden' class='r input' value='" + o.id + "' /></div>";
			html += "<div class='i activityinfoid' style='display:none;'><h6>activityinfoid</h6><input type='hidden' class='r input' value='" + o.activityinfoid + "' /></div>";
			
			$.alert({
				title: "添加奖品",
				txt: html,
				btnY: o.btnY,
				btnN: "取消",
				css: "pop-alert-appendWechatKeyword",
				init: function(){
					$('.awardid').find("option[ value="+o.awardid+"]").attr("selected","selected");
					//$('.discount').find("option[ value="+o.discount+"]").attr("selected","selected");
					$('.probability').find("option[ value="+o.probability+"]").attr("selected","selected");
					$('.validdate').find("option[ value="+o.validdate+"]").attr("selected","selected");
					
					//$("#pop-alert .validtypediv .r").inputInit();
					//$("#pop-alert .js_date").dateInputInit();
				},
				callbackY: function(){
					var awardid = $("#pop-alert .pop .bd .awardid select");
					//var discount = $("#pop-alert .pop .bd .discount select");
					var probability = $("#pop-alert .pop .bd .probability select");
					var winnum = $("#pop-alert .pop .bd .winnum input");
					var validdate = $("#pop-alert .pop .validdate select");
					var id = $("#pop-alert .pop .bd .id input");
					var activityinfoid = $("#pop-alert .pop .bd .activityinfoid input");
					
					//中奖名额不能为空
					if(winnum.inputEmpty()){
						winnum.inputError("中奖名额不能为空");
						return false;
					}else if (!hhutil.checkInt(winnum.val())){
						winnum.inputError("中奖名额只能为数字");
						return false;
					}

					//将值写入到table中
					//$(this).find("option[ value="+value+"]").attr("selected","selected");
					var html = ("<td class='t1'>" + awardid.find("option[ value="+awardid.val()+"]").text()  + "</td>");
						//html += '<td class="t2">'+discount.find("option[ value="+discount.val()+"]").text()+'</td>';
						html += '<td class="t3">'+probability.find("option[ value="+probability.val()+"]").text()+'</td>';
						html += '<td class="t4">'+winnum.val()+'</td>';
						html += '<td class="t5">'+validdate.find("option[ value="+validdate.val()+"]").text()+'</td>';
						
					var alinputs = "<input type='hidden' name='awardMap.id' class='id' value='" + id.val() + "' />"
						+ "<input type='hidden' name='awardMap.activityinfoid' class='activityinfoid' value='" + activityinfoid.val() + "' />"
						
						+ "<input type='hidden' name='awardMap.awardid' class='awardid' value='" + awardid.val() + "' />"
						//+ "<input type='hidden' name='awardMap.discount' class='discount' value='" + discount.val() + "' />"
						+ "<input type='hidden' name='awardMap.probability' class='probability' value='" + probability.val() + "' />"
						+ "<input type='hidden' name='awardMap.winnum' class='winnum' value='" + winnum.val() + "' />"
						+ "<input type='hidden' name='awardMap.validdate' class='validdate' value='" + validdate.val() + "' />";
					
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
			o.id = td.find("input.id").val();
			o.activityinfoid = td.find("input.activityinfoid").val();
			o.awardid = td.find(".awardid").val();
			//o.discount = td.find(".discount").val();
			o.probability = td.find(".probability").val();
			o.winnum = td.find("input.winnum").val();
			o.validdate = td.find(".validdate").val();
			o.td = td;
			o.btnY = "修改";
			keyword.showPop(o);
		},
		del: function(){
			var tr = $(this).parents("tr");
			$.alert({
				title: "温馨提示",
				txt: "确定要删除此奖品吗？",
				btnY: "删除",
				btnYcss: "btnC",
				btnN: "取消",
				callbackY: function(){
					$.loading();
					setTimeout(function(){
						$.loading.remove();
						tr.find(".console").append("<input type='hidden' name='awardMap.ifactive' value='1'/>");
						tr.hide();
						//tr.remove();
					},500);
				}
			});
		}
	};
	keyword.init();

});

