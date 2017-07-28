package com.wh.whtth.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wh.whtth.util.EncryptUtil;
import com.wh.whtth.util.EncryptUtilRb;


public class Test3 {
	public static void httpUpload(String murl,String mfilename){  
        String boundary="-------------------------7e020233150564";//编节符  
        String prefix="--";//前缀 上传时需要多出两个-- 一定需要注意！！！  
        String end="\r\n";//这里也需要注意，在html协议中，用 “/r/n” 换行，而不是 “/n”。  
  
        SimpleDateFormat adf=new SimpleDateFormat("HHmmss");//通过时间来创建文件名  
        String uploadname=adf.format(new Date())+".jsp";//上传的文件名称  
  
        try {  
            URL http=new URL(murl);  
            HttpURLConnection conn= (HttpURLConnection) http.openConnection();  
            conn.setRequestMethod("POST");  
            conn.setReadTimeout(5000);  
            conn.setDoInput(true);//准许向服务器读数据  
            conn.setDoOutput(true);//准许向服务器写入数据  
  
            /*设置向服务器上传数据的请求方式  默认的是表单形式 
            * 通过Content-Type协议向服务器上传数据 
            * boundary 
            * */  
            conn.setRequestProperty("Content-Type","multipart/form-data;boundary="+boundary);  
  
            //创建一个输出流对象，  
            DataOutputStream out=new DataOutputStream(conn.getOutputStream());  
            /* 
            * 
              -----------------------------7e020233150564 
              Content-Disposition: form-data; name="file"; filename="I:\迅雷下载\18fb1f51c9eb63489cce9e029154782e.jpg" 
              Content-Type: image/jpeg 
                                        //这里是空一行  需要注意 
              <二进制文件数据未显示> 
              ---------------------------7e020233150564-- 
              */  
            //向服务器写入数据  这里就需要完全根据以上协议格式来写，需要仔细，避免出错。  
            out.writeBytes(prefix+boundary+end);//这是第一行  并回车换行  
            //这是第二行，文件名和对应服务器的  
            out.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""+uploadname+"\""+end);//这是第二行  
            out.writeBytes(end);//空一行  
            //以下写入图片  
            FileInputStream fileInputStream=new FileInputStream(new File(mfilename));  
            byte[]b=new byte[1024*4];//缓冲区  
            int len;  
            //循环读数据  
            while((len=fileInputStream.read(b))!=-1){  
                out.write(b, 0, len);  
            }  
            //写完数据后 回车换行  
            out.writeBytes(end);  
            out.writeBytes(prefix + boundary + prefix + end);  
            out.flush();//清空  
  
            //创建一个输入流对象  获取返回的信息  是否上传成功  
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            StringBuffer sb=new StringBuffer();  
            String str;  
            while((str=reader.readLine())!=null){  
                sb.append(str);  
            }  
            //关闭流信息  
            if(out!=null)out.close();  
            if(reader!=null)reader.close();  
  
            System.out.print("返回结果："+sb.toString());  
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
    }  
	
	
	public static void main(String[] args) {
		String url =  "http://localhost:9001/whtth/common/upload/image"; 
		String filename = "E:\\demonstration\\ruibang_img\\adImg\\svgfiles\\1491810491393.jpg";
		
//		httpUpload(url,filename);
		System.out.println("邳州市明星维纳斯摄影有限公司"+","+"13805222480"+":"+EncryptUtilRb.PBEDecrypt("b4d42dd72c65c94f9a2220de573d89ac"));
		System.out.println("永州市冷水滩区锦源健身中心"+","+"15111668122"+":"+EncryptUtilRb.PBEDecrypt("3d5dbae03530bd25"));
		System.out.println("湖南一品会教育咨询有限公司帝王广场店"+","+"18821971850"+":"+EncryptUtilRb.PBEDecrypt("2c9e4c2b34016df3"));
		System.out.println("永州市冷水滩区博雅琴行"+","+"18374617272"+":"+EncryptUtilRb.PBEDecrypt("a048b383590662248ed0bbc3ec392d02"));
	}
}
