package com.guoyang.viewbinding_helper

import android.view.View
import androidx.viewbinding.ViewBinding

inline fun <reified VB : ViewBinding> View.getBinding() = getBinding(VB::class.java)

@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> View.getBinding(clazz: Class<VB>) =
    getTag(R.id.tag_view_binding) as? VB ?: (clazz.getMethod("bind", View::class.java)
        .invoke(null, this) as VB)
        .also { setTag(R.id.tag_view_binding, it) }