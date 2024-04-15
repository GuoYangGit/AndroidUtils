package com.guoyang.base.ui

/**
 * 加载框接口
 */
interface ILoading {
    /**
     * 显示Loading
     * @param message 显示的信息
     */
    fun showLoading(message: String = "")

    /**
     * 隐藏Loading
     */
    fun dismissLoading()
}