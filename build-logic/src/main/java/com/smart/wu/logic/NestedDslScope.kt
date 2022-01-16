package com.smart.wu.logic

class NestedDslScope(private val nested_feature: Boolean, exclude_list: List<String>) {


    val manager: DependencyHandlerManager by lazy {
        DependencyHandlerManager()
    }

    fun isExclude(path:String):Boolean{
        if(!nested_feature)return false
        return false

    }


}