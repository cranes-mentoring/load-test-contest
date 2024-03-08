package com.ere.stockservice.config.init

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.seconds

@Configuration
class ExpensiveLoader

@Component
class MyBeanPostProcessor : BeanPostProcessor {

    private val logger = KotlinLogging.logger {}

    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (beanName == "expensiveLoader") {
            runBlocking {
                logger.info { "Looks like we have a special scanner here >.<" }

                for (i in  0..60) {
                    logger.info { "scan: $i" }
                    delay(1.seconds)
                }

                logger.info { "ok" }
            }
        }

        return bean
    }
}