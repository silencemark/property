<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>嘀嗒叫人</title>
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<style type="text/css">
.gridBody th{
	text-align: center;
}
.gridBody td{ 
	text-align: center;
}
</style>
<body class="p-order p-releaseorder">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	</div>
	
<div class="content">
	<div class="crumb">
		<h5>
			订单管理>> 项目详情
		</h5>
	</div>
    <div class="content-container">
        <div class="row">
            <div class="col-md-12" role="main">
                <h3 class="current-tit"><span>项目详情</span></h3>
  				<div class="table-responsive">
                    <div class="modal-body">
	                    	<table class="table table-bordered table-highlight table_tab_li" width="100%">
		                    	<tr>
		                    		<td>
		                   	 			<div class="form-group"  style="line-height: 300%">
						                <label class="col-md-3 control-label">项目标题</label>
						                <div class="col-md-7">
						                  <input type="text" class="form-control parsley-validated" readonly="readonly"  value="${projectmap.projecttitle}"/>
						                </div>
						            	</div>
	                   	 			</td>
	                   	 			<td> 
							            <div class="form-group"  style="line-height: 300%">
							                <label class="col-md-3 control-label">所属行业</label>
							                <div class="col-md-7">
							                  <input type="text" class="form-control parsley-validated" readonly="readonly"  value="${projectmap.cname }"/>
							                </div>
							            </div>
					            	</td>
		                    	</tr>
	                   	 		<tr>
	                   	 			<td>
							            <div class="form-group" style="line-height: 300%">
							                <label class="col-md-3 control-label">项目周期</label>
							                <div class="col-md-7">
							                  <input type="text" class="form-control parsley-validated" readonly="readonly"  value="<fmt:formatDate value="${projectmap.starttime }" pattern="MM-dd HH:mm"/>至<fmt:formatDate value="${projectmap.endtime }" pattern="MM-dd HH:mm"/>"/>
							                </div>
							            </div>
	                    			</td>
	                   	 			<td>
							             <div class="form-group" style="line-height: 300%">
							                <label class="col-md-3 control-label">工作（项目）地区</label>
							                <div class="col-md-7">
							                  <input type="text" class="form-control parsley-validated" readonly="readonly"  value="${projectmap.projectarea}"/> 
							                </div>
							            </div>
	                    			</td>
	                   	 		</tr>
	                   	 		<tr>
	                   	 			<td> 
							            <div class="form-group"  style="line-height: 300%">
							                <label class="col-md-3 control-label">项目描述</label>
							                <div class="col-md-7">
							                  <input type="text" class="form-control parsley-validated" readonly="readonly"  value="${projectmap.projectdescription }"/>
							                </div>
							            </div>
					            	</td> 
	                    			<td>
		                   	 			<div class="form-group"  style="line-height: 300%">
						                <label class="col-md-3 control-label">联系人</label>
						                <div class="col-md-7">
						                
						                  <input type="text" class="form-control parsley-validated" readonly="readonly"  value="${projectmap.contacter }" />
						                </div>
						            	</div>
	                   	 			</td>
	                   	 		</tr>
	                   	 		<tr>
	                   	 			<td>
		                   	 			<div class="form-group"  style="line-height: 300%">
						                <label class="col-md-3 control-label">联系方式</label>
						                <div class="col-md-7">
						                
						                  <input type="text" class="form-control parsley-validated" readonly="readonly"  value="${projectmap.telephone }" />
						                </div>
						            	</div>
	                   	 			</td>
	                   	 			<td>
		                   	 			<div class="form-group"  style="line-height: 300%">
						                <label class="col-md-3 control-label">项目地址</label>
						                <div class="col-md-7">
						                  <input type="text" class="form-control parsley-validated" readonly="readonly"  value="${projectmap.adress }" />
						                </div>
						            	</div>
	                   	 			</td> 
	                   	 		</tr>
	                   	 		<tr>
	                   	 			<td colspan="2">
		                   	 			<div class="form-group"  style="line-height: 300%">
						                <label class="col-md-3 control-label">项目图片</label>
						                <div class="col-md-7">
						                	<div id="room_divdm">
									                 <c:forEach items="${projectimglist}" var="pro" varStatus="st">
									                  		<img class="form-control" src="<%=request.getContextPath()%>${pro.url}"  id="headimage_img" style="width: 50px; height: 50px"/>
									                 </c:forEach>
							                 </div>
						                </div>
						            	</div>
	                   	 			</td>
	                   	 		</tr>
	                   	 		
	                    	</table><br/>
                    </div>
                </div> 
                
                 <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc"><center>序号</center></th>
                                    <th data-sortable="true"><center>姓名</center></th>
                                    <th data-sortable="true"><center>联系方式</center></th>
                                    <th data-sortable="true"><center>申请报价</center></th>
                                    <th data-sortable="true"><center>报价描述</center></th>
                                    <th data-sortable="true"><center>状态</center></th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${applyList}" var="item" varStatus="st">
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td> 
	                                    <td>${item.realname }</td>
	                                    <td>${item.phone }</td>
	                                    <td>${item.quotation }</td>
	                                    <td>${item.quotationdescription }</td>
	                                    <td>
	                                    	${item.status == 1?'处理中':item.status == 2 ? '待确认':item.status == 3 ? '已成交' :'未成交' }
	                                    </td>
	                                </tr> 
                            	</c:forEach>
                            </tbody>
                        </table>              
                    </div>
                
                
                
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

function shieldData(tenderid,shield){
	$('#shiel_a').click();
	if(shield == "1"){
		$('#shield',$('#shieldForm')).val("0");
		$('#shield_txt').text("解除屏蔽?");
	}else{
		$('#shield',$('#shieldForm')).val("1");
		$('#shield_txt').text("屏蔽?");
	}
	$('#tenderid',$('#shieldForm')).val(tenderid);
}


</script>

<a  href="#shielddiv" onclick=""  data-toggle="modal" data-backdrop="static" style="display: none" id="shiel_a"></a>

<div id="shielddiv" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form  action="<%=request.getContextPath()%>/system/edittenderHome" id="shieldForm"  method="post" name="shieldForm"
				 theme="simple" method="post" >
		<input type="hidden" id="tenderid" name="tenderid" />
		<input type="hidden" id="shield" name="shield" />
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4>确定<span id="shield_txt"></span></h4>
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