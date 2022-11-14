@file:Suppress("unused")

package com.guoyang.utils_helper

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * 双击返回退出 App
 * @param toastText 提示文本
 * @param interval 间隔时间
 */
fun Fragment.pressBackTwiceToExitApp(toastText: String, interval: Long = 2000) =
    requireActivity().pressBackTwiceToExitApp(toastText, interval, viewLifecycleOwner)

/**
 * 双击返回退出 App
 * @param toastText 提示文本资源ID
 * @param interval 间隔时间
 */
fun Fragment.pressBackTwiceToExitApp(@StringRes toastText: Int, interval: Long = 2000) =
    requireActivity().pressBackTwiceToExitApp(toastText, interval, viewLifecycleOwner)

/**
 * 双击返回退出 App
 * @param interval 间隔时间
 * @param onFirstBackPressed 第一次点击返回键时回调
 */
fun Fragment.pressBackTwiceToExitApp(interval: Long = 2000, onFirstBackPressed: () -> Unit) =
    requireActivity().pressBackTwiceToExitApp(interval, viewLifecycleOwner, onFirstBackPressed)

/**
 * 点击返回不退出 App
 */
fun Fragment.pressBackToNotExitApp() =
    requireActivity().pressBackToNotExitApp(viewLifecycleOwner)

/**
 * 监听手机的返回事件
 */
fun Fragment.doOnBackPressed(onBackPressed: () -> Unit) =
    requireActivity().doOnBackPressed(viewLifecycleOwner, onBackPressed)