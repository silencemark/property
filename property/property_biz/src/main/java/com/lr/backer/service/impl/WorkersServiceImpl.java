package com.lr.backer.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lr.backer.dao.OrderMapper;
import com.lr.backer.dao.WorkersMapper;
import com.lr.backer.service.WorkersService;

public class WorkersServiceImpl implements WorkersService { 
	private transient static Log log = LogFactory.getLog(WorkersServiceImpl.class);
	@Autowired WorkersMapper workersMapper;
	@Autowired OrderMapper orderMapper;
	

	@Override
	public List<Map<String, Object>> getHireWorkerList(Map<String, Object> data) {
		if(data.containsKey("timeid") && !String.valueOf(data.get("timeid")).equals("0")){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String starttime = sdf.format(new Date());
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(sdf.parse(starttime));
			} catch (ParseException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(String.valueOf(data.get("timeid")).equals("day1")){
				cal.add(Calendar.DAY_OF_MONTH, 1);
				data.put("starttime",starttime);
				data.put("stoptime", cal.getTime());
			}else if(String.valueOf(data.get("timeid")).equals("day3")){
				cal.add(Calendar.DAY_OF_MONTH, 3);
				data.put("starttime",starttime);
				data.put("stoptime", cal.getTime());
			}else if(String.valueOf(data.get("timeid")).equals("day7")){
				cal.add(Calendar.DAY_OF_MONTH, 7);
				data.put("starttime",starttime);
				data.put("stoptime", cal.getTime());
				
			}else if(String.valueOf(data.get("timeid")).equals("day30")){
				cal.add(Calendar.DAY_OF_MONTH, 30);
				data.put("starttime",starttime);
				data.put("stoptime", cal.getTime());
				
			}else if(String.valueOf(data.get("timeid")).equals("day0")){
				cal.add(Calendar.DAY_OF_MONTH, 30);
				
				data.put("stoptime1",starttime);
				data.put("starttime1",cal.getTime());
			}
		}
		if(data.containsKey("salaryid") && !String.valueOf(data.get("salaryid")).equals("0")){
			String[] salarys=String.valueOf(data.get("salaryid")).split("-");
			if(salarys.length==1){
				data.put("minsalary", salarys[0]);
			}else if(salarys.length > 1){
				data.put("minsalary", salarys[0]);
				data.put("maxsalary", salarys[1]);
			}
		}
		// TODO Auto-generated method stub
		return this.workersMapper.getHireWorkerList(data);
	}

	@Override
		// TODO Auto-generated method stub
	public Map<String, Object> getJobInfo(Map<String, Object> data) {
		return this.workersMapper.getJobInfo(data);
	}

	@Override
	public int insertApplyOrder(Map<String, Object> data) {
		 //判断是否满足抢单条件
		 int type = this.judgeOrder(data);
		 if(type != 0){
			 return type;
		 }
		
		//判断是否接过此单
		Map<String, Object> applymap=new HashMap<String, Object>();
		applymap.put("orderid", data.get("orderid"));
		applymap.put("applicantid", data.get("applicantid"));
		applymap=this.workersMapper.getApplyOrder(applymap);
		if(applymap != null && applymap.size()>0){
			return 1;
		}else{
			//查询接过的单子
			if(data.containsKey("jobid") && !data.get("jobid").equals("")){
				applymap=new HashMap<String, Object>();
				applymap.put("applicantid", data.get("applicantid"));
				applymap.put("isjobid", "1");
				applymap.put("taostatus", new String[]{"1","3","5","6","7","8","9","10"});
				List<Map<String, Object>> applymlist=this.workersMapper.getApplyJobOrder(applymap);
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date starttimenow = null;
				Date endtimenow = null;
				try {
					starttimenow = sdf.parse(data.get("starttime")+"");
					endtimenow = sdf.parse(data.get("endtime")+"");
				} catch (ParseException e1) {
					
					e1.printStackTrace();
				}
				int timestatus=0;
				if(applymlist!=null && applymlist.size()>0){
					for(Map<String, Object> apply:applymlist){
						try {
							Date starttime=sdf.parse(apply.get("starttime")+"");
							Date endtime=sdf.parse(apply.get("endtime")+"");
							if((starttimenow.before(starttime) && endtimenow.after(starttime)) || (starttimenow.after(starttime) && endtimenow.before(endtime)) || (starttimenow.before(endtime) && endtimenow.after(endtime))){
								timestatus=1;
								break;
							}
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
					}
				}
				if(timestatus==1){
					return 2;
				}
			}
			data.put("applyorderid", UUID.randomUUID().toString().replace("-", ""));
			data.put("status", 1);
			data.put("createtime", new Date());
			Map<String, Object> attenmap=new HashMap<String, Object>();
			attenmap.put("userid", data.get("createrid"));
			attenmap.put("attentionerid", data.get("publisherid"));
			attenmap=this.workersMapper.getAttention(attenmap);
			if(attenmap != null && attenmap.size() > 0){
				data.put("ifattention", 1);
			}else{
				data.put("ifattention", 0);
			}
			data.put("status", 1);

			int num = this.orderMapper.getApplyOrderCountByNow();
			DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String orderno=sdf.format(new Date())+String.format("%05d",(num+1));
			data.put("orderno",orderno);
						
			this.workersMapper.insertApplyOrder(data);
			return 0;
		}
	}

	private int parseInt(String string) {
 
		return 0;
	}

	@Override
	public List<Map<String, Object>> getProjectChartList(Map<String, Object> data) {
 
		if(data.containsKey("timeid") && !String.valueOf(data.get("timeid")).equals("0")){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String starttime = sdf.format(new Date());
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(sdf.parse(starttime));
			} catch (ParseException e){
			
				e.printStackTrace();
			}
			if(String.valueOf(data.get("timeid")).equals("day1")){
				cal.add(Calendar.DAY_OF_MONTH, 1);
				data.put("starttime",starttime);
				data.put("stoptime", cal.getTime());
			}else if(String.valueOf(data.get("timeid")).equals("day3")){
				cal.add(Calendar.DAY_OF_MONTH, 3);
				data.put("starttime",starttime);
				data.put("stoptime", cal.getTime());
			}else if(String.valueOf(data.get("timeid")).equals("day7")){
				cal.add(Calendar.DAY_OF_MONTH, 7);
				data.put("starttime",starttime);
				data.put("stoptime", cal.getTime());
				
			}else if(String.valueOf(data.get("timeid")).equals("day30")){
				cal.add(Calendar.DAY_OF_MONTH, 30);
				data.put("starttime",starttime);
				data.put("stoptime", cal.getTime());
				
			}else if(String.valueOf(data.get("timeid")).equals("day0")){
				cal.add(Calendar.DAY_OF_MONTH, 30);
				
				data.put("stoptime1",starttime);
				data.put("starttime1",cal.getTime());
			}
		}
		if(data.containsKey("salaryid") && !String.valueOf(data.get("salaryid")).equals("0")){
			String[] salarys=String.valueOf(data.get("salaryid")).split("-");
			if(salarys.length==1){
				data.put("minsalary", salarys[0]);
			}else if(salarys.length > 1){
				data.put("minsalary", salarys[0]);
				data.put("maxsalary", salarys[1]);
			}
		}
		return this.workersMapper.getProjectChartList(data);
	}

	@Override
	public Map<String, Object> getProjectInfo(Map<String, Object> data) {
		
		return this.workersMapper.getProjectInfo(data);
	}

	@Override
	public int getHireWorkerListNum(Map<String, Object> data) {
		 
		return this.workersMapper.getHireWorkerListNum(data);
	}

	@Override
	public int getProjectChartListNum(Map<String, Object> data) {
		
		return this.workersMapper.getProjectChartListNum(data);
	}

	@Override
	public Map<String, Object> getApplyOrder(Map<String, Object> data) {
	
		return this.workersMapper.getApplyOrder(data);
	}

	@Override
	public int judgeOrder(Map<String, Object> data) {
		//判断当前订单是否过期
		Map<String,Object> param0 =  new HashMap<String, Object>();
		param0.put("orderid", data.get("orderid"));//设置订单id
		Map<String,Object> orderInfo = this.workersMapper.getOrderInfo(param0);
		if(orderInfo.get("isend") != null && orderInfo.get("isend").toString().equals("0")){// 如果为0  该订单已过期
			return 4;
		}
		//判断当前订单是否已经抢满了
		Map<String,Object> param1 =  new HashMap<String, Object>();
		String [] statusList = {"3","8","10"};
		param1.put("orderid", data.get("orderid"));//设置订单id
		param1.put("statusList", statusList);
		int num = this.workersMapper.getApplyNum(param1);
		
		int recruitnum=0;
		if(orderInfo != null && orderInfo.containsKey("recruitmentnum") && orderInfo.get("recruitmentnum") != null){
			recruitnum=Integer.parseInt(orderInfo.get("recruitmentnum").toString());
		}
		log.debug("num:"+num+"------------------------------------------------------------------------------------------");
		log.debug("recruitmentnum:"+recruitnum+"-----------------------------------------------------------------------------------------------------------");
		if(num >= recruitnum && orderInfo.get("recruitmentnum") != null){//如果抢单人数 大于发布招工的总人数，订单已满
			return 5;
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> getApplyOrderList(Map<String, Object> data) {
		// TODO Auto-generated method stub
		return this.workersMapper.getApplyOrderList(data);
	}

}