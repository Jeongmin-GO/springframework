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
	
	@Around("execution(public * com.mycompany.webapp.controller.Ch15Controller.board*(..))")
	public Object loginCheckAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		//전처리만 있는 경우
		
		//현재 요청에 대한 정보를 갖고 있는 속성 
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = sra.getRequest();
		//controller에서 redirect해주기 때문에 content에 나타나지 않음
		HttpSession session = request.getSession();
		String mid = (String)session.getAttribute("sessionMid");
		
		if(mid == null) {
			//로그인이 안되어있으면 jsp에 json을 전송
			JSONObject obj = new JSONObject();
			obj.put("result", "loginNeed");
			String json = obj.toString();
			
			//응답보내기
			HttpServletResponse response = sra.getResponse();
			response.setContentType("application/json;charset=UTF-8");
			//json은 바이너리 데이터가 아닌 문자열이기 때문에 IO stream을 사용해서 응답을 스트림에 실어줌
			PrintWriter pw = response.getWriter();
			pw.println(json);
			pw.flush();
			pw.close();
			//막바로 응답을 만들어냈기 때문에 null로 리턴
			return null;
			
		}else {
			//controller의 return에 적혀져 있는 jsp의 뷰이름을 리턴하게 되는 것
			Object result = joinPoint.proceed();
			return result;
		}
	}
}
