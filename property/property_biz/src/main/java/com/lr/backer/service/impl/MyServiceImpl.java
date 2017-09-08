package com.lr.backer.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;





import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.labor.weixin.api.HttpRequest;
import com.labor.weixin.util.WeiXinConfigure;
import com.lr.backer.dao.MyMapper;
import com.lr.backer.service.MyService;

public class MyServiceImpl implements MyService {
	@Autowired
	public MyMapper myMapper;

	@Override
	public List<Map<String, Object>> getTickUserList(Map<String, Object> map) {
		return myMapper.getTickUserList(map);
	}

	@Override
	public List<Map<String, Object>> getTradeRecord(Map<String, Object> map) {
		return myMapper.getTradeRecord(map);
	}

	@Override
	public Map<String, Object> getTradeSum(Map<String, Object> map) {
		return myMapper.getTradeSum(map);
	}

	@Override
	public List<Map<String, Object>> getcoinRecord(Map<String, Object> map) {
		return myMapper.getcoinRecord(map);
	}

	/*
	 * 此方法未实现完
	 */
	@Override
	public String createMyQRCode(Map<String, Object> map) {
		List<Map<String, Object>> list = myMapper.getTickUserList(map);
		String qrcodeUrl = "";
		String basePath = "";
		String memberid = "";
		if (list != null && list.size() > 0) {
			qrcodeUrl = (String) list.get(0).get("qrcodeurl");
		}
		if (StringUtils.isEmpty(qrcodeUrl)) {

			// MatrixUtil.encode(contents, file);
		}
		List<Map<String, Object>> members = myMapper.getMembersForArea(map);
		return null;
	}

	@Override
	public List<Map<String, Object>> getAttentionMembers(Map<String, Object> map) {
		return myMapper.getAttentionMembers(map);
	}

	@Override
	public List<Map<String, Object>> getMemberDetail(Map<String, Object> map) {
		return myMapper.getMemberDetail(map);
	}

	@Override
	public List<Map<String, Object>> getAttention(Map<String, Object> map) {
		return myMapper.getAttention(map);
	}

	@Override
	public void insertAttention(Map<String, Object> map) {
		
		myMapper.insertAttention(map);

	}

	@Override
	public List<Map<String, Object>> getApplyOrderAndOrder(
			Map<String, Object> map) {
		return this.myMapper.getApplyOrderAndOrder(map);
	}

	@Override
	public void updateApplyOrderStatus(Map<String, Object> map) {
		myMapper.updateApplyOrderStatus(map);
	}

	@Override
	public String getWeixinQRcode(String memberid, String filePath,
			String access_token) {
		/*
		 * 获取设置带参数二维码ticket参数
		 */
		String outputStr = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\":\""
				+ memberid + "\"}}}";
		//调用微信接口获取返回json值 
		JSONObject json = HttpRequest.httpRequest(WeiXinConfigure.QRCODE_URL+ access_token, HttpRequest.POST, outputStr);
		//声明获取得到的参数
		String ticket = "";
		Integer expire_seconds = null;
		String url = "";
		//获取从微信接口获取的参数
		if (json.get("ticket") != null) {
			ticket = (String) json.get("ticket");
		}

		if (json.get("expire_seconds") != null) {
			expire_seconds = (Integer) json.get("expire_seconds");
		}
		if (json.get("url") != null) {
			url = (String) json.get("url");
		}
		//调用下载图片方法，将微信带参数二维码下载到本地
		filePath = HttpRequest.httpsGetDownload(WeiXinConfigure.SHOWQRCODE
				+ ticket, filePath, memberid + ".jpg");
		return filePath;
	}

	@Override
	public int getcoinRecordNum(Map<String, Object> map) {
		
		return this.myMapper.getcoinRecordNum(map);
	}

	@Override
	public int getTradeRecordNum(Map<String, Object> map) {
		
		return this.myMapper.getTradeRecordNum(map);
	}

	@Override
	public int getAttentionMembersNum(Map<String, Object> map) {
		 
		return this.myMapper.getAttentionMembersNum(map);
	}

	@Override
	public int getSurplusAppNum(Map<String, Object> map) {
		return this.myMapper.getSurplusAppNum(map);
	}

	@Override
	public void cancelAttention(Map<String, Object> map) {
		this.myMapper.cancelAttention(map);
		
	}

}
