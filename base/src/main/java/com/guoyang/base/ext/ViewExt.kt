package com.guoyang.base.ext

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat

// <editor-fold desc="View相关的扩展类">

/**
 * View双击拓展
 * @param block: 双击回调事件
 */
@SuppressLint("ClickableViewAccessibility")
inline fun <T : View> T.doOnDoubleClick(crossinline block: (T) -> Unit) {
    setOnClickListener {  }
    val gestureDetector = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            block(this@doOnDoubleClick)
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            this@doOnDoubleClick.performClick()
            return true
        }
    }
    val gestureDetectorCompat = GestureDetectorCompat(context, gestureDetector)
    this.setOnTouchListener { _, event ->
        gestureDetectorCompat.onTouchEvent(event)
        return@setOnTouchListener true
    }
}

// </editor-fold>