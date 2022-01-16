package com.smart.wu.logic

import groovy.lang.Closure
import org.gradle.api.*
import org.gradle.api.provider.Property

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
        internal fun Project.theNested(): TheNestedDslExtension =
            extensions.create(
                "nestedDsl",
                TheNestedDslExtension::class.java,
                this,
                NestedDslScope()
            )
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
        dslScope.manager.context =
            dslScope.manager.DepContext(stair, name, "ui")

        project.dependencies(v)

        dslScope.manager.context = null

    }

    fun biz(v: Closure<Any>) {
        dslScope.manager.context =
            dslScope.manager.DepContext(stair, name, "biz")

        project.dependencies(v)

        dslScope.manager.context = null

    }

    fun api(v: Closure<Any>) {
        dslScope.manager.context =
            dslScope.manager.DepContext(stair, name, "api")

        project.dependencies(v)

        dslScope.manager.context = null

    }

    fun dep(v: Closure<Any>) {
        dslScope.manager.context =
            dslScope.manager.DepContext(stair, name, "dep")

        project.dependencies(v)

        dslScope.manager.context = null
    }


}



