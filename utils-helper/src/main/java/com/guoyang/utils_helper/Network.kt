@file:Suppress("unused")

package com.guoyang.utils_helper

import android.Manifest.permission.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.*
import android.net.NetworkRequest
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.content.getSystemService
import androidx.lifecycle.LiveData

/**
 * 判断网络是否可用
 * @return Boolean true:可用 false:不可用
 */
@get:RequiresPermission(ACCESS_NETWORK_STATE)
val isNetworkAvailable: Boolean
    get() = application.getSystemService<ConnectivityManager>()?.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getNetworkCapabilities(activeNetwork)?.run {
                hasCapability(NET_CAPABILITY_INTERNET) && hasCapability(NET_CAPABILITY_VALIDATED)
            }
        } else {
            @Suppress("DEPRECATION")
            activeNetworkInfo?.isConnectedOrConnecting
        }
    } ?: false

/**
 * 判断网络是否是 Wifi
 * @return Boolean true:是 false:不是
 */
@get:RequiresPermission(ACCESS_NETWORK_STATE)
val isWifiConnected: Boolean
    get() = application.getSystemService<ConnectivityManager>()?.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getNetworkCapabilities(activeNetwork)?.hasTransport(TRANSPORT_WIFI)
        } else {
            @Suppress("DEPRECATION")
            activeNetworkInfo?.run { isConnected && type == ConnectivityManager.TYPE_WIFI }
        }
    } ?: false

/**
 * 判断网络是否是移动数据
 * @return Boolean true:是 false:不是
 */
@get:RequiresPermission(ACCESS_NETWORK_STATE)
val isMobileData: Boolean
    get() = application.getSystemService<ConnectivityManager>()?.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getNetworkCapabilities(activeNetwork)?.hasTransport(TRANSPORT_CELLULAR)
        } else {
            @Suppress("DEPRECATION")
            activeNetworkInfo?.run { isAvailable && type == ConnectivityManager.TYPE_MOBILE }
        }
    } ?: false

/**
 * 判断 Wifi 是否打开
 * @return Boolean true:打开 false:关闭
 */
@get:RequiresPermission(ACCESS_WIFI_STATE)
inline val isWifiEnabled: Boolean
    get() = application.getSystemService<WifiManager>()?.isWifiEnabled == true

/**
 * 判断扫描结果是否是 2.4GHz
 */
inline val ScanResult.is24GHz: Boolean
    get() = frequency in 2400..2550

/**
 * 判断扫描结果是否是 5GHz
 */
inline val ScanResult.is5GHz: Boolean
    get() = frequency in 5500..5800

/**
 * 监听网络状态改变(使用LiveData)
 * ```
 * NetworkAvailableLiveData().observe(this) {
 *     Log.d("NetworkAvailableLiveData", "isAvailable: $it")
 * })
 * ```
 */
class NetworkAvailableLiveData @RequiresPermission(ACCESS_NETWORK_STATE) constructor() :
    LiveData<Boolean>() {

    private val connectivityManager = application.getSystemService<ConnectivityManager>()

    @RequiresPermission(ACCESS_NETWORK_STATE)
    override fun onActive() {
        super.onActive()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ->
                connectivityManager?.registerDefaultNetworkCallback(networkCallback)
            else ->
                connectivityManager?.registerNetworkCallback(networkRequest, networkCallback)
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager?.unregisterNetworkCallback(networkCallback)
    }

    override fun setValue(value: Boolean?) {
        if (this.value == value) return
        super.setValue(value)
    }

    private val networkRequest by lazy {
        NetworkRequest.Builder()
            .addTransportType(TRANSPORT_CELLULAR)
            .addTransportType(TRANSPORT_ETHERNET)
            .addTransportType(TRANSPORT_WIFI)
            .build()
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                postValue(true)
            }
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                networkCapabilities.run {
                    postValue(
                        hasCapability(NET_CAPABILITY_INTERNET) && hasCapability(
                            NET_CAPABILITY_VALIDATED
                        )
                    )
                }
            }
        }

        override fun onLost(network: Network) {
            postValue(false)
        }
    }
}

/**
 * 监听扫描的 Wifi 列表(使用LiveData)
 * ```
 * WifiListLiveData().observe(this) {
 *     Log.d("NetworkAvailableLiveData", "isAvailable: $it")
 * })
 * ```
 */
class WifiListLiveData @RequiresPermission(allOf = [ACCESS_WIFI_STATE, CHANGE_WIFI_STATE]) constructor() :
    LiveData<List<ScanResult>?>() {

    private val wifiManager: WifiManager by lazy(LazyThreadSafetyMode.NONE) {
        application.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    override fun onActive() {
        application.registerReceiver(
            wifiScanReceiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )
    }

    override fun onInactive() {
        application.unregisterReceiver(wifiScanReceiver)
    }

    private val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            ) {
                value = wifiManager.scanResults
            }
        }
    }
}