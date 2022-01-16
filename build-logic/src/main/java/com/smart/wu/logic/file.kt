package com.smart.wu.logic

import java.io.File

fun File.file(vararg path: String) = File(this, path.joinToString(File.separator))

fun File.touch(): File {
    if (!this.exists()) {
        this.parentFile?.mkdirs()
        this.createNewFile()
    }
    return this
}