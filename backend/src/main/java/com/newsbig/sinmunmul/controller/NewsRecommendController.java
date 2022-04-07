package com.newsbig.sinmunmul.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsbig.sinmunmul.dto.CodeDto;
import com.newsbig.sinmunmul.entity.News;
import com.newsbig.sinmunmul.entity.NewsRecommend;
import com.newsbig.sinmunmul.exception.NotExistsUserException;
import com.newsbig.sinmunmul.exception.NotRecommendException;
import com.newsbig.sinmunmul.response.AdvancedResponseBody;
import com.newsbig.sinmunmul.response.BaseResponseBody;
import com.newsbig.sinmunmul.service.NewsRecommendService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/recom")
@Api("뉴스 추천기사 API")
public class NewsRecommendController {
	
	@Autowired
	NewsRecommendService newsRService;
	
	@GetMapping("/keyword")
	@ApiOperation(value = "긍정 추천 기사 1개, 부정 추천 기사 1개 반환", notes = "입력한 키워드와 연관된 긍정/부정 추천 기사 반환", response = BaseResponseBody.class)
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "등록 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류"),
			  @ApiResponse(code = 202, message = "키워드에 해당하는 기사가 없습니다.")
			})
	public ResponseEntity<? extends BaseResponseBody> registInterest(@RequestParam int userSeq) {
		Map<String, Object> result = new HashMap<>();
		try {
			result = newsRService.recommentArticle(userSeq);
		}
		catch(NotRecommendException e) {
			return ResponseEntity.status(202).body(BaseResponseBody.of(202, "키워드에 해당하는 기사가 없습니다."));
		}
		return ResponseEntity.status(200).body(AdvancedResponseBody.of(200, "추천 기사 조회 성공",result));
	}
}
