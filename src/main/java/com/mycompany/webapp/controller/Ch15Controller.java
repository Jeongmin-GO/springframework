package com.mycompany.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.webapp.dto.Ch14Board;
import com.mycompany.webapp.dto.Ch14Member;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.service.Ch14BoardService;
import com.mycompany.webapp.service.Ch14MemberService;
import com.mycompany.webapp.service.Ch14MemberService.loginResult;

@Controller
@RequestMapping("/ch15")
public class Ch15Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch15Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("실행");
		return "ch15/content";
	}
	
	@RequestMapping("/before")
	public String before() {
		logger.info("실행");
		return "redirect:/ch15/content";
	}
	
	@RequestMapping("/after")
	public String after() {
		logger.info("실행");
		return "redirect:/ch15/content";
	}
	
	@RequestMapping("/afterReturning")
	public String afterReturning() {
		logger.info("실행");
		return "redirect:/ch15/content";
	}
	@RequestMapping("/afterThrowing")
	public String afterThrowing() {
		logger.info("실행");
		if(true) { //최소한 컴파일 오류는 안나게 하기 위해서
			throw new RuntimeException("테스트 예외입니다."); //예외 메시지
		}
		return "redirect:/ch15/content";
	}
	@RequestMapping("/around")
	public String around() {
		logger.info("실행");
		return "redirect:/ch15/content";
	}
	
	//runtimeCheck test를 위함 
	@Resource
	private Ch14BoardService bservice;
	
	@RequestMapping("/runtimeCheck")
	public String runtimeCheck() {
		logger.info("실행");
		//1페이지 글을 불러오는데 걸리는 시간을 계산하기 위함
		Pager pager = new Pager(10, 5, bservice.getTotalBoard(), 1);
		List<Ch14Board> board = bservice.getBoards(pager);
		return "redirect:/ch15/content";
	}
	@RequestMapping("/authCheck")
	public String authCheck() {
		logger.info("실행");
		return "redirect:/ch15/content";
	}
	
	@GetMapping("/login")
	public String loginForm() {
		logger.info("실행");
		return "ch15/loginForm";
	}
	
	@Resource
	private Ch14MemberService mservice;
	
	@PostMapping("/login")
	public String login(Ch14Member member, Model model, HttpSession session) {
		logger.info("실행");
		loginResult ls = mservice.login(member);
		if(ls == loginResult.SUCCESS) {
			session.setAttribute("sessionMid", member.getMid());
			return "redirect:/";
		}else if(ls == loginResult.ID_FAIL) {
			model.addAttribute("error", "아이디가 존재하지 않습니다.");
			return "ch15/loginForm";
		}else if(ls==loginResult.PW_FAIL) {
			model.addAttribute("error", "비밀번호를 다시 입력해주세요.");
			return "ch15/loginForm";			
		}else {
			model.addAttribute("error", "로그인 실패했습니다. 다시 시도해주세요.");
			return "ch15/loginForm";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("실행");
		session.removeAttribute("sessionMid");
		//session.invalidate();
		return "redirect:/";
	}
	//jsp파일 ch14/boardList
	@GetMapping("/boardList1")
	public String boardList1(Model model) {
		logger.info("실행2");
		Pager pager = new Pager(5, 5, bservice.getTotalBoard(), 1);
		List<Ch14Board> boards = bservice.getBoards(pager);
		model.addAttribute("boards", boards);
		return "ch15/boardList";
	}
	@GetMapping(value="/boardList2", produces="application/json;charset=UTF-8")
	@ResponseBody
	public String boardList2(Model model) {
		logger.info("실행2");
		Pager pager = new Pager(5, 5, bservice.getTotalBoard(), 1);
		List<Ch14Board> boards = bservice.getBoards(pager);
//		model.addAttribute("boards", boards);
		JSONObject obj = new JSONObject();
		obj.put("result", "success");
//		obj.put("boards", boards);
		
		//jsp에서 board.bdate를 원하는 형식으로 포맷할 수 없어서 포맷해서 jsonarray에 넣어주기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		JSONArray array = new JSONArray();
		for(Ch14Board board: boards) {
			JSONObject boardobj = new JSONObject();
			boardobj.put("bno", board.getBno());
			boardobj.put("btitle", board.getBtitle());
			boardobj.put("bdate", sdf.format(board.getBdate()));
			boardobj.put("mid", board.getMid());
			array.put(boardobj);
		}
		obj.put("boards", array);
		
		String json = obj.toString();
		return json;
	}
}
