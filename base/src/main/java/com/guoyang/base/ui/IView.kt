package com.guoyang.base.ui

import android.os.Bundle

/**
 * Activity、Fragment的基类接口
 * @author Yang.Guo on 2021/6/3.
 */
interface IView {
    /**
     * 获取布局文件ID
     * @return 布局文件ID
     */
    fun layoutId(): Int

    /**
     * 初始化数据
     * @param savedInstanceState: Bundle? 保存的数据
     */
    fun initView(savedInstanceState: Bundle?)
}