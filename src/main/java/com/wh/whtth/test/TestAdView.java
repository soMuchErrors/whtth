package com.wh.whtth.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.alibaba.fastjson.JSONObject;
import com.wh.whtth.model.Shop;
import com.wh.whtth.model.User;
import com.wh.whtth.vo.ShopManageVo;
import com.wh.whtth.vo.ViewVo;
public class TestAdView {
	
	public static Integer num = 1;
	private static Long startTime;  
    private static Long totleTime; 

	public  static void main(String[] args){
		TestThread[] threads = new TestThread[num];
		for(int i = 0;i< num;i++){
			threads[i] = new TestAdView().new TestThread();
		}
		
		for(TestThread th: threads){
			th.start();
		}
	}
	
	class TestThread extends Thread {

		@Override
		public void run() {
//			String url =  "http://123.57.209.104:9001/whtth/user/shop/getShopinfo?id=7"; 
			String url =  "http://127.0.0.1:9001/whtth/common/login"; 
			//String url =  "http://121.40.51.183:8084/cyjad-api/appAd/getAdViewAd";
			long end = 0;
			try {
//				ShopManageVo vo = new ShopManageVo();
//				Shop shop = new Shop();
//				shop.setAddress("杭州");
//				shop.setName("脏数据");
//				User user = new User();
//				user.setEmail("332831840@qq.com");
//				user.setPhonenum("1360653882");
//				user.setIdcardNum("331082199203024582");
//				vo.setShop(shop);
//				vo.setUser(user);
//				ViewVo vo = new ViewVo();
//				vo.setPage(1);
//				vo.setPagesize(5);
//				vo.setId("12");
				Map<String,String> vo = new HashMap<String,String>();
//				vo.put("num", "2");
//				vo.put("productid", "22");
//				vo.put("userid", "12");
//				vo.put("shopid", "12");
//				vo.put("page", "3");
//				vo.put("pagesize", "5");
//				vo.put("id", "12");
//				vo.put("trade", null);
				vo.put("username", "13606543972");
				vo.put("password", "123456");
				vo.put("verifyCode", "qu5x");
				String json = JSONObject.toJSONString(vo);
				System.out.println(json);
				HttpClient client = new HttpClient();
				PostMethod method = new PostMethod(url);
				method.setRequestHeader(new Header("Accept", "application/json"));
				method.setRequestHeader(new Header("Content-Type",
						"application/json"));
				RequestEntity entity2 = new StringRequestEntity(json,
						"application/json", "utf-8");
				method.setRequestEntity(entity2);
				startTime = System.currentTimeMillis();
				client.executeMethod(method);
				System.out.println("client::response: "
						+ method.getResponseBodyAsString());
				 end = System.currentTimeMillis(); 
				 totleTime = end - startTime;  
			}catch (Exception e) {
				
			}
			
			 synchronized (num) {  
				 num--;  
//	              System.out.printf("还有%d个未完线程, 耗时%d毫秒\n", threads,  
//	                      (System.currentTimeMillis() - startTime));  
	                if (num == 0) {  
	                    System.out.printf("总耗时：%d毫秒\n",totleTime);  
	                }  
	            }  
		}
		
	}
	
	
}
