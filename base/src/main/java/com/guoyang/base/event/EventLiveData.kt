package com.guoyang.base.event

import com.kunminx.architecture.ui.callback.UnPeekLiveData

/***
 * 解决数据倒灌的LiveData，可以用来做事件订阅
 * @author Yang.Guo on 2021/6/3.
 */
class EventLiveData<T> : UnPeekLiveData<T>()