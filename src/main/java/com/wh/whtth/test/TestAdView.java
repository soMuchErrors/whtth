package com.wh.whtth.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
//			String url =  "http://123.57.209.104:9001/whtth/admin/productManage/listValidShops"; 
			String url =  "http://localhost:9001/whtth/admin/shopManage/listValidShops"; 
			//String url =  "http://121.40.51.183:8084/cyjad-api/appAd/getAdViewAd";
			long end = 0;
			try {
//				ShopManageVo vo = new ShopManageVo();
//				Shop shop = new Shop();
//				shop.setAddress("杭州");
//				shop.setName("大脸猫");
//				User user = new User();
//				user.setEmail("332831840@qq.com");
//				user.setPhonenum("13606543972");
//				user.setIdcardNum("331082199203015512");
//				vo.setShop(shop);
//				vo.setUser(user);
				ViewVo vo = new ViewVo();
				vo.setPage(1);
				vo.setPagesize(5);
				String json = JSONObject.toJSONString(vo);
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
