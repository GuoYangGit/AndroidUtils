@file:Suppress("unused")

package com.guoyang.utils_helper

import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString

/**
 * MD5 加密
 * @return 加密后的字符串
 */
fun String.encryptMD5(): String = encodeUtf8().md5().hex()

/**
 * MD5 加密
 * @return 加密后的字符串
 */
fun ByteArray.encryptMD5(): String = toByteString().md5().hex()

/**
 * SHA1 加密
 * @return 加密后的字符串
 */
fun String.encryptSHA1(): String = encodeUtf8().sha1().hex()

/**
 * SHA1 加密
 * @return 加密后的字符串
 */
fun ByteArray.encryptSHA1(): String = toByteString().sha1().hex()

/**
 * SHA256 加密
 * @return 加密后的字符串
 */
fun String.encryptSHA256(): String = encodeUtf8().sha256().hex()

/**
 * SHA256 加密
 * @return 加密后的字符串
 */
fun ByteArray.encryptSHA256(): String = toByteString().sha256().hex()

/**
 * SHA512 加密
 * @return 加密后的字符串
 */
fun String.encryptSHA512(): String = encodeUtf8().sha512().hex()

/**
 * SHA512 加密
 * @return 加密后的字符串
 */
fun ByteArray.encryptSHA512(): String = toByteString().sha512().hex()

/**
 * HMAC-SHA1 加密
 * @param key 密钥
 * @return 加密后的字符串
 */
fun String.encryptHmacSHA1(key: String): String = encodeUtf8().hmacSha1(key.encodeUtf8()).hex()

/**
 * HMAC-SHA1 加密
 * @param key 密钥
 * @return 加密后的字符串
 */
fun ByteArray.encryptHmacSHA1(key: String): String = toByteString().hmacSha1(key.encodeUtf8()).hex()

/**
 * HMAC-SHA256 加密
 * @param key 密钥
 * @return 加密后的字符串
 */
fun String.encryptHmacSHA256(key: String): String = encodeUtf8().hmacSha256(key.encodeUtf8()).hex()

/**
 * HMAC-SHA256 加密
 * @param key 密钥
 * @return 加密后的字符串
 */
fun ByteArray.encryptHmacSHA256(key: String): String =
    toByteString().hmacSha256(key.encodeUtf8()).hex()

/**
 * HMAC-SHA512 加密
 * @param key 密钥
 * @return 加密后的字符串
 */
fun String.encryptHmacSHA512(key: String): String = encodeUtf8().hmacSha512(key.encodeUtf8()).hex()

/**
 * HMAC-SHA512 加密
 * @param key 密钥
 * @return 加密后的字符串
 */
fun ByteArray.encryptHmacSHA512(key: String): String =
    toByteString().hmacSha512(key.encodeUtf8()).hex()