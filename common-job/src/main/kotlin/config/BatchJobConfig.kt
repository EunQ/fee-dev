package com.example.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import java.util.*


@Configuration
class BatchJobConfig(
    private val jobRepository: JobRepository,                     // 스프링이 자동 주입
    private val txManager: PlatformTransactionManager             // 동일
) {
    @Bean
    fun helloJob(): Job =
        JobBuilder("helloJob", jobRepository)
            .start(helloStep())
            .build()

    @Bean
    fun helloStep(): Step =
        StepBuilder("helloStep", jobRepository)
            .tasklet({ contribution: StepContribution, chunkContext: ChunkContext ->
                println("✅ Hello from Batch λ at ${java.time.LocalDateTime.now()}")
                RepeatStatus.FINISHED
            }, txManager)
            .build()
}

@Component
class JobScheduler(
    private val jobLauncher: JobLauncher,
    private val helloJob: Job
) {
    @Scheduled(fixedDelay = 10_000)
    fun runHelloJob() {
        val params = org.springframework.batch.core.JobParametersBuilder()
            .addString("runId", UUID.randomUUID().toString())
            .toJobParameters()

        println("▶️ Launching job...")
        jobLauncher.run(helloJob, params)
    }
}