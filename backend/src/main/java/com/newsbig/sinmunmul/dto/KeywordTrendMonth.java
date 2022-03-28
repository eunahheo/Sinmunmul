package com.newsbig.sinmunmul.dto;

import java.math.BigInteger;

import lombok.Data;

@Data
public class KeywordTrendMonth {
	private String label;
	private BigInteger count;

	public KeywordTrendMonth(String label, BigInteger count) {
		super();
		this.label = label;
		this.count = count;
	}
}
