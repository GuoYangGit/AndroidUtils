# Log日志库(基于XLog实现)

> 该库主要用于日志打印和本地化保存，考虑性能方面的问题，基于 [mars-xlog](https://github.com/Tencent/mars) 实现。

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

添加配置和依赖：

```groovy
api "com.github.GuoYangGit.AndroidUtils:xloghelper:xxx"
```

## 初始化

在 `Application` 的 `onCreate` 中进行初始化操作(**建议初始化越早越好**)

```kotlin
class MyApplication : Application(){
   override fun onCreate() {
     		// 初始化日志打印
        LogHelper.init(application, BuildConfig.DEBUG, LOG_PATH) {
            this.pubKey = XLOG_PUBKEY // 这里是日志文件加密公钥
        }
   }
}

```

## 日志打印

> 下面用法中含有方括号 `[]` 的参数是可选的。

| 方法                    | 说明         |
| ----------------------- | ------------ |
| `xLogV([tag], message)` | 详细日志输出 |
| `xLogD([tag], message)` | 调试日志输出 |
| `xLogI([tag], message)` | 信息日志输出 |
| `xLogW([tag], message)` | 警告日志输出 |
| `xLogE([tag], message)` | 错误日志输出 |
| `xLogF([tag], message)` | 致命日志输出 |

## Api

[具体查看](/api/xlog-helper/)
