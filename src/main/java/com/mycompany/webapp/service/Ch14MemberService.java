package com.mycompany.webapp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.Ch14MemberDao;
import com.mycompany.webapp.dto.Ch14Member;

@Service
public class Ch14MemberService {
	@Resource
	private Ch14MemberDao memberDao;
	//열거타입 선언
	public enum JoinResult{
		SUCCESS,
		FAIL,
		DUPLICATED
	}

	//회원 가입을 처리하는 비즈니스 로직 
	public JoinResult join(Ch14Member member) {
		try {
			//이미 가입된 아이디인지 확인
			Ch14Member dbMember = memberDao.selectByMid(member.getMid()); //select * from member where mid=?
			
			//db에 회원 정보를 저장
			if(dbMember == null) { //이미 가입된 아이디가 없으면 회원가입 진행
			
				memberDao.insert(member);
				return JoinResult.SUCCESS;
			}else {
				return JoinResult.DUPLICATED;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return JoinResult.FAIL;
		}
		
	}
	
}
