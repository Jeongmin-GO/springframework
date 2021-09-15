package com.mycompany.webapp.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Ch15Aspect4AfterReturning {
	private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect4AfterReturning.class);
	
	//핵심 코드가 정상적으로 끝났을 때 실행
	//왜 매개값이 필요할까? -> 핵심코드에서 리턴값이 있을 때 그 값을 받아주기위함
	//advice의 매개변수는 2개이상일 때 속성명을 지정해줘야함.
	@AfterReturning(
			pointcut = "execution(public * com.mycompany.webapp.controller.Ch15Controller.afterReturning*(..))",
			returning = "returnValue"
	)
	public void method(String returnValue) {
		logger.info("실행");
		logger.info("리턴값 : " + returnValue);
	}

}
