package com.newsbig.sinmunmul.exception;

public class NotExistsNewsException extends RuntimeException {
	public NotExistsNewsException() {
		super("해당 번호의 뉴스가 존재하지 않습니다.");
	}
}