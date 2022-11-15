# Utils-helper

> 是一个简化 `Android` 开发的 `Kotlin` 工具库，可以使代码更加简洁易读。**目前有超过 500 个常用方法或属性，能有效提高开发效率**。

## Gradle

在根目录的 `build.gradle` 添加：

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

添加依赖：

```groovy
api "com.github.GuoYangGit.AndroidUtils:utils-helper:xxx"
```

> 下面用法中含有方括号 `[]` 的参数是可选的。

## Activity.kt

| 方法                                                         | 说明                                    |
| ------------------------------------------------------------ | --------------------------------------- |
| `[Activity].startActivity<T>("id" to 5)`                     | 启动 Activity                           |
| `Activity.finishWithResult("id" to 5, "name" to name)`       | 关闭 Activity 并返回结果                |
| `activityList`                                               | 获取 Activity 栈链表                    |
| `topActivity`                                                | 获取栈顶 Activity                       |
| `isActivityExistsInStack<SomeOtherActivity>`                 | 判断 Activity 是否存在栈中              |
| `finishActivity<SomeOtherActivity>`                          | 结束某个 Activity                       |
| `finishToActivity<SomeOtherActivity>`                        | 结束到某个 Activity                     |
| `finishAllActivities`                                        | 结束所有 Activity                       |
| `finishAllActivitiesExcept<T>`                               | 结束除某个 Activity 之外的所有 Activity |
| `finishAllActivitiesExceptNewest`                            | 结束除最新 Activity 之外的所有 Activity |
| `ComponentActivity.pressBackTwiceToExitApp(toastText, [delayMillis])` | 双击返回退出 App                        |
| `ComponentActivity.pressBackTwiceToExitApp([delayMillis]) {...}` | 双击返回退出 App，自定义 Toast          |
| `ComponentActivity.pressBackToNotExit()`                     | 点击返回不退出 App                      |
| `ComponentActivity.doOnBackPressed {...}`                    | 监听手机的返回事件                      |
| `Context.isPermissionGranted(permission)`                    | 判断是否有权限                          |

## Application.kt

| 用法                         | 作用                       |
| ---------------------------- | -------------------------- |
| `application`                | 获取 Application           |
| `packageName`                | 获取包名                   |
| `appName`                    | 获取 App 名字              |
| `appIcon`                    | 获取 App 图标              |
| `appVersionName`             | 获取 App 版本号            |
| `appVersionCode`             | 获取 App 版本码            |
| `isAppDebug`                 | 判断 App 是否是 Debug 版本 |
| `isAppDarkMode`              | 判断 App 是否是夜间模式    |
| `launchAppDetailsSettings`   | 启动 App 详情设置          |
| `relaunchApp([killProcess])` | 重启 App                   |

## AppForegroundObserver

| 用法                            | 作用               |
| ------------------------------- | ------------------ |
| ``AppForegroundObserver {...}`` | App 前后台切换监听 |

## Bluetooth.kt

| 用法                           | 作用                   |
| ------------------------------ | ---------------------- |
| `isBluetoothEnabled`           | 判断蓝牙是否开启       |
| `BluetoothStateLiveData {...}` | 监听蓝牙设备的连接状态 |

## Bundle.kt

| 用法                                   | 作用                                         |
| -------------------------------------- | -------------------------------------------- |
| `by Activity.bundle([default], [key])` | 通过 Intent 的 extras 获取含默认值的参数     |
| `by Fragment.bundle([default], [key])` | 通过 Fragment 的 argments 获取含默认值的参数 |

## Clipboard.kt

| 用法                                                     | 作用             |
| -------------------------------------------------------- | ---------------- |
| `CharSequence/Uri/Intent.copyToClipboard([label])`       | 复制到剪贴板     |
| `getTextFromClipboard()`                                 | 获取剪贴板的文本 |
| `clearClipboard()`                                       | 清理剪贴板       |
| `doOnClipboardChanged(listener)`                         | 监听剪贴板的变化 |
| `ClipboardManager.OnPrimaryClipChangedListener.cancel()` | 移除剪贴板监听器 |

## Crash.kt

| 用法                               | 作用               |
| ---------------------------------- | ------------------ |
| `saveCrashLogLocally([dirPath])`   | 保存崩溃日志到本地 |
| `handleUncaughtException {...}`    | 处理未捕获的异常   |
| `handleMainThreadException  {...}` | 处理主线程的异常   |

## DateTime.kt

| 用法                                                         | 作用                     |
| ------------------------------------------------------------ | ------------------------ |
| `Instant.format(pattern, [zone], [locale])`                  | Instant 转字符串         |
| `LocalDateTime.format(pattern, [locale])`                    | LocalDateTime 转字符串   |
| `LocalDate.format(pattern, [locale])`                        | LocalDate 转字符串       |
| `LocalDateTime.toInstant(pattern, [zone])`                   | LocalDateTime 转 Instant |
| `Instant.toLocalDateTime(pattern, [zone])`                   | Instant 转 LocalDateTime |
| `LocalDateTime.toEpochSecond(pattern, [zone])`               | LocalDateTime 转秒数     |
| `LocalDateTime.toEpochMilli(pattern, [zone])`                | LocalDateTime 转毫秒     |
| `String.toInstant(pattern, [zone])`                          | 字符串转 Instant         |
| `String.toLocalDateTime(pattern)`                            | 字符串转 LocalDateTime   |
| `String.toLocalDate(pattern)`                                | 字符串转 LocalDate       |
| `String.toEpochMilliseconds(pattern)`                        | 字符串转毫秒             |
| `String.toEpochSeconds(pattern)`                             | 字符串转秒数             |
| `LocalDateTime/LocalDate.isToday([zone])`                    | 判断是不是今天           |
| `LocalDateTime/LocalDate.isYesterday([zone])`                | 判断是不是昨天           |
| `LocalDateTime/LocalDate.firstDayOfYear()`                   | 今年的第一天             |
| `LocalDateTime/LocalDate.lastDayOfYear()`                    | 今年的最后一天           |
| `LocalDateTime/LocalDate.firstDayOfNextYear()`               | 名年的第一天             |
| `LocalDateTime/LocalDate.firstDayOfLastYear()`               | 去年的第一天             |
| `LocalDateTime/LocalDate.firstDayOfMonth()`                  | 这个月的第一天           |
| `LocalDateTime/LocalDate.lastDayOfMonth()`                   | 这个月的最后一天         |
| `LocalDateTime/LocalDate.firstDayOfNextMonth()`              | 下个月的第一天           |
| `LocalDateTime/LocalDate.firstDayOfLastMonth()`              | 上个月的第一天           |
| `LocalDateTime/LocalDate.firstInMonth(dayOfWeek)`            | 这个月的第一个周几       |
| `LocalDateTime/LocalDate.lastInMonth(dayOfWeek)`             | 这个月的最后一个周几     |
| `LocalDateTime/LocalDate.dayOfWeekInMonth(ordinal, dayOfWeek)` | 这个月的第几个周几       |
| `LocalDateTime/LocalDate.next(dayOfWeek)`                    | 下一个周几，不包含今天   |
| `LocalDateTime/LocalDate.nextOrSame(dayOfWeek)`              | 下一个周几，包含今天     |
| `LocalDateTime/LocalDate.previous(dayOfWeek)`                | 上一个周几，不包含今天   |
| `LocalDateTime/LocalDate.previousOrSame(dayOfWeek)`          | 上一个周几，包含今天     |

## DeviceInfo.kt

| 用法                 | 作用               |
| -------------------- | ------------------ |
| `sdkVersionName`     | 获取设备系统版本号 |
| `sdkVersionCode`     | 获取设备系统版本码 |
| `deviceManufacturer` | 获取设备厂商       |
| `deviceModel`        | 获取设备型号       |

## Dimensions.kt

| 用法                             | 作用     |
| -------------------------------- | -------- |
| `Int/Long/Float/Double.dp`       | dp 转 px |
| `Int/Long/Float/Double.sp`       | sp 转 px |
| `Int/Long/Float/Double.pxToDp()` | px 转 dp |
| `Int/Long/Float/Double.pxToSp()` | px 转 sp |

## DownloadManager.kt

| 用法                  | 作用                              |
| --------------------- | --------------------------------- |
| `download(url) {...}` | 使用原生 DownloadManager 下载文件 |

## Encode.kt

| 用法                                     | 作用        |
| ---------------------------------------- | ----------- |
| `ByteArray.base64Encode([flag])`         | Base64 编码 |
| `ByteArray.base64EncodeToString([flag])` | Base64 编码 |
| `String.base64Decode([flag])`            | Base64 解码 |
| `String.urlEncode(enc)`                  | Url 编码    |
| `String.urlDecode(enc)`                  | Url 解码    |

## Encrypt.kt

| 用法                                      | 作用             |
| ----------------------------------------- | ---------------- |
| `String/ByteArray.encrtpyMD5()`           | MD5 加密         |
| `String/ByteArray.encrtpySHA1()`          | SHA1 加密        |
| `String/ByteArray.encrtpySHA256()`        | SHA256 加密      |
| `String/ByteArray.encrtpySHA512()`        | SHA512 加密      |
| `String/ByteArray.encrtpyHmacSHA1(key)`   | HMAC-SHA1 加密   |
| `String/ByteArray.encrtpyHmacSHA256(key)` | HMAC-SHA256 加密 |
| `String/ByteArray.encrtpyHmacSHA512(key)` | HMAC-SHA512 加密 |

## File.kt

| 用法                                    | 作用                               |
| --------------------------------------- | ---------------------------------- |
| `File.isExistOrCreateNewFile()`         | 判断是否存在，不存在则创建新文件   |
| `File?.isExistOrCreateNewDir()`         | 判断是否存在，不存在则创建新文件夹 |
| `File.createNewFileAfterDeleteExist()`  | 创建新文件，如果文件已存在则先删除 |
| `File.rename(name)`                     | 重命名文件                         |
| `File.mimeType`                         | 获取文件的 MIME 类型               |
| `fileSeparator`                         | 获取文件分隔符                     |
| `File.print {...}`                      | 打印内容到文件                     |
| `File.checkMD5(md5)`                    | 检测 MD5 的值                      |
| `File.checkSHA1(sha1)`                  | 检测 SHA1 的值                     |
| `File.checkSHA256(sha256)`              | 检测 SHA256 的值                   |
| `File.checkSHA512(sha512)`              | 检测 SHA512 的值                   |
| `File.checkHmacSHA1(key, hmacSHA1)`     | 检测 HMAC-SHA1 的值                |
| `File.checkHmacSHA256(key, hmacSHA256)` | 检测 HMAC-SHA256 的值              |
| `File.checkHmacSHA512(key, hmacSHA512)` | 检测 HMAC-SHA512 的值              |
| `File.calculateMD5()`                   | 计算 MD5 的值                      |
| `File.calculateSHA1()`                  | 计算 SHA1 的值                     |
| `File.calculateSHA256()`                | 计算 SHA256 的值                   |
| `File.calculateSHA512()`                | 计算 SHA512 的值                   |
| `File.calculateHmacSHA1(key)`           | 计算 HMAC-SHA1 的值                |
| `File.calculateHmacSHA256(key)`         | 计算 HMAC-SHA256 的值              |
| `File.calculateHmacSHA512(key)`         | 计算 HMAC-SHA512 的值              |

## Flow.kt

| 用法                                                        | 作用                           |
| ----------------------------------------------------------- | ------------------------------ |
| `Flow<T>.launchAndCollectIn(owner, [minActiveState]) {...}` | 启动 lifecycleScope 并收集数据 |

## Fragment.kt

| 用法                                  | 作用                         |
| ------------------------------------- |----------------------------|
| `Fragment.withArguments("id" to 5)	` | 创建 Fragment 时伴随参数          |
| `Fragment.pressBackTwiceToExitApp(toastText, [delayMillis])` | 双击返回退出 App                 |
| `Fragment.pressBackTwiceToExitApp([delayMillis]) {...}` | 双击返回退出 App，自定义 Toast       |
| `Fragment.pressBackToNotExit()`                     | 点击返回不退出 App                |
| `Fragment.doOnBackPressed {...}`                     | 监听手机的返回事件                  |

## Intents.kt

| 用法                                     | 作用                                            |
| ---------------------------------------- | ----------------------------------------------- |
| `intentOf<SomeOtherActivity>("id" to 5)` | 创建 Intent                                     |
| `by Activity.intentExtras(key)`          | 通过 Intent 的 extras 获取可空的参数            |
| `by Activity.intentExtras(key, default)` | 通过 Intent 的 extras 获取含默认值的参数        |
| `by Activity.safeIntentExtras(key)`      | 通过 Intent 的 extras 获取人为保证非空的参数    |
| `dial(phoneNumber)`                      | 拨号                                            |
| `makeCall(phoneNumber)`                  | 打电话                                          |
| `sendSMS(phoneNumber, content)`          | 发短信                                          |
| `browse(url, [newTask])`                 | 打开网页                                        |
| `email(email, [subject], [text])`        | 发送邮件                                        |
| `installAPK(uri)`                        | 安装 APK                                        |
| `Intent.clearTask()`                     | 添加 FLAG_ACTIVITY_CLEAR_TASK 的 flag           |
| `Intent.clearTop()`                      | 添加 FLAG_ACTIVITY_CLEAR_TOP 的 flag            |
| `Intent.newDocument()`                   | 添加 FLAG_ACTIVITY_NEW_DOCUMENT 的 flag         |
| `Intent.excludeFromRecents()`            | 添加 FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS 的 flag |
| `Intent.multipleTask()`                  | 添加 FLAG_ACTIVITY_MULTIPLE_TASK 的 flag        |
| `Intent.newTask()`                       | 添加 FLAG_ACTIVITY_NEW_TASK 的 flag             |
| `Intent.noAnimation()`                   | 添加 FLAG_ACTIVITY_NO_ANIMATION 的 flag         |
| `Intent.noHistory()`                     | 添加 FLAG_ACTIVITY_NO_HISTORY 的 flag           |
| `Intent.singleTop()`                     | 添加 FLAG_ACTIVITY_SINGLE_TOP 的 flag           |
| `Intent.grantReadPermission()`           | 添加 FLAG_GRANT_READ_URI_PERMISSION 的 flag     |

## Keyboard.kt

| 用法                      | 作用             |
| ------------------------ | ---------------- |
| `View.showKeyboard()`    | 显示键盘         |
| `View.hideKeyboard()`    | 隐藏键盘         |
| `View.toggleKeyboard()`  | 切换键盘显示状态 |
| `View.isKeyboardVisible` | 判断键盘是否可见 |
| `View.keyboardHeight`    | 获取键盘高度     |

## Lifecycle.kt

| 用法                                     | 作用                                   |
| ---------------------------------------- | -------------------------------------- |
| `Application.doOnActivityLifecycle(...)` | 监听所有 Activity 的生命周期           |
| `LifecycleOwner.doOnLifecycle {...}`     | 监听当前 Activity 或 Fragment 生命周期 |
| `Fragment.doOnViewLifecycle {...}`       | 监听当前 Fragment 视图的生命周期       |

## MetaData.kt

| 用法                          | 作用                             |
| ----------------------------- | -------------------------------- |
| `applicationMetaDataOf(name)` | 获取 application 的 meta-data 值 |
| `activityMetaDataOf<T>(name)` | 获取 activity 的 meta-data 值    |
| `serviceMetaDataOf<T>(name)`  | 获取 service 的 meta-data 值     |
| `providerMetaDataOf<T>(name)` | 获取 provider 的 meta-data 值    |
| `receiverMetaDataOf<T>(name)` | 获取 receiver 的 meta-data 值    |

## Network.kt

| 用法                         | 作用                   |
| ---------------------------- | ---------------------- |
| `isNetworkAvailable`         | 判断网络是否可用       |
| `isWifiConnected`            | 判断网络是否是  Wifi   |
| `isMobileData`               | 判断网络是否是移动数据 |
| `isWifiEnabled`              | 判断 Wifi 是否打开     |
| `ScanResult.is24GHz`         | 判断扫描结果是否是 2.4GHz |
| `ScanResult.is5GHz`          | 判断扫描结果是否是 5GHz |
| `NetworkAvailableLiveData()` | 监听网络状态改变       |
| `WifiListLiveData()`         | 监听扫描的 Wifi 列表   |

## Paths.kt

| 用法                           | 作用                         |
| ------------------------------ | ---------------------------- |
| `cacheDirPath`                 | 获取应用缓存路径（优先外存） |
| `externalCacheDirPath`         | 获取外存应用缓存路径         |
| `externalFilesDirPath`         | 获取外存应用文件路径         |
| `externalPicturesDirPath`      | 获取外存应用图片路径         |
| `externalMoviesDirPath`        | 获取外存应用视频路径         |
| `externalDownloadsDirPath`     | 获取外存应用下载路径         |
| `externalDocumentsDirPath`     | 获取外存应用文档路径         |
| `externalMusicDirPath`         | 获取外存应用音乐路径         |
| `externalPodcastsDirPath`      | 获取外存应用播客路径         |
| `externalRingtonesDirPath`     | 获取外存应用铃声路径         |
| `externalAlarmsDirPath`        | 获取外存应用闹铃路径         |
| `externalNotificationsDirPath` | 获取外存应用通知路径         |
| `internalCacheDirPath`         | 获取内存应用缓存路径         |
| `internalFileDirPath`          | 获取内存应用文件路径         |
| `internalPicturesDirPath`      | 获取内存应用图片路径         |
| `internalMoviesDirPath`        | 获取内存应用视频路径         |
| `internalDownloadsDirPath`     | 获取内存应用下载路径         |
| `internalDocumentsDirPath`     | 获取内存应用文档路径         |
| `internalMusicDirPath`         | 获取内存应用音乐路径         |
| `internalPodcastsDirPath`      | 获取内存应用播客路径         |
| `internalRingtonesDirPath`     | 获取内存应用铃声路径         |
| `internalNotificationsDirPath` | 获取内存应用通知路径         |
| `isExternalStorageWritable`    | 判断外存是否可读写           |
| `isExternalStorageReadable`    | 判断外存是否可读             |
| `isExternalStorageRemovable`   | 判断外存是否可移除           |

## Resource.kt

| 用法                                          | 作用                  |
| --------------------------------------------- | --------------------- |
| `View.getString(id)`                          | 根据 id 获取字符串    |
| `Context/Fragment/View.getDimension(id)`      | 根据 id 获取数字      |
| `Context/Fragment/View.getCompatColor(id)`    | 根据 id 获取颜色      |
| `Context/Fragment/View.getCompatDrawable(id)` | 根据 id 获取 Drawable |
| `Context/Fragment/View.getCompatFont(id)`     | 根据 id 获取字体      |
| `parseColor(colorString)`                     | 根据字符串获取颜色    |

## Rom.kt

| 用法             | 作用               |
| ---------------- | ---------------- |
| `isXiaomiRom`    | 判断是否是小米 Rom  |
| `isHuaweiRom`    | 判断是否是华为 Rom  |
| `isOppoRom`      | 判断是否是 OPPO Rom |
| `isVivoRom`      | 判断是否是 vivo Rom |
| `isOnePlusRom`   | 判断是否是一加 Rom  |
| `isSmartisanRom` | 判断是否是锤子 Rom  |
| `isMeiZuRom`     | 判断是否是魅族 Rom  |
| `isSamsungRom`   | 判断是否是三星 Rom  |
| `isGoogleRom`    | 判断是否是谷歌 Rom  |
| `isSonyRom`      | 判断是否是索尼 Rom  |
| `isRomOf(names)` | 判断是否是某个 Rom  |
| `isHarmonyOS`    | 判断是否是鸿蒙系统  |

## Screen.kt

| 用法                             | 作用               |
| -------------------------------- | ------------------ |
| `screenWidth`                    | 获取屏幕宽度       |
| `screenHeight`                   | 获取屏幕高度       |
| `Activity/Fragment.isFullScreen` | 判断或设置是否全屏 |
| `Activity/Fragment.isLandscape`  | 判断或设置是否横屏 |
| `Activity/Fragment.isPortrait`   | 判断或设置是否竖屏 |

## Shell.kt

| 用法                                     | 作用               |
| --------------------------------------- | ------------------ |
| `executeCmd(command)`                   | 执行命令             |

## StatusBar.kt

| 用法                                     | 作用               |
| --------------------------------------- | ------------------ |
| `Activity.immersive([view], [darkMode])`                   | 设置全透明状态栏或者状态栏颜色             |
| `Activity.immersiveRes([color], [darkMode])`                   | 和上面函数区别是使用颜色资源值             |
| `Activity.darkMode([darkMode])`                   | 设置状态栏文字颜色为黑色             |
| `View.statusPadding()`                   | 为 View 的 PaddingTop 增加一个状态栏高度             |
| `Activity.setNavigationBar`                   | 显示导航栏 (系统开启可以隐藏, 系统未开启不能开启)             |
| `Activity.setFullscreen`                   | 显示全屏             |
| `Activity?.isNavigationBar`                   | 是否存在导航栏             |
| `Context?.navigationBarHeight`                   | 导航栏高度             |
| `Context?.statusBarHeight`                   | 状态栏高度             |

## String.kt

| 用法                              | 作用                               |
| --------------------------------- | ---------------------------------- |
| `randomUUIDString`                | 获取随机 UUID 字符串               |
| `Long.toFileSizeString()`         | 数字转文件大小字符串               |
| `Long.toShortFileSizeString()`    | 数字转精度位数较少的文件大小字符串  |
| `String.limitLength(length: Int)` | 限制字符长度                      |
| `String.isPhone()`                | 判断是否是手机号                   |
| `String.isDomainName()`           | 判断是否是域名                     |
| `String.isEmail()`                | 判断是否是邮箱                     |
| `String.isIP()`                   | 判断是否是 IP 地址                 |
| `String.isWebUrl()`               | 判断是否是网址                     |
| `String.isIDCard15()`             | 判断是否是 15 位身份证号码         |
| `String.isIDCard18()`             | 判断是否是 18 位身份证号码         |
| `String.isJson()`                 | 判断是否是 Json 字符串             |
| `Float/Double.toNumberString(...)` | 小数转为字符串，默认保留小数点后两位  |

## Threads.kt

| 用法                              | 作用         |
| --------------------------------- | ------------ |
| `isMainThread`                    | 是否在主线程 |
| `mainThread([delayMillis]) {...}` | 在主线程运行 |

## Toast.kt

| 用法                                  | 作用                          |
| ------------------------------------- | ----------------------------- |
| `Context/Fragment.toast(message)`     | 吐司                          |
| `Context/Fragment.longToast(message)` | 长吐司                        |

## View.kt

| 用法                                                         | 作用                              |
| ------------------------------------------------------------ | --------------------------------- |
| `View/List<View>.doOnClick {...}`                            | 设置点击事件                      |
| `View/List<View>.doOnLongClick {...}`                        | 设置长按事件                      |
| `View/List<View>.doOnDebouncingClick([interval]) {...}`      | 设置点击事件                      |
| `View/List<View>.doOnDebouncingLongClick([interval]) {...}`  | 设置长按事件                      |
| `View.expandClickArea(...)`                                  | 增大点击区域                      |
| `View.roundCorners`                                          | 设置圆角                          |
| `View?.isTouchedAt(x, y)`                                    | 判断控件是否在触摸位置上          |
| `View.findTouchedChild(view, x, y)`                          | 寻找触摸位置上的子控件            |
| `View.locationOnScreen`                                      | 获取控件在屏幕的位置              |
| `View.withStyledAttributes(set, attrs, [defStyleAttr], [defStyleRes]) {...}` | 获取自定义属性                    |
| `View.rootWindowInsetsCompat`                                | 获取根视图的 WinowInsetsCompat    |
| `View.windowInsetsControllerCompat`                          | 获取 WindowInsetsControllerCompat |

## ViewModel.kt

| 用法                                                    | 作用                                              |
| ------------------------------------------------------- | ------------------------------------------------- |
| `by ComponentActivity/Fragment.applicationViewModels()` | 获取 Application 级别的 ViewModel 实例，支持 Hilt |

## Api

[具体查看](https://guoyanggit.github.io/AndroidUtils/api/utils-helper/)