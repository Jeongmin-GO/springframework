package com.mycompany.webapp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ch06")
public class Ch06Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch06Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		return "ch06/content";
	}
	
	@RequestMapping("/forward")
	public String forward() {
		return "ch06/forward";
	}
	
	@RequestMapping("/redirect")
	public String redirect() {
		return "redirect:/";
	}
	
	@GetMapping("/getFragmentHtml")
	public String getFragmentHtml() {
		return "ch06/fragmentHtml";
	}
	
	@GetMapping("/getJson")
	public void getJson(HttpServletResponse response) throws IOException {
		logger.info("실행");
		JSONObject obj = new JSONObject();
		obj.put("fileName", "photo5.jpg");
		String json = obj.toString(); //json을 문자열로 변환
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.println(json);
		pw.flush();
		pw.close();
	}
	
	//이 메서드가 json을 만들거고 인코딩은 utf-8로 할거야(produces)
	@GetMapping(value="/getJson2", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String getJson2(){
		logger.info("실행");
		JSONObject obj = new JSONObject();
		obj.put("fileName", "photo9.jpg");
		String json = obj.toString(); //json을 문자열로 변환
		return json;
	}
	
	@GetMapping("/getJson3")
	public String getJson3(){
		logger.info("실행");
//		동작은 되지만 절대절대 이렇게 하지마 
//		return "redirect:/ch06/getJson2";
		return "redirect:/";
	}
}
