package com.example.database2.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.example.database2.repository.mysql"],
    entityManagerFactoryRef = "mainEntityManagerFactory",
    transactionManagerRef = "mainTransactionManager"
)
@ConfigurationPropertiesScan("com.example.database2")
class PrimaryDataSourceConfig(
){

    @ConfigurationProperties(prefix = "spring.datasource.main")
    @Bean
    fun mainDatasourceProperties() = MainDatasourceProperties()


    @Primary
    @Bean
    fun mainDataSource(props: MainDatasourceProperties): DataSource {
        return DataSourceBuilder.create()
            .url(props.url)
            .username(props.username)
            .password(props.password)
            .driverClassName(props.driverClassName)
            .build()
    }

    @Primary
    @Bean
    fun mainEntityManagerFactory(
        @Qualifier("mainDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean =
        LocalContainerEntityManagerFactoryBean().apply {
            setDataSource(dataSource)
            setPackagesToScan("com.example.database2.repository.mysql")
            jpaVendorAdapter = HibernateJpaVendorAdapter()
        }

    @Primary
    @Bean
    fun mainTransactionManager(
        @Qualifier("mainEntityManagerFactory") emf: EntityManagerFactory
    ): PlatformTransactionManager =
        JpaTransactionManager(emf)
}