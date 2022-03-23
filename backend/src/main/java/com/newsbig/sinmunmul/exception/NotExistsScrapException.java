package com.newsbig.sinmunmul.exception;

public class NotExistsScrapException extends RuntimeException {
	public NotExistsScrapException() {
		super("사용자가 스크랩한 뉴스가 없습니다.");
	}
}