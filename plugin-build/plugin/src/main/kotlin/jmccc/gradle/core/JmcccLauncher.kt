package jmccc.gradle.core

import org.to2mbn.jmccc.launch.LaunchArgument
import org.to2mbn.jmccc.launch.LauncherBuilder
import org.to2mbn.jmccc.launch.MissingDependenciesException
import org.to2mbn.jmccc.launch.ProcessListener
import org.to2mbn.jmccc.option.LaunchOption

private val launcher = LauncherBuilder.create().printDebugCommandline(true).build()
fun launch(option: LaunchOption) {
//    launcher.generateLaunchArgs(option).gameArguments
    println("Launching ${option.version.version}")
    val listener = JmcccProcessListener()
    try {
        launcher.launch(option, listener)
    } catch (e: MissingDependenciesException) {
        e.missingLibraries.forEach {
            download(option.minecraftDirectory, it)
        }
        launcher.launch(option, listener)
    }.waitFor()
}

fun LaunchOption.generateLaunchArgs(): LaunchArgument = launcher.generateLaunchArgs(this)

class JmcccProcessListener : ProcessListener {
    override fun onLog(log: String?) {
        println(log)
    }

    override fun onErrorLog(log: String?) {
        System.err.println(log)
    }

    override fun onExit(code: Int) {
        println("Game exit with $code")
        shutdown()
    }
}
