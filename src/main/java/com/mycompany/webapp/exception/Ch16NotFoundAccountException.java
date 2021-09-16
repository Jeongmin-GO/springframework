package com.mycompany.webapp.exception;

public class Ch16NotFoundAccountException extends RuntimeException {
	
	public Ch16NotFoundAccountException() {
		
	}
	
	public Ch16NotFoundAccountException(String msg) {
		//부모로 메시지를 넘겨라
		super(msg);
	}
}
