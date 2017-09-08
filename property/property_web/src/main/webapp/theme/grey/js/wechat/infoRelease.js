$(function(){




    // 单图文
    var asset = {
        init: function(){
            asset.item = $("#itemSingle");
            $("#title").on("input", asset.inputTitle);
            $("#submit").on("click", asset.checkForm);
            asset.uditor = UE.getEditor("content",{
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
        checkForm: function(){
            var $title = $("#title");

            var title = $.trim($title.val());
            var summary = $.trim($("#summary").val());
            var coverimg = $.trim($("#imgurl").val());
            var content = UE.getEditor("content").getContent();

            if ($title.inputEmpty() || $title.inputLengthOverflow(64)){
                $title.inputError("标题不能为空且长度不能超过64字");
            } else if ($("#summary").inputEmpty() || $.validateStrLen($("#summary").val())/2>57){
                $.tips.error("摘要不能为空且长度不能超过57字");
            }else if (coverimg == ""){
                $.tips.error("请上传封面图片");
            }else if ($.trim(content) == "" || $.realLength(content) > 20000){
                $.tips.error("正文不能为空且长度不能超过20000字");
            } else {
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




