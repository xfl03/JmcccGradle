# JMCCC Gradle 🐘

[![Pre Merge Checks](https://github.com/xfl03/JmcccGradle/workflows/Pre%20Merge%20Checks/badge.svg)](https://github.com/xfl03/JmcccGradle/actions?query=workflow%3A%22Pre+Merge+Checks%22)  [![License](https://img.shields.io/github/license/xfl03/JmcccGradle.svg)](LICENSE) ![Language](https://img.shields.io/github/languages/top/xfl03/JmcccGradle?color=blue&logo=kotlin)

Launch Minecraft Client with JMCCC in Gradle PLugin.

## Publish Manually
```shell
export JAVA_HOME=(/usr/libexec/java_home -v 1.8)
export GRADLE_PUBLISH_KEY=
export GRADLE_PUBLISH_SECRET=
./gradlew preMerge --continue
./gradlew --project-dir plugin-build setupPluginUploadFromEnvironment publishPlugins
```
