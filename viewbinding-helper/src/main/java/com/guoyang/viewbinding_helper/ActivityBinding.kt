package com.guoyang.viewbinding_helper

import android.app.Activity
import androidx.viewbinding.ViewBinding

/**
 * Activity ViewBinding 接口
 */
interface ActivityBinding<VB : ViewBinding> {
    /**
     *  ViewBinding 对象
     */
    val binding: VB

    /**
     * Activity setContent
     */
    fun Activity.setContentViewWithBinding()
}

/**
 * Activity ViewBinding 代理实现
 */
class ActivityBindingDelegate<VB : ViewBinding> : ActivityBinding<VB> {
    private lateinit var _binding: VB

    /**
     *  ViewBinding 对象
     */
    override val binding: VB get() = _binding

    /**
     * Activity setContent
     */
    override fun Activity.setContentViewWithBinding() {
        _binding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(binding.root)
    }
}