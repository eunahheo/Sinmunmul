package com.newsbig.sinmunmul.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Service
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SigninDto {
	private String userEmail;
	private String userPwd;
	private String userGender;
	private int userAge;
	private List<Integer> interest = new ArrayList<>();
}
