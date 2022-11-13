package com.guoyang.base.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

// <editor-fold desc="时间相关的扩展类">

/**
 * Long的扩展方法
 * 获取当前的格式化日期
 * @param format: 格式化类型(例如yyyy-MM-dd HH:mm:ss)
 */
@SuppressLint("SimpleDateFormat")
fun Long.getDateStr(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    return try {
        if (format.isBlank()) return ""
        val date = Date(this)
        val formatter = SimpleDateFormat(format)
        formatter.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

// </editor-fold>