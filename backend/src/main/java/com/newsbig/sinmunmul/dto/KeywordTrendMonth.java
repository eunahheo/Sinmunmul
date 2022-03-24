package com.newsbig.sinmunmul.dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class KeywordTrendMonth {
	private Integer month;
	private BigInteger count;

	public KeywordTrendMonth(Integer month, BigInteger count) {
		super();
		this.month = month;
		this.count = count;
	}
}
