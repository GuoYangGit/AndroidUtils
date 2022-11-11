@file:Suppress("unused")

package com.guoyang.utils_helper

import android.os.Handler
import android.os.Looper

/**
 * 主线程Handler
 */
val mainThreadHandler by lazy { Handler(Looper.getMainLooper()) }

/**
 * 判断是否在主线程
 */
val isMainThread: Boolean get() = Looper.myLooper() != Looper.getMainLooper()

/**
 * 在主线程执行
 * @param block 执行的动作
 */
fun mainThread(block: () -> Unit) {
    if (isMainThread) mainThreadHandler.post(block) else block()
}

/**
 * 在主线程延迟执行
 * @param delayMillis 延迟时间
 * @param block 执行的动作
 */
fun mainThread(delayMillis: Long, block: () -> Unit) =
    mainThreadHandler.postDelayed(block, delayMillis)