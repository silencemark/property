<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="false"></jsp:include>
<body class="p-setting p-notice_auto">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>系统设置 >> 自动消息管理</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>消息列表</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" action="<%=request.getContextPath()%>/notice/getAutoMessage">
                                 <div class="form-input" style="margin-left:0px;">
                                    <input class="form-control" name="title" type="text" placeholder="请输入订单标题" style="width:140px;" value="${map.title }">     
                                </div>
                                
                                <%-- <div class="form-select" style="margin-left:30px;">
                                    <select name="type" class="form-control select2-input select2-offscreen" style="width:120px;" value="${map.type }">
	                                   <option value="">请选择消息类型</option>
	                                   <c:forEach items="${typeList }" var="row">
	                                     <option ${row.dataid == map.type ?'selected':''} value="${row.dataid}">${row.cname }</option>
	                                   </c:forEach>  
                                    </select>
                                </div>  --%>
                                
                                <div class="form-search-sub" style="margin-left:30px;">
                                    
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                    
                                    <a data-toggle="modal" data-backdrop="static" href="<%=request.getContextPath() %>/notice/initEditNotice"  
                                    	style="margin-left:30px;"   class="btn btn-default"><i class="ico-add"></i>新 增</a>
                                </div>
                            </form>
                        </div>
                        
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">消息名称</th>
                                    <th data-sortable="true">消息内容</th>
                                    <th data-sortable="true">消息类型</th>
                                    <th data-sortable="true">接收人/发起人</th>
                                    <th data-sortable="true">是否已读</th>
                                    <th data-sortable="true">所属订单名称</th>
                                    <th data-sortable="true">创建时间</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dataList }" var="u" varStatus="st">
									<input type="hidden" id="delflag_${u.noticeid}" value="${u.delflag }"/>
                            		 <tr id="tr_${u.noticeid }">
	                                    <td class="checkbox-column">${st.index+1}</td>
	                                    <td>${u.title }</td>
	                                    <td>${u.content }</td>
	                                    <td>${u.type == 1?'发布订单消息':'申请订单消息' }</td>
	                                    <td>${u.aname }</td>
	                                    <td>${u.isread == 0?'未读':'已读' }</td>
	                                    <td>${u.ordertitle }</td>
	                                    <td> <fmt:formatDate value="${u.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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

function initDel(id,status){
	$("#noticeid",$("#delflagEditForm")).val(id);
	$("#delflag",$("#delflagEditForm")).val(status);
}
 
 function updateStatus(){
	var noticeid = $("#noticeid",$("#delflagEditForm")).val();
	var delflag = $("#delflag",$("#delflagEditForm")).val();
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/notice/updateStatus",
		data:{noticeid:noticeid,delflag:delflag},
		success:function(data){
			if(data){
				$("#btn_close").click();
				$("#tr_"+data.noticeid).remove();
				$.gritter.add({
		            title: '删除成功',
		            text: '该条记录已删除',
		            sticky: false,
		            time: '3000'
		        });
			}
		}
	});
 }
 
</script>
<div id="delflagEdit" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form    id="delflagEditForm" name="delflagEditForm">
		<input type="hidden" id="noticeid" name="noticeid" />
		<input type="hidden" id="delflag" name="delflag" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4>您确定要删除吗？<span id="txt"></span></h4>
      </div>
		
      <div class="modal-footer">
        <button type="button" id="btn_close" class="btn btn-close" data-dismiss="modal">取 消</button>
        <button type="button" onclick="updateStatus();" class="btn btn-save"><i class="ico-ok"></i>确 定</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
 
</body>
</html>