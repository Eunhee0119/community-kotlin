package com.example.study.common.config


import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@RequiredArgsConstructor
class QuerydslConfig(
    private val entityManager: EntityManager
) {

    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}