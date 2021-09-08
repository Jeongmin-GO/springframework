package com.mycompany.webapp.view;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.mycompany.webapp.controller.Ch12Controller;

@Component
public class Ch12FileDownloadView extends AbstractView {
	private static final Logger logger = LoggerFactory.getLogger(Ch12Controller.class);

	@Override
	protected void renderMergedOutputModel(
			Map<String, Object> model,
			HttpServletRequest request,
			HttpServletResponse response
			) throws Exception {
		logger.info("실행");
		
		String fileName = (String)model.get("fileName");
		String userAgent = (String)model.get("userAgent");
		
		//fileName을 이용해서 데이터를 가져온 것(via db)이 아래코드라고 가정
		//파일이름을 불러와서 확장명에 따라 contentType을 다르게..?
		String contentType = request.getServletContext().getMimeType(fileName);
		String originalFilename = fileName;
		String savedName = fileName;
		
		//응답 body의 데이터의 형식 설정
		response.setContentType(contentType);
		
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
