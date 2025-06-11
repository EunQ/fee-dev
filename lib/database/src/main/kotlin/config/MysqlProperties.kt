package config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.datasource.batch")
data class BatchDatasourceProperties(
    var url: String = "",
    var username: String = "",
    var password: String = "",
    var driverClassName: String = ""
)

@Component
@ConfigurationProperties(prefix = "spring.datasource.main")
data class MainDatasourceProperties(
    var url: String = "",
    var username: String = "",
    var password: String = "",
    var driverClassName: String = ""
)