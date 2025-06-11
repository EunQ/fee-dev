group = "com.example"
version = "0.0.1-SNAPSHOT"

plugins {
    // 버전만 선언하고 apply는 안함
    kotlin("jvm") version "1.9.25" apply false
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.4.6" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
    id( "java")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "io.spring.dependency-management")

    if (project.path.startsWith("lib:")) {
        apply(plugin = "java-library")
    } else {
        //bootJar, bootRun 없이 순수 .jar만 생성 가능
        apply(plugin = "org.springframework.boot")
        apply(plugin = "java")
    }

    project.group = "com.example"
    project.version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "21"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}