package com.mycompany.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch11City;
import com.mycompany.webapp.dto.Ch11Member;
import com.mycompany.webapp.dto.Ch11Skill;

@Controller
@RequestMapping("/ch11")
public class Ch11Controller {

	private static final Logger logger = LoggerFactory.getLogger(Ch10Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("실행");
		return "ch11/content";
	}
	
	//디폴트값을 정해주기 위함
	@GetMapping("/form1")
	public String form1(/*Model model*/ @ModelAttribute("member") Ch11Member member) {
		logger.info("실행");
//		String defaultNation = "한국";
//		model.addAttribute("defaultNation", defaultNation);
		
		member.setMnation("한국");
		return "ch11/form1";
	}
	
	//폼에 입력된 데이터를 받을 목적 
	@PostMapping("/form1")
	public String handleForm1(@ModelAttribute("member") Ch11Member member) {
		logger.info("실행");
		logger.info(member.getMid());
		logger.info(member.getMname());
		logger.info(member.getMnation());
		logger.info(member.getMpassword());
		return "redirect:/ch11/content";
	}
	
	@GetMapping("/form2")
	public String form2(@ModelAttribute("member") Ch11Member member, Model model) {
		logger.info("실행");
		//db에서 얻어왔다고 가정
		//ch11member에 private String mtype; 추가
		//드롭다운리스트의 항목을 추가할 목적
		List<String> typeList = new ArrayList<>();
		typeList.add("일반회원");
		typeList.add("기업회원");
		typeList.add("헤드헌터회원");
		
		model.addAttribute("typeList", typeList);
		
		//기본 선택 항목을 설정
		member.setMtype("헤드헌터회원");
		
		//jobList받아오기 (db에서 받아온다고 가정)
		List<String> jobList = new ArrayList<>();
		jobList.add("학생");
		jobList.add("개발자");
		jobList.add("디자이너");
		model.addAttribute("jobList", jobList);
		member.setMjob("개발자"); //디폴트값 설정 
		
		//드롭다운리스트의 항목을 추가할 목적
		List<Ch11City> cityList = new ArrayList<>();
		cityList.add(new Ch11City(1, "서울"));
		cityList.add(new Ch11City(2, "부산"));
		cityList.add(new Ch11City(3, "제주"));
		model.addAttribute("cityList", cityList);
		
		member.setMcity(3);
		
		return "ch11/form2";
	}
	
	@GetMapping("/form3")
	public String form3(@ModelAttribute("member") Ch11Member member, Model model) {
		logger.info("실행");
		
		List<String> languageList = new ArrayList<>();
		languageList.add("C");
		languageList.add("Python");
		languageList.add("Java");
		languageList.add("JavaScript");
		model.addAttribute("languageList", languageList);
		
		//기본 체크되어있는 항목은 python과 js 여러개로 받아주기 위해 string배열로 객체 생성
		member.setMlanguage(new String[] {"Python", "JavaScript"});
		
		List<Ch11Skill> skillList = new ArrayList<>();
		skillList.add(new Ch11Skill(1, "SpringFramework"));
		skillList.add(new Ch11Skill(2, "SpringBoot"));
		skillList.add(new Ch11Skill(3, "Vue"));
		model.addAttribute("skillList", skillList);
			
		member.setMskill(new int[] {1, 3});
		
		return "ch11/form3";
	}
}
