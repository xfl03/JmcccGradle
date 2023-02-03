package jmccc.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

const val EXTENSION_NAME = "jmccc"

abstract class JmcccPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(EXTENSION_NAME, JmcccExtension::class.java, project)

        project.gradle.projectsEvaluated {
            extension.runs.forEach { runConfig ->
                println(runConfig.getTaskName())
                project.tasks.register(runConfig.getTaskName(), JmcccRunClientTask::class.java) {
                    it.runConfig.set(runConfig)
                }
            }
        }
    }
}
