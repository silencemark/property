package com.lr.backer.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImgCompress {

    private Image img;
    private int width;
    private int height;
    private static final int LIMIT_WIDTH = 400 ;

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {
        String pathString = "D:\\1.jpg" ;

        System.out.println(pathString.substring(0,pathString.lastIndexOf("."))+".png");


//        System.out.println("开始：" + new Date().toLocaleString());
        ImgCompress imgCom = new ImgCompress("D:\\1.jpg");
        imgCom.resize(800, 900,"D:\\xxxxx.jpg");
//        System.out.println("结束：" + new Date().toLocaleString());
    }
    /**
     * 构造函数
     */
    public ImgCompress(String fileName) throws IOException {
        File file = new File(fileName);// 读入文件
        img = ImageIO.read(file);      // 构造Image对象
        width = img.getWidth(null);    // 得到源图宽
        height = img.getHeight(null);  // 得到源图长
    }
    public ImgCompress(File sourceFile) throws IOException {
        img = ImageIO.read(sourceFile);      // 构造Image对象
        width = img.getWidth(null);    // 得到源图宽
        height = img.getHeight(null);  // 得到源图长
    }
    public ImgCompress(InputStream inputStream) throws IOException {
        img = ImageIO.read(inputStream);      // 构造Image对象
        width = img.getWidth(null);    // 得到源图宽
        height = img.getHeight(null);  // 得到源图长
    }

    /**
     * 按照宽度还是高度进行压缩
     * @param w int 最大宽度
     * @param h int 最大高度
     */
    public void resizeFix(int w, int h) throws IOException {
        if(width>LIMIT_WIDTH){
            w = 400 ;
            h = w * height / width ;
        }else {
            w = width ;
            h = height ;
        }
        resize(w, h);
    }
    public void resizeFix(int w, int h,String targetPath) throws IOException {
        if(width>LIMIT_WIDTH){
            w = 400 ;
            h = w * height / width ;
        }else {
            w = width ;
            h = height ;
        }
        resize(w, h,targetPath);
    }
    /**
     * 以宽度为基准，等比例放缩图片
     * @param w int 新宽度
     */
    public void resizeByWidth(int w) throws IOException {
        int h = (int) (height * w / width);
        resize(w, h);
    }
    public void resizeByWidth(int w,String targetPath) throws IOException {
        int h = (int) (height * w / width);
        resize(w, h,targetPath);
    }
    /**
     * 以高度为基准，等比例缩放图片
     * @param h int 新高度
     */
    public void resizeByHeight(int h) throws IOException {
        int w = (int) (width * h / height);
        resize(w, h);
    }
    public void resizeByHeight(int h,String targetPath) throws IOException {
        int w = (int) (width * h / height);
        resize(w, h,targetPath);
    }
    private String parsePNGImage(String sourcePath){
        return sourcePath.substring(0,sourcePath.lastIndexOf("."))+".png";
    }
    /**
     * 强制压缩/放大图片到固定的大小
     * @param w int 新宽度
     * @param h int 新高度
     */
    public void resize(int w, int h) throws IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
        File destFile = new File(parsePNGImage("D:\\456.png"));
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image); // JPEG编码
        out.close();
    }
    public void resize(int w, int h,String targetPath) throws IOException {
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );
        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
//			File destFile = new File(targetPath);
        FileOutputStream out = new FileOutputStream(targetPath); // 输出到文件流
        // 可以正常实现bmp、png、gif转jpg
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(image); // JPEG编码
        out.close();
    }


}
