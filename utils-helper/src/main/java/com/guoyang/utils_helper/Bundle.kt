package com.guoyang.utils_helper

import android.app.Activity
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 从序列化中检索到数据
 * @param name 键名
 * @param defValue 默认值
 */
inline fun <reified T> Activity?.bundle(defValue: T? = null, name: String? = null) =
    lazyField {
        val adjustName = name ?: it.name
        val result = when {
            Parcelable::class.java.isAssignableFrom(T::class.java) -> this?.intent?.getParcelableExtra<Parcelable>(
                adjustName
            ) as? T
            else -> this?.intent?.getSerializableExtra(adjustName) as? T
        }
        result ?: defValue ?: null as T
    }


/**
 * 从序列化中检索到数据
 * @param name 键名
 * @param defValue 默认值
 */
@JvmSynthetic
inline fun <reified T> Fragment?.bundle(defValue: T? = null, name: String? = null) =
    lazyField {
        val adjustName = name ?: it.name
        val result = when {
            Parcelable::class.java.isAssignableFrom(T::class.java) -> this?.arguments?.getParcelable<Parcelable>(
                adjustName
            ) as? T
            else -> this?.arguments?.getSerializable(adjustName) as? T
        }
        result ?: defValue ?: null as T
    }

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