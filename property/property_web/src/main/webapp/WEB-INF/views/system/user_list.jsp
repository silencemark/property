<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-system p-system-user">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>系统设置 >> 用户管理</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>用户管理</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" action="<%=request.getContextPath()%>/system/queryUserList">
                                <div class="form-select">
                                    <select name="status" class="form-control select2-input select2-offscreen" style="width:90px;" value="${map.status }">
	                                    <c:if test="${null==map.status || ''==map.status }">
											<option value="" selected="selected">全部</option>
					                		<option value="1">启用</option>
	                                        <option value="0">禁用</option>
										</c:if>
										<c:if test="${!(null==map.status || ''==map.status )}">
											<option value="" selected="selected">全部</option>
					                		<option value="1" <c:if test="${map.status==1 }">selected="selected"</c:if>>启用</option>
					                		<option value="0" <c:if test="${map.status==0 }">selected="selected"</c:if>>禁用</option>
										</c:if>	
                                    </select>
                                </div>
                                <div class="form-input" style="margin-left:30px;">
                                    <input class="form-control" name="username" type="text" placeholder="用户名" style="width:80px;" value="${map.username }">     
                                </div>
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <input class="form-control" name="realname" type="text" placeholder="姓名" style="width:80px;" value="${map.realname }">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                    
                                    <button data-toggle="modal" data-backdrop="static" href="#save_edit" onclick="initAdd();" 
                                    	style="margin-left:30px;" type="button" class="btn btn-default"><i class="ico-add"></i>新 增</button>
                                </div>
                            </form>
                        </div>
                        <div class="area-operate">
                            
                            <%-- 
                            <button type="button" class="btn btn-default"><i class="ico-edit"></i>修 改</button>
                            <button data-toggle="modal" data-backdrop="static" href="#del" type="button" class="btn btn-default"><i class="ico-del"></i>删 除</button>
                            <button data-toggle="modal" data-backdrop="static" href="#accredit" type="button" class="btn btn-default"><i class="ico-key"></i>授 权</button>
                            --%>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">用户名</th>
                                    <th data-sortable="true">姓名</th>
                                    <th data-sortable="true">省份</th>
                                    <th width="60px" data-sortable="true">性别</th>
                                    <th>手机</th>
                                    <th>邮箱</th>
                                    <th data-sortable="true">所属角色</th>
                                    <th data-sortable="true">用户状态</th>
                                    <th>二维码</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${userList }" var="u" varStatus="st">
                            		<input type="hidden" id="username_${u.userid}" value="${u.username }"/>
                            		<input type="hidden" id="password_${u.userid}" value="${u.password }"/>
									<input type="hidden" id="realname_${u.userid}" value="${u.realname }"/>
									<input type="hidden" id="sex_${u.userid}" value="${u.sex }"/>
									<input type="hidden" id="phone_${u.userid}" value="${u.phone }"/>
									<input type="hidden" id="telephone_${u.userid}" value="${u.telephone }"/>
									<input type="hidden" id="email_${u.userid}" value="${u.email }"/>
									<input type="hidden" id="status_${u.userid}" value="${u.status }"/>
									<input type="hidden" id="birthday_${u.userid}" value="${u.birthday }"/>
									<input type="hidden" id="roleids_${u.userid}" value="${u.roleids }"/>
									<input type="hidden" id="img_${u.userid}" value="${u.headimage}"/>
									<input type="hidden" id="rolenames_${u.userid}" value="${u.rolenames}"/>
									<input type="hidden" id="provinceid_${u.userid}" value="${u.provinceid}"/> 
									
									
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td>
	                                    <td>${u.username }</td>
	                                    <td>${u.realname }</td> 
	                                    <td>${u.provinceid == 0?'全国':u.provincename }</td> 
	                                    <td><c:if test="${1==u.sex }">男</c:if>
											<c:if test="${0==u.sex }">女</c:if>
											<c:if test="${!(1==u.sex or 0==u.sex) }">保密</c:if></td>
	                                    <td>${u.phone}</td>
	                                    <td>${u.telephone }</td>
	                                    <td>${u.rolenames }</td>
	                                    <td><c:if test="${1==u.status }">启用</c:if>
											<c:if test="${0==u.status }">禁用</c:if>
										</td>
										<td>
										    <c:if test="${u.ewm != null and u.ewm != '' }">
										      <img alt="" src="${ u.ewm}" onclick="imgutil.FDIMG(this)"  width="50" height="50" style="display: block">
										    </c:if>
										   <c:if test="${u.ewm == null or u.ewm == '' }">
										      <a href="javascript:void(0)"  onclick="shengChengEwm('${u.userid}',this);"  data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>生成二维码</a>
										   </c:if>
										   
										</td>
	                                    <td>
	                                    	<a href="#save_edit" onclick="initEdit('${u.userid}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>修改</a>
											
											<a href="#accredit"  onclick="showAccredit('${u.userid}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>授权</a>
											<c:if test="${1==u.status }"><a href="#statusEdit"  onclick="statusEdit('${u.userid}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>禁用</a></c:if>
											<c:if test="${0==u.status }"><a href="#statusEdit"  onclick="statusEdit('${u.userid}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>启用</a></c:if>
											<a href="#reset"  onclick="initEdit('${u.userid}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>重置密码</a>
											
	                                    </td>
	                                </tr>
                            	</c:forEach>
                            	 
                            </tbody>
                        </table>              
                    </div>
                    ${pager}
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
function shengChengEwm(id,obj){
	$.ajax({
		url:"<%=request.getContextPath()%>/system/member/myqrcode",
		type:"post",
		data:{userid:id},
		success:function(data){
			$(obj).before("<img onclick=\"imgutil.FDIMG(this)\" src='"+data.ewm+"' width='50px' height='50px'>");
			$(obj).hide();
		}
	});
}

function deleteData(id){
	$('#userid',$('#deleteForm')).val(id);
}
function statusEdit(id){
	if($('#status_'+id).val()=="1"){
		$('#status',$('#statusEditForm')).val("0");
		$('#txt').text("禁用?");
	}else{
		$('#status',$('#statusEditForm')).val("1");
		$('#txt').text("启用?");
	}
	$('#userid',$('#statusEditForm')).val(id);
}
//点击编辑，初始化form表单
function initEdit(id){ 
	$('#username',$('#saveEditForm')).val($('#username_'+id).val());
	$('#password',$('#saveEditForm')).val($('#password_'+id).val()); 
	$('#editpass').hide();
	$('#ispass').val("no");
	$('#realname',$('#saveEditForm')).val($('#realname_'+id).val());
	$('#phone',$('#saveEditForm')).val($('#phone_'+id).val());
	$('#telephone',$('#saveEditForm')).val($('#telephone_'+id).val());
	$('#email',$('#saveEditForm')).val($('#email_'+id).val());
	$('#birthday',$('#saveEditForm')).val($('#birthday_'+id).val());
	$("input[name='sex'][value="+$('#sex_'+id).val()+"]",$('#saveEditForm')).iCheck('check');
    $("input[name='status'][value="+$('#status_'+id).val()+"]",$('#saveEditForm')).iCheck('check');
	$('#userid',$('#reset')).val(id);
	$('#img',$('#saveEditForm')).val($('#img_'+id).val());
	$('#userid',$('#saveEditForm')).val(id);
	$("#head_img").attr("src",$('#img_'+id).val()); 
	$('select[name="provinceid"]').select2('val',$('#provinceid_'+id).val()); 
} 
function initAdd(){
	$('#username',$('#saveEditForm')).val("");
	$('#editpass').show();  
	$('#ispass').val("yes");
	$('#password',$('#saveEditForm')).val("");
	$('#realname',$('#saveEditForm')).val("");
	$('#phone',$('#saveEditForm')).val("");
	$('#telephone',$('#saveEditForm')).val("");
	$('#email',$('#saveEditForm')).val("");
	$('#birthday',$('#saveEditForm')).val("");
	$("input[name='sex'][value='']",$('#saveEditForm')).iCheck('check');
    $("input[name='status'][value='0']",$('#saveEditForm')).iCheck('check');
	$('#userid',$('#saveEditForm')).val();
	$('#img',$('#saveEditForm')).val("");
	$('input[name="provinceid"]').select2('val','0'); 
}

function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPath=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/nnjssd
//    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return localhostPath;
//    return(localhostPath+projectName);
}
//授权
function showAccredit(id){
	$('#authUser',$('#authRolesForm')).text($('#realname_'+id).val());
	$('#userid',$('#authRolesForm')).val(id);
	var roleids =  $('#roleids_'+id).val();
	$('#roleids',$('#authRolesForm')).val(roleids);
	
	var rolenames=  $('#rolenames_'+id).val();
	var roleList=rolenames.split(",");
	$('#rolenames',$('#authRolesForm')).html("");
	for(var i = 0; i < roleList.length;i++){
		$('#rolenames',$('#authRolesForm')).append("<input type='button' class='but' value='"+roleList[i]+"'/>&nbsp;&nbsp;<input  class='roleid' type='hidden' value='"+roleList[i]+"'>");
	}
	var roleidArray = roleids.split(",");
	$("input[name='roleid']:checkbox").each(function(){
		var flag = false ;
		for(var i=0,len=roleidArray.length;i<len;i++){
			if($(this).val()==roleidArray[i]){
				flag = true ;
				break ;
			}
		}
		if(flag){
			$(this).iCheck('check');
		}else{
			$(this).iCheck("uncheck");
		}
	});
	
}
function showAccreditT(){ 
	var i=0;
	$("input[name='roleid']:checkbox").each(function(){
		if($(this)[0].checked==true){
			i++;
		}
	});
	
	if(i==0){
		return;
	}else if(i>1){
		 $.gritter.add({
	            title: '提示',
	            text: '您只能给一个用户添加一个角色！！',
	            sticky: false,
	            time: '3000'
	        });
		 return;
	}else{
		$("#authRolesForm").submit();
	}
}

$(function(){
	$('#username').change(function(){
		var url = '<%=request.getContextPath()%>/system/validateUsername?username='+$("#username").val();
		hhutil.ajax(url,null,function(data){
			if(!data){
				 $.gritter.add({
	 		            title: '提示',
	 		            text: '用户名'+$("#username").val()+'已经被人注册过了',
	 		            sticky: false,
	 		            time: '3000'
	 		        });
				 
				 $("#isusername").val("1");
				 $('#username').focus();
			}else{
				 $("#isusername").val("0");
			}
		});
	})
})
function email2(call){
	if($("#email").val()==null||$("#email").val()==""){
		return;
	}
	var userid = $("#userid",$("#saveEditForm")).val();
	var username1 = $("#phone_"+userid).val();
	var username2 = $("#username_"+userid).val();
	var username3 = $("#email_"+userid).val();
	if(username1!=null&&(username1==$("#email").val() || username2==$("#email").val() || username3==$("#email").val())){
		if(call!=null)
			call();
		return;
	} 
	var url = '<%=request.getContextPath()%>/index/reg/validateusername?username='+$("#email").val();
	hhutil.ajax(url,null,function(data){
		if(data){
			if(call!=null)
				call();
		}else{
			winpop('该邮箱已经被绑定注册');
		}
	});
}
function phone2(call){
	if($("#phone").val()==null||$("#phone").val()==""){
		return;
	}
	var userid = $("#userid",$("#saveEditForm")).val();
	var username1 = $("#phone_"+userid).val();
	var username2 = $("#username_"+userid).val();
	var username3 = $("#email_"+userid).val();
	if(username1!=null&&(username1==$("#phone").val() || username2==$("#phone").val() || username3==$("#phone").val())){
		if(call!=null)
			call();
		return;
	} 
	var url = '<%=request.getContextPath()%>/index/reg/validateusername?username='+$("#phone").val();
	hhutil.ajax(url,null,function(data){
		if(data){
			if(call!=null)
				call();
		}else{
			winpop('该手机号已经被绑定注册');
		}
	});
}
function username2(call){
	if($("#username").val()==null||$("#username").val()==""){
		return;
	}
	var userid = $("#userid",$("#saveEditForm")).val();
	var username1 = $("#phone_"+userid).val();
	var username2 = $("#username_"+userid).val();
	var username3 = $("#email_"+userid).val();
	if(username1!=null&&(username1==$("#username").val() || username2==$("#username").val() || username3==$("#username").val())){
		if(call!=null)
			call();
		return;
	} 
	var url = '<%=request.getContextPath()%>/index/reg/validateusername?username='+$("#username").val();
	hhutil.ajax(url,null,function(data){
		if(data){
			if(call!=null)
				call();
		}else{
			winpop('该用户名已经注册过');
		}
	});
}


function ajaxFileUpload(id,Fileid,noimg){
	 
	if(!Fileid){
		Fileid = "fileName";
	}
	hhutil.ajaxFileUpload("<%=request.getContextPath()%>/upload/headimg",Fileid,function(data){
		if(noimg){//不是上传图片
			if(data.url){
				$("#img").val(data.url);
				winpop("文件上传成功！<br>"+data.url,250,300);
			}else{ 
				winpop("文件上传失败！");
			}
		}else{
			if(data.url){
				$("#img").val(data.url);
				$("#head_img").attr("src","<%=request.getContextPath()%>"+data.url); 
				winpop('图片上传成功记得保存哦！<br><img class="form-control" src="<%=request.getContextPath()%>/'+data.url+'" style="width: 50px; height: 50px"/>');
			}else{
				winpop("图片上传失败！");
			}
		}
	});
} 



function chkPwd(){
	var pwd1=$("#pwd1").val();
	var pwd2=$("#pwd2").val();
	if(pwd1!=pwd2){
		alert("两次输入的密码不相同，请重新输入。");
		return;
	}
	$("#resetForm").submit();
	
}
function tijiao(){
	if($('#isusername').val()=="1"){
		$.gritter.add({
	            title: '提示',
	            text: '用户名'+$("#username").val()+'已经被人注册过了',
	            sticky: false,
	            time: '3000'
	        });
		return false;
	}else{
		return true;
	}
} 
</script>



<div id="save_edit" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">编辑用户</h3>
        </div>
        <form id="saveEditForm" class="form-horizontal parsley-form" role="form" name="saveEditForm"
        	 action="<%=request.getContextPath()%>/system/editUser" onsubmit="return tijiao();" method="post"> 
        	<input type="hidden" id="userid" name="userid"  />
        	<input type="hidden"  value="yes" id="ispass" name="ispass">    
        <div class="modal-body">
        	
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>用户名</label>
                <div class="col-md-7">
                  <input type="text" id="username" name="username" class="form-control parsley-validated" data-required="true" data-rangelength="[4, 32]">
                	<input type="hidden" id="isusername" value="0">
                </div>
            </div>
            <div class="form-group" id="editpass">
                <label class="col-md-3 control-label"><i class="required">*</i>密码</label>
                <div class="col-md-7">
                  <input type="text" id="password" name="password" class="form-control parsley-validated" data-rangelength="[6, 108]" data-required="true">  
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>姓名</label>
                <div class="col-md-7">
                  <input type="text" id="realname" name="realname" class="form-control parsley-validated" data-required="true" data-maxlength="32">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>所属省</label>
                <div class="col-md-7 ui-icheck-label">
                    <select name="provinceid" class="form-control select2-input select2-offscreen" style="width:90px;" value="${map.status }">
							<option value="0" selected="selected">全国</option>
							<c:forEach items="${provinceList }" var="item"> 
								<option value="${item.areaid }" >${item.cname }</option>
							</c:forEach>
		               		 
		               		
                    </select> 
                </div>
            </div> 
            <div class="form-group">
                <label class="col-md-3 control-label">性别</label>
                <div class="col-md-7 ui-icheck-label">
                    <label><input type="radio" name="sex" checked="checked" class="icheck-input" value="1">男</label>
                    <label><input type="radio" name="sex" class="icheck-input" value="0">女</label>
                    <label><input type="radio" name="sex" class="icheck-input"  value="2">保密</label>
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-md-3 control-label">邮箱</label>
                <div class="col-md-7">
                  <input type="email"  class="form-control" name="email" id="email">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label">手机</label>
                <div class="col-md-7">
                  <input type="text" class="form-control"  name="phone" id="phone">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label">电话</label>
                <div class="col-md-7">
                  <input type="text" class="form-control parsley-validated" data-maxlength="64" name="telephone" id="telephone">
                </div>
            </div>
           <!--  <div class="form-group">
                <label class="col-md-3 control-label">出生日期</label>
                <div class="col-md-7">
                  <input type="date" class="form-control parsley-validated" data-maxlength="64" name="birthday" id="birthday">
                </div>
            </div> -->
            <div class="form-group">
                <label class="col-md-3 control-label">状态</label>
                <div class="col-md-7 ui-icheck-label">
                    <label><input type="radio" name="status" value="1"  class="icheck-input">启用</label>
                    <label><input type="radio" name="status" value="0" checked="checked" class="icheck-input">停用</label>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-close" data-dismiss="modal">取 消</button>
            <button type="submit"  class="btn btn-save"><i class="ico-ok"></i>保 存</button>
        </div>  
        </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="deleteTip" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form action="system!deleteUser.action" id="deleteForm" name="deleteForm"
				 theme="simple" method="post"
				namespace="/mbacker" >
		<input type="hidden" id="userid" name="userid"  />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4>确定删除？</h4>
        <p class="tips-modal-sub">用户删除之后无法恢复</p>
      </div>
		
      <div class="modal-footer">
        <button type="button" class="btn btn-close" data-dismiss="modal">取 消</button>
        <button type="submit" class="btn btn-save"><i class="ico-ok"></i>确 定</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div id="accredit" class="modal fade accredit">
  <div class="modal-dialog w550">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">用户授权</h3>
        </div>
        <form id="authRolesForm" class="form-horizontal" role="form" 
        action="<%=request.getContextPath()%>/system/editUserRole" method="post">
        <input type="hidden" id="userid" name="userid"/>
        <input type="hidden" id="roleids" name="roleids"/>
        <div class="modal-body">
	            <div class="accredit-tip">
	                <p>您正在给用户<span id="authUser">张三丰、王重阳</span>授予角色权限。</p>
	            </div>
                <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>用户当前角色</label>
                <div class="col-md-7" id="rolenames">
                </div>
            </div>
            <div class="form-group" style="margin-top:20px;padding-top:20px;">
                <label class="col-md-3 control-label"><i class="required">*</i>所有角色</label>
                <div class="col-md-7">
                	<c:forEach items="${roleList }" var="role">
	                	<label title="${role.name }" class="icheck-inline">
                        	<input type="checkbox" name="roleid" class="icheck-input" value="${role.roleid}">${role.rolename}
                    	</label>
                    </c:forEach>
                   </div>
            </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-close" data-dismiss="modal">取 消</button>
            <button type="button" onclick="showAccreditT()" class="btn btn-save"><i class="ico-ok"></i>保 存</button>
        </div>
        </form>
    </div><!-- /.modal-content submit -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div id="reset" class="modal fade accredit">
  <div class="modal-dialog w550">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">重置密码</h3>
        </div>
        <form id="resetForm" class="form-horizontal parsley-form" role="form" 
        action="<%=request.getContextPath()%>/system/resetPwd" method="post">
         <div class="modal-body">
             <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>请输入新密码:</label>
                <div class="col-md-7">
                  <input type="password" id="pwd1" name="password" class="form-control parsley-validated" data-required="true" data-rangelength="[4, 11]">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>重新输入新密码:</label>
                <div class="col-md-7">
                  <input type="password" id="pwd2" name="password" class="form-control parsley-validated" data-required="true" data-rangelength="[4, 11]">
                </div>
            </div>
        </div> 
	    <input type="hidden" id="userid" name="userid"/>
	        
        <div class="modal-footer">
            <button type="button" class="btn btn-close" data-dismiss="modal">取 消</button>
            <button type="button" onclick="chkPwd();" class="btn btn-save"><i class="ico-ok"></i>确定</button>
        </div>
        </form>
    </div><!-- /.modal-content submit -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
         	
<div id="statusEdit" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form  action="<%=request.getContextPath()%>/system/editUser" id="statusEditForm" name="statusEditForm"
				 theme="simple" method="post" >
		<input type="hidden" id="userid" name="userid" />
		<input type="hidden" id="status" name="status" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4>确定<span id="txt"></span></h4>
      </div>
		
      <div class="modal-footer">
        <button type="button" class="btn btn-close" data-dismiss="modal">取 消</button>
        <button type="submit" class="btn btn-save"><i class="ico-ok"></i>确 定</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
	</div>
</body>
</html>