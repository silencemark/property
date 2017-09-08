<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html> 
<jsp:include page="/black/include/header.jsp" flush="false"></jsp:include>
<body class="p-setting p-notice">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	
	<div class="content">
        <div class="crumb">
            <h5>系统设置 >> 系统通知</h5>
        </div>
         	<div class="content-container">
            <div class="row">
                <div class="col-md-12" role="main">
                    <h3 class="current-tit"><span>系统通知用户列表</span></h3>
                    <div class="row area-list-dec">
                        <div class="area-search">
                            <form class="form-inline" method="post" action="<%=request.getContextPath()%>/system/notice/getNoticeMemberList?noticeid=${map.noticeid}">
                                 <div class="form-input" style="margin-left:0px;">
                                    <input class="form-control" name="realname" type="text" placeholder="请输入用户名称" style="width:140px;" value="${map.realname }">     
                                </div>
                                <div class="form-search-sub" style="margin-left:30px;">
                                    <button type="submit" class="btn btn-default" style="margin-left:30px;"><i class="ico-search"></i>查 询</button>
                                   
                                </div>		
                            </form>
                        </div>
                        
                    </div>
                    <div class="table-responsive">
                        <table class="gridBody table table-striped table-bordered table-hover table-highlight table-checkable order-column" data-provide="datatable">
                            <thead>
                                <tr>
                                    <th width="80px" data-sortable="true" data-direction="asc">序号</th>
                                    <th data-sortable="true">消息名称</th>
                                    <th data-sortable="true">接收人</th>
                                    <th data-sortable="true">是否已读</th>
                                    <th data-sortable="true">发送时间</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${dataList }" var="u" varStatus="st">
									<input type="hidden" id="delflag_${u.noticeid}" value="${u.delflag }"/>
                            		 <tr id="tr_${u.noticeid }">
	                                    <td class="checkbox-column">${st.index+1}</td>
	                                    <td>${u.title }</td>
	                                    <td>${u.rname }</td>
	                                    <td>${u.isread == 0?'未读':'已读' }</td>
	                                    <td> <fmt:formatDate value="${u.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
 
</body>
</html>