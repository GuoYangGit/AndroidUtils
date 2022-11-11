@file:Suppress("unused")

package com.guoyang.utils_helper

import android.util.Base64
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * Base64 编码
 */
fun ByteArray.base64Encode(flag: Int = Base64.DEFAULT): ByteArray =
    Base64.encode(this, flag)

/**
 * Base64 编码
 */
fun ByteArray.base64EncodeToString(flag: Int = Base64.DEFAULT): String =
    Base64.encodeToString(this, flag)

/**
 * Base64 解码
 */
fun String.base64Decode(flag: Int = Base64.DEFAULT): ByteArray =
    Base64.decode(this, flag)

/**
 * Url 编码
 */
fun String.urlEncode(enc: String): String =
    URLEncoder.encode(this, enc)

/**
 * Url 解码
 */
fun String.urlDecode(enc: String): String =
    URLDecoder.decode(this, enc)