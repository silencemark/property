package com.hoheng.util;

import java.util.UUID;

public class StringUtil {

	//获取uuid
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
  public static void main(String[] args) {
	System.out.println(StringUtil.getUUID());
}
}
