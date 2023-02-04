plugins {
    id("org.jetbrains.kotlin.jvm")
    id("dev.3-3.jmccc-gradle")
}

jmccc {
    runs {
        create("Forge 1.19.3") {
            version.set("1.19.3-forge-44.1.8")
            workingDirectory.set(project.file("run"))
            modFiles.add(tasks["jar"])
        }
        create("Fabric 1.19.3") {
            version.set("fabric-loader-0.14.13-1.19.3")
            workingDirectory.set(project.file("run"))
        }
    }
}
