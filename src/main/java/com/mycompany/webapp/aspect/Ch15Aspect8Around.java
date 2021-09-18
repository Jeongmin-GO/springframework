package com.mycompany.webapp.aspect;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class Ch15Aspect8Around {
	private static final Logger logger = LoggerFactory.getLogger(Ch15Aspect8Around.class);
	
	@Around("execution(public * com.mycompany.webapp.controller.Ch15Controller.boardList1*(..))")
	public Object loginCheckAdvice1(ProceedingJoinPoint joinPoint) throws Throwable{
		//전처리만 있는 경우
		
		//현재 요청에 대한 정보를 갖고 있는 속성 
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		//controller에서 redirect해주기 때문에 content에 나타나지 않음
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("sessionMid");
		
		if(mid == null) {
			return "ch15/authFail";
		}else {
			Object result = joinPoint.proceed();
//			request.setAttribute("loginCheck", true);
			return result;
		}
		
	}
	@Around("execution(public * com.mycompany.webapp.controller.Ch15Controller.boardList2*(..))")
	public Object loginCheckAdvice2(ProceedingJoinPoint joinPoint) throws Throwable{
		//전처리만 있는 경우
		
		//현재 요청에 대한 정보를 갖고 있는 속성 
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		//controller에서 redirect해주기 때문에 content에 나타나지 않음
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("sessionMid");
		
		//2번째 방법
		if(mid == null) {
			JSONObject obj = new JSONObject();
			obj.put("result", "authFail");
			String json = obj.toString();
			HttpServletResponse response = sra.getResponse();
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.println(json);
			pw.flush();
			pw.close();
			return null;
			
		}
			Object result = joinPoint.proceed();
			return result;
	}
}
