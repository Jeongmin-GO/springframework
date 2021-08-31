package com.mycompany.webapp.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch04Member;
import com.mycompany.webapp.validator.Ch04MemberEmailValidator;
import com.mycompany.webapp.validator.Ch04MemberIdValidator;
import com.mycompany.webapp.validator.Ch04MemberPasswordValidator;
import com.mycompany.webapp.validator.Ch04MemberTelValidator;

@Controller
@RequestMapping("/ch04")
public class Ch04Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch04Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		return "ch04/content";
	}
	
	@PostMapping("/method1")
	public String method1() {
		return "redirect:/ch04/content";
	}
	
	@InitBinder("joinForm")
	public void joinFormSetValidate(WebDataBinder binder) {
		logger.info("실행");
		
//		binder.setValidator(new Ch04MemberJoinFormValidator());
		
		//필드 단위 validator을 추가해주기 
		binder.addValidators(
				new Ch04MemberIdValidator(),
				new Ch04MemberPasswordValidator(),
				new Ch04MemberEmailValidator(),
				new Ch04MemberTelValidator()
		);
	}
	
	/*
	 * 클라이언트(브라우저) 가 보낸 객체가 (파라미터)member
	 * 스프링에서 dto클래스의 소문자인 ch04member로 저장이 되어 있음.
	 * 요청처리가 끝날 때까지 내부에서 관리할 수 있도록 하는 것임.
	 * 그래서 위 메소드의 @InitBinder()안에 그 관리하는 객체이름을 적어주는 것임(연결) 
	 * */
	@PostMapping("/join")
	public String join(@ModelAttribute("joinForm") @Valid Ch04Member member, BindingResult bindingResult) {
		logger.info("실행");
		if(bindingResult.hasErrors()) { //validator클래스에서 rejectvalue가 하나라도 실행되었다. 
			logger.info("다시 입력폼 제공 + 에러 메시지");
			return "ch04/content";
		} else {
			logger.info("정상 가입");
			return "redirect:/";
		}
	}
	
	@InitBinder("loginForm")
	public void loginFormSetValidate(WebDataBinder binder) {
		logger.info("실행");
		 
		binder.addValidators(
				new Ch04MemberIdValidator(),
				new Ch04MemberPasswordValidator());
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute("loginForm") @Valid Ch04Member member, Errors errors) {
		logger.info("실행");
		if(errors.hasErrors()) {
			logger.info("다시 입력폼 제공 + 에러 메시지");
			//forward
			return "ch04/content";
		}else {
			logger.info("정상 요청 처리 후 응답 제공");
			//redirect 
			return "redirect:/";
		}
	}
}
