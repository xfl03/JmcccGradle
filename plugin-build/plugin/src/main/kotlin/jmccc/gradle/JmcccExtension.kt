package jmccc.gradle

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import javax.inject.Inject

@Suppress("UnnecessaryAbstractClass")
abstract class JmcccExtension @Inject constructor(project: Project) {

    private val objects = project.objects

    val internalRuns: NamedDomainObjectContainer<RunConfig> =
        objects.domainObjectContainer(RunConfig::class.java) { name: String -> RunConfig(project, name) }

    fun runs(closure: Action<NamedDomainObjectContainer<RunConfig>>) {
        closure.execute(internalRuns)
    }
}
