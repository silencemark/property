<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>供应商管理</title>
</head>
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-suppe p-suppe-manage">
<script type="text/javascript">
	function add(){
		window.location.href="<%=request.getContextPath()%>/system/supplier/initsupperinfo";
	}
	function deleteData(id){
		$('#supplierid',$('#deleteForm')).val(id);
	}	
</script>
<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>供应商管理 >> 供应商管理</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>供应商列表</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" id="myform" action="<%=request.getContextPath()%>/system/supplier/getsupperlist">
                                <div class="form-input" style="margin-left: 10px">
                                   <input class="form-control"  name="suppliername"  placeholder="供应商名称" type="text"  value="${map.suppliername}">
                                </div>
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                    <button data-toggle="modal" data-backdrop="static" onclick="add()"
                                    	style="margin-left:30px;" type="button" class="btn btn-default"><i class="ico-add"></i>新 增</button>
                                </div>
                                <input type="hidden" name ="sourcepid" value="${map.sourcepid}"/>
                            </form>
                        </div>
                        <c:if test="${isInvestment=='false'}">
                         <div class="area-operate"  >
		                     <button type="button" onclick="exportExcel();" class="btn btn-default ">导出供应商信息</button>
		                 </div>
		                 </c:if>
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">供应商名称</th>
                                    <th data-sortable="true">所属代理商</th>
                                    <th data-sortable="true">所在地区</th>
                                    <th data-sortable="true">主营产品</th>
                                    <th data-sortable="true">营业时间</th>
                                    <th>营业地址</th>
                                    <th>联系电话</th>
                                    <th>分享次数</th>
                                    <th>阅读次数</th>
                                    <th>创建人</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${supplierList}" var="sup" varStatus="st">
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td>
	                                    <td>${sup.suppliername }</td>
	                                    <td>${sup.agencyname==null?'平台':sup.agencyname }</td>
	                                    <td>${sup.provincename}&nbsp;${sup.cityname}&nbsp;${sup.districtname}</td>
	                                    <td width="300px">${sup.products }</td>
	                                    <td>${sup.businesshours}</td>
	                                    <td>${sup.address}</td>
	                                    <td>${sup.telephone}</td>
	                                    <td>${sup.sharetimes}</td>
	                                    <td>${sup.readtimes}</td>
	                                    <td>${sup.createname}</td>
	                                    <td>
	                                    	<a href="<%=request.getContextPath()%>/system/supplier/initupdatesupperinfo?supplierid=${sup.supplierid}" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>修改</a>
											<c:if test="${isInvestment=='false'}">
												<a href="#deleteTip" onclick="deleteData('${sup.supplierid}');" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>删除</a>
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
			

<div id="deleteTip" class="modal fade tips-modal tips-modal-warning">
  <div class="modal-dialog">
    <div class="modal-content">
      <form  action="<%=request.getContextPath()%>/system/supplier/detelesupper" id="deleteForm" name="deleteForm"
				 theme="simple" method="post" >
		<input type="hidden" id="supplierid" name="supplierid" />
		<input type="hidden" id="delflag" name="delflag" value="1" />
		
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h3 class="modal-title">提示</h3>
      </div>
      <div class="modal-body">
        <i class="ico-tips-warning"></i>
        <h4>确定删除？</h4>
        <p class="tips-modal-sub">删除之后无法恢复</p>
      </div>
		
      <div class="modal-footer">
        <button type="button" class="btn btn-close" data-dismiss="modal">取 消</button>
        <button type="submit" class="btn btn-save"><i class="ico-ok"></i>确 定</button>
      </div>
      </form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
function exportExcel(){
	var data = hhutil.getFormBean("myform"); 
	 $.ajax({
		url:"<%=request.getContextPath()%>/system/supplier/exportSupplierInfo",
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
            window.location.href='<%=request.getContextPath()%>/system/supplier/downloadSupplierExcel';        	   
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