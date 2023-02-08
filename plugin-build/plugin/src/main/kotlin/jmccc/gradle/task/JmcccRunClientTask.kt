package jmccc.gradle.task

import jmccc.gradle.extension.RunConfig
import jmccc.gradle.core.generateLaunchArgs
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import org.gradle.jvm.toolchain.JavaToolchainService
import org.gradle.process.JavaExecSpec
import org.to2mbn.jmccc.auth.OfflineAuthenticator
import org.to2mbn.jmccc.option.LaunchOption
import org.to2mbn.jmccc.option.MinecraftDirectory

abstract class JmcccRunClientTask : DefaultTask() {

    init {
        description = "JMCCC run client task"
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
        val config = runConfig.get()
        if (!config.version.isPresent) {
            throw GradleException("run version not set")
        }
        val version = config.version.get().getVersionString()
        val mcDir = MinecraftDirectory(config.workingDirectory.get())

        val option = LaunchOption(version, OfflineAuthenticator.name("player"), mcDir)
        val args = option.generateLaunchArgs()
        project.javaexec { exec: JavaExecSpec ->
            exec.workingDir = config.workingDirectory.get()
            exec.jvmArgs = args.jvmArguments
            exec.args = args.gameArguments
            exec.mainClass.set(option.version.mainClass)

            // Special toolchain
            if (config.toolchain.isPresent) {
                val service = project.extensions.getByType(JavaToolchainService::class.java)
                exec.executable = service.launcherFor(config.toolchain.get()).get().executablePath.asFile.absolutePath
            }
        }
    }
}
