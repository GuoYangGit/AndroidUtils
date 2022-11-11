@file:Suppress("unused")

package com.guoyang.utils_helper

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

val applicationViewModelStore by lazy { ViewModelStore() }

/**
 * 获取 Application 级别的 ViewModel 实例，支持 Hilt
 * @param factoryProducer ViewModelProvider.Factory, 默认使用defaultViewModelProviderFactory
 * ```
 * by applicationViewModels()
 * ```
 */
@MainThread
inline fun <reified VM : ViewModel> ComponentActivity.applicationViewModels(
    noinline factoryProducer: () -> ViewModelProvider.Factory = { defaultViewModelProviderFactory }
): Lazy<VM> =
    createApplicationViewModelLazy(factoryProducer)

/**
 * 获取 Application 级别的 ViewModel 实例，支持 Hilt
 * @param factoryProducer ViewModelProvider.Factory, 默认使用defaultViewModelProviderFactory
 * ```
 * by applicationViewModels()
 * ```
 */
@MainThread
inline fun <reified VM : ViewModel> Fragment.applicationViewModels(
    noinline factoryProducer: () -> ViewModelProvider.Factory = { defaultViewModelProviderFactory }
): Lazy<VM> =
    createApplicationViewModelLazy(factoryProducer)

/**
 * 创建 Application 级别的 ViewModel 实例，支持 Hilt
 * @param factoryProducer ViewModelProvider.Factory, 默认使用defaultViewModelProviderFactory
 */
@MainThread
inline fun <reified VM : ViewModel> createApplicationViewModelLazy(
    noinline factoryProducer: () -> ViewModelProvider.Factory
) =
    ViewModelLazy(VM::class, { applicationViewModelStore }, factoryProducer)