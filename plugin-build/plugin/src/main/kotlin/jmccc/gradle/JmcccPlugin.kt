package jmccc.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
abstract class JmcccPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("jmccc", JmcccExtension::class.java, project)

        project.gradle.projectsEvaluated {
            extension.internalRuns.forEach { runConfig ->
                println(runConfig.getTaskName())
                project.tasks.register(runConfig.getTaskName(), JmcccRunClientTask::class.java) {
                    runConfig.addTaskDependencies(it)
                    it.runConfig.set(runConfig)
                }
            }
        }
    }
}
