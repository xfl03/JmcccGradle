pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("io.gitlab.arturbosch.detekt") version "1.22.0"
        id("org.jetbrains.kotlin.jvm") version "1.8.10"
        id("org.jlleitschuh.gradle.ktlint") version "11.1.0"
        id("com.gradle.plugin-publish") version "1.1.0"
        id("com.github.ben-manes.versions") version "0.45.0"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }
}

plugins {
    `gradle-enterprise`
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(System.getenv("GITHUB_ACTIONS") == "true")
        publishOnFailure()
    }
}

rootProject.name = "JmcccGradle"

include(":example")
includeBuild("plugin-build")
