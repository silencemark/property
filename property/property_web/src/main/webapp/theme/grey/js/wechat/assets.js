$(function(){


	// 图文消息列表
	if ($("#wechatArticleAssetsList").length != 0){
		var list = {
			init: function(){
				list.dom = $("#wechatArticleAssetsList");
				$.waterfall.init(list.dom.find(".itemList"), "item", 3, 289, 15);
				list.dom.on("click", ".itemList .item .tools .del", list.onItemDel);
			},
			onItemDel: function(e){
				var dom = $(this).parents(".item");
				$.alert({
					title: "温馨提示",
					txt: "确定要删除此素材吗？",
					btnY: "删除",
					btnYcss: "btnC",
					btnN: "取消",
					callbackY: function(){
						$.loading();
						dom.fadeOut(200, function(){
							$.loading.remove();
							dom.remove();
							$.waterfall.init(list.dom.find(".itemList"), "item", 3, 289, 15);
						});
					}
				});
			}
		};
		list.init();
	}
	// 图片列表
	else if ($("#wechatPhotoAssetsList").length != 0){
		var list = {
			init: function(){
				list.dom = $("#wechatPhotoAssetsList");
				list.dom.on("click", ".itemList .item .tools .del", list.onItemDel);
				$("#flashupload").on("click", list.upload);
			},
			onItemDel: function(e){
				var dom = $(this).parents(".item");
				$.alert({
					title: "温馨提示",
					txt: "确定要删除此图片吗？",
					btnY: "删除",
					btnYcss: "btnC",
					btnN: "取消",
					callbackY: function(){
						$.loading();
						dom.fadeOut(200, function(){
							$.loading.remove();
							dom.remove();
						});
					}
				});
			},
			upload: function(){
				$.imgUploadPop({
					width: 500,
					height: -1,
					complete: list.uploadComplete
				});
			},
			uploadComplete: function(d){
				var html = "<div class='item'><div class='img'><img src='" + hhutil.getRootPath()+d + "' /></div>";
				html += "<div class='tools'><a href='javascript:void(0)' class='del'></a></div></div>";
				list.dom.find(".itemList").prepend(html);
			}
		};
		list.init();
	}

	// 单图文
	if ($("#wechatSingleAsset").length != 0){
		var asset = {
			init: function(){
				asset.item = $("#itemSingle");
				$("#title").on("input", asset.inputTitle);
				$("#summary").on("input", asset.inputSummary);
				$("#submit").on("click", asset.checkForm);
				asset.uditor = UE.getEditor("article",{
				    initialFrameWidth: 579,
				    toolbars: [['bold', 'italic', 'underline', '|', 'insertorderedlist', 'insertunorderedlist', '|', 'simpleupload', '|', 'removeformat', 'forecolor', 'backcolor', 'insertvideo']]
				});
			},
			inputTitle: function(){
				var val = $("#title").val();
				if ($.trim(val) == ""){
					val = "标题";
				}
				asset.item.find("h2 a").html(val);
			},
			inputSummary: function(){
				var val = $("#summary").val();
				asset.item.find("p").html(val);
			},
			checkForm: function(){
				var $title = $("#title");
				var $author = $("#author");
				var $summary = $("#summary");
				var $link = $("#link");

				var title = $.trim($title.val());
				var author = $.trim($author.val());
				var coverimg = $.trim($("#coverimg").val());
				var showInArticle = $("#showInArticle")[0].checked;
				var summary = $.trim($summary.val());
				var article = UE.getEditor("article").getContent();
				var link = $.trim($link.val());
				
				if ($title.inputEmpty() || $title.inputLengthOverflow(64)){
					$title.inputError("标题不能为空且长度不能超过64字");
				} else if ($author.inputLengthOverflow(8)){
					$author.inputError("作者不能超过8个字");
				} else if (coverimg == ""){
					$.tips.error("请上传封面图片");
				} else if ($summary.inputEmpty() || $summary.inputLengthOverflow(120)){
					$summary.inputError("摘要不能为空且长度不能超过120字");
				} else if ($.trim(article) == "" || $.realLength(article) > 20000){
					$.tips.error("正文不能为空且长度不能超过20000字");
				} else {
					$.ajaxSubmit({
						url: "url",
						data: {},
						success: function(){}
					});
				}
			}
		}
		asset.init();
		$.__singleArticleAssetImgChange__ = function(data){
			if (data){
				asset.item.find(".img").html("<img src='" + hhutil.getRootPath() + data + "' />");
			} else {
				asset.item.find(".img").html("封面图片");
			}
		}
	}
	// 多图文
	else if ($("#wechatMulitAsset").length != 0){
		var asset = {
			init: function(){
				asset.item = $("#itemMulit");
				asset.item.on("hover", ".cover, .sub", asset.toggleTools);
				asset.item.on("click", ".tools .edit", asset.editItem);
				asset.item.on("click", ".sub .tools .del", asset.deleteItem);
				$("#title").on("input", asset.inputTitle);
				asset.item.on("click", ".append a", asset.appendItem);
				asset.current = 0;
				asset.initItem();
				$("#article").html(__mulit.cover.article);
				asset.uditor = UE.getEditor("article",{
				    initialFrameWidth: 579,
				    toolbars: [['bold', 'italic', 'underline', '|', 'insertorderedlist', 'insertunorderedlist', '|', 'simpleupload', '|', 'removeformat', 'forecolor', 'backcolor', 'insertvideo']]
				});
				asset.restoreCurrent(false);
				
			},
			toggleTools: function(e){
				if (e.type == "mouseenter"){
					$(this).find(".tools").show();
				} else {
					$(this).find(".tools").hide();
				}
			},
			initItem: function(){
				if (window.__mulit && __mulit.cover && __mulit.sub){
					var html = "<div class='cover'><div class='img'>";
					if (__mulit.cover.coverimg){
						html += "<div class='p'><img src='" +hhutil.getRootPath()+ __mulit.cover.coverimg + "' /></div>";
					} else {
						html += "<div class='p'>封面图片</div>";
					}
					html += "<p><a href='javascript:void(0)'>" + __mulit.cover.title + "</a></p>";
					html += "<div class='tools'><a href='javascript:void(0)' class='edit' data-i='cover'></a></div></div></div>";
					for (var i = 0; i < __mulit.sub.length; i++){
						html += "<div class='sub clearFix'><p><a href='javascript:void(0)'>标题</a></p>";
						html += "<div class='img'>缩略图</div>";
						html += "<div class='tools'><a href='javascript:void(0)' class='edit'></a><a href='javascript:void(0)' class='del'></a></div></div>";
						html += "<div class='append'><a href='javascript:void(0)' class='btnB btnSmall'>添加</a></div>";
					}
					asset.item.html(html);
				} else {
					window.__mulit = {
						cover: { title: "", author: "", coverimg: "", show: true, article: "", link: ""},
						sub: [{ title: "", author: "", coverimg: "", show: true, article: "", link: "" }]
					}
					asset.initItem();
				}
			},
			appendItem: function(){
				var length = asset.item.find(".sub").length;
				if (length >= 7){
					$.tips.error("你最多只可以加入8条图文消息");
				} else {
					var html = "<div class='sub clearFix'><p><a href='javascript:void(0)'>标题</a></p><div class='img'>缩略图</div><div class='tools'><a href='javascript:void(0)' class='edit'></a><a href='javascript:void(0)' class='del'></a></div></div>";
					asset.item.find(".append").before(html);
					__mulit.sub.push({title: "", author: "", coverimg: "", show: false, article: "", link: ""});
				}
				if (length == 6){
					asset.item.find(".append").hide();
				}
			},
			editItem: function(){
				asset.saveCurrent();
				if ($(this).data("i") == "cover"){
					asset.current = 0;
				} else {
					asset.current = $(this).parents(".sub").index();
				}
				$(this).parent(".tools").hide();
				asset.restoreCurrent();
			},
			deleteItem: function(){
				var length = asset.item.find(".sub").length;
				if (length <= 1){
					$.tips.error("无法删除，多条图文至少需要2条消息");
				} else {
					var dom = $(this).parents(".sub");
					var index = dom.index();
					$("#wechatMulitAsset .form .uploadbtn").hide(); // swf bug,会遮挡弹出框的文字部分
					$.alert({
						title: "温馨提示",
						txt: "确定要删除此素材吗？",
						btnY: "删除",
						btnYcss: "btnC",
						btnN: "取消",
						callbackY: function(){
							$.loading();
							$("#wechatMulitAsset .form .uploadbtn").show();
							dom.fadeOut(200, function(){
								$.loading.remove();
								asset.saveCurrent();
								dom.remove();
								__mulit.sub.splice(index - 1, 1);
								asset.item.find(".append").show();
								if (index == asset.current){
									asset.current = 0;
									asset.restoreCurrent();
								} else if (index < asset.current) {
									asset.current = index;
									asset.restoreCurrent();
								}
							});
						},
						callbackN: function(){
							$("#wechatMulitAsset .form .uploadbtn").show();
						}
					});
				}
			},
			saveCurrent: function(){
				var title = $.trim($("#title").val());
				var author = $.trim($("#author").val());
				var coverimg = $.trim($("#coverimg").val());
				var showInArticle = $("#showInArticle")[0].checked;
				var article = UE.getEditor("article").getContent();
				var link = $.trim($("#link").val());
				if (asset.current == 0){
					__mulit.cover.title = title;
					__mulit.cover.author = author;
					__mulit.cover.coverimg = coverimg;
					__mulit.cover.show = showInArticle;
					__mulit.cover.article = article;
					__mulit.cover.link = link;
				} else {
					__mulit.sub[asset.current - 1].title = title;
					__mulit.sub[asset.current - 1].author = author;
					__mulit.sub[asset.current - 1].coverimg = coverimg;
					__mulit.sub[asset.current - 1].show = showInArticle;
					__mulit.sub[asset.current - 1].article = article;
					__mulit.sub[asset.current - 1].link = link;
				}
			},
			restoreCurrent: function(rtDitor){
				rtDitor = rtDitor == undefined ? true : false;
				var title, author, coverimg, showInArticle, article, link;
				if (asset.current == 0){
					title = __mulit.cover.title;
					author = __mulit.cover.author;
					coverimg = __mulit.cover.coverimg;
					showInArticle = __mulit.cover.show;
					article = __mulit.cover.article;
					link = __mulit.cover.link;
					$("#wechatMulitAsset .form .cover h6 .up2").hide().siblings(".up1").show();
				} else {
					title = __mulit.sub[asset.current - 1].title;
					author = __mulit.sub[asset.current - 1].author;
					coverimg = __mulit.sub[asset.current - 1].coverimg;
					showInArticle = __mulit.sub[asset.current - 1].show;
					article = __mulit.sub[asset.current - 1].article;
					link = __mulit.sub[asset.current - 1].link;
					$("#wechatMulitAsset .form .cover h6 .up1").hide().siblings(".up2").show();
				}
				$("#wechatMulitAsset .form").attr("class", "form form_t" + asset.current);
				$("#title").val(title);
				$("#author").val(author);
				$("#coverimg").imgUploadSetVal(coverimg);
				$("#showInArticle")[0].checked = showInArticle;
				var css = showInArticle ? "js_checkbox js_checkboxChecked" : "js_checkbox";
				$("#showInArticle").next(".js_checkbox").attr("class", css);
				if (rtDitor){
					asset.uditor.setContent(article, false);
				}
				$("#link").val(link);
			},
			inputTitle: function(){
				var dom;
				if (asset.current == 0){
					dom = asset.item.find(".cover p a");
				} else {
					dom = asset.item.find(".sub:eq(" + (asset.current - 1) + ") p a");
				}
				var val = $(this).val();
				if (val == ""){
					val = "标题";
				}
				dom.html(val);
			}
		}
		asset.init();
		$.__mulitArticleAssetImgChange__ = function(data){
			var dom, txt;
			if (asset.current == 0){
				dom = asset.item.find(".cover .img .p");
				txt = "封面图片";
			} else {
				dom = asset.item.find(".sub:eq(" + (asset.current - 1) + ") .img");
				txt = "缩略图";
			}
			if (data){
				dom.html("<img src='" + hhutil.getRootPath() + data + "' />");
			} else {
				dom.html(txt);
			}
		}
	}




	
	









});




