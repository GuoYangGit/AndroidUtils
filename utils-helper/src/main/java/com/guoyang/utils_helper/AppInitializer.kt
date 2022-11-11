@file:Suppress("unused")

package com.guoyang.utils_helper

import android.app.Application
import android.content.Context
import androidx.startup.Initializer

/**
 * 应用初始化类，用于初始化一些上下文相关的东西
 * @author yang.guo on 2022/11/11
 */
class AppInitializer : Initializer<Unit> {
    override fun dependencies() = emptyList<Class<Initializer<*>>>()

    override fun create(context: Context) {
        if (isApplicationInitialized) return
        application = context as Application
        application.doOnActivityLifecycle(
            onActivityCreated = { activity, _ ->
                activityCache.add(activity)
            },
            onActivityDestroyed = { activity ->
                activityCache.remove(activity)
            }
        )
    }
}