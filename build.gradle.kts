plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.8.0")
    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.7.1")
    implementation("org.jetbrains.kotlinx:kandy-echarts:0.7.1")
    implementation("org.jetbrains.kotlinx:kandy-util:0.7.1")
    implementation("org.jetbrains.kotlinx:kandy-api:0.7.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}
