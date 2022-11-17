package com.guoyang.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * 提供基类的Application
 * @author Yang.Guo on 2021/5/31.
 */
open class BaseApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}