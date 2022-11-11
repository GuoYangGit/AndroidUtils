@file:Suppress("unused")

package com.guoyang.utils_helper

import android.os.Build

/**
 * 获取设备系统版本号
 */
inline val sdkVersionName: String get() = Build.VERSION.RELEASE

/**
 * 获取设备系统版本码
 */
inline val sdkVersionCode: Int get() = Build.VERSION.SDK_INT

/**
 * 获取设备厂商
 */
inline val deviceManufacturer: String get() = Build.MANUFACTURER

/**
 * 获取设备型号
 */
inline val deviceModel: String get() = Build.MODEL