package com.ere.stockservice.config

import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class IntegrationTest {

    companion object {
        @JvmStatic
        private val mongoDBContainer = MongoDBContainer(DockerImageName.parse("mongo:4.0.10"))
            .waitingFor(HostPortWaitStrategy())

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            mongoDBContainer.start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.host", mongoDBContainer::getHost)
            registry.add("spring.data.mongodb.port", mongoDBContainer::getFirstMappedPort)
        }
    }
}
