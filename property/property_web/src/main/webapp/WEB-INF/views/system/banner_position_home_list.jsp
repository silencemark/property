<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<script src="<%=request.getContextPath() %>/black/js/hhutil.js"></script> 
<body class="p-setting p-homepage-banner">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>系统设置 >> banner位置列表 </h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>banner位置列表 </span></h3>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">banner位置</th>
                                    <th data-sortable="true">code</th>
                                    <th data-sortable="true">创建时间</th>
                                    <th data-sortable="true">创建者</th>
                                    <th data-sortable="true">操作</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${bannerPositionList}" var="item" varStatus="st">
                            		 <tr>
	                                    <td class="checkbox-column">${st.count }</td>
	                                    <td>${item.name }</td>
	                                    <td>${item.code }</td>
	                                    <td>
	                                    <fmt:formatDate value="${item.createtime }"  type="date" dateStyle="long"/>
	                                    <fmt:formatDate value="${item.createtime }"  type="time"/>
	                                    </td>
	                                    <td>${item.realname }</td>
	                                    <td><a href="<%=request.getContextPath()%>/system/banner2ListHome?positionid=${item.id}">查看</a></td>
	                                  
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
</body>
</html>