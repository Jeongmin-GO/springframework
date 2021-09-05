package com.mycompany.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ch09")
public class Ch09Controller {

	private static final Logger logger = LoggerFactory.getLogger(Ch09Controller.class);
	
	@RequestMapping("/content")
	public String content() {
		logger.info("실행");
		return "ch09/content";
	}
	
	//content.jsp에서 해당 input name과 인자 변수명이 동일해야 함. 
	@PostMapping("/fileupload")
	public String fileUpload(String title, String desc, MultipartFile attach) throws IllegalStateException, IOException {
		logger.info("실행");
		
		//문자 파트 내용 읽기
		logger.info("title : " + title);
		logger.info("desc : " + desc);
		
		//파일 파트 내용 읽기
		//클라이언트측에서 보낸 파일 이름, 
		logger.info("file original name : " + attach.getOriginalFilename());
		logger.info("file contentType : " + attach.getContentType());
		logger.info("file size : " + attach.getSize());
		
		//파일 파트 데이터를 서버의 파일로 저장
		//저장 이름과 originalname은 다르게 만들어주는 게 좋음
		String savedname = new Date().getTime() +"-"+attach.getOriginalFilename(); //현재시간을 붙임 
		File file = new File("/Users/go/hyundai_itne/upload_files/"+savedname);
		attach.transferTo(file);
		
		return "redirect:/ch09/content";
	}
	
	@PostMapping(value="/fileuploadAjax", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String fileUploadAjax(String title, String desc, MultipartFile attach) throws IllegalStateException, IOException {
		logger.info("실행");
		
		//문자 파트 내용 읽기
		logger.info("title : " + title);
		logger.info("desc : " + desc);
		 
		logger.info("file original name : " + attach.getOriginalFilename());
		logger.info("file contentType : " + attach.getContentType());
		logger.info("file size : " + attach.getSize());
		
		String savedname = new Date().getTime() +"-"+attach.getOriginalFilename(); //현재시간을 붙임 
		File file = new File("/Users/go/hyundai_itne/upload_files/"+savedname);
		attach.transferTo(file);
		
		JSONObject obj = new JSONObject();
		obj.put("result", "success");
		obj.put("savedname", savedname);
		String json = obj.toString();
		
		return json;
	}
	
	/* 별로 좋은 방식이 아님 
	 * 1. 응답 바디의 데이터 형식 고정 
	 * 2. @GetMapping(value="/filedownload", produces="image/jpeg")
	 * 
	@GetMapping(value="/filedownload", produces="image/jpeg")
	@ResponseBody
	public byte[] fileDownload(String savedname) throws Exception{
		logger.info("실행");
		
		String filePath = "/Users/go/hyundai_itne/upload_files/"+savedname;
		InputStream is = new FileInputStream(filePath);
		byte[] data = IOUtils.toByteArray(is);
		return data;
	}
	*/
	
	@GetMapping("/filedownload")
	public void fileDownload(int fileNo, HttpServletResponse response, @RequestHeader("User-Agent") String userAgent) throws Exception{
		logger.info("실행");
		//fileNo를 이용해서 데이터를 가져온 것(via db)이 아래코드라고 가정
		String contentType = "image/jpeg";
		String originalFilename = "눈내리는마을.jpg";
		String savedName = "1630656700929-눈내리는마을.jpg";
		
		
		//응답 body의 데이터의 형식 설정
		response.setContentType(contentType);
		
		//http헤더에는 아스키문자만 들어가는데 한글은 아스키문자에 포함이 안됨
		//파일명이 한글일 때 문제가 생기기 때문에
		//한글 파일명을 변환해서 제대로 나오게끔 하려면?
		//originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1"); //UTF-8로 인코딩후 바이트 배열 -> iso~
		
		//브라우저별로 한글 파일명을 변환
		if(userAgent.contains("Trident") || userAgent.contains("MSIE")) {//IE11이하 버전일 경우
			originalFilename = URLEncoder.encode(originalFilename, "UTF-8");
		}else {//크롬, 엣지, 사파리 브라우저에서 한글 파일명을 변환
			originalFilename = new String(originalFilename.getBytes("UTF-8"), "ISO-8859-1"); //UTF-8로 인코딩후 바이트 배열 -> iso~			
		}
		
		//파일을 다운로드하도록 설정
		//response.setHeader코드는 이렇게 고정 (안에 파일명 제외)
		response.setHeader("Content-Disposition", "attachment; filename=\""+originalFilename+"\"");
		
		//파일로부터 데이터를 읽는 입력스트림 생성
		String filePath = "/Users/go/hyundai_itne/upload_files/"+savedName;
		InputStream is = new FileInputStream(filePath);
		
		//응답 body에 출력하는 출력스트림 얻기
		OutputStream os = response.getOutputStream();
		
		//입력 스트림 -> 출력 스트림
		FileCopyUtils.copy(is, os);
		is.close();
		os.flush();
		os.close();
	}
}
