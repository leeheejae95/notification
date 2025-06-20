plugins {
	java
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.4" // Spring Cloud의존성 관리
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

//tasks.withType<Test> {
//	useJUnitPlatform()
//}

ext["springCloudVersion"] = "2023.0.0" // 변수 선언

allprojects {
//	group = "org.example"
	group = "com.fc"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply {
		plugin("java")
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
		plugin("java-library")
	}

	java {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(21))
		}
	}

	dependencies {
		compileOnly("org.projectlombok:lombok:1.18.30")
		annotationProcessor("org.projectlombok:lombok:1.18.30")

		testCompileOnly("org.projectlombok:lombok:1.18.30")
		testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
		testImplementation("org.junit.jupiter:junit-jupiter")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		}
	}

	tasks.test {
		useJUnitPlatform()
	}
}