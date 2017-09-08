/**
var imgUploadServer = "http://192.168.0.16/php/img.php";
var imgUploadFile = "http://192.168.0.16/php/";
var mp3UploadServer = "http://192.168.0.16/php/mp3.php";
var mp3UploadFile = "http://192.168.0.16/php/uploads/";
**/
//图片上传地址
var imgUploadServer = hhutil.getRootPath()+"/backer/upload/img/uploadImgFlash";
var imgUploadThumbServer = hhutil.getRootPath()+"/backer/upload/img/uploadImgThumbFlash";
var imgUploadFile = hhutil.getRootPath();

var mp3UploadFile = hhutil.getRootPath();

;(function($) {


	// 瀑布流算法
	$.waterfall = {
		maxArr:function(arr){
	        var len = arr.length,temp = arr[0];
	        for(var ii= 1; ii < len; ii++){
	            if(temp < arr[ii]){
	                temp = arr[ii];
	            }
	        }
	        return temp;
	    },
	    getMar:function(node){
	        var dis = 0;
	        if(node.currentStyle){
	            dis = parseInt(node.currentStyle.marginBottom);
	        }else if(document.defaultView){
	            dis = parseInt(document.defaultView.getComputedStyle(node,null).marginBottom);
	        }
	        return dis;
	    },
		getMinCol:function(arr){
			var ca = arr,cl = ca.length,temp = ca[0],minc = 0;
			for(var ci = 0; ci < cl; ci++){
				if(temp > ca[ci]){
					temp = ca[ci];
					minc = ci;
				}
			}
			return minc;
		},
		init: function(elem, subCss, count, width, margin){
			margin = margin ? margin : 0;
	        var _this = elem;
	        var col = [], iArr = [];
	        var nodes = elem.find("." + subCss), len = nodes.length;
	        for(var i = 0; i < count; i++){
	            col[i] = 0;
	        }
	        for(var i = 0; i < len; i++){
	            nodes[i].h = nodes[i].offsetHeight + $.waterfall.getMar(nodes[i]);
	            iArr[i] = i;
	        }
			for(var i = 0; i < len; i++){
				var ming = $.waterfall.getMinCol(col);
				nodes[i].style.left = (width + margin) * ming + margin + "px";
				nodes[i].style.top = col[ming] + margin + "px";
				$(nodes[i]).attr("count", ming);
				col[ming] += nodes[i].h + margin;
			}
			elem.height($.waterfall.maxArr(col) + margin);
	    }
    }

	$.fromInit = function(){
		$(".form form, .table, .tableTools").each(function(){

			var _this = $(this);

			// 样式化表单内的下拉框
			//_this.find("select").selectInit();

			// 样式化表单内的单选框
			//_this.find("input").inputInit();

			// 样式化表单内的日期框
			_this.find("input.js_date").dateInputInit();

			// 样式化图片上传框
			_this.find("input.js_imgupload").imgUploadInit();

			// 样式化图片组上传
			_this.find("input.js_imgupload_arr").imgArrUploadInit();

			// 初始化富文本编辑器
			_this.find(".js_ueditor").each(function(){
				if ($(this).data("init") != "true" && UE){
					var id = $(this).attr("id");
					UE.getEditor(id);
				}
			});

		});
	};


	// 弹出框
	var alertCB = {};
	$("body").on("click", "#pop-alert>.pop>.btn>a", function(){
		if (alertCB.cy != false){
			$("#pop-alert").removeClass("show");
		}
		var _t = $(this).data("t")
		if (_t == "n" && alertCB.n && typeof alertCB.n == "function"){
			var r = alertCB.n();
			if (r == false){
				return false;
			}
			$("#pop-alert").remove();
		} else if (_t == "y" && alertCB.y && typeof alertCB.y == "function"){
			var r = alertCB.y();
			if (r == false){
				return false;
			}
			if (alertCB.cy != false){
				$("#pop-alert").remove();
			}
		} else {
			$("#pop-alert").remove();
		}
	});
	$.alert = function(o){
		if (typeof o == "string"){
			var txt = o;
			o = {};
			o.title = "提示";
			o.txt = txt;
			o.btnY = "确定";
			o.css = "";
			o.btnYcss = "";
		} else {
			o.title = o.title ? o.title : "提示";
			o.txt = o.txt ? o.txt : "提示内容为空";
			o.btnY = o.btnY ? o.btnY : "确定";
			o.css = o.css ? o.css : "";
			o.btnYcss = o.btnYcss ? o.btnYcss : "";
			alertCB.n = o.callbackN;
			alertCB.y = o.callbackY;
			alertCB.cy = o.btnYClose;
		}

		$("#pop-alert").remove();
		var _html = "<div id='pop-alert' class='" + o.css + "'><div class='pop'>";
		_html += "<div class='hd'>" + o.title + "</div>";
		_html += "<div class='bd'><p class='err'></p><div class='p'>" + o.txt + "</div></div>";
		if (o.btnN){
			_html += "<div class='btn btnNY'>";
			_html += "<a href='javascript:void(0)' class='btnD' data-t='n'>" + o.btnN + "</a>";
		} else {
			_html += "<div class='btn'>";
		}
		_html += "<a href='javascript:void(0)' class='btnA " + o.btnYcss + "' data-t='y'>" + o.btnY + "</a>";
		_html += "</div>";
		_html += "</div></div>";

		$("body").append(_html);
		if (o.init){
			o.init();
		}
	};
	
	$.alertYes = function(o){
		if (typeof o == "string"){
			var txt = o;
			o = {};
			o.title = "提示";
			o.txt = txt;
			o.btnY = "确定";
			o.css = "";
			o.btnYcss = "";
		} else {
			o.title = o.title ? o.title : "提示";
			o.txt = o.txt ? o.txt : "提示内容为空";
			o.btnY = o.btnY ? o.btnY : "确定";
			o.css = o.css ? o.css : "";
			o.btnYcss = o.btnYcss ? o.btnYcss : "";
			alertCB.n = o.callbackN;
			alertCB.y = o.callbackY;
			alertCB.cy = o.btnYClose;
		}

		$("#pop-alert").remove();
		var _html = "<div id='pop-alert' class='" + o.css + "'><div class='pop'>";
		_html += "<div class='hd'>" + o.title + "</div>";
		_html += "<div class='bd'><p class='err'></p><div class='p'>" + o.txt + "</div></div>";
		_html += "<div class='btn'>";
		_html += "<a href='javascript:void(0)' style='margin-left:180px;' class='btnA " + o.btnYcss + "' data-t='y'>" + o.btnY + "</a>";
		_html += "</div>";
		_html += "</div></div>";

		$("body").append(_html);
		if (o.init){
			o.init();
		}
	};
	$.alert.error = function(t){
		$("#pop-alert .pop .bd .err").show().html(t);
	}
	$.alert.removeError = function(){
		$("#pop-alert .pop .bd .err").hide().empty();
	}

	// 加载中
	$.loading = function(){
		$("#_loading_").remove();
		$("body").append("<div id='_loading_'></div>");
	}
	$.loading.remove = function(){
		$("#_loading_").remove();
	}

	// 去空格 还有个功能就是把<>这些标签换成&lt;和&gt;
	$.trim = function(e) {
		if(!hhutil.isEmpty(e)){
			return e.replace(/(^\s*)|(\s*$)/g,"").replace(/</g,"&lt;").replace(/>/g,"&gt;");
		}
		return "";
	}

	// 微信内容输入框
	$("input.js_wcInput").each(function(){
		var _this = $(this);
		if (_this.data("init") != "true"){
			var html = "<div class='wcInput'><div class='hd'>";
			html += "<a href='javascript:void(0)' class='icon txt'>文字</a>";
			html += "<a href='javascript:void(0)' class='icon img'>图片</a>";
			html += "</div><div class='bd'>";
			html += "<div class='txtCon' contenteditable='true'></div>";

			html += "</div><div class='ft clearFix'><span>还可以输入600字</span><a href='javascript:void(0)' class='emo'></a>";
			html += "<div class='emoList clearFix' style='display:none;'><ul class='clearFix'>";
			for (var i = 0; i < 104; i++){
				html += "<li data-i='" + i + "'><span style='background-position:" + (-24 * i) + "px 0;'></span></li>";
			}
			html += "</ul></div></div></div>";
			_this.data("init", "true").after(html);

			var after = _this.next(".wcInput");
			after.find(".ft .emo").on("click", wcInputEmoShow);
			after.on("click", ".ft .emoList li", wcInputEmoClick);
		}
	});
	function wcInputEmoShow(){
		var _this = $(this);
		if (!_this.hasClass("emoOpend")){
			_this.addClass("emoOpend").next(".emoList").show();
		} else {
			_this.removeClass("emoOpend").next(".emoList").hide();
		}
	}
	function wcInputEmoClick(){
		var _this = $(this), _parent = _this.parents(".wcInput"), _i = _this.data("i");
		_insertimg(_parent.find(".bd .txtCon"), "<img src='"+hhutil.getRootPath()+"/theme/grey/images/ui/emotion/" + _i + ".gif' />");
	}
})(jQuery);

// input验证部分
(function($){
	var errorTimeout;
	$.fn.inputError = function(t) {
		if ($(this).length) {
			if ($("#pop-alert").length == 0) {
				$.tips.error(t);
			} else {
				$.alert.error(t);
			}
			$(this).focus().addClass("inputError");
			clearTimeout(errorTimeout);
			errorTimeout = setTimeout(function(){
				$(".inputError").removeClass("inputError");
			}, 2000);
		}
		return false;
	}
	$.fn.inputEmpty = function() {
		if ($(this).length && $.trim($(this).val()) == "") {
			return true;
		}
	}
	$.fn.inputLengthOverflow = function(l) {
		if ($(this).length) {
		    return $(this).inputLength() > l;
	    }
	}
	$.fn.inputLength = function() {
		return $.realLength($(this).val());
	}
	$.realLength = function(val){
		if (val && val.length) {
		    var realLength = 0, len = val.length, charCode = -1;
		    for (var i = 0; i < len; i++) {
		        charCode = val.charCodeAt(i);
		        if (charCode >= 0 && charCode <= 128) realLength += 1;
		        else realLength += 2;
		    }
		    return realLength;
		}
		return 0;
	}
})(jQuery);

// ajax提交
(function($){
	$.ajaxSubmit = function(o){
		$.loading();
		$.ajax({
            type: "GET",
            url: o.url,
            data: o.data,
            dataType: "json",
            success: function(data){
            	if (o.success){
            		o.success(data);
            	}
				$.loading.remove();
			},
			error: function(data){
				if (o.error){
            		o.error(data);
            	}
            	$.loading.remove();
			}
		});
	}
})(jQuery);

// 错误提示
(function($){
	$.tips = {
		error: function(t){
			clearTimeout($.tips.timeout);
			$("#_tips_").remove();
			$("body").append("<div id='_tips_' class='error'>" + t + "</div>");
			$.tips.timeout = setTimeout($.tips.remove, 2000);
		},
		remove: function(){
			$("#_tips_").remove();
		}
	}
})(jQuery);


// 自定义radio和checkbox
;(function($){
	$.fn.inputInit = function(){
		$(this).each(function(){
			var _in = $(this);
			if (_in.attr("type") == "radio" && _in.data("init") != "true"){
				var _t = _in.data("val") ? _in.data("val") : "";
				var _n = _in.attr("name");
				var _css = _in.data("css") ? _in.data("css") : "";
				var _checked = _in.attr("checked") ? "js_radioChecked" : "";
				_in.hide().data("init", "true").after("<a href='javascript:void(0)' class='js_radio " + _checked + " " + _css + "' data-n='" + _n + "'><span></span><p>" + _t + "</p></a>");
				_in.change(function(){
					var _n = _in.attr("name");
					$(this).parent().find(".js_radio").each(function(){
						if ($(this).data("n") == _n){
							$(this).removeClass("js_radioChecked");
						}
					});
					$(this).next(".js_radio").addClass("js_radioChecked");
				}).next(".js_radio").on("click", function(){
					var _this = $(this);
					var _n = _this.data("n");
					_this.parent().find("input").each(function(){
						var _in = $(this);
						if (_in.attr("type") == "radio" && _in.data("init") == "true" && _in.attr("name") == _n){
							_in[0].checked = false;
						}
					});
					_this.parent().find(".js_radioChecked").each(function(){
						var _this = $(this);
						if (_this.data("n") == _n){
							_this.removeClass("js_radioChecked");
						}
					});
					_this.addClass("js_radioChecked").prev("input")[0].checked = true;
				});
			} else if (_in.attr("type") == "checkbox" && _in.data("init") != "true"){
				var _t = _in.data("val") ? _in.data("val") : "";
				var _css = _in.data("css") ? _in.data("css") : "";
				var _checked = _in.attr("checked") ? "js_checkboxChecked" : "";
				_in.hide().data("init", "true").after("<a href='javascript:void(0)' class='js_checkbox " + _checked + " " + _css + "'><span></span><p>" + _t + "</p></a>");
				_in.change(function(){
					if ($(this)[0].checked) {
						$(this).next(".js_checkbox").addClass("js_checkboxChecked");
					} else {
						$(this).next(".js_checkbox").removeClass("js_checkboxChecked");
					}
				}).next(".js_checkbox").on("click", function(){
					$(this).toggleClass("js_checkboxChecked");
					var _checked = $(this).hasClass("js_checkboxChecked");
					$(this).prev("input")[0].checked = _checked;
				});
			}
		});
	}
})(jQuery);

// 自定义日期选择框
;(function($){
	function hideOptions(e){
		if($(document).data("nowselectoptions")){
			if (e && $(e.target).parents(".js_dateinput").find(".pop").get(0) == $($(document).data("nowselectoptions")).get(0)) {
				if (!$(e.target).hasClass("j_cl") || $(e.target).hasClass("n")){
					return false;
				}
			}
			$($(document).data("nowselectoptions")).hide().parent(".js_dateinput, .js_select").removeClass("js_dateinputOpen js_selectOpen");
			$(document).data("nowselectoptions", null);
			$(document).unbind("click", hideOptions);
		}
	}
	function showOptions(){
		$(document).bind("click", hideOptions);
		$($(document).data("nowselectoptions")).show().parent(".js_dateinput").addClass("js_dateinputOpen").find(".pop .bd .bdtime").hide();
	}
	$.fn.dateInputInit = function(){
		$(this).each(function(){
			var _in = $(this);
			if (_in.attr("type") == "text" && _in.data("init") != "true"){
				_in.data("init", "true");
				var defaultDate = _in.val(), defaultTime = "0:0", obj = {};
				if (defaultDate && defaultDate.split("-").length == 3) {
					if (defaultDate.indexOf("|") != -1){
						defaultTime = defaultDate.split("|")[1];
						defaultDate = defaultDate.split("|")[0];
					}
					defaultDate = defaultDate.split("-");
					obj = {Yr: defaultDate[0], Mn: defaultDate[1], Dd: defaultDate[2]};
				}
				var html = "";
				if (_in.data("time") == true || _in.data("time") == "true"){
					html = "<div class='js_dateinput' data-time='true' onselectstart='javascript:return false;'>";
				} else {
					html = "<div class='js_dateinput' onselectstart='javascript:return false;'>";
				}
				html += "<div class='current j_cl'><p class='j_cl'></p><span class='selector j_cl'></span></div>";
				html += "<div class='pop'><div class='hd'>";
				html += "<div class='yy'><a href='javascript:void(0)' class='prev prevY'></a><strong class='y'></strong><span>年</span><a href='javascript:void(0)' class='next nextY'></a></div>";
				html += "<div class='mm'><a href='javascript:void(0)' class='prev prevM'></a><strong class='m'></strong><span>月</span><a href='javascript:void(0)' class='next nextM'></a></div>";
				html += "</div><div class='bd'>";
				html += "<ul class='wk'><li>日</li><li>一</li><li>二</li><li>三</li><li>四</li><li>五</li><li>六</li></ul>";
				html += "<div class='day'>";
				for (var i = 0; i < 6; i++){
		        	html += "<div><a href='javascript:void(0)' class='n'></a><a href='javascript:void(0)' class='n'></a><a href='javascript:void(0)' class='n'></a><a href='javascript:void(0)' class='n'></a><a href='javascript:void(0)' class='n'></a><a href='javascript:void(0)' class='n'></a><a href='javascript:void(0)' class='n'></a></div>";
				}
				html += "</div>";
				if (_in.data("time") == true || _in.data("time") == "true"){
					defaultTime = defaultTime.split(":");
					obj.th = defaultTime[0];
					obj.tm = defaultTime[1];

					html += "<div class='time clearFix'><a href='javascript:void(0)' class='h'>" + defaultTime[0] + "</a><strong class='hh'>时</strong><a href='javascript:void(0)' class='m'>" + defaultTime[1] + "</a><strong class='mm'>分</strong></div>";
					html += "<div class='bdtime bdtime1'>";
					for (var j = 0; j < 24; j++){
						if (j % 4 == 0){ html += "<div class='clearFix'>";}
						var c = j == obj.th ? "c" : "";
						if (j % 4 != 3){
							html += "<a class='h " + c + "' href='javascript:void(0)' data-t='" + j + "'>" + j + "</a>";
						} else {
							html += "<a class='last h " + c + "' href='javascript:void(0)' data-t='" + j + "'>" + j + "</a></div>";
						}
					}
					html += "</div>";
					html += "<div class='bdtime bdtime2'>";
					for (var j = 0; j < 12; j++){
						if (j % 4 == 0){ html += "<div class='clearFix'>";}
						var c = (j * 5) == obj.tm ? "c" : "";
						if (j % 4 != 3){
							html += "<a class='m " + c + "' href='javascript:void(0)' data-t='" + (j * 5) + "'>" + (j * 5) + "</a>";
						} else {
							html += "<a class='last m " + c + "' href='javascript:void(0)' data-t='" + (j * 5) + "'>" + (j * 5) + "</a></div>";
						}
					}
					html += "</div>";
				}
				html += "</div></div></div>";
				_in.hide().after(html);

				var divDateInput = _in.next(".js_dateinput");
				divDateInput.find(".pop .hd a").on("click", YYMMSlide);
				divDateInput.find(".pop .bd .day a").on("click", DDClick);
				if (_in.data("time") == true || _in.data("time") == "true"){
					divDateInput.find(".pop .bd .time a").on("click", TimePopClick);
					divDateInput.find(".pop .bd .bdtime a").on("click", TimeClick);
				}

				divDateInput.on("click", function(e){
					if (!$(this).hasClass("js_dateinputDisabled")){
						var option = $(this).find(".pop");
						if($($(document).data("nowselectoptions")).get(0) != option.get(0)){
							hideOptions();
						}
						if(!option.is(":visible")){
							e.stopPropagation();
							$(document).data("nowselectoptions", option);
							showOptions();
						}
					}
				}).hover(function(e){
					if (e.type == "mouseenter"){
						$(this).addClass("js_dateinputHover");
					} else {
						$(this).removeClass("js_dateinputHover");
					}
				});

				YYMMinit(divDateInput, obj, true);
			}
		});
	};

	function YYMMSlide(){
		var _this = $(this), _parent = _this.parents(".js_dateinput");
		var y = _parent.data("year"), m = _parent.data("month"), d = _parent.data("dd");

		if (_this.hasClass("prevY") || _this.hasClass("nextY")){
			if (y > 1900 && _this.hasClass("prevY")) {
				y = parseInt(y) - 1;
			} else if (y < 2100 && _this.hasClass("nextY")) {
				y = parseInt(y) + 1;
			}
			_parent.data("year", y);
		} else if (_this.hasClass("prevM") || _this.hasClass("nextM")){
			if (_this.hasClass("prevM")){
				if (m > 1){
					m = parseInt(m) - 1;
				} else if (y > 1900){
					m = 12;
					y = parseInt(y) - 1;
				}
			} else {
				if (m < 12){
					m = parseInt(m) + 1;
				} else if (y < 2100){
					m = 1;
					y = parseInt(y) + 1;
				}
			}
			_parent.data("month", m);
			_parent.data("year", y);
		}

        // 闰年的2月为29天，翻页时，如果当前月份的当前日期为30、31号，到下个月时如果没有31，自动变成30，如有，则继续为31
        var dayArr = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        if (y % 4 == 0){
        	dayArr[1] = 29;
        }
        d = Math.min(dayArr[m - 1], d);
		YYMMinit(_parent, {Yr: y, Mn: m, Dd: d});
		return false;
	}
	function DDClick(){
		if (!$(this).hasClass("n")){
			var _this = $(this), _parent = _this.parents(".js_dateinput");
			var y = _parent.data("year"), m = _parent.data("month"), d = _this.html();
			if (_parent.data("time") == "true" || _parent.data("time") == true){
				var th = _parent.find(".pop .bd .time a.h").html();
				var tm = _parent.find(".pop .bd .time a.m").html();
				YYMMinit(_parent, {Yr: y, Mn: m, Dd: d, th: th, tm: tm}, true);
			} else {
				YYMMinit(_parent, {Yr: y, Mn: m, Dd: d}, true);
			}
		}
	}
	function YYMMinit(elem, o, save) {
        var now = new Date();
        var year = o.Yr || now.getFullYear();
        var month = now.getMonth();
        var hasTime = o.th != undefined && o.tm != undefined;

        if (o.Mn != null){
        	month = o.Mn - 1;
        }
        var dd = o.Dd || now.getDate();

        elem.find(".hd .yy .y").html(year);
        elem.find(".hd .mm .m").html(o.Mn || now.getMonth() + 1);

        if (save){
	        elem.data("year", year);
	        elem.data("month", month + 1);
	        elem.data("dd", dd);
	        if (!hasTime){
	        	elem.find(".current p").html(year + "年" + (month + 1) + "月" + dd + "日");
	        	elem.prev("input.js_date").val(year + "-" + (month + 1) + "-" + dd);
	        } else {
	        	elem.find(".current p").html(year + "年" + (month + 1) + "月" + dd + "日 " + o.th + "时 " + o.tm + "分");
	        	elem.prev("input.js_date").val(year + "-" + (month + 1) + "-" + dd + "|" + o.th + ":" + o.tm);
	        }
        }

        DDinit(elem, year, month, dd);
    }
    function DDinit(elem, Yr, Mn, Dd) {
        // 根据传入的数值生成新的日期
        var newDd = new Date();
        Dd = Dd ? Dd : newDd.getDate();
        newDd.setFullYear(Yr, Mn, Dd);
        var year = newDd.getFullYear(), month = newDd.getMonth(), dd = newDd.getDate();
        var firstD = new Date();
        firstD.setFullYear(year, month, 1);
        var firstDay = firstD.getDay();

        // 判断每个月有多少天
        var dayArr = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        // 闰年的2月为29天
        if (year % 4 == 0){
        	dayArr[1] = 29;
        }
        var dayLen = dayArr[month];
        var prevDayLen = month != 0 ? dayArr[month - 1] : dayArr[11];

        // 填充日期
        var dayElem = elem.find(".pop .bd .day a");
        for (var j = 0; j < dayElem.length; j++) {
        	var _this = $(dayElem[j]).attr("class", "n j_cl");
        	if (j >= firstDay && j < dayLen + firstDay){
        		_this.removeClass("n").html(j + 1 - firstDay);
        		if (j + 1 - firstDay == dd){
        			_this.addClass("c");
        		}
        	} else if (j >= dayLen + firstDay){
        		// 下个月的日期
        		_this.html(j + 1 - dayLen - firstDay);
        	} else {
        		// 上个月的日期
        		_this.html(prevDayLen - firstDay + j + 1);
        	}
        }
    }
    function TimePopClick(e){
    	var _this = $(this), pop = _this.parents(".pop .bd");
    	pop.find(".bdtime").hide();
    	if (_this.hasClass("h")){
    		if (_this.hasClass("c")){
    			_this.removeClass("c");
    		} else {
    			pop.find(".time a.c").removeClass("c");
    			_this.addClass("c");
    			pop.find(".bdtime1").show();
    		}
    	} else if (_this.hasClass("m")) {
    		if (_this.hasClass("c")){
    			_this.removeClass("c");
    		} else {
    			pop.find(".time a.c").removeClass("c");
    			_this.addClass("c");
    			pop.find(".bdtime2").show();
    		}
    	}
    }
    function TimeClick(e){
    	var _this = $(this), _parent = _this.parents(".js_dateinput"), _html = "", th, tm;
		var y = _parent.data("year"), m = _parent.data("month"), d = _parent.data("dd");
		if (_this.hasClass("h")){
			_parent.find(".pop .bd .bdtime1").hide().find("a.c").removeClass("c");
			_html = _this.addClass("c").html();
			th = _parent.find(".pop .bd .time a.h").removeClass("c").html(_html).html();
			tm = _parent.find(".pop .bd .time a.m").html();
		} else {
			_parent.find(".pop .bd .bdtime2").hide().find("a.c").removeClass("c");
			_html = _this.addClass("c").html();
			th = _parent.find(".pop .bd .time a.h").html();
			tm = _parent.find(".pop .bd .time a.m").removeClass("c").html(_html).html();
		}
		YYMMinit(_parent, {Yr: y, Mn: m, Dd: d, th: th, tm: tm}, true);
    }
})(jQuery);


// 图片上传
;(function($){
	function getSwfInstance(movieName) { 
		if (navigator.appName.indexOf("Microsoft") != -1) { 
			return window[movieName];
		} else { 
			return document[movieName];
		} 
	}

	// 文件式上传
	$.fn.imgUploadInit = function(){
		$(this).each(function(){
			var _in = $(this);
			if (_in.data("init") != "true" && _in.attr("id")){
				_in.data("init", "true");
				var canCut = _in.attr("cut");
				var cutWidth = _in.attr("width");
				var cutHeight = _in.attr("height");
				var maxWidth = _in.attr("maxWidth") || 400;

				var val = _in.val(), html;
				if (val.indexOf(".jpg") != -1 || val.indexOf(".jpeg") != -1 || val.indexOf(".png") != -1){
					html = "<div class='js_swfimgupload clearFix'><input type='text' readonly='readonly' class='p' value='" + val + "' /><div class='thumb'><img src='" + hhutil.getRootPath() + val + "' /></div><a href='javascript:void(0)' class='delBtn'>删除</a>";
				} else {
					html = "<div class='js_swfimgupload clearFix'><input type='text' readonly='readonly' class='p' value='未上传图片' /><div class='thumb'></div>";
				}
				if (canCut == "true" && cutWidth && cutHeight){
					html += "<div class='uploadbtn'><a href='javascript:void(0)'>上传</a></div></div>";
					_in.hide().after(html);
					_in.next(".js_swfimgupload").find(".uploadbtn a").on("click", function(){
						$.imgUploadPop({
							width: cutWidth,
							height: cutHeight,
							complete: function(data){
								_in.imgUploadSetVal(eval(data));
							}
						});
					});
				} else {
					html += "<div class='uploadbtn'><div id='_swf_" + _in.attr("id") + "'></div></div></div>";
					_in.hide().after(html);
					swfobject.embedSWF(hhutil.getRootPath()+"/js/weixin/swf/upload.swf?v2", "_swf_" + _in.attr("id"), 80, 36, "11.9.0", "", {"id": _in.attr("id"), "imgServer": imgUploadThumbServer, "maxWidth": maxWidth});
				}
				var divInput = _in.next(".js_swfimgupload");
				divInput.on("hover", "input.p, .delBtn, .thumb", function(e){
					if (e.type == "mouseenter"){
						if (divInput.find(".delBtn").length){
							divInput.find(".thumb").show().siblings(".delBtn").css({"display": "block"});
						}
					} else {
						divInput.find(".thumb").hide().siblings(".delBtn").hide();
					}
				});
				divInput.on("click", ".delBtn", function(e){
					$.alert({
						title: "温馨提示",
						txt: "确定要删除该图片吗？",
						btnN: "取消",
						btnY: "删除",
						btnYcss: "btnC",
						callbackY: function(){
							_in.imgUploadSetVal("");
						}
					});
				});
			}
		});
	};
	$.fn.imgUploadSetVal = function(url){
		if(url == 'thumbsizeout'){
			//alert("图文的封面图片最大支持64K");
			//$.alert.error("图文的封面图片最大支持64K");
			//$('body').inputError("图文的封面图片最大支持64K");
			$.alertYes( {
				title : "温馨提示",
				txt : "图文的封面图片最大支持64K！",
				btnN : "确定"
			});
		}else{
			if (url == ""){
				$(this).val("").next(".js_swfimgupload").find("input.p").val("未上传图片").siblings(".thumb").empty().siblings(".delBtn").remove();
			} else {
				var _this = $(this);
				_this.val(url);
				var divupload = _this.next(".js_swfimgupload");
				divupload.find("input.p").val(url).siblings(".thumb").html("<img src='" + hhutil.getRootPath()+url + "' />");
				if (divupload.find("a.delBtn").length == 0){
					divupload.append("<a href='javascript:void(0)' class='delBtn'>删除</a>");
				}
			}
			var onchange = $(this).attr("onchange");
			if (onchange){
				try{
					eval(onchange + "('" + url + "')");
				} catch(e) {}
			}
			
		}
		
	};

	// 一组图片上传
	$.fn.imgArrUploadInit = function(){
		$(this).each(function(){
			var _in = $(this);
			if (_in.data("init") != "true" && _in.attr("id")){
				_in.data("init", "true");
				var imgs = _in.val(), imgArr = imgs.split("|||");
				var cutWidth = _in.attr("width");
				var cutHeight = _in.attr("height");
				var itemHeight = Math.round(253 / cutWidth * cutHeight);
				var max = _in.attr("max") || 6;

				var html = "<div class='js_swfimgupload_arr clearFix'>";
				if (imgs && imgArr.length != 0){
					for (var i = 0; i < imgArr.length; i++){
						html += "<div class='item' style='height:" + itemHeight + "px;'><a href='javascript:void(0)' class='delBtn' style='display: none;'>删除</a><img src='" + hhutil.getRootPath() + imgArr[i] + "' /></div>";
					}
				}
				html += "<a href='javascript:void(0)' class='item appendbtn' style='height:" + (itemHeight - 2) + "px;'><span>添加图片</span></a></div>";
				_in.hide().after(html);
				checkItemId(_in);

				var appendBtn = _in.next(".js_swfimgupload_arr").find(".appendbtn");
				appendBtn.on("click", function(){
					if (max < _in.next(".js_swfimgupload_arr").find("div.item").length + 1){
						return false;
					}
					$.imgUploadPop({
						width: cutWidth,
						height: cutHeight,
						complete: function(data){
							appendBtn.before("<div class='item' style='height:" + itemHeight + "px;'><a href='javascript:void(0)' class='delBtn' style='display: none;'>删除</a><img src='" + hhutil.getRootPath() + data + "' /></div>");
							checkItemId(_in);
						}
					});
				});
				_in.next(".js_swfimgupload_arr").dragsort({ dragSelector: "div.item", dragBetween: true, placeHolderTemplate: "<div class='item holder'></div>", dragEnd: function(){
					checkItemId(_in);
				}});
				_in.next(".js_swfimgupload_arr").on("hover", ".item", function(e){
					if (e.type == "mouseenter"){
						$(this).find(".delBtn").show();
					} else {
						$(this).find(".delBtn").hide();
					}
				}).on("click", ".item .delBtn", function(){
					var _th = $(this).parent(".item");
					$.alert({
						title: "温馨提示",
						txt: "确定要删除该图片吗？",
						btnN: "取消",
						btnY: "删除",
						btnYcss: "btnC",
						callbackY: function(){
							_th.remove();
							checkItemId(_in);
						}
					});
				});
			}
		});
	}
	function checkItemId(dom){
		var val = "", max = dom.attr("max") || 6, after = dom.next(".js_swfimgupload_arr");
		after.find(".item").each(function(i){
			var th = $(this);
			if (!th.hasClass("appendbtn")){
				th.attr("class", "item item" + i);
				val += th.find("img").attr("src") + "|||";
			} else {
				th.attr("class", "item item" + i + " appendbtn");
			}
		});
		if (max < after.find("div.item").length + 1){
			after.find(".appendbtn").hide();
		} else {
			after.find(".appendbtn").css("display", "block");
		}
		val = val.replace(/\|\|\|$/gi, "");
		dom.val(val);
	}

	// 弹框式上传（可裁切）
	$.imgUploadPop = function(o){
		$.imgUploadPop.isReady = false;
		$.imgUploadPop.complete = o.complete;
		$.alert({
			title: "上传图片",
			txt: "<div class='swfupload'><div id='swfimgupload'></div></div>",
			init: function(){
				swfobject.embedSWF(hhutil.getRootPath()+"/js/weixin/swf/uploadPop.swf?v2", "swfimgupload", 500, 500, "11.9.0", "", {"id": "swfimgupload_pop", "imgServer": imgUploadServer, "width": o.width, "height": o.height});
			},
			btnY: "确定",
			btnN: "取消",
			css: "pop-alert-swf-upload",
			btnYClose: false,
			callbackY: function(){
				if (!$.imgUploadPop.isReady){
					$.alert.error("图片上传插件尚未加载，请刷新重试");
				} else {
					getSwfInstance("swfimgupload").upload();
				}
			}
		});
	}

	$._imgUpload_ = {
		error: function(type, id, isPop){
			// console.log("upload error:" + type + " / " + id + "/" + isPop);
			if (isPop){
				switch(type){
					case "imageIsTooSmall":
						$.alert.error("图片尺寸太小，请选择稍大的图片");
					break;
					case "imageTypeError":
						$.alert.error("图片格式错误");
					break;
					case "imageIsEmpty":
						$.alert.error("未上传任何图片");
					break;
					case "swfUnReady":
						$.alert.error("图片上传插件尚未加载，请刷新重试");
					break;
					case "uploadError":
						$.alert.error("上传错误");
					break;
				}
			}
		},
		start: function(id, isPop){
			 //console.log("upload start width id:" + id);
			 //console.log("isPop="+isPop);
		},
		complete: function(data, id, isPop){
			
			try{   
				if(saveImg && typeof(saveImg) == "function"){    
					 saveImg(eval(data));   
				}  
			 }catch(e){   
				 //alert("方法不存在");  
			 }
			if (!isPop){
				var dom = $("#" + id);
				dom.imgUploadSetVal( eval(data));
			} else {
				if(!hhutil.isEmpty(eval(data)) && eval(data)=='sizeout'){
					$.alert.error("图片尺寸太大，微信素材图片最大支持1M");
				}else{
					if ($.imgUploadPop.complete){
						$.imgUploadPop.complete(eval(data));
					}
					$("#pop-alert").remove();
				}
			}
		},
		popReady: function(){
			$.imgUploadPop.isReady = true;
		}
	}
})(jQuery);

// 音频播放器
;(function($){
	$.audioPlayer = function(url){
		$("#_as3_audio_player_").remove();
		var html = "<div id='_as3_audio_player_' style='width:1px;height:1px;position:absolute;left:-999px;top:-999px;overflow:hidden;'><div id='_as3_audio_player_swf_'></div></div>";
		$("body").append(html);
		swfobject.embedSWF(hhutil.getRootPath()+"/theme/grey/swf/audioPlayer.swf", "_as3_audio_player_swf_", 1, 1, "11.9.0", "", {"audio": url});
		console.log("play audio:" + url);
	}
	$.audioPlayer.remove = function(){
		$("#_as3_audio_player_").remove();
	}
})(jQuery);

// 微信菜单
;(function($){
	
	var wc = {
		init: function(){
			wc.dom = $("#wcInputs");

			// 设置动作的类型切换
			wc.dom.find(".hd a").on("click", wc.menuTypeSlide);

			// 弹出图文消息选择框
			wc.dom.find(".bd .bdarticle .appendAsset").on("click", wc.openAssetsPop);

			// 更换素材移入与点击
			wc.dom.find(".bd .bdarticle .asset").on("hover", ".item", wc.onAssetHover).on("click", ".item", wc.openAssetsPop);

			// 弹出图片选择框
			wc.dom.find(".bd .bdimg .appendPhoto").on("click", wc.openPhotoPop);

			// 更换图片移入与点击
			wc.dom.find(".bd .bdimg .photo").on("hover", ".item", wc.onAssetHover).on("click", ".item", wc.openPhotoPop);

			// 弹出音频选择框
			wc.dom.find(".bd .bdaudio .appendAudio").on("click", wc.openAudioPop);

			// 显示当前
			var c = wc.dom.find(".hd a.c").data("type");
			if (c){
				wc.dom.find(".bd .bditem").hide().siblings("." + c).show();
			}
			
			//初始化页面
			var msgtype = wc.dom.data("msgtype");
			var content = wc.dom.data("content");
			if(!hhutil.isEmpty(msgtype) && !hhutil.isEmpty(content)){
				//初始化
				wc.initMsgContent(msgtype,content);
			}
			
		},
		initMsgContent: function(msgtype,content){
			//初始化消息内容
			if(msgtype == "txt"){
				//文本
				wc.dom.find("textarea").val(content);
			}else if(msgtype == "article"){
				//图文
				gtyutil.getGraphicHtml(content.substr(2),wc,function(html){
					var current = $("#assetsListPop .current");
					//if (current.hasClass("itemSingle")){
					//	html = "<div class='item itemSingle' data-id='" + msgtype + "'>" + html + "</div>";
					//} else {
					//	html = "<div class='item itemMulit' data-id='" + msgtype + "'>" + html + "</div>";
					//}
					var dom = wc.dom.find(".bd .bdarticle .asset");
					dom.show().html(html).siblings(".appendAsset").hide();
					dom.find(".item .mask").hide().html("更换素材");
				});	
			}else if(msgtype == "img"){
				//图片
				gtyutil.getPhotoHtml(content.substr(2), wc,function(html){
					var current = $("#photoListPop .current .img img");
					//var html = "<div class='item' data-id='" + msgtype + "'><div class='mask'></div><img src='" + html + "' /></div>";
					var dom = wc.dom.find(".bd .bdimg .photo");
					dom.show().html(html).siblings(".appendPhoto").hide();
					dom.find(".mask").hide().html("更换图片");
					
				});
				
			}else if(msgtype == "audio"){
				//语音
				
			}
			
		},
		menuTypeSlide: function(){
			var type = $(this).data("type");
			$(this).addClass("c").siblings("a").removeClass("c");
			wc.dom.find(".bd ." + type).show().siblings(".bditem").hide();
		},
		onAssetHover: function(e){
			if (e.type == "mouseenter"){
				$(this).find(".mask").show();
			} else {
				$(this).find(".mask").hide();
			}
		},
		openAssetsPop: function(){
			wc.assetId = $(this).data("id");
			gtyutil.getGraphicHtml(null,wc,function(html){
				$.alert({
					title: "添加图文消息",
					txt: "<div class='assetsList' id='assetsListPop'></div>",
					init: function(){
						//console.log(html);
						$("#assetsListPop").html(html).on("hover", ".item", wc.assetsPopItemHover).on("click", ".item", wc.assetsPopItemClick);
						$.waterfall.init($("#assetsListPop"), "item", 3, 289, 15);
					},
					btnY: "确定",
					btnN: "取消",
					btnYClose: false,
					css: "pop-alert-assetsList",
					callbackY: function(){
						if (wc.assetId){
							var current = $("#assetsListPop .current");
							var html = "";
							if (current.hasClass("itemSingle")){
								html = "<div class='item itemSingle' data-id='" + wc.assetId + "'>" + current.html() + "</div>";
							} else {
								html = "<div class='item itemMulit' data-id='" + wc.assetId + "'>" + current.html() + "</div>";
							}
							var dom = wc.dom.find(".bd .bdarticle .asset");
							dom.show().html(html).siblings(".appendAsset").hide();
							dom.find(".item .mask").hide().html("更换素材");
							$("#pop-alert").remove();
						}
					}
				});
			});
			//=================
		},
		assetsPopItemHover: function(e){
			var _this = $(this);
			if (e.type == "mouseenter"){
				if (!_this.hasClass("current")){
					$(this).find(".mask").show();
				}
			} else {
				if (!_this.hasClass("current")){
					$(this).find(".mask").hide();
				}
			}
		},
		assetsPopItemClick: function(){
			$("#assetsListPop .current").removeClass("current").find(".mask").hide();
			$(this).addClass("current").find(".mask").show();
			wc.assetId = $(this).data("id");
		},
		openPhotoPop: function(){
			wc.picId = $(this).data("id");
			
			gtyutil.getPhotoHtml(null, wc,function(html){
				$.alert({
					title: "添加图片",
					txt: "<div class='photoList' id='photoListPop'></div>",
					init: function(){
						//console.log(html);
						$("#photoListPop").html(html).on("hover", ".item", wc.photoPopItemHover).on("click", ".item", wc.photoPopItemClick);
					},
					btnY: "确定",
					btnN: "取消",
					btnYClose: false,
					css: "pop-alert-photoList",
					callbackY: function(){
						if (wc.picId){
							var current = $("#photoListPop .current .img img");
							//图片中内容已经保存了完整的图片地址
							var imgsrc = current.attr("src") ;
							if(!hhutil.isEmpty(imgsrc) && imgsrc.indexOf("http://")<0){
								//不是完整路径
								imgsrc = hhutil.getRootPath() + imgsrc ; 
							}	
							
							var html = "<div class='item' data-id='" + wc.picId + "'><div class='mask'></div><img src='" + imgsrc + "' /></div>";
							var dom = wc.dom.find(".bd .bdimg .photo");
							dom.show().html(html).siblings(".appendPhoto").hide();
							dom.find(".mask").hide().html("更换图片");
							$("#pop-alert").remove();
						}
					}
				});
				//=========
			});
			//==================
		},
		photoPopItemHover: function(e){
			var _this = $(this);
			if (e.type == "mouseenter"){
				if (!_this.hasClass("current")){
					$(this).find(".mask").show();
				}
			} else {
				if (!_this.hasClass("current")){
					$(this).find(".mask").hide();
				}
			}
		},
		photoPopItemClick: function(){
			$("#photoListPop .current").removeClass("current").find(".mask").hide();
			$(this).addClass("current").find(".mask").show();
			wc.picId = $(this).data("id");
		},
		audioPopItemHover: function(e){
			var _this = $(this);
			if (e.type == "mouseenter"){
				if (!_this.hasClass("current")){
					$(this).find(".mask").show();
				}
			} else {
				if (!_this.hasClass("current")){
					$(this).find(".mask").hide();
				}
			}
		},
		audioPopItemClick: function(){
			$("#audioListPop .current").removeClass("current").find(".mask").hide();
			$(this).addClass("current").find(".mask").show();
			wc.picId = $(this).data("id");
		},
		openAudioPop: function(){
			wc.audioId = $(this).data("id");
			gtyutil.getAudioHtml(wc.audioId, wc,function(html){
				$.alert({
					title: "添加音频",
					txt: "<div class='audioList' id='audioListPop'></div>",
					init: function(){
						$("#audioListPop").html(html).on("hover", ".item", wc.audioPopItemHover).on("click", ".item", wc.audioPopItemClick);
					},
					btnY: "确定",
					btnN: "取消",
					btnYClose: false,
					css: "pop-alert-audioList",
					callbackY: function(){
						if (wc.audioId){
							console.log("选中 wc.audioId="+wc.audioId);
							// var current = $("#photoListPop .current .img img");
							// var html = "<div class='item' data-id='" + wc.audioId + "'><div class='mask'></div><img src='" + current.attr("src") + "' /></div>";
							// var dom = wc.dom.find(".bd .bdimg .photo");
							// dom.show().html(html).siblings(".appendPhoto").hide();
							// dom.find(".mask").hide().html("更换图片");
							$("#pop-alert").remove();
						}
					}
				});
				//=========
			});
			//==================
		}
	};
	wc.init();

})(jQuery);


// 图表
;(function($){

	$.chart = {};

	$.chart.setUvPv = function(d){
		// var _m;
		// if (d.period == 30) {
		// 	_m = 6;
		// }
		// for (var i = 0, len = d.data.length; i < len; i++){
		// 	if (i % _m != 0 && i != len - 1) {
		// 		d.data[i] = "";
		// 	}
		// }
		var _total = 0, _average = 0;
		for (var j = 0, len = d.c.length; j < len; j++){
			_total += parseInt(d.c[j]);
		}
		if(!hhutil.isEmpty(d.c.length) && d.c.length>0){
			_average = Math.round(_total / d.c.length);
		}
		
		$("#" + d.average).html(hhutil.isEmpty(_average)?0:_average);
		$("#" + d.total).html(hhutil.isEmpty(_total)?0:_total);

		var lineChartData = {
			labels : d.data,
			datasets : [{
				fillColor : "rgba(92,172,223,0.5)",
				strokeColor : "rgba(92,172,223,1)",
				pointColor : "#fff",
				pointStrokeColor : "rgba(92,172,223,1)",
				data : d.c,
				average : _average
			}]
		}

		var myLine = new Chart(document.getElementById(d.id).getContext("2d")).Line(lineChartData, {
			scaleLabel : "<%=value%>",
			scaleLabelInt : true,
			scaleFontColor : "#bdc5c8",
			showAverage : true
		});
		return myLine;
	}


	$.chart.doug = function(d){

		var _dataArr = [], _html = "<ul>";
		var _colors = ["#4d5360", "#f74b4d", "#46bebc", "#fdb358", "#74b365", "#dcb3f4", "#9cbee5", "#949fb1", "#939f88", "#e2db76", "#669fbc", "#3b8693"];
		var barChartData = {
			labels : d.data,
			datasets : [{
				fillColor : "rgba(151,187,205,0.5)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				data : d.c
			}]
		}

		var _len = Math.min(d.c.length, d.data.length);
		for (var i = 0; i < _len; i++){
			_dataArr.push({value: parseFloat(d.c[i]), color: _colors[i]});
			if (i % 5 == 0 && i != 0) {
				_html += "</ul><ul>";
			}
			_html += "<li><span style='background: " + _colors[i] + "'></span><p>" + d.data[i] + "<sub>" + d.c[i] + "%</sub></p></li>";
		}
		_html += "</ul>";
		$("#" + d.html).html(_html);
		var myd = new Chart(document.getElementById(d.id).getContext("2d")).Doughnut(_dataArr);
	}




})(jQuery);

$(function(){

	// 初始化from表单内的元素
	$.fromInit();

	// 显示弹出框
	$("#webHeader .tools .chagepw").on("click", function(){
		var html = "<form id='userForm' action='/user/userBean!modifyPassword.action' >";
		html += "<input type='hidden' name='dtUser.id' id='userId' value=''/>";
		
		html += "<div class='i old'><h6>原始密码</h6><input type='dtUser.yspassword' class='input' /></div>";
		html += "<div class='i new'><h6>新密码</h6><input type='password' name='dtUser.password' class='input' /></div>";
		html += "<div class='i new2'><h6>确认新密码</h6><input type='password' name='dtUser.repassword' class='input' /></div>";
		html += "</form>"
		// 弹出框
		$.alert({
			title: "修改密码",
			txt: html,
			btnY: "修改",
			btnN: "取消",
			css: "pop-alert-changepw",
			callbackY: function(){
				var old = $("#pop-alert .old input");
				var n = $("#pop-alert .new input");
				var n2 = $("#pop-alert .new2 input");
				if (old.inputEmpty()){
					old.inputError("原始密码不能为空");
					return false;
				} else if (n.inputEmpty()){
					n.inputError("新密码不能为空");
					return false;
				} else if (n2.inputEmpty()){
					n2.inputError("确认密码不能为空");
					return false;
				} else if (n.val() != n2.val()){
					n2.inputError("两次密码输入不一致");
					return false;
				} else {
					$.loading();
					$('#userId').val($('#my_current_user').val());
					$('#userForm').submit();
					setTimeout(function(){
						$.loading.remove();
					},2000);
				}
			}
		});
	});
	
	
	
	// 显示弹出框
	$("#webHeader .tools .agencychagepw").on("click", function(){
		var html = "<form id='userForm' action='/user/userBean!agencyModifyPassword.action' >";
		html += "<input type='hidden' name='dtUser.id' id='userId' value=''/>";
		
		html += "<div class='i old'><h6>原始密码</h6><input type='dtUser.yspassword' class='input' /></div>";
		html += "<div class='i new'><h6>新密码</h6><input type='password' name='dtUser.password' class='input' /></div>";
		html += "<div class='i new2'><h6>确认新密码</h6><input type='password' name='dtUser.repassword' class='input' /></div>";
		html += "</form>"
		// 弹出框
		$.alert({
			title: "修改密码",
			txt: html,
			btnY: "修改",
			btnN: "取消",
			css: "pop-alert-changepw",
			callbackY: function(){
				var old = $("#pop-alert .old input");
				var n = $("#pop-alert .new input");
				var n2 = $("#pop-alert .new2 input");
				if (old.inputEmpty()){
					old.inputError("原始密码不能为空");
					return false;
				} else if (n.inputEmpty()){
					n.inputError("新密码不能为空");
					return false;
				} else if (n2.inputEmpty()){
					n2.inputError("确认密码不能为空");
					return false;
				} else if (n.val() != n2.val()){
					n2.inputError("两次密码输入不一致");
					return false;
				} else {
					$.loading();
					$('#userId').val($('#my_current_user').val());
					$('#userForm').submit();
					setTimeout(function(){
						$.loading.remove();
					},2000);
				}
			}
		});
	});
	

	if ($("body").height() < $(window).height()){
		$("body").height($(window).height());
	}



	

	







});




