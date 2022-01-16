package com.smart.wu.logic

class DependencyHandlerManager {


    val componentsDeps: MutableMap<String, MutableMap<String, MutableMap<String, MutableList<String>>>> =
        hashMapOf()

    var context:DepContext? = null

    fun addFirstStairs(stair: String): MutableMap<String, MutableMap<String, MutableList<String>>> {
        if (!componentsDeps.containsKey(stair)) {
            componentsDeps[stair] = hashMapOf()
        }
        return componentsDeps[stair]!!
    }

    private fun addSecStairs(
        firstStair: String,
        secStair: String
    ): MutableMap<String, MutableList<String>> {
        val firstStairs = addFirstStairs(firstStair)
        if (!firstStairs.containsKey(secStair)) {
            firstStairs[secStair] = hashMapOf()
        }
        return firstStairs[secStair]!!
    }

    fun addThirdStairs(
        firstStair: String,
        secStair: String,
        thirdStair: String
    ): MutableList<String>? {
        val secStairs = addSecStairs(firstStair, secStair)
        if (!secStairs.containsKey(thirdStair)) {
            secStairs[thirdStair]= mutableListOf()
        }
        return secStairs[thirdStair]
    }


    inner class DepContext(val firstStair: String,val secStair:String,val thirdStair:String)

}