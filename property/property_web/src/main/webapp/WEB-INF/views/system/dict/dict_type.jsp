<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="false"></jsp:include>
<body class="p-setting p-dictionarie">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>系统设置 >> 字典管理</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>字典类别</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" action="<%=request.getContextPath()%>/system/dic/getDicTypeList">
                                <div class="form-input" style="margin-left:30px;">
                                    <input class="form-control" name="name" type="text" placeholder="请输入名称" style="width:120px;" value="${map.name }">     
                                </div>
                                   <div class="form-select" style="margin-left: 30px">
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
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                    <button data-toggle="modal" data-backdrop="static" href="#save_edit" onclick="initAdd();" style="margin-left:30px;" type="button" class="btn btn-default"><i class="ico-add"></i>新 增</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">名称</th>
                                    <th data-sortable="true">备注</th>
                                    <th data-sortable="true">创建者</th>
                                    <th data-sortable="true">创建时间</th>
                                    <th data-sortable="true">更新者</th>
                                    <th data-sortable="true">更新时间</th>
                                    <th data-sortable="true">状态</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dataList }" var="u" varStatus="st">
                            		<input type="hidden" id="name_${u.datatypeid}" value="${u.name }"/>
                            		<input type="hidden" id="comment_${u.datatypeid}" value="${u.comment }"/> 
									<input type="hidden" id="status_${u.datatypeid}" value="${u.status }"/>
                            		<tr>
	                                    <td class="checkbox-column">${st.index+1}</td>
	                                    <td>${u.name }</td>
	                                    <td>${u.comment }</td> 
	                                    <td>${u.crname }</td>
	                                    <td><fmt:formatDate value="${u.createtime}" pattern="yyyy-MM-DD HH:mm:ss"/></td> 
	                                    
	                                    <td>${u.upname}</td>
	                                    <td><fmt:formatDate value="${u.updatetime}" pattern="yyyy-MM-DD HH:mm:ss"/></td>
	                                    <td><c:if test="${1==u.status }">启用</c:if>
											<c:if test="${0==u.status }">禁用</c:if>
										</td>
	                                    <td>
	                                        <a href="#save_edit" onclick="initEdit('${u.datatypeid}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>修改</a>
	                                        <a href="<%=request.getContextPath() %>/system/dic/getDicList?datatypeid=${u.datatypeid}"  data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>查看</a>
											
	                                    </td>
	                                </tr>
                            	</c:forEach>
                            	 
                            </tbody>
                        </table>              
                    </div>
                    ${page}
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
 
//点击编辑，初始化form表单
function initEdit(id){ 
	$('#name',$('#saveEditForm')).val($('#name_'+id).val());
	$('#comment',$('#saveEditForm')).val($('#comment_'+id).val());
	$("input[name='status'][value="+$('#status_'+id).val()+"]",$('#saveEditForm')).iCheck('check');
	$('#datatypeid',$('#saveEditForm')).val(id);
} 
function initAdd(){
	$('#name',$('#saveEditForm')).val(""); 
	$('#comment',$('#saveEditForm')).val("");
	$("input[name='status'][value=1]",$('#saveEditForm')).iCheck('check');
	$('#datatypeid',$('#saveEditForm')).val("");
}
 
</script>
<div id="save_edit" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">编辑字典类型</h3>
        </div>
        <form id="saveEditForm" class="form-horizontal parsley-form"  method="post" name="saveEditForm" action="<%=request.getContextPath() %>/system/dic/editDicType"> 
        	<input type="hidden" id="datatypeid" name="datatypeid"  />
        <div class="modal-body">
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>名称</label>
                <div class="col-md-7">
                  <input type="text" id="name" name="name" class="form-control parsley-validated" data-required="true" data-rangelength="[0, 32]"> 
                </div>
            </div>
            
            <div class="form-group"> 
                <label class="col-md-3 control-label"><i class="required"></i>备注</label>
                <div class="col-md-7">
                  <input type="text" id="comment" name="comment" class="form-control parsley-validated" data-maxlength="32">
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label">状态</label>
                <div class="col-md-7 ui-icheck-label">
                    <label><input type="radio" name="status" value="1"  class="icheck-input">启用</label>
                    <label><input type="radio" name="status" value="0" checked="checked" class="icheck-input">禁用</label>
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
 
</body>
</html>