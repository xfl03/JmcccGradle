package jmccc.gradle.extension

import org.gradle.api.Project
import org.gradle.api.provider.Property

class MinecraftVersion(project: Project) {
    val minecraft: Property<String> = project.objects.property(String::class.java)
    val forge: Property<String> = project.objects.property(String::class.java)
    val fabric: Property<String> = project.objects.property(String::class.java)

    fun getVersionString() = when {
        !minecraft.isPresent -> throw IllegalArgumentException("Minecraft version missing")
        forge.isPresent && fabric.isPresent -> throw IllegalArgumentException("Forge and Fabric is not compatible")
        forge.isPresent -> "${minecraft.get()}-forge-${forge.get()}"
        fabric.isPresent -> "fabric-loader-${fabric.get()}-${minecraft.get()}"
        else -> minecraft.get()
    }
}
