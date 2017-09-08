package com.lr.backer.util;

import java.net.URLDecoder;

import javax.servlet.ServletRequest;

public class ParamUtil {
	public static String getParameter(ServletRequest request, String paramName)
	  {
	    String temp = request.getParameter(paramName);
	    if ((temp != null) && (!temp.equals("")))
	    {
	      try
	      {
	        temp = URLDecoder.decode(temp, "UTF-8");
	      }
	      catch (Exception e) {
	        return "";
	      }
	      return temp;
	    }

	    return "";
	  }

	  public static int getIntParameter(ServletRequest request, String paramName, int defaultNum)
	  {
	    String temp = request.getParameter(paramName);
	    if ((temp != null) && (!temp.equals("")))
	    {
	      int num = defaultNum;
	      try
	      {
	        num = Integer.parseInt(temp);
	      }
	      catch (Exception localException) {
	      }
	      return num;
	    }

	    return defaultNum;
	  }
}
