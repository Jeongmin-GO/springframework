package com.mycompany.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.mycompany.webapp.dto.Ch08InputForm;

@Controller
@RequestMapping("/ch08")
@SessionAttributes({"inputForm"})
public class Ch08Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch08Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("실행");
		return "ch08/content";
	}
	
	@GetMapping(value = "/saveData", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String saveData(String name, HttpServletRequest req, HttpSession session) {
		logger.info("실행" + "| 이름값 : " + name);
		
//		HttpSession session = req.getSession();
		session.setAttribute("name", name);
		
		JSONObject obj = new JSONObject();
		obj.put("result", "success");
		String json = obj.toString(); // {"result", "success"}
		
		return json;
	}
	
	@GetMapping(value = "/readData", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String readData(HttpSession session, @SessionAttribute String name) {
		logger.info("실행");
		
		//방법1
		//object로 저장되어있기 때문에 강제 형변환 
		//String name = (String)session.getAttribute("name");
		//logger.info(name);
		
		//방법2
		logger.info("name:" + name);
		
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		String json = obj.toString(); // {"name", "홍길동"}
		
		return json;
	}
	
	@GetMapping("/login")
	public String loginForm() {
		logger.info("실행");
		return "ch08/loginForm";
	}
	
	@PostMapping("/login")
	public String login(String mid, String mpassword, HttpSession session) {
		logger.info("실행");
		if(mid.equals("spring") && mpassword.equals("12345")) {
			session.setAttribute("sessionMid", mid);
		}
		return "redirect:/ch08/content";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("실행");
		//방법1
		session.removeAttribute("sessionMid");
		
		//방법2
		//session.invalidate();
		
		return "redirect:/ch08/content";
	}
	
	@PostMapping(value="/loginAjax", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String loginAjax(String mid, String mpassword, HttpSession session) {
		logger.info("실행");
		String result = "";
		
		if(!mid.equals("spring")) {
			result = "wrongMid";
		}else if(!mpassword.equals("12345")) {
			result = "wrongMpassword";
		}else {
			result = "success";
			session.setAttribute("sessionMid", mid);
		}
		
		JSONObject obj = new JSONObject();
		obj.put("result", result);
		String json = obj.toString();
		return json;
	}
	
	
	@GetMapping(value="/logoutAjax", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String logoutAjax(HttpSession session) {
		logger.info("실행");
		
		session.invalidate();
		
		JSONObject obj = new JSONObject();
		obj.put("result", "success");
		String json = obj.toString();
		return json;
	}
	//request에 범위에 지정되기 때문에 이것만 지정하면 요청을 할 때마다 실행함.
	//하지만 클래스위에 @SessionAttributes를 같은 이름으로 지정해주면 딱 한 번만 실행하게 됨
	// -> 지정한 변수명이 session에 없을 때에만 딱 한 번 실행 
	// -> 위에 @SessionAttributes 가 session에 저장하게 해줌 
	@ModelAttribute("inputForm")
	public Ch08InputForm getInputForm() {
		logger.info("실행");
		Ch08InputForm inputForm = new Ch08InputForm();
		return inputForm;
	}
	
	@GetMapping("/inputStep1")
	public String inputStep1(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
		logger.info("실행");
		return "ch08/inputStep1";
	}
	
	@PostMapping("/inputStep2")
	public String inputStep2(@ModelAttribute("inputForm") Ch08InputForm inputForm) {
		logger.info("실행");
		logger.info("data1 : " + inputForm.getData1());
		logger.info("data2 : " + inputForm.getData2());
		logger.info("data3 : " + inputForm.getData3());
		logger.info("data4 : " + inputForm.getData4());
		return "ch08/inputStep2";
	}
	
	@PostMapping("/inputDone")
	public String inputDone(@ModelAttribute("inputForm") Ch08InputForm inputForm,SessionStatus sessionStatus) {
		logger.info("실행");
		logger.info("data1 : " + inputForm.getData1());
		logger.info("data2 : " + inputForm.getData2());
		logger.info("data3 : " + inputForm.getData3());
		logger.info("data4 : " + inputForm.getData4());
		
		//처리 내용~
//		session.removeAttribute("inputForm"); 절대안돼!!!!!!!!!!이렇게 쓰지마
		
		//세션에 저장되어 있는 inputForm을 제거
		sessionStatus.setComplete();
		
		return "redirect:/ch08/content";
	}

}
