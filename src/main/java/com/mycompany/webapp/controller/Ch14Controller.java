package com.mycompany.webapp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.webapp.dto.Ch14Member;
import com.mycompany.webapp.service.Ch14MemberService;
import com.mycompany.webapp.service.Ch14MemberService.JoinResult;


@Controller
@RequestMapping("/ch14")
public class Ch14Controller {
	
	//DataSource는 인터페이스임
	@Resource
	private DataSource dataSource;
	
	private static final Logger logger = LoggerFactory.getLogger(Ch14Controller.class);
	@RequestMapping("/content")
	public String content() {
		return "ch14/content";
	}
	
	@GetMapping("/testConnectToDB")
	public String testConnetToDB() throws SQLException {
		//커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		logger.info("연결 성공");
		
		//커넥션 풀로 연결 객체를 반납하기
		conn.close();
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testInsert")
	public String testInsert() throws Exception {
		//커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		
		try {
			//작업 처리
			String sql = "INSERT INTO BOARD VALUES(SEQ_BNO.NEXTVAL, ?, ?, SYSDATE, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "오늘은 월요일");
			pstmt.setString(2, "오류 나지마ㅠ");
			pstmt.setString(3, "user");
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//커넥션 풀로 연결 객체를 반납하기
		//예외가 나던 안나던 커넥션 풀 객체는 반납해야 함 
		conn.close();
		return "redirect:/ch14/content";
	}
	
	@GetMapping("/testSelect")
	public String testSelect() throws Exception {
		//커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		
		try {
			//작업 처리
			String sql = "SELECT bno, btitle, bcontent, bdate, mid FROM board";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int bno = rs.getInt("bno");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Date bdate = rs.getDate("bdate");
				String mid = rs.getString("mid");
				logger.info("select db : " + bno + "\t" + btitle + "\t" + bcontent + "\t" + bdate + "\t");
			}
			rs.close();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//커넥션 풀로 연결 객체를 반납하기
		//예외가 나던 안나던 커넥션 풀 객체는 반납해야 함 
		conn.close();
		return "redirect:/ch14/content";
	}
	@GetMapping("/testUpdate")
	public String testUpdate() throws Exception {
		//커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		
		try {
			//작업 처리
			String sql = "UPDATE board SET btitle=?, bcontent=? WHERE bno=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "이제 곧 점심시간"); //1번째 물음표
			pstmt.setString(2, "맛있는 거 먹어야지"); //2번째 물음표
			pstmt.setInt(3, 1); //3번째 물음표
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//커넥션 풀로 연결 객체를 반납하기
		//예외가 나던 안나던 커넥션 풀 객체는 반납해야 함 
		conn.close();
		return "redirect:/ch14/content";
	}
	@GetMapping("/testDelete")
	public String testDelete() throws Exception {
		//커넥션 풀에서 연결 객체 하나를 가져오기
		Connection conn = dataSource.getConnection();
		
		try {
			//작업 처리
			String sql = "DELETE FROM board WHERE bno=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0); //1번째 물음표
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//커넥션 풀로 연결 객체를 반납하기
		//예외가 나던 안나던 커넥션 풀 객체는 반납해야 함 
		conn.close();
		return "redirect:/ch14/content";
	}
	
   @Resource
   private Ch14MemberService memberService;
   
   @GetMapping("/join")
   public String joinForm() {
	   return "ch14/joinForm";
   }
   
   @PostMapping("/join")
   public String join(Ch14Member member, Model model) {
      //유효성 검사 @Valid
      // 필요한 데이터 서버에서 보충 (데이터 가공)
      member.setMenabled(1);
      member.setMrole("ROLE_USER");
      // 서비스에 객체를 넘겨줌으로서 원래 하려던 회원가입기능 처리
      JoinResult jr = memberService.join(member);
      if(jr == JoinResult.SUCCESS) {
         return "redirect:/ch14/content";
      }else if(jr == JoinResult.DUPLICATED) {
    	  model.addAttribute("error", "중복된 아이디가 있습니다.");
    	  return "ch14/joinForm";
      }else{
    	 model.addAttribute("error", "회원 가입 실패했습니다. 다시 시도해주세요.");
         return "ch14/joinForm";
      }
   }
   @GetMapping("/login")
   public String loginForm() {
	   return "ch14/loginForm";
   }
   
   @PostMapping("/login")
   public String login(Ch14Member member, Model model) {
	   return "redirect:/ch14/content";
   }
}
