package com.newsbig.sinmunmul.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.News;
import com.newsbig.sinmunmul.entity.User;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
//	SELECT * FROM `atest` WHERE DATE_FORMAT(insert_date, "%Y-%m-%d") = CURDATE()
//	public Optional<News> countBydelYnAndCommonCodeGroupAndRegDtBetween(String delYn, String commonCodeGroup, LocalDateTime start, LocalDateTime end);
}
