package com.guoyang.base.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.guoyang.base.ui.IView

/**
 * 通用的[AppCompatActivity]基类
 */
abstract class BaseActivity : AppCompatActivity(), IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (userDataBinding()) {
            initDataBind()
        } else {
            setContentView(layoutId())
        }
        initView(savedInstanceState)
    }

    /**
     * 是否使用DataBinding
     */
    open fun userDataBinding() = false

    /**
     * 供子类BaseVmDbActivity 初始化DataBinding操作
     */
    open fun initDataBind() {}
}