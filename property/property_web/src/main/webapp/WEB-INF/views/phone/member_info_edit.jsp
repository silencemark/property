<%@page import="com.qiniu.util.AuthConstant"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" >
<title>个人资料编辑</title>
<link href="<%=request.getContextPath() %>/appcssjs/style/public.css" type="text/css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/appcssjs/style/page.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/jquery-1.10.2.min.js"></script>

<script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script> 
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.iframe-transport.js"></script>
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.fileupload.js"></script> 
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.fileupload-ui.js"></script> 
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.fileupload-process.js"></script>   
<script src="<%=request.getContextPath() %>/js/jqueryfile/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/Labor.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath() %>/sweetalert/dist/sweetalert.css">
<script src="<%=request.getContextPath() %>/sweetalert/dist/sweetalert-dev.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/index.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/appcssjs/scripts/RecordQueryCondition.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/checkCard.js"></script>
</head>
<body name="myinfo">
<!--头部-->
<c:if test="${isweixin == false }">
<div class="headbox" id="head_nav">
  <div class="title f-20">个人资料编辑</div>
    <a href="javascript:history.go(-1)" class="ico_back f-16">返回</a>
    <!-- <a href="#" class="ico_list">更多</a> -->
</div>  
</c:if>
 <!-- 性别 -->
 <div class="menu_list" style="display: none" id="sex_choose">
	<ul>
      <li onclick="chooseSex('1','男')"><a href="javascript:void(0)" ><span>男</span><i>更多</i></a></li>
      <li onclick="chooseSex('0','女')"><a href="javascript:void(0)" ><span>女</span><i>更多</i></a></li>
      <li onclick="chooseSex('2','保密')"><a href="javascript:void(0)" ><span>保密</span><i>更多</i></a></li>
   </ul>
</div>
<!-- 工种行业 -->
<div class="menu_list" style="display: none" id="worktype">
	<ul>
		<c:forEach items="${industrylist}" var="item">
    		<li><a href="javascript:void(0)" onclick="getnextdict('${item.dataid}','${item.cname}')"><span>${item.cname}</span><i>更多</i></a></li>
    	</c:forEach>
    </ul>
</div>
<div class="menu_list" style="display: none" id="worktype2">
	<ul id="twotype">
    </ul>
</div>

<div class="menu_list" style="display: none" id="workarea">
	<ul>
		<c:forEach items="${areaList}" var="item">
    		<li><a href="javascript:void(0)" onclick="getnextarea('${item.areaid}','${item.cname}')"><span>${item.cname}</span><i>更多</i></a></li>
    	</c:forEach>
    </ul>
</div>
<!-- <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	$(function(){
		$('#headimage_a').click(function(){
			alert("1");
			wx.chooseImage({
				success:function(res){
					alert("2");
					var localIds=res.localIds;
					syncUpload(localIds);
				}
			})
		})
		var synUpload= function(localIds){
			alert("3");
			var localId=localIds.pop();
			wx.uploadImage({
				localId:localId,
				isShowProgressTips:1,
				success:function(res){
					alert("4");
					var serverId =res.serverId;
					if(localIds.length > 0){
						alert("5");
						syncUpload(localIds);
					}
				}
			})
		}
	})
</script> -->
 <form  id="editForm">
 <input type="hidden" name ="memberid" value="${data.memberid }"/>
 <input type="hidden" name ="perfectdegree" />
 
 <div class="info_box from_publish">
	<div style="background-color: #F4F4F4"  class="t_name">个人资料</div>
    <ul>
    	<li>
    	  <span>头像</span>
    	   <c:choose>
        		<c:when test="${isweixin==true}">
        			<a href="javascript:void(0)" onclick="javascript:$('#headimage_upload').click();" id="headimage_a">
        		</c:when>
        		<c:otherwise>
        			<c:choose>
        				<c:when test="${isAndroidFrom==true}">
		        			<a href="javascript:void(0)" onclick="upload('headimage_a',1)" id="headimage_a">
		        		</c:when>
		        		<c:otherwise><a href="javascript:void(0)" onclick="uploadImg('headimage_a',1)" id="headimage_a"></c:otherwise>
        			</c:choose>
        		</c:otherwise>
        	</c:choose>
    	  <b><img src="${data.headimage == null?'/appcssjs/images/page/pic_bg.png':data.headimage_show }" width="36" height="36"><input type="hidden" name="headimage" value="${data.headimage }"/></b></a>
    	  <input style="display: none"  type="file" name="file" id="headimage_upload"/> 
    	  <div class="clear"></div>
       </li>
        <li>
           <span><font color="red">*</font>昵称</span> <input type="text"    placeholder="请输入昵称" class="text_01" value="${data.nickname }" name="nickname"  oninput="on_input(this,'nickname');" id="nickname" maxlength="16">
           <div class="clear"></div>
        </li>
        <li>
          <span><font color="red">*</font>姓名</span><input type="text"      placeholder="请输入姓名" class="text_01" value="${data.realname }" oninput="on_input(this,'realname');" name="realname" id="realname" maxlength="10">
          <div class="clear"></div>
        </li>
        <li id="sex_box">
          <span><font color="red">*</font>性别</span>
             <a href="javascript:void(0)" >
               <c:if test="${data.sex == 1 }">男</c:if>
               <c:if test="${data.sex == 0 }">女</c:if>
               <c:if test="${data.sex == 2 }">保密</c:if>
             </a>
             <input type="hidden" name="sex" id="sex" value="${data.sex }"/>
          <div class="clear"></div>
        </li>
         <li>
        	<span><font color="red">*</font>所在地区</span>
            <i class="sel" onclick="getworkarea()" id="areatxt">${data.provincename }&nbsp;${data.cityname }</i>
               <input type="hidden" name="provinceid" value="${data.provinceid }"/>
               <input type="hidden" name="cityid" value="${data.cityid }"/>
            <div class="clear"></div>
       	</li>
        <li>
        	<span><font color="red">*</font>所属行业</span>
        	<i class="sel" onclick="getworktype()" id="jobwork">${data.cname==null?'请选择':data.cname}</i><input value="${data.jobtype }" type="hidden" name="jobtype" id="jobtype"/>
            <div class="clear"></div>
       	</li> 
        
        <li>
          <span><font color="red">*</font>手机号码</span><input type="tel"   oninput="on_input(this,'phone');" data-type="num"  placeholder="请输入手机号码" class="text_01" value="${data.phone }" name="phone" id="phone" maxlength="11">
          <div class="clear"></div>
        </li>
        <li style="background-color: #F4F4F4;font-size: 12px">请输入真实的身份证信息，并上传身份证正反面照片（2张照片）</li>
        <li>
          <span><font color="red">*</font>身份证号</span><input type="tel" data-type="num" oninput="on_input(this,'idcard');"  placeholder="请输入身份证号" class="text_01" value="${data.idcard }" name="idcard" id="idcard" maxlength="18">
          <div class="clear"></div>
        </li>
        <li>
          <span><font color="red">*</font>身份证照</span><span style="font-size: 12px">（2张照片）</span>
          <c:choose>  
        		<c:when test="${isweixin==true}">
        			<a href="javascript:void(0)" onclick="uploadIdCard()">上传</a>
        		</c:when>
        		<c:otherwise>
        			<c:choose>
        				<c:when test="${isAndroidFrom==true}">
		        			<a href="javascript:void(0)" onclick="uploadIdcardImg('idcardimgurl_img_box')" id="idcardimgid">上传</a>
		        		</c:when>
		        		<c:otherwise>
		        		  <a href="javascript:void(0)" onclick="uploadIdcardImg('idcardimgurl_img_box')" id="idcardimgid">上传</a>
		        		</c:otherwise>
        			</c:choose>
        		</c:otherwise>
        	</c:choose>
          <input style="display: none"  type="file" name="file" id="idcardimgurl_upload"/> 
    	  <div class="clear"></div>
    	  <div class="pic_box" style="border: 0" id="idcardimgurl_img_box"> 
             <c:forEach items="${data.idCardImgList }" var = "row" > 
	             <b>
	             	<input type='hidden' name='idcardimg' value='${row.url}'/>
	                <img onclick="imgutil.FDIMG(this)" src='${row.idcardimgurl_show}' width='52' height='52'>
	                <em onclick="deleteimg(this,'idcard');">删除</em>
	             </b>
             </c:forEach>
          </div> 
          <div class="clear"></div>
        </li>
        <li>
          <span>个人介绍</span> 
          <div class="clear"></div>
        </li>
        <li>
        	<div class="text_area"><textarea  maxlength="800"  placeholder="请输入个人介绍..." id="personalintroduction" oninput="on_input(this,'personalintroduction');" name="personalintroduction"  class="text_02">${data.personalintroduction }</textarea></div>
        </li>
        <li>
          <span>个人照片</span><span style="font-size: 12px">（最多9张）</span>
    	  <c:choose>
        		<c:when test="${isweixin==true}">
        			<a href="javascript:void(0)" onclick="uploadPersonalImg()">上传</a>
        		</c:when>
        		<c:otherwise>
        			<c:choose>
        				<c:when test="${isAndroidFrom==true}">
		        			<a href="javascript:void(0)" onclick="upload_personal_img('personalimgurl_img_box')" id="personalimgid">上传</a>
		        		</c:when>
		        		<c:otherwise><a href="javascript:void(0)" onclick="upload_personal_img('personalimgurl_img_box')" id="personalimgid">上传</a></c:otherwise>
        			</c:choose>
        		</c:otherwise>
        	</c:choose>
          <input style="display: none"  type="file" name="file" id="personalimgurl_upload"/> 
    	  <div class="clear"></div>
    	  <div class="pic_box" style="border: 0" id="personalimgurl_img_box">
             <c:forEach items="${data.personalImgList }" var = "row" > 
	             <b>
	             	<input type='hidden' name='personalimg' value='${row.url}'/>
	                <img  onclick="imgutil.FDIMG(this)" src='${row.personal_show}' width='52' height='52'>
	                <em onclick="deleteimg(this);">删除</em>
	             </b>
             </c:forEach>
          </div> 
          <div class="clear"></div>
        </li>
    </ul>
    <div style="background-color: #F4F4F4" class="t_name">公司信息</div>
    <ul>
    	<li>
    	  <span>公司名称</span><input type="text"  placeholder="请输入公司名称" class="text_01" oninput="on_input(this,'companyname');" value="${data.companyname }" id="companyname" name="companyname" maxlength="36">
    	  <div class="clear"></div>
    	</li>
        <li>
          <span>营业执照</span>
    	  <c:choose>
        		<c:when test="${isweixin==true}">
        			<a  href="javascript:void(0)" onclick="javascript:$('#companyimgurl_upload').click();">上传</a>
        		</c:when>
        		<c:otherwise>
        			<c:choose>
        				<c:when test="${isAndroidFrom==true}">
		        			<a href="javascript:void(0)" onclick="upload('companyimgurl_img_box',1)">上传</a>
		        		</c:when>
		        		<c:otherwise><a href="javascript:void(0)" onclick="uploadImg('companyimgurl_img_box',1)">上传</a></c:otherwise>
        			</c:choose>
        		</c:otherwise>
        	</c:choose>
          <input style="display: none"  type="file" name="file" id="companyimgurl_upload"/> 
    	  <div class="clear"></div>
    	  <div class="pic_box" style="border: 0px" id="companyimgurl_img_box">
             <b><input type='hidden' name='companyimgurl' id="companyimgurl_value" value='${data.companyimgurl }'/><img   onclick="imgutil.FDIMG(this)" src='${data.companyimgurl_show}' width='52' height='52'></b>
          </div> 
           <div class="clear"></div>
        </li>
        <li>
          <span>公司介绍</span>
          <div class="clear"></div>
        </li>
        <li>
        	<div class="text_area"><textarea  maxlength="800" oninput="on_input(this,'companyintroduction');" placeholder="请输入公司介绍..." id="companyintroduction" name="companyintroduction"   class="text_02">${data.companyintroduction }</textarea></div>
        </li>
    </ul>
</div>
<div class="main_bigbtn"><input type="button" onclick="subBaseInfo();" value="保存"></div>
</form>
<script type="text/javascript">
  
//存redis
function intoRedis(data){
	data.key='${key}';
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/member/addMemberToRedis",
		data:data,
		success:function(){
			
		}
		
	});
}

function on_input(obj,name){
   var dt = new Object;
   dt[name]=$(obj).val();
   intoRedis(dt);
}

function uploadIdcardImg(id){
	var imgcount = $("input[name='idcardimg']").length;
	if("${isAndroidFrom}" == "true"){
		upload(id,parseInt(2-imgcount));
	}else{
		uploadImg(id,parseInt(2-imgcount));
	}
}

function upload_personal_img(id){
	var imgcount = $("input[name='personalimg']").length;
	if("${isAndroidFrom}" == "true"){
		upload(id,parseInt(9-imgcount));
	}else{
		uploadImg(id,parseInt(9-imgcount));
	}
}


function getworkarea(){
	$("#editForm").hide();
	$("#head_nav").hide();
	$('#workarea').show();
}
function getnextarea(dataid,cname){
	$("#twotype").html("");
	$('#workarea').hide();
	$('#worktype2').show();
	$.ajax({
		type:"post",
		dataType:"json",
		url:"<%=request.getContextPath()%>/employer/getnextarea?areaid="+dataid,
		success:function(data){
			if(data.length> 0){
				var temp="";
				for(var i=0;i<data.length;i++){
					//temp+="<li><a href=\"javascript:void(0)\" onclick=\"getnextarea(\'"+data[i].areaid+"\',\'"+data[i].cname+"\')\"><span>"+data[i].cname+"</span><i>更多</i></a></li>";
					temp+="<li><a href=\"javascript:void(0)\" onclick=\"getInfo(\'"+dataid+"\',\'"+cname+"\',\'"+data[i].areaid+"\',\'"+data[i].cname+"\')\"><span>"+data[i].cname+"</span><i>更多</i></a></li>";
				}
				$("#twotype").html(temp);
			}else{
				$('#areatxt').text(cname);
				$("#head_nav").show(); 
				$('#editForm').show();
				$('#workarea').hide();
				$('#worktype2').hide();
			}
		}
	});
}
function getInfo(provinceid,provincename,cityid,cityname){
	$('#areatxt').text(provincename+" "+cityname);
	$("#head_nav").show(); 
	$('#editForm').show();
	$('#workarea').hide();
	$('#worktype2').hide();
	$("input[name='cityid']").val(cityid);
	$("input[name='provinceid']").val(provinceid);
	 
	var dt = {
		cityid:cityid,
		provinceid:provinceid,
		cityname:cityname,
		provincename:provincename
	};
	
	intoRedis(dt);
	
}
/* var filenum=1;
var idCardsize = $("#idcardimgurl_img_box img").length;
if(idCardsize!=0){
	filenum=1+idCardsize;
}
var filenum1=1;
var personalImgsize = $("#personalimgurl_img_box img").length;
if(personalImgsize!=0){
	filenum1=1+personalImgsize;
} */
/*
 oninput="value=value.replace(/[^\a-\z\A-\Z0-9\u4E00-\u9FA5]/g,'')"
*/
function uploadcall(data){
	var id=data.id;
	var keylist=(data.keys).split(",");
	if(data.id=="headimage_a"){
		$('#headimage_a').html(""); 
    	$.ajax({
			url:'<%=request.getContextPath() %>/upload/downimg/wx/'+keylist[0],
			success:function(data1){
				$('#headimage_a').html("<b><input type='hidden' name='headimage' value='"+keylist[0]+"'/><img src='"+data1+"' width='36' height='36'></b>");
				var dt= {
						headimage:keylist[0],
						headimage_show:data1
				};
				intoRedis(dt);
			}  
		});
	}else if(data.id=="idcardimgurl_img_box"){
		for(var i=0;i<keylist.length;i++){
			var keyvalue=keylist[i];
			$.ajax({
				url:'<%=request.getContextPath() %>/upload/downimg/wx/'+keyvalue,
				success:function(data1){
					$('#idcardimgurl_img_box').append("<b><input type='hidden' name='idcardimg' value='"+keyvalue+"'/><img onclick=\"imgutil.FDIMG(this)\" src='"+data1+"' width='52' height='52'><em onclick=\'deleteimg(this,'idcard');\'>删除</em></b>");
					$("#idcardimgurl_img_box").show();
					$("#idcardimgid").attr("onclick","uploadIdcardImg('idcardimgurl_img_box')");
				}  
			});
		}
		var array = new Array();
		$("input[name='idcardimg']").each(function(index,dom){
			   t = {
		            idcardimgurl_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
		            name: "身份证照片",
		            delflag: 0,
		            url: $(this).val(),
		            memberimgid: "${data.memberid}"
		        };
			   array.push(t);
            
    	 });
	          var dt = {
	            idCardImgList:JSON.stringify(array)  
	          }; 
	          intoRedis(dt);
		
		
	}else if(data.id=="personalimgurl_img_box"){
		for(var i=0;i<keylist.length;i++){
			var keyvalue=keylist[i]; 
			$.ajax({
				url:'<%=request.getContextPath() %>/upload/downimg/wx/'+keylist[i],
				success:function(data1){
					$('#personalimgurl_img_box').append("<b><input type='hidden' name='personalimg' value='"+keyvalue+"'/><img onclick=\"imgutil.FDIMG(this)\" src='"+data1+"' width='52' height='52'><em onclick=\'deleteimg(this);\'>删除</em></b>");
					$("#personalimgurl_img_box").show();
					$("#personalimgid").attr("onclick","upload_personal_img('personalimgurl_img_box')");
				}  
			});
		}
		
		var array = new Array();
		$("input[name='personalimg']").each(function(index,dom){
			   t = {
					personal_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
		            name: "个人照片",
		            delflag: 0,
		            url: $(this).val(),
		            memberimgid: "${data.memberid}"
		        };
			   array.push(t);
            
    	 });
	          var dt = {
	        	 personalImgList:JSON.stringify(array)  
	          }; 
	          intoRedis(dt);
		
		
	}else if(data.id=="companyimgurl_img_box"){
		$.ajax({
			url:'<%=request.getContextPath() %>/upload/downimg/wx/'+keylist[0],
			success:function(data1){
				 $("#companyimgurl_value").val(keylist[0]).next().attr("src",data1);
				 $("#companyimgurl_img_box").show();
				 var dt = new Object();
		    	  dt.companyimgurl=keylist[0];
		    	  dt.companyimgurl_show = data1;
		    	  intoRedis(dt);
			}  
		});
	}
}


function  upLoadIosImageKey(data){
	   var keylist=(data.keys).split(",");
	/*    for(var i=0;i<keylist.length;i++){
		   $('#'+data.id).before("<b><input type='hidden' name='projectimg' value='"+keylist[i]+"'/><img src='http://7xqoy0.com1.z0.glb.clouddn.com/"+keylist[i]+"' width='52' height='52'><em onclick=\'deleteimg(this);\'>删除</em></b>");
		   var imgcount=$('input[name=projectimg]').length;
		   $("#updateimgid").attr("onclick","uploadImg('projectimgs',"+(9-imgcount)+")"); 
  } */
	   if(data.id+"" == "headimage_a"){
		    $('#headimage_a').html(""); 
			$('#'+data.id).html("<b><input type='hidden' name='headimage' value='"+keylist[0]+"'/><img src='http://7xqoy0.com1.z0.glb.clouddn.com/"+keylist[0]+"' width='36' height='36'></b>");
			var dt= {
					headimage:keylist[0],
					headimage_show:"http://7xqoy0.com1.z0.glb.clouddn.com/"+keylist[0]
			};
			intoRedis(dt);
			
			
	   }else if(data.id+"" == "idcardimgurl_img_box"){
			for(var i=0;i<keylist.length;i++){
						$('#idcardimgurl_img_box').append("<b><input type='hidden' name='idcardimg' value='"+keylist[i]+"'/><img onclick=\"imgutil.FDIMG(this)\" src='http://7xqoy0.com1.z0.glb.clouddn.com/"+keylist[i]+"' width='52' height='52'><em onclick=\'deleteimg(this,'idcard');\'>删除</em></b>");
						$("#idcardimgurl_img_box").show();
						$("#idcardimgid").attr("onclick","uploadIdcardImg('idcardimgurl_img_box')");
			}

			var array = new Array();
			$("input[name='idcardimg']").each(function(index,dom){
				   t = {
			            idcardimgurl_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
			            name: "身份证照片",
			            delflag: 0,
			            url: $(this).val(),
			            memberimgid: "${data.memberid}"
			        };
				   array.push(t);
                
	    	 });
		          var dt = {
		            idCardImgList:JSON.stringify(array)  
		          }; 
		          intoRedis(dt);
			
			
		}else if(data.id=="personalimgurl_img_box"){
			for(var i=0;i<keylist.length;i++){
				 
						$('#personalimgurl_img_box').append("<b><input type='hidden' name='personalimg' value='"+keylist[i]+"'/><img onclick=\"imgutil.FDIMG(this)\" src='http://7xqoy0.com1.z0.glb.clouddn.com/"+keylist[i]+"' width='52' height='52'><em onclick=\'deleteimg(this);\'>删除</em></b>");
						$("#personalimgurl_img_box").show();
						$("#personalimgid").attr("onclick","upload_personal_img('personalimgurl_img_box')");
				 
			}
			
			var array = new Array();
			$("input[name='personalimg']").each(function(index,dom){
				   t = {
						personal_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
			            name: "个人照片",
			            delflag: 0,
			            url: $(this).val(),
			            memberimgid: "${data.memberid}"
			        };
				   array.push(t);
	            
	    	 });
		          var dt = {
		              personalImgList:JSON.stringify(array)  
		          }; 
		          intoRedis(dt);
		}else if(data.id=="companyimgurl_img_box"){
					 $("#companyimgurl_value").val(keylist[0]).next().attr("src","http://7xqoy0.com1.z0.glb.clouddn.com/"+keylist[0]);
					 $("#companyimgurl_img_box").show();
					 var dt = new Object();
			    	  dt.companyimgurl=keylist[0];
			    	  dt.companyimgurl_show = "http://7xqoy0.com1.z0.glb.clouddn.com/"+keylist[0];
			    	  intoRedis(dt);
		} 
	   
	   
	   
}
 

 $(function(){
   $("#nickname").blur(function(){
	   if($(this).val().trim() !=  ""  ){
		   /* || /^[u4e00-\u9fa5A-Za-z0-9-_]*$/.test($(this).val()) == false */
		 /*   swal({
				title : "",
				text : "昵称 只能由数字 、字母、下划线、-或者中文字符组成！",
				type : "",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				$("#nickname").focus(); 
			});	
	  */   
	      /*  var dt = new Object;
	       dt.nickname=$(this).val().trim();
		   intoRedis(dt); */
      }
   });
 
 
   $("#realname").blur(function(){
	   if($(this).val() == "" || $(this).val().match(/[^\u4e00-\u9fa5\a-z\A-Z]/g)){
		   swal({
				title : "",
				text : "姓名只能由字母或中文字符组成！",
				type : "",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				  
			});	
	   } 
   });
   
   /* $("#realname").blur(function(){
	   if($(this).val() == "" || $(this).val().match(/[^\u4e00-\u9fa5\a-z\A-Z]/g)){
		   swal({
 				title : "",
 				text : "姓名只能由字母或中文字符组成！",
				type : "",
 				showCancelButton : false,
 				confirmButtonColor : "#ff7922",
 				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				$("#realname").focus(); 
			});	
	   }
   }); */
   
   $("#phone").blur(function(){
     checkPhone(function(data){
	      if(data+"" == "1"){
	    	   swal({
	 				title : "",
	 				text : "您输入的手机号已经被人注册了！",
					type : "",
	 				showCancelButton : false,
	 				confirmButtonColor : "#ff7922",
	 				confirmButtonText : "确认",
					closeOnConfirm : true
				}, function(){
					 
				});	
	      } 
     });
   });
   
   $("#idcard").blur(function(){
	   if($(this).val() != "" && /^\d{15}|\d{}18$/.test($(this).val()) == false){
		   swal({
				title : "",
				text : "身份证号只能由15或者18位数字组成！",
				type : "",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				 
			});	
	   } 
   });
   
   $("#personalintroduction").blur(function(){
	   if($(this).val() != "" && /^[u4e00-\u9fa5\w,!:;""\/''，：；！‘’。.“”\s@#$%^&*()_+=！#￥……（）——+-~？?《》]*$/.test($(this).val()) == false){
		   swal({
				title : "",
				text : "个人简介不能包含特殊字符,如表情等！",
				type : "",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				  
			});	
	   } 
   });
   
   $("#companyname").blur(function(){
	   if($(this).val() !=""  &&  /^[u4e00-\u9fa5A-Za-z]*$/.test($(this).val()) == false){
		   swal({
				title : "",
				text : "公司名称只能由字母、数字或中文字符组成！",
				type : "",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				  
			});	
	   } 
   });
   
	 
   $("#companyintroduction").blur(function(){
	   if($(this).val() != "" && /^[u4e00-\u9fa5\w,!:;""\/''，：；！‘’。.“”\s@#$%^&*()_+=！#￥……（）——+-~？?《》]*$/.test($(this).val()) == false){
		   swal({
				title : "",
				text : "公司简介不能包含特殊字符,如表情等！",
				type : "",
				showCancelButton : false,
				confirmButtonColor : "#ff7922",
				confirmButtonText : "确认",
				closeOnConfirm : true
			}, function(){
				 
			});	
	   } 
   });
   
   
   
	 
	 $("#sex_box").click(function(){
		 $("#editForm").hide();
		 $("#head_nav").hide();
		 $("#sex_choose").show();
	 });
	 
	 if('${data.companyimgurl}' == ''){
		 $("#companyimgurl_img_box").hide();
	 }
	
	 $('#headimage_upload').fileupload({    
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
		    	$('#headimage_a').html(""); 
		    	$.ajax({
					url:'<%=request.getContextPath() %>/upload/downimg/wx/'+data.result.key,
					success:function(data1){
						$('#headimage_a').html("<b><input type='hidden' name='headimage' value='"+data.result.key+"'/><img  src='"+data1+"' width='36' height='36'></b>");
						var dt= {
								headimage:data.result.key,
								headimage_show:data1
						};
						intoRedis(dt);
					}  
				});
		    },
		    progressall: function (e, data) {
		    	 
		    }
		}); 
	
	 $('#idcardimgurl_upload').fileupload({    
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
							$('#idcardimgurl_img_box').append("<b><input type='hidden' name='idcardimg' value='"+data.result.key+"'/><img onclick=\"imgutil.FDIMG(this)\" src='"+data1+"' width='52' height='52'><em onclick=\"deleteimg(this,'idcard');\">删除</em></b>");
							$("#idcardimgurl_img_box").show();
							var array = new Array();
							$("input[name='idcardimg']").each(function(index,dom){
								   t = {
							            idcardimgurl_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
							            name: "身份证照片",
							            delflag: 0,
							            url: $(this).val(),
							            memberimgid: "${data.memberid}"
							        };
								   array.push(t);
                                
					    	 });
						          var dt = {
						            idCardImgList:JSON.stringify(array)  
						          }; 
						          intoRedis(dt);
						          
						}
					});
		     
		    },
		    progressall: function (e, data) {
		     
		    }
		}); 
	 
	 $('#personalimgurl_upload').fileupload({    
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
							$('#personalimgurl_img_box').append("<b><input type='hidden' name='personalimg' value='"+data.result.key+"'/><img onclick=\"imgutil.FDIMG(this)\" src='"+data1+"' width='52' height='52'><em onclick=\'deleteimg(this);\'>删除</em></b>");
							$("#personalimgurl_img_box").show();
							var array = new Array();
							$("input[name='personalimg']").each(function(index,dom){
								   t = {
										personal_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
							            name: "个人照片",
							            delflag: 0,
							            url: $(this).val(),
							            memberimgid: "${data.memberid}"
							        };
								   array.push(t); 
					            
					    	 });
						          var dt = {
						        	personalImgList:JSON.stringify(array)  
						          }; 
						          intoRedis(dt);
						}  
					});
		    	 
		    },
		    progressall: function (e, data) {
		     
		    }
		}); 
	 $('#companyimgurl_upload').fileupload({    
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
						 $("#companyimgurl_value").val(data.result.key).next().attr("src",data1);
						 $("#companyimgurl_img_box").show();
						 var dt = new Object();
				    	  dt.companyimgurl=data.result.key;
				    	  dt.companyimgurl_show = data1;
				    	  intoRedis(dt);
					}  
				});
		    },
		    progressall: function (e, data) {
		     
		    }
		});
 });
 
 function checkPhone(call){
	 $.ajax({
		url:"<%=request.getContextPath() %>/member/checkPhone",
		type:"post",
		data:{memberphone:$("#phone").val()},
		success:function(data){
			call(data);
		}
		 
	 });
	 
 }
 
 //上传身份证图片
 function uploadIdCard(){
	 var idCardLength = $("#idcardimgurl_img_box img").length;
	 if(idCardLength < 2){
	 $('#idcardimgurl_upload').click();
   }else{
	   swal({
			title : "",
			text : "最多上传2张身份证照片！",
			type : "info",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function(){
			 
		});	
   }
 } 
 //上传个人照片
 function uploadPersonalImg(){
	 var personalImgLength = $("#personalimgurl_img_box img").length;
	 if(personalImgLength < 9){
	 $('#personalimgurl_upload').click();
   }else{
	   swal({
			title : "",
			text : "最多上传9张个人照片！",
			type : "info",
			showCancelButton : false,
			confirmButtonColor : "#ff7922",
			confirmButtonText : "确认",
			closeOnConfirm : true
		}, function(){
			 
		});	
   }
 }
 
 function chooseSex(sex,txt){
	 $("#editForm").show();
	 $("#head_nav").show();
	 $("#sex_choose").hide();
	 $("#sex_box").find("a").html(txt).next().val(sex);
	 var dt = new Object;
	 dt.sex = sex;
	 intoRedis(dt);  
 }
 
function getnextdict(dataid,cname){
		$("#twotype").html("");
		$('#worktype').hide();
		$('#worktype2').show();
		$.ajax({
			type:"post",
			dataType:"json",
			url:"<%=request.getContextPath()%>/employer/getnextdict?dataid="+dataid,
			success:function(data){
				if(data.length> 0){
					var temp="";
					for(var i=0;i<data.length;i++){
						temp+="<li><a href=\"javascript:void(0)\" onclick=\"chooseworktype(\'"+data[i].dataid+"\',\'"+data[i].cname+"\')\"><span>"+data[i].cname+"</span><i>更多</i></a></li>";
					}
					$("#twotype").html(temp);
				}else{
					$('#jobwork').text(cname);
					$('#jobtype').val(dataid);
					$('#editForm').show();
					$('#worktype').hide();
				}
			}
		});
	}
function getworktype(){
	$('#editForm').hide();
	$('#worktype').show();
	$("#head_nav").hide();
}
function chooseworktype(dataid,cname){
	$('#jobwork').text(cname);
	$('#jobtype').val(dataid);
	$('#editForm').show();
	$("#head_nav").show();
	$('#worktype2').hide();
	
	var dt = {
			cname:cname,
			jobtype:dataid
	}
	intoRedis(dt);  
	
	
}

//删除身份证照片或者个人照片
function deleteimg(obj,id){
	$(obj).parent().remove();	
	if(id+"" == "idcard"){
		var array = new Array();
		$("input[name='idcardimg']").each(function(index,dom){
			   t = {
		            idcardimgurl_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
		            name: "身份证照片",
		            delflag: 0,
		            url: $(this).val(),
		            memberimgid: "${data.memberid}"
		        };
			   array.push(t);
            
    	 });
	          var dt = {
	            idCardImgList:JSON.stringify(array)  
	          }; 
	          intoRedis(dt);
	}else{
		var array = new Array();
		$("input[name='personalimg']").each(function(index,dom){
			   t = {
					personal_show: "http://7xqoy0.com1.z0.glb.clouddn.com/"+$(this).val(),
		            name: "个人照片",
		            delflag: 0,
		            url: $(this).val(),
		            memberimgid: "${data.memberid}"
		        };
			   array.push(t); 
            
    	 });
	          var dt = {
	        	personalImgList:JSON.stringify(array)  
	          }; 
	          intoRedis(dt);
	}
	 
}

function subBaseInfo(){
	  checkPhone(function(data){
	      if(data+"" == "1"){
	    	   swal({
	 				title : "",
	 				text : "您输入的手机号已经被人注册了！",
					type : "",
	 				showCancelButton : false,
	 				confirmButtonColor : "#ff7922",
	 				confirmButtonText : "确认",
					closeOnConfirm : true
				}, function(){
				 
				});	
	      }else{
	    	  if($("input[name='nickname']").val() == ""){
	    		  swal({
	    				title : "",
	    				text : "昵称不能为空",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				$("input[name='nickname']").focus();
	    			});		
	           return;
	    	 };
	    	 
	    /* 	 if($("input[name='nickname']").val() == "" || /^[u4e00-\u9fa5A-Za-z0-9-_]*$/.test($("input[name='nickname']").val()) == false ){
	    		   swal({
	    				title : "",
	    				text : "昵称 只能由数字 、字母、下划线、-或者中文字符组成！",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				$("#nickname").focus(); 
	    			});
	    		   return ;
	    	   }
	     
	    	  */
	    	 if($("input[name='realname']").val() == ""){
	    		  swal({
	    				title : "",
	    				text : "请输入真实姓名",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				$("input[name='realname']").focus();
	    			});		
	          return;
	    	 };
	    	 
	    	 
	    	   if($("#realname").val() == "" || $("#realname").val().match(/[^\u4e00-\u9fa5\a-z\A-Z]/g)){
	    		   swal({
	    				title : "",
	    				text : "姓名只能由字母或中文字符组成！",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				$("#realname").focus(); 
	    			});	
	    		   return;
	    	   }

	    	 
	    	 if($("input[name='sex']").val() == ""){
	    		  swal({
	    				title : "",
	    				text : "请选择性别",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				 
	    			});		
	         return;
	    	 };
	    	 
	    	 if($("input[name='cityid']").val() == ""){
	    		  swal({
	    				title : "",
	    				text : "请选择工作地区",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				 
	    			});		
	       return;
	    	 };
	    	 
	    	 if($("input[name='jobtype']").val() == ""){
	    		  swal({
	    				title : "",
	    				text : "请选择所属行业",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				 
	    			});		
	        return;
	    	 };
	    	 
	    	 if($("input[name='phone']").val() == ""){
	    		  swal({
	    				title : "",
	    				text : "请输入手机号",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				$("input[name='phone']").focus();
	    			});		
	       return;
	    	 };
	    	 
	    	 if($("input[name='phone']").val() != "" && !/^1[3|4|5|7|8]\d{9}$/.test($("input[name='phone']").val())){
	    		  swal({
	    				title : "",
	    				text : "手机号不正确,请从新输入!",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				$("input[name='phone']").focus();
	    			});		
	      return;
	    	 }; 
	    	 
	    	 if($("input[name='idcard']").val() == ""){
	    		  swal({
	    				title : "",
	    				text : "请输入身份证号!",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				$("input[name='idcard']").focus();
	    			});		
	      return;
	    	 };
	    	 
	       if($("#idcard").val() != "" && /^\d{15}|\d{}18$/.test($("#idcard").val()) == false){
	    	   swal({
	    			title : "",
	    			text : "身份证号只能由15或者18位数字组成！",
	    			type : "",
	    			showCancelButton : false,
	    			confirmButtonColor : "#ff7922",
	    			confirmButtonText : "确认",
	    			closeOnConfirm : true
	    		}, function(){
	    			$("input[name='idcard']").focus();
	    		});	
	    	   return ;
	       }
	       if(IdCardValidate($("#idcard").val())==false){
	    	   swal({
	    			title : "",
	    			text : "您输入的身份证号无效，请重新输入 ！",
	    			type : "",
	    			showCancelButton : false,
	    			confirmButtonColor : "#ff7922",
	    			confirmButtonText : "确认",
	    			closeOnConfirm : true
	    		}, function(){
	    			$("input[name='idcard']").focus();
	    		});	
	    	   return ;
	       }
	       
	    	  
	     
	    	   if($("#personalintroduction").val() != "" && /^[u4e00-\u9fa5\w,!:;""\/''，：；！‘’。.“”\s@#$%^&*()_+=！#￥……（）——+-~？?《》]*$/.test($("#personalintroduction").val()) == false){
	    		   swal({
	    				title : "",
	    				text : "个人简介不能包含特殊字符,如表情等！",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				 $("#personalintroduction").focus(); 
	    			});
	    		   return ;
	    	   }
	     
	    	   if($("#companyname").val() !=""  &&  /^[u4e00-\u9fa5A-Za-z]*$/.test($("#companyname").val()) == false){
	    		   swal({
	    				title : "",
	    				text : "公司名称只能由字母、数字或中文字符组成！",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				 $("#companyname").focus(); 
	    			});	
	    		   return;
	    	   }
	      
	     
	    	   if($("#companyintroduction").val() != "" && /^[u4e00-\u9fa5\w,!:;""\/''，：；！‘’。.“”\s@#$%^&*()_+=！#￥……（）——+-~？?《》]*$/.test($("#companyintroduction").val()) == false){
	    		   swal({
	    				title : "",
	    				text : "公司简介不能包含特殊字符,如表情等！",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				 $("#companyintroduction").focus(); 
	    			});	
	    		   return;
	    	   }
	      
	      
	    	 
	        if($("#idcardimgurl_img_box img").length != 2){
	      	  swal({
	    			title : "",
	    			text : "请上传2张正反面身份证照片!",
	    			type : "",
	    			showCancelButton : false,
	    			confirmButtonColor : "#ff7922",
	    			confirmButtonText : "确认",
	    			closeOnConfirm : true
	    		}, function(){
	    			 
	    		});	 
	        	
	    		 return;
	    	 }
	    	 
	    	 if($("#personalimgurl_img_box img").length>9){
	    		  swal({
	    				title : "",
	    				text : "最多上传9张个人照片!",
	    				type : "",
	    				showCancelButton : false,
	    				confirmButtonColor : "#ff7922",
	    				confirmButtonText : "确认",
	    				closeOnConfirm : true
	    			}, function(){
	    				 
	    			});	 
	    		 return;
	    	 }
	    	 
	    	 var isgive_t="";
	    	 if('${data.isgive}' != "1" && $("#idcardimgurl_img_box img").length>0 && $("input[name='idcard']").val() != ""){
	    		 isgive_t="1";
	    	 }
	    	
	    	var perfectdegree = 0 ;
	    	var t = 0;
	    	var cm = 0;
	        if($("input[name='headimage']").val() != ""){
	        	perfectdegree+=10;
	    	 };
	    	 
	    	 if($("input[name='nickname']").val() != ""){
	    		 perfectdegree+=10; 
	    	 };
	    	 
	    	 if($("input[name='realname']").val() != ""){
	    		 perfectdegree+=10; 
	    	 };
	    	 
	    	 if($("input[name='sex']").val() != ""){
	    		 perfectdegree+=10;
	    	 };
	    	 
	    	 if($("input[name='cityid']").val() != ""){
	    		 perfectdegree+=10;
	    	 }
	    	 
	    	 if($("input[name='jobtype']").val() != ""){
	    		 perfectdegree+=10;		 
	    	 };
	    	
	    	 if($("input[name='phone']").val() != ""){
	    		 perfectdegree+=10;
	    	 };
	    	 
	    	 if($("input[name='idcard']").val() != ""){
	    		 t+=10;
	    		 perfectdegree+=10;
	    	 };
	    	 
	    	 if($("#idcardimgurl_img_box img").length == 2){
	    		 perfectdegree+=10;
	    		 t+=10;
	    	 };  
	    	 
	    	 if($("#personalintroduction").val() != ""){
	    		 perfectdegree+=10;
	    	 };
	    	 
	    	 if($("#personalimgurl_img_box img").length>0 &&  $("#personalimgurl_img_box img").length<=9){
	    		 perfectdegree+=10;
	    	 }; 
	    	 
	    	 if($("input[name='companyname']").val() != ""){
	    		 perfectdegree+=10;
	    		 cm+=10;
	    	 };
	    	 
	    	 if($("input[name='companyimgurl']").val() != ""){
	    		 perfectdegree+=10;
	    		 cm+=10;
	    	 };
	    	 
	    	 if($("#companyintroduction").val() != ""){
	    		 perfectdegree+=10;
	    		 cm+=10;
	    	 };
	    	 
	    	 if(perfectdegree >= 140){
	    		 perfectdegree=100;
	    	 }else if(perfectdegree >=120  && perfectdegree <140 ){
	    		 perfectdegree=95;
	    	 }else if(perfectdegree >=100 && perfectdegree <120 ){
	    		 perfectdegree = 90;
	    	 }else if (perfectdegree >=90 && perfectdegree <100 ){
	    		 perfectdegree = 85;
	    	 }else if(perfectdegree >=70 && perfectdegree <90 ){
	    		 perfectdegree = 80;
	    	 }else{
	    		 perfectdegree = 70;
	    	 }
	    	 
	         $("input[name='perfectdegree']").val(perfectdegree);
	    	 var data = hhutil.getFormBean("editForm");
	    	 if(t== 20 || cm == 30){
	    		 data.iscompletion=1;
	    	 }
	    	 var idCardImgArray="";
	    	 $("input[name='idcardimg']").each(function(index,dom){
	    		 idCardImgArray+=$(this).val()+",";
	    	 });
	    	 var personalImgArray="";
	    	 $("input[name='personalimg']").each(function(index,dom){
	    			 personalImgArray+=$(this).val()+",";
	    	 });
	    	 data.idCardImgArray = idCardImgArray;
	    	 data.personalImgArray = personalImgArray;
	    	 data.isgive_t = isgive_t;
	    	 $.ajax({
	    		 type:"post",
	    		 url:"<%=request.getContextPath() %>/member/updateBaseInfo",
	    		 data:data,
	    		 success:function(data){
	    			 if(data.flg){
	    				 swal({
	    						title : "",
	    						text : "保存个人信息成功！点击确认返回个人首页,不返回点击取消",
	    						type : "success",
	    						showCancelButton : true,
	    						confirmButtonColor : "#ff7922",
	    						confirmButtonText : "确认",
	    						cancelButtonText : "取消",
	    						closeOnConfirm : true
	    					}, function(){
	    						 window.location.href="<%=request.getContextPath() %>/my/mycenter";
	    					});	
	    				 
	    			 }else{
	    				  swal({
	    						title : "",
	    						text : data.msg,
	    						type : "error",
	    						showCancelButton : false,
	    						confirmButtonColor : "#ff7922",
	    						confirmButtonText : "确认",
	    						closeOnConfirm : true
	    					}, function(){
	    						 
	    					});	
	    			 }
	    		 },error:function(text){
	    			  swal({
	    					title : "",
	    					text : "保存个人信息失败，请稍后再试！",
	    					type : "",
	    					showCancelButton : false,
	    					confirmButtonColor : "#ff7922",
	    					confirmButtonText : "确认",
	    					closeOnConfirm : true
	    				}, function(){
	    					 
	    				});	
	    		 }
	    		 
	    	 }) ;  
	    	  
	    	  
	      }  
     });
	
}

</script>
<!--底部导航-->
<jsp:include page="/public/app_bottom.jsp" flush="true" />
<!--底部导航-->
</body>
</html>
