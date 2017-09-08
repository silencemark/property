package com.lr.backer.util;

import java.io.ByteArrayInputStream;  
import java.io.File;  
import java.io.InputStream;  
import java.util.HashMap;  
import java.util.LinkedList;  
import java.util.List;  
import java.util.Map;  
  

import net.sf.json.JSONObject;  
  

import org.jdom.Document;  
import org.jdom.Element;  
import org.jdom.input.SAXBuilder;  
  
public class Xml2JsonUtil {  
    /** 
     * 转换一个xml格式的字符串到json格式 
     *  
     * @param xml 
     *            xml格式的字符串 
     * @return 成功返回json 格式的字符串;失败反回null 
     */  
    @SuppressWarnings("unchecked")  
    public static  String xml2JSON(String xml) {  
        JSONObject obj = new JSONObject();  
        try {  
            InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));  
            SAXBuilder sb = new SAXBuilder();  
            Document doc = sb.build(is);  
            Element root = doc.getRootElement();  
            obj.put(root.getName(), iterateElement(root));  
            return obj.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 转换一个xml格式的字符串到json格式 
     *  
     * @param file 
     *            java.io.File实例是一个有效的xml文件 
     * @return 成功反回json 格式的字符串;失败反回null 
     */  
    @SuppressWarnings("unchecked")  
    public static String xml2JSON(File file) {  
        JSONObject obj = new JSONObject();  
        try {  
            SAXBuilder sb = new SAXBuilder();  
            Document doc = sb.build(file);  
            Element root = doc.getRootElement();  
            obj.put(root.getName(), iterateElement(root));  
            return obj.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 一个迭代方法 
     *  
     * @param element 
     *            : org.jdom.Element 
     * @return java.util.Map 实例 
     */  
    @SuppressWarnings("unchecked")  
    private static Map  iterateElement(Element element) {  
        List jiedian = element.getChildren();  
        Element et = null;  
        Map obj = new HashMap();  
        List list = null;  
        for (int i = 0; i < jiedian.size(); i++) {  
            list = new LinkedList();  
            et = (Element) jiedian.get(i);  
            if (et.getTextTrim().equals("")) {  
                if (et.getChildren().size() == 0)  
                    continue;  
                if (obj.containsKey(et.getName())) {  
                    list = (List) obj.get(et.getName());  
                }  
                list.add(iterateElement(et));  
                obj.put(et.getName(), list);  
            } else {  
                if (obj.containsKey(et.getName())) {  
                    list = (List) obj.get(et.getName());  
                }  
                list.add(et.getTextTrim());  
                obj.put(et.getName(), list);  
            }  
        }  
        return obj;  
    }  
    /**
     * 判断是否成功
     * @author silence
     * @param args
     */
    public static boolean isSuccessByxml(String Xml) {  
        String json=Xml2JsonUtil.xml2JSON(Xml);  
        JSONObject hahah=JSONObject.fromObject(json);
        Map<String, Object> xml=(Map<String, Object>) hahah.get("xml");
        System.out.println(xml.get("result_code"));
        boolean flag=true;
        if("[\"FAIL\"]".equals(String.valueOf(xml.get("result_code")))){
        	flag=false;
        }
        return flag;
    }
    
    // 测试  
    public static void main(String[] args) {  
        String json=Xml2JsonUtil.xml2JSON("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[参数错误:act_name字段必填,并且少于32个字符.]]></return_msg><result_code><![CDATA[FAIL]]></result_code><err_code><![CDATA[PARAM_ERROR]]></err_code><err_code_des><![CDATA[参数错误:act_name字段必填,并且少于32个字符.]]></err_code_des><mch_billno><![CDATA[1318817701201607027785778331]]></mch_billno><mch_id>1318817701</mch_id><wxappid><![CDATA[wxe61fa1b721d619b9]]></wxappid><re_openid><![CDATA[oYoUptzSmEjhF7m87e3A-IVuJ3Vc]]></re_openid><total_amount>500</total_amount></xml>");  
        JSONObject hahah=JSONObject.fromObject(json);
        Map<String, Object> xml=(Map<String, Object>) hahah.get("xml");
        System.out.println(xml.get("result_code"));
        System.out.println("[\"FAIL\"]".equals(String.valueOf(xml.get("result_code"))));
    }  
}  