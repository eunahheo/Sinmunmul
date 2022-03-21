package com.newsbig.sinmunmul.exception;

public class UnavailableKakaoAccessTokenException extends RuntimeException {
	public UnavailableKakaoAccessTokenException() {
		super("유효하지 않은 토큰입니다.");
	}
}