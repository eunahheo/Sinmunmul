package com.newsbig.sinmunmul.dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class KeywordTrendWeek {
	private String start;
	private String end;
	private String date;
	private BigInteger count;
	public KeywordTrendWeek(String start, String end, String date, BigInteger count) {
		super();
		this.start = start;
		this.end = end;
		this.date = date;
		this.count = count;
	}
}
