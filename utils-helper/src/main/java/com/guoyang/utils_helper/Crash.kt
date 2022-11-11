@file:Suppress("unused")

package com.guoyang.utils_helper

import android.content.Context
import android.os.Looper
import java.io.File
import java.time.Instant

/**
 * 处理未捕获的异常
 * @param block 处理未捕获的异常的代码块
 */
inline fun handleUncaughtException(crossinline block: (Thread, Throwable) -> Unit) {
    val defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
    Thread.setDefaultUncaughtExceptionHandler { t, e ->
        block(t, e)
        defaultCrashHandler?.uncaughtException(t, e)
    }
}

/**
 * 处理主线程的异常
 * @param block 处理主线程的异常的代码块
 */
inline fun handleMainThreadException(crossinline block: (Throwable) -> Unit) {
    mainThreadHandler.post {
        while (true) {
            try {
                Looper.loop()
            } catch (e: Throwable) {
                block(e)
            }
        }
    }
}

/**
 * 保存崩溃日志到本地
 * @param dirPath 崩溃日志保存路径
 */
fun Context.saveCrashLogLocally(dirPath: String = cacheDirPath) =
    handleUncaughtException { thread, e ->
        val now = Instant.now()
        File(dirPath, "crash_${now.format("yyyy-MM-dd")}.txt").print(append = true) {
            println("Time:          ${now.format("yyyy-MM-dd HH:mm:ss")}")
            println("App version:   $appVersionName ($appVersionCode)")
            println("OS version:    Android $sdkVersionName ($sdkVersionCode)")
            println("Manufacturer:  $deviceManufacturer")
            println("Model:         $deviceModel")
            println("Thread:        ${thread.name}")
            println()
            e.printStackTrace(this)
            println()
            println("-----------------------------------------------------")
            println()
        }
    }