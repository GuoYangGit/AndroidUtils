@file:Suppress("unused")

package com.guoyang.utils_helper

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import java.util.*

/**
 * App 中 Activity 缓存集合
 */
internal val activityCache = LinkedList<Activity>()

/**
 * 全局启动 Activity 的扩展方法(使用栈顶 Activity 启动)
 * @param intent Intent
 */
fun startActivity(intent: Intent) = topActivity.startActivity(intent)

/**
 * 全局启动 Activity 的扩展方法(使用栈顶 Activity 启动)
 * @param pairs 传递参数, 例如: "key" to "value"
 * @param block Intent 附加操作
 */
inline fun <reified T : Activity> startActivity(
    vararg pairs: Pair<String, Any?>,
    crossinline block: Intent.() -> Unit = {}
) = topActivity.startActivity<T>(pairs = pairs, block = block)

/**
 * 启动Activity 的扩展方法
 * @param pairs 传递参数, 例如: "key" to "value"
 * @param block Intent 附加操作
 */
inline fun <reified T : Activity> Context.startActivity(
    vararg pairs: Pair<String, Any?>,
    crossinline block: Intent.() -> Unit = {}
) = startActivity(intentOf<T>(*pairs).apply(block))

/**
 * 关闭 Activity 并返回结果
 * @param pairs 传递参数, 例如: "key" to "value"
 */
fun Activity.finishWithResult(vararg pairs: Pair<String, *>) {
    setResult(Activity.RESULT_OK, Intent().putExtras(bundleOf(*pairs)))
    finish()
}

/**
 * 获取应用中 Activity 集合
 */
val activityList: List<Activity> get() = activityCache.toList()

/**
 * 获取应用中栈顶 Activity
 */
val topActivity: Activity get() = activityCache.last()

/**
 * 判断 Activity 是否存在栈中
 */
inline fun <reified T : Activity> isActivityExistsInStack(): Boolean =
    isActivityExistsInStack(T::class.java)

/**
 * 判断 Activity 是否存在栈中
 * @param clazz Activity
 */
fun <T : Activity> isActivityExistsInStack(clazz: Class<T>): Boolean =
    activityCache.any { it.javaClass.name == clazz.name }

/**
 * 结束某个 Activity
 */
inline fun <reified T : Activity> finishActivity(): Boolean = finishActivity(T::class.java)

/**
 * 结束某个 Activity
 * @param clazz Activity
 */
fun <T : Activity> finishActivity(clazz: Class<T>): Boolean =
    activityCache.removeAll {
        if (it.javaClass.name == clazz.name) it.finish()
        it.javaClass.name == clazz.name
    }

/**
 * 结束到某个 Activity(该 Activity 栈顶元素都会被移除)
 */
inline fun <reified T : Activity> finishToActivity(): Boolean = finishToActivity(T::class.java)

/**
 * 结束到某个 Activity(该 Activity 栈顶元素都会被移除)
 * @param clazz Activity
 */
fun <T : Activity> finishToActivity(clazz: Class<T>): Boolean {
    for (i in activityCache.count() - 1 downTo 0) {
        if (clazz.name == activityCache[i].javaClass.name) {
            return true
        }
        activityCache.removeAt(i).finish()
    }
    return false
}

/**
 * 结束所有 Activity
 */
fun finishAllActivities(): Boolean =
    activityCache.removeAll {
        it.finish()
        true
    }

/**
 * 结束除某个 Activity 之外的所有 Activity
 */
inline fun <reified T : Activity> finishAllActivitiesExcept(): Boolean =
    finishAllActivitiesExcept(T::class.java)

/**
 * 结束除某个 Activity 之外的所有 Activity
 * @param clazz Activity
 */
fun <T : Activity> finishAllActivitiesExcept(clazz: Class<T>): Boolean =
    activityCache.removeAll {
        if (it.javaClass.name != clazz.name) it.finish()
        it.javaClass.name != clazz.name
    }

/**
 * 结束除最新 Activity 之外的所有 Activity
 */
fun finishAllActivitiesExceptNewest(): Boolean =
    finishAllActivitiesExcept(topActivity.javaClass)

/**
 * 双击返回退出 App
 * @param toastText 双击返回提示文本
 * @param delayMillis 退出延迟时间
 * @param owner LifecycleOwner
 */
fun ComponentActivity.pressBackTwiceToExitApp(
    toastText: String,
    delayMillis: Long = 2000,
    owner: LifecycleOwner = this
) = pressBackTwiceToExitApp(delayMillis, owner) { toast(toastText) }

/**
 * 双击返回退出 App
 * @param toastText 双击返回提示文本ResID
 * @param delayMillis 退出延迟时间
 * @param owner LifecycleOwner
 */
fun ComponentActivity.pressBackTwiceToExitApp(
    @StringRes toastText: Int,
    delayMillis: Long = 2000,
    owner: LifecycleOwner = this
) = pressBackTwiceToExitApp(delayMillis, owner) { toast(toastText) }

/**
 * 双击返回退出 App
 * @param delayMillis 退出延迟时间
 * @param owner LifecycleOwner
 * @param onFirstBackPressed 第一次点击返回键回调
 */
fun ComponentActivity.pressBackTwiceToExitApp(
    delayMillis: Long = 2000,
    owner: LifecycleOwner = this,
    onFirstBackPressed: () -> Unit
) = onBackPressedDispatcher.addCallback(owner, object : OnBackPressedCallback(true) {
    private var lastBackTime: Long = 0

    override fun handleOnBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackTime > delayMillis) {
            onFirstBackPressed()
            lastBackTime = currentTime
        } else {
            finishAllActivities()
        }
    }
})

/**
 * 点击返回不退出 App
 * @param owner LifecycleOwner
 */
fun ComponentActivity.pressBackToNotExitApp(owner: LifecycleOwner = this) =
    doOnBackPressed(owner) { moveTaskToBack(false) }

/**
 * 监听手机的返回事件
 * @param owner LifecycleOwner
 * @param onBackPressed 返回事件回调
 */
fun ComponentActivity.doOnBackPressed(owner: LifecycleOwner = this, onBackPressed: () -> Unit) =
    onBackPressedDispatcher.addCallback(owner, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() = onBackPressed()
    })

/**
 * 判断是否有权限
 * @param permission 权限
 */
fun Context.isPermissionGranted(permission: String): Boolean =
    ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

/**
 * 判断是否有权限
 * @param permissions 权限数组
 */
fun Context.arePermissionsGranted(vararg permissions: String): Boolean =
    permissions.all { isPermissionGranted(it) }

/**
 * 把 Context 作为 Activity 使用
 */
fun Context.asActivity(): Activity? =
    this as? Activity ?: (this as? ContextWrapper)?.baseContext?.asActivity()

/**
 * 作用域的 this 不是 Activity 时获取 Context
 */
inline val Context.context: Context get() = this

/**
 * 作用域的 this 不是 Activity 时获取 Activity
 */
inline val Activity.activity: Activity get() = this

/**
 * 作用域的 this 不是 Activity 时获取 FragmentActivity
 */
inline val FragmentActivity.fragmentActivity: FragmentActivity get() = this

/**
 * 作用域的 this 不是 Activity 时获取 LifecycleOwner
 */
inline val ComponentActivity.lifecycleOwner: LifecycleOwner get() = this