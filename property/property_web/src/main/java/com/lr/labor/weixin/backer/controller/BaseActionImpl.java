package com.lr.labor.weixin.backer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.lr.backer.util.Constants;
import com.lr.backer.util.ExportUtil;
import com.lr.backer.util.FileDeal;
import com.lr.backer.util.PageHelper;
import com.lr.labor.weixin.util.StringUtils;

public class BaseActionImpl {

	private static final Logger LOGGER = Logger.getLogger(BaseActionImpl.class);

	// 返回结果给客户端
	protected String result;

	protected int currentPage = 1;

	private Map errorMsgMap;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	protected void initPage(Map<String, Object> paramMap, PageHelper pageHelper) {
		int rowPage = pageHelper.getRowPage(); // 每页显示的记录
		currentPage = pageHelper.getCurrentPage();
		if (currentPage < 1) {
			currentPage = 1;
		}
		if (rowPage < 1)
			rowPage = 1;
		int startnum = (currentPage - 1) * rowPage; // 分页查询开始的序号
		int rownum = rowPage; // 每页显示的条数
		paramMap.put("startnum", startnum);
		paramMap.put("rownum", rownum);
	
	}

	public boolean hasPassed(Map outputMap) {
		if (outputMap.containsKey("status")
				&& "0".equals(outputMap.get("status"))) {
			return false;
		} else
			return true;
	}

	public Map addErrorMsg(String message) {
		if (errorMsgMap == null)
			errorMsgMap = new HashMap();

		errorMsgMap.put("status", "0");
		errorMsgMap.put("msg", message);
		return errorMsgMap;
	}

	public Map checkRequestParam(String json) {
		if (json == null || "".equals(json)) {
			return addErrorMsg("参数不能为空");
		} else
			return new HashMap();
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}




	public String getRequestParameterValue(HttpServletRequest request ,String key) {
		if (!StringUtils.isNullOrEmpty(key)) {
			return null != request.getParameter(key) ? request
					.getParameter(key).toString() : "";
		}
		return "";
	}

	public String parseJsonData(Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		JSONObject jsonObject = JSONObject.fromObject(map);
		return jsonObject.toString();
	}

	/**
	 * @return Map<String,Object>
	 * @function:将json字符串转成Map
	 * @datetime:2014-10-30 下午08:51:15
	 * @Author: robin
	 * @param: @param json
	 */
	public Map<String, Object> parseJsonToMap(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Iterator<String> keyIterator = jsonObject.keys();
		Map<String, Object> map = new HashMap<String, Object>();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			map.put(key, jsonObject.get(key));
		}
		return map;
	}

	/**
	 * @return boolean 存在主键对应的列，则表示插入
	 * @function:Map中是否存在主键值
	 * @datetime:2014-11-9 上午11:38:59
	 * @Author: robin
	 * @param: @param data 需要保存的数据
	 * @param: @param pk 主键列
	 */
	protected boolean hasSavePK(Map<String, Object> data, String pk) {
		return data.containsKey(pk) && null != data.get(pk)
				&& !"".equals(data.get(pk).toString());
	}

	/**
	 * @return String
	 * @function:下载
	 * @datetime:2015-1-26 下午06:33:31
	 * @Author: robin
	 * @param: @param fileName
	 * @param: @param filePath
	 */
	@SuppressWarnings("deprecation")
	protected String download(HttpServletRequest request,HttpServletResponse response ,String fileName, String filePath) {

		String extension = FileDeal.getExtention(filePath);
		String path = request.getRealPath("/");
		ServletOutputStream out;
		try {
			// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
			response.setContentType("APPLICATION/OCTET-STREAM");
			// getResponse().setContentType("multipart/form-data");
			response.setHeader(
					"Content-Disposition",
					"attachment;fileName="
							+ URLEncoder.encode(fileName, "UTF-8") + extension);

			File file = new File(path + filePath);

			FileInputStream inputStream = new FileInputStream(file);
			out = response.getOutputStream();
			int BUFFER_SIZE = 16 * 1024;
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = inputStream.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			/*
			 * out = getResponse().getOutputStream(); int b = 0; byte[] buffer =
			 * new byte[512]; while (b != -1){ b = inputStream.read(buffer);
			 * //4.写到输出流(out)中 out.write(buffer,0,b); }
			 */
			inputStream.close();
			out.close();
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 导出excel
	 * 
	 * @param name
	 * @param exportDataList
	 * @return
	 */
	public String exportExcel(String name,
			List<LinkedHashMap<String, Object>> exportDataList,HttpServletResponse response) {

		String excelName = name + ".xls";
		// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
		response.setContentType("APPLICATION/OCTET-STREAM");
		HSSFWorkbook workbook = null;
		OutputStream os = null;
		try {
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ URLEncoder.encode(excelName, "UTF-8"));
			os = response.getOutputStream();
			workbook = ExportUtil.exportSimpleSheetWorkbook(name,
					exportDataList);
			workbook.write(os);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			LOGGER.error(Constants.LOGGER_EXCEPTION, e);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(Constants.LOGGER_EXCEPTION, e);
		} finally {
			// 写入文件
			workbook = null;
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					LOGGER.error(Constants.LOGGER_EXCEPTION, e);
				}
			}
		}
		return null;

	}

	public Map<String, Object> initPageV2(Map<String, Object> paramMap,
			PageHelper pageHelper) {
		int rowPage = pageHelper.getRowPage(); // 每页显示的记录
		int currentPage = pageHelper.getCurrentPage();
		if (currentPage < 1)
			currentPage = 1;
		if (rowPage < 1)
			rowPage = 1;
		int startnum = (currentPage - 1) * rowPage; // 分页查询开始的序号
		int rownum = rowPage; // 每页显示的条数
		paramMap.put("startnum", startnum);
		paramMap.put("rownum", rownum);
		return paramMap;
	}

}
