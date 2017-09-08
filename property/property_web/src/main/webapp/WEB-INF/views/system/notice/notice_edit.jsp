<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<jsp:include page="/black/include/header.jsp" flush="true"></jsp:include>
<link rel="stylesheet" type="text/css"href="<%=request.getContextPath()%>/ueditor/themes/default/css/ueditor.css" />
<script type="text/javascript" charset="utf-8"src="<%=request.getContextPath()%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"src="<%=request.getContextPath()%>/ueditor/ueditor.all.min.js"></script>	
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/ueditor/lang/zh-cn/zh-cn.js"></script>
<body class="p-setting p-notice">
	<div class="wrapper">
		<jsp:include page="/black/include/top.jsp" flush="true"></jsp:include>
		<jsp:include page="/black/include/left.jsp" flush="true"></jsp:include> 
	</div>
	
	<div class="content">
		<div class="crumb">
				<h5>
					系统设置 » 通知管理
				</h5>
		</div>
        <div class="content-container">
	            <div class="row">
	                <div class="col-md-12" role="main">
	                    <h3 class="current-tit"><span>通知编辑</span></h3> 
	                    
	               <div class="tab-content">
	                    
	                    <div class="modal-body"> 
	                    <form id="activity" method="post" action="<%=request.getContextPath()%>/system/notice/editNotice"  class="form-horizontal parsley-form" data-validate="parsley">
	                        <table class="table table-bordered table-highlight table_tab_li tab_li_2" width="100%">
	                        <span id="t_content" style="display: none">${data.content}</span>
	                        <tr>
	                    		   <td colspan="2"> 
		                    			<div class="form-group">
							                <label class="col-md-2 control-label">消息标题 </label>
							                <div class="col-md-7">
							                  <input type="text" id="title" name="title" value="${data.title }" class="form-control parsley-validated" data-required="true" data-maxlength="32">
							                  <input type="hidden" id="noticeid" name="noticeid" value="${data.noticeid }"/>
							                </div>
							            </div>
							            
	                    			</td>
	                    		</tr> 
	                    	 
	                    	   <tr > 
	                    	      <td colspan="2">
	                    	        <div class="form-group">
									<label style="" class="col-md-2 control-label">
										 消息内容
									</label>
									<div class="col-md-7"> 
										<textarea id="content" name="content" rows="5" cols="15">
                                        </textarea>
									</div>
								</div>
	                    	      </td>
	                    	   </tr>
	                        </table>
	                        <br>
	                    	<button type="submit"  class="btn btn-save"><i class="ico-ok"></i>保 存</button> 
	                    	<a href="javascript:history.go(-1)" style="width: 80px;height:32px " class="btn btn-close" data-dismiss="modal">取 消</a> 
	                    	</form>
	                    	
				        </div> 
	               </div>
	           </div>
	     </div>
	   </div>
	</div>   
<script> 
$(function(){
	$('#type').select2({
	    maximumSelectionSize : 1,                               // 限制数量
	});
	
	if('${data.type }'==""){
		$('#type').select2('val',"");
	}
	var editor = new baidu.editor.ui.Editor({
		initialFrameHeight : 400,
		initialFrameWidth : 600,
		toolbars:[[
                   'fullscreen', 'source', '|', 'undo', 'redo', '|',
                   'bold', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
                   'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                   'customstyle', 'paragraph', 'fontfamily', '|',
                   'directionalityltr', 'directionalityrtl', 'indent', '|',
                   'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                   'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                   'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'gmap', 'insertframe', 'insertcode', 'webapp', 'pagebreak', 'template', 'background', '|',
                   'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
                   'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
                   'print', 'preview', 'searchreplace', 'help', 'drafts'
               ]]
	});
	editor.render("content");
	document.getElementById("content").value =$("#t_content").html();
	$('.user-tooltip').hover(function() {
		$(this).find('.user-detail').stop().show();
	}, function() {
		$(this).find('.user-detail').stop().hide();
	});
});
 
</script> 
</body>
</html>