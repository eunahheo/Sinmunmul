package com.newsbig.sinmunmul.exception;

public class NotExistsInterestException extends RuntimeException{
	public NotExistsInterestException() {
		super("사용자가 등록한 관심사가 없습니다.");
	}
}
