package jmccc.gradle

import jmccc.gradle.extension.JmcccExtension
import jmccc.gradle.ide.getIdeaModel
import jmccc.gradle.ide.print
import jmccc.gradle.task.JmcccPrepareRunClientTask
import jmccc.gradle.task.JmcccRunClientTask
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class JmcccPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("jmccc", JmcccExtension::class.java, project)
        project.getIdeaModel()?.print()

        project.gradle.projectsEvaluated {
            extension.internalRuns.forEach { runConfig ->
                println(runConfig.getTaskName())
                val prepare = project.tasks.register(
                    "prepareRun" + runConfig.getTaskName(),
                    JmcccPrepareRunClientTask::class.java
                ) {
                    runConfig.addTaskDependencies(it)
                    it.runConfig.set(runConfig)
                }
                project.tasks.register("run" + runConfig.getTaskName(), JmcccRunClientTask::class.java) {
                    it.dependsOn.add(prepare.get())
                    it.runConfig.set(runConfig)
                }
            }
        }
    }
}
