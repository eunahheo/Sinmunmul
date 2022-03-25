package com.newsbig.sinmunmul.dto;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@Service
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class TodayNewsDto {
	private String codeGroupValue;
	private int num;
}
