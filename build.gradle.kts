plugins {
    id("java")
    id("application")
}

group = "katas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "katas.tax.calculation.Main"
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.junit.platform:junit-platform-suite:1.11.4")

    // AssertJ
    testImplementation("org.assertj:assertj-core:3.24.2")

    // Mockito
    testImplementation("org.mockito:mockito-core:5.15.2")
    testImplementation("org.mockito:mockito-junit-jupiter:5.15.2")

    // Cucumber
    testImplementation("io.cucumber:cucumber-java:7.21.1")
    testImplementation("io.cucumber:cucumber-junit:7.21.1")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.21.1")
}

tasks.test {
    useJUnitPlatform()
}