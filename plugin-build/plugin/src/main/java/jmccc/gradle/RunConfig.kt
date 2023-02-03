package jmccc.gradle

import org.gradle.api.Project
import org.gradle.api.provider.Property

class RunConfig(project: Project, private val name: String) {
    val fullVersion: Property<String> = project.objects.property(String::class.java)

    fun getTaskName() = "run$name"
}
