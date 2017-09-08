package com.lr.backer.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hoheng.base.controller.BaseController;
import com.hoheng.util.HttpHeaderUtil;
import com.hoheng.util.StringUtil;
import com.lr.backer.redis.RedisUtil;
import com.lr.backer.service.IndexService;
import com.lr.backer.service.OrderService;
import com.lr.backer.service.SupplierService;
import com.lr.backer.service.SystemService;
import com.lr.backer.util.Constants;
import com.lr.backer.util.CookieUtil;
import com.lr.backer.vo.UploadUtil;
import com.lr.labor.weixin.core.CoreService;
import com.lr.weixin.backer.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {

	@Resource
	MemberService memberService;
	@Resource
	IndexService indexService;
	@Resource
	SupplierService supplierService;
	@Resource
	SystemService systemService;
	@Resource
	OrderService orderService;

	private static final Logger LOGGER = Logger.getLogger(MemberController.class);
	
	/**
	 * 编辑手机端用户信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/initEditMemberInfo")
	public String initEditMemberInfo(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		// 用户信息放redis里面
		String key = this.getUserId(request) + "_userinfo_ddjr";
		Map<String, Object> datas = null;
		if(RedisUtil.exist(key)){
			datas = RedisUtil.getObject(key);
		}
		
		if (datas!=null&&!datas.isEmpty()&&(datas.containsValue("openid")||datas.containsValue("phone"))) {
			try{
			 
			if(datas.get("idCardImgList") != null  && !datas.get("idCardImgList").equals("") ){
               datas.put("idCardImgList", CookieUtil.getList(datas.get("idCardImgList").toString()));
			}
			if(datas.get("personalImgList") != null && !datas.get("personalImgList").equals("")){
               datas.put("personalImgList", CookieUtil.getList(datas.get("personalImgList").toString()));
			}
			model.addAttribute("data", datas);
			}catch(Exception e){
				e.printStackTrace();
			}
		} else {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			map.put("memberid", this.getUserId(request));
			List<Map<String, Object>> dataList = this.memberService
					.getMemberList(map);
			if (dataList.size() > 0 && dataList.size() == 1) {
				LOGGER.info("----------------------headimage:"+dataList.get(0).get("headimage"));
				if(dataList.get(0).containsKey("headimage") && dataList.get(0).get("headimage") != null && !"".equals(dataList.get(0).get("headimage"))){
					dataList.get(0).put(
							"headimage_show",
							UploadUtil.downImg(String.valueOf(dataList.get(0).get(
									"headimage"))));
				}
				if(dataList.get(0).containsKey("companyimgurl") && dataList.get(0).get("companyimgurl") != null && !"".equals(dataList.get(0).get("companyimgurl"))){
					dataList.get(0).put(
							"companyimgurl_show",
							UploadUtil.downImg(String.valueOf(dataList.get(0).get(
									"companyimgurl"))));
				}
				
				dataMap = dataList.get(0);
				
				// 身份证照片
				Map<String, Object> idCardMap = new HashMap<String, Object>();
				idCardMap.put("type", "1");
				idCardMap.put("memberid", map.get("memberid"));
				List<Map<String, Object>> idCardImgList = this.memberService
						.getMemberImgList(idCardMap);
				if (idCardImgList.size() > 0) {
					for (Map<String, Object> data : idCardImgList) {
						data.put("idcardimgurl_show",
								UploadUtil.downImg(String.valueOf(data.get("url"))));
					}

				}

				dataMap.put("idCardImgList", JSON.toJSON(idCardImgList)  );
				// 个人照
				Map<String, Object> personalMap = new HashMap<String, Object>();
				personalMap.put("type", "2");
				personalMap.put("memberid", map.get("memberid"));
				List<Map<String, Object>> personalImgList = this.memberService
						.getMemberImgList(personalMap);
				if (personalImgList.size() > 0) {
					for (Map<String, Object> data : personalImgList) {
						data.put("personal_show",
								UploadUtil.downImg(String.valueOf(data.get("url"))));
					}

				}
				if(personalImgList!= null && personalImgList.size()> 0){
					dataMap.put("personalImgList",JSON.toJSON(personalImgList) );
				}
				
				
				
				// 存在redis里面
				try {
					RedisUtil.setObject(key, dataMap, 336);
				} catch (Exception e) {
					// TODO: handle exception
					LOGGER.error("存入redis错误---------------------key:"+key+"-----------------dataMap:"+dataMap);
				}
				
				model.addAttribute("data", dataMap);
			}
		}

		// 行业
		Map<String, Object> dicMap = new HashMap<String, Object>();
		dicMap.put("typeid", Constants.INDUSTRY);
		dicMap.put("ifparentidone", "1");
		List<Map<String, Object>> industrylist = indexService
				.getDictData(dicMap);
		model.addAttribute("industrylist", industrylist);

		// 地区
		Map<String, Object> areaMap = new HashMap<String, Object>();
		areaMap.put("parentid", 4);
		List<Map<String, Object>> areaList = indexService.getAreaInfo(areaMap);
		model.addAttribute("areaList", areaList);
		model.addAttribute("key", key);

		model.addAttribute("isAndroidFrom",
				HttpHeaderUtil.isAndroidFrom(request));
		model.addAttribute("isIosFrom", HttpHeaderUtil.isIOSFrom(request));
		model.addAttribute("isweixin", HttpHeaderUtil.isWeiXinFrom(request));
		return "/phone/member_info_edit";
	}

	/**
	 * 更新个人信息
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateBaseInfo")
	@ResponseBody
	public Map<String, Object> updateBaseInfo(
			@RequestParam Map<String, Object> map, Model model,
			HttpServletRequest request) {
		boolean flg = false;
		this.memberService.deleteMemberImg(map);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Timestamp tm = new Timestamp(System.currentTimeMillis());
		String[] idcardList = map.get("idCardImgArray").toString().split(",");
		String[] personalList = map.get("personalImgArray").toString()
				.split(",");
		List<Map<String, Object>> memberImgArray = new ArrayList<Map<String, Object>>();
		Map<String, Object> memberImgMap = null;
		List<Map<String, Object>> imgArray = new ArrayList<Map<String, Object>>();
		Map<String, Object> imgMap = null;
		if (personalList.length > 0 && map.get("personalImgArray") != null
				&& !map.get("personalImgArray").equals("")) {
			for (int i = 0; i < personalList.length; i++) {
				imgMap = new HashMap<String, Object>();
				imgMap.put("imgid", StringUtil.getUUID());
				imgMap.put("url", personalList[i]);
				imgMap.put("name", "个人照片");
				imgMap.put("suffix", personalList[i]);
				imgMap.put("createrid", map.get("memberid"));
				imgMap.put("createtime", tm);
				imgMap.put("updatetime", tm);
				imgMap.put("delflag", "0");
				memberImgMap = new HashMap<String, Object>();
				memberImgMap.put("memberimgid", StringUtil.getUUID());
				memberImgMap.put("memberid", map.get("memberid"));
				memberImgMap.put("imgid", imgMap.get("imgid"));
				memberImgMap.put("type", "2");
				memberImgMap.put("createrid", map.get("memberid"));
				memberImgMap.put("createtime", tm);
				memberImgMap.put("updatetime", tm);
				memberImgMap.put("delflag", "0");
				imgArray.add(imgMap);
				memberImgArray.add(memberImgMap);
			}

		}

		if (idcardList.length > 0 && map.get("idCardImgArray") != null
				&& !map.get("idCardImgArray").equals("")) {
			for (int i = 0; i < idcardList.length; i++) {
				imgMap = new HashMap<String, Object>();
				imgMap.put("imgid", StringUtil.getUUID());
				imgMap.put("url", idcardList[i]);
				imgMap.put("name", "身份证照片");
				imgMap.put("suffix", idcardList[i]);
				imgMap.put("createrid", map.get("memberid"));
				imgMap.put("createtime", tm);
				imgMap.put("updatetime", tm);
				imgMap.put("delflag", "0");
				memberImgMap = new HashMap<String, Object>();
				memberImgMap.put("memberimgid", StringUtil.getUUID());
				memberImgMap.put("memberid", map.get("memberid"));
				memberImgMap.put("imgid", imgMap.get("imgid"));
				memberImgMap.put("type", "1");
				memberImgMap.put("createrid", map.get("memberid"));
				memberImgMap.put("createtime", tm);
				memberImgMap.put("updatetime", tm);
				memberImgMap.put("delflag", "0");
				imgArray.add(imgMap);
				memberImgArray.add(memberImgMap);
			}
		}
		if (imgArray.size() > 0 || memberImgArray.size() > 0) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("imgArray", imgArray);
			dataMap.put("memberImgArray", memberImgArray);
			this.memberService.insertMemberImg(dataMap);
			this.memberService.insertimg(dataMap);
		}

		Map<String, Object> pv = new HashMap<String, Object>();
		pv.put("memberid", map.get("memberid"));
		List<Map<String, Object>> dataList = this.memberService
				.getMemberList(pv);
		System.out.println("-----------------" + dataList);
		if (!dataList.get(0).get("isgive").toString().equals("1")) {// 如果为1可以赠送
			/*Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pkey", "completeinfo");
			paramMap.put("pstatus", "1");
			List<Map<String, Object>> paramList = systemService
					.getParams(paramMap);
			// 送滴滴币
			map.put("tickcoin", paramList.get(0).get("pvalue"));
			// 插入滴答币记录表
			Map<String, Object> coinMap = new HashMap<String, Object>();
			coinMap.put("recordid", StringUtil.getUUID());
			coinMap.put("title", "完善个人信息赠送");
			coinMap.put("description", "完善个人信息赠送");
			coinMap.put("amount", paramList.get(0).get("pvalue"));
			coinMap.put("income_userid", map.get("memberid"));
			coinMap.put("paytime", tm);
			this.orderService.insertCoinRecord(coinMap);*/
			map.put("isgive", "1");
		}

		if (dataList.get(0).get("refusetype").toString().equals("1")) {// 如果被拒绝审核了
																		// 直接改成再次审核
			map.put("individualstatus", "3");
			map.put("enterprisestatus", "3");
		}

		map.put("userid", map.get("memberid"));
		map.put("idcardnum", map.get("idcard"));
		this.memberService.updateExtend(map);
		this.memberService.updateMember(map);
		String key = this.getUserId(request) + "_userinfo";
		RedisUtil.deleteRedis(key);
		flg = true;
		resultMap.put("flg", flg);
		return resultMap;
	}

	/**
	 * 删除个人照片或者身份证照片
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/delMemberImg")
	@ResponseBody
	public boolean delMemberImg(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		boolean flg = false;
		map.put("delflag", "1");
		this.memberService.updateMemberImg(map);
		flg = true;
		return flg;
	}

	/**
	 * 验证手机号码是否被人使用过
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkPhone")
	@ResponseBody
	public String checkPhone(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		List<Map<String, Object>> userList = this.memberService
				.getMemberList(map);
		if (userList != null
				&& userList.size() == 1
				&& !userList.get(0).get("memberid").toString()
						.equals(this.getUserId(request))) {
			return "1";
		} else {
			return "0";
		}

	}

	/**
	 * 临时保存用户信息到redis
	 * 
	 * @param map
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/addMemberToRedis")
	@ResponseBody
	public boolean addMemberInfoToRedis(@RequestParam Map<String, Object> map,
			Model model, HttpServletRequest request) {
		boolean flg = false;
		if(map != null ){
		String keys = map.get("key").toString();
		map.remove("key");
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Object> e = it.next();
			String key = e.getKey();
			Object value = e.getValue();
			RedisUtil.putKeys(keys, key,value);	
		 }
		  flg = true;
		}
		return flg;
	}

}
