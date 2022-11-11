@file:Suppress("unused")

package com.guoyang.utils_helper

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

/**
 * 根据 id 获取字符串
 * @param id 资源id
 * @return String 字符串
 */
fun View.getString(@StringRes id: Int): String =
    context.getString(id)

/**
 * 根据 id 获取数字
 * @param id 资源id
 * @return Int 数字
 */
fun Fragment.getDimension(@DimenRes id: Int): Float =
    requireContext().getDimension(id)

/**
 * 根据 id 获取数字
 * @param id 资源id
 * @return Int 数字
 */
fun View.getDimension(@DimenRes id: Int): Float =
    context.getDimension(id)

/**
 * 根据 id 获取数字
 * @param id 资源id
 * @return Int 数字
 */
fun Context.getDimension(@DimenRes id: Int): Float =
    resources.getDimension(id)

/**
 * 根据 id 获取颜色
 * @param id 资源id
 * @return Int 颜色
 */
@ColorInt
fun Fragment.getCompatColor(@ColorRes id: Int): Int =
    requireContext().getCompatColor(id)

/**
 * 根据 id 获取颜色
 * @param id 资源id
 * @return Int 颜色
 */
@ColorInt
fun View.getCompatColor(@ColorRes id: Int): Int =
    context.getCompatColor(id)

/**
 * 根据 id 获取颜色
 * @param id 资源id
 * @return Int 颜色
 */
@ColorInt
fun Context.getCompatColor(@ColorRes id: Int): Int =
    ResourcesCompat.getColor(resources, id, null)

/**
 * 根据 id 获取 Drawable
 * @param id 资源id
 * @return Drawable
 */
fun Fragment.getCompatDrawable(@DrawableRes id: Int): Drawable? =
    requireContext().getCompatDrawable(id)

/**
 * 根据 id 获取 Drawable
 * @param id 资源id
 * @return Drawable
 */
fun View.getCompatDrawable(@DrawableRes id: Int): Drawable? =
    context.getCompatDrawable(id)

/**
 * 根据 id 获取 Drawable
 * @param id 资源id
 * @return Drawable
 */
fun Context.getCompatDrawable(@DrawableRes id: Int): Drawable? =
    ResourcesCompat.getDrawable(resources, id, null)

/**
 * 根据 id 获取 字体
 * @param id 资源id
 * @return Typeface 字体
 */
fun Fragment.getCompatFont(@FontRes id: Int): Typeface? =
    requireContext().getCompatFont(id)

/**
 * 根据 id 获取 字体
 * @param id 资源id
 * @return Typeface 字体
 */
fun View.getCompatFont(@FontRes id: Int): Typeface? =
    context.getCompatFont(id)

/**
 * 根据 id 获取 字体
 * @param id 资源id
 * @return Typeface 字体
 */
fun Context.getCompatFont(@FontRes id: Int): Typeface? =
    ResourcesCompat.getFont(this, id)