package com.newsbig.sinmunmul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
}
