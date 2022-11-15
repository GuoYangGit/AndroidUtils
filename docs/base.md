# Android框架基础模块

> 该模块是 `Android` 项目快速开发的基础模块，包含 `Activity/Fragment` 基类、常用扩展类、页面状态类等。

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
api "com.github.GuoYangGit.AndroidUtils:base:xxx"
```

## 使用

### Application

创建自己的 `Application` 基类，继承 `BaseApp`。

```kotlin
open class MvvmApplication : BaseApp() {

    override fun onCreate() {
        super.onCreate()
      	// 这里执行自己的业务操作逻辑
    }
}
```



### Activity

创建自己的 `Activity` 基类，继承 `BaseActivity`。

```kotlin
abstract class BaseBindingActivity<VB : ViewBinding> : BaseActivity(),
    ActivityBinding<VB> by ActivityBindingDelegate(), ILoading {
    private val viewDelegate: LoadingDelegate by lazy {
        LoadingDelegate(this)
    }

    override fun userDataBinding(): Boolean = true

    override fun initDataBind() {
        setContentViewWithBinding()
    }

    override fun layoutId(): Int = -1

    override fun showLoading(message: String) {
        viewDelegate.showLoading(message)
    }

    override fun dismissLoading() {
        viewDelegate.dismissLoading()
    }
}
```

### Fragment

创建自己的 `Fragment` 基类，继承 `BaseFragment`。

```kotlin
abstract class BaseBindingFragment<VB : ViewBinding> : BaseFragment(),
    FragmentBinding<VB> by FragmentBindingDelegate(), ILoading {
    private val viewDelegate: LoadingDelegate by lazy {
        LoadingDelegate(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = createViewWithBinding(inflater, container)

    override fun layoutId(): Int = -1

    override fun showLoading(message: String) {
        viewDelegate.showLoading(message)
    }

    override fun dismissLoading() {
        viewDelegate.dismissLoading()
    }
}
```

### EventLiveData

该类是 `LiveData` 扩展类，主要用于解决 `LiveData` 数据倒灌问题。

```kotlin
// 使用方式和LiveData一样
val eventLiveData = EventLiveData<String>()
// 发送事件
eventLiveData.postValue("event")
// 订阅事件
eventLiveData.observe(this){
  // do something
}
```

### ContextExt

`Context` 相关扩展类

| 方法                       | 说明                 |
| -------------------------- | -------------------- |
| `Context.runMainProcess{}` | 判断当前是否是主进程 |

### PermissionExt

权限相关扩展类

| 方法                                                      | 说明                         |
| --------------------------------------------------------- | ---------------------------- |
| `Activity/Fragment.requestReadOrWritePermissions{}`       | 读写权限(兼容Android 13)     |
| `Activity/Fragment.requestCameraPermissions{}`            | 相机、录音权限               |
| `Activity/Fragment.requestLocationPermissions{}`          | 定位权限(兼容Android 12)     |
| `Activity/Fragment.requestNotificationPermissions{}`      | 通知权限(兼容Android 13以下) |
| `Activity/Fragment.requestSystemAlertWindowPermissions{}` | 悬浮窗权限                   |
| `Activity/Fragment.requestWriteSettingPermissions{}`      | 系统设置权限                 |
| `Activity/Fragment.requestInstallPackagePermissions{}`    | 允许安装未知来源权限         |

使用示例：

```kotlin
requestReadOrWritePermissions { allGranted, _, _ ->
		if (!allGranted) return@requestReadOrWritePermissions
		// do something
}
```

### RecyclerViewExt

`RecyclerView` 扩展类

| 方法                                                         | 说明           |
| ------------------------------------------------------------ | -------------- |
| `Rv.linear(orientation: Int,reverseLayout: Boolean)`         | 创建线性布局   |
| `Rv.grid(spanCount: Int,orientation: Int,reverseLayout: Boolean)` | 创建网格布局   |
| `Rv.staggered(spanCount: Int,orientation: Int)`              | 创建瀑布流布局 |
| `Rv.divider(block: DefaultDecoration.() -> Unit)`            | 创建分割线     |

### ViewExt

`View` 扩展类

| 方法                     | 说明         |
| ------------------------ | ------------ |
| `View.doOnDoubleClick{}` | View双击事件 |

### UiSate

该类主要用户处理 `UI` 页面数据状态

使用方法：

```kotlin
// 通过ViewModel获取到数据的Flow
// 通过asUiStateFlow()方法转换成UiSate
// 最终在Flow流终端可以使用doSuccess、doError方法进行事件判断
followViewModel.getFollowList(index)
    .asUiStateFlow()
    .launchAndCollectIn(viewLifecycleOwner) {
        it.doSuccess { list ->
            // do something
        }.doError { throwable ->
            // do something
        }
}
```

### Weight

通用自定义 `View` 封装

| 类                     | 说明                                                         |
| ---------------------- | ------------------------------------------------------------ |
| `DefaultDecoration`    | RecyclerView最强大的分割线工具                               |
| `NestedScrollableHost` | 此类用于解决 ViewPager2  嵌套 ViewPager2 或者 RecyclerView 等相互嵌套的冲突问题 |

## Api

[具体查看](https://guoyanggit.github.io/AndroidUtils/api/base/)
