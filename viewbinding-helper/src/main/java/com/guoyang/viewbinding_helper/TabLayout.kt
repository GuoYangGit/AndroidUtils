@file:Suppress("unused")
package com.guoyang.viewbinding_helper

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.google.android.material.tabs.TabLayout

inline fun <reified VB : ViewBinding> TabLayout.Tab.setCustomView(block: VB.() -> Unit) {
    requireNotNull(parent) { "Tab not attached to a TabLayout" }
    inflateBinding<VB>(LayoutInflater.from(parent!!.context)).apply(block).let { binding ->
        customView = binding.root
        customView?.tag = binding
    }
}

inline fun <reified VB : ViewBinding> TabLayout.updateCustomTab(index: Int, block: VB.() -> Unit) =
    getTabAt(index)?.customView?.getBinding<VB>()?.also(block)

inline fun <reified VB : ViewBinding> TabLayout.doOnCustomTabSelected(
    crossinline onTabUnselected: VB.(TabLayout.Tab) -> Unit = {},
    crossinline onTabReselected: VB.(TabLayout.Tab) -> Unit = {},
    crossinline onTabSelected: VB.(TabLayout.Tab) -> Unit = {},
) =
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            tab.customView?.getBinding<VB>()?.onTabSelected(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            tab.customView?.getBinding<VB>()?.onTabUnselected(tab)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            tab.customView?.getBinding<VB>()?.onTabReselected(tab)
        }
    })