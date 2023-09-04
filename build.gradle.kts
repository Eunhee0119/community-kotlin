import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val KOTLIN_VERSION = "1.8.22"
	id("org.springframework.boot") version "3.1.3"
	id("io.spring.dependency-management") version "1.1.3"
	kotlin("jvm") version KOTLIN_VERSION
	kotlin("plugin.spring") version KOTLIN_VERSION
	kotlin("plugin.jpa") version KOTLIN_VERSION
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("org.springframework.boot:spring-boot-starter-data-redis:3.1.3")

	implementation("org.springframework.boot:spring-boot-starter-validation:3.1.3")

	implementation("org.springframework.boot:spring-boot-starter-security:3.1.3")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	compileOnly("org.projectlombok:lombok")

	runtimeOnly("com.h2database:h2")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test:6.1.3")
	testImplementation("io.rest-assured:rest-assured:5.3.1")
	testImplementation("org.mockito:mockito-core:5.5.0")
}

allOpen{
	annotation("jakarta.persistance.Entity")
}

noArg{
	annotation("jakarta.persistance.Entity")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
