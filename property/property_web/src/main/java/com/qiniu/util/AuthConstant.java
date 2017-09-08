package com.qiniu.util;

public class AuthConstant {
	private final static String ACCESS_KEY = "l9EYNSvtx6yNOg90eqfVJwQWH555i8F6ekgHw_y_";
	
	private final static String SECRET_KEY = "0hJJGcfdVjTLQ36ZHMAMxsJQgySpZR5OFEFLCNPE";
	
	public final static String QINIU_COM = "http://7xnl7l.com2.z0.glb.qiniucdn.com/";
	
	private final static String PROJECT_NAME = "labor";
	
	private static Auth auth;
	
	public static Auth getAuth(){
		if(auth == null){
			auth = Auth.create(AuthConstant.ACCESS_KEY, AuthConstant.SECRET_KEY);
		}
		return auth;
	}
	
	public static String getToken(){
		if(auth == null){
			auth = Auth.create(AuthConstant.ACCESS_KEY, AuthConstant.SECRET_KEY);
		}
		return	auth.uploadToken(AuthConstant.PROJECT_NAME);
	}
}
