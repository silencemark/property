package com.lr.backer.util;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @ClassName:ExportUtil.java
 * @ClassPath:com.hypers.core.util
 * @Desciption:导出工具类
 * @Author: Robin
 * @Date: 2014年8月13日 下午3:52:32
 * 
 */
public class ExportUtil {
	
	private static final Logger LOGGER = Logger.getLogger(ExportUtil.class);
	
	/**
	 * @function:创建sheet对应的表头
	 * @datetime:2014年8月13日 下午7:19:05
	 * @Author: Robin
	 * @param: @param row
	 */
	@SuppressWarnings("deprecation")
	private static void createSheetTitle(HSSFSheet sheet,int headRow, HSSFCellStyle style,LinkedHashMap<String, Object> headMap){
		HSSFRow row = sheet.createRow((int) headRow-1);
		Iterator<Map.Entry<String, Object>> keyIterator = headMap.entrySet().iterator();
		int i=0;
		while (keyIterator.hasNext()) {
			Map.Entry<String, Object> mapEntry = (Map.Entry<String, Object>)keyIterator.next() ;
			String value = null!=mapEntry.getValue()?mapEntry.getValue().toString():"";
			HSSFCell cell = row.createCell((short) i);
			cell.setCellValue(value);
			cell.setCellStyle(style);
			i++;
		}
		//=======sheet = 头部保存成功
	}
	
	@SuppressWarnings("deprecation")
	private static void createSheetContent(HSSFSheet sheet,int headRow,List<LinkedHashMap<String, Object>> dataList){
		for(int j=headRow,len=dataList.size();j<len;j++){
			Map<String, Object> dataMap = dataList.get(j);
			Iterator<Map.Entry<String, Object>> dataIterator = dataMap.entrySet().iterator();
			HSSFRow row = sheet.createRow((int) j);
			int z=0;
			while (dataIterator.hasNext()) {
				Map.Entry<String, Object> mapEntry = (Map.Entry<String, Object>)dataIterator.next() ;
				String dataValue = null!=mapEntry.getValue() ? mapEntry.getValue().toString() : "";
				// 第四步，创建单元格，并设置值
				row.createCell((short) z).setCellValue(dataValue);
				z++;
			}
		}
	}
	
	/**
	 * @function:导出生成excel(生成单Sheet的excel)
	 * @datetime:2014年8月13日 下午4:06:02
	 * @Author: Robin
	 * @param: @param dataList {{}}
	 */
	public static void exportSimpleSheet(String sheetName,
			List<LinkedHashMap<String, Object>> dataList){
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = exportSimpleSheetWorkbook(sheetName, dataList);
			
			// 第六步，将文件存到指定位置
			try {
				FileOutputStream fout = new FileOutputStream("D:/students.xls");
				wb.write(fout);
				System.out.println("导出excel成功！");
				fout.close();
			} catch (Exception e) {
				LOGGER.error(Constants.LOGGER_EXCEPTION,e);
			}
	}
	
	public static HSSFWorkbook exportSimpleSheetWorkbook(String sheetName,
			List<LinkedHashMap<String, Object>> dataList){
			// 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet(sheetName);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

			//List<LinkedHashMap<String,Object>> 中的第一项表示表格的头部
			if(null!=dataList && dataList.size()>=1){
				//默认List中第一项是描述的excel的头部
				LinkedHashMap<String, Object> headMap = dataList.get(0);
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				int headRow = 1 ;// 定义列头的行数
				
				//1. 创建表头
				createSheetTitle(sheet, headRow,style, headMap);
				//2. 生成数据
				createSheetContent(sheet, headRow,dataList);
			}
			// 第六步，将文件存到指定位置
			return wb ;
	}
	
	private static LinkedHashMap<String, Object> getMap(String id,String name,String date){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", id);
		map.put("name", name);
		map.put("date", date);
//		"序号", "姓名", "日期"
		return map  ;
	}
	/**
	 * @功能：手工构建一个简单格式的Excel
	 */
	private static List<LinkedHashMap<String, Object>> getStudent() throws Exception {
		List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		list.add(getMap("序号", "姓名", "日期"));
		list.add(getMap("1", "张三", "1997-03-12"));
		list.add(getMap("2", "李四", "1996-08-12"));
		list.add(getMap("3", "王五", "1985-11-12"));
		return list;
	}

	public static void main(String[] args) throws Exception {
		List<LinkedHashMap<String, Object>> dataList = getStudent();
		List<String> indexList = new ArrayList<String>();
		indexList.add("序号");
		indexList.add("姓名");
		indexList.add("日期");
		exportSimpleSheet("学生表", dataList);
		
	}

}