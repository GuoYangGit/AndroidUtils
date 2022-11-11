package com.guoyang.utils_helper

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 延迟初始化
 * 线程安全
 * 等效于[lazy], 但是可以获取委托字段属性
 */
@Suppress("UNCHECKED_CAST")
fun <T, V> T.lazyField(block: T.(KProperty<*>) -> V) = object : ReadWriteProperty<T, V> {
    @Volatile
    private var value: V? = null
    override fun getValue(thisRef: T, property: KProperty<*>): V {

        return synchronized(this) {
            if (value == null) {
                value = block(thisRef, property)
                value as V
            } else value as V
        }
    }

    override fun setValue(thisRef: T, property: KProperty<*>, value: V) {
        synchronized(this) {
            this.value = value
        }
    }
}