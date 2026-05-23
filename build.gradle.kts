plugins {
    kotlin("jvm") version "2.0.21"
    application
}

kotlin {
    jvmToolchain(21)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("org.jfree:jfreechart:1.5.3")
}

tasks.test {
    useJUnitPlatform()
}


application {
    mainClass.set("MainKt")
}