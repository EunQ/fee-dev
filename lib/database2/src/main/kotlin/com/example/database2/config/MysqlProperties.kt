package com.example.database2.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.datasource.batch")
open class BatchDatasourceProperties(
    var url: String = "",
    var username: String = "",
    var password: String = "",
    var driverClassName: String = ""
)

data class MainDatasourceProperties(
    var url: String = "",
    var username: String = "",
    var password: String = "",
    var driverClassName: String = ""
)