plugins {
	id("java")
	id("org.springframework.boot") version "3.2.6"
	id("io.spring.dependency-management") version "1.1.4"
	id("pmd")
	id("jacoco")
	id("org.sonarqube") version "4.0.0.2929"
	id("checkstyle")
	//id("net.ltgt.errorprone")
	//id("com.github.spotbugs")
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
	checkstyle("com.thomasjensen.checkstyle.addons:checkstyle-addons:7.0.1")
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


	//errorprone("com.google.errorprone:error_prone_core:2.27.1")
	//errorprone("jp.skypencil.errorprone.slf4j:errorprone-slf4j:0.1.24")
	implementation("com.google.errorprone:error_prone_core:2.26.0")
	implementation("com.puppycrawl.tools:checkstyle:9.2")
	implementation("com.google.code.findbugs:findbugs:3.0.1")
	implementation("com.github.spotbugs:spotbugs:4.5.0")
}

tasks {

	test {
		testLogging.showStandardStreams = false // set to true for debug purposes
		useJUnitPlatform()
		finalizedBy(jacocoTestReport, jacocoTestCoverageVerification)
	}

	jacocoTestReport {
		dependsOn(test)
		reports {
			xml.required.set(true)
			html.required.set(true)
		}
	}

	jacocoTestCoverageVerification {
		dependsOn(jacocoTestReport)
		violationRules {
			rule {
				limit {
					counter = "CLASS"
					value = "MISSEDCOUNT"
					maximum = "0.0".toBigDecimal()
				}
			}
			rule {
				limit {
					counter = "METHOD"
					value = "MISSEDCOUNT"
					maximum = "0.0".toBigDecimal()
				}
			}
			rule {
				limit {
					counter = "LINE"
					value = "MISSEDCOUNT"
					maximum = "0.0".toBigDecimal()
				}
			}
			rule {
				limit {
					counter = "INSTRUCTION"
					value = "COVEREDRATIO"
					minimum = "1.0".toBigDecimal()
				}
			}
			rule {
				limit {
					counter = "BRANCH"
					value = "COVEREDRATIO"
					minimum = "1.0".toBigDecimal()
				}
			}
		}

	}
	/*withType<SonarTask>().configureEach {
		dependsOn(test, jacocoTestReport)
	}*/


}

pmd {
	toolVersion = "6.55.0"
	ruleSets = listOf()
	ruleSetFiles = files("config/pmd/pmd.xml")
}
sonarqube {
	properties {
		property("sonar.projectKey", "pandamaroder_NewsService")
		property("sonar.organization", "pandamaroder")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}

checkstyle {
	toolVersion = "10.16.0"
	configFile = file("config/checkstyle/checkstyle.xml")
	isIgnoreFailures = false
	maxWarnings = 0
	maxErrors = 0
}




jacoco {
	toolVersion = "0.8.12"
}
