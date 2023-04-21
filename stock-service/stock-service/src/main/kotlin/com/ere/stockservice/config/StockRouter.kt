package com.ere.stockservice.config

import com.ere.stockservice.port.StockHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class StockRouter(
    private val properties: ServerProperties,
    private val stockHandler: StockHandler
) {

    @Bean
    fun router() = coRouter {
        with(properties) {
            main.nest {
                contentType(APPLICATION_JSON).nest {
                    POST("", stockHandler::save)

                    findAll.nest {
                        GET("", stockHandler::findAll)
                    }
                }
            }
        }
    }
}
