package com.lr.backer.util;

import javax.servlet.http.HttpServletRequest;

public class UrlUtil {

	 public static String getCurUri(HttpServletRequest request) {
		    String qStr = request.getQueryString();
		    String rtn = request.getRequestURI();

		    rtn = rtn.replaceAll("//", ""); 
		     
		    if(rtn.indexOf("/") == 0){
		    	rtn = rtn.substring(1, rtn.length());  
		    }
		    
		    if (qStr != null) {
		      rtn = rtn + "?" + qStr;
		    }
		   /* String ur = getCurUr(request);
		    if ((qStr != null) && (!qStr.equals(""))) {
		      if (qStr.indexOf("&") != -1)
		        rtn = ur.substring(ur.indexOf("/"), ur.indexOf("&")).toString();
		      else
		        rtn = ur;
		    }*/
		    if(rtn.indexOf("http://")<0){
		    	/*while(true){
		    		if(rtn.indexOf("/")==0){
			    		rtn = rtn.substring(1);
			    	}else{
			    		break;
			    	} 
		    	}*/
		    	rtn=Constants.PROJECT_PATH+rtn;
		    }
		    return rtn.toString();
     }
	 public static String getCurUr(HttpServletRequest request) {
		    String qStr = request.getQueryString();
		    String rtn = request.getRequestURI();
		    if (qStr != null) {
		      rtn += "?" + qStr ;
		    }
		    if(rtn.indexOf("http://")<0){
		    	while(true){
		    		if(rtn.indexOf("/")==0){
			    		rtn = rtn.substring(1);
			    	}else{
			    		break;
			    	}
		    	}
		    	rtn=Constants.PROJECT_PATH+rtn;
		    }
		    return rtn.toString();
	 }
	      
}
