package jmccc.gradle.core

import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder
import org.to2mbn.jmccc.mcdownloader.download.concurrent.CallbackAdapter
import org.to2mbn.jmccc.mcdownloader.download.concurrent.DownloadCallback
import org.to2mbn.jmccc.mcdownloader.download.tasks.DownloadTask
import org.to2mbn.jmccc.mcdownloader.provider.DownloadProviderChain
import org.to2mbn.jmccc.mcdownloader.provider.MojangDownloadProvider
import org.to2mbn.jmccc.mcdownloader.provider.fabric.FabricDownloadProvider
import org.to2mbn.jmccc.mcdownloader.provider.forge.ForgeDownloadProvider
import org.to2mbn.jmccc.option.MinecraftDirectory
import org.to2mbn.jmccc.version.Library
import org.to2mbn.jmccc.version.Version

private val forgeProvider = ForgeDownloadProvider()
private val fabricProvider = FabricDownloadProvider()

private val downloader = MinecraftDownloaderBuilder.create().providerChain(
    DownloadProviderChain.create()
        .baseProvider(MojangDownloadProvider())
        .addProvider(forgeProvider)
        .addProvider(fabricProvider)
).build()

fun download(mcDir: MinecraftDirectory, version: String): Version {
    println("Downloading $version")
    return downloader.downloadIncrementally(mcDir, version, JmcccDownloadCallback()).get()
}

fun download(mcDir: MinecraftDirectory, library: Library) {
    println("Downloading $library")
    downloader.download(downloader.provider.library(mcDir, library), JmcccDownloadCallback()).get()
}

fun shutdown() {
    downloader.shutdown()
}

class JmcccDownloadCallback<T> : CallbackAdapter<T>() {
    override fun failed(e: Throwable) {
        throw e
    }

    override fun <R : Any?> taskStart(task: DownloadTask<R>): DownloadCallback<R> {
        println(task.uri)
        return JmcccDownloadCallback<R>()
    }
}
