package com.mycompany.webapp.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.webapp.dto.Ch04Member;

public class Ch04MemberIdValidator implements Validator {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch04MemberIdValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		logger.info("실행");
		boolean check = Ch04Member.class.isAssignableFrom(clazz);
		return check;
	}

	@Override
	public void validate(Object target, Errors errors) {
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
	}
	
}
