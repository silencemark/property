<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>财务管理</title>
</head>
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-trade p-trade-manage">
<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>财务管理 >> 财务记录</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>财务列表</span></h3>
                    <div class="row area-list-dec">
                          <div class="area-search">
                             <form class="form-inline" id="myform" method="post" action="<%=request.getContextPath()%>/system/trade/gettradelist">
                                <input type="hidden" name="v_query" value="${map.v_query}"/>
                                <div class="form-select">
                                  <div class="form-input" >
                                    <input class="form-control" name="realname" type="text" placeholder="请输入会员姓名" style="width:120px;" value="${map.realname }">     
                                  </div>
                                   <div class="form-input" style="margin-left:30px;">
                                     <input class="form-control" name="agencyname"  type="text" placeholder="请输入来源名称" style="width:140px;margin-left: 30px" value="${map.agencyname }">         
                                   </div>   
                                  
                                    <select name="paymethod" class="form-control select2-input select2-offscreen" style="width:120px;margin-left: 30px" value="${map.paymethod }">
											<option value="" selected="selected">请选择支付方式</option>
					                		<option ${map.paymethod == 1?'selected':'' } value="1">滴滴币</option>
	                                        <option ${map.paymethod == 2?'selected':'' } value="2">微信</option>
	                                        <option ${map.paymethod == 3?'selected':'' } value="3">支付宝</option>
	                                        <option ${map.paymethod == 99?'selected':'' } value="99">其他</option>
                                    </select>
                                      <input type="hidden" name="source" value=""/>
                                <div class="form-input" style="margin-left:30px;">
                                    <input class="form-control" id="starttime" name="starttime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endtime\')}'})" type="text" placeholder="请输入开始时间" style="width:140px;margin-left: 30px" value="${map.starttime }">           
                                </div>
                                
                                <div class="form-input" style="margin-left:30px;">
                                     <input class="form-control" id="endtime" name="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'starttime\')}'})" type="text" placeholder="请输入结束时间" style="width:140px;margin-left: 30px" value="${map.endtime }">         
                                </div>           
                                    
                                </div>
                                 
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                </div>
                            </form>  
                        </div>
                         <div class="area-operate"  >
		                     <button type="button" onclick="exportExcel();" class="btn btn-default ">导出财务记录</button>
		                 </div> 
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">订单号</th>
                                    <th data-sortable="true">收入金额</th>
                                    <th data-sortable="true">支出金额</th>
                                    <th data-sortable="true">支付方式</th>
                                    <th data-sortable="true">支付类型</th>
                                    <th>会员姓名</th>
                                    <th>会员来源</th>
                                    <th>交易时间</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${datalist}" var="data" varStatus="st">
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td>
	                                    <td >${data.orderno}</td>
	                                    <td>
	                                    	<c:if test="${data.incomepay==2}">
		                                    	${data.amount}
		                                    </c:if>
		                                    
	                                    </td>
	                                    <td>
	                                    	<c:if test="${data.incomepay==1}">
		                                    	${data.amount}
		                                    </c:if>
	                                    </td>
	                                    <td>
	                                    	<c:if test="${data.paymethod==1}">
		                                    	嘀嗒币
		                                    </c:if>
		                                    <c:if test="${data.paymethod==2}">
		                                    	微信
		                                    </c:if>
		                                    <c:if test="${data.paymethod==3}">
		                                    	支付宝
		                                    </c:if>
		                                    <c:if test="${data.paymethod==99}">
		                                    	其它
		                                    </c:if>
	                                    </td>
	                                    <td>
	                                    	<c:if test="${data.paypurposetype==1}">
		                                    	消费
		                                    </c:if>
		                                    <c:if test="${data.paypurposetype==2}">
		                                    	查看工人信息
		                                    </c:if>
		                                    <c:if test="${data.paypurposetype==99}">
		                                    	资料完善奖励红包
		                                    </c:if>
	                                    </td>
	                                    <td>${data.realname}</td>
	                                    <td>${data.agencyname}</td>
	                                    <td>
											${data.createtime}
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
function exportExcel(){
	var data = hhutil.getFormBean("myform");
	  
	 $.ajax({
		url:"<%=request.getContextPath()%>/system/trade/exportTradeRecord",
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
            window.location.href='<%=request.getContextPath()%>/system/order/downloadExcel?exname=滴答叫人财务记录.xls';        	   
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