package com.newsbig.sinmunmul.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.newsbig.sinmunmul.entity.NewsTopic;

public interface NewsTopicRepository extends JpaRepository<NewsTopic, Long>{

	// 코드로 키워드 찾기
	public NewsTopic findBydelYnAndCode(String delYn, int code);
}
