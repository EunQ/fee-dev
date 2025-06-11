package com.example.commonjob2

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(
    scanBasePackages = [
        "com.example.database2",  // lib:database 모듈
        "com.example.springbatch2",     // lib:spring-batch 모듈
        "com.example.commonjob2",
    ]
)
@EnableBatchProcessing
@EnableScheduling
class CommonJob2Application

fun main(args: Array<String>) {
    runApplication<CommonJob2Application>(*args)
}
