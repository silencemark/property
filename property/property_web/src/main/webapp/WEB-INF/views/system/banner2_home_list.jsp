<%@page import="com.qiniu.util.AuthConstant"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<script src="<%=request.getContextPath() %>/black/js/hhutil.js"></script> 
<body class="p-setting p-homepage-banner">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>系统设置 >> <a href="<%=request.getContextPath()%>/system/bannerListHome">banner位置列表 </a> >> banner列表  </h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>banner列表 </span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" action="<%=request.getContextPath()%>/system/banner2ListHome?positionid=${map.positionid}">
                                
                               <%--  <div class="form-input" style="margin-left: 10px">
                                   <input class="form-control"  name="name"  placeholder="" type="text"  value="${map.name}">
                                </div> --%>
                                <div class="form-search-sub" >
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
                                    <th data-sortable="true">图片</th>
                                    <th data-sortable="true">url</th>
                                    <th data-sortable="true">图片位置</th>
                                    <th data-sortable="true">排序</th>
                                    <th data-sortable="true">状态</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${bannerList}" var="item" varStatus="st">
  									 <input type="hidden" id="imgurl_${item.id}" value="${item.imgurl }"/>
  									 <input type="hidden" id="imgurl_show_${item.id}" value="${item.imgurl_show }"/>
  									 <input type="hidden" id="linkurl_${item.id}" value="${item.linkurl }"/>
  									 <input type="hidden" id="imgurl2_${item.id}" value="${item.imgurl2 }"/>
  									 <input type="hidden" id="priority_${item.id}" value="${item.priority }"/>
  									 <input type="hidden" id="status_${item.id}" value="${item.status }"/>
  
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td>
	                                    <td><img src="${item.imgurl_show }" style="height: 60px;"/></td>
	                                    <td>${item.linkurl }</td>
	                                    <td>${item.positionname }</td>
	                                    <td>${item.priority }</td>
	                                    <td>
	        								<c:if test="${0 eq item.status}">停用</c:if>
	                                 		<c:if test="${1 eq item.status}">启用</c:if>
	                                    </td>
	                                    
	                                    <td>
											<a href="#save_edit" onclick="initEdit('${item.id}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>修改</a>
											
											<a href="#deleteTip" onclick="deleteData('${item.id}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>删除</a>
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

$(function(){
	 $('#fileName').fileupload({    
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
						 $('#room_div').html('<img  width="310" height="134" src="'+data1+'">');
						 $('#imgurl').val(data.result.key);
					}  
				});
		    },
		    progressall: function (e, data) {
		     
		    }
		}); 
	
});

function deleteData(id){
	$('#id',$('#deleteForm')).val(id);
}


function initAdd(){
	$('#imgurl',$('#saveEditForm')).val('');
	$('#linkurl',$('#saveEditForm')).val('');
	$('#priority',$('#saveEditForm')).val('0');
	$("input[name='status'][value=1]",$('#saveEditForm')).iCheck('check');
	$('#room_div').html('');
	$('#id',$('#saveEditForm')).val('');
}

function initEdit(id){
	$('#imgurl',$('#saveEditForm')).val($('#imgurl_'+id).val());
	$('#linkurl',$('#saveEditForm')).val($('#linkurl_'+id).val());
	$('#room_div').html('<img   width="310" height="134" src="'+$('#imgurl_show_'+id).val()+'">'); 
	$('#priority',$('#saveEditForm')).val($('#priority_'+id).val());
	$("input[name='status'][value="+$('#status_'+id).val()+"]",$('#saveEditForm')).iCheck('check');
	$('#id',$('#saveEditForm')).val(id);
}
 
 
function tijiao(){
	
  $("#saveEditForm").submit();
}
</script>


<div id="deleteTip" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form  action="<%=request.getContextPath()%>/system/deleteBanner2Home" id="deleteForm" name="deleteForm"
				 theme="simple" method="post" >
		<input type="hidden" id="id" name="id" />
		<input type="hidden" id="positionid" name="positionid" value="${map.positionid}" />
		
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
        <button type="button" class="btn btn-close" data-dismiss="modal">取 消</button>
        <button type="submit" class="btn btn-save"><i class="ico-ok"></i>确 定</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="save_edit" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">banner</h3>
        </div>
        <form id="saveEditForm" class="form-horizontal parsley-form" role="form"  method="post" name="saveEditForm"
        	 action="<%=request.getContextPath()%>/system/updateBanner2Home">
        	<input type="hidden" id="id" name="id"  />
        	<input type="hidden" id="positionid" name="positionid" value="${map.positionid}" />
        <div class="modal-body">
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>图片</label>
                <div class="col-md-7">
                  <input type="text" id="imgurl" name="imgurl" class="form-control parsley-validated" data-required="true" style="display: none">
                  <div id="room_div" style="width:310px"></div>
                  <input type="file" name="file" style="display: none" id="fileName" T="file_headimg" />
 
                  <input type="button" value="编辑" onclick="$('#fileName').click();"/> 
                  规格：${map.size}
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>连接地址</label>
                <div class="col-md-7">
                  <input type="text" id="linkurl" name="linkurl" class="form-control parsley-validated" data-required="true">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>排序越大越靠前</label>
                <div class="col-md-7">
                  <input type="text" id="priority" name="priority" class="form-control parsley-validated" data-required="true">
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
            <button type="submit" class="btn btn-save"><i class="ico-ok"></i>保 存</button>
        </div>
        </form>
       
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



</body>
</html>