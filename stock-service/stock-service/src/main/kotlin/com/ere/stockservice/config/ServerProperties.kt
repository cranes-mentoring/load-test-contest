package com.ere.stockservice.config

import org.jetbrains.annotations.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("router")
data class ServerProperties @ConstructorBinding constructor(
    @NotNull val main: String,
    @NotNull val findAll: String
)
