plugins {
    id("org.jetbrains.kotlin.jvm")
    id("dev.3-3.jmccc-gradle")
}

jmccc {
    runs {
        create("Forge 1.19.3") {
            version {
                minecraft.set("1.19.3")
                forge.set("44.1.16")
            }
            workingDirectory.set(project.file("run"))
            modFiles.add(tasks["jar"])
        }
        create("Fabric 1.19.3") {
            version {
                minecraft.set("1.19.3")
                fabric.set("0.14.14")
            }
            workingDirectory.set(project.file("run"))
        }
        create("Forge 1.12.2") {
            version {
                minecraft.set("1.12.2")
                forge.set("14.23.5.2860")
            }
            workingDirectory.set(project.file("run"))
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(8))
            }
        }
    }
}
