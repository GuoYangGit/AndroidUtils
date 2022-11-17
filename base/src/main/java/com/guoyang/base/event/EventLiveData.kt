package com.guoyang.base.event

import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * 解决数据倒灌的LiveData，可以用来做事件订阅
 * @author Yang.Guo on 2021/6/3.
 * ```
 * val eventLiveData = EventLiveData<String>()
 * // 发送事件
 * eventLiveData.postValue("event")
 * // 订阅事件
 * eventLiveData.observe(this){
 *    // do something
 * }
 * ```
 */
class EventLiveData<T> : UnPeekLiveData<T>()