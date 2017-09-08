<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-kfmanager p-evaluate"> 
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>客服管理 >> 评价管理</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>评价列表</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                              <form class="form-inline" method="post" action="<%=Constants.PROJECT_PATH%>/system/evaluate/getEvaluateList">
                               
                                <div class="form-input" >
                                    <input class="form-control" name="description" type="text" placeholder="请输入评价内容" style="width:140px;" value="${map.description }">     
                                </div>
                                <div class="form-input" >
                                    <input class="form-control" id="starttime" name="starttime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endtime\')}'})" type="text" placeholder="请输入评论开始时间" style="width:140px;margin-left: 30px" value="${map.starttime }">     
                                </div>
                                <div class="form-input" >
                                    <input class="form-control" id="endtime" name="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'starttime\')}'})" type="text" placeholder="请输入评论结束时间" style="width:140px;margin-left: 30px" value="${map.endtime }">     
                                </div>
                                
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                </div>
                            </form> 
                        </div>
                        <div class="area-operate">
                       
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                	<th data-sortable="true">序号</th>
                                    <th data-sortable="true">评价人</th>
                                    <th data-sortable="true">评价时间</th>
                                    <th data-sortable="true">评价订单</th>
                                    <th data-sortable="true">订单发布人</th>
                                    <th data-sortable="true">评价分数</th>
                                    <th data-sortable="true">评价内容</th>
                                    <th data-sortable="true">是否封杀</th>
                                    <th data-sortable="true">是否屏蔽</th>
                                    <th data-sortable="true">操作</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dataList }" var="u" varStatus="st">								
                            		 <tr>
	                                    <td class="checkbox-column">${st.count}</td>
	                                    <td>${u.pname  }</td>
	                                    <td><fmt:formatDate value="${u.createtime }" pattern="YYYY-MM-dd HH:mm:ss"/></td>
	                                    <td>${u.title }</td>  
	                                    <td>${u.oname }</td> 
	                                    <td>${u.score }</td> 
	                                    <td width="300px">${u.description }</td>
	                                    <td>${u.isban == 0?'否':'是' }</td>
	                                    <td>${u.status == 0?'未屏蔽':'已屏蔽' }</td>
	                                    <td>
	                                       <c:choose>
	                                          <c:when test="${u.status == 0  }">
	                                            <a href="#delModel" onclick="initDel('${u.id}','${ u.status}')" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>屏蔽</a>
	                                          </c:when>
	                                          <c:when test="${u.status == 1 }">
	                                            <a href="#delModel" onclick="initDel('${u.id}','${u.status }')" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>取消屏蔽</a>
	                                          </c:when>
	                                       </c:choose>
	                                       
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
	  $("#id","#statusEditForm").val(id);
	  $("#status","#statusEditForm").val(status);
  }
 
  function updateStatus(){
	  var id = $("#id","#statusEditForm").val();
	  var status =  $("#status","#statusEditForm").val();
	  if(id=="" || status == ""){
		  $("#btn_close").click();
		  $.gritter.add({
	            title: '操作失败',
	            text: 'sorry 操作失败，请稍后再试',
	            sticky: false,
	            time: '3000'
	        });
		 return ;
	  }
	 
	  if(status == "0"){
		  status="1";
	  }else{
		  status="0";
	  }
	  
	 $.ajax({
		 type:"post",
		 url:"<%=request.getContextPath()%>/system/evaluate/updateEvluate",
		 data:{id:id,status:status},
		 success:function(data){
			 $("#btn_close").click();
			 if(data){		
				  window.location.reload(true);
			 }else{
				  $.gritter.add({
			            title: '操作失败',
			            text: 'sorry 操作失败，请稍后再试',
			            sticky: false,
			            time: '3000'
			        });
			 }
			
		 },error:function(data){
			 $.gritter.add({
		            title: '操作失败',
		            text: 'sorry 操作失败，请稍后再试',
		            sticky: false,
		            time: '3000'
		        });
		 }
	 });
	  
  }
  
</script>
<div id="delModel" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form    id="statusEditForm" name="statusEditForm">
		<input type="hidden" id="id" name="id" />
		<input type="hidden" id="status" name="status" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4>您确定要进行此操作？<span id="txt"></span></h4>
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