package com.newsbig.sinmunmul.exception;

public class NotRecommendException extends RuntimeException{
	public NotRecommendException() {
		super("키워드에 해당 하는 추천기사가 없습니다.");
	}
}
