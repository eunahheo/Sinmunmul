package com.newsbig.sinmunmul.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.UnavailableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsbig.sinmunmul.dto.EmailDto;
import com.newsbig.sinmunmul.dto.SigninDto;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.exception.NotExistsUserException;
import com.newsbig.sinmunmul.response.AdvancedResponseBody;
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
			userService.signIn(user);
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "회원가입 성공"));
	}
	
	@GetMapping("/cert")
	@ApiOperation(value = "이메일 인증 코드 생성", notes = "이메일 인증코드를 생성하고, 사용자가 입력한 이메일로 이메일 인증코드를 보낸다.", response = String.class)
	public ResponseEntity<Map<String, String>> emailCertify(@RequestParam String email) {
		Map<String, String> result = new HashMap<>();
		
		String certKey = mailService.generateKey();
		String message = mailContentBuilder.build(certKey);
		mailService.sendMail(new EmailDto(email, "[신문물] 이메일 인증", message));
		
		result.put("certKey", certKey);
		
		return new ResponseEntity<Map<String,String>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/kakao/info")
	@ApiOperation(value = "카카오 계정 사용자 정보 반환", notes = "AccessToken을 활용해 카카오 서버에서 사용자 정보를 받아온다.", response = AdvancedResponseBody.class)
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "카카오 계정 정보 조회 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류"),
			  @ApiResponse(code = 401, message = "토큰이 잘못되었거나, 만료되어 유효하지 않아 발생하는 오류"),
			  @ApiResponse(code = 501, message = "카카오 계정 정보 제공 비동의로 정보 조회를 할 수 없는 경우")
			})
	public ResponseEntity<? extends AdvancedResponseBody> getKakaoUserInfo(@RequestHeader @ApiParam(value = "카카오 인가토드로 발급받은 accessToken") String accessToken) {
		Map<String, String> result = new HashMap<>();
		
		try {
			result = userService.getKakaoUserInfo(accessToken);
			if(result.get("responseCode").equals("401")) 
				return ResponseEntity.status(401).body(AdvancedResponseBody.of(401, "만료된 토큰입니다.", result));
			if(result.get("responseCode").equals("400")) 
				return ResponseEntity.status(400).body(AdvancedResponseBody.of(400, "잘못된 요청입니다.", result));
			return ResponseEntity.status(200).body(AdvancedResponseBody.of(200, "회원정보 조회 성공", result));
		}
		catch(NullPointerException e){
			return ResponseEntity.status(501).body(AdvancedResponseBody.of(501, "카카오 계정 정보를 읽어올 수 없습니다.", result));
		}
	}
}
