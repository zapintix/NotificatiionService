plugins {
	kotlin("jvm") version "2.0.21"
	kotlin("plugin.spring") version "2.0.21"
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "2.0.21"
	id("org.jetbrains.kotlin.plugin.noarg") version "2.0.21"
	kotlin("plugin.allopen") version "2.0.21"
}

group = "io.dtechs"
version = "0.0.1-SNAPSHOT"


repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.springframework:spring-context-support")
	implementation("org.springframework:spring-context")
	implementation("org.apache.httpcomponents.client5:httpclient5:5.2")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("jakarta.validation:jakarta.validation-api:3.1.0")
	implementation("org.hibernate:hibernate-validator:8.0.1.Final")
	implementation("org.liquibase:liquibase-core:4.23.0")

	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
