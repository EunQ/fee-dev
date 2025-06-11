package com.example.springbatch2.config

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing(dataSourceRef = "batchDataSource", transactionManagerRef = "batchTransactionManager")
class BatchJobConfig {

    @Bean
    fun jobRepository(
        @Qualifier("batchDataSource") dataSource: DataSource,
        @Qualifier("batchTransactionManager") transactionManager: PlatformTransactionManager
    ): JobRepository {
        val factory = JobRepositoryFactoryBean()
        factory.setDataSource(dataSource)
        factory.transactionManager = transactionManager
        factory.setTablePrefix("BATCH3_")
        factory.afterPropertiesSet()
        return factory.`object`!!
    }
}