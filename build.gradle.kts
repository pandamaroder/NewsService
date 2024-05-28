plugins {
	id("java")
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("pmd")
	id("jacoco")
	id("org.sonarqube") version "4.0.0.2929"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.liquibase:liquibase-core")
	implementation("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.mapstruct:mapstruct:1.4.2.Final")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")
	implementation("org.reflections:reflections:0.10.2")
	pmd("net.sourceforge.pmd:pmd-core:6.55.0")
	pmd("net.sourceforge.pmd:pmd-java:6.55.0")
	pmd("net.sourceforge.pmd:pmd-javascript:6.55.0")
	pmd("net.sourceforge.pmd:pmd-jsp:6.55.0")

	implementation("com.google.errorprone:error_prone_core:2.26.0")
		//implementation("org.sonarsource.sonarqube:sonarqube-gradle-plugin:3.2.1")
	implementation("com.puppycrawl.tools:checkstyle:9.2")
	implementation("com.google.code.findbugs:findbugs:3.0.1")
	implementation("com.github.spotbugs:spotbugs:4.5.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy("jacocoTestReport")
}

pmd {
	toolVersion = "6.55.0"
	ruleSets = listOf()
	ruleSetFiles = files("config/pmd/pmd.xml")
}

tasks.withType<Pmd> {
	reports {
		xml.required.set(false)
		html.required.set(true)
		html.outputLocation.set(file("${buildDir}/reports/pmd.html"))
	}
	ruleSetFiles = files("config/pmd/pmd.xml")

}

sonarqube {
	properties {
		property("sonar.projectKey", "pandamaroder_NewsService")
		property("sonar.organization", "pandamaroder")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}

tasks.register("sonarAnalyze") {
	dependsOn("build")
	finalizedBy("sonarqube")
}

tasks.check {
	dependsOn(tasks.withType<Pmd>())
}


tasks.register("jacocoCheck") {
	dependsOn("jacocoTestCoverageVerification")
	finalizedBy("jacocoTestReport")
}
