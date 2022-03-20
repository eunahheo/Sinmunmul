package com.newsbig.sinmunmul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newsbig.sinmunmul.entity.CommonCodeGroup;

@Repository
public interface CommonCodeGroupRepository extends JpaRepository<CommonCodeGroup, Integer> {
}
