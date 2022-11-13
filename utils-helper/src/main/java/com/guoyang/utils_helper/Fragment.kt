@file:Suppress("unused")

package com.guoyang.utils_helper

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

/**
 * 创建 Fragment 时伴随参数
 * @param pairs 参数, 例如: "key" to "value"
 */
fun <T : Fragment> T.withArguments(vararg pairs: Pair<String, *>) = apply {
    arguments = bundleOf(*pairs)
}

/**
 * 从序列化中检索到数据
 * @param name 键名
 * @param defValue 默认值
 */
@JvmSynthetic
inline fun <reified T> Fragment?.arguments(name: String? = null, defValue: T? = null) = lazyField {
    lazyField {
        val adjustName = name?: it.name
        val result = when {
            Parcelable::class.java.isAssignableFrom(T::class.java) -> this?.arguments?.getParcelable<Parcelable>(
                adjustName
            ) as? T
            else -> this?.arguments?.getSerializable(adjustName) as? T
        }
        result ?: defValue ?: null as T
    }
}

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