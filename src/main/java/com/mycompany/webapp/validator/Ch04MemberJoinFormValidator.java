package com.mycompany.webapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.webapp.dto.Ch04Member;


public class Ch04MemberJoinFormValidator implements Validator{

	private static final Logger logger = LoggerFactory.getLogger(Ch04MemberJoinFormValidator.class);
	
	/*
	 * 검사할 수 있는 객체인지 확인하는 메서드
	 * return값이 true면 validate실행 
	 * */
	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("실행");
		boolean check = Ch04Member.class.isAssignableFrom(clazz);
		return check;
	}

	@Override
	public void validate(Object target, Errors errors) {
	//검사할 객체(supports로 받을), 객체 검사하고 에러나면 에러를 표시할 객체 
		logger.info("실행");
		Ch04Member member = (Ch04Member) target;
		
		//mid 검사
		//mid가 null || 공백제거했더니 ""라면 
		if(member.getMid()==null || member.getMid().trim().equals("")) {
			errors.rejectValue("mid", "errors.mid.required");
			//"mid"는 dto에 있는 변수명과 동일해야 함 
			//"errors.mid.required"는 messages의 해당 키 값 
		} else {
			if(member.getMid().length()< 4) {
				//앞 두 개는 위와 동일
				//new Object[] {4} : ch04_error에서 해당 키 값의 값 {}에 동적 바인딩 
				//네번째 인자 : "errors.mid.minlength"가 없을 경우 나타날 메시지 
				errors.rejectValue("mid", "errors.mid.minlength", new Object[] {4}, "");
			}
		}
		
		//mpassword 검사 
		if(member.getMpassword() == null || member.getMpassword().trim().equals("")) {
			errors.rejectValue("mpassword", "errors.mpassword.required");
		}else {
			if(member.getMpassword().length()< 8) {
				errors.rejectValue("mpassword", "errors.mpassword.minlength", new Object[] {8}, "");
			}
		}
		
		//memail 검사
		if(member.getMemail() == null || member.getMemail().trim().equals("")) {
			errors.rejectValue("memail", "errors.memail.required");
		}else {
			String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(member.getMemail());
			
			if(!matcher.matches()) {// matcher.matches() => true : 이메일이 올바른 형식 , false: 올바른 형식 아님 
				errors.rejectValue("memail", "errors.memail.invalid");
			}
		}
		
		//mtel 검사
		if(member.getMtel() == null || member.getMtel().trim().equals("")) {
			errors.rejectValue("mtel", "errors.mtel.required");
		}else {
			String regex = "^\\d{3}-\\d{3,4}-\\d{4}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(member.getMtel());
			
			if(!matcher.matches()) {
				errors.rejectValue("mtel", "errors.mtel.invalid");
			}
		}

	}
	
}
