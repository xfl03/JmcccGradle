package jmccc.gradle

import groovy.lang.Closure
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
abstract class JmcccExtension @Inject constructor(project: Project) {

    private val objects = project.objects

    /**
     * Kotlin DSL
     */
    val runs: NamedDomainObjectContainer<RunConfig> =
        objects.domainObjectContainer(RunConfig::class.java) { name: String -> RunConfig(project, name) }

    /**
     * Groovy DSL
     */
    fun runs(closure: Closure<Any>): NamedDomainObjectContainer<RunConfig> {
        return runs.configure(closure)
    }
}
