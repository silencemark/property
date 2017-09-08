<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title> 代理商管理</title>
</head>
<jsp:include page="/black/include/header.jsp" flush="false"></jsp:include>
<body class="p-suppe p-agency">
<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	<div class="content">
        <div class="crumb">
            <h5>商务合作 >>分销渠道</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>分销列表</span></h3>  
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" id="myform" action="<%=request.getContextPath()%>/system/agency/agencyManager">
                                <div class="form-input" style="margin-left: 10px">
                                   <input class="form-control"  name="username"  placeholder="请输入渠道账号" type="text"  value="${map.username}">
                                </div>
                                <div class="form-input" style="margin-left: 10px">
                                   <input class="form-control"  name="agencyname"  placeholder="请输入渠道名称" type="text"  value="${map.agencyname}">
                                </div>
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                    <a data-toggle="modal" data-backdrop="static" href="<%=request.getContextPath() %>/system/agency/agencyDetail"
                                    	style="margin-left:30px;"  class="btn btn-default"><i class="ico-add"></i>新 增</a>
                                </div>
                                <input class="form-control"   name="parentid"   type="hidden"  value="${map.parentid}">
                            </form>
                        </div>
                         <div class="area-operate"  >
		                     <button type="button" onclick="exportExcel();" class="btn btn-default ">导出渠道用户信息</button>
		                 </div>
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">渠道帐号</th>
                                    <th data-sortable="true">渠道名称</th>
                                    <th data-sortable="true">渠道类型</th>
                                    <th data-sortable="true">渠道区域</th>
                                    <th data-sortable="true">上级渠道</th>
                                    <th data-sortable="true">提成比例</th>
                                    <th data-sortable="true">渠道地址</th>
                                    <th data-sortable="true">联系人</th>
                                    <th data-sortable="true">联系电话</th>
                                    <th data-sortable="true">资质图片</th>
                                    <th data-sortable="true">创建时间</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dataList}" var="sup" varStatus="st">
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td>
	                                    <td>${sup.username }</td>
	                                    <td>${sup.agencyname }</td>
	                                    <td>
	                                      <c:choose>
	                                        <c:when test="${sup.roletype == '1'}">代理商</c:when>
	                                        <c:when test="${sup.roletype == '2'}">销售员</c:when>
	                                      </c:choose>
	                                    </td>
	                                    <td>${sup.provincename}&nbsp;${sup.cityname}&nbsp;${sup.districtname}</td>
	                                    <td>
	                                     <c:choose>
	                                        <c:when test="${sup.parentid == '0'}">平台</c:when>
	                                        <c:otherwise>${sup.parentname}</c:otherwise>
	                                     </c:choose>
	                                    
	                                    </td>
	                                    <td>${sup.commissionrate}%</td>
	                                    <td>${sup.address}</td>
	                                    <td>${sup.contacter}</td>
	                                    <td>${sup.phonenum}</td> 
	                                    <td>
	                                        <c:choose>
							                    <c:when test="${sup.agencyImgList.size()>0 }">
							                       <c:forEach items="${sup.agencyImgList}" var = "item">
   							                          <img   src="${item.agencyImgShow}" onclick="imgutil.FDIMG(this)" width="50px" height="50px"/> 
							                       </c:forEach>
							                    </c:when>
							                  </c:choose>
	                                    
	                                    </td>  
	                                    <td><fmt:formatDate value="${sup.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
	                                    <td>
	                                    	<a href="<%=request.getContextPath() %>/system/agency/agencyDetail?agencyid=${sup.agencyid}" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>修改</a>
	                                    	<a href="<%=request.getContextPath() %>/system/agency/agencyManager?parentid=${sup.agencyid}" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>查看下级</a>
	                                    	<a href="<%=request.getContextPath() %>/system/member/getMemberList?sourcepid=${sup.userid}" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>查看会员</a>
											<%-- <a href="#deleteTip" onclick="deleteData('${sup.agencyid}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>删除</a> --%>
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
		url:"<%=request.getContextPath()%>/system/agency/exportAgencyInfo",
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
            window.location.href='<%=request.getContextPath()%>/system/agency/downloadAgencyExcel';        	   
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
	 
	function deleteData(id){
		$('#agencyid',$('#deleteForm')).val(id);
	}
	
	function delAjax(){
		var agencyid = $("#agencyid").val();
		var delflag = $("#delflag").val();
	    $.ajax({
	    	type:"post",
	    	url:"<%=request.getContextPath()%>/system/agency/updateAgency",
	    	data:{agencyid:agencyid,delflag:delflag,ty:"del"},
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
<div id="deleteTip" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form  id="deleteForm" name="deleteForm" theme="simple" method="post" >
		<input type="hidden" id="agencyid" name="agencyid" />
		<input type="hidden" id="delflag" name="delflag" value="1" />
		
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4>确定删除？</h4>
        <p class="tips-modal-sub">删除之后无法恢复!</p>
      </div>
		
      <div class="modal-footer">
        <button type="button" id="btn_close" class="btn btn-close" data-dismiss="modal">取 消</button>
        <button type="button" onclick="delAjax();" class="btn btn-save"><i class="ico-ok"></i>确 定</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


</body>
</html>