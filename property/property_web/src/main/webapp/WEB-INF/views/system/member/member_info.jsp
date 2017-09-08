<%@page import="com.lr.backer.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员详情</title>
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include> 
<body class="p-member-menu  p-member">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include>

	</div>
	<div class="content">
		<div class="crumb">
			<h5>权限设置 » 会员管理</h5>
		</div>
		<div class="content-container">
			<div class="row">
				<div class="col-md-12" role="main">
					<h3 class="current-tit">
						<span>会员详情</span>
					</h3>

					<div class="tab-content">

						<div class="modal-body">
							<table
								class="table table-bordered table-highlight table_tab_li tab_li_2"
								width="100%" >

								<tr >
									<td width="50%"> 
										<div class="form-group">
											<label class="col-md-2 control-label">昵称 </label>
											<div class="col-md-2"  >
												<label>${data.nickname} </label>
												<%-- <input value="${data.nickname}" type="text" class="form-control parsley-validated" > --%>
											</div>
										</div>

									</td>

									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">用户头像</label>
											<div class="col-md-2">
												<c:choose>
													<c:when
														test="${data.headimage != null and data.headimage != ''}">
													 <img
															src="${data.headimage_show}" onclick="imgutil.FDIMG(this)" width="50px" height="50px" /> 
													</c:when>
												</c:choose>
											</div>
										</div>
									</td>

								</tr>
							 

								<tr>
									<td width="50%">
										<div class="form-group">
											<label class="col-md-2 control-label">姓名</label>
											<div class="col-md-7">
												<label>${data.realname } </label>
												<%-- <input type="text" value="${data.realname }" class="form-control parsley-validated"  > --%>
											</div>
										</div>
									</td>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">性别</label>
											<div class="col-md-7">
												<c:choose>
													<c:when test="${data.sex==1}">
														<label>男</label>
													</c:when>
													<c:when test="${data.sex==1}">
														<label>女</label>
													</c:when>
													<c:otherwise>
														<label>保密</label>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</td>

								</tr>
								<tr>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">手机号</label>
											<div class="col-md-7">
												<label>${data.phone } </label>
												<%-- <input type="text"   value="${data.phone }" class="form-control parsley-validated"  > --%>
											</div>
										</div>
									</td>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">滴滴币</label>
											<div class="col-md-7">
												<label>${data.tickcoin } </label>
												<%--  <input type="text"   value="${data.tickcoin }" class="form-control parsley-validated"  > --%>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">成功抢单</label>
											<div class="col-md-7">
												<label>${data.applynum } 单</label>
												<%--  <input type="text"  value="${data.applynum }" class="form-control parsley-validated"  > --%>
											</div>
										</div>
									</td>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">成功发布</label>
											<div class="col-md-7">
												<label>${data.ordernum } 单</label>
												<%--  <input type="text"   value="${data.ordernum }" class="form-control parsley-validated"  > --%>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">详细地址</label>
											<div class="col-md-7">
												<label>${data.address } </label>
												<%-- <input type="text"  value="${data.address }" class="form-control parsley-validated"  > --%>
											</div>
										</div>
									</td>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">个人图片</label>
											<div class="col-md-7">

												<c:choose>
													<c:when test="${personalImgList.size()>0}">
														<c:forEach items="${ personalImgList}" var="row">
															 <img src="${row.personal_show}" onclick="imgutil.FDIMG(this)" width="50px" height="50px" /> 
														</c:forEach>
													</c:when>
												</c:choose>

											</div>
										</div>
									</td>

								</tr>
								<tr>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">身份证号</label>
											<div class="col-md-7">
												<label>${data.idcardnum } </label>
												<%--  <input type="text"  value="${data.idcardnum }" class="form-control parsley-validated"  > --%>
											</div>
										</div>
									</td>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">证件照</label>
											<div class="col-md-7">
												<c:choose>
													<c:when test="${idCardImgList.size()>0}">
														<c:forEach items="${ idCardImgList}" var="row"
															varStatus="t">
															 <img
																src="${row.idcardimgurl_show}" onclick="imgutil.FDIMG(this)" width="50px"
																height="50px" />  
														</c:forEach>
													</c:when>
												</c:choose>
											</div>
										</div>
									</td>
								</tr>


								<tr>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">公司名称</label>
											<div class="col-md-7">
												<label>${data.companyname } </label>
												<%--  <input type="text"   value="${data.companyname }" class="form-control parsley-validated"  > --%>
											</div>
										</div>
									</td>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">公司营业执照</label>
											<div class="col-md-7">
												<c:choose>
													<c:when
														test="${data.companyimgurl != null and data.companyimgurl != ''}">
													     <img  src="${data.companyimgurl_show}"  onclick="imgutil.FDIMG(this)" width="50px" height="50px">  
													</c:when>
												</c:choose>
											</div>
										</div>
									</td>
								</tr>
								<tr>


								</tr>
								<tr>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">个人简介</label>
											<div class="col-md-7">

												 <label>${data.personalintroduction }</label>
											</div>
										</div>
									</td>
									<td>
										<div class="form-group">
											<label class="col-md-2 control-label">公司介绍</label>
											<div class="col-md-7">
												<label>${data.companyintroduction }</label>
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