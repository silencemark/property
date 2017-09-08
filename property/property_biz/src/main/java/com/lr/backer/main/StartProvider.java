package com.lr.backer.main;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartProvider {
	private static ClassPathXmlApplicationContext context;

	public static void main(String[] args) throws IOException {
		context = new ClassPathXmlApplicationContext("local/spring.xml");
		context.start();   
		System.out.println("ok");
		System.in.read(); // 按任意键退出
	}
}