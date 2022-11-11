@file:Suppress("unused")

package com.guoyang.utils_helper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField.DAY_OF_MONTH
import java.time.temporal.ChronoField.DAY_OF_YEAR
import java.time.temporal.ChronoUnit
import java.time.temporal.ChronoUnit.MONTHS
import java.time.temporal.ChronoUnit.YEARS
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

val systemZoneId: ZoneId by object : ReadOnlyProperty<Any?, ZoneId> {
    private lateinit var zoneId: ZoneId

    override fun getValue(thisRef: Any?, property: KProperty<*>): ZoneId {
        if (!::zoneId.isInitialized) {
            zoneId = ZoneId.systemDefault()
            application.registerReceiver(object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent?) {
                    if (intent?.action == Intent.ACTION_TIMEZONE_CHANGED) {
                        TimeZone.setDefault(null)
                        zoneId = ZoneId.systemDefault()
                    }
                }
            }, IntentFilter(Intent.ACTION_TIMEZONE_CHANGED))
        }
        return zoneId
    }
}

/**
 * Instant 转字符串
 * @param pattern 格式化字符串
 * @param zone 时区
 * @param locale 语言环境
 * @return 格式化后的字符串
 */
fun Instant.format(pattern: String, zone: ZoneId = systemZoneId, locale: Locale? = null): String =
    dateTimeFormatterOf(pattern, locale).withZone(zone).format(this)

/**
 * LocalDateTime 转字符串
 * @param pattern 格式化字符串
 * @param locale 语言环境
 * @return 格式化后的字符串
 */
fun LocalDateTime.format(pattern: String, locale: Locale? = null): String =
    dateTimeFormatterOf(pattern, locale).format(this)

/**
 * LocalDate 转字符串
 * @param pattern 格式化字符串
 * @param locale 语言环境
 * @return 格式化后的字符串
 */
fun LocalDate.format(pattern: String, locale: Locale? = null): String =
    dateTimeFormatterOf(pattern, locale).format(this)

/**
 * LocalDateTime 转 Instant
 * @param zone 时区
 * @return [Instant]
 */
fun LocalDateTime.toInstant(zone: ZoneId = systemZoneId): Instant =
    atZone(zone).toInstant()

/**
 * Instant 转 LocalDateTime
 * @param zone 时区
 * @return [LocalDateTime]
 */
fun Instant.toLocalDateTime(zone: ZoneId = systemZoneId): LocalDateTime =
    LocalDateTime.ofInstant(this, zone)

/**
 * LocalDateTime 转秒数
 * @param zone 时区
 * @return 秒数
 */
fun LocalDateTime.toEpochSecond(zone: ZoneId = systemZoneId): Long =
    atZone(zone).toEpochSecond()

/**
 * LocalDateTime 转毫秒
 * @param zone 时区
 * @return 毫秒
 */
fun LocalDateTime.toEpochMilli(zone: ZoneId = systemZoneId): Long =
    toEpochSecond(zone) * 1000 + toLocalTime().nano / 1000000

/**
 * 字符串转 Instant
 * @param pattern 格式化字符串
 * @param zone 时区
 * @return [Instant]
 */
fun String.toInstant(pattern: String, zone: ZoneId = systemZoneId): Instant =
    ZonedDateTime.parse(this, DateTimeFormatter.ofPattern(pattern).withZone(zone)).toInstant()

/**
 * 字符串转 LocalDateTime
 * @param pattern 格式化字符串
 * @return [LocalDateTime]
 */
fun String.toLocalDateTime(pattern: String): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))

/**
 * 字符串转 LocalDate
 * @param pattern 格式化字符串
 * @return [LocalDate]
 */
fun String.toLocalDate(pattern: String): LocalDate =
    LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))

/**
 * 字符串转毫秒
 * @param pattern 格式化字符串
 * @param zone 时区
 * @return 毫秒
 */
fun String.toEpochMilliseconds(pattern: String, zone: ZoneId = systemZoneId): Long =
    toInstant(pattern, zone).toEpochMilli()

/**
 * 字符串转秒数
 * @param pattern 格式化字符串
 * @param zone 时区
 * @return 秒数
 */
fun String.toEpochSeconds(pattern: String, zone: ZoneId = systemZoneId): Long =
    toInstant(pattern, zone).epochSecond

/**
 * 判断是不是今天
 * @param zone 时区
 * @return true 是今天，false 不是今天
 */
fun LocalDateTime.isToday(zone: ZoneId = systemZoneId): Boolean = toLocalDate().isToday(zone)

/**
 * 判断是不是今天
 * @param zone 时区
 * @return true 是今天，false 不是今天
 */
fun LocalDate.isToday(zone: ZoneId = systemZoneId): Boolean = this == LocalDate.now(zone)

/**
 * 判断是不是昨天
 * @param zone 时区
 * @return true 是昨天，false 不是昨天
 */
fun LocalDateTime.isYesterday(zone: ZoneId = systemZoneId): Boolean =
    toLocalDate().isYesterday(zone)

/**
 * 判断是不是昨天
 * @param zone 时区
 * @return true 是昨天，false 不是昨天
 */
fun LocalDate.isYesterday(zone: ZoneId = systemZoneId): Boolean =
    this == LocalDate.now(zone).minus(1, ChronoUnit.DAYS)

/**
 * 今年的第一天
 * @return [LocalDate]
 */
fun LocalDateTime.firstDayOfYear(): LocalDateTime = with(TemporalAdjusters.firstDayOfYear())

/**
 * 今年的最后一天
 * @return [LocalDate]
 */
fun LocalDateTime.lastDayOfYear(): LocalDateTime = with(TemporalAdjusters.lastDayOfYear())

/**
 * 明年的第一天
 * @return [LocalDate]
 */
fun LocalDateTime.firstDayOfNextYear(): LocalDateTime = with(TemporalAdjusters.firstDayOfNextYear())

/**
 * 去年的第一天
 * @return [LocalDate]
 */
fun LocalDateTime.firstDayOfLastYear(): LocalDateTime =
    with { it.with(DAY_OF_YEAR, 1).minus(1, YEARS) }

/**
 * 这个月的第一天
 * @return [LocalDate]
 */
fun LocalDateTime.firstDayOfMonth(): LocalDateTime = with(TemporalAdjusters.firstDayOfMonth())

/**
 * 这个月的最后一天
 * @return [LocalDate]
 */
fun LocalDateTime.lastDayOfMonth(): LocalDateTime = with(TemporalAdjusters.lastDayOfMonth())

/**
 * 下个月的第一天
 * @return [LocalDate]
 */
fun LocalDateTime.firstDayOfNextMonth(): LocalDateTime =
    with(TemporalAdjusters.firstDayOfNextMonth())

/**
 * 上个月的第一天
 * @return [LocalDate]
 */
fun LocalDateTime.firstDayOfLastMonth(): LocalDateTime =
    with { it.with(DAY_OF_MONTH, 1).minus(1, MONTHS) }

/**
 * 这个月的第一个周几
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDateTime.firstInMonth(dayOfWeek: DayOfWeek): LocalDateTime =
    with(TemporalAdjusters.firstInMonth(dayOfWeek))

/**
 * 这个月的最后一个周几
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDateTime.lastInMonth(dayOfWeek: DayOfWeek): LocalDateTime =
    with(TemporalAdjusters.lastInMonth(dayOfWeek))

/**
 * 这个月的第几个周几
 * @param ordinal 第几个
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDateTime.dayOfWeekInMonth(ordinal: Int, dayOfWeek: DayOfWeek): LocalDateTime =
    with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek))

/**
 * 下一个周几，不包含今天
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDateTime.next(dayOfWeek: DayOfWeek): LocalDateTime =
    with(TemporalAdjusters.next(dayOfWeek))

/**
 * 下一个周几，包含今天
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDateTime.nextOrSame(dayOfWeek: DayOfWeek): LocalDateTime =
    with(TemporalAdjusters.nextOrSame(dayOfWeek))

/**
 * 上一个周几，不包含今天
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDateTime.previous(dayOfWeek: DayOfWeek): LocalDateTime =
    with(TemporalAdjusters.previous(dayOfWeek))

/**
 * 上一个周几，包含今天
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDateTime.previousOrSame(dayOfWeek: DayOfWeek): LocalDateTime =
    with(TemporalAdjusters.previousOrSame(dayOfWeek))

/**
 * 今天的开始时间
 * @return [LocalDateTime]
 */
fun LocalDate.firstDayOfYear(): LocalDate = with(TemporalAdjusters.firstDayOfYear())

/**
 * 今年的最后一天
 * @return [LocalDate]
 */
fun LocalDate.lastDayOfYear(): LocalDate = with(TemporalAdjusters.lastDayOfYear())

/**
 * 明年的第一天
 * @return [LocalDate]
 */
fun LocalDate.firstDayOfNextYear(): LocalDate = with(TemporalAdjusters.firstDayOfNextYear())

/**
 * 去年的第一天
 * @return [LocalDate]
 */
fun LocalDate.firstDayOfLastYear(): LocalDate = with { it.with(DAY_OF_YEAR, 1).minus(1, YEARS) }

/**
 * 这个月的第一天
 * @return [LocalDate]
 */
fun LocalDate.firstDayOfMonth(): LocalDate = with(TemporalAdjusters.firstDayOfMonth())

/**
 * 这个月的最后一天
 * @return [LocalDate]
 */
fun LocalDate.lastDayOfMonth(): LocalDate = with(TemporalAdjusters.lastDayOfMonth())

/**
 * 下个月的第一天
 * @return [LocalDate]
 */
fun LocalDate.firstDayOfNextMonth(): LocalDate = with(TemporalAdjusters.firstDayOfNextMonth())

/**
 * 上个月的第一天
 * @return [LocalDate]
 */
fun LocalDate.firstDayOfLastMonth(): LocalDate = with { it.with(DAY_OF_MONTH, 1).minus(1, MONTHS) }

/**
 * 这个月的第一个周几
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDate.firstInMonth(dayOfWeek: DayOfWeek): LocalDate =
    with(TemporalAdjusters.firstInMonth(dayOfWeek))

/**
 * 这个月的最后一个周几
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDate.lastInMonth(dayOfWeek: DayOfWeek): LocalDate =
    with(TemporalAdjusters.lastInMonth(dayOfWeek))

/**
 * 这个月的第几个周几
 * @param ordinal 第几个
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDate.dayOfWeekInMonth(ordinal: Int, dayOfWeek: DayOfWeek): LocalDate =
    with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek))

/**
 * 下一个周几，不包含今天
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDate.next(dayOfWeek: DayOfWeek): LocalDate = with(TemporalAdjusters.next(dayOfWeek))

/**
 * 下一个周几，包含今天
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDate.nextOrSame(dayOfWeek: DayOfWeek): LocalDate =
    with(TemporalAdjusters.nextOrSame(dayOfWeek))

/**
 * 上一个周几，不包含今天
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDate.previous(dayOfWeek: DayOfWeek): LocalDate =
    with(TemporalAdjusters.previous(dayOfWeek))

/**
 * 上一个周几，包含今天
 * @param dayOfWeek 周几
 * @return [LocalDate]
 */
fun LocalDate.previousOrSame(dayOfWeek: DayOfWeek): LocalDate =
    with(TemporalAdjusters.previousOrSame(dayOfWeek))

/**
 * 日期格式化方法
 * @param pattern 格式
 * @param locale 语言环境
 * @return [DateTimeFormatter] 格式化对象
 */
private fun dateTimeFormatterOf(pattern: String, locale: Locale?): DateTimeFormatter =
    if (locale != null) {
        DateTimeFormatter.ofPattern(pattern, locale)
    } else {
        DateTimeFormatter.ofPattern(pattern)
    }