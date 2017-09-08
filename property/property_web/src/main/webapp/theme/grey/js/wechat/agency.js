$(function(){

	var agency = {
		init: function(){
				//省市级联
				$('#province').change(function(){
					hhutil.ajax(hhutil.getRootPath()+"/battery!getCityList.action?province="+$(this).val(),null,function(data){
						$('#city').parent().find(".js_select").remove();
						var dataList = data.data ;
						var tmpl2 = $("#tmp_option").html();
						$("#city").empty();
						$("#city").append("<option value='' selected='selected'>所有区域</option>");		
						if(!hhutil.isEmpty(dataList)){
							for(var i=0,len=dataList.length;i<len;i++){
								var tdata = dataList[i] ;
								$("#city").append($.render(tmpl2, tdata));		
							}
						}
						var dataid = $('#city').data("id");
						if(!hhutil.isEmpty(dataid)){
							$('#city').find("option[value="+dataid+"]").attr("selected","selected");;
						}
						$('#city').data("init","");
						$('#city').selectInit();
						if($('#agencyid').size()>0){
							$('#city').trigger("change");
						}
					});
				});
				//点击城市，加载经销商
				$('#city').change(function(){
					//查询区域下的经销商	
					hhutil.ajax(hhutil.getRootPath()+"/account!getAgencyJsonList.action?cityid="+$(this).val()+"&provinceid="+$('#province').val(),null,function(data){
						$('#agencyid').parent().find(".js_select").remove();
						var dataList = data.data ;
						var tmpl2 = $("#tmp_option").html();
						$("#agencyid").empty();
						$("#agencyid").append("<option value='' selected='selected'>所有</option>");
						//$("#agencyid").append("<option value='-1'>其他</option>");
						if(!hhutil.isEmpty(dataList)){
							for(var i=0,len=dataList.length;i<len;i++){
								var tdata = dataList[i] ;
								tdata.id = tdata.agencyid;
								tdata.name = tdata.name;
								$("#agencyid").append($.render(tmpl2, tdata));		
							}
						}
						var dataid = $('#agencyid').data("id");
						if(!hhutil.isEmpty(dataid)){
							$('#agencyid').find("option[value="+dataid+"]").attr("selected","selected");;
						}
						$('#agencyid').data("init","");
						$('#agencyid').selectInit();
					});
				});
				
				if(!hhutil.isEmpty($('#province').val())){
					$('#province').trigger("change");
				}
				
				
				$('#submit').click(function(){
					if ($("#name").inputEmpty()){
						$("#name").inputError("红框内容不能为空");
						return false;
					}
					if ($("#address").inputEmpty()){
						$("#address").inputError("门店地址不能为空");
						return false;
					}
					if ($("#inte").inputEmpty()){
						$("#inte").inputError("每次获得积分");
						return false;
					} else if ($("#inte").inputNotNum()) {
						$("#inte").inputError("积分需为数字");
						return false;
					}
					if ($("#numlimit").inputEmpty()){
						$("#numlimit").inputError("限制次数不能为空");
						return false;
					}
					if ($("#intedaylimit").inputEmpty()){
						$("#intedaylimit").inputError("限制次数不能为空");
						return false;
					}
					if ($("#instruction").inputEmpty()){
						$("#instruction").inputError("描述不能为空");
						return false;
					}
					if ($("#linkman").inputEmpty()){
						$("#linkman").inputError("门店店主不能为空");
						return false;
					}
					if ($("#opentime").inputEmpty()){
						$("#opentime").inputError("营业时间不能为空");
						return false;
					}
					if ($("#linkphone").inputEmpty()){
						$("#linkphone").inputError("电话不能为空");
						return false;
					} else if ($("#linkphone").inputNotNum()) {
						$("#linkphone").inputError("电话需为数字");
						return false;
					}
					if ($("#summary").inputEmpty()){
						$("#summary").inputError("帮派宣言不能为空");
						return false;
					}
					if ($("#service").inputEmpty()){
						$("#service").inputError("特色服务不能为空");
						return false;
					}
					$.alert({
						title: "保存",
						txt: "确定要保存？",
						btnY: "确定",
						btnN: "取消",
						callbackY: function(){
							$.loading();
							$('#myform').submit();
						}
					});
				});
				
				$('#btn_search').click(function(){
					$.loading();
					$('#myform').submit();
				});
				
				
				
				//功能按钮
				//移动分组
				/**
				$(document).delegate('.team', 'change', function() {
					//获取所有选中的粉丝
					var value = $(this).val();
					var ids="";
					$("input:checkbox[class='id_checkbox']:checked").each(function(){  
						ids+=$(this).data("id")+",";
				     });
				     //console.log(memberids);
					//保存分组
					var url = hhutil.getRootPath()+"/account!saveAgency.action?a=1";
					if(!hhutil.isEmpty(ids)){
						url += "&agencyids="+ids;
					}
					if(!hhutil.isEmpty(value)){
						url += "&groupid="+value;
					}
					location.href = url ;
				});
				**/
				//锁定
				$("#batch_cancel").click(function(){
					var ids="";
					$("input:checkbox[class='id_checkbox']:checked").each(function(){  
						ids+=$(this).data("id")+",";
				     });
					var url = hhutil.getRootPath()+"/account!saveAgency.action?a=1";
					if(!hhutil.isEmpty(ids)){
						url += "&agencyids="+ids;
						url += "&status=2";
					}
					location.href = url ;
				});
				//注销
				$("#batch_lock").click(function(){
					var ids="";
					$("input:checkbox[class='id_checkbox']:checked").each(function(){  
						ids+=$(this).data("id")+",";
				     });
					var url = hhutil.getRootPath()+"/account!saveAgency.action?a=1";
					if(!hhutil.isEmpty(ids)){
						url += "&agencyids="+ids;
						url += "&status=1";
					}
					location.href = url ;
				});
				//启用
				$("#batch_enable").click(function(){
					var ids="";
					$("input:checkbox[class='id_checkbox']:checked").each(function(){  
						ids+=$(this).data("id")+",";
				     });
					var url = hhutil.getRootPath()+"/account!saveAgency.action?a=1";
					if(!hhutil.isEmpty(ids)){
						url += "&agencyids="+ids;
						url += "&status=0";
					}
					location.href = url ;
				});
				//删除
				$("#batch_delete").click(function(){
					var ids="";
					$("input:checkbox[class='id_checkbox']:checked").each(function(){  
						ids+=$(this).data("id")+",";
				     });
					var url = hhutil.getRootPath()+"/account!deleteAgency.action?a=1";
					if(!hhutil.isEmpty(ids)){
						url += "&agencyids="+ids;
					}
					location.href = url ;
				});
		}
	};
	agency.init();









});




