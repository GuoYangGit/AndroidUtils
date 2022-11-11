package com.guoyang.xloghelper

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.os.Process
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog

/**
 * 日志帮助类
 * @author yang.guo on 2022/10/28
 */
object LogHelper {

    /**
     * 初始化日志库
     * @param isDebug 是否debug模式
     * @param logPath 日志存储路径
     * @param apply 其他设置
     */
    fun init(context: Context, isDebug: Boolean, logPath: String, apply: LogConfig.() -> Unit) {
        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")
        var processName: String? = null
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val myPid = Process.myPid()
        val var3: Iterator<*> = am.runningAppProcesses.iterator()
        while (var3.hasNext()) {
            val appProcessInfo = var3.next() as RunningAppProcessInfo
            if (appProcessInfo.pid == myPid) {
                processName = appProcessInfo.processName
                break
            }
        }
        if (processName.isNullOrBlank()) return
        val namePrefix = if (!processName.contains(":")) "uplog"
        else "uplog_" + processName.substring(processName.indexOf(":") + 1)
        val config = LogConfig(isDebug, "$logPath/xlog", "$logPath/cache", namePrefix)
        config.apply(apply)
        Xlog.appenderOpen(
            if (config.isDebug) Xlog.LEVEL_DEBUG else Xlog.LEVEL_INFO,
            if (config.isDebug) Xlog.AppednerModeSync else Xlog.AppednerModeAsync,
            config.cachePath,
            config.logPath,
            config.namePrefix,
            config.cacheDays,
            config.pubKey
        )
        Xlog.setConsoleLogOpen(config.isDebug)
        Log.setLogImp(Xlog())
    }

    fun v(tag: String?, info: String?) {
        if (info == null) return
        val stack = Throwable().stackTrace
        if (stack.isNullOrEmpty()) return
        val s = stack[1]
        val names = s.className.split("\\.").toTypedArray()
        Log.v(tag, names[names.size - 1], s.methodName, s.lineNumber, info)
    }

    fun d(tag: String?, info: String?) {
        if (info == null) return
        val stack = Throwable().stackTrace
        if (stack.isNullOrEmpty()) return
        val s = stack[1]
        val names = s.className.split("\\.").toTypedArray()
        Log.d(tag, names[names.size - 1], s.methodName, s.lineNumber, info)
    }

    fun i(tag: String?, info: String?) {
        if (info == null) return
        val stack = Throwable().stackTrace
        if (stack.isNullOrEmpty()) return
        val s = stack[1]
        val names = s.className.split("\\.").toTypedArray()
        Log.i(tag, names[names.size - 1], s.methodName, s.lineNumber, info)
    }

    fun w(tag: String?, info: String?) {
        if (info == null) return
        val stack = Throwable().stackTrace
        if (stack.isNullOrEmpty()) return
        val s = stack[1]
        val names = s.className.split("\\.").toTypedArray()
        Log.w(tag, names[names.size - 1], s.methodName, s.lineNumber, info)
    }

    fun e(tag: String?, info: String?) {
        if (info == null) return
        val stack = Throwable().stackTrace
        if (stack.isNullOrEmpty()) return
        val s = stack[1]
        val names = s.className.split("\\.").toTypedArray()
        Log.e(tag, names[names.size - 1], s.methodName, s.lineNumber, info)
    }

    fun f(tag: String?, info: String?) {
        if (info == null) return
        val stack = Throwable().stackTrace
        if (stack.isNullOrEmpty()) return
        val s = stack[1]
        val names = s.className.split("\\.").toTypedArray()
        Log.f(tag, names[names.size - 1], s.methodName, s.lineNumber, info)
    }

    /**
     * 关闭日志
     */
    fun closeLog() {
        Log.appenderClose()
    }
}