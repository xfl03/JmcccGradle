plugins {
    java
    id("dev.3-3.jmccc-gradle")
}

jmccc {
    runs {
        create("forge") {
            logger.warn("SET full Version")
            fullVersion.set("1.19.3-forge-44.1.8")
        }
        logger.warn(this.size.toString())
    }
}
