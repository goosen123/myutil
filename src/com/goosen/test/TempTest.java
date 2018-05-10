package com.goosen.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.junit.Test;

import com.goosen.bean.HTTPMethod;
import com.goosen.bean.ReqParams;
import com.goosen.util.CommonUtil;
import com.goosen.util.DateUtil;
import com.goosen.util.EncryUtil;
import com.goosen.util.HttpUtil;
import com.goosen.util.JsonUtil;
import com.goosen.util.MadeRandomUtil;

public class TempTest {
	
	@Test
	public void random(){
		String random = MadeRandomUtil.random(10, false, true);
		System.out.println(random);
	}
	
	@Test
	public void dateUtil(){
		Date date = new Date();
//		String dateStr = DateUtil.convert2String(date, "yyyy-MM-dd HH:mm:ss");
//		System.out.println(dateStr);
//		String dateStr = DateUtil.convert2String(DateUtil.addDays(date, -2), "yyyy-MM-dd HH:mm:ss");
//		System.out.println(dateStr);
//		String dateStr = DateUtil.convert2String(DateUtil.addHours(date, -100), "yyyy-MM-dd HH:mm:ss");
//		System.out.println(dateStr);
		String dateStr = DateUtil.convert2String(DateUtil.setStartDay(DateUtil.getLastDayOfWeek(date)), "yyyy-MM-dd HH:mm:ss");
		System.out.println(dateStr);
	}
	
	@Test
	public void commonUtil(){
		//Vector vec = new Vector();
//		Set set = new HashSet();
//		boolean flag = CommonUtil.isEmpty(set);
//		System.out.println(flag);
		
//		Map map = new HashMap();
//		boolean flag = CommonUtil.isEmpty(map);
//		System.out.println(flag);
		
		StringBuffer stringBuffer =  new StringBuffer("222");
		boolean flag = CommonUtil.isEmpty(stringBuffer);
		System.out.println(flag);
	}
	
	@Test
	public void jsonUtil(){
		Milk milk = new Milk();  
	    milk.setName("冰糖雪梨");  
	    milk.setBrand("冰糖");  
	    milk.setType("混合果汁");  
	    milk.setNum(5);
	    milk.setNumD(5.6);
	    milk.setDate(new Date());
	    Map map = new HashMap();
	    map.put("1", 1);
	    milk.setMap(map);
	    Map map2 = new HashMap();
	    map2.put("1", "22");
	    map.put("map2", map2);
	    Vector vec = new Vector();
	    vec.add(map);
	    vec.add(map2);
	    milk.setVec(vec);
	    milk.setList(new ArrayList());
	    
	    /**************************** json----bean *********************************************/
	    //使用JSON.toJSONString(obj)方式来实现javaBean到json  
	    String jsonString=JsonUtil.toJSONString(milk);  
	    System.out.println(jsonString);  
	    //使用JSON.parseObject(jsonString, clazz)可以将json数据转换成相应的javabean，可以是实体类，也可以是List，Map等集合类  
	    Milk milk2=JsonUtil.parseObject(jsonString, Milk.class);  
	    System.out.println(milk2.getNum()); 
	    System.out.println(milk2.getDate()); 
	    Vector vecFan = milk2.getVec();
	    for (int i = 0; i < vecFan.size(); i++) {
			Map item = (Map) vecFan.elementAt(i);
			System.out.println(item.get("1"));
		}
	    System.out.println("====================================================");
	    
	    /**************************** json----Map *********************************************/
	    String jsonStringMap=JsonUtil.toJSONString(map);  
	    System.out.println(jsonStringMap); 
	    Map mapFan =JsonUtil.parseObject(jsonStringMap, Map.class);  
	    System.out.println(mapFan.get("1")); 
	    System.out.println("====================================================");
	    
	    /**************************** json----vec *********************************************/
	    String jsonStringVec=JsonUtil.toJSONString(vec);  
	    System.out.println(jsonStringVec); 
	    Vector vecFan2 =JsonUtil.parseObject(jsonStringVec, Vector.class);  
	    for (int i = 0; i < vecFan2.size(); i++) {
			Map item = (Map) vecFan2.elementAt(i);
			System.out.println(item.get("1"));
		}
	    System.out.println("====================================================");
	}
	
	@Test
	public void httpUtil(){
		
		String accountSid="30ba4b0ae3074d651d74cdb80422e257";
		String token="dd5c142ab8f68ea921ef3e27d682425d";
		String appId="e90cf9f4a830494aaf9a8bfd92ebe6f4";
		String templateId="40435";
		
		//请求的url链接
		String url = "https://api.ucpaas.com/2014-06-30/Accounts/"+ accountSid +"/Messages/templateSMS?";
		String sigParameter = "";
		String timestamp = DateUtil.convert2String(new Date(), "yyyyMMddHHmmss");// 时间戳
		//URL后必须带有sig参数，sig= MD5（账户Id + 账户授权令牌 + 时间戳），共32位(注:转成大写)
		String temStr = accountSid + token + timestamp;
		String xiaoxie = EncryUtil.encodeByMD5(temStr);
		sigParameter = xiaoxie.toUpperCase();
		url += "sig=" + sigParameter;
		
		ReqParams reqParams = new ReqParams();
		
		//请求头
		Map mapHeard = new HashMap();
		mapHeard.put("Accept", "application/json");
		//在HttpRequest里面请求的时候，不用加，自动会加上去的；在外部API接口的时候，要加。这里要加
		//https://blog.csdn.net/majinggogogo/article/details/78383772
		mapHeard.put("Content-Type", "application/json"+";charset=utf-8");
		String src = accountSid + ":" + timestamp;
		String auth = EncryUtil.encodeByBase64(src);
		mapHeard.put("Authorization", auth);
		reqParams.setHeaderMap(mapHeard);
		
		//参数
		//如果设了postText,则paramsMap无效
		Map map = new HashMap();
		Map mapParams = new HashMap();
		mapParams.put("appId", appId);
		mapParams.put("templateId", templateId);
		mapParams.put("to", "15088132168");
		mapParams.put("param", "123456");
		map.put("templateSMS", mapParams);
		reqParams.setPostText(JsonUtil.toJSONString(map));
		
		String responseJson = HttpUtil.sendHTTPRequest(url, reqParams, HTTPMethod.METHOD_POST);
		System.out.println("jieguo:"+responseJson);
		
	}
	
}
