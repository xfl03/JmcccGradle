package jmccc.gradle.extension

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import org.gradle.jvm.toolchain.JavaToolchainSpec
import org.gradle.jvm.toolchain.internal.DefaultToolchainSpec
import java.io.File

class RunConfig(private val project: Project, private val name: String) {
    val version: Property<MinecraftVersion> = project.objects.property(MinecraftVersion::class.java).convention(
        MinecraftVersion(project)
    )
    val workingDirectory: Property<File> =
        project.objects.property(File::class.java).convention(project.file(".minecraft"))
    val modFiles: ListProperty<Any> = project.objects.listProperty(Any::class.java)
    val toolchain: Property<JavaToolchainSpec> =
        project.objects.property(JavaToolchainSpec::class.java).convention(DefaultToolchainSpec(project.objects))

    fun getTaskName() = name.replace(Regex("[^a-zA-Z0-9\\-_]"), "")

    fun getModJarFiles() =
        if (modFiles.isPresent) modFiles.get().map { getModJarFile(project, it) } else emptyList<File>()

    fun addTaskDependencies(task: Task) {
        if (!modFiles.isPresent) {
            return
        }
        modFiles.get().filterIsInstance<Task>().forEach { task.dependsOn(it) }
    }

    fun toolchain(action: Action<JavaToolchainSpec>) {
        action.execute(toolchain.get())
    }

    fun version(action: Action<MinecraftVersion>) {
        action.execute(version.get())
    }
}

fun getModJarFile(project: Project, modFile: Any): File {
    return when (modFile) {
        is File -> modFile
        is AbstractArchiveTask -> modFile.archiveFile.get().asFile
        else -> project.file(modFile)
    }
}
