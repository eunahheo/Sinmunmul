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
	
	@Query(value =
			 "SELECT DATE_FORMAT(DATE_SUB(n.news_reg_dt, INTERVAL (DAYOFWEEK(n.news_reg_dt)-1) DAY), '%Y/%m/%d') as start, "
			+"DATE_FORMAT(DATE_SUB(n.news_reg_dt, INTERVAL (DAYOFWEEK(n.news_reg_dt)-7) DAY), '%Y/%m/%d') as end, "
			+ "DATE_FORMAT(n.news_reg_dt, '%Y%u') AS 'date', "
			+"count(*) AS count "
			+"FROM news n "
			+"WHERE n.del_yn=:delYn and n.news_title like %:keyword% OR n.news_desc like %:keyword2% "
			+"GROUP BY date LIMIT 6", nativeQuery = true)
	public List<Object> keywordWeekTrend(@Param("delYn") String delYn, @Param("keyword") String keyword, @Param("keyword2") String keyword2);


}
