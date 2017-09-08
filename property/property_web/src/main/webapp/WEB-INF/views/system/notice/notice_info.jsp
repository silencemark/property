<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>消息详情</title>
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include> 
<body class="p-setting p-notice">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include>

	</div>
	<div class="content">
		<div class="crumb">
			<h5>系统管理 » 消息管理</h5>
		</div>
		<div class="content-container">
			<div class="row">
				<div class="col-md-12" role="main">
					<h3 class="current-tit">
						<span>消息详情</span>
					</h3>

					<div class="tab-content">

						<div class="modal-body" style="border-bottom: 0">
							<table
								class="table table-bordered table-highlight table_tab_li tab_li_2"
								width="100%" >

							 
								<tr>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">消息名称</label>
											<div class="col-md-7">
												<label>${data.title } </label>
												<%-- <input type="text"   value="${data.phone }" class="form-control parsley-validated"  > --%>
											</div>
										</div>
									</td>
									 
								</tr>
						 	<tr>
									 
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">消息内容</label>
											<div class="col-md-7">
												<label>${data.content } </label>
												<%--  <input type="text"   value="${data.tickcoin }" class="form-control parsley-validated"  > --%>
											</div>
										</div>
									</td>
								</tr>
						 
						 
						 
							</table>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
 
</body>
</html>