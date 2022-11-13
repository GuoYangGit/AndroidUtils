package com.guoyang.base.state

/**
 * 数据和UI相关的状态类
 * @author Yang.Guo on 2021/6/3.
 */
sealed class UiState<out T> {
    companion object {
        fun <T> onStart(): UiState<T> = Start()

        fun <T> onSuccess(data: T?): UiState<T> = Success(data)

        fun <T> onError(error: Throwable): UiState<T> = Error(error)
    }

    data class Start(val isRefresh: Boolean = true) : UiState<Nothing>()

    data class Success<out T>(val data: T?) : UiState<T>()

    data class Error(val error: Throwable) : UiState<Nothing>()
}

interface IPageSate {
    /**
     * 是否还有更多数据
     */
    fun hasMore(): Boolean

    /**
     * 是否是空数据
     */
    fun hasEmpty(): Boolean
}