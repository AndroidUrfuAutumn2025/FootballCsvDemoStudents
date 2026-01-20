pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

// Настройка автоматической загрузки Java toolchain
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "FootballCsvDemo"