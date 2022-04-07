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
public class RecomNewsDto implements Comparable<RecomNewsDto>{
	private long newsSeq;
	private double emotion;
	private String newsPhoto;
	private String newsTitle;
	private String newsSummary;
	
	@Override
	public int compareTo(RecomNewsDto o) {
		return -Double.compare(this.emotion,o.emotion);
	}
}
