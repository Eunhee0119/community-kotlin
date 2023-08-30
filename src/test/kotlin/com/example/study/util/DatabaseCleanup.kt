package com.example.study.util

import jakarta.persistence.Entity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.metamodel.EntityType
import jakarta.transaction.Transactional
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Profile("test")
@Service
class DatabaseCleanup : InitializingBean {
    @PersistenceContext
    private val entityManager: EntityManager? = null

    private var tableNames: List<String>? = null

    override fun afterPropertiesSet() {
        tableNames = entityManager!!.metamodel.entities.stream()
            .filter { entity: EntityType<*> ->
                entity.javaType.getAnnotation(
                    Entity::class.java
                ) != null
            }
            .map { entity: EntityType<*> -> entity.name }
            .collect(Collectors.toList())
    }

    @Transactional
    fun execute() {
        entityManager!!.flush()
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
        for (tableName in tableNames!!) {
            entityManager.createNativeQuery("TRUNCATE TABLE $tableName").executeUpdate()
            entityManager.createNativeQuery("ALTER TABLE $tableName ALTER COLUMN ID RESTART WITH 1").executeUpdate()
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate()
    }
}