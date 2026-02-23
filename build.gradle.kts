plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "5.1.0.4882"
}

group = "id.ac.ui.cs.advprog"
version = "0.0.1-SNAPSHOT"
description = "eshop"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val seleniumJavaVersion = "4.14.1"
val seleniumJupiterVersion = "5.0.1"
val webdrivermanagerVersion = "5.6.3"
val junitJupiterVersion = "5.10.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")

    // Test Dependencies
    testImplementation(platform("org.junit:junit-bom:5.10.0")) // Enforce BOM
    testImplementation("org.springframework.boot:spring-boot-starter-test") // Recommended over individual test starters

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("org.seleniumhq.selenium:selenium-java:$seleniumJavaVersion")
    testImplementation("io.github.bonigarcia:selenium-jupiter:$seleniumJupiterVersion")
    testImplementation("io.github.bonigarcia:webdrivermanager:$webdrivermanagerVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
}

// Keep your custom tasks if you need them for other purposes
tasks.register<Test>("unitTest") {
    description = "Runs unit tests."
    group = "verification"

    filter {
        excludeTestsMatching("*FunctionalTest")
    }
}

tasks.register<Test>("functionalTest") {
    description = "Runs functional tests."
    group = "verification"

    filter {
        includeTestsMatching("*FunctionalTest")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

// --- ADDED FOR CI/CD TUTORIAL ---

// Modifies the built-in test task provided by the java Gradle plugin [cite: 30]
tasks.test {
    filter {
        // Exclude the functional tests from being run by the test task [cite: 29]
        excludeTestsMatching("*FunctionalTest")
    }
    // Ensure the code coverage report generation is always generated after running the test task [cite: 31]
    finalizedBy(tasks.jacocoTestReport)
}

// Configure the JaCoCo report generation
tasks.jacocoTestReport {
    // Tell the jacocoTestReport task to run after the test task [cite: 32]
    dependsOn(tasks.test)
    reports {
        xml.required.set(true) // Add this line so SonarCloud can read the coverage
        html.required.set(true)
    }
}

// --------------------------------

// --- CRITICAL FIX: FORCE JUNIT VERSIONS ---
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.junit.platform") {
            useVersion("1.10.0")
        }
        if (requested.group == "org.junit.jupiter") {
            useVersion("5.10.0")
        }
    }
}

sonar {
    properties {
        property("sonar.projectKey", "KKI-Melanton-Gabriel-Siregar-2406365364_Module-1---Coding-Standards")
        property("sonar.organization", "kki-melanton-gabriel-siregar-2406365364")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}