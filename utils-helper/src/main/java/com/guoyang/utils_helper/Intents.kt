@file:Suppress("unused")

package com.guoyang.utils_helper

import android.Manifest.permission.CALL_PHONE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresPermission
import androidx.core.os.bundleOf

/**
 * 创建 Intent
 * @param pairs Intent 参数, 例如: "key" to "value"
 */
inline fun <reified T> Context.intentOf(vararg pairs: Pair<String, *>): Intent =
    intentOf<T>(bundleOf(*pairs))

/**
 * 创建 Intent
 * @param bundle Bundle实例
 */
inline fun <reified T> Context.intentOf(bundle: Bundle): Intent =
    Intent(this, T::class.java).putExtras(bundle)

/**
 * 通过 Intent 的 extras 获取可空的参数
 * @param name 参数名
 */
inline fun <reified T> Activity.intentExtras(name: String) = lazyField {
    intent.extras?.get(name)
}

/**
 * 通过 Intent 的 extras 获取含默认值的参数
 * @param name 参数名
 * @param default 默认值
 */
fun <T> Activity.intentExtras(name: String, default: T) = lazyField {
    intent.extras?.get(name) ?: default
}

/**
 * 通过 Intent 的 extras 获取人为保证非空的参数
 * @param name 参数名
 */
fun <T> Activity.safeIntentExtras(name: String) = lazyField {
    checkNotNull(intent.extras?.get(name)) { "No intent value for key \"$name\"" }
}

/**
 * 拨号
 * @param phoneNumber 电话号码
 */
fun dial(phoneNumber: String): Boolean =
    Intent(Intent.ACTION_DIAL, Uri.parse("tel:${Uri.encode(phoneNumber)}"))
        .startForActivity()

/**
 * 打电话
 * @param phoneNumber 电话号码
 */
@RequiresPermission(CALL_PHONE)
fun makeCall(phoneNumber: String): Boolean =
    Intent(Intent.ACTION_CALL, Uri.parse("tel:${Uri.encode(phoneNumber)}"))
        .startForActivity()

/**
 * 发送短信
 * @param phoneNumber 电话号码
 * @param content 短信内容
 */
fun sendSMS(phoneNumber: String, content: String): Boolean =
    Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${Uri.encode(phoneNumber)}"))
        .putExtra("sms_body", content)
        .startForActivity()

/**
 * 打开网页
 * @param url 网页地址
 * @param newTask 是否在新的任务中打开
 */
fun browse(url: String, newTask: Boolean = false): Boolean =
    Intent(Intent.ACTION_VIEW, Uri.parse(url))
        .apply { if (newTask) newTask() }
        .startForActivity()

/**
 * 发送邮件
 * @param email 邮箱地址
 * @param subject 邮件主题
 * @param content 邮件内容
 */
fun email(email: String, subject: String? = null, content: String? = null): Boolean =
    Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
        .putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        .putExtra(Intent.EXTRA_SUBJECT, subject)
        .putExtra(Intent.EXTRA_TEXT, content)
        .startForActivity()

/**
 * 安装应用
 * @param uri 文件 Uri
 */
fun installAPK(uri: Uri): Boolean =
    Intent(Intent.ACTION_VIEW)
        .setDataAndType(uri, "application/vnd.android.package-archive")
        .newTask()
        .grantReadUriPermission()
        .startForActivity()

/**
 * 启动意图(通过栈顶 Activity)
 */
fun Intent.startForActivity(): Boolean =
    try {
        topActivity.startActivity(this)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

/**
 * 添加 FLAG_ACTIVITY_CLEAR_TASK 的 flag
 */
fun Intent.clearTask(): Intent = addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

/**
 * 添加 FLAG_ACTIVITY_CLEAR_TOP 的 flag
 */
fun Intent.clearTop(): Intent = addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

/**
 * 添加 FLAG_ACTIVITY_NEW_DOCUMENT 的 flag
 */
fun Intent.newDocument(): Intent = addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)

/**
 * 添加 FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS 的 flag
 */
fun Intent.excludeFromRecents(): Intent = addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)

/**
 * 添加 FLAG_ACTIVITY_MULTIPLE_TASK 的 flag
 */
fun Intent.multipleTask(): Intent = addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

/**
 * 添加 FLAG_ACTIVITY_NEW_TASK 的 flag
 */
fun Intent.newTask(): Intent = addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

/**
 * 添加 FLAG_ACTIVITY_NO_ANIMATION 的 flag
 */
fun Intent.noAnimation(): Intent = addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

/**
 * 添加 FLAG_ACTIVITY_NO_HISTORY 的 flag
 */
fun Intent.noHistory(): Intent = addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

/**
 * 添加 FLAG_ACTIVITY_SINGLE_TOP 的 flag
 */
fun Intent.singleTop(): Intent = addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

/**
 * 添加 FLAG_GRANT_READ_URI_PERMISSION 的 flag
 * 授予读取 Uri 权限
 */
fun Intent.grantReadUriPermission(): Intent = apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
}