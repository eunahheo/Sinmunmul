package com.newsbig.sinmunmul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.CommonCode;

@Repository
public interface CommonCodeRepository extends JpaRepository<CommonCode, Integer> {
}
