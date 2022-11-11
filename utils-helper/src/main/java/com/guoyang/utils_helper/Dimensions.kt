@file:Suppress("unused")

package com.guoyang.utils_helper

import android.content.res.Resources
import android.util.TypedValue

/**
 * dp 转 px
 */
inline val Int.dp: Float get() = toFloat().dp

/**
 * dp 转 px
 */
inline val Long.dp: Float get() = toFloat().dp

/**
 * dp 转 px
 */
inline val Double.dp: Float get() = toFloat().dp

/**
 * dp 转 px
 */
inline val Float.dp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

/**
 * sp 转 px
 */
inline val Int.sp: Float get() = toFloat().sp

/**
 * sp 转 px
 */
inline val Long.sp: Float get() = toFloat().sp

/**
 * sp 转 px
 */
inline val Double.sp: Float get() = toFloat().sp

/**
 * sp 转 px
 */
inline val Float.sp: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )

/**
 * px 转 dp
 */
fun Int.pxToDp(): Int = toFloat().pxToDp()

/**
 * px 转 dp
 */
fun Long.pxToDp(): Int = toFloat().pxToDp()

/**
 * px 转 dp
 */
fun Double.pxToDp(): Int = toFloat().pxToDp()

/**
 * px 转 dp
 */
fun Float.pxToDp(): Int =
    (this / Resources.getSystem().displayMetrics.density + 0.5f).toInt()

/**
 * px 转 sp
 */
fun Int.pxToSp(): Int = toFloat().pxToSp()

/**
 * px 转 sp
 */
fun Long.pxToSp(): Int = toFloat().pxToSp()

/**
 * px 转 sp
 */
fun Double.pxToSp(): Int = toFloat().pxToSp()

/**
 * px 转 sp
 */
fun Float.pxToSp(): Int =
    (this / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()