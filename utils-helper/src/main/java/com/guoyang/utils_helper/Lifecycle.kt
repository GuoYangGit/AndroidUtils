@file:Suppress("unused")

package com.guoyang.utils_helper

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope

/**
 * 监听所有 Activity 的生命周期
 * @param onActivityCreated Activity 创建时回调
 * @param onActivityStarted Activity 启动时回调
 * @param onActivityResumed Activity 恢复时回调
 * @param onActivityPaused Activity 暂停时回调
 * @param onActivityStopped Activity 停止时回调
 * @param onActivitySaveInstanceState Activity 保存状态时回调
 * @param onActivityDestroyed Activity 销毁时回调
 */
fun Application.doOnActivityLifecycle(
    onActivityCreated: ((Activity, Bundle?) -> Unit)? = null,
    onActivityStarted: ((Activity) -> Unit)? = null,
    onActivityResumed: ((Activity) -> Unit)? = null,
    onActivityPaused: ((Activity) -> Unit)? = null,
    onActivityStopped: ((Activity) -> Unit)? = null,
    onActivitySaveInstanceState: ((Activity, Bundle?) -> Unit)? = null,
    onActivityDestroyed: ((Activity) -> Unit)? = null,
): Application.ActivityLifecycleCallbacks =
    object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            onActivityCreated?.invoke(activity, savedInstanceState)
        }

        override fun onActivityStarted(activity: Activity) {
            onActivityStarted?.invoke(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            onActivityResumed?.invoke(activity)
        }

        override fun onActivityPaused(activity: Activity) {
            onActivityPaused?.invoke(activity)
        }

        override fun onActivityStopped(activity: Activity) {
            onActivityStopped?.invoke(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            onActivitySaveInstanceState?.invoke(activity, outState)
        }

        override fun onActivityDestroyed(activity: Activity) {
            onActivityDestroyed?.invoke(activity)
        }
    }.also {
        // 注册Activity生命周期回调
        registerActivityLifecycleCallbacks(it)
    }

/**
 * 监听当前 Activity 或 Fragment 生命周期
 * @param onCreate Activity 或 Fragment 创建时回调
 * @param onStart Activity 或 Fragment 启动时回调
 * @param onResume Activity 或 Fragment 恢复时回调
 * @param onPause Activity 或 Fragment 暂停时回调
 * @param onStop Activity 或 Fragment 停止时回调
 * @param onDestroy Activity 或 Fragment 销毁时回调
 */
fun LifecycleOwner.doOnLifecycle(
    onCreate: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onPause: (() -> Unit)? = null,
    onStop: (() -> Unit)? = null,
    onDestroy: (() -> Unit)? = null,
) = lifecycle.addObserver(object : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        onCreate?.invoke()
    }

    override fun onStart(owner: LifecycleOwner) {
        onStart?.invoke()
    }

    override fun onResume(owner: LifecycleOwner) {
        onResume?.invoke()
    }

    override fun onPause(owner: LifecycleOwner) {
        onPause?.invoke()
    }

    override fun onStop(owner: LifecycleOwner) {
        onStop?.invoke()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        onDestroy?.invoke()
    }
})

/**
 * 监听当前 Fragment 生命周期
 * @param onCreateView Fragment 创建视图时回调
 * @param onDestroyView Fragment 销毁视图时回调
 * @param onStart Fragment 启动时回调
 * @param onStop Fragment 停止时回调
 * @param onResume Fragment 恢复时回调
 * @param onPause Fragment 暂停时回调
 */
fun Fragment.doOnViewLifecycle(
    onCreateView: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onPause: (() -> Unit)? = null,
    onStop: (() -> Unit)? = null,
    onDestroyView: (() -> Unit)? = null,
) = viewLifecycleOwner.doOnLifecycle(
    onCreateView,
    onStart,
    onResume,
    onPause,
    onStop,
    onDestroyView
)

/**
 * 获取 Fragment 中 View 的生命周期作用域
 */
val Fragment.viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope