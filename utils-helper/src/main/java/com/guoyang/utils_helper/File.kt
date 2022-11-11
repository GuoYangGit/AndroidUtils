@file:Suppress("unused")

package com.guoyang.utils_helper

import okio.ByteString.Companion.encodeUtf8
import okio.HashingSink
import okio.blackholeSink
import okio.buffer
import okio.source
import java.io.*
import java.net.URLConnection

/**
 * 判断是否存在，不存在则创建新文件
 */
fun File.isExistOrCreateNewFile(): Boolean =
    try {
        if (exists()) {
            isFile
        } else {
            parentFile.isExistOrCreateNewDir() && createNewFile()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }

/**
 * 判断是否存在，不存在则创建新文件夹
 */
fun File?.isExistOrCreateNewDir(): Boolean =
    when {
        this == null -> false
        exists() -> isDirectory
        else -> mkdir()
    }

/**
 * 创建新文件，如果文件已存在则先删除
 */
fun File.createNewFileAfterDeleteExist(): Boolean =
    try {
        if (exists()) {
            delete() && parentFile.isExistOrCreateNewDir() && createNewFile()
        } else {
            parentFile.isExistOrCreateNewDir() && createNewFile()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        false
    }

/**
 * 重命名文件
 */
fun File.rename(newName: String): Boolean =
    exists() && newName.isNotEmpty() && name != newName &&
            File("${parent.orEmpty()}$fileSeparator$newName").let { renameFile ->
                !renameFile.exists() && renameTo(renameFile)
            }

/**
 * 获取文件的 MIME 类型
 */
inline val File.mimeType: String? get() = URLConnection.guessContentTypeFromName(name)

/**
 * 获取文件分隔符
 */
inline val fileSeparator: String get() = File.separator

/**
 * 打印内容到文件
 */
inline fun File.print(append: Boolean = false, crossinline block: PrintWriter.() -> Unit) =
    PrintWriter(BufferedWriter(FileWriter(this, append))).apply(block).close()

/**
 * 检测 MD5 的值是否一致
 * @param md5 MD5 值
 */
fun File.checkMD5(md5: String): Boolean = calculateMD5().equals(md5, true)

/**
 * 检查 SHA1 的值是否一致
 * @param sha1 SHA1 值
 */
fun File.checkSHA1(sha1: String): Boolean = calculateSHA1().equals(sha1, true)

/**
 * 检查 SHA256 的值是否一致
 * @param sha256 SHA256 值
 */
fun File.checkSHA256(sha256: String): Boolean = calculateSHA256().equals(sha256, true)

/**
 * 检查 SHA512 的值是否一致
 * @param sha512 SHA512 值
 */
fun File.checkSHA512(sha512: String): Boolean = calculateSHA512().equals(sha512, true)

/**
 * 检查 HMAC-SHA1 的值是否一致
 * @param key HMAC-SHA1 的 key
 * @param hmacSHA1 HMAC-SHA1 值
 */
fun File.checkHmacSHA1(key: String, hmacSHA1: String): Boolean =
    calculateHmacSHA1(key).equals(hmacSHA1, true)

/**
 * 检查 HMAC-SHA256 的值是否一致
 * @param key HMAC-SHA256 的 key
 * @param hmacSHA256 HMAC-SHA256 值
 */
fun File.checkHmacSHA256(key: String, hmacSHA256: String): Boolean =
    calculateHmacSHA256(key).equals(hmacSHA256, true)

/**
 * 检查 HMAC-SHA512 的值是否一致
 * @param key HMAC-SHA512 的 key
 * @param hmacSHA512 HMAC-SHA512 值
 */
fun File.checkHmacSHA512(key: String, hmacSHA512: String): Boolean =
    calculateHmacSHA512(key).equals(hmacSHA512, true)

/**
 * 计算 MD5 值
 * @return MD5 值
 */
fun File.calculateMD5(): String = calculateHash(HashingSink.md5(blackholeSink()))

/**
 * 计算 SHA1 值
 * @return SHA1 值
 */
fun File.calculateSHA1(): String = calculateHash(HashingSink.sha1(blackholeSink()))

/**
 * 计算 SHA256 值
 * @return SHA256 值
 */
fun File.calculateSHA256(): String = calculateHash(HashingSink.sha256(blackholeSink()))

/**
 * 计算 SHA512 值
 * @return SHA512 值
 */
fun File.calculateSHA512(): String = calculateHash(HashingSink.sha512(blackholeSink()))

/**
 * 计算 HMAC-SHA1 值
 * @param key HMAC-SHA1 的 key
 * @return HMAC-SHA1 值
 */
fun File.calculateHmacSHA1(key: String): String =
    calculateHash(HashingSink.hmacSha1(blackholeSink(), key.encodeUtf8()))

/**
 * 计算 HMAC-SHA256 值
 * @param key HMAC-SHA256 的 key
 * @return HMAC-SHA256 值
 */
fun File.calculateHmacSHA256(key: String): String =
    calculateHash(HashingSink.hmacSha256(blackholeSink(), key.encodeUtf8()))

/**
 * 计算 HMAC-SHA512 值
 * @param key HMAC-SHA512 的 key
 * @return HMAC-SHA512 值
 */
fun File.calculateHmacSHA512(key: String): String =
    calculateHash(HashingSink.hmacSha512(blackholeSink(), key.encodeUtf8()))


/**
 * 计算文件的 Hash 值
 */
private fun File.calculateHash(hashingSink: HashingSink) =
    hashingSink.use {
        source().buffer().use { source ->
            source.readAll(hashingSink)
            hashingSink.hash.hex()
        }
    }