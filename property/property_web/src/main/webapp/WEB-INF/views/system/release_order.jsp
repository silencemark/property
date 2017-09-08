
<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-order p-releaseorder">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>订单管理 >> 发布订单管理</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>发布订单管理</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                             <form class="form-inline" method="post" id="myform" action="<%=request.getContextPath()%>/system/order/releaseorder">
                                <input type="hidden"  name="v_query" value="${map.v_query}" />
                                <%--<div class="form-select">
                                    <div class="form-input" > 
                                    <input class="form-control" name="projectname" type="text" placeholder="请输入所属项目" style="width:120px;" value="${map.projectname }">     
                                </div> --%>
                                
                                <div class="form-input" style="margin-left:0px;">
                                    <input class="form-control" name="title" type="text" placeholder="请输入订单标题" style="width:120px;" value="${map.title }">     
                                </div>
                                <div class="form-input" style="margin-left:10px;">
                                    <input class="form-control" id="starttime" name="starttime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endtime\')}'})" type="text" placeholder="请输入开始时间" style="width:140px;margin-left: 30px" value="${map.starttime }">           
                                </div>
                                
                                <div class="form-input" style="margin-left:10px;">
                                     <input class="form-control" id="endtime" name="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'starttime\')}'})" type="text" placeholder="请输入结束时间" style="width:140px;margin-left: 30px" value="${map.endtime }">         
                                </div>  
                                <select name="status_or" class="form-control select2-input select2-offscreen" style="width:90px;margin-left: 30px" value="${map.status }">
											<option value="" selected="selected">全部</option>
					                		<option ${map.status_or == 1?'selected':'' } value="1">处理中</option>
	                                        <option ${map.status_or == 2?'selected':'' } value="2">已完成</option>
                                    </select>
                                      <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                </div>
                                </form>  
                            </div>
                           <div class="area-operate"  >
		                     <button type="button" onclick="exportExcel();" class="btn btn-default ">导出发布订单记录</button>
		                 </div>          
                              
                        </div>
                        
                       <%--  <div class="area-operate">
                             <button type="button" onclick="exportExcel();" class="btn btn-default ">导出订单信息</button> 
                            
                            <button type="button" class="btn btn-default"><i class="ico-edit"></i>修 改</button>
                            <button data-toggle="modal" data-backdrop="static" href="#del" type="button" class="btn btn-default"><i class="ico-del"></i>删 除</button>
                            <button data-toggle="modal" data-backdrop="static" href="#accredit" type="button" class="btn btn-default"><i class="ico-key"></i>授 权</button>
                           
                        </div> --%>
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                	<th data-sortable="true">序号</th>
                                    <th data-sortable="true">订单号</th>
                                    <th data-sortable="true">订单标题</th>
                                    <th data-sortable="true">发布人</th>
                                    <th data-sortable="true">金额</th>
                                    <th data-sortable="true">工种/行业</th>
                                    <th data-sortable="true">工期</th>
                                    <th data-sortable="true">订单类型</th>
                                    <th data-sortable="true">状态</th>
                                    <th data-sortable="true">发布时间</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${orderList }" var="u" varStatus="st">								
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td>
	                                    <td>${u.orderno}</td>
	                                    <td>${u.title }</td> 
	                                    <td>${u.realname }</td> 
	                                    <td>${u.amount == null ? '------' :u.amount }</td>   
	                                    <td>${u.jobname}</td> 
	                                    <td><fmt:formatDate value="${u.starttime }" pattern="YYYY-MM-dd HH:mm"/>&nbsp;至&nbsp; <fmt:formatDate value="${u.endtime }" pattern="YYYY-MM-dd HH:mm"/></td> 
	                                    <td>${u.isjob == null ?  '项目招工' : '临时招工'}</td> 
	                                    <td>${u.status==1?'处理中':'已完成'}</td>    
	                                    <td><fmt:formatDate value="${u.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
	                                    <td>
	                                    	<c:choose>
	                                    	  <c:when test="${u.isjob == null}"><a href="<%=request.getContextPath() %>/system/order/releaseorderDetail?orderid=${u.orderid}&type=project&projectid=${u.projectid}"  data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>详情</a></c:when> 
	                                    	  <c:when test="${u.isjob != null}"><a href="<%=request.getContextPath() %>/system/order/releaseorderDetail?orderid=${u.orderid}&jobid=${u.jobid}"  data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>详情</a></c:when>
	                                    	</c:choose>
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
function exportExcel(){
	var data = hhutil.getFormBean("myform");  
	 $.ajax({
		url:"<%=request.getContextPath()%>/system/order/exportReleaseOrderRecord",
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
           window.location.href='<%=request.getContextPath()%>/system/order/downloadReleaseExcel';        	   
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