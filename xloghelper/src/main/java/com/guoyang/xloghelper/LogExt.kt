package com.guoyang.xloghelper

// 日志输出Tag,默认为当前调用类名
inline val TAG: String
    get() = Thread.currentThread().stackTrace
        .find { !it.isIgnorable }?.simpleClassName.orEmpty()

// 用于判断当前信息是否需要输出
val StackTraceElement.isIgnorable: Boolean
    get() = isNativeMethod || className == Thread::class.java.name || className == LogHelper::class.java.name

// 获取当前调用类名
val StackTraceElement.simpleClassName: String?
    get() = className.split(".").run {
        if (isNotEmpty()) last() else null
    }


/**
 * 详细日志输出
 * @param message 日志输出信息
 */
inline fun xLogV(message: Any?) = xLogV(TAG, message.toString())

/**
 * 详细日志输出
 * @param tag 日志输出Tag
 * @param message 日志输出信息
 */
inline fun xLogV(tag: String, message: Any?) = LogHelper.v(tag, message.toString())

/**
 * 调试日志输出
 * @param message 日志输出信息
 */
inline fun xLogD(message: Any?) = xLogD(TAG, message.toString())

/**
 * 调试日志输出
 * @param tag 日志输出Tag
 * @param message 日志输出信息
 */
inline fun xLogD(tag: String, message: Any?) = LogHelper.d(tag, message.toString())

/**
 * 信息日志输出
 * @param message 日志输出信息
 */
inline fun xLogI(message: Any?) = xLogI(TAG, message.toString())

/**
 * 信息日志输出
 * @param tag 日志输出Tag
 * @param message 日志输出信息
 */
inline fun xLogI(tag: String, message: Any?) = LogHelper.i(tag, message.toString())

/**
 * 警告日志输出
 * @param message 日志输出信息
 */
inline fun xLogW(message: Any?) = xLogW(TAG, message.toString())

/**
 * 警告日志输出
 * @param tag 日志输出Tag
 * @param message 日志输出信息
 */
inline fun xLogW(tag: String, message: Any?) = LogHelper.w(tag, message.toString())

/**
 * 错误日志输出
 * @param message 日志输出信息
 */
inline fun xLogE(message: Any?) = xLogE(TAG, message.toString())

/**
 * 错误日志输出
 * @param tag 日志输出Tag
 * @param message 日志输出信息
 */
inline fun xLogE(tag: String, message: Any?) = LogHelper.e(tag, message.toString())

/**
 * 致命日志输出
 * @param message 日志输出信息
 */
inline fun xLogF(message: Any?) = xLogF(TAG, message.toString())

/**
 * 致命日志输出
 * @param tag 日志输出Tag
 * @param message 日志输出信息
 */
inline fun xLogF(tag: String, message: Any?) = LogHelper.f(tag, message.toString())