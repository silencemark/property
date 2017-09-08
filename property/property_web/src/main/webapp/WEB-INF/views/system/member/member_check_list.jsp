<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
 <jsp:include page="/black/include/header.jsp" flush="false"></jsp:include>
<body class="p-member-menu  p-system-membercheck">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>会员管理 >> 会员认证</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>会员列表</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" action="<%=request.getContextPath()%>/system/member/checkMember">
                                 <div class="form-input" style="margin-left:0px;">
                                    <input class="form-control" name="username" type="text" placeholder="请输入登录名" style="width:120px;" value="${map.username }">     
                                </div>
                                <div class="form-input" style="margin-left:30px;">
                                    <input class="form-control" name="realname" type="text" placeholder="请输入姓名" style="width:120px;" value="${map.realname }">     
                                </div>
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                </div>
                                <div class="form-search-sub" style="margin-left:30px;">
                                	<c:choose>
                                		<c:when test="${switchmap.pvalue==1}">
                                			<button type="button" class="btn btn-default" style="margin-left:30px;" onclick="changeswith('0')"><i class="ico"></i>关闭发红包活动</button>
                                		</c:when>
                                		<c:otherwise>
                                			<button type="button" class="btn btn-default" style="margin-left:30px;" onclick="changeswith('1')"><i class="ico"></i>开启发红包活动</button>
                                		</c:otherwise>
                                	</c:choose>
                                    
                                </div>
                                
                            </form>
                        </div>
                         <div class="area-operate"  >
		                            <button type="button" onclick="changeCheck('1');" class="btn btn-default ">个人认证审核</button>
		                            <button type="button" onclick="changeCheck('2');" class="btn btn-default ">企业认证审核</button>
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
                                    <th data-sortable="true">登录名</th>
                                    <!-- <th data-sortable="true">昵称</th> -->
                                    <th data-sortable="true">姓名</th>
                                    <th data-sortable="true">身份证号</th>
                                    <th data-sortable="true">身份证图片</th>
                                    <th data-sortable="true">公司名称</th>
                                    <th data-sortable="true">公司证件照图片</th>
                                    <th data-sortable="true">最后登陆时间</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dataList }" var="u" varStatus="st">
                            		 <tr id="tr_${u.extendid }">
                            		   <td class="checkbox-column"><label class="icheck-inline">
									      <input id="checkbox_box" type="checkbox" class="icheck-input fundidchild" ent="${u.enterprisestatus }" inv="${u.individualstatus}" value="${u.extendid}" />
									    </label>
									    </td>
	                                    <td class="checkbox-column">${st.index+1}</td>
	                                    <td>${u.username }</td>
	                                  <%--   <td>${u.nickname }</td> --%>
	                                    <td>${u.realname }</td> 
	                                    <td>${u.idcardnum }</td>
	                                    <td>
	                                         <c:choose>
							                    <c:when test="${u.idCardImgList.size()>0 }">
							                       <c:forEach items="${u.idCardImgList}" var = "item">
   							                          <img   src="${item.idcardimgurl_show}" onclick="imgutil.FDIMG(this)" width="50px" height="50px"/> 
							                       </c:forEach>
							                    </c:when>
							                  </c:choose>
	                                    </td>
	                                    <td>${u.companyname }</td>
	                                    <td>
	                                         <c:choose>
							                    <c:when test="${u.companyimgurl != null and u.companyimgurl != ''}">
							                        <img onclick="imgutil.FDIMG(this)"  src="${u.companyimgurl_show}"  width="50px" height="50px"/>
							                    </c:when>
							                  </c:choose>
	                                    </td> 
	                                    <td><fmt:formatDate value="${u.lasttime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                                    <td>
	                                        <c:if test="${u.individualstatus eq 0 or u.individualstatus eq 3}">
	                                          <a href="#delflagEdit" onclick="initDel('${u.extendid}', '1')" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i><span>个人认证审核</span></a>
	                                        </c:if>
	                                        <c:if test="${u.enterprisestatus eq 0 or u.enterprisestatus eq 3}">
	                                          <a href="#delflagEdit" onclick="initDel('${u.extendid}', '2')" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i><span>企业认证审核</span></a>
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
<a href="#change_status" data-toggle="modal" data-backdrop="static" id="tps_t"></a>
<script type="text/javascript">
function changeswith(status){
	 $.ajax({
			type:"post",
			url:"<%=request.getContextPath()%>/system/member/updatesysparam?pvalue="+status,
			success:function(data){
				location.reload(true);
			}
		});
}
 function changeCheck(flg){
	 
	var idList  =new Array();
	if(flg == '1'){
		$(".fundidchild").each(function(index,dom){
			if($(this).is(":checked") && $(this).attr("inv") == '0'){
				idList.push($(this).val());
			}
		});
		$("#tip_info_status").html("个人认证审核");
    }else{
    	
    	$(".fundidchild").each(function(index,dom){
			if($(this).is(":checked") &&   $(this).attr("ent") == '0'){
				idList.push($(this).val());
			}
		});
    	$("#tip_info_status").html("企业认证审核");
    }
	 
	 if(idList.length ==0){
		 $.gritter.add({
	            title: '操作失败',
	            text: '您还没有勾选列或者没有可操作的数据',
	            sticky: false,
	            time: '3000'
	        });
		 return;
	 }
	 $('#doublepass').attr('disabled',false);
	 $("#tps_t").click();
	 $("#idList",$("#c_form")).val(idList);
	 $("#occut",$("#c_form")).val(flg);
	 
 }
 
 
function initDel(id,flg){
	$('#singlepass').attr('disabled',false);
	if(flg == '1'){
		$("#tip_info").html("个人认证审核");
		$("#flg",$("#delflagEditForm")).val("1");
	}else{
		$("#tip_info").html("企业认证审核");
		 
		$("#flg",$("#delflagEditForm")).val("2");
	}
	$("#extendid",$("#delflagEditForm")).val(id);
	
}
 
 function changeAll(status){
	 var flg = $("#occut",$("#c_form")).val();
	 var enterprisestatus = "";
	 var individualstatus = "";
	 var idlist = $("#idList",$("#c_form")).val();
	 var  opt={};
	 if(flg == '1'){
			if(status == '1'){
				individualstatus="1";
			}else {
				individualstatus="2";
			}
			opt = {
					flg:'1',
					idlist:idlist,
					individualstatus:individualstatus
			}
		 }else{
			if(status == '1'){
				enterprisestatus="1";
			}else {
				enterprisestatus="2";
			}
			opt = {
					flg:'1',
					idlist:idlist,
					enterprisestatus:enterprisestatus
			}
		}
	 
	 $.ajax({
			type:"post",
			url:"<%=request.getContextPath()%>/system/member/changeCheckStatus",
			data:opt,
			success:function(data){
				if(data.status=='1'){
					$("#btn_close").click();
					alert("用户认证失败，由于商户余额不足，无法继续发送红包,请尽快充值");
					location.reload(true);
				}else{
					$("#btn_close").click();
	                location.reload(true);
				}
			}
		});
	 
 }
 
 
 function updateStatus(status){
	var extendid = $("#extendid",$("#delflagEditForm")).val();
	var flg = $("#flg",$("#delflagEditForm")).val();
	var enterprisestatus = "";
	var individualstatus = "";
	var  opt={};
	if(flg == '1'){
		if(status == '1'){
			individualstatus="1";
		}else {
			individualstatus="2";
		}
		opt = {
				extendid:extendid,
				individualstatus:individualstatus
		}
	 }else{
		if(status == '1'){
			enterprisestatus="1";
		}else {
			enterprisestatus="2";
		}
		opt = {
				extendid:extendid,
				enterprisestatus:enterprisestatus
		}
	}
 
 
	$.ajax({
		type:"post",
		url:"<%=request.getContextPath()%>/system/member/changeCheckStatus",
		data:opt,
		success:function(data){
			if(data.status=='1'){
				$("#btn_close").click();
				alert("认证失败，由于商户余额不足，无法发送红包,请尽快充值");
				location.reload(true);
			}else{
				$("#btn_close").click();
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
		<input type="hidden" id="extendid" name="extendid" />
		<input type="hidden" id="flg" name="flg" />
		<input type="hidden" id="idList" name="idList" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title" id="tip_info">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-info"></i>
        <h4><span id="txt">请选择操作（通过/拒绝）</span></h4>
      </div>
      <div class="modal-footer">
        <button type="button" onclick="updateStatus('1');$(this).attr('disabled','disabled');" class="btn btn-save" id="singlepass"><i class="ico-ok"></i>通过</button>
        <button type="button" onclick="updateStatus('2');" style="background-color: #FF7200;border-color: #FF7200" class="btn btn-save"><i class="ico-ok"></i>拒绝</button>
        <button type="button" id="btn_close" class="btn btn-close" data-dismiss="modal">取 消</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
 
 
 <div id="change_status" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form id="c_form" name="c_form">
		 
		<input type="hidden" id="idList" name="idList" />
		<input type="hidden" id="occut" name="occut" />
		
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title" id="tip_info_status">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-info"></i>
        <h4><span id="txt">请选择操作（通过/拒绝）</span></h4>
      </div>
      <div class="modal-footer">
        <button type="button" onclick="changeAll('1');$(this).attr('disabled','disabled');" class="btn btn-save" id="doublepass"><i class="ico-ok"></i>通过</button>
        <button type="button" onclick="changeAll('2');" style="background-color: #FF7200;border-color: #FF7200" class="btn btn-save"><i class="ico-ok"></i>拒绝</button>
        <button type="button" id="btn_close" class="btn btn-close" data-dismiss="modal">取 消</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
 
 
</body>
</html>