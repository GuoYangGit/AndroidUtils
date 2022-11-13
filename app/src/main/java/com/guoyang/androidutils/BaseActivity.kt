package com.guoyang.androidutils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.guoyang.viewbinding_helper.ActivityBinding
import com.guoyang.viewbinding_helper.ActivityBindingDelegate

/**
 * @author yang.guo on 2022/11/13
 * @describe Activity基类
 */
abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(),
    ActivityBinding<T> by ActivityBindingDelegate() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithBinding()
        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)
}