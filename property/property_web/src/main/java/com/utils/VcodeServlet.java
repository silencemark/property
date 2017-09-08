package com.utils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class VcodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public VcodeServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		
		
		Random random = new Random();

		// 定义数组存放加减乘除四个运算符
		char[] arr = { '+', '-', 'x' };

		// 生成10以内的随机整数num1
		int num1 = random.nextInt(10);

		// 生成一个0-4之间的随机整数operate
		int operate = random.nextInt(3);

		// 生成10以内的随机整数num1
		int num2 = random.nextInt(10);

		// 运算结果
		int result = 0;

		// 假定position值0/1/2分别代表”+”,”-“,”*”计算前面操作数的运算结果
		switch (operate) {
		case 0:
			result = num1 + num2;
			break;
		case 1:
			if(num1==0){
				num2 = 0;
			}else{
				num2 = random.nextInt(num1);
			}
			
			
			result = num1 - num2;
			break;
		case 2:
			result = num1 * num2;
			break;
		}

		
		int width = 75, height = 30;

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		g.setColor(getRandColor(random, 200, 250));
		g.fillRect(0, 0, width, height);
		String[] fontTypes = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53",
				"\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };
		int fontTypesLength = fontTypes.length;
		g.setColor(getRandColor(random, 160, 200));
		g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		for (int i = 0; i < 7; i++) {
			g.drawString("*********************************************", 0,
					5 * (i + 2));
		}
		String sRand = String.format("%d%s%d=?", num1,arr[operate],num2);
		int charLenth = sRand.length();
		for (int i = 0; i < charLenth; i++) {
			String rand = sRand.substring(i, i + 1);
			g.setColor(getRandColor(random, 10, 150));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypesLength)],
					Font.BOLD, 17 + random.nextInt(3)));
			g.drawString(rand, 15 * i , 22);
		}
		request.getSession().setAttribute("vcode", result);
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	Color getRandColor(Random random, int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
}
