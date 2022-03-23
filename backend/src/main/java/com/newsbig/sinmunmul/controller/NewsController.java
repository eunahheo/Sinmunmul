package com.newsbig.sinmunmul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsbig.sinmunmul.response.AdvancedResponseBody;
import com.newsbig.sinmunmul.response.BaseResponseBody;
import com.newsbig.sinmunmul.service.NewsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/news")
@Api("뉴스 API")
public class NewsController {
	
	@Autowired
	NewsService newsService;
	
	@PutMapping("/{news_seq}/scrap")
	@ApiImplicitParam(name = "news_seq", value = "news_seq")
	@ApiOperation(value = "뉴스 스크랩", notes = "스크랩을 원하는 뉴스를 DB에 저장")
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "스크랩 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> scrap(@PathVariable("news_seq") int newsSeq, @RequestParam("user_Seq") int userSeq) {
		newsService.scrap(newsSeq, userSeq);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "스크랩 성공"));
	}
	
	@GetMapping("/today")
	@ApiOperation(value = "오늘의 뉴스 현황 조회", notes = "전체, 정치, 경제, 사회, 생활/문화, 세계, 과학 별 기사 개수와 전체 대비 퍼센트를 반환한다.")
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "오늘의 뉴스 현황 조회 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> todayNews() {
		return ResponseEntity.status(200).body(AdvancedResponseBody.of(200, "오늘의 뉴스 현황 조회 성공", newsService.todayNews()));
	}
}