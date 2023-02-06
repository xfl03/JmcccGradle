package jmccc.gradle

import jmccc.gradle.core.download
import jmccc.gradle.core.launch
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.to2mbn.jmccc.auth.OfflineAuthenticator
import org.to2mbn.jmccc.option.LaunchOption
import org.to2mbn.jmccc.option.MinecraftDirectory

abstract class JmcccRunClientTask : DefaultTask() {

    init {
        description = "JMCCC run client task"
        group = "dev.3-3"
    }

    @get:Input
    @get:Option(option = "runConfig", description = "JMCCC client run config")
    abstract val runConfig: Property<RunConfig>

    @get:Input
    @get:Option(option = "launchGame", description = "Need launch the game")
    @get:Optional
    abstract val launchGame: Property<Boolean>

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
        val needLaunchGame = launchGame.orNull ?: true
        var version = config.version.get()

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
            version = download(mcDir, version).version
        }
        // Launch game
        if (needLaunchGame) {
            launch(LaunchOption(version, OfflineAuthenticator.name("player"), mcDir))
        }
    }
}
