package com.newsbig.sinmunmul.dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class KeywordTrendWeek {
	private String label;
	private String end;
	private String date;
	private BigInteger count;

	public KeywordTrendWeek(String label, String end, String date, BigInteger count) {
		super();
		this.label = label;
		this.end = end;
		this.date = date;
		this.count = count;
	}

}
