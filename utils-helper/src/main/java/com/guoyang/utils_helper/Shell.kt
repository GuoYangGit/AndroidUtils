@file:Suppress("unused")

package com.guoyang.utils_helper

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * 执行 shell 命令
 * @param command 命令
 */
fun executeCmd(command: String): String {
    val process = Runtime.getRuntime().exec(command)

    val resultReader = BufferedReader(InputStreamReader(process.inputStream))
    val resultBuilder = StringBuilder()
    var resultLine = resultReader.readLine()
    while (resultLine != null) {
        resultBuilder.append(resultLine)
        resultLine = resultReader.readLine()
    }

    val errorReader = BufferedReader(InputStreamReader(process.errorStream))
    val errorBuilder = StringBuilder()
    var errorLine = errorReader.readLine()
    while (errorLine != null) {
        errorBuilder.append(errorLine)
        errorLine = errorReader.readLine()
    }

    return "$resultBuilder\n$errorBuilder"
}