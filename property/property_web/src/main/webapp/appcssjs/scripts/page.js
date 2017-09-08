function PageHelper(s){
	var $this = this;
	//document.write('<script type="text/javascript" src="/js/jquery-1.10.2.min.js"></script>');
	
	var id = new Date().getTime();
	var ajaxSettings = {
			/**
			 * 数据调取URL
			 */
			url:"",    
			/**
			 * 提交方式
			 */
			type:"post",
			/**
			 * 提交的数据
			 */
			data:{},
			/**
			 * 成功 调用的方法
			 */
			success:function(data){ 
				
			/**
			 * 失败 调用的方法
			 */
			},error:function(data){ },
			/**
			 * 初始页码          默认   2
			 */
			pageNo:2,
			/**
			 * 每次调取数据的大小 默认为10
			 */
			pageSize:10,
			/**
			 * 初始化时自动加载
			 * 默认 不加载
			 */
			isload:false,
			/**
			 * 下拉刷新 
			 */
			isslide:false,
			/**
			 * 访问前调用的方法
			 */
			before:function(){ 
				
				$("ul[class][id]").append('<li id="fresh_'+id+'" style="background:none; width:100%; margin:auto; height: 50px;"><div style="padding: 6px 0px;  position: absolute;  bottom: 0px; text-align: center;  left: 0; width: 100%;"><a href="#" style="background:url(/appcssjs/images/public/ico_fresh.png) no-repeat left center; padding-left:25px; width:110px;line-height:28px; color:#999;">数据加载中...</a></div></li>').trigger("create");
				
			/**
			 * 访问结束后调取的方法
			 */
			},after:function(){ 
				$("#fresh_"+id).remove();
				if("function" == $this.slide){
					$this.slide();
				}
			},
			 
			isBody:true,
			isok:true
	},
	s = jQuery.extend({}, ajaxSettings, s); 
	$this.s = s;
	 
	//scroll
	if(s.isBody){
		
		if(s.isslide){
			document.write('<script type="text/javascript" src="/js/jquery.slide.js"></script>');
			setTimeout(function(){
				slide();
			},1000)
		} 
		slide = function(){
			$("ul[class][id]")
			.css({"transform":"translate(0px,-60px)"})
			.prepend('<li style="height:50px; text-align:center;"> 下拉刷新...</li>')
			.slide(60, function (e) {
	            var that = this;  
	            setTimeout(function () {
	            	that.back.call();
	            	slide();
	            }, 2000);  
	            location.reload(true);
	            /*$this.s.isload = true;
	            $this.s.pageNo = 1;
	            $this.s.isok = true;
	           
	            $("ul[class][id]").html("").css({"transform":""}).bind("touchend",function(){});
	           */   
	            $this.ajax(); 
	        })
		}
		
		//动态加载
		$(window).scroll(function () {
			 
	        var scrollTop = $(this).scrollTop();
	        var scrollHeight = $(document).height();
	        var windowHeight = $(this).height();
	        if (scrollTop + windowHeight+100 >= scrollHeight) {  
	        	if(!$this.s.isok){
					return;
				}
				try{
					s.data.pageNo = s.pageNo;
					s.data.pageSize = s.pageSize;
					s.data.rowPage = s.pageSize;
					s.data.currentPage = s.pageNo;
				}catch(e){
					s.data = {};
					s.data.pageNo = s.pageNo;
					s.data.pageSize = s.pageSize;
					s.data.rowPage = s.pageSize;
					s.data.currentPage = s.pageNo;
				} 
				$this.s.isok=false;
				$.ajax({
					url:s.url,
					data:s.data,
					type:s.type,
					beforeSend:s.before,
					success:function(data){ 
						if(data.page.pageNo>1){
							s.success(data);
						}
						s.pageNo++ 
						if(data.page.totalPages >= s.pageNo){
							$this.s.isok = true;
						}  
						s.after();
						$this.imgerror();
					},error:function(XMLHttpRequest, textStatus, errorThrown){
						
						s.error(XMLHttpRequest);
					    $this.s.isok = true;
					    s.after();
						if(XMLHttpRequest.status == 518){
							// openid 失效，访问以下地址重新请求
							var fromurl = encodeURI(location.href) ;
							var locationhref = XMLHttpRequest.getResponseHeader("Location");	
							location.href = locationhref+"?sourceUrl="+fromurl ;
						}

					}
				});  
	        }
	    });
	} 
	
	$this.imgerror = function(){
		 setTimeout(function(){
			$('img').each(function(index,dom){
				var src=  $(dom).attr("src");
				var img=new Image();
			    img.src=src;
			    var width=img.width,height=img.height;//图片大小
				if(src == null || src == '' || src == '/'){
					$(dom).attr('src','/appcssjs/images/page/pic_bg.png');
				}else if(width == 0 && height == 0){
					$(dom).attr('src','/appcssjs/images/page/pic_bg.png');
				}
			});
			$("img").error(function(){
				$(this).attr('src','/appcssjs/images/page/pic_bg.png');
			});
		},300); 
	}
	$this.load = function(){
		if(!$this.s.isok){
			return;
		}
		try{
			s.data.pageNo = s.pageNo;
			s.data.pageSize = s.pageSize;
			s.data.rowPage = s.pageSize;
			s.data.currentPage = s.pageNo;
		}catch(e){
			s.data = {};
			s.data.pageNo = s.pageNo;
			s.data.pageSize = s.pageSize;
			s.data.rowPage = s.pageSize;
			s.data.currentPage = s.pageNo;
		} 
		$this.s.isok=false;
		$.ajax({
			url:s.url,
			data:s.data,
			type:s.type,
			beforeSend:s.before,
			success:function(data){ 
				if(data.page.pageNo>1 || s.isload){
					s.success(data);
				}
				s.isload = false;
				s.pageNo++ 
				if(data.page.totalPages >= s.pageNo){
					$this.s.isok = true;
				}  
				s.after();
				$this.imgerror();
			},error:function(XMLHttpRequest, textStatus, errorThrown){
				
				s.error(XMLHttpRequest);
			    $this.s.isok = true;
			    s.after();
				if(XMLHttpRequest.status == 518){
					// openid 失效，访问以下地址重新请求
					var fromurl = encodeURI(location.href) ;
					var locationhref = XMLHttpRequest.getResponseHeader("Location");	
					location.href = locationhref+"?sourceUrl="+fromurl ;
				}

			}
		})
	}
	$this.ajax = function(){
		if(!$this.s.isok){
			return;
		}
		try{
			s.data.pageNo = s.pageNo;
			s.data.pageSize = s.pageSize;
			s.data.rowPage = s.pageSize;
			s.data.currentPage = s.pageNo;
		}catch(e){
			s.data = {};
			s.data.pageNo = s.pageNo;
			s.data.pageSize = s.pageSize;
			s.data.rowPage = s.pageSize;
			s.data.currentPage = s.pageNo;
		} 
		$this.s.isok=false;
		$.ajax({
			url:s.url,
			data:s.data,
			type:s.type,
			beforeSend:s.before,
			success:function(data){ 
				if(data.page.pageNo>1 || s.isload){
					s.success(data);
				}
				s.pageNo++ 
				if(data.page.totalPages >= s.pageNo){
					$this.s.isok = true;
				}  
				s.after();
				$this.imgerror();
			},error:function(XMLHttpRequest, textStatus, errorThrown){
				
				s.error(XMLHttpRequest);
			    $this.s.isok = true;
			    s.after();
				if(XMLHttpRequest.status == 518){
					// openid 失效，访问以下地址重新请求
					var fromurl = encodeURI(location.href) ;
					var locationhref = XMLHttpRequest.getResponseHeader("Location");	
					location.href = locationhref+"?sourceUrl="+fromurl ;
				}

			}
		})
	}
	
	if(s.isload){
		this.ajax();
	}
	
	return $this;
}