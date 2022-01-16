package com.smart.wu.logic

import com.google.gson.Gson
import com.smart.wu.logic.TheNestedDslExtension.Companion.theNested
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.LogLevel
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class PluginNestedDsl : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        theNested().apply {
            configurations.all { cfg ->
                if (!cfg.name.contains("Test") || !cfg.name.contains("Test")) {
                    cfg.dependencies.whenObjectAdded {
                        dslScope.manager.context?.run {
                            dslScope.manager.addThirdStairs(firstStair, secStair, thirdStair)?.add(
                                "${cfg.name} ${it.name}:${it.group}:${it.version}"
                            )
                            logger.log(
                                LogLevel.INFO, """
                            $firstStair {
                                $secStair {
                                    $thirdStair {
                                       ${cfg.name} ${it.name}:${it.group}:${it.version}
                                    }
                                }
                            }                                
                            """.trimIndent()
                            )
                        }
                    }

                }
            }
        }.run {
            project.afterEvaluate {
                it.tasks.register("NestedDslTask", NestedDslTask::class.java) { task ->
                    task.componentsDeps.set(dslScope.manager.componentsDeps)
                }
            }
        }

    }


    abstract class NestedDslTask : DefaultTask() {
        init {
            group = "Nested"
        }

        @get:Input
        abstract val componentsDeps: MapProperty<String, Map<String, Map<String, List<String>>>>


        @TaskAction
        fun action() {

            project.buildDir.file("reports", name, "NestedDeps.json").touch().printWriter().use {
                Gson().toJson(componentsDeps.get()).run {
                    it.println(this)
                }
            }

        }
    }

}