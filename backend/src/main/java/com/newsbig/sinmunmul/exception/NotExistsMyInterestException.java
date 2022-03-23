package com.newsbig.sinmunmul.exception;

public class NotExistsMyInterestException extends RuntimeException {
	public NotExistsMyInterestException() {
		super("사용자의 관심사가 존재하지 않습니다.");
	}
}