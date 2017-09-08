<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="false"></jsp:include>
<body class="p-member-menu  p-system-memberinstance">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>会员管理 >> 会员保险</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>记录列表</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" id="myform" method="post" action="<%=request.getContextPath()%>/system/member/getInstanceList">
                                 <div class="form-input" style="margin-left:0px;">
                                    <input class="form-control" name="realname" type="text" placeholder="请输入真实姓名" style="width:120px;" value="${map.realname }">     
                                </div>
                                <div class="form-input" style="margin-left:30px;">
                                    <input class="form-control" name="phone" type="text" placeholder="请输入手机号" style="width:120px;" value="${map.phone }">     
                                </div>
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                </div>
                            </form>
                        </div>
                         <div class="area-operate"  >
                         	 <c:if test="${isInvestment=='false'}">
		                     <button type="button" onclick="exportExcel();" class="btn btn-default ">导出会员保险记录</button>
		                     </c:if>
		                     <button type="button" onclick="changeCheck();" class="btn btn-default ">批量购买</button>
		                 </div>
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th class="checkbox-column">
                                    <label class="icheck-inline">
									     <input id="checkbox_box" type="checkbox" class="icheck-input fatherchick" value="" />
									</label>
                                    </th>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">订单编号</th>
                                    <th data-sortable="true">订单标题</th>
                                    <th data-sortable="true">登录名</th>
                                    <!-- <th data-sortable="true">昵称</th> -->
                                    <th data-sortable="true">姓名</th>
                                    <th data-sortable="true">身份证号</th>
                                    <th data-sortable="true">手机号</th>
                                    <th data-sortable="true">生成时间</th>
                                    <th data-sortable="true">购买时间</th>
                                    <th data-sortable="true">是否购买</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dataList }" var="u" varStatus="st">
                            		 <tr id="tr_${u.insuranceid }">
                            		   <td class="checkbox-column"><label class="icheck-inline">
									      <input id="checkbox_box" type="checkbox" class="icheck-input fundidchild"  status="${u.status}"  value="${u.insuranceid}" />
									    </label>
									    </td>
	                                    <td class="checkbox-column">${st.count}</td>
	                                    <td>${u.orderno }</td>
	                                    <td>${u.title }</td>
	                                    <td>${u.username }</td>
	                                    <%-- <td>${u.nickname }</td> --%>
	                                    <td>${u.realname }</td> 
	                                    <td>${u.idcard }</td>
	                                    <td>
	                                        ${u.phone }  
	                                    </td>
	                                   
	                                    <td>
	                                         <fmt:formatDate value="${u.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/>
	                                    </td>
	                                    <td>
	                                         <fmt:formatDate value="${u.updatetime }" pattern="yyyy-MM-dd HH:mm:ss"/>
	                                    </td> 
	                                    <td> 
	                                         <c:choose>
	                                           <c:when test="${u.status eq 1 }">是</c:when>
	                                           <c:when test="${u.status eq 0 }">否</c:when>
	                                         </c:choose>
	                                    </td>
	                                    <td>
	                                        <c:if test="${u.status eq 0}">
	                                          <a href="#delflagEdit" onclick="initDel('${u.insuranceid}')" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i><span>购买</span></a>
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
<a href="#delflagEdit" data-toggle="modal" data-backdrop="static" id="tps_t"></a>
<script type="text/javascript">
 function exportExcel(){
	 var data = hhutil.getFormBean("myform"); 
	 $.ajax({
		url:"<%=request.getContextPath()%>/system/member/exportMemberInstanceRecord",
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
             window.location.href='<%=request.getContextPath()%>/system/member/downloadExcel';        	   
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
 function changeCheck(){
	var idList  =new Array();
		$(".fundidchild").each(function(index,dom){
			if($(this).is(":checked") && $(this).attr("status") == "0" ){
				idList.push($(this).val());
			}
		});
	 if(idList.length ==0){
		 $.gritter.add({
	            title: '操作失败',
	            text: '您还没有勾选列或者没有可操作的数据',
	            sticky: false,
	            time: '3000'
	        });
		 return;
	 }
	 
	 $("#tps_t").click();
	 $("#idList",$("#delflagEditForm")).val(idList);
	 
 }
 
 
function initDel(id){
	 $("#insuranceid",$("#delflagEditForm")).val(id);
	 
}
 
  
function buyInstance(){
	
	if( $("#insuranceid",$("#delflagEditForm")).val() == "" &&  $("#idList",$("#delflagEditForm")).val() == "" ){
		return ;
	}
     var idList = $("#idList",$("#delflagEditForm")).val();
	if($("#idList",$("#delflagEditForm")).val() == ""){
		idList =  $("#insuranceid",$("#delflagEditForm")).val();
	}
	
	var opt={
			idList:idList
	};
	
	 $.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/system/member/updateInstance",
		data:opt,
		success:function(data){
			if(data){
                location.reload(true);
			}
		}
	}); 
	
} 
 
	
 
 
</script>
<div id="delflagEdit" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form id="delflagEditForm" name="delflagEditForm">
		<input type="hidden" id="insuranceid" name="insuranceid" />
		<input type="hidden" id="idList" name="idList" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title" id="tip_info">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4><span id="txt">确定给选中的用户订单购买保险吗？</span></h4>
      </div>
      <div class="modal-footer">
        <button type="button" onclick="buyInstance();" class="btn btn-save"><i class="ico-ok"></i>确定</button>
        <button type="button" id="btn_close" class="btn btn-close" data-dismiss="modal">取 消</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>