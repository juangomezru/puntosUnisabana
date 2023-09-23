plugins {
	java
	id("org.springframework.boot") version "2.7.15"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	jacoco
}

group = "co.edu.unisabana"
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
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.data:spring-data-jpa:2.7.15")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("mysql:mysql-connector-java:8.0.30")
	implementation("com.h2database:h2:2.2.220")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.test {
	finalizedBy(tasks.jacocoTestReport)
}
tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		csv.required.set(true)
	}
}
tasks.withType<JacocoReport> {
	classDirectories.setFrom(
			sourceSets.main.get().output.asFileTree.matching {
				exclude("**/controller/DTO/**")
				exclude("**/model/**")
				exclude("**/service/**")
				exclude("**/PuntosUnisabanaApplication.class/**")
			}
	)
}
