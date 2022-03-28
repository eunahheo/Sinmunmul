package com.newsbig.sinmunmul.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
	// 오늘의 전체 뉴스 개수
	public int countBydelYnAndNewsRegDtBetween(String delYn, String start, String end);

	public Page<News> findBydelYnAndNewsTitleContainingOrNewsDescContaining(String delYn, String keyword,
			String keyword2, Pageable pageable);

	public Optional<News> findBydelYnAndNewsSeq(String delYn, long newsSeq);

}
