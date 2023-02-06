package jmccc.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

val RUN_GROUP = "JMCCC Runs"

abstract class JmcccPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("jmccc", JmcccExtension::class.java, project)

        project.gradle.projectsEvaluated {
            extension.internalRuns.forEach { runConfig ->
                println(runConfig.getTaskName())
                project.tasks.register("run" + runConfig.getTaskName(), JmcccRunClientTask::class.java) {
                    runConfig.addTaskDependencies(it)
                    it.launchGame.set(true)
                    it.runConfig.set(runConfig)
                    it.group = RUN_GROUP
                }
                project.tasks.register("prepareRun" + runConfig.getTaskName(), JmcccRunClientTask::class.java) {
                    runConfig.addTaskDependencies(it)
                    it.launchGame.set(false)
                    it.runConfig.set(runConfig)
                    it.group = RUN_GROUP
                }
            }
        }
    }
}
