package com.example.database2.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@ConfigurationPropertiesScan("com.example.database2.config")
class BatchDataSourceConfig(
) {

    @Bean("batchDataSource")
    fun batchDataSource(props: BatchDatasourceProperties): DataSource {
        return DataSourceBuilder.create().url(props.url)
            .username(props.username)
            .password(props.password)
            .driverClassName(props.driverClassName)
            .build()
    }

    @Bean("batchTransactionManager")
    fun batchTransactionManager(
        @Qualifier("batchDataSource") dataSource: DataSource
    ): PlatformTransactionManager = DataSourceTransactionManager(dataSource)

    @Bean
    fun batchDataSourceInitializer(
        @Qualifier("batchDataSource") dataSource: DataSource
    ): DataSourceInitializer {
        val initializer = DataSourceInitializer()
        initializer.setDataSource(dataSource)
        return initializer
    }
}