var model = {

		/**
		 * 保存
		 */
		 setRecordQueryCondition : function(key,value,callback){
			var data = {
					KeyUrl:location.href,
					Key:key,
					value:value
			};
			model.set(key,value);
			if(model.isLocalStorage()){
				if("function" == typeof callback){
					callback(true);
				}
				return;
			}
			$.ajax({
				url:"/web/setRecordQueryCondition",
				type:"post",
				data:data,
				success:function(data){
					if("function" == typeof callback){
						callback(true);
					}
				},error:function(){
					if("function" == typeof callback){
						callback(false);
					}
				}
			});
		},
		
		/**
		 * 得到
		 */
		 getRecordQueryCondition : function(key,callback){
			var data = {
					KeyUrl:location.href,
					Key:key
			};
			if(!model.isNull(key)){ 
				if("function" == typeof callback){
					callback(model.get(key));
				}
				return;
			}
			if(model.isLocalStorage()){
				if("function" == typeof callback){
					callback();
				}
				return;
			}
			$.ajax({
				url:"/web/getRecordQueryCondition",
				type:"post",
				data:data,
				success:function(data){
					model.set(key,data);
					if("function" == typeof callback){
						if(data==""){
							callback();
						}else{
							callback(data);							
						}
					}
				},error:function(){
					if("function" == typeof callback){
						callback();
					}
				}
			});
		},
		
		/**
		 * 删除
		 */
		deleteRecordQueryCondition : function(key,callback){
			var data = {
					KeyUrl:location.href,
					Key:key,
					deleteType:"key"
			}
			model.del(key);
			if(model.isLocalStorage()){
				if("function" == typeof callback){
					callback(true);
				}
				return;
			}
			$.ajax({
				url:"/web/getRecordQueryCondition",
				type:"post",
				data:data,
				success:function(data){
					if("function" == typeof callback){
						callback(true);
					}
				},error:function(){
					if("function" == typeof callback){
						callback(false);
					}
				}
			});
		},
		
		/**
		 * 清空
		 */
		 clearRecordQueryCondition : function(key,callback){
			var data = {
					KeyUrl:location.href, 
					deleteType:"KeyUrl"
			}
			model.del();
			if(model.isLocalStorage()){
				if("function" == typeof callback){
					callback(true);
				}
				return;
			}
			$.ajax({
				url:"/web/getRecordQueryCondition",
				type:"post",
				data:data,
				success:function(data){
					if("function" == typeof callback){
						callback(true);
					}
				},error:function(){
					if("function" == typeof callback){
						callback(false);
					}
				}
			});
		},
		
		   set: function (key,value){
			var locationhref = location.href;
			//是否存在 localStorage
			if("object" == typeof model.localStorage){
				var RecordQueryCondition = localStorage.RecordQueryCondition;
				try { RecordQueryCondition = JSON.parse(RecordQueryCondition); } catch (e) { }
				if(RecordQueryCondition==null || "object" != typeof RecordQueryCondition){
					RecordQueryCondition={};
				}
				if(RecordQueryCondition[locationhref]==null){
					RecordQueryCondition[locationhref]={};
				}
				RecordQueryCondition[locationhref][key] = value;
				localStorage.RecordQueryCondition = JSON.stringify(RecordQueryCondition);
				localStorage.RecordQueryConditionLen = Object.keys(RecordQueryCondition).length;
			     //不存在者存在window中
			}else{
				var RecordQueryCondition = window.WinRecordQueryCondition;
				if(RecordQueryCondition==null || "object" != typeof RecordQueryCondition){
					RecordQueryCondition={};
				}
				RecordQueryCondition[key] = value;	 
				window.WinRecordQueryCondition = RecordQueryCondition;
			}
		},
		
		 del : function (key){
			var locationhref = location.href;
			//是否存在 localStorage
			if("object" == typeof localStorage){
				var RecordQueryCondition = localStorage.RecordQueryCondition;
				try { RecordQueryCondition = JSON.parse(RecordQueryCondition); } catch (e) { }
				if(RecordQueryCondition==null || "object" != typeof RecordQueryCondition){
					RecordQueryCondition={};
				}
				if(key==null){
					delete RecordQueryCondition[locationhref];
				}else{
					if(RecordQueryCondition[locationhref]==null){
						RecordQueryCondition[locationhref]={};
					}
					delete RecordQueryCondition[locationhref][key];
				}
				localStorage.RecordQueryCondition = JSON.stringify(RecordQueryCondition);
				localStorage.RecordQueryConditionLen = Object.keys(RecordQueryCondition).length;
			     //不存在者存在window中
			}else{
				var RecordQueryCondition = window.WinRecordQueryCondition;
				if(RecordQueryCondition==null || "object" != typeof RecordQueryCondition){
					RecordQueryCondition={};
				}
				
				if(key==null){
					RecordQueryCondition = null;
				}else{ 
					delete RecordQueryCondition[key];
				}
				window.WinRecordQueryCondition = RecordQueryCondition;
			}
		},
		
		 get:function (key){
			var locationhref = location.href;
			var value = null;
			//是否存在 localStorage
			if("object" == typeof localStorage){
				var RecordQueryCondition = localStorage.RecordQueryCondition;
				try { RecordQueryCondition = JSON.parse(RecordQueryCondition); } catch (e) { }
				if(RecordQueryCondition==null || "object" != typeof RecordQueryCondition){
					RecordQueryCondition={};
				}
				if(RecordQueryCondition[locationhref]==null){
					RecordQueryCondition[locationhref]={};
				}
				localStorage.RecordQueryCondition = JSON.stringify(RecordQueryCondition);
				localStorage.RecordQueryConditionLen = Object.keys(RecordQueryCondition).length;
				value = RecordQueryCondition[locationhref][key];
			     //不存在者存在window中
			}else{
				var RecordQueryCondition = window.WinRecordQueryCondition;
				if(RecordQueryCondition==null || "object" != typeof RecordQueryCondition){
					RecordQueryCondition={};
				}
				window.WinRecordQueryCondition = RecordQueryCondition;
				value = RecordQueryCondition[key];	 
			}
			return value;
		},
		
		
		// 判断是否支持本地html5缓存
		 isLocalStorage : function(){
			if("object" == typeof localStorage){
				return true;
			}else{
				return false;
			}
		},
		 isNull:function (key){
			var locationhref = location.href;
			var value = null;
			//是否存在 localStorage
			if("object" == typeof localStorage){
				var RecordQueryCondition = localStorage.RecordQueryCondition;
				try { RecordQueryCondition = JSON.parse(RecordQueryCondition); } catch (e) { }
				if(RecordQueryCondition==null || "object" != typeof RecordQueryCondition){
					RecordQueryCondition={};
				}
				if(RecordQueryCondition[locationhref]==null){
					RecordQueryCondition[locationhref]={};
				}
				localStorage.RecordQueryCondition = JSON.stringify(RecordQueryCondition);
				localStorage.RecordQueryConditionLen = Object.keys(RecordQueryCondition).length;
				value = RecordQueryCondition[locationhref][key];
			     //不存在者存在window中
			}else{
				var RecordQueryCondition = window.WinRecordQueryCondition;
				if(RecordQueryCondition==null || "object" != typeof RecordQueryCondition){
					RecordQueryCondition={};
				}
				window.WinRecordQueryCondition = RecordQueryCondition;
				value = RecordQueryCondition[key];	 
			}
			if(value==null){
				return true;
			}else{
				return false;
			}
		}
	 
	}