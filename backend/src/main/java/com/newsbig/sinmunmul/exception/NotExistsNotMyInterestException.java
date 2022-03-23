package com.newsbig.sinmunmul.exception;

public class NotExistsNotMyInterestException extends RuntimeException {
	public NotExistsNotMyInterestException() {
		super("등록할 수 있는 관심사가 존재하지 않습니다.");
	}
}