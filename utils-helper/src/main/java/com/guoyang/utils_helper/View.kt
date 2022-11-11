@file:Suppress("unused")

package com.guoyang.utils_helper

import android.content.res.TypedArray
import android.graphics.Outline
import android.graphics.Rect
import android.util.AttributeSet
import android.view.*
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 记录 View 上次点击时间
 */
private var View.lastClickTime: Long? by viewTags(R.id.tag_last_click_time)

/**
 * 默认点击间隔时间
 */
var debouncingClickIntervals = 500


/**
 * View的点击事件(不防抖)
 */
inline fun View.doOnClick(crossinline block: () -> Unit) = setOnClickListener { block() }

/**
 * View的防抖点击事件
 * @param interval 点击间隔时间, 默认 [debouncingClickIntervals] 毫秒
 * @param block 点击事件
 */
fun View.doOnDebouncingClick(interval: Int = debouncingClickIntervals, block: () -> Unit) =
    doOnClick {
        val currentTime = System.currentTimeMillis()
        if (currentTime - (lastClickTime ?: 0L) > interval) {
            lastClickTime = currentTime
            block()
        }
    }

/**
 * View的长按事件(不防抖)
 */
inline fun View.doOnLongClick(crossinline block: () -> Unit) = setOnLongClickListener {
    block()
    true
}

/**
 * View的防抖长按事件
 * @param interval 点击间隔时间, 默认 [debouncingClickIntervals] 毫秒
 * @param block 点击事件
 */
fun View.doOnDebouncingLongClick(
    interval: Int = debouncingClickIntervals,
    block: () -> Unit
) = doOnLongClick {
    val currentTime = System.currentTimeMillis()
    if (currentTime - (lastClickTime ?: 0L) > interval) {
        lastClickTime = currentTime
        block()
    }
}

/**
 * 增大点击区域
 * @param expandSize 增大的区域大小
 */
fun View.expandClickArea(expandSize: Float) = expandClickArea(expandSize.toInt())

/**
 * 增大点击区域
 * @param expandSize 增大的区域大小
 */
fun View.expandClickArea(expandSize: Int) =
    expandClickArea(expandSize, expandSize, expandSize, expandSize)


/**
 * 增大点击区域
 * @param top 增大的区域大小
 * @param left 增大的区域大小
 * @param right 增大的区域大小
 * @param bottom 增大的区域大小
 */
fun View.expandClickArea(top: Float, left: Float, right: Float, bottom: Float) =
    expandClickArea(top.toInt(), left.toInt(), right.toInt(), bottom.toInt())

/**
 * 增大点击区域
 * @param top 增大的区域大小
 * @param left 增大的区域大小
 * @param right 增大的区域大小
 * @param bottom 增大的区域大小
 */
fun View.expandClickArea(top: Int, left: Int, right: Int, bottom: Int) {
    val parent = parent as? ViewGroup ?: return
    parent.post {
        val rect = Rect()
        getHitRect(rect)
        rect.top -= top
        rect.left -= left
        rect.right += right
        rect.bottom += bottom
        val touchDelegate = parent.touchDelegate
        if (touchDelegate == null || touchDelegate !is MultiTouchDelegate) {
            parent.touchDelegate = MultiTouchDelegate(rect, this)
        } else {
            touchDelegate.put(rect, this)
        }
    }
}

/**
 * 设置 View 的圆角
 * @param radius 圆角半径
 */
fun View.setRoundCorner(radius: Float) {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.width, view.height, radius)
        }
    }
}

/**
 * 判断控件是否在触摸位置上
 * @param x 触摸位置的 x 坐标
 * @param y 触摸位置的 y 坐标
 */
fun View?.isTouchedAt(x: Float, y: Float): Boolean =
    isTouchedAt(x.toInt(), y.toInt())

/**
 * 判断控件是否在触摸位置上
 * @param x 触摸位置的 x 坐标
 * @param y 触摸位置的 y 坐标
 */
fun View?.isTouchedAt(x: Int, y: Int): Boolean =
    this?.locationOnScreen?.contains(x, y) == true

/**
 * 寻找触摸位置上的子控件
 * @param x 触摸位置的 x 坐标
 * @param y 触摸位置的 y 坐标
 */
fun View.findTouchedChild(x: Float, y: Float): View? =
    findTouchedChild(x.toInt(), y.toInt())

/**
 * 寻找触摸位置上的子控件
 * @param x 触摸位置的 x 坐标
 * @param y 触摸位置的 y 坐标
 */
fun View.findTouchedChild(x: Int, y: Int): View? =
    touchables.find { it.isTouchedAt(x, y) }

/**
 * 获取控件在屏幕的位置
 * @return 控件在屏幕的位置 [Rect]
 */
inline val View.locationOnScreen: Rect
    get() = IntArray(2).let {
        getLocationOnScreen(it)
        Rect(it[0], it[1], it[0] + width, it[1] + height)
    }

/**
 * 获取View自定义属性
 * @param attrs 属性集合
 * @param defStyleAttr 默认样式
 * @param defStyleRes 默认样式资源
 * @param block 获取属性的回调
 */
inline fun View.withStyledAttributes(
    set: AttributeSet?,
    @StyleableRes attrs: IntArray,
    @AttrRes defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
    block: TypedArray.() -> Unit
) = context.withStyledAttributes(set, attrs, defStyleAttr, defStyleRes, block)

/**
 * 增大点击区域的触摸代理
 */
class MultiTouchDelegate(bound: Rect, delegateView: View) : TouchDelegate(bound, delegateView) {
    private val map = mutableMapOf<View, Pair<Rect, TouchDelegate>>()
    private var targetDelegate: TouchDelegate? = null

    init {
        put(bound, delegateView)
    }

    fun put(bound: Rect, delegateView: View) {
        map[delegateView] = bound to TouchDelegate(bound, delegateView)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                targetDelegate = map.entries.find { it.value.first.contains(x, y) }?.value?.second
            }
            MotionEvent.ACTION_CANCEL -> {
                targetDelegate = null
            }
        }
        return targetDelegate?.onTouchEvent(event) ?: false
    }
}

fun <T> viewTags(key: Int) = object : ReadWriteProperty<View, T?> {
    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: View, property: KProperty<*>) =
        thisRef.getTag(key) as? T

    override fun setValue(thisRef: View, property: KProperty<*>, value: T?) =
        thisRef.setTag(key, value)
}

@Suppress("UNCHECKED_CAST")
fun <T> viewTags(key: Int, block: View.() -> T) = ReadOnlyProperty<View, T> { thisRef, _ ->
    if (thisRef.getTag(key) == null) {
        thisRef.setTag(key, block(thisRef))
    }
    thisRef.getTag(key) as T
}

val View.rootWindowInsetsCompat: WindowInsetsCompat? by viewTags(R.id.tag_root_window_insets) {
    ViewCompat.getRootWindowInsets(this)
}

val View.windowInsetsControllerCompat: WindowInsetsControllerCompat? by viewTags(R.id.tag_window_insets_controller) {
    ViewCompat.getWindowInsetsController(this)
}