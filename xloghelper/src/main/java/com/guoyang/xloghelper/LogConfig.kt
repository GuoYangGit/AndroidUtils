package com.guoyang.xloghelper

/**
 * 日志配置类
 * @author yang.guo on 2022/11/11
 */
class LogConfig(
    val isDebug: Boolean, // 是否是Debug模式
    val logPath: String, // 写入的文件目录
    val cachePath: String, // 缓存目录
    val namePrefix: String, // 日志文件名的前缀
) {

    // 缓存天数，在多少天以后从缓存目录移到日志目录，一般情况下填0即可
    var cacheDays: Int = 0

    // 加密的公钥，如果不加密，可以不填
    var pubKey: String = ""
}