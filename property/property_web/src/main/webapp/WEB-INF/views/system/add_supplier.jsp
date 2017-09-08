<%@page import="com.qiniu.util.AuthConstant"%>
<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>供应商管理</title>
<link href="<%=request.getContextPath()%>/js/z-tree/css/demo.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/js/z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<!-- 百度地图API -->
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
<!-- 百度地图结束 -->

</head>
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-suppe p-suppe-manage">
<div class="wrapper">
<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include>
<div class="content">
        <div class="crumb">
            <h5>供应商管理&#187; 新增供应商</h5>
        </div>
        <div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>新增供应商</span></h3>
                    <div class="col-md-12">
                        <form class="form-horizontal parsley-form" onsubmit="return commitForm();" id="editForm" role="form" action="<%=request.getContextPath() %>/system/supplier/updatesupperinfo">
                        	<input type="hidden" name="supplierid" id="supplierid" value="${suppliermap.supplierid}">
                        	<input type="hidden" name="sourcepid" id="sourcepid" value="${suppliermap.sourcepid}">
                        	
                            <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i>供应商名称</label>
                                <div class="col-md-5">
                                    <input type="text" name="suppliername" id="suppliername" value="${suppliermap.suppliername}"  class="form-control parsley-validated" data-required="true">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i>供应商logo</label>
                                <div class="col-md-5">
                                    <img src="${suppliermap.supplierlog_show }" onclick="imgutil.FDIMG(this)" id="supplier_log" width="60px" height="60px"/>
                                    <input type="file" name="file" style="display: none" id="supplog_upload" />
                                    <input type="text" id="supplierlog" name="supplierlog" value="${suppliermap.supplierlog}" class="form-control parsley-validated" style="display: none">
							        <input type="button"  class="form-control" style="width:50px" onclick="$('#supplog_upload').click();" value="上传">
                                </div>
                            </div>
                            
                            <div class="form-group" >
				                <label class="col-md-2 control-label">所属代理商</label>
				                <div class="col-md-5">
				                  <input type="text" id="agency_parent_name"  value="${suppliermap.agencyname == null?'平台': suppliermap.agencyname}" class="form-control parsley-validated"  >
				                  <input type="hidden" name="agencyid" id="agencyid" value="${data.agencyid}"/>
				                  <ul id="treeDemo"  class="ztree" style="margin-top:0; width:80%;position: absolute;z-index: 999999;height: auto;display:none"></ul>
				                </div>
							 </div> 
                            <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i>所在地区</label>
                                <div class="col-md-5">
				                    <select id="ag_province" name="provinceid" class="form-control" onchange="getCityInfo();" style="width: 30.33%;height: 32px">
				                       
				                   </select>
				                   
				                    <select id="ag_city" name="cityid" class="form-control" onchange="getDistrictInfo();" style="width: 30.33%;height: 32px">
				                      
				                   </select>
				                   
				                    <select id="ag_district" name="districtid" class="form-control" style="width: 30.33%;height: 32px">
				                        
				                   </select>
							              
                                </div>
                                 <div class="col-md-5">
                                    <p class="form-control-static">填写供应商所在省市区</p>
                                    <input type="hidden" id="provincename"  name="provincename"/>
                                    <input type="hidden" id="cityname" name="cityname" />
                                    <input type="hidden" id="districtname" name="districtname" />
                                </div>
                            </div>
                            
                              <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i>详细地址</label>
                                <div class="col-md-5">
                                    <input type="text" name="address" id="address" value="${suppliermap.address}"  class="form-control parsley-validated" data-required="true">
                                </div>
                                 <div class="col-md-5">
                                    <p class="form-control-static">填写供应商详细地址</p>
                                </div>
                            </div>
                            
                             <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i>供应商店面图片</label>
     
						                <div class="col-md-5">
						                   	<input type="file" name="file" style="display: none" id="fileDm_upload" />
							                <span id="room_divdm">
									                 <c:forEach items="${supplierimglist}" var="sup" varStatus="st">
									                 	<c:if test="${sup.type==2}">
									                  		<div style="float: left;width: 50px;margin-left:10px;"><img src="<%=request.getContextPath()%>${sup.sup_url}" onclick="imgutil.FDIMG(this)" class="form-control" id="headimage_img" style="width: 50px; height: 50px;float: left;"/>
									                  		&nbsp;&nbsp;<b onclick="$('#${sup.supplierimgid}').click();" style="cursor:pointer;color:#428bca">删除</b>
									                  		<input type="hidden" name="dm" value="${sup.url}"/>
									                  		<a href="#deleteTip" onclick="deleteData('${sup.supplierimgid}')" id="${sup.supplierimgid}" data-toggle="modal" style="display:none;" data-backdrop="static">删除</a>
									                  		</div>
									                 	</c:if>
									                 </c:forEach>
							                 </span>
							                  <input type="text" id="imgurldm" name="dmimgurl" class="form-control parsley-validated" style="display: none">
							                  <input type="button"  class="form-control" style="margin-left:20px;margin-top:20px;width:50px" onclick="$('#fileDm_upload').click();" value="上传">
						                </div>
                                
                                 <div class="col-md-5">
                                    <p class="form-control-static">上传供应商图片</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i>供应商产品图片</label>
                                		<div class="col-md-5">
						                   	  <input type="file" name="file" style="display: none" id="fileCp_upload" />
							                 <span id="room_divcp">
									                  <c:forEach items="${supplierimglist}" var="sup" varStatus="st">
									                 	<c:if test="${sup.type==1}">
									                 		<div style="float: left;width: 50px;margin-left:10px;"><img src="<%=request.getContextPath()%>${sup.sup_url}" onclick="imgutil.FDIMG(this)"  class="form-control" id="headimage_img" style="width: 50px; height: 50px;float: left;"/>
									                  		&nbsp;&nbsp;<b onclick="$('#${sup.supplierimgid}').click();" style="cursor:pointer;color:#428bca">删除</b>
									                  		<input type="hidden" name="cp" value="${sup.url}"/>
									                  		<a href="#deleteTip" onclick="deleteData('${sup.supplierimgid}')" id="${sup.supplierimgid}" data-toggle="modal" style="display:none;" data-backdrop="static">删除</a>
									                  		</div>
									                  	</c:if>
									                 </c:forEach>
							                 </span>
							                  <input type="text" id="imgurlcp" name="cpimgurl" class="form-control parsley-validated" style="display: none">
							                  <input type="button"  class="form-control"  style="margin-left:20px;margin-top:20px;width:50px" onclick="$('#fileCp_upload').click();" value="上传">
						                </div>
                                 <div class="col-md-5">
                                    <p class="form-control-static">上传供应商产品图片</p>
                                </div>
                            </div>
                           
                             <c:if test="${suppliermap.supplierid !=null }">
                              <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required"></i>经纬度：</label>
                                <div class="col-md-5">
                                	<input type="text" name="longitudelatitude" id="longitudelatitude" value="${suppliermap.longitudelatitude}"   class="form-control parsley-validated"  disabled="disabled">
                                </div>
                                 <!-- <div class="col-md-5">
                                    <p class="form-control-static">请在地图上选择你的位置</p>
                                </div> -->
                            </div>
                            </c:if> 
                            <!--   <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i></label>
                                <div class="col-md-5">
                                	<div id="container" style="width:500px; height:500px;"></div>
                                </div>
                            </div> -->
                            <div class="form-group">
                                <label class="col-md-2 control-label">营业时间</label>
                               <div class="col-md-5">
                                    <input type="text" name="businesshours" id="businesshours"  value="${suppliermap.businesshours}"  class="form-control parsley-validated" data-required="true">
                                </div>
                                 <div class="col-md-5">
                                    <p class="form-control-static">填写营业时间，如：09:00-17:00</p>
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i>联系人</label>
                                <div class="col-md-5">
                                    <input type="text" name="contacter" id="contacter"  value="${suppliermap.contacter}"  class="form-control parsley-validated" data-required="true">
                                </div>
                                 <div class="col-md-5">
                                    <p class="form-control-static">填写供应商联系人</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i>联系方式</label>
                                <div class="col-md-5">
                                    <input type="text" name="telephone" id="telephone" value="${suppliermap.telephone}"  class="form-control parsley-validated" data-required="true">
                                </div>
                                 <div class="col-md-5"> 
                                    <p class="form-control-static">填写供应商联系电话</p>
                                </div>
                            </div>
                             <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i>主营产品</label>
                                <div class="col-md-5">
                                    <textarea name="products" class="form-control"  rows="3" data-required="true">${suppliermap.products}</textarea>
                                </div>
                                 <div class="col-md-5">
                                    <p class="form-control-static">填写供应商主营产品</p>
                                </div>
                            </div>
                           <div class="form-group">
                                <label class="col-md-2 control-label"><i class="required">*</i>外链地址</label>
                                <div class="col-md-5">
                                    <input type="text" name="outsideurl" id="outsideurl" value="${suppliermap.outsideurl}" class="form-control parsley-validated" data-required="true">
                                </div>
                                 <div class="col-md-5">
                                    <p class="form-control-static">填写供应商外链地址</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-5 col-md-offset-2">
                                    <button type="submit"   class="btn btn-save"><i class="ico-ok"></i>保 存</button>
                                    <a href="javascript:history.go(-1)" class="btn btn-close">返 回</a>
                                </div>
                            </div>
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
		<input type="hidden" id="supplierimgid" name="supplierimgid" />
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

              
<script type="text/javascript">
 
$(function(){
 getProvinceInfo();
 $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$('#agency_parent_name').click(function(){
		$("#treeDemo").toggle();
	});
	
	 $('#supplog_upload').fileupload({     
			url:'http://up.qiniu.com',   
			formData:{
				'token'	: '<%=AuthConstant.getToken() %>'  
			},
			type:'POST',
			maxNumberOfFiles:1,
			autoUpload:true,
		    dataType: 'json',
		    acceptFileTypes:  /(\.|\/)(gif|jpe?g|png)$/i, 
		    maxFileSize: 5000000, 
		    done: function (e, data) {
		    	$.ajax({
					url:'<%=request.getContextPath() %>/upload/downimg/wx/'+data.result.key,
					success:function(data1){
						$("#supplierlog").val(data.result.key);
						$("#supplier_log").attr("src",data1);
					}  
				});
		    },
		    progressall: function (e, data) {
		    	 
		    }
		});
	
	
	 $('#fileDm_upload').fileupload({    
			url:'http://up.qiniu.com',   
			formData:{
				'token'	: '<%=AuthConstant.getToken() %>'  
			},
			type:'POST',
			maxNumberOfFiles:1,
			autoUpload:true,
		    dataType: 'json',
		    acceptFileTypes:  /(\.|\/)(gif|jpe?g|png)$/i, 
		    maxFileSize: 5000000, 
		    done: function (e, data) {
		    	$.ajax({
					url:'<%=request.getContextPath() %>/upload/downimg/wx/'+data.result.key,
					success:function(data1){
						$('#room_divdm').append('<div style="margin-left:10px;float: left;width: 50px;"><input type="hidden" name="dm" value="'+data.result.key+'"/><img  style="width: 50px;height: 50px;float: left;" onclick="imgutil.FDIMG(this)"  class="form-control" src="'+data1+'">&nbsp;&nbsp;<b onclick="deleteimgs(this)" style="cursor:pointer;color:#428bca">删除</b></div>');
					}  
				});
		    },
		    progressall: function (e, data) {
		    	 
		    }
		});
	 
	 $('#fileCp_upload').fileupload({     
			url:'http://up.qiniu.com',   
			formData:{
				'token'	: '<%=AuthConstant.getToken() %>'  
			},
			type:'POST',
			maxNumberOfFiles:1,
			autoUpload:true,
		    dataType: 'json',
		    acceptFileTypes:  /(\.|\/)(gif|jpe?g|png)$/i, 
		    maxFileSize: 5000000, 
		    done: function (e, data) {
		    	$.ajax({
					url:'<%=request.getContextPath() %>/upload/downimg/wx/'+data.result.key,
					success:function(data1){
						$('#room_divcp').append('<div style="margin-left:10px;float: left;width: 50px;"><input type="hidden" name="cp" value="'+data.result.key+'"/><img  style="width: 50px;height: 50px"  onclick="imgutil.FDIMG(this)" class="form-control" src="'+data1+'">&nbsp;&nbsp;<b onclick="deleteimgs(this)" style="cursor:pointer;color:#428bca">删除</b></div>');
					}  
				});
		    },
		    progressall: function (e, data) {
		    	 
		    }
		});
	 
	 
/* //在百度 map中显示地址
var map = new BMap.Map("container");// 创建地图实例
//根据ip地址定位
var point = new BMap.Point(116.331398,39.897445);
map.centerAndZoom(point,12);
function myFun(result){
	var cityName = result.name;
	map.setCenter(cityName);
} */
/* var myCity = new BMap.LocalCity();
myCity.get(myFun);
//获取经纬度
function showInfo(e){
	$("#longitudelatitude").val(e.point.lng + ", " + e.point.lat);
	var new_point = new BMap.Point(e.point.lng,e.point.lat);
	var marker = new BMap.Marker(new_point);  // 创建标注
	map.addOverlay(marker);              // 将标注添加到地图中
	map.panTo(new_point);     
} */
/* map.addEventListener("click", showInfo);

map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
var localSearch = new BMap.LocalSearch(map);
localSearch.enableAutoViewport(); */
/* //地图加载完成后触发事件
map.addEventListener("tilesloaded",function(){

//当经纬度存在时在地图上标记出来
if($("#longitudelatitude").val()!=""){
	var new_point = new BMap.Point($("#longitudelatitude").val());
	var marker = new BMap.Marker(new_point);  // 创建标注
	map.addOverlay(marker);              // 将标注添加到地图中
	map.panTo(new_point);     
}
}); */

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
	        agencytype:'${row.agencytype}',
	        userid:'${row.userid}'
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
		
		 if (nodes.length > 0) {
				var d = getFilePath(nodes[0]);
				if(d!='' && d!= 'underfined' ){
					$("#sourcepid").val(d);
				}
		 }
			 
		
		nodes.sort(function compare(a,b){return a.id-b.id;}); 
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			if(nodes[i].id+""=="-1"){
				nodes[i].id=0;
			}
			
			id+= nodes[i].id + ",";
		}
		if (v.length > 0 ) 
		v = v.substring(0, v.length-1);
		id = id.substring(0, id.length-1);
		var cityObj = $("#agency_parent_name");
		cityObj.attr("value", v);
		$("#agencyid").val(id);
		$("#treeDemo").hide();
	}
 
	function getFilePath(treeObj){
		if(treeObj == null)return "";
		var sourcepid = treeObj.userid;
		var pNode = treeObj.getParentNode();
		if(pNode != null){
			sourcepid = getFilePath(pNode) +","+ sourcepid;
		}
		return sourcepid;
     }
	
//删除图片
function deleteData(id){
	$("#supplierimgid").val(id);
}	
function deleteimgs(demos){
   $(demos).parent().remove();
}
function deleteimg(){
	var id = $("#supplierimgid").val();
	$("#"+id).css("display","none");
<%-- 	hhutil.ajaxFileUpload("<%=request.getContextPath() %>/supplier/detelesupperimg?supplierimgid="+id+"&delflag=1",function(data){
		alert("成功");
	}); --%>
	url="<%=request.getContextPath() %>/system/supplier/detelesupperimg?supplierimgid="+id+"&delflag=1";
	$.ajax({
		type:"post",
		url:url,
		success:function(data){
			//history.go(0);
			$("#quxiao").click();
		}
	});

}


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
			 if(dom.areaid+'' == '${suppliermap.provinceid}'){
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
			 if(dom.areaid+'' == '${suppliermap.cityid}'){
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
			 if(dom.areaid+'' == '${suppliermap.districtid}'){
				 opt = "<option selected='selected' value= '"+dom.areaid+"'>"+dom.cname+"</option>";
			 }
			 $("#ag_district").append(opt);
		 }); 
		 
	 });
}

function commitForm(){
	if($("#ag_province").val() ==""){
	 
		$.gritter.add({
	            title: '提示信息',
	            text: '请选择所在区域',
	            sticky: false,
	            time: '3000'
	        });
	 
		return false;
	}
	if($('#supplierlog').val()==""){
		$.gritter.add({
            title: '提示信息',
            text: '请上传您的供应商logo',
            sticky: false,
            time: '3000'
        });
 
	return false;
	}
	if($('input[name=dm]').length==0){
		$.gritter.add({
            title: '提示信息',
            text: '请上传您的供应商店面图片',
            sticky: false,
            time: '3000'
        });
 
		return false;
	}
	if($('input[name=cp]').length==0){
		$.gritter.add({
            title: '提示信息',
            text: '请上传您的供应商产品图片',
            sticky: false,
            time: '3000'
        });
 
		return false;
	}
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	if(!myreg.test($("#telephone").val())) 
	{ 
		$.gritter.add({
            title: '提示信息',
            text: '请输入有效的联系方式',
            sticky: false,
            time: '3000'
        });
		$('#telephone').focus();
		return false;
	} 
	var dm ="";
	$(":input[name='dm']").each(function(index,dom){
		dm+=$(this).val()+",";
	});
	var cp ="";
	$(":input[name='cp']").each(function(index,dom){
		cp+=$(this).val()+",";
	});
	
	$('#imgurldm').val(dm);
	$('#imgurlcp').val(cp);
	$("#provincename").val($("#ag_province").find("option:selected").html());
	$("#cityname").val($("#ag_city").find("option:selected").html());
	$("#districtname").val($("#ag_district").find("option:selected").html());
};

</script>
</body>
</html>