package com.mycompany.webapp.controller;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Controller
@RequestMapping("/ch05")
public class Ch05Controller {
	private static final Logger logger = LoggerFactory.getLogger(Ch05Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		return "ch05/content";
	}
	
	
	/*헤더에 대한 모든 정보를 받아올 수 있음 */
	@GetMapping("/getHeaderValue")
	public String getHeaderValue(HttpServletRequest request) {
		logger.info("실행");
		
		//시작행에 실려서 나옴 (ppt 30)
		//contextroot이후의 경로를 뽑아내는 것 : getRequestURI()
		logger.info("method : " +request.getMethod()); //method 출력 
		logger.info("requestURI: " + request.getRequestURI());
		logger.info("client IP : " +request.getRemoteAddr()); //요청한 브라우저를 사용한 pc의 ip주소 (client의 ip주소) 
		logger.info("contextRoot : "+request.getContextPath());
		
		String userAgent = request.getHeader("User-Agent");
		logger.info("user-Agent : " + userAgent);
		if(userAgent.contains("Windows NT")) {
			logger.info("client OS : Windows ");
		} else if(userAgent.contains("Macintosh")) {
			logger.info("client OS : MAC OS ");
		}
		
		if(userAgent.contains("Edg")) {
			logger.info("client browser : MS Edge");
		} else if(userAgent.contains("Chrome")) {
			logger.info("client browser : Chrome");
		}else if(userAgent.contains("Trident")) {
			logger.info("client browser : IE11");
		}else if(userAgent.contains("Safari")) {
			logger.info("client browser : Safari");
		}
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/createCookie")
	public String createCookie(HttpServletResponse response) {
		logger.info("실행");
		
		Cookie cookie = new Cookie("useremail", "jeongminjjang@naver.com"); // 쿠키의 값은 문자열으로만 저장이 가능하다 
		//localhost:8080/ 이하의 모든 경로에서 쿠키를 사용할 수 있게 하기 위함.
		cookie.setDomain("localhost"); //localhost면 전송 
		cookie.setPath("/"); // localhost/...면 전송 
		cookie.setMaxAge(30*60);//초단위 60초가 30번 >> 30분 :: 이 시간동안에만 전송 
		cookie.setHttpOnly(true); //javascript에서 못 읽게 함.
		cookie.setSecure(true); //https:// 만 전송 
		
		response.addCookie(cookie);
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/getCookie1")
	public String getCookie1(@CookieValue("useremail") String email) {
		logger.info("실행");
		logger.info("useremail: " + email);
		return "redirect:/ch05/content";
	}
	@GetMapping("/getCookie2")
	public String getCookie2(HttpServletRequest request) {
		logger.info("실행");
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			String cookieName = cookie.getName();
			String cookieValue = cookie.getValue();
			
			//쿠키 이름이 username일 때만 value를 콘솔에 출력 
			if(cookieName.equals("useremail")) {
				logger.info(cookieValue);
				break;
			}
		}
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/createJsonCookie")
	public String createJsonCookie(HttpServletResponse response) throws Exception {
		logger.info("실행");
		
		//객체 {}
		//String json = "{\"userid\":\"fall\", \"useremail\":\"fall@company.com\",\"username\":\"fall\", \"usertel\":\"0101231234\"}";
		
		//위와 동일 
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userid", "fall");
		jsonObject.put("useremail", "fall@company.com");
		jsonObject.put("usertel", "0101231234");
		jsonObject.put("username", "홍길동");
		String json = jsonObject.toString();
	
		logger.info(json); //정상출력 
		json = URLEncoder.encode(json, "UTF-8");
		logger.info(json); //ascii 코드로 변환되어 출력 
		
//		Cookie cookie = new Cookie("user", json);
//		Cookie cookie = new Cookie("user", "홍길동");
		Cookie cookie = new Cookie("user", json);
		response.addCookie(cookie);
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/getJsonCookie")
	public String getJsonCookie(@CookieValue String user) {
		logger.info("실행");
		logger.info("user : " + user);
		JSONObject jsonObject = new JSONObject(user); //파싱 
		
		//파싱과정 
		String userid= jsonObject.getString("userid");
		logger.info("userid : " + userid);
		
		String usertel= jsonObject.getString("usertel");
		logger.info("usertel : " + usertel);
		
		String useremail= jsonObject.getString("useremail");
		logger.info("useremail : " + useremail);
		
		String username= jsonObject.getString("username");
		logger.info("username : " + username);
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/createJwtCookie")
	public String createJwtCookie(HttpServletResponse response) throws Exception {
		logger.info("실행");
		
		String userid="fall";
		String useremail = "fall@mycompany.com";
		String username = "홍길동";
		
		//Header
		JwtBuilder builder = Jwts.builder();
		builder.setHeaderParam("alg", "HS256");
		builder.setHeaderParam("typ", "JWT");
		
		 //~.getTime () : 1/1000초 단위,  1000 = 1초 
		builder.setExpiration(new Date(new Date().getTime() + 1000*60*30));
		
		//payload(claim) 추가 
		builder.claim("userid", userid);
		builder.claim("useremail", useremail);
		builder.claim("username", username);
		
		//verify signature(키)
		String secretkey = "abc12345";
		builder.signWith(SignatureAlgorithm.HS256, secretkey.getBytes("UTF-8"));
		
		String jwt = builder.compact();
		logger.info("jwt : " + jwt);
		
		Cookie cookie = new Cookie("jwt", jwt);
		response.addCookie(cookie);
		
		return "redirect:/ch05/content";
	}
	
	@GetMapping("/getJwtCookie")
	public String getJwtCookie(@CookieValue String jwt) throws Exception {
		logger.info("실행");
		logger.info(jwt);
		
		JwtParser parser = Jwts.parser();
		String secretkey = "abc12345";
		parser.setSigningKey(secretkey.getBytes("UTF-8"));
		
		//claims = payload(claim)안에 들어있는 개별 값들 
		Jws<Claims> jws = parser.parseClaimsJws(jwt);
		Claims claims = jws.getBody();
		String userid = claims.get("userid", String.class);
		String useremail = claims.get("useremail", String.class);
		String username  = claims.get("username", String.class);
		
		logger.info("userid : " + userid);
		logger.info("useremail : " + useremail);
		logger.info("username : " + username);
		
		return "redirect:/ch05/content";
	}
}
