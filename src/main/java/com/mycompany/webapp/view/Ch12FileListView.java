package com.mycompany.webapp.view;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

@Component
public class Ch12FileListView extends AbstractView{

	private static final Logger logger = LoggerFactory.getLogger(Ch12FileListView.class);

	@Override
	protected void renderMergedOutputModel(
			Map<String, Object> model,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		logger.info("실행");
		//fileDir경로에 저장되어 있는 파일의 총 개수 및 파일 이름 목록 얻기
		String fileDir = "/Users/go/hyundai_itne/upload_files/";
		File file = new File(fileDir);
		String[] fileList = file.list();
		int totalFileNum = fileList.length;
		//응답 생성 및 보내기
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		
		JSONObject obj = new JSONObject();
		obj.put("totalFileNum", totalFileNum);
		//굳이 jsonarray안써도 되지만 일단 이렇게 
		JSONArray jsonarr = new JSONArray();
		for(String fileName : fileList) {
			jsonarr.put(fileName);
		}
		obj.put("fileList", jsonarr);
		String json = obj.toString();
		pw.println(json);
		
		pw.flush();
		pw.close();
		
	}

}
