package com.newsbig.sinmunmul.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {
	 @PersistenceContext
	 EntityManager entityManager;

	 @Bean
	 public JPAQueryFactory jpaQueryFactory() {
	     return new JPAQueryFactory(entityManager);
	 }
}
