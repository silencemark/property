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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页</title>
<meta name="Keywords" content="">
<meta name="Description" content="">
<jsp:include page="../../../../black/include/header.jsp" flush="true"></jsp:include>
<link href="<%=request.getContextPath() %>/theme/grey/style/reset.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/theme/grey/style/base.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/theme/grey/style/page.css" rel="stylesheet" type="text/css" />

</head>

<body>

	<!--container-->
	<div class="webContainer clearFix containerContentReview" id="webContainer" style="width:1040px;background: url('<%=request.getContextPath() %>/theme/grey/images/ui/lineB.png') 39px 0 repeat-y #fff;">


		<!--main-->
		<div class="webMain">
			
			<div class="mainBody">
				
				
				<c:choose><c:when test="${null == pvMap and '' == pvMap}">
					
					<h1>您无权访问！</h1>
					
				</c:when>
				<c:otherwise>
				
					<h1>${pvMap.title}</h1>
					<h2><fmt:formatDate value="${pvMap.createtime }" pattern="yyyy-MM-dd"/><span>${pvMap.author}</span><span>滴答叫人</span></h2>
	
					<div class="article">
						<!-- 封面图片展示  -->
						<c:if test="${null != pvMap.ifviewcontent and 1 == pvMap.ifviewcontent}">
							<img style="width:1024px;height:300px;" src="<%=request.getContextPath() %>${pvMap.imgurl}">	
						</c:if>
						
						<%--
						<P style="TEXT-INDENT: 2em"><!--keyword--><span class="infoMblog"><A target=_blank href="http://t.qq.com/qqsports#pref=qqcom.keyword" class="a-tips-Article-QQ" rel="qqsports" reltitle="腾讯体育"><!--/keyword-->腾讯体育<!--keyword--></A></span><!--/keyword-->讯 北京时间1月4日下午3时50分，中国奥委会名誉主席、国际奥委会委员何振梁先生今天下午在协和医院因病去世，享年85岁。何振梁是中国奥林匹克运动的巨大贡献者，曾两次参加北京申办奥运会，是北京申奥由失败到成功的标志性人物。<STRONG></STRONG></P><P style="TEXT-INDENT: 2em">出生于1929年的何振梁先生，是新中国著名的体育外交家，作为新中国体育先行者，他见证了新中国体育历史的变迁。在北京申办夏季奥运会的历程上，何振梁先生两次参与，担任申奥投票中方陈述人，他是北京申奥由失败到成功的标志性人物。何振梁也是新中国体育走向世界的见证人，他曾被外国体育刊物评为全世界最有影响的十大体育领导人之一。</P><P style="TEXT-INDENT: 2em">前中体产业竞赛集团副总裁、现北京奥林匹克俱乐部总经理王奇<!--keyword-->(<span class="infoMblog"><A target=_blank href="http://t.qq.com/wangqicsstar#pref=qqcom.keyword" class="a-tips-Article-QQ" rel="wangqicsstar" reltitle="王奇">微博</A></span>)<!--/keyword-->怀着悲痛地心情在朋友圈敲下了上述这番话“今天下午3点50分，何老去世了，悲痛欲绝。从1990年亚运会前夕跟随何振梁，25年了，何老将奥林匹克理想传递给我们，又和许多热爱奥林匹克的人民把奥运会带到中国。何老今天走了，很是伤心”。</P><P style="TEXT-INDENT: 2em">北京奥运会的成功举办是何振梁先生最大的荣誉，但这并没有让何振梁停止继续传递<!--keyword--><a class="a-tips-Article-QQ" href="http://2012.qq.com/" target="_blank"><!--/keyword-->奥运<!--keyword--></a><!--/keyword-->的脚步，年过八旬的他不顾身体抱恙仍积极投身于南京青奥会的申办工作中去，身为江苏人的他协助家乡申办成功。何老当时为南京青奥会申办团手写的文稿目前已经被收藏在南京奥林匹克博物馆里，那份文稿上面有蓝、黑两色的修改笔迹。</P><P style="TEXT-INDENT: 2em">两个月前何老已经卧床，但他仍关注着北京申冬奥的情况。王奇透露说，何老坚信北京一定会申办成功的，这也是何老的一个理想，希望北京在举办过夏季奥运会后还能在举办一届冬奥会。</P><P style="TEXT-INDENT: 2em">冬奥申委发言人表示，对于何老的去世北京冬奥申委深表哀悼。他生前对国际奥林匹克事业做出重要贡献，为推动奥林匹克运动在中国发展发挥重要作用，也是成功申办和举办2008年奥运会的功勋。成功申办2022年冬奥会也是他的愿望，我们将全力以赴、扎实工作，用实际行动完成申办使命。（腾讯体育记者肖苑玫<!--keyword-->(<span class="infoMblog"><A target=_blank href="http://t.qq.com/sharronxiao#pref=qqcom.keyword" class="a-tips-Article-QQ" rel="sharronxiao" reltitle="肖苑玫">微博</A></span>)<!--/keyword-->整理）</P><P style="TEXT-INDENT: 2em"><STRONG>延伸阅读：</STRONG></P><P style="TEXT-INDENT: 2em"><STRONG>刘翔<!--keyword-->( <span class="infoMblog"><A target=_blank href="http://t.qq.com/liuxiang#pref=qqcom.keyword" class="a-tips-Article-QQ" rel="liuxiang" reltitle="刘翔">微博</A></span> <span class="infoMblog"><A target=_blank href="http://liuxiang.qq.com/" class="a-tips-Article-QQ">官网</A></span> <A target=_blank href="http://liuxiang.qzone.qq.com/" class="a-tips-Article-QQ">博客</A>)<!--/keyword-->：难忘何老为我颁奖的一刻</STRONG></P><P style="TEXT-INDENT: 2em">得知何振梁去世的消息，刘翔感到非常悲伤。何振梁和他不但是忘年交，而且是2004年奥运会为刘翔颁奖的人。而刘翔还记得那次颁奖，自己没有流泪，但何老却激动地流下了泪水。</P><P style="TEXT-INDENT: 2em">“非常伤心，何老师为中国体育事业做出了很大的功勋。”刘翔说。而在2008年北京奥运会，何振梁多次表示还想为刘翔颁奖，但遗憾在预赛刘翔就退出了比赛。</P><P style="TEXT-INDENT: 2em">“2004年，那次是我主动要求去比赛现场为他颁奖的。”何振梁坦言，从那时起就期待着在鸟巢重温雅典的一幕，而在电视机前观看的刘翔的预赛，当说到刘翔撕下号码，一瘸一拐地走出赛场时，何老捂着胸口，神情痛苦。“刘翔只有25岁，承受的压力是常人不能想象的，他与我和孙海平<!--keyword-->(<span class="infoMblog"><A target=_blank href="http://t.qq.com/sunhaiping0113#pref=qqcom.keyword" class="a-tips-Article-QQ" rel="sunhaiping0113" reltitle="孙海平">微博</A></span>)<!--/keyword-->教练的心情是一样的。”</P><P style="TEXT-INDENT: 2em"><STRONG>专访杨澜：何老曾因北京申奥失败痛哭</STRONG></P><P style="TEXT-INDENT: 2em">“对我们来说，我们只是阶段性参与奥林匹克的工作，但是他把自己的一生都献给了奥林匹克运动。”曾两次见证、参与了北京申奥的著名主持人杨澜在接受腾讯体育采访时如是说。</P><P style="TEXT-INDENT: 2em">1993年，杨澜因主持了国际奥委会来京考察的一次活动，语言能力得到了国际奥委会的高度认可。活动结束后，时任中国奥委会主席的何振梁找到杨澜说，希望她能随北京申办团前往蒙特卡洛，因为申办成功后将在那里举办一个大型的派对，邀请她作为活动的主持人并希望她做好准备，把活动主持好。</P><P style="TEXT-INDENT: 2em">“那天晚上可以说是让很多国民有挫败感的一晚吧，很多人都以为北京会赢，最终2票之差落败于悉尼。我记得在回国的飞机上，何老非常憔悴。在后来接受我采访的时候他认为这么多人付出了这么多努力，最后仅仅是2票之差失之交臂，他又自责又难过，那天晚上他把自己锁在洗手间里痛哭了一场，所以在回国飞机上他非常憔悴。他坚持从头等舱走到后面经济舱来（代表团的专机），跟代表团每一个工作人员握手，跟大家道歉，这给我留下非常深刻的印象。”</P><P style="TEXT-INDENT: 2em"><STRONG>何振梁简介：</STRONG> <P style="TEXT-INDENT: 2em">1929年12月出生于江苏无锡，祖籍浙江上虞。</P><P style="TEXT-INDENT: 2em">1938年，何振梁随全家搬到了上海的法租界，转读教会学校。</P><P style="TEXT-INDENT: 2em">1939年至1946年在上海中法学堂（解放后改名上海市光明中学）学习。</P><P style="TEXT-INDENT: 2em">1950年毕业于上海震旦大学电机系。同年到团中央对外联络部工作。</P><P style="TEXT-INDENT: 2em">1952年作为中国体育代表团成员参加在芬兰举行的第十五届奥林匹克运动会。</P><P style="TEXT-INDENT: 2em">1953年年底，何振梁与当时同在团中央工作的梁丽娟结婚。</P><P style="TEXT-INDENT: 2em">1954年加入中国共产党。</P><P style="TEXT-INDENT: 2em">1955年起从事体育工作，到国家体委搞国际联络工作。</P><P style="TEXT-INDENT: 2em">1964年 起历任中国体操协会副秘书长、中国乒乓球协会秘书长、中华全国体育总会秘书处主任、中华全国体育总会副秘书长、中国奥林匹克委员会副秘书长、国家体委司长、中国奥委会执委、中华全国体育总会常委等职。</P><P style="TEXT-INDENT: 2em">1980年获国家体育运动荣誉奖章；他作为中国体育代表团副团长参加在美国普拉希德湖举行的第十三届冬季奥林匹克运动会。</P><P style="TEXT-INDENT: 2em">1981年10月2日，在国际奥林匹克委员会第八十四届大会上当选为国际奥林匹克委员会委员。在国际体育组织中，他还曾担任各国体育总会国际大会主席团副主席、国际奥林匹克委员会副主席和国际奥委会奥林匹克团结委员会委员等职务。何振梁为发展国际体育交往，恢复中国在国际奥委会的合法席位做了大量工作。</P><P style="TEXT-INDENT: 2em">1985年起任国家体委副主任，党组副书记，同年当选国际奥委会执委。</P><P style="TEXT-INDENT: 2em">1988年获亚洲举重联合会授予的卓越贡献金质奖；</P><P style="TEXT-INDENT: 2em">1989年当选中国奥委会主席，并当选国际奥委会副主席，曾任国际奥委会大众体育委员全副主席。后任国际奥委会文化委员会主席，曾任奥林匹克运动委员会和百周年大会成果研究委员会委员。是第七届全国政协委员、第八届全国政协常委(体育界)、全国政协科教文卫体委员会副主任。</P><P style="TEXT-INDENT: 2em">1992年被西班牙<a class="a-tips-Article-QQ" href="http://sports.qq.com/d/f_players/3/2943" target="_blank">卡洛斯</a><!--keyword--><a class="a-tips-Article-QQ" href="http://nbadata.sports.qq.com/team/23/teaminfo.html" target="_blank"><!--/keyword-->国王<!--keyword--></a><!--/keyword-->授予大十字勋章；</P><P style="TEXT-INDENT: 2em">1993年被<a class="a-tips-Article-QQ" href="http://sports.qq.com/d/f_teams/1/32" target="_blank">摩纳哥</a>国家元首雷尼埃大公授予圣查理十字勋章，同年荣获亚洲奥林匹克理事会功勋章。</P><P style="TEXT-INDENT: 2em">1993年北京申奥团陈述人之一，参加申办2000年夏季奥运会。</P><P style="TEXT-INDENT: 2em">1997年组织和主持的“世界体育文化论坛”在瑞士举行，被萨马兰奇称为“让文化委员会获得了青春”。</P><P style="TEXT-INDENT: 2em">2001年北京申奥团陈述人之一，参加申办2008年夏季奥运会。申办成功后，他逐渐退出了大众的视野。</P><P style="TEXT-INDENT: 2em">2008年5月20日，“何振梁奥林匹克陈列馆”在他的出生地无锡开馆，这是国内首座将体育名人和奥林匹克文化精神相结合的主题馆。</P><P style="TEXT-INDENT: 2em">2008年7月，出版了《何振梁申奥日记》。</P><P style="TEXT-INDENT: 2em">2008年8月6日担任北京奥运会北京站第432名火炬手。</P><P style="TEXT-INDENT: 2em">现任中国奥委会名誉主席，国际奥委会委员，国际奥委会文化和奥林匹克教育委员会主席，第29届奥林匹克运动会组织委员会顾问、执委。</P>
	 --%>	
	 					<P style="TEXT-INDENT: 2em">
	 						${pvMap.content}
	 					</P>
	 
					</div>
				
				</c:otherwise></c:choose>

			</div>
		</div>
		<!--main end-->

	</div>
	<!--container end-->

</div>

<script src="<%=request.getContextPath()%>/theme/black/js/gtyutil.js"></script>
	<script src="<%=request.getContextPath()%>/theme/black/js/hhutil.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/swfobject.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/theme/grey/js/common.js"></script>




</body>
</html>