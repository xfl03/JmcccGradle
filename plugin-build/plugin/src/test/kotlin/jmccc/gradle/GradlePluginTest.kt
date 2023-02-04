package jmccc.gradle

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert.assertNotNull
import org.junit.Test

class GradlePluginTest {
    @Test
    fun `extension templateExampleConfig is created correctly`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("dev.3-3.jmccc-gradle")

        assertNotNull(project.extensions.getByName("jmccc"))
    }
}
