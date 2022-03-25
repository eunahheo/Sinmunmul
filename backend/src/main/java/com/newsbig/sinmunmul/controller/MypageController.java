package com.newsbig.sinmunmul.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.dto.PwdUpdateDto;
import com.newsbig.sinmunmul.repository.UserRepository;
import com.newsbig.sinmunmul.response.AdvancedResponseBody;
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
	@Autowired
	UserRepository userRepository;
	
	@PutMapping("/{user_seq}/updatePassword")
	@ApiImplicitParam(name = "user_seq", value = "user_seq")
	@ApiOperation(value = "비밀번호 수정", notes = "영어, 숫자, 특수문자(!@#$%^&*)를 포함한 8~16자리로 비밀번호를 수정할 수 있습니다.")
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "비밀번호 수정 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> updatePassword(@PathVariable("user_seq") int userSeq, @RequestBody PwdUpdateDto pwdUpdateDto) {
		String userPwd = pwdUpdateDto.getUserPwd();
		String newUserPwd = pwdUpdateDto.getNewUserPwd();
		
		if(!passwordEncoder.matches(userPwd, userRepository.getById(userSeq).getUserPwd()))
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "비밀번호가 올바르지 않습니다."));
		
		// 비밀번호 유효성 검사
		String regx = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(newUserPwd);
		if(!matcher.matches())
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "비밀번호 형식이 올바르지 않습니다."));
		
		mypageService.updatePassword(userSeq, newUserPwd);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "비밀번호 수정 성공"));
	}
	
	@PutMapping("/{user_seq}/updateInterest")
	@ApiImplicitParam(name = "user_seq", value = "user_seq")
	@ApiOperation(value = "관심사 수정", notes = "등록된 관심사들의 관심분야 코드들을 DB에 저장")
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "관심사 수정 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> updateInterest(@PathVariable("user_seq") int userSeq, @RequestBody List<CodeDto> interests) {
		mypageService.updateInterest(userSeq, interests);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "관심사 수정 성공"));
	}
	
	@GetMapping("/{user_seq}/searchInterest")
	@ApiImplicitParam(name = "user_seq", value = "user_seq")
	@ApiOperation(value = "관심사 조회", notes = "현재 등록되어 있는 관심사와 등록되어 있지 않은 관심사를 조회한다.")
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "관심사 조회 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> searchInterest(@PathVariable("user_seq") int userSeq) {
		return ResponseEntity.status(200).body(AdvancedResponseBody.of(200, "관심사 조회 성공", mypageService.searchInterest(userSeq)));
	}
	
	@DeleteMapping("/{user_seq}")
	@ApiImplicitParam(name = "user_seq", value = "user_seq")
	@ApiOperation(value = "회원 탈퇴", notes = "DB에서 로그인한 회원의 del_yn 값을 'y'로, mod_dt 값을 현재 시간으로 업데이트")
	@ApiResponses({
		@ApiResponse(code = 200, message = "회원 탈퇴 성공"),
		@ApiResponse(code = 400, message = "잘못된 요청입니다."),
		@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> deleteUser(@PathVariable("user_seq") int userSeq, @RequestParam String userPwd) {
		if(!passwordEncoder.matches(userPwd, userRepository.getById(userSeq).getUserPwd()))
			return ResponseEntity.status(400).body(BaseResponseBody.of(400, "비밀번호가 올바르지 않습니다."));
		
		mypageService.deleteUser(userSeq);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "회원 탈퇴 성공"));
	}
	
	@GetMapping("/{user_seq}/searchScrap")
	@ApiImplicitParam(name = "user_seq", value = "user_seq")
	@ApiOperation(value = "스크랩 조회", notes = "스크랩 한 뉴스 조회")
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "스크랩 조회 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> searchScrap(@PathVariable("user_seq") int userSeq,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		return ResponseEntity.status(200).body(AdvancedResponseBody.of(200, "스크랩 조회 성공", mypageService.searchScrap(userSeq, page)));
	}
	
	@DeleteMapping("/{user_seq}/deleteScrap/{news_seq}")
	@ApiImplicitParam(name = "user_seq", value = "user_seq")
	@ApiOperation(value = "스크랩 삭제", notes = "스크랩 한 뉴스 삭제")
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "스크랩 삭제 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> deleteScrap(@PathVariable("user_seq") int userSeq,
			@RequestParam(value = "news_seq") int newsSeq) {
		mypageService.deleteScrap(userSeq, newsSeq);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "스크랩 삭제 성공"));
	}
}