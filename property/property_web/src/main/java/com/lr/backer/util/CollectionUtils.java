package com.lr.backer.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class CollectionUtils {
	
private static final Logger log = Logger.getLogger(CollectionUtils.class);
	
	private CollectionUtils() {
		throw new Error("Do Not Create This Object");
	}
	
	/**
	 * 空判断
	 * @param collection
	 * @return 空返回true
	 */
	public static boolean isEmpty (Collection<?> collection){
		return org.apache.commons.collections.CollectionUtils.isEmpty(collection);
	}
	
	/**
	 * 非空判断
	 * @param collection
	 * @return 非空返回true
	 */
	public static boolean isNotEmpty (Collection<?> collection){
		return org.apache.commons.collections.CollectionUtils.isNotEmpty(collection);
	}
	
	/**
	 * 获取当前集合内Map数据的拼凑
	 * @param collection
	 * @return List内集合数据拼凑
	 */
	public static String getListToString(Collection<Map<String,String>> collection){
		StringBuffer strReturn = new StringBuffer();
		for (Map<String,String> map : collection){
			for (String value : map.values()){
				strReturn.append(value);
			}
		}
		return strReturn.toString();
	}
	
	/**
	 * 是否包含当前对象
	 * @param col 待检测数据
	 * @param o 需要查找数据
	 * @return 包含则返回true,空与不包含则返回false
	 */
	public static boolean isContains(Collection<?> col,Object o){
		
		boolean retContains = false;
		if (!isEmpty(col)){
			retContains = col.contains(o);
		}
		
		return retContains;
	}
	
	/** 
	 *
	 * @return
	 * @author Dong   
	 * @date 2014年10月16日 
	 * @Description
	 * 返回空List
	 * @version V0.1 
	 * 
	 */
	public static <T> List<T> emptyList(){
		return Collections.emptyList();
	}
	
	
	
	
	private static final Integer DEFAULTLIST = 500;

	/**
	 * 拆分集合
	 * 
	 * @param <T>
	 * @param resList
	 *            要拆分的集合
	 * @param count
	 *            每个集合的元素个数
	 * @return 返回拆分后的各个集合
	 */
	public static <T> List<List<T>> split(List<T> resList, int count) {

		if (resList == null || count < 1)
			return null;
		List<List<T>> ret = new ArrayList<List<T>>();
		int size = resList.size();
		if (size <= count) { // 数据量不足count指定的大小
			ret.add(resList);
		} else {
			int pre = size / count;
			int last = size % count;
			// 前面pre个集合，每个大小都是count个元素
			for (int i = 0; i < pre; i++) {
				List<T> itemList = new ArrayList<T>();
				for (int j = 0; j < count; j++) {
					itemList.add(resList.get(i * count + j));
				}
				ret.add(itemList);
			}
			// last的进行处理
			if (last > 0) {
				List<T> itemList = new ArrayList<T>();
				for (int i = 0; i < last; i++) {
					itemList.add(resList.get(pre * count + i));
				}
				ret.add(itemList);
			}
		}
		return ret;

	}

	/**
	 * 拆分集合
	 * 
	 * @param <T>
	 * @param resList
	 *            要拆分的集合
	 * @return 返回拆分后的各个集合
	 */
	public static <T> List<List<T>> split(List<T> resList) {
		int count = DEFAULTLIST;
		if (resList == null || count < 1)
			return null;
		List<List<T>> ret = new ArrayList<List<T>>();
		int size = resList.size();
		if (size <= count) { // 数据量不足count指定的大小
			ret.add(resList);
		} else {
			int pre = size / count;
			int last = size % count;
			// 前面pre个集合，每个大小都是count个元素
			for (int i = 0; i < pre; i++) {
				List<T> itemList = new ArrayList<T>();
				for (int j = 0; j < count; j++) {
					itemList.add(resList.get(i * count + j));
				}
				ret.add(itemList);
			}
			// last的进行处理
			if (last > 0) {
				List<T> itemList = new ArrayList<T>();
				for (int i = 0; i < last; i++) {
					itemList.add(resList.get(pre * count + i));
				}
				ret.add(itemList);
			}
		}
		return ret;

	}
	/**
	 * 
	 *
	 * @param stringList
	 * @return
	 * @author Sunyawei   
	 * @date 2015年7月9日 
	 * @Description 将List<String>转换成成String[] 
	 * @version V0.1 
	 *
	 */
	public static String[] turnListToStringArray(List<String> stringList){
		int length=stringList.size();
		String[] str=new String[length];
		for(int i=0;i<length;i++){
			str[i]=stringList.get(i);
		}
		return str;
	}

}
