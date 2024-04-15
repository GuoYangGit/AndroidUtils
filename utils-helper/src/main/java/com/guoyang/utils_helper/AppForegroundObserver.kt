@file:Suppress("unused")

package com.guoyang.utils_helper

import androidx.lifecycle.*

/**
 * App前后台切换监听
 * ```
 * AppForegroundObserver().observe(this) {
 *     Log.d("AppLifeLiveData", "当前是否在前台: $it")
 * })
 * ```
 */
class AppForegroundObserver : LifecycleEventObserver {
    private var observer: Observer<Boolean>? = null

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> observer?.onChanged(true)
            Lifecycle.Event.ON_STOP -> observer?.onChanged(false)
            else -> {}
        }
    }

    fun observe(lifecycleOwner: LifecycleOwner? = null, observer: Observer<Boolean>) {
        lifecycleOwner?.lifecycle?.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (event == Lifecycle.Event.ON_DESTROY) {
                    ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
                }
            }
        })
        this.observer = observer
    }
}