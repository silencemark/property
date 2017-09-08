$(function(){

	// 单图文
	var asset = {
		init: function(){
			asset.item = $("#itemSingle");
			$("#title").on("input", asset.inputTitle);
			$("#summary").on("input", asset.inputSummary);
			$("#submit").on("click", asset.checkForm);
			asset.uditor = UE.getEditor("article",{
				initialFrameWidth: 563,
				toolbars: [['bold', 'italic', 'underline', '|', 'insertorderedlist', 'insertunorderedlist', '|', 'simpleupload', '|', 'removeformat', 'forecolor', 'backcolor', 'insertvideo']]
			});

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
						var preview= $("#pop-alert .pop .bd .preview input");

						var item = new Object();
						item["title"]=title;
						item["author"]=author;
						item["imgurl"]=coverimg;
						item["ifviewcontent"]=showInArticle?"1":"0";
						item["summary"]=summary;
						item["content"]=article;
						item["linkurl"]=link;
						item["openid"]=preview.val();

						//将单图文或多图文保存
						sendPreview(item);
						$("#pop-alert").remove();
					}
				});
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
				//$('#content').val(article);
				$('#ifviewcontent').val(showInArticle?"1":"0");
				document.getElementById('graphic').submit();
				/**$.ajaxSubmit({
					url: $('#graphic').attr("action"),
					data: {},
					success: function(){}
				});**/
			}
		}
	};
	
	asset.init();
	$.__singleArticleAssetImgChange__ = function(data){
		if (data){
			asset.item.find(".img").html("<img src='" +hhutil.getRootPath() + data + "' />");
		} else {
			asset.item.find(".img").html("封面图片");
		}
	};



});