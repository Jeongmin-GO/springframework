package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.Ch14BoardDao;
import com.mycompany.webapp.dto.Ch14Board;
import com.mycompany.webapp.dto.Pager;

@Service
public class Ch14BoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(Ch14BoardService.class);
	
	//인터페이스 
	@Resource
	private Ch14BoardDao dao;
	
	//게시물 목록
	public List<Ch14Board> getBoards(Pager pager) {
		return dao.selectByPage(pager);
	}
	
	//bno에 해당하는 하나의 게시물 상세정보 가져오기 
	public Ch14Board getBoard(int bno){
		return dao.selectByBno(bno);
	}
	
	//총 게시물 수
	public int getTotalBoard() {
		return dao.count();
	}
	
	//게시물 작성
	public void writeBoard(Ch14Board board) {
		dao.insert(board);
	}
	
	//게시물 삭제
	public void deleteBoard(int bno) {
		dao.deleteByBno(bno);
	}
	
	//게시물 수정
	public void updateBoard(Ch14Board board) {
		dao.update(board);
	}
}
