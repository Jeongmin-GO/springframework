package com.mycompany.webapp.dto;

import lombok.Data;

@Data
public class Ch11City {
	private int code; //form2에서의 value
	private String label; //form2에서 값 
	
	//아래 생성자를 만들어주니 롬복의 기본 생성자가 사라져 직접 선언 
	public Ch11City() {
		
	}
	
	public Ch11City(int code, String label) {
		this.code = code;
		this.label = label;
	}
	
	
}
