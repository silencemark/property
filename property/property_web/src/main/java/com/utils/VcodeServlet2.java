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

public final class VcodeServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public VcodeServlet2() {
		super();
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0); 
		int width = 114, height = 40;
		String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int length = base.length();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
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
		String sRand = "";
		int charLenth = 4;
		charLenth = charLenth==0?1:charLenth;
		for (int i = 0; i < charLenth; i++) {
			int start = random.nextInt(length);
			String rand = base.substring(start, start + 1);
			sRand += rand;
			g.setColor(getRandColor(random, 10, 150));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypesLength)],
					Font.BOLD, 30 + random.nextInt(3)));
			g.drawString(rand, 24 * i + 10 + random.nextInt(8), 30);
		}
		request.getSession().setAttribute("vcode2", sRand);
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void ajaxCode(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
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
