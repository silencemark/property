<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.lr.backer.util.Constants"%>
<!DOCTYPE html>
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<script src="<%=request.getContextPath() %>/js/jquery.md5.js"></script> 
<body class="p-dashboard"> 
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	</div> 
	
<div class="content">
	<div class="crumb">
		<h5>
			<h5>修改密码</h5>
		</h5>
	</div>
    <div class="content-container">
        <div class="row">
            <div class="col-md-12" role="main">
                <h3 class="current-tit"><span>修改密码</span></h3>
  				<div class="table-responsive">
                    <div class="modal-body">
                    <form  id="updatePwdForm" class="form-horizontal parsley-form" role="form" method="post" action="<%=request.getContextPath() %>/system/updatePassword"
								onsubmit="return chkPwd();" data-validate="parsley">
								<input type="hidden" id="userid" name="userid" value="${map.userid }">
								<input type="hidden" id="oldpwd1" value="${map.oldPassword }" >
	                    	<table class="table table-bordered table-highlight table_tab_li" width="100%">
		                    	<tr>  
		                    		<td>   
		                   	 			<div class="form-group"  style="width:50%;line-height: 100%">
						                <label class="col-md-3 control-label"><i class="required">*</i>输入原密码</label>
						                <div class="col-md-7">
						                  <input type="password" class="form-control parsley-validated"  data-maxlength="64" value=""  id="oldpwd2" />
						                </div>
						            	</div> 
	                   	 			</td>
	                   	 			
	                   	 			
		                    	</tr>
	                   	 		<tr>
	                   	 			<td> 
							            <div class="form-group"  style="width:50%;line-height: 100%">
							                <label class="col-md-3 control-label"><i class="required">*</i>输入新密码</label>
							                <div class="col-md-7">
							                  <input type="password" class="form-control parsley-validated"  data-maxlength="64" value="" id="pwd1" name="password"/>
							                </div>
							            </div>
					            	</td>
	                   	 		</tr>
	                   	 		<tr>
	                   	 			<td>
		                   	 			<div class="form-group"  style="width:50%;line-height: 100%">
						                <label class="col-md-3 control-label"><i class="required">*</i>再次输入新密码</label>
						                <div class="col-md-7">
						                
						                  <input type="password" class="form-control parsley-validated" data-maxlength="64" value="" id="pwd2" name="password"/>
						                </div>
						            	</div>
	                   	 			</td>
	                   	 		</tr>
	                   	 		
	                    	</table><br/>
						    <div class="form-group">
									<div class="col-md-7 col-md-offset-2">
										<!--  <input type="submit" class="btn btn-save" value="保 存"/>-->
											<button  class="btn btn-save">确定</button>
											
										
									</div>
							</div>
							</form>
                    </div>
                </div> 
            </div>
        </div>
    </div>
</div>
<script>
function chkPwd(){
	var pwd1=$("#pwd1").val();
	var pwd2=$("#pwd2").val();
	var oldpwd1=$("#oldpwd1").val();
	if(0 == $("#oldpwd2").val()){  
		alert('请输入原密码');
		return false; 
	}
	var oldpwd2=$.md5($("#oldpwd2").val()); 
	if(oldpwd1!=oldpwd2){
		alert("输入的原密码不正确，请重新输入"); 
		return false;
		
	}
	if(pwd1 == ''){
		alert('请输入新的密码');
		return false;
	}	 
	
	if(pwd1!=pwd2){ 
		alert("两次输入的密码不相同，请重新输入");
		return false;
	}
		
} 
</script>




<!-- <script type="text/javascript">
	var editor = new baidu.editor.ui.Editor({
		initialFrameHeight : 400,
		initialFrameWidth : 700
	});
	editor.render("content");
</script> -->

	    </div>
</body>
</html>