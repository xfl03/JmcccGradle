# JMCCC Gradle 🐘

[![Pre Merge Checks](https://github.com/xfl03/JmcccGradle/workflows/Pre%20Merge%20Checks/badge.svg)](https://github.com/xfl03/JmcccGradle/actions?query=workflow%3A%22Pre+Merge+Checks%22)  [![License](https://img.shields.io/github/license/xfl03/JmcccGradle.svg)](LICENSE) ![Language](https://img.shields.io/github/languages/top/xfl03/JmcccGradle?color=blue&logo=kotlin)

Launch Minecraft Client with JMCCC in Gradle PLugin.

## How To Use

Our plugin supports both Kotlin DSL and Groovy DSL, while the example code was written in Kotlin DSL.
```kotlin
jmccc {
    runs {
        // Add a new version to run
        create("Forge 1.12.2") {
            // The version which will be used
            version {
                // Minecraft version
                minecraft.set("1.12.2")
                // Forge version
                forge.set("14.23.5.2860")
            }
            // Special the run dir
            workingDirectory.set(project.file("run"))
            // Copy mod file to mods dir automatically
            modFiles.add(tasks["jar"])
            // Special the Java version as Java 8
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(8))
            }
        }
    }
}
```

## Publish Plugin Manually

This project is using Java 8 to compile.

```shell
export JAVA_HOME=(/usr/libexec/java_home -v 1.8)
export GRADLE_PUBLISH_KEY=
export GRADLE_PUBLISH_SECRET=
./gradlew preMerge --continue
./gradlew --project-dir plugin-build setupPluginUploadFromEnvironment publishPlugins
```
