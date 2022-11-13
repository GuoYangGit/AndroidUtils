package com.guoyang.viewbinding_helper

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

/**
 * Fragment ViewBinding 接口
 */
interface FragmentBinding<VB : ViewBinding> {
    /**
     *  ViewBinding 对象
     */
    val binding: VB

    /**
     * Fragment createView
     */
    fun Fragment.createViewWithBinding(inflater: LayoutInflater, container: ViewGroup?): View
}

/**
 * Fragment ViewBinding 代理实现
 */
class FragmentBindingDelegate<VB : ViewBinding> : FragmentBinding<VB> {
    private var _binding: VB? = null
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    /**
     *  ViewBinding 对象
     */
    override val binding: VB
        get() = requireNotNull(_binding) { "The property of binding has been destroyed." }

    /**
     * Fragment createView
     */
    override fun Fragment.createViewWithBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        if (_binding == null) {
            _binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    handler.post { _binding = null }
                }
            })
        }
        return _binding!!.root
    }
}