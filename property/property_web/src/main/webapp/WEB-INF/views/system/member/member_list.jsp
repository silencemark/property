<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="false"></jsp:include>
<body class="p-member-menu p-member">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>${param.source==null?'权限设置 >> 会员管理':'商务管理 >> 分销渠道' }</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>会员列表</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" id="myform" method="post" action="<%=request.getContextPath()%>/system/member/getMemberList">
                            	<input type="hidden" name="sourcepid" value="${map.sourcepid}"/>
                            	<input type="hidden" name="add_query" value="${map.add_query}"/>
                            	<input type="hidden" name="v_query" value="${map.v_query}"/>
                                <div class="form-input" style="margin-left:0px;">
                                    <input class="form-control" name="realname" type="text" placeholder="请输入姓名" style="width:120px;" value="${map.realname }">     
                                </div>
                                <div class="form-input" style="margin-left:10px;">
                                    <input class="form-control" name="companyname" type="text" placeholder="请输入公司名称" style="width:120px;" value="${map.companyname }">     
                                </div>
                                
                                <div class="form-input" style="margin-left:10px;">
                                    <input class="form-control" name="phone" type="text" placeholder="请输入手机号" style="width:120px;" value="${map.phone }">     
                                </div>
                                
                                <div class="form-input" style="margin-left:10px;">
                                    <input class="form-control" name="agencyname" type="text" placeholder="请输入来源名称" style="width:120px;" value="${map.agencyname }">     
                                </div>
                                
                                <input type="hidden" name="source" value="${map.source}"/>
                                <div class="form-input" style="margin-left:10px;">
                                    <input class="form-control" id="starttime" name="starttime"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',maxDate:'#F{$dp.$D(\'endtime\')}'})" type="text" placeholder="请输入开始时间" style="width:140px;margin-left: 30px" value="${map.starttime }">           
                                </div>
                                
                                
                                <div class="form-input" style="margin-left:5px;">
                                     <input class="form-control" id="endtime" name="endtime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'#F{$dp.$D(\'starttime\')}'})" type="text" placeholder="请输入结束时间" style="width:140px;margin-left: 30px" value="${map.endtime }">         
                                </div>
                                
                                <div class="form-search-sub" style="margin-left:10px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:5px;"><i class="ico-search"></i>查 询</button>
                                   <%--  
                                    <a data-toggle="modal" data-backdrop="static" href="<%=request.getContextPath() %>/notice/initEditNotice"  
                                    	style="margin-left:30px;"   class="btn btn-default"><i class="ico-add"></i>新 增</a> --%>
                                </div>
                            </form>
                        </div>
                        <c:if test="${zhangshangflag!=true}">
                        <div class="area-operate"  >
		                     <button type="button" onclick="exportExcel();" class="btn btn-default ">导出会员信息</button>
		                 </div>
		                 </c:if>
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <!-- <th data-sortable="true">登录名</th> -->
                                    <!-- <th data-sortable="true">昵称</th> -->
                                    <th data-sortable="true">姓名</th>
                                    <th data-sortable="true">地区</th>
                                    <c:if test="${zhangshangflag!=true}">
                                    	<th data-sortable="true">手机号</th>
                                    </c:if>
                                    <th data-sortable="true">公司名称</th>
                                    <th data-sortable="true">个人认证状态</th>
                                    <th data-sortable="true">企业认证状态</th>
                                    <th data-sortable="true">发布订单数</th>
                                    <th data-sortable="true">滴滴币余额</th>
                                    <th data-sortable="true">来源</th>
                                    <th>最后登陆时间</th>
                                    <th>状态</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dataList }" var="u" varStatus="st">
									 
                            		 <tr id="tr_${u.memberid }">
	                                    <td class="checkbox-column">${st.index+1}</td>
	                                    <%-- <td>${u.username }</td> --%>
	                                    <%-- <td>${u.nickname }</td> --%>
	                                    <td>${u.realname }</td>
	                                    <td>${u.provincename}&nbsp;${u.cityname}</td>
	                                    <c:if test="${zhangshangflag!=true}">
	                                     <td>${u.phone }</td>
	                                     </c:if>
	                                    <td>${u.companyname }</td>
	                                    <td>
	                                    <c:choose>
	                                        <c:when test="${u.individualstatus eq 0 or u.individualstatus == null }">待审核</c:when>
	                                        <c:when test="${u.individualstatus eq 1}">审核通过</c:when>
	                                        <c:when test="${u.individualstatus eq 2}">审核不通过</c:when>
	                                        <c:when test="${u.individualstatus eq 3}">再次审核</c:when>
	                                    </c:choose>
	                                    </td>
	                                   <td>
	                                      <c:choose>
	                                        <c:when test="${u.enterprisestatus eq 0  or u.enterprisestatus == null}">待审核</c:when>
	                                        <c:when test="${u.enterprisestatus eq 1}">审核通过</c:when>
	                                        <c:when test="${u.enterprisestatus eq 2}">审核不通过</c:when>
	                                        <c:when test="${u.enterprisestatus eq 3}">再次审核</c:when>
	                                    </c:choose>
	                                   </td>
	                                    <td>
	                                        ${u.ordernum }
	                                    </td>
	                                    
	                                    <td>${u.tickcoin ==null?0:u.tickcoin}</td>
	                                    <td>
	                                        ${u.agencyname }
	                                    </td>
	                                    <td><fmt:formatDate value="${u.lasttime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                                    <td>
	                                    <c:choose>
	                                    	<c:when test="${u.status==1}">
	                                    		已启用
	                                    	</c:when>
	                                    	<c:otherwise>
	                                    		已禁用
	                                    	</c:otherwise>
	                                    </c:choose>
	                                    </td>
	                                    <td>
	                                    	<a href="<%=request.getContextPath() %>/system/member/getMemberDetail?memberid=${u.memberid}" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i><span>详情</span></a>
	                                    	<c:if test="${zhangshangflag!=true}">
		                                    	<c:choose>
			                                    	<c:when test="${u.status==1}">
			                                    		<a href="#statusEdit"  onclick="statusEdit('${u.memberid}','0');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i><span>禁用</span></a>
			                                    	</c:when>
			                                    	<c:otherwise>
			                                    		<a href="#statusEdit"  onclick="statusEdit('${u.memberid}','1');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i><span>启用</span></a>
			                                    	</c:otherwise>
			                                    </c:choose>
			                                 </c:if>
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

function statusEdit(id,status){
	if(status=="1"){
		$('#status',$('#statusEditForm')).val("1");
		$('#txt').text("启用?");
	}else{
		$('#status',$('#statusEditForm')).val("0");
		$('#txt').text("禁用?");
	}
	$('#memberid',$('#statusEditForm')).val(id);
}
function exportExcel(){
	var data = hhutil.getFormBean("myform"); 
	 $.ajax({
		url:"<%=request.getContextPath()%>/system/member/exportMemberRecord",
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
           window.location.href='<%=request.getContextPath()%>/system/member/downloadMemberInfoExcel';        	   
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
 <div id="statusEdit" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form  action="<%=request.getContextPath()%>/system/member/memberedit" id="statusEditForm" name="statusEditForm"
				 theme="simple" method="post" >
		<input type="hidden" id="memberid" name="memberid" />
		<input type="hidden" id="status" name="status" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4>确定<span id="txt"></span></h4>
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