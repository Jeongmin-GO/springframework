package com.mycompany.webapp.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class Ch15Aspect7Around {
	private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect7Around.class);
	
	@Around("execution(public * com.mycompany.webapp.controller.Ch15Controller.runtimeCheck*(..))")
	public Object runtimeCheck(ProceedingJoinPoint joinPoint) {
		//---------------------------------------
		long start = System.nanoTime();
		//---------------------------------------
		Object result= null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//---------------------------------------
		long end = System.nanoTime();
		long howlong = end - start;
		String methodName = joinPoint.getSignature().toShortString(); //Pointcut의 순수 메소드명 
		logger.info(methodName+"실행 시간 : " + howlong + "ns");
		
		//현재 요청에 대한 정보를 갖고 있는 속성 
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		//controller에서 redirect해주기 때문에 content에 나타나지 않음
		HttpSession session = request.getSession();
		
		session.setAttribute("methodName", methodName);
		session.setAttribute("howlong", howlong);
		//---------------------------------------
		return result;
	}
}
