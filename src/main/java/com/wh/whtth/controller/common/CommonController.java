package com.wh.whtth.controller.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.joda.MillisecondInstantPrinter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.ruibang.jedis.util.JedisUtil;
import com.ruibang.jedis.util.MultipleJedisPool;
import com.wh.whtth.controller.BaseController;
import com.wh.whtth.service.CommonService;
import com.wh.whtth.util.DateUtil;
import com.wh.whtth.util.EncryptUtil;
import com.wh.whtth.util.VerifyCodeUtils;
import com.wh.whtth.vo.Message;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {
	@Value("${imgpath}")
	private String imgurl;
	
	@Value("${imgsubfix}")
	private String imgsubfix;
	
	@Autowired
	private CommonService commonService;
	/**
	 * 上传图片文件
	 * 
	 * @param req
	 * @param res
	 * @param img
	 * @return
	 */
	@RequestMapping(value = "/upload/image")
	public @ResponseBody String uploadImg(HttpServletRequest req,
			HttpServletResponse res, @RequestBody MultipartFile img)
			throws Exception {
		String originalFilename = img.getOriginalFilename();
		String newdir = "";
		String newFileName = "";
		if (img != null && !StringUtils.isEmpty(originalFilename)) {
			newdir= File.separator
					+"shop"
					+ File.separator
					+ DateUtil.formatYm();
			newFileName = UUID.randomUUID()
					+ originalFilename.substring(originalFilename
							.lastIndexOf("."));
			File newFile = new File(imgurl + newdir);
			newFile.setWritable(true);
			if(!newFile.exists()){
				newFile.mkdirs();
			}
			newFile = new File(imgurl + newdir + File.separator +newFileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
				img.transferTo(newFile);
			}
		}
		return imgsubfix + newdir + File.separator +newFileName;
	}

	@RequestMapping(value = "/loadImg/**", method = RequestMethod.GET)
	public void getImgFromCyj(HttpServletRequest req, HttpServletResponse res) {
		String uri = req.getRequestURI();
		uri = uri.replace("/whtth/common/loadImg", "");
		FileInputStream fis = null;
		res.setContentType("image/jpeg");
		try {
			OutputStream out = res.getOutputStream();
			fis = new FileInputStream(imgurl + uri);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			out.write(b);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//图片验证码
	@RequestMapping(value = "/verifyCode**", method = RequestMethod.GET)
	public void verifyCode(HttpServletRequest req, HttpServletResponse res){
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setDateHeader("Expires", 0);
		res.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 存入会话session
		// 生成图片
		int w = 150, h = 60;
		Jedis jedis = MultipleJedisPool.getJedisFromPool();
		Pipeline pipeline = jedis.pipelined();
		try {
			pipeline.set(req.getRemoteHost(), verifyCode);
			pipeline.expire(req.getRemoteHost(), 120);
			pipeline.sync();
			VerifyCodeUtils.outputImage(w, h, res.getOutputStream(), verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			MultipleJedisPool.jedisRelease(jedis);
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> login(HttpServletRequest req,
			@RequestBody Map<String,String> params) {
		String password = params.get("password");
		String username = params.get("username");
		Map<String,Object> map =  new HashMap<String,Object>();
		Jedis jedis = MultipleJedisPool.getJedisFromPool();
		try{
			String verifyCode = params.get("verifyCode");
			String vrfCode = jedis.get(req.getRemoteHost());
			if(vrfCode ==null ){
				map.put("message", new Message("0","验证码过期请重新刷新"));
				return map;
			}
			if(!vrfCode.toLowerCase().equals(verifyCode.toLowerCase())){
				map.put("message", new Message("0","验证码错误"));
				return map;
			}
			Message message = new Message();
			Map<String,Object> user = commonService.login(username,password);
			if(user == null){
				message.setMsg("账号或密码错误");
				message.setStatus("0");
				map.put("message", message);
				return map;
			}
			String token = EncryptUtil.PBEEncrypt(System.currentTimeMillis()+"");
			Object shopid = user.get("shopid");
			message.setMsg("成功");
			message.setStatus("1");
			map.put("user", user);
			map.put("token", token);
			map.put("message", message);
			jedis.hset(token, "userid", user.get("id").toString());
			if(!(shopid==null))
				jedis.hset(token, "shopid", shopid.toString());
			jedis.expire(token, 5*60);
		}
		catch(Exception e){
			e.printStackTrace();
		}finally{
			MultipleJedisPool.jedisRelease(jedis);
		}
		return map;
	}
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> loginUser(HttpServletRequest req,
			@RequestBody Map<String,String> params) {
		String password = params.get("password");
		String username = params.get("username");
		Map<String,Object> map = new HashMap<String,Object>();
		
		Map<String,Object> user = commonService.login(username,password);
		if(user == null){
			map.put("message", new Message("0","账号或密码错误"));
			return map;
		}
		String token = EncryptUtil.PBEEncrypt(System.currentTimeMillis()+"");
		Object shopid = user.get("shopid");
		map.put("user", user);
		map.put("token", token);
		map.put("message", new Message("1"));
		JedisUtil.hset(token, "userid", user.get("id").toString());
		if(!(shopid==null))
			JedisUtil.hset(token, "shopid", shopid.toString());
		JedisUtil.expire(token, 300);
		return map;
	}
	
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest req) {
		JedisUtil.del(req.getParameter("token"));
	}
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> register(HttpServletRequest req,
			@RequestBody Map<String,String> params) {
		String username = params.get("username");
		String password = params.get("password");
		return commonService.register(username,password);
	}
}
