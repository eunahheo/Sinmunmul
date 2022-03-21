package com.newsbig.sinmunmul.exception;

public class NotExistsUserException extends RuntimeException {
	public NotExistsUserException() {
		super("존재하지 않는 회원입니다.");
	}
}