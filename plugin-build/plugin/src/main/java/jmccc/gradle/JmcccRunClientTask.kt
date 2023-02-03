package jmccc.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class JmcccRunClientTask : DefaultTask() {

    init {
        description = "JMCCC run client task"
        group = "dev.3-3"
    }

    @get:Input
    @get:Option(option = "runConfig", description = "JMCCC client run config")
    abstract val runConfig: Property<RunConfig>

    @TaskAction
    fun sampleAction() {
        logger.warn(runConfig.orNull?.fullVersion?.orNull)
    }
}
