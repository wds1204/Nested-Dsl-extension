package com.smart.wu.logic

import groovy.lang.Closure
import org.gradle.api.*
import org.gradle.api.provider.Property

const val NESTED_FEATURE = "nested.enable"
const val NESTED_EXCLUDE_LIST = "nested.exclude.list"

open class TheNestedDslExtension(
    private val project: Project,
    val dslScope: NestedDslScope
) {
    internal val business: NamedDomainObjectContainer<NestedDslHandler> =
        project.container(NestedDslHandler::class.java) {
            NestedDslHandler(it, project, "business", dslScope)
        }

    internal val framework: NamedDomainObjectContainer<NestedDslHandler> =
        project.container(NestedDslHandler::class.java) {
            NestedDslHandler(it, project, "framework", dslScope)
        }

    internal val base: NamedDomainObjectContainer<NestedDslHandler> =
        project.container(NestedDslHandler::class.java) {
            NestedDslHandler(it, project, "base", dslScope)
        }

    fun business(action: Action<NamedDomainObjectContainer<NestedDslHandler>>) {
        dslScope.manager.addFirstStairs("business")
        action.execute(business)
    }

    fun framework(action: Action<NamedDomainObjectContainer<NestedDslHandler>>) {
        dslScope.manager.addFirstStairs("framework")
        action.execute(framework)
    }

    fun base(action: Action<NamedDomainObjectContainer<NestedDslHandler>>) {
        dslScope.manager.addFirstStairs("base")
        action.execute(base)
    }

    companion object {

        internal fun Project.theNested(): TheNestedDslExtension {
            val nestedFeature = properties.getOrDefault(NESTED_FEATURE, true) as Boolean

            val excludeList =
                loadFromProperties(NESTED_EXCLUDE_LIST, properties as Map<String, Any>)

            return extensions.create(
                "nestedDsl",
                TheNestedDslExtension::class.java,
                this,
                NestedDslScope(nestedFeature, excludeList)
            )
        }

    }


}


open class NestedDslHandler(
    private val name: String,
    private val project: Project,
    private val stair: String,
    private val dslScope: NestedDslScope
) :
    Named {
    override fun getName(): String = name

    internal val description: Property<String> = project.objects.property(String::class.java)

    fun description(description: String) {
        this.description.set(description)
        this.description.disallowChanges()
    }

    fun ui(v: Closure<Any>) {
        actionFun(v, "ui")
    }


    fun biz(v: Closure<Any>) {
        actionFun(v, "biz")
    }

    fun service(v: Closure<Any>) {
        actionFun(v, "service")
    }

    fun api(v: Closure<Any>) {
        actionFun(v, "api")

    }

    fun dep(v: Closure<Any>) {
        actionFun(v, "dep")
    }

    internal fun actionFun(v: Closure<Any>, innerName: String) {
        dslScope.manager.context =
            dslScope.manager.DepContext(stair, name, innerName)
        project.dependencies(v)
        dslScope.manager.context = null
    }


}

fun loadFromProperties(key: String, config: Map<String, Any>): List<String> {
    val list = mutableListOf<String>()
    config[key]?.run {

        this as String
    }?.run {
        split(".")
    }?.forEach { str ->
        if (str.trim() != "") {
            list.add(str.trim())
        }
    }

    return list

}



