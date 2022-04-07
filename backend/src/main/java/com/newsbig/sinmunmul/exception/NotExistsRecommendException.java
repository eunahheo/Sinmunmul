package com.newsbig.sinmunmul.exception;

public class NotExistsRecommendException extends RuntimeException{
	public NotExistsRecommendException() {
		super("키워드에 해당 하는 추천기사가 없습니다.");
	}
}
