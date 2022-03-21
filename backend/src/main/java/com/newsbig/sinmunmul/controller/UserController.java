package com.newsbig.sinmunmul.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsbig.sinmunmul.dto.EmailDto;
import com.newsbig.sinmunmul.dto.SigninDto;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.exception.NotExistsUserException;
import com.newsbig.sinmunmul.response.BaseResponseBody;
import com.newsbig.sinmunmul.service.MailService;
import com.newsbig.sinmunmul.service.UserService;
import com.newsbig.sinmunmul.util.MailContentBuilder;
import com.newsbig.sinmunmul.util.TimeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	
	@PostMapping("/signin")
	@ApiOperation(value = "회원가입", notes = "사용자가 입력한 회원정보를 등록한다.", response = BaseResponseBody.class)
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "회원가입 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> regist(@RequestBody SigninDto signInDto) {
		String now = TimeUtils.curTime();
		
		User user = User.builder()
				.userEmail(signInDto.getUserEmail())
				.userPwd(signInDto.getUserPwd())
				.userGender(signInDto.getUserGender())
				.userAge(signInDto.getUserAge())
				.regDt(now)
				.regId(signInDto.getUserEmail())
				.modDt(now)
				.modId(signInDto.getUserEmail()).build();
		
		try {
			if(userService.getUserByEmail(signInDto.getUserEmail())!=null);
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 가입된 이메일입니다."));
		}
		catch(NotExistsUserException e) {
			userService.signin(user);
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "회원가입 성공"));
	}
	
	@GetMapping("/cert")
	@ApiOperation(value = "회원가입", notes = "사용자가 입력한 회원정보를 등록한다.", response = String.class)
	public ResponseEntity<Map<String, String>> emailCertify(@RequestParam String email) {
		Map<String, String> result = new HashMap<>();
		
		String certKey = mailService.generateKey();
		String message = mailContentBuilder.build(certKey);
		mailService.sendMail(new EmailDto(email, "[신문물] 이메일 인증", message));
		
		result.put("certKey", certKey);
		
		return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
	}
	
}
