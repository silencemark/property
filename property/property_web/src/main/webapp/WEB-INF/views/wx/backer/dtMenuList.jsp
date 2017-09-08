<%@ page language="java"  pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<!--[if lt IE 7 ]><html class="ie6 ieOld"><![endif]-->
<!--[if IE 7 ]><html class="ie7 ieOld"><![endif]-->
<!--[if IE 8 ]><html class="ie8 ieOld"><![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html><!--<![endif]-->
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>微信管理-自定义菜单</title>
		<meta name="Keywords" content="">
		<meta name="Description" content="">
		<jsp:include page="../../../../black/include/header.jsp" flush="true"></jsp:include>
		<link href="<%=request.getContextPath() %>/theme/grey/style/reset.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath() %>/theme/grey/style/base.css" rel="stylesheet" type="text/css" />
		<link href="<%=request.getContextPath() %>/theme/grey/style/page.css" rel="stylesheet" type="text/css" />
<style>
.webContainer{
	padding-top:80px;
	width: auto;
}
.webContainer .webMain{
	float: left;
}
.saved .content{
    margin-left: 0px; 
	min-height:auto;
}
<%-- .webContainer{
	background:url(<%=request.getContextPath()%>/black/js/weixin/images/ui/lineB.png) 15x 0 repeat-y #fff;
} --%>
</style>
</head>

<body class="p-weixin p-weixin-menu">
	<div class="wrapper">

	<jsp:include page="../../../../black/include/top.jsp"></jsp:include>

	<div class="content">
		<div class="crumb">
            <h5>微信管理 &#187; 自定义菜单</h5>
        </div>

	<!--container-->
	<div class="webContainer clearFix containerWcMenu" id="webContainer">

		<jsp:include page="../../../../black/include/left.jsp"></jsp:include>

		<!--main-->
		<div class="webMain">
			<div class="mainHeader">
				<h1>自定义菜单</h1>
			</div>
			<div class="mainBody">

				<div class="summary mb20">可创建最多3个一级菜单，每个一级菜单下可创建最多5个二级菜单。</div>

				<div class="wcMenu clearFix" id="wcMenu">
					<div class="l">
						<div class="header"><h6>菜单管理</h6><a href="javascript:void(0)" class="add"></a></div>
						<div class="bd"></div>
					</div>
					<div class="r">
						<div class="header">
							<h6>设置动作</h6>
							<%--<a href="javascript:void(0)" class="btnC stop">停用菜单</a>
							--%><!-- <a href="javascript:void(0)" class="btnB start">启用菜单</a> -->
						</div>
						<div class="bd">
							<p class="tips" id="wcMenuTips">你可以先添加一个菜单，然后开始为其设置响应动作</p>
							<div class="edit" id="wcMenuEdit" style="display:none">
								<div class="wcInputs" id="wcInputs">
									<div class="hd clearFix">
										<a href="javascript:void(0)" data-type="bdtxt" class="txt"><span></span>文本回复</a>
										<a href="javascript:void(0)" data-type="bdarticle" class="article"><span></span>图文消息</a>
										<a href="javascript:void(0)" data-type="bdimg" class="img"><span></span>图片回复</a>
										<a href="javascript:void(0)" data-type="bdaudio" class="audio"><span></span>语音</a>
										<a href="javascript:void(0)" data-type="bdlink" class="link"><span></span>链接跳转</a>
									</div>
									<div class="bd">
										<div class="bditem bdtxt">
											<textarea></textarea>
											<div class="tools"><p>还可以输入600字</p></div>
										</div>
										<div class="bditem bdarticle">
											<a href="javascript:void(0)" class="appendAsset"
											data-roletype="${pvMap.roletype}">添加素材</a>
											<div class="asset"></div>
										</div>
										<div class="bditem bdimg">
											<a href="javascript:void(0)" class="appendPhoto"
											data-roletype="${pvMap.roletype}">添加图片</a>
											<div class="photo"></div>
										</div>
										<div class="bditem bdaudio">
												<a href="javascript:void(0)" class="appendAudio"
												data-roletype="${pvMap.roletype}">添加语音</a>
												<div class="audio"></div>
										</div>
										<div class="bditem bdlink">
											<p>订阅者点击该子菜单会跳到以下链接</p>
											<input type="text" class="input" />
										</div>
									</div>
								</div>
								<div class="btns clearFix"><a href="javascript:void(0)" class="btnB btnSmall" id="saveBtn">保存</a></div>
							</div>
							<div class="saved" id="wcMenuSaved" style="display:none">
								<h1 class="title">文本回复</h1>
								<div class="content"></div>
								<div class="btns clearFix"><a href="javascript:void(0)" class="btnD btnSmall" id="editBtn">修改</a></div>
							</div>
						</div>
					</div>
				</div>

				<div class="btnCon btnCon2">
					<input type="submit" class="btnA" value="发布"  onclick="createWeiXinMenu();"/>
					<!--
					<a href="javascript:void(0)" class="btnD">预览</a>
					 -->
				</div>


			</div>
		</div>
		<!--main end-->

	</div>
	<!--container end-->
	</div></div>
	
	<!-- 自定义菜单 -->
		<script>
	var __menuItem = {
		s: 0,
		m:[
		<c:forEach items="${weixinmenuList }" var="parent">
	 			{
		 			menuid:"${parent.menuid}",
		 			parentid:"${parent.parentid}",
		 			menu:"${parent.menuname}",
					type:"${parent.msgtypekey}",
					content:"${parent.linkurl}",
					html:"",
					item:[
						<c:forEach items="${parent.childRen}" var="child">
							{
								menuid:"${child.menuid}",
					 			parentid:"${child.parentid}",
								title: "${child.menuname}",
								type: "${child.msgtypekey}",
								content: "${child.linkurl}",
								html:"",
							},
						</c:forEach>
					]
		 		},
		 	</c:forEach>
      ]
	};

	function loadMenu(callback){
		hhutil.ajax("<%=request.getContextPath() %>/ws/backer/system/findDTMenuJsonList",null,function(data){
			var menuList = data.data.menuList ;
			__menuItem = {s:0,m:[]} ;
			if(!hhutil.isEmpty(menuList)){
				for(var i=0,len=menuList.length;i<len;i++){
					var t1 = menuList[i] ;

					var item = [];
					if(t1.hasOwnProperty('childRen')){
						var childRen = t1.childRen;
						for(var j=0,len2=childRen.length;j<len2;j++){
							var t2 = childRen[j];
							item[j] = {
								menuid:t2.menuid,
								parentid:t2.parentid,
								title:t2.menuname,
								type:t2.msgtypekey,
								content:t2.linkurl,
								html:""
							};
						}
					}

					var menu = {
						menu:t1.menuname,
						type:t1.msgtypekey,
						content:t1.linkurl,
						item:item,
						menuid:t1.menuid,
						parentid:t1.parentid,
						html:"",
					};
					__menuItem.m[i] = menu;
				}
			}
			if(!hhutil.isEmpty(callback)){
				callback();
			}
			//==========
		});
	}
	</script>
    <script>
    //添加或保存菜单
	function saveMenu(obj,callback){
		var url = "<%=request.getContextPath() %>/ws/backer/system/saveDTMenu?1=1";
		if(!hhutil.isEmpty(obj.menuid)){
			url += "&menuid="+obj.menuid;
		}
		if(!hhutil.isEmpty(obj.parentid)){
			url += "&parentid="+obj.parentid;
		}
		if(!hhutil.isEmpty(obj.menuname)){
			url += "&menuname="+obj.menuname;
		}
        url = encodeURI(url);
		hhutil.ajax(url,null,function(data){
			loadMenu(callback);
		});
	}
	//删除菜单
	function deleteMenu(obj,callback){
		var url = "<%=request.getContextPath() %>/ws/backer/system/delDTMenu?menuid="+obj.menuid;
		hhutil.ajax(url,null,function(data){
			loadMenu(callback);
		});
	}
	//保存内容
	function saveMenuContent(obj,callback){
		var url = "<%=request.getContextPath() %>/ws/backer/system/saveDTMenu?1=1";
		if(!hhutil.isEmpty(obj.menuid)){
			url += "&menuid="+obj.menuid;
		}
		if(!hhutil.isEmpty(obj.msgtypename)){
			url += "&msgtypename="+obj.msgtypename;
		}
		if(!hhutil.isEmpty(obj.linkurl)){
			url += "&linkurl="+encodeURIComponent(obj.linkurl);
		}
        //url = encodeURI(url);
		hhutil.ajax(url,null,function(data){
			loadMenu(callback);
		});
	}
	function createWeiXinMenu(){
		//生成微信菜单
		location.href = "<%=request.getContextPath()%>/ws/backer/system/createWeixinMenu";
	}
    </script>

	<script src="<%=request.getContextPath()%>/theme/black/js/gtyutil.js"></script>
    <script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/common.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/wechat/menu.js"></script>

</body>
</html>