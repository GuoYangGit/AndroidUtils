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

<img src="https://jitpack.io/v/GuoYangGit/AndroidUtils.svg"/>

> 根据自己需求进行选择

```groovy
def android_utils = "0.0.2-snapshot"
// 框架基础库
api "com.github.GuoYangGit.AndroidUtils:base:$android_utils"
// Log日志库
api "com.github.GuoYangGit.AndroidUtils:xloghelper:$android_utils"
// Kotlin工具类
api "com.github.GuoYangGit.AndroidUtils:utils-helper:$android_utils"
// 视图绑定ViewBinding
api "com.github.GuoYangGit.AndroidUtils:viewbinding-helper:$android_utils"
```

## 模块

| 名称                                          | 简介                        |
| --------------------------------------------- | --------------------------- |
| [base](./base.md)                             | Android 框架基础库          |
| [xloghelper](./xlog-helper.md)                | Log 日志库                  |
| [utils-helpe](./utils-helper.md)              | Kotlin 工具类               |
| [viewbinding-helper](./viewbinding-helper.md) | 视图绑定 ViewBinding 工具类 |
