<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="false"></jsp:include>
<body class="p-kfmanager p-complaint">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>系统设置 >> 意见与反馈管理</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>意见反馈列表</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" action="<%=request.getContextPath()%>/system/complaint/getComplaintList">
                                 <div class="form-input" style="margin-left:0px;">
                                    <input class="form-control" name="content" type="text" placeholder="请输入内容" style="width:160px;" value="${map.content }">     
                                </div>
                               <%--   <div class="form-select" style="margin-left:30px;">
                                    <select name="type" class="form-control select2-input select2-offscreen" style="width:120px;" value="${map.type }">
	                                     <option value="">请选择类型</option>
	                                     <option value="1" ${map.type == 1?'selected':'' }>建议</option>
	                                     <option value="2" ${map.type == 2?'selected':'' }>投诉</option>
                                    </select>
                                </div>  --%>
                                
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                    
                                </div>
                            </form>
                        </div>
                        
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                   <!--  <th data-sortable="true">标题</th> -->
                                    <th data-sortable="true">内容</th>
                                    <th data-sortable="true">联系方式</th>
                                  <!--   <th>类型</th> -->
                                    <th>发起人</th>
                                    <th>创建时间</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dataList }" var="u" varStatus="st">
									<input type="hidden" id="status_${u.feedbackid}" value="${u.status }"/>
                            		 <tr id="tr_${u.feedbackid }">
	                                    <td class="checkbox-column">${st.index+1}</td>
	                                   <%--  <td>${u.title }</td> --%>
	                                    <td width="248">${u.content }</td> 
	                                    <td>${u.contact}</td> 
	                                    <%-- <td>${u.type == 1?'建议':'投诉' }</td> --%>
	                                    <td>${u.realname }</td>
	                                    <td> <fmt:formatDate value="${u.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                                    <td>
	                                    	  <a href="#statusEdit" id="dt_${u.feedbackid }" onclick="initDel('${u.feedbackid}','1')" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i><span>删除</span></a>
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

function initDel(id,status){
	$("#feedbackid",$("#statusEditForm")).val(id);
	$("#status",$("#statusEditForm")).val(status);
}
 
 function updateStatus(){
	var feedbackid = $("#feedbackid",$("#statusEditForm")).val();
	var status = $("#status",$("#statusEditForm")).val();
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/system/complaint/updateStatus",
		data:{feedbackid:feedbackid,status:status},
		success:function(data){
			if(data){
				$("#btn_close").click();
				/* $("#tr_"+data.feedbackid).remove();
				  $('.ui-switch').removeClass('switch-off');
			        $.gritter.add({
			            title: '删除成功',
			            text: '该条记录已删除',
			            sticky: false,
			            time: '3000'
			        }); */
			        window.location.reload(true);
			}
		}
	});
 }
 
</script>
<div id="statusEdit" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form    id="statusEditForm" name="statusEditForm">
		<input type="hidden" id="feedbackid" name="feedbackid" />
		<input type="hidden" id="status" name="status" />
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