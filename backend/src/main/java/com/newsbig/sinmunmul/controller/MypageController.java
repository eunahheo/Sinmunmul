package com.newsbig.sinmunmul.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsbig.sinmunmul.dto.PwdUpdateInfo;
import com.newsbig.sinmunmul.response.BaseResponseBody;
import com.newsbig.sinmunmul.service.MypageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/mypage")
@Api("마이페이지 컨트롤러 API")
public class MypageController {
	
	@Autowired
	private MypageService mypageService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PutMapping("/{user_seq}/updatePassword")
	@ApiImplicitParam(name = "user_seq", value = "user_seq")
	@ApiOperation(value = "비밀번호 수정", notes = "영어, 숫자, 특수문자(!@#$%^&*)를 포함한 8~16자리로 비밀번호를 수정할 수 있습니다.")
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "비밀번호 수정 성공"),
			  @ApiResponse(code = 400, message = "존재하지 않는 유저입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> updatePassword(@PathVariable("user_seq") int userSeq, @RequestBody PwdUpdateInfo pwdUpdateInfo) {
		String userPwd = pwdUpdateInfo.getUserPwd();
		String newUserPwd = pwdUpdateInfo.getNewUserPwd();
		
		if(!passwordEncoder.matches(userPwd, mypageService.getUserByUserSeq(userSeq).getUserPwd()))
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "비밀번호가 올바르지 않습니다."));
		
		String regx = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(newUserPwd);
		if(!matcher.matches())
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "비밀번호 형식이 올바르지 않습니다."));
		
		mypageService.updatePassword(userSeq, newUserPwd);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "비밀번호 수정 성공"));
	}
}