package com.example.study.util

import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AcceptanceTest {
    @Autowired
    private val databaseCleanup: DatabaseCleanup? = null

    @Autowired
    private val dataLoader: DataLoader? = null
    @BeforeEach
    fun setUp() {
        databaseCleanup!!.execute()
        dataLoader!!.loadData()
    }
}