package com.hoheng.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

	/** 
	 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！ 
	 *  
	 * @author leno 
	 * @version v1.0 
	 * @param <T> 
	 *            应用泛型，代表任意一个符合javabean风格的类 
	 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx() 
	 *            byte[]表jpg格式的图片数据 
	 */  
	public  class ExportExcelPoi<T>  
	{  
	    public void exportExcel(Collection<T> dataset, OutputStream out)  
	    {  
	        exportExcel("测试POI导出EXCEL文档", null, dataset, out, "yyyy-MM-dd");  
	    }  
	  
	    public void exportExcel(String title,String[] headers, Collection<T> dataset,  
	            OutputStream out)  
	    {  
	        exportExcel(title, headers, dataset, out, "yyyy-MM-dd");  
	    }  
	  
	    public void exportExcel(String[] headers, Collection<T> dataset,  
	            OutputStream out, String pattern)  
	    {  
	        exportExcel("测试POI导出EXCEL文档", headers, dataset, out, pattern);  
	    }  
	  
	    /** 
	     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上 
	     *  
	     * @param title 
	     *            表格标题名 
	     * @param headers 
	     *            表格属性列名数组 
	     * @param dataset 
	     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 
	     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据) 
	     * @param out 
	     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中 
	     * @param pattern 
	     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd" 
	     */  
	    @SuppressWarnings({ "unchecked", "deprecation" })  
	    public void exportExcel(String title, String[] headers,  
	            Collection<T> dataset, OutputStream out, String pattern)  
	    {  
	        // 声明一个工作薄  
	        HSSFWorkbook workbook = new HSSFWorkbook();  
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.createSheet(title); 
	        HSSFRow rowm = sheet.createRow(0);  
            HSSFCell cellTiltle = rowm.createCell(0); 
	        
          //sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】  
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);//获取列头样式对象  
            HSSFCellStyle style = this.getStyle(workbook);                  //单元格样式对象  
              
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (headers.length-1)));    
            cellTiltle.setCellStyle(columnTopStyle);  
            cellTiltle.setCellValue(title);  
            
	        // 设置表格默认列宽度为15个字节  
	        sheet.setDefaultColumnWidth((short) 15);  
	        
	        // 产生表格标题行  
	        HSSFRow row = sheet.createRow(2);  
	        for (short i = 0; i < headers.length; i++)  
	        {  
	            HSSFCell cell = row.createCell(i);  
	            cell.setCellStyle(columnTopStyle);
	            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
	            cell.setCellValue(text);  
	        }
	  
	        List<Map<String, Object>> it=(List<Map<String, Object>>) dataset;
	        int index = 2;
	        for(Map<String, Object> data:it){
	        	index++;
	        	row = sheet.createRow(index);
	        	Iterator<String> iterator = data.keySet().iterator();
	        	int i=0;
	    		while (iterator.hasNext()) {
	    			HSSFCell cell = row.createCell(i);  
	                cell.setCellStyle(style);  
	    			Object key = iterator.next();
	    			Object value=data.get(key);
	    			String textValue = null;
	    			if(!value.equals("")){
	    				textValue = value.toString();
	    			}else{
	    				textValue="";
	    			}
	    			if (textValue != null)
                    {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
                        Matcher matcher = p.matcher(textValue);  
                        if (matcher.matches())  
                        {  
                            // 是数字当作double处理  
                            cell.setCellValue(Double.parseDouble(textValue));  
                        }  
                        else  
                        {  
                            HSSFRichTextString richString = new HSSFRichTextString(  
                                    textValue);  
                            cell.setCellValue(richString);
                        }
                    }
	    			i++;
	    		}
	        } 
	        try
	        {  
	            workbook.write(out);  
	        }  
	        catch (IOException e)  
	        {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    public static void main(String[] args)  
	    {  
	        // 测试学生  
	        ExportExcelPoi<Map<String, Object>> ex = new ExportExcelPoi<Map<String, Object>>();  
	        String[] headers =  
	        { "学号", "姓名", "年龄", "性别", "出生日期" };  
	        List<Map<String, Object>> dataset = new ArrayList<Map<String, Object>>();
	        TreeMap<String, Object> studentmap=new TreeMap<String, Object>();
	        studentmap.put("a", 10000001);
	        studentmap.put("b", "张三");
	        studentmap.put("c", "20");
	        studentmap.put("d",true);
	        studentmap.put("e", new Date());
	        dataset.add(studentmap);
//            List<Student> dataset = new ArrayList<Student>();  
//            dataset.add(new Student(10000001, "张三", 20, true, new Date()));  
//            dataset.add(new Student(20000002, "李四", 24, false, new Date()));  
//            dataset.add(new Student(30000003, "王五", 22, true, new Date()));  
	        // 测试图书  
	        ExportExcelPoi<Map<String, Object>> ex2 = new ExportExcelPoi<Map<String, Object>>();  
	        String[] headers2 =  
	        { ".", "图书名称", "图书作者", "图书价格", "图书ISBN", "图书出版社", "封面图片" };  
	        List<Map<String, Object>> dataset2 = new ArrayList<Map<String, Object>>();
	        try  
	        {
	        	TreeMap<String, Object> bookmap=new TreeMap<String, Object>();
	            bookmap.put("a", 1);
	            bookmap.put("b","java编程思想");
	            bookmap.put("c","leno");
	            bookmap.put("d","300.33f");
	            bookmap.put("e","1234567");
	            bookmap.put("f","清华出版社");
	            bookmap.put("g","ceshi");
	            dataset2.add(bookmap);
	            OutputStream out = new FileOutputStream("E://a.xls");  
	            OutputStream out2 = new FileOutputStream("E://b.xls");  
	            ex.exportExcel(headers, dataset, out,"");  
	            ex2.exportExcel(headers2, dataset2, out2,"");  
	            out.close();  
	            System.out.println("excel导出成功！");  
	        }  
	        catch (FileNotFoundException e)  
	        {
	            e.printStackTrace();  
	        }  
	        catch (IOException e)  
	        {  
	            e.printStackTrace();  
	        }  
	    }
	    /*  
	     * 列头单元格样式 
	     */      
	    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {  
	          
	          // 设置字体  
	          HSSFFont font = workbook.createFont();  
	          //设置字体大小  
	          font.setFontHeightInPoints((short)11);  
	          //字体加粗  
	          font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	          //设置字体名字   
	          font.setFontName("Courier New");  
	          //设置样式;   
	          HSSFCellStyle style = workbook.createCellStyle();  
	          //设置底边框;   
	          style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	          //设置底边框颜色;    
	          style.setBottomBorderColor(HSSFColor.BLACK.index);  
	          //设置左边框;     
	          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	          //设置左边框颜色;   
	          style.setLeftBorderColor(HSSFColor.BLACK.index);  
	          //设置右边框;   
	          style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	          //设置右边框颜色;   
	          style.setRightBorderColor(HSSFColor.BLACK.index);  
	          //设置顶边框;   
	          style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
	          //设置顶边框颜色;    
	          style.setTopBorderColor(HSSFColor.BLACK.index);  
	          //在样式用应用设置的字体;    
	          style.setFont(font);  
	          //设置自动换行;   
	          style.setWrapText(false);  
	          //设置水平对齐的样式为居中对齐;    
	          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	          //设置垂直对齐的样式为居中对齐;   
	          style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	            
	          return style;  
	            
	    }  
	      
	    /*   
	     * 列数据信息单元格样式 
	     */    
	    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {  
	          // 设置字体  
	          HSSFFont font = workbook.createFont();  
	          //设置字体大小  
	          //font.setFontHeightInPoints((short)10);  
	          //字体加粗  
	          //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
	          //设置字体名字   
	          font.setFontName("Courier New");  
	          //设置样式;   
	          HSSFCellStyle style = workbook.createCellStyle();  
	          //设置底边框;   
	          style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	          //设置底边框颜色;    
	          style.setBottomBorderColor(HSSFColor.BLACK.index);  
	          //设置左边框;     
	          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	          //设置左边框颜色;   
	          style.setLeftBorderColor(HSSFColor.BLACK.index);  
	          //设置右边框;   
	          style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	          //设置右边框颜色;   
	          style.setRightBorderColor(HSSFColor.BLACK.index);  
	          //设置顶边框;   
	          style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
	          //设置顶边框颜色;    
	          style.setTopBorderColor(HSSFColor.BLACK.index);  
	          //在样式用应用设置的字体;    
	          style.setFont(font);  
	          //设置自动换行;   
	          style.setWrapText(false);  
	          //设置水平对齐的样式为居中对齐;    
	          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	          //设置垂直对齐的样式为居中对齐;   
	          style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	           
	          return style;  
	      
	    }  
	    
	    
	    /**
	     *下载excel 
	     * @param path
	     * @param response
	     * @throws IOException 
	     */
	    public ResponseEntity<byte[]>  download(String filename, HttpServletResponse response,HttpServletRequest request) throws IOException {  
	         
	            // 取得文件名。  
	            String rspName=request.getSession().getServletContext().getRealPath("/upload/");
	    		HttpHeaders headers = new HttpHeaders();  
	    		rspName = rspName+"/"+filename;  
	    		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
	    		try {
					headers.setContentDispositionFormData("attachment", new String((filename).getBytes("gb2312"), "iso-8859-1"));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
	    		File file = new File(rspName);  
	             
	    		byte[] bytes = FileUtils.readFileToByteArray(file);  
				 
	    		try {  
	    		    if (file.exists()){
	    		        file.delete();  
	    		    }
	    		} catch (Exception e){
	    		    e.printStackTrace();  
	    		}  
	        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
	    }  
	    
	}