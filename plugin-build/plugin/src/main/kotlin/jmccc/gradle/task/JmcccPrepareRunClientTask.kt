package jmccc.gradle.task

import jmccc.gradle.core.download
import jmccc.gradle.extension.RunConfig
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.to2mbn.jmccc.option.MinecraftDirectory

abstract class JmcccPrepareRunClientTask : DefaultTask() {

    init {
        description = "JMCCC prepare run client task"
        group = "JMCCC"
    }

    @get:Input
    @get:Option(option = "runConfig", description = "JMCCC client run config")
    abstract val runConfig: Property<RunConfig>

    @TaskAction
    fun runAction() {
        // Check config
        if (!runConfig.isPresent) {
            throw GradleException("run config not found")
        }
        val config = runConfig.orNull!!
        if (!config.version.isPresent) {
            throw GradleException("run version not set")
        }
        val version = config.version.get().getVersionString()

        // Copy mods
        val mcDir = MinecraftDirectory(config.workingDirectory.get())
        val modDir = mcDir.root.resolve("mods")
        if (modDir.exists()) {
            modDir.deleteRecursively()
        }
        modDir.mkdirs()
        config.getModJarFiles().forEach { it.copyTo(modDir.resolve(it.name)) }

        // Download game
        if (!mcDir.getVersionJson(version).exists()) {
            download(mcDir, version).version
        }
    }
}
