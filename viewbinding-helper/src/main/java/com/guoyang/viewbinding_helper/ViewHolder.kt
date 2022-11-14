@file:Suppress("unused")
package com.guoyang.viewbinding_helper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

inline fun <reified VB : ViewBinding> RecyclerView.ViewHolder.withBinding(block: VB.(RecyclerView.ViewHolder) -> Unit) = apply {
    block(getBinding(), this@withBinding)
}

fun <VB : ViewBinding> BindingViewHolder<VB>.withBinding(block: VB.(BindingViewHolder<VB>) -> Unit) = apply {
    block(binding, this@withBinding)
}

inline fun <reified VB : ViewBinding> RecyclerView.ViewHolder.getBinding() = itemView.getBinding<VB>()

inline fun <reified VB : ViewBinding> BindingViewHolder(parent: ViewGroup) =
    BindingViewHolder(inflateBinding<VB>(parent))

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {

    constructor(parent: ViewGroup, inflate: (LayoutInflater, ViewGroup, Boolean) -> VB) :
            this(inflate(LayoutInflater.from(parent.context), parent, false))
}

fun <T> OnItemClickListener<T>.onItemClick(holder: RecyclerView.ViewHolder, block: (Int) -> T) =
    onItemClick(block(holder.adapterPosition), holder.adapterPosition)

fun interface OnItemClickListener<T> {
    fun onItemClick(item: T, position: Int)
}