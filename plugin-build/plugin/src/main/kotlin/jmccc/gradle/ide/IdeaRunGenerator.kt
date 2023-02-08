package jmccc.gradle.ide

import org.gradle.api.Project
import org.gradle.plugins.ide.idea.model.IdeaModel

fun Project.getIdeaModel(): IdeaModel? {
    return extensions.findByType(IdeaModel::class.java)
}

fun IdeaModel.print() {
    println(this.project)
    println(this.module)
}
