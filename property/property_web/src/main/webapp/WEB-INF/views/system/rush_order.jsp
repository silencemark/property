<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-order p-rushorder"> 
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>订单管理 >> 抢单管理</h5>
        </div>
         	<div class="content-container">
            <div class="u">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>抢单管理</span></h3>
                    <div class="u area-list-dec">
                        <div class="area-search">
                              <form class="form-inline" method="post" id="myform" action="<%=request.getContextPath()%>/system/order/rushorder">
                               <input type="hidden" name="v_query" value="${map.v_query}" />
                                <div class="form-input" >
                                    <input class="form-control" name="title" type="text" placeholder="请输入订单标题" style="width:120px;" value="${map.title }">     
                                </div>
                                <div class="form-input" style="margin-left:10px;">
                                    <input class="form-control" id="starttime" name="starttime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endtime\')}'})" type="text" placeholder="请输入开始时间" style="width:140px;margin-left: 30px" value="${map.starttime }">           
                                </div>
                                
                                <div class="form-input" style="margin-left:10px;">
                                     <input class="form-control" id="endtime" name="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'starttime\')}'})" type="text" placeholder="请输入结束时间" style="width:140px;margin-left: 30px" value="${map.endtime }">         
                                </div>  
                                <div class="form-select">
                                     <select name="status" class="form-control select2-input select2-offscreen" style="width:120px;margin-left: 30px" value="${map.status }">
											<option value="" selected="selected">全部</option>
					                		<option ${map.status == 1?'selected':'' } value="1">待处理</option>
	                                        <option ${map.status == 2?'selected':'' } value="2">已取消</option>
	                                        <option ${map.status == 3?'selected':'' } value="3">已成交</option>
	                                        <option ${map.status == 4?'selected':'' } value="4">未成交</option>
					                		<option ${map.status == 5?'selected':'' } value="5">工人取消</option>
	                                        <option ${map.status == 6?'selected':'' } value="6">雇主同意</option>
	                                        <option ${map.status == 7?'selected':'' } value="7">雇主拒绝</option>
	                                        <option ${map.status == 8?'selected':'' } value="8">雇主取消</option>
					                		<option ${map.status == 9?'selected':'' } value="9">工人同意</option>
	                                        <option ${map.status == 10?'selected':'' } value="10">工人拒绝</option>
	                                        <option ${map.status == 11?'selected':'' } value="11">已过期</option>
	                                        <option ${map.status == 12?'selected':'' } value="12">已被抢走</option>
                                    </select>
                                </div>
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                </div>
                            </form> 
                        </div>
                          <div class="area-operate"  >
		                     <button type="button" onclick="exportExcel();" class="btn btn-default ">导出抢单记录</button>
		                 </div>   
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                	<th data-sortable="true">序号</th>
                                	<th data-sortable="true">抢单单号</th>
                                    <th data-sortable="true">抢单人</th>
                                    <th data-sortable="true">联系方式</th>
                                    <th data-sortable="true">发单单号</th>
                                    <th data-sortable="true">订单标题</th>
                                    <th data-sortable="true">发布人</th>
                                    <th data-sortable="true">工种/行业</th>
                                    <th data-sortable="true">金额/报价</th>
                                    <th data-sortable="true">订单类型</th>
                                    <th data-sortable="true">抢单状态</th>
                                    <th data-sortable="true">抢单时间</th>
                                 <!--    <th data-sortable="true">操作</th> -->
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${orderList }" var="u" varStatus="st">								
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td>
	                                    <td>${u.applyno}</td> 
	                                    <td>${u.releasename  }</td> 
	                                    <td>${u.appphone }</td> 
	                                    <td>${u.sendno}</td>
	                                    <td>${u.title }</td> 
	                                    <td>${u.pname }</td>
	                                    <td>${u.jobname}</td>     
	                                    <td>${u.amount}</td> 
	                                    <td>${u.isjob == null ?  '项目招工' : '临时招工'}</td> 
	                                    <td>
	                                       <c:choose>
										     <c:when test="${u.status eq 1 }">待处理</c:when>
										     <c:when test="${u.status eq 2 }">已取消</c:when>
										     <c:when test="${u.status eq 3 }">已成交</c:when>
										     <c:when test="${u.status eq 4 }">未成交</c:when>
										     <c:when test="${u.status eq 5 }">工人取消</c:when>
										     <c:when test="${u.status eq 6 }">雇主同意</c:when>
										     <c:when test="${u.status eq 7 }">雇主拒绝</c:when>
										     <c:when test="${u.status eq 8 }">雇主取消</c:when>
										     <c:when test="${u.status eq 9 }">工人同意</c:when>
										     <c:when test="${u.status eq 10 }">工人拒绝</c:when>
										     <c:when test="${u.status eq 11 }">已过期</c:when>
										     <c:when test="${u.status eq 12 }">已被抢走</c:when>
										   </c:choose>
	                                    </td>    
	                                    <td><fmt:formatDate value="${u.createtime }" pattern="YYYY-MM-dd HH:mm:ss"/></td>
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
function exportExcel(){
	var data = hhutil.getFormBean("myform");
	  
	 $.ajax({
		url:"<%=request.getContextPath()%>/system/order/exportRushOrderRecord",
		type:"post",
		data:data,
		success:function(data){
          if(data){
       	   $.gritter.add({
		            title: '提示',
		            text: "操作成功 ！正在导出...",
		            sticky: false,
		            time: '3000'
		        });
            window.location.href='<%=request.getContextPath()%>/system/order/downloadExcel?exname=滴答叫人抢单记录.xls';        	   
          }else{
       	   $.gritter.add({
		            title: '提示',
		            text: "导出失败",
		            sticky: false,
		            time: '3000'
		        });
			 return;
          }			
		},error:function(txt){
			 $.gritter.add({
		            title: '提示',
		            text: '操作失败，请稍后再试!',
		            sticky: false,
		            time: '3000'
		        });
			 return;
		}
		 
	 });
	 
}

</script>
</body>
</html>