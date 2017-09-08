<%@page import="com.qiniu.util.AuthConstant"%>
<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>代理商详情</title>
<link href="<%=request.getContextPath()%>/js/z-tree/css/demo.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/js/z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-suppe p-agency">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	</div>
	
	<div class="content">
		<div class="crumb">
				<h5>
					商务合作  »代销渠道
				</h5>
		</div>
        <div class="content-container">
	            <div class="row">
	                <div class="col-md-12"  >
	                    <h3 class="current-tit"><span>代理商详情</span></h3> 
	                    
	               <div class="tab-content">
	                    
	                    <div class="modal-body" style="overflow-y:visible ">
	                    <form id="editForm" method="post" onsubmit="return subForm();" class="form-horizontal parsley-form" data-validate="parsley">
	                        <table class="table table-bordered table-highlight table_tab_li tab_li_2"  >
	                        <input type ="hidden" value="${data.agencyid }" id ="agencyid" name="agencyid"/>
	                         <c:if test="${data.agencyid == null }">
	                         <tr>
	                    		   <td width="50%"> 
		                    			<div class="form-group">
							                <label class="col-md-3 control-label">渠道账号 </label>
							                <div class="col-md-7" style="height: 23px"> 
							                   <input value="${data.username}" id="username" name="username" data-required="true" type="text" class="form-control parsley-validated" >
							                </div>
							            </div>
	                    			</td>
	                    			<td> 
							          <div class="form-group" >
							                <label class="col-md-3 control-label">初始密码</label>
							                <div class="col-md-7">
							                   <input type="text" id="password" name="password" data-required="true" value="${data.pwd }" class="form-control parsley-validated"  maxlength="16">
							                </div>
							            </div> 
	                    			</td>
	                    		</tr> 
	                    	    </c:if>
	                    		
	                    		<tr>
	                    			<td>  
		                    			<div class="form-group">
							                <label class="col-md-3 control-label">渠道名称</label>
							                <div class="col-md-7">
							                  <input type="text" id="agencyname" name="agencyname" data-required="true"  value="${data.agencyname }" class="form-control parsley-validated"  >
							                </div>
							            </div>
	                    			</td>
	                    			<td> 
							            <div class="form-group" >
							                <label class="col-md-3 control-label">渠道类型</label>
							                <div class="col-md-7">
							                 
							                   <select id="roletype" name="roletype"  class="form-control select2-input select2-offscreen" style="line-height: 30px;height: 32px">
							                      <option value="1" ${data.roletype==1?'selected':'' }>代理商</option>
							                      <option value="2"  ${data.roletype==2?'selected':'' }>销售员</option>
							                   </select>
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                    		
	                    		<tr>
	                    		   <td> 
							            <div class="form-group" >
							                <label class="col-md-3 control-label">提成比列</label>
							                <div class="col-md-7">
							                  <input type="text" id="commissionrate" data-required="true" name ="commissionrate" value="${data.commissionrate }" class="form-control parsley-validated" oninput="this.value=this.value.replace(/\D/g,'')" >
							                </div>
							                <span style="line-height: 28px">
							                 %
							                </span>
							            </div> 
	                    			</td>
	                    			<td> 
							            <div class="form-group" >
							                <label class="col-md-3 control-label">上级渠道</label>
							                <div class="col-md-7">
							                  <input type="text" id="agency_parent_name"  value="${data.parentname == null?'平台': data.parentname}" class="form-control parsley-validated"  >
							                  <input type="hidden" name="parentid" id="parentid" value="${data.parentid}"/>
							                  <ul id="treeDemo"  class="ztree" style="margin-top:0; width:80%;position: absolute;z-index: 999999;height: auto;display:none"></ul>
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                    	   <tr>
	                    		   <td> 
							            <div class="form-group" >
							                <label class="col-md-3 control-label">渠道地址</label>
							                <div class="col-md-7">
							                  <input type="text" id="address" name ="address"  value="${data.address }" data-required="true" class="form-control parsley-validated"  >
							                </div>
							            </div> 
	                    			</td>
	                    			<td> 
							            <div class="form-group" >
							                <label class="col-md-3 control-label">渠道区域</label>
							                <div class="col-md-7">
							                    <select id="ag_province" name="provinceid" class="form-control" onchange="getCityInfo();" style="width: 30.33%;height: 32px">
							                       
							                   </select>
							                   
							                    <select id="ag_city" name="cityid" class="form-control" onchange="getDistrictInfo();" style="width: 30.33%;height: 32px">
							                      
							                   </select>
							                   
							                    <select id="ag_district" name="districtid" class="form-control" style="width: 30.33%;height: 32px">
							                       
							                   </select>
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                    	 
	                    	    <tr>
	                    		   <td> 
							            <div class="form-group" >
							                <label class="col-md-3 control-label">联系人</label>
							                <div class="col-md-7">
							                  <input type ="text" id="contacter" name="contacter"  data-required="true" class="form-control parsley-validated" value="${data.contacter }" maxlength="15" >
							                </div>
							            </div> 
	                    			</td>
	                    			<td> 
							            <div class="form-group" >
							                <label class="col-md-3 control-label">联系方式</label>
							                <div class="col-md-7">
							                  <input type ="text" id="phonenum" name ="phonenum" data-required="true"  class="form-control parsley-validated"  value="${data.phonenum }" maxlength="11" oninput="this.value=this.value.replace(/\D/g,'')" >
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                    	  <tr>
	                    		   <td> 
							            <div class="form-group" >
							                <label class="col-md-3 control-label">资质图片</label>
							                <div class="col-md-7">
						                   	 <input type="file" name="file" style="display: none" id="fileDm_upload" />
							                 <div id="room_divdm">
									                 <c:forEach items="${agencyImgList}" var="sup" varStatus="st">
									                  		<a href="#deleteTip"   onclick="deleteData('${sup.agimgid}')" id="${sup.agimgid}" data-toggle="modal" data-backdrop="static">
									                  		  <img class="form-control"  src="${sup.agencyImgShow}"   style="width: 50px; height: 50px"/>
									                  		</a>
									                 </c:forEach>
							                 </div>
							                 <input type="button"  class="form-control" style="width:50px" onclick="$('#fileDm_upload').click();" value="上传">
							                </div>
							            </div> 
	                    			</td>
	                    			<td> 
							            <div class="form-group" >
							                <label class="col-md-3 control-label"></label>
							                <div class="col-md-7">
							                  
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                        </table>
	                        <br/>
	                    	 <button type="submit"  class="btn btn-save"><i class="ico-ok"></i>保 存</button> 
	                    	<a href="javascript:history.go(-1)" style="width: 80px;height:32px " class="btn btn-close" data-dismiss="modal">取 消</a> 
	                    	</form>
				        </div> 
	               </div>
	           </div>
	     </div>
	   </div>
	</div>
<div id="deleteTip" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
		<input type="hidden" id="agimgid" name="agimgid" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4>确定删除？</h4>
        <p class="tips-modal-sub">删除之后无法恢复</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-close"   data-dismiss="modal" id="quxiao">取 消</button>
        <button type="button" onclick="deleteimg()" class="btn btn-save"><i class="ico-ok"></i>确 定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<input type="hidden" id="tp"/>
<SCRIPT type="text/javascript">
//删除图片
function deleteData(id){
	$("#agimgid").val(id);
}
function deleteimgs(demos){
	   $(demos).remove();
}
function deleteimg(){
	var id = $("#agimgid").val();
	$("#"+id).css("display","none");
	url="<%=request.getContextPath() %>/system/agency/deleteAgencyImg";
	$.ajax({
		type:"post",
		url:url,
		data:{agimgid:id},
		success:function(data){
			 
			$("#quxiao").click();
		}
	});

}
$(function(){
	 $('#fileDm_upload').fileupload({    
			url:'http://up.qiniu.com',   
			formData:{
				'token'	: '<%=AuthConstant.getToken() %>'  
			},
			type:'POST',
			maxNumberOfFiles:5,
			autoUpload:true,
		    dataType: 'json',
		    acceptFileTypes:  /(\.|\/)(gif|jpe?g|png)$/i, 
		    maxFileSize: 5000000, 
		    done: function (e, data) {
		    	$.ajax({
					url:'<%=request.getContextPath() %>/upload/downimg/wx/'+data.result.key,
					success:function(data1){
						$('#room_divdm').append('<b onclick="deleteimgs(this)"><input type="hidden" name="agencyimg" value="'+data.result.key+'"/><img  style="width: 50px;height: 50px"  class="form-control" src="'+data1+'"></b>');
					}  
				});
		    },
		    progressall: function (e, data) {
		    	 
		    }
		});
});
var setting = {
	view: {
		dblClickExpand: false,
		expandSpeed: "fast",
		fontCss : {color:"black"},
		selectedMulti: false
	},
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: 0
		}
	},
	callback: {
		/* beforeClick: beforeClick, */
		onClick: onClick
	}
};

var dataArray = new Array();
   dataArray.push(obj={
	  id:-1,pId:0,name:"平台"
   });
 <c:choose>
  <c:when test="${dataList.size()>0}">
  <c:forEach items="${dataList}" var ="row">
       var  obj ={
        id:'${row.agencyid}',
        pId:'${row.parentid}',
        name:'${row.agencyname}',
        agencytype:'${row.agencytype}'
       };
        dataArray.push(obj);
    </c:forEach>
  </c:when>
 </c:choose>
 
var zNodes = dataArray;

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getSelectedNodes(),
	v = "";
	id ="";
	nodes.sort(function compare(a,b){return a.id-b.id;});
	for (var i=0, l=nodes.length; i<l; i++) {
		v += nodes[i].name + ",";
		id+= nodes[i].id + ",";
	}
	if (v.length > 0 ) 
	v = v.substring(0, v.length-1);
	id = id.substring(0, id.length-1);
	var cityObj = $("#agency_parent_name");
	cityObj.attr("value", v);
	$("#parentid").val(id);
	$("#treeDemo").hide();
}

$(document).ready(function(){
	<c:if test="${dataList.size()>0}">
	$('#agency_parent_name').click(function(){
		$("#treeDemo").toggle();
	});
	</c:if>
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	getProvinceInfo();
	
	
	$("#username").blur(function(){
		checkUserName(function(data){
		  
		});
		
	});
	
});
 
 function getData(areatype,areaid,call){ 
	 $.ajax({
			type:"post",
			url:"<%=request.getContextPath()%>/system/agency/getArea",
			data:{areatype:areatype,parentid:areaid},
			success:function(data){
				if("function" == typeof call){
					call(data);
				}
			}
		}); 
 }
 
 
 function getProvinceInfo(){
	 $("#ag_province").html("").append("<option value=''>请选择省份</option>");
	 getData("1",null,function(data){
		 $.each(data.dataList,function(index,dom){
			 var opt = "<option value= '"+dom.areaid+"'>"+dom.cname+"</option>";
			 if(dom.areaid+'' == '${data.provinceid}'){
				 opt = "<option selected='selected' value= '"+dom.areaid+"'>"+dom.cname+"</option>";
			 }
			 $("#ag_province").append(opt);
		 }); 
		 getCityInfo();
	 });
 }
 
 
 function getCityInfo(){
	 $("#ag_city").html("").append("<option value=''>请选择城市</option>");
	 if($("#ag_province").val() == ""){
		 getDistrictInfo();
		 return;
	 }
	   getData(null,$("#ag_province").val(),function(data){
		  $.each(data.dataList,function(index,dom){
			 var opt = "<option value= '"+dom.areaid+"'>"+dom.cname+"</option>";
			 if(dom.areaid+'' == '${data.cityid}'){
				 opt = "<option selected='selected' value= '"+dom.areaid+"'>"+dom.cname+"</option>";
			 }
			 $("#ag_city").append(opt);
		 }); 
		  getDistrictInfo();
	 });  
 }
 
 function getDistrictInfo(){
	 $("#ag_district").html("").append("<option value=''>请选择地区</option>");
	 if($("#ag_city").val() == ""){
		 return;
	 }
	  getData(null,$("#ag_city").val(),function(data){
		 $.each(data.dataList,function(index,dom){
			 var opt = "<option value= '"+dom.areaid+"'>"+dom.cname+"</option>";
			 if(dom.areaid+'' == '${data.districtid}'){
				 opt = "<option selected='selected' value= '"+dom.areaid+"'>"+dom.cname+"</option>";
			 }
			 $("#ag_district").append(opt);
		 }); 
	 });
 }
	function checkUserName(call){
		if($("#username").val()==null||$("#username").val()==""){
			return;
		}
		var url = '<%=request.getContextPath()%>/system/validateUsername?username='+$("#username").val();
		hhutil.ajax(url,null,function(data){
			if(!data){
				 $.gritter.add({
	 		            title: '提示',
	 		            text: '该账号已经被人注册过了',
	 		            sticky: false,
	 		            time: '3000'
	 		        });
			$("#tp").val("0");
			}else{
				$("#tp").val("1");
				call();
			} 
		});
	}
 
 function subForm(){
	 
	 if($("#tp").val()+"" == "0"){
		 $.gritter.add({
	            title: '提示',
	            text: '该账号已经被人注册过了',
	            sticky: false,
	            time: '3000'
	        });
		 return false;
	 }
	 
	 var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
		if(!myreg.test($("#phonenum").val())) 
		{ 
			$.gritter.add({
	            title: '提示信息',
	            text: '请输入有效的联系方式',
	            sticky: false,
	            time: '3000'
	        });
			$('#phonenum').focus();
			return false;
		} 
	 
	 if($("#username").val() =="" || $("#password").val() ==""||  $("#agencyname").val() =="" || $("#phonenum").val()==null|| $("#contacter").val()=="" ||  $("#address").val() == "" ||  $("#commissionrate").val()==""){
		 $.gritter.add({
	            title: '提示信息',
	            text: '请完善资料再提交',
	            sticky: false,
	            time: '3000'
	        });
			$('#phonenum').focus();
			return false;
		 return false;
	 }
	 
	 var agencyImg="";
		$(":input[name='agencyimg']").each(function(index,dom){
			agencyImg+=$(this).val()+",";
		});
	 
	var dt =  hhutil.getFormBean("editForm");
	    dt.agencyImg = agencyImg;
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/system/agency/updateAgency",
		data:dt,
		success:function(data){
			if(data){
				window.location.href="<%=request.getContextPath()%>/system/agency/agencyManager";
			}
				
		}
	});
	
	return false;
 
 }
</SCRIPT>
 
</body>
</html>