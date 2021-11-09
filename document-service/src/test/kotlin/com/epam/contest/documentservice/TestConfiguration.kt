package com.epam.contest.documentservice

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan

@TestConfiguration
@ComponentScan(lazyInit = true)
class TestConfiguration
