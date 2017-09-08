<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-setting p-setting-dict-province">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>系统管理 >> 省市管理</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main"> 
                    <h3 class="current-tit"><span>市级管理</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" action="<%=request.getContextPath()%>/system/getAreaDic">
                            	<input type="hidden" value="${map.parentid }" name="parentid">
                            	<input type="hidden" value="${map.fromid }" name="fromid">
                                <div class="form-input">
                                   <input class="form-control"  name="cname"  placeholder="名称" type="text"  value="${map.cname }">
                                   
                                   
                                   
                                </div>
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                    
                                </div>
                                <button data-toggle="modal" data-backdrop="static" href="#save_edit" onclick="initAdd();" 
                                    	style="margin-left:30px;" type="button" class="btn btn-default"><i class="ico-add"></i>新 增</button>
                            </form>
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc"><center>序号</center></th>
                                    <th data-sortable="true"><center>名称</center></th>
                                    <th data-sortable="true"><center>状态</center></th>
                                    <th data-sortable="true"><center>创建时间</center></th>
                                    <th data-sortable="true"><center>操作</center></th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${provinceList}" var="item" varStatus="st">
                            		<input type="hidden" id="cname_${item.areaid}" value="${item.cname }"/>
                            		<input type="hidden" id="priority_${item.areaid}" value="${item.priority }"/>
                            		<input type="hidden" id="ifactive_${item.areaid}" value="${item.ifactive }"/>
                            		 <tr>
	                                    <td class="checkbox-column"><center>${st.count }</center></td> 
	                                    <td><center>${item.cname }</center></td>
	                                    
	                                    <td><center><c:if test="${item.ifactive==1 }">启用中</c:if><c:if test="${item.ifactive==0 }">禁用中</c:if></center></td>
	                                    
	                                    <td><center><fmt:formatDate value="${item.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></center></td>
	                                    <td><center>
											<a href="<%=request.getContextPath()%>/system/getAreaDic?parentid=${item.areaid}&fromid=district" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>详细</a>
	                                    	<a href="#save_edit" onclick="initEdit(${item.areaid});" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>修改</a>
						                    <c:if test="${1==item.ifactive }">
												<a href="#startTip" onclick="initIfactive(${item.areaid},${item.ifactive });" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>禁用</a>
											</c:if>
											<c:if test="${0==item.ifactive }">
												<a href="#startTip" onclick="initIfactive(${item.areaid},${item.ifactive });" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>启用</a>
											</c:if>
                						</center>
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

function initEdit(id){
	$('#areaid',$('#saveEditForm')).val(id);
	$('#cname',$('#saveEditForm')).val($('#cname_'+id).val());
	$('#priority',$('#saveEditForm')).val($('#priority_'+id).val());
	$('#saveEditForm').attr('action','<%=request.getContextPath()%>/system/updateArea');  
}
function initAdd(){
	$('#cname',$('#saveEditForm')).val('');
	$('#priority',$('#saveEditForm')).val('');  
	$('#saveEditForm').attr('action','<%=request.getContextPath()%>/system/insertArea'); 
} 
function initIfactive(id,w_id){ 
	$('#areaid',$('#startTip')).val(id);
	if(w_id == 1){
		$('#ifactivetext').html('确定禁用吗？');
		$('#ifactive',$('#ifActiveForm')).val(0);
	}else{
		$('#ifactivetext').html('确定启用吗？');
		$('#ifactive',$('#ifActiveForm')).val(1);
	}
	
}
</script>

<div id="save_edit" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h3 class="modal-title">编辑市级信息</h3>
        </div>
        <form id="saveEditForm" class="form-horizontal parsley-form" role="form" name="saveEditForm" method="post" action="<%=request.getContextPath()%>/system/updateArea">
        <input type="hidden" id="areaid" name="areaid"  />
        <input type="hidden" value="${map.parentid }" name="parentid"  />
        <input type="hidden" value="${map.fromid }" name="fromid"  />
        <input type="hidden" value="2" name="areatype"  />
        <div class="modal-body">
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>名称</label>
                <div class="col-md-7">
                  <input type="text" id="cname" name="cname" class="form-control parsley-validated" data-required="true">
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-md-3 control-label"><i class="required">*</i>排序</label>
                <div class="col-md-7"> 
                  <input type="number" id="priority" name="priority" class="form-control parsley-validated" data-required="true">
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

<div id="startTip" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form id="ifActiveForm" class="form-horizontal parsley-form" role="form" name="saveEditForm" method="post" action="<%=request.getContextPath()%>/system/updateArea">
		<input type="hidden" id="areaid" name="areaid"  />
        <input type="hidden" id="ifactive" name="ifactive"  />
       	<input type="hidden" value="${map.parentid }" name="parentid"  />
        <input type="hidden" value="${map.fromid }" name="fromid"  />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4><span id="ifactivetext">确定启用吗？</span></h4>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-close" data-dismiss="modal">取 消</button>
        <button type="submit" class="btn btn-save"><i class="ico-ok"></i>确 定</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->




</body>
</html>