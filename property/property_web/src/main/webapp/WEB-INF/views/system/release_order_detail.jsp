<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员详情</title>
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
 
<body class="p-order p-releaseorder">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	</div>
	
	<div class="content">
		<div class="crumb">
				<h5>
					订单管理  » 订单详情
				</h5>
		</div>
        <div class="content-container">
	            <div class="row">
	                <div class="col-md-12" role="main">
	                    <h3 class="current-tit"><span>订单详情</span></h3> 
	                    
	               <div class="tab-content">
	                    
	                    <div class="modal-body"> 
	                       <c:choose><c:when test="${type ==null }">
	                        <table class="table table-bordered table-highlight table_tab_li tab_li_2" width="100%">
	                        
	                        <tr>
	                    		   <td  width="50%"> 
		                    			<div class="form-group">
							                <label class="col-md-2 control-label">订单标题</label>
							                <div class="col-md-7">
							                ${jobMap.jobtitle }
							                  <%-- <input type="text" value="${jobMap.jobtitle }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div>
	                    			</td>
	                    		  <td> 
							          <div class="form-group" >
							                <label class="col-md-2 control-label">工种</label>
							                <div class="col-md-7">
							                ${jobMap.cname }
							                  <%--  <input type="text" value="${jobMap.cname }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    		</tr> 
	                    		
	                    		<tr>
	                    			
	                    			<td   width="50%"> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">薪资</label>
							                <div class="col-md-7">
							                ${jobMap.salary }
							                  <%-- <input type="text"  value="${jobMap.salary }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    			 <td> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">用工时间</label>
							                <div class="col-md-7">
							                <fmt:formatDate value="${jobMap.starttime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${jobMap.endtime }" pattern="yyyy-MM-dd HH:mm"/>
							                  <%-- <input type="text"  value="<fmt:formatDate value="${jobMap.starttime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${jobMap.endtime }" pattern="yyyy-MM-dd HH:mm"/>" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                    	  
	                    	   <tr>
	                    		   <td  width="50%"> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">结算方式</label>
							                <div class="col-md-7">
							                ${jobMap.settlementmethod }
							                  <%-- <input type="text"  value="${jobMap.settlementmethod }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    			<td> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">招聘人数</label>
							                <div class="col-md-7">
							                ${jobMap.recruitmentnum }
							                  <%-- <input type="text"   value="${jobMap.recruitmentnum }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                    	 
	                    	    <tr>
	                    		   <td  width="50%"> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">联系人</label>
							                <div class="col-md-7">
							                ${jobMap.contacter }
							                  <%-- <input type="text" value="${jobMap.contacter }" class="form-control parsley-validated" > --%>
							                </div>
							            </div> 
	                    			</td>
	                    			<td> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">联系方式</label>
							                <div class="col-md-7">
							                ${jobMap.telephone }
							                  <%-- <input type="text"  value="${jobMap.telephone }"  class="form-control parsley-validated"> --%>
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                    	   
	                    		  <tr>
	                    		  <td  width="50%"> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">工作地点</label>
							                <div class="col-md-7">
							                ${jobMap.workplace}
							                </div>
							            </div> 
	                    			</td>
	                    			<td  > 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">工作要求	</label>
							                <div class="col-md-7">
							                     <label>${jobMap.jobrequirements }</label>
							                </div>
							            </div>
	                    			</td>
	                    		</tr>
	                        </table>
	                        </c:when><c:otherwise>
	                        <table class="table table-bordered table-highlight table_tab_li tab_li_2" width="100%">
	                        
	                        <tr>
	                    		   <td width="50%"> 
		                    			<div class="form-group">
							                <label class="col-md-2 control-label">项目标题 </label>
							                <div class="col-md-7" style="height: 23px"> 
							                ${projectMap.projecttitle}
							                   <%-- <input value="${projectMap.projecttitle}" type="text" class="form-control parsley-validated" > --%>
							                </div>
							            </div>
							            
	                    			</td>
	                    			 <td> <!-- style="background-color:white;color:#333;cursor: pointer;" readonly="readonly" -->
		                    			<div class="form-group">
							                <label class="col-md-2 control-label">行业</label>
							                <div class="col-md-7">
							                ${projectMap.cname }
							                  <%-- <input type="text" value="${projectMap.cname }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div>
	                    			</td>
	                    		</tr> 
	                    		<tr>
	                    		</tr>
	                    		
	                    		<tr>
	                    			<td  width="50%"> 
							          <div class="form-group" >
							                <label class="col-md-2 control-label">项目周期</label>
							                <div class="col-md-7">
							                <fmt:formatDate value="${projectMap.starttime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${projectMap.endtime }" pattern="yyyy-MM-dd HH:mm"/>
							                  <%--  <input type="text"  value="<fmt:formatDate value="${projectMap.starttime }" pattern="yyyy-MM-dd HH:mm"/> 至 <fmt:formatDate value="${projectMap.endtime }" pattern="yyyy-MM-dd HH:mm"/>" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    			<td> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">工作地区</label>
							                <div class="col-md-7">
							                ${projectMap.projectarea }
							                  <%-- <input type="text"  value="${projectMap.projectarea }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                    		
	                    		<tr>
	                    		   <td  width="50%"> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">联系人</label>
							                <div class="col-md-7">
							                ${projectMap.contacter }
							                  <%-- <input type="text"  value="${projectMap.contacter }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    			<td> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">联系方式</label>
							                <div class="col-md-7">
							                ${projectMap.telephone }
							                  <%-- <input type="text"   value="${projectMap.telephone }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                    	 
	                    	   <tr>
	                    		   <td  width="50%"> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">项目地址</label>
							                <div class="col-md-7">
							                ${projectMap.address }
							                  <%-- <input type="text"  value="${projectMap.address }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    			<td> 
							            <div class="form-group" >
							                <label class="col-md-2 control-label">项目描述</label>
							                <div class="col-md-7">
							                ${projectMap.projectdescription }
							                  <%-- <input type="text"   value="${projectMap.projectdescription }" class="form-control parsley-validated"  > --%>
							                </div>
							            </div> 
	                    			</td>
	                    		</tr>
	                    	 
	                    	    <tr>
	                    		   <td  width="100%" colspan="2"> 
							            <div class="form-group" >
							                <label class="col-md-1 control-label">项目图片</label>
							                <div class="col-md-7">
							                <c:if test="${photolist.size()>0 }">
							                  <c:forEach items="${photolist }" var ="row">
							                    <img alt=""   onclick="imgutil.FDIMG(this)" src="${row.url }" width="50px" height="50px">
							                   </c:forEach>
							                  </c:if>
							                </div>
							            </div> 
	                    			</td>
	                    			 
	                    		</tr>
	                    	    
	                        </table> 
	                      </c:otherwise></c:choose>
				        </div> 
				        <div class="table-responsive">
                        <table id="table" class=" table table-striped table-bordered table-hover table-highlight" data-provide="datatable">
                         <caption ><h4 style="float: left">抢单人列表</h4></caption>
                            <thead>
                                <tr> 
									<th width="50">序号</th>
									<th>姓名</th> 
									<th>联系方式</th>
									<th>抢单时间</th> 
									<c:if test="${type!=null }"><th>项目报价</th></c:if>
									<th>状态</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${peopleList }" var="u" varStatus="s">  
                            		 <tr> 
										<td>${s.index+1}</td>
										<td>${u.realname}</td>
										<td>${u.phone}</td>
										<td><fmt:formatDate value="${u.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
										<c:if test="${type!=null }"><td>${u.quotation}</td></c:if>
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
	                                </tr>
                            	</c:forEach> 
                            </tbody>
                        </table>              
                    </div>  
                      ${pager } 
	               </div>
	           </div>
	     </div>
	   </div>
	</div>   
	 <script type="text/javascript">
	  $(function(){
		  $("a[name='projectimg']").each(function(i,item){
       	   $(item).imgbox({
  				'speedIn'		: 0,
  				'speedOut'		: 0,
  				'alignment'		: 'center',
  				'overlayShow'	: true,
  				'allowMultiple'	: false
  			}); 
          });
		  
	  });
	 </script>
</body>
</html>