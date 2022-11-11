@file:Suppress("unused")

package com.guoyang.utils_helper

import android.os.Build

/**
 * 判断是否是小米 Rom
 */
inline val isXiaomiRom: Boolean get() = isRomOf("xiaomi")

/**
 * 判断是否是华为 Rom
 */
inline val isHuaweiRom: Boolean get() = isRomOf("huawei")

/**
 * 判断是否是 OPPO Rom
 */
inline val isOppoRom: Boolean get() = isRomOf("oppo")

/**
 * 判断是否是 VIVO Rom
 */
inline val isVivoRom: Boolean get() = isRomOf("vivo")

/**
 * 判断是否是一加 Rom
 */
inline val isOnePlusRom: Boolean get() = isRomOf("oneplus")

/**
 * 判断是否是锤子 Rom
 */
inline val isSmartisanRom: Boolean get() = isRomOf("smartisan", "deltainno")

/**
 * 判断是否是魅族 Rom
 */
inline val isMeiZuRom: Boolean get() = isRomOf("meizu")

/**
 * 判断是否是三星 Rom
 */
inline val isSamsungRom: Boolean get() = isRomOf("samsung")

/**
 * 判断是否是谷歌 Rom
 */
inline val isGoogleRom: Boolean get() = isRomOf("google")

/**
 * 判断是否是索尼 Rom
 */
inline val isSonyRom: Boolean get() = isRomOf("sony")

/**
 * 判断是否是指定 Rom
 * @param names Rom 名称
 * @return Boolean 是否是指定 Rom
 */
fun isRomOf(vararg names: String): Boolean =
    names.any { it.contains(Build.BRAND, true) || it.contains(Build.MANUFACTURER, true) }

/**
 * 判断是否是鸿蒙系统
 */
val isHarmonyOS: Boolean
    get() {
        try {
            val clazz = Class.forName("com.huawei.system.BuildEx")
            val classLoader = clazz.classLoader
            if (classLoader != null && classLoader.parent == null) {
                return clazz.getMethod("getOsBrand").invoke(clazz) == "harmony"
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }