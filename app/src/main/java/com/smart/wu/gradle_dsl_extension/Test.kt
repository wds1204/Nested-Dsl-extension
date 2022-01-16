package com.smart.wu.gradle_dsl_extension

import android.view.View
import androidx.core.text.htmlEncode

class Test {

    val name="wds"
    fun test(){
        name.htmlEncode()
    }
}