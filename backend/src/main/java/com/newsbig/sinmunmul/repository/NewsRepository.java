package com.newsbig.sinmunmul.repository;

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
	@Query(value = "select count(*) from news WHERE del_yn=:delYn AND unix_timestamp(news_reg_dt) >= unix_timestamp(:start) AND unix_timestamp(news_reg_dt) <= unix_timestamp(:end)",
			nativeQuery = true)
	public int countBydelYnAndNewsRegDtBetween(@Param("delYn") String delYn,@Param("start") String start,@Param("end") String end);

	@Query(value = "select * from news WHERE del_yn=:delYn AND MATCH(news_title, news_desc) AGAINST(:keyword IN BOOLEAN MODE)",
			nativeQuery = true)
	public Page<News> searchNewsKeyword(@Param("delYn") String delYn, @Param("keyword") String keyword, Pageable pageable);

	public Optional<News> findBydelYnAndNewsSeq(String delYn, long newsSeq);

}
