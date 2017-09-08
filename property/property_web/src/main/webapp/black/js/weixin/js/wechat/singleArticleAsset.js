$(function(){


	

	// 单图文
	var asset = {
		init: function(){
			asset.item = $("#itemSingle");
			$("#title").on("input", asset.inputTitle);
			$("#summary").on("input", asset.inputSummary);
			$("#submit").on("click", asset.checkForm);
			asset.uditor = UE.getEditor("article",{
			    initialFrameWidth: 561,
			    initialFrameHeight:400,
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
				//$title.inputError("标题不能为空且长度不能超过64字");
				$.alertYes({
					title: "温馨提示",
					txt: "标题不能为空且长度不能超过64字！",
					btnY: "确定"
				});
			} else if ($author.inputLengthOverflow(8)){
				//$author.inputError("作者不能超过8个字");
				$.alertYes({
					title: "温馨提示",
					txt: "作者不能超过8个字！",
					btnY: "确定"
				});
			} else if (coverimg == ""){
				//$.tips.error("请上传封面图片");
				$.alertYes({
					title: "温馨提示",
					txt: "请上传封面图片！",
					btnY: "确定"
				});
			} else if ($summary.inputEmpty() || $summary.inputLengthOverflow(120)){
				//$summary.inputError("摘要不能为空且长度不能超过120字");
				$.alertYes({
					title: "温馨提示",
					txt: "摘要不能为空且长度不能超过120字！",
					btnY: "确定"
				});
			} else if ($.trim(article) == ""){
				//$.tips.error("正文不能为空且长度不能超过20000字");
				$.alertYes({
					title: "温馨提示",
					txt: "正文不能为空",
					btnY: "确定"
				});
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
	}
	asset.init();
	$.__singleArticleAssetImgChange__ = function(data){
		if (data){
			asset.item.find(".img").html("<img src='" +hhutil.getRootPath() + data + "' />");
		} else {
			asset.item.find(".img").html("封面图片");
		}
	}




	
	









});




