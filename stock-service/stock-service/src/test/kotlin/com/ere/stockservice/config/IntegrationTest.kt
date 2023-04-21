package com.ere.stockservice.config

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.MongoDBContainer


@ContextConfiguration(initializers = [IntegrationTest::class])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IntegrationTest: ApplicationContextInitializer<ConfigurableApplicationContext> {

    private val mongo = MongoDBContainer("mongo:4.0.10")

    init {
        mongo.start()
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
            applicationContext,
            "spring.data.mongodb.uri=" + mongo.replicaSetUrl
        )
    }

}
