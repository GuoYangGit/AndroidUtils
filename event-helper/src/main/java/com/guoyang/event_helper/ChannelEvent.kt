package com.guoyang.event_helper

/**
 * Channel承载事件的模型
 * @author yang.guo on 2022/11/11
 */
@PublishedApi
internal class ChannelEvent<T>(val event: T, val tag: String? = null)