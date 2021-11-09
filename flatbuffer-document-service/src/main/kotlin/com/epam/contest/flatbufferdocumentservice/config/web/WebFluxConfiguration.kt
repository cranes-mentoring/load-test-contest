package com.epam.contest.flatbufferdocumentservice.config.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
class WebFluxConfiguration {

    @Bean
    fun webFluxConfig(): WebFluxConfigurer =
        object : WebFluxConfigurer {
            override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
                configurer.customCodecs().register(FlatbufferDecoder())
            }
        }
}
