package com.lr.backer.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 文件相关
 * 
 * @author 作者:pangkc
 * @version 创建时间：2011-12-29
 * 
 */
public final class FileUtil {
	private static String root_path_html = Constants.File_Path;

	private FileUtil() {
	}

	/**
	 * 保存文件
	 * 
	 * @param path
	 * @param stream
	 */
	public static void save(String path, String fileName, InputStream stream) {

		FileUtil.createNewFolder(path, false);

		OutputStream bos = null;
		try {
			bos = new FileOutputStream(path + fileName);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();

		} finally {
			try {
				stream.close();
				bos.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();

			}

		}
	}

	public static void save(String path, String fileName, String saveContent) {
		FileUtil.createNewFolder(path, false);
		File file = new File(path + fileName);
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, false), "UTF-8"));
			writer.write(saveContent);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public static String createFile(List obj, Map<String, String> root) {
		boolean rs = false;
		String path = root.get("file_path");
		String filename = root.get("file_name");
		String fullpath = root_path_html + path;
		FileUtil.creatDirs(fullpath);
		Writer writer = null;
		String filepath = fullpath + filename;
		File file = new File(filepath);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			String json_info = JsonComm.createJson(obj);
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, false), "UTF-8"));
			writer.write(json_info);
			writer.flush();
			writer.close();
			rs = true;
		} catch (IOException e) {
			filepath = "";
			e.printStackTrace();
		}
		return filepath;
	}

	public static String createFile(Map obj, Map<String, String> root) {
		boolean rs = false;
		String path = root.get("file_path");
		String filename = root.get("file_name");
		String fullpath = root_path_html + path;
		FileUtil.creatDirs(fullpath);
		Writer writer = null;
		String filepath = fullpath + filename;
		File file = new File(filepath);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			String json_info = JsonComm.createkjtpJson(obj);
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, false), "UTF-8"));
			writer.write(json_info);
			writer.flush();
			writer.close();
			rs = true;
		} catch (IOException e) {
			filepath = "";
			e.printStackTrace();
		}
		return filepath;
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 * @param completely
	 * @return
	 */
	public static boolean delete(final File file, final boolean completely) {

		if (file.isDirectory()) {
			for (File i : file.listFiles()) {
				if (!delete(i, true)) {
					return false;
				}
			}

		}

		if (completely && file.exists()) {
			return file.delete();
		}

		return true;
	}

	/**
	 * 指定文件名删除
	 * 
	 * @param file
	 * @param completely
	 * @return
	 */
	public static boolean delete(final String file, final boolean completely) {

		return delete(new File(file), completely);
	}

	/**
	 * 复制文件
	 * 
	 * @param inputfile
	 * @param outputfile
	 */
	public static void copyFile(String inputfile, String outputfile) {

		try {
			FileChannel sourceChannel = new FileInputStream(inputfile)
					.getChannel();
			FileChannel destinationChannel = new FileOutputStream(outputfile)
					.getChannel();
			sourceChannel.transferTo(0, sourceChannel.size(),
					destinationChannel);
			sourceChannel.close();
			destinationChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件是否存在
	 * 
	 * @param targetFileName
	 * @return
	 */
	public static boolean fileExistsChk(String targetFileName) {

		File targetFile = new File(targetFileName);
		if (targetFile.isFile()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param folderPath
	 * @param isForced
	 *            true先删后建
	 */
	public static void createNewFolder(String folderPath, boolean isForced) {

		File resultFolder = new File(folderPath);
		if (resultFolder.exists()) {

			if (isForced) {
				delete(resultFolder, true);
				resultFolder.mkdir();
			}
		} else {
			resultFolder.mkdirs();
		}

	}

	/**
	 * 获取文件的内容
	 */
	public static String getFileContent(String file, String encode) {
		if (!FileUtil.fileExistsChk(file)) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		BufferedReader bf = null;
		String line = null;
		try {
			bf = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), encode));
			line = bf.readLine();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		while (line != null) {
			result.append(line).append("\n");
			try {
				line = bf.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	public static boolean creatDirs(String path) {
		File aFile = new File(path);
		if (!aFile.exists()) {
			return aFile.mkdirs();
		} else {
			return true;
		}
	}

	public static FileParam createJsonPath(Object obj, Map<String, String> root) {
		FileParam fp = new FileParam();
		boolean rs = false;
		String path = root.get("file_path");
		String filename = root.get("file_name");
		String fullpath = root_path_html + path;
		FileUtil.creatDirs(fullpath);
		Writer writer = null;
		String filepath = fullpath + filename;

		fp.setFullpath(filepath);
		File file = new File(filepath);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			String json_info = JsonComm.createJsonOBJ(obj);
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, false), "UTF-8"));
			writer.write(json_info);
			writer.flush();
			writer.close();
			rs = true;
			fp.setIst(rs);
		} catch (IOException e) {
			rs = false;
			fp.setIst(rs);
			e.printStackTrace();
		}
		return fp;
	}

	public static void copyPicture(String sourceFile, String targetFile) {

		try {
			String path = targetFile.substring(0, targetFile.lastIndexOf("/"));
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			File inputFile = new File(sourceFile);// 定义读取源文件
			File outputFile = new File(targetFile);// 定义拷贝目标文件
			// 定义输入文件流
			FileInputStream in = new FileInputStream(inputFile);
			// 将文件输入流构造到缓存
			BufferedInputStream bin = new BufferedInputStream(in);
			// 定义输出文件流
			FileOutputStream out = new FileOutputStream(outputFile);
			// 将输出文件流构造到缓存
			BufferedOutputStream bout = new BufferedOutputStream(out);
			int c;
			// 循环读取文件和写入文件
			while ((c = bin.read()) != -1)
				bout.write(c);
			// 关闭输入输出流，释放资源
			bin.close();
			bout.close();
		} catch (IOException e) {
			// 文件操作，捕获IO异常。
		}
	}

	public static List<String> getImage(String content) {
		List<String> lst = new ArrayList();
		String sReg = "(*.png)";
		Pattern pattern = Pattern.compile(sReg);
		Matcher matcher = pattern.matcher(content);
		String sImgName;
		while (matcher.find()) {
			sImgName = matcher.group();
			lst.add(matcher.group());
		}
		return lst;
	}

	public static List<String> getImgStr(String htmlStr) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
		List<String> pics = new ArrayList<String>();

		String regEx_img = "<img.*src=(.*?)[^>]*?>"; // 图片链接地址
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); // 匹配src
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}

	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析

		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	public static List<Map<String, Object>> getListByUrl(String url) {
		try {
			// 通过HTTP获取JSON数据
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2List(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> getMapByUrl(String url) {
		try {

			// 通过HTTP获取JSON数据
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			System.out.println(sb.toString());
			return parseJSON2Map(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean exist(String path) {
		URL url;
		try {
			url = new URL(path);
			HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
			 String message = urlcon.getHeaderField(0);
			// 文件存在‘HTTP/1.1 200 OK’ 文件不存在 ‘HTTP/1.1 404 Not Found’
			Long TotalSize = Long.parseLong(urlcon
					.getHeaderField("Content-Length"));
			if (TotalSize > 0) {
				return true;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getMapValue(Map<String, Object> map,String key){
		if(null == map || map.isEmpty() || !map.containsKey(key)){
			return "" ;
		}
		Object valueObject = map.get(key) ;
		return null != valueObject ? valueObject.toString() : "" ;
	}

}
