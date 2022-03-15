package com.newsbig.sinmunmul.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsbig.sinmunmul.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@Api("사용자 컨트롤러 API")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	@ApiOperation(value = "모든 사용자 조회", notes = "테스트용", response = List.class)
	public ResponseEntity<List<Map<String, Object>>> getInfo() {
		List<Map<String, Object>> userinfo = new ArrayList<>();
		userinfo = userService.userAllInfo();

		return new ResponseEntity<List<Map<String, Object>>>(userinfo, HttpStatus.OK);
	}
}
