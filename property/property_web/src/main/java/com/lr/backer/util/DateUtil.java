package com.lr.backer.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

public class DateUtil {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	
	public static String getNowYMD(){
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");  
		return sdf1.format(new Date());
	}
	
	public static long getLongTime(){
		return System.currentTimeMillis();
	}
	
	/**
	 * 获取某一时间  这一周日期
	 * @param mdate
	 * @return
	 */
	public static List<Date> dateToWeek(Date mdate) {  
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(mdate);
		cal.add(java.util.Calendar.DATE, -1);
		mdate=cal.getTime();
		int b = mdate.getDay();  
	    Date fdate;  
	   // String []week={"星期一","星期二","星期三","星期四","星期五","星期六","星期日",};
	    //List<Map<String,Date>> mapList=new ArrayList<Map<String,Date>>();
	    List<Date> list = new ArrayList<Date>();  
	    Long fTime = mdate.getTime() - b * 24 * 3600000;  
	    for (int a = 1; a <= 7; a++) {  
	    	//Map<String,Date> map=new HashMap<String,Date>();
	        fdate = new Date();  
	        fdate.setTime(fTime + (a * 24 * 3600000));  
	        list.add(a-1, fdate);  
	       // map.put(week[a-1], fdate);
	        //mapList.add(map);
	    }  
	    return list;  
	}  
	/**
	 * 获得昨天日期
	 * @return
	 */
	public static Date yesterDay(){
		java.util.Calendar cal = java.util.Calendar.getInstance();
	    cal.setTime(new Date());
	    cal.add(java.util.Calendar.DATE, -1); // 向前一周
	    return cal.getTime();
	}
	/**
	 * 获得明天日期
	 * @return
	 */
	public static Date tomorrowDay(){
		java.util.Calendar cal = java.util.Calendar.getInstance();
	    cal.setTime(new Date());
	    cal.add(java.util.Calendar.DATE, 1); // 向前一周
	    return cal.getTime();
	}
	public static Integer forMM(Object text){
		SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-dd");  
		try {
			Date d=sdfL.parse(text.toString());
			
			return d.getDate();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String forYear(Date date){
		SimpleDateFormat sdfL = new SimpleDateFormat("yyyy"); 
		return sdfL.format(date);
	}
	public static String forMM2(Date date){
		SimpleDateFormat sdfL = new SimpleDateFormat("dd"); 
//		java.util.Calendar cal = java.util.Calendar.getInstance();
//	    cal.setTime(date);
//	    cal.add(Calendar.DATE, -1);
		return sdfL.format(date);
	}
	/**
	 * 获取某一时间具体日期
	 * @param mdate				某一时间
	 * @param z_type			true:周	false:月
	 * @param w_type			true:上一周日期	false:下一周日期
	 * @param m_type			true:上一月日期	false:下一月日期
	 * @return
	 */
	public static List<Date> lastToweek(Date mdate,boolean z_type,boolean w_type,boolean m_type){
	    java.util.Calendar cal = java.util.Calendar.getInstance();
	    cal.setTime(mdate);
	    if(z_type){
	    	if(w_type)
	    		 cal.add(java.util.Calendar.DATE, -7); // 向前一周
	    	else
	    		 cal.add(java.util.Calendar.DATE, 7);  // 向后一周
	    }else{
	    	if(m_type)
	    		cal.add(java.util.Calendar.MONTH, -1); // 向前一月
	    	else
	    		cal.add(java.util.Calendar.MONTH, 1);  // 向后一月
	    }
	    return dateToWeek(cal.getTime());
	}
	/**
	 * 获取调试时间
	 * @param date
	 * @param type
	 * @return
	 */
	public static Date changeTime(Date date,String type){
		 java.util.Calendar cal = java.util.Calendar.getInstance();
		 cal.setTime(date);
		 if(type.equals("s_z")){
			 cal.add(java.util.Calendar.DATE, -7);
		 }else if(type.equals("x_z")){
			 cal.add(java.util.Calendar.DATE, 7); 
		 }else if(type.equals("s_y")){
			 cal.add(java.util.Calendar.MONTH, -1);
		 }else if(type.equals("x_y")){
			 cal.add(java.util.Calendar.MONTH, 1);
		 }
		 return cal.getTime();
	}
	public static Date textToDate(String text){
		SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		try {
			return sdfL.parse(text);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}
	public static Date textToMin(String text){
		SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
		try {
			return sdfL.parse(text);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Date();
	}
	public static String dateToText(Date  date){
		SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		return sdfL.format(date);
	}
	public static String dataYMD(Date date){
		SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-dd");  
		return sdfL.format(date);
	}
	public static Date textYMD(String text){
		SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-dd");  
		try {
			return sdfL.parse(text);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 取得两段时间之间年份差
	 * @return
	 */
	public static int only(Date date,String text){
		SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date now=sdfL.parse(text);
			long l=(date.getTime()-now.getTime())/(24*60*60*1000);
			return (int)l/365;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 取得两段时间之间时间差
	 * @return
	 */
	public static int onlyTiem(Date date,String text){
		SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date now=sdfL.parse(text);
			long l=date.getTime()-now.getTime();
			return (int)l;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	//判断两个日期是不是同一天
	public static boolean compare(String text1,String text2){
		SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1=sdfL.parse(text1);
			Date d2=sdfL.parse(text2);
			if(d1.compareTo(d2) == 0){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	//得到本月第一天日期或最后一天日期
	public static String giveDate(Date date,boolean fag){
		if(fag){
			SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-01");
			return sdfL.format(date);
		}
		else{
			SimpleDateFormat sdfL = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();  
			// 设置时间,当前时间不用设置  
			calendar.setTime(date);  
			// 设置日期为本月最大日期  
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE)); 
			return 			sdfL.format(calendar.getTime());
		}

	}
	/**
	 * 得到日历
	 */
	public static List<List<Map<String,Object>>> getcalendar(Date date,Model model){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 0);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(date);
	    calendar1.set(Calendar.DAY_OF_MONTH, calendar     
	            .getActualMinimum(Calendar.DAY_OF_MONTH));    
		 // calendar.setTime(date);
//		  System.out.println(calendar.getTime());
		//得到当前这个月
		//System.out.println(calendar.get(Calendar.DAY_OF_WEEK)+1); 
	    //calendar.setTime(date);
	    //System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//		calendar.set(Calendar.DAY_OF_WEEK, 1);
//		SimpleDateFormat format = new SimpleDateFormat("E");
//		System.out.println("本月第一天是：" + format.format(calendar.getTime())); 
		
		List<List<Map<String,Object>>> rowList=new ArrayList<List<Map<String,Object>>>();
		String []rostring={"zero","one","two","three","four","five","six"};
		int count=1;
	//	List<Integer> intlist=new ArrayList<Integer>();
		for(int index =0;index<9;index++){
			
			List<Map<String,Object>> collist=new ArrayList<Map<String,Object>>();
			
			Map<String,Object> num=new HashMap<String,Object>();
			Map<String,Object> numMap=new HashMap<String,Object>();
			for(int i=0;i<7;i++){
				if(rowList.size()==0&&num.size()==0){
					int row=0;
//					if(calendar.get(Calendar.DAY_OF_WEEK)!=7)
						row=calendar1.get(Calendar.DAY_OF_WEEK)-1;
					for(int j=0;j<row-1;j++){
						//if(i==6){i=5;}
						i++;
						numMap.put(rostring[i],null);
						num.put(rostring[j],null);
					}
				}
			//	intlist.add(count++);
				numMap.put(rostring[i], count++);
				num.put(rostring[i],calendar1.getTime());
				calendar1.add(Calendar.DATE, 1);
				if(count==calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+2){
					for(int j=i;j<7;j++){
						numMap.put(rostring[i],null);
						num.put(rostring[j],null);
					}
					break;
				}				
			}
			collist.add(num);
			
			collist.add(numMap);
 			rowList.add(collist);
			if(count==calendar.getActualMaximum(Calendar.DAY_OF_MONTH)+2){
				break;
			}
		}
	//	model.addAttribute("intlist", intlist);
//		for(int index=1;index<calendar.getActualMaximum(Calendar.DAY_OF_MONTH);index++){
//			int count=0;
//			List<List<Integer>> collist=new ArrayList<List<Integer>>();
//			List<Integer> num=new ArrayList<Integer>();
//			if(rowList.size()==0){
//				for(int i=0;i<calendar.get(Calendar.DAY_OF_WEEK)+2;i++){
//					num.add(0);
//					count++;
//				}
//			}
//			num.add(index);
//			count++;
//			collist.add(num);
//			rowList.add(collist);
//		}
		
//		for(int i=0;i<rowList.size();i++ ){
//			List<Map<String,Object>> collist=rowList.get(i);
//			for(int num=0;num<collist.size();num++){
//				System.out.print(collist.get(num));
//				System.out.println("");
//			}
//		}
	     return  rowList;
	      
		//System.out.println(	DateUtil.changeTime(date, "x_y").getMonth());
		
	}
	public static void main(String[] args) {
		DateUtil.getcalendar(new Date(), null);
	//	System.out.println(DateUtil.forMM(new Date()));
//		DateUtil.getcalendar(new Date(),null);
//		DateUtil.getcalendar(DateUtil.changeTime(DateUtil.changeTime(DateUtil.changeTime(new Date(), "x_y"), "x_y"),"x_y") ,null);
	//	System.out.println(DateUtil.compare("2015-07-18 23:12:21","2015-07-18 22:12:21"));
		//lastToweek(new Date());
		//Date currentDate = DateUtil.text("2015-07-19");
		//System.out.println(DateUtil.only(new Date(), "1975-07-19"));
	//	List<Date> days=dateToWeek(currentDate);
		//List<Date> days= lastToweek(currentDate,true,true,true);  
		//for(Date d:days){
			//System.out.println(d);
	//	}
	}
}
