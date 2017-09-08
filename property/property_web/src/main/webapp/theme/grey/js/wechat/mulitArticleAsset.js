$(function(){


	
	// 多图文
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
			$("#content").html(__mulit.cover.content);
			asset.uditor = UE.getEditor("content",{
			    initialFrameWidth: 579,
			    toolbars: [['bold', 'italic', 'underline', '|', 'insertorderedlist', 'insertunorderedlist', '|', 'simpleupload', '|', 'removeformat', 'forecolor', 'backcolor', 'insertvideo']]
			});
			asset.restoreCurrent(false);
			$("#submit").on("click", asset.onSubmit);
			
			//点击预览
			$('#preview').click(function(){
				var html = "<div class='i preview'><h6><center>请输入微信昵称，此图文消息将发送至该微信昵称预览。</center></h6><input type='text' style='display:block;' class='input' value=''/></div>";
					$.alert({
						title: "发送预览",
						txt: html,
						btnY: "确定",
						btnN: "取消",
						css: "pop-alert-appendWechatKeyword",
						init: function(){
							
						},
						callbackY: function(){
							asset.saveCurrent();  
							asset.initItem();   
							console.log(window.__mulit);
							//将单图文或多图文保存
							sendPreview($("#pop-alert .pop .bd .preview input").val());
							$("#pop-alert").remove();
						}
					});
			});
			//=====================================
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
				if (__mulit.cover.imgurl){
					html += "<div class='p'><img src='" + hhutil.getRootPath() + __mulit.cover.imgurl + "' /></div>";
				} else {
					html += "<div class='p'>封面图片</div>";
				}
				html += "<p><a href='javascript:void(0)'>" + __mulit.cover.title + "</a></p>";
				html += "<div class='tools'><a href='javascript:void(0)' class='edit' data-i='cover'></a></div></div></div>";
				for (var i = 0; i < __mulit.sub.length; i++){
					var title = __mulit.sub[i].title || "标题";
					var img = __mulit.sub[i].imgurl ? "<img src='" + hhutil.getRootPath() + __mulit.sub[i].imgurl + "' />" : "缩略图"
					html += "<div class='sub clearFix'><p><a href='javascript:void(0)'>" + title + "</a></p>";
					html += "<div class='img'>" + img + "</div>";
					html += "<div class='tools'><a href='javascript:void(0)' class='edit'></a><a href='javascript:void(0)' class='del'></a></div></div>";
				}
				html += "<div class='append'><a href='javascript:void(0)' class='btnB btnSmall'>添加</a></div>";
				asset.item.html(html);
			} else {
				window.__mulit = {
						cover: { title: "", author: "", imgurl: "", ifviewcontent: "1", content: "", linkurl: "", imgtextid:"", imgtextlistid:""},
						sub: [{ title: "", author: "", imgurl: "", ifviewcontent: "1", content: "", linkurl: "", imgtextid:"", imgtextlistid:"" }]
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
				__mulit.sub.push({title: "", author: "", imgurl: "", ifviewcontent: "1", content: "", linkurl: "", imgtextid:"", imgtextlistid:"" });
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
			var imgurl = $.trim($("#imgurl").val());
			var ifviewcontent = $("#ifviewcontent")[0].checked;
			var content = UE.getEditor("content").getContent();
			var linkurl = $.trim($("#linkurl").val());
			var imgtextid = $.trim($("#imgtextid").val());
			var imgtextlistid = $.trim($("#imgtextlistid").val());
			//var summary = $.trim($("#summary").val());
			if (asset.current == 0){
				__mulit.cover.title = title;
				__mulit.cover.author = author;
				__mulit.cover.imgurl = imgurl;
				__mulit.cover.ifviewcontent = ifviewcontent ? "1" : "0";
				__mulit.cover.content = content;
				__mulit.cover.linkurl = linkurl;
				__mulit.cover.imgtextid = imgtextid;
				__mulit.cover.imgtextlistid = imgtextlistid;
				//__mulit.cover.summary = summary;
			} else {
				__mulit.sub[asset.current - 1].title = title;
				__mulit.sub[asset.current - 1].author = author;
				__mulit.sub[asset.current - 1].imgurl = imgurl;
				__mulit.sub[asset.current - 1].ifviewcontent = ifviewcontent ? "1" : "0";
				__mulit.sub[asset.current - 1].content = content;
				__mulit.sub[asset.current - 1].linkurl = linkurl;
				__mulit.sub[asset.current - 1].imgtextid = imgtextid;
				__mulit.sub[asset.current - 1].imgtextlistid = imgtextlistid;
				//__mulit.sub[asset.current - 1].summary = summary;
			}
		},
		restoreCurrent: function(rtDitor){
			rtDitor = rtDitor == undefined ? true : false;
			var title, author, imgurl, ifviewcontent, content, linkurl,imgtextid,imgtextlistid;
			if (asset.current == 0){
				title = __mulit.cover.title;
				author = __mulit.cover.author;
				imgurl = __mulit.cover.imgurl; 
				ifviewcontent = __mulit.cover.ifviewcontent;
				//summary = __mulit.cover.summary;
				content = __mulit.cover.content;
				linkurl = __mulit.cover.linkurl;
				imgtextid = __mulit.cover.imgtextid;
				imgtextlistid = __mulit.cover.imgtextlistid;
				$("#wechatMulitAsset .form .cover h6 .up2").hide().siblings(".up1").show();
				$("#coverimg").attr("maxWidth", "600").data("init", "").next(".js_swfimgupload").remove();
				$("#coverimg").imgUploadInit();
			} else {
				title = __mulit.sub[asset.current - 1].title;
				author = __mulit.sub[asset.current - 1].author;
				imgurl = __mulit.sub[asset.current - 1].imgurl;
				ifviewcontent = __mulit.sub[asset.current - 1].ifviewcontent;
				//summary = __mulit.sub[asset.current - 1].summary;
				content = __mulit.sub[asset.current - 1].content;
				linkurl = __mulit.sub[asset.current - 1].linkurl;
				imgtextid = __mulit.sub[asset.current - 1].imgtextid;
				imgtextlistid = __mulit.sub[asset.current - 1].imgtextlistid;
				$("#wechatMulitAsset .form .cover h6 .up1").hide().siblings(".up2").show();
				$("#coverimg").attr("maxWidth", "200").data("init", "").next(".js_swfimgupload").remove();
				$("#coverimg").imgUploadInit();
			}
			ifviewcontent = ifviewcontent==1 ? true:false;
			$("#wechatMulitAsset .form").attr("class", "form form_t" + asset.current);
			$("#title").val(title);
			$("#author").val(author);
			//$("#summary").val(summary);
			$("#imgurl").imgUploadSetVal(imgurl);
			$("#ifviewcontent")[0].checked =ifviewcontent;
			var css = ifviewcontent ? "js_checkbox js_checkboxChecked" : "js_checkbox";
			$("#ifviewcontent").next(".js_checkbox").attr("class", css);
			if (rtDitor){
				asset.uditor.setContent(content, false);
			}
			$("#linkurl").val(linkurl);
			$("#imgtextid").val(imgtextid);
			$("#imgtextlistid").val(imgtextlistid); 
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
		},
		showError: function(checkid){
			asset.current = checkid;
			asset.restoreCurrent();
			return false;
		},
		onSubmit: function(){
			asset.saveCurrent();

			// 检查所有内容是否填写
			var checkid = 0, title, imgurl, content;
			for (var i = 0; i < __mulit.sub.length + 1; i++){
				checkid = i;
				if (i != 0){
					title = __mulit.sub[i - 1].title;
					imgurl = __mulit.sub[i - 1].imgurl;
					content = __mulit.sub[i - 1].content;
				} else {
					title = __mulit.cover.title;
					imgurl = __mulit.cover.imgurl;
					content = __mulit.cover.content;
				}
				// 验证
				if (title == ""){
					$("#title").inputError("标题不能为空");
					return asset.showError(checkid);
				} else if ($.realLength(title) > 64){
					$("#title").inputError("标题不能超过32个中文字或64个英文字");
					return asset.showError(checkid);
				} else if (imgurl == ""){
					$.tips.error("请上传封面图片");
					return asset.showError(checkid);
				} else if (content == ""){
					$.tips.error("正文不能为空");
					return asset.showError(checkid);
				} else if ($.realLength(content) > 20000){
					$.tips.error("正文不能超过20000字");
					return asset.showError(checkid);
				}
			}

			// 以下写提交内容服务器部分代码
			//alert("mulitArticleAsset.js / 228行 / 这里已经验证内容是否全部填写，该行以下加提交方法");
			//console.log(__mulit);
			saveDTImgTextMulti();
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



	
	









});