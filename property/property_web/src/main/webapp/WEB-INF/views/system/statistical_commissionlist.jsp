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
<link href="<%=request.getContextPath()%>/js/z-tree/css/demo.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/js/z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<body class="p-statistical p-statistical-commission">
<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	<div class="content">
        <div class="crumb">
            <h5>数据统计 >>代理商佣金统计</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>代理商佣金统计</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" id="myform" action="<%=request.getContextPath()%>/system/commissionlist">
                            	<input type="hidden" name="parentid" id="pId" value="${map.parentid}"/>
                                <span>分销渠道：</span>
                                 <div class="form-input" style="margin-left:30px;">
                                 	  <input type="text" name="agencysalename" id="agency_parent_name"  value="${map.agencysalename}" class="form-control parsley-validated"  readonly="readonly" style="cursor: pointer;">
					                  <input type="hidden" name="salerid" id="parentid" value="${map.salerid}"/>
					                  <ul id="treeDemo"  class="ztree" style="margin-top:0; width:80%;position: absolute;z-index: 999999;height: auto;display:none;width:260px"></ul>
                                 </div>     
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                </div>
                                  
                            </form>
                        </div>
                         <div class="area-operate"  >
			                     <button type="button" onclick="exportExcel();" class="btn btn-default ">导出佣金信息</button> 
			                 </div>   
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">代理商帐号</th>
                                    <th data-sortable="true">代理商名称</th>
                                    <th data-sortable="true">代理区域</th>
                                    <th data-sortable="true">上级代理</th>
                                    <th data-sortable="true">会员消费金额</th>
                                    <th data-sortable="true">佣金比例</th>
                                    <th data-sortable="true">获得佣金</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dataList}" var="sup" varStatus="st">
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td>
	                                    <td>${sup.username }</td>
	                                    <td>${sup.agencyname }</td>
	                                    <td>${sup.provincename}&nbsp;${sup.cityname}&nbsp;${sup.districtname}</td>
	                                    <td>
	                                     <c:choose>
	                                        <c:when test="${sup.parentid == '0'}">无</c:when>
	                                        <c:otherwise>${sup.parentname}</c:otherwise>
	                                     </c:choose>
	                                    
	                                    </td>
	                                    <td>${sup.amount}</td>
	                                    <td>${sup.commissionrate}%</td>
	                                    <td>${sup.commissionamount}</td>
	                                   
	                                    <td>
	                                    	<c:if test="${sup.soncount!=0}">
	                                    		<a href="<%=request.getContextPath() %>/system/commissionlist?parentid=${sup.agencyid}" data-toggle="modal" data-backdrop="static"><i class="ico-about"></i>查看下级代理商</a>
											</c:if>
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
var setting = {
		view: {
			dblClickExpand: false,
			expandSpeed: "fast",
			fontCss : {color:"black"},
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0
			}
		},
		callback: {
			/* beforeClick: beforeClick, */
			onClick: onClick
		}
	};
	var dataArray = new Array();
	<c:if test="${ifmanager==1}">   
	dataArray.push(obj={
	  userid:'',id:-1,pId:0,name:"平台"
   });
</c:if>
	 <c:choose>
	  <c:when test="${agencysaleList.size()>0}">
	  <c:forEach items="${agencysaleList}" var ="row">
	       var  obj ={
	    	userid:'${row.userid}',
	        id:'${row.agencyid}',
	        pId:'${row.parentid}',
	        name:'${row.agencyname}',
	        agencytype:'${row.agencytype}'
	       };
	        dataArray.push(obj);
	    </c:forEach>
	  </c:when>
	 </c:choose>
	var zNodes = dataArray;

	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getSelectedNodes(),
		v = "";
		id ="";
		userid="";
		pId="";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
			id+= nodes[i].id + ",";
			userid+= nodes[i].userid + ",";
			pId+= nodes[i].pId + ",";
		}
		if (v.length > 0 ) 
		v = v.substring(0, v.length-1);
		id = id.substring(0, id.length-1);
		userid= userid.substring(0, userid.length-1);
		pId= pId.substring(0, pId.length-1);
		var cityObj = $("#agency_parent_name");
		cityObj.attr("value", v);
		$("#parentid").val(userid);
		$("#treeDemo").hide();
		$("#pId").val(pId);
	}

$(document).ready(function(){
	<c:if test="${agencysaleList.size()>0}">
	$('#agency_parent_name').click(function(){
		$("#treeDemo").toggle();
	});
	</c:if>
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
})

function exportExcel(){
	var data = hhutil.getFormBean("myform");  
	 $.ajax({
		url:"<%=request.getContextPath()%>/system/member/exportCommissionRecord",
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
           window.location.href='<%=request.getContextPath()%>/system/member/downloadCommissionExcel';        	   
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
<script type="text/javascript">
	$('select[name=salerid]').select2({
	    maximumSelectionSize : 1,                               // 限制数量
	});
	$('select[name=parentagencyid]').select2({
	    maximumSelectionSize : 1,                               // 限制数量
	});
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
	
	function AgencyChange(obj){
		if($(obj).val() != ''){
			$.ajax({   
				url:'<%=request.getContextPath() %>/system/getAgencyData?parentid='+$(obj).val(),
				type:'post',
				dataType:'json',  
				success:function(data){
					$('select[name="salerid"]').html();  
					var str = "";
					$.each(data.data,function(i,item){ 
						str += "<option value="+item.userid+">"+item.agencyname+"</option>";   			
					});           
					$('select[name="salerid"]').append(str);    
					$('select[name="salerid"]').select2('val','${map.salerid}');
				} 
			});
		} 
	}
	$('select[name="parentagencyid"]').change();   
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