package com.guoyang.base.state

import com.guoyang.base.ui.ILoading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

// <editor-fold desc="[UiState]扩展类">

/**
 * 开始请求回调
 * @param block 事件回调
 */
inline fun <T> UiState<T>.doStart(block: () -> Unit): UiState<T> {
    if (this is UiState.Start) {
        block.invoke()
    }
    return this
}


/**
 * 成功请求回调
 * @param block 事件回调
 */
inline fun <T> UiState<T>.doSuccess(block: (data: T?) -> Unit): UiState<T> {
    if (this is UiState.Success) {
        block.invoke(data)
    }
    return this
}

/**
 * 错误请求回调
 * @param block 事件回调
 */
inline fun <T> UiState<T>.doError(block: (throwable: Throwable) -> Unit): UiState<T> {
    if (this is UiState.Error) {
        block.invoke(error)
    }
    return this
}

/**
 * 将Flow转换为状态布局Flow
 */
fun <T> Flow<T>.asUiStateFlow(): Flow<UiState<T>> {
    return this.map {
        UiState.onSuccess(it)
    }.onStart {
        this.emit(UiState.onStart())
    }.catch {
        this.emit(UiState.onError(it))
    }
}

/**
 * 绑定Loading框
 * @param iLoading 具体实现
 * @param msg 加载显示内容(默认为空)
 */
fun <T> UiState<T>.bindLoading(iLoading: ILoading, msg: String = ""): UiState<T> {
    doStart {
        iLoading.showLoading(msg)
    }.doSuccess {
        iLoading.dismissLoading()
    }.doError {
        iLoading.dismissLoading()
    }
    return this
}

// </editor-fold>