package com.qiniu.test;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class test {
	public static void main(String[] args) throws QiniuException {
		Auth a = Auth.create("sI4w4kYM6Goyr5DKn-akrvO8m7KUq12AuiVqxGSE", "61G-rzq88sZhg9QhhYTb2fkq-I3IKLxg91Ls1dQh");
		String url = "http://7xnl7l.com2.z0.glb.qiniucdn.com/中文测试";
		
		String urlSigned = a.privateDownloadUrl(url);
		
		System.out.println(urlSigned);
		
		
		
//		http://7xnl7l.com2.z0.glb.qiniucdn.com/中文测试.jpg?e=1446546481&token=sI4w4kYM6Goyr5DKn-akrvO8m7KUq12AuiVqxGSE:vzZTBjkp87cDD4idgk7x4F47Ym0=
//			http://7xnl7l.com2.z0.glb.qiniucdn.com/中文测试.jpg?e=1446546712&token=sI4w4kYM6Goyr5DKn-akrvO8m7KUq12AuiVqxGSE:mfvlYevroEtI6ke1zTXqjxcnkYU=
//		Auth a = Auth.create("sI4w4kYM6Goyr5DKn-akrvO8m7KUq12AuiVqxGSE", "61G-rzq88sZhg9QhhYTb2fkq-I3IKLxg91Ls1dQh");
//		String token = a.uploadToken("ruijiawang");
//		UploadManager uploadManager = new UploadManager();
//		System.out.println(token);
//		Response res = uploadManager.put("E:\\Img\\t01b6d3aa1aec6dd4b4.jpg", "test01", token);
//		if(res.isOK()){
//			System.out.println("success");
//		}else{
//			System.out.println("Failure");
//		}
	}
}
