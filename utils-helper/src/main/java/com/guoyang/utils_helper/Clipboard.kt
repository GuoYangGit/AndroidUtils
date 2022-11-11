@file:Suppress("unused")

package com.guoyang.utils_helper

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ClipboardManager.OnPrimaryClipChangedListener
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * 复制到剪贴板
 * CharSequence 扩展方法
 * @param label 需要复制的文本
 */
fun CharSequence.copyToClipboard(label: CharSequence? = null) =
    (application.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)
        ?.setPrimaryClip(ClipData.newPlainText(label, this))

/**
 * 复制到剪贴板
 * Uri 扩展方法
 * @param label 需要复制的文本
 */
fun Uri.copyToClipboard(label: CharSequence? = null) =
    (application.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)
        ?.setPrimaryClip(ClipData.newUri(application.contentResolver, label, this))

/**
 * 复制到剪贴板
 * Intent 扩展方法
 * @param label 需要复制的文本
 */
fun Intent.copyToClipboard(label: CharSequence? = null) =
    (application.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)
        ?.setPrimaryClip(ClipData.newIntent(label, this))

/**
 * 获取剪贴板的文本
 * @return 剪贴板的文本
 */
fun getTextFromClipboard(): CharSequence? =
    (application.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)
        ?.primaryClip?.takeIf { it.itemCount > 0 }?.getItemAt(0)?.coerceToText(application)

/**
 * 清理剪贴板
 */
fun clearClipboard() = (application.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)
    ?.setPrimaryClip(ClipData.newPlainText(null, ""))

/**
 * 监听剪贴板的变化
 * @param listener 剪贴板变化的监听器
 */
fun doOnClipboardChanged(listener: OnPrimaryClipChangedListener): OnPrimaryClipChangedListener =
    (application.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)
        ?.addPrimaryClipChangedListener(listener).let { listener }

/**
 * 移除剪贴板监听器
 * ```
 * ClipboardManager.OnPrimaryClipChangedListener.cancel()
 * ```
 */
fun OnPrimaryClipChangedListener.cancel() =
    (application.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)
        ?.removePrimaryClipChangedListener(this)