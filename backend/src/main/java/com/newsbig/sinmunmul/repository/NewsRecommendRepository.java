package com.newsbig.sinmunmul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.NewsRecommend;

@Repository
public interface NewsRecommendRepository extends JpaRepository<NewsRecommend, Long> {
}
