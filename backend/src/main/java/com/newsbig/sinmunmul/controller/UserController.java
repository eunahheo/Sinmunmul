package com.newsbig.sinmunmul.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.newsbig.sinmunmul.dto.EmailDto;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.service.MailService;
import com.newsbig.sinmunmul.service.UserService;
import com.newsbig.sinmunmul.util.MailContentBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/user")
@Api("사용자 컨트롤러 API")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MailContentBuilder mailContentBuilder;
	
	@GetMapping
	@ApiOperation(value = "모든 사용자 조회", notes = "테스트용", response = List.class)
	public ResponseEntity<List<Map<String, Object>>> getInfo() {
		List<Map<String, Object>> userinfo = new ArrayList<>();
		userinfo = userService.userAllInfo();

		return new ResponseEntity<List<Map<String, Object>>>(userinfo, HttpStatus.OK);
	}
	
	@PostMapping("/signin")
	@ApiOperation(value = "회원가입", notes = "사용자가 입력한 회원정보를 등록한다.", response = String.class)
	public ResponseEntity<String> regist(@RequestBody User user) {
		
		
		
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	
	@GetMapping("/cert")
	@ApiOperation(value = "회원가입", notes = "사용자가 입력한 회원정보를 등록한다.", response = String.class)
	public ResponseEntity<Map<String, String>> emailCertify(String email) {
		Map<String, String> result = new HashMap<>();
		
		String certKey = mailService.generateKey();
		String message = mailContentBuilder.build(certKey);
		mailService.sendMail(new EmailDto(email, "[신문물] 이메일 인증", message));
		
		result.put("certKey", certKey);
		
		return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
	}
	
}
