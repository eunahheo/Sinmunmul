package com.newsbig.sinmunmul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newsbig.sinmunmul.repository.ScrapRepositorySupport;
import com.newsbig.sinmunmul.response.BaseResponseBody;
import com.newsbig.sinmunmul.service.NewsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/news")
@Api("뉴스 컨트롤러 API")
public class NewsController {
	
	@Autowired
	NewsService newsService;
	@Autowired
	ScrapRepositorySupport scrapRepositorySupport;
	
	@PostMapping("/{news_seq}/scrap")
	@ApiImplicitParam(name = "news_seq", value = "news_seq")
	@ApiOperation(value = "뉴스 스크랩", notes = "스크랩을 원하는 뉴스를 DB에 저장")
	@ApiResponses(
			{ @ApiResponse(code = 200, message = "스크랩 성공"),
			  @ApiResponse(code = 400, message = "잘못된 요청입니다."),
			  @ApiResponse(code = 500, message = "서버 오류")
			})
	public ResponseEntity<? extends BaseResponseBody> scrap(@PathVariable("news_seq") int newsSeq, @RequestParam("user_Seq") int userSeq) {
		
		if(scrapRepositorySupport.checkScrap(userSeq, newsSeq))
			return ResponseEntity.status(409).body(BaseResponseBody.of(409, "이미 존재하는 뉴스 스크랩 입니다."));
		
		newsService.scrap(newsSeq, userSeq);
		
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "스크랩 성공"));
	}
}