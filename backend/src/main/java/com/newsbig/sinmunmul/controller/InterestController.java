package com.newsbig.sinmunmul.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.dto.SigninDto;
import com.newsbig.sinmunmul.entity.User;
import com.newsbig.sinmunmul.exception.NotExistsUserException;
import com.newsbig.sinmunmul.response.BaseResponseBody;
import com.newsbig.sinmunmul.service.InterestService;
import com.newsbig.sinmunmul.util.TimeUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/interest")
@Api("사용자 컨트롤러 API")
public class InterestController {
	
	@Autowired
	InterestService interestService;
	
	@PostMapping("/regist")
	@ApiOperation(value = "회원가입 시 관심사 등록", notes = "사용자가 입력한 관심사를 등록한다.", response = BaseResponseBody.class)
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "등록 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> registInterest(@RequestBody  List<CodeDto> list, 
			@RequestParam int userSeq) {
		
		try {
			interestService.registInterest(list, userSeq);
		}
		catch(NotExistsUserException e) {
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "회원정보를 불러올 수 없습니다."));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "관심사 등록 성공"));
	}
}
