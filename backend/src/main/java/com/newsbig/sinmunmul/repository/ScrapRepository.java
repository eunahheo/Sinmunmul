package com.newsbig.sinmunmul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.Scrap;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Integer> {
}
