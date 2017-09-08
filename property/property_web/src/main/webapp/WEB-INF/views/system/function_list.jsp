<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-system p-system-func">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>系统设置 >> 菜单管理</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>菜单管理</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" action="<%=request.getContextPath()%>/system/functionList">
                                <div class="form-input" style="margin-left: 10px">
                                   <input class="form-control"  name="name"  placeholder="菜单名称" type="text"  value="${map.name}">
                                </div>
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                    
                                    <button data-toggle="modal" data-backdrop="static" href="#save_edit" onclick="initAdd();" 
                                    	style="margin-left:30px;" type="button" class="btn btn-default"><i class="ico-add"></i>新 增</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">菜单名称</th>
                                    <th data-sortable="true">连接地址</th>
                                    <th data-sortable="true">父级菜单名称</th>
                                    <th>样式备注</th>
                                    <th>创建时间</th>
                                    <th>排序值</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${functionList}" var="func" varStatus="st">
                            		<input type="hidden" id="name_${func.id}" value="${func.name }"/>
                            		<input type="hidden" id="parentid_${func.id}" value="${func.parentid }"/>
                            		<input type="hidden" id="linkurl_${func.id}" value="${func.linkurl }"/>
                            		<input type="hidden" id="status_${func.id}" value="${func.status }"/>
                            		<input type="hidden" id="priority_${func.id}" value="${func.priority }"/>
                            		<input type="hidden" id="remark_${func.id}" value="${func.remark }"/>
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td>
	                                    <td>${func.name }</td>
	                                    <td>${func.linkurl }</td>
	                                    <td>${func.parentname}</td>
	                                    <td>${func.remark}</td>
	                                    <td>${func.createtime}</td>
	                                    <td>${func.priority}</td>
	                                    <td>
	                                    	<a href="#save_edit" onclick="initEdit('${func.id}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>修改</a>
											<a href="#deleteTip" onclick="deleteData('${func.id}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>删除</a>
		                                    <c:if test="${1==func.status }"><a  href="#statusdiv" onclick="statusData('${func.id}');"  data-toggle="modal" data-backdrop="static">禁用</a></c:if>
											<c:if test="${0==func.status }"><a  href="#statusdiv"  onclick="statusData('${func.id}');" data-toggle="modal" data-backdrop="static">启用</a></c:if>
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
function deleteData(id){
	$('#id',$('#deleteForm')).val(id);
}
function statusData(id){
	if($('#status_'+id).val()=="1"){
		$('#status',$('#statusForm')).val("0");
		$('#txt').text("禁用?");
	}else{
		$('#status',$('#statusForm')).val("1");
		$('#txt').text("启用?");
	}
	$('#id',$('#statusForm')).val(id);
}
//点击编辑，初始化form表单
function initEdit(id){
	$('#name',$('#saveEditForm')).val($('#name_'+id).val());
	$('#linkurl',$('#saveEditForm')).val($('#linkurl_'+id).val());
	$('#priority',$('#saveEditForm')).val($('#priority_'+id).val());
	$('#remark',$('#saveEditForm')).val($('#remark_'+id).val());
	$('#parentid',$('#saveEditForm')).select2("val",$('#parentid_'+id).val());
    $("input[name='status'][value="+$('#status_'+id).val()+"]",$('#saveEditForm')).iCheck('check');
	$('#id',$('#saveEditForm')).val(id);
}
function initAdd(){
	$('#name',$('#saveEditForm')).val("");
	$('#linkurl',$('#saveEditForm')).val("");
	$('#priority',$('#saveEditForm')).val("");
	$('#remark',$('#saveEditForm')).val("");
	$('#parentid',$('#saveEditForm')).select2("val","0");
	$('#id',$('#saveEditForm')).val("");
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
	$('#authUser').text($('#realname_'+id).val());
	$('#userid',$('#authRolesForm')).val(id);
	var roleids =  $('#roleids_'+id).val();
	$('#roleids',$('#authRolesForm')).val(roleids);
	
	var roleidArray = roleids.split(",");
	$("input[name='roleid']:radio").each(function(){
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
	$("input[name='roleid']:radio").each(function(){
		if($(this)[0].checked==true){
			i++;
		}
	})
	if(i==0){
		return;
	}else{
		$("#authRolesForm").submit();
	}
}
function tijiao(){
	//username2(function(){
	//	email2(function(){
	//		phone2(function(){
				$("#saveEditForm").submit();
	//		});
	//	});
	//}); 
}
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
</script>


<div id="save_edit" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">编辑用户</h3>
        </div>
        <form id="saveEditForm" class="form-horizontal parsley-form" role="form" name="saveEditForm" action="<%=request.getContextPath()%>/system/editfunction">
        <input type="hidden" id="id" name="id"  />
        <div class="modal-body">
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>菜单名称</label>
                <div class="col-md-7">
                  <input type="text" id="name" name="name" class="form-control parsley-validated" data-required="true">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>连接地址</label>
                <div class="col-md-7">
                  <input type="text" id="linkurl" name="linkurl" class="form-control parsley-validated">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label">父级菜单</label>
                <div class="col-md-7">
                			<select id="parentid" name="parentid" class="form-control select2-input select2-offscreen" style="width:150px;">
                                    	<option value="0" selected="selected">无父级菜单</option>
										<c:forEach items="${parentList}" var="parent">
								       		<option value="${parent.id}">${parent.name}</option>
								       </c:forEach>
                            </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label">样式备注</label>
                <div class="col-md-7">
                  <input type="text"  class="form-control" name="remark" id="remark">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>排序值</label> 
                <div class="col-md-7">
                  <input type="number"  class="form-control" name="priority" id="priority" data-required="true">
                </div> 
            </div>
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
            <button type="button" onclick="tijiao()" class="btn btn-save"><i class="ico-ok"></i>保 存</button>
        </div>
        </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="deleteTip" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form  action="<%=request.getContextPath()%>/system/deletefunction" id="deleteForm" name="deleteForm"
				 theme="simple" method="post" >
		<input type="hidden" id="id" name="id" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4>确定删除？</h4>
        <p class="tips-modal-sub">菜单删除之后无法恢复</p>
      </div>
		
      <div class="modal-footer">
        <button type="button" class="btn btn-close" data-dismiss="modal">取 消</button>
        <button type="submit" class="btn btn-save"><i class="ico-ok"></i>确 定</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="statusdiv" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form  action="<%=request.getContextPath()%>/system/editfunction" id="statusForm" name="statusForm"
				 theme="simple" method="post" >
		<input type="hidden" id="id" name="id" />
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